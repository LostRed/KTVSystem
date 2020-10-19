package com.lostred.ktv.view;

import com.lostred.ktv.controller.PSAction;
import com.lostred.ktv.controller.PSWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 播放主界面
 */
public class PlayerServerView extends JFrame {
    /**
     * 播放主界面单例对象
     */
    private static PlayerServerView PLAY_SERVER_VIEW;
    /**
     * 开启服务端按钮
     */
    private final DefaultButton btnStart = new DefaultButton("开启服务端");
    /**
     * 点播服务端状态标签
     */
    private final JLabel lbServer = new JLabel("点播服务端：未连接", JLabel.RIGHT);
    /**
     * 点播服务端消息文本域
     */
    private final JTextArea taVODServer = new JTextArea();
    /**
     * 播放客户端消息文本域
     */
    private final JTextArea taPlayerClient = new JTextArea();

    /**
     * 构造播放主界面
     */
    private PlayerServerView() {
        //初始化控件
        JPanel topPanel = new JPanel();
        JPanel playerServerPanel = new JPanel();
        JPanel VODClientPanel = new JPanel();
        JScrollPane playerServerScrollPane = new JScrollPane();
        JScrollPane VODClientScrollPane = new JScrollPane();
        JLabel lbPlayerServer = new JLabel("点播服务端消息：");
        JLabel lbVODClient = new JLabel("播放客户端消息：");
        DefaultButton btnClose = new DefaultButton("关闭服务端");
        //设置控件
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerServerPanel.setLayout(new BorderLayout(0, 10));
        VODClientPanel.setLayout(new BorderLayout(0, 10));
        topPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        playerServerPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
        VODClientPanel.setBorder(new EmptyBorder(20, 50, 50, 50));
        playerServerScrollPane.setPreferredSize(new Dimension(600, 200));
        VODClientScrollPane.setPreferredSize(new Dimension(600, 200));
        lbServer.setPreferredSize(new Dimension(380, 28));
        lbServer.setForeground(new Color(150, 50, 50));
        taVODServer.setEditable(false);
        taPlayerClient.setEditable(false);
        playerServerScrollPane.setViewportView(taVODServer);
        VODClientScrollPane.setViewportView(taPlayerClient);
        //添加控件
        topPanel.add(btnStart);
        topPanel.add(btnClose);
        topPanel.add(lbServer);
        playerServerPanel.add(lbPlayerServer, BorderLayout.NORTH);
        playerServerPanel.add(playerServerScrollPane);
        VODClientPanel.add(lbVODClient, BorderLayout.NORTH);
        VODClientPanel.add(VODClientScrollPane);
        add(topPanel, BorderLayout.NORTH);
        add(playerServerPanel);
        add(VODClientPanel, BorderLayout.SOUTH);
        //添加监听
        PSWindow psWindow = new PSWindow();
        PSAction psAction = new PSAction();
        addWindowListener(psWindow);
        btnStart.addActionListener(psAction);
        btnClose.addActionListener(psAction);
        btnStart.setActionCommand("start");
        btnClose.setActionCommand("close");
        //设置窗口
        setTitle("KTV播放服务端");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 获取单例播放主界面
     *
     * @return 播放主界面单例对象
     */
    public static PlayerServerView getPlayerServerView() {
        if (PLAY_SERVER_VIEW == null) {
            synchronized (PlayerServerView.class) {
                if (PLAY_SERVER_VIEW == null) {
                    PLAY_SERVER_VIEW = new PlayerServerView();
                }
            }
        }
        return PLAY_SERVER_VIEW;
    }

    /**
     * 改变服务器状态
     *
     * @param status 服务端连接状态，1为已连接，0为未连接
     */
    public void changeServerStatus(int status) {
        if (status == 1) {
            lbServer.setText("点播服务端：已连接");
            lbServer.setForeground(new Color(50, 150, 50));
        } else if (status == 0) {
            lbServer.setText("点播服务端：未连接");
            lbServer.setForeground(new Color(150, 50, 50));
        }
    }

    //get和set方法
    public DefaultButton getBtnStart() {
        return btnStart;
    }

    public JTextArea getTaVODServer() {
        return taVODServer;
    }

    public JTextArea getTaPlayerClient() {
        return taPlayerClient;
    }
}
