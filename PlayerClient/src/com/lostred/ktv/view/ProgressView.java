package com.lostred.ktv.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 进度条界面
 */
public class ProgressView extends JDialog {
    /**
     * 进度条界面单例对象
     */
    private static ProgressView PROGRESS_VIEW;
    /**
     * 进度条
     */
    private final JProgressBar progressBar = new JProgressBar();

    /**
     * 构造进度条界面
     */
    private ProgressView() {
        //初始化控件
        JPanel mainPanel = new JPanel();
        JLabel lbLoading = new JLabel("请稍等...", JLabel.LEFT);
        //设置控件
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        progressBar.setPreferredSize(new Dimension(200, 20));
        //添加控件
        mainPanel.add(lbLoading, BorderLayout.NORTH);
        mainPanel.add(progressBar);
        add(mainPanel);
        //设置窗口
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setModal(true);
        pack();
        setLocationRelativeTo(PlayerClientView.getPlayerClientView());
    }

    /**
     * 获取单例进度条界面
     *
     * @return 进度条界面
     */
    public static ProgressView getProgressView() {
        if (PROGRESS_VIEW == null) {
            synchronized (ProgressView.class) {
                if (PROGRESS_VIEW == null) {
                    PROGRESS_VIEW = new ProgressView();
                }
            }
        }
        return PROGRESS_VIEW;
    }

    //get和set方法
    public JProgressBar getProgressBar() {
        return progressBar;
    }
}
