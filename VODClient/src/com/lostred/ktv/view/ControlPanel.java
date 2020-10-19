package com.lostred.ktv.view;

import com.lostred.ktv.controller.ComboBoxItem;
import com.lostred.ktv.controller.ControlAction;
import com.lostred.ktv.controller.TFSearchDocument;
import com.lostred.ktv.controller.TFSingerSearchDocument;
import com.lostred.ktv.po.StylePO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 操作面板
 */
public class ControlPanel extends JPanel {
    /**
     * 已点歌曲面板
     */
    private final JPanel playlistPanel = new JPanel();
    /**
     * 拼音点歌面板
     */
    private final JPanel pinYinPanel = new JPanel();
    /**
     * 歌星点歌面板
     */
    private final JPanel singerPanel = new JPanel();
    /**
     * 分类点歌面板
     */
    private final JPanel stylePanel = new JPanel();
    /**
     * 点歌操作面板
     */
    private final JPanel pickPanel = new JPanel();
    /**
     * 页码面板
     */
    private final JPanel pagePanel = new JPanel();
    /**
     * 类型下拉框
     */
    private final JComboBox<StylePO> cbStyle = new JComboBox<>();
    /**
     * 拼音首字母文本框
     */
    private final JTextField tfSearch = new JTextField();
    /**
     * 歌手首字母文本框
     */
    private final JTextField tfSingerSearch = new JTextField();
    /**
     * 页码标签
     */
    private final JLabel lbPage = new JLabel("1/1", JLabel.CENTER);

    /**
     * 构造操作面板
     */
    public ControlPanel() {
        //初始化控件
        DefaultButton btnKSong = new DefaultButton("K歌");
        DefaultButton btnTop = new DefaultButton("置顶");
        DefaultButton btnDelete = new DefaultButton("删除");
        DefaultButton btnPick = new DefaultButton("点歌");
        DefaultButton btnPageUp = new DefaultButton("上一页");
        DefaultButton btnPageDown = new DefaultButton("下一页");
        //设置控件
        setLayout(new BorderLayout());
        pickPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        pagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnKSong.setPreferredSize(new Dimension(60, 28));
        lbPage.setPreferredSize(new Dimension(40, 28));
        tfSingerSearch.setPreferredSize(new Dimension(120, 28));
        cbStyle.setPreferredSize(new Dimension(120, 28));
        tfSearch.setPreferredSize(new Dimension(120, 28));
        StylePO all = new StylePO(0, "全部");
        cbStyle.addItem(all);
        //添加监听
        TFSearchDocument tfSearchDocument = new TFSearchDocument();
        TFSingerSearchDocument tfSingerSearchDocument = new TFSingerSearchDocument();
        ComboBoxItem comboBoxItem = new ComboBoxItem();
        ControlAction controlAction = new ControlAction();
        tfSearch.getDocument().addDocumentListener(tfSearchDocument);
        tfSingerSearch.getDocument().addDocumentListener(tfSingerSearchDocument);
        cbStyle.addItemListener(comboBoxItem);
        btnKSong.addActionListener(controlAction);
        btnTop.addActionListener(controlAction);
        btnDelete.addActionListener(controlAction);
        btnPick.addActionListener(controlAction);
        btnPageUp.addActionListener(controlAction);
        btnPageDown.addActionListener(controlAction);
        btnKSong.setActionCommand("kSong");
        btnTop.setActionCommand("top");
        btnDelete.setActionCommand("delete");
        btnPick.setActionCommand("pick");
        btnPageUp.setActionCommand("pageUp");
        btnPageDown.setActionCommand("pageDown");
        //添加控件
        playlistPanel.add(btnKSong);
        playlistPanel.add(btnTop);
        playlistPanel.add(btnDelete);
        pinYinPanel.add(tfSearch);
        singerPanel.add(tfSingerSearch);
        stylePanel.add(cbStyle);
        pickPanel.add(btnPick);
        pagePanel.add(btnPageUp);
        pagePanel.add(lbPage);
        pagePanel.add(btnPageDown);
        toPlaylistPanel();
    }

    /**
     * 刷新下拉框
     *
     * @param stylePOList 类型集合
     */
    public void refreshComboBox(List<StylePO> stylePOList) {
        int count = cbStyle.getItemCount();
        for (int i = 1; i < count; i++) {
            cbStyle.removeItemAt(1);
        }
        for (StylePO stylePOPO : stylePOList) {
            cbStyle.addItem(stylePOPO);
        }
    }

    /**
     * 切换至已点歌曲面板
     */
    public void toPlaylistPanel() {
        removeAll();
        add(playlistPanel, BorderLayout.WEST);
        add(pagePanel, BorderLayout.EAST);
        repaint();
    }

    /**
     * 切换至拼音点歌面板
     */
    public void toPinYinPanel() {
        removeAll();
        add(pinYinPanel, BorderLayout.WEST);
        add(pickPanel);
        add(pagePanel, BorderLayout.EAST);
        repaint();
    }

    /**
     * 切换至歌星点歌面板
     */
    public void toSingerPanel() {
        removeAll();
        add(singerPanel, BorderLayout.WEST);
        add(pickPanel);
        add(pagePanel, BorderLayout.EAST);
        repaint();
    }

    /**
     * 切换至类型点歌面板
     */
    public void toStylePanel() {
        removeAll();
        add(stylePanel, BorderLayout.WEST);
        add(pickPanel);
        add(pagePanel, BorderLayout.EAST);
        repaint();
    }

    /**
     * 切换至热榜推荐面板
     */
    public void toHotPanel() {
        removeAll();
        add(pickPanel, BorderLayout.WEST);
        add(pagePanel, BorderLayout.EAST);
        repaint();
    }

    //get和set方法
    public JComboBox<StylePO> getCbStyle() {
        return cbStyle;
    }

    public JTextField getTfSearch() {
        return tfSearch;
    }

    public JTextField getTfSingerSearch() {
        return tfSingerSearch;
    }

    public JLabel getLbPage() {
        return lbPage;
    }
}
