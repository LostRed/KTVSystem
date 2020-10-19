package com.lostred.ktv.controller;

import com.lostred.ktv.view.PlayerServerView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 播放服务端主界面窗口监听
 */
public class PSWindow extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        PlayerServerView psv = PlayerServerView.getPlayerServerView();
        //播放服务端未开启时
        if (!psv.getBtnStart().isEnabled()) {
            JOptionPane.showMessageDialog(psv, "异常退出！", "警告", JOptionPane.ERROR_MESSAGE);
        }
    }
}
