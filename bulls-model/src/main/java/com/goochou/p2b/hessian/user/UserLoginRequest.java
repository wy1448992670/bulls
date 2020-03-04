package com.goochou.p2b.hessian.user;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class UserLoginRequest extends Request{
	private static final long serialVersionUID = -6682603920241197678L;
	@FieldMeta(description = "账号", have = true)
	private String account;
	private String password;//手机号登陆不需要密码
	@FieldMeta(description = "ip", have = true)
	private String ip;
	@FieldMeta(description = "版本号", have = true)
	private String appVersion;
	@FieldMeta(description = "登陆方式(1:密码登录 2:手机号登录)", have = true)
	private Integer LoginType;
	
	private String deviceToken;
	private String client;
	private String equipment;
	
	
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public Integer getLoginType() {
		return LoginType;
	}
	public void setLoginType(Integer loginType) {
		LoginType = loginType;
	}
	
}
