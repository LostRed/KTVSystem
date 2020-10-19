package com.lostred.ktv.dto;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求查询歌曲热度排行的消息
 */
public class HotRequest {
    private String type = JsonUtil.HOT;
    private RoomPO roomPO;
    private int limit;
    private int offset;

    public HotRequest() {
    }

    public HotRequest(RoomPO roomPO, int limit, int offset) {
        this.roomPO = roomPO;
        this.limit = limit;
        this.offset = offset;
    }

    public RoomPO getRoomPO() {
        return roomPO;
    }

    public void setRoomPO(RoomPO roomPO) {
        this.roomPO = roomPO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
