package com.lostred.ktv.view.songInfoPanel;

import com.lostred.ktv.controller.TableMouse;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.FileSizeUtil;
import com.lostred.ktv.view.ManagementSystemView;

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
        setGridColor(new Color(210, 210, 210));
        setIntercellSpacing(new Dimension(1, 0));
        setRowHeight(50);
        setShowHorizontalLines(false);
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
        getColumnModel().getColumn(5).setCellRenderer(render2);
        //设置列宽
        getColumnModel().getColumn(0).setPreferredWidth(50);
        getColumnModel().getColumn(1).setPreferredWidth(150);
        getColumnModel().getColumn(2).setPreferredWidth(100);
        getColumnModel().getColumn(3).setPreferredWidth(50);
        getColumnModel().getColumn(4).setPreferredWidth(100);
        getColumnModel().getColumn(5).setPreferredWidth(50);
        getColumnModel().getColumn(6).setPreferredWidth(80);
        getColumnModel().getColumn(7).setPreferredWidth(300);
        //添加监听
        TableMouse tableMouse = new TableMouse();
        addMouseListener(tableMouse);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        int modelRow = convertRowIndexToModel(row);
        Component comp = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(modelRow)) {
            if (modelRow % 2 != 0) {
                comp.setBackground(new Color(225, 225, 225));
            } else {
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
        ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
        int limit = mv.getLimit();
        if (count % limit == 0 && count != 0) {
            mv.setTotalPage(count / limit);
            if (mv.getCurrentPage() > mv.getTotalPage()) {
                mv.setCurrentPage(mv.getCurrentPage() - 1);
            }
        } else {
            mv.setTotalPage(count / limit + 1);
        }
        mv.setOffset((mv.getCurrentPage() - 1) * limit);
        //设置页码标签
        mv.getControlPanel().getLbPage().setText(mv.getCurrentPage() + "/" + mv.getTotalPage());
    }

    /**
     * 刷新表格
     *
     * @param list 歌曲集合
     */
    public void refreshTable(List<SongPO> list) {
        model.setRowCount(0);
        for (SongPO songPO : list) {
            Object[] data = {songPO.getSongId(), songPO, songPO.getSinger(), songPO.getStyle(), songPO.getSongInitial(), songPO.getSongTime(),
                    FileSizeUtil.format(songPO.getFileSize()), songPO.getFilePath()};
            model.addRow(data);
        }
    }
}
