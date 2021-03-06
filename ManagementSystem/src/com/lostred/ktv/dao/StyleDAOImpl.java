package com.lostred.ktv.dao;

import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StyleDAOImpl implements InStyleDAO {
    /**
     * 按照歌曲类型PO添加歌曲信息
     *
     * @param stylePO 歌曲类型PO
     * @return 受影响的记录数
     */
    @Override
    public int addStyle(StylePO stylePO) {
        String sql = "INSERT INTO t_style VALUES (NULL,?)";
        PreparedStatement ps = null;
        int total = 0;
        try {
            ps = JdbcUtil.getConnection().prepareStatement(sql);
            ps.setString(1, stylePO.getStyleName());
            total += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, null);
        }
        return total;
    }

    /**
     * 查询所有歌曲类型
     *
     * @return 歌曲类型集合
     */
    @Override
    public List<StylePO> queryAllStyle() {
        String sql = "SELECT * FROM t_style";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<StylePO> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int styleId = rs.getInt("style_id");
                String styleName = rs.getString("style_name");
                StylePO stylePO = new StylePO(styleId, styleName);
                list.add(stylePO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return list;
    }
}
