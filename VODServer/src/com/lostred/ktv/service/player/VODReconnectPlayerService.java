package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.EnterResponse;
import com.lostred.ktv.dto.player.VODReconnectResponse;
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
 * 点播客户端重连业务
 */
public class VODReconnectPlayerService implements InPlayerService {
    @Override
    public void doService(PSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        VODReconnectResponse vodReconnectResponse = (VODReconnectResponse) JSONObject.toBean(jsonObject, VODReconnectResponse.class);
        RoomPO roomPO = vodReconnectResponse.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：重新进入";
        handler.showMessage(message);
        Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //如果房间处于登录状态
        if (socket != null) {
            //通知房间已占用
            EnterResponse enterResponse = new EnterResponse(roomPO);
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(JsonUtil.toJsonString(enterResponse));
            pw.flush();
        }
    }
}
