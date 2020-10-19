package com.lostred.ktv.net;

import com.lostred.ktv.util.TimeUtil;
import com.lostred.ktv.view.VODServerView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class VCServer implements Runnable {
    /**
     * 客户端通信线程集合
     */
    private static final List<VCRecvThread> VC_RECV_THREAD_LIST = new ArrayList<>();

    /**
     * 获取客户端通信线程集合
     *
     * @return 客户端通信线程集合
     */
    public static List<VCRecvThread> getVcRecvThreadList() {
        return VC_RECV_THREAD_LIST;
    }

    /**
     * 显示消息
     *
     * @param socket 客户端连接的socket
     * @param log    true为连接，false为断开连接
     */
    public static void showLoginMessage(Socket socket, boolean log) {
        VODServerView vodSv = VODServerView.getVODServerView();
        String message;
        if (log) {
            message = TimeUtil.getNowTime() + "\tIP" + socket.getInetAddress() + ":" + socket.getPort() + "：客户端连接成功";
        } else {
            message = TimeUtil.getNowTime() + "\tIP" + socket.getInetAddress() + ":" + socket.getPort() + "：客户端断开连接";
        }
        System.out.println(message);
        vodSv.getTaVODClient().append(message);
        vodSv.getTaVODClient().append("\r\n");
        vodSv.getTaVODClient().setCaretPosition(vodSv.getTaVODClient().getText().length());
    }

    /**
     * 启动服务端
     */
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(10080);
            while (true) {
                System.out.println("等待客户端连接...");
                Socket socket = serverSocket.accept();
                showLoginMessage(socket, true);
                VCRecvThread vcRecvThread = new VCRecvThread(socket);
                VC_RECV_THREAD_LIST.add(vcRecvThread);
                vcRecvThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
