package com.lostred.ktv.controller;

import com.lostred.ktv.po.SingerPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.InitialUtil;
import com.lostred.ktv.view.ManagementSystemView;
import com.lostred.ktv.view.SongInfoEditView;
import com.lostred.ktv.view.songInfoPanel.SongInfoTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 编辑窗口动作监听
 */
public class EditAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        SongInfoEditView ev = SongInfoEditView.getEditDialog();
        LocalService localService = LocalService.getService();
        int num;
        switch (command) {
            //添加歌手
            case "addSinger":
                String singerName = ev.getTfSinger().getText();
                if (singerName.equals("")) {
                    JOptionPane.showMessageDialog(ev, "请输入歌手名！");
                    return;
                }
                String singerInitial = InitialUtil.convertToInitial(singerName);
                SingerPO singerPO = new SingerPO(0, singerName, singerInitial);
                num = localService.addSinger(singerPO);
                if (num > 0) {
                    //更新歌手下拉框
                    List<SingerPO> singerPOList = LocalService.getService().queryAllSinger();
                    ev.refreshComboBox(singerPOList, null);
                    //把下拉框选中新增歌手类型
                    int count = ev.getCbSinger().getItemCount();
                    ev.getCbSinger().setSelectedIndex(count - 1);
                    ev.getTfSinger().setText("");
                    JOptionPane.showMessageDialog(ev, "成功添加了歌手：" + singerName + "！");
                }
                break;
            //添加类型
            case "addStyle":
                String styleName = ev.getTfStyle().getText();
                if (styleName.equals("")) {
                    JOptionPane.showMessageDialog(ev, "请输入歌曲类型！");
                    return;
                }
                StylePO stylePO = new StylePO(0, styleName);
                num = localService.addStyle(stylePO);
                if (num > 0) {
                    //更新歌曲类型下拉框
                    List<StylePO> stylePOList = LocalService.getService().queryAllStyle();
                    ev.refreshComboBox(null, stylePOList);
                    //把下拉框选中新增歌曲类型
                    int count = ev.getCbStyle().getItemCount();
                    ev.getCbStyle().setSelectedIndex(count - 1);
                    ev.getTfStyle().setText("");
                    JOptionPane.showMessageDialog(ev, "成功添加了歌曲类型：" + styleName + "！");
                }
                break;
            //确定
            case "sure":
                ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
                SongInfoTable songInfoTable = mv.getSongInfoScrollPane().getTable();
                int selectRow = songInfoTable.getSelectedRow();
                SongPO songPO = (SongPO) songInfoTable.getValueAt(selectRow, 1);
                //歌曲名填写为空时
                if (ev.getTfSongName().getText().equals("")) {
                    JOptionPane.showMessageDialog(ev, "歌曲名不能为空！");
                    return;
                } else {
                    songPO.setSongName(ev.getTfSongName().getText());
                }
                songPO.setSinger((SingerPO) ev.getCbSinger().getSelectedItem());
                songPO.setStyle((StylePO) ev.getCbStyle().getSelectedItem());
                songPO.setSongInitial(ev.getTfSongInitial().getText().toUpperCase());
                songPO.setModifyInfo("update");
                ev.dispose();
                num = localService.modifySong(songPO);
                //刷新界面
                localService.refreshView();
                JOptionPane.showMessageDialog(mv, "成功修改了" + num + "首歌曲！");
                //更改修改状态
                MSViewWindow.MODIFIED = true;
                break;
            //取消
            case "cancel":
                ev.dispose();
                break;
        }
    }
}
