package com.lostred.ktv.view;

import com.lostred.ktv.controller.ControlAction;

import javax.swing.*;
import java.awt.*;

/**
 * 操作面板
 */
public class ControlPanel extends JPanel {
    /**
     * 页码标签
     */
    private final JLabel lbPage = new JLabel("", JLabel.CENTER);

    /**
     * 构造操作面板
     */
    public ControlPanel() {
        //初始化控件
        JPanel controlPanel = new JPanel();
        JPanel pagePanel = new JPanel();
        DefaultButton btnImportOne = new DefaultButton("单曲导入");
        DefaultButton btnImportBatch = new DefaultButton("批量导入");
        DefaultButton btnModify = new DefaultButton("修改");
        DefaultButton btnDelete = new DefaultButton("删除");
        DefaultButton btnCreateFile = new DefaultButton("生成文件");
        DefaultButton btnCheck = new DefaultButton("检测");
        DefaultButton btnPageUp = new DefaultButton("上一页");
        DefaultButton btnPageDown = new DefaultButton("下一页");
        //设置控件
        setLayout(new BorderLayout());
        lbPage.setPreferredSize(new Dimension(40, 28));
        //添加监听
        ControlAction controlAction = new ControlAction();
        btnImportOne.addActionListener(controlAction);
        btnImportBatch.addActionListener(controlAction);
        btnModify.addActionListener(controlAction);
        btnDelete.addActionListener(controlAction);
        btnCreateFile.addActionListener(controlAction);
        btnCheck.addActionListener(controlAction);
        btnPageUp.addActionListener(controlAction);
        btnPageDown.addActionListener(controlAction);
        btnImportOne.setActionCommand("importOne");
        btnImportBatch.setActionCommand("importBatch");
        btnModify.setActionCommand("modify");
        btnDelete.setActionCommand("delete");
        btnCreateFile.setActionCommand("createFile");
        btnCheck.setActionCommand("check");
        btnPageUp.setActionCommand("pageUp");
        btnPageDown.setActionCommand("pageDown");
        //添加控件
        controlPanel.add(btnImportOne);
        controlPanel.add(btnImportBatch);
        controlPanel.add(btnModify);
        controlPanel.add(btnDelete);
        controlPanel.add(btnCreateFile);
        controlPanel.add(btnCheck);
        pagePanel.add(btnPageUp);
        pagePanel.add(lbPage);
        pagePanel.add(btnPageDown);
        add(controlPanel, BorderLayout.WEST);
        add(pagePanel, BorderLayout.EAST);
    }

    //get和set方法
    public JLabel getLbPage() {
        return lbPage;
    }
}
