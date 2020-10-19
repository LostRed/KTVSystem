package com.lostred.ktv.dao;

import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAOImpl implements InRoomDAO {
    /**
     * 按照输入的房间对象查询房间
     *
     * @param inputRoomPO 输入的房间对象
     * @return 房间对象
     */
    @Override
    public RoomPO selectRoom(RoomPO inputRoomPO) {
        String sql = "SELECT * FROM t_room WHERE room_id=? AND room_password=?";
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RoomPO roomPO = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, inputRoomPO.getRoomId());
            ps.setString(2, inputRoomPO.getRoomPassword());
            rs = ps.executeQuery();
            if (rs.next()) {
                roomPO = inputRoomPO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(ps, rs);
        }
        return roomPO;
    }
}
