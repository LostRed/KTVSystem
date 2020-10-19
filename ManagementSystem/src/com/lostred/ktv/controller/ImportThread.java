package com.lostred.ktv.controller;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.InitialUtil;
import com.lostred.ktv.util.MD5Util;
import com.lostred.ktv.util.SongTimeUtil;
import com.lostred.ktv.view.ManagementSystemView;
import com.lostred.ktv.view.ProgressView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * 歌曲信息导入线程
 */
public class ImportThread extends Thread {
    /**
     * 选择的文件数组
     */
    private final File[] files;
    /**
     * 新增导入歌曲的数量
     */
    private int total;
    /**
     * 重复歌曲的数量
     */
    private int repeat;

    /**
     * 构造导入线程
     *
     * @param files 选择的文件数组
     */
    public ImportThread(File[] files) {
        this.files = files;
    }

    @Override
    public void run() {
        //进度条初始化
        ProgressView pv = ProgressView.getProgressView();
        pv.getProgressBar().setMaximum(files.length);
        pv.getProgressBar().setValue(0);
        //遍历文件数组
        for (File file : files) {
            String songName = file.getName();
            String md5 = MD5Util.getMD5(file);
            long fileSize = file.length();
            String filePath = file.getAbsolutePath();
            int time = SongTimeUtil.getSeconds(file);
            String songInitial = InitialUtil.convertToInitial(songName);
            String songTime = SongTimeUtil.format(time);
            SongPO songPO = new SongPO(0, songName, null, null, songInitial, songTime, 0,
                    md5, fileSize, filePath, "new", null);
            //导入该歌曲的信息
            try {
                total += LocalService.getService().importSong(songPO);
            } catch (IOException ex) {
                repeat++;//有重复则将重复数量加1
            }
            //更新进度条
            SwingUtilities.invokeLater(() -> pv.getProgressBar().setValue(pv.getProgressBar().getValue() + total + repeat));
        }
        pv.dispose();
        //弹窗提示
        if (repeat > 0) {
            JOptionPane.showMessageDialog(ManagementSystemView.getManagementSystemView(),
                    "检测到" + repeat + "首重复歌曲，成功添加了" + total + "首歌曲！");
        } else {
            JOptionPane.showMessageDialog(ManagementSystemView.getManagementSystemView(),
                    "成功添加了" + total + "首歌曲！");
        }
        LocalService.getService().refreshView();
    }
}
