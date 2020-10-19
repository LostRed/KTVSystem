package com.lostred.ktv.run;

import com.lostred.ktv.view.PlayerServerView;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * 主程序入口
 */
public class PSStart {
    public static void main(String[] args) {
        initGlobalFontSetting(new Font("微软雅黑", Font.PLAIN, 13));
        //使用本地操作系统界面风格
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        //显示主界面
        SwingUtilities.invokeLater(() -> PlayerServerView.getPlayerServerView().setVisible(true));
    }

    /**
     * 设置全局字体
     *
     * @param font 字体
     */
    public static void initGlobalFontSetting(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
