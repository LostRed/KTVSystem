package com.lostred.ktv.view;

import com.lostred.ktv.controller.ComboBoxItem;
import com.lostred.ktv.controller.MSViewWindow;
import com.lostred.ktv.controller.TFSearchDocument;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.view.songInfoPanel.SongInfoScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * 后台管理系统主界面
 */
public class ManagementSystemView extends JFrame {
    /**
     * 后台管理系统主界面单例对象
     */
    private static ManagementSystemView MANAGEMENT_SYSTEM_VIEW;
    /**
     * 控制面板
     */
    private final ControlPanel controlPanel = new ControlPanel();
    /**
     * 歌曲信息滚动条面板
     */
    private final SongInfoScrollPane songInfoScrollPane = new SongInfoScrollPane();
    /**
     * 分类下拉框
     */
    private final JComboBox<StylePO> cbStyle = new JComboBox<>();
    /**
     * 拼音搜索文本框
     */
    private final JTextField tfSearch = new JTextField();
    /**
     * 每页显示歌曲数量
     */
    private final int limit = 5;
    /**
     * 当前页码
     */
    private int currentPage = 1;
    /**
     * 总页码
     */
    private int totalPage = 1;
    /**
     * 查询偏移量
     */
    private int offset;

    /**
     * 构造后台管理系统主界面
     */
    private ManagementSystemView() {
        //初始化控件
        JPanel mainPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel stylePanel = new JPanel();
        JPanel searchPanel = new JPanel();
        JLabel lbStyle = new JLabel("歌曲类型", JLabel.RIGHT);
        JLabel lbSearch = new JLabel("拼音搜索", JLabel.RIGHT);
        //设置控件
        mainPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        cbStyle.setPreferredSize(new Dimension(120, 28));
        tfSearch.setPreferredSize(new Dimension(120, 28));
        mainPanel.setBorder(new EmptyBorder(10, 50, 15, 50));
        topPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        controlPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        StylePO all = new StylePO(0, "全部");
        cbStyle.addItem(all);
        cbStyle.setFocusable(false);
        //添加控件
        stylePanel.add(lbStyle);
        stylePanel.add(cbStyle);
        searchPanel.add(lbSearch);
        searchPanel.add(tfSearch);
        topPanel.add(stylePanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(songInfoScrollPane);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        add(mainPanel);
        //添加监听
        MSViewWindow MSViewWindow = new MSViewWindow();
        TFSearchDocument tfSearchDocument = new TFSearchDocument();
        ComboBoxItem comboBoxItem = new ComboBoxItem();
        addWindowListener(MSViewWindow);
        tfSearch.getDocument().addDocumentListener(tfSearchDocument);
        cbStyle.addItemListener(comboBoxItem);
        //设置窗口
        setTitle("KTV后台管理端");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 获取单例后台管理系统主界面
     *
     * @return 后台管理系统主界面单例对象
     */
    public static ManagementSystemView getManagementSystemView() {
        if (MANAGEMENT_SYSTEM_VIEW == null) {
            synchronized (ManagementSystemView.class) {
                if (MANAGEMENT_SYSTEM_VIEW == null) {
                    MANAGEMENT_SYSTEM_VIEW = new ManagementSystemView();
                }
            }
        }
        return MANAGEMENT_SYSTEM_VIEW;
    }

    /**
     * 刷新下拉框
     *
     * @param stylePOList 类型集合
     */
    public void refreshComboBox(List<StylePO> stylePOList) {
        for (StylePO stylePOPO : stylePOList) {
            cbStyle.addItem(stylePOPO);
        }
    }

    /**
     * 上一页
     */
    public void pageUp() {
        if (currentPage > 1) {
            currentPage--;
        }
        offset = (currentPage - 1) * limit;
    }

    /**
     * 下一页
     */
    public void pageDown() {
        if (currentPage < totalPage) {
            currentPage++;
        }
        offset = (currentPage - 1) * limit;
    }

    //get和set方法
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public SongInfoScrollPane getSongInfoScrollPane() {
        return songInfoScrollPane;
    }

    public JComboBox<StylePO> getCbStyle() {
        return cbStyle;
    }

    public JTextField getTfSearch() {
        return tfSearch;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
