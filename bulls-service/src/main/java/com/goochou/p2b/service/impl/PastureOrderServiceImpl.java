package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.goochou.p2b.model.*;
import com.goochou.p2b.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.OrderDoneEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.ProjectTypeEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.constant.pasture.ProjectStatusEnum;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderListRequest;
import com.goochou.p2b.model.vo.InvestmentDetailsVO;
import com.goochou.p2b.model.vo.InvestmentOrderListVO;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.BigDecimalUtil;

/**
 * 牲畜领养订单
 *
 * @Auther: huangsj
 * @Date: 2019/5/13 16:22
 * @Description:
 */
@Service
public class PastureOrderServiceImpl implements PastureOrderService {
	private final static Logger logger = Logger.getLogger(PastureOrderServiceImpl.class);
	
    @Autowired
    private HongbaoService hongbaoService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private TradeRecordService tradeRecordService;
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private InvestmentBlanceService investmentBlanceService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private OrderDoneService orderDoneService;
    @Autowired
    private UserService userService;
    @Autowired
    private InvestmentMapper investmentMapper;
    @Autowired
    private ActivityService activityService;
    @Resource
    private MemcachedManager memcachedManager;
    @Resource
    private ActivityBlessingChanceRecordService activityBlessingChanceRecordService;
    
    @Override
    public Investment findOrderByNum(String orderNum) {
        InvestmentExample example = new InvestmentExample();
        InvestmentExample.Criteria c = example.createCriteria();
        c.andOrderNoEqualTo(orderNum);
        List<Investment> orders = investmentService.getMapper().selectByExample(example);
        if (orders != null && orders.size() > 0) {
            return orders.get(0);
        } else {
            return null;
        }
    }
    
    final static String SALE_STATUS_PROJECT_MINI_COUNT="SALE_STATUS_PROJECT_MINI_COUNT";
    
	@Override
    public boolean doPaySuccess(Investment order, Assets userAccount) throws Exception {
    	boolean flag = true;
    	if (order.getPayStatus() != InvestPayStateEnum.PAYING.getCode()) {
    		throw new Exception("订单状态错误");
        }
    	//0：未饲养，1：饲养期，2：已卖牛 3 已取消, 4 待成交
		if(order.getType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
			if(order.getOrderStatus() != InvestmentStateEnum.no_buy.getCode()) {
				throw new Exception("订单状态错误");
			}
		}else if(order.getType().equals(ProjectTypeEnum.PINNIU.getFeatureType())){
			if(order.getOrderStatus() != InvestmentStateEnum.no_buy.getCode()) {
				throw new Exception("订单状态错误");
			}
		}else {
			throw new Exception("订单状态错误");
		}
    	
    	//现金支付
    	if(order.getRemainAmount().compareTo(BigDecimal.ZERO) > 0) {
    		if(userAccountService.modifyAccount(userAccount, order.getRemainAmount(), order.getId(),
                    BusinessTableEnum.investment, AccountOperateEnum.INVEST_CASH_SUBTRACT) == 0) {
            	flag = false;
            }
    		//现金支付可能锁定余额,需要扣除
    		if (order.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
    			//BLANCE,FROZEN_SUBTRACT
            	if(userAccountService.modifyAccount(userAccount, order.getBalancePayMoney(), order.getId(),
                        BusinessTableEnum.investment, AccountOperateEnum.INVEST_BALANCE_FROZEN_SUBTRACT) == 0) {
                	flag = false;
                }
            	//更新用户资金
                if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount) == 0) {
                	throw new LockFailureException();
                }
    		}
    	}
        
		//订单支付完成
		order.setPayStatus(InvestPayStateEnum.PAYED.getCode());
		if(order.getType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
			order.setOrderStatus(InvestmentStateEnum.waiting.getCode());
		}
		//增加节点
  		OrderDone orderDone = new OrderDone();
  		orderDone.setOrderNo(order.getOrderNo());
  		orderDone.setOrderStatus(OrderDoneEnum.PAY.getFeatureName());
  		orderDone.setUpdateDate(new Date());
  		orderDone.setCreateDate(new Date());
  		orderDone.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
  		if (orderDoneService.insert(orderDone) != 1) {
  			flag = false;
  		}
  		
		//更新订单
		if(investmentService.getMapper().updateByPrimaryKeySelectiveAndVersion(order) == 0) {
        	throw new LockFailureException();
        }
		
        //修改标的物售出状态
        Project project = projectService.getMapper().selectByPrimaryKey(order.getProjectId());
        
        //增加收益明细并获取授信金额
  		this.tryCompleteProject(project);
	  	//projectService.doGeneratedInterestForOneInvestment(project, order);
  		
        //todo: 生成合同
        if(!flag) {
        	throw new LockFailureException();
        }
        return flag;
    }
	
	/**
	 * 生成账单 根据project,查询对应的investmentList, 生成interest账单并插入数据库,
	 * 汇总investment和project的利息,将投资的状态从no_buy改为buyed,并保存 将结果封装回investment和project
	 *
	 * @author:[张琼麒]
	 * @update:[日期2019-05-17] [张琼麒]
	 * @param project
	 * @return
	 * @throws Exception
	 */
	private void tryCompleteProject(Project project) throws Exception {
		if(!project.getStatus().equals(ProjectStatusEnum.PAYING.getCode())) {
			throw new Exception("项目数据异常");
		}
		if(project.getInvestedAmount().compareTo(project.getTotalAmount())<0) {
    		return;
    	}else if(project.getInvestedAmount().compareTo(project.getTotalAmount())>0) {
    		throw new Exception("超卖了!");
    	}
    	Date now=new Date();
    	InvestmentExample example = new InvestmentExample();
		InvestmentExample.Criteria c = example.createCriteria();
		//0：未饲养，1：饲养期，2：已卖牛 3 已取消, 4 待成交
		c.andProjectIdEqualTo(project.getId()).andOrderStatusNotEqualTo(InvestmentStateEnum.caceled.getCode());
		List<Investment> investmentList = investmentService.getMapper().selectByExample(example);
		
		BigDecimal countAmount=BigDecimal.ZERO;
		for(Investment investment:investmentList) {
			if(investment.getPayStatus() != InvestPayStateEnum.PAYED.getCode()) {
				return;
			}
			//0：未饲养，1：饲养期，2：已卖牛 3 已取消, 4 待成交
			if(investment.getType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
				if(investment.getOrderStatus() != InvestmentStateEnum.no_buy.getCode()) {
					return;
				}
			}else if(investment.getType().equals(ProjectTypeEnum.PINNIU.getFeatureType())){
				if(investment.getOrderStatus() != InvestmentStateEnum.waiting.getCode()) {
					return;
				}
			}
			countAmount=countAmount.add(BigDecimal.valueOf(investment.getAmount()));
		}
		if(countAmount.compareTo(BigDecimal.valueOf(project.getTotalAmount()))!=0) {
			throw new Exception("成交数据异常");
		}
		project.setInvestmentList(investmentList);

		//计算账单,保存账单
		/*创建账单
		根据project和单个investment,
		生成interest账单并插入数据库,
		修改investment和project的利息及创建订单后状态但不保存
		将结果封装回investment和project,由外部保存
		警告:本方法不更新project和investment,为了数据统一,请立即更新*/
		projectService.doGeneratedInterest(project);
		
		//项目完成出售
        project.setStatus(ProjectStatusEnum.SALED.getCode());
        project.setTradeTime(now);
        projectService.updateByPrimaryKeySelectiveForVersion(project);
        
		for (Investment investment : project.getInvestmentList()) {
			investment.setOrderStatus(InvestmentStateEnum.buyed.getCode());// 0：未饲养，1：饲养期，2：已卖牛 3 已取消
			investmentService.getMapper().updateByPrimaryKey(investment);

			//更新订单
			if(investmentService.getMapper().updateByPrimaryKeySelectiveAndVersion(investment) == 0) {
	        	throw new LockFailureException();
	        }
			//获取授信金额并添加
	        if(investmentBlanceService.add(investment.getId(), investment.getUserId(), BigDecimal.valueOf(investment.getInterestAmount())) == 0) {
	        	throw new LockFailureException();
	        }
	        // 获取用户账户
			Assets userAccount = assetsService.findByuserId(investment.getUserId());
	        //增加用户授信资金
	        if(userAccountService.modifyAccount(userAccount, BigDecimal.valueOf(investment.getInterestAmount()), investment.getId(),
	                BusinessTableEnum.investment, AccountOperateEnum.INVEST_CREDIT_ADD) == 0) {
	        	throw new LockFailureException();
	        }
	        //更新用户资金
            if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount) == 0) {
            	throw new LockFailureException();
            }
            
            //增加节点
	  		OrderDone orderDone = new OrderDone();
	  		orderDone.setOrderNo(investment.getOrderNo());
	  		orderDone.setOrderStatus(OrderDoneEnum.TRADE_COMPLETE.getFeatureName());
	  		orderDone.setUpdateDate(now);
	  		orderDone.setCreateDate(now);
	  		orderDone.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
	  		if (orderDoneService.insert(orderDone) != 1) {
	  			throw new LockFailureException();
	  		}
	  		
			if(investment.getType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
				//用户会员等级提升
		  		User user = userService.get(userAccount.getUserId());
		  		
				// 2020年新春集五牛活动 =============> begin
        		try {
            		logger.info("2020年新春集五牛活动 begin =====================>");
            		activityBlessingChanceRecordService.doSendOutChance(user, 2, investment.getId());
        		} catch (Exception e) {
            		logger.error("2020年新春集五牛活动异常:" + e.getMessage(), e);
        		} finally {
            		logger.info("2020年新春集五牛活动 end <=====================");
        		}
        		// 2020年新春集五牛活动 <============= end
				
		  		if(user.getLevel() == 0) {
		  			user.setLevel(1);
		  			if(userService.update(user) == 0) {
		  				throw new LockFailureException();
		  			}
		  		}
		  		//执行投资活动
		  		activityService.doActivityInvestment(investment);
		  		
			}
		}
		
		String saleStatusProjectMiniCountStr=memcachedManager.getCacheKeySingleValue(SALE_STATUS_PROJECT_MINI_COUNT);
  		Integer saleStatusProjectMiniCount=5;
  		if(StringUtils.isNumeric(saleStatusProjectMiniCountStr)) {
  			saleStatusProjectMiniCount=Integer.parseInt(saleStatusProjectMiniCountStr);
  		}
  		//自动上架同期限标的
  		try {
            projectService.doEnableSaleByLimitDays(project.getLimitDays(),project.getProjectType(), saleStatusProjectMiniCount);
		} catch (Exception e) {
			logger.error("自动上架 doEnableSaleByLimitDays():"+e.getMessage(),e);
		}
	}
	
    @Override
    public boolean cancelOrder(Investment order) throws Exception {
    	boolean flag = true;
        //查看此订单有没有支付订单，如果有查找第三方支付接口，看此订单的支付情况，确认失败或不存在则再走下面的业务

        //锁项目order.getProjectId()
        Project project = projectService.getMapper().selectByPrimaryKeyForUpdate(order.getProjectId());
        //锁投资order.getId()??
        order = investmentService.getMapper().selectByPrimaryKeyForUpdate(order.getId());
        //锁用户order.getUserId()
        Assets userAccount = assetsService.getMapper().selectByPrimaryKeyForUpdate(order.getUserId());
        if(project.getStatus()!=ProjectStatusEnum.PAYING.getCode()) {
        	throw new Exception("订单此时的状态不支持取消");
        }
        //order.getPayStatus() != InvestPayStateEnum.PAYING.getCode()调用结构查询,如果有结果,修改order.getPayStatus()
        // if (order.getPayStatus() != InvestPayStateEnum.NO_PAY.getCode() && order.getPayStatus() != InvestPayStateEnum.PAYING.getCode()) {
        if (order.getPayStatus() != InvestPayStateEnum.NO_PAY.getCode()) {
            throw new Exception("订单此时的状态不支持取消");
        }
        if (order.getOrderStatus() != InvestmentStateEnum.no_buy.getCode()) {
            throw new Exception("订单此时的状态不支持取消");
        }
        //充值状态:0成功，1处理中，2失败
        //查看此订单有没有支付订单，如果有查找第三方支付接口，看此订单的支付情况，确认失败或不存在则再走下面的业务
        int rechargeStatus = rechargeService.getRechargeStatusByOrderTypeAndNo(OrderTypeEnum.INVESTMENT.getFeatureName(), order.getOrderNo());
        if (rechargeStatus == 0) {
            throw new Exception("充值已成功,不能取消");
        } else if (rechargeStatus == 1) {
            throw new Exception("充值中,不能取消");
        }
        //解冻已使用的余额
        if (order.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
            if(userAccountService.modifyAccount(userAccount, order.getBalancePayMoney(), order.getId(),
                    BusinessTableEnum.investment, AccountOperateEnum.INVEST_CLOSE_BALANCE_UNFREEZE) == 0) {
            	flag = false;
            }
        }

        if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount) == 0) {
        	flag = false;
        }

        //返还已使用的红包
        if (order.getHongbaoMoney().compareTo(BigDecimal.ZERO) > 0) {
            Hongbao hongbao = hongbaoService.getMapper().selectByPrimaryKey(order.getHongbaoId());
            hongbao.setUseTime(null);
            if(hongbaoService.getMapper().updateByPrimaryKey(hongbao) == 0) {
            	flag = false;
            }
        }
        project.setInvestorsNum(project.getInvestorsNum()-1);
        project.setInvestedAmount(BigDecimal.valueOf(project.getInvestedAmount()).subtract(BigDecimal.valueOf(order.getAmount())).doubleValue());
        if(order.getType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
        	//续购标的不需要上架
            if(project.getBuyAgain()){
                project.setStatus(ProjectStatusEnum.BUILDED.getCode());
            }else {
                project.setStatus(ProjectStatusEnum.ENABLE_SALE.getCode());
                project.setUserId(null);
            }
        }else if(order.getType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
        	//续购标的不需要上架
            if(project.getInvestorsNum()<0 || project.getInvestedAmount()<0){
            	throw new Exception("投资数据有误");
            }else if(project.getInvestorsNum()==0 && project.getInvestedAmount()>0){
            	throw new Exception("投资数据有误");
            }else if(project.getInvestorsNum()>0 && project.getInvestedAmount()==0){
            	throw new Exception("投资数据有误");
            }
            if(project.getInvestorsNum()==0) {
            	project.setStatus(ProjectStatusEnum.ENABLE_SALE.getCode());
            	project.setUserId(null);
            }
        }else {
        	throw new Exception("订单类型有误");
        }
        
        projectService.updateByPrimaryKeySelectiveForVersion(project);
        //修改订单状态
        order.setOrderStatus(InvestmentStateEnum.caceled.getCode());
        order.setUpdateDate(new Date());
        if(investmentService.getMapper().updateByPrimaryKeySelectiveAndVersion(order) == 0) {
        	flag = false;
        }
        //增加节点
  		OrderDone orderDone = new OrderDone();
  		orderDone.setOrderNo(order.getOrderNo());
  		orderDone.setOrderStatus(OrderDoneEnum.CANNEL.getFeatureName());
  		orderDone.setUpdateDate(new Date());
  		orderDone.setCreateDate(new Date());
  		orderDone.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
  		if (orderDoneService.insert(orderDone) != 1) {
  			flag = false;
  		}
        if(!flag) {
        	throw new LockFailureException();
        }
        return flag;
    }
    
    @Deprecated
    @Override
    public boolean addOrder(Investment investment,Project project, Hongbao hongbao) throws Exception {
        boolean flag = true;

        if (hongbao != null) {
            //修改红包的使用情况
            hongbao.setUseTime(new Date());
            if(hongbaoService.getMapper().update(hongbao) == 0) {
                flag = false;
            }
        }

        //设置标的投资情况
        project.setUpdateDate(new Date());
        project.setUserId(investment.getUserId());
        project.setInvestorsNum(1);
        project.setInvestedAmount(project.getTotalAmount());
        project.setStatus(ProjectStatusEnum.PAYING.getCode());
        //考虑并发
        projectService.updateByPrimaryKeySelectiveForVersion(project);

		//增加节点
		OrderDone orderDone = new OrderDone();
		orderDone.setOrderNo(investment.getOrderNo());
		orderDone.setOrderStatus(OrderDoneEnum.SUBMIT.getFeatureName());
		orderDone.setUpdateDate(new Date());
		orderDone.setCreateDate(new Date());
		orderDone.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
		if (orderDoneService.insert(orderDone) != 1) {
			flag = false;
		}

        if(investmentService.getMapper().insertSelective(investment) == 0) {
            flag = false;
        }
        if(!flag) {
        	throw new LockFailureException();
        }
        return flag;
    }

    @Deprecated
    @Override
    public boolean submitOrder(Investment investment, Assets userAccount, Project project) throws Exception {

        boolean flag = true;

        //可用资金变动纪录
        if (investment.getRemainAmount().compareTo(BigDecimal.ZERO) == 0) {
            if (investment.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
                //扣除使用的余额
                userAccountService.modifyAccount(userAccount, investment.getBalancePayMoney(),
                        investment.getId(), BusinessTableEnum.investment, AccountOperateEnum.INVEST_BALANCE_SUBTRACT);
            }
        } else {
            if (investment.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
                //冻结支付的余额
            	userAccountService.modifyAccount(userAccount, investment.getBalancePayMoney(),
                        investment.getId(), BusinessTableEnum.investment, AccountOperateEnum.INVEST_BALANCE_FREEZE);
            }
        }
        if(investmentService.getMapper().updateByPrimaryKeySelective(investment) == 0) {
        	flag = false;
        }

        //插入订单
        if (investment.getRemainAmount().compareTo(BigDecimal.ZERO) == 0) {
        	investment.setPayStatus(InvestPayStateEnum.PAYING.getCode());
            if(!doPaySuccess(investment, userAccount)) {
            	flag = false;
            }
        }else {
	        //更新用户资金
	        if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount) == 0) {
	            flag = false;
	        }
        }
        
        projectService.updateByPrimaryKeySelectiveForVersion(project);
        
        if(!flag) {
        	throw new LockFailureException();
        }
        return flag;
    }

	/**
	 * 新增投资订单并提交支付 
	 * 合并addOrder及submitOrder
	 * @author 张琼麒
	 * @version 创建时间：2019年6月1日 上午11:50:48
	 * @param investment
	 * @param userAccount
	 * @param project
	 * @param hongbao
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addSubmitOrder(Investment investment, Assets userAccount, Project project, Hongbao hongbao)
			throws Exception {

		if (hongbao != null) {
			// 修改红包的使用情况
			hongbao.setUseTime(new Date());
			//id = #{id,jdbcType=INTEGER} and user_id = #{userId,jdbcType=INTEGER} and use_time is null
			if (hongbaoService.getMapper().update(hongbao) == 0) {
				throw new LockFailureException();
			}
		}

		if (investmentService.getMapper().insertSelective(investment) == 0) {
			throw new LockFailureException();
		}
		
		// 设置标的投资情况
		project.setUpdateDate(new Date());
		project.setUserId(investment.getUserId());
		project.setInvestorsNum(project.getInvestorsNum()+1);
		project.setInvestedAmount(project.getInvestedAmount()+ investment.getAmount());
		project.setStatus(ProjectStatusEnum.PAYING.getCode());
		// 考虑并发
		projectService.updateByPrimaryKeySelectiveForVersion(project);
		// 增加节点
		OrderDone orderDone = new OrderDone();
		orderDone.setOrderNo(investment.getOrderNo());
		orderDone.setOrderStatus(OrderDoneEnum.SUBMIT.getFeatureName());
		orderDone.setUpdateDate(new Date());
		orderDone.setCreateDate(new Date());
		orderDone.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
		if (orderDoneService.insert(orderDone) != 1) {
			throw new LockFailureException();
		}
		
		/*
		// 可用资金变动纪录
		if (investment.getRemainAmount().compareTo(BigDecimal.ZERO) == 0) {
			if (investment.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 扣除使用的余额
				
				userAccountService.modifyBalance(userAccount,
						investment.getBalancePayMoney().multiply(BigDecimalUtil.parse(-1)), investment.getId(),
						BusinessTableEnum.investment, AccountOperateEnum.INVEST_BALANCE_PAY);
			}
		} else {
			if (investment.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 冻结支付的余额
				userAccountService.modifyFrozenBalance(userAccount, investment.getBalancePayMoney(), investment.getId(),
						BusinessTableEnum.investment, AccountOperateEnum.INVEST_FREZEE_BALANCE);
			}
		}*/

		if (investment.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
			if (investment.getRemainAmount().compareTo(BigDecimal.ZERO) == 0) {//全额余额支付
				userAccountService.modifyAccount(userAccount,investment.getBalancePayMoney(),
						investment.getId(),BusinessTableEnum.investment, AccountOperateEnum.INVEST_BALANCE_SUBTRACT);
			} else {//现金组合支付
				userAccountService.modifyAccount(userAccount, investment.getBalancePayMoney(),
						investment.getId(),BusinessTableEnum.investment, AccountOperateEnum.INVEST_BALANCE_FREEZE);
			}
		}
		// 更新用户资金
		if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount) == 0) {
			throw new LockFailureException();
		}
		// 插入订单
		if (investment.getRemainAmount().compareTo(BigDecimal.ZERO) == 0) {
			investment=investmentService.get(investment.getId());
			investment.setPayStatus(InvestPayStateEnum.PAYING.getCode());
			userAccount.setVersion(userAccount.getVersion()+1);
			if (!doPaySuccess(investment, userAccount)) {
				throw new LockFailureException();
			}
		}

	}
    
	@Override
	public List<InvestmentOrderListVO> queryPayList(InvestmentOrderListRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
        map.put("orderNo", request.getOrderNo());
        map.put("orderStatus", request.getOrderStatus());
        map.put("payStatus", request.getPayStatus());
        map.put("userId", request.getUserId());
        map.put("start",request.getLimitStart());
        map.put("limit",request.getLimitEnd());
		map.put("projectType", request.getProjectType());
		List<InvestmentOrderListVO> orderList= investmentMapper.queryInvestPayList(map);
		return orderList;
	}

	@Override
	public int queryPayListCount(InvestmentOrderListRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
        map.put("orderNo", request.getOrderNo());
        map.put("orderStatus", request.getOrderStatus());
        map.put("payStatus", request.getPayStatus());
        map.put("userId", request.getUserId());
        map.put("projectType", request.getProjectType());
		return investmentMapper.queryInvestPayCount(map);
	}
	
	@Override
	public InvestmentDetailsVO queryInvestDetailById(Integer id){
		return investmentMapper.queryInvestDetailById(id);
	}
}
