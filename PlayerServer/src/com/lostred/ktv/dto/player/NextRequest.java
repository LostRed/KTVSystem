package com.lostred.ktv.dto.player;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 播放客户端请求切歌的消息
 */
public class NextRequest {
    private String type = JsonUtil.NEXT;
    private RoomPO roomPO;

    public NextRequest() {
    }

    public NextRequest(RoomPO roomPO) {
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
