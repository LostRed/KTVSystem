package com.lostred.ktv.controller;


import com.lostred.ktv.dto.ex.DownloadRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.SongTimeUtil;
import com.lostred.ktv.view.PlayerClientView;

import javax.media.Player;
import javax.swing.*;

/**
 * 音乐进度条线程
 */
public class ProgressThread extends Thread {
    @Override
    public void run() {
        while (true) {
            PlayerClientView pcv = PlayerClientView.getPlayerClientView();
            JSlider slProgress = pcv.getSlProgress();
            Player player = pcv.getPlayer();
            //如果多媒体对象不为空且歌曲处于播放状态时
            if (player != null && player.getState() == Player.Started) {
                //获取歌曲当前时间
                int currentTime = (int) player.getMediaTime().getSeconds();
                double totalTime = player.getDuration().getSeconds();
                //写入配置文件
                ConfigUtil.writeProperties("currentTime", Integer.toString(currentTime));
                //预下载下一首歌曲
                requestDownload(currentTime, totalTime);
                //更新窗口组件
                SwingUtilities.invokeLater(() -> {
                    slProgress.setValue(currentTime);
                    pcv.getLbCurrentTime().setText(SongTimeUtil.format(currentTime));
                });
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送请求下载歌曲业务
     *
     * @param currentTime 当前歌曲播放时间
     * @param totalTime   当前歌曲的总时长
     */
    private void requestDownload(int currentTime, double totalTime) {
        //如果下载线程未开始且歌曲播放时间超过70%或剩余20秒时间
        if (!DownloadFileThread.START && (currentTime / totalTime > 0.7 || totalTime - currentTime < 20)) {
            PlayerClientView pcv = PlayerClientView.getPlayerClientView();
            //如果客户端已连接且播放列表不为空时
            if (Client.getClient().isConnected() && pcv.getPlaylist().size() != 0) {
                DownloadFileThread.START = true;
                //向播放服务端下载下一首歌的请求
                DownloadRequest downloadRequest = new DownloadRequest(pcv.getRoomPO());
                Client.getClient().sendMessage(JsonUtil.toJsonString(downloadRequest));
            }
        }
    }
}
