package com.lostred.ktv.util;

import java.text.DecimalFormat;

/**
 * 文件大小格式化工具
 */
public class FileSizeUtil {
    /**
     * 将Byte单位的文件大小转换成适合的单位显示
     *
     * @param byteSize Byte单位的文件大小
     * @return 文件大小及单位的字符串
     */
    public static String format(long byteSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        int i = 0;
        double size = byteSize;
        //值大于1024时，循环除以1024，最多除以3次
        while (size > 1024) {
            size /= 1024;
            if (i == 3) {
                break;
            }
            i++;
        }
        switch (i) {
            case 0:
                return df.format(size) + " B";
            case 1:
                return df.format(size) + " KB";
            case 2:
                return df.format(size) + " MB";
            case 3:
                return df.format(size) + " GB";
            default:
                return df.format(size) + " TB";
        }
    }
}
