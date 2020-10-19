package com.lostred.ktv.service;

import com.lostred.ktv.controller.DownloadFileThread;
import com.lostred.ktv.controller.PlayThread;
import com.lostred.ktv.controller.RecvFileThread;
import com.lostred.ktv.dto.PlayRequest;
import com.lostred.ktv.dto.PlayResponse;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.util.FileServiceUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.MD5Util;
import com.lostred.ktv.view.PlayerClientView;
import com.lostred.ktv.view.ProgressView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 播放服务端请求播放歌曲业务
 */
public class PlayService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PlayRequest playRequest = (PlayRequest) JSONObject.toBean(jsonObject, PlayRequest.class);
        PlaylistPO playlistPO = playRequest.getPlaylistPO();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<PlaylistPO> collection = JSONArray.toCollection(jsonArray, PlaylistPO.class);
        List<PlaylistPO> list = new ArrayList<>(collection);
        String md5 = playRequest.getMd5();
        String filePath = playRequest.getFilePath();
        long fileSize = playRequest.getFileSize();
        //查询本地文件
        File file = new File("PlayerClient/music/" + md5);
        //写入配置文件
        ConfigUtil.writeProperties("songName", playlistPO.getSongPO().getSongName());
        ConfigUtil.writeProperties("singerName", playlistPO.getSongPO().getSinger().getSingerName());
        ConfigUtil.writeProperties("md5", md5);
        //如果文件存在
        if (file.exists()) {
            //文件md5码正确时
            if (MD5Util.getMD5(file).equals(md5)) {
                DownloadFileThread.DONE = true;
            }
            //开启等待播放线程
            new PlayThread(file).start();
        } else {
            //分配ip和端口
            ServerSocket serverSocket = FileServiceUtil.getFileServerUtil().getServerSocket();
            String ip = FileServiceUtil.getFileServerUtil().getIp();
            int port = FileServiceUtil.getFileServerUtil().getPort();
            //开启接收文件线程
            new RecvFileThread(serverSocket, md5, fileSize).start();
            //通知服务端
            PlayResponse playResponse = new PlayResponse(filePath, ip, port);
            Client.getClient().sendMessage(JsonUtil.toJsonString(playResponse));
            //弹出进度条
            ProgressView.getProgressView().setVisible(true);
        }
        //更新缓存中的已点歌曲
        PlayerClientView.getPlayerClientView().setPlaylist(list);
        //更新界面
        SwingUtilities.invokeLater(() -> {
            //显示当前播放的歌曲
            PlayerClientView.getPlayerClientView().showPlayingSong(playlistPO);
            //显示下一首歌曲
            PlayerClientView.getPlayerClientView().showNextSong();
        });
        //恢复切歌按钮
        PlayerClientView.getPlayerClientView().getBtnNext().setEnabled(true);
    }
}
