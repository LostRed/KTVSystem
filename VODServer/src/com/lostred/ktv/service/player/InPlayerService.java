package com.lostred.ktv.service.player;

import com.lostred.ktv.net.PSRecvThread;

import java.io.IOException;

/**
 * 播放服务器业务
 */
public interface InPlayerService {
    /**
     * 执行业务
     *
     * @param handler 通讯线程对象
     * @throws IOException IO异常
     */
    void doService(PSRecvThread handler) throws IOException;
}
