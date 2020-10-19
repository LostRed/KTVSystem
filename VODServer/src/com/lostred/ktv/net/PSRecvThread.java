package com.lostred.ktv.net;

import com.lostred.ktv.controller.ReconnectThread;
import com.lostred.ktv.dto.player.ExceptionExitResponse;
import com.lostred.ktv.service.player.PlayerServiceController;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.VODServerView;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * 点播服务端通讯主接收线程(面向点播客户端)
 */
public class PSRecvThread extends Thread {
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
    public PSRecvThread(Socket socket) {
        this.socket = socket;
        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环解析服务端发来的消息并执行业务
     */
    @Override
    public void run() {
        while (true) {
            try {
                //读取消息
                String jsonString = br.readLine();
                //解析消息
                jsonObject = JsonUtil.toJsonObject(jsonString);
                String type = jsonObject.getString("type");
                //执行业务
                PlayerServiceController.getService(type).doService(this);
            } catch (IOException e) {
                e.printStackTrace();
                PSClient.getPSClient().setConnected(false);
                //更改界面
                SwingUtilities.invokeLater(() -> VODServerView.getVODServerView().changeServerStatus(0));
                //启动重连线程
                new ReconnectThread().start();
                //给所有客户端发送异常消息
                ExceptionExitResponse exceptionExitResponse = new ExceptionExitResponse();
                sendMessageToAllClient(JsonUtil.toJsonString(exceptionExitResponse));
                JOptionPane.showMessageDialog(VODServerView.getVODServerView(),
                        "与IP" + socket.getInetAddress() + ":" + socket.getPort() + "服务端断开连接！");
                break;
            }
        }
    }

    /**
     * 向所有客户端发送json消息
     *
     * @param jsonString json消息内容
     */
    public void sendMessageToAllClient(String jsonString) {
        List<VCRecvThread> list = VCServer.getVcRecvThreadList();
        for (VCRecvThread vcRecvThread : list) {
            PrintWriter pw = vcRecvThread.getPw();
            pw.println(jsonString);
            pw.flush();
        }
    }

    /**
     * 显示消息
     *
     * @param message 消息
     */
    public void showMessage(String message) {
        VODServerView vodSv = VODServerView.getVODServerView();
        System.out.println(message);
        vodSv.getTaPlayerServer().append(message);
        vodSv.getTaPlayerServer().append("\r\n");
        vodSv.getTaPlayerServer().setCaretPosition(vodSv.getTaPlayerServer().getText().length());
    }

    //get和set方法
    public Socket getSocket() {
        return socket;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}