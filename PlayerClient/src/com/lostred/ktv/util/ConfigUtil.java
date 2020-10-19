package com.lostred.ktv.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 配置文件工具
 */
public class ConfigUtil {
    /**
     * 获取服务端ip
     *
     * @return 服务端ip
     */
    public static String getIp() {
        String host = null;
        Properties prop = new Properties();
        try {
            prop.load(new BufferedReader(new FileReader("PlayerClient/data/config.properties")));
            host = prop.getProperty("ip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return host;
    }

    /**
     * 获取服务端端口
     *
     * @return 服务端端口
     */
    public static String getPort() {
        String port = null;
        Properties prop = new Properties();
        try {
            prop.load(new BufferedReader(new FileReader("PlayerClient/data/config.properties")));
            port = prop.getProperty("port");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }

    /**
     * 读取上次播放歌曲的配置文件
     *
     * @param keyName 配置文件中的键值
     * @return 键值对应的值
     */
    public static String readPropertiesValue(String keyName) {
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("PlayerClient/data/lastSong.properties"));
            pps.load(in);
            return pps.getProperty(keyName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写入上次播放歌曲的配置文件
     *
     * @param keyName  键值
     * @param keyValue 值
     */
    public static void writeProperties(String keyName, String keyValue) {
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("PlayerClient/data/lastSong.properties"));
            pps.load(in);
            Set<Object> keys = pps.keySet();
            Map<Object, Object> toSaveMap = new HashMap<>();
            for (Object object : keys) {
                String key = (String) object;
                String value = (String) pps.get(key);
                toSaveMap.put(key, value);
            }
            toSaveMap.put(keyName, keyValue);
            pps.putAll(toSaveMap);
            OutputStream out = new FileOutputStream("PlayerClient/data/lastSong.properties");
            pps.store(out, "Copyright(c) by LostRed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
