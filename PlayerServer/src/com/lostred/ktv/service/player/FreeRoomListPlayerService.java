package com.lostred.ktv.service.player;

import com.lostred.ktv.dto.player.FreeRoomListResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 播放客户端请求查询空闲房间业务
 */
public class FreeRoomListPlayerService implements InPlayerService {
    @Override
    public void doService(PCRecvThread handler) {
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：请求查询空闲房间";
        handler.showMessage(message);
        //查询所有空闲的房间
        Set<Integer> keys = PCRecvThread.getRoomMap().keySet();
        List<RoomPO> list = new ArrayList<>();
        for (Integer roomId : keys) {
            Socket socket = PCRecvThread.getRoomMap().get(roomId);
            //如果房间为空闲状态
            if (socket == null) {
                RoomPO roomPO = new RoomPO(roomId, null);
                list.add(roomPO);
            }
        }
        //将房间列表排序
        list.sort(Comparator.comparingInt(RoomPO::getRoomId));
        //回应播放客户端
        FreeRoomListResponse freeRoomListResponse = new FreeRoomListResponse(list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(freeRoomListResponse));
        pw.flush();
    }
}
