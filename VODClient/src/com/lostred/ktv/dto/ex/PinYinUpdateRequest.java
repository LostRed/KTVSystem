package com.lostred.ktv.dto.ex;

import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求拼音首字母搜索更新的消息
 */
public class PinYinUpdateRequest {
    private String type = JsonUtil.PINYIN_QUERY_UPDATE;
    private String keyword;
    private String time;

    public PinYinUpdateRequest() {
    }

    public PinYinUpdateRequest(String keyword, String time) {
        this.keyword = keyword;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
