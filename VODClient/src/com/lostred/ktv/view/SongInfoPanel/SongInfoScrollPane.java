package com.lostred.ktv.view.SongInfoPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 歌曲信息滚动条面板
 */
public class SongInfoScrollPane extends JScrollPane {
    /**
     * 歌曲信息表格
     */
    private final SongInfoTable table = new SongInfoTable();

    /**
     * 构造歌曲信息面板
     */
    public SongInfoScrollPane() {
        //设置面板
        setPreferredSize(new Dimension(580, 281));
        setViewportView(table);
    }

    //get和set方法
    public SongInfoTable getTable() {
        return table;
    }
}
