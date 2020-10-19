package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.NextResponse;
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
 * 播放服务端房间切歌业务
 */
public class NextPlayerService implements InPlayerService {
    @Override
    public void doService(PSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        NextResponse nextResponse = (NextResponse) JSONObject.toBean(jsonObject, NextResponse.class);
        RoomPO roomPO = nextResponse.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId()
                + "号房间：请求切歌";
        handler.showMessage(message);
        Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //如果房间处于登录状态
        if (socket != null) {
            //通知点播客户端更新播放列表
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(JsonUtil.toJsonString(nextResponse));
            pw.flush();
        }
    }
}
