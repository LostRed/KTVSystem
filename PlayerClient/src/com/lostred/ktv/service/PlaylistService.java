package com.lostred.ktv.service;

import com.lostred.ktv.controller.DownloadFileThread;
import com.lostred.ktv.dto.NextRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.PlayerClientView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 播放服务端回应查询播放列表业务
 */
public class PlaylistService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<PlaylistPO> collection = JSONArray.toCollection(jsonArray, PlaylistPO.class);
        List<PlaylistPO> list = new ArrayList<>(collection);
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        //更新缓存中的已点歌曲
        pcv.setPlaylist(list);
        //显示下一首歌曲
        SwingUtilities.invokeLater(pcv::showNextSong);
        //多媒体对象为空且播放列表不为空时
        if (pcv.getPlayer() == null && pcv.getPlaylist().size() != 0) {
            //重置预下载开始状态
            DownloadFileThread.START = false;
            //请求切歌业务
            NextRequest nextRequest = new NextRequest(pcv.getRoomPO());
            Client.getClient().sendMessage(JsonUtil.toJsonString(nextRequest));
        }
    }
}
