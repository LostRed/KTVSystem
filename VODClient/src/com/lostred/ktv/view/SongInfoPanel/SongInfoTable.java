package com.lostred.ktv.view.SongInfoPanel;

import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.view.VODClientView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * 歌曲信息表格
 */
public class SongInfoTable extends JTable {
    /**
     * 歌曲信息表格模型
     */
    private final SongInfoTableModel model = new SongInfoTableModel();

    /**
     * 构造歌曲信息表格
     */
    public SongInfoTable() {
        //设置表格
        setModel(model);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setGridColor(new Color(210, 210, 210));
        setIntercellSpacing(new Dimension(1, 0));
        setRowHeight(50);
        setShowHorizontalLines(true);
        setFocusable(false);
        //设置表头
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);
        //设置右对齐
        DefaultTableCellRenderer render1 = new DefaultTableCellRenderer();
        render1.setHorizontalAlignment(SwingConstants.RIGHT);
        getColumnModel().getColumn(0).setCellRenderer(render1);
        getColumnModel().getColumn(6).setCellRenderer(render1);
        //设置居中对齐
        DefaultTableCellRenderer render2 = new DefaultTableCellRenderer();
        render2.setHorizontalAlignment(SwingConstants.CENTER);
        getColumnModel().getColumn(3).setCellRenderer(render2);
        getColumnModel().getColumn(4).setCellRenderer(render2);
        //设置列宽
        getColumnModel().getColumn(0).setPreferredWidth(50);
        getColumnModel().getColumn(1).setPreferredWidth(150);
        getColumnModel().getColumn(2).setPreferredWidth(100);
        getColumnModel().getColumn(3).setPreferredWidth(50);
        getColumnModel().getColumn(4).setPreferredWidth(100);
        getColumnModel().getColumn(5).setPreferredWidth(50);
        getColumnModel().getColumn(6).setPreferredWidth(60);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        int modelRow = convertRowIndexToModel(row);
        Component comp = super.prepareRenderer(renderer, row, column);
        //非选中行
        if (!isRowSelected(modelRow)) {
            if (modelRow % 2 != 0) {
                //设置表格背景色
                comp.setBackground(new Color(225, 225, 225));
            } else {
                //设置表格背景色
                comp.setBackground(getBackground());
            }
        }
        return comp;
    }

    /**
     * 刷新页码
     *
     * @param count 歌曲总数
     */
    public void refreshPage(int count) {
        VODClientView mv = VODClientView.getVODClientView();
        int limit = mv.getLimit();
        //总数除以每页显示数量余数为0且总数不为0时
        if (count % limit == 0 && count != 0) {
            mv.setTotalPage(count / limit);
            //当前页码大于总页码时
            if (mv.getCurrentPage() > mv.getTotalPage()) {
                mv.setCurrentPage(mv.getCurrentPage() - 1);
            }
        } else {
            mv.setTotalPage(count / limit + 1);
        }
        mv.setOffset((mv.getCurrentPage() - 1) * limit);
        //设置页码标签
        mv.getSongInfoPanel().getControlPanel().getLbPage().setText(mv.getCurrentPage() + "/" + mv.getTotalPage());
    }

    /**
     * 刷新表格
     *
     * @param list 歌曲集合
     */
    public void refreshTable(List<SongPO> list) {
        model.setRowCount(0);
        int index = VODClientView.getVODClientView().getOffset();//序号
        for (SongPO songPO : list) {
            index++;
            Object[] data = {index, songPO, songPO.getSinger(), songPO.getStyle(), songPO.getSongInitial(), songPO.getSongTime()};
            model.addRow(data);
        }
    }

    /**
     * 刷新已点歌曲表格
     */
    public void refreshPlaylistTable() {
        model.setRowCount(0);
        int limit = VODClientView.getVODClientView().getLimit();
        int offset = VODClientView.getVODClientView().getOffset();
        List<PlaylistPO> playlist = VODClientView.getVODClientView().getPlaylist();
        List<PlaylistPO> list;
        //播放列表歌曲总数大于每页显示数量时
        if (playlist.size() > limit) {
            list = playlist.subList(offset, offset + limit);
        } else {
            list = playlist.subList(offset, playlist.size());
        }
        int index = VODClientView.getVODClientView().getOffset();//序号
        for (PlaylistPO playlistPO : list) {
            index++;
            Object[] data = {index, playlistPO, playlistPO.getSongPO().getSinger(), playlistPO.getSongPO().getStyle(),
                    playlistPO.getSongPO().getSongInitial(), playlistPO.getSongPO().getSongTime()};
            model.addRow(data);
        }
    }

    /**
     * 刷新热榜表格
     *
     * @param list 歌曲集合
     */
    public void refreshHotTable(List<SongPO> list) {
        model.setRowCount(0);
        int index = VODClientView.getVODClientView().getOffset();//序号
        for (SongPO songPO : list) {
            index++;
            Object[] data = {index, songPO, songPO.getSinger(), songPO.getStyle(), songPO.getSongInitial(), songPO.getSongTime(), songPO.getSongHot()};
            model.addRow(data);
        }
    }
}
