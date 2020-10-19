package com.lostred.ktv.util;

import net.sf.json.JSONObject;

/**
 * Json工具
 */
public class JsonUtil {
    //业务标识
    public static final String UPDATE_DATABASE = "UPDATE_DATABASE";
    public static final String LOGIN = "LOGIN";
    public static final String LOGOFF = "LOGOFF";
    public static final String EXCEPTION_LOGOFF = "EXCEPTION_LOGOFF";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    public static final String VOD_RECONNECT = "VOD_RECONNECT";
    public static final String PLAYER_RECONNECT = "PLAYER_RECONNECT";
    public static final String PLAYLIST = "PLAYLIST";
    public static final String HOT = "HOT";
    public static final String K_SONG = "K_SONG";
    public static final String TOP_SONG = "TOP_SONG";
    public static final String DELETE_SONG = "DELETE_SONG";
    public static final String PICK_SONG = "PICK_SONG";
    public static final String ROOM_LIST = "ROOM_LIST";
    public static final String ENTER = "ENTER";
    public static final String EXCEPTION_EXIT = "EXCEPTION_EXIT";
    public static final String EXIT = "EXIT";
    public static final String PLAY = "PLAY";
    public static final String END = "END";
    public static final String NEXT = "NEXT";
    public static final String PINYIN_QUERY_UPDATE = "PINYIN_QUERY_UPDATE";
    public static final String SINGER_QUERY_UPDATE = "SINGER_QUERY_UPDATE";
    public static final String STYLE_QUERY_UPDATE = "STYLE_QUERY_UPDATE";
    public static final String DOWNLOAD = "DOWNLOAD";

    /**
     * 将普通对象转换为Json对象
     *
     * @param object 普通对象
     * @return Json对象
     */
    public static String toJsonString(Object object) {
        return JSONObject.fromObject(object).toString();
    }

    /**
     * 将Json字符串转换为Json对象
     *
     * @param jsonString Json字符串
     * @return Json对象
     */
    public static JSONObject toJsonObject(String jsonString) {
        return JSONObject.fromObject(jsonString);
    }
}
