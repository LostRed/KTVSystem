package com.lostred.ktv.controller;

import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.ManagementSystemView;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 主界面搜索文本框监听
 */
public class TFSearchDocument implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
        //重置页码
        mv.setCurrentPage(1);
        mv.setOffset(0);
        LocalService.getService().refreshView();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
        //重置页码
        mv.setCurrentPage(1);
        mv.setOffset(0);
        LocalService.getService().refreshView();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
