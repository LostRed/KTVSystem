package com.lostred.ktv.dto.player;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 播放客户端请求播放结束的消息
 */
public class EndRequest {
    private String type = JsonUtil.END;
    private RoomPO roomPO;

    public EndRequest() {
    }

    public EndRequest(RoomPO roomPO) {
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
