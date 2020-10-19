package com.lostred.ktv.controller;

import com.lostred.ktv.dto.EndRequest;
import com.lostred.ktv.dto.NextRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.SongTimeUtil;
import com.lostred.ktv.view.PlayerClientView;

import javax.media.*;
import javax.swing.*;

/**
 * 多媒体文件监听
 */
public class MediaController extends ControllerAdapter {
    @Override
    public void realizeComplete(RealizeCompleteEvent e) {
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        Player player = pcv.getPlayer();
        player.prefetch();
    }

    @Override
    public void prefetchComplete(PrefetchCompleteEvent e) {
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        Player player = pcv.getPlayer();
        //获取音乐文件的总时长
        int time = (int) player.getDuration().getSeconds();
        //更新播放客户端主界面音乐时长标签
        pcv.getSlProgress().setMaximum(time);
        pcv.getLbTotalTime().setText(SongTimeUtil.format(time));
        //将歌曲开始播放时间设置为播放客户端主界面时间
        player.setMediaTime(new Time((double) pcv.getTime()));
        //更改进度条位置
        pcv.getSlProgress().setValue(pcv.getTime());
        player.start();
    }

    @Override
    public void endOfMedia(EndOfMediaEvent e) {
        //写入配置文件
        ConfigUtil.writeProperties("songName", "");
        ConfigUtil.writeProperties("singerName", "");
        ConfigUtil.writeProperties("md5", "");
        ConfigUtil.writeProperties("currentTime", "");
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        //关闭外部文件
        pcv.getPlayer().close();
        //把多媒体对象置为空
        pcv.setPlayer(null);
        //进度条改为0
        pcv.getSlProgress().setValue(0);
        //把当前时间和总时长改为0
        pcv.getLbCurrentTime().setText(SongTimeUtil.format(0));
        pcv.getLbTotalTime().setText(SongTimeUtil.format(0));
        //显示当前播放的歌曲
        PlayerClientView.getPlayerClientView().showPlayingSong(null);
        //显示下一首歌曲
        PlayerClientView.getPlayerClientView().showNextSong();
        //如果客户端已连接
        if (Client.getClient().isConnected()) {
            //播放列表为空时
            if (pcv.getPlaylist().size() == 0) {
                //向播放服务端发送播放结束请求
                EndRequest endRequest = new EndRequest(pcv.getRoomPO());
                Client.getClient().sendMessage(JsonUtil.toJsonString(endRequest));
                JOptionPane.showMessageDialog(pcv, "已点歌曲已没有下一首了，请先点歌！");
                return;
            }
            //向播放服务端发送切歌请求
            NextRequest nextRequest = new NextRequest(pcv.getRoomPO());
            Client.getClient().sendMessage(JsonUtil.toJsonString(nextRequest));
        } else {
            JOptionPane.showMessageDialog(pcv, "未连接播放服务端！");
        }
    }
}
