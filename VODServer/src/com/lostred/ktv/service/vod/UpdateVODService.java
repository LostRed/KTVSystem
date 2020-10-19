package com.lostred.ktv.service.vod;

import com.lostred.ktv.controller.SendFileThread;
import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dto.vod.UpdateRequest;
import com.lostred.ktv.dto.vod.UpdateResponse;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.FileServiceUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.List;

/**
 * 播放客户端请求更新数据库业务
 */
public class UpdateVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        UpdateRequest updateRequest = (UpdateRequest) JSONObject.toBean(jsonObject, UpdateRequest.class);
        String time = updateRequest.getTime();
        //客户端首次登录
        if (time.equals("1970-01-01 00:00:00")) {
            //显示消息
            String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                    + ":" + handler.getSocket().getPort() + "：请求首次更新数据";
            handler.showMessage(message);
            //分配ip和端口
            ServerSocket serverSocket = FileServiceUtil.getFileServerUtil().getServerSocket();
            String ip = FileServiceUtil.getFileServerUtil().getIp();
            int port = FileServiceUtil.getFileServerUtil().getPort();
            //开启文件发送线程
            new SendFileThread(serverSocket).start();
            long fileSize = new File("data/.patch").length();
            //回应客户端
            UpdateResponse updateResponse = new UpdateResponse(null, fileSize, ip, port);
            PrintWriter pw = handler.getPw();
            pw.println(JsonUtil.toJsonString(updateResponse));
            pw.flush();
        }
        //客户端非首次登录
        else {
            //显示消息
            String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                    + ":" + handler.getSocket().getPort() + "：请求非首次更新数据";
            handler.showMessage(message);
            InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
            //查询已更新的歌曲
            List<SongPO> list = songDAO.querySongAfterTime(time);
            //回应客户端
            UpdateResponse updateResponse = new UpdateResponse(list, 0, null, 0);
            PrintWriter pw = handler.getPw();
            pw.println(JsonUtil.toJsonString(updateResponse));
            pw.flush();
        }
    }
}
