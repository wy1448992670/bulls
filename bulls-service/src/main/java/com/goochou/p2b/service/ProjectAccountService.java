package com.goochou.p2b.service;

import com.goochou.p2b.exception.ManagerException;
import com.goochou.p2b.model.ProjectAccount;

import java.util.List;

/**
 * Created by irving on 2016/7/19.
 */
public interface ProjectAccountService {
    public List<ProjectAccount> list(Integer start, Integer limit);

    public Integer listCount();

    public void save(ProjectAccount account);

    public void update(ProjectAccount account);

    public ProjectAccount detail(Integer id);

    public List<ProjectAccount> getByProjectId(Integer id);
    
    public ProjectAccount getProjectAccountByOrderNo(Integer investmentId);
    
    List<ProjectAccount> getProjectAccountByInvestmentId(String investmentId) throws ManagerException;

	public ProjectAccount queryProjectAccountByEnterprise(Integer enterpriseId);
	
	boolean checkAccountNameOrOrderNoNotExists(String name, String orderNo);
    
}
