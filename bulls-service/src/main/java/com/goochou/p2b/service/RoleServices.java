package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.Role;

public interface RoleServices {

    /**
     * 查询所有角色分页
     */
    public List<Role> findAllRole(int start, int limit, String keyword);

    /**
     * 添加角色
     */
    public int save(Role role);

    /**
     * 删除角色
     */
    public int delete(int id);

    public int update(Role role);

    public Role detail(int id);

    public int getCount(String keyword);

    /**
     * 检测角色名是否已存在
     */
    public boolean checkNameExists(String role);

    /**
     * 根据角色编号来查询权限列表
     */
    public List<Resources> findById(int roleId);

    /**
     * 获取角色
     */
    public List<Role> findAllRole();

}
