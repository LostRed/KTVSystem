package com.lostred.ktv.dto.player;

import com.lostred.ktv.util.JsonUtil;

/**
 * 播放客户端请求查询空闲房间的消息
 */
public class FreeRoomListRequest {
    private String type = JsonUtil.ROOM_LIST;

    public FreeRoomListRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
