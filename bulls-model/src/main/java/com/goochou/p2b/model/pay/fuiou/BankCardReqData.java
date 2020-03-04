package com.goochou.p2b.model.pay.fuiou;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "FM")
public class BankCardReqData {
    private String mchntcd;

    private String ono;

    private String sign;

    public String getMchntcd() {
        return mchntcd;
    }
    @XmlElement(name = "MchntCd")
    public void setMchntcd(String mchntcd) {
        this.mchntcd = mchntcd;
    }

    public String getOno() {
        return ono;
    }
    @XmlElement(name = "Ono")
    public void setOno(String ono) {
        this.ono = ono;
    }

    public String getSign() {
        return sign;
    }
    @XmlElement(name = "Sign")
    public void setSign(String sign) {
        this.sign = sign;
    }
}
