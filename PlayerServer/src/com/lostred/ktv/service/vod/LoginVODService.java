package com.lostred.ktv.service.vod;

import com.lostred.ktv.dto.player.EnterRequest;
import com.lostred.ktv.dto.vod.LoginResponse;
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
 * 点播服务端登录房间业务
 */
public class LoginVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        LoginResponse loginResponse = (LoginResponse) JSONObject.toBean(jsonObject, LoginResponse.class);
        RoomPO roomPO = loginResponse.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：登录成功";
        handler.showMessage(message);
        Set<Integer> keys = PCRecvThread.getRoomMap().keySet();
        //如果当前Map中未存在该房间
        if (!keys.contains(roomPO.getRoomId())) {
            //将房间号添加到Map集合
            PCRecvThread.getRoomMap().put(roomPO.getRoomId(), null);
        } else {
            Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
            //如果该房间被占用
            if (socket != null) {
                //回应点播服务端该房间已占用
                EnterRequest enterRequest = new EnterRequest(roomPO);
                PrintWriter pw = handler.getPw();
                pw.println(JsonUtil.toJsonString(enterRequest));
                pw.flush();
                //通知播放客户端该房间已登录
                pw = new PrintWriter(socket.getOutputStream());
                pw.println(JsonUtil.toJsonString(loginResponse));
                pw.flush();
            }
        }
    }
}
