package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;

import java.io.IOException;

/**
 * 业务接口
 */
public interface InService {
    /**
     * 执行业务
     *
     * @param handler 通讯线程对象
     * @throws IOException IO异常
     */
    void doService(RecvThread handler) throws IOException;
}