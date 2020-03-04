package com.goochou.p2b.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.AdminLogMapper;
import com.goochou.p2b.model.AdminLog;
import com.goochou.p2b.service.AdminLogService;

@Service
public class AdminLogServiceImpl implements AdminLogService {
    @Resource
    private AdminLogMapper adminLogMapper;

    @Override
    public void save(AdminLog log) {

        adminLogMapper.insert(log);

    }

    @Override
    public List<AdminLog> query(String keyWord, Date startTime, Date endTime, Integer lvl, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            map.put("endTime", c.getTime());
        } else {
            map.put("endTime", null);
        }

        map.put("keyWord", keyWord);
        map.put("startTime", startTime);

        map.put("lvl", lvl);
        map.put("start", start);
        map.put("limit", limit);
        return adminLogMapper.query(map);
    }

    @Override
    public Long queryCount(String keyWord, Date startTime, Date endTime, Integer lvl) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            map.put("endTime", c.getTime());
        } else {
            map.put("endTime", null);
        }
        map.put("keyWord", keyWord);
        map.put("startTime", startTime);
        map.put("lvl", lvl);
        return adminLogMapper.queryCount(map);
    }
}
