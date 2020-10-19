package com.lostred.ktv.controller;

import com.lostred.ktv.net.Client;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.SelectRoomView;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * 下拉框弹出菜单监听
 */
public class ComboBoxPopupMenu implements PopupMenuListener {
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        SelectRoomView sv = SelectRoomView.getSelectRoomView();
        //客户端连接时
        if (Client.getClient().isConnected()) {
            //向播放服务器请求空闲的房间列表
            LocalService.getLocalService().requestRoomList();
        } else {
            JOptionPane.showMessageDialog(sv, "未连接播放服务端！");
        }
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }
}
