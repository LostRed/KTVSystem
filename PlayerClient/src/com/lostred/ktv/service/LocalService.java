package com.lostred.ktv.service;

import com.lostred.ktv.dto.FreeRoomListRequest;
import com.lostred.ktv.dto.PlayerReconnectRequest;
import com.lostred.ktv.dto.PlaylistRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.SongTimeUtil;
import com.lostred.ktv.view.PlayerClientView;

import javax.media.Player;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

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
     * 请求空闲的房间列表
     */
    public void requestRoomList() {
        FreeRoomListRequest freeRoomListRequest = new FreeRoomListRequest();
        Client.getClient().sendMessage(JsonUtil.toJsonString(freeRoomListRequest));
    }

    /**
     * 请求房间的播放列表
     *
     * @param roomPO 房间
     */
    public void requestPlaylist(RoomPO roomPO) {
        PlaylistRequest playlistRequest = new PlaylistRequest(roomPO);
        Client.getClient().sendMessage(JsonUtil.toJsonString(playlistRequest));
    }

    /**
     * 未进入房间状态下连接服务端
     */
    public void connect() {
        String host = ConfigUtil.getIp();
        String port = ConfigUtil.getPort();
        try {
            Client.getClient().connect(host, port);
            //更改界面
            SwingUtilities.invokeLater(() -> {
                PlayerClientView.getPlayerClientView().changeServerStatus(1);
                PlayerClientView.getPlayerClientView().changeVODStatus(1);
            });
            //请求房间列表
            requestRoomList();
        } catch (IOException ignored) {
        }
    }

    /**
     * 进入房间状态下重连服务端
     */
    public void reconnect() {
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        String host = ConfigUtil.getIp();
        String port = ConfigUtil.getPort();
        try {
            Client.getClient().connect(host, port);
            //更改界面
            SwingUtilities.invokeLater(() -> pcv.changeServerStatus(1));
            //发送重连请求
            PlayerReconnectRequest playerReconnectRequest = new PlayerReconnectRequest(pcv.getRoomPO());
            Client.getClient().sendMessage(JsonUtil.toJsonString(playerReconnectRequest));
            JOptionPane.showMessageDialog(pcv, "已重新连接播放服务端！");
        } catch (IOException ignored) {
        }
    }

    /**
     * 播放音乐文件
     *
     * @param file 音乐文件
     */
    public void play(File file) {
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        Player player = pcv.getPlayer();
        //如果多媒体对象不为空
        if (player != null) {
            //关闭外部文件
            player.close();
            //把多媒体对象置为空
            pcv.setPlayer(null);
            //进度条改为0
            pcv.getSlProgress().setValue(0);
            //把当前时间和总时长改为0
            pcv.getLbCurrentTime().setText(SongTimeUtil.format(0));
            pcv.getLbTotalTime().setText(SongTimeUtil.format(0));
        }
        pcv.setFile(file);
        pcv.setTime(0);
        pcv.realizeMusic();
    }
}
