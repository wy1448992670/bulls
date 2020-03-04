package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.dao.GoodsCategoryMapper;
import com.goochou.p2b.model.goods.GoodsCategory;

/**
 * 商品类别
 * 
 * @ClassName GoodsCategoryService
 * @author zj
 * @Date 2019年5月15日 下午3:33:37
 * @version 1.0.0
 */
public interface GoodsCategoryService {
	public GoodsCategoryMapper getMapper();  
	/**
	 * 获取商品类别
	 * @return
	 * @author: zj
	 */
	List<GoodsCategory> selectGoodsCategoryList();
	
	List<GoodsCategory> listCategory(String categoryName,  Integer limitStart, Integer limitEnd);
	
	int countCategory(String categoryName);
	
	
	void saveCategory(GoodsCategory category);
	
	void updateCategory(GoodsCategory category);
}
