package com.lostred.ktv.controller;

import com.lostred.ktv.dto.*;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.SongInfoPanel.SongInfoTable;
import com.lostred.ktv.view.VODClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 点播客户端主界面控制按钮动作监听
 */
public class ControlAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        VODClientView vodCv = VODClientView.getVODClientView();
        String command = e.getActionCommand();
        switch (command) {
            //K歌
            case "kSong":
                //如果客户端已连接
                if (Client.getClient().isConnected()) {
                    SongInfoTable songInfoTable = vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable();
                    int selectRow = songInfoTable.getSelectedRow();
                    //选中表格中的某一行时
                    if (selectRow != -1) {
                        if (!vodCv.isEntered()) {
                            JOptionPane.showMessageDialog(vodCv, "播放端未连接！");
                            return;
                        }
                        PlaylistPO playlistPO = (PlaylistPO) songInfoTable.getValueAt(selectRow, 1);
                        //发送业务请求
                        KRequest kRequest = new KRequest(playlistPO);
                        Client.getClient().sendMessage(JsonUtil.toJsonString(kRequest));
                    } else {
                        JOptionPane.showMessageDialog(vodCv, "请先选中一首歌曲！");
                    }
                } else {
                    JOptionPane.showMessageDialog(vodCv, "未连接点播服务端！");
                }
                break;
            //置顶
            case "top":
                //如果客户端已连接
                if (Client.getClient().isConnected()) {
                    SongInfoTable songInfoTable = vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable();
                    int selectRow = songInfoTable.getSelectedRow();
                    //选中表格中的某一行时
                    if (selectRow != -1) {
                        PlaylistPO playlistPO = (PlaylistPO) songInfoTable.getValueAt(selectRow, 1);
                        //发送业务请求
                        TopRequest topRequest = new TopRequest(vodCv.getRoomPO(), playlistPO);
                        Client.getClient().sendMessage(JsonUtil.toJsonString(topRequest));
                    } else {
                        JOptionPane.showMessageDialog(vodCv, "请先选中一首歌曲！");
                    }
                } else {
                    JOptionPane.showMessageDialog(vodCv, "未连接点播服务端！");
                }
                break;
            //删除
            case "delete":
                //如果客户端已连接
                if (Client.getClient().isConnected()) {
                    SongInfoTable songInfoTable = vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable();
                    int selectRow = songInfoTable.getSelectedRow();
                    //选中表格中的某一行时
                    if (selectRow != -1) {
                        PlaylistPO playlistPO = (PlaylistPO) songInfoTable.getValueAt(selectRow, 1);
                        //发送业务请求
                        DeleteRequest deleteRequest = new DeleteRequest(vodCv.getRoomPO(), playlistPO);
                        Client.getClient().sendMessage(JsonUtil.toJsonString(deleteRequest));
                    } else {
                        JOptionPane.showMessageDialog(vodCv, "请先选中一首歌曲！");
                    }
                } else {
                    JOptionPane.showMessageDialog(vodCv, "未连接点播服务端！");
                }
                break;
            //点歌
            case "pick":
                //如果客户端已连接
                if (Client.getClient().isConnected()) {
                    SongInfoTable songInfoTable = vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable();
                    int selectRow = songInfoTable.getSelectedRow();
                    //选中表格中的某一行时
                    if (selectRow != -1) {
                        SongPO songPO = (SongPO) songInfoTable.getValueAt(selectRow, 1);
                        //发送业务请求
                        PickRequest pickRequest = new PickRequest(vodCv.getRoomPO(), songPO);
                        Client.getClient().sendMessage(JsonUtil.toJsonString(pickRequest));
                    } else {
                        JOptionPane.showMessageDialog(vodCv, "请先选中一首歌曲！");
                    }
                } else {
                    JOptionPane.showMessageDialog(vodCv, "未连接点播服务端！");
                }
                break;
            //上一页
            case "pageUp":
                //如果客户端模式为热榜推荐
                if (vodCv.getMode() == VODClientView.Mode.HOT) {
                    //如果客户端已连接
                    if (Client.getClient().isConnected()) {
                        vodCv.pageUp();
                        refreshView();
                    } else {
                        JOptionPane.showMessageDialog(vodCv, "未连接点播服务端！");
                    }
                } else {
                    vodCv.pageUp();
                    refreshView();
                }
                break;
            //下一页
            case "pageDown":
                //如果客户端模式为热榜推荐
                if (vodCv.getMode() == VODClientView.Mode.HOT) {
                    //如果客户端已连接
                    if (Client.getClient().isConnected()) {
                        vodCv.pageDown();
                        refreshView();
                    } else {
                        JOptionPane.showMessageDialog(vodCv, "未连接点播服务端！");
                    }
                } else {
                    vodCv.pageDown();
                    refreshView();
                }
                break;
        }
    }

    /**
     * 刷新界面
     */
    private void refreshView() {
        VODClientView vodCv = VODClientView.getVODClientView();
        LocalService localService = LocalService.getLocalService();
        //根据点播客户端主界面模式刷新界面
        switch (vodCv.getMode()) {
            case PLAYLIST:
                localService.refreshPlaylistView();
                break;
            case PINYIN:
                localService.refreshPinYinView();
                break;
            case SINGER:
                localService.refreshSingerView();
                break;
            case STYLE:
                localService.refreshStyleView();
                break;
            case HOT:
                int limit = vodCv.getLimit();
                int offset = vodCv.getOffset();
                //向点播服务端发送歌曲热度排行版请求
                HotRequest hotRequest = new HotRequest(vodCv.getRoomPO(), limit, offset);
                Client.getClient().sendMessage(JsonUtil.toJsonString(hotRequest));
                break;
        }
    }
}
