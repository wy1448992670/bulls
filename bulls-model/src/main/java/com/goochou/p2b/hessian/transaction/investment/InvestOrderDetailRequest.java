package com.goochou.p2b.hessian.transaction.investment;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class InvestOrderDetailRequest extends Request{
	private static final long serialVersionUID = -7112427500436264895L;
	
	@FieldMeta(description = "投资ID", have = true)
	private Integer investId;

	/**
	 * @return the investId
	 */
	public Integer getInvestId() {
		return investId;
	}
	/**
	 * @param investId the investId to set
	 */
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
}
