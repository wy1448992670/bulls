package com.goochou.p2b.model.pay.fuiou;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "FM")
public class QuickPayOrderReqData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2245782096246011242L;

	private String mchntcd;

    private String orderId;

	private String sign;

    public String getMchntcd() {
        return mchntcd;
    }
    @XmlElement(name = "MchntCd")
    public void setMchntcd(String mchntcd) {
        this.mchntcd = mchntcd;
    }

    public String getOrderId() {
		return orderId;
	}
    @XmlElement(name = "OrderId")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

    public String getSign() {
        return sign;
    }
    @XmlElement(name = "Sign")
    public void setSign(String sign) {
        this.sign = sign;
    }
}
