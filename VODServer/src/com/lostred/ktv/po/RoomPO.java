package com.lostred.ktv.po;

/**
 * 房间PO
 */
public class RoomPO {
    private int roomId;
    private String roomPassword;

    public RoomPO() {
    }

    public RoomPO(int roomId, String roomPassword) {
        this.roomId = roomId;
        this.roomPassword = roomPassword;
    }

    @Override
    public String toString() {
        return Integer.toString(roomId);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }
}
