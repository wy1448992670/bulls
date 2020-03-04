package com.goochou.p2b.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品种类
 * 
 * @ClassName: ProductTypeEnum
 * @author zj
 * @date 2019-08-15 18:11
 */
public enum GoodsTypeEnum {
	ALL(0, "全部"), 
	BEEF(1, "牛肉精选"), 
	//RED_WINE(2, "红酒精选"),
	;

	/**
	 * 表主键
	 */
	private Integer id;
	/**
	 *类型说明
	 */
	private String typeName;



	GoodsTypeEnum(Integer id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}

	public static List<Enum<GoodsTypeEnum>> getEnumList() {
		List<Enum<GoodsTypeEnum>> list = new ArrayList<>();
		list.addAll(Arrays.asList(GoodsTypeEnum.values()));
		return list;
	}

	public static List<Map<String, Object>> enumParseMap() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		for (GoodsTypeEnum e : GoodsTypeEnum.values()) {
			map = new HashMap<>();
			map.put("id", e.getId()+"");
			map.put("subTitle", e.getTypeName());
			list.add(map);
		}
		return list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
 
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public static void main(String[] args) {
		System.out.println(enumParseMap());

	}
}
