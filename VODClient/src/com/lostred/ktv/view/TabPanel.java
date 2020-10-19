package com.lostred.ktv.view;

import com.lostred.ktv.controller.TabAction;

import javax.swing.*;
import java.awt.*;

/**
 * 切换按钮面板
 */
public class TabPanel extends JPanel {
    /**
     * 分类点歌按钮
     */
    private final DefaultButton btnStyle = new DefaultButton("分类点歌");
    /**
     * 热门推荐按钮
     */
    private final DefaultButton btnHot = new DefaultButton("热门推荐");

    /**
     * 构造切换按钮面板
     */
    public TabPanel() {
        //初始化控件
        JPanel mainPanel = new JPanel();
        DefaultButton btnPlaylist = new DefaultButton("已点歌曲");
        DefaultButton btnPinYin = new DefaultButton("拼音点歌");
        DefaultButton btnSinger = new DefaultButton("歌星点歌");
        DefaultButton btnQuit = new DefaultButton("注销房间");
        //设置控件
        setLayout(new BorderLayout());
        mainPanel.setLayout(new GridLayout(6, 1, 0, 5));
        //添加控件
        //按钮
        mainPanel.add(btnPlaylist);
        mainPanel.add(btnPinYin);
        mainPanel.add(btnSinger);
        mainPanel.add(btnStyle);
        mainPanel.add(btnHot);
        mainPanel.add(btnQuit);
        add(mainPanel);
        //添加监听
        TabAction tabAction = new TabAction();
        btnPlaylist.addActionListener(tabAction);
        btnPinYin.addActionListener(tabAction);
        btnSinger.addActionListener(tabAction);
        btnStyle.addActionListener(tabAction);
        btnHot.addActionListener(tabAction);
        btnQuit.addActionListener(tabAction);
        btnPlaylist.setActionCommand("playlist");
        btnPinYin.setActionCommand("pinYin");
        btnSinger.setActionCommand("singer");
        btnStyle.setActionCommand("style");
        btnHot.setActionCommand("hot");
        btnQuit.setActionCommand("quit");
    }

    //get和set方法
    public DefaultButton getBtnStyle() {
        return btnStyle;
    }

    public DefaultButton getBtnHot() {
        return btnHot;
    }
}
