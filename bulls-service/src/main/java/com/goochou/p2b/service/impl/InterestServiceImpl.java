package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.goochou.p2b.model.vo.InterestVO;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.CapitalMapper;
import com.goochou.p2b.dao.CurrentInvestmentMapper;
import com.goochou.p2b.dao.CurrentRedeemMapper;
import com.goochou.p2b.dao.InterestMapper;
import com.goochou.p2b.dao.InvestmentDetailMapper;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.dao.ProjectMapper;
import com.goochou.p2b.dao.RateCouponMapper;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.dao.UserInvestConfigDetailsMapper;
import com.goochou.p2b.dao.UserInvestConfigMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.AssetsExample;
import com.goochou.p2b.model.Capital;
import com.goochou.p2b.model.Interest;
import com.goochou.p2b.model.InterestExample;
import com.goochou.p2b.model.InterestExample.Criteria;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.InvestmentDetail;
import com.goochou.p2b.model.InvestmentDetailExample;
import com.goochou.p2b.model.InvestmentExample;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectExample;
import com.goochou.p2b.model.RateCoupon;
import com.goochou.p2b.model.TradeRecord;
import com.goochou.p2b.model.UserDailyInvest;
import com.goochou.p2b.model.UserInvestConfig;
import com.goochou.p2b.model.UserInvestConfigDetails;
import com.goochou.p2b.model.vo.AnXinZhuanOfflineDataVO;
import com.goochou.p2b.service.InterestService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;

@Service
public class InterestServiceImpl implements InterestService {

	private static final Logger logger = Logger.getLogger(InterestServiceImpl.class);

    @Resource
    private InterestMapper interestMapper;
    @Resource
    private InvestmentMapper investmentMapper;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private CapitalMapper capitalMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private InvestmentDetailMapper investmentDetailMapper;
    @Resource
    private RateCouponMapper rateCouponMapper;

    @Resource
    private CurrentRedeemMapper currentRedeemMapper;

    @Resource
    private CurrentInvestmentMapper currentInvestmentMapper;

    @Resource
    private UserInvestConfigDetailsMapper userInvestConfigDetailsMapper;

    @Resource
    private UserInvestConfigMapper userInvestConfigMapper;

    @Override
    public Double getUncollectInterestAmountByUserId(Integer userId) {
        return interestMapper.getUncollectInterestAmountByUserId(userId);
    }

    @Override
    public List<Map<String, Double>> statisticsByYear(int userId) {
        return interestMapper.statisticsByYear(userId);
    }

    @Override
    public List<Map<String, Object>> statisticsByMonth(int userId) {
        return interestMapper.statisticsByMonth(userId);
    }

    @Override
    public List<Map<String, Object>> findByUserId(Integer userId, int start, int limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return interestMapper.findByUserId(map);
    }

    @Override
    public List<Map<String, Object>> findCapitalByUserId(Integer userId, int start, int limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return interestMapper.findCapitalByUserId(map);
    }

    @Override
    public List<Interest> getUndividendedByDate(Date date, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("limit", limit);
        return interestMapper.getUndividendedByDate(map);
    }

    @Override
    public void getUndividendedByDateMessage() throws Exception {
        List<Map<String, Object>> list = interestMapper.getUndividendedByDateMessage();
        if (!list.isEmpty() && list.size() > 0) {
            for (Map<String, Object> map : list) {
                if (map.get("phone") != null) {
                    String phone = map.get("phone").toString();
                    Double amount = Double.valueOf(map.get("amount").toString());
                    String content = "尊敬的鑫聚财用户，您今日回款" + BigDecimalUtil.fixed2(amount) + "元，现已派发至账户余额。TD退订【鑫聚财】 ";
                    //SendMessageUtils.send(content, phone);
                }
            }
        }
    }

    @Override
    public boolean grantInterestToUser(Interest interest) throws Exception {
        boolean b = false;
        double capital = interest.getCapitalAmount(); // 获取当前回收本金的金额
        double interestAmount = interest.getInterestAmount(); // 获取应该获得的利息金额
        double addInterest = interest.getAddInterest(); // 获取应该获得的加息金额


        logger.info("定期派息定时任务执行的记录为interestId ==> " + interest.getId());
        //如果是月月盈派息任务
//        if(interest.getInvestmentDetailId() != null){
//        	logger.info("============月月盈资产派息 ==>>  本次需要计算的本金为:【" + capital + "】,需要计算的利息为:【" + interestAmount + "】,需要计算的加息为:【" + addInterest + "】==================");
//
////          此处注释，为了事务
////       	try {
//        		return grantMonthlyGainInterestToUser(interest);
////			} catch (Exception e) {
////				// 此处是否需要抛出异常，保证事务完整
////				logger.info("月月盈资产派息异常："+e);
////				return b;
////			}
//
//        }else{
//            logger.info("============普通资产派息 ==>>  本次需要计算的本金为:【" + capital + "】,需要计算的利息为:【" + interestAmount + "】,需要计算的加息为:【" + addInterest + "】==================");
//            // 修改investment
//            Investment investment = investmentMapper.selectByPrimaryKey(interest.getInvestmentId());
//            Integer userId = investment.getUserId();
////            logger.info("11111reamount="+investment.getRemainAmount());
////            investment.setRemainAmount(BigDecimalUtil.sub(investment.getRemainAmount(), capital)); // 投资剩余金额需要减去回收的本金金额
////            investment.setInterestUsableAmount(BigDecimalUtil.sub(investment.getInterestUsableAmount(), capital)); // 可用于计算利息的金额
//            investmentMapper.updateByPrimaryKeySelective(investment);
//            // 修改用户资产之前需要把 当前 利息-->投资-->项目 如果当前计算利息的项目有债券转让在挂着的话，不管什么状态直接 改成取消状态
//            logger.info("=============开始查询当前需要取消的债券转让==============");
//            ProjectExample projectExample = new ProjectExample();
//            com.goochou.p2b.model.ProjectExample.Criteria projectCriteria = projectExample.createCriteria();
//            projectCriteria.andParentIdEqualTo(investment.getProjectId()); // 当前债券转让的项目是需要计算利息的项目
//            projectCriteria.andUserIdEqualTo(investment.getUserId()); // 并且当前债券是该用户发起的
//            projectCriteria.andStatusEqualTo(0); // 并且当前债券状态还是转让中的状态
//            List<Project> projects = projectMapper.selectByExample(projectExample);
//            logger.info("=============查询到当前需要取消的债券转让==============" + projects.size());
//            if (null != projects && !projects.isEmpty()) {
//                for (Project p : projects) {
//                    logger.info("=============修改当前[" + p.getId() + "]债券状态到取消==============");
//                    p.setStatus(2); // 取消
//                    projectMapper.updateByPrimaryKeySelective(p);
//
//                    //investment = p.getInvestment();
//                    //remain_amount还原
////                    investment.setRemainAmount(BigDecimalUtil.sub(
////                            BigDecimalUtil.add(investment.getRemainAmount(), p.getTotalAmount()), p.getInvestedAmount()));
//
////                    if (investment.getRemainAmount() > investment.getAmount()) {
////                        investment.setRemainAmount(investment.getAmount());
////                    }
////                    if (investment.getRemainAmount() < 0) {
////                        investment.setRemainAmount(0d);
////                    }
//                    investmentMapper.updateByPrimaryKeySelective(investment);
//                    logger.info("=============修改当前[" + p.getId() + "]债券状态到取消完成==============");
//                }
//            }
//
//            // 获取用户资产
//            AssetsExample assetsExample = new AssetsExample();
//            assetsExample.createCriteria().andUserIdEqualTo(userId);
//            List<Assets> assetsList = assetsMapper.selectByExample(assetsExample);
//            Assets assets = assetsList.get(0);
//
//            // 修改用户的assets
//            double availableBalance = assets.getAvailableBalance();
//            double frozenBalance = assets.getFrozenAmount();
//            assets.setAvailableBalance(BigDecimalUtil.add(assets.getAvailableBalance(), capital, interestAmount, addInterest)); // 账户总资产+获取的回收本金金额+获取的利息金额+获取的加息金额
//            assets.setTotalIncome(BigDecimalUtil.add(assets.getTotalIncome(), interestAmount, addInterest)); // 总的投资收益
//            assets.setUncollectCapital(BigDecimalUtil.sub(assets.getUncollectCapital(), capital)); // 待收本金
//            assets.setUncollectInterest(BigDecimalUtil.sub(assets.getUncollectInterest(), interestAmount, addInterest)); // 待收利息
//
//            //自动续投配置，赎回金额视情况冻结
//            double uicBalance = 0d;
//            double uicFrozen = 0d;
//    		List<UserInvestConfigDetails> configDetails = userInvestConfigDetailsMapper.queryConfigDetails(interest.getId());
//    		if(null != configDetails && configDetails.size()>0){
//    			UserInvestConfigDetails uicd = configDetails.get(0);
//    			//assets.setAvailableBalance(BigDecimalUtil.add(availableBalance, uicd.getAvailableBalance(), interestAmount, addInterest));
//    			//assets.setFrozenAmount(BigDecimalUtil.add(frozenBalance, uicd.getFrozenAmount()));
//    			uicBalance = uicd.getAvailableBalance();
//    			uicFrozen = uicd.getFrozenAmount();
//    			//更新需要续投金额
//    			UserInvestConfig uic = userInvestConfigMapper.selectByPrimaryKey(uicd.getInvestConfigId());
//    			uic.setNeedInvestAmount(BigDecimalUtil.add(uic.getNeedInvestAmount(), uicd.getFrozenAmount()));
//    			int ret = userInvestConfigMapper.updateByPrimaryKeyAndVersionSelective(uic);
//    			if (ret != 1) {
//                    throw new LockFailureException();
//                }
//    		}
//
//            // 插入交易记录表 -- 收益利息记录
//            TradeRecord tr = new TradeRecord();
//            tr.setAmount(interestAmount);
//            //tr.setBalance(BigDecimalUtil.add(availableBalance, uicBalance, interestAmount));
//            tr.setOtherId(interest.getId());
//            tr.setTableName("interest");
//            tr.setCreateDate(new Date());
////            tr.setType(3);
//            tr.setUserId(userId);
//            //tr.setFrozenBalance(BigDecimalUtil.add(frozenBalance, uicFrozen));
//            tradeRecordMapper.insertSelective(tr);
//
//            // 如果本金大于0的话 插入本金回收交易记录
//            if (capital > 0) {
//                Capital ca = new Capital();
//                ca.setAmount(capital);
//                ca.setInvestmentId(investment.getId());
//                ca.setTime(new Date());
//                capitalMapper.insert(ca);
//
//                TradeRecord ctr = new TradeRecord();
//                ctr.setAmount(capital);
////                if(uicBalance>0){
////                	ctr.setBalance(BigDecimalUtil.add(availableBalance, interestAmount, uicBalance));
////                }else{
////                	ctr.setBalance(BigDecimalUtil.add(availableBalance, interestAmount, capital));
////                }
//                ctr.setOtherId(ca.getId());
//                ctr.setTableName("capital");
//                ctr.setCreateDate(new Date());
////                ctr.setType(4);
//                ctr.setUserId(userId);
////                ctr.setFrozenBalance(BigDecimalUtil.add(frozenBalance, uicFrozen));
//                tradeRecordMapper.insertSelective(ctr);
//            }
//            // 如果有新加息券
//            if(addInterest>0){
//            	TradeRecord tradeRecord = new TradeRecord();
//                tradeRecord.setCreateDate(new Date());
////                if(uicBalance>0){
////                	tradeRecord.setBalance(BigDecimalUtil.add(availableBalance, interestAmount, uicBalance, addInterest));
////                }else{
////                	tradeRecord.setBalance(BigDecimalUtil.add(availableBalance, interestAmount, capital, addInterest));
////                }
//                tradeRecord.setAmount(addInterest);
//                tradeRecord.setUserId(userId);
////                tradeRecord.setSource(7);
//                tradeRecord.setTableName("rate_coupon");
////                tradeRecord.setOtherId(investment.getCouponId());
////                tradeRecord.setType(3);
//                //tradeRecord.setFrozenBalance(BigDecimalUtil.add(frozenBalance, uicFrozen));
//                tradeRecordMapper.insertSelective(tradeRecord);
//                RateCoupon rc = new RateCoupon();
////                rc.setId(investment.getCouponId());
//                rc.setHasDividended(1);
//                rateCouponMapper.updateByPrimaryKey(rc);
//            }
//
////            if (assets.getUncollectCapital() < 0) {
////                //不能小于0
////                logger.info("=========本金将要小于0，异常返回============");
////                return true;
////            }
//            int ret = assetsMapper.updateByPrimaryKeyAndVersionSelective(assets);
//            if (ret != 1) {
//                throw new LockFailureException();
//            }
//            // 修改interest状态
//            interest.setHasDividended(1);
//            interestMapper.updateByPrimaryKeySelective(interest);
//            b = true;
//        }

        return b;
    }

    @Override
    public Double getInterestedAmountByUserId(Integer userId) {
        return interestMapper.getInterestedAmountByUserId(userId);
    }

    @Override
    public List<Interest> getByInvestmentId(Integer investmentId, Integer start, Integer limit) {
        InterestExample example = new InterestExample();
        if (start != null && limit != null) {
            example.setLimitStart(start);
            example.setLimitEnd(limit);
        }
        Criteria c = example.createCriteria();
        c.andInvestmentIdEqualTo(investmentId);
        example.setOrderByClause("id asc");
        return interestMapper.selectByExample(example);
    }

    @Override
    public int getCountByInvestmentId(Integer investmentId) {
        InterestExample example = new InterestExample();
        Criteria c = example.createCriteria();
        c.andInvestmentIdEqualTo(investmentId);
        example.setOrderByClause("id asc");
        return interestMapper.countByExample(example);
    }

    public List<Interest> getCreditorIncomeByInvestmentId(Integer investmentId) {
        InterestExample example = new InterestExample();
        Criteria c = example.createCriteria();
        c.andInvestmentIdEqualTo(investmentId);
        c.andHasDividendedEqualTo(0);
        example.setOrderByClause("date");
        return interestMapper.selectByExample(example);
    }

    @Override
    public List<Interest> getByInvestmentDetailId(Integer investmentDetailId) {
        InterestExample example = new InterestExample();
        Criteria c = example.createCriteria();
//        c.andInvestmentDetailIdEqualTo(investmentDetailId);
        return interestMapper.selectByExample(example);
    }


    @Override
    public Double getUserDynamicInterest(Integer userId, Integer type) {
        InterestExample example = new InterestExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        c.andTypeEqualTo(0);
        Calendar cal = Calendar.getInstance();
        if (type == 0) {
            c.andDateEqualTo(cal.getTime()); // 昨日收益
        } else if (type == 1) {
            // 近一周收益
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 6);
            c.andDateGreaterThanOrEqualTo(cal.getTime());
        } else if (type == 2) {
            // 近一月收益
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
            c.andDateGreaterThanOrEqualTo(cal.getTime());
        }
        List<Interest> list = interestMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            double sum = 0d;
            for (Interest i : list) {
                sum += i.getInterestAmount();
            }
            return sum;
        }
        return null;
    }

    public int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    @Override
    public List<Map<String, Object>> query(String keyword, Date startTime, Date endTime, Integer start, Integer end) {
        return interestMapper.query(keyword, startTime, endTime, start, end);
    }

    @Override
    public Long queryCount(String keyword, Date startTime, Date endTime) {
        return interestMapper.queryCount(keyword, startTime, endTime);
    }

    @Override
    public Map<String, Object> queryTotalAmount(String keyword, Date startTime, Date endTime) {
        return interestMapper.queryTotalAmount(keyword, startTime, endTime);
    }

    @Override
    public List<Interest> detailByUser(Integer userId, Date startTime, Date endTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("start", start);
        map.put("limit", limit);
        return interestMapper.detailByUser(map);
    }

    @Override
    public Integer detailCount(Integer userId, Date startTime, Date endTime) {
        return interestMapper.detailCount(userId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> queryDailyExtractInterest(int timeSlot) {
        return interestMapper.queryDailyExtractInterest(timeSlot);
    }

    @Override
    public List<Interest> get7Day(Integer userId) {
        List<Interest> list = interestMapper.get7Day(userId);
        if (list.size() != 7) {
            List<Date> dates = new ArrayList<Date>();
            for (int i = 0; i < 7; i++) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                c.set(Calendar.DATE, c.get(Calendar.DATE) - i);
                dates.add(c.getTime());
            }
            Map<Date, Interest> map = new TreeMap<Date, Interest>();

            for (Date date : dates) {
                Interest i = new Interest();
                i.setInterestAmount(0d);
                i.setDate(date);
                map.put(date, i);
            }
            if (null != list && !list.isEmpty()) {
                for (Interest interest : list) {
                    map.put(interest.getDate(), interest);
                }
            }
            return new ArrayList<Interest>(map.values());
        }
        return list;
    }

    @Override
    public Interest queryLatestPaymentInfo(Integer userId, Integer investmentId, Integer investmentDetailId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("investmentId", investmentId);
        map.put("investmentDetailId", investmentDetailId);
        return interestMapper.queryLatestPaymentInfo(map);
    }

    @Override
    public List<Map<String, Object>> selectUserPaymentList(String keyword, Date startTime, Date endTime, Integer start, Integer limit, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("adminId", adminId);
        map.put("start", start);
        map.put("limit", limit);
        return interestMapper.selectUserPaymentList(map);
    }

    @Override
    public Integer selectUserPaymentCount(String keyword, Date startTime, Date endTime, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("adminId", adminId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return interestMapper.selectUserPaymentCount(map);
    }

    @Override
    public List<Map<String, Object>> selectUserPaymentSum(String keyword, Date startTime, Date endTime, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("adminId", adminId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return interestMapper.selectUserPaymentSum(map);
    }

    public Double getInterestedAmountByMonth(Date date) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        return interestMapper.getInterestedAmountByMonth(map);
    }

    @Override
    public void update(Interest interest) {
        interestMapper.updateByPrimaryKeySelective(interest);
    }

    @Override
    public List<Interest> getPackageUnInterest() {
        InterestExample example = new InterestExample();
        example.createCriteria().andTypeEqualTo(2);
        return interestMapper.selectByExample(example);
    }

    @Override
    public List<Interest> selectRegularInterestList(Date now, Integer projectId, Double annualized) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("now", now);
        map.put("projectId", projectId);
        map.put("annualized", annualized);
        return interestMapper.selectRegularInterestList(map);
    }

    @Override
    public List<Interest> selectRegularInterestListTwo(Date now, Integer projectId, Double annualized) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("now", now);
        map.put("projectId", projectId);
        map.put("annualized", annualized);
        return interestMapper.selectRegularInterestListTwo(map);
    }

    @Override
    public List<Interest> queryTodayInterestInfo() {
        String date = DateFormatTools.dateToStr2(new Date());
        return interestMapper.queryIterestBackAmount(date);
    }

	@Override
	public List<Interest> queryInterestInfoByInvestemntInfo(AnXinZhuanOfflineDataVO date) {

		return interestMapper.queryInterestInfoByInvestemntInfo(date.getInvestmentId());
	}

	@Override
	public Double[] offlineDataOperate(AnXinZhuanOfflineDataVO data, String type) {

		Double totalInterest = 0d;
		Double totalCapital = 0d;

		Double annualizedMin = data.getAnnualizedMin();
		Integer userId = data.getUserId();

		try {

			logger.info("***********处理安鑫赚数据(deleteAndInsertInterestData): annualizedMin: " + annualizedMin + " **********************");

			//查询的interest信息
			List<Interest> interestList =interestMapper.queryInterestInfoByInvestemntInfo(data.getInvestmentId());
			logger.info("***********处理安鑫赚数据(queryInterestInfoByInvestemntInfo): interestList: " + interestList + " **********************");

			int operateCount = interestList == null ? 0 : interestList.size();
			logger.info("***********处理安鑫赚数据(queryInterestInfoByInvestemntInfo): interest条数: " + operateCount + " **********************");
			if(operateCount == 0){
				throw new RuntimeException("queryInterestInfoByInvestemntInfo: interest条数: " + operateCount);
			}

			InterestExample example = new InterestExample();
			Integer investmentId = data.getInvestmentId();

			//查询investment_detail表
			Integer []status = new Integer[]{0,1};
			InvestmentDetailExample investmentDetailExample = new InvestmentDetailExample();
			investmentDetailExample.createCriteria().andInvestmentIdEqualTo(investmentId).andStatusIn(Arrays.asList(status));
			List<InvestmentDetail> investmentDetailList = investmentDetailMapper.selectByExample(investmentDetailExample);
			if(investmentDetailList == null || investmentDetailList.size() == 0){
				throw new RuntimeException("根据investmentId查询investment_detail数据失败");
			}

			List<Integer> investmentDetailIds = new ArrayList<>();
			for(InvestmentDetail detail : investmentDetailList){
				investmentDetailIds.add(detail.getId());
			}

			logger.info("***********处理安鑫赚数据: InvestmentId: " + investmentId + " investmentDetailIds: " + investmentDetailIds + " **********************");

			//删除
			example.createCriteria().andUserIdEqualTo(userId)
									.andInvestmentIdEqualTo(investmentId)
//									.andInvestmentDetailIdIn(investmentDetailIds)
									.andHasDividendedEqualTo(0);
			Integer deleteCount = interestMapper.deleteByExample(example);
			if(deleteCount == 0){
				throw new RuntimeException("删除interest数据失败 : 删除条数为0");
			}

			if(deleteCount != interestList.size()){
				throw new RuntimeException("删除interest数据失败 : 删除条数不正确: deleteCount: " + deleteCount + ", interestList.size(): " + interestList.size());
			}

			//需要补派息数据
			Interest addInterest = null;
			List<Interest> addInterestList = new ArrayList<>();
			Double interestAmount = 0d;
			for(InvestmentDetail detail : investmentDetailList){
				Double interest = BigDecimalUtil.fixed2(detail.getAmount() * data.getDay() * annualizedMin / 365);
				logger.info("***********处理安鑫赚数据(补派息数据): 需要补利息: " + interest + " 元**********************");

				addInterest = new Interest();
				addInterest.setUserId(detail.getUserId());
				addInterest.setInvestmentId(detail.getInvestmentId());
//				addInterest.setInvestmentDetailId(detail.getId());
				addInterest.setInterestAmount(interest);
				addInterest.setCapitalAmount(detail.getAmount());
				addInterest.setHasDividended(1);
//				addInterest.setHasCast(0);
//				addInterest.setHasCastCapital(0);
				addInterest.setType(2);

				addInterestList.add(addInterest);

				interestAmount += interest;
			}

			//插入

			int insertInterestSussessCount = interestMapper.insertBatch(addInterestList);
			if(insertInterestSussessCount == 0){
				throw new RuntimeException("插入派息数据失败");
			}

			if(insertInterestSussessCount != addInterestList.size()){
				throw new RuntimeException("插入派息数据失败: 插入的条数补正确: " + insertInterestSussessCount + "条 与addInterestList.size():" + addInterestList.size() + "条");
			}

			logger.info("***********处理安鑫赚数据(插入派息数据) 成功: addInterestList " + addInterestList + " **********************");

			//---------------------------------------------------第二步, 更新investment信息, 更新investment_detail信息---------------------------------------------------
			logger.info("***********处理安鑫赚数据: investmentDetailIds: " + investmentDetailIds + " , investmentId: " + investmentId + "**********************");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("investmentId", investmentId);
			params.put("interestAmount", interestAmount);
			Integer investmentUpdateCount = investmentMapper.updateBatchInvestmentInfoForAnXinZhuan(params);
			if(investmentUpdateCount == 0){
				throw new RuntimeException("更新investment信息失败, 更新条数为0");
			}

			for(Integer investmentDetailId : investmentDetailIds){
				if(investmentDetailMapper.updateForAnXinZhuan(investmentDetailId) <= 0){
					throw new RuntimeException("更新investment_detail信息失败, investmentDetailId: " + investmentDetailId);
				}
			}


			//---------------------------------------------------第三步, 插入交易日志, 及更新账户---------------------------------------------------
			//查询账户余额
			Assets assets = assetsMapper.selectByPrimaryKey(userId);

//			Double availableBalance = assets.getAvailableBalance();
//			Double uncollectCapital = assets.getUncollectCapital();
			//Double totalIncome = assets.getTotalIncome();

			//-------------------------------------------------------从investment取本金数据------------------------------------------------------
			//查询出所有capital数据
			InvestmentExample investmentExample = new InvestmentExample();
			investmentExample.createCriteria().andIdEqualTo(investmentId);
			List<Investment> investmentList = investmentMapper.selectByExample(investmentExample);

			if(investmentList == null || investmentList.size() == 0){
				throw new RuntimeException("查询出所有investmentList数据: 条数为0");
			}

			List<TradeRecord> recordList = new ArrayList<>();
			TradeRecord record = null;

			Investment investment = investmentList.get(0);
			if(type.equals("5")){
				//批量把capital数据插入trade_record中, 并记录下totalAmount
				//3.INSERT INTO `trade_record` (`id`, `user_id`, `other_id`, `table_name`, `type`, `source`, `time`, `amount`, `huo_balance`, `balance`) VALUES
				//(null, '31314', 25349, 'capital', '15', '5', now(), '65.2', null, '0.83');
				record = new TradeRecord();
				record.setUserId(userId);
				record.setOtherId(investment.getId());
				record.setTableName("investment");
//				record.setType(15);
//				record.setSource(5);
				record.setAmount(investment.getAmount());
				record.setReamk("安鑫赚批量处理");
				record.setCreateDate(new Date());

				//availableBalance += investment.getAmount();


				//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$总本金金额$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
				totalCapital += investment.getAmount();

				//record.setBalance(availableBalance);

				logger.info("***********处理安鑫赚数据: 要添加的Capital信息: " +
						"investment.id: " + investment.getId()
						+ " investment.getAmount(): " + investment.getAmount() + " **********************");

				int investmentInsertSuccessCount = tradeRecordMapper.insert(record);

				if(investmentInsertSuccessCount <= 0){
					throw new RuntimeException("批量把investment数据插入trade_record失败, 条数为0");
				}

			}

			//uncollectCapital -= investment.getAmount();
			logger.info("***********处理安鑫赚数据: 扣除待收本金: " + investment.getAmount() + " **********************");

			//批量把interest数据插入trade_record中, 并记录下totalAmount
			//INSERT INTO `trade_record` (`id`, `user_id`, `other_id`, `table_name`, `type`, `source`, `time`, `amount`, `huo_balance`, `balance`) VALUES
			//(null, '31314', 828687, 'interest', '3', '5', now(), '0.45', null, '66.03');
			for(Interest interest : addInterestList){
				record = new TradeRecord();
				record.setUserId(userId);
//				record.setOtherId(interest.getInvestmentDetailId());
				record.setTableName("investment_detail");
//				record.setType(3);
//				record.setSource(5);
				record.setAmount(interest.getInterestAmount());
				record.setReamk("安鑫赚批量处理");

				//availableBalance += interest.getInterestAmount();
				//totalIncome += interest.getInterestAmount();

				//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$总派息金额$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
				totalInterest += interest.getInterestAmount();

				//record.setBalance(availableBalance);

				recordList.add(record);

				logger.info("***********处理安鑫赚数据: 要添加的interest信息: " +
						"interest.id: " + addInterest.getId()
						+ " interest.getInterestAmount(): " + addInterest.getInterestAmount() + " **********************");
			}

			int interestInsertSuccessCount = tradeRecordMapper.insertBatch(recordList);

			if(interestInsertSuccessCount <= 0){
				throw new RuntimeException("批量把interest数据插入interest失败, 条数为0");
			}

			//更新assets
//			assets.setAvailableBalance(availableBalance);
//			assets.setUncollectCapital(uncollectCapital);
////			assets.setUncollectInterest(uncollectInterest);
//			assets.setTotalIncome(totalIncome);
			if(assetsMapper.updateByPrimaryKeyAndVersionSelective(assets) <= 0){
				throw new RuntimeException("inertTradeRecordBatchByInterest: , 更新用户账户失败");
			}

		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e.getMessage() + " ,userId: " + userId);
		}

		return new Double[]{totalInterest, totalCapital};

	}


	@Override
	public List<Interest> selectMonthlyGainInterestList(Date now, Integer projectId, double annualized) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("now", now);
        map.put("projectId", projectId);
        map.put("annualized", annualized);
        return interestMapper.selectMonthlyGainInterestList(map);
	}

	@Override
	public Double getNoDividendPayout(Integer userId) {
		return interestMapper.getNoDividendPayout(userId);
	}

	@Override
	public List<Map<String, Object>> investIntellectualList(String keyword, Date startTime, Date endTime, Integer start,Integer limit,Integer adminId,String quitable,Integer limitDay) {
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("keyword", keyword);
	    if(startTime!=null) {
	    	map.put("startTime", DateUtil.dateTimeZoreFormat.format(startTime));
	    }
	    if(endTime!=null) {
	    	map.put("endTime", DateUtil.dateTimeMaxSMFormat.format(endTime));
	    }
	    map.put("adminId", adminId);
	    map.put("quitable", quitable);
	    map.put("limitDay", limitDay);
        map.put("start", start);
        map.put("limit", limit);
		return interestMapper.queryInvestIntellectualList(map);
	}

	@Override
	public Integer investIntellectualListCount(String keyword, Date startTime, Date endTime,Integer adminId,String quitable,Integer limitDay) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("adminId", adminId);
        map.put("quitable", quitable);
	    map.put("limitDay", limitDay);
        if(startTime!=null) {
	    	map.put("startTime", DateUtil.dateTimeZoreFormat.format(startTime));
	    }
	    if(endTime!=null) {
	    	map.put("endTime", DateUtil.dateTimeMaxSMFormat.format(endTime));
	    }
		return interestMapper.queryInvestIntellectualListCount(map);
	}

	@Override
	public Double investIntellectualListCountTotalAmount(String keyword, Date startTime, Date endTime,
			Integer adminId,String quitable,Integer limitDay) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("adminId", adminId);
        map.put("quitable", quitable);
	    map.put("limitDay", limitDay);
        if(startTime!=null) {
	    	map.put("startTime", DateUtil.dateTimeZoreFormat.format(startTime));
	    }
	    if(endTime!=null) {
	    	map.put("endTime", DateUtil.dateTimeMaxSMFormat.format(endTime));
	    }
		return interestMapper.queryInvestIntellectualListCountTotalAmount(map);
	}

	@Override
	public List<Interest> getCapitalUndividendedByDate(Date date, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("limit", limit);
        return interestMapper.getCapitalUndividendedByDate(map);
	}

	public static void main(String[] args) {
		Double cap = 50000d;
		System.out.println(cap%100);
		System.out.println(new BigDecimal(cap%100).floatValue());
		System.out.println(BigDecimalUtil.sub(cap, new BigDecimal(cap%100).floatValue()));
	}

	@Override
	public boolean grantInterestToConfigDetails(UserInvestConfig uic,
			Interest interest) throws Exception{
		UserInvestConfigDetails detail = new UserInvestConfigDetails();
		detail.setCapitalAmount(interest.getCapitalAmount());
		detail.setCreateTime(new Date());
		detail.setInvestConfigId(uic.getId());
		detail.setInterestId(interest.getId());
		double amount = BigDecimalUtil.sub(uic.getMaxAmount(), uic.getInvestAmount());
		//统计已经插入多少金额了
		String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		Map<String, Object> params = new HashMap<>();
		params.put("userId", uic.getUserId());
		params.put("time", time);
		double tamount = userInvestConfigDetailsMapper.queryInsertAmount(params);
		double samount = BigDecimalUtil.sub(amount, tamount);
		//兼容未来租房分期产品
		float avbanne = new BigDecimal(interest.getCapitalAmount()%100).floatValue();
		double capitalAmount = BigDecimalUtil.sub(interest.getCapitalAmount(), avbanne);
		if(samount<capitalAmount){
			double s = BigDecimalUtil.sub(capitalAmount, samount);
			if(samount<0){
				detail.setAvailableBalance(BigDecimalUtil.add(capitalAmount, avbanne));
				detail.setFrozenAmount(0d);
			}else{
				detail.setAvailableBalance(BigDecimalUtil.add(s, avbanne));
				detail.setFrozenAmount(samount);
			}
		}else{
			detail.setAvailableBalance(BigDecimalUtil.add(0, avbanne));
			detail.setFrozenAmount(capitalAmount);
		}
		int i = userInvestConfigDetailsMapper.insert(detail);
		return i>0?true:false;
	}

	@Override
	public Double calTotalCapital(Integer userId) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("date", DateUtil.dateFormat.format(new Date()));
		return interestMapper.getTotalCapital(paramMap);
	}

	@Override
	public void grantDynamicInterestToUser(UserDailyInvest u) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean doOfflineHuo(Assets assets) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean grantMonthlyGainInterestToUser(Interest interest) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void insert(Interest interest) {
		this.interestMapper.insert(interest);
	}

	@Override
	public List<Map<String, Object>> getInterestListByInvestId(Integer investId, Integer userId) {

		return interestMapper.getInterestListByInvestId(investId,userId);
	}

    @Override
	public List<InterestVO> queryInterestList(String keyword, String orderNo, Date investmentStartDate, Date investmentEndDate,
                                              Integer hasDividended, Date interestStartDate, Date interestEndDate, Integer start, Integer limit,
                                              Integer adminId,Integer departmentId) {
        Map<String, Object> params = new HashMap();
        params.put("keyword", keyword);
        params.put("orderNo", orderNo);
        params.put("investmentStartDate", DateUtil.getMinInDay(investmentStartDate));
        params.put("investmentEndDate", DateUtil.getMaxInDay(investmentEndDate));
        params.put("interestStartDate", DateUtil.getMinInDay(interestStartDate));
        params.put("interestEndDate", DateUtil.getMaxInDay(interestEndDate));
        params.put("hasDividended", hasDividended);
        params.put("start", start);
        params.put("limit", limit);
        params.put("adminId", adminId);
        params.put("departmentId", departmentId);
        return interestMapper.queryInterestList(params);
    }

    @Override
    public int queryInterestCount(String keyword, String orderNo, Date investmentStartDate, Date investmentEndDate, Integer hasDividended,
                                  Date interestStartDate, Date interestEndDate,Integer adminId,Integer departmentId){
        Map<String, Object> params = new HashMap();
        params.put("keyword", keyword);
        params.put("orderNo", orderNo);
        params.put("investmentStartDate", DateUtil.getMinInDay(investmentStartDate));
        params.put("investmentEndDate", DateUtil.getMaxInDay(investmentEndDate));
        params.put("interestStartDate", DateUtil.getMinInDay(interestStartDate));
        params.put("interestEndDate", DateUtil.getMaxInDay(interestEndDate));
        params.put("hasDividended", hasDividended);
        params.put("adminId", adminId);
        params.put("departmentId", departmentId);
        return interestMapper.queryInterestCount(params);
    }

    @Override
	public InterestMapper getInterestMapper() {
		return interestMapper;
	}

}
