package com.goochou.p2b.adapter.impl;

import java.net.URLDecoder;

import com.goochou.p2b.adapter.CommunicateException;
import com.goochou.p2b.adapter.ICommunicateAdapter;
import com.goochou.p2b.adapter.ICommunicator;
import com.goochou.p2b.adapter.IRemote;
import com.goochou.p2b.adapter.IStreamableMessage;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.hessian.Response;


/**
 * Created on 2014-8-28
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [REMOTE 实现]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class RemoteImpl implements IRemote
{
    private String encoding = Constants.UTF8;
    
    private ICommunicateAdapter adapter;

    public ICommunicateAdapter getAdapter()
    {
        return adapter;
    }

    public void setAdapter(ICommunicateAdapter adapter)
    {
        this.adapter = adapter;
    }


	public Response callRemote2(IStreamableMessage request) throws CommunicateException
	{
		if (this.adapter == null) {
			throw new CommunicateException("没有配置Communicate Filter, 通讯模块无法适配!");
		}

		ICommunicator communicator = this.adapter.findCommunicatorForRequest(request);
		System.out.println("==================== start" + communicator);
		long beginTime, endTime;
		beginTime = System.currentTimeMillis();
		String channelName = "";
		Exception stackTrace = null;
		channelName = request.getChannelName();
		Response ret = null;
		try {
			ret = communicator.httSend2(request.getReq(), channelName);
		} catch (Exception e) {
			stackTrace = e;
		}
		endTime = System.currentTimeMillis();
		if (stackTrace != null) {
			throw new CommunicateException(stackTrace);
		}
		System.out.println("==================== end " + (endTime - beginTime));
		return ret;
	}

    @Override
    public Object callRemote(IStreamableMessage request) throws CommunicateException
    {
		if (this.adapter == null) {
			throw new CommunicateException("没有配置Communicate Filter, 通讯模块无法适配!");
		}

		ICommunicator communicator = this.adapter.findCommunicatorForRequest(request);
		System.out.println("==================== start" + communicator);
		String req = request.getReqStr();
		long beginTime, endTime;
		beginTime = endTime = System.currentTimeMillis();
		String channelName = "";
		String requestType = "";
		Exception stackTrace = null;
		channelName = request.getChannelName();
		requestType = request.getRequestType();
		String ret = null;
		try {
			ret = communicator.httSend(req, channelName, requestType);
			if (ret != null && ret.length()> 0 && ret.toCharArray()[0] == '%') {
				return URLDecoder.decode(ret, encoding);
			}
		} catch (Exception e) {
			stackTrace = e;
		}
		endTime = System.currentTimeMillis();
		if (stackTrace != null) {
			throw new CommunicateException(stackTrace);
		}
		System.out.println("==================== end " + (endTime - beginTime));
		return ret;
    }
}
