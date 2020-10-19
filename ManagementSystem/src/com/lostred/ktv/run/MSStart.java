package com.lostred.ktv.run;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.view.ManagementSystemView;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;

/**
 * 主程序入口
 */
public class MSStart {
    public static void main(String[] args) {
        initGlobalFontSetting(new Font("微软雅黑", Font.PLAIN, 13));
        //使用本地操作系统界面风格
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
        //初始化歌曲信息表格
        LocalService localService = LocalService.getService();
        String keyword = mv.getTfSearch().getText();
        int total = localService.countFuzzyQuerySong(keyword);
        mv.getSongInfoScrollPane().getTable().refreshPage(total);
        List<SongPO> songPOList = localService.fuzzyQuerySongByPage(keyword, mv.getLimit(), mv.getOffset());
        mv.getSongInfoScrollPane().getTable().refreshTable(songPOList);
        //初始化类型下拉框
        List<StylePO> stylePOList = localService.queryAllStyle();
        mv.refreshComboBox(stylePOList);
        //显示主界面
        SwingUtilities.invokeLater(() -> mv.setVisible(true));
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
