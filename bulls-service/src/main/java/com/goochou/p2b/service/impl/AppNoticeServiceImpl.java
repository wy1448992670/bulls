package com.goochou.p2b.service.impl;

import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.dao.AppNoticeMapper;
import com.goochou.p2b.dao.AppNoticeReaderMapper;
import com.goochou.p2b.model.*;
import com.goochou.p2b.service.AppNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppNoticeServiceImpl implements AppNoticeService {

    @Resource
    private AppNoticeMapper appNoticeMapper;
    @Resource
    private AppNoticeReaderMapper appNoticeReaderMapper;

    @Override
    public List<AppNoticeWithBLOBs> list(Integer status, Integer start, Integer limit) {
        AppNoticeExample example = new AppNoticeExample();
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        example.setOrderByClause("send_date desc");
        if (status != null) {
            example.createCriteria().andStatusEqualTo(status);
        }
        List<AppNoticeWithBLOBs> list = appNoticeMapper.selectByExampleWithBLOBs(example);
        if (null != list && !list.isEmpty()) {
            for (AppNoticeWithBLOBs appNoticeWithBLOBs : list) {
                appNoticeWithBLOBs.setReadStatus(1); // 已读
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> listWithRoll(Integer status, Integer isShow, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("isShow", isShow);
        map.put("start", start);
        map.put("limit", limit);
        return appNoticeMapper.listWithRoll(map);
    }

    @Override
    public AppNoticeWithBLOBs get(Integer id) {
        return appNoticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer listCount(Integer status) {
        AppNoticeExample example = new AppNoticeExample();
        if (status != null) {
            example.createCriteria().andStatusEqualTo(status);
        }
        return (int) appNoticeMapper.countByExample(example);
    }

    @Override
    public void update(AppNoticeWithBLOBs notice) {
        notice.setSendDate(new Date());
        appNoticeMapper.updateByPrimaryKeySelective(notice);

        //删除其他公告设置首页显示的
        AppNoticeExample example = new AppNoticeExample();
        example.createCriteria().andIsShowEqualTo(1);
        List<AppNoticeWithBLOBs> list = appNoticeMapper.selectByExampleWithBLOBs(example);
        if (null != list && !list.isEmpty()) {
            for (AppNoticeWithBLOBs n : list) {
                notice.setIsShow(0);
                appNoticeMapper.updateByPrimaryKeySelective(n);
            }
        }
    }


    @Override
    public void delete(Integer id) {
        appNoticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(AppNoticeWithBLOBs notice) {
        Date d = new Date();
        notice.setCreateDate(d);
        notice.setSendDate(d);
        appNoticeMapper.insert(notice);
        notice.setLink(ClientConstants.H5_URL+"newsDetail.html?id="+notice.getId()+"&channelType=1");
        update(notice);

        //删除其他公告设置首页显示的
        AppNoticeExample example = new AppNoticeExample();
        example.createCriteria().andIsShowEqualTo(1);
        List<AppNoticeWithBLOBs> list = appNoticeMapper.selectByExampleWithBLOBs(example);
        if (null != list && !list.isEmpty()) {
            for (AppNoticeWithBLOBs n : list) {
                notice.setIsShow(0);
                appNoticeMapper.updateByPrimaryKeySelective(n);
            }
        }
    }


    @Override
    public Integer queryCount() {
        AppNoticeExample example = new AppNoticeExample();
        example.createCriteria().andStatusEqualTo(1);
        return (int) appNoticeMapper.countByExample(example);
    }

    @Override
    public Long getTodayCount() {
        return appNoticeMapper.getTodayCount();
    }

    @Override
    public Integer getUnReadCount(Integer userId) {
        return appNoticeMapper.getUnReadCount(userId);
    }

    @Override
    public boolean updateNoticeRead(Integer userId, List<Integer> noticeId) {
        AppNoticeReaderExample example = new AppNoticeReaderExample();
        example.createCriteria().andUserIdEqualTo(userId).andNoticeIdIn(noticeId);
//        int count = appNoticeReaderMapper.countByExample(example);
        List<AppNoticeReader> list = appNoticeReaderMapper.selectByExample(example);
        if (noticeId != null) {
            for (Integer nid: noticeId) {
                boolean temp = false;
                for (AppNoticeReader anr: list) {
                    if (nid.equals(anr.getNoticeId())) {
                        temp = true;
                    }
                }
                if (temp) continue;

                AppNoticeReader reader = new AppNoticeReader();
                reader.setUserId(userId);
                reader.setNoticeId(nid);
                reader.setReadTime(new Date());
                appNoticeReaderMapper.insertSelective(reader);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AppNoticeWithBLOBs> getNoticeList(Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        List<AppNoticeWithBLOBs> list = appNoticeMapper.getNoticeList(map);
        if (null != list && !list.isEmpty()) {
            for (AppNoticeWithBLOBs appNoticeWithBLOBs : list) {
                if (appNoticeWithBLOBs.getReadTime() != null) {
                    appNoticeWithBLOBs.setReadStatus(1); // 已读
                } else {
                    appNoticeWithBLOBs.setReadStatus(0); // 未读
                }
            }
        }
        return list;
    }

    @Override
    public void saveSetRoll(Integer noticeId) {
//        AppNoticeExample example = new AppNoticeExample();
//        example.createCriteria().andIsShowEqualTo(1);
//        List<AppNoticeWithBLOBs> list = appNoticeMapper.selectByExampleWithBLOBs(example);
//        if (null != list && !list.isEmpty()) {
//            for (AppNoticeWithBLOBs notice : list) {
//                notice.setIsShow(0);
//                appNoticeMapper.updateByPrimaryKeySelective(notice);
//            }
//        }
        AppNoticeWithBLOBs notice = appNoticeMapper.selectByPrimaryKey(noticeId);
        notice.setIsShow(1);
        appNoticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public void saveCancelRoll(Integer id) {
        AppNoticeWithBLOBs notice = appNoticeMapper.selectByPrimaryKey(id);
        notice.setIsShow(0);
        appNoticeMapper.updateByPrimaryKeySelective(notice);

    }

    @Override
    public AppNoticeWithBLOBs getShowNotice() {
        AppNoticeExample example = new AppNoticeExample();
        example.createCriteria().andIsShowEqualTo(1);
        List<AppNoticeWithBLOBs> list = appNoticeMapper.selectByExampleWithBLOBs(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Integer> selectAllNoReadNoticeList(Integer userId) {
        return appNoticeMapper.selectAllNoReadNoticeList(userId);
    }

    @Override
    public void saveAllReadNotice(Integer userId, List<Integer> noReadList) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("list", noReadList);
        appNoticeReaderMapper.saveAllReadNotice(map);
    }

    @Override
    public AppNoticeMapper getMapper() {
        return appNoticeMapper;
    }

    @Override
    public Integer getNoticeListCount(Integer userId) {
        return appNoticeMapper.getNoticeListCount(userId);
    }



    @Override
    public AppNotice getShowNoticeById(Integer id) {
        AppNoticeExample example = new AppNoticeExample();
        example.createCriteria().andIdEqualTo(id).andIsShowEqualTo(1).andStatusEqualTo(1);
        List<AppNoticeWithBLOBs> list = appNoticeMapper.selectByExampleWithBLOBs(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
