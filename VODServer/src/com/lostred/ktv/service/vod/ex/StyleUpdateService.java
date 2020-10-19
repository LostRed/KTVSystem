package com.lostred.ktv.service.vod.ex;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dto.ex.StyleUpdateRequest;
import com.lostred.ktv.dto.ex.StyleUpdateResponse;
import com.lostred.ktv.net.VCRecvThread;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.service.vod.InVODService;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * 点播客户端分类搜索更新回应业务
 */
public class StyleUpdateService implements InVODService {
    @Override
    public void doService(VCRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        StyleUpdateRequest styleUpdateRequest = (StyleUpdateRequest) JSONObject.toBean(jsonObject, StyleUpdateRequest.class);
        StylePO stylePO = styleUpdateRequest.getStylePO();
        String time = styleUpdateRequest.getTime();
        //显示消息
        String message = TimeUtil.getNowTime() + "\tIP" + handler.getSocket().getInetAddress()
                + ":" + handler.getSocket().getPort() + "：请求类型查询更新数据";
        handler.showMessage(message);
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        //查询已更新的歌曲
        List<SongPO> list = songDAO.queryUpdateByStyle(stylePO, time);
        //回应客户端
        StyleUpdateResponse styleUpdateResponse = new StyleUpdateResponse(list);
        PrintWriter pw = handler.getPw();
        pw.println(JsonUtil.toJsonString(styleUpdateResponse));
        pw.flush();
    }
}
