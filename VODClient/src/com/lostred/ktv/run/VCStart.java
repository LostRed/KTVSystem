package com.lostred.ktv.run;

import com.lostred.ktv.controller.ConnectThread;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.view.ConnectView;
import com.lostred.ktv.view.LoginView;
import com.lostred.ktv.view.VODClientView;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 主程序入口
 */
public class VCStart {
    public static void main(String[] args) {
        initGlobalFontSetting(new Font("微软雅黑", Font.PLAIN, 13));
        //使用本地操作系统界面风格
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        //启动线程
        new Thread(() -> {
            String host = ConfigUtil.getIp();
            String port = ConfigUtil.getPort();
            try {
                Client.getClient().connect(host, port);
                SwingUtilities.invokeLater(() -> {
                    ConnectView.getConnectView().dispose();
                    VODClientView.getVODClientView().changeServerStatus(1);
                });
                //请求更新数据
                LocalService.getLocalService().requestUpdate();
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> ConnectView.getConnectView().dispose());
                //启动连接线程
                new ConnectThread().start();
                JOptionPane.showMessageDialog(null, "无法连接点播服务端！");
            } finally {
                //显示登录界面
                SwingUtilities.invokeLater(() -> LoginView.getLoginView().setVisible(true));
            }
        }).start();
        //使用模态框阻塞
        SwingUtilities.invokeLater(() -> ConnectView.getConnectView().setVisible(true));
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
