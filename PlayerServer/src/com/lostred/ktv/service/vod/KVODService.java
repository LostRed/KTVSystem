package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dto.player.PlayRequest;
import com.lostred.ktv.dto.vod.KResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSRecvThread;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点播服务端请求K歌业务
 */
public class KVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) throws IOException {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        KResponse kResponse = (KResponse) JSONObject.toBean(jsonObject, KResponse.class);
        PlaylistPO playlistPO = kResponse.getPlaylistPO();
        RoomPO roomPO = playlistPO.getRoomPO();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Collection<PlaylistPO> collection = JSONArray.toCollection(jsonArray, PlaylistPO.class);
        List<PlaylistPO> list = new ArrayList<>(collection);
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + playlistPO.getRoomPO().getRoomId()
                + "号房间：请求K歌《" + playlistPO.getSongPO().getSinger().getSingerName() + " - " + playlistPO.getSongPO().getSongName() + "》";
        handler.showMessage(message);
        //查询歌曲文件信息
        SongPO songPO = playlistPO.getSongPO();
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        songPO = songDAO.selectSong(songPO.getSongId());
        String md5 = songPO.getMd5();
        String filePath = songPO.getFilePath();
        long fileSize = new File(filePath).length();
        //通知播放服务端播放歌曲
        Socket socket = PCRecvThread.getRoomMap().get(roomPO.getRoomId());
        PlayRequest playRequest = new PlayRequest(playlistPO, list, md5, fileSize, filePath);
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.println(JsonUtil.toJsonString(playRequest));
        pw.flush();
    }
}
