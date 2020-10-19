package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dto.vod.PlaylistRequest;
import com.lostred.ktv.dto.vod.PlaylistResponse;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 点播客户端请求查询播放列表业务
 */
public class PlaylistVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PlaylistRequest playlistRequest = (PlaylistRequest) JSONObject.toBean(jsonObject, PlaylistRequest.class);
        RoomPO roomPO = playlistRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：请求查询播放列表";
        handler.showMessage(message);
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        //查询该房间的已点歌曲
        List<PlaylistPO> list = playlistDAO.queryPlaylist(roomPO);
        //回应点播客户端更新播放列表
        PlaylistResponse playlistResponse = new PlaylistResponse(roomPO, list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(playlistResponse));
        pw.flush();
    }
}
