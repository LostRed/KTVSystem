package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求重连的消息
 */
public class VODReconnectRequest {
    private String type = JsonUtil.VOD_RECONNECT;
    private RoomPO roomPO;

    public VODReconnectRequest() {
    }

    public VODReconnectRequest(RoomPO roomPO) {
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
