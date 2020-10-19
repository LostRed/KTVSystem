package com.lostred.ktv.controller;

import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.VODServerView;

import javax.swing.*;

/**
 * 重连线程
 */
public class ReconnectThread extends Thread {
    @Override
    public void run() {
        while (true) {
            //更改界面
            SwingUtilities.invokeLater(() -> {
                VODServerView vodSv = VODServerView.getVODServerView();
                vodSv.changeServerStatus(-1);
            });
            //重连播放服务端
            LocalService.getLocalService().reconnect();
            //如果连接上
            if (PSClient.getPSClient().isConnected()) {
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
