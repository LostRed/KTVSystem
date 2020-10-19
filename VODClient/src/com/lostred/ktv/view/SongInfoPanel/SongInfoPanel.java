package com.lostred.ktv.view.SongInfoPanel;

import com.lostred.ktv.view.ControlPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 歌曲信息面板
 */
public class SongInfoPanel extends JPanel {
    /**
     * 歌曲信息滚动条面板
     */
    private final SongInfoScrollPane songInfoScrollPane = new SongInfoScrollPane();
    /**
     * 控制面板
     */
    private final ControlPanel controlPanel = new ControlPanel();

    /**
     * 构造歌曲信息面板
     */
    public SongInfoPanel() {
        //设置控件
        setLayout(new BorderLayout());
        controlPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        //添加控件
        add(songInfoScrollPane);
        add(controlPanel, BorderLayout.SOUTH);
    }

    //get和set方法
    public SongInfoScrollPane getSongInfoScrollPane() {
        return songInfoScrollPane;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }
}
