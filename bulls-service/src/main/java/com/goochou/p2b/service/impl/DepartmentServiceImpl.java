package com.goochou.p2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.DepartmentMapper;
import com.goochou.p2b.model.Department;
import com.goochou.p2b.model.DepartmentExample;
import com.goochou.p2b.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private static final Logger logger = Logger.getLogger(DepartmentServiceImpl.class);
	@Resource
	private DepartmentMapper departmentMapper;

	@Override
	public List<Department> getShowDepartments(Integer superDepartmentId) {
		
		DepartmentExample example=new DepartmentExample();
		example.createCriteria().andIsShowEqualTo(true);
		return departmentMapper.selectByExampleAndSuperDepartmentId(example,superDepartmentId);
	}
	@Override
	public List<Department> getSubDepartments(Integer superDepartmentId) {
		DepartmentExample example=new DepartmentExample();
		return departmentMapper.selectByExampleAndSuperDepartmentId(example,superDepartmentId);
	}
	
	@Override
	public List<Department> getAllDepartment() {
		DepartmentExample example=new DepartmentExample();
		example.createCriteria().andIsShowEqualTo(true);
		return departmentMapper.selectByExample(example);
	}

	@Override
	public Department getDepartmentById(Integer id) {
		return departmentMapper.selectByPrimaryKey(id);
	}
	
}
