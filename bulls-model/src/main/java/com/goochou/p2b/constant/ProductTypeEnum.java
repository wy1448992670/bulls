package com.goochou.p2b.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 畜牧品种
 * 
 * @ClassName: ProductTypeEnum
 * @author zj
 * @date 2019-08-15 18:11
 */
public enum ProductTypeEnum {
	ALL(0, "全部"), AGS(1, "安格斯"), XMTR(5, "西门塔尔");

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 类型描述
	 */
	private String typeName;



	ProductTypeEnum(Integer id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}
	
	public static ProductTypeEnum getProductTypeById(Integer id) {
		for (ProductTypeEnum enums : values()) {
			if (enums.getId().equals(id)) {
				return enums;
			}
		}
		return null;
	}

	public static List<Enum<ProductTypeEnum>> getEnumList() {
		List<Enum<ProductTypeEnum>> list = new ArrayList<>();
		list.addAll(Arrays.asList(ProductTypeEnum.values()));
		return list;
	}

	public static List<Map<String, Object>> enumParseMap() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		for (ProductTypeEnum e : ProductTypeEnum.values()) {
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
