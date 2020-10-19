package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.ExceptionExitRequest;
import com.lostred.ktv.dto.player.ExceptionExitResponse;
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
 * 播放服务端房间异常退出业务
 */
public class ExceptionExitPlayerService implements InPlayerService {
    @Override
    public void doService(PSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        ExceptionExitRequest exceptionExitRequest = (ExceptionExitRequest) JSONObject.toBean(jsonObject, ExceptionExitRequest.class);
        RoomPO roomPO = exceptionExitRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：异常退出";
        handler.showMessage(message);
        Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //如果房间处于登录状态
        if (socket != null) {
            //通知播放服务端房间处于登录状态
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(JsonUtil.toJsonString(exceptionExitRequest));
            pw.flush();
            //通知点播客户端房间异常退出
            ExceptionExitResponse exceptionExitResponse = new ExceptionExitResponse(roomPO, true);
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(exceptionExitResponse));
        } else {
            //通知播放服务端房间已注销
            ExceptionExitResponse exceptionExitResponse = new ExceptionExitResponse(roomPO, false);
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(exceptionExitResponse));
        }
    }
}
