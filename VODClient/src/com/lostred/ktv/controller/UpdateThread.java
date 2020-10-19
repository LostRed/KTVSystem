package com.lostred.ktv.controller;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.LoginView;
import com.lostred.ktv.view.ProgressView;
import com.lostred.ktv.view.VODClientView;

import javax.swing.*;
import java.util.List;

/**
 * 更新歌曲信息线程
 */
public class UpdateThread extends Thread {
    /**
     * 更新歌曲信息集合
     */
    private final List<SongPO> list;
    /**
     * 当前已更新的歌曲数量
     */
    private int currentSize;
    /**
     * 更新模式，-1为登录非首次更新，0为在线拼音首字母搜索更新，1为在线歌手首字母搜索更新，2为在线歌曲类型搜索更新
     */
    private final int mode;

    /**
     * 构造更新歌曲信息线程
     *
     * @param list 更新歌曲信息集合
     */
    public UpdateThread(List<SongPO> list, int mode) {
        this.list = list;
        this.mode = mode;
    }

    @Override
    public void run() {
        //如果更新模式为登录非首次更新
        if (mode == -1) {
            //进度条初始化
            SwingUtilities.invokeLater(() -> {
                ProgressView.getProgressView().setTitle("正在导入歌曲信息");
                ProgressView.getProgressView().getProgressBar().setMaximum(list.size());
                ProgressView.getProgressView().getProgressBar().setValue(0);
            });
        }
        //开始更新
        for (SongPO songPO : list) {
            //导入数据
            LocalService.getLocalService().importDate(songPO);
            //更新进度条
            currentSize++;
            SwingUtilities.invokeLater(() -> ProgressView.getProgressView().getProgressBar().setValue(currentSize));
        }
        //如果更新模式为登录非首次更新
        if (mode == -1) {
            //关闭进图条窗口
            SwingUtilities.invokeLater(() -> ProgressView.getProgressView().dispose());
            JOptionPane.showMessageDialog(LoginView.getLoginView(), "更新完成！");
        } else {
            //根据更新模式刷新界面
            refreshView();
        }
    }

    /**
     * 根据更新模式刷新界面
     */
    private void refreshView() {
        List<StylePO> stylePOList;
        switch (mode) {
            case 0:
                //更新下拉框
                stylePOList = LocalService.getLocalService().queryAllStyle();
                VODClientView.getVODClientView().getSongInfoPanel().getControlPanel().refreshComboBox(stylePOList);
                //查询本地数据库根据拼音首字母刷新界面
                LocalService.getLocalService().localRefreshPinYinView();
                break;
            case 1:
                //更新下拉框
                stylePOList = LocalService.getLocalService().queryAllStyle();
                VODClientView.getVODClientView().getSongInfoPanel().getControlPanel().refreshComboBox(stylePOList);
                //查询本地数据库根据歌手首字母刷新界面
                LocalService.getLocalService().localRefreshSingerView();
                break;
            case 2:
                //查询本地数据库根据选择的类型刷新界面
                LocalService.getLocalService().localRefreshStyleView();
                break;
        }
    }
}
