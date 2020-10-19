package com.lostred.ktv.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * DAO实现类工厂
 */
public class DaoFactory {
    /**
     * DAO接口集合
     */
    private static final Map<String, Object> DAO_MAP = new HashMap<>();

    static {
        Object object;
        try {
            object = Class.forName("com.lostred.ktv.dao.PlaylistDAOImpl").newInstance();
            DAO_MAP.put("InPlaylistDAO", object);
            object = Class.forName("com.lostred.ktv.dao.SongDAOImpl").newInstance();
            DAO_MAP.put("InSongDAO", object);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取DAO接口实现类对象
     *
     * @param InDAOName DAO接口名
     * @return DAO接口实现类对象
     */
    public static Object getDAO(String InDAOName) {
        return DAO_MAP.get(InDAOName);
    }
}
