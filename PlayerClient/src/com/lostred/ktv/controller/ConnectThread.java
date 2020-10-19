package com.lostred.ktv.controller;

import com.lostred.ktv.net.Client;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.PlayerClientView;

import javax.swing.*;

/**
 * 未进入房间的连接线程
 */
public class ConnectThread extends Thread {
    @Override
    public void run() {
        while (true) {
            //更改界面状态
            SwingUtilities.invokeLater(() -> PlayerClientView.getPlayerClientView().changeServerStatus(-1));
            //连接播放服务端
            LocalService.getLocalService().connect();
            //如果连接上
            if (Client.getClient().isConnected()) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
