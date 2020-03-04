package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class QueryFuiouPayRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6257257236564317514L;
	@FieldMeta(description = "平台支付订单号", have=true)
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
