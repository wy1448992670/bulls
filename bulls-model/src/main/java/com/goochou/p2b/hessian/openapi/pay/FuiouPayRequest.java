package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class FuiouPayRequest extends Request {

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 3628005017086247544L;
	@FieldMeta(description = "银行卡号", have=true)
	private String cardNo;
	@FieldMeta(description = "内部支付单号", have=true)
	private String orderNo;
	@FieldMeta(description = "单号ID", have=true)
	private String orderId;
	@FieldMeta(description = "signPay", have=true)
	private String signPay;
	@FieldMeta(description = "userId", have=true)
	private String userId;
	@FieldMeta(description = "验证码", have=true)
	private String verifyCode;
	@FieldMeta(description = "ip", have=true)
	private String ip;
	@FieldMeta(description = "phone", have=true)
	private String phone;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSignPay() {
		return signPay;
	}
	public void setSignPay(String signPay) {
		this.signPay = signPay;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public FuiouPayRequest(String cardNo, String orderNo, String orderId, String signPay, String userId,
			String verifyCode, String ip, String phone) {
		super();
		this.cardNo = cardNo;
		this.orderNo = orderNo;
		this.orderId = orderId;
		this.signPay = signPay;
		this.userId = userId;
		this.verifyCode = verifyCode;
		this.ip = ip;
		this.phone = phone;
	}
	
}
