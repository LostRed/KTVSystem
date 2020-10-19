package com.lostred.ktv.view.SongInfoPanel;

import javax.swing.table.DefaultTableModel;

/**
 * 歌曲信息表格模型
 */
public class SongInfoTableModel extends DefaultTableModel {
    /**
     * 构造歌曲信息表格模型
     */
    public SongInfoTableModel() {
        //设置表格字段
        Object[] columnsName = {"序号", "歌曲名", "歌手", "类型", "拼音缩写", "时长", "热度"};
        setColumnIdentifiers(columnsName);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
