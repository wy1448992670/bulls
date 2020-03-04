package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.LotteryGiftPrizeTypeEnum;
import com.goochou.p2b.constant.RateCouponTypeEnum;
import com.goochou.p2b.dao.LotteryGiftMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.dao.UserSignAwardMapper;
import com.goochou.p2b.dao.UserSignConfigMapper;
import com.goochou.p2b.model.LotteryGift;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserExample;
import com.goochou.p2b.model.UserSignAward;
import com.goochou.p2b.model.UserSignConfig;
import com.goochou.p2b.model.UserSignConfigExample;
import com.goochou.p2b.model.UserSignConfigExample.Criteria;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.UserSignConfigService;
import com.goochou.p2b.service.UserSignedService;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class UserSignConfigServiceImpl implements UserSignConfigService {
	
	private final static Logger logger = Logger.getLogger(UserSignConfigServiceImpl.class);
	
	@Resource
	private UserSignConfigMapper UserSignConfigMapper;
	
	@Resource
	private LotteryGiftMapper lotteryGiftMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserSignAwardMapper userSignAwardMapper;
	
	@Resource
	private HongbaoService hongbaoService;
	
	@Resource
	private RateCouponService rateCouponService;
	
	@Resource
    private UserSignedService userSignedService;

	@Override
	public List<UserSignConfig> queryUserSignConfigByDate(Date begin, Date end, Boolean isInvested) {
		
		UserSignConfigExample example = new UserSignConfigExample();
		Criteria criteria = example.createCriteria();
		example.createCriteria().andIsDeletedEqualTo(1);
		if(begin == null || end == null){
			criteria.andStartTimeIsNull().andEndTimeIsNull();
		}else{
			criteria.andStartTimeLessThanOrEqualTo(begin).andEndTimeGreaterThanOrEqualTo(end);
		}
		
		if(isInvested != null){
			if(isInvested){
				criteria.andIsInvestedEqualTo(1);
			}else{
				criteria.andIsInvestedEqualTo(0);
			}
		}
		
		return UserSignConfigMapper.selectByExample(example);
	}

	@Override
	public Map<String, Object> doSign(User user, UserSignConfig config, boolean isInvested) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("status", "failed");
		result.put("msg", "签到失败");
        
		UserSignAward record = null;
		
			
		Integer userId = user.getId();
		
		Date now = new Date();
		Date signDate = user.getSignTime();
		boolean flag = false;
		//如果签到时间不为空, 判断是否连续签到(签到时间 - 1天, 是否和当天是同一天)
		if(signDate != null){
			String signDateStr = DateUtil.dateFormat.format(signDate);
			Calendar c = Calendar.getInstance();
        	c.setTime(now);
        	c.add(Calendar.DAY_OF_MONTH, -1);
			String nowStr = DateUtil.dateFormat.format(c.getTime());
			//是否是连续签到
			if(nowStr.equals(signDateStr)){
				flag = true;
			}
		}
		
		//签到奖品列表
		List<UserSignAward> awardList = new ArrayList<>();
		//具体奖励
		List<LotteryGift> prizeList = new ArrayList<>();
    	
		//普通签到
		if(config.getStartTime() == null || config.getEndTime() == null){
			
			if(flag){
				Integer normalSignComboCount = user.getNormalSignComboCount()==null?0:user.getNormalSignComboCount();
				user.setNormalSignComboCount(normalSignComboCount + 1);
			}else{
				user.setNormalSignComboCount(1);
			}
			
			//返回连续签到次数
			result.put("comboSignCount", user.getNormalSignComboCount());
			
			String prize = config.getNormalPrize();
			if(StringUtil.isNull(prize)){
				logger.info("普通签到: 签到奖品配置获取失败 user: " + user.getId() + ", prize: " + prize);
				result.put("msg", "签到奖品获取失败");
				result.put("status", "failed");
				throw new RuntimeException("普通签到: 签到奖品配置获取 user: " + user.getId() + ", prize: " + prize);
			}
			
			JSONArray jsonArray = JSONArray.fromObject(prize);
			for(int i=0; i<jsonArray.size(); i++){
				JSONObject json = JSONObject.fromObject(jsonArray.get(i));
				
				//获取奖励信息
				LotteryGift lottery = lotteryGiftMapper.selectByPrimaryKey(json.getInt("giftId"));
				
				if(lottery == null){
					logger.info("普通签到: 签到奖品获取失败 user: " + user.getId() + ", json: " + json);
					result.put("msg", "签到奖品获取失败");
					result.put("status", "failed");
					throw new RuntimeException("普通签到: 签到奖品获取 user: " + user.getId() + ", json: " + json);
				}
				
				prizeList.add(lottery);
			}
		}else{
			
			//是否是活动连续签到
			if(flag){
				Integer activitySignComBoCount = user.getActivitySignComboCount()==null?0:user.getActivitySignComboCount();
				user.setActivitySignComboCount(activitySignComBoCount + 1);
			}else{
				user.setActivitySignComboCount(1);
			}
			
			//返回连续签到次数
			result.put("comboSignCount", user.getActivitySignComboCount());
			
			String activityPrize = config.getActivityPrize();
			if(StringUtil.isNull(activityPrize)){
				logger.info("活动: 签到奖品配置获取失败 user: " + user.getId() + ", activityPrize: " + activityPrize);
				result.put("msg", "签到奖品获取失败");
				result.put("status", "failed");
				throw new RuntimeException("活动: 签到奖品配置获取 user: " + user.getId() + ", activityPrize: " + activityPrize);
			}
			
			JSONArray jsonArray = JSONArray.fromObject(activityPrize);
			for(int i=0; i<jsonArray.size(); i++){
				JSONObject json = JSONObject.fromObject(jsonArray.get(i));
				if(json == null){
					logger.info("签到奖励未配置 userId : " + userId + ", activityPrize : " + activityPrize);
					result.put("msg", "签到奖励未配置");
					result.put("status", "failed");
					throw new RuntimeException("签到奖励未配置 userId : " + userId + ", activityPrize : " + activityPrize);
					
				}
				
				//获取普通签到lottery信息
				Integer lotteryGiftId = json.getInt("giftId");
				LotteryGift lottery = lotteryGiftMapper.selectByPrimaryKey(lotteryGiftId);
				if(lottery == null){
					logger.info("签到奖品未找到 userId : " + userId + ", activityPrize : " + activityPrize);
					result.put("msg", "签到奖品未找到");
					result.put("status", "failed");
					throw new RuntimeException("签到奖品未找到 userId : " + userId + ", activityPrize : " + activityPrize);
				}
				
				prizeList.add(lottery);
			}
			
			
			//判断连续签到
			Integer comboCount = user.getActivitySignComboCount();
    		//发送连续签到奖励 
			String comboPrize = config.getComboPrize();
			if(StringUtil.isNull(comboPrize)){
				logger.info("活动: 签到奖品配置获取失败 user: " + user.getId() + ", comboPrize: " + comboPrize);
				result.put("msg", "签到奖品获取失败");
				result.put("status", "failed");
				throw new RuntimeException("活动: 签到奖品配置获取 user: " + user.getId() + ", comboPrize: " + comboPrize);
			}
			
			JSONObject comboPrizeJson = JSONObject.fromObject(comboPrize);
			JSONObject json = comboPrizeJson.getJSONObject("times" + comboCount);
			if(json != null && json.size() != 0){
				//获取连续签到lottery信息
        		Integer lotteryGiftId = json.getInt("giftId");
        		LotteryGift lottery = lotteryGiftMapper.selectByPrimaryKey(lotteryGiftId);
				if(lottery == null){
					logger.info("签到奖品未找到 userId : " + userId + ", activityPrize : " + activityPrize);
					result.put("msg", "签到奖品未找到");
					result.put("status", "failed");
					throw new RuntimeException("签到奖品未找到 userId : " + userId + ", activityPrize : " + activityPrize);
				}
				
				prizeList.add(lottery);
			}
		}
		
		Double lotteryAmount = 0d;
		for(LotteryGift lottery : prizeList){
			String ruleStr = lottery.getRule();
            JSONObject rule = JSONObject.fromObject(ruleStr);
			switch (LotteryGiftPrizeTypeEnum.getValueByName(lottery.getPrizeType())) {
			case INVEST_HONGBAO:
				lotteryAmount = lottery.getAmount();
				if(hongbaoService.saveInvestHongBao(lottery.getAmount(), user.getId(), "恭喜你获得" + lottery.getAmount() + "元投资红包", 1, 2, 0, rule.getInt("limitDays"), rule.getInt("limitAmount"), null, rule.getInt("expireDays")) <= 0){
				    logger.info("发送签到奖品(投资红包)失败 user: " + user.getId() + ", rule: " + rule);
					result.put("msg", "签到失败");
					result.put("status", "failed");
					throw new RuntimeException("发送签到奖品(投资红包)失败 user: " + user.getId() + ", rule: " + rule);
				}
				result.put("awardType", LotteryGiftPrizeTypeEnum.INVEST_HONGBAO.getFeatureName());
				result.put("awardStr", lotteryAmount + "元" + LotteryGiftPrizeTypeEnum.INVEST_HONGBAO.getDescription());
				break;
				
			case CASH_HONGBAO:
				lotteryAmount = lottery.getAmount();
				String description = "恭喜你获得" + lottery.getAmount() + "元现金红包";
				if(hongbaoService.sendHongBaoToUser(lottery.getAmount(), user.getId(), description, null, 1, 1, 0, null, rule.getInt("expireDays")) <= 0){
					logger.info("发送签到奖品(现金红包)失败 user: " + user.getId() + ", rule: " + rule);
					result.put("msg", "签到失败");
					result.put("status", "failed");
					throw new RuntimeException("发送签到奖品(投资红包)失败 user: " + user.getId() + ", rule: " + rule);
				}
				result.put("awardType", LotteryGiftPrizeTypeEnum.CASH_HONGBAO.getFeatureName());
				result.put("awardStr", lotteryAmount + "元" + LotteryGiftPrizeTypeEnum.CASH_HONGBAO.getDescription());
				break;
				
			case RATE_COUPON:
				lotteryAmount = BigDecimalUtil.fixed2(BigDecimalUtil.multi(rule.getDouble("rate"), 100));
				if(rateCouponService.saveRateCouponToUser(3, "恭喜你获得散标加息券", rule.getDouble("rate"), user.getId(), rule.getInt("expireDays"), rule.getInt("days"), null, RateCouponTypeEnum.LIMITED.getFeatureName()) <= 0){
                    logger.info("发送签到奖品(加息券)失败 user: " + user.getId() + ", rule: " + rule);
					result.put("msg", "签到失败");
					result.put("status", "failed");
					throw new RuntimeException("发送签到奖品(加息券)失败 user: " + user.getId() + ", rule: " + rule);
				}
				result.put("awardType", LotteryGiftPrizeTypeEnum.RATE_COUPON.getFeatureName());
				result.put("awardStr", lotteryAmount + "%" + LotteryGiftPrizeTypeEnum.RATE_COUPON.getDescription());
				break;

			default:
				logger.info("获取签到奖品失败");
				result.put("msg", "签到失败");
				result.put("status", "failed");
				throw new RuntimeException("获取签到奖品失败 user: " + user.getId() + ", rule: " + rule);
			}
			
			//记录签到奖励日志
			record = new UserSignAward();
			record.setAmount(lotteryAmount);
			record.setCount(1);
			record.setCreateDate(now);
			record.setIsDeleted(1);
			record.setIsInvested(isInvested ? 1 : 0);
			record.setPrizeType(lottery.getPrizeType());
			record.setRule(ruleStr);
			record.setUpdateDate(now);
			record.setUserId(user.getId());
			
			awardList.add(record);
		}
		
		//已投资用户提示获得了随机现金
		if(isInvested){
			result.put("hasRamdomCashAwardStr", "随机现金");
		}
		
		//更新user
		UserExample example = new UserExample();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		
		com.goochou.p2b.model.UserExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(user.getId());
		if(user.getSignTime() != null){
			criteria.andSignTimeLessThan(c.getTime());
		}
		
		user.setSignTime(now);
		if(userMapper.updateByExampleSelective(user, example) <= 0){
			logger.info("更新用户签到信息失败 userId : " + userId);
			result.put("msg", "更新用户签到信息失败");
			result.put("status", "failed");
			throw new RuntimeException("更新用户签到信息失败 userId : " + userId);
		} 
		
		//记录签到日志
		if(userSignedService.saveSignedLog(userId) <= 0){
			logger.info("记录签到日志失败 userId : " + userId);
			result.put("msg", "签到失败");
			result.put("status", "failed");
			throw new RuntimeException("记录签到日志失败 userId : " + userId);
		}
		
		//记录签到奖励
		if(userSignAwardMapper.insertSelectiveBatch(awardList) <= 0){
			logger.info("记录签到奖励日志失败");
			result.put("msg", "签到失败");
			result.put("status", "failed");
			throw new RuntimeException("记录签到奖励日志失败 user: " + user.getId());
		}
		
		result.put("status", "ok");
		result.put("msg", "签到成功");
			
		
		return result;
	}
	
}
