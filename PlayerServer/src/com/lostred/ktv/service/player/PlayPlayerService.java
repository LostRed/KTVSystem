package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.PlayResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.controller.SendFileThread;
import net.sf.json.JSONObject;

/**
 * 播放客户端回应播放歌曲业务
 */
public class PlayPlayerService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PlayResponse playResponse = (PlayResponse) JSONObject.toBean(jsonObject, PlayResponse.class);
        String ip = playResponse.getIp();
        int port = playResponse.getPort();
        String filePath = playResponse.getFilePath();
        //开启发送文件线程
        new SendFileThread(ip, port, filePath).start();
    }
}
