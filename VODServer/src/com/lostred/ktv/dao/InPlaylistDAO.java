package com.lostred.ktv.dao;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;

import java.util.List;

public interface InPlaylistDAO {
    /**
     * 按照房间在已点歌曲表中新增歌曲
     *
     * @param roomPO 房间
     * @param songPO 歌曲
     */
    void addPlaylist(RoomPO roomPO, SongPO songPO);

    /**
     * 按照已点歌曲在已点歌曲表中修改歌曲播放顺序
     *
     * @param playOrder  播放顺序
     * @param playlistPO 已点歌曲
     */
    void updatePlaylist(int playOrder, PlaylistPO playlistPO);

    /**
     * 按照已点歌曲在已点歌曲表中删除歌曲
     *
     * @param playlistPO 已点歌曲
     */
    void deletePlaylist(PlaylistPO playlistPO);

    /**
     * 按照房间在已点歌曲表中删除所有歌曲
     *
     * @param roomPO 房间
     */
    void deletePlaylist(RoomPO roomPO);

    /**
     * 在所有房间已点歌曲表中查询播放顺序最小的歌曲
     *
     * @return 播放顺序最小的已点歌曲
     */
    PlaylistPO queryPlaylistForemostAllRoom();

    /**
     * 按照房间查询已点歌曲
     *
     * @param roomPO 房间对象
     * @return 已点歌曲集合
     */
    List<PlaylistPO> queryPlaylist(RoomPO roomPO);
}
