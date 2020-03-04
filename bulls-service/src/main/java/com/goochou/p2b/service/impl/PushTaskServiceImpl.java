package com.goochou.p2b.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.PushTaskMapper;
import com.goochou.p2b.model.PushTask;
import com.goochou.p2b.service.PushTaskService;

@Service
public class PushTaskServiceImpl implements PushTaskService {
	@Resource
	public PushTaskMapper pushTaskMapper;
	
	@Override
	public int addPushTask(PushTask task) {
		return pushTaskMapper.insertSelective(task);
	}
	
}
