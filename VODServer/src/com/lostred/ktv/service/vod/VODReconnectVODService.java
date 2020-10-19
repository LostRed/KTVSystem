package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dto.vod.PlaylistResponse;
import com.lostred.ktv.dto.vod.VODReconnectRequest;
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
 * 点播客户端请求重连业务
 */
public class VODReconnectVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        VODReconnectRequest VODReconnectRequest = (VODReconnectRequest) JSONObject.toBean(jsonObject, VODReconnectRequest.class);
        RoomPO roomPO = VODReconnectRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：重新登录";
        handler.showMessage(message);
        //查询该房间的已点歌曲
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        List<PlaylistPO> list = playlistDAO.queryPlaylist(roomPO);
        //回应播放客户端
        PlaylistResponse playlistResponse = new PlaylistResponse(roomPO, list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(playlistResponse));
        pw.flush();
        //将房间号和对应端口保存到Map集合
        VCRecvThread.getRoomMap().put(roomPO.getRoomId(), handler.getSocket());
        VCRecvThread.getClientSocketMap().put(handler.getSocket(), roomPO.getRoomId());
        //如果播放服务端在线
        if (PSClient.getPSClient().isConnected()) {
            //向播放服务端发送消息
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(VODReconnectRequest));
        }
    }
}
