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
public class DownloadFileThread extends Thread {
    /**
     * 下载是否开始，false为未开始下载，true为开始下载
     */
    public static volatile boolean START;
    /**
     * 下载是否完成，false为未完成，true为完成
     */
    public static volatile boolean DONE;
    /**
     * 文件的md5码
     */
    private final String md5;
    /**
     * 文件大小
     */
    private final long fileSize;
    /**
     * 主机ip
     */
    private final String ip;
    /**
     * 主机端口
     */
    private final int port;

    /**
     * 构造文件接收线程
     *
     * @param md5      文件的md5码
     * @param fileSize 文件大小
     * @param ip       主机ip
     * @param port     主机端口
     */
    public DownloadFileThread(String md5, long fileSize, String ip, int port) {
        this.md5 = md5;
        this.fileSize = fileSize;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        //接收文件
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            socket = new Socket(ip, port);
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
            DONE = true;
        }
    }
}
