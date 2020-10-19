package com.lostred.ktv.service.vod;

import com.lostred.ktv.dto.player.PlaylistResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 点播服务端回应查询播放列表业务
 */
public class PlaylistVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PlaylistResponse playlistResponse = (PlaylistResponse) JSONObject.toBean(jsonObject, PlaylistResponse.class);
        RoomPO roomPO = playlistResponse.getRoomPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：更新播放列表";
        handler.showMessage(message);
        Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
        //如果该房间被占用
        if (socket != null) {
            //通知客户端更新播放列表
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(JsonUtil.toJsonString(playlistResponse));
            pw.flush();
        }
    }
}
