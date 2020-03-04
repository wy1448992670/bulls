package com.goochou.p2b.hessian.transaction.goods;

import java.util.Date;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.model.goods.GoodsOrder;

public class GoodsOrderListRequest extends Request{
	private static final long serialVersionUID = -306727988961003094L;
	@FieldMeta(description = "真实姓名", have = true)
	private String trueName;
	@FieldMeta(description = "关键字", have = true)
	private String keyword;
	@FieldMeta(description = "状态", have = true)
	private Integer status;
	@FieldMeta(description = "开始时间", have = true)
	private Date startTime;
	@FieldMeta(description = "结束时间", have = true)
	private Date endTime;
	@FieldMeta(description = "开始行", have = true)
	private Integer limitStart;
	@FieldMeta(description = "结束行", have = true)
	private Integer limitEnd;
	@FieldMeta(description = "用户ID", have = true)
	private Integer userId;
	@FieldMeta(description = "商品ID", have = true)
	private Integer goodsId;
	
	
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	 
}
