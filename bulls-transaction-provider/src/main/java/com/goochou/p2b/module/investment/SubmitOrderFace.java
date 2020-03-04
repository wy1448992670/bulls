package com.goochou.p2b.module.investment;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.ProjectTypeEnum;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.constant.pasture.ProjectStatusEnum;
import com.goochou.p2b.dao.ProjectViewMapper;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.OrderResponse;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectExample;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.idGenerator.OrderIdGenerator;
import com.goochou.p2b.model.vo.ProjectView;
import com.goochou.p2b.model.vo.ProjectViewExample;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.PastureOrderService;
import com.goochou.p2b.service.PayTunnelService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.BigDecimalUtil;

/**
 * @Auther: huangsj
 * @Date: 2019/5/21 13:09
 * @Description:
 */
@Service
public class SubmitOrderFace implements HessianInterface {

	private static final Logger logger = Logger.getLogger(SubmitOrderFace.class);

	@Autowired
	private HongbaoService hongbaoService;
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private PastureOrderService pastureOrderService;
	@Autowired
	private OrderIdGenerator bullsOrderIdGenerator;
	@Autowired
	private UserService userService;
	@Resource
	private PayTunnelService payTunnelService;
	@Resource
	private ProjectViewMapper projectViewMapper;
	

	@Override
	public OrderResponse execute(ServiceMessage msg) {
		OrderResponse response = new OrderResponse();
		response.setSuccess(false);
		Date now = new Date();
		InvestmentOrderRequest request = (InvestmentOrderRequest) msg.getReq();

		// 参数非空判断
		if (null == request.getUserId() || null == request.getProjectId()) {
			response.setSuccess(false);
			response.setErrorMsg("[userId,projectId]为必传参数");
			return response;
		}
		if (request.getBlancePayMoney() == null) {
			request.setBlancePayMoney(BigDecimal.ZERO);
		}
		if(request.getBlancePayMoney().compareTo(BigDecimal.ZERO)<0) {
			response.setSuccess(false);
			response.setErrorMsg("余额支付金额不能小于0");
			return response;
		}
		// 判断用户
		User user = userService.get(request.getUserId());
		if (null == user) {
			response.setSuccess(false);
			response.setErrorMsg("用户不存在");
			return response;
		}
		//项目是否可购买
		Project project = projectService.getProjectById(request.getProjectId());
		if (null == project) {
			response.setSuccess(false);
			response.setErrorMsg("项目不存在");
			return response;
		}
		
		ProjectViewExample pvExample=new ProjectViewExample();
		pvExample.createCriteria().andIdEqualTo(request.getProjectId());
        List<ProjectView> projectViewList=projectViewMapper.selectByExample(pvExample);
        ProjectView projectView=null;
        if(projectViewList.size()==1){
        	projectView=projectViewList.get(0);
        }else {
        	response.setSuccess(false);
			response.setErrorMsg("项目不存在");
			return response;
        }
        
        //检查牛只可售状态
		if(projectView.getProjectType().equals(ProjectTypeEnum.PINNIU.getFeatureType())){
			if (project.getStatus() != ProjectStatusEnum.ENABLE_SALE.getCode() && project.getStatus() != ProjectStatusEnum.PAYING.getCode()) {
				response.setSuccess(false);
				response.setErrorMsg("项目不在出售状态");
				return response;
			}
		}else if(projectView.getProjectType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())){
			if(project.getBuyAgain()){
				if(project.getUserId().intValue()!= user.getId()){
					response.setSuccess(false);
					response.setErrorMsg("此牛只你不能续养");
					return response;
				}
				if (project.getStatus() != ProjectStatusEnum.BUILDED.getCode()) {
					response.setSuccess(false);
					response.setErrorMsg("项目不在可续购状态");
					return response;
				}
			}else if (project.getStatus() != ProjectStatusEnum.ENABLE_SALE.getCode()) {
				response.setSuccess(false);
				response.setErrorMsg("项目不在出售状态");
				return response;
			}
		}else {
			response.setSuccess(false);
			response.setErrorMsg("项目不在出售状态");
			return response;
		}
		
		//检查牛只可售额度
		if(projectView.getProjectType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
        	if(request.getPoint() == null || request.getPoint()<=0) {
        		response.setSuccess(false);
        		response.setErrorMsg("拼牛份数有误");
				return response;
        	}
        	if(request.getPoint()>projectView.getPinResiduePoint()) {
        		response.setSuccess(false);
        		response.setErrorMsg("拼牛份数不足");
				return response;
        	}
        	if(request.getPinAmount() == null || request.getPinAmount().compareTo(BigDecimal.ZERO)<0) {
        		response.setSuccess(false);
        		response.setErrorMsg("拼牛金额有误");
				return response;
        	}
        	//当牛只不能被份数整除或份数调整时,会出现最后一份金额变化
        	if(projectView.getPinAmountForPoint(request.getPoint()).compareTo(request.getPinAmount())!=0  ) {
        		response.setSuccess(false);
        		response.setErrorMsg("拼牛金额有变");
				return response;
        	}
        }else {
        	request.setPinAmount(BigDecimal.valueOf(projectView.getTotalAmount()));
        }
		
		//检查牛只可售额度
		if(request.getPinAmount().compareTo(projectView.getResidueAmount())>0) {
    		response.setSuccess(false);
    		response.setErrorMsg("可售金额不足");
			return response;
    	}
		
		// 判断是否是新手标，只能购买一次
		if (project.getNoob() == 1) {
			ProjectExample example=new ProjectExample();
			example.createCriteria().andNoobEqualTo(1).andUserIdEqualTo(user.getId()).andStatusIn(Arrays.asList(2,3,4,5));////0待上架1上架2待付款3已出售4已回购
			int noobCnt=projectService.getMapper().countByExample(example);
			if(noobCnt>0) {
				response.setSuccess(false);
				response.setErrorMsg("新手项目用户只能购买一次");
				return response;
			}
		}
        
		Hongbao hongbao = null;
		// 判断红包是否可用
		if (null != request.getHongbaoId() && 0 != request.getHongbaoId()) {
			hongbao = hongbaoService.get(request.getHongbaoId());
			if (hongbao == null) {
				response.setSuccess(false);
				response.setErrorMsg("红包不存在");
				return response;
			}
			if (hongbao.getUserId().intValue() != request.getUserId().intValue()) {
				response.setSuccess(false);
				response.setErrorMsg("红包归属错误");
				return response;
			}
			
			if (hongbao.getExpireTime().getTime() < now.getTime()) {
				response.setSuccess(false);
				response.setErrorMsg("红包已过期");
				return response;
			}
			if (hongbao.getUseTime() != null) {
				response.setSuccess(false);
				response.setErrorMsg("红包已使用");
				return response;
			}
			
			if(projectView.getProjectType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
				if (hongbao.getType() != 4) {
					response.setSuccess(false);
					response.setErrorMsg("红包不支持此类订单");
					return response;
				}
			}else if(projectView.getProjectType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
				if (hongbao.getType() != 2) {
					response.setSuccess(false);
					response.setErrorMsg("红包不支持此类订单");
					return response;
				}
			}else {
				response.setSuccess(false);
				response.setErrorMsg("红包不支持此类订单");
				return response;
			}
			
			if (hongbao.getLimitAmount() > request.getPinAmount().doubleValue()) {
				response.setSuccess(false);
				response.setErrorMsg("未满足红包限制金额");
				return response;
			}
			
			//使用期限
			if(hongbao.getLimitDay()>project.getLimitDays()) {
				response.setSuccess(false);
				response.setErrorMsg("未满足红包使用期限");
				return response;
			}
		}

		// 获取用户账户
		Assets userAccount = assetsService.findByuserId(request.getUserId());
		if (null == userAccount) {
			response.setSuccess(false);
			response.setErrorMsg("用户账户异常");
			return response;
		}
	
		// 初始化订单
		Investment investment = new Investment();
		investment.setOrderNo(bullsOrderIdGenerator.next());
		investment.setProductId(project.getProductId());
		investment.setProjectId(project.getId());
		investment.setUserId(request.getUserId());
		investment.setPayStatus(InvestPayStateEnum.NO_PAY.getCode());
		investment.setOrderStatus(InvestmentStateEnum.no_buy.getCode());
		investment.setClient(request.getClientEnum().getFeatureName());
		investment.setCreateDate(new Date());
		investment.setType(project.getProjectType());
		//总投资金额
		investment.setAmount(request.getPinAmount().doubleValue());

		investment.setRemainAmount(BigDecimalUtil.parse(investment.getAmount()));

		//红包使用
		if (hongbao != null) {
			investment.setHongbaoId(hongbao.getId());

			if (BigDecimalUtil.parse(hongbao.getAmount()).compareTo(investment.getRemainAmount()) > 0) {
				investment.setHongbaoMoney(investment.getRemainAmount());
			} else {
				investment.setHongbaoMoney(BigDecimalUtil.parse(hongbao.getAmount()));
			}
		} else {
			investment.setHongbaoMoney(BigDecimal.ZERO);
		}
		investment.setRemainAmount(investment.getRemainAmount().subtract(investment.getHongbaoMoney()));

		//自动使用余额
		if (request.isAutoUseBalance()) {
			if(investment.getRemainAmount().compareTo(BigDecimalUtil.parse(userAccount.getBalanceAmount()))>0){
				investment.setBalancePayMoney(BigDecimalUtil.parse(userAccount.getBalanceAmount()));
			}else{
				investment.setBalancePayMoney(investment.getRemainAmount());
			}
		}else {//指定余额
			if (investment.getRemainAmount().compareTo(request.getBlancePayMoney()) < 0) {
				investment.setBalancePayMoney(investment.getRemainAmount());
			}else{
				investment.setBalancePayMoney(request.getBlancePayMoney());
			}
		}
		investment.setRemainAmount(investment.getRemainAmount().subtract(investment.getBalancePayMoney()));
		
		if(investment.getRemainAmount().compareTo(BigDecimal.ZERO)<0 
				|| investment.getBalancePayMoney().compareTo(BigDecimal.ZERO)<0 
				|| investment.getHongbaoMoney().compareTo(BigDecimal.ZERO)<0 ){
			response.setSuccess(false);
			response.setErrorMsg("参数有误");
			return response;
		}

		if (BigDecimalUtil.parse(userAccount.getBalanceAmount()).compareTo(investment.getBalancePayMoney()) < 0) {
			response.setSuccess(false);
			response.setErrorMsg("可用余额不足");
			return response;
		}
		
		try {
			project.setDeadline(now);
			//预生账单,试算利息
			projectService.tryGeneratedInterestForOneInvestment(project, investment);
			project.setDeadline(null);
		} catch (Exception e) {
			e.printStackTrace();
			OpenApiApp.EXCEPTION.exception(msg, e);
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
			logger.error(request.toString());
		}
		// 添加订单
		try {
			pastureOrderService.addSubmitOrder(investment, userAccount, project,hongbao);
			response.setId(investment.getId());
			response.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
			response.setNeedPayMoney(investment.getRemainAmount());
			response.setOrderNo(investment.getOrderNo());
			response.setInvestmentType(investment.getType());
			response.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			OpenApiApp.EXCEPTION.exception(msg, e);
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
			logger.error(request.toString());
		}

		return response;
	}

	@Override
	public void before(ServiceMessage msg) {
	}

	@Override
	public void after(ServiceMessage msg) {
	}

	public static void main(String[] args) {
		System.out.println(BigDecimalUtil.parse(100000).subtract(BigDecimalUtil.parse(0))
				.subtract(BigDecimalUtil.parse(307686.74)));
	}

}
