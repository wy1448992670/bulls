package com.goochou.p2b.adapter;

import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;

/**
 * 
 * Created on 2017-6-6
 * <p>Title:       DEC集团系统_[子系统统名]_[模块名]/p>
 * <p>Description: [接口服务需要实现的接口]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     澳钜（上海）教育科技有限公司</p>
 * <p>Department:  研发中心</p>
 * @author         [薛祺] [421288505@qq.com]
 * @version        1.0
 */
public interface ICommunicator
{
	String httSend(String data, String channel,String isWerservice) throws CommunicateException;

	Response httSend2(Request request, String method) throws CommunicateException;
}