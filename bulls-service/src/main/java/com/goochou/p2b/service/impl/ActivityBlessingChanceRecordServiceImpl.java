package com.goochou.p2b.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ActivityBlessingChanceRecordMapper;
import com.goochou.p2b.model.ActivityBlessingChanceRecord;
import com.goochou.p2b.model.ActivityBlessingChanceRecordExample;
import com.goochou.p2b.service.ActivityBlessingChanceRecordService;
import com.goochou.p2b.service.exceptions.LockFailureException;

@Service
public class ActivityBlessingChanceRecordServiceImpl implements ActivityBlessingChanceRecordService {
	private final static Logger logger = Logger.getLogger(ActivityBlessingChanceRecordServiceImpl.class);

	@Resource
	private ActivityBlessingChanceRecordMapper activityBlessingChanceRecordMapper;

	@Resource
	private ActivityService activityService;

	@Resource
	private UserService userService;


	public ActivityBlessingChanceRecordMapper getMapper(){
		return this.activityBlessingChanceRecordMapper;
	}
	
	@Override
	public void updateByExampleForVersion(ActivityBlessingChanceRecord activityBlessingChanceRecord) throws Exception {
		if (activityBlessingChanceRecord.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (activityBlessingChanceRecord.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		ActivityBlessingChanceRecordExample example = new ActivityBlessingChanceRecordExample();
		example.createCriteria().andIdEqualTo(activityBlessingChanceRecord.getId())
				.andVersionEqualTo(activityBlessingChanceRecord.getVersion());
		activityBlessingChanceRecord.setVersion(activityBlessingChanceRecord.getVersion() + 1);
		if (1 != activityBlessingChanceRecordMapper.updateByExample(activityBlessingChanceRecord, example)) {
			throw new LockFailureException();
		}
	}

	@Override
	public void updateByExampleSelectiveForVersion(ActivityBlessingChanceRecord activityBlessingChanceRecord)
			throws Exception {
		if (activityBlessingChanceRecord.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (activityBlessingChanceRecord.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		ActivityBlessingChanceRecordExample example = new ActivityBlessingChanceRecordExample();
		example.createCriteria().andIdEqualTo(activityBlessingChanceRecord.getId())
				.andVersionEqualTo(activityBlessingChanceRecord.getVersion());
		activityBlessingChanceRecord.setVersion(activityBlessingChanceRecord.getVersion() + 1);
		if (1 != activityBlessingChanceRecordMapper.updateByExampleSelective(activityBlessingChanceRecord, example)) {
			throw new LockFailureException();
		}
	}
	
	/**
	 * type 获得方式:1定时发送 2分享获得 3注册获得 4邀请注册 5购牛 6邀请购牛
	 * 
	 * @throws Exception
	 */
	@Override
	public int addToAllUser(Integer getCount, Integer type,Date now, Integer foreignId) throws Exception {
		return activityBlessingChanceRecordMapper.addToAllUser(getCount,type,now,foreignId);
	}
	
	/**
	 * type 获得方式:1定时发送 2分享获得 3注册获得 4邀请注册 5购牛 6邀请购牛
	 * 
	 * @throws Exception
	 */
	@Override
	public void addToUser(Integer userId, Integer getCount, Integer type, Integer foreignId) throws Exception {
		ActivityBlessingChanceRecord activityBlessingChanceRecord = new ActivityBlessingChanceRecord();
		activityBlessingChanceRecord.setUserId(userId);
		activityBlessingChanceRecord.setGetCount(getCount);
		activityBlessingChanceRecord.setType(type);
		activityBlessingChanceRecord.setForeignId(foreignId);
		activityBlessingChanceRecord.setUseCount(0);
		activityBlessingChanceRecord.setCreateDate(new Date());
		activityBlessingChanceRecord.setVersion(0);
		activityBlessingChanceRecordMapper.insert(activityBlessingChanceRecord);
	}
	
	@Override
	public ActivityBlessingChanceRecord useOneChance(Integer userId) throws Exception {
		ActivityBlessingChanceRecord activityBlessingChanceRecord = activityBlessingChanceRecordMapper.selectByUserIdCanUse(userId);
		if(activityBlessingChanceRecord==null) {
			throw new Exception("抽完了");
		}
		if(activityBlessingChanceRecord.getUseCount().intValue()>=activityBlessingChanceRecord.getGetCount().intValue()) {
			throw new Exception("程序出错了");
		}
		activityBlessingChanceRecord.setUseCount(activityBlessingChanceRecord.getUseCount()+1);
		this.updateByExampleForVersion(activityBlessingChanceRecord);
		return activityBlessingChanceRecord;
	}

    @Override
    public int getCanUseChanceCount(Integer userId) {
        int chanceCount = activityBlessingChanceRecordMapper.getCanUseChanceCount(userId);
        if(chanceCount < 0) {
            return 0;
        } else {
            return chanceCount;
        }
    }

	@Override
	public void doSendOutChance(User user, int optType, Integer businessId) throws Exception {
		// optType 1.注册，2.购牛
//		if (user == null) {
		if (user == null || user.getId() == null) {
			logger.error("user 为空");
			return;
		}
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.YEAR) == 2020 || (now.get(Calendar.YEAR) == 2019)) {
			Activity activity = activityService.getByName(ActivityConstant.NEW_YEAR_2020);
			if (activity == null) {
				logger.warn(ActivityConstant.NEW_YEAR_2020 + " 活动不存在");
				return;
			}
			Date realEndTime = DateUtil.getNextDayMinTime(activity.getEndTime());
			// 当前时间不在活动期内
			if (now.getTime().before(activity.getStartTime()) || now.getTime().after(realEndTime)) {
				logger.warn(" 当前时间不在活动期内 ");
				return;
			}
			if (optType == 1) { // 注册
				if (user.getCreateDate().before(activity.getStartTime()) || user.getCreateDate().after(realEndTime)) {
					logger.warn(" 用户注册时间不在活动内 ");
					return;
				}
				//新用户注册发放一次抽奖机会
				this.addToUser(user.getId(), 1, 3, user.getId());
				logger.info(" 新用户注册发放1次抽奖机会 ");
				
				// 新用户的邀请人发送一次抽奖次数
				if (user.getInviteByCode() == null) {
					logger.warn(" 该用户无邀请关系人 ");
					return;
				}
				User inviter = userService.getByInviteCode(user.getInviteByCode());
				if (inviter != null) {
					// 如果邀请人，当天通过邀请注册获得抽奖次数不超过10次，再获得一次抽奖次数
					ActivityBlessingChanceRecordExample example = new ActivityBlessingChanceRecordExample();
					example.createCriteria().andUserIdEqualTo(inviter.getId()).andTypeEqualTo(4)
							.andCreateDateGreaterThanOrEqualTo(DateUtil.getDayMinTime(now.getTime()))
							.andCreateDateLessThan(DateUtil.getNextDayMinTime(now.getTime()));
					long count = activityBlessingChanceRecordMapper.countByExample(example);
					if (count < 10) {
						this.addToUser(inviter.getId(), 1, 4, user.getId());
					}
					logger.info(" 新用户邀请人发送1次抽奖次数 ");
				}
			} else if (optType == 2) { // 投资
				if (businessId == null) {
					logger.error("bussinessId 为空");
					return;
				}
				// 每次投资，投资人都发放五次抽奖机会
				this.addToUser(user.getId(), 5, 5, businessId);
				logger.info(" 投资人获得5次抽奖次数 ");

				// 活动内，与投资人产生邀请关系的邀请人在投资人首投将获得五次抽奖机会
				if (user.getLevel() == 0) {    
					if (StringUtils.isBlank(user.getInviteByCode())) {
						logger.warn(" 该用户在活动期间无邀请关系人 ");
						return;
					}
					if (user.getCreateDate().before(activity.getStartTime()) || user.getCreateDate().after(realEndTime)) {
						logger.warn(" 该用户邀请关系不在活动期间产生 ");
						return;
					}
					// 投资人对应邀请人
					User inviter = userService.getByInviteCode(user.getInviteByCode());
					if (inviter == null) {
						logger.warn(" 该用户邀请关系人不存在 ");
						return;
					}
					// 投资人对应邀请人发放抽奖机会
					this.addToUser(inviter.getId(), 5, 6, businessId);
					logger.info(" 邀请人通过投资人首投获得5次抽奖次数 ");
				}
			} else {
				logger.error("optType 错误");
				return;
			}
		}
	}
	
}
