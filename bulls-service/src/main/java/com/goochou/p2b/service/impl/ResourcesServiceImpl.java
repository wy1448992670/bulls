package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.ResourcesMapper;
import com.goochou.p2b.dao.RoleMapper;
import com.goochou.p2b.dao.RoleResourcesMapper;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.ResourcesExample.Criteria;
import com.goochou.p2b.service.ResourcesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourcesServiceImpl implements ResourcesService {

    @Resource
    private ResourcesMapper resourcesMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleResourcesMapper roleResourcesMapper;

    @Override
    public List<Resources> findAllResource(int start, int limit, String keyword) {
        ResourcesExample example = new ResourcesExample();
        Criteria c = example.createCriteria();
        if (keyword != null) {
            c.andUrlLike("%" + keyword + "%");
        }
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        return resourcesMapper.selectByExample(example);
    }

    @Override
    public int getCount(String keyword) {
        ResourcesExample example = new ResourcesExample();
        Criteria c = example.createCriteria();
        if (keyword != null) {
            c.andUrlLike("%" + keyword + "%");
        }
        return resourcesMapper.countByExample(example);
    }

    @Override
    public Resources detail(int id) {
        return resourcesMapper.selectByPrimaryKey(id);
    }

    @Override
    public int save(Resources resources) {
        return resourcesMapper.insert(resources);
    }

    @Override
    public int delete(int id) {
        return resourcesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Resources resources) {
        return resourcesMapper.updateByPrimaryKeySelective(resources);
    }

    @Override
    public boolean checkNameOrUrlExists(String name, String url, String permission) {
        ResourcesExample example = new ResourcesExample();
        Criteria c = example.createCriteria();
        if (name != null) {
            c.andNameEqualTo(name.trim());
        }
        if (url != null) {
            c.andUrlEqualTo(url.trim());
        }
        if (permission != null) {
            c.andPermissionEqualTo(permission);
        }
        List<Resources> list = resourcesMapper.selectByExample(example);
        return list.size() > 0 ? false : true;
    }

    @Override
    public List<Resources> findByroleId(int roleId) {
        List<Resources> resourcesList = new ArrayList<Resources>();
        if (roleId > 0) {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if (role != null) {
                RoleResourcesExample example = new RoleResourcesExample();
                com.goochou.p2b.model.RoleResourcesExample.Criteria c = example.createCriteria();
                c.andRoleIdEqualTo(role.getId());
                List<RoleResources> list = roleResourcesMapper.selectByExample(example);
                if (list != null && !list.isEmpty()) {
                    for (RoleResources rr : list) {
                        Resources resources = resourcesMapper.selectByPrimaryKey(rr.getResourceId());
                        if (resources != null) {
                            resourcesList.add(resources);
                        }
                    }
                }
            }
        }
        return resourcesList;
    }

    @Override
    public List<Resources> findAllResource() {
        ResourcesExample example = new ResourcesExample();
        return resourcesMapper.selectByExample(example);
    }

    @Override
    public List<Resources> findAllFather() {
        ResourcesExample example = new ResourcesExample();
        Criteria c = example.createCriteria();
        c.andParentIdEqualTo(1);
        example.setOrderByClause("id");
        return resourcesMapper.selectByExample(example);
    }

    // 插入用户的权限
    @Override
    public int batchInsertRights(List<RoleResources> allRights) {
        return roleResourcesMapper.batchInsertRoleresources(allRights);
    }

    // 根据角色的id来删除
    @Override
    public int deletRights(Integer roleId) {
        return roleResourcesMapper.deleteByRoleId(roleId);

    }

    @Override
    public List<Resources> getResourceByParentId(Integer parentId) {
        ResourcesExample example = new ResourcesExample();
        Criteria c = example.createCriteria();
        if (parentId != null) {
            c.andParentIdEqualTo(parentId);
        } else {
            c.andParentIdIsNull();
        }
        List<Resources> list = resourcesMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            for (Resources r : list) {
                ResourcesExample example2 = new ResourcesExample();
                example2.createCriteria().andParentIdEqualTo(r.getId());
                int count = resourcesMapper.countByExample(example2);
                String state = count > 0 ? "closed" : "open";
                r.setState(state);
            }
        }
        return list;
    }
}
