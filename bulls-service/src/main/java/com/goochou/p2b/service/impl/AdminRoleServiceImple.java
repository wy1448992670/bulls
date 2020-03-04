package com.goochou.p2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.AdminRoleMapper;
import com.goochou.p2b.model.AdminRole;
import com.goochou.p2b.model.AdminRoleExample;
import com.goochou.p2b.model.AdminRoleExample.Criteria;
import com.goochou.p2b.service.AdminRoleService;

@Service
public class AdminRoleServiceImple implements AdminRoleService {

    private static Logger logger = Logger.getLogger(AdminRoleServiceImple.class);

    @Resource
    private AdminRoleMapper adminRoleMapper;

    public List<AdminRole> getByUserId(int userId) {
        try {
            AdminRoleExample example = new AdminRoleExample();
            Criteria c = example.createCriteria();
            if (userId > 0) {
                c.andAdminIdEqualTo(userId);
                return adminRoleMapper.selectByExample(example);
            }
        } catch (Exception e) {
            logger.equals(e);
        }
        return null;
    }

    public void update(AdminRole adminRole) {
        adminRoleMapper.updateByPrimaryKey(adminRole);
    }

    public void save(AdminRole adminRole) {
        adminRoleMapper.insert(adminRole);
    }

    public void delete(int id) {
        adminRoleMapper.deleteByPrimaryKey(id);
    }



}
