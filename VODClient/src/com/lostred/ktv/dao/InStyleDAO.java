package com.lostred.ktv.dao;

import com.lostred.ktv.po.StylePO;

import java.util.List;

public interface InStyleDAO {
    /**
     * 按照歌曲类型PO添加歌曲信息
     *
     * @param stylePO 歌曲类型PO
     */
    void addStyle(StylePO stylePO);

    /**
     * 查询所有歌曲类型
     *
     * @return 歌曲类型集合
     */
    List<StylePO> queryAllStyle();
}
