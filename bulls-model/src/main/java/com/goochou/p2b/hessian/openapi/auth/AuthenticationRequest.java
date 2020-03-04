package com.goochou.p2b.hessian.openapi.auth;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;


public class AuthenticationRequest extends Request {

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 3151520386469017999L;
	@FieldMeta(description = "姓名", have=true)
	private String username;
	@FieldMeta(description = "身份号", have=true)
	private String idNo;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
}
