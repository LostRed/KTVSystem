package com.lostred.ktv.controller;

import com.lostred.ktv.view.VODServerView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 点播服务端按钮窗口监听
 */
public class VODSWindow extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        VODServerView vodSv = VODServerView.getVODServerView();
        //点播服务端未开启时
        if (!vodSv.getBtnStart().isEnabled()) {
            JOptionPane.showMessageDialog(VODServerView.getVODServerView(), "异常退出！", "警告", JOptionPane.ERROR_MESSAGE);
        }
    }
}
