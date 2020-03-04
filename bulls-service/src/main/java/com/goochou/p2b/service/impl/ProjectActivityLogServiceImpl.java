package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.ProjectActivityLogMapper;
import com.goochou.p2b.service.ProjectActivityLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectActivityLogServiceImpl implements ProjectActivityLogService {
    @Resource
    private ProjectActivityLogMapper projectActivityLogMapper;

    @Override
    public List<Map<String, Object>> queryActivityNumberList(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        return projectActivityLogMapper.queryActivityNumberList(map);
    }
}
