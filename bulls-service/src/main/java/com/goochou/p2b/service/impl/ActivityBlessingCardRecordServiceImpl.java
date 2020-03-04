package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import com.goochou.p2b.utils.AESUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.HongbaoTypeEnum;
import com.goochou.p2b.constant.LotteryBullsTypeEnum;
import com.goochou.p2b.constant.ProbabilityBullsEnum;
import com.goochou.p2b.dao.ActivityBlessingCardRecordMapper;
import com.goochou.p2b.dao.HongbaoMapper;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.ActivityBlessingCardRecord;
import com.goochou.p2b.model.ActivityBlessingCardRecordExample;
import com.goochou.p2b.model.ActivityBlessingChanceRecord;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.ActivityBlessingCardRecordExample.Criteria;
import com.goochou.p2b.service.ActivityBlessingCardRecordService;
import com.goochou.p2b.service.ActivityBlessingChanceRecordService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.DateFormatTools;
import static com.goochou.p2b.constant.LotteryBullsTypeEnum.*;

@Service
public class ActivityBlessingCardRecordServiceImpl implements ActivityBlessingCardRecordService {
	private final static Logger logger = Logger.getLogger(ActivityBlessingCardRecordServiceImpl.class);

	@Resource
	private ActivityBlessingCardRecordMapper activityBlessingCardRecordMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private HongbaoMapper hongbaoMapper;
	@Resource
	private InvestmentMapper investmentMapper;
	
	@Override
	public ActivityBlessingCardRecordMapper getMapper() {
		return activityBlessingCardRecordMapper;
	}
	
	@Resource
	private ActivityBlessingChanceRecordService activityBlessingChanceRecordService;
	
	@Override
	public void updateByExampleForVersion(ActivityBlessingCardRecord activityBlessingCardRecord) throws Exception {
		if (activityBlessingCardRecord.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (activityBlessingCardRecord.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		ActivityBlessingCardRecordExample example = new ActivityBlessingCardRecordExample();
		example.createCriteria().andIdEqualTo(activityBlessingCardRecord.getId())
				.andVersionEqualTo(activityBlessingCardRecord.getVersion());
		activityBlessingCardRecord.setVersion(activityBlessingCardRecord.getVersion() + 1);
		if (1 != activityBlessingCardRecordMapper.updateByExample(activityBlessingCardRecord, example)) {
			throw new LockFailureException();
		}
	}

	@Override
	public void updateByExampleSelectiveForVersion(ActivityBlessingCardRecord activityBlessingCardRecord)
			throws Exception {
		if (activityBlessingCardRecord.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (activityBlessingCardRecord.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		ActivityBlessingCardRecordExample example = new ActivityBlessingCardRecordExample();
		example.createCriteria().andIdEqualTo(activityBlessingCardRecord.getId())
				.andVersionEqualTo(activityBlessingCardRecord.getVersion());
		activityBlessingCardRecord.setVersion(activityBlessingCardRecord.getVersion() + 1);
		if (1 != activityBlessingCardRecordMapper.updateByExampleSelective(activityBlessingCardRecord, example)) {
			throw new LockFailureException();
		}
	}
	
	/*
	HEALTH_BULLS(1,"health_bulls", "健康牛"),
	HAPPY_BULLS(2, "happy_bulls", "快乐牛"),
	SAFETY_BULLS(3, "safety_bulls", "平安牛"),
	LUCKY_BULLS(4,"lucky_bulls", "幸运牛"),
	BENFU_BULLS(5,  "benfu_bulls", "奔富牛"),
	GREATE_BULLS(6, "greate_bulls", "牛气冲天"),
	*/
	@Override
	public void doCompoundGreateBulls(Integer userId) throws Exception{
		Date now=new Date();
		//防止一个用户同时多次提交合成
		User user=userMapper.selectByPrimaryKeyForUpdate(userId);
		
		/*
		ActivityBlessingCardRecordExample example = new ActivityBlessingCardRecordExample();
		ActivityBlessingCardRecordExample.Criteria baseCriteria=example.createCriteria();
		baseCriteria.andUserIdEqualTo(userId).andIsUsedEqualTo(false).andIsTransferEqualTo(false).andIsUnfinishedEqualTo(false);
		//HEALTH_BULLS
		ActivityBlessingCardRecordExample healthBullsExample = new ActivityBlessingCardRecordExample();
		healthBullsExample.createCriteria().andTypeEqualTo(HEALTH_BULLS.getType()).getCriteria().addAll(baseCriteria.getCriteria());
		ActivityBlessingCardRecord healthBulls;
		//HAPPY_BULLS
		ActivityBlessingCardRecordExample happyBullsExample = new ActivityBlessingCardRecordExample();
		happyBullsExample.createCriteria().andTypeEqualTo(HAPPY_BULLS.getType()).getCriteria().addAll(baseCriteria.getCriteria());
		ActivityBlessingCardRecord happyBulls;
		//SAFETY_BULLS
		ActivityBlessingCardRecordExample safetyBullsExample = new ActivityBlessingCardRecordExample();
		safetyBullsExample.createCriteria().andTypeEqualTo(SAFETY_BULLS.getType()).getCriteria().addAll(baseCriteria.getCriteria());
		ActivityBlessingCardRecord safetyBulls;
		//LUCKY_BULLS
		ActivityBlessingCardRecordExample luckyBullsExample = new ActivityBlessingCardRecordExample();
		luckyBullsExample.createCriteria().andTypeEqualTo(LUCKY_BULLS.getType()).getCriteria().addAll(baseCriteria.getCriteria());
		ActivityBlessingCardRecord luckyBulls;
		//BENFU_BULLS
		ActivityBlessingCardRecordExample benfuBullsExample = new ActivityBlessingCardRecordExample();
		benfuBullsExample.createCriteria().andTypeEqualTo(BENFU_BULLS.getType()).getCriteria().addAll(baseCriteria.getCriteria());
		ActivityBlessingCardRecord benfuBulls;
		*/
		//GREATE_BULLS
		ActivityBlessingCardRecordExample greateBullsExample = new ActivityBlessingCardRecordExample();
		greateBullsExample.createCriteria().andTypeEqualTo(GREATE_BULLS.getType()).andUserIdEqualTo(userId).andIsTransferEqualTo(false).andIsUnfinishedEqualTo(false);
		
		//预埋牛气冲天
		ActivityBlessingCardRecordExample preburyGreateBullsExample = new ActivityBlessingCardRecordExample();
		preburyGreateBullsExample.createCriteria().andTypeEqualTo(GREATE_BULLS.getType()).andUserIdEqualTo(userId).andIsTransferEqualTo(false).andIsUnfinishedEqualTo(true);
		ActivityBlessingCardRecord preburyGreateBulls = null;
		
		List<ActivityBlessingCardRecord> theBullList = activityBlessingCardRecordMapper.selectByExample(greateBullsExample);
		if(theBullList.size()>0) {
			throw new Exception("您已拥有2020牛气冲天卡，不可再次合成；多余卡片可转送给他人哦！！");
			//throw new Exception("已经有"+GREATE_BULLS.getDescription()+"了");
		}
		
		List<ActivityBlessingCardRecord> wufuBullList =new ArrayList<ActivityBlessingCardRecord>();
		
		for(LotteryBullsTypeEnum lotteryBullsTypeEnum:LotteryBullsTypeEnum.values()) {
			if(lotteryBullsTypeEnum==GREATE_BULLS || lotteryBullsTypeEnum==NOTHING) {
				continue;
			}
			ActivityBlessingCardRecordExample theBullExample = new ActivityBlessingCardRecordExample();
			theBullExample.createCriteria().andTypeEqualTo(lotteryBullsTypeEnum.getType()).andUserIdEqualTo(userId).andIsUsedEqualTo(false).andIsTransferEqualTo(false).andIsUnfinishedEqualTo(false);
			theBullList = activityBlessingCardRecordMapper.selectByExample(theBullExample);
			if(theBullList.size()>0) {
				wufuBullList.add(theBullList.get(0));
			}else {
				throw new Exception("缺少"+lotteryBullsTypeEnum.getDescription());
			}
		}
		
		theBullList = activityBlessingCardRecordMapper.selectByExample(preburyGreateBullsExample);
		if(theBullList.size()>1) {
			throw new Exception("VVIP用户您好,服务出错了,请联系客服");
		}else if(theBullList.size()>0) {
			preburyGreateBulls=theBullList.get(0);
		}
		
		for(ActivityBlessingCardRecord wufuBull:wufuBullList) {
			wufuBull.setIsUsed(true);
			wufuBull.setUseDate(now);
			this.updateByExampleForVersion(wufuBull);
		}
		
		if(preburyGreateBulls !=null ) {
			preburyGreateBulls.setIsUnfinished(false);
			preburyGreateBulls.setParentId(-1);
			preburyGreateBulls.setUseDate(now);
			this.updateByExampleForVersion(preburyGreateBulls);
		}else {
			ActivityBlessingCardRecord greateBulls = new ActivityBlessingCardRecord();
			greateBulls.setCardNo(LotteryBullsTypeEnum.GREATE_BULLS.getCode() + (int) ((Math.random() * 9 + 1) * 100000));
			greateBulls.setUserId(userId);
			greateBulls.setType(GREATE_BULLS.getType());
			greateBulls.setIsUsed(false);
			greateBulls.setIsTransfer(false);
			greateBulls.setIsUnfinished(false);
			greateBulls.setAmount(null);
			greateBulls.setIsDouble(null);
			greateBulls.setCreateDate(now);
			greateBulls.setUseDate(null);
			greateBulls.setChanceRecordId(null);
			greateBulls.setParentId(null);
			greateBulls.setIsFan(true);
			greateBulls.setVersion(0);
			activityBlessingCardRecordMapper.insert(greateBulls);
		}
	}
	
	@Override
	public void doTransformGreateBulls(ActivityBlessingCardRecord greateBulls) throws Exception{
		Date now=new Date();
		//ActivityBlessingCardRecord greateBulls = activityBlessingCardRecordMapper.selectByPrimaryKey(activityBlessingCardId);
		if(greateBulls.getType().intValue() != GREATE_BULLS.getType()) {
			throw new Exception(greateBulls.getId()+"不是牛气冲天");
		}
		if(greateBulls.getIsUsed()) {
			throw new Exception(greateBulls.getId()+"该牛气冲天已经兑换过了");
		}
		if(greateBulls.getIsTransfer()) {
			throw new Exception(greateBulls.getId()+"该牛气冲天已经转让了");
		}
		if(greateBulls.getIsUnfinished()) {
			throw new Exception(greateBulls.getId()+"该牛气冲天尚未完成");
		}
		
		//已使用的GREATE_BULLS
		ActivityBlessingCardRecordExample usedGreateBullsExample = new ActivityBlessingCardRecordExample();
		usedGreateBullsExample.createCriteria().andTypeEqualTo(GREATE_BULLS.getType()).andUserIdEqualTo(greateBulls.getUserId()).andIsUsedEqualTo(true).andIsTransferEqualTo(false).andIsUnfinishedEqualTo(false);
		List<ActivityBlessingCardRecord> usedGreateBullsList = activityBlessingCardRecordMapper.selectByExample(usedGreateBullsExample);
		if(usedGreateBullsList.size()>0) {
			throw new Exception(greateBulls.getId()+"该用户已近兑换过一个牛气冲天了");
		}
		//1<奖金≤9元 取整到角
    	//(11 + random(80)(0-79))/10
		/*
		1、非会员用户
			开奖区间为1<奖金≤9元  奖金不翻倍			
			小数点后1位派发
			例：红包金额为：1.1;1.2;1.3;...8.9;9
			红包派发几率一致；区间随机派发
		2、累计投资5000元（不含5000）以下的会员及VIP用户
			开奖区间为10<奖金≤20元（此区间是未翻倍的区间）实际派发翻倍
			会员翻倍开奖金额X 2
			显示效果为例：10X2（前端显示）
			因您是会员用户所以红包
			奖金翻倍；实际派发为20.2元
		3、累计投资5000-20000元（含5000；20000）的会员及VIP用户
			开奖区间为30<奖金≤40元 （此区间是未翻倍的区间）会员翻倍开奖金额X 2
		4、累计投资2-10万元（含2万；10万）的会员及VIP用户
			开奖区间为50<奖金≤60元 （此区间是未翻倍的区间）会员翻倍开奖金额X 2
		5、累计投资10-50万元（含10万；50万）的会员及VIP用户
			开奖区间为80<奖金≤90元 （此区间是未翻倍的区间）会员翻倍开奖金额X 2
		6、累计投资50万以上（不含50万）的会员及VIP用户
			开奖区间为100<奖金≤200元 （此区间是未翻倍的区间）会员翻倍开奖金额X 2
		*/
		Random random = new Random();
		//Integer userLevel =random.nextInt(6);//0-5
		Double userInvestmentAmount=investmentMapper.getInvestmentAmountCount(greateBulls.getUserId());
		
		BigDecimal ten=BigDecimal.valueOf(10);
		Integer baseAmount=null;
		Integer randomAmount=null;
		Boolean isDouble=false;
		if(userInvestmentAmount<=0) {//非会员用户	1<奖金≤20元	奖金不翻倍	(11 + random(190)[0-189])/10
			baseAmount=11;
			randomAmount=190;
		}else if(userInvestmentAmount<5000) {//5000元（不含5000）以下	10<奖金≤30元	翻倍	(101 + random(200)[0-199])/10
			baseAmount=101;
			randomAmount=200;
			isDouble=true;
		}else if(userInvestmentAmount<20000) {//5000-20000元（含5000；20000）	30<奖金≤40		翻倍	(301 + random(100)[0-99])/10
			baseAmount=301;
			randomAmount=100;
			isDouble=true;
		}else if(userInvestmentAmount<100000) {//2-10万元（含2万；10万）	50<奖金≤60		翻倍	(501 + random(100)[0-99])/10
			baseAmount=501;
			randomAmount=100;
			isDouble=true;
		}else if(userInvestmentAmount<500000) {//10-50万元（含10万；50万）	80<奖金≤90		翻倍	(801 + random(100)[0-99])/10
			baseAmount=801;
			randomAmount=100;
			isDouble=true;
		}else if(true) {//50万以上（不含50万）	100<奖金≤200元	翻倍	(1001 + random(1000)[0-999])/10
			baseAmount=1001;
			randomAmount=1000;
			isDouble=true;
		}
		greateBulls.setIsDouble(isDouble);
		if(greateBulls.getAmount()==null) {
			BigDecimal 	ratio=BigDecimal.ONE;
			if(isDouble) {
				ratio=BigDecimal.valueOf(2);
			}
			greateBulls.setAmount(BigDecimal.valueOf((baseAmount+random.nextInt(randomAmount))).divide(ten).multiply(ratio).doubleValue());
		}
		greateBulls.setIsUsed(true);
		greateBulls.setUseDate(now);
		this.updateByExampleForVersion(greateBulls);
		
		Hongbao hongbao = new Hongbao();
		hongbao.setUserId(greateBulls.getUserId());
		hongbao.setType(HongbaoTypeEnum.CASH.getCode());
		hongbao.setAmount(greateBulls.getAmount()); //金额
		hongbao.setLimitAmount(0);
		hongbao.setLimitDay(0);
		hongbao.setSource(114); // 来源
		hongbao.setDescript("牛气冲天红包");
		hongbao.setSendTime(now);
		Date expTime = DateFormatTools.jumpOneDay(now, 30);
		hongbao.setExpireTime(expTime);
		hongbao.setActivityDetailId(null);
		hongbao.setTriggerType(0);// activity_detail.trigger_type 0.特殊活动 1.注册 2.投资 3.消费 4.登录 5.邀请首投 6.回购
		hongbao.setOtherId(greateBulls.getId());
		hongbaoMapper.insert(hongbao);
	}
	
	@Override
	public List<ActivityBlessingCardRecord> selectAllUnusedGreateBulls() throws Exception{
		ActivityBlessingCardRecordExample unusedGreateBullsExample = new ActivityBlessingCardRecordExample();
		unusedGreateBullsExample.createCriteria().andTypeEqualTo(GREATE_BULLS.getType()).andIsUsedEqualTo(false).andIsTransferEqualTo(false).andIsUnfinishedEqualTo(false);
		return activityBlessingCardRecordMapper.selectByExample(unusedGreateBullsExample);
	}

    @Override
    public List<ActivityBlessingCardRecord> listBlessingCard(Integer userId, LotteryBullsTypeEnum lotteryBullsTypeEnum,
        boolean isFan) {
        ActivityBlessingCardRecordExample example = new ActivityBlessingCardRecordExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsTransferEqualTo(false).andIsUnfinishedEqualTo(false);
        if(userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(lotteryBullsTypeEnum != null) {
            criteria.andTypeEqualTo(lotteryBullsTypeEnum.getType());
            if(lotteryBullsTypeEnum.getType() != LotteryBullsTypeEnum.GREATE_BULLS.getType()) {
                criteria.andIsUsedEqualTo(false);
            }
        }
        if(isFan) {
            criteria.andIsFanEqualTo(true);
        } else {
            criteria.andIsFanEqualTo(false);
        }
        
        return activityBlessingCardRecordMapper.selectByExample(example);
    }

    @Override
    public int countBlessingCard(Integer userId, LotteryBullsTypeEnum lotteryBullsTypeEnum, boolean isFan) {
        ActivityBlessingCardRecordExample example = new ActivityBlessingCardRecordExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsTransferEqualTo(false).andIsUnfinishedEqualTo(false);
        if(userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(lotteryBullsTypeEnum != null) {
            criteria.andTypeEqualTo(lotteryBullsTypeEnum.getType());
            if(lotteryBullsTypeEnum.getType() != LotteryBullsTypeEnum.GREATE_BULLS.getType()) {
                criteria.andIsUsedEqualTo(false);
            }
        }
        if(isFan) {
            criteria.andIsFanEqualTo(true);
        } else {
            criteria.andIsFanEqualTo(false);
        }
        
        return (int)activityBlessingCardRecordMapper.countByExample(example);
    }
	
	@Override
	public ActivityBlessingCardRecord insertCardRecord(Integer userId, ProbabilityBullsEnum hitBulls) throws Exception {
		if(hitBulls == null) {
			throw new Exception("用户抽卡为空");
		}
		// 使用一张卡
		ActivityBlessingChanceRecord chanceRecord = activityBlessingChanceRecordService.useOneChance(userId);
		if(chanceRecord == null) {
			throw new Exception("用户抽卡为空");
		}
		// 生成用卡记录
		ActivityBlessingCardRecord cardRecord = new ActivityBlessingCardRecord();
		cardRecord.setUserId(userId);
		cardRecord.setIsTransfer(false);
		//卡牌简拼+6位随机数 生成cardNo
		cardRecord.setCardNo(hitBulls.getLotteryBulls().getCode() + (int) ((Math.random() * 9 + 1) * 100000));
		cardRecord.setChanceRecordId(chanceRecord.getId());
		cardRecord.setCreateDate(new Date());
		cardRecord.setIsFan(true);
		cardRecord.setIsUnfinished(false);
		cardRecord.setType(hitBulls.getLotteryBulls().getType()); 
		cardRecord.setIsUsed(false);
		cardRecord.setIsDouble(false);
		cardRecord.setVersion(1);
		if(activityBlessingCardRecordMapper.insert(cardRecord) != 1) {
			throw new Exception("生成用卡记录失败");
		}
		return cardRecord;
	}


	@Override
	public ActivityBlessingCardRecord getByUserIdAndCardNo(Integer userId, String cardNo) {
		ActivityBlessingCardRecordExample example = new ActivityBlessingCardRecordExample();
		example.setOrderByClause(" id desc ");
		example.createCriteria()
				.andUserIdEqualTo(userId)
				.andCardNoEqualTo(cardNo)
				.andIsUsedEqualTo(false)
		;
		List<ActivityBlessingCardRecord> list = activityBlessingCardRecordMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

    @Override
    public int getJoinUserCount() {
        return activityBlessingCardRecordMapper.getJoinUserCount();
    }

	public static void main(String[] args) throws Exception {
		System.out.println(AESUtil.decrypt("GkGzHg0S+DbTrq6HCAaysQ==="));
	}

}
