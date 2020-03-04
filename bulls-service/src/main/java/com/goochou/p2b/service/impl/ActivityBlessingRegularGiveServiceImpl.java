package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ActivityBlessingRegularGiveMapper;
import com.goochou.p2b.model.ActivityBlessingRegularGive;
import com.goochou.p2b.model.ActivityBlessingRegularGiveExample;
import com.goochou.p2b.service.ActivityBlessingChanceRecordService;
import com.goochou.p2b.service.ActivityBlessingRegularGiveService;
import com.goochou.p2b.service.exceptions.LockFailureException;

@Service
public class ActivityBlessingRegularGiveServiceImpl implements ActivityBlessingRegularGiveService {
	private final static Logger logger = Logger.getLogger(ActivityBlessingRegularGiveServiceImpl.class);

	@Resource
	private ActivityBlessingRegularGiveMapper activityBlessingRegularGiveMapper;
	@Resource
	private ActivityBlessingChanceRecordService activityBlessingChanceRecordService;
	@Override
	public void updateByExampleForVersion(ActivityBlessingRegularGive activityBlessingRegularGive) throws Exception {
		if (activityBlessingRegularGive.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (activityBlessingRegularGive.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		ActivityBlessingRegularGiveExample example = new ActivityBlessingRegularGiveExample();
		example.createCriteria().andIdEqualTo(activityBlessingRegularGive.getId())
				.andVersionEqualTo(activityBlessingRegularGive.getVersion());
		activityBlessingRegularGive.setVersion(activityBlessingRegularGive.getVersion() + 1);
		if (1 != activityBlessingRegularGiveMapper.updateByExample(activityBlessingRegularGive, example)) {
			throw new LockFailureException();
		}
	}

	@Override
	public void updateByExampleSelectiveForVersion(ActivityBlessingRegularGive activityBlessingRegularGive)
			throws Exception {
		if (activityBlessingRegularGive.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (activityBlessingRegularGive.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		ActivityBlessingRegularGiveExample example = new ActivityBlessingRegularGiveExample();
		example.createCriteria().andIdEqualTo(activityBlessingRegularGive.getId())
				.andVersionEqualTo(activityBlessingRegularGive.getVersion());
		activityBlessingRegularGive.setVersion(activityBlessingRegularGive.getVersion() + 1);
		if (1 != activityBlessingRegularGiveMapper.updateByExampleSelective(activityBlessingRegularGive, example)) {
			throw new LockFailureException();
		}
	}
	
	@Override
	public List<ActivityBlessingRegularGive> getNoGivenList() throws Exception {
		Date now=new Date();
		ActivityBlessingRegularGiveExample example=new ActivityBlessingRegularGiveExample();
		ActivityBlessingRegularGiveExample.Criteria criteria=example.createCriteria();
		criteria.andIsGivenEqualTo(false).andRegularTimeLessThanOrEqualTo(now);
		return activityBlessingRegularGiveMapper.selectByExample(example);
	}
	
	@Override
	public void doGive(ActivityBlessingRegularGive activityBlessingRegularGive) throws Exception{
		Date now=new Date();
		if(activityBlessingRegularGive.getRegularTime().after(now)) {
			throw new Exception("还没到时间呢");
		}
		if(activityBlessingRegularGive.getIsGiven()) {
			throw new Exception("已经发放过了");
		}
		activityBlessingChanceRecordService.addToAllUser(activityBlessingRegularGive.getGiveNumber(), 1, now, activityBlessingRegularGive.getId());
		activityBlessingRegularGive.setIsGiven(true);
		activityBlessingRegularGive.setGiveTime(now);
		this.updateByExampleForVersion(activityBlessingRegularGive);
	}
}
