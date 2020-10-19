package com.lostred.ktv.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * 文件业务工具
 */
public class FileServiceUtil {
    /**
     * 文件业务工具单例对象
     */
    private static FileServiceUtil FILE_SERVER_UTIL;
    /**
     * 随机生成的服务端套接字
     */
    private ServerSocket serverSocket;
    /**
     * 本机ip
     */
    private String ip;
    /**
     * 随机生成套接字的本地端口
     */
    private int port;

    /**
     * 构造文件业务工具
     */
    private FileServiceUtil() {
        try {
            this.serverSocket = new ServerSocket(0);
            this.ip = InetAddress.getLocalHost().getHostAddress();
            this.port = serverSocket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取单例文件业务工具
     *
     * @return 文件业务工具单例对象
     */
    public static FileServiceUtil getFileServerUtil() {
        if (FILE_SERVER_UTIL == null) {
            synchronized (FileServiceUtil.class) {
                if (FILE_SERVER_UTIL == null) {
                    FILE_SERVER_UTIL = new FileServiceUtil();
                }
            }
        }
        return FILE_SERVER_UTIL;
    }

    //get和set方法
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
