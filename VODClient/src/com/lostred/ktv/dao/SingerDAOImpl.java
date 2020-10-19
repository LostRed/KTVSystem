package com.lostred.ktv.dao;

import com.lostred.ktv.po.SingerPO;
import com.lostred.ktv.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SingerDAOImpl implements InSingerDAO {
    /**
     * 按照歌手PO添加歌曲信息
     *
     * @param singerPO 歌手PO
     */
    @Override
    public void addSinger(SingerPO singerPO) {
        String sql = "INSERT INTO t_singer VALUES (?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setInt(1, singerPO.getSingerId());
            ps.setString(2, singerPO.getSingerName());
            ps.setString(3, singerPO.getSingerInitial());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
    }

    /**
     * 查询所有歌手
     *
     * @return 歌手集合
     */
    @Override
    public List<SingerPO> queryAllSinger() {
        String sql = "SELECT * FROM t_singer ORDER BY singer_initial";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SingerPO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int singerId = rs.getInt("singer_id");
                String singerName = rs.getString("singer_name");
                String singerInitial = rs.getString("singer_initial");
                SingerPO singerPO = new SingerPO(singerId, singerName, singerInitial);
                list.add(singerPO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }
}
