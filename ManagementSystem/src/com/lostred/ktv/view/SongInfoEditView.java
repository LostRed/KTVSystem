package com.lostred.ktv.view;

import com.lostred.ktv.controller.EditAction;
import com.lostred.ktv.controller.TFSongNameDocument;
import com.lostred.ktv.po.SingerPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * 歌曲信息编辑窗口
 */
public class SongInfoEditView extends JDialog {
    /**
     * 歌曲信息编辑窗口单例对象
     */
    private static SongInfoEditView EDIT_DIALOG;
    /**
     * 歌曲名文本框
     */
    private final JTextField tfSongName = new JTextField();
    /**
     * 歌手下拉框
     */
    private final JComboBox<SingerPO> cbSinger = new JComboBox<>();
    /**
     * 歌手文本框
     */
    private final JTextField tfSinger = new JTextField();
    /**
     * 歌曲类型下拉框
     */
    private final JComboBox<StylePO> cbStyle = new JComboBox<>();
    /**
     * 类型文本框
     */
    private final JTextField tfStyle = new JTextField();
    /**
     * 歌曲首字母文本框
     */
    private final JTextField tfSongInitial = new JTextField();

    /**
     * 构造歌曲信息编辑窗口
     */
    private SongInfoEditView() {
        //初始化控件
        JPanel songNamePanel = new JPanel();
        JPanel singerPanel = new JPanel();
        JPanel stylePanel = new JPanel();
        JPanel songInitialPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JLabel lbSongName = new JLabel("歌曲名", JLabel.RIGHT);
        JLabel lbSinger = new JLabel("歌手", JLabel.RIGHT);
        JLabel lbStyle = new JLabel("类型", JLabel.RIGHT);
        JLabel lbSongInitial = new JLabel("拼音缩写", JLabel.RIGHT);
        DefaultButton btnAddSinger = new DefaultButton("新增歌手");
        DefaultButton btnAddStyle = new DefaultButton("新增类型");
        DefaultButton btnSure = new DefaultButton("确认");
        DefaultButton btnCancel = new DefaultButton("取消");
        //设置控件
        setLayout(new GridLayout(5, 1));
        songNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        singerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        stylePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        songInitialPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        songNamePanel.setBorder(new EmptyBorder(5, 20, 5, 40));
        singerPanel.setBorder(new EmptyBorder(5, 20, 5, 40));
        stylePanel.setBorder(new EmptyBorder(5, 20, 5, 40));
        songInitialPanel.setBorder(new EmptyBorder(5, 20, 5, 40));
        lbSongName.setPreferredSize(new Dimension(60, 28));
        lbSinger.setPreferredSize(new Dimension(60, 28));
        lbStyle.setPreferredSize(new Dimension(60, 28));
        lbSongInitial.setPreferredSize(new Dimension(60, 28));
        tfSongName.setPreferredSize(new Dimension(300, 28));
        cbSinger.setPreferredSize(new Dimension(100, 28));
        tfSinger.setPreferredSize(new Dimension(100, 28));
        cbStyle.setPreferredSize(new Dimension(100, 28));
        tfStyle.setPreferredSize(new Dimension(100, 28));
        tfSongInitial.setPreferredSize(new Dimension(100, 28));
        cbSinger.setFocusable(false);
        cbStyle.setFocusable(false);
        getRootPane().setDefaultButton(btnSure);
        //添加控件
        songNamePanel.add(lbSongName);
        songNamePanel.add(tfSongName);
        singerPanel.add(lbSinger);
        singerPanel.add(cbSinger);
        singerPanel.add(tfSinger);
        singerPanel.add(btnAddSinger);
        stylePanel.add(lbStyle);
        stylePanel.add(cbStyle);
        stylePanel.add(tfStyle);
        stylePanel.add(btnAddStyle);
        songInitialPanel.add(lbSongInitial);
        songInitialPanel.add(tfSongInitial);
        buttonPanel.add(btnSure);
        buttonPanel.add(btnCancel);
        add(songNamePanel);
        add(singerPanel);
        add(stylePanel);
        add(songInitialPanel);
        add(buttonPanel);
        //添加监听
        EditAction editAction = new EditAction();
        TFSongNameDocument TFSongNameDocument = new TFSongNameDocument();
        btnAddSinger.addActionListener(editAction);
        btnAddStyle.addActionListener(editAction);
        btnSure.addActionListener(editAction);
        btnCancel.addActionListener(editAction);
        btnAddSinger.setActionCommand("addSinger");
        btnAddStyle.setActionCommand("addStyle");
        btnSure.setActionCommand("sure");
        btnCancel.setActionCommand("cancel");
        tfSongName.getDocument().addDocumentListener(TFSongNameDocument);
        //设置窗口
        setTitle("修改歌曲信息");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        pack();
        setLocationRelativeTo(ManagementSystemView.getManagementSystemView());
    }

    /**
     * 获取单例歌曲信息编辑窗口
     *
     * @return 歌曲信息编辑窗口
     */
    public static SongInfoEditView getEditDialog() {
        if (EDIT_DIALOG == null) {
            synchronized (SongInfoEditView.class) {
                if (EDIT_DIALOG == null) {
                    EDIT_DIALOG = new SongInfoEditView();
                }
            }
        }
        return EDIT_DIALOG;
    }

    /**
     * 刷新下拉框
     *
     * @param singerPOList 歌手集合
     * @param stylePOList  类型集合
     */
    public void refreshComboBox(List<SingerPO> singerPOList, List<StylePO> stylePOList) {
        if (singerPOList != null) {
            cbSinger.removeAllItems();
            for (SingerPO singerPO : singerPOList) {
                cbSinger.addItem(singerPO);
            }
        }
        if (stylePOList != null) {
            cbStyle.removeAllItems();
            for (StylePO stylePOPO : stylePOList) {
                cbStyle.addItem(stylePOPO);
            }
        }
    }

    /**
     * 导入歌曲数据
     *
     * @param songPO 歌曲对象
     */
    public void importSongPO(SongPO songPO) {
        tfSongName.setText(songPO.getSongName());
        int count = cbSinger.getItemCount();
        for (int i = 0; i < count; i++) {
            if (cbSinger.getItemAt(i).getSingerId() == songPO.getSinger().getSingerId()) {
                cbSinger.setSelectedIndex(i);
                break;
            }
        }
        count = cbStyle.getItemCount();
        for (int i = 0; i < count; i++) {
            if (cbStyle.getItemAt(i).getStyleId() == songPO.getStyle().getStyleId()) {
                cbStyle.setSelectedIndex(i);
                break;
            }
        }
        tfSongInitial.setText(songPO.getSongInitial());
    }

    //get和set方法
    public JTextField getTfSongName() {
        return tfSongName;
    }

    public JComboBox<SingerPO> getCbSinger() {
        return cbSinger;
    }

    public JTextField getTfSinger() {
        return tfSinger;
    }

    public JComboBox<StylePO> getCbStyle() {
        return cbStyle;
    }

    public JTextField getTfStyle() {
        return tfStyle;
    }

    public JTextField getTfSongInitial() {
        return tfSongInitial;
    }
}
