package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class QueryFuiouQuickPayRequest extends Request {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2912538865180410359L;
	
	@FieldMeta(description = "富友支付订单号", have=true)
	private String outOrderNo;

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
	
	
	
}
