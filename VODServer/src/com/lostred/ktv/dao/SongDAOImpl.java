package com.lostred.ktv.dao;

import com.lostred.ktv.po.SingerPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAOImpl implements InSongDAO {
    /**
     * 按照歌曲PO修改歌曲热度
     *
     * @param songPO 歌曲PO
     */
    @Override
    public void updateSong(SongPO songPO) {
        String sql = "UPDATE t_song SET song_hot=? WHERE song_id=?";
        PreparedStatement ps = null;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setInt(1, songPO.getSongHot());
            ps.setInt(2, songPO.getSongId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
    }

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

    /**
     * 查询所有非删除歌曲的总数
     *
     * @return 歌曲总数
     */
    @Override
    public int countQueryAllSong() {
        String sql = "SELECT COUNT(*) FROM v_song_info WHERE modify_info!='delete'";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return total;
    }

    /**
     * 查询所有非删除歌曲，按照热度降序排序
     *
     * @param limit  限制量
     * @param offset 偏移量
     * @return 歌曲集合
     */
    @Override
    public List<SongPO> queryAllSongOrderByHot(int limit, int offset) {
        String sql = "SELECT * FROM v_song_info WHERE modify_info!='delete' ORDER BY song_hot DESC LIMIT ? OFFSET ?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
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
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, songHot, md5, fileSize, filePath, modifyInfo, modifyTime);
                list.add(songPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }

    /**
     * 查询在某个修改时间之后的歌曲
     *
     * @param time 给定的时间字符串
     * @return 歌曲集合
     */
    @Override
    public List<SongPO> querySongAfterTime(String time) {
        String sql = "SELECT * FROM v_song_info WHERE modify_time>?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, time);
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
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, songHot, md5, fileSize, filePath, modifyInfo, modifyTime);
                list.add(songPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }

    /**
     * 按照拼音首字母模糊查询某个修改时间之后的歌曲
     *
     * @param keyword 拼音首字母关键字
     * @param time    给定的时间字符串
     * @return 歌曲集合
     */
    @Override
    public List<SongPO> fuzzyQueryUpdateSong(String keyword, String time) {
        String sql = "SELECT * FROM v_song_info WHERE song_initial LIKE ? AND modify_time>?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword + "%");
            ps.setString(2, time);
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
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, songHot, md5, fileSize, filePath, modifyInfo, modifyTime);
                list.add(songPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }

    /**
     * 按照歌手首字母模糊查询某个修改时间之后的歌曲
     *
     * @param keyword 歌手首字母关键字
     * @param time    给定的时间字符串
     * @return 歌曲集合
     */
    @Override
    public List<SongPO> fuzzyQueryUpdateBySinger(String keyword, String time) {
        String sql = "SELECT * FROM v_song_info WHERE singer_initial LIKE ? AND modify_time>?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword + "%");
            ps.setString(2, time);
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
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, songHot, md5, fileSize, filePath, modifyInfo, modifyTime);
                list.add(songPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }

    /**
     * 按照歌曲类型查询某个修改时间之后的歌曲
     *
     * @param stylePO 歌曲类型
     * @param time    给定的时间字符串
     * @return 歌曲集合
     */
    @Override
    public List<SongPO> queryUpdateByStyle(StylePO stylePO, String time) {
        String sql = "SELECT * FROM v_song_info WHERE style_id=? AND modify_time>?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, stylePO.getStyleId());
            ps.setString(2, time);
            rs = ps.executeQuery();
            while (rs.next()) {
                int songId = rs.getInt("song_id");
                String songName = rs.getString("song_name");
                int singerId = rs.getInt("singer_id");
                String singerName = rs.getString("singer_name");
                String singerInitial = rs.getString("singer_initial");
                String songInitial = rs.getString("song_initial");
                String songTime = rs.getString("song_time");
                int songHot = rs.getInt("song_hot");
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, songHot, md5, fileSize, filePath, modifyInfo, modifyTime);
                list.add(songPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }
}
