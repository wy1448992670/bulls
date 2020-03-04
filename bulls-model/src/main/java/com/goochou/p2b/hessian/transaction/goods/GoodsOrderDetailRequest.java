package com.goochou.p2b.hessian.transaction.goods;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class GoodsOrderDetailRequest extends Request{
	private static final long serialVersionUID = -7112427500436264895L;
	
	@FieldMeta(description = "商品订单号", have = true)
	private String orderNo;
	@FieldMeta(description = "用户ID", have = true)
	private Integer userId;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	 
	
}
