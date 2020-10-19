package com.lostred.ktv.controller;

import com.lostred.ktv.dto.EnterRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.SelectRoomView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 选择房间界面按钮动作监听
 */
public class SRViewAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "enter":
                //获取房间PO
                RoomPO roomPO = (RoomPO) SelectRoomView.getSelectRoomView().getCbRoom().getSelectedItem();
                //如果房间PO不为空
                if (roomPO != null) {
                    //发送业务请求消息
                    EnterRequest enterRequest = new EnterRequest(roomPO);
                    Client.getClient().sendMessage(JsonUtil.toJsonString(enterRequest));
                } else {
                    JOptionPane.showMessageDialog(SelectRoomView.getSelectRoomView(), "请选择一个房间！");
                }
                break;
        }
    }
}
