package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

public interface ProjectAwardService {

    /**
     * 根据项目查询项目获取到奖励的人信息
     * 
     * @param projectId
     * @return
     */
    public List<Map<String, Object>> getProjectAwards(Integer projectId);

    public void save(Integer userId, Integer projectId, Integer awardId);
}
