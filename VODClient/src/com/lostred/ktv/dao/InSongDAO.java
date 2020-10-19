package com.lostred.ktv.dao;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;

import java.util.List;

public interface InSongDAO {
    /**
     * 按照歌曲PO添加歌曲信息
     *
     * @param songPO 歌曲PO
     */
    void addSong(SongPO songPO);

    /**
     * 按照歌曲PO修改歌曲信息
     *
     * @param songPO 歌曲PO
     * @return 受影响的记录数
     */
    int updateSong(SongPO songPO);

    /**
     * 按照歌曲PO的id删除歌曲
     *
     * @param songId 歌曲PO的id
     */
    void deleteSong(int songId);

    /**
     * 按照拼音首字母模糊查询歌曲信息的总数
     *
     * @param keyword 拼音首字母关键字
     * @return 歌曲信息总数
     */
    int countFuzzyQuerySong(String keyword);

    /**
     * 按照歌手首字母模糊查询歌曲信息的总数
     *
     * @param singerKeyword 歌手首字母关键字
     * @return 歌曲信息总数
     */
    int countFuzzyQuerySongOnSinger(String singerKeyword);

    /**
     * 按照歌曲类型查询歌曲信息的总数
     *
     * @param stylePO 歌曲类型
     * @return 歌曲信息总数
     */
    int countQuerySong(StylePO stylePO);

    /**
     * 按照拼音首字母模糊查询歌曲信息
     *
     * @param keyword 拼音首字母关键字
     * @return 歌曲信息集合
     */
    List<SongPO> fuzzyQuerySongByPage(String keyword, int limit, int offset);

    /**
     * 按照歌手首字母关键字模糊查询歌曲信息
     *
     * @param singerKeyword 歌手首字母关键字
     * @return 歌曲信息集合
     */
    List<SongPO> fuzzyQuerySongOnSingerByPage(String singerKeyword, int limit, int offset);

    /**
     * 按照歌曲类型查询歌曲信息
     *
     * @param stylePO 歌曲类型
     * @return 歌曲信息集合
     */
    List<SongPO> querySongByPage(StylePO stylePO, int limit, int offset);

    /**
     * 查询所有歌曲
     *
     * @return 歌曲集合
     */
    List<SongPO> queryAllSong();

    /**
     * 查询所有歌曲的最新修改时间
     *
     * @return 时间字符串
     */
    String queryLastTime();
}
