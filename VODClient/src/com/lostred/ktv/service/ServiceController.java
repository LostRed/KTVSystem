package com.lostred.ktv.service;

import com.lostred.ktv.service.ex.PinYinUpdateService;
import com.lostred.ktv.service.ex.SingerUpdateService;
import com.lostred.ktv.service.ex.StyleUpdateService;
import com.lostred.ktv.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务控制器
 */
public class ServiceController {
    /**
     * 业务集合
     */
    private static final Map<String, InService> SERVICE_MAP = new HashMap<>();

    static {
        SERVICE_MAP.put(JsonUtil.UPDATE_DATABASE, new UpdateService());
        SERVICE_MAP.put(JsonUtil.LOGIN, new LoginService());
        SERVICE_MAP.put(JsonUtil.LOGOFF, new LogoffService());
        SERVICE_MAP.put(JsonUtil.PLAYLIST, new PlaylistService());
        SERVICE_MAP.put(JsonUtil.HOT, new HotService());
        SERVICE_MAP.put(JsonUtil.DELETE_SONG, new DeleteService());
        SERVICE_MAP.put(JsonUtil.K_SONG, new KService());
        SERVICE_MAP.put(JsonUtil.PICK_SONG, new PickService());
        SERVICE_MAP.put(JsonUtil.TOP_SONG, new TopService());
        SERVICE_MAP.put(JsonUtil.ENTER, new EnterService());
        SERVICE_MAP.put(JsonUtil.EXIT, new ExitService());
        SERVICE_MAP.put(JsonUtil.EXCEPTION_EXIT, new ExceptionExitService());
        SERVICE_MAP.put(JsonUtil.NEXT, new NextService());
        SERVICE_MAP.put(JsonUtil.END, new EndService());
        SERVICE_MAP.put(JsonUtil.PINYIN_QUERY_UPDATE, new PinYinUpdateService());//亮点业务
        SERVICE_MAP.put(JsonUtil.SINGER_QUERY_UPDATE, new SingerUpdateService());//亮点业务
        SERVICE_MAP.put(JsonUtil.STYLE_QUERY_UPDATE, new StyleUpdateService());//亮点业务
    }

    /**
     * 获取业务对象
     *
     * @param type 英文类型
     * @return 业务对象
     */
    public static InService getService(String type) {
        return SERVICE_MAP.get(type);
    }
}
