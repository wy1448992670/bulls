package com.goochou.p2b.service;

import java.util.List;


import com.goochou.p2b.dao.MessagePushMapper;
import com.goochou.p2b.model.BaseResult;
import com.goochou.p2b.model.MessagePush;

public interface MessagePushService {
	
	public BaseResult messagePush(MessagePush messagePush);
	
	public List<MessagePush> queryList(String keyword, Integer status, Integer start, Integer limit);
	
	public Integer queryCount(String keyword,Integer Status);
	
	public MessagePush queryMessagePush(Integer id);
	
	public BaseResult searchMessagePush(MessagePush messagePush);
	
	public boolean addMessagePush(MessagePush messagePush);
	
	public boolean updateMessagePush(MessagePush messagePush);
	 
	MessagePushMapper getMessagePushMapper();
	
	public void addMessagePush(List<String> list,MessagePush messagePush, Integer uploadType) throws Exception;
	
	/**
	 * @date 2019年8月19日
	 * @author wangyun
	 * @time 下午4:04:47
	 * @Description 定时任务跑批
	 *
	 */
	public void doMessagePush();
}
