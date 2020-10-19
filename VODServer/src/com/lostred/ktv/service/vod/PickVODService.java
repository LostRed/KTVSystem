package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dto.vod.PickRequest;
import com.lostred.ktv.dto.vod.PickResponse;
import com.lostred.ktv.dto.vod.PlaylistResponse;
import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 点播客户端请求点歌业务
 */
public class PickVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PickRequest pickRequest = (PickRequest) JSONObject.toBean(jsonObject, PickRequest.class);
        RoomPO roomPO = pickRequest.getRoomPO();
        SongPO songPO = pickRequest.getSongPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId()
                + "号房间：请求点歌《" + songPO.getSinger().getSingerName() + " - " + songPO.getSongName() + "》";
        handler.showMessage(message);
        //将该歌曲添加到该房间的已点歌曲列表
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        playlistDAO.addPlaylist(roomPO, songPO);
        //数据库查询该歌曲的热度并将该热度加1
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        songPO = songDAO.selectSong(songPO.getSongId());
        songPO.setSongHot(songPO.getSongHot() + 1);
        songDAO.updateSong(songPO);
        //查询该房间的已点歌曲
        List<PlaylistPO> list = playlistDAO.queryPlaylist(roomPO);
        //回应点播客户端更新播放列表
        PickResponse pickResponse = new PickResponse(roomPO, songPO, list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(pickResponse));
        pw.flush();
        //如果已连接播放服务端
        if (PSClient.getPSClient().isConnected()) {
            //通知播放服务端更新播放列表
            PlaylistResponse playlistResponse = new PlaylistResponse(roomPO, list);
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(playlistResponse));
        }
    }
}
