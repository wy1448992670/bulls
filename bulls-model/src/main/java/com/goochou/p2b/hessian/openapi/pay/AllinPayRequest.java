package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class AllinPayRequest extends Request {

	/**
     *
     */
    private static final long serialVersionUID = 7678868219643472102L;
    @FieldMeta(description = "订单号", have=true)
	private String orderNo;
	@FieldMeta(description = "金额（分）", have=true)
	private Long amount;
	@FieldMeta(description = "订单内容", have=true)
	private String subject;
	@FieldMeta(description = "协议号", have=true)
	private String agreeId;
	@FieldMeta(description = "验证码", have=true)
    private String code;
	@FieldMeta(description = "交易透传信息", have=true)
    private String thpInfo;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getThpInfo() {
        return thpInfo;
    }
    public void setThpInfo(String thpInfo) {
        this.thpInfo = thpInfo;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getAgreeId() {
        return agreeId;
    }
    public void setAgreeId(String agreeId) {
        this.agreeId = agreeId;
    }
	
}
