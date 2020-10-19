package com.lostred.ktv.dto.ex;

import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.util.JsonUtil;

/**
 * 点播客户端请求歌曲类型搜索更新的消息
 */
public class StyleUpdateRequest {
    private String type = JsonUtil.STYLE_QUERY_UPDATE;
    private StylePO stylePO;
    private String time;

    public StyleUpdateRequest() {
    }

    public StyleUpdateRequest(StylePO stylePO, String time) {
        this.stylePO = stylePO;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StylePO getStylePO() {
        return stylePO;
    }

    public void setStylePO(StylePO stylePO) {
        this.stylePO = stylePO;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
