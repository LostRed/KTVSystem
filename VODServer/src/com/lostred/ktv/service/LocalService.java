package com.lostred.ktv.service;

import com.lostred.ktv.controller.ReconnectThread;
import com.lostred.ktv.dto.player.LoginRoomListResponse;
import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.ConnectView;
import com.lostred.ktv.view.VODServerView;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 本地业务
 */
public class LocalService {
    /**
     * 单例业务对象
     */
    private static LocalService LOCAL_SERVICE;

    /**
     * 构造业务对象
     */
    private LocalService() {
    }

    /**
     * 获取单例业务
     *
     * @return 业务对象
     */
    public static LocalService getLocalService() {
        if (LOCAL_SERVICE == null) {
            synchronized (LocalService.class) {
                if (LOCAL_SERVICE == null) {
                    LOCAL_SERVICE = new LocalService();
                }
            }
        }
        return LOCAL_SERVICE;
    }

    /**
     * 向播放服务端发送房间列表
     */
    public void sendLoginRoomList() {
        Collection<Integer> collection = VCRecvThread.getClientSocketMap().values();
        List<RoomPO> list = new ArrayList<>();
        for (Integer roomId : collection) {
            RoomPO roomPO = new RoomPO(roomId, null);
            list.add(roomPO);
        }
        LoginRoomListResponse loginRoomListResponse = new LoginRoomListResponse(list);
        PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(loginRoomListResponse));
    }

    /**
     * 首次连接播放服务端
     */
    public void connect() {
        new Thread(() -> {
            String host = ConfigUtil.getIp();
            String port = ConfigUtil.getPort();
            try {
                PSClient.getPSClient().connect(host, port);
                SwingUtilities.invokeLater(() -> {
                    ConnectView.getConnectView().dispose();
                    VODServerView.getVODServerView().changeServerStatus(1);
                });
                JOptionPane.showMessageDialog(VODServerView.getVODServerView(), "成功连接播放服务端！");
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> ConnectView.getConnectView().dispose());
                //启动重连线程
                new ReconnectThread().start();
                JOptionPane.showMessageDialog(VODServerView.getVODServerView(), "无法连接播放服务端！");
            } finally {
                VODServerView.getVODServerView().setVisible(true);
                VODServerView.getVODServerView().setState(JFrame.NORMAL);
            }
        }).start();
        //使用模态框阻塞
        SwingUtilities.invokeLater(() -> ConnectView.getConnectView().setVisible(true));
    }

    /**
     * 重连播放服务端
     */
    public void reconnect() {
        VODServerView vodSv = VODServerView.getVODServerView();
        String host = ConfigUtil.getIp();
        String port = ConfigUtil.getPort();
        try {
            PSClient.getPSClient().connect(host, port);
            SwingUtilities.invokeLater(() -> VODServerView.getVODServerView().changeServerStatus(1));
            //向播放服务端发送房间列表
            sendLoginRoomList();
            JOptionPane.showMessageDialog(vodSv, "已重新连接播放服务端！");
        } catch (IOException ignored) {
        }
    }
}
