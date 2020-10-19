package com.lostred.ktv.service.vod;

import com.lostred.ktv.dto.vod.ExceptionLogoffRequest;
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
 * 点播服务端请求异常注销房间业务
 */
public class ExceptionLogoffVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        ExceptionLogoffRequest exceptionLogoffRequest = (ExceptionLogoffRequest) JSONObject.toBean(jsonObject, ExceptionLogoffRequest.class);
        RoomPO roomPO = exceptionLogoffRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：异常注销";
        handler.showMessage(message);
        Set<Integer> keys = PCRecvThread.getRoomMap().keySet();
        //如果当前Map中已存在该房间
        if (keys.contains(roomPO.getRoomId())) {
            Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
            if (socket != null) {
                //通知播放客户端房间异常注销
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(JsonUtil.toJsonString(exceptionLogoffRequest));
                pw.flush();
            } else {
                //将该房间从Map中移除
                PCRecvThread.getRoomMap().remove(roomPO.getRoomId());
            }
        }
    }
}
