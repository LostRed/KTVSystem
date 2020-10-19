package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.EndRequest;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSServer;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

/**
 * 播放客户端请求播放结束业务
 */
public class EndPlayerService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        EndRequest endRequest = (EndRequest) JSONObject.toBean(jsonObject, EndRequest.class);
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：播放结束";
        handler.showMessage(message);
        //如果点播服务端在线
        if (VSServer.getVSServer().isConnected()) {
            VSServer.getVSServer().sendMessage(JsonUtil.toJsonString(endRequest));
        }
    }
}
