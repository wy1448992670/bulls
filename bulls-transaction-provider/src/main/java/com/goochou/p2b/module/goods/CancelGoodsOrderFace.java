package com.goochou.p2b.module.goods;

import java.util.List;

import com.goochou.p2b.constant.redis.RedisConstants;
import com.goochou.p2b.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.RechargeService;

/**
 * 商城订单取消
 *
 * @Auther: huangsj
 * @Date: 2019/5/10 17:37
 * @Description:
 */
@Service
public class CancelGoodsOrderFace implements HessianInterface {

	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private RechargeService rechargeService;
	
	@Override
	public Response execute(ServiceMessage msg) {

		GoodsOrderRequest orderRequest = (GoodsOrderRequest) msg.getReq();
		Response response = new Response();
		response.setSuccess(false);

		try {
			//判断订单是否存在
			GoodsOrder goodsOrder = goodsOrderService.findByOrderNum(orderRequest.getOrderNo());
			if (goodsOrder == null) {
				response.setSuccess(false);
				response.setErrorMsg("参数有误");
				return response;
			}

			if (goodsOrder.getUserId().intValue() != orderRequest.getUserId()) {
				response.setSuccess(false);
				response.setErrorMsg("参数有误");
				return response;
			}
			if(goodsOrder.getState() == GoodsOrderStatusEnum.PAYING.getCode()) {
				List<Recharge> rechargeList = rechargeService.getPayingRechargeByOrderTypeAndNo(
						OrderTypeEnum.GOODS.getFeatureName(), goodsOrder.getId());
				if (rechargeList.size() != 1) {
					throw new Exception("exceedGoodOrder:" + goodsOrder.getId() + " "
							+ goodsOrder.getOrderNo() + "支付单异常");
				}else {
					//尝试完成支付
					rechargeService.doTryCompletePayingRecharge(rechargeList.get(0));
					
					//如果尝试完成过支付,重新加载investment
					goodsOrder= goodsOrderService.findByOrderNum(orderRequest.getOrderNo());
				}
			}
			
			if (goodsOrder.getState() != GoodsOrderStatusEnum.NO_PAY.getCode()) {
				response.setSuccess(false);
				response.setErrorMsg("订单此时的状态不支持取消");
				return response;
			}
			Assets assets = assetsService.findByuserId(orderRequest.getUserId());
			if (null == assets) {
				response.setSuccess(false);
				response.setErrorMsg("用户账户异常");
				return response;
			}
			goodsOrderService.cancelOrder(goodsOrder, assets);
			response.setSuccess(true);
		} catch (Exception e) {
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
