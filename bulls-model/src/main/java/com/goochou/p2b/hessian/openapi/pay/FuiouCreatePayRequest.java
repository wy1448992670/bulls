package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class FuiouCreatePayRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8845540054737386658L;

	@FieldMeta(description = "userId", have=true)
	private String userId;
	@FieldMeta(description = "金额（分）", have=true)
	private Long amount;
	@FieldMeta(description = "银行卡号", have=true)
	private String cardNo;
	@FieldMeta(description = "真实姓名", have=true)
	private String trueName;
	@FieldMeta(description = "证件号", have=true)
	private String identityCard;
	@FieldMeta(description = "手机号", have=true)
	private String phone;
	@FieldMeta(description = "ip", have=true)
	private String ip;
	@FieldMeta(description = "内部支付单号", have=true)
	private String orderNo;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public FuiouCreatePayRequest(String userId, Long amount, String cardNo, String trueName, String identityCard,
			String phone, String ip, String orderNo) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.cardNo = cardNo;
		this.trueName = trueName;
		this.identityCard = identityCard;
		this.phone = phone;
		this.ip = ip;
		this.orderNo = orderNo;
	}
	
	
}
