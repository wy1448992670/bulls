package com.goochou.p2b.model.pay.allinpay;
/**
 * @Description 程序异常 参数异常等等
 * @Author meixf@allinpay.com
 * @Date 2018年5月23日
 **/
public class AIPGException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String errcode;

	public AIPGException() {
		super();
	}

	public AIPGException(String errmsg) {
		super(errmsg);
	}

	public AIPGException(String errcode, String errmsg) {
		super(errmsg);
		this.errcode = errcode;
	}

	public AIPGException(int errcode, String msg) {
		super(msg);
		this.errcode = String.valueOf(errcode);
	}

	public AIPGException(String msg, Exception ex) {
		super(msg, ex);
	}

	public AIPGException(Throwable rootCause) {
		super(rootCause);
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
}
