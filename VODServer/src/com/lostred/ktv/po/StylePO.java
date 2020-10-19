package com.lostred.ktv.po;

/**
 * 歌曲类型PO
 */
public class StylePO {
    private int styleId;
    private String styleName;

    public StylePO() {
    }

    public StylePO(int styleId, String styleName) {
        this.styleId = styleId;
        this.styleName = styleName;
    }

    @Override
    public String toString() {
        return styleName;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
}
