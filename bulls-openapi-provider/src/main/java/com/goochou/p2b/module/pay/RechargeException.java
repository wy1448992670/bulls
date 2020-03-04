package com.goochou.p2b.module.pay;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.TradeMessageStatusEnum;
import com.goochou.p2b.constant.TradeMessageTypeEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouPayRequest;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouPayResponse;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayRequest;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayResponse;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.TradeMessageLogService;
import com.goochou.p2b.utils.DateUtil;

/**
 * 充值异常订单处理
 * 1、1小时未无报文记录，系统自动修改为用户取消充值
 * 2、1小时有报文记录，查询第三方接口处理相应业务
 * @author WuYJ
 *
 */
@Service
public class RechargeException implements HessianInterface {

	private final static Logger logger = Logger.getLogger(RechargeException.class);

	@Resource
	private RechargeService rechargeService;
	@Resource
    private TradeMessageLogService tradeMessageLogService;
	@Autowired
	private QueryFuiouPay queryFuiouPayAO;
	@Autowired
	private QueryFuiouQuickPay queryFuiouQuickPay;
	@Resource
    private AssetsMapper assetsMapper;

	@Override
	public Response execute(ServiceMessage msg) {
		Response result = new Response();
		Date startDate = null;
		String source = "2017-09-25 22:00:00";
		try {
			startDate = DateUtil.dateFullTimeFormat.parse(source);
		} catch (ParseException e1) {
			e1.printStackTrace();
			logger.error("日期格式化有异常");
		}
		//1小时未无报文记录，系统自动修改为用户取消充值
		int cancelCount = rechargeService.rechargeCancel(startDate);
		logger.info("1小时未无报文记录，系统自动修改为用户取消充值记录数：" + cancelCount);
		//1小时有报文记录，网关充值处理中数据
//		List<Recharge> gatewayRecharges = rechargeService.selectRechargeException(OutPayEnum.FUIOU.getFeatureName(), 1, 50,startDate);
//		int gatewaySuccess = 0;
//		if (null != gatewayRecharges && !gatewayRecharges.isEmpty()) {
//			for (Recharge recharge : gatewayRecharges) {
//				//是否执行操作
//				boolean isOperate = true;
//				//调用网关支付查询接口
//				QueryFuiouPayRequest request = new QueryFuiouPayRequest();
//				request.setOrderNo(recharge.getOrderNo());
//				ServiceMessage payMsg = new ServiceMessage("fuiou.order.query",request);
//				QueryFuiouPayResponse payResult = (QueryFuiouPayResponse) queryFuiouPayAO.execute(payMsg);
//				String resultStatus = payResult.getStatus();
//				String remark = payResult.getErrorMsg();
//				String outOrderno = payResult.getOutOrderId();
//				switch (resultStatus) {
//					case "1"://成功
//						recharge.setStatus(0);
//						break;
//					case "2"://失败
//						recharge.setStatus(2);
//						break;
//					case "0":
//						recharge.setStatus(2);
//						remark = "用户取消支付";
//						break;
//					default://不处理
//						isOperate = false;
//						break;
//				}
//	            if (isOperate) {
//	            	Assets assets = assetsMapper.selectByPrimaryKey(recharge.getUserId()); // 获取他的资产，取得可用余额
//	            	//查询是否有回调记录
//	            	TradeMessageLog tradeMessageLog = tradeMessageLogService.getTradeMessageLog(outOrderno, TradeMessageTypeEnum.BACK.getFeatureName());
//	            	if (null == tradeMessageLog) {
//	            		tradeMessageLog = TradeMessageLog.createTradeMessageLogFactory(null, String.valueOf(recharge.getPayChannel()),
//                				String.valueOf(recharge.getPayChannel()), TradeMessageTypeEnum.BACK.getFeatureName(), String.valueOf(recharge.getUserId()),
//                				recharge.getOrderNo(), recharge.getOutOrderNo(), null, remark, String.valueOf(true), TradeMessageStatusEnum.YIWANCHENG.getFeatureName());
//					}else {
//						tradeMessageLog = null;
//					}
//	            	//充值成功
//		            recharge.setRemark(remark);
//		            recharge.setOutOrderNo(outOrderno);
//		            try {
//	                    rechargeService.updateRecord(recharge, assets, tradeMessageLog);
//	                    gatewaySuccess++;
//					} catch (Exception e) {
//						logger.error("充值操作业务异常，" + recharge.toString());
//						e.printStackTrace();
//						continue;
//					}
//				}else {
//					gatewaySuccess++;
//				}
//			}
//			logger.info("1小时未无报文记录，查询网关接口处理业务：处理数："+ gatewayRecharges.size() + "，成功数：" + gatewaySuccess);
//		}

		//1小时有报文记录，认证充值处理中数据
		List<Recharge> authapplyRecharges = rechargeService.selectRechargeException(OutPayEnum.FUIOU_QUICK.getFeatureName(), 1, 50, startDate); // TODO sq 枚举
		int authapplySuccess = 0;
		if (null != authapplyRecharges && !authapplyRecharges.isEmpty()) {
			for (Recharge recharge : authapplyRecharges) {
				//是否执行操作
				boolean isOperate = true;
				//调用认证支付查询接口
				QueryFuiouQuickPayRequest request = new QueryFuiouQuickPayRequest();
				request.setOutOrderNo(recharge.getOutOrderNo());
				ServiceMessage quickMsg = new ServiceMessage("fuiou.quick.order.query",request);
				QueryFuiouQuickPayResponse payResult = (QueryFuiouQuickPayResponse) queryFuiouQuickPay.execute(quickMsg);
				String resultStatus = payResult.getStatus();
				String remark = payResult.getErrorMsg();
				switch (resultStatus) {
					case "1"://成功
						remark = "成功（已查询）";
						recharge.setStatus(0);
						break;
					case "2"://失败
						recharge.setStatus(2);
						break;
					case "0"://失败
						recharge.setStatus(2);
						remark = "用户取消支付";
						break;
					default://不处理
						isOperate = false;
						break;
				}
	            if (isOperate) {
	            	Assets assets = assetsMapper.selectByPrimaryKey(recharge.getUserId()); // 获取他的资产，取得可用余额
	            	//查询是否有回调记录
	            	TradeMessageLog tradeMessageLog = tradeMessageLogService.getTradeMessageLog(recharge.getOutOrderNo(), TradeMessageTypeEnum.BACK.getFeatureName());
	            	if (null == tradeMessageLog) {
	            		tradeMessageLog = TradeMessageLog.createTradeMessageLogFactory(null, String.valueOf(recharge.getPayChannel()),
                				String.valueOf(recharge.getPayChannel()), TradeMessageTypeEnum.BACK.getFeatureName(), String.valueOf(recharge.getUserId()),
                				recharge.getOrderNo(), recharge.getOutOrderNo(), null, remark, String.valueOf(true), TradeMessageStatusEnum.YIWANCHENG.getFeatureName());
					}else {
						tradeMessageLog = null;
					}
	            	//充值成功
		            recharge.setRemark(remark);
		            try {
	                    rechargeService.updateRecord(recharge, assets, tradeMessageLog);
					} catch (Exception e) {
						logger.error("充值操作业务异常，" + recharge.toString());
						e.printStackTrace();
						continue;
					}
		            authapplySuccess++;
				}else {
					authapplySuccess++;
				}
			}
			logger.info("1小时未无报文记录，查询网关接口处理业务：处理数："+ authapplyRecharges.size() + "，成功数：" + authapplySuccess);
		}
		result.setSuccess(true);
		return result;
	}

	@Override
	public void before(ServiceMessage msg) {
	}

	@Override
	public void after(ServiceMessage msg) {
	}

}
