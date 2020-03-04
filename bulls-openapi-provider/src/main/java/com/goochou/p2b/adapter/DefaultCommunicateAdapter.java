package com.goochou.p2b.adapter;


import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Created on 2014-8-28
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [接口适配器]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class DefaultCommunicateAdapter implements ICommunicateAdapter
{
	private static final Logger logger = Logger.getLogger(DefaultCommunicateAdapter.class);
	private ICommunicator communicator;
	
    private Map<String,ICommunicator> communicatorMap;

    @Override
	public ICommunicator findCommunicatorForRequest(Object request)
	{
		if(request instanceof IStreamableMessage)
		{
			IStreamableMessage msg = (IStreamableMessage)request;
//			String channelName = null;
			String requestType = null;
		
			//平台转发:根据接口名
			try {
				if(msg instanceof StringTeletext == false) {
					logger.error("对外接口传入的参数类型非法."+msg.getClass().getCanonicalName());
					return null;
				}
				
				StringTeletext teletext = (StringTeletext) msg;
//				channelName = teletext.getChannelName();
				requestType = teletext.getRequestType();
//				
//				// 短信处理
//				SmsGalleryEnum smsEnum = SmsGalleryEnum.getValueByName(requestType);
//				if (smsEnum != null) {
//					return this.communicatorMap.get(smsEnum.getFeatureName());
//				}
				// 其它接口处理
				String communicatorName = requestType;
				
				return this.communicatorMap.get(communicatorName);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
		//默认（暂时没有默认）
		return null;
	}
	
	public ICommunicator getCommunicator() {
		return communicator;
	}

	public void setCommunicator(ICommunicator communicator) {
		this.communicator = communicator;
	}

	public Map<String, ICommunicator> getCommunicatorMap() {
		return communicatorMap;
	}

	public void setCommunicatorMap(Map<String, ICommunicator> communicatorMap) {
		this.communicatorMap = communicatorMap;
	}
}
