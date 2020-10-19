package com.lostred.ktv.net;

import com.lostred.ktv.dto.vod.ExceptionLogoffRequest;
import com.lostred.ktv.service.vod.VODServiceController;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.PlayerServerView;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Set;

/**
 * 点播服务端通讯主接收线程
 */
public class VSRecvThread extends Thread {
    /**
     * 与客户端建立的套接字对象
     */
    private final Socket socket;
    /**
     * 字符输出流
     */
    private PrintWriter pw;
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
     * @param socket 与客户端建立的套接字对象
     */
    public VSRecvThread(Socket socket) {
        this.socket = socket;
        try {
            this.pw = new PrintWriter(socket.getOutputStream());
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
                System.out.println(jsonString);
                //解析消息
                jsonObject = JsonUtil.toJsonObject(jsonString);
                String type = jsonObject.getString("type");
                //执行业务
                VODServiceController.getService(type).doService(this);
            } catch (IOException e) {
                e.printStackTrace();
                VSServer.getVSServer().setConnected(false);
                //移除所有空闲房间
                Set<Integer> keys = PCRecvThread.getRoomMap().keySet();
                for (Integer roomId : keys) {
                    Socket socket = PCRecvThread.getRoomMap().get(roomId);
                    if (socket == null) {
                        PCRecvThread.getRoomMap().remove(roomId);
                    }
                }
                //更改界面
                SwingUtilities.invokeLater(() -> PlayerServerView.getPlayerServerView().changeServerStatus(0));
                //给所有客户端发送异常消息
                ExceptionLogoffRequest exceptionLogoffRequest = new ExceptionLogoffRequest();
                sendMessageToAllClient(JsonUtil.toJsonString(exceptionLogoffRequest));
                //显示消息
                VSServer.showLoginMessage(socket, false);
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
        List<PCRecvThread> list = PCServer.getPcRecvThreadList();
        for (PCRecvThread pcRecvThread : list) {
            PrintWriter pw = pcRecvThread.getPw();
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
        PlayerServerView psv = PlayerServerView.getPlayerServerView();
        System.out.println(message);
        psv.getTaVODServer().append(message);
        psv.getTaVODServer().append("\r\n");
        psv.getTaVODServer().setCaretPosition(psv.getTaVODServer().getText().length());
    }

    //get和set方法
    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
