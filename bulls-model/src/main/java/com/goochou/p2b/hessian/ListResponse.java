package com.goochou.p2b.hessian;

import java.util.List;
import java.util.Map;

public class ListResponse extends Response{
	private static final long serialVersionUID = -6836178366660962490L;
	private Integer count;
    private List<Map<String, Object>> list;
    
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
