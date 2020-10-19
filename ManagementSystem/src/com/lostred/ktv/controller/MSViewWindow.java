package com.lostred.ktv.controller;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.ManagementSystemView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

/**
 * 后台管理系统主界面窗口监听
 */
public class MSViewWindow extends WindowAdapter {
    /**
     * 数据库是否已改动，true为已改动但未生成文件，false为未改动或已生成过文件
     */
    public static boolean MODIFIED;

    @Override
    public void windowClosing(WindowEvent e) {
        ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
        int choice = JOptionPane.showConfirmDialog(mv, "确定退出吗？", "提示", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.OK_OPTION) {
            if (MODIFIED) {
                LocalService localService = LocalService.getService();
                List<SongPO> list = localService.queryAllSong();
                try {
                    localService.createFile(list);
                    JOptionPane.showMessageDialog(mv, "检查到有新的改动，已自动生成文件！");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mv, "检查到有新的改动，自动生成文件失败！");
                }
            }
            System.exit(0);
        }
    }
}
