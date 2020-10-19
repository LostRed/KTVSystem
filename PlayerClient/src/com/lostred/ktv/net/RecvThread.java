package com.lostred.ktv.net;

import com.lostred.ktv.controller.ReconnectThread;
import com.lostred.ktv.service.ServiceController;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.PlayerClientView;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 播放客户端通讯主接收线程
 */
public class RecvThread extends Thread {
    /**
     * 与服务端建立的套接字对象
     */
    private final Socket socket;
    /**
     * 字符输入流
     */
    private BufferedReader br;
    /**
     * 解析的JSON对象
     */
    private JSONObject jsonObject;

    /**
     * 构造通讯主接收线程
     *
     * @param socket 套接字对象
     */
    public RecvThread(Socket socket) {
        this.socket = socket;
        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                //读取消息
                String jsonString = br.readLine();
                System.out.println("IP" + socket.getInetAddress() + ":" + socket.getPort() + " - " + jsonString);
                //解析消息
                jsonObject = JsonUtil.toJsonObject(jsonString);
                String type = jsonObject.getString("type");
                //执行业务
                ServiceController.getService(type).doService(this);
            } catch (IOException e) {
                e.printStackTrace();
                //更改界面
                SwingUtilities.invokeLater(() -> {
                    PlayerClientView.getPlayerClientView().changeServerStatus(0);
                    PlayerClientView.getPlayerClientView().changeVODStatus(0);
                });
                Client.getClient().setConnected(false);
                //启动重连线程
                new ReconnectThread().start();
                JOptionPane.showMessageDialog(PlayerClientView.getPlayerClientView(),
                        "与IP" + socket.getInetAddress() + ":" + socket.getPort() + "服务端断开连接！");
                break;
            }
        }
    }

    //get和set方法
    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
