package com.lostred.ktv.controller;

import com.lostred.ktv.net.PCServer;
import com.lostred.ktv.net.VSServer;
import com.lostred.ktv.view.PlayerServerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 播放服务端按钮动作监听
 */
public class PSAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        PlayerServerView psv = PlayerServerView.getPlayerServerView();
        String command = e.getActionCommand();
        switch (command) {
            //开启服务端
            case "start":
                //开启点播服务端通讯线程
                new Thread(VSServer.getVSServer()).start();
                //开启播放客户端通讯线程
                new Thread(new PCServer()).start();
                psv.getBtnStart().setEnabled(false);
                break;
            //关闭服务端
            case "close":
                int choice = JOptionPane.showConfirmDialog(psv, "是否关闭服务端？", "提示", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
        }
    }
}
