package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.RoleResources;

/**
 * 权限
 */
public interface ResourcesService {

    /**
     * 显示所有的权限
     */
    public List<Resources> findAllResource(int start, int limit, String keyword);

    public List<Resources> getResourceByParentId(Integer parentId);

    /**
     * 查询总数
     */
    public int getCount(String keyword);

    // 详情
    public Resources detail(int id);

    // 修改
    public int update(Resources resources);

    // 添加
    public int save(Resources resources);

    //
    public int delete(int id);

    //
    public boolean checkNameOrUrlExists(String name, String url, String pression);

    /**
     * 根据角色编号来查询权限列表
     */
    public List<Resources> findByroleId(int roleId);

    public List<Resources> findAllResource();

    // 获取父id为0 的权限列表
    public List<Resources> findAllFather();

    public int deletRights(Integer roleId);

    public int batchInsertRights(List<RoleResources> allRights);
}
