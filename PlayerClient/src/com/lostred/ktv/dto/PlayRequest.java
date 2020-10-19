package com.lostred.ktv.dto;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.util.JsonUtil;

import java.util.List;

/**
 * 播放服务端请求播放歌曲的消息
 */
public class PlayRequest {
    private String type = JsonUtil.PLAY;
    private PlaylistPO playlistPO;
    private List<PlaylistPO> list;
    private String md5;
    private long fileSize;
    private String filePath;

    public PlayRequest() {
    }

    public PlayRequest(PlaylistPO playlistPO, List<PlaylistPO> list, String md5, long fileSize, String filePath) {
        this.playlistPO = playlistPO;
        this.list = list;
        this.md5 = md5;
        this.fileSize = fileSize;
        this.filePath = filePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PlaylistPO getPlaylistPO() {
        return playlistPO;
    }

    public void setPlaylistPO(PlaylistPO playlistPO) {
        this.playlistPO = playlistPO;
    }

    public List<PlaylistPO> getList() {
        return list;
    }

    public void setList(List<PlaylistPO> list) {
        this.list = list;
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
}
