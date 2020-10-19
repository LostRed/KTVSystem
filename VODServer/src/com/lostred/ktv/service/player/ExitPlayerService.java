package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.ExitRequest;
import com.lostred.ktv.dto.player.ExitResponse;
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

/**
 * 播放服务端房间退出业务
 */
public class ExitPlayerService implements InPlayerService {
    @Override
    public void doService(PSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        ExitRequest exitRequest = (ExitRequest) JSONObject.toBean(jsonObject, ExitRequest.class);
        RoomPO roomPO = exitRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：退出成功";
        handler.showMessage(message);
        Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //如果房间处于登录状态
        if (socket != null) {
            //通知点播客户端房间已退出
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(JsonUtil.toJsonString(exitRequest));
            pw.flush();
            //回应播放服务端房间处于登录状态
            ExitResponse exitResponse = new ExitResponse(roomPO, true);
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(exitResponse));
        } else {
            //回应播放服务端房间已注销
            ExitResponse exitResponse = new ExitResponse(roomPO, false);
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(exitResponse));
        }
    }
}
