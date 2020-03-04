package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ProjectAwardMapper;
import com.goochou.p2b.model.ProjectAward;
import com.goochou.p2b.service.ProjectAwardService;

@Service
public class ProjectAwardServiceImpl implements ProjectAwardService {

    @Resource
    private ProjectAwardMapper projectAwardMapper;

    @Override
    public List<Map<String, Object>> getProjectAwards(Integer projectId) {
        List<Map<String, Object>> list = projectAwardMapper.getProjectAwards(projectId);
        if (null != list && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                String username = (String) map.get("username");
                username = username.substring(0, 1) + "***" + username.substring(username.length() - 1, username.length());
                map.put("username", username);
            }
        }
        return list;
    }

    @Override
    public void save(Integer userId, Integer projectId, Integer awardId) {
        ProjectAward pa = new ProjectAward();
        pa.setUserId(userId);
        pa.setProjectId(projectId);
        pa.setAwardId(awardId);
        pa.setTime(new Date());
        projectAwardMapper.insert(pa);
    }
}
