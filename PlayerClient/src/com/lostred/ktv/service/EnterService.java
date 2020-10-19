package com.lostred.ktv.service;

import com.lostred.ktv.dto.EnterResponse;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.view.PlayerClientView;
import com.lostred.ktv.view.SelectRoomView;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

/**
 * 播放服务端回应进入房间业务
 */
public class EnterService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        EnterResponse enterRequest = (EnterResponse) JSONObject.toBean(jsonObject, EnterResponse.class);
        RoomPO roomPO = enterRequest.getRoomPO();
        //如果房间不为空
        if (roomPO != null) {
            //跳转界面
            SelectRoomView.getSelectRoomView().toPlayerClientView(roomPO);
            //播放上次未播完的歌曲
            playLastSong();
            //请求该房间的播放列表
            LocalService.getLocalService().requestPlaylist(roomPO);
        } else {
            JOptionPane.showMessageDialog(SelectRoomView.getSelectRoomView(), "房间已被占用！");
        }
    }

    /**
     * 播放上次未播完的歌曲
     */
    private void playLastSong() {
        //读取配置文件
        String fileName = ConfigUtil.readPropertiesValue("md5");
        //如果文件名不为空
        if (fileName != null) {
            File file = new File("PlayerClient/music/" + fileName);
            //当文件名不为空且文件存在时
            if (!fileName.equals("") && file.exists()) {
                //播放上次播放的歌曲
                String songName = ConfigUtil.readPropertiesValue("songName");
                String singerName = ConfigUtil.readPropertiesValue("singerName");
                int time = Integer.parseInt(Objects.requireNonNull(ConfigUtil.readPropertiesValue("currentTime")));
                PlayerClientView pcv = PlayerClientView.getPlayerClientView();
                pcv.setFile(file);
                pcv.setTime(time);
                pcv.realizeMusic();
                //显示当前播放的歌曲
                pcv.getLbPlayingSong().setText(singerName + " - " + songName);
            }
        }
    }
}
