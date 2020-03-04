package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.AdminRole;

public interface AdminRoleService {

    /**
     * 根据用户的id获取AdminRole
     */
    public List<AdminRole> getByUserId(int userId) throws Exception;

    /**
     * 更新用户的AdminRole
     */
    public void update(AdminRole adminRole);

    /**
     * 添加
     */
    public void save(AdminRole adminRole);

    /**
     * 删除
     */
    public void delete(int id);
}
