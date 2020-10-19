package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求登录房间的消息
 */
public class LoginRequest {
    private String type = JsonUtil.LOGIN;
    private RoomPO roomPO;

    public LoginRequest() {
    }

    public LoginRequest(RoomPO roomPO) {
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
