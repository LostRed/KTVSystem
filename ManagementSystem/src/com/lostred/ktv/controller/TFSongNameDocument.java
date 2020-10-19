package com.lostred.ktv.controller;

import com.lostred.ktv.util.InitialUtil;
import com.lostred.ktv.view.SongInfoEditView;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * 编辑窗口歌曲名文本框监听
 */
public class TFSongNameDocument implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        generateInitial(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        generateInitial(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    private void generateInitial(DocumentEvent e) {
        Document document = e.getDocument();
        try {
            //获取编辑窗口歌曲名的文本
            String text = document.getText(document.getStartPosition().getOffset(), document.getLength());
            SongInfoEditView ev = SongInfoEditView.getEditDialog();
            //自动修改拼音首字母
            ev.getTfSongInitial().setText(InitialUtil.convertToInitial(text));
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}
