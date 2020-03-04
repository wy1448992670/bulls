package com.goochou.p2b.service;

import com.goochou.p2b.dao.AppNoticeMapper;
import com.goochou.p2b.model.AppNotice;
import com.goochou.p2b.model.AppNoticeWithBLOBs;

import java.util.List;
import java.util.Map;

public interface AppNoticeService {

    /**
     * 查询公告列表
     * @author sxy
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<AppNoticeWithBLOBs> list(Integer status, Integer start, Integer limit);

    /**
     * 查询公告列表内容
     *
     * @param status
     * @param start
     * @param limit
     * @return
     * @author 刘源
     * @date 2016/5/3
     */
    List<Map<String, Object>> listWithRoll(Integer status, Integer isShow, Integer start, Integer limit);

    public Integer listCount(Integer status);

    public AppNoticeWithBLOBs get(Integer id);

    public void update(AppNoticeWithBLOBs notice);

    public void delete(Integer id);

    public void add(AppNoticeWithBLOBs notice);

    public Integer queryCount();

    public Long getTodayCount();

    public Integer getUnReadCount(Integer userId);

    public boolean updateNoticeRead(Integer userId, List<Integer> noticeId);

    Integer getNoticeListCount(Integer userId);

    List<AppNoticeWithBLOBs> getNoticeList(Integer userId, Integer start, Integer limit);

    /**
     * 设置首页滚动公告显示
     *
     * @param noticeId
     * @author 刘源
     * @date 2016/5/3
     */
    public void saveSetRoll(Integer noticeId);

    /**
     * 取消首页滚动公告显示
     *
     * @param id
     * @author 刘源
     * @date 2016/5/3
     */
    public void saveCancelRoll(Integer id);

    /**
     * APP首页滚动公告
     * @author ydp
     * @return
     */
    public AppNoticeWithBLOBs getShowNotice();

    /**
     * @Description: 查询所有没有阅读的公告id
     * @date 2016/10/24
     * @author 王信
     */
    List<Integer> selectAllNoReadNoticeList(Integer userId);

    /**
     * @Description: 阅读全部公告
     * @date 2016/10/24
     * @author 王信
     */
    void saveAllReadNotice(Integer userId, List<Integer> noReadList);

    AppNoticeMapper getMapper();

    /**
     * @description 功能描述
     * @author shuys
     * @date 2019/6/17
     * @param id
     * @return com.goochou.p2b.model.AppNotice
    */
    AppNotice getShowNoticeById(Integer id);
}
