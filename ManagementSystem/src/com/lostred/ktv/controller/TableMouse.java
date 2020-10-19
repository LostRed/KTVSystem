package com.lostred.ktv.controller;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.ManagementSystemView;
import com.lostred.ktv.view.songInfoPanel.SongInfoTable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 表格鼠标监听
 */
public class TableMouse extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        //鼠标左键双击时
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
            SongInfoTable songInfoTable = mv.getSongInfoScrollPane().getTable();
            int selectRow = songInfoTable.getSelectedRow();
            //选中表格某行时
            if (selectRow != -1) {
                SongPO songPO = (SongPO) songInfoTable.getValueAt(selectRow, 1);
                LocalService localService = LocalService.getService();
                localService.showEditView(songPO);
            }
        }
    }
}
