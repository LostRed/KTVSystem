package com.lostred.ktv.controller;

import com.lostred.ktv.service.LocalService;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 下拉框监听
 */
public class ComboBoxItem implements ItemListener {
    @Override
    public void itemStateChanged(ItemEvent e) {
        //下拉框发生改变且为选中的项目时
        if (e.getStateChange() == ItemEvent.SELECTED) {
            LocalService localService = LocalService.getService();
            localService.refreshView();
        }
    }
}
