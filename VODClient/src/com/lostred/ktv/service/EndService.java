package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.view.VODClientView;

import javax.swing.*;

/**
 * 点播服务端请求播放结束业务
 */
public class EndService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //修改正在播放标签
        SwingUtilities.invokeLater(() -> VODClientView.getVODClientView().getLbSong().setText("当前播放歌曲：无"));
    }
}
