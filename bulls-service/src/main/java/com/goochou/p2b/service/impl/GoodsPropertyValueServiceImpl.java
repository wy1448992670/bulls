package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.GoodsMapper;
import com.goochou.p2b.dao.GoodsPropertyMapper;
import com.goochou.p2b.dao.GoodsPropertyValueMapper;
import com.goochou.p2b.model.ProjectPropertyValue;
import com.goochou.p2b.model.goods.GoodsPropertyValue;
import com.goochou.p2b.service.GoodsPropertyService;
import com.goochou.p2b.service.GoodsPropertyValueService;

@Service
public class GoodsPropertyValueServiceImpl implements GoodsPropertyValueService {
	@Autowired
	GoodsPropertyValueMapper goodsPropertyValueMapper;
	@Autowired
	GoodsMapper goodsMapper;
	@Autowired
	GoodsPropertyService goodsPropertyService;
	@Autowired
	GoodsPropertyMapper goodsPropertyMapper;

	@Override
	public void saveProjectPropertyValue(ProjectPropertyValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveGoodsPropertyValue(String[] productPropertyIdArray, String[] propertyValueArray, int goodsId) {
		String propertyValue = "";
		int productPropertyId = 0;
		for (int i = 0; i < propertyValueArray.length; i++) {
			propertyValue = propertyValueArray[i];
			productPropertyId = Integer.parseInt(productPropertyIdArray[i]);
			GoodsPropertyValue value = new GoodsPropertyValue();
			value.setPropertyId(productPropertyId);
			value.setPropertyValue(propertyValue);
			value.setCreateDate(new Date());
			value.setUpdateDate(new Date());
			value.setGoodsId(goodsId);
			goodsPropertyValueMapper.insert(value);
		}

	}

	@Override
	public List<ProjectPropertyValue> listProjectPropertyValueByProductPropertyId(int productPropertyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateProjectPropertyValue(String[] productPropertyIdArray, String[] propertyValueArray, int goodsId) {
		String propertyValue = "";
		int productPropertyId = 0;

		goodsMapper.delPropertyValues(goodsId);
		if (productPropertyIdArray == null || propertyValueArray == null) {
			return;
		}
		for (int i = 0; i < propertyValueArray.length; i++) {
			propertyValue = propertyValueArray[i];
			productPropertyId = Integer.parseInt(productPropertyIdArray[i]);
			GoodsPropertyValue value = new GoodsPropertyValue();
			value.setPropertyId(productPropertyId);
			value.setPropertyValue(propertyValue);
			value.setGoodsId(goodsId);
			value.setCreateDate(new Date());
			value.setUpdateDate(new Date());
			goodsPropertyValueMapper.insert(value);
		}

	}

	@Override
	public GoodsPropertyValue getValueByGoodsIdAndPropertyId(Integer goodsId, Integer propertyId) {
		return goodsPropertyValueMapper.getGoodsPropertyValue(propertyId, goodsId);
	}

}
