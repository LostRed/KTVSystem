package com.lostred.ktv.dao;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;

import java.util.List;

/**
 * 播放列表访问接口
 */
public interface InPlaylistDAO {
    /**
     * 按照已点歌曲在已点歌曲表中删除歌曲
     *
     * @param playlistPO 已点歌曲
     */
    void deletePlaylist(PlaylistPO playlistPO);

    /**
     * 按照房间在已点歌曲表中查询播放顺序最小的歌曲，即最先播放的歌曲
     *
     * @param roomPO 房间
     * @return 播放顺序最小的已点歌曲
     */
    PlaylistPO queryPlaylistForemost(RoomPO roomPO);

    /**
     * 按照房间查询已点歌曲
     *
     * @param roomPO 房间对象
     * @return 已点歌曲集合
     */
    List<PlaylistPO> queryPlaylist(RoomPO roomPO);
}
