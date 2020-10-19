package com.lostred.ktv.dto;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求K歌的消息
 */
public class KRequest {
    private String type = JsonUtil.K_SONG;
    private PlaylistPO playlistPO;

    public KRequest() {
    }

    public KRequest(PlaylistPO playlistPO) {
        this.playlistPO = playlistPO;
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
}
