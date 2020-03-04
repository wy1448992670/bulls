package com.goochou.p2b.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * Created on 2015-5-5
 * <p>Title:       中投融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [APP请求报文对象]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     中投融线上交易平台</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class RequestParams implements Serializable {
	private static final long serialVersionUID = -2715676671200467093L;
	
	//渠道
	private String channel;
	//业务参数
	private Map<?,?> params;
	//签名
	private String sign;
	//版本
	private String version;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Map<?, ?> getParams() {
		return params;
	}
	public void setParams(Map<?, ?> params) {
		this.params = params;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "RequestParams [channel=" + channel + ", params=" + params
				+ ", sign=" + sign + ", version=" + version + "]";
	}
}
