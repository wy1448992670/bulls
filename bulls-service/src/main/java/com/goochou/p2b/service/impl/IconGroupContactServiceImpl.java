package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.IconGroupContactMapper;
import com.goochou.p2b.dao.IconGroupMapper;
import com.goochou.p2b.dao.IconMapper;
import com.goochou.p2b.dao.UploadMapper;
import com.goochou.p2b.model.Icon;
import com.goochou.p2b.model.IconGroup;
import com.goochou.p2b.model.IconGroupContact;
import com.goochou.p2b.model.IconGroupExample;
import com.goochou.p2b.model.IconGroupExample.Criteria;
import com.goochou.p2b.service.IconGroupContactService;
import com.goochou.p2b.service.IconService;

@Service
public class IconGroupContactServiceImpl implements IconGroupContactService {
    @Resource
    private IconGroupContactMapper iconGroupContactMapper;
    @Resource
    private UploadMapper uploadMapper;
    @Resource
    private IconMapper iconMapper;

	@Override
	public List<Map<String, Object>> queryByGroupId(Integer groupId) {
		return iconGroupContactMapper.queryByGroupId(groupId);
	}

	@Override
	public List<Map<String, Object>> queryGroupIcons(Integer groupId) {
		return iconGroupContactMapper.queryGroupIcons(groupId);
	}

	@Override
	public void deleteIcons(Integer[] links, Integer[] icons, Integer[] uploads) {
		for(int i=0; i<links.length; i++){
			iconMapper.deleteByPrimaryKey(icons[i]);
			iconGroupContactMapper.deleteByPrimaryKey(links[i]);
		}
	}

	@Override
	public Integer checkDelete(Integer[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return iconGroupContactMapper.checkDelete(map);
	}

    
}
