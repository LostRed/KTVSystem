package com.lostred.ktv.service.player;

import com.lostred.ktv.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务控制器
 */
public class PlayerServiceController {
    /**
     * 业务集合
     */
    private static final Map<String, InPlayerService> SERVICE_MAP = new HashMap<>();

    static {
        SERVICE_MAP.put(JsonUtil.ROOM_LIST, new FreeRoomListPlayerService());
        SERVICE_MAP.put(JsonUtil.ENTER, new EnterPlayerService());
        SERVICE_MAP.put(JsonUtil.EXIT, new ExitPlayerService());
        SERVICE_MAP.put(JsonUtil.PLAYLIST, new PlaylistPlayerService());
        SERVICE_MAP.put(JsonUtil.NEXT, new NextPlayerService());
        SERVICE_MAP.put(JsonUtil.PLAY, new PlayPlayerService());
        SERVICE_MAP.put(JsonUtil.END, new EndPlayerService());
        SERVICE_MAP.put(JsonUtil.PLAYER_RECONNECT, new PlayerReconnectPlayerService());
        SERVICE_MAP.put(JsonUtil.DOWNLOAD, new DownloadService());
    }

    /**
     * 获取业务对象
     *
     * @param type 英文类型
     * @return 业务对象
     */
    public static InPlayerService getService(String type) {
        return SERVICE_MAP.get(type);
    }
}
