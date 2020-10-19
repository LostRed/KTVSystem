package com.lostred.ktv.controller;

import com.lostred.ktv.net.VCServer;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.VODServerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 点播服务端按钮动作监听
 */
public class VODSAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        VODServerView vodSv = VODServerView.getVODServerView();
        String command = e.getActionCommand();
        switch (command) {
            case "start":
                new Thread(new VCServer()).start();
                LocalService.getLocalService().connect();
                vodSv.getBtnStart().setEnabled(false);
                break;
            case "close":
                int choice = JOptionPane.showConfirmDialog(vodSv, "是否关闭服务端？", "提示", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
        }
    }
}
