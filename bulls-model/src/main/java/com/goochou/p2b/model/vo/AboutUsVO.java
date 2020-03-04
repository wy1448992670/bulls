package com.goochou.p2b.model.vo;

/**
 * Created on 2017-11-22
 * <p>Title:       [关于我们接口对象]</p>
 * <p>Company:     鑫聚财线上交易平台</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class AboutUsVO {
	private String lable;
	private String value;
	private String valueType;
	private String url;
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public AboutUsVO(String lable, String value, String valueType, String url) {
		this.lable = lable;
		this.value = value;
		this.valueType = valueType;
		this.url = url;
	}
	
}
