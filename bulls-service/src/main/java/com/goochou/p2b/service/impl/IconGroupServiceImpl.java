package com.goochou.p2b.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.IconGroupMapper;
import com.goochou.p2b.model.IconGroup;
import com.goochou.p2b.service.IconGroupService;

@Service
public class IconGroupServiceImpl implements IconGroupService {
	@Resource
    private IconGroupMapper iconGroupMapper;

	@Override
	public IconGroup queryByGroupId(Integer id) {
		return iconGroupMapper.selectByPrimaryKey(id);
	}
   
}
