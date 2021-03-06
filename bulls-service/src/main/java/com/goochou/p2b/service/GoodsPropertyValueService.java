package com.goochou.p2b.service;

import java.util.List;


import com.goochou.p2b.model.ProjectPropertyValue;
import com.goochou.p2b.model.goods.GoodsPropertyValue;

public interface GoodsPropertyValueService {

	/**
	 * 新增记录
	 * 
	 * @param value
	 * @author: zj
	 */
	void saveProjectPropertyValue(ProjectPropertyValue value);

	/**
	 * 新增商品属性值
	 * 
	 * @param productPropertyIdArray 属性id数组（来自页面多输入框）
	 * @param propertyValueArray     属性值数组（来自页面多输入框）
	 * @param projectId              项目主键
	 * @author: zj
	 */
	void saveGoodsPropertyValue(String[] productPropertyIdArray, String[] propertyValueArray, int projectId);

	/**
	 * 根据productPropertyId获取到相关牧场产品及属性内容
	 * 
	 * @param productPropertyId
	 * @return
	 * @author: zj
	 */
	List<ProjectPropertyValue> listProjectPropertyValueByProductPropertyId(int productPropertyId);

	/**
	 * 更新项目的产品属性
	 * 
	 * @param productPropertyIdArray
	 * @param propertyValueArray
	 * @param projectId
	 * @author: zj
	 */
	void updateProjectPropertyValue(String[] productPropertyIdArray, String[] propertyValueArray, int projectId);
	
	
	GoodsPropertyValue getValueByGoodsIdAndPropertyId(Integer goodsId, Integer PropertyId);

}
