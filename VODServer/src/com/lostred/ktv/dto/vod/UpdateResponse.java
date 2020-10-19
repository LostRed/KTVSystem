package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 点播服务端回应更新数据库的消息
 */
public class UpdateResponse {
    private String type = JsonUtil.UPDATE_DATABASE;
    private List<SongPO> list;
    private long fileSize;
    private String ip;
    private int port;

    public UpdateResponse() {
    }

    public UpdateResponse(List<SongPO> list, long fileSize, String ip, int port) {
        this.list = list;
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

    public List<SongPO> getList() {
        return list;
    }

    public void setList(List<SongPO> list) {
        this.list = list;
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
