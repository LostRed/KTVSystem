package com.lostred.ktv.service.player;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dto.player.NextRequest;
import com.lostred.ktv.dto.player.NextResponse;
import com.lostred.ktv.dto.player.PlayRequest;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSServer;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 播放客户端请求切歌业务
 */
public class NextPlayerService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        NextRequest nextRequest = (NextRequest) JSONObject.toBean(jsonObject, NextRequest.class);
        RoomPO roomPO = nextRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：请求切歌";
        handler.showMessage(message);
        //查询数据库中已点歌曲播放顺序最小的值
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        PlaylistPO playlistPO = playlistDAO.queryPlaylistForemost(roomPO);
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        SongPO songPO = songDAO.selectSong(playlistPO.getSongPO().getSongId());
        //查询歌曲文件信息
        String md5 = songPO.getMd5();
        long fileSize = songPO.getFileSize();
        String filePath = songPO.getFilePath();
        //将该歌曲从数据库中删除
        playlistDAO.deletePlaylist(playlistPO);
        //查询该房间的已点歌曲
        List<PlaylistPO> list = playlistDAO.queryPlaylist(playlistPO.getRoomPO());
        //回应播放客户端
        PlayRequest playRequest = new PlayRequest(playlistPO, list, md5, fileSize, filePath);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(playRequest));
        pw.flush();
        //如果点播服务端在线
        if (VSServer.getVSServer().isConnected()) {
            //转发给点播服务端
            NextResponse nextResponse = new NextResponse(roomPO, playlistPO, list);
            VSServer.getVSServer().sendMessage(JsonUtil.toJsonString(nextResponse));
        }
    }
}
