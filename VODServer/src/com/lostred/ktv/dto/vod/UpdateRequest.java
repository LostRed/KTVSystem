package com.lostred.ktv.dto.vod;

import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求更新数据库的消息
 */
public class UpdateRequest {
    private String type = JsonUtil.UPDATE_DATABASE;
    private String time;

    public UpdateRequest() {
    }

    public UpdateRequest(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
