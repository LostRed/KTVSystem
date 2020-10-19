package com.lostred.ktv.controller;

import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.IOStreamUtil;
import com.lostred.ktv.view.ProgressView;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收文件线程
 */
public class RecvFileThread extends Thread {
    /**
     * 服务端socket
     */
    private final ServerSocket serverSocket;
    /**
     * 文件的md5码
     */
    private final String md5;
    /**
     * 文件大小
     */
    private final long fileSize;

    /**
     * 构造文件接收线程
     *
     * @param serverSocket 服务端socket
     * @param md5          文件的md5码
     * @param fileSize     文件大小
     */
    public RecvFileThread(ServerSocket serverSocket, String md5, long fileSize) {
        this.serverSocket = serverSocket;
        this.md5 = md5;
        this.fileSize = fileSize;
    }

    @Override
    public void run() {
        //初始化进度条
        SwingUtilities.invokeLater(() -> {
            ProgressView.getProgressView().setTitle("正在下载音乐文件");
            ProgressView.getProgressView().getProgressBar().setMaximum((int) fileSize);
            ProgressView.getProgressView().getProgressBar().setValue(0);
        });
        //接收文件
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            socket = serverSocket.accept();
            is = socket.getInputStream();
            os = new FileOutputStream("PlayerClient/music/" + md5);
            IOStreamUtil.Transmit(is, os);
            socket.shutdownOutput();
            //关闭进度条窗口
            SwingUtilities.invokeLater(() -> ProgressView.getProgressView().dispose());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOStreamUtil.release(is, os, socket);
        }
        //播放歌曲
        File file = new File("PlayerClient/music/" + md5);
        LocalService.getLocalService().play(file);
    }
}
