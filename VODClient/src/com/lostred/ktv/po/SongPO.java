package com.lostred.ktv.po;

/**
 * 歌曲PO
 */
public class SongPO {
    private int songId;
    private String songName;
    private SingerPO singerPO;
    private StylePO stylePO;
    private String songInitial;
    private String songTime;
    private int songHot;
    private String md5;
    private long fileSize;
    private String filePath;
    private String modifyInfo;
    private String modifyTime;

    public SongPO() {
    }

    public SongPO(int songId, String songName, SingerPO singerPO, StylePO stylePO, String songInitial, String songTime, int songHot, String md5, long fileSize, String filePath, String modifyInfo, String modifyTime) {
        this.songId = songId;
        this.songName = songName;
        this.singerPO = singerPO;
        this.stylePO = stylePO;
        this.songInitial = songInitial;
        this.songTime = songTime;
        this.songHot = songHot;
        this.md5 = md5;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.modifyInfo = modifyInfo;
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return songName;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public SingerPO getSinger() {
        return singerPO;
    }

    public void setSinger(SingerPO singerPO) {
        this.singerPO = singerPO;
    }

    public StylePO getStyle() {
        return stylePO;
    }

    public void setStyle(StylePO stylePO) {
        this.stylePO = stylePO;
    }

    public String getSongInitial() {
        return songInitial;
    }

    public void setSongInitial(String songInitial) {
        this.songInitial = songInitial;
    }

    public String getSongTime() {
        return songTime;
    }

    public void setSongTime(String songTime) {
        this.songTime = songTime;
    }

    public int getSongHot() {
        return songHot;
    }

    public void setSongHot(int songHot) {
        this.songHot = songHot;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getModifyInfo() {
        return modifyInfo;
    }

    public void setModifyInfo(String modifyInfo) {
        this.modifyInfo = modifyInfo;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
