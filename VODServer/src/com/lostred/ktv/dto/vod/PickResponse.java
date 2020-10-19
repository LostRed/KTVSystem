package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 点播服务端回应点歌的消息
 */
public class PickResponse {
    private String type = JsonUtil.PICK_SONG;
    private RoomPO roomPO;
    private SongPO songPO;
    private List<PlaylistPO> list;

    public PickResponse() {
    }

    public PickResponse(RoomPO roomPO, SongPO songPO, List<PlaylistPO> list) {
        this.roomPO = roomPO;
        this.songPO = songPO;
        this.list = list;
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

    public List<PlaylistPO> getList() {
        return list;
    }

    public void setList(List<PlaylistPO> list) {
        this.list = list;
    }
}
