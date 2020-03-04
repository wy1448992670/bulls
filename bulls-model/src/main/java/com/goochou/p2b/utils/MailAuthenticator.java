package com.goochou.p2b.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * Created on 2015-3-23
 * <p>Title:       中投融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [创建密码验证器]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     中投融线上交易平台</p>
 * <p>Department:  研发中心</p>
 * @author         [武勇吉] [293563839@qq.com]
 * @version        1.0
 */
public class MailAuthenticator extends Authenticator {
	
	String userName = null;
	String password = null;

	public MailAuthenticator() {
	}

	public MailAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
