package com.lostred.ktv.controller;

import com.lostred.ktv.view.PlayerClientView;

import javax.media.Player;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 音量控制条监听
 */
public class VolumeSlider extends MouseAdapter implements ChangeListener {
    @Override
    public void mouseReleased(MouseEvent e) {
        JSlider slider = (JSlider) e.getSource();
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        Player player = pcv.getPlayer();
        int volume = slider.getValue();
        pcv.setVolume(volume);
        if (player != null) {
            float level = (float) volume / 200;
            player.getGainControl().setLevel(level);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        PlayerClientView pcv = PlayerClientView.getPlayerClientView();
        Player player = pcv.getPlayer();
        int volume = slider.getValue();
        if (player != null) {
            float level = (float) volume / 200;
            player.getGainControl().setLevel(level);
        }
    }
}
