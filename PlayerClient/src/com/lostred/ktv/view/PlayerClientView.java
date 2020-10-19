package com.lostred.ktv.view;

import com.lostred.ktv.controller.*;
import com.lostred.ktv.po.PlaylistPO;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.util.JmfUtil;

import javax.media.Player;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 播放主界面
 */
public class PlayerClientView extends JFrame {
    /**
     * 播放主界面单例对象
     */
    private static PlayerClientView PLAYER_CLIENT_VIEW;
    /**
     * 播放进度条线程
     */
    private final ProgressThread progressThread = new ProgressThread();
    /**
     * 音乐播放进度条
     */
    private final JSlider slProgress = new JSlider();
    /**
     * 当前房间标签
     */
    private final JLabel lbRoom = new JLabel("当前房间：0000");
    /**
     * 正在播放歌曲标签
     */
    private final JLabel lbPlayingSong = new JLabel("正在播放：无");
    /**
     * 下一首标签
     */
    private final JLabel lbNextSong = new JLabel("下一首：无");
    /**
     * 服务端状态标签
     */
    private final JLabel lbServer = new JLabel("播放服务端：未连接");
    /**
     * 点播端状态标签
     */
    private final JLabel lbVOD = new JLabel("点播端：未连接");
    /**
     * 当前时间标签
     */
    private final JLabel lbCurrentTime = new JLabel("00:00", JLabel.RIGHT);
    /**
     * 总时间标签
     */
    private final JLabel lbTotalTime = new JLabel("00:00", JLabel.LEFT);
    /**
     * 切歌按钮
     */
    private final DefaultButton btnNext = new DefaultButton("切歌");
    /**
     * 静音按钮
     */
    private final DefaultButton btnSilence = new DefaultButton("静音");
    /**
     * 音量控制条
     */
    private JSlider slVolume = new JSlider();
    /**
     * 暂停按钮
     */
    private DefaultButton btnPause = new DefaultButton("暂停");
    /**
     * 已点歌曲集合
     */
    private List<PlaylistPO> playlist = new ArrayList<>();
    /**
     * 当前播放的文件
     */
    private File file;
    /**
     * 当前开始播放的时间
     */
    private int time;
    /**
     * 当前房间
     */
    private RoomPO roomPO;
    /**
     * 多媒体
     */
    private Player player;
    /**
     * 音量
     */
    private int volume = 100;

    /**
     * 构造播放主界面
     */
    private PlayerClientView() {
        //初始化控件
        JPanel topPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel statusPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        JPanel progressPanel = new JPanel();
        JPanel volumePanel = new JPanel();
        JLabel lbBlank = new JLabel();
        DefaultButton btnExit = new DefaultButton("退出");
        DefaultButton btnReplay = new DefaultButton("重唱");
        //设置控件
        lbRoom.setForeground(Color.WHITE);
        lbPlayingSong.setForeground(Color.WHITE);
        lbNextSong.setForeground(Color.WHITE);
        mainPanel.setBackground(Color.BLACK);
        slProgress.setValue(0);
        slProgress.setEnabled(false);
        slVolume.setValue(100);
        slVolume.setFocusable(false);
        topPanel.setOpaque(false);
        infoPanel.setOpaque(false);
        statusPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());
        statusPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        mainPanel.setLayout(new BorderLayout());
        controlPanel.setLayout(new FlowLayout());
        progressPanel.setLayout(new BorderLayout(5, 0));
        bottomPanel.setLayout(new BorderLayout());
        lbCurrentTime.setPreferredSize(new Dimension(50, 28));
        lbTotalTime.setPreferredSize(new Dimension(50, 28));
        slVolume.setPreferredSize(new Dimension(100, 28));
        topPanel.setBorder(new EmptyBorder(30, 50, 20, 50));
        bottomPanel.setBorder(new EmptyBorder(20, 50, 30, 50));
        lbServer.setForeground(new Color(150, 50, 50));
        lbVOD.setForeground(new Color(150, 50, 50));
        //添加控件
        infoPanel.add(lbRoom, BorderLayout.NORTH);
        infoPanel.add(lbPlayingSong);
        infoPanel.add(lbNextSong, BorderLayout.SOUTH);
        statusPanel.add(lbServer, BorderLayout.NORTH);
        statusPanel.add(lbVOD);
        topPanel.add(infoPanel, BorderLayout.WEST);
        topPanel.add(lbBlank);
        topPanel.add(statusPanel, BorderLayout.EAST);
        controlPanel.add(btnExit);
        controlPanel.add(btnPause);
        controlPanel.add(btnReplay);
        controlPanel.add(btnNext);
        progressPanel.add(lbCurrentTime, BorderLayout.WEST);
        progressPanel.add(slProgress);
        progressPanel.add(lbTotalTime, BorderLayout.EAST);
        volumePanel.add(btnSilence);
        volumePanel.add(slVolume);
        bottomPanel.add(controlPanel, BorderLayout.WEST);
        bottomPanel.add(progressPanel);
        bottomPanel.add(volumePanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        add(mainPanel);
        add(bottomPanel, BorderLayout.SOUTH);
        //添加监听
        PCViewWindow pcViewWindow = new PCViewWindow();
        ControlAction controlAction = new ControlAction();
        VolumeSlider volumeSlider = new VolumeSlider();
        addWindowListener(pcViewWindow);
        btnExit.addActionListener(controlAction);
        btnPause.addActionListener(controlAction);
        btnReplay.addActionListener(controlAction);
        btnNext.addActionListener(controlAction);
        btnSilence.addActionListener(controlAction);
        btnExit.setActionCommand("exit");
        btnPause.setActionCommand("pause");
        btnReplay.setActionCommand("replay");
        btnNext.setActionCommand("next");
        btnSilence.setActionCommand("silence");
        slVolume.addMouseListener(volumeSlider);
        slVolume.addChangeListener(volumeSlider);
        //设置窗口
        setTitle("KTV播放界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(880, 660));
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 获取单例播放主界面
     *
     * @return 播放主界面单例对象
     */
    public static PlayerClientView getPlayerClientView() {
        if (PLAYER_CLIENT_VIEW == null) {
            synchronized (PlayerClientView.class) {
                if (PLAYER_CLIENT_VIEW == null) {
                    PLAYER_CLIENT_VIEW = new PlayerClientView();
                }
            }
        }
        return PLAYER_CLIENT_VIEW;
    }

    /**
     * 显示正在播放的歌曲
     */
    public void showPlayingSong(PlaylistPO playlistPO) {
        if (playlistPO != null) {
            SongPO songPO = playlistPO.getSongPO();
            String text = songPO.getSinger().getSingerName() + " - " + songPO.getSongName();
            lbPlayingSong.setText("正在播放：" + text);
        } else {
            lbPlayingSong.setText("正在播放：无");
        }
    }

    /**
     * 显示下一首歌曲
     */
    public void showNextSong() {
        if (playlist.size() != 0) {
            SongPO songPO = playlist.get(0).getSongPO();
            String text = songPO.getSinger().getSingerName() + " - " + songPO.getSongName();
            lbNextSong.setText("下一首：" + text);
        } else {
            lbNextSong.setText("下一首：无");
        }
    }

    /**
     * 加载音乐
     */
    public void realizeMusic() {
        player = JmfUtil.getPlayer(file);
        MediaController mediaController = new MediaController();
        player.addControllerListener(mediaController);
        player.realize();
    }

    /**
     * 改变服务器状态
     *
     * @param status 服务端连接状态，1为已连接，0为未连接，-1为重连中
     */
    public void changeServerStatus(int status) {
        if (status == 1) {
            lbServer.setText("播放服务端：已连接");
            lbServer.setForeground(new Color(50, 150, 50));
        } else if (status == 0) {
            lbServer.setText("播放服务端：未连接");
            lbServer.setForeground(new Color(150, 50, 50));
        } else {
            lbServer.setText("播放服务端：重连中");
            lbServer.setForeground(new Color(150, 150, 50));
        }
    }

    /**
     * 改变点播端状态
     *
     * @param status 点播端连接状态，1为已连接，0为未连接，-1为重连中
     */
    public void changeVODStatus(int status) {
        if (status == 1) {
            lbVOD.setText("点播端：已连接");
            lbVOD.setForeground(new Color(50, 150, 50));
        } else if (status == 0) {
            lbVOD.setText("点播端：未连接");
            lbVOD.setForeground(new Color(150, 50, 50));
        } else {
            lbVOD.setText("点播端：重连中");
            lbVOD.setForeground(new Color(150, 150, 50));
        }
    }

    //get和set方法
    public ProgressThread getProgressThread() {
        return progressThread;
    }

    public JSlider getSlProgress() {
        return slProgress;
    }

    public JSlider getSlVolume() {
        return slVolume;
    }

    public void setSlVolume(JSlider slVolume) {
        this.slVolume = slVolume;
    }

    public JLabel getLbRoom() {
        return lbRoom;
    }

    public JLabel getLbPlayingSong() {
        return lbPlayingSong;
    }

    public JLabel getLbCurrentTime() {
        return lbCurrentTime;
    }

    public JLabel getLbTotalTime() {
        return lbTotalTime;
    }

    public DefaultButton getBtnPause() {
        return btnPause;
    }

    public void setBtnPause(DefaultButton btnPause) {
        this.btnPause = btnPause;
    }

    public DefaultButton getBtnNext() {
        return btnNext;
    }

    public DefaultButton getBtnSilence() {
        return btnSilence;
    }

    public List<PlaylistPO> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<PlaylistPO> playlist) {
        this.playlist = playlist;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public RoomPO getRoomPO() {
        return roomPO;
    }

    public void setRoomPO(RoomPO roomPO) {
        this.roomPO = roomPO;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
