package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class YeePayRequest extends Request {

    /**
     *
     */
    private static final long serialVersionUID = 472553264305806827L;
    @FieldMeta(description = "订单号", have=true)
	private String orderNo;
	@FieldMeta(description = "金额（元）", have=true)
	private String amount;
	@FieldMeta(description = "用户ID", have=true)
	private String userId;
	@FieldMeta(description = "返回商户", have=true)
    private String payResult;
	@FieldMeta(description = "商品名称", have=true)
    private String subject;
	@FieldMeta(description = "交易流水号", have=true)
    private String outOrderNo;
	@FieldMeta(description = "手机号", have=true)
    private String phone;
	@FieldMeta(description = "姓名", have=true)
    private String trueName;
	@FieldMeta(description = "身份证", have=true)
    private String identityCard;
	

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
    public String getOutOrderNo() {
        return outOrderNo;
    }
    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public YeePayRequest() {
        
    }
    public YeePayRequest(String orderNo, String amount, String userId, String payResult, String subject, String phone, String trueName, String identityCard) {
        super();
        this.orderNo = orderNo;
        this.amount = amount;
        this.userId = userId;
        this.payResult = payResult;
        this.subject = subject;
        this.phone = phone;
        this.trueName = trueName;
        this.identityCard = identityCard;
    }
    
}
