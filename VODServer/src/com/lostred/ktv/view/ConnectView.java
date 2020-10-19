package com.lostred.ktv.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 等待连接界面
 */
public class ConnectView extends JDialog {
    /**
     * 等待连接界面单例对象
     */
    private static ConnectView CONNECT_VIEW;

    /**
     * 构造等待连接界面
     */
    public ConnectView() {
        //初始化控件
        JPanel mainPanel = new JPanel();
        JLabel lbLoading = new JLabel("正在连接播放服务端...", JLabel.CENTER);
        //设置控件
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        mainPanel.setPreferredSize(new Dimension(250, 80));
        //添加控件
        mainPanel.add(lbLoading);
        add(mainPanel);
        //设置窗口
        setTitle("请稍等");
        setResizable(false);
        setModal(true);
        pack();
        setLocationRelativeTo(VODServerView.getVODServerView());
    }

    /**
     * 获取单例等待连接界面
     *
     * @return 等待连接界面
     */
    public static ConnectView getConnectView() {
        if (CONNECT_VIEW == null) {
            synchronized (ConnectView.class) {
                if (CONNECT_VIEW == null) {
                    CONNECT_VIEW = new ConnectView();
                }
            }
        }
        return CONNECT_VIEW;
    }
}
