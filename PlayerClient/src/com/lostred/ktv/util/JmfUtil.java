package com.lostred.ktv.util;

import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import java.io.File;
import java.io.IOException;

/**
 * JMF框架工具
 */
public class JmfUtil {
    /**
     * 获取多媒体对象
     *
     * @param file 文件对象
     * @return 多媒体对象
     */
    public static Player getPlayer(File file) {
        MediaLocator fileMusic = new MediaLocator("file:///" + file.getAbsolutePath());
        Player player = null;
        try {
            player = Manager.createPlayer(fileMusic);
        } catch (IOException | NoPlayerException ex) {
            ex.printStackTrace();
        }
        return player;
    }
}
