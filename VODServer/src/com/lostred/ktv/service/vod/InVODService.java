package com.lostred.ktv.service.vod;

import com.lostred.ktv.net.VCRecvThread;

import java.io.IOException;

/**
 * 点播客户端业务
 */
public interface InVODService {
    /**
     * 执行业务
     *
     * @param handler 通讯线程对象
     * @throws IOException IO异常
     */
    void doService(VCRecvThread handler) throws IOException;
}
