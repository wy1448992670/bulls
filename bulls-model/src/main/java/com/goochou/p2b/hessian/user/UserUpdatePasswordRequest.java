package com.goochou.p2b.hessian.user;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class UserUpdatePasswordRequest extends Request{
	private static final long serialVersionUID = -6682603920241197678L;
	@FieldMeta(description = "newPassword", have = true)
	private String newPassword;//新密码
	@FieldMeta(description = "confirmPassoword", have = true)
	private String confirmPassoword;//确认密码
	@FieldMeta(description = "phone", have = true)
	private String phone;//手机号
	
	 
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassoword() {
		return confirmPassoword;
	}
	public void setConfirmPassoword(String confirmPassoword) {
		this.confirmPassoword = confirmPassoword;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
