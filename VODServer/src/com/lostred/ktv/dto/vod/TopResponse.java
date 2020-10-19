package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 点播服务端回应置顶歌曲的消息
 */
public class TopResponse {
    private String type = JsonUtil.TOP_SONG;
    private List<PlaylistPO> list;

    public TopResponse() {
    }

    public TopResponse(List<PlaylistPO> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PlaylistPO> getList() {
        return list;
    }

    public void setList(List<PlaylistPO> list) {
        this.list = list;
    }
}
