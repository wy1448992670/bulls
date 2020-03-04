package com.goochou.p2b.hessian;

import java.io.Serializable;

import com.goochou.p2b.annotationin.FieldMeta;

public class Response implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1184133421153294345L;
	
	@FieldMeta(description = "是否成功")
	protected boolean success = false;
	@FieldMeta(description = "错误编码")
	protected String errorCode;
	@FieldMeta(description = "错误描述")
	protected String errorMsg;
	@FieldMeta(description = "描述")
	protected String msg;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Response() {
		super();
	}
	public Response(boolean success, String errorCode, String msg) {
		super();
		this.success = success;
		this.errorCode = errorCode;
		this.msg = msg;
	}
	public Response(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Response [success=" + success + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", msg=" + msg
				+ "]";
	}
}
