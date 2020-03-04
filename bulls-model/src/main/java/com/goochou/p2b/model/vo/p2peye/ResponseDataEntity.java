package com.goochou.p2b.model.vo.p2peye;

import java.util.List;
import java.util.Map;

/**
 * 3.6 响应数据结构
 *
 */
public class ResponseDataEntity {

	private String code;
	private String message ;
	private List<Map<String, Object>> data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
}
