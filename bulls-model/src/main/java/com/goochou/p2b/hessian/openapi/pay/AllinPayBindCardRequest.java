package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class AllinPayBindCardRequest extends Request {

	/**
     *
     */
    private static final long serialVersionUID = -6841753148871702452L;
    
    
    @FieldMeta(description = "userId", have=true)
	private String userId;
	@FieldMeta(description = "银行预留手机号", have=true)
	private String phoneNo;
	@FieldMeta(description = "身份证", have=true)
	private String identityCard;
	@FieldMeta(description = "真实姓名", have=true)
	private String trueName;
	@FieldMeta(description = "银行卡号", have=true)
	private String bankCard;
	@FieldMeta(description = "验证码", have=false)
    private String code;
	@FieldMeta(description = "交易透传信息", have=false)
    private String thpInfo;
    public String getThpInfo() {
        return thpInfo;
    }
    public void setThpInfo(String thpInfo) {
        this.thpInfo = thpInfo;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getIdentityCard() {
        return identityCard;
    }
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }
    public String getTrueName() {
        return trueName;
    }
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
    public String getBankCard() {
        return bankCard;
    }
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }
	
}
