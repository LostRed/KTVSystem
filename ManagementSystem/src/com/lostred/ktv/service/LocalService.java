package com.lostred.ktv.service;

import com.lostred.ktv.dao.DaoFactory;
import com.lostred.ktv.dao.InSingerDAO;
import com.lostred.ktv.dao.InSongDAO;
import com.lostred.ktv.dao.InStyleDAO;
import com.lostred.ktv.po.SingerPO;
import com.lostred.ktv.po.SongPO;
import com.lostred.ktv.po.StylePO;
import com.lostred.ktv.util.JsonUtil;
import com.lostred.ktv.view.ManagementSystemView;
import com.lostred.ktv.view.SongInfoEditView;

import java.io.*;
import java.util.List;

/**
 * 业务
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
    public static LocalService getService() {
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
     * 根据拼音首字母查询和选择的歌曲类型刷新界面
     */
    public void refreshView() {
        ManagementSystemView mv = ManagementSystemView.getManagementSystemView();
        StylePO stylePO = (StylePO) mv.getCbStyle().getSelectedItem();
        //选中的类型不为空且选中的类型是全部时
        if (stylePO != null && stylePO.getStyleName().equals("全部")) {
            String keyword = mv.getTfSearch().getText();
            int total = countFuzzyQuerySong(keyword);
            mv.getSongInfoScrollPane().getTable().refreshPage(total);
            List<SongPO> list = fuzzyQuerySongByPage(keyword, mv.getLimit(), mv.getOffset());
            mv.getSongInfoScrollPane().getTable().refreshTable(list);
        }
        //选中其它的类型
        else {
            String keyword = mv.getTfSearch().getText();
            int total = countFuzzyQuerySong(keyword, stylePO);
            mv.getSongInfoScrollPane().getTable().refreshPage(total);
            List<SongPO> list = fuzzyQuerySongByPage(keyword, stylePO, mv.getLimit(), mv.getOffset());
            mv.getSongInfoScrollPane().getTable().refreshTable(list);
        }
    }

    /**
     * 显示编辑窗口
     *
     * @param songPO 选中的歌曲
     */
    public void showEditView(SongPO songPO) {
        SongInfoEditView ev = SongInfoEditView.getEditDialog();
        List<SingerPO> singerPOList = queryAllSinger();
        List<StylePO> stylePOList = queryAllStyle();
        ev.refreshComboBox(singerPOList, stylePOList);
        ev.importSongPO(songPO);
        ev.setVisible(true);
    }

    /**
     * 检查歌曲是否重复
     *
     * @param songPO 歌曲
     * @return 重复返回true，否则返回false
     */
    public boolean checkRepeat(SongPO songPO) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        List<SongPO> list = songDAO.queryAllSong();
        for (SongPO dbSongPO : list) {
            if (!dbSongPO.getModifyInfo().equals("delete")) {
                if (songPO.getFilePath().equals(dbSongPO.getFilePath())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 导入歌曲信息
     *
     * @param songPO 歌曲PO
     * @return 导入歌曲的输流
     * @throws IOException IO异常
     */
    public int importSong(SongPO songPO) throws IOException {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        int total = 0;
        if (checkRepeat(songPO)) {
            throw new IOException("重复文件");
        } else {
            total += songDAO.addSong(songPO);
        }
        return total;
    }

    /**
     * 修改歌曲信息
     *
     * @param songPO 歌曲PO
     * @return 受影响的记录数
     */
    public int modifySong(SongPO songPO) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.updateSong(songPO);
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
     * 按照拼音首字母和歌曲类型模糊查询歌曲总数
     *
     * @param keyword 拼音首字母关键字
     * @param stylePO 选则的歌曲类型
     * @return 歌曲总数
     */
    public int countFuzzyQuerySong(String keyword, StylePO stylePO) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.countFuzzyQuerySong(keyword, stylePO);
    }

    /**
     * 按照拼音首字母分页模糊查询歌曲
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
     * 按照拼音首字母和歌曲类型分页模糊查询歌曲
     *
     * @param keyword 拼音首字母关键字
     * @param stylePO 歌曲类型
     * @param limit   限制量
     * @param offset  偏移量
     * @return 歌曲集合
     */
    public List<SongPO> fuzzyQuerySongByPage(String keyword, StylePO stylePO, int limit, int offset) {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.fuzzyQuerySongByPage(keyword, stylePO, limit, offset);
    }

    /**
     * 查询所有歌曲
     *
     * @return 歌曲集合
     */
    public List<SongPO> queryAllSong() {
        InSongDAO songDAO = (InSongDAO) DaoFactory.getDAO("InSongDAO");
        return songDAO.queryAllSong();
    }

    /**
     * 新增歌手
     *
     * @param singerPO 歌手对象
     * @return 受影响的记录数
     */
    public int addSinger(SingerPO singerPO) {
        InSingerDAO singerDAO = (InSingerDAO) DaoFactory.getDAO("InSingerDAO");
        return singerDAO.addSinger(singerPO);
    }

    /**
     * 查询所有歌手
     *
     * @return 歌手集合
     */
    public List<SingerPO> queryAllSinger() {
        InSingerDAO singerDAO = (InSingerDAO) DaoFactory.getDAO("InSingerDAO");
        return singerDAO.queryAllSinger();
    }

    /**
     * 新增歌曲类型
     *
     * @param stylePO 歌曲类型
     * @return 受影响的记录数
     */
    public int addStyle(StylePO stylePO) {
        InStyleDAO styleDAO = (InStyleDAO) DaoFactory.getDAO("InStyleDAO");
        return styleDAO.addStyle(stylePO);
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
     * 生成文件，文件中为集合中所有歌曲PO的json字符串
     *
     * @param list 歌曲集合
     * @throws IOException IO异常
     */
    public void createFile(List<SongPO> list) throws IOException {
        OutputStream os = new FileOutputStream("data/.patch");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
        for (SongPO songPO : list) {
            String jsonString = JsonUtil.toJsonString(songPO);
            pw.println(jsonString);
            pw.flush();
        }
        os.close();
    }
}
