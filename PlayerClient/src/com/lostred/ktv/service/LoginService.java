package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.view.PlayerClientView;

import javax.swing.*;

/**
 * 播放服务端回应登录房间业务
 */
public class LoginService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //修改界面
        SwingUtilities.invokeLater(() -> PlayerClientView.getPlayerClientView().changeVODStatus(1));
    }
}
