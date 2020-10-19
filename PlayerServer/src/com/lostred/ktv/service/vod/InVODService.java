package com.lostred.ktv.service.vod;

import com.lostred.ktv.net.VSRecvThread;

import java.io.IOException;

/**
 * 点播服务端业务接口
 */
public interface InVODService {
    /**
     * 执行业务
     *
     * @param handler 通讯线程对象
     * @throws IOException IO异常
     */
    void doService(VSRecvThread handler) throws IOException;
}
