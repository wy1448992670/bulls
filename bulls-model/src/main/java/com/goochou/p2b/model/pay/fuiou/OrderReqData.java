package com.goochou.p2b.model.pay.fuiou;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author xiaohao@fuiou.com
 */
@XmlRootElement(name = "REQUEST")
public class OrderReqData {
    private String version;

    private String mchntcd;

    private String type;

    private String mchntorderid;

    private String orderId;

    private String userid;

    private String amt;

    private String bankcard;

    private String name;

    private String backurl;

    private String idtype;

    private String idno;

    private String mobile;

    private String cvn;

    private String rem1;

    private String rem2;

    private String rem3;

    private String signtp;

    private String userip;

    private String sign;

    public String getVersion() {
        return version;
    }

    @XmlElement(name = "VERSION")
    public void setVersion(String version) {
        this.version = version;
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

    @XmlElement(name = "ORDERID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserid() {
        return userid;
    }

    @XmlElement(name = "USERID")
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAmt() {
        return amt;
    }

    @XmlElement(name = "AMT")
    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getBankcard() {
        return bankcard;
    }

    @XmlElement(name = "BANKCARD")
    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "NAME")
    public void setName(String name) {
        this.name = name;
    }

    public String getBackurl() {
        return backurl;
    }

    @XmlElement(name = "BACKURL")
    public void setBackurl(String backurl) {
        this.backurl = backurl;
    }

    public String getIdtype() {
        return idtype;
    }

    @XmlElement(name = "IDTYPE")
    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getIdno() {
        return idno;
    }

    @XmlElement(name = "IDNO")
    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMobile() {
        return mobile;
    }

    @XmlElement(name = "MOBILE")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCvn() {
        return cvn;
    }

    @XmlElement(name = "CVN")
    public void setCvn(String cvn) {
        this.cvn = cvn;
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

    public String getUserip() {
        return userip;
    }

    @XmlElement(name = "USERIP")
    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getSign() {
        return sign;
    }

    @XmlElement(name = "SIGN")
    public void setSign(String sign) {
        this.sign = sign;
    }
}
