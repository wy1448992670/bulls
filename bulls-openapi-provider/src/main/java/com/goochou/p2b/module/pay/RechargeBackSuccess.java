package com.goochou.p2b.module.pay;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.TradeMessageStatusEnum;
import com.goochou.p2b.constant.TradeMessageTypeEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.TradeMessageLogService;

/**
 * 充值回调成功业务接口，回调时业务未正常处理，此处重复处理
 * @author WuYJ
 *
 */
@Service
public class RechargeBackSuccess implements HessianInterface {

	private static final Logger logger = Logger.getLogger(RechargeBackSuccess.class);

	@Resource
	private RechargeService rechargeService;
	@Resource
    private TradeMessageLogService tradeMessageLogService;
	@Resource
    private AssetsMapper assetsMapper;


	@Override
	public Response execute(ServiceMessage msg) {
		Response result = new Response();
		List<TradeMessageLog> list = tradeMessageLogService.selectTradeMessageLog(TradeMessageStatusEnum.WEIWANCHENG.getFeatureName(), TradeMessageTypeEnum.BACK.getFeatureName(), 100);
		if (null != list && !list.isEmpty()) {
			for (TradeMessageLog tradeMessageLog : list) {
				Recharge recharge = rechargeService.getByOrder(tradeMessageLog.getInOrderId());
				if (recharge == null) {
                    logger.info("============找不到该笔充值订单=============");
                    continue;
				}
                if (recharge.getStatus()!=1) {
                    logger.info("==============充值单状态不是处理中,不能执行完成支付中支付单方法==============");
                    continue;
                }
                //充值成功
                recharge.setRemark(tradeMessageLog.getMessageInfo());
                recharge.setOutOrderNo(tradeMessageLog.getOutOrderId());
                try {
                	Assets assets = assetsMapper.selectByPrimaryKey(recharge.getUserId()); // 获取他的资产，取得可用余额
                	if(String.valueOf(Constants.DATE_VALIT).equals(tradeMessageLog.getMessageStatus())){
                        recharge.setStatus(0);
                    } else {
                        recharge.setStatus(2);
                    }
                	rechargeService.updateRecord(recharge, assets, null);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
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
