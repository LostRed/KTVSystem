package com.lostred.ktv.service.vod;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dto.vod.HotRequest;
import com.lostred.ktv.dto.vod.HotResponse;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 点播客户端请求查询歌曲热度排行业务
 */
public class HotVODService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        HotRequest hotRequest = (HotRequest) JSONObject.toBean(jsonObject, HotRequest.class);
        RoomPO roomPO = hotRequest.getRoomPO();
        int limit = hotRequest.getLimit();
        int offset = hotRequest.getOffset();
        //显示消息
        String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：请求查询歌曲热度排行";
        handler.showMessage(message);
        //查询所有歌曲，按热度降序排行
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        int total = songDAO.countQueryAllSong();
        List<SongPO> list = songDAO.queryAllSongOrderByHot(limit, offset);
        //回应点播客户端
        HotResponse hotResponse;
        if (total > 10) {
            hotResponse = new HotResponse(10, list);
        } else {
            hotResponse = new HotResponse(total, list);
        }
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(hotResponse));
        pw.flush();
    }
}
