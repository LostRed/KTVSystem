package com.lostred.ktv.service.vod;

import com.lostred.ktv.dto.vod.LogoffRequest;
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
 * 点播服务端注销房间业务
 */
public class LogoffVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        LogoffRequest logoffRequest = (LogoffRequest) JSONObject.toBean(jsonObject, LogoffRequest.class);
        RoomPO roomPO = logoffRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：注销成功";
        handler.showMessage(message);
        Set<Integer> keys = PCRecvThread.getRoomMap().keySet();
        //如果当前Map中已存在该房间
        if (keys.contains(roomPO.getRoomId())) {
            Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
            //如果该房间被占用
            if (socket != null) {
                //通知播放客户端
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(JsonUtil.toJsonString(logoffRequest));
                pw.flush();
            } else {
                //将该房间从Map中移除
                PCRecvThread.getRoomMap().remove(roomPO.getRoomId());
            }
        }
    }
}
