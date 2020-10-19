package com.lostred.ktv.service.player;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dto.player.PlaylistRequest;
import com.lostred.ktv.dto.player.PlaylistResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 播放客户端请求查询播放列表业务
 */
public class PlaylistPlayerService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PlaylistRequest playlistRequest = (PlaylistRequest) JSONObject.toBean(jsonObject, PlaylistRequest.class);
        RoomPO roomPO = playlistRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：请求查询播放列表";
        handler.showMessage(message);
        //查询该房间的已点歌曲
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        List<PlaylistPO> list = playlistDAO.queryPlaylist(roomPO);
        //回应播放客户端
        PlaylistResponse playlistResponse = new PlaylistResponse(roomPO, list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(playlistResponse));
        pw.flush();
    }
}
