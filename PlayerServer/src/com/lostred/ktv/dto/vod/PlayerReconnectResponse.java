package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播服务端回应播放客户端重连的消息
 */
public class PlayerReconnectResponse {
    private String type = JsonUtil.PLAYER_RECONNECT;
    private RoomPO roomPO;

    public PlayerReconnectResponse() {
    }

    public PlayerReconnectResponse(RoomPO roomPO) {
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
