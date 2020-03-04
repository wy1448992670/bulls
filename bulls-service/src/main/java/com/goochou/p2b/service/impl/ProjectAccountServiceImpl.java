package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.ProjectAccountMapper;
import com.goochou.p2b.model.ProjectAccount;
import com.goochou.p2b.model.ProjectAccountExample;
import com.goochou.p2b.service.ProjectAccountService;
import com.goochou.p2b.utils.StringUtil;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by irving on 2016/7/19.
 */
@Service
public class ProjectAccountServiceImpl implements ProjectAccountService {
    @Resource
    private ProjectAccountMapper projectAccountMapper;

    @Override
    public List<ProjectAccount> list(Integer start, Integer limit) {
        ProjectAccountExample example = new ProjectAccountExample();
        example.setOrderByClause("id desc");
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        return projectAccountMapper.selectByExample(example);
    }

    @Override
    public Integer listCount() {
        ProjectAccountExample example = new ProjectAccountExample();
        return projectAccountMapper.countByExample(example);
    }

    @Override
    public void save(ProjectAccount account) {
        account.setCreateTime(new Date());
        projectAccountMapper.insertSelective(account);
    }

    @Override
    public void update(ProjectAccount account) {
        projectAccountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public ProjectAccount detail(Integer id) {
        return projectAccountMapper.detail(id);
    }

    @Override
    public List<ProjectAccount> getByProjectId(Integer id) {
        ProjectAccountExample example = new ProjectAccountExample();
        example.createCriteria().andProjectIdEqualTo(id).andStatusEqualTo(0);
        return projectAccountMapper.selectByExample(example);
    }

	@Override
	public ProjectAccount getProjectAccountByOrderNo(Integer investmentId) {
		
		ProjectAccountExample example = new ProjectAccountExample();
		example.createCriteria().andOrderNoEqualTo(investmentId.toString()).andStatusEqualTo(0);
		List<ProjectAccount> list = projectAccountMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}
	
	@Override
	public List<ProjectAccount> getProjectAccountByInvestmentId(String investmentId) {
			
		ProjectAccountExample example = new ProjectAccountExample();
		example.createCriteria().andOrderNoEqualTo(investmentId);
		return projectAccountMapper.selectByExample(example);
			
	}

	@Override
	public ProjectAccount queryProjectAccountByEnterprise(Integer enterpriseId) {
		return projectAccountMapper.queryProjectAccountByEnterprise(enterpriseId);
	}

	@Override
	public boolean checkAccountNameOrOrderNoNotExists(String name, String orderNo) {
		
		ProjectAccountExample example = new ProjectAccountExample();
		if(!StringUtil.isNull(name)){
			example.createCriteria().andNameEqualTo(name);
		}else if(!StringUtil.isNull(orderNo)){
			example.createCriteria().andOrderNoEqualTo(orderNo);
		}else{
			return false;
		}
		
		List<ProjectAccount> list =  projectAccountMapper.selectByExample(example);
		
		return list != null && list.size() > 0 ? false : true;
	}

}
