package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import com.alipay.api.domain.CreationPlanData;
import com.goochou.p2b.constant.GoodsPropertyEnum;
import com.goochou.p2b.dao.GoodsCategoryMapper;
import com.goochou.p2b.dao.GoodsPropertyMapper;
import com.goochou.p2b.model.goods.GoodsCategory;
import com.goochou.p2b.model.goods.GoodsCategoryExample;
import com.goochou.p2b.model.goods.GoodsCategoryExample.Criteria;
import com.goochou.p2b.model.goods.GoodsProperty;
import com.goochou.p2b.service.GoodsCategoryService;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService{
	@Autowired
	GoodsCategoryMapper goodsCategoryMapper;
	@Autowired
	GoodsPropertyMapper goodsPropertyMapper;

	@Override
    public List<GoodsCategory> selectGoodsCategoryList() {
    	GoodsCategoryExample example = new GoodsCategoryExample();
    	example.createCriteria().andIdNotEqualTo(0);
        return goodsCategoryMapper.selectByExample(example);
    }

	@Override
	public GoodsCategoryMapper getMapper() {
		return goodsCategoryMapper;
	}

	@Override
	public List<GoodsCategory> listCategory(String categoryName, Integer limitStart, Integer limitEnd) {
		GoodsCategoryExample example = new GoodsCategoryExample();
		
		GoodsCategoryExample.Criteria cri = example.createCriteria();
		if(StringUtils.isNotEmpty(categoryName)) {
			cri.andCategoryNameEqualTo(categoryName);
		}
		cri.andIdNotEqualTo(0);
		if(limitStart != null  && limitEnd != null) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
	
		return goodsCategoryMapper.selectByExample(example);
	}

	@Override
	public int countCategory(String categoryName) {
		GoodsCategoryExample example = new GoodsCategoryExample();
		GoodsCategoryExample.Criteria cri = example.createCriteria();
		if(StringUtils.isNotEmpty(categoryName)) {
			cri.andCategoryNameEqualTo(categoryName);
		}
		cri.andIdNotEqualTo(0);
		return (int)goodsCategoryMapper.countByExample(example);
	}

	@Override
	public void saveCategory(GoodsCategory category) {
		category.setParentId(0);
		category.setCreateDate(new Date());
		category.setUpdateDate(new Date());
		// 新增商品分类,同时添加分类属性
		if(goodsCategoryMapper.insert(category) == 1) {
			GoodsProperty property = new GoodsProperty();
			property.setCategoryId(category.getId());
			// 目前商品属性只有规格
			property.setPropertyName(GoodsPropertyEnum.SPECIFICATION.getPropertyName());
			property.setCreateDate(new Date());
			property.setUpdateDate(new Date());
			goodsPropertyMapper.insert(property);
		}
	}

	@Override
	public void updateCategory(GoodsCategory category) {
		goodsCategoryMapper.updateByPrimaryKeySelective(category);
	}
}
