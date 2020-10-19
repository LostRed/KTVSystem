package com.lostred.ktv.service;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InSingerDAO;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dao.InStyleDAO;
import com.lostred.ktv.dto.ReconnectRequest;
import com.lostred.ktv.dto.UpdateRequest;
import com.lostred.ktv.dto.ex.PinYinUpdateRequest;
import com.lostred.ktv.dto.ex.SingerUpdateRequest;
import com.lostred.ktv.dto.ex.StyleUpdateRequest;
import com.lostred.ktv.net.Client;
import com.lostred.ktv.po.*;
import com.lostred.ktv.util.ConfigUtil;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.VODClientView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 本地业务
 */
public class LocalService {
    /**
     * 单例业务对象
     */
    private static LocalService LOCAL_SERVICE;

    /**
     * 构造业务对象
     */
    private LocalService() {
    }

    /**
     * 获取单例业务
     *
     * @return 业务对象
     */
    public static LocalService getLocalService() {
        if (LOCAL_SERVICE == null) {
            synchronized (LocalService.class) {
                if (LOCAL_SERVICE == null) {
                    LOCAL_SERVICE = new LocalService();
                }
            }
        }
        return LOCAL_SERVICE;
    }

    /**
     * 已点歌曲模式下刷新界面
     */
    public void refreshPlaylistView() {
        VODClientView vodCv = VODClientView.getVODClientView();
        List<PlaylistPO> list = vodCv.getPlaylist();
        vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshPage(list.size());
        vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshPlaylistTable();
    }

    /**
     * 根据拼音首字母刷新界面
     */
    public void refreshPinYinView() {
        VODClientView vodCv = VODClientView.getVODClientView();
        String keyword = vodCv.getSongInfoPanel().getControlPanel().getTfSearch().getText();
        //如果客户端已连接且搜索关键字不为空时
        if (Client.getClient().isConnected() && !keyword.equals("")) {
            //请求更新数据
            requestQueryUpdate(0);
        }
        localRefreshPinYinView();
    }

    /**
     * 根据歌手首字母刷新界面
     */
    public void refreshSingerView() {
        VODClientView vodCv = VODClientView.getVODClientView();
        String keyword = vodCv.getSongInfoPanel().getControlPanel().getTfSingerSearch().getText();
        //如果客户端已连接且搜索关键字不为空时
        if (Client.getClient().isConnected() && !keyword.equals("")) {
            //请求更新数据
            requestQueryUpdate(1);
        }
        localRefreshSingerView();
    }

    /**
     * 根据选择的类型刷新界面
     */
    public void refreshStyleView() {
        VODClientView vodCv = VODClientView.getVODClientView();
        StylePO stylePO = (StylePO) vodCv.getSongInfoPanel().getControlPanel().getCbStyle().getSelectedItem();
        //如果客户端已连接且选中的类型不为空且选中的类型不是全部时
        if (Client.getClient().isConnected() && stylePO != null && !stylePO.getStyleName().equals("全部")) {
            //请求更新数据
            requestQueryUpdate(2);
        }
        localRefreshStyleView();
    }

    /**
     * 请求查询增量更新
     *
     * @param mode 查询模式，0为拼音首字母查询，1为歌手首字母查询，2为歌曲分类查询
     */
    public void requestQueryUpdate(int mode) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        //获取首次更新的文件
        File file = new File("data/.patch");
        String time;
        //文件存在时
        if (file.exists()) {
            time = songDAO.queryLastTime();
        } else {
            time = "1970-01-01 00:00:00";
        }
        if (mode == 0) {
            //获取拼音首字母关键字
            VODClientView vodCv = VODClientView.getVODClientView();
            String keyword = vodCv.getSongInfoPanel().getControlPanel().getTfSearch().getText();
            System.out.println(keyword);
            //发送更新请求
            PinYinUpdateRequest pinYinUpdateRequest = new PinYinUpdateRequest(keyword, time);
            Client.getClient().sendMessage(JsonUtil.toJsonString(pinYinUpdateRequest));
        } else if (mode == 1) {
            //获取歌手首字母关键字
            VODClientView vodCv = VODClientView.getVODClientView();
            String keyword = vodCv.getSongInfoPanel().getControlPanel().getTfSingerSearch().getText();
            //发送更新请求
            SingerUpdateRequest singerUpdateRequest = new SingerUpdateRequest(keyword, time);
            Client.getClient().sendMessage(JsonUtil.toJsonString(singerUpdateRequest));
        } else {
            //获取选中的歌曲类型
            VODClientView vodCv = VODClientView.getVODClientView();
            StylePO stylePO = (StylePO) vodCv.getSongInfoPanel().getControlPanel().getCbStyle().getSelectedItem();
            //发送更新请求
            StyleUpdateRequest styleUpdateRequest = new StyleUpdateRequest(stylePO, time);
            Client.getClient().sendMessage(JsonUtil.toJsonString(styleUpdateRequest));
        }
    }

    /**
     * 查询本地数据库根据拼音首字母刷新界面
     */
    public void localRefreshPinYinView() {
        VODClientView vodCv = VODClientView.getVODClientView();
        String keyword = vodCv.getSongInfoPanel().getControlPanel().getTfSearch().getText();
        int total = countFuzzyQuerySong(keyword);
        vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshPage(total);
        List<SongPO> list = fuzzyQuerySongByPage(keyword, vodCv.getLimit(), vodCv.getOffset());
        vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshTable(list);
    }

    /**
     * 查询本地数据库根据歌手首字母刷新界面
     */
    public void localRefreshSingerView() {
        VODClientView vodCv = VODClientView.getVODClientView();
        String keyword = vodCv.getSongInfoPanel().getControlPanel().getTfSingerSearch().getText();
        int total = countFuzzyQuerySongOnSinger(keyword);
        vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshPage(total);
        List<SongPO> list = fuzzyQuerySongOnSingerByPage(keyword, vodCv.getLimit(), vodCv.getOffset());
        vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshTable(list);
    }

    /**
     * 查询本地数据库根据选择的类型刷新界面
     */
    public void localRefreshStyleView() {
        VODClientView vodCv = VODClientView.getVODClientView();
        StylePO stylePO = (StylePO) vodCv.getSongInfoPanel().getControlPanel().getCbStyle().getSelectedItem();
        //选中的类型为空或选中的类型是全部时
        if (stylePO == null || stylePO.getStyleName().equals("全部")) {
            int total = countFuzzyQuerySong("");
            vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshPage(total);
            List<SongPO> list = fuzzyQuerySongByPage("", vodCv.getLimit(), vodCv.getOffset());
            vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshTable(list);
        }
        //选中其它的类型
        else {
            int total = countQuerySong(stylePO);
            vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshPage(total);
            List<SongPO> list = querySongByPage(stylePO, vodCv.getLimit(), vodCv.getOffset());
            vodCv.getSongInfoPanel().getSongInfoScrollPane().getTable().refreshTable(list);
        }
    }

    /**
     * 按照拼音首字母模糊查询歌曲总数
     *
     * @param keyword 拼音首字母关键字
     * @return 歌曲总数
     */
    public int countFuzzyQuerySong(String keyword) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.countFuzzyQuerySong(keyword);
    }

    /**
     * 按照拼音首字母模糊查询歌曲
     *
     * @param keyword 拼音首字母关键字
     * @param limit   限制量
     * @param offset  偏移量
     * @return 歌曲集合
     */
    public List<SongPO> fuzzyQuerySongByPage(String keyword, int limit, int offset) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.fuzzyQuerySongByPage(keyword, limit, offset);
    }

    /**
     * 按照歌手首字母模糊查询歌曲总数
     *
     * @param keyword 歌手首字母关键字
     * @return 歌曲总数
     */
    public int countFuzzyQuerySongOnSinger(String keyword) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.countFuzzyQuerySongOnSinger(keyword);
    }

    /**
     * 按照歌手首字母模糊查询歌曲
     *
     * @param keyword 歌手首字母关键字
     * @param limit   限制量
     * @param offset  偏移量
     * @return 歌曲集合
     */
    public List<SongPO> fuzzyQuerySongOnSingerByPage(String keyword, int limit, int offset) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.fuzzyQuerySongOnSingerByPage(keyword, limit, offset);
    }

    /**
     * 按照歌曲类型查询歌曲总数
     *
     * @param stylePO 歌曲类型
     * @return 歌曲总数
     */
    public int countQuerySong(StylePO stylePO) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.countQuerySong(stylePO);
    }

    /**
     * 按照歌曲类型查询歌曲
     *
     * @param stylePO 歌曲类型
     * @param limit   限制量
     * @param offset  偏移量
     * @return 歌曲集合
     */
    public List<SongPO> querySongByPage(StylePO stylePO, int limit, int offset) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.querySongByPage(stylePO, limit, offset);
    }

    /**
     * 查询所有歌曲类型
     *
     * @return 歌曲类型集合
     */
    public List<StylePO> queryAllStyle() {
        InStyleDAO styleDAO = (InStyleDAO) DaoFactory.getDAO("InStyleDAO");
        return styleDAO.queryAllStyle();
    }

    /**
     * 请求更新数据
     */
    public void requestUpdate() {
        //判断是否第一次启动客户端
        UpdateRequest updateRequest;
        File file = new File("data/.patch");
        if (file.exists()) {
            InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
            String time = songDAO.queryLastTime();
            updateRequest = new UpdateRequest(time);
        } else {
            //请求服务端更新本地数据库
            updateRequest = new UpdateRequest("1970-01-01 00:00:00");
        }
        Client.getClient().sendMessage(JsonUtil.toJsonString(updateRequest));
    }

    /**
     * 导入数据
     *
     * @param songPO 歌曲PO
     */
    public void importDate(SongPO songPO) {
        SingerPO singerPO = songPO.getSinger();
        StylePO stylePO = songPO.getStyle();
        String modifyInfo = songPO.getModifyInfo();
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        switch (modifyInfo) {
            case "new":
                addSong(songPO);
                addSinger(singerPO);
                addStyle(stylePO);
                break;
            case "update":
                int total = songDAO.updateSong(songPO);
                if (total == 0) {
                    songDAO.addSong(songPO);
                }
                addSinger(singerPO);
                addStyle(stylePO);
                break;
            case "delete":
                songDAO.deleteSong(songPO.getSongId());
                break;
        }
    }

    /**
     * 添加歌曲
     *
     * @param songPO 歌曲
     */
    private void addSong(SongPO songPO) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        if (!checkRepeat(songPO)) {
            songDAO.addSong(songPO);
        }
    }

    /**
     * 添加歌手
     *
     * @param singerPO 歌手
     */
    private void addSinger(SingerPO singerPO) {
        InSingerDAO singerDAO = (InSingerDAO) DaoFactory.getDAO("InSingerDAO");
        if (!checkRepeat(singerPO)) {
            singerDAO.addSinger(singerPO);
        }
    }

    /**
     * 添加歌曲类型
     *
     * @param stylePO 歌曲类型
     */
    private void addStyle(StylePO stylePO) {
        InStyleDAO styleDAO = (InStyleDAO) DaoFactory.getDAO("InStyleDAO");
        if (!checkRepeat(stylePO)) {
            styleDAO.addStyle(stylePO);
        }
    }

    /**
     * 检查歌曲是否重复
     *
     * @param songPO 歌曲
     * @return 重复返回true，否则返回false
     */
    private boolean checkRepeat(SongPO songPO) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        List<SongPO> list = songDAO.queryAllSong();
        for (SongPO dbSongPO : list) {
            if (songPO.getSongId() == dbSongPO.getSongId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查歌手是否重复
     *
     * @param singerPO 歌手
     * @return 重复返回true，否则返回false
     */
    private boolean checkRepeat(SingerPO singerPO) {
        InSingerDAO singerDAO = (InSingerDAO) DaoFactory.getDAO("InSingerDAO");
        List<SingerPO> list = singerDAO.queryAllSinger();
        for (SingerPO dbSingerPO : list) {
            if (singerPO.getSingerId() == dbSingerPO.getSingerId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查歌曲类型是否重复
     *
     * @param stylePO 歌手
     * @return 重复返回true，否则返回false
     */
    private boolean checkRepeat(StylePO stylePO) {
        InStyleDAO styleDAO = (InStyleDAO) DaoFactory.getDAO("InStyleDAO");
        List<StylePO> list = styleDAO.queryAllStyle();
        for (StylePO dbStylePO : list) {
            if (stylePO.getStyleId() == dbStylePO.getStyleId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 未登录状态下连接服务端
     */
    public void connect() {
        String host = ConfigUtil.getIp();
        String port = ConfigUtil.getPort();
        try {
            Client.getClient().connect(host, port);
            //更改界面
            SwingUtilities.invokeLater(() -> VODClientView.getVODClientView().changeServerStatus(1));
            //请求更新数据
            requestUpdate();
        } catch (IOException ignored) {
        }
    }

    /**
     * 登录状态下重连服务端
     */
    public void reconnect() {
        VODClientView vodCv = VODClientView.getVODClientView();
        String host = ConfigUtil.getIp();
        String port = ConfigUtil.getPort();
        try {
            Client.getClient().connect(host, port);
            //更改界面
            SwingUtilities.invokeLater(() -> vodCv.changeServerStatus(1));
            //给服务端发送房间号
            RoomPO roomPO = vodCv.getRoomPO();
            ReconnectRequest reconnectRequest = new ReconnectRequest(roomPO);
            Client.getClient().sendMessage(JsonUtil.toJsonString(reconnectRequest));
            JOptionPane.showMessageDialog(vodCv, "已重新连接点播服务端！");
        } catch (IOException ignored) {
        }
    }
}
