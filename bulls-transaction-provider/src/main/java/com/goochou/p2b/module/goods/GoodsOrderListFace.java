package com.goochou.p2b.module.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListRequest;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListResponse;
import com.goochou.p2b.model.GoodsPicture;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.GoodsPictureService;

/**
 * 
 * @author wangyun
 *
 */
@Service
public class GoodsOrderListFace implements HessianInterface {

    @Autowired
    private GoodsOrderService goodsOrderService;
    private final static String picUrl = ClientConstants.ALIBABA_PATH + "upload/";
    @Override
    public GoodsOrderListResponse execute(ServiceMessage msg) {

    	GoodsOrderListRequest orderRequest = (GoodsOrderListRequest) msg.getReq();
    	GoodsOrderListResponse orderList = new GoodsOrderListResponse();
        try {
         
			List<GoodsOrder>  list = goodsOrderService.listGoodsOrder(orderRequest);
        	
        	int count = goodsOrderService.countOrder(orderRequest);
        	 
            orderList.setSuccess(true);
            orderList.setList(list);
            orderList.setCount(count);
            
        } catch (Exception e) {
        	e.printStackTrace();
            OpenApiApp.EXCEPTION.exception(msg, e);
            orderList.setSuccess(false);
            orderList.setErrorMsg("出现异常");
        }
        return orderList;
    }

    @Override
    public void before(ServiceMessage msg) {

    }

    @Override
    public void after(ServiceMessage msg) {

    }
}
