package com.lostred.ktv.service.vod;

import com.lostred.ktv.dto.vod.LoginResponse;
import com.lostred.ktv.dto.vod.PlayerReconnectResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerReconnectVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PlayerReconnectResponse playerReconnectResponse = (PlayerReconnectResponse) JSONObject.toBean(jsonObject, PlayerReconnectResponse.class);
        RoomPO roomPO = playerReconnectResponse.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：重新登录";
        handler.showMessage(message);
        Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //如果房间被占用
        if (socket != null) {
            //通知播放客户端端房间已登录
            LoginResponse loginResponse = new LoginResponse(JsonUtil.SUCCESS, roomPO);
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(JsonUtil.toJsonString(loginResponse));
            pw.flush();
        }
    }
}
