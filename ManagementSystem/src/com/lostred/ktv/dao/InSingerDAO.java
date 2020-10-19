package com.lostred.ktv.dao;

import com.lostred.ktv.po.SingerPO;

import java.util.List;

public interface InSingerDAO {
    /**
     * 按照歌手PO添加歌曲信息
     *
     * @param singerPO 歌手PO
     * @return 受影响的记录数
     */
    int addSinger(SingerPO singerPO);

    /**
     * 查询所有歌手
     *
     * @return 歌手集合
     */
    List<SingerPO> queryAllSinger();
}
