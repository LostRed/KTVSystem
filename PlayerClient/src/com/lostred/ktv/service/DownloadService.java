package com.lostred.ktv.service;

import com.lostred.ktv.controller.DownloadFileThread;
import com.lostred.ktv.dto.ex.DownloadResponse;
import com.lostred.ktv.net.RecvThread;
import net.sf.json.JSONObject;

/**
 * 播放服务端回应下载文件业务
 */
public class DownloadService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        DownloadResponse downloadResponse = (DownloadResponse) JSONObject.toBean(jsonObject, DownloadResponse.class);
        String ip = downloadResponse.getIp();
        int port = downloadResponse.getPort();
        String md5 = downloadResponse.getMd5();
        long fileSize = downloadResponse.getFileSize();
        //开启下载文件线程
        new DownloadFileThread(md5, fileSize, ip, port).start();
    }
}
