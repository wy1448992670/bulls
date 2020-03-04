package com.goochou.p2b.module.goods;

import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.GoodsOrderService;

/**
 * @Auther: huangsj
 * @Date: 2019/5/13 11:42
 * @Description:
 */
@Service
public class GoodsOrderPaySuccessFace implements HessianInterface {

    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private AssetsService assetsService;

    @Override
    public Response execute(ServiceMessage msg) {

        GoodsOrderRequest request = (GoodsOrderRequest) msg.getReq();
        Response response = new Response();
        response.setSuccess(false);

        try {
            //判断订单是否存在
            GoodsOrder goodsOrder = goodsOrderService.findByOrderNum(request.getOrderNo());
            if (goodsOrder == null) {
                response.setSuccess(false);
                response.setErrorMsg("参数有误");
                return response;
            }

            if (goodsOrder.getState().intValue() != GoodsOrderStatusEnum.PAYING.getCode()) {
                response.setSuccess(false);
                response.setErrorMsg("订单已处理");
                return response;
            }
            
            //获取用户账户
            Assets assets = assetsService.findByuserId(goodsOrder.getUserId());
            if (null == assets) {
                response.setSuccess(false);
                response.setErrorMsg("用户账户异常");
                return response;
            }
            if(goodsOrderService.doPaySuccess(goodsOrder, assets)) {
            	response.setSuccess(true);
            }

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