package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.view.VODClientView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点播服务端回应删除歌曲业务
 */
public class DeleteService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<PlaylistPO> collection = JSONArray.toCollection(jsonArray, PlaylistPO.class);
        List<PlaylistPO> list = new ArrayList<>(collection);
        //更新缓存中的已点歌曲
        VODClientView.getVODClientView().setPlaylist(list);
        //如果窗口处于已点歌曲模式
        if (VODClientView.getVODClientView().getMode() == VODClientView.Mode.PLAYLIST) {
            //显示已点歌曲
            LocalService.getLocalService().refreshPlaylistView();
        }
    }
}
