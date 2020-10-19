package com.lostred.ktv.dto.vod;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 点播服务端回应查询歌曲热度排行的消息
 */
public class HotResponse {
    private String type = JsonUtil.HOT;
    private int total;
    private List<SongPO> list;

    public HotResponse() {
    }

    public HotResponse(int total, List<SongPO> list) {
        this.total = total;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SongPO> getList() {
        return list;
    }

    public void setList(List<SongPO> list) {
        this.list = list;
    }
}
