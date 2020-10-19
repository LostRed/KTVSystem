package com.lostred.ktv.view;

import com.lostred.ktv.controller.LoginViewAction;
import com.lostred.ktv.controller.LoginViewWindow;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.service.LocalService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * 登录主界面
 */
public class LoginView extends JFrame {
    /**
     * 登录主界面单例对象
     */
    private static LoginView LOGIN_VIEW;
    /**
     * 房间号文本框
     */
    private final JTextField tfRoomId = new JTextField();
    /**
     * 登录按钮
     */
    private final JButton btnLogin = new JButton("登录");
    /**
     * 密码文本框
     */
    private JPasswordField pfPassword = new JPasswordField();

    /**
     * 构造登录主界面
     */
    private LoginView() {
        //初始化控件
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Image image = new ImageIcon("VODClient/data/logo.jpg").getImage();
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        JPanel mainPanel = new JPanel();
        JPanel roomIdPanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        JPanel btnPanel = new JPanel();
        JLabel lbRoomId = new JLabel("房间号：", JLabel.RIGHT);
        JLabel lbPassword = new JLabel("密码：", JLabel.RIGHT);
        JButton btnClear = new JButton("清除");
        //设置控件
        mainPanel.setBorder(new EmptyBorder(15, 0, 10, 0));
        btnPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        topPanel.setPreferredSize(new Dimension(360, 120));
        lbRoomId.setPreferredSize(new Dimension(55, 28));
        lbPassword.setPreferredSize(new Dimension(55, 28));
        tfRoomId.setPreferredSize(new Dimension(150, 28));
        pfPassword.setPreferredSize(new Dimension(150, 28));
        mainPanel.setLayout(new BorderLayout());
        getRootPane().setDefaultButton(btnLogin);
        //添加控件
        roomIdPanel.add(lbRoomId);
        roomIdPanel.add(tfRoomId);
        passwordPanel.add(lbPassword);
        passwordPanel.add(pfPassword);
        mainPanel.add(roomIdPanel, BorderLayout.NORTH);
        mainPanel.add(passwordPanel);
        btnPanel.add(btnLogin);
        btnPanel.add(btnClear);
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel);
        add(btnPanel, BorderLayout.SOUTH);
        //添加监听
        LoginViewWindow loginViewWindow = new LoginViewWindow();
        LoginViewAction loginViewAction = new LoginViewAction();
        addWindowListener(loginViewWindow);
        btnLogin.addActionListener(loginViewAction);
        btnClear.addActionListener(loginViewAction);
        btnLogin.setActionCommand("login");
        btnClear.setActionCommand("clear");
        //设置窗口
        setTitle("KTV登录界面");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 获取单例登录主界面
     *
     * @return 登录主界面单例对象
     */
    public static LoginView getLoginView() {
        if (LOGIN_VIEW == null) {
            synchronized (LoginView.class) {
                if (LOGIN_VIEW == null) {
                    LOGIN_VIEW = new LoginView();
                }
            }
        }
        return LOGIN_VIEW;
    }

    /**
     * 跳转到点播主界面
     *
     * @param roomPO 房间PO
     */
    public void toVODClientView(RoomPO roomPO) {
        dispose();
        VODClientView vodCv = VODClientView.getVODClientView();
        vodCv.setRoomPO(roomPO);
        vodCv.getLbRoom().setText("当前房间：" + roomPO.getRoomId());
        vodCv.setVisible(true);
        //下拉框初始化
        List<StylePO> stylePOList = LocalService.getLocalService().queryAllStyle();
        vodCv.getSongInfoPanel().getControlPanel().refreshComboBox(stylePOList);
    }

    //get和set方法
    public JTextField getTfRoomId() {
        return tfRoomId;
    }

    public JPasswordField getPfPassword() {
        return pfPassword;
    }

    public void setPfPassword(JPasswordField pfPassword) {
        this.pfPassword = pfPassword;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }
}
