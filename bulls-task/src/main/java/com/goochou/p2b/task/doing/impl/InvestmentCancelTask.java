package com.goochou.p2b.task.doing.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.constant.InvestmentTypeEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.TradeMessageStatusEnum;
import com.goochou.p2b.constant.TradeMessageTypeEnum;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.client.TransactionClient;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayRequest;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayResponse;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.RechargeService;

/**
 * Created on 2019-05-14
 * <p>Title:[投资订单过期取消]</p>
 * @author [张琼麒] [259392141@qq.com]
 * @version 1.0
 */
public class InvestmentCancelTask extends BaseTask {

	private static final long serialVersionUID = 4381179482699047613L;
	private static final Logger logger = Logger.getLogger(InvestmentCancelTask.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("投资订单过期取消 start------");
		InvestmentService investmentService=(InvestmentService)applicationContext.getBean("investmentServiceImpl");

		List<Investment> exceedInvestmentList;
		try {
			exceedInvestmentList = investmentService.getExceedTimeLimitInvestment();
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage(), e1);
			exceedInvestmentList=new ArrayList<Investment>();
		}
		for (Investment exceedInvestment : exceedInvestmentList) {
			try {
				logger.info(exceedInvestment.getOrderNo() + "投资订单过期取消业务处理开始！");
				// investmentService.cancelInvestment(exceedInvestment);
				InvestmentOrderRequest request = new InvestmentOrderRequest();
				request.setOrderNo(exceedInvestment.getOrderNo());
				request.setUserId(exceedInvestment.getUserId());
				ServiceMessage msg = new ServiceMessage("investorder.cancel", request);
				Response response = (Response) TransactionClient.getInstance().setServiceMessage(msg).send();

				if (response.isSuccess()) {
					logger.info(exceedInvestment.getOrderNo() + "投资订单过期取消业务处理成功！");
				} else {
					logger.info(exceedInvestment.getOrderNo() + "投资订单过期取消业务处理失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}

		logger.info("投资订单过期取消 end------");
	}

	
}
