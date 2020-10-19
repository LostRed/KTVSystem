package com.lostred.ktv.dao;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;

import java.util.List;

public interface InSongDAO {
    /**
     * 按照歌曲PO修改歌曲热度
     *
     * @param songPO 歌曲PO
     */
    void updateSong(SongPO songPO);

    /**
     * 按照歌曲id搜索歌曲
     *
     * @param songId 歌曲id
     * @return 歌曲
     */
    SongPO selectSong(int songId);

    /**
     * 查询所有非删除歌曲的总数
     *
     * @return 歌曲总数
     */
    int countQueryAllSong();

    /**
     * 查询所有非删除歌曲，按照热度降序排序
     *
     * @param limit  限制量
     * @param offset 偏移量
     * @return 歌曲集合
     */
    List<SongPO> queryAllSongOrderByHot(int limit, int offset);

    /**
     * 查询在某个修改时间之后的歌曲
     *
     * @param time 给定的时间字符串
     * @return 歌曲集合
     */
    List<SongPO> querySongAfterTime(String time);

    /**
     * 按照拼音首字母模糊查询某个修改时间之后的歌曲
     *
     * @param keyword 拼音首字母关键字
     * @param time    给定的时间字符串
     * @return 歌曲集合
     */
    List<SongPO> fuzzyQueryUpdateSong(String keyword, String time);

    /**
     * 按照歌手首字母模糊查询某个修改时间之后的歌曲
     *
     * @param keyword 歌手首字母关键字
     * @param time    给定的时间字符串
     * @return 歌曲集合
     */
    List<SongPO> fuzzyQueryUpdateBySinger(String keyword, String time);

    /**
     * 按照歌曲类型查询某个修改时间之后的歌曲
     *
     * @param stylePO 歌曲类型
     * @param time    给定的时间字符串
     * @return 歌曲集合
     */
    List<SongPO> queryUpdateByStyle(StylePO stylePO, String time);
}
