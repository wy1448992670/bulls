package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.EmployMapper;
import com.goochou.p2b.dao.EmployUserMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.Employ;
import com.goochou.p2b.model.EmployExample;
import com.goochou.p2b.model.EmployExample.Criteria;
import com.goochou.p2b.model.EmployUser;
import com.goochou.p2b.model.EmployUserExample;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.EmployService;

@Service
public class EmployServiceImpl implements EmployService {
	@Resource
	private EmployMapper employMapper;
	@Resource
	private EmployUserMapper employUserMapper;
	@Resource
	private UserMapper userMapper;
	
	@Override
	public boolean checkMobileExsits(String mobile) {
		EmployExample example = new EmployExample();
		Criteria cri = example.createCriteria();
		cri.andMobileEqualTo(mobile);
		List<Employ> list = employMapper.selectByExample(example);
		return list.isEmpty() ? false : true;
	}


	@Override
	public EmployMapper getEmployMapper() {
		return employMapper;
	}


	@Override
	public int addEmploy(Employ employ) throws Exception{
		int num = 0;
		Date date = new Date();
		employ.setCreateDate(date);
 
		if(employMapper.insert(employ) == 1) {
			EmployUser empUser = new EmployUser();
			empUser.setEmoloyId(employ.getId());
			
			User user = userMapper.selectUserByPhone(employ.getMobile());
			if(user != null) {
				empUser.setUserId(user.getId());
			}
			empUser.setCreateDate(date);
			num = employUserMapper.insert(empUser);
		}
		return num;
	}


	@Override
	public Boolean addRelationEmployUser(List<Integer> userIds, Integer employId) throws Exception{
		//查询用户是否被其他分销管理员绑定
		EmployUserExample example = null;
		for (Integer userId : userIds) {
			example = new EmployUserExample();
			com.goochou.p2b.model.EmployUserExample.Criteria cri = example.createCriteria(); 
			cri.andUserIdEqualTo(userId);
			List<EmployUser> list = employUserMapper.selectByExample(example);
			
			boolean flag  = list.isEmpty() ? false : true;
			if(flag) {
				EmployUser empUser = list.get(0);
				Employ employ = employMapper.selectByPrimaryKey(empUser.getEmoloyId());//分销员
				User user = userMapper.selectByPrimaryKey(empUser.getUserId());//用户
				throw new Exception(user.getTrueName()+" 已被分销员'"+employ.getName()+"'绑定");
			}
		}
		if(employUserMapper.insertByUserIds(userIds, employId) != userIds.size()) {
			return false;
		}
		//更新employ_user表中的user的department_id
		employUserMapper.updateUserDepartmentByEmployId(employId);
		
		return true;
	}
}
