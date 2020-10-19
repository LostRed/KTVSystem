package com.lostred.ktv.controller;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.LoginView;
import com.lostred.ktv.view.ProgressView;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.*;

/**
 * 导入歌曲信息线程
 */
public class ImportThread extends Thread {
    /**
     * 文件大小
     */
    private final long fileSize;
    /**
     * 当前已导入的文件大小
     */
    private int currentSize;

    /**
     * 构造导入歌曲信息线程
     *
     * @param fileSize 文件大小
     */
    public ImportThread(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public void run() {
        //进度条初始化
        SwingUtilities.invokeLater(() -> {
            ProgressView.getProgressView().setTitle("正在导入歌曲信息");
            ProgressView.getProgressView().getProgressBar().setMaximum((int) fileSize);
            ProgressView.getProgressView().getProgressBar().setValue(0);
        });
        //读取文件
        InputStream is = null;
        try {
            is = new FileInputStream("data/.patch");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while (true) {
                //按行读取
                String jsonString = br.readLine();
                //文件读取完毕
                if (jsonString == null) {
                    break;
                }
                //解析json
                JSONObject jsonObject = JsonUtil.toJsonObject(jsonString);
                SongPO songPO = (SongPO) JSONObject.toBean(jsonObject, SongPO.class);
                //导入数据
                LocalService.getLocalService().importDate(songPO);
                //更新进度条
                currentSize += jsonString.getBytes().length;
                SwingUtilities.invokeLater(() -> ProgressView.getProgressView().getProgressBar().setValue(currentSize));
            }
            SwingUtilities.invokeLater(() -> ProgressView.getProgressView().dispose());
            JOptionPane.showMessageDialog(LoginView.getLoginView(), "更新完成！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
