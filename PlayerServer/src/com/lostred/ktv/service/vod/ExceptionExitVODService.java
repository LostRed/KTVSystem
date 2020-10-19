package com.lostred.ktv.service.vod;

import com.lostred.ktv.dto.vod.ExceptionExitResponse;
import com.lostred.ktv.net.PCRecvThread;
import com.lostred.ktv.net.VSRecvThread;
import com.lostred.ktv.po.RoomPO;
import com.lostred.ktv.util.TimeUtil;
import net.sf.json.JSONObject;

import java.net.Socket;

/**
 * 点播服务端回应异常退出房间业务
 */
public class ExceptionExitVODService implements InVODService {
    @Override
    public void doService(VSRecvThread handler) {
        //解析json
        JSONObject jsonObject = handler.getJsonObject();
        ExceptionExitResponse exceptionExitResponse = (ExceptionExitResponse) JSONObject.toBean(jsonObject, ExceptionExitResponse.class);
        RoomPO roomPO = exceptionExitResponse.getRoomPO();
        boolean exist = exceptionExitResponse.isExist();
        //如果房间还是登录状态
        if (exist) {
            //显示消息
            String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：登录中";
            handler.showMessage(message);
        } else {
            //显示消息
            String message = TimeUtil.getNowTime() + "\t" + roomPO.getRoomId() + "号房间：已注销";
            handler.showMessage(message);
            //将该房间从Map中移除
            Socket socket = PCRecvThread.getRoomMap().remove(roomPO.getRoomId());
            PCRecvThread.getClientSocketMap().remove(socket);
        }
    }
}
