package com.goochou.p2b.service;

import java.util.Date;

import com.goochou.p2b.dao.ActivityBlessingChanceRecordMapper;
import com.goochou.p2b.model.ActivityBlessingChanceRecord;
import com.goochou.p2b.model.User;

public interface ActivityBlessingChanceRecordService {

	ActivityBlessingChanceRecordMapper getMapper();
	
	void updateByExampleForVersion(ActivityBlessingChanceRecord activityBlessingChanceRecord) throws Exception;

	void updateByExampleSelectiveForVersion(ActivityBlessingChanceRecord activityBlessingChanceRecord) throws Exception;
	
	/**
	 * type 获得方式:1定时发送 2分享获得 3注册获得 4邀请注册 5购牛 6邀请购牛
	 * 
	 * @throws Exception
	 */
	int addToAllUser(Integer getCount, Integer type, Date now, Integer foreignId) throws Exception;
	
	/**
	 * type 获得方式:1定时发送 2分享获得 3注册获得 4邀请注册 5购牛 6邀请购牛
	 * 
	 * @throws Exception
	 */
	void addToUser(Integer userId, Integer getCount, Integer type, Integer foreignId) throws Exception;
	
	ActivityBlessingChanceRecord useOneChance(Integer userId) throws Exception;

	/**
	 * 用户可抽卡次数
	 * @author sxy
	 * @param userId
	 * @return
	 */
	int getCanUseChanceCount(Integer userId);

	/**
	 * 发放抽奖次数 <br/>
	 * <>
	 * @author shuys
	 * @date 2019/12/26
	 * @param user
	 * @param optType 1.注册，2.购牛
	 * @param businessId 业务主键
	 * @return void
	*/
	void doSendOutChance(User user, int optType, Integer businessId) throws Exception;
	
}
