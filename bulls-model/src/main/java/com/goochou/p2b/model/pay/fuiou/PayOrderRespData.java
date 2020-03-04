package com.goochou.p2b.model.pay.fuiou;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ap")
public class PayOrderRespData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5559800168320019884L;

	private PlainData plain;

    private String md5;

	public PlainData getPlain() {
		return plain;
	}
	@XmlElement(name = "plain")
	public void setPlain(PlainData plain) {
		this.plain = plain;
	}

	public String getMd5() {
		return md5;
	}
	@XmlElement(name = "md5")
	public void setMd5(String md5) {
		this.md5 = md5;
	}

    
}
