package com.lostred.ktv.controller;

import com.lostred.ktv.view.VODClientView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 点播端主界面窗口监听
 */
public class VODCViewWindow extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        JOptionPane.showMessageDialog(VODClientView.getVODClientView(), "异常退出！", "警告", JOptionPane.ERROR_MESSAGE);
    }
}

