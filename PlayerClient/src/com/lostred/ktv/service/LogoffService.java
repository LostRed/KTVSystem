package com.lostred.ktv.service;

import com.lostred.ktv.dto.ExitRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.PlayerClientView;

/**
 * 播放服务端请求注销房间业务
 */
public class LogoffService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //写入配置文件
        ConfigUtil.writeProperties("songName", "");
        ConfigUtil.writeProperties("singerName", "");
        ConfigUtil.writeProperties("md5", "");
        ConfigUtil.writeProperties("currentTime", "");
        //发送退出房间请求
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        ExitRequest exitRequest = new ExitRequest(pcv.getRoomPO());
        Client.getClient().sendMessage(JsonUtil.toJsonString(exitRequest));
    }
}
