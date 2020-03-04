package com.goochou.p2b.hessian.openapi.withdraw;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class WithdrawRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8845540054737386658L;

	@FieldMeta(description = "订单号", have=true)
	private String orderNo;
	@FieldMeta(description = "银行编码", have=true)
	private String bankNo;
	@FieldMeta(description = "日期（yyyyMMdd）", have=true)
	private String date;
	@FieldMeta(description = "银行卡号", have=true)
	private String cardNo;
	@FieldMeta(description = "真实姓名", have=true)
	private String trueName;
	@FieldMeta(description = "提现金额（元）", have=true)
	private Double amount;
	@FieldMeta(description = "身份证号码", have=true)
    private String idNo;
	public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
