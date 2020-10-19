package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;

/**
 * 点播客户端业务接口
 */
public interface InService {
    /**
     * 执行业务
     *
     * @param handler 通讯线程对象
     */
    void doService(RecvThread handler);
}
