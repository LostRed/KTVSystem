package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.EnterResponse;
import com.lostred.ktv.net.PSRecvThread;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点播服务端回应占用房间业务
 */
public class EnteredRoomListPlayerService implements InPlayerService {
    @Override
    public void doService(PSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<RoomPO> collection = JSONArray.toCollection(jsonArray, RoomPO.class);
        List<RoomPO> list = new ArrayList<>(collection);
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：当前占用的房间" + list;
        handler.showMessage(message);
        //查询当前Map的房间列表
        for (RoomPO roomPO : list) {
            Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
            //如果该房间已登录
            if (socket != null) {
                //通知播放客户端该房间已被占用
                EnterResponse enterResponse = new EnterResponse(roomPO);
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(JsonUtil.toJsonString(enterResponse));
                pw.flush();
            }
        }
    }
}
