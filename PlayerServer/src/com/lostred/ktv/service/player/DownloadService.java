package com.lostred.ktv.service.player;

import com.lostred.ktv.controller.DownloadFileThread;
import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dto.ex.DownloadRequest;
import com.lostred.ktv.dto.ex.DownloadResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.FileServiceUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * 播放客户端请求下载文件业务
 */
public class DownloadService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        DownloadRequest downloadRequest = (DownloadRequest) JSONObject.toBean(jsonObject, DownloadRequest.class);
        RoomPO roomPO = downloadRequest.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：请求预下载";
        handler.showMessage(message);
        //查询数据库中已点歌曲播放顺序最小的值
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        PlaylistPO playlistPO = playlistDAO.queryPlaylistForemost(roomPO);
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        SongPO songPO = songDAO.selectSong(playlistPO.getSongPO().getSongId());
        //查询歌曲文件信息
        String md5 = songPO.getMd5();
        long fileSize = songPO.getFileSize();
        String filePath = songPO.getFilePath();
        //分配ip和端口
        ServerSocket serverSocket = FileServiceUtil.getFileServerUtil().getServerSocket();
        String ip = FileServiceUtil.getFileServerUtil().getIp();
        int port = FileServiceUtil.getFileServerUtil().getPort();
        //开启发送下载文件线程
        new DownloadFileThread(serverSocket, filePath).start();
        //回应播放客户端
        DownloadResponse downloadResponse = new DownloadResponse(md5, fileSize, ip, port);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(downloadResponse));
        pw.flush();
    }
}
