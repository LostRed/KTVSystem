package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.ExitRequest;
import com.lostred.ktv.dto.player.ExitResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSServer;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 播放客户端请求退出房间业务
 */
public class ExitPlayerService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        ExitRequest exitRequest = (ExitRequest) JSONObject.toBean(jsonObject, ExitRequest.class);
        RoomPO roomPO = exitRequest.getRoomPO();
        //如果该房间不为空
        if (roomPO != null) {
            //如果点播服务端处于连接状态
            if (VSServer.getVSServer().isConnected()) {
                //把房间改为空闲
                Socket socket = PCRecvThread.getRoomMap().put(roomPO.getRoomId(), null);
                PCRecvThread.getClientSocketMap().remove(socket);
                //如果点播服务器在线
                if (VSServer.getVSServer().isConnected()) {
                    //发送消息给点播服务端
                    VSServer.getVSServer().sendMessage(JsonUtil.toJsonString(exitRequest));
                }
            } else {
                //将该房间从Map集合中移除
                Socket socket = PCRecvThread.getRoomMap().remove(roomPO.getRoomId());
                PCRecvThread.getClientSocketMap().remove(socket);
            }
        }
        //回应播放客户端
        ExitResponse exitResponse = new ExitResponse(roomPO);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(exitResponse));
        pw.flush();
        //关闭socket
        Socket socket = handler.getSocket();
        PCRecvThread.getClientSocketMap().remove(socket);
        socket.close();
        //如果房间存在
        if (roomPO != null) {
            //显示消息
            String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：退出房间";
            handler.showMessage(message);
        }
        throw new IOException("正常退出");
    }
}
