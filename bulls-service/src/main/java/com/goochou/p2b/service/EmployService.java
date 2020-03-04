package com.goochou.p2b.service;



import java.util.List;

import com.goochou.p2b.dao.EmployMapper;
import com.goochou.p2b.model.Employ;

public interface EmployService {
	public EmployMapper getEmployMapper();
	
	public boolean checkMobileExsits(String mobile);
	
	public int addEmploy(Employ employ) throws Exception;
	
	public Boolean addRelationEmployUser(List<Integer> userIds, Integer employId) throws Exception;
}
