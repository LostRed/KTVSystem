package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.EnterRequest;
import com.lostred.ktv.dto.player.EnterResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSServer;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * 播放客户端请求进入房间业务
 */
public class EnterPlayerService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        EnterRequest enterRequest = (EnterRequest) JSONObject.toBean(jsonObject, EnterRequest.class);
        RoomPO roomPO = enterRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：请求进入房间";
        handler.showMessage(message);
        Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //如果房间是空闲状态
        if (socket == null) {
            //处理业务，将房间改为占用状态，并记录该客户端socket对应的房间号
            PCRecvThread.getRoomMap().put(roomPO.getRoomId(), handler.getSocket());
            PCRecvThread.getClientSocketMap().put(handler.getSocket(), roomPO.getRoomId());
            //回应点播客户端可以进入
            EnterResponse enterResponse = new EnterResponse(roomPO);
            PrintWriter pw = handler.getPw();
            pw.println(JsonUtil.toJsonString(enterResponse));
            pw.flush();
            //如果点播服务端在线
            if (VSServer.getVSServer().isConnected()) {
                VSServer.getVSServer().sendMessage(JsonUtil.toJsonString(enterResponse));
            }
            //显示消息
            message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                    + ":" + handler.getSocket().getPort() + "：进入" + roomPO.getRoomId() + "号房间";
            handler.showMessage(message);
        } else {
            //回应点播客户端房间已被占用
            EnterResponse enterResponse = new EnterResponse(null);
            PrintWriter pw = handler.getPw();
            pw.println(JsonUtil.toJsonString(enterResponse));
            pw.flush();
        }
    }
}
