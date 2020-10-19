package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.EnterResponse;
import com.lostred.ktv.dto.player.PlayerReconnectRequest;
import com.lostred.ktv.dto.player.PlayerReconnectResponse;
import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.net.PSRecvThread;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

/**
 * 点播服务端重连回应业务
 */
public class PlayerReconnectPlayerService implements InPlayerService {
    @Override
    public void doService(PSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PlayerReconnectRequest playerReconnectRequest = (PlayerReconnectRequest) JSONObject.toBean(jsonObject, PlayerReconnectRequest.class);
        RoomPO roomPO = playerReconnectRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：重新进入";
        handler.showMessage(message);
        Set<Integer> keys = VCRecvThread.getRoomMap().keySet();
        //如果当前Map中已存在该房间
        if (keys.contains(roomPO.getRoomId())) {
            Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
            //如果该房间被占用
            if (socket != null) {
                //通知点播客户端端该房间已重新进入
                EnterResponse enterResponse = new EnterResponse(roomPO);
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(JsonUtil.toJsonString(enterResponse));
                pw.flush();
                //如果播放服务器在线
                if(PSClient.getPSClient().isConnected()){
                    //回应播放服务端该房间已登录
                    PlayerReconnectResponse reconnectResponse = new PlayerReconnectResponse(roomPO);
                    PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(reconnectResponse));
                }
            }
        } else {
            //将房间号添加到Map集合
            VCRecvThread.getRoomMap().put(roomPO.getRoomId(), null);
        }
    }
}
