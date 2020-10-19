package com.lostred.ktv.view;

import com.lostred.ktv.controller.ComboBoxPopupMenu;
import com.lostred.ktv.controller.SRViewAction;
import com.lostred.ktv.controller.SRViewWindow;
import com.lostred.ktv.po.RoomPO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择房间主界面
 */
public class SelectRoomView extends JFrame {
    /**
     * 选择房间主界面单例对象
     */
    private static SelectRoomView SELECT_ROOM_VIEW;
    /**
     * 房间下拉框
     */
    private final JComboBox<RoomPO> cbRoom = new JComboBox<>();
    /**
     * 空闲房间集合
     */
    private List<RoomPO> roomPOList = new ArrayList<>();

    /**
     * 构造选择房间主界面
     */
    private SelectRoomView() {
        //初始化控件
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JLabel lbSelectRoom = new JLabel("请选择房间");
        DefaultButton btnEnter = new DefaultButton("进入房间");
        //设置控件
        topPanel.setBorder(new EmptyBorder(25, 70, 10, 70));
        bottomPanel.setBorder(new EmptyBorder(10, 70, 25, 70));
        cbRoom.setPreferredSize(new Dimension(100, 28));
        cbRoom.setFocusable(false);
        //添加控件
        topPanel.add(lbSelectRoom);
        topPanel.add(cbRoom);
        bottomPanel.add(btnEnter);
        add(topPanel);
        add(bottomPanel, BorderLayout.SOUTH);
        //添加监听
        SRViewWindow srViewWindow = new SRViewWindow();
        SRViewAction srViewAction = new SRViewAction();
        ComboBoxPopupMenu comboBoxPopupMenu = new ComboBoxPopupMenu();
        addWindowListener(srViewWindow);
        cbRoom.addPopupMenuListener(comboBoxPopupMenu);
        btnEnter.addActionListener(srViewAction);
        btnEnter.setActionCommand("enter");
        //设置窗口
        setTitle("选择房间界面");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 获取单例选择房间主界面
     *
     * @return 选择房间主界面单例对象
     */
    public static SelectRoomView getSelectRoomView() {
        if (SELECT_ROOM_VIEW == null) {
            synchronized (SelectRoomView.class) {
                if (SELECT_ROOM_VIEW == null) {
                    SELECT_ROOM_VIEW = new SelectRoomView();
                }
            }
        }
        return SELECT_ROOM_VIEW;
    }

    /**
     * 刷新房间列表
     */
    public void refreshComboBox() {
        cbRoom.removeAllItems();
        for (RoomPO roomPO : roomPOList) {
            cbRoom.addItem(roomPO);
        }
    }

    /**
     * 跳转到播放主界面
     *
     * @param roomPO 房间PO
     */
    public void toPlayerClientView(RoomPO roomPO) {
        dispose();
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        pcv.setRoomPO(roomPO);
        pcv.getLbRoom().setText("当前房间：" + roomPO.getRoomId());
        pcv.setVisible(true);
        pcv.getProgressThread().start();
    }

    //get和set方法
    public JComboBox<RoomPO> getCbRoom() {
        return cbRoom;
    }

    public void setRoomPOList(List<RoomPO> roomPOList) {
        this.roomPOList = roomPOList;
    }
}
