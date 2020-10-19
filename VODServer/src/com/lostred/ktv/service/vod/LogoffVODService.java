package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dto.vod.LogoffRequest;
import com.lostred.ktv.dto.vod.LogoffResponse;
import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 点播客户端请求注销房间业务
 */
public class LogoffVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        LogoffRequest logoffRequest = (LogoffRequest) JSONObject.toBean(jsonObject, LogoffRequest.class);
        RoomPO roomPO = logoffRequest.getRoomPO();
        Integer roomId = VCRecvThread.getClientSocketMap().get(handler.getSocket());
        //如果房间有登录过
        if (roomId != null) {
            //如果已连接播放服务端
            if (PSClient.getPSClient().isConnected()) {
                //向播放服务端发送消息通知该房间已注销
                PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(logoffRequest));
            }
            //将房间号和对应端口从Map集合中移除
            VCRecvThread.getRoomMap().remove(roomPO.getRoomId());
            VCRecvThread.getClientSocketMap().remove(handler.getSocket());
            //将房间的已点歌曲全部删除
            InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
            playlistDAO.deletePlaylist(roomPO);
        }
        //回应点播客户端
        LogoffResponse logoffResponse = new LogoffResponse(roomPO);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(logoffResponse));
        pw.flush();
        //关闭socket
        Socket socket = handler.getSocket();
        VCRecvThread.getClientSocketMap().remove(socket);
        socket.close();
        //如果房间存在
        if (roomId != null) {
            //显示消息
            String message = TimeUtil.getNowTime() + "\t" + roomId + "号房间：注销成功";
            handler.showMessage(message);
        }
        throw new IOException("正常退出");
    }
}
