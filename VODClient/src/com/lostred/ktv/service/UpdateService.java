package com.lostred.ktv.service;

import com.lostred.ktv.controller.RecvFileThread;
import com.lostred.ktv.controller.UpdateThread;
import com.lostred.ktv.dto.UpdateResponse;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.view.ProgressView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点播服务端回应更新数据库业务
 */
public class UpdateService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        UpdateResponse updateResponse = (UpdateResponse) JSONObject.toBean(jsonObject, UpdateResponse.class);
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<SongPO> collection = JSONArray.toCollection(jsonArray, SongPO.class);
        List<SongPO> list = new ArrayList<>(collection);
        long fileSize = updateResponse.getFileSize();
        String ip = updateResponse.getIp();
        int port = updateResponse.getPort();
        //首次登录时
        if (fileSize != 0) {
            //开启文件接收线程
            new RecvFileThread(fileSize, ip, port).start();
            //显示进度条窗口
            SwingUtilities.invokeLater(() -> ProgressView.getProgressView().setVisible(true));
        }
        //返回集合不为空时
        else if (list.size() != 0) {
            //开启更新线程
            new UpdateThread(list, -1).start();
            //显示进度条窗口
            SwingUtilities.invokeLater(() -> ProgressView.getProgressView().setVisible(true));
        }
    }
}
