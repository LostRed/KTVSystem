package com.lostred.ktv.dao;

import com.lostred.ktv.po.SingerPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 歌曲信息访问接口实现类
 */
public class SongDAOImpl implements InSongDAO {
    /**
     * 按照歌曲id搜索歌曲
     *
     * @param songId 歌曲id
     * @return 歌曲
     */
    @Override
    public SongPO selectSong(int songId) {
        String sql = "SELECT * FROM v_song_info WHERE song_id=?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        SongPO songPO = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, songId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String songName = rs.getString("song_name");
                int singerId = rs.getInt("singer_id");
                String singerName = rs.getString("singer_name");
                String singerInitial = rs.getString("singer_initial");
                int styleId = rs.getInt("style_id");
                String styleName = rs.getString("style_name");
                String songInitial = rs.getString("song_initial");
                String songTime = rs.getString("song_time");
                int hot = rs.getInt("song_hot");
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, hot, md5, fileSize, filePath, modifyInfo, modifyTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return songPO;
    }
}
