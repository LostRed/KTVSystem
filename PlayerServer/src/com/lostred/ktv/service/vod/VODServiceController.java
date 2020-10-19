package com.lostred.ktv.service.vod;

import com.lostred.ktv.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class VODServiceController {
    /**
     * 业务集合
     */
    private static final Map<String, InVODService> SERVICE_MAP = new HashMap<>();

    static {
        SERVICE_MAP.put(JsonUtil.ROOM_LIST, new LoginRoomListVODService());
        SERVICE_MAP.put(JsonUtil.LOGIN, new LoginVODService());
        SERVICE_MAP.put(JsonUtil.LOGOFF, new LogoffVODService());
        SERVICE_MAP.put(JsonUtil.EXIT, new ExitVODService());
        SERVICE_MAP.put(JsonUtil.PLAYLIST, new PlaylistVODService());
        SERVICE_MAP.put(JsonUtil.K_SONG, new KVODService());
        SERVICE_MAP.put(JsonUtil.VOD_RECONNECT, new VODReconnectVODService());
        SERVICE_MAP.put(JsonUtil.PLAYER_RECONNECT, new PlayerReconnectVODService());
        SERVICE_MAP.put(JsonUtil.EXCEPTION_EXIT, new ExceptionExitVODService());
        SERVICE_MAP.put(JsonUtil.EXCEPTION_LOGOFF, new ExceptionLogoffVODService());
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
