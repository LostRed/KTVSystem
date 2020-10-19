package com.lostred.ktv.dto.player;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 播放服务端回应点播客户端重连的消息
 */
public class VODReconnectResponse {
    private String type = JsonUtil.VOD_RECONNECT;
    private RoomPO roomPO;

    public VODReconnectResponse() {
    }

    public VODReconnectResponse(RoomPO roomPO) {
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
