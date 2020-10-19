package com.lostred.ktv.controller;

import com.lostred.ktv.view.PlayerClientView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 播放客户端主界面窗口监听
 */
public class PCViewWindow extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        JOptionPane.showMessageDialog(PlayerClientView.getPlayerClientView(), "异常退出！", "警告", JOptionPane.ERROR_MESSAGE);
    }
}
