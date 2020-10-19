package com.lostred.ktv.service;

import com.lostred.ktv.dto.HotResponse;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.view.VODClientView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点播服务端回应查询歌曲热度排行业务
 */
public class HotService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        HotResponse hotResponse = (HotResponse) JSONObject.toBean(jsonObject, HotResponse.class);
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<SongPO> collection = JSONArray.toCollection(jsonArray, SongPO.class);
        int total = hotResponse.getTotal();
        VODClientView.getVODClientView().getSongInfoPanel().getSongInfoScrollPane().getTable().refreshPage(total);
        List<SongPO> list = new ArrayList<>(collection);
        //显示歌曲热度排行
        VODClientView.getVODClientView().getSongInfoPanel().getSongInfoScrollPane().getTable().refreshHotTable(list);
    }
}
