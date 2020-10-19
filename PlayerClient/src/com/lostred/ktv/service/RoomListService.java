package com.lostred.ktv.service;

import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.view.SelectRoomView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 播放服务端回应查询空闲房间业务
 */
public class RoomListService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<RoomPO> collection = JSONArray.toCollection(jsonArray, RoomPO.class);
        List<RoomPO> list = new ArrayList<>(collection);
        //设置房间列表
        SelectRoomView.getSelectRoomView().setRoomPOList(list);
        //更新界面
        SwingUtilities.invokeLater(() -> {
            SelectRoomView.getSelectRoomView().refreshComboBox();
        });
    }
}
