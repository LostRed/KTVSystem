package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.EnterResponse;
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
 * 播放服务端请求进入房间业务
 */
public class EnterPlayerService implements InPlayerService {
    @Override
    public void doService(PSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        EnterResponse enterResponse = (EnterResponse) JSONObject.toBean(jsonObject, EnterResponse.class);
        RoomPO roomPO = enterResponse.getRoomPO();
        Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //如果房间处于登录状态
        if (socket != null) {
            //通知点播客户端房间已被占用
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(JsonUtil.toJsonString(enterResponse));
            pw.flush();
            //显示消息
            String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：进入成功";
            handler.showMessage(message);
        }
    }
}
