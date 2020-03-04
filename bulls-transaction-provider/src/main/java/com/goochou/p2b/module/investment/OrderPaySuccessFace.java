package com.goochou.p2b.module.investment;

import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.PastureOrderService;

/**
 * @Auther: huangsj
 * @Date: 2019/5/14 10:37
 * @Description:
 */
@Service
public class OrderPaySuccessFace  implements HessianInterface {

    @Autowired
    private PastureOrderService pastureOrderService;
    @Autowired
    private AssetsService assetsService;

    @Override
    public Response execute(ServiceMessage msg) {

    	InvestmentOrderRequest request = (InvestmentOrderRequest) msg.getReq();
        Response response = new Response();
        response.setSuccess(false);

        try {
            //判断订单是否存在
            Investment order = pastureOrderService.findOrderByNum(request.getOrderNo());
            if (order == null) {
                response.setSuccess(false);
                response.setErrorMsg("参数有误");
                return response;
            }

            if (order.getPayStatus().intValue() != InvestPayStateEnum.PAYING.getCode()){
                response.setSuccess(false);
                response.setErrorMsg("订单已处理");
                return response;
            }
            
            //获取用户账户
            Assets userAccount = assetsService.findByuserId(order.getUserId());
            if (null == userAccount) {
                response.setSuccess(false);
                response.setErrorMsg("用户账户异常");
                return response;
            }
            if(pastureOrderService.doPaySuccess(order, userAccount)) {
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