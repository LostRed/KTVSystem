package com.lostred.ktv.controller;


import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.VODClientView;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 点播客户端主界面搜索文本框监听
 */
public class TFSearchDocument implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        VODClientView vodCv = VODClientView.getVODClientView();
        //重置页码
        vodCv.setCurrentPage(1);
        vodCv.setOffset(0);
        //按歌曲名首字母拼音搜索歌曲
        LocalService.getLocalService().refreshPinYinView();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        VODClientView vodCv = VODClientView.getVODClientView();
        //重置页码
        vodCv.setCurrentPage(1);
        vodCv.setOffset(0);
        //按歌曲名首字母拼音搜索歌曲
        LocalService.getLocalService().refreshPinYinView();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
