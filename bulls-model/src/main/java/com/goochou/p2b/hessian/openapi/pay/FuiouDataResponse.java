package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.pay.fuiou.OrderRespData;


public class FuiouDataResponse extends Response {
	
	
	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -3077361439321845087L;
	
	@FieldMeta(description = "OrderRespData", have=true)
	private OrderRespData orderRespData;

	public OrderRespData getOrderRespData() {
		return orderRespData;
	}

	public void setOrderRespData(OrderRespData orderRespData) {
		this.orderRespData = orderRespData;
	}
	
	
}
