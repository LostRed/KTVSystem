package com.lostred.ktv.dto.ex;

import com.lostred.ktv.util.JsonUtil;

/**
 * 播放服务端端回应下载文件结果的消息
 */
public class DownloadResponse {
    private String type = JsonUtil.DOWNLOAD;
    private String md5;
    private long fileSize;
    private String ip;
    private int port;

    public DownloadResponse() {
    }

    public DownloadResponse(String md5, long fileSize, String ip, int port) {
        this.md5 = md5;
        this.fileSize = fileSize;
        this.ip = ip;
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
