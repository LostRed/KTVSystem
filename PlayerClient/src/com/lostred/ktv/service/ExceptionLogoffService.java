package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.view.PlayerClientView;

import javax.swing.*;

/**
 * 播放服务端请求异常注销房间业务
 */
public class ExceptionLogoffService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //更改界面
        SwingUtilities.invokeLater(() -> PlayerClientView.getPlayerClientView().changeVODStatus(0));
    }
}
