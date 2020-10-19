package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InPlaylistDAO;
import com.lostred.ktv.dto.vod.KRequest;
import com.lostred.ktv.dto.vod.KResponse;
import com.lostred.ktv.net.PSClient;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 点播客户端请求K歌业务
 */
public class KVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        KRequest kRequest = (KRequest) JSONObject.toBean(jsonObject, KRequest.class);
        PlaylistPO playlistPO = kRequest.getPlaylistPO();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + playlistPO.getRoomPO().getRoomId()
                + "号房间：请求K歌《" + playlistPO.getSongPO().getSinger().getSingerName() + " - " + playlistPO.getSongPO().getSongName() + "》";
        handler.showMessage(message);
        //将该歌曲从数据库中删除
        InPlaylistDAO playlistDAO = (InPlaylistDAO) DaoFactory.getDAO("InPlaylistDAO");
        playlistDAO.deletePlaylist(playlistPO);
        //查询该房间的已点歌曲
        List<PlaylistPO> list = playlistDAO.queryPlaylist(playlistPO.getRoomPO());
        //回应点播客户端更新播放列表
        KResponse kResponse = new KResponse(playlistPO, list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(kResponse));
        pw.flush();
        //如果播放服务端在线
        if (PSClient.getPSClient().isConnected()) {
            //通知播放服务端播放该歌曲，并更新播放列表
            PSClient.getPSClient().sendMessage(JsonUtil.toJsonString(kResponse));
        }
    }
}
