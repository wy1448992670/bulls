package com.goochou.p2b.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.dao.AppVersionContentMapper;
import com.goochou.p2b.model.AppVersionContent;
import com.goochou.p2b.model.AppVersionContentWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.AppVersionMapper;
import com.goochou.p2b.model.AppVersion;
import com.goochou.p2b.model.AppVersionExample;
import com.goochou.p2b.model.AppVersionExample.Criteria;
import com.goochou.p2b.service.AppVersionService;

@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppVersionContentMapper appVersionContentMapper;

    @Override
    public AppVersion getAppVersion() {
        AppVersionExample example = new AppVersionExample();
        Criteria c = example.createCriteria();
        example.setOrderByClause("id desc");
        return appVersionMapper.selectByExample(example).get(0);
    }

    @Override
    public AppVersionContent getAppVersionContent(String client) {
        return appVersionContentMapper.getAppVersionContent(client);
    }

    @Override
    public AppVersionContentWithBLOBs getAppVersionContentWithBLOBs(@Param("client") String client) {
        return appVersionContentMapper.getAppVersionContentWithBLOBs(client);
    }
    
    @Override
    public List<AppVersionContent> queryAppVersionContentList(String keyword, Integer start, Integer limit) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("keyword", keyword);
	map.put("start", start);
	map.put("limit", limit);
	return appVersionContentMapper.queryAppVersionContentList(map);
    }

    @Override
    public Integer queryAppVersionContentCount(String keyword) {
	return appVersionContentMapper.queryAppVersionContentCount(keyword);
    }

    @Override
    public void saveAppVersionContent(AppVersionContentWithBLOBs group) {
	System.out.println(group);
	if (group.getId() == null) {
	    appVersionContentMapper.insertSelective(group);
	} else {
	    appVersionContentMapper.updateByPrimaryKeySelective(group);
	}
    }

    @Override
    public AppVersionContent selectAppVersionContentKey(Integer id) {
	return appVersionContentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteAppVersionContentKey(Integer id) {
	appVersionContentMapper.deleteByPrimaryKey(id);
    }
}
