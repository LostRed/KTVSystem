package com.lostred.ktv.net;

import com.lostred.ktv.util.TimeUtil;
import com.lostred.ktv.view.PlayerServerView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 播放客户端服务端
 */
public class PCServer implements Runnable {
    /**
     * 所有播放客户端通讯线程集合
     */
    private static final List<PCRecvThread> PC_RECV_THREAD_LIST = new ArrayList<>();

    /**
     * 显示消息
     *
     * @param socket 客户端连接的socket
     * @param log    true为连接，false为断开连接
     */
    public static void showLoginMessage(Socket socket, boolean log) {
        PlayerServerView psv = PlayerServerView.getPlayerServerView();
        String message;
        if (log) {
            message = TimeUtil.getNowTime() + "\tIP" + socket.getInetAddress() + ":" + socket.getPort() + "：客户端连接成功";
        } else {
            message = TimeUtil.getNowTime() + "\tIP" + socket.getInetAddress() + ":" + socket.getPort() + "：客户端断开连接";
        }
        System.out.println(message);
        psv.getTaPlayerClient().append(message);
        psv.getTaPlayerClient().append("\r\n");
        psv.getTaPlayerClient().setCaretPosition(psv.getTaPlayerClient().getText().length());
    }

    /**
     * 获取所有播放客户端的通讯线程集合
     *
     * @return 所有播放客户端的通讯线程集合
     */
    public static List<PCRecvThread> getPcRecvThreadList() {
        return PC_RECV_THREAD_LIST;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(10091);
            while (true) {
                System.out.println("等待客户端连接...");
                Socket socket = serverSocket.accept();
                showLoginMessage(socket, true);
                PCRecvThread pcRecvThread = new PCRecvThread(socket);
                PC_RECV_THREAD_LIST.add(pcRecvThread);
                pcRecvThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
