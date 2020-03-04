package com.goochou.p2b.hessian.transaction.goods;


import java.util.List;
import java.util.Map;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.goods.GoodsOrder;

public class GoodsOrderResponse extends Response{
	private static final long serialVersionUID = 1L;
	
	@FieldMeta(description = "商品订单列表", have = true)
	private  GoodsOrder order;
	 
	private List<Map<String, Object>>  assessList;
	 
	public GoodsOrder getOrder() {
		return order;
	}

	public void setOrder(GoodsOrder order) {
		this.order = order;
	}

	public List<Map<String, Object>> getAssessList() {
		return assessList;
	}

	public void setAssessList(List<Map<String, Object>> assessList) {
		this.assessList = assessList;
	}
	
}
