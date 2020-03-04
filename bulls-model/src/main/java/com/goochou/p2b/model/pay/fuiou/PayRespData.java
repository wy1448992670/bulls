package com.goochou.p2b.model.pay.fuiou;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RESPONSE")
public class PayRespData {
    private String version = "";
    private String type = "";
    private String responsecode = "";
    private String responsemsg = "";
    private String mchntcd = "";
    private String mchntorderid = "";
    private String bankcard = "";
    private String orderid = "";
    private String userId = "";
    private String amt = "";
    private String rem1 = "";
    private String rem2 = "";
    private String rem3 = "";
    private String signtp = "";
    private String sign = "";
    private String signpay = "";

    public String getVersion() {
        return version;
    }

    @XmlElement(name = "VERSION")
    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    @XmlElement(name = "TYPE")
    public void setType(String type) {
        this.type = type;
    }

    public String getResponsecode() {
        return responsecode;
    }

    @XmlElement(name = "RESPONSECODE")
    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getResponsemsg() {
        return responsemsg;
    }

    @XmlElement(name = "RESPONSEMSG")
    public void setResponsemsg(String responsemsg) {
        this.responsemsg = responsemsg;
    }

    public String getMchntcd() {
        return mchntcd;
    }

    @XmlElement(name = "MCHNTCD")
    public void setMchntcd(String mchntcd) {
        this.mchntcd = mchntcd;
    }

    public String getMchntorderid() {
        return mchntorderid;
    }

    @XmlElement(name = "MCHNTORDERID")
    public void setMchntorderid(String mchntorderid) {
        this.mchntorderid = mchntorderid;
    }

    public String getBankcard() {
        return bankcard;
    }

    @XmlElement(name = "Bankcard")
    public void setUserid(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getOrderid() {
        return orderid;
    }

    @XmlElement(name = "ORDERID")
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @XmlElement(name = "USERID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmt() {
        return amt;
    }

    @XmlElement(name = "AMT")
    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getRem1() {
        return rem1;
    }

    @XmlElement(name = "REM1")
    public void setRem1(String rem1) {
        this.rem1 = rem1;
    }

    public String getRem2() {
        return rem2;
    }

    @XmlElement(name = "REM2")
    public void setRem2(String rem2) {
        this.rem2 = rem2;
    }

    public String getRem3() {
        return rem3;
    }

    @XmlElement(name = "REM3")
    public void setRem3(String rem3) {
        this.rem3 = rem3;
    }

    public String getSigntp() {
        return signtp;
    }

    @XmlElement(name = "SIGNTP")
    public void setSigntp(String signtp) {
        this.signtp = signtp;
    }

    public String getSign() {
        return sign;
    }

    @XmlElement(name = "SIGN")
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignpay() {
        return signpay;
    }

    @XmlElement(name = "SIGNPAY")
    public void setSignpay(String signpay) {
        this.signpay = signpay;
    }

    public void verifySign() {
        StringBuffer temp = new StringBuffer();
        temp.append(this.type).append(this.version).append(this.responsecode).append(this.mchntcd).append(this.mchntorderid).append(this.userId);
    }

    @Override
    public String toString() {
        return "PayRespData{" +
                "version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", responsecode='" + responsecode + '\'' +
                ", responsemsg='" + responsemsg + '\'' +
                ", mchntcd='" + mchntcd + '\'' +
                ", mchntorderid='" + mchntorderid + '\'' +
                ", bankcard='" + bankcard + '\'' +
                ", orderid='" + orderid + '\'' +
                ", userId='" + userId + '\'' +
                ", amt='" + amt + '\'' +
                ", rem1='" + rem1 + '\'' +
                ", rem2='" + rem2 + '\'' +
                ", rem3='" + rem3 + '\'' +
                ", signtp='" + signtp + '\'' +
                ", sign='" + sign + '\'' +
                ", signpay='" + signpay + '\'' +
                '}';
    }
}
