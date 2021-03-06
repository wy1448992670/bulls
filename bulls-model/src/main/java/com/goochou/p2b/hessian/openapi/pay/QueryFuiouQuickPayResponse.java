package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;


public class QueryFuiouQuickPayResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3363061944472953059L;
	
	@FieldMeta(description = "返回码[0：找不到订单，1：支付成功，2：支付失败，3：支付中]", have=true)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
