package com.lostred.ktv.service.vod.ex;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dto.ex.PinYinUpdateRequest;
import com.lostred.ktv.dto.ex.PinYinUpdateResponse;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.service.vod.InVODService;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 点播客户端拼音首字母搜索更新回应业务
 */
public class PinYinUpdateService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        PinYinUpdateRequest pinYinUpdateRequest = (PinYinUpdateRequest) JSONObject.toBean(jsonObject, PinYinUpdateRequest.class);
        String keyword = pinYinUpdateRequest.getKeyword();
        String time = pinYinUpdateRequest.getTime();
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：请求拼音查询更新数据";
        handler.showMessage(message);
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        //查询已更新的歌曲
        List<SongPO> list = songDAO.fuzzyQueryUpdateSong(keyword, time);
        //回应客户端
        PinYinUpdateResponse pinYinUpdateResponse = new PinYinUpdateResponse(list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(pinYinUpdateResponse));
        pw.flush();
    }
}
