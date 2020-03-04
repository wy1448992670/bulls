package com.goochou.p2b;

import java.util.*;

import com.goochou.p2b.hessian.transaction.OrderResponse;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.RepayUnitEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.hessian.ListResponse;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.TransactionClient;
import com.goochou.p2b.hessian.openapi.pay.PayOrderRequest;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListRequest;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListResponse;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderRequest;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderListRequest;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderRequest;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateTimeUtil;
import com.goochou.p2b.utils.DateUtil;

/**
 * @Auther: huangsj
 * @Date: 2019/5/14 16:05
 * @Description:
 */
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class OrderTest {


    @Test
    public void addPayOrder() {
        Response result = new Response();
        PayOrderRequest req = new PayOrderRequest();
        req.setUserId(4);
        req.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
        req.setOrderNum("N2019052114260266984");
        req.setPayChannel(OutPayEnum.FUIOU_QUICK.getFeatureName());
        req.setBankCardId(2);
        req.setClientEnum(ClientEnum.PC);

        ServiceMessage msg = new ServiceMessage("payorder.create", req);
//        result = OpenApiClient.getInstance().setServiceMessage(msg).send();
        result = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }


    @Test
    public void addGoodsOrder() {
        Response result = new Response();
        GoodsOrderRequest req = new GoodsOrderRequest();
//        req.setGoodsId(1);
//        req.setCount(1);
        req.setGoodsIds(Arrays.asList(14, 15, 16));
        req.setCounts(Arrays.asList(1, 5, 2));

        req.setClientEnum(ClientEnum.PC);
//        req.setHongbaoId(36);
        req.setAddressId(3);
        req.setUserId(4);
        ServiceMessage msg = new ServiceMessage("goodsorder.add", req);
        result = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }


    @Test
    public void submitGoodsOrder() {
//        Response result = new Response();
//        GoodsOrderRequest req = new GoodsOrderRequest();
////        req.setOrderNo("G2019052716290053506");
////        req.setGoodsId(1);
////        req.setCount(1);
//        req.setClientEnum(ClientEnum.PC);
//        req.setCreditPayMoney(BigDecimalUtil.parse(0));
//        req.setBalancePayMoney(BigDecimalUtil.parse(0));
//        req.setUserId(4);
//        ServiceMessage msg = new ServiceMessage("goodsorder.submit", req);
//        result = TransactionClient.getInstance().setServiceMessage(msg).send();
//        System.out.println(result.toString());


        GoodsOrderRequest req = new GoodsOrderRequest();
//            req.setOrderNo(orderNo);
        req.setClientEnum(ClientEnum.PC);

        req.setCreditPayMoney(BigDecimalUtil.parse(0));

        req.setBalancePayMoney(BigDecimalUtil.parse(0));
        req.setUserId(546848);
        req.setAddressId(12);
//        req.setHongbaoId(hongbaoId);
        List<Integer> goods=new ArrayList<Integer>();
        goods.add(973);
        req.setGoodsIds(goods);
        req.setAutoUseCredit(false);
        req.setAutoUseBalance(false);

        List<Integer> counts=new ArrayList<Integer>();
        counts.add(1);
        req.setCounts(counts);
        //参与秒杀活动
        if(true) {
            req.setSecondKill(true);
            req.setActivityDetailId(3);
        }

        ServiceMessage msg = new ServiceMessage("goodsorder.submit", req);
        OrderResponse response = (OrderResponse) TransactionClient.getInstance().setServiceMessage(msg).send();

    }

    @Test
    public void goodsOrderPaySuccess() {
        Response result = new Response();
        GoodsOrderRequest req = new GoodsOrderRequest();
        req.setClientEnum(ClientEnum.PC);
        req.setOrderNo("G2019052011380560348");
        ServiceMessage msg = new ServiceMessage("goodsorder.paysuccess", req);
        result = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }

    @Test
    public void refundGoodsOrder() {
        Response result = new Response();
        GoodsOrderRequest req = new GoodsOrderRequest();
        req.setClientEnum(ClientEnum.PC);
        req.setOrderNo("G2019052716290053506");
        req.setUserId(4);
        ServiceMessage msg = new ServiceMessage("goodsorder.refund", req);
        result = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }


    @Test
    public void queryOrderList() {
        GoodsOrderListResponse result = new GoodsOrderListResponse();
        GoodsOrderListRequest req = new GoodsOrderListRequest();
        req.setStatus(1);
//        req.setOrder(order);
        ServiceMessage msg = new ServiceMessage("goodsorder.list", req);
        result = (GoodsOrderListResponse) TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.getList());
        System.out.println(result.toString());

    }


    @Test
    public void addPastureOrder() {
        Response result = new Response();
        InvestmentOrderRequest req = new InvestmentOrderRequest();
        req.setProjectId(17);
        req.setClientEnum(ClientEnum.PC);
//        req.setBlancePayMoney(BigDecimalUtil.parse(300));
//        req.setHongbaoId(36);
        req.setUserId(4);
        ServiceMessage msg = new ServiceMessage("investorder.add", req);
        result = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }


    @Test
    public void submitPastureOrder() {
        Response result = new Response();
        InvestmentOrderRequest req = new InvestmentOrderRequest();
        req.setProjectId(17);
        req.setClientEnum(ClientEnum.PC);
        req.setBlancePayMoney(BigDecimalUtil.parse(200));
        req.setOrderNo("N2019052315590577279");
//        req.setRechargeMoney(BigDecimalUtil.parse(300));
//        req.setHongbaoId(36);
        req.setUserId(4);
        ServiceMessage msg = new ServiceMessage("investorder.submit", req);
        result = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }


    @Test
    public void pastureOrderPaySucess() {
        Response result = new Response();
        InvestmentOrderRequest req = new InvestmentOrderRequest();

        req.setOrderNo("N2019052011070038151");
        req.setClientEnum(ClientEnum.PC);

        ServiceMessage msg = new ServiceMessage("investorder.paysuccess", req);
        result = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }


    @Test
    public void cancelPastureOrder() {
        Response result = new Response();

        InvestmentOrderRequest req = new InvestmentOrderRequest();
        req.setOrderNo("N2019052118050381659");
        req.setUserId(4);
        req.setClientEnum(ClientEnum.PC);
        ServiceMessage msg = new ServiceMessage("investorder.cancel", req);
        Response response = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }

    @Test
    public void cancelGoodsOrder() {
        Response result = new Response();

        GoodsOrderRequest req = new GoodsOrderRequest();
        req.setOrderNo("G19122311410138709");
        req.setUserId(546848);
        req.setClientEnum(ClientEnum.PC);
        ServiceMessage msg = new ServiceMessage("goodsorder.cancel", req);
        Response response = TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }
    
    @Test
    public void investmentOrder() {
        InvestmentOrderListRequest request = new InvestmentOrderListRequest();
        ListResponse response = new ListResponse();
        request.setLimitEnd(10);
        request.setLimitStart((1 - 1) * 10);
        //request.setOrderStatus(orderStatus);
        request.setOrderStatus(0);
        request.setUserId(23);
        ServiceMessage msg = new ServiceMessage("investorder.list", request);
        response = (ListResponse) TransactionClient.getInstance().setServiceMessage(msg).send();
        System.out.println(response.toString());
        if(response.isSuccess()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for(int i=0;i<response.getList().size();i++) {
                if (response.getList().get(i).get("repay_unit").equals(RepayUnitEnum.MONTH.getFeatureName())) {
                    response.getList().get(i).put("limitDayStr",
                        response.getList().get(i).get("limit_days") + RepayUnitEnum.MONTH.getDescription());
                } else if (response.getList().get(i).get("repay_unit").equals(RepayUnitEnum.DAY.getFeatureName())) {
                    response.getList().get(i).put("limitDayStr", response.getList().get(i).get("limit_days") + RepayUnitEnum.DAY.getDescription());
                }
                response.getList().get(i).put("path", ClientConstants.ALIBABA_PATH + "upload/"+response.getList().get(i).get("path"));
                long lockTime = 0;
                String createDate = null == response.getList().get(i).get("create_date") ? null
                    : String.valueOf(response.getList().get(i).get("create_date"));
                //createDate = DateTimeUtil.secondToDate(Long.valueOf(response.getList().get(i).get("create_date")+""), DateTimeUtil.allPattern);
                if (null != createDate) {
                    String date = DateUtil.addtime(createDate, "30");
                    lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern), date,
                        DateTimeUtil.allPattern);
                }
                response.getList().get(i).put("lockTime", lockTime);
                response.getList().get(i).put("createDate", createDate);
            }
            System.out.println(response.getList().toString());
        } else {
            
        }
    }

}
