package com.goochou.p2b.constant.pay;

import java.io.Serializable;
import java.util.Map;

/**
 * 支付结果通知
 * @author 叶东平
 * @date 2017-09-16
 */
public class PayResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 713726623062516647L;
	
	public PayResult(){};
	
	private String code;
	
	private String message;
	
	private String errorMessage;
	
	public Map<?, ?> retInfo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<?, ?> getRetInfo() {
		return retInfo;
	}

	public void setRetInfo(Map<?, ?> retInfo) {
		this.retInfo = retInfo;
	}
	
}
