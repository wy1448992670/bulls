package com.goochou.p2b.hessian.transaction.investment;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class InvestmentOrderListRequest extends Request{
	private static final long serialVersionUID = -9105266467586813493L;
	
	@FieldMeta(description = "认购编号", have = true)
    private String keyword;
    @FieldMeta(description = "认购订单号", have = true)
    private String orderNo;
    @FieldMeta(description = "购买的标的编号", have = true)
    private Integer projectId;
    @FieldMeta(description = "用户编号", have = true)
    private Integer userId;
    @FieldMeta(description = "开始行", have = true)
	private Integer limitStart;
	@FieldMeta(description = "结束行", have = true)
	private Integer limitEnd;
    @FieldMeta(description = "订单状态", have = true)
    private Integer orderStatus;
    @FieldMeta(description = "支付状态", have = true)
    private Integer payStatus;
    @FieldMeta(description = "订单类型", have = true)
    private Integer projectType;
    
    
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}
	public Integer getLimitEnd() {
		return limitEnd;
	}
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
 
}
