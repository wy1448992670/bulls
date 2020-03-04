package com.goochou.p2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.goochou.p2b.dao.AdminRoleMapper;
import com.goochou.p2b.model.AdminRoleExample;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ResourcesMapper;
import com.goochou.p2b.dao.RoleMapper;
import com.goochou.p2b.dao.RoleResourcesMapper;
import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.Role;
import com.goochou.p2b.model.RoleExample;
import com.goochou.p2b.model.RoleExample.Criteria;
import com.goochou.p2b.service.ResourcesService;
import com.goochou.p2b.service.RoleServices;

@Service
public class RoleServicesImpl implements RoleServices {
    private static Logger logger = Logger.getLogger(RoleServicesImpl.class);

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleResourcesMapper roleResourcesMapper;
    @Resource
    private ResourcesMapper resourcesMapper;
    @Resource
    private ResourcesService resourcesService;
    @Resource
    private AdminRoleMapper adminRoleMapper;

    public List<Role> findAllRole(int start, int limit, String keyword) {
        RoleExample example = new RoleExample();
        Criteria c = example.createCriteria();
        if (keyword != null && keyword.trim() != null) {
            c.andRoleLike("%" + keyword + "%");
        }
        example.setLimitEnd(limit);
        example.setLimitStart(start);
        List<Role> list = roleMapper.selectByExample(example);
        for (Role role : list) {
            int count = this.countByRleId(role.getId());
            role.setCount(count);
        }
        return list;
    }

    private int countByRleId(Integer roleId) {
        if (roleId == null) {
            return 0;
        }
        AdminRoleExample example = new AdminRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        return adminRoleMapper.countByExample2(example);
    }

    public int save(Role role) {

        return roleMapper.insert(role);
    }

    public int delete(int id) {

        return roleMapper.deleteByPrimaryKey(id);
    }

    public int update(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);

    }

    public Role detail(int id) {

        return roleMapper.selectByPrimaryKey(id);
    }

    public int getCount(String keyword) {
        RoleExample example = new RoleExample();
        Criteria c = example.createCriteria();
        if (keyword != null && keyword.trim() != null) {
            c.andRoleLike("%" + keyword + "%");
        }
        return roleMapper.countByExample(example);
    }

    public boolean checkNameExists(String role) {
        RoleExample example = new RoleExample();
        Criteria c = example.createCriteria();
        if (role != null) {
            c.andRoleEqualTo(role);
        }
        List<Role> list = roleMapper.selectByExample(example);
        return list.size() > 0 ? false : true;
    }

    public List<Resources> findById(int roleId) {
        List<Resources> resourceList = resourcesService.findAllResource();
        List<Resources> roleResource = resourcesService.findByroleId(roleId);
        String[] roleresouce = new String[roleResource.size()];
        for (int i = 0; i < roleResource.size(); i++) {
            roleresouce[i] = roleResource.get(i).getId() + "";
        }
        for (int k = 0; k < resourceList.size(); k++) {
            int flag = 0;
            for (int j = 0; j < roleresouce.length; j++) {
                if ((resourceList.get(k).getId() + "").equals(roleresouce[j])) {
                    flag++;
                }
            }
            if (flag == 1) {
                resourceList.get(k).setChecked("true");
                flag = 0;
            } else {
                flag = 0;
                resourceList.get(k).setChecked("false");
            }
        }
        return resourceList;
    }

    public List<Role> findAllRole() {
        RoleExample example = new RoleExample();
        return roleMapper.selectByExample(example);
    }

}
