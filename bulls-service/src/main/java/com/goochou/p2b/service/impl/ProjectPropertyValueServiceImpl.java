package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.goochou.p2b.dao.RechargeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ProductPropertyMapper;
import com.goochou.p2b.dao.ProjectPropertyValueMapper;
import com.goochou.p2b.model.ProductProperty;
import com.goochou.p2b.model.ProductPropertyExample;
import com.goochou.p2b.model.ProjectPropertyValue;
import com.goochou.p2b.model.ProjectPropertyValueExample;
import com.goochou.p2b.service.ProjectPropertyValueService;

@Service
public class ProjectPropertyValueServiceImpl implements ProjectPropertyValueService {
	@Autowired
	ProjectPropertyValueMapper projectPropertyValueMapper;
	@Autowired
	ProductPropertyMapper productPropertyMapper;


	@Override
	public ProjectPropertyValueMapper getMapper(){
		return projectPropertyValueMapper;
	}


	@Override
	public void saveProjectPropertyValue(ProjectPropertyValue value) {
		projectPropertyValueMapper.insert(value);
	}

	@Override
	public void saveProjectPropertyValue(String[] productPropertyIdArray, String[] propertyValueArray, int projectId) {
		String propertyValue = "";
		int productPropertyId = 0;
		for (int i = 0; i < propertyValueArray.length; i++) {
			propertyValue = propertyValueArray[i];
			productPropertyId = Integer.parseInt(productPropertyIdArray[i]);
			ProjectPropertyValue value = new ProjectPropertyValue();
			value.setProjectId(projectId);
			value.setProductPropertyId(productPropertyId);
			value.setPropertyValue(propertyValue);
			value.setCreateDate(new Date());
			value.setUpdateDate(new Date());
			projectPropertyValueMapper.insert(value);
		}
	}


	@Override
	public List<ProjectPropertyValue> listProjectPropertyValueByProductPropertyId(int productPropertyId) {
		ProjectPropertyValueExample example = new ProjectPropertyValueExample();
		example.createCriteria().andProjectIdEqualTo(productPropertyId);
		List<ProjectPropertyValue> list = projectPropertyValueMapper.selectByExample(example);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			// 先获取产品属性对象
			ProductProperty productProperty = productPropertyMapper.selectByPrimaryKey(productPropertyId);

			ProjectPropertyValue projectPropertyValue = (ProjectPropertyValue) iterator.next();
			projectPropertyValue.setPropertyName(productProperty.getPropertyName());
		}
		return list;
	}
	
	@Override
	public void updateProjectPropertyValue(String[] productPropertyIdArray, String[] propertyValueArray, int projectId) {
		String propertyValue = "";
		int productPropertyId = 0;
		
		ProjectPropertyValueExample example=new ProjectPropertyValueExample();
		example.createCriteria().andProjectIdEqualTo(projectId);
		projectPropertyValueMapper.deleteByExample(example);
		
		for (int i = 0; i < propertyValueArray.length; i++) {
			propertyValue = propertyValueArray[i];
			productPropertyId = Integer.parseInt(productPropertyIdArray[i]);
			ProjectPropertyValue value = new ProjectPropertyValue();
			value.setProjectId(projectId);
			value.setProductPropertyId(productPropertyId);
			value.setPropertyValue(propertyValue);
			value.setCreateDate(new Date());
			value.setUpdateDate(new Date());
			projectPropertyValueMapper.insert(value);
		}
	}
}
