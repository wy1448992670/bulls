package com.goochou.p2b.model.pay.fuiou;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FM")
public class BankCardResqData implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8633543507484683689L;
    private String rcd;
    private String rdesc;
    private String ctp;
    private String cnm;
    private String inscd;
    private String sign;

    private String ossn;
    private String ver;
    private String cardno;

    public String getRcd() {
        return rcd;
    }
    @XmlElement(name = "Rcd")
    public void setRcd(String rcd) {
        this.rcd = rcd;
    }

    public String getRdesc() {
        return rdesc;
    }
    @XmlElement(name = "RDesc")
    public void setRdesc(String rdesc) {
        this.rdesc = rdesc;
    }

    public String getCtp() {
        return ctp;
    }
    @XmlElement(name = "Ctp")
    public void setCtp(String ctp) {
        this.ctp = ctp;
    }

    public String getCnm() {
        return cnm;
    }
    @XmlElement(name = "Cnm")
    public void setCnm(String cnm) {
        this.cnm = cnm;
    }

    public String getInscd() {
        return inscd;
    }
    @XmlElement(name = "InsCd")
    public void setInscd(String inscd) {
        this.inscd = inscd;
    }

    public String getSign() {
        return sign;
    }
    @XmlElement(name = "Sign")
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOssn() {
        return ossn;
    }
    @XmlElement(name = "OSsn")
    public void setOssn(String ossn) {
        this.ossn = ossn;
    }

    public String getVer() {
        return ver;
    }
    @XmlElement(name = "Ver")
    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getCardno() {
        return cardno;
    }
    @XmlElement(name = "CardNo")
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
}
