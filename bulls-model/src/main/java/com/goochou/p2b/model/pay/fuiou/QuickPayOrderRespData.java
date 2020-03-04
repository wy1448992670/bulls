package com.goochou.p2b.model.pay.fuiou;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FM")
public class QuickPayOrderRespData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5559800168320019884L;

	private String rcd;

    private String rdesc;

	private String sign;

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

	@XmlElement(name = "Sign")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

    
}
