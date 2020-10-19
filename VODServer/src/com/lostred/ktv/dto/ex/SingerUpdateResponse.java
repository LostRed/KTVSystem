package com.lostred.ktv.dto.ex;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 点播服务端回应歌手首字母搜索更新的消息
 */
public class SingerUpdateResponse {
    private String type = JsonUtil.SINGER_QUERY_UPDATE;
    private List<SongPO> list;

    public SingerUpdateResponse() {
    }

    public SingerUpdateResponse(List<SongPO> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SongPO> getList() {
        return list;
    }

    public void setList(List<SongPO> list) {
        this.list = list;
    }
}
