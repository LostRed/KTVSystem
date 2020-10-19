package com.lostred.ktv.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 歌曲工具
 */
public class TimeUtil {
    /**
     * 获取当前时间
     *
     * @return 当前时间字符串
     */
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }
}
