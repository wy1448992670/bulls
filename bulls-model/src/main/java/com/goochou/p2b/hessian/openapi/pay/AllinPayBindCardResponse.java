package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;


public class AllinPayBindCardResponse extends Response {

    /**
     *
     */
    private static final long serialVersionUID = 3813788921962958777L;
    @FieldMeta(description = "协议号")
    private String agreeId;
    @FieldMeta(description = "通联银行编码")
    private String bankCode;
    @FieldMeta(description = "通联银行名称")
    private String bankName;
    @FieldMeta(description = "交易透传信息")
    private String thpInfo;
    public String getThpInfo() {
        return thpInfo;
    }
    public void setThpInfo(String thpInfo) {
        this.thpInfo = thpInfo;
    }
    public String getAgreeId() {
        return agreeId;
    }
    public void setAgreeId(String agreeId) {
        this.agreeId = agreeId;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    @Override
    public String toString() {
        return "AllinPayBindCardResponse [agreeId=" + agreeId + ", bankCode=" + bankCode + ", bankName=" + bankName
            + ", thpInfo=" + thpInfo + ", success=" + success + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg
            + ", msg=" + msg + "]";
    }
}
