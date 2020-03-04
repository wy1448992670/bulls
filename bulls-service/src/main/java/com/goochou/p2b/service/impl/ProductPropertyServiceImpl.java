package com.goochou.p2b.service.impl;

import java.util.List;

import com.goochou.p2b.dao.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ProductMapper;
import com.goochou.p2b.dao.ProductPropertyMapper;
import com.goochou.p2b.model.ProductProperty;
import com.goochou.p2b.model.ProductPropertyExample;
import com.goochou.p2b.service.ProductPropertyService;

@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {
	@Autowired
	ProductPropertyMapper productPropertyMapper;
	@Autowired
	ProductMapper productMapper;

	@Override
	public ProductPropertyMapper getMapper() {
		return productPropertyMapper;
	}

	@Override
	public List<ProductProperty> listProductProperty(int productId) {
		ProductPropertyExample example = new ProductPropertyExample();
		example.createCriteria().andProductIdEqualTo(productId);
		List<ProductProperty> properties = productPropertyMapper.selectByExample(example);
		return properties;
	}

}
