package com.lostred.ktv.util;

import com.lostred.ktv.view.ProgressView;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * 网络传输IO流工具
 */
public class IOStreamUtil {
    /**
     * 传输数据
     *
     * @param is 字节输入流
     * @param os 字节输出流
     * @throws IOException IO异常
     */
    public static void Transmit(InputStream is, OutputStream os) throws IOException {
        int currentSize = 0;
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        byte[] bytes = new byte[1024 * 1024];
        while (true) {
            int rs = bis.read(bytes);
            if (rs < 0) {
                break;
            }
            bos.write(bytes, 0, rs);
            bos.flush();
            currentSize += rs;
            int finalCurrentSize = currentSize;
            SwingUtilities.invokeLater(() -> ProgressView.getProgressView().getProgressBar().setValue(finalCurrentSize));
        }
    }

    /**
     * 释放资源
     *
     * @param is     字节输入流
     * @param os     字节输出流
     * @param socket 套接字
     */
    public static void release(InputStream is, OutputStream os, Socket socket) {
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
