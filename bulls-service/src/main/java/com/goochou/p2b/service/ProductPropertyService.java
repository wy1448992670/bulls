package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.dao.ProductPropertyMapper;
import com.goochou.p2b.model.ProductProperty;

public interface ProductPropertyService {

	ProductPropertyMapper getMapper();

	/**
	 * 根据产品id 获取相关下面的属性集合
	 * 
	 * @param productId
	 * @return
	 */
	List<ProductProperty> listProductProperty(int productId);

}
