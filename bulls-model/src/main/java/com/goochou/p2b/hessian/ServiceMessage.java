package com.goochou.p2b.hessian;

import java.io.Serializable;

import com.goochou.p2b.utils.CloneUtils;

/**
 * 
 * Created on 2014年8月7日
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [接口请求消息对象]</p>
 * <p>Copyright:   Copyright (c) 2011</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] []
 * @version        1.0
 */
public class ServiceMessage implements Serializable, Cloneable {
	/**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = 3342748526116261971L;
	/**
	 * 方法
	 */
	private String method;
	
	private Request req;
	
	//渠道,枚举
	private String channel;
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
	
    public Request getReq() {
		return req;
	}

	public void setReq(Request req) {
		this.req = req;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public ServiceMessage()
    {
        
    }

    public ServiceMessage(String method, Request req)
    {
        this.method = method;
        this.req = req;
    }
	
    public ServiceMessage clone() {
    	ServiceMessage serviceMessage = null;
		try {
			serviceMessage = (ServiceMessage)super.clone();
			req = (Request)CloneUtils.clone(req);
			
		} catch (CloneNotSupportedException e) {
		}
		return serviceMessage;
	}


}
