package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.LoginRecordMapper;
import com.goochou.p2b.model.LoginRecord;
import com.goochou.p2b.model.LoginRecordExample;
import com.goochou.p2b.service.LoginRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {

    @Resource
    private LoginRecordMapper loginRecordMapper;


    @Override
    public List<Map<String, Object>> selectAllLoginRecord(Integer start, Integer limit, String keyword, Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        map.put("keyword", keyword);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return loginRecordMapper.selectAllLoginRecord(map);
    }

    @Override
    public Integer selectAllLoginRecordCount(String keyword, Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return loginRecordMapper.selectAllLoginRecordCount(map);
    }

    @Override
    public void saveLoginRecord(LoginRecord record) {
        loginRecordMapper.insertSelective(record);
    }

    @Override
    public LoginRecord getFirstLogin(Integer userId) {
        LoginRecordExample example = new LoginRecordExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("time asc");
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<LoginRecord> list = loginRecordMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
