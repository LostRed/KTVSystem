package com.lostred.ktv.net;

import com.lostred.ktv.util.TimeUtil;
import com.lostred.ktv.view.PlayerServerView;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 点播服务端服务端
 */
public class VSServer implements Runnable {
    /**
     * 点播服务端服务端单例对象
     */
    private static VSServer VSSERVER;
    /**
     * 点播服务端是否连接
     */
    private boolean connected;
    /**
     * 字符输出流
     */
    private PrintWriter pw;

    /**
     * 构造点播服务端服务端
     */
    private VSServer() {
    }

    /**
     * 获取单例服务端
     *
     * @return 单例服务端
     */
    public static VSServer getVSServer() {
        if (VSSERVER == null) {
            synchronized (VSServer.class) {
                if (VSSERVER == null) {
                    VSSERVER = new VSServer();
                }
            }
        }
        return VSSERVER;
    }

    /**
     * 显示消息
     *
     * @param socket 点播服务端连接的socket
     * @param log    true为连接，false为断开连接
     */
    public static void showLoginMessage(Socket socket, boolean log) {
        PlayerServerView psv = PlayerServerView.getPlayerServerView();
        String message;
        if (log) {
            message = TimeUtil.getNowTime() + "\tIP" + socket.getInetAddress() + ":" + socket.getPort() + "：点播服务端连接成功";
        } else {
            message = TimeUtil.getNowTime() + "\tIP" + socket.getInetAddress() + ":" + socket.getPort() + "：点播服务端断开连接";
        }
        System.out.println(message);
        psv.getTaVODServer().append(message);
        psv.getTaVODServer().append("\r\n");
        psv.getTaVODServer().setCaretPosition(psv.getTaVODServer().getText().length());
    }

    /**
     * 启动服务端
     */
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(10090);
            while (true) {
                System.out.println("等待点播服务端连接...");
                Socket socket = serverSocket.accept();
                connected = true;
                pw = new PrintWriter(socket.getOutputStream());
                //更改界面
                SwingUtilities.invokeLater(() -> PlayerServerView.getPlayerServerView().changeServerStatus(1));
                showLoginMessage(socket, true);
                new VSRecvThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向点播服务端发送消息
     *
     * @param sendMessage 需要发送给点播服务端的消息
     */
    public void sendMessage(String sendMessage) {
        pw.println(sendMessage);
        pw.flush();
    }

    //get和set方法
    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
