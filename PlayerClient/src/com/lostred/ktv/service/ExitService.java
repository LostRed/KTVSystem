package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;

/**
 * 播放服务端回应退出房间业务
 */
public class ExitService implements InService {
    @Override
    public void doService(RecvThread handler) {
        System.exit(0);
    }
}
