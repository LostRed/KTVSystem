package com.lostred.ktv.dto;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 播放服务端回应查询空闲房间的消息
 */
public class FreeRoomListResponse {
    private String type = JsonUtil.ROOM_LIST;
    private List<RoomPO> list;

    public FreeRoomListResponse() {
    }

    public FreeRoomListResponse(List<RoomPO> list) {
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
