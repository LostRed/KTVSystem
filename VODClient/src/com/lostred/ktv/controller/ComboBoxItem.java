package com.lostred.ktv.controller;

import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.VODClientView;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 下拉框监听
 */
public class ComboBoxItem implements ItemListener {
    @Override
    public void itemStateChanged(ItemEvent e) {
        //当下拉框变化为选中项目时
        if (e.getStateChange() == ItemEvent.SELECTED) {
            VODClientView vodCv = VODClientView.getVODClientView();
            //重置页码
            vodCv.setCurrentPage(1);
            vodCv.setOffset(0);
            //按照歌曲类型刷新界面
            LocalService.getLocalService().refreshStyleView();
        }
    }
}
