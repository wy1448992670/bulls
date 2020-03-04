package com.goochou.p2b.model.pay.fuiou;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.utils.Md5Util;
import com.goochou.p2b.utils.XmlBeanUtils;

@XmlRootElement(name = "ORDER")
public class CheckIdReqData
{
	private String version;
	private String typeId;
	private String mchntCd;
	private String mchntOrderid;
	private String name;
	private String idNo;
	private String sign;
	private String rem1;
	private String rem2;
	private String rem3;

	public String getVersion()
	{
		return version;
	}

	@XmlElement(name = "VERSION")
	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getTypeId()
	{
		return typeId;
	}

	@XmlElement(name = "TYPEID")
	public void setTypeId(String typeId)
	{
		this.typeId = typeId;
	}

	public String getMchntCd()
	{
		return mchntCd;
	}

	@XmlElement(name = "MCHNTCD")
	public void setMchntCd(String mchntCd)
	{
		this.mchntCd = mchntCd;
	}

	public String getMchntOrderid()
	{
		return mchntOrderid;
	}

	@XmlElement(name = "MCHNTORDERID")
	public void setMchntOrderid(String mchntOrderid)
	{
		this.mchntOrderid = mchntOrderid;
	}

	public String getName()
	{
		return name;
	}

	@XmlElement(name = "NAME")
	public void setName(String name)
	{
		this.name = name;
	}

	public String getIdNo()
	{
		return idNo;
	}

	@XmlElement(name = "IDNO")
	public void setIdNo(String idNo)
	{
		this.idNo = idNo;
	}
	
	public String getSign()
	{
		return sign;
	}

	@XmlElement(name = "SIGN")
	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public String getRem1()
	{
		return rem1;
	}

	@XmlElement(name = "REM1")
	public void setRem1(String rem1)
	{
		this.rem1 = rem1;
	}

	public String getRem2()
	{
		return rem2;
	}

	@XmlElement(name = "REM2")
	public void setRem2(String rem2)
	{
		this.rem2 = rem2;
	}

	public String getRem3()
	{
		return rem3;
	}

	@XmlElement(name = "REM3")
	public void setRem3(String rem3)
	{
		this.rem3 = rem3;
	}

	public String buildXml() throws JAXBException
	{
		this.setMchntCd(PayConstants.FUIOU_QUICK_MCHNT_CD);
		StringBuffer temp = new StringBuffer();
		temp.append(this.version).append("|").append(this.typeId).append("|").append(this.mchntCd).append("|").append(this.mchntOrderid).append("|")
				.append(this.name).append("|").append(this.idNo).append("|").append(PayConstants.FUIOU_QUICK_MCHNT_KEY);
		System.out.println("验签明文："+temp.toString());
		this.sign = Md5Util.toMD5(temp.toString());
		return XmlBeanUtils.convertBean2Xml(this, "UTF-8",true);
	}
}
