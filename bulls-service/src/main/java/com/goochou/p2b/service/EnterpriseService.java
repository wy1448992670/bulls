package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.Enterprise;

public interface EnterpriseService {

    /**
     * 企业根据关键词分页查询
     *
     * @param keyword
     * @param start
     * @param limit
     * @return 记录总数 count，结果list
     */
    public List<Enterprise> query(String keyword, int start, int limit,Integer type) throws Exception;

    public Integer queryCount(String keyword,Integer type);

    public Enterprise detail(Integer id);

    /**
     * 验证企业名是否存在
     *
     * @param name
     * @return true 不存在, false存在
     * @throws Exception
     */
    public boolean checkNameExists(String username, Integer id) throws Exception;

    /**
     * 保存企业同时更新图片关联
     *
     * @param enterprise
     * @param picture
     */
    public void saveWithPicture(Enterprise enterprise, String picture) throws Exception;
    public void saveWithPicture(Enterprise enterprise, String picture, String picture2) throws Exception;

    /**
     * 编辑企业信息
     *
     * @param enterprise
     * @param picture
     */
    public void update(Enterprise enterprise, String picture) throws Exception;
    public void update(Enterprise enterprise, String picture,String picture2) throws Exception;

    /**
     * @Description: 根据投资ID   查询对应项目的企业公章图片路径
     * @date  2016/11/21
     * @author 王信
     */
    public String selectEnterpriseSealPath(Integer investmentId);

    public Enterprise selectByPrimaryKey(Integer id);

    public Enterprise selectByUserId(Integer userId);

    public Enterprise selectFirstRecord();
}
