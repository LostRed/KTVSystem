package com.lostred.ktv.net;

import com.lostred.ktv.dto.player.ExceptionLogoffRequest;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.service.vod.VODServiceController;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import com.lostred.ktv.view.VODServerView;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 点播服务端通讯主接收线程(面向播放服务端)
 */
public class VCRecvThread extends Thread {
    /**
     * 房间号Map集合
     */
    private static final Map<Integer, Socket> ROOM_MAP = new HashMap<>();
    /**
     * 客户端连接后对应的套接字集合
     */
    private static final Map<Socket, Integer> CLIENT_SOCKET_MAP = new HashMap<>();
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
    public VCRecvThread(Socket socket) {
        this.socket = socket;
        try {
            this.pw = new PrintWriter(socket.getOutputStream());
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取房间Map集合
     *
     * @return 房间Map集合
     */
    public static Map<Integer, Socket> getRoomMap() {
        return ROOM_MAP;
    }

    /**
     * 获取用户套接字集合
     *
     * @return 用户套接字集合
     */
    public static Map<Socket, Integer> getClientSocketMap() {
        return CLIENT_SOCKET_MAP;
    }

    /**
     * 循环解析客户端发来的消息并执行业务
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
                VODServiceController.getService(type).doService(this);
            } catch (IOException e) {
                if (!e.getMessage().equals("正常退出")) {
                    e.printStackTrace();
                    //将该socket对应的房间从Map中移除
                    Integer roomId = CLIENT_SOCKET_MAP.remove(socket);
                    if (roomId != null) {
                        //将该房间从Map集合中删除
                        ROOM_MAP.remove(roomId);
                        showMessage(TimeUtil.getNowTime() + "\t" + roomId + "号房间：异常注销");
                        //发送消息给播放服务端
                        ExceptionLogoffRequest exceptionLogoffRequest = new ExceptionLogoffRequest(new RoomPO(roomId, null));
                        PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(exceptionLogoffRequest));
                    }
                }
                VCServer.showLoginMessage(socket, false);
                break;
            }
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
        vodSv.getTaVODClient().append(message);
        vodSv.getTaVODClient().append("\r\n");
        vodSv.getTaVODClient().setCaretPosition(vodSv.getTaVODClient().getText().length());
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