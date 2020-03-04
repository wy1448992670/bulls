package com.goochou.p2b.exception;

/**
 * 
 * Created on 2016年4月7日
 * <p>Title:       中投融线上交易平台_[展示系统]_[系统错误提示]]/p>
 * <p>Description: [MessageSource处理消息Key]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * <p>Company:     中投融线上交易平台</p>
 * <p>Department:  研发中心</p>
 * @author         [李旭东] [lixudong@ztrong.com]
 * @version        1.0
 */
public class MessageException extends Exception { 

	private static final long serialVersionUID = 409580980756391375L;
	private String messageKey;
	
	public MessageException(String messageKey){
		this.messageKey = messageKey;
	}

	@Override
	public String getMessage(){
		return messageKey;
	}
}
