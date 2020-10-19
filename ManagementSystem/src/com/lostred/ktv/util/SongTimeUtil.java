package com.lostred.ktv.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * 歌曲时间工具
 */
public class SongTimeUtil {
    /**
     * 将秒数格式化为 00:00的格式
     *
     * @param time 秒数
     * @return 时间字符串
     */
    public static String format(int time) {
        int minute = time / 60;
        int second = time % 60;
        String minuteString;
        String secondString;
        if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = "" + minute;
        }
        if (second < 10) {
            secondString = "0" + second;
        } else {
            secondString = "" + second;
        }
        return minuteString + ":" + secondString;
    }

    /**
     * 获取音乐文件的歌曲时长
     *
     * @param file 音乐文件
     * @return 歌曲的秒数时长
     */
    public static int getSeconds(File file) {
        int second = 0;
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip.open(ais);
            second = (int) (clip.getMicrosecondLength() / 1000000d);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return second;
    }
}
