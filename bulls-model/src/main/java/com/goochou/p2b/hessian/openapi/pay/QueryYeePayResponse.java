package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;


public class QueryYeePayResponse extends Response {


	/**
     *
     */
    private static final long serialVersionUID = -4177645794505197867L;
    @FieldMeta(description = "返回码[PROCESSING:支付中 SUCCESSED:支付完成 FAILED:支付失败]", have=true)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
