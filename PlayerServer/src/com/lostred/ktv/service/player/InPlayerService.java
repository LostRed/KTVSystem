package com.lostred.ktv.service.player;

import com.lostred.ktv.net.PCRecvThread;

import java.io.IOException;

/**
 * 播放客户端业务接口
 */
public interface InPlayerService {
    /**
     * 执行业务
     *
     * @param handler 通讯线程对象
     * @throws IOException IO异常
     */
    void doService(PCRecvThread handler) throws IOException;
}
