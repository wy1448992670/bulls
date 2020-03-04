package com.goochou.p2b.model.pay.fuiou;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.utils.Md5Util;
import com.goochou.p2b.utils.XmlBeanUtils;

@XmlRootElement(name="FM")
public class CheckCardReqData
{
	private String MchntCd= PayConstants.FUIOU_QUICK_MCHNT_CD;
	
	private String Ver="";
	
	private String OSsn="";
	
	private String Ono="";
	
	private String Onm="";
	
	private String OCerTp="";
	
	private String OCerNo="";
	
	private String Mno="";
	
	private String Sign = "";

	public String getMchntCd()
	{
		return MchntCd;
	}

	@XmlElement(name="MchntCd")
	public void setMchntCd(String mchntCd)
	{
		MchntCd = mchntCd;
	}


	public String getVer()
	{
		return Ver;
	}

	@XmlElement(name="Ver")
	public void setVer(String ver)
	{
		Ver = ver;
	}


	public String getOSsn()
	{
		return OSsn;
	}

	@XmlElement(name="OSsn")
	public void setOSsn(String oSsn)
	{
		OSsn = oSsn;
	}


	public String getOno()
	{
		return Ono;
	}

	@XmlElement(name="Ono")
	public void setOno(String ono)
	{
		Ono = ono;
	}


	public String getOnm()
	{
		return Onm;
	}

	@XmlElement(name="Onm")
	public void setOnm(String onm)
	{
		Onm = onm;
	}


	public String getOCerTp()
	{
		return OCerTp;
	}

	@XmlElement(name="OCerTp")
	public void setOCerTp(String oCerTp)
	{
		OCerTp = oCerTp;
	}


	public String getOCerNo()
	{
		return OCerNo;
	}

	@XmlElement(name="OCerNo")
	public void setOCerNo(String oCerNo)
	{
		OCerNo = oCerNo;
	}


	public String getMno()
	{
		return Mno;
	}

	@XmlElement(name="Mno")
	public void setMno(String mno)
	{
		Mno = mno;
	}


	public String getSign()
	{
		return Sign;
	}

	@XmlElement(name="Sign")
	public void setSign(String sign)
	{
		Sign = sign;
	}

	public String buildReqXml() throws JAXBException {
		StringBuffer temp = new StringBuffer();
		temp.append(this.MchntCd).append("|").append(this.Ver).append("|").append(this.OSsn).append("|").append(this.Ono).append("|").append(this.OCerTp).append("|").append(this.OCerNo).append("|").append(PayConstants.FUIOU_QUICK_MCHNT_KEY);
		System.out.println("签名明文："+temp.toString());
		this.Sign = Md5Util.toMD5(temp.toString());
		return XmlBeanUtils.convertBean2Xml(this, "UTF-8",false);
	}
}
