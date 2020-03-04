package com.goochou.p2b.model;

import java.util.Map;

/**
 * 
 * Created on 2014年8月7日
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [控制层调用AO层消息返回基类]</p>
 * <p>Copyright:   Copyright (c) 2011</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [杜成] [196168@qq.com]
 * @version        1.0
 */
public class BaseResult {
	
	protected boolean success = false;
	protected String errorCode;
	protected String errorMsg;
	protected Map<?, ?> map;
	public Map<?, ?> retInfo;
	protected String msg;
	public BaseResult(){}
	public BaseResult(boolean success){
		this.success = success;
	}
	public BaseResult(String msg){
		this.msg = msg;
		if (!success)this.errorMsg = msg;
	}
	public BaseResult(boolean success, String msg){
		this.success = success;
		this.msg = msg;
		if (!success)this.errorMsg = msg;
	}
	public BaseResult(boolean success, String code, String msg){
		this.success = success;
		this.msg = msg;
		this.errorCode = code;
		if (!success)this.errorMsg = msg;
	}
	public Map<?, ?> getMap() {
		return map;
	}
	public void setMap(Map<?, ?> map) {
		this.map = map;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode; 
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		this.msg = errorMsg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Map<?, ?> getRetInfo() {
		return retInfo;
	}
	public void setRetInfo(Map<?, ?> retInfo) {
		this.retInfo = retInfo;
	}
	@Override
	public String toString() {
		return "BaseResult [success=" + success + ", errorCode=" + errorCode
				+ ", errorMsg=" + errorMsg + ", map=" + map + ", retInfo=" + retInfo + ", msg=" + msg
				+ "]";
	}
}
