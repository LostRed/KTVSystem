package com.lostred.ktv.service.vod;

import com.lostred.ktv.dto.vod.LoginResponse;
import com.lostred.ktv.dto.vod.VODReconnectRequest;
import com.lostred.ktv.dto.vod.VODReconnectResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

/**
 * 播放服务端回应点播客户端重连业务
 */
public class VODReconnectVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        VODReconnectRequest vodReconnectRequest = (VODReconnectRequest) JSONObject.toBean(jsonObject, VODReconnectRequest.class);
        RoomPO roomPO = vodReconnectRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：重新登录";
        handler.showMessage(message);
        Set<Integer> keys = PCRecvThread.getRoomMap().keySet();
        //如果当前Map中已存在该房间
        if (keys.contains(roomPO.getRoomId())) {
            Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
            //如果该房间被占用
            if (socket != null) {
                //通知播放端该房间已重新登录
                LoginResponse loginResponse = new LoginResponse(JsonUtil.SUCCESS, roomPO);
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(JsonUtil.toJsonString(loginResponse));
                pw.flush();
                //回应点播服务端
                VODReconnectResponse VODReconnectResponse = new VODReconnectResponse(roomPO);
                pw = handler.getPw();
                pw.println(JsonUtil.toJsonString(VODReconnectResponse));
                pw.flush();
            }
        } else {
            //将房间号添加到Map集合
            PCRecvThread.getRoomMap().put(roomPO.getRoomId(), null);
        }
    }
}
