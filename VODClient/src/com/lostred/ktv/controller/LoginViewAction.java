package com.lostred.ktv.controller;

import com.lostred.ktv.dto.LoginRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录窗口动作监听
 */
public class LoginViewAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        LoginView lv = LoginView.getLoginView();
        switch (command) {
            //登录
            case "login":
                //获取文本框文本
                String roomId = lv.getTfRoomId().getText();
                String password = new String(lv.getPfPassword().getPassword());
                //房间号为空
                if (roomId.equals("")) {
                    JOptionPane.showMessageDialog(lv, "请输入房间号！");
                    return;
                }
                try {
                    Integer.parseInt(roomId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(lv, "房间号请输入数字！");
                    return;
                }
                //密码为空
                if (password.equals("")) {
                    JOptionPane.showMessageDialog(lv, "请输入密码！");
                    return;
                }
                //如果客户端未连接
                if (!Client.getClient().isConnected()) {
                    JOptionPane.showMessageDialog(lv, "未连接点播服务端！");
                } else {
                    //发送登录请求
                    RoomPO inputRoomPO = new RoomPO(Integer.parseInt(roomId), password);
                    LoginRequest loginRequest = new LoginRequest(inputRoomPO);
                    Client.getClient().sendMessage(JsonUtil.toJsonString(loginRequest));
                    lv.getBtnLogin().setEnabled(false);
                }
                break;
            //清除
            case "clear":
                //清除房间号和密码文本框的内容
                lv.getTfRoomId().setText("");
                lv.getPfPassword().setText("");
                break;
        }
    }
}
