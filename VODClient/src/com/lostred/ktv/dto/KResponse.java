package com.lostred.ktv.dto;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 点播服务端回应K歌的消息
 */
public class KResponse {
    private String type = JsonUtil.K_SONG;
    private PlaylistPO playlistPO;
    private List<PlaylistPO> list;

    public KResponse() {
    }

    public KResponse(PlaylistPO playlistPO, List<PlaylistPO> list) {
        this.playlistPO = playlistPO;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
