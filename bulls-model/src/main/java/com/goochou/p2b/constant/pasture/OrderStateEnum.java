package com.goochou.p2b.constant.pasture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 订单状态枚举 供前端展示用
* @ClassName: OrderStateEnum 
* @author zj 
* @date 2019-06-26 13:50
 */
public enum OrderStateEnum {
    NO_BUY(0,"待付款"),
	BUYED(1,"已认养"),
	SALED(2,"已出售");

	private int code;
    private String description;

	public static List<Map<String, Object>> enumParseMap() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		for (OrderStateEnum e : OrderStateEnum.values()) {
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
    	for (OrderStateEnum e : OrderStateEnum.values()) {
    			map = new HashMap<>();
    			map.put("code", e.getCode());
    			map.put("description", e.getDescription());
    			bigList.add(map);
    	}
    	return bigList;
    }
    OrderStateEnum(int code,String description){
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
