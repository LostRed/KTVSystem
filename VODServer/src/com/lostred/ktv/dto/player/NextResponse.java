package com.lostred.ktv.dto.player;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 播放服务端回应切歌的消息
 */
public class NextResponse {
    private String type = JsonUtil.NEXT;
    private RoomPO roomPO;
    private PlaylistPO playlistPO;
    private List<PlaylistPO> list;

    public NextResponse() {
    }

    public NextResponse(RoomPO roomPO, PlaylistPO playlistPO, List<PlaylistPO> list) {
        this.roomPO = roomPO;
        this.playlistPO = playlistPO;
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

    public PlaylistPO getPlaylistPO() {
        return playlistPO;
    }

    public void setPlaylistPO(PlaylistPO playlistPO) {
        this.playlistPO = playlistPO;
    }

    public List<PlaylistPO> getList() {
        return list;
    }

    public void setList(List<PlaylistPO> list) {
        this.list = list;
    }
}
