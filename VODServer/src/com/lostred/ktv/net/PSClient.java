package com.lostred.ktv.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PSClient {
    /**
     * 单例客户端对象
     */
    private static PSClient PSCLIENT;
    /**
     * 格式化输出流
     */
    private PrintWriter pw;
    /**
     * 是否连接上服务端
     */
    private boolean connected;

    /**
     * 构造客户端
     */
    private PSClient() {
    }

    /**
     * 获取单例客户端
     *
     * @return 单例客户端
     */
    public static PSClient getPSClient() {
        if (PSCLIENT == null) {
            synchronized (PSClient.class) {
                if (PSCLIENT == null) {
                    PSCLIENT = new PSClient();
                }
            }
        }
        return PSCLIENT;
    }

    /**
     * 请求连接客户端
     *
     * @param host ip地址
     * @param port 端口
     * @throws IOException IO异常
     */
    public void connect(String host, String port) throws IOException {
        Socket socket = new Socket(host, Integer.parseInt(port));
        pw = new PrintWriter(socket.getOutputStream());
        connected = true;
        new PSRecvThread(socket).start();
    }

    /**
     * 向服务端发送消息
     *
     * @param sendMessage 需要发送给服务端的消息
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
