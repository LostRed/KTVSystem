package com.lostred.ktv.view;

import com.lostred.ktv.controller.VODCViewWindow;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.view.SongInfoPanel.SongInfoPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 点播主界面
 */
public class VODClientView extends JFrame {
    /**
     * 点播主界面单例对象
     */
    private static VODClientView VOD_CLIENT_VIEW;
    /**
     * 当前播放歌曲标签
     */
    private final JLabel lbSong = new JLabel("当前播放歌曲：无");
    /**
     * 已点歌曲标签
     */
    private final JLabel lbTheme = new JLabel("已点歌曲", JLabel.RIGHT);
    /**
     * 当前房间标签
     */
    private final JLabel lbRoom = new JLabel("当前房间：0000");
    /**
     * 服务端状态标签
     */
    private final JLabel lbServer = new JLabel("点播服务端：未连接");
    /**
     * 播放端状态标签
     */
    private final JLabel lbPlayer = new JLabel("播放端：未连接");
    /**
     * 每页显示的歌曲数量
     */
    private final int limit = 5;
    private SongInfoPanel songInfoPanel = new SongInfoPanel();
    /**
     * 已点歌曲集合
     */
    private List<PlaylistPO> playlist = new ArrayList<>();
    /**
     * 模式
     */
    private Mode mode = Mode.PLAYLIST;
    /**
     * 当前房间
     */
    private RoomPO roomPO;
    /**
     * 是否有播放端进入房间，true为有，false为没有
     */
    private boolean entered;
    /**
     * 当前页码
     */
    private int currentPage = 1;
    /**
     * 总页码
     */
    private int totalPage = 1;
    /**
     * 查询偏移量
     */
    private int offset;

    /**
     * 构造点播主界面
     */
    private VODClientView() {
        //初始化控件
        JPanel mainPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel statusPanel = new JPanel();
        //设置控件
        mainPanel.setLayout(new BorderLayout());
        leftPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new GridLayout(1, 2));
        statusPanel.setLayout(new GridLayout(3, 1, 0, 5));
        mainPanel.setBorder(new EmptyBorder(10, 50, 15, 50));
        topPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        leftPanel.setBorder(new EmptyBorder(0, 0, 0, 30));
        statusPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        lbTheme.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        lbServer.setForeground(new Color(150, 50, 50));
        lbPlayer.setForeground(new Color(150, 50, 50));
        //添加控件
        topPanel.add(lbSong);
        topPanel.add(lbTheme);
        statusPanel.add(lbRoom);
        statusPanel.add(lbServer);
        statusPanel.add(lbPlayer);
        //面板
        TabPanel tabPanel = new TabPanel();
        leftPanel.add(tabPanel);
        leftPanel.add(statusPanel, BorderLayout.SOUTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(songInfoPanel);
        add(mainPanel);
        //添加监听
        VODCViewWindow vodcViewWindow = new VODCViewWindow();
        addWindowListener(vodcViewWindow);
        //设置窗口
        setTitle("KTV点播界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 获取单例点播主界面
     *
     * @return 点播主界面单例对象
     */
    public static VODClientView getVODClientView() {
        if (VOD_CLIENT_VIEW == null) {
            synchronized (VODClientView.class) {
                if (VOD_CLIENT_VIEW == null) {
                    VOD_CLIENT_VIEW = new VODClientView();
                }
            }
        }
        return VOD_CLIENT_VIEW;
    }

    /**
     * 回到第一页
     */
    public void toPageOne() {
        currentPage = 1;
        offset = 0;
    }

    /**
     * 上一页
     */
    public void pageUp() {
        if (currentPage > 1) {
            currentPage--;
        }
        offset = (currentPage - 1) * limit;
    }

    /**
     * 下一页
     */
    public void pageDown() {
        if (currentPage < totalPage) {
            currentPage++;
        }
        offset = (currentPage - 1) * limit;
    }

    /**
     * 改变服务器状态
     *
     * @param status 服务端连接状态，1为已连接，0为未连接，-1为重连中
     */
    public void changeServerStatus(int status) {
        if (status == 1) {
            lbServer.setText("点播服务端：已连接");
            lbServer.setForeground(new Color(50, 150, 50));
        } else if (status == 0) {
            lbServer.setText("点播服务端：未连接");
            lbServer.setForeground(new Color(150, 50, 50));
        } else {
            lbServer.setText("点播服务端：重连中");
            lbServer.setForeground(new Color(150, 150, 50));
        }
    }

    /**
     * 改变播放端状态
     *
     * @param status 点播端连接状态，1为已连接，0为未连接，-1为重连中
     */
    public void changePlayerStatus(int status) {
        if (status == 1) {
            lbPlayer.setText("播放端：已连接");
            lbPlayer.setForeground(new Color(50, 150, 50));
        } else if (status == 0) {
            lbPlayer.setText("播放端：未连接");
            lbPlayer.setForeground(new Color(150, 50, 50));
        } else {
            lbPlayer.setText("播放端：重连中");
            lbPlayer.setForeground(new Color(150, 150, 50));
        }
    }

    //get和set方法
    public SongInfoPanel getSongInfoPanel() {
        return songInfoPanel;
    }

    public void setSongInfoPanel(SongInfoPanel songInfoPanel) {
        this.songInfoPanel = songInfoPanel;
    }

    public JLabel getLbSong() {
        return lbSong;
    }

    public JLabel getLbTheme() {
        return lbTheme;
    }

    public JLabel getLbRoom() {
        return lbRoom;
    }

    public JLabel getLbServer() {
        return lbServer;
    }

    public JLabel getLbPlayer() {
        return lbPlayer;
    }

    public List<PlaylistPO> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<PlaylistPO> playlist) {
        this.playlist = playlist;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public RoomPO getRoomPO() {
        return roomPO;
    }

    public void setRoomPO(RoomPO roomPO) {
        this.roomPO = roomPO;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * 模式枚举类
     */
    public enum Mode {
        PLAYLIST, PINYIN, SINGER, STYLE, HOT
    }
}
