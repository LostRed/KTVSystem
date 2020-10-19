package com.lostred.ktv.controller;

import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.service.LocalService;
import com.lostred.ktv.util.FileTypeFilter;
import com.lostred.ktv.util.SuffixFilter;
import com.lostred.ktv.view.ManagementSystemView;
import com.lostred.ktv.view.ProgressView;
import com.lostred.ktv.view.songInfoPanel.SongInfoTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 主界面动作监听
 */
public class ControlAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
        JFileChooser chooser = new JFileChooser();
        LocalService localService = LocalService.getService();
        int choice;
        int total;
        switch (command) {
            //单曲导入
            case "importOne":
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(new FileTypeFilter(".wav"));
                choice = chooser.showOpenDialog(mv);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    if (!file.getName().endsWith(".wav")) {
                        JOptionPane.showMessageDialog(mv, "请选择wav格式文件！");
                        return;
                    }
                    File[] files = new File[1];
                    files[0] = file;
                    new ImportThread(files).start();
                    ProgressView.getProgressView().setVisible(true);
                    //更改修改状态
                    MSViewWindow.MODIFIED = true;
                }
                break;
            //批量导入
            case "importBatch":
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setFileFilter(new FileTypeFilter(".wav"));
                choice = chooser.showOpenDialog(mv);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File dir = chooser.getSelectedFile();
                    File[] files = dir.listFiles(new SuffixFilter(".wav"));
                    new ImportThread(files).start();
                    ProgressView.getProgressView().setVisible(true);
                    //更改修改状态
                    MSViewWindow.MODIFIED = true;
                }
                break;
            //修改
            case "modify":
                SongInfoTable songInfoTable = mv.getSongInfoScrollPane().getTable();
                int selectRow = songInfoTable.getSelectedRow();
                //选中表格中的某一行时
                if (selectRow != -1) {
                    SongPO songPO = (SongPO) songInfoTable.getValueAt(selectRow, 1);
                    localService.showEditView(songPO);
                } else {
                    JOptionPane.showMessageDialog(mv, "请先选中一首歌曲！");
                }
                break;
            //删除
            case "delete":
                songInfoTable = mv.getSongInfoScrollPane().getTable();
                selectRow = songInfoTable.getSelectedRow();
                if (selectRow != -1) {
                    SongPO songPO = (SongPO) songInfoTable.getValueAt(selectRow, 1);
                    songPO.setModifyInfo("delete");
                    total = localService.modifySong(songPO);
                    localService.refreshView();
                    JOptionPane.showMessageDialog(mv, "成功删除了" + total + "首歌曲！");
                }
                //更改修改状态
                MSViewWindow.MODIFIED = true;
                break;
            //生成文件
            case "createFile":
                List<SongPO> list = localService.queryAllSong();
                try {
                    localService.createFile(list);
                    JOptionPane.showMessageDialog(mv, "成功生成文件！");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mv, "生成文件失败！");
                }
                //更改修改状态
                MSViewWindow.MODIFIED = false;
                break;
            //检测
            case "check":
                songInfoTable = mv.getSongInfoScrollPane().getTable();
                selectRow = songInfoTable.getSelectedRow();
                //选中表格中的某一行时
                if (selectRow != -1) {
                    String filePath = songInfoTable.getValueAt(selectRow, 7).toString();
                    File file = new File(filePath);
                    if (file.exists()) {
                        JOptionPane.showMessageDialog(mv, "该歌曲文件存在！");
                    } else {
                        JOptionPane.showMessageDialog(mv, "该歌曲文件不存在！");
                    }
                } else {
                    JOptionPane.showMessageDialog(mv, "请先选中一首歌曲！");
                }
                break;
            //上一页
            case "pageUp":
                mv.pageUp();
                localService.refreshView();
                break;
            //下一页
            case "pageDown":
                mv.pageDown();
                localService.refreshView();
                break;
        }
    }
}
