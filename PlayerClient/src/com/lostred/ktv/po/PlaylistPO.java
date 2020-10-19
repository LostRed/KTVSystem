package com.lostred.ktv.po;

/**
 * 歌曲播放列表PO
 */
public class PlaylistPO {
    private int playOrder;
    private RoomPO roomPO;
    private SongPO songPO;

    public PlaylistPO() {
    }

    public PlaylistPO(int playOrder, RoomPO roomPO, SongPO songPO) {
        this.playOrder = playOrder;
        this.roomPO = roomPO;
        this.songPO = songPO;
    }

    @Override
    public String toString() {
        return songPO.getSongName();
    }

    public int getPlayOrder() {
        return playOrder;
    }

    public void setPlayOrder(int playOrder) {
        this.playOrder = playOrder;
    }

    public RoomPO getRoomPO() {
        return roomPO;
    }

    public void setRoomPO(RoomPO roomPO) {
        this.roomPO = roomPO;
    }

    public SongPO getSongPO() {
        return songPO;
    }

    public void setSongPO(SongPO songPO) {
        this.songPO = songPO;
    }
}
