package com.lostred.ktv.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置
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
            prop.load(new BufferedReader(new FileReader("VODServer/data/config.properties")));
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
            prop.load(new BufferedReader(new FileReader("VODServer/data/config.properties")));
            port = prop.getProperty("port");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }
}
