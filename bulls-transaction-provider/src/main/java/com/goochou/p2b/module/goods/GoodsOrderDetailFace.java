package com.goochou.p2b.module.goods;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderDetailRequest;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderResponse;
import com.goochou.p2b.model.Assess;
import com.goochou.p2b.model.AssessImgs;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.service.AssessService;
import com.goochou.p2b.service.GoodsOrderService;

/**
 * 
 * @author wangyun
 *
 */
@Service
public class GoodsOrderDetailFace implements HessianInterface {

    @Autowired
    private GoodsOrderService goodsOrderService;
    
    @Autowired
    private AssessService assessService;
    

    @Override
    public GoodsOrderResponse execute(ServiceMessage msg) {

    	GoodsOrderDetailRequest goodsReq = (GoodsOrderDetailRequest) msg.getReq();
    	GoodsOrderResponse response = new GoodsOrderResponse();
        try {
         
        	String orderNo = goodsReq.getOrderNo();
        	 
        	GoodsOrder order = goodsOrderService.queryGoodsOrderDetail(orderNo);
        	
        	if(order == null) {
        		response.setSuccess(false);
        		response.setErrorMsg("订单不存在");
        		return response;
        	}
        	if(order.getUserId().intValue() != goodsReq.getUserId().intValue()) {
        		response.setSuccess(false);
        		response.setErrorMsg("查询用户订单参数错误");
        		return response;
        	}
        	
        	//查询订单评价
        	List<Map<String, Object>> goodsAssess = new ArrayList<>();
        	//每个商品都有评论,且会有多个评论
    		List<GoodsOrderDetail> detail = order.getGoodsOrderDetail();
    		for (GoodsOrderDetail goodsOrderDetail : detail) {
    			
    			Map<String, Object> map = new HashMap<>();
    			
    			List<Assess> assList = assessService.getAssessByGoodsIdAndOrderId(goodsOrderDetail.getGoodsId(), order.getId());
    			StringBuffer path = new StringBuffer();
    			for (Assess ass : assList) {
					List<AssessImgs> imgs = ass.getAssessImgs();
					
					Iterator<AssessImgs> iterator = imgs.iterator();
			        while(iterator.hasNext()){
			        	AssessImgs img = iterator.next();
			        	path.append(img.getUpload() != null ? img.getUpload().getCdnPath():null);
						path.append(",");
						iterator.remove();
			        }
			        ass.setChilden(null);//不显示嵌套查询的信息
				}
    			
    			map.put("path", path.length() > 0 ? path.deleteCharAt(path.length() - 1) : null); 
    			map.put("assList", assList);
    			goodsAssess.add(map);
			}
        	response.setAssessList(goodsAssess);
            response.setSuccess(true);
            response.setOrder(order);
            
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
