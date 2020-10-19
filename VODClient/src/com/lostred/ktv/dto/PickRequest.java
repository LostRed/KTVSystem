package com.lostred.ktv.dto;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求点歌的消息
 */
public class PickRequest {
    private String type = JsonUtil.PICK_SONG;
    private RoomPO roomPO;
    private SongPO songPO;

    public PickRequest() {
    }

    public PickRequest(RoomPO roomPO, SongPO songPO) {
        this.roomPO = roomPO;
        this.songPO = songPO;
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

    public SongPO getSongPO() {
        return songPO;
    }

    public void setSongPO(SongPO songPO) {
        this.songPO = songPO;
    }
}
