package com.goochou.p2b.task.doing.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.TransactionClient;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderRequest;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.service.GoodsOrderService;

/**
 * Created on 2019-05-13
 * <p>Title:       [商城订单过期取消]</p>
 * @author         [张琼麒] [259392141@qq.com]
 * @version        1.0
 */
public class QrderCancelTask extends BaseTask {

	private static final long serialVersionUID = 491189105702371416L;

	private static final Logger logger = Logger.getLogger(QrderCancelTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("商城订单过期取消 start------");
    	
    	GoodsOrderService goodsOrderService=(GoodsOrderService)applicationContext.getBean("goodsOrderServiceImpl");
    	if (goodsOrderService == null) {
			System.out.println("goodsOrderService==null");
		}
    	List<GoodsOrder> exceedOrderList;
        try {
        	exceedOrderList=goodsOrderService.getExceedTimeLimitOrder();
        } catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage(), e1);
			exceedOrderList=new ArrayList<GoodsOrder>();
		}
        for(GoodsOrder goodsOrder:exceedOrderList) {
        	 try {
        		logger.info(goodsOrder.getOrderNo() + "商城订单过期取消业务处理开始！");
             	GoodsOrderRequest goodsOrderRequest=new GoodsOrderRequest();
             	goodsOrderRequest.setUserId(goodsOrder.getUserId());
             	goodsOrderRequest.setOrderNo(goodsOrder.getOrderNo());
             	
             	ServiceMessage msg = new ServiceMessage("goodsorder.cancel", goodsOrderRequest);
             	Response response = (Response) TransactionClient.getInstance().setServiceMessage(msg).send();
             	if (response.isSuccess()) {
					logger.info(goodsOrder.getOrderNo() + "商城订单过期取消业务处理成功！");
				} else {
					logger.info(goodsOrder.getOrderNo() + "商城订单过期取消业务处理失败！");
				}
             } catch (Exception e) {
                 e.printStackTrace();
                 logger.error(e.getMessage(), e);
             }
        }
       
        logger.info("商城订单过期取消 end------");
    }
}
