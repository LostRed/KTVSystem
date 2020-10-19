package com.lostred.ktv.controller;

import com.lostred.ktv.dto.LogoffRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.VODClientView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 登录界面窗口监听
 */
public class LoginViewWindow extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        //客户端连接时
        if (Client.getClient().isConnected()) {
            VODClientView vodCv = VODClientView.getVODClientView();
            //向点播服务端发送注销请求
            LogoffRequest logoffRequest = new LogoffRequest(vodCv.getRoomPO());
            Client.getClient().sendMessage(JsonUtil.toJsonString(logoffRequest));
        } else {
            System.exit(0);
        }
    }
}
