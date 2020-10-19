package com.lostred.ktv.service;

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
        SERVICE_MAP.put(JsonUtil.ROOM_LIST, new RoomListService());
        SERVICE_MAP.put(JsonUtil.ENTER, new EnterService());
        SERVICE_MAP.put(JsonUtil.EXIT, new ExitService());
        SERVICE_MAP.put(JsonUtil.PLAYLIST, new PlaylistService());
        SERVICE_MAP.put(JsonUtil.PLAY, new PlayService());
        SERVICE_MAP.put(JsonUtil.LOGIN, new LoginService());
        SERVICE_MAP.put(JsonUtil.LOGOFF, new LogoffService());
        SERVICE_MAP.put(JsonUtil.EXCEPTION_LOGOFF, new ExceptionLogoffService());
        SERVICE_MAP.put(JsonUtil.DOWNLOAD, new DownloadService());
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
