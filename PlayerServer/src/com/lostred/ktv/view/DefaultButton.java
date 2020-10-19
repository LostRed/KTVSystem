package com.lostred.ktv.view;

import javax.swing.*;

/**
 * 界面默认按钮
 */
public class DefaultButton extends JButton {
    /**
     * 构造界面默认按钮
     *
     * @param text 按钮文本
     */
    public DefaultButton(String text) {
        //设置按钮
        super(text);
        setFocusable(false);
        setFocusPainted(false);
    }
}
