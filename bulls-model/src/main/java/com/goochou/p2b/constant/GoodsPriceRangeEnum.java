package com.goochou.p2b.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品价格区间
 * 
 * @ClassName: {@link GoodsPriceRangeEnum}
 * @author zj
 * @date 2019-08-15 18:11
 */
public enum GoodsPriceRangeEnum {
	ALL(0, "全部"), PRICE_0TO100(1, "0-100"), PRICE_100TO200(2, "100-200"), PRICE_200UP(3, "200以上");

	/**
	 * 表主键
	 */
	private Integer id;
	/**
	 * 商品价格区间说明
	 */
	private String typeName;

	GoodsPriceRangeEnum(Integer id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}

	public static List<Enum<GoodsPriceRangeEnum>> getEnumList() {
		List<Enum<GoodsPriceRangeEnum>> list = new ArrayList<>();
		list.addAll(Arrays.asList(GoodsPriceRangeEnum.values()));
		return list;
	}

	public static List<Map<String, Object>> enumParseMap() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		for (GoodsPriceRangeEnum e : GoodsPriceRangeEnum.values()) {
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
