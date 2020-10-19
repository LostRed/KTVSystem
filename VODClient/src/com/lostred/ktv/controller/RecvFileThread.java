package com.lostred.ktv.controller;

import com.lostred.ktv.util.IOStreamUtil;
import com.lostred.ktv.view.ProgressView;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 接收文件线程
 */
public class RecvFileThread extends Thread {
    /**
     * 文件大小
     */
    private final long fileSize;
    /**
     * 服务端ip
     */
    private final String ip;
    /**
     * 服务端端口
     */
    private final int port;

    /**
     * 构造接收文件线程
     *
     * @param fileSize 文件大小
     * @param ip       服务端ip
     * @param port     服务端端口
     */
    public RecvFileThread(long fileSize, String ip, int port) {
        this.fileSize = fileSize;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        //进度条初始化
        SwingUtilities.invokeLater(() -> {
            ProgressView.getProgressView().setTitle("正在下载更新文件");
            ProgressView.getProgressView().getProgressBar().setMaximum((int) fileSize);
            ProgressView.getProgressView().getProgressBar().setValue(0);
        });
        //接收文件
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            socket = new Socket(ip, port);
            is = socket.getInputStream();
            os = new FileOutputStream("VODClient/data/.patch");
            IOStreamUtil.Transmit(is, os);
            socket.shutdownOutput();
            //关闭进度条窗口
            SwingUtilities.invokeLater(() -> ProgressView.getProgressView().dispose());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOStreamUtil.release(is, os, socket);
        }
        //开启导入歌曲线程
        new ImportThread(fileSize).start();
        //显示进度条窗口
        SwingUtilities.invokeLater(() -> ProgressView.getProgressView().setVisible(true));
    }
}
