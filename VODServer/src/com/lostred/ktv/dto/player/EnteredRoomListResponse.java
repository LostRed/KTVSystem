package com.lostred.ktv.dto.player;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 点播服务端回应占用房间的消息
 */
public class EnteredRoomListResponse {
    private String type = JsonUtil.ROOM_LIST;
    private List<RoomPO> list;

    public EnteredRoomListResponse() {
    }

    public EnteredRoomListResponse(List<RoomPO> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<RoomPO> getList() {
        return list;
    }

    public void setList(List<RoomPO> list) {
        this.list = list;
    }
}
