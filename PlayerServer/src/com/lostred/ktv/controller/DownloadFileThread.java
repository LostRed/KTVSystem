package com.lostred.ktv.controller;

import com.lostred.ktv.util.IOStreamUtil;
import com.lostred.ktv.util.TimeUtil;
import com.lostred.ktv.view.PlayerServerView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 发送文件线程
 */
public class DownloadFileThread extends Thread {
    /**
     * 服务端socket
     */
    private final ServerSocket serverSocket;
    /**
     * 发送文件的路径
     */
    private final String filePath;


    /**
     * 构造发送文件线程
     *
     * @param serverSocket 服务端socket
     * @param filePath     文件路径
     */
    public DownloadFileThread(ServerSocket serverSocket, String filePath) {
        this.serverSocket = serverSocket;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            socket = serverSocket.accept();
            is = new FileInputStream(filePath);
            os = socket.getOutputStream();
            //传输数据
            String message = TimeUtil.getNowTime() + "\tIP" + socket.getInetAddress() + ":" + socket.getPort() + "：开始下载文件";
            showMessage(message);
            System.out.println(message);
            IOStreamUtil.Transmit(is, os);
            message = TimeUtil.getNowTime() + "\tIP" + socket.getInetAddress() + ":" + socket.getPort() + "：文件下载完成";
            showMessage(message);
            System.out.println(message);
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOStreamUtil.release(is, os, socket);
        }
    }

    /**
     * 显示消息
     *
     * @param message 消息
     */
    private void showMessage(String message) {
        PlayerServerView psv = PlayerServerView.getPlayerServerView();
        System.out.println(message);
        psv.getTaPlayerClient().append(message);
        psv.getTaPlayerClient().append("\r\n");
        psv.getTaPlayerClient().setCaretPosition(psv.getTaPlayerClient().getText().length());
    }
}
