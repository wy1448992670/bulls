package com.goochou.p2b.constant.pasture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangyun
 * @date 2020年1月3日
 * @time 上午9:43:15
 */
public enum OrderPinStateEnum {
    NO_BUY(0,"待付款"),
	BUYING(4,"待成团"),
	BUYED(1,"已成团"),
	SALED(2,"已结算");

	private int code;
    private String description;

	public static List<Map<String, Object>> enumParseMap() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		for (OrderPinStateEnum e : OrderPinStateEnum.values()) {
			map = new HashMap<>();
			map.put("code", e.getCode());
			map.put("description", e.getDescription());
			list.add(map);
		}
		return list;
	}
	
	 /**
     * 拼接外部集合
    * @Title: enumParseMap 
    * @param list  需要拼接的集合
    * @return List<Map<String,Object>>
    * @author zj
    * @date 2019-06-26 16:39
     */
    public static List<Map<String, Object>> enumParseMap(List<Map<String, Object>> list) {
    	
    	List<Map<String, Object>> bigList = new ArrayList<>();
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			bigList.add(map);
		}
    	Map<String, Object> map;
    	for (OrderPinStateEnum e : OrderPinStateEnum.values()) {
    			map = new HashMap<>();
    			map.put("code", e.getCode());
    			map.put("description", e.getDescription());
    			bigList.add(map);
    	}
    	return bigList;
    }
    OrderPinStateEnum(int code,String description){
    	this.code=code;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setDescription(String description) {
        this.description = description;
    }
    public static void main(String[] args) {
		System.out.println(enumParseMap());
	}
}
