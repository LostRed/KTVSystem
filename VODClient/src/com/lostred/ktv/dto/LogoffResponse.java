package com.lostred.ktv.dto;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播服务端回应注销房间的消息
 */
public class LogoffResponse {
    private String type = JsonUtil.LOGOFF;
    private RoomPO roomPO;

    public LogoffResponse() {
    }

    public LogoffResponse(RoomPO roomPO) {
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
