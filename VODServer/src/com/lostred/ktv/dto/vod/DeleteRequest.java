package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求删除歌曲的消息
 */
public class DeleteRequest {
    private String type = JsonUtil.DELETE_SONG;
    private RoomPO roomPO;
    private PlaylistPO playlistPO;

    public DeleteRequest() {
    }

    public DeleteRequest(RoomPO roomPO, PlaylistPO playlistPO) {
        this.roomPO = roomPO;
        this.playlistPO = playlistPO;
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
}
