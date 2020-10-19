package com.lostred.ktv.run;

import com.lostred.ktv.controller.ConnectThread;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.view.ConnectView;
import com.lostred.ktv.view.PlayerClientView;
import com.lostred.ktv.view.SelectRoomView;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 主程序入口
 */
public class PCStart {
    public static void main(String[] args) {
        initGlobalFontSetting(new Font("微软雅黑", Font.PLAIN, 13));
        //使用本地操作系统界面风格
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        SelectRoomView sv = SelectRoomView.getSelectRoomView();
        //启动线程
        new Thread(() -> {
            String host = ConfigUtil.getIp();
            String port = ConfigUtil.getPort();
            try {
                Client.getClient().connect(host, port);
                ConnectView.getConnectView().dispose();
                SwingUtilities.invokeLater(() -> {
                    PlayerClientView pcv = PlayerClientView.getPlayerClientView();
                    pcv.changeServerStatus(1);
                    pcv.changeVODStatus(1);
                });
                //请求房间列表
                LocalService.getLocalService().requestRoomList();
            } catch (IOException e) {
                ConnectView.getConnectView().dispose();
                //开启连接线程
                new ConnectThread().start();
                JOptionPane.showMessageDialog(sv, "无法连接播放服务端！");
            } finally {
                sv.setVisible(true);
                sv.setState(JFrame.NORMAL);
            }
        }).start();
        //使用模态框阻塞
        ConnectView.getConnectView().setVisible(true);
    }

    /**
     * 设置全局字体
     *
     * @param font 字体
     */
    public static void initGlobalFontSetting(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
