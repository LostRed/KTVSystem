package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.EndRequest;
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
 * 播放服务端请求播放结束业务
 */
public class EndPlayerService implements InPlayerService {
    @Override
    public void doService(PSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        EndRequest endRequest = (EndRequest) JSONObject.toBean(jsonObject, EndRequest.class);
        RoomPO roomPO = endRequest.getRoomPO();
        Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：播放结束";
        handler.showMessage(message);
        //如果房间处于登录状态
        if (socket != null) {
            //通知点播客户端播放结束
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(JsonUtil.toJsonString(endRequest));
            pw.flush();
        }
    }
}
