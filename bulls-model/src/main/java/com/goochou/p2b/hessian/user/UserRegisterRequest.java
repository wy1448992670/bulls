package com.goochou.p2b.hessian.user;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class UserRegisterRequest extends Request{
	private static final long serialVersionUID = 5114347663950152180L;
	@FieldMeta(description = "手机号", have = true)
	private String phone;
	@FieldMeta(description = "密码", have = true)
	private String password;
	@FieldMeta(description = "用户名")
	private String username;
	@FieldMeta(description = "头像")
	private Integer uploadId;
	@FieldMeta(description = "注册IP")
	private String registerIp;
	@FieldMeta(description = "终端")
	private String client;
	@FieldMeta(description = "邀请码")
	private String inviteByCode;
	@FieldMeta(description = "注册市场来源")
	private String dataSource;
	@FieldMeta(description = "注册渠道")
	private String channelNo;
	
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getUploadId() {
		return uploadId;
	}
	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getInviteByCode() {
		return inviteByCode;
	}
	public void setInviteByCode(String inviteByCode) {
		this.inviteByCode = inviteByCode;
	}
	
}
