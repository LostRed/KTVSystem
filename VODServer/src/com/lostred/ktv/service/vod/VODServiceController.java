package com.lostred.ktv.service.vod;

import com.lostred.ktv.service.vod.ex.PinYinUpdateService;
import com.lostred.ktv.service.vod.ex.SingerUpdateService;
import com.lostred.ktv.service.vod.ex.StyleUpdateService;
import com.lostred.ktv.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务控制器
 */
public class VODServiceController {
    /**
     * 业务集合
     */
    private static final Map<String, InVODService> SERVICE_MAP = new HashMap<>();

    static {
        SERVICE_MAP.put(JsonUtil.UPDATE_DATABASE, new UpdateVODService());
        SERVICE_MAP.put(JsonUtil.LOGIN, new LoginVODService());
        SERVICE_MAP.put(JsonUtil.LOGOFF, new LogoffVODService());
        SERVICE_MAP.put(JsonUtil.VOD_RECONNECT, new VODReconnectVODService());
        SERVICE_MAP.put(JsonUtil.PLAYLIST, new PlaylistVODService());
        SERVICE_MAP.put(JsonUtil.HOT, new HotVODService());
        SERVICE_MAP.put(JsonUtil.DELETE_SONG, new DeleteVODService());
        SERVICE_MAP.put(JsonUtil.K_SONG, new KVODService());
        SERVICE_MAP.put(JsonUtil.PICK_SONG, new PickVODService());
        SERVICE_MAP.put(JsonUtil.TOP_SONG, new TopVODService());
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
    public static InVODService getService(String type) {
        return SERVICE_MAP.get(type);
    }
}
