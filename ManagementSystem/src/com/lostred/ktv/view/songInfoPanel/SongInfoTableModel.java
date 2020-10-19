package com.lostred.ktv.view.songInfoPanel;

import javax.swing.table.DefaultTableModel;

/**
 * 歌曲信息表格模型
 */
public class SongInfoTableModel extends DefaultTableModel {
    /**
     * 构造歌曲信息表格模型
     */
    public SongInfoTableModel() {
        Object[] columnsName = {"歌曲ID", "歌曲名", "歌手", "类型", "拼音缩写", "时长", "文件大小", "文件路径"};
        setColumnIdentifiers(columnsName);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
