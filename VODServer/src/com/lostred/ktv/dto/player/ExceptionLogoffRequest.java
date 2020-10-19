package com.lostred.ktv.dto.player;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播服务端请求异常注销房间的消息
 */
public class ExceptionLogoffRequest {
    private String type = JsonUtil.EXCEPTION_LOGOFF;
    private RoomPO roomPO;

    public ExceptionLogoffRequest() {
    }

    public ExceptionLogoffRequest(RoomPO roomPO) {
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
