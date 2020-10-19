package com.lostred.ktv.service;

import com.lostred.ktv.dto.PickResponse;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.view.VODClientView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点播服务端回应点歌业务
 */
public class PickService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PickResponse pickResponse = (PickResponse) JSONObject.toBean(jsonObject, PickResponse.class);
        SongPO songPO = pickResponse.getSongPO();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<PlaylistPO> collection = JSONArray.toCollection(jsonArray, PlaylistPO.class);
        List<PlaylistPO> list = new ArrayList<>(collection);
        //更新缓存中的已点歌曲
        VODClientView.getVODClientView().setPlaylist(list);
        //弹窗提示
        JOptionPane.showMessageDialog(VODClientView.getVODClientView(),
                "已点歌曲：《" + songPO.getSinger().getSingerName() + " - " + songPO.getSongName() + "》！");
    }
}
