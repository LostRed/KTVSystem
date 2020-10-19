package com.lostred.ktv.service.ex;

import com.lostred.ktv.controller.UpdateThread;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.service.InService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点播服务端回应类型搜索更新业务
 */
public class StyleUpdateService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<SongPO> collection = JSONArray.toCollection(jsonArray, SongPO.class);
        List<SongPO> list = new ArrayList<>(collection);
        //返回集合不为空时
        if (list.size() != 0) {
            //开启更新线程
            new UpdateThread(list, 2).start();
        }
    }
}
