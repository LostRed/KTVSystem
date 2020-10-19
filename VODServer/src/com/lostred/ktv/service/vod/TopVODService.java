package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dto.vod.PlaylistResponse;
import com.lostred.ktv.dto.vod.TopRequest;
import com.lostred.ktv.dto.vod.TopResponse;
import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 播放客户端请求置顶歌曲业务
 */
public class TopVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        TopRequest topRequest = (TopRequest) JSONObject.toBean(jsonObject, TopRequest.class);
        RoomPO roomPO = topRequest.getRoomPO();
        PlaylistPO playlistPO = topRequest.getPlaylistPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + playlistPO.getRoomPO().getRoomId()
                + "号房间：请求置顶歌曲《" + playlistPO.getSongPO().getSinger().getSingerName() + " - " + playlistPO.getSongPO().getSongName() + "》";
        handler.showMessage(message);
        //查询数据库中已点歌曲播放顺序最小的值
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        PlaylistPO playlistPOForemost = playlistDAO.queryPlaylistForemostAllRoom();
        int playOrder = playlistPOForemost.getPlayOrder();
        //修改当前需要置顶的歌曲的播放顺序
        playlistDAO.updatePlaylist(playOrder - 1, playlistPO);
        //查询该房间的已点歌曲
        List<PlaylistPO> list = playlistDAO.queryPlaylist(playlistPO.getRoomPO());
        //回应点播客户端更新播放列表
        TopResponse topResponse = new TopResponse(list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(topResponse));
        pw.flush();
        //如果播放服务端在线
        if (PSClient.getPSClient().isConnected()) {
            //通知播放服务端更新播放列表
            PlaylistResponse playlistResponse = new PlaylistResponse(roomPO, list);
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(playlistResponse));
        }
    }
}
