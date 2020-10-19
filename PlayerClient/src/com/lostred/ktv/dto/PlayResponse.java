package com.lostred.ktv.dto;

import com.lostred.ktv.util.JsonUtil;

/**
 * 播放客户端回应播放歌曲结果的消息
 */
public class PlayResponse {
    private String type = JsonUtil.PLAY;
    private String filePath;
    private String ip;
    private int port;

    public PlayResponse() {
    }

    public PlayResponse(String filePath, String ip, int port) {
        this.filePath = filePath;
        this.ip = ip;
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
