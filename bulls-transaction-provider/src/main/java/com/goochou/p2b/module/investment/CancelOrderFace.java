package com.goochou.p2b.module.investment;

import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayRequest;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayResponse;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderRequest;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.service.PastureOrderService;
import com.goochou.p2b.service.RechargeService;

/**
 * @Auther: huangsj
 * @Date: 2019/5/14 10:37
 * @Description:
 */
@Service
public class CancelOrderFace implements HessianInterface {

	@Autowired
	private PastureOrderService orderService;
	@Autowired
	private RechargeService rechargeService;
	
	@Override
	public Response execute(ServiceMessage msg) {

		InvestmentOrderRequest orderRequest = (InvestmentOrderRequest) msg.getReq();
		Response response = new Response();
		response.setSuccess(false);

		try {
			//判断订单是否存在
			Investment order = orderService.findOrderByNum(orderRequest.getOrderNo());
			if (order == null) {
				response.setSuccess(false);
				response.setErrorMsg("参数有误");
				return response;
			}

			if (order.getUserId().intValue() != orderRequest.getUserId()) {
				response.setSuccess(false);
				response.setErrorMsg("参数有误");
				return response;
			}
			if(order.getPayStatus() == InvestPayStateEnum.PAYING.getCode()) {
				List<Recharge> rechargeList = rechargeService.getPayingRechargeByOrderTypeAndNo(
						OrderTypeEnum.INVESTMENT.getFeatureName(), order.getId());
				if (rechargeList.size() != 1) {
					throw new Exception("exceedInvestment:" + order.getId() + " "
							+ order.getOrderNo() + "支付单异常");
				}else {
					//尝试完成支付
					rechargeService.doTryCompletePayingRecharge(rechargeList.get(0));
					
					//如果尝试完成过支付,重新加载investment
					order= orderService.findOrderByNum(orderRequest.getOrderNo());
				}
			}
			
			if (order.getPayStatus() != InvestPayStateEnum.NO_PAY.getCode()) {
				response.setSuccess(false);
				response.setErrorMsg("订单此时的状态不支持取消");
				return response;
			}

			if (order.getOrderStatus() != InvestmentStateEnum.no_buy.getCode()) {
				response.setSuccess(false);
				response.setErrorMsg("订单不支持取消");
				return response;
			}

			if (orderService.cancelOrder(order)) {
				response.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			OpenApiApp.EXCEPTION.exception(msg, e);
			response.setSuccess(false);
			response.setErrorMsg("出现异常");
		}
		return response;
	}

	@Override
	public void before(ServiceMessage msg) {

	}

	@Override
	public void after(ServiceMessage msg) {

	}
}