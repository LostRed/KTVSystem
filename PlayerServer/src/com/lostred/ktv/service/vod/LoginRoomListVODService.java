package com.lostred.ktv.service.vod;

import com.lostred.ktv.dto.vod.EnteredRoomListResponse;
import com.lostred.ktv.dto.vod.LoginResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSRecvThread;
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
import java.util.Set;

/**
 * 点播服务端回应登录房间业务
 */
public class LoginRoomListVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<RoomPO> collection = JSONArray.toCollection(jsonArray, RoomPO.class);
        List<RoomPO> list = new ArrayList<>(collection);
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：当前登录的房间" + list;
        handler.showMessage(message);
        //查询当前Map的房间列表
        Set<Integer> keys = PCRecvThread.getRoomMap().keySet();
        //创建绑定房间的集合
        List<RoomPO> enteredRoomList = new ArrayList<>();
        for (RoomPO roomPO : list) {
            //当前房间不包含已登录的房间时
            if (!keys.contains(roomPO.getRoomId())) {
                //把该房间加到Map集合中
                PCRecvThread.getRoomMap().put(roomPO.getRoomId(), null);
            } else {
                Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
                //如果该房间被占用
                if (socket != null) {
                    //把该房间加入集合
                    enteredRoomList.add(roomPO);
                    //通知播放客户端该房间已经登录
                    LoginResponse loginResponse = new LoginResponse(JsonUtil.SUCCESS, roomPO);
                    PrintWriter pw = new PrintWriter(socket.getOutputStream());
                    pw.println(JsonUtil.toJsonString(loginResponse));
                    pw.flush();
                }
            }
        }
        //回应点播服务端
        EnteredRoomListResponse enteredRoomListResponse = new EnteredRoomListResponse(enteredRoomList);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(enteredRoomListResponse));
        pw.flush();
    }
}
