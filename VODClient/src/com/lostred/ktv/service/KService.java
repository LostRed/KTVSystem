package com.lostred.ktv.service;

import com.lostred.ktv.dto.KResponse;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.view.VODClientView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点播服务端回应K歌业务
 */
public class KService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        KResponse kResponse = (KResponse) JSONObject.toBean(jsonObject, KResponse.class);
        PlaylistPO playlistPO = kResponse.getPlaylistPO();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<PlaylistPO> collection = JSONArray.toCollection(jsonArray, PlaylistPO.class);
        List<PlaylistPO> list = new ArrayList<>(collection);
        //更新缓存中的已点歌曲
        VODClientView.getVODClientView().setPlaylist(list);
        String text = "当前播放歌曲：" + playlistPO.getSongPO().getSinger().getSingerName() + " - " + playlistPO.getSongPO().getSongName();
        //修改正在播放标签
        SwingUtilities.invokeLater(() -> VODClientView.getVODClientView().getLbSong().setText(text));
        //如果窗口处于已点歌曲模式
        if (VODClientView.getVODClientView().getMode() == VODClientView.Mode.PLAYLIST) {
            //显示已点歌曲
            LocalService.getLocalService().refreshPlaylistView();
        }
    }
}
