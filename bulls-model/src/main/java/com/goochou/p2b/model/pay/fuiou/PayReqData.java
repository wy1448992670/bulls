package com.goochou.p2b.model.pay.fuiou;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "REQUEST")
public class PayReqData {
    private String version;
    private String userip;
    private String mchntcd;
    private String type;
    private String mchntorderid;
    private String userid;
    private String orderid;
    private String bankcard;
    private String mobile;
    private String vercd;
    private String rem1;
    private String rem2;
    private String rem3;
    private String signtp;
    private String sign;
    private String signpay;

    public String getVersion() {
        return version;
    }

    @XmlElement(name = "VERSION")
    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserip() {
        return userip;
    }

    @XmlElement(name = "USERIP")
    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getMchntcd() {
        return mchntcd;
    }

    @XmlElement(name = "MCHNTCD")
    public void setMchntcd(String mchntcd) {
        this.mchntcd = mchntcd;
    }

    public String getType() {
        return type;
    }

    @XmlElement(name = "TYPE")
    public void setType(String type) {
        this.type = type;
    }

    public String getMchntorderid() {
        return mchntorderid;
    }

    @XmlElement(name = "MCHNTORDERID")
    public void setMchntorderid(String mchntorderid) {
        this.mchntorderid = mchntorderid;
    }

    public String getUserid() {
        return userid;
    }

    @XmlElement(name = "USERID")
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderid() {
        return orderid;
    }

    @XmlElement(name = "ORDERID")
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getBankcard() {
        return bankcard;
    }

    @XmlElement(name = "BANKCARD")
    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getMobile() {
        return mobile;
    }

    @XmlElement(name = "MOBILE")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVercd() {
        return vercd;
    }

    @XmlElement(name = "VERCD")
    public void setVercd(String vercd) {
        this.vercd = vercd;
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
}
