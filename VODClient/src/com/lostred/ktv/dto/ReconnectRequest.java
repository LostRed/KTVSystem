package com.lostred.ktv.dto;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求重连的消息
 */
public class ReconnectRequest {
    private String type = JsonUtil.VOD_RECONNECT;
    private RoomPO roomPO;

    public ReconnectRequest() {
    }

    public ReconnectRequest(RoomPO roomPO) {
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
