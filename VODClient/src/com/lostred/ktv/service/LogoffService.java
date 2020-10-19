package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;

/**
 * 点播服务端回应注销房间业务
 */
public class LogoffService implements InService {
    @Override
    public void doService(RecvThread handler) {
        System.exit(0);
    }
}
