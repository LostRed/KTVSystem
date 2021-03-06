package com.lostred.ktv.dao;

import com.lostred.ktv.po.*;
import com.lostred.ktv.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 播放列表访问接口实现类
 */
public class PlaylistDAOImpl implements InPlaylistDAO {
    /**
     * 按照已点歌曲在已点歌曲表中删除歌曲
     *
     * @param playlistPO 已点歌曲
     */
    @Override
    public void deletePlaylist(PlaylistPO playlistPO) {
        String sql = "DELETE FROM t_playlist WHERE play_order=?";
        PreparedStatement ps = null;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setInt(1, playlistPO.getPlayOrder());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
    }

    /**
     * 按照房间在已点歌曲表中查询播放顺序最小的歌曲，即最先播放的歌曲
     *
     * @param roomPO 房间
     * @return 播放顺序最小的已点歌曲
     */
    @Override
    public PlaylistPO queryPlaylistForemost(RoomPO roomPO) {
        String sql = "SELECT song_id,song_name,singer_id,singer_name,singer_initial,style_id,style_name," +
                "song_initial,song_time,song_hot,room_id,MIN(play_order) play_order FROM v_playlist WHERE room_id=?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        PlaylistPO playlistPO = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, roomPO.getRoomId());
            rs = ps.executeQuery();
            if (rs.next()) {
                int songId = rs.getInt("song_id");
                String songName = rs.getString("song_name");
                int singerId = rs.getInt("singer_id");
                String singerName = rs.getString("singer_name");
                String singerInitial = rs.getString("singer_initial");
                int styleId = rs.getInt("style_id");
                String styleName = rs.getString("style_name");
                String songInitial = rs.getString("song_initial");
                String songTime = rs.getString("song_time");
                int songHot = rs.getInt("song_hot");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, songHot,
                        null, 0, null, null, null);
                int playOrder = rs.getInt("play_order");
                playlistPO = new PlaylistPO(playOrder, roomPO, songPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return playlistPO;
    }

    /**
     * 按照房间查询已点歌曲
     *
     * @param roomPO 房间对象
     * @return 已点歌曲集合
     */
    @Override
    public List<PlaylistPO> queryPlaylist(RoomPO roomPO) {
        String sql = "SELECT * FROM v_playlist WHERE room_id=? ORDER BY play_order";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PlaylistPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, roomPO.getRoomId());
            rs = ps.executeQuery();
            while (rs.next()) {
                int songId = rs.getInt("song_id");
                String songName = rs.getString("song_name");
                int singerId = rs.getInt("singer_id");
                String singerName = rs.getString("singer_name");
                String singerInitial = rs.getString("singer_initial");
                int styleId = rs.getInt("style_id");
                String styleName = rs.getString("style_name");
                String songInitial = rs.getString("song_initial");
                String songTime = rs.getString("song_time");
                int songHot = rs.getInt("song_hot");
                int playOrder = rs.getInt("play_order");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime,
                        songHot, null, 0, null, null, null);
                PlaylistPO playlistPO = new PlaylistPO(playOrder, roomPO, songPO);
                list.add(playlistPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }
}
