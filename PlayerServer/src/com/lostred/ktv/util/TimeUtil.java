package com.lostred.ktv.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }
}
