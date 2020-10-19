package com.lostred.ktv.service.player;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dto.player.PlayerReconnectRequest;
import com.lostred.ktv.dto.player.PlaylistResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSServer;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 播放客户端请求重连业务
 */
public class PlayerReconnectPlayerService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PlayerReconnectRequest playerReconnectRequest = (PlayerReconnectRequest) JSONObject.toBean(jsonObject, PlayerReconnectRequest.class);
        RoomPO roomPO = playerReconnectRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：重新进入";
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
        PCRecvThread.getRoomMap().put(roomPO.getRoomId(), handler.getSocket());
        PCRecvThread.getClientSocketMap().put(handler.getSocket(), roomPO.getRoomId());
        //如果点播服务端在线
        if (VSServer.getVSServer().isConnected()) {
            //向点播服务端发送消息
            VSServer.getVSServer().sendMessage(JsonUtil.toJsonString(playerReconnectRequest));
        }
    }
}
