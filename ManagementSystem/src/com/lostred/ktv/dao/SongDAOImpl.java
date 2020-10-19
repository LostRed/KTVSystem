package com.lostred.ktv.dao;

import com.lostred.ktv.po.SingerPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SongDAOImpl implements InSongDAO {
    /**
     * 按照歌曲PO添加歌曲信息
     *
     * @param songPO 歌曲PO
     * @return 受影响的记录数
     */
    @Override
    public int addSong(SongPO songPO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        String sql = "INSERT INTO t_song (song_name,singer_id,style_id,song_initial,song_time,song_hot," +
                "md5,file_size,file_path,modify_info,modify_time) VALUES (?,0,0,?,?,0,?,?,?,'new',?)";
        PreparedStatement ps = null;
        int total = 0;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setString(1, songPO.getSongName());
            ps.setString(2, songPO.getSongInitial());
            ps.setString(3, songPO.getSongTime());
            ps.setString(4, songPO.getMd5());
            ps.setLong(5, songPO.getFileSize());
            ps.setString(6, songPO.getFilePath());
            ps.setString(7, nowTime);
            total += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
        return total;
    }

    /**
     * 按照歌曲PO修改歌曲信息
     *
     * @param songPO 歌曲PO
     * @return 受影响的记录数
     */
    @Override
    public int updateSong(SongPO songPO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        String sql = "UPDATE t_song SET song_name=?,singer_id=?,style_id=?,song_initial=?,modify_info=?,modify_time=? WHERE song_id=?";
        PreparedStatement ps = null;
        int total = 0;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setString(1, songPO.getSongName());
            ps.setInt(2, songPO.getSinger().getSingerId());
            ps.setInt(3, songPO.getStyle().getStyleId());
            ps.setString(4, songPO.getSongInitial());
            ps.setString(5, songPO.getModifyInfo());
            ps.setString(6, nowTime);
            ps.setInt(7, songPO.getSongId());
            total += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
        return total;
    }

    /**
     * 按照歌曲PO的id删除歌曲
     *
     * @param songId 歌曲PO的id
     * @return 受影响的记录数
     */
    @Override
    public int deleteSong(int songId) {
        String sql = "DELETE FROM t_song WHERE song_id=?";
        PreparedStatement ps = null;
        int total = 0;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setInt(1, songId);
            total += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
        return total;
    }

    /**
     * 按照拼音首字母模糊查询歌曲信息的总数
     *
     * @param keyword 拼音首字母关键字
     * @return 歌曲信息总数
     */
    @Override
    public int countFuzzyQuerySong(String keyword) {
        String sql = "SELECT COUNT(*) FROM v_song_info WHERE song_initial LIKE ? AND modify_info!='delete'";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword + "%");
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
     * 按照拼音首字母和歌曲类型模糊查询歌曲信息的总数
     *
     * @param keyword 拼音首字母关键字
     * @param stylePO 歌曲类型
     * @return 歌曲信息总数
     */
    @Override
    public int countFuzzyQuerySong(String keyword, StylePO stylePO) {
        String sql = "SELECT COUNT(*) FROM v_song_info WHERE song_initial LIKE ? AND modify_info!='delete' AND style_id=?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword + "%");
            ps.setInt(2, stylePO.getStyleId());
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
     * 按照拼音首字母分页模糊查询歌曲信息
     *
     * @param keyword 拼音首字母关键字
     * @param limit   限制量
     * @param offset  偏移量
     * @return 歌曲信息集合
     */
    @Override
    public List<SongPO> fuzzyQuerySongByPage(String keyword, int limit, int offset) {
        String sql = "SELECT * FROM v_song_info WHERE song_initial LIKE ? AND modify_info!='delete' LIMIT ? OFFSET ?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword + "%");
            ps.setInt(2, limit);
            ps.setInt(3, offset);
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
                int hot = rs.getInt("song_hot");
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, hot, md5, fileSize, filePath, modifyInfo, modifyTime);
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
     * 按照拼音首字母和歌曲类型分页模糊查询歌曲信息
     *
     * @param keyword 拼音首字母关键字
     * @param stylePO 歌曲类型
     * @param limit   限制量
     * @param offset  偏移量
     * @return 歌曲信息集合
     */
    @Override
    public List<SongPO> fuzzyQuerySongByPage(String keyword, StylePO stylePO, int limit, int offset) {
        String sql = "SELECT * FROM v_song_info WHERE song_initial LIKE ? AND modify_info!='delete' AND style_id=? LIMIT ? OFFSET ?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword + "%");
            ps.setInt(2, stylePO.getStyleId());
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                int songId = rs.getInt("song_id");
                String songName = rs.getString("song_name");
                int singerId = rs.getInt("singer_id");
                String singerName = rs.getString("singer_name");
                String singerInitial = rs.getString("singer_initial");
                String songInitial = rs.getString("song_initial");
                String songTime = rs.getString("song_time");
                int hot = rs.getInt("song_hot");
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, hot, md5, fileSize, filePath, modifyInfo, modifyTime);
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
     * 查询所有歌曲
     *
     * @return 歌曲集合
     */
    @Override
    public List<SongPO> queryAllSong() {
        String sql = "SELECT * FROM v_song_info";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
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
                int hot = rs.getInt("song_hot");
                String md5 = rs.getString("md5");
                long fileSize = rs.getLong("file_size");
                String filePath = rs.getString("file_path");
                String modifyInfo = rs.getString("modify_info");
                String modifyTime = rs.getString("modify_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime, hot, md5, fileSize, filePath, modifyInfo, modifyTime);
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
