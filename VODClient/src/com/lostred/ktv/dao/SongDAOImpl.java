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
     * 按照歌曲PO添加歌曲信息
     *
     * @param songPO 歌曲PO
     */
    @Override
    public void addSong(SongPO songPO) {
        String sql = "INSERT INTO t_song VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setInt(1, songPO.getSongId());
            ps.setString(2, songPO.getSongName());
            ps.setInt(3, songPO.getSinger().getSingerId());
            ps.setInt(4, songPO.getStyle().getStyleId());
            ps.setString(5, songPO.getSongInitial());
            ps.setString(6, songPO.getSongTime());
            ps.setString(7, songPO.getMd5());
            ps.setString(8, songPO.getModifyTime());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
    }

    /**
     * 按照歌曲PO修改歌曲信息
     *
     * @param songPO 歌曲PO
     * @return 受影响的记录数
     */
    @Override
    public int updateSong(SongPO songPO) {
        String sql = "UPDATE t_song SET song_name=?,singer_id=?,style_id=?,song_initial=?,modify_time=? WHERE song_id=?";
        PreparedStatement ps = null;
        int total = 0;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setString(1, songPO.getSongName());
            ps.setInt(2, songPO.getSinger().getSingerId());
            ps.setInt(3, songPO.getStyle().getStyleId());
            ps.setString(4, songPO.getSongInitial());
            ps.setString(5, songPO.getModifyTime());
            ps.setInt(6, songPO.getSongId());
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
     */
    @Override
    public void deleteSong(int songId) {
        String sql = "DELETE FROM t_song WHERE song_id=?";
        PreparedStatement ps = null;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setInt(1, songId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
    }

    @Override
    public int countFuzzyQuerySong(String keyword) {
        String sql = "SELECT COUNT(*) FROM v_song_info WHERE song_initial LIKE ?";
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
     * 按照歌手首字母模糊查询歌曲信息的总数
     *
     * @param singerKeyword 歌手首字母关键字
     * @return 歌曲信息总数
     */
    @Override
    public int countFuzzyQuerySongOnSinger(String singerKeyword) {
        String sql = "SELECT COUNT(*) FROM v_song_info WHERE singer_initial LIKE ?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, singerKeyword + "%");
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
     * 按照歌曲类型查询歌曲信息的总数
     *
     * @param stylePO 歌曲类型
     * @return 歌曲信息总数
     */
    @Override
    public int countQuerySong(StylePO stylePO) {
        String sql = "SELECT COUNT(*) FROM v_song_info WHERE style_id=?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, stylePO.getStyleId());
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
     * 按照拼音首字母模糊查询歌曲信息
     *
     * @param keyword 拼音首字母关键字
     * @return 歌曲信息集合
     */
    @Override
    public List<SongPO> fuzzyQuerySongByPage(String keyword, int limit, int offset) {
        String sql = "SELECT * FROM v_song_info WHERE song_initial LIKE ? LIMIT ? OFFSET ?";
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
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime,
                        0, null, 0, null, null, null);
                list.add(songPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }

    @Override
    public List<SongPO> fuzzyQuerySongOnSingerByPage(String singerKeyword, int limit, int offset) {
        String sql = "SELECT * FROM v_song_info WHERE singer_initial LIKE ? LIMIT ? OFFSET ?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, singerKeyword + "%");
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
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime,
                        0, null, 0, null, null, null);
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
     * 按照歌曲类型查询歌曲信息
     *
     * @param stylePO 歌曲类型
     * @return 歌曲信息集合
     */
    @Override
    public List<SongPO> querySongByPage(StylePO stylePO, int limit, int offset) {
        String sql = "SELECT * FROM v_song_info WHERE style_id=? LIMIT ? OFFSET ?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SongPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, stylePO.getStyleId());
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                int songId = rs.getInt("song_id");
                String songName = rs.getString("song_name");
                int singerId = rs.getInt("singer_id");
                String singerName = rs.getString("singer_name");
                String singerInitial = rs.getString("singer_initial");
                String songInitial = rs.getString("song_initial");
                String songTime = rs.getString("song_time");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime,
                        0, null, 0, null, null, null);
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
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                StylePO stylePO = new StylePO(styleId, styleName);
                SongPO songPO = new SongPO(songId, songName, singerPO, stylePO, songInitial, songTime,
                        0, null, 0, null, null, null);
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
     * 查询所有歌曲的最新修改时间
     *
     * @return 时间字符串
     */
    @Override
    public String queryLastTime() {
        String sql = "SELECT MAX(modify_time) FROM v_song_info";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String time = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                time = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return time;
    }
}
