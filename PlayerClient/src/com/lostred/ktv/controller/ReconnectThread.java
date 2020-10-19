package com.lostred.ktv.controller;

import com.lostred.ktv.net.Client;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.PlayerClientView;

import javax.swing.*;

/**
 * 进入房间后的重连线程
 */
public class ReconnectThread extends Thread {

    @Override
    public void run() {
        while (true) {
            //更改界面状态
            SwingUtilities.invokeLater(() -> PlayerClientView.getPlayerClientView().changeServerStatus(-1));
            //重连播放服务端
            LocalService.getLocalService().reconnect();
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
