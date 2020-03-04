package com.goochou.p2b.hessian.transaction.investment;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class InterestListRequest extends Request{
	private static final long serialVersionUID = -7112427500436264895L;
	
	@FieldMeta(description = "投资ID", have = true)
	private Integer investId;
	@FieldMeta(description = "用户ID", have = true)
	private Integer userId;
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
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
