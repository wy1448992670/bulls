package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.constant.*;
import com.goochou.p2b.dao.*;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.vo.InvestementCountGroupLimitDay;
import com.goochou.p2b.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.annotation.Obsolete;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.ConfigHelper;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.OrderDoneEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.constant.pasture.ProjectStatusEnum;
import com.goochou.p2b.dao.ActivityMapper;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.CapitalMapper;
import com.goochou.p2b.dao.CurrentInvestmentMapper;
import com.goochou.p2b.dao.CurrentRedeemMapper;
import com.goochou.p2b.dao.EnterpriseMapper;
import com.goochou.p2b.dao.HongbaoMapper;
import com.goochou.p2b.dao.HongbaoTemplateMapper;
import com.goochou.p2b.dao.InterestMapper;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.dao.ProductMapper;
import com.goochou.p2b.dao.ProjectActivityLogMapper;
import com.goochou.p2b.dao.ProjectLinkProjectMapper;
import com.goochou.p2b.dao.ProjectMapper;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.dao.UserContractMapper;
import com.goochou.p2b.dao.UserInvestConfigMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Interest;
import com.goochou.p2b.model.InterestExample;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.InvestmentBlance;
import com.goochou.p2b.model.InvestmentExample;
import com.goochou.p2b.model.InvestmentExample.Criteria;
import com.goochou.p2b.model.OrderDone;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.vo.InvestmentOrderVO;
import com.goochou.p2b.model.vo.InvestmentSearchCondition;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.BankCardService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.InterestService;
import com.goochou.p2b.service.InvestmentBlanceService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.OrderDoneService;
import com.goochou.p2b.service.PastureOrderService;
import com.goochou.p2b.service.ProjectAccountService;
import com.goochou.p2b.service.ProjectActivitySettingService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserAccountService;
import com.goochou.p2b.service.UserInviteService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.YaoCountService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.MoneyUtil;


@Service
public class InvestmentServiceImpl implements InvestmentService {

	private static final Logger logger = Logger.getLogger(InvestmentServiceImpl.class);
    @Resource
    private InvestmentMapper investmentMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private InterestMapper interestMapper;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private AssetsService assetsService;
    @Resource
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CapitalMapper capitalMapper;
    @Resource
    private HongbaoMapper hongbaoMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserInviteService userInviteService;
    @Resource
    private ProjectLinkProjectMapper projectLinkProjectMapper;
    @Resource
    private HongbaoTemplateMapper hongbaoTemplateMapper;
    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private YaoCountService yaoCountService;
    @Resource
    private RateCouponService rateCouponService;
    @Resource
    private CurrentInvestmentMapper currentInvestmentMapper;
    @Resource
    private CurrentRedeemMapper currentRedeemMapper;
    @Resource
    private InterestService interestService;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private MessageService messageService;

    @Resource
    private EnterpriseMapper enterpriseMapper;

    @Resource
    private ProjectAccountService projectAccountService;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ProjectActivitySettingService projectActivitySettingService;

    @Resource
    private ProjectActivityLogMapper projectActivityLogMapper;

    @Resource
    private BankCardService bankCardService;

    @Resource
    private MemcachedManager memcachedManager;

    @Resource
    private HongbaoService hongbaoService;

    @Resource
    private UserContractMapper userContractMapper;
    
    @Resource
    private ProjectService projectService;

    @Resource
    private UserInvestConfigMapper userInvestConfigMapper;

    @Resource
    private InvestmentBlanceService investmentBlanceService;
    
    @Resource
    private UserAccountService userAccountService;
    
	@Resource
	private OrderDoneService orderDoneService;

	@Resource
	private InvestmentViewMapper investmentViewMapper;
	
	@Resource
	private ActivityService activityService;

    public InvestmentMapper getMapper(){
        return investmentMapper;
    }

    @Override
    public Investment get(Integer id) {
        return investmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Investment investment) {
        investmentMapper.updateByPrimaryKeySelective(investment);
    }

    @Override
    public List<Map<String, Object>> query(InvestmentSearchCondition searchCondition) {
        searchCondition.setCodes(StringUtils.trim(searchCondition.getCodes()));
        searchCondition.setKeyword(StringUtils.trim(searchCondition.getKeyword()));
        return investmentMapper.query(searchCondition);
    }

    @Override
    public Map<String, Object> queryCount(InvestmentSearchCondition searchCondition) {
        return investmentMapper.queryCount(searchCondition);
    }

    @Override
    public Double getAmountCount(Integer userId) {
        return investmentMapper.getAmountCount(userId);
    }

    @Override
    public List<Investment> findByUserId(Integer userId, int start, int limit) {
        InvestmentExample example = new InvestmentExample();
        Criteria c = example.createCriteria();
        if (userId > 0) {
            c.andUserIdEqualTo(userId);
        }
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        return investmentMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> findByUserId(int userId, Date startDate, Date endDate, Integer status, Integer order, Integer start, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("status", status);
        map.put("order", order);
        map.put("start", start);
        map.put("limit", limit);
        return investmentMapper.findByUserId(map);
    }

    @Override
    public Integer findByUserIdCount(int userId, Date startDate, Date endDate, Integer status) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("status", status);
        return investmentMapper.findByUserIdCount(map);
    }

    @Override
    public Map<String, Object> investmentDetail(Integer investmentId) {
        Project project = projectMapper.getProjectByInvestmentId(investmentId);
        Map<String, Object> map = null;
        if (project != null) {
            if (project.getParentId() == null) {
                map = investmentMapper.investmentOfProjectDetal(investmentId);
            } else {
                map = investmentMapper.investmentOfBondDetail(investmentId);
            }
        }
        return map;
    }
    
	@Override
	public int getInvestmentCountByExample(InvestmentExample example) {
		return investmentMapper.countByExample(example);
	}

	/**
	 * 获得过期投资(未完成购买的,包括未购买和购买中)
	 * 2019-05-15 张琼麒
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Investment> getExceedTimeLimitInvestment() throws Exception {
		InvestmentExample example=new InvestmentExample();
		//最早的 有效的 创建时间
		GregorianCalendar earliestValidTime=new GregorianCalendar();
		//有效期(分钟)
		String validity=memcachedManager.getCacheKeySingleValue(DictConstants.PAY_WAIT_TIME);
		System.out.println("validity2:"+validity);
		
		if(StringUtils.isBlank(validity) || !com.goochou.p2b.utils.StringUtils.checkInt(validity)) {
			validity="30";//默认30分钟
		}
		//当前时间 - 有效期=当前存在的最早的 有效的 创建时间
		earliestValidTime.add(Calendar.MINUTE, - Integer.parseInt(validity));
		//earliestValidTime 以前,且未支付的pay_status=0,且未饲养的投资订单order_status=0
		Criteria c = example.createCriteria();
		c.andOrderStatusEqualTo(InvestmentStateEnum.no_buy.getCode());
		c.andPayStatusIn(new ArrayList<Integer>() {{add(InvestPayStateEnum.NO_PAY.getCode());add(InvestPayStateEnum.PAYING.getCode());}});
		c.andCreateDateLessThan(earliestValidTime.getTime());

		return this.getMapper().selectByExample(example);
	}


	@Resource
	PastureOrderService pastureOrderService;
	/**
	 *
	 *
	 * 关闭投资
	 * 2019-05-15 张琼麒
	 * @return
	 * @throws Exception
	 */
	@Override
	public void cancelInvestment(Investment investment) throws Exception {
		pastureOrderService.cancelOrder(investment);
	}
	/**
	 *
	 *
	 * 关闭过期投资
	 * 2019-05-15 张琼麒
	 * @return
	 * @throws Exception
	 */
	/*
	@Override
	public void cancelExceedTimeLimitInvestment() throws Exception {
		//获得过期投资
		List<Investment> exceedInvestmentList=this.getExceedTimeLimitInvestment();
		for(Investment exceedInvestment:exceedInvestmentList) {
			pastureOrderService.cancelOrder(exceedInvestment);
		}
	}
	*/

	@Override
    public List<InvestmentOrderVO> queryBullsOrderList(String keyword, String orderNo, Integer temp, Integer orderStatus, Integer payStatus, Date startDate, Date endDate, Integer curPage, Integer limit, Integer userId,Integer yueLing) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("orderNo", orderNo);
        map.put("orderStatus", orderStatus);
        map.put("payStatus", payStatus);
		map.put("startDate", DateUtil.getMinInDay(startDate)); // 饲养/支付 开始日期
		map.put("endDate", DateUtil.getMinInDay(endDate)); //饲养/支付 结束日期 mapper sql中加了一天
        map.put("start",curPage);
        map.put("limit",limit);
        map.put("yueLing",yueLing);
        map.put("userId",userId);
        return  investmentMapper.selectBullsOrderList(map);
    }

    @Override
    public Integer queryBullsOrderCount(String keyword, String orderNo, Integer temp, Integer orderStatus, Integer payStatus, Date startDate, Date endDate, Integer userId,Integer yueLing) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("orderNo", orderNo);
        map.put("orderStatus", orderStatus);
        map.put("payStatus", payStatus);
        map.put("startDate", DateUtil.getMinInDay(startDate)); // 饲养/支付 开始日期
        map.put("endDate", DateUtil.getMinInDay(endDate)); //饲养/支付 结束日期 mapper sql中加了一天
        map.put("yueLing",yueLing);
        map.put("userId",userId);
        return investmentMapper.selectBullsOrderCount(map);
    }

    @Override
    public InvestmentOrderVO queryOrderDetail(Integer id) {
	    return investmentMapper.queryOrderDetail(id);
    }

	@Override
	public Investment findByOrderNo(String orderNo) {
		InvestmentExample example = new InvestmentExample();
		InvestmentExample.Criteria c = example.createCriteria();
        c.andOrderNoEqualTo(orderNo);

        List<Investment> investments = investmentMapper.selectByExample(example);
        if (investments != null && investments.size() > 0) {
            return investments.get(0);
        } else {
            return null;
        }
	}
	
	/**
     * 传入project,预生成投资账单interest并注入到project中
     * @author 张琼麒
     * @version 创建时间：2019年5月27日 下午2:07:15
     * @param project
     * @throws Exception
     */
	@Override
	public void pregeneratedInterest(Project project) throws Exception {
		//如果有加息,调用两次计算账单,一次带加息,一次不带加息,合并两次计算的结果
		if(project.getIncreaseAnnualized()!=null && project.getIncreaseAnnualized()>0) {
			//计算总利息的项目project,project.Annualized的年化利息,只包含预定了年化利息,不包含加息等活动利息
			project.setAnnualized(BigDecimal.valueOf(project.getAnnualized()).add(BigDecimal.valueOf(project.getIncreaseAnnualized())).floatValue());
			//不计算任何加息的项目projectNoIncreaseInterest
			Project projectNoII=project.clone();
			projectNoII.setAnnualized(BigDecimal.valueOf(projectNoII.getAnnualized()).subtract(BigDecimal.valueOf(projectNoII.getIncreaseAnnualized())).floatValue());
			
			pregeneratedSimpleInterest(project);
			pregeneratedSimpleInterest(projectNoII);
			//将按[总利息]和[不加息利息]计算的project,整合回project中
			for(int i=0;i<project.getInvestmentList().size();i++) {
				//计算总利息的investment
				Investment investment=project.getInvestmentList().get(i);
				//不计算任何加息的investmentNoII
				Investment investmentNoII=projectNoII.getInvestmentList().get(i);
				//投资的加息=计算总利息的投资investment的总利息-不计算任何加息的投资investmentNoII的总利息
				investment.setAddInterest(BigDecimal.valueOf(investment.getInterestAmount()).subtract(BigDecimal.valueOf(investmentNoII.getInterestAmount())).doubleValue());
				
				for(int j=0;j<investment.getInterestList().size();j++) {
					Interest interest=investment.getInterestList().get(j);
					Interest interestNoII=investmentNoII.getInterestList().get(j);
					//账单的加息=计算总利息的账单interest的总利息-不计算任何加息的账单interestNoII的总利息
					interest.setAddInterest(BigDecimal.valueOf(interest.getInterestAmount()).subtract(BigDecimal.valueOf(interestNoII.getInterestAmount())).doubleValue());
				}
				
			}
			//项目的加息=计算总利息的项目project的总利息-不计算任何加息的项目projectNoII的总利息
			project.setAddInterest(BigDecimal.valueOf(project.getInterestAmount()).subtract(BigDecimal.valueOf(projectNoII.getInterestAmount())).doubleValue());
			
			//还原项目的年息
			project.setAnnualized(BigDecimal.valueOf(project.getAnnualized()).subtract(BigDecimal.valueOf(project.getIncreaseAnnualized())).floatValue());

		}else {
			pregeneratedSimpleInterest(project);
		}
	}
	
	
	/**
	 * 传入project,预生成简单的(不计算加息等活动)投资账单interest并注入到project中
	 *  Created on 2019-05-17
	 * <p>Title:[预生账单]</p>
	 * @author:[张琼麒]
	 * @update:[日期2019-05-17] [张琼麒]
	 * @param project
	 * @return
	 * @throws Exception
	 */
	@Override
	public void pregeneratedSimpleInterest(Project project) throws Exception {
		//结算时间
		GregorianCalendar computationTime=new GregorianCalendar();
		computationTime.setTime(project.getDeadline());
		//现在
		GregorianCalendar now=new GregorianCalendar();
		//TODO 验证Project
		//borrowApplyService.preValidation(project);

		//需要计算的本金:如果有就用borrowApply.getUseAmount(签约借款金额),否则 如果有就用borrowApply.getApproveAmount(审批金额),否则 如果有就用borrowApply.getApplyAmount(申请金额)
		//BigDecimal principal=BigDecimal.valueOf(project.getTotalAmount());
		//利息计算按照公式计算出完整总金额（totalamount去整百）
		BigDecimal principal=BigDecimal.valueOf(project.getWeight()).multiply(BigDecimal.valueOf(project.getUnitZoomPrice())).add(BigDecimal.valueOf(project.getUnitFeedPrice()).multiply(BigDecimal.valueOf(project.getLimitDays()))).add(BigDecimal.valueOf(project.getUnitManagePrice()).multiply(BigDecimal.valueOf(project.getLimitDays())));

		//是否先将利息授信
		boolean firstCredit=true;

		//计算借款申请总服务费
		//服务费计算规则:1.service_fee=approve_amount*service_cost_rate,存在borrowApply.setServiceFee在账单循环中使用
		/*没有账单服务费
		if(project.getServiceCostRule().equals(1)) {
			project.setServiceFee(principal.multiply(project.getServiceCostRate()).setScale(0,BigDecimal.ROUND_HALF_EVEN));
		}
		*/
		//[计息期]类型 0日,1月
		Integer interestPeriodsType=0;
		//总[计息期]数
		Integer interestTotalPeriods=project.getLimitDays();
		//每[账单期][计息期]数
		Integer interestPeriodsPerBill=30;

		//总[账单期]数
		BigDecimal billPeriod=new BigDecimal((interestTotalPeriods/interestPeriodsPerBill)
				+(interestTotalPeriods%interestPeriodsPerBill>0?1:0));
		
		//金额取整方式
		final RoundingMode MONEY_ROUNDING_MODE=RoundingMode.DOWN;
		
		//金额取整小数位
		final int MONEY_SCALE=2;
		
		//年息
		BigDecimal annualInterestRate=BigDecimal.valueOf(project.getAnnualized());
		
		//年化[计息期数]
		BigDecimal interestPeriodsPerAnnual=new BigDecimal(360);
		
		//每[计息期数]利息率
		BigDecimal interestRatePerInterestPeriod=BigDecimal.ZERO;
		if(interestPeriodsType==0) {//[计息期]类型 0日
			//年化[计息期数]
			interestPeriodsPerAnnual=new BigDecimal(360);
			//按日息算,每[计息期数]利息率保留12位取整
			//为提高精度,不使用每[计息期数]利息率,使用[年息]/年化[计息期数]
			interestRatePerInterestPeriod=BigDecimal.valueOf(project.getAnnualized()).divide(interestPeriodsPerAnnual,12, BigDecimal.ROUND_HALF_UP);
		}

		//repayment_method:0每[账单期]还息,到期还本  1每[账单期]息本都还(等额本息/等额本金/等本等金?)
		if(project.getRepaymentMethod()==0) {
			//项目总利息=取整(本金*每[计息期]利率*总[计息期]数)
			project.setInterestAmount(principal.multiply(annualInterestRate).multiply(BigDecimal.valueOf(interestTotalPeriods)).divide(interestPeriodsPerAnnual,MONEY_SCALE,MONEY_ROUNDING_MODE).doubleValue());
		}

		for(int currentPeriod=1;currentPeriod<=billPeriod.intValue();currentPeriod++) {
			/*
			//当期[账单期]累计[账单期]数
			BigDecimal currentCountPeriodD=BigDecimal.valueOf(currentPeriod);
			//上期[账单期]累计[账单期]数
			BigDecimal priorCountPeriodD=BigDecimal.valueOf(currentPeriod-1);
			 */
			//当期[账单期]累计[计息期]数
			BigDecimal currentCountInterestPeriods=BigDecimal.valueOf(currentPeriod*interestPeriodsPerBill>interestTotalPeriods?interestTotalPeriods:currentPeriod*interestPeriodsPerBill);
			//上期[账单期]累计[计息期]数
			BigDecimal priorCountInterestPeriods=BigDecimal.valueOf((currentPeriod-1)*interestPeriodsPerBill);
			/*
			//当期[账单期][计息期]数
			BigDecimal currentInterestPeriods=BigDecimal.valueOf(currentCountInterestPeriods.intValue()-((currentPeriod-1)*interestPeriodsPerBill));
			 */

			//当期[账单期]到期日
			Date dueDate=null;
			if(interestPeriodsType==0) {//[计息期]类型 0日,1月
				Calendar cloneCalendar=(Calendar)computationTime.clone();
				cloneCalendar.add(Calendar.DATE,currentCountInterestPeriods.intValue());
				dueDate=cloneCalendar.getTime();
			}else if(interestPeriodsType==1){
				Calendar cloneCalendar=(Calendar)computationTime.clone();
				cloneCalendar.add(Calendar.MONTH,currentCountInterestPeriods.intValue());
				dueDate=cloneCalendar.getTime();
			}

			//账单总本金
			BigDecimal billPrincipal=BigDecimal.ZERO;
			//账单总利息
			BigDecimal billInterest=BigDecimal.ZERO;
			//计算借款账单
			//repayment_method:0每[账单期]还息,到期还本  1每[账单期]息本都还(等额本息/等额本金/等本等金?)
			if(project.getRepaymentMethod()==0) {
				//本金
				if(currentPeriod==billPeriod.intValue()) {
					billPrincipal=principal;
				}else {
					billPrincipal=BigDecimal.ZERO;
				}
				//当期利息=取整(本金*每[计息期]利率*当期累计[计息期]数)-取整(本金*每[计息期]利率*上期累计[计息期]数)
				billInterest=principal.multiply(annualInterestRate).multiply(currentCountInterestPeriods).divide(interestPeriodsPerAnnual,MONEY_SCALE,MONEY_ROUNDING_MODE)
						.subtract(
								principal.multiply(annualInterestRate).multiply(priorCountInterestPeriods).divide(interestPeriodsPerAnnual,MONEY_SCALE,MONEY_ROUNDING_MODE)
							);

			}

			//计算每期服务费
			//服务费计算规则:1.service_fee=approve_amount*service_cost_rate,已存,取borrowApply.getServiceFee
			/*不计算服务费
			if(project.getServiceCostRule().equals(1)) {
				//服务费支付方式:1.随账单分期支付(总服务费平摊到每期账单)
				if(project.getServiceCostRule().equals(1)) {
					//当期服务费=取整(总服务费*当期累计期数/总期数)-取整(总服务费*上期累计期数/总期数)
					interest.setDueServiceFee(
						project.getServiceFee().multiply(currentCountPeriodD).divide(billPeriod,0,BigDecimal.ROUND_HALF_EVEN)
						.subtract(
								project.getServiceFee().multiply(priorCountPeriodD).divide(billPeriod,0,BigDecimal.ROUND_HALF_EVEN)
						)
					);
				}
			}*/

			//累计投资本金,用于分配利息
			BigDecimal countInvestmentCapital=BigDecimal.ZERO;
			if(project.getInvestmentList()==null) {
				throw new Exception("生成账单时,没有投资列表");
			}
			//投资
			for(Investment investment:project.getInvestmentList()) {
				//新建一个默认状态的账单
				Interest interest=new Interest();
				interest.setType(1);//类型：1定期
				interest.setUserId(investment.getUserId());
				//借款申请
				interest.setInvestmentId(investment.getId());
				if(firstCredit) {//是否先将利息授信
					interest.setHasDividended(2);//0：未派息，1：已派息 2：已计算（未实际发放）
				}else {
					interest.setHasDividended(0);//0：未派息，1：已派息 2：已计算（未实际发放）
				}
				interest.setAddInterest(0d);//加息利息
				interest.setCreateDate(now.getTime());
				//当期期数
				interest.setStage(currentPeriod);

				//到期日
				interest.setDate(dueDate);

				//repayment_method:0每[账单期]还息,到期还本  1每[账单期]息本都还(等额本息/等额本金/等本等金?)
				if(project.getRepaymentMethod()==0){
					//本金
					if(currentPeriod==billPeriod.intValue()) {
						interest.setCapitalAmount(investment.getAmount());
					}else {
						interest.setCapitalAmount(0d);
					}
				}
				//计算第一[账单期]时初始化投资总利息,利息列表
				if(currentPeriod==1) {
					investment.setInterestAmount(0d);
					investment.setInterestList(new ArrayList<Interest>());
				}
				if(currentPeriod!=billPeriod.intValue()) {
					//当期投资账单利息=取整(当期借款账单利息*(之前累计计算投资额+本账单投资额)/总借款额)-取整(当期借款账单利息*累计计算投资额/总借款额)
					/*interest.setInterestAmount();*/
					BigDecimal investmentBillInterest=
							billInterest.multiply(
									countInvestmentCapital.add(BigDecimal.valueOf(investment.getAmount()))
							).divide(
									BigDecimal.valueOf(project.getTotalAmount()), MONEY_SCALE,MONEY_ROUNDING_MODE
							)
							.subtract(
									billInterest.multiply(
											countInvestmentCapital
									).divide(
											BigDecimal.valueOf(project.getTotalAmount()), MONEY_SCALE,MONEY_ROUNDING_MODE
									)
							);
					interest.setInterestAmount(investmentBillInterest.doubleValue());
					//累积投资计息
					investment.setInterestAmount(investmentBillInterest.add(BigDecimal.valueOf(investment.getInterestAmount())).doubleValue());
				}else {
					//这笔投资总利息=取整(项目总利息*(之前累计计算投资额+本账单投资额)/总借款额)-取整(项目总利息*(之前累计计算投资额)/总借款额)
					BigDecimal investmentTotalInterest=
							BigDecimal.valueOf(project.getInterestAmount()).multiply(
									countInvestmentCapital.add(BigDecimal.valueOf(investment.getAmount()))
							).divide(
									BigDecimal.valueOf(project.getTotalAmount()), MONEY_SCALE,MONEY_ROUNDING_MODE
							)
							.subtract(
									BigDecimal.valueOf(project.getInterestAmount()).multiply(
											countInvestmentCapital
									).divide(
											BigDecimal.valueOf(project.getTotalAmount()), MONEY_SCALE,MONEY_ROUNDING_MODE
									)
							);
					//最后一期投资账单利息=投资总利息-之前投资账单已计算利息
					interest.setInterestAmount(investmentTotalInterest.subtract(BigDecimal.valueOf(investment.getInterestAmount())).doubleValue());
					investment.setInterestAmount(investmentTotalInterest.doubleValue());
				}
				investment.getInterestList().add(interest);
				countInvestmentCapital=countInvestmentCapital.add(BigDecimal.valueOf(investment.getAmount()));
			}
		}
	}

	/**
	 * 传入investment,回购该投资
	 *  Created on 2019-05-17
	 * <p>Title:[预生账单]</p>
	 * @author:[张琼麒]
	 * @update:[日期2019-05-17] [张琼麒]
	 * @param investment
	 * @throws Exception
	 */
	@Override
	public void doInvestmentBuyBack(Investment investment) throws Exception{
		//锁项目order.getProjectId()
		Project project=projectService.getMapper().selectByPrimaryKeyForUpdate(investment.getProjectId());
		//锁投资order.getId()
		investment=this.getMapper().selectByPrimaryKeyForUpdate(investment.getId());
		//锁用户order.getUserId()
		Assets userAccount = assetsService.getMapper().selectByPrimaryKeyForUpdate(investment.getUserId());
		//当前时间
		Calendar now=Calendar.getInstance();
		//验证投资为可回购状态
		if (project.getStatus() != ProjectStatusEnum.SALED.getCode()) {
			throw new Exception("饲养期未结束不能出售");
		}
		if (investment.getOrderStatus() != InvestmentStateEnum.buyed.getCode()) {
			throw new Exception("饲养期未结束不能出售");
		}
		if (investment.getPayStatus() != InvestPayStateEnum.PAYED.getCode()) {
			throw new Exception("饲养期未结束不能出售");
		}
		//最后结算时间
		Calendar dueCalendar=Calendar.getInstance();
		//起息时间
		dueCalendar.setTime(project.getDeadline());
		//[计息期]类型 0日,1月
		Integer interestPeriodsType=0;
		//总[计息期]数
		Integer interestTotalPeriods=project.getLimitDays();
		if(interestPeriodsType==0) {//[计息期]类型 0日,1月
			dueCalendar.add(Calendar.DATE,interestTotalPeriods.intValue());
		}else if(interestPeriodsType==1){
			dueCalendar.add(Calendar.MONTH,interestTotalPeriods.intValue());
		}
		if(dueCalendar.after(now)) {
			throw new Exception("饲养期结束方可出售");
		}
		
		InvestmentBlance investmentBlance=investmentBlanceService.selectByInvestmentId(investment.getId());
		investmentBlance=investmentBlanceService.selectByPrimaryKeyForUpdate(investmentBlance.getId());
		//兑付冻结利息
		if(investmentBlance.getState()==0) {//0未兑付,1已兑付
			//投资冻结的利息未使用部分,赎回时增加到用户余额
			BigDecimal surplus=investmentBlance.getAmount().subtract(investmentBlance.getUseAmount());
			if(surplus.compareTo(BigDecimal.ZERO)>0) {
				//userAccount.CreditAmount<investmentBlance.surplus
				//用户冻结利息(授信额度)不足,无法加回到余额
				if(BigDecimal.valueOf(userAccount.getCreditAmount()).compareTo(surplus)<0) {
					throw new Exception("授信额度计算错误");
				}
				//利息 减CREDIT
				userAccountService.modifyAccount(userAccount, surplus, investment.getId(), BusinessTableEnum.investment, AccountOperateEnum.INVEST_BUYBACK_INTEREST_CREDIT_SUBTRACT);
				//利息 加BALANCE
				userAccountService.modifyAccount(userAccount, surplus, investment.getId(), BusinessTableEnum.investment, AccountOperateEnum.INVEST_BUYBACK_INTEREST_BALANCE_ADD);
				
			}
			investmentBlance.setUpdateDate(now.getTime());
			investmentBlance.setState(1);
			investmentBlanceService.getMapper().updateByPrimaryKey(investmentBlance);
		}
		//兑付投资本金 加BALANCE
		userAccountService.modifyAccount(userAccount, BigDecimal.valueOf(investment.getAmount()), investment.getId(), BusinessTableEnum.investment, AccountOperateEnum.INVEST_BUYBACK_PRINCIPAL_BALANCE_ADD);
		int affectedRows = assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount);
		if (affectedRows == 0) {
			throw new LockFailureException();
		}
		
		//0：未饲养，1：饲养期，2：已卖牛 3 已取消
		investment.setOrderStatus(InvestmentStateEnum.saled.getCode());
		investment.setUpdateDate(now.getTime());
		affectedRows=this.getMapper().updateByPrimaryKeySelectiveAndVersion(investment);
		if (affectedRows == 0) {
			throw new LockFailureException();
		}
		
		//完结账单interest
		Interest updateSetInterest=new Interest();
		updateSetInterest.setHasDividended(1);//0：未派息，1：已派息 2：已计算（未实际发放）
		updateSetInterest.setUpdateDate(now.getTime());
		InterestExample updateWhereExample=new InterestExample();
		updateWhereExample.createCriteria()
		.andInvestmentIdEqualTo(investment.getId())
		.andHasDividendedEqualTo(2);//0：未派息，1：已派息 2：已计算（未实际发放）
		interestService.getInterestMapper().updateByExampleSelective(updateSetInterest, updateWhereExample);

		//增加节点
  		OrderDone orderDone = new OrderDone();
  		orderDone.setOrderNo(investment.getOrderNo());
  		orderDone.setOrderStatus(OrderDoneEnum.SUCCESS.getFeatureName());
  		orderDone.setUpdateDate(new Date());
  		orderDone.setCreateDate(new Date());
  		orderDone.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
  		if (orderDoneService.insert(orderDone) != 1) {
  			throw new LockFailureException();
  		}
  		
		//根据project的investment是否"已卖牛",决定是否修改project的状态到"已回购"
		projectService.doBuyBack(project);
		
		// 卖牛赠送红包
		if(investment.getType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
			activityService.doActivityBuyBack(investment);
		}
		
	}
	
	
	/**
	 * 获得到期,可自动回购的投资
	 * 2019-05-15 张琼麒
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Investment> getDueInvestmentCouldAutoBuyBack() throws Exception {
		InvestmentExample example=new InvestmentExample();
		//查询到期时间是1天前的未回购的投资
		GregorianCalendar greCalendar=new GregorianCalendar();
		greCalendar.add(Calendar.DATE, -1);
		List<Investment> result=this.getMapper().getDueInvestmentCouldBuyBack(greCalendar.getTime());
		
		//拼牛到期即回购
		result.addAll(this.getMapper().getDueInvestmentCouldBuyBackPin(new Date()));
		
		return result;
	}
	
	/**
	 * 获得今日到期的投资订单
	 * 2019-06-19 张琼麒
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Investment> getInvestmentDueInToday() throws Exception {

		return this.getMapper().getInvestmentDueInToday();
	}
	
	@Override
	public List<Map<String, Object>> listPrepaidBill(Integer userId, Integer limitStart, Integer limitEnd) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    
	    map.put("userId", userId);
	    map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);
	    
        return this.getMapper().listPrepaidBill(map);
	}
	
	@Override
	public Integer countPrepaidBill(Integer userId) {
	    Map<String, Object> map = new HashMap<String, Object>();

        map.put("userId", userId);

        return this.getMapper().countPrepaidBill(map);
	}

	/**
     * 支付结果修改订单支付中或未支付状态 ye
     * @param investment
     * @param needStatus
     * @return
     */
    @Override
    public int update(Investment investment, int orderStatus,int payStatus) {
        InvestmentExample example = new InvestmentExample();
        InvestmentExample.Criteria c = example.createCriteria();
        c.andOrderStatusEqualTo(orderStatus);
        c.andPayStatusEqualTo(payStatus).andIdEqualTo(investment.getId());
        return investmentMapper.updateByExampleSelective(investment, example);
    }
    
    /**
     * 根据用户ID查询数量 ye
     * @param userId
     * @return
     */
    @Override
    public Integer queryCountByUserId(Integer userId) {
        InvestmentExample example = new InvestmentExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        return investmentMapper.countByExample(example);
    }
    
    @Override
    public List<Investment> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();
        InvestmentExample example = new InvestmentExample();
        InvestmentExample.Criteria c = example.createCriteria();
        c.andIdIn(ids);
        return  investmentMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> getInvestAmountByMonthDay(Integer adminId, Integer departmentId) {
        return investmentMapper.getInvestAmountByMonthDay(adminId, departmentId);
    }

    @Override
    public Map<String, Object> getProjectOrderInfo(Integer investId) {
        // 拿到项目订单基础信息
        Map<String, Object> map = investmentMapper.getProjectOrderInfo(investId);
        map.put("all_amount", MoneyUtil.insertComma(map.get("all_amount").toString()));
        map.put("interest_amount", "-"+MoneyUtil.insertComma(map.get("interest_amount").toString()));
        map.put("settle", MoneyUtil.insertComma(map.get("settle").toString()));
        Integer pId = Integer.valueOf(map.get("p_id")+"");
        // 项目产品属性及值
        List<Map<String, Object>> proList = projectMapper.listProjectProductPropertyInfoById(pId);
        String littleImagePath = projectService.getProjectsmallImagePath("1", pId, true);
        map.put("property", proList);
        map.put("littleImagePath", littleImagePath);
        return map;
    }
    
	/**
	 * 投资到期
	 * 2019-06-19 张琼麒
	 * @return
	 * @throws Exception
	 */
	@Override
	public void doDue(Investment investment) throws Exception {
		Project project=projectService.getMapper().selectByPrimaryKey(investment.getProjectId());
		Calendar dueTime=Calendar.getInstance();
  		dueTime.setTime(project.getDeadline());
  		dueTime.add(Calendar.DATE,project.getLimitDays());
		
  		Calendar today = Calendar.getInstance();
  		today.setTime(new Date());
  		today.set(Calendar.HOUR_OF_DAY, 0);
  		today.set(Calendar.MINUTE, 0);
  		today.set(Calendar.SECOND, 0);
  		today.set(Calendar.MILLISECOND, 0);
  		Calendar tomorrow = (Calendar) today.clone();
  		tomorrow.add(Calendar.DATE,1);
  		
		if(dueTime.before(today) || !dueTime.before(tomorrow) ) {
			throw new Exception("投资到期只能执行当天到期的投资");
		}

		//增加节点
  		OrderDone orderDone = new OrderDone();
  		orderDone.setOrderNo(investment.getOrderNo());
  		orderDone.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
  		orderDone.setOrderStatus(OrderDoneEnum.DUE.getFeatureName());
  		orderDone.setCreateDate(dueTime.getTime());
  		orderDone.setUpdateDate(dueTime.getTime());
  		
  		if (orderDoneService.insert(orderDone) != 1) {
  			throw new LockFailureException();
  		}

	}

	@Override
    public Double getAmountAll(Integer adminId,Integer departmentId) {
		return investmentMapper.getAmountAll(adminId,departmentId);
    }


	@Override
	public List<InvestmentOrderVO> listInvestment(String keyword, String orderNo, List<Integer> orderStatusArr, Integer payStatus, Date startDate, Date endDate,
			Date startDueTime, Date endDueTime,Date startBuyBackTime, Date endBuyBackTime,
			Integer curPage, Integer limit, Integer userId,Integer yueLing, Integer adminId,Integer departmentId) {
		Map<String, Object> map = new HashMap<>();
		InvestmentViewExample example = new InvestmentViewExample();
		example.setOrderByClause(" id desc ");
		if (curPage != null) {
			example.setLimitStart(curPage);
		}
		if (limit != null) {
			example.setLimitEnd(limit);
		}
		InvestmentViewExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(orderNo)) {
			criteria.andOrderNoLike("%"+orderNo+"%");
		}
		if (orderStatusArr != null && !orderStatusArr.isEmpty()) {
			criteria.andOrderStatusIn(orderStatusArr);
		}
		if (payStatus != null) {
			criteria.andPayStatusEqualTo(payStatus);
		}
		if (startDate != null) {
			criteria.andPayTimeGreaterThanOrEqualTo(startDate);
		}
		if (endDate != null) {
			criteria.andPayTimeLessThan(endDate);
		}
		if (startDueTime != null) {
			criteria.andDueTimeGreaterThanOrEqualTo(startDueTime);
		}
		if (endDueTime != null) {
			criteria.andDueTimeLessThan(endDueTime);
		}
		if (startBuyBackTime != null) {
			criteria.andBuyBackTimeGreaterThanOrEqualTo(startBuyBackTime);
		}
		if (endBuyBackTime != null) {
			criteria.andBuyBackTimeLessThan(endBuyBackTime);
		}
		map.put("yueLing",yueLing);
		map.put("userId",userId);
		map.put("keyword", keyword);
		map.put("adminId", adminId);
		map.put("departmentId", departmentId);
		return investmentViewMapper.listInvestmentVoByExampleAndCondition(map, example);
	}

	@Override
	public long countInvestment(String keyword, String orderNo, List<Integer> orderStatusArr, Integer payStatus, Date startDate, Date endDate,
			Date startDueTime, Date endDueTime,Date startBuyBackTime, Date endBuyBackTime,Integer userId,Integer yueLing, Integer adminId,Integer departmentId) {
		Map<String, Object> map = new HashMap<>();
		InvestmentViewExample example = new InvestmentViewExample();
		InvestmentViewExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(orderNo)) {
			criteria.andOrderNoLike("%"+orderNo+"%");
		}
		if (orderStatusArr != null && !orderStatusArr.isEmpty()) {
			criteria.andOrderStatusIn(orderStatusArr);
		}
		if (payStatus != null) {
			criteria.andPayStatusEqualTo(payStatus);
		}
		if (startDate != null) {
			criteria.andPayTimeGreaterThanOrEqualTo(startDate);
		}
		if (endDate != null) {
			criteria.andPayTimeLessThan(endDate);
		}
		if (startDueTime != null) {
			criteria.andDueTimeGreaterThanOrEqualTo(startDueTime);
		}
		if (endDueTime != null) {
			criteria.andDueTimeLessThan(endDueTime);
		}
		if (startBuyBackTime != null) {
			criteria.andBuyBackTimeGreaterThanOrEqualTo(startBuyBackTime);
		}
		if (endBuyBackTime != null) {
			criteria.andBuyBackTimeLessThan(endBuyBackTime);
		}
		map.put("yueLing",yueLing);
		map.put("userId",userId);
		map.put("keyword", keyword);
		map.put("adminId", adminId);
		map.put("departmentId", departmentId);
		return investmentViewMapper.countInvestmentVoByExampleAndCondition(map, example);
	}

	@Override
	public Map sumInvestment(Date startDate, Date endDate, List<Integer> orderStatusArr) {
		Map<String, Object> map = new HashMap<>();
		InvestmentViewExample example = new InvestmentViewExample();
		InvestmentViewExample.Criteria criteria = example.createCriteria();
		if (startDate != null) {
			criteria.andPayTimeGreaterThanOrEqualTo(startDate);
		}
		if (endDate != null) {
			criteria.andPayTimeLessThan(endDate);
		}
		// 默认查询 物权交易成功
		if (orderStatusArr == null) {
			orderStatusArr = new ArrayList<>();
			orderStatusArr.add(1); // 饲养期
			orderStatusArr.add(2); // 已买牛
		}
		if (!orderStatusArr.isEmpty()) {
			criteria.andOrderStatusIn(orderStatusArr);
		}
		return investmentViewMapper.sumInvestmentVoByExampleAndCondition(map, example);
	}
	
	@Override
	public List<InvestmentView> listInvestmentViewByProject(Integer userId,Integer projectId, List<Integer> orderStatusArr) {
		InvestmentViewExample example = new InvestmentViewExample();
		example.setOrderByClause(" id ");
		InvestmentViewExample.Criteria criteria = example.createCriteria();
		
		if (userId != null) {
			criteria.andUserIdEqualTo(userId);
		}
		if (projectId != null) {
			criteria.andProjectIdEqualTo(projectId);
		}
		if (orderStatusArr != null && !orderStatusArr.isEmpty()) {
			criteria.andOrderStatusIn(orderStatusArr);
		}
		return investmentViewMapper.selectByExample(example);
	}
	
	@Override
	public List<InvestmentView> listInvestmentViewByProjectUnuserId(Integer unuserId,Integer projectId, List<Integer> orderStatusArr) {
		InvestmentViewExample example = new InvestmentViewExample();
		example.setOrderByClause(" id ");
		InvestmentViewExample.Criteria criteria = example.createCriteria();
		if (unuserId != null) {
			criteria.andUserIdNotEqualTo(unuserId);
		}
		if (projectId != null) {
			criteria.andProjectIdEqualTo(projectId);
		}
		if (orderStatusArr != null && !orderStatusArr.isEmpty()) {
			criteria.andOrderStatusIn(orderStatusArr);
		}
		return investmentViewMapper.selectByExample(example);
	}
}
