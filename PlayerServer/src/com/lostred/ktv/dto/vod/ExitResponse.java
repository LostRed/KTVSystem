package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播服务端回应退出房间的消息
 */
public class ExitResponse {
    private String type = JsonUtil.EXIT;
    private RoomPO roomPO;
    private boolean exist;

    public ExitResponse() {
    }

    public ExitResponse(RoomPO roomPO, boolean exist) {
        this.roomPO = roomPO;
        this.exist = exist;
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

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
