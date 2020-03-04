package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.Department;

public interface DepartmentService {

	List<Department> getShowDepartments(Integer superDepartmentId);

	List<Department> getSubDepartments(Integer superDepartmentId);

	List<Department> getAllDepartment();
	
	Department getDepartmentById(Integer id);
	
}
