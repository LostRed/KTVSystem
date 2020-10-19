package com.lostred.ktv.controller;

import com.lostred.ktv.service.LocalService;

import java.io.File;

/**
 * 等待播放音乐线程
 */
public class PlayThread extends Thread {
    /**
     * 等待播放的文件
     */
    private final File file;

    /**
     * 构造等待播放音乐线程
     *
     * @param file 等待播放的文件
     */
    public PlayThread(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        //循环等待直到文件下载完成
        while (!DownloadFileThread.DONE) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //播放歌曲
        LocalService.getLocalService().play(file);
        //重置预下载状态
        DownloadFileThread.START = false;
        DownloadFileThread.DONE = false;
    }
}
