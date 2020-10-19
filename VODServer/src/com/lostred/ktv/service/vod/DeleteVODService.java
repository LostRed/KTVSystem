package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dto.vod.DeleteRequest;
import com.lostred.ktv.dto.vod.DeleteResponse;
import com.lostred.ktv.dto.vod.PlaylistResponse;
import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 点播客户端请求删除歌曲业务
 */
public class DeleteVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        DeleteRequest deleteRequest = (DeleteRequest) JSONObject.toBean(jsonObject, DeleteRequest.class);
        RoomPO roomPO = deleteRequest.getRoomPO();
        PlaylistPO playlistPO = deleteRequest.getPlaylistPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + playlistPO.getRoomPO().getRoomId()
                + "号房间：请求删除歌曲《" + playlistPO.getSongPO().getSinger().getSingerName() + " - " + playlistPO.getSongPO().getSongName() + "》";
        handler.showMessage(message);
        //将该歌曲从数据库中删除
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        playlistDAO.deletePlaylist(playlistPO);
        //查询该房间的已点歌曲
        List<PlaylistPO> list = playlistDAO.queryPlaylist(playlistPO.getRoomPO());
        //回应点播客户端更新播放列表
        DeleteResponse deleteResponse = new DeleteResponse(list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(deleteResponse));
        pw.flush();
        //如果播放服务端在线
        if (PSClient.getPSClient().isConnected()) {
            //通知播放服务端更新播放列表
            PlaylistResponse playlistResponse = new PlaylistResponse(roomPO, list);
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(playlistResponse));
        }
    }
}
