package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.view.VODClientView;

import javax.swing.*;

/**
 * 点播服务端请求退出房间业务
 */
public class ExitService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //设置播放段状态
        VODClientView.getVODClientView().setEntered(false);
        //修改界面
        SwingUtilities.invokeLater(() -> VODClientView.getVODClientView().changePlayerStatus(0));
    }
}
