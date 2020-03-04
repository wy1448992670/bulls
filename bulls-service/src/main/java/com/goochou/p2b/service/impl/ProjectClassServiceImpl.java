package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ProjectClassMapper;
import com.goochou.p2b.model.ProjectClass;
import com.goochou.p2b.model.ProjectClassExample;
import com.goochou.p2b.model.ProjectClassExample.Criteria;
import com.goochou.p2b.service.ProjectClassService;

@Service
public class ProjectClassServiceImpl implements ProjectClassService {

    @Resource
    private ProjectClassMapper projectClassMapper;

    public List<ProjectClass> selectProjectClassList(String deviceType) {
        ProjectClassExample example = new ProjectClassExample();
        Criteria c = example.createCriteria();
        c.andStatusEqualTo(1);
        c.andDeviceTypeEqualTo(deviceType);
        example.setOrderByClause("sort asc");
        return projectClassMapper.selectByExample(example);
    }

    @Override
    public ProjectClass get(Integer id) {
        ProjectClassExample example = new ProjectClassExample();
        example.createCriteria().andIdEqualTo(id);
        return projectClassMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProjectClass get(Integer limitDays, Integer noob) {
        ProjectClassExample example = new ProjectClassExample();
        Criteria c = example.createCriteria();
        c.andLimitDaysEqualTo(limitDays);
        c.andNoobEqualTo(noob);
        example.setOrderByClause("sort asc");
        List<ProjectClass> l = projectClassMapper.selectByExample(example);
        return l.size() == 0 ? null : l.get(0);
    }

    @Override
    public List<ProjectClass> queryProjectClassList(String keyword, Integer page, Integer limit, String name, Integer noob, Integer limitDays) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("page", page);
        map.put("limit", limit);
        map.put("name", name);
        map.put("noob", noob);
        map.put("limitDays", limitDays);
        return projectClassMapper.queryProjectClassList(map);
    }

    @Override
    public Integer queryProjectClassListCount(String keyword, String name, Integer noob, Integer limitDays) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("name", name);
        map.put("noob", noob);
        map.put("limitDays", limitDays);
        return projectClassMapper.queryProjectClassListCount(map);
    }

    @Override
    public Integer save(ProjectClass projectClass) {
        projectClass.setCreateDate(new Date());
        return projectClassMapper.insertSelective(projectClass);
    }

    @Override
    public Integer updateProjectClass(ProjectClass projectClass) {
        projectClass.setUpdateTime(new Date());
        return projectClassMapper.updateByPrimaryKeySelective(projectClass);
    }

    @Override
    public List<ProjectClass> queryIndex(int start, int limit,String deviceType) {
        ProjectClassExample example = new ProjectClassExample();
        ProjectClassExample.Criteria c = example.createCriteria();
        c.andStatusEqualTo(1);
        c.andNoobNotEqualTo(1);//排除新手标这里
        c.andDeviceTypeEqualTo(deviceType);
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        example.setOrderByClause("sort");
        return projectClassMapper.selectByExample(example);
    }
}
