package com.goochou.p2b.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.constant.LotteryGiftPrizeTypeEnum;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.dao.UserSignAwardMapper;
import com.goochou.p2b.model.UserSignAward;
import com.goochou.p2b.model.UserSignAwardExample;
import com.goochou.p2b.service.UserSignAwardService;
import com.goochou.p2b.utils.BigDecimalUtil;

import net.sf.json.JSONObject;

@Service
public class UserSignAwardServiceImpl implements UserSignAwardService {
	
	private final static Logger logger = Logger.getLogger(UserSignAwardServiceImpl.class);
	
	@Resource
	private UserSignAwardMapper userSignAwardMapper;
	
	@Resource
	private TradeRecordMapper tradeRecordMapper;

	@Override
	public Double queryUserSignAwardAmount(Integer userId) {
		
		UserSignAwardExample example = new UserSignAwardExample();
		String []prizeType = new String[]{LotteryGiftPrizeTypeEnum.CASH_HONGBAO.getFeatureName(), LotteryGiftPrizeTypeEnum.EXPERIENCE.getFeatureName()};
		example.createCriteria().andPrizeTypeIn(Arrays.asList(prizeType)).andUserIdEqualTo(userId);
		List<UserSignAward> list = userSignAwardMapper.selectByExample(example);
		Double allAmount = 0d;
		int count = 0;
		for(UserSignAward award : list){
			Double amount = 0d;
			switch (LotteryGiftPrizeTypeEnum.getValueByName(award.getPrizeType())) {
			case CASH_HONGBAO:
				amount = award.getAmount();
				count = award.getCount();
				allAmount += amount * count;
				break;
				
			case EXPERIENCE:
				amount = award.getAmount();
				count = award.getCount();
				String rule = award.getRule();
				JSONObject json = JSONObject.fromObject(rule);
				int days = json.getInt("expDays");
				double rate = ConstantsAdmin.RATE_DEFAULT_VALUE;
//				allAmount += BigDecimalUtil.fixed2(amount * rate / 365  * days * count);
//				
//				LogUtil.infoLogs(BigDecimalUtil.fixed2(amount * rate / 365  * days * count).toString());
				

				allAmount = BigDecimalUtil.add(allAmount, BigDecimalUtil.fixed2(amount * rate / 365  * days * count));
				break;

			default:
				break;
			}
		}
		logger.info(allAmount.toString());
		//现金奖励
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("type", 11);
		Double sum = tradeRecordMapper.sumAmountByType(params);
		sum = sum == null ? 0d : sum;
		allAmount += sum;
		
		return allAmount;
	}

	@Override
	public List<Map<String, Object>> queryUserSignAwardList(Integer userId,
			Integer limit, Integer offset) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("limit", limit);
		params.put("offset", offset);
		return userSignAwardMapper.queryUserSignAwardList(params);
	}

	@Override
	public Integer queryUserSignAwardCount(Integer userId) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		return userSignAwardMapper.queryUserSignAwardCount(params);
	}
	
	
}
