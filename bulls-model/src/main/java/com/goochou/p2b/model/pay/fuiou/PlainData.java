package com.goochou.p2b.model.pay.fuiou;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plain")
public class PlainData {
    private String orderPayCode;
    private String orderPayError;
    private String orderId;
    private String orderSt;
    private String fySsn;
    private String resv1;
	public String getOrderPayCode() {
		return orderPayCode;
	}
	@XmlElement(name = "order_pay_code")
	public void setOrderPayCode(String orderPayCode) {
		this.orderPayCode = orderPayCode;
	}
	public String getOrderPayError() {
		return orderPayError;
	}
	@XmlElement(name = "order_pay_error")
	public void setOrderPayError(String orderPayError) {
		this.orderPayError = orderPayError;
	}
	public String getOrderId() {
		return orderId;
	}
	@XmlElement(name = "order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderSt() {
		return orderSt;
	}
	@XmlElement(name = "order_st")
	public void setOrderSt(String orderSt) {
		this.orderSt = orderSt;
	}
	public String getFySsn() {
		return fySsn;
	}
	@XmlElement(name = "fy_ssn")
	public void setFySsn(String fySsn) {
		this.fySsn = fySsn;
	}
	public String getResv1() {
		return resv1;
	}
	@XmlElement(name = "resv1")
	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}
    
}
