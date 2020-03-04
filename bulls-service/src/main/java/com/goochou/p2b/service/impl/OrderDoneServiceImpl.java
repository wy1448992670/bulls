package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.OrderDoneEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.dao.ActivityDetailSendHongbaoMapper;
import com.goochou.p2b.dao.OrderDoneMapper;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;
import com.goochou.p2b.model.ActivityDetailSendHongbao;
import com.goochou.p2b.model.ActivityDetailSendHongbaoExample;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.InvestmentBlance;
import com.goochou.p2b.model.OrderDone;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.InvestmentBlanceService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.OrderDoneService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.PayUtil;

@Service
public class OrderDoneServiceImpl implements OrderDoneService {

    private static final Logger logger = Logger.getLogger(OrderDoneServiceImpl.class);

    @Resource
    private OrderDoneMapper orderDoneMapper;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private InvestmentBlanceService investmentBlanceService;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private UserService userService;
    @Resource
    private ProjectService projectService;
    @Resource
    private MemcachedManager memcachedManager;
    @Resource
    private ActivityDetailSendHongbaoMapper activityDetailSendHongbaoMapper;
    @Override
    public int insert(OrderDone orderDone) {
        orderDone.setCreateDate(new Date());
        orderDone.setUpdateDate(new Date());
        return orderDoneMapper.insertSelective(orderDone);
    }

	@Override
	public List<OrderDone> queryOrderDoneListByOrderNo(String orderNo) {
		return orderDoneMapper.queryOrderDoneListByOrderNo(orderNo);
	}

    @Override
    public List<OrderDone> queryOrderDoneNeedSendMessage() {
         return orderDoneMapper.queryOrderDoneNeedSendMessage();
    }

    @Override
    public boolean doSend(OrderDone next) {
        boolean flag = false;
        boolean market = false;
        int userId = 0;
        SendMessageRequest smr = new SendMessageRequest();
        if(next.getOrderType().equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
            Investment investment = investmentService.findByOrderNo(next.getOrderNo());
            Project project = projectService.get(investment.getProjectId());
            userId = investment.getUserId();
            if(next.getOrderStatus().equals(OrderDoneEnum.SUBMIT.getFeatureName())) {
                market = true;
                BigDecimal interestAmount = PayUtil.getInterestAmount(project, false);
                smr.setContent(DictConstants.BULLS_VALIDATE_CODE.replaceAll("\\{title}", project.getTitle()));
                smr.setContent(smr.getContent().replaceAll("\\{interestAmount}", interestAmount+""));
                smr.setContent(smr.getContent().replaceAll("\\{orderNo}", investment.getOrderNo()));
                smr.setContent(smr.getContent().replaceAll("\\{time}", String.valueOf(memcachedManager.getCacheKeyValue(DictConstants.PAY_WAIT_TIME))));
            }else if(next.getOrderStatus().equals(OrderDoneEnum.PAY.getFeatureName())) {
                smr.setContent(DictConstants.SUBMIT_VALIDATE_CODE.replaceAll("\\{title}", project.getTitle()));
                smr.setContent(smr.getContent().replaceAll("\\{orderNo}", investment.getOrderNo()));
                smr.setContent(smr.getContent().replaceAll("\\{interestAmount}", investment.getInterestAmount()+""));
            }else if(next.getOrderStatus().equals(OrderDoneEnum.SUCCESS.getFeatureName())) {
                smr.setContent(DictConstants.AUTO_BACK_VALIDATE_CODE.replaceAll("\\{title}", project.getTitle()));
                smr.setContent(smr.getContent().replaceAll("\\{interestAmount}", investment.getInterestAmount()+""));
                smr.setContent(smr.getContent().replaceAll("\\{orderNo}", investment.getOrderNo()));
                smr.setContent(smr.getContent().replaceAll("\\{totalAmount}", BigDecimalUtil.add(investment.getAmount(), investment.getInterestAmount()).toString()));
                InvestmentBlance investmentBlance = investmentBlanceService.findByInvestId(investment.getId());
                smr.setContent(smr.getContent().replaceAll("\\{balanceAmount}", BigDecimalUtil.sub(BigDecimalUtil.add(investment.getAmount(), investment.getInterestAmount()), investmentBlance.getUseAmount()).toString()));
                
                ActivityDetailSendHongbaoExample activityDetailSendHongbaoExample=new ActivityDetailSendHongbaoExample();
				activityDetailSendHongbaoExample.createCriteria().andActivityDetailIdEqualTo(8);//卖牛获得58元红包
				List<ActivityDetailSendHongbao> activityDetailSendHongbaoList = activityDetailSendHongbaoMapper.selectByExample(activityDetailSendHongbaoExample);
                smr.setContent(smr.getContent().replaceAll("\\{redAmount}", !activityDetailSendHongbaoList.isEmpty() ? String.valueOf(activityDetailSendHongbaoList.get(0).getAmount()) : "58"));
            }
            else if(next.getOrderStatus().equals(OrderDoneEnum.DUE.getFeatureName())) {
                smr.setContent(DictConstants.DEADLINE_VALIDATE_CODE.replaceAll("\\{title}", project.getTitle()));
                smr.setContent(smr.getContent().replaceAll("\\{orderNo}", investment.getOrderNo()));
            }
        }else if(next.getOrderType().equals(OrderTypeEnum.GOODS.getFeatureName())){
            GoodsOrder goodsOrder = goodsOrderService.findByOrderNum(next.getOrderNo());
            userId = goodsOrder.getUserId();
            if(next.getOrderStatus().equals(OrderDoneEnum.SUBMIT.getFeatureName())) {
                market = true;
                smr.setContent(DictConstants.GOODS_VALIDATE_CODE.replaceAll("\\{title}", goodsOrder.getGoodsOrderDetail().get(0).getGoods().getGoodsName()));
                smr.setContent(smr.getContent().replaceAll("\\{orderNo}", goodsOrder.getOrderNo()));
                smr.setContent(smr.getContent().replaceAll("\\{time}", String.valueOf(memcachedManager.getCacheKeyValue(DictConstants.PAY_WAIT_TIME))));
            }
        }
        User user = userService.get(userId);
        next.setSend(1);
        logger.info(JSONArray.toJSON(next));
        int i = orderDoneMapper.updateByPrimaryKey(next);
        logger.info("i="+i);
        if(i>0) {
            flag = true;
            try {
                smr.setPhone(user.getPhone());
                smr.setMarket(market);
                logger.info(JSONArray.toJSON(smr));
                ServiceMessage msg = new ServiceMessage("message.send", smr);
                SendMessageResponse result = (SendMessageResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                logger.info(JSONArray.toJSON(result));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
