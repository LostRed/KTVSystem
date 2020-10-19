package com.lostred.ktv.dao;


import com.lostred.ktv.po.RoomPO;

public interface InRoomDAO {
    /**
     * 按照输入的房间对象查询房间
     *
     * @param inputRoomPO 输入的房间对象
     * @return 房间对象
     */
    RoomPO selectRoom(RoomPO inputRoomPO);
}
