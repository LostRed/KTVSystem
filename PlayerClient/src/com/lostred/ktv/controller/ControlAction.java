package com.lostred.ktv.controller;

import com.lostred.ktv.dto.ExitRequest;
import com.lostred.ktv.dto.NextRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.PlayerClientView;

import javax.media.Player;
import javax.media.Time;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 播放客户端主界面控制按钮动作监听
 */
public class ControlAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        Player player = pcv.getPlayer();
        String command = e.getActionCommand();
        switch (command) {
            //退出
            case "exit":
                int choice = JOptionPane.showConfirmDialog(pcv, "是否退出房间？", "提示", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    //写入配置文件
                    ConfigUtil.writeProperties("songName", "");
                    ConfigUtil.writeProperties("singerName", "");
                    ConfigUtil.writeProperties("md5", "");
                    ConfigUtil.writeProperties("currentTime", "");
                    //客户端连接时
                    if (Client.getClient().isConnected()) {
                        //向播放客户端请求退出
                        ExitRequest exitRequest = new ExitRequest(pcv.getRoomPO());
                        Client.getClient().sendMessage(JsonUtil.toJsonString(exitRequest));
                    } else {
                        System.exit(0);
                    }
                }
                break;
            //暂停和继续
            case "pause":
                //如果多媒体对象不为空
                if (pcv.getPlayer() != null) {
                    //当多媒体对象处于播放状态时
                    if (pcv.getPlayer().getState() == Player.Started) {
                        pcv.getBtnPause().setText("播放");
                        player.stop();
                    } else {
                        pcv.getBtnPause().setText("暂停");
                        player.start();
                    }
                }
                break;
            //重唱
            case "replay":
                if (player != null) {
                    player.setMediaTime(new Time(0));
                }
                break;
            //切歌
            case "next":
                //播放列表为空时
                if (pcv.getPlaylist().size() == 0) {
                    JOptionPane.showMessageDialog(pcv, "已点歌曲已经没有下一首了，请先点歌！");
                    return;
                }
                //客户端连接时
                if (Client.getClient().isConnected()) {
                    //发送业务消息
                    NextRequest nextRequest = new NextRequest(pcv.getRoomPO());
                    Client.getClient().sendMessage(JsonUtil.toJsonString(nextRequest));
                    pcv.getBtnNext().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(pcv, "未连接播放服务端！");
                }
                break;
            //静音
            case "silence":
                JSlider slider = pcv.getSlVolume();
                int volume = slider.getValue();
                //音量不为0时
                if (volume != 0) {
                    slider.setValue(0);
                    pcv.getBtnSilence().setText("恢复");
                } else {
                    slider.setValue(pcv.getVolume());
                    pcv.getBtnSilence().setText("静音");
                }
                //如果多媒体对象不为空
                if (player != null) {
                    volume = slider.getValue();
                    float level = (float) volume / 200;
                    player.getGainControl().setLevel(level);
                }
                break;
        }
    }
}
