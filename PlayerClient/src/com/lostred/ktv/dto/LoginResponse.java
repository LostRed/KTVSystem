package com.lostred.ktv.dto;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播服务端回应登录房间的消息
 */
public class LoginResponse {
    private String type = JsonUtil.LOGIN;
    private String result;
    private RoomPO roomPO;

    public LoginResponse() {
    }

    public LoginResponse(String result, RoomPO roomPO) {
        this.result = result;
        this.roomPO = roomPO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public RoomPO getRoomPO() {
        return roomPO;
    }

    public void setRoomPO(RoomPO roomPO) {
        this.roomPO = roomPO;
    }
}
