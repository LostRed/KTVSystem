package com.lostred.ktv.service;

import com.lostred.ktv.dto.LoginResponse;
import com.lostred.ktv.dto.PlaylistRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.net.RecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.LoginView;
import com.lostred.ktv.view.VODClientView;
import net.sf.json.JSONObject;

import javax.swing.*;

/**
 * 点播服务端回应登录房间业务
 */
public class LoginService implements InService {
    @Override
    public void doService(RecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        LoginResponse loginResponse = (LoginResponse) JSONObject.toBean(jsonObject, LoginResponse.class);
        String result = loginResponse.getResult();
        RoomPO roomPO = loginResponse.getRoomPO();
        if (result.equals(JsonUtil.SUCCESS)) {
            JOptionPane.showMessageDialog(LoginView.getLoginView(), "登录成功！");
            LoginView.getLoginView().toVODClientView(roomPO);
            //请求显示已点歌曲
            VODClientView vodCv = VODClientView.getVODClientView();
            PlaylistRequest playlistRequest = new PlaylistRequest(vodCv.getRoomPO());
            Client.getClient().sendMessage(JsonUtil.toJsonString(playlistRequest));
        } else if (result.equals(JsonUtil.FAIL) && roomPO != null) {
            JOptionPane.showMessageDialog(LoginView.getLoginView(), "房间已登录，不能重复登录！");
        } else {
            JOptionPane.showMessageDialog(LoginView.getLoginView(), "房间号或密码错误！");
        }
        //恢复登录按钮
        LoginView.getLoginView().getBtnLogin().setEnabled(true);
    }
}
