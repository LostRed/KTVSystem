package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InRoomDAO;
import com.lostred.ktv.dto.vod.LoginRequest;
import com.lostred.ktv.dto.vod.LoginResponse;
import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.MD5Util;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

/**
 * 点播客户端请求登录房间业务
 */
public class LoginVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        LoginRequest loginRequest = (LoginRequest) JSONObject.toBean(jsonObject, LoginRequest.class);
        RoomPO inputRoomPO = loginRequest.getRoomPO();
        String password = inputRoomPO.getRoomPassword();
        try {
            //密码转换为加密密码
            password = MD5Util.EncoderByMd5(password);
            inputRoomPO.setRoomPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：请求登录";
        handler.showMessage(message);
        //根据输入的房间对象查询数据库找到对应的房间
        InRoomDAO roomDAO = (InRoomDAO) DaoFactory.getDAO("InRoomDAO");
        RoomPO roomPO = roomDAO.selectRoom(inputRoomPO);
        //房间不为空的情况
        if (roomPO != null) {
            Socket socket = VCRecvThread.getRoomMap().get(roomPO.getRoomId());
            //如果该房间处于登录状态
            if (socket != null) {
                //回应点播客户端该房间已登录
                LoginResponse loginResponse = new LoginResponse(JsonUtil.FAIL, roomPO);
                PrintWriter pw = handler.getPw();
                pw.println(JsonUtil.toJsonString(loginResponse));
                pw.flush();
            } else {
                //将房间号和对应端口保存到Map集合
                VCRecvThread.getRoomMap().put(roomPO.getRoomId(), handler.getSocket());
                VCRecvThread.getClientSocketMap().put(handler.getSocket(), roomPO.getRoomId());
                //回应点播客户端登录成功
                LoginResponse loginResponse = new LoginResponse(JsonUtil.SUCCESS, roomPO);
                PrintWriter pw = handler.getPw();
                pw.println(JsonUtil.toJsonString(loginResponse));
                pw.flush();
                //显示消息
                message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：登录成功";
                handler.showMessage(message);
                //如果连接上播放服务端
                if (PSClient.getPSClient().isConnected()) {
                    //向播放服务端发送消息通知该房间已登录，并询问该房间是否被占用
                    PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(loginResponse));
                }
            }
        } else {
            //回应点播客户端没有找到该房间
            LoginResponse loginResponse = new LoginResponse(JsonUtil.FAIL, null);
            PrintWriter pw = handler.getPw();
            pw.println(JsonUtil.toJsonString(loginResponse));
            pw.flush();
        }
    }
}
