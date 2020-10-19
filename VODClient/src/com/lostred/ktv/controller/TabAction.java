package com.lostred.ktv.controller;

import com.lostred.ktv.dto.HotRequest;
import com.lostred.ktv.dto.LogoffRequest;
import com.lostred.ktv.dto.PlaylistRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.VODClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 播放客户端主界面切换模式按钮动作监听
 */
public class TabAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        VODClientView vodCv = VODClientView.getVODClientView();
        String command = e.getActionCommand();
        LocalService localService = LocalService.getLocalService();
        switch (command) {
            //已点歌曲
            case "playlist":
                //更改界面显示
                vodCv.setMode(VODClientView.Mode.PLAYLIST);
                vodCv.toPageOne();
                vodCv.getLbTheme().setText("已点歌曲");
                //切换面板
                vodCv.getSongInfoPanel().getControlPanel().toPlaylistPanel();
                //如果客户端未连接
                if (!Client.getClient().isConnected()) {
                    //显示缓存中保存的已点歌曲集合
                    localService.refreshPlaylistView();
                }
                //如果客户端已连接，向服务端请求查询已点歌曲
                else {
                    //发送业务请求消息
                    PlaylistRequest playlistRequest = new PlaylistRequest(vodCv.getRoomPO());
                    Client.getClient().sendMessage(JsonUtil.toJsonString(playlistRequest));
                }
                break;
            //拼音点歌
            case "pinYin":
                //更改界面显示
                vodCv.setMode(VODClientView.Mode.PINYIN);
                vodCv.toPageOne();
                vodCv.getLbTheme().setText("拼音点歌");
                //切换面板
                vodCv.getSongInfoPanel().getControlPanel().toPinYinPanel();
                //刷新表格视图
                localService.refreshPinYinView();
                break;
            //歌星点歌
            case "singer":
                //更改界面显示
                vodCv.setMode(VODClientView.Mode.SINGER);
                vodCv.toPageOne();
                vodCv.getLbTheme().setText("歌星点歌");
                //切换面板
                vodCv.getSongInfoPanel().getControlPanel().toSingerPanel();
                //刷新表格视图
                localService.refreshSingerView();
                break;
            //分类点歌
            case "style":
                //更改界面显示
                vodCv.setMode(VODClientView.Mode.STYLE);
                vodCv.toPageOne();
                vodCv.getLbTheme().setText("分类点歌");
                //切换面板
                vodCv.getSongInfoPanel().getControlPanel().toStylePanel();
                //刷新表格视图
                localService.refreshStyleView();
                break;
            //热门推荐
            case "hot":
                //如果客户端已连接
                if (Client.getClient().isConnected()) {
                    //更改界面显示
                    vodCv.setMode(VODClientView.Mode.HOT);
                    vodCv.toPageOne();
                    vodCv.getLbTheme().setText("热门推荐");
                    //切换面板
                    vodCv.getSongInfoPanel().getControlPanel().toHotPanel();
                    //发送业务请求消息
                    int limit = vodCv.getLimit();
                    int offset = vodCv.getOffset();
                    HotRequest hotRequest = new HotRequest(vodCv.getRoomPO(), limit, offset);
                    Client.getClient().sendMessage(JsonUtil.toJsonString(hotRequest));
                } else {
                    JOptionPane.showMessageDialog(vodCv, "未连接点播服务端！");
                }
                break;
            //注销房间
            case "quit":
                int choice = JOptionPane.showConfirmDialog(vodCv, "是否注销房间？", "提示", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    if (Client.getClient().isConnected()) {
                        //发送业务请求消息
                        LogoffRequest logoffRequest = new LogoffRequest(vodCv.getRoomPO());
                        Client.getClient().sendMessage(JsonUtil.toJsonString(logoffRequest));
                    } else {
                        System.exit(0);
                    }
                }
                break;
        }
    }
}
