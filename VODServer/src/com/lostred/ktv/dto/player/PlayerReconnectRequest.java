package com.lostred.ktv.dto.player;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 播放客户端请求重连的消息
 */
public class PlayerReconnectRequest {
    private String type = JsonUtil.PLAYER_RECONNECT;
    private RoomPO roomPO;

    public PlayerReconnectRequest() {
    }

    public PlayerReconnectRequest(RoomPO roomPO) {
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
