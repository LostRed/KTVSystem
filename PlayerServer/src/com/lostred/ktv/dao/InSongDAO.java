package com.lostred.ktv.dao;

import com.lostred.ktv.po.SongPO;

/**
 * 歌曲信息访问接口
 */
public interface InSongDAO {
    /**
     * 按照歌曲id搜索歌曲
     *
     * @param songId 歌曲id
     * @return 歌曲
     */
    SongPO selectSong(int songId);
}
