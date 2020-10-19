package com.lostred.ktv.dto.ex;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 播放客户端请求下载文件的消息
 */
public class DownloadRequest {
    private String type = JsonUtil.DOWNLOAD;
    private RoomPO roomPO;

    public DownloadRequest() {
    }

    public DownloadRequest(RoomPO roomPO) {
        this.roomPO = roomPO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RoomPO getRoomPO() {
        return roomPO;
    }

    public void setRoomPO(RoomPO roomPO) {
        this.roomPO = roomPO;
    }
}
