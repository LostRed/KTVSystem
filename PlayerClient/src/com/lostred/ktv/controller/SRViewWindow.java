package com.lostred.ktv.controller;

import com.lostred.ktv.dto.ExitRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.PlayerClientView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 选择房间界面窗口监听
 */
public class SRViewWindow extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        //客户端连接时
        if (Client.getClient().isConnected()) {
            PlayerClientView pcv = PlayerClientView.getPlayerClientView();
            //向播放服务端发送退出请求
            ExitRequest exitRequest = new ExitRequest(pcv.getRoomPO());
            Client.getClient().sendMessage(JsonUtil.toJsonString(exitRequest));
        } else {
            System.exit(0);
        }
    }
}