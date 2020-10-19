package com.lostred.ktv.dto.player;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 播放服务端回应查询播放列表的消息
 */
public class PlaylistResponse {
    private String type = JsonUtil.PLAYLIST;
    private RoomPO roomPO;
    private List<PlaylistPO> list;

    public PlaylistResponse() {
    }

    public PlaylistResponse(RoomPO roomPO, List<PlaylistPO> list) {
        this.roomPO = roomPO;
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

    public List<PlaylistPO> getList() {
        return list;
    }

    public void setList(List<PlaylistPO> list) {
        this.list = list;
    }
}
