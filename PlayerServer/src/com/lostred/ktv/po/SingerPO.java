package com.lostred.ktv.po;

/**
 * 歌手PO
 */
public class SingerPO {
    private int singerId;
    private String singerName;
    private String singerInitial;

    public SingerPO() {
    }

    public SingerPO(int singerId, String singerName, String singerInitial) {
        this.singerId = singerId;
        this.singerName = singerName;
        this.singerInitial = singerInitial;
    }

    @Override
    public String toString() {
        return singerName;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSingerInitial() {
        return singerInitial;
    }

    public void setSingerInitial(String singerInitial) {
        this.singerInitial = singerInitial;
    }
}
