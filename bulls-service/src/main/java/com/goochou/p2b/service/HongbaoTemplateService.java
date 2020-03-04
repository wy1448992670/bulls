package com.goochou.p2b.service;

import com.goochou.p2b.model.HongbaoTemplate;

import java.util.List;
import java.util.Map;

/**
 * liuyuan
 * 2016/6/23.
 */
public interface HongbaoTemplateService {
    /**
     * 查询红包模板列表
     * @param keyword
     * @param type
     *@param status
     * @param start
     * @param limit   @return
     */
    List<Map<String,Object>> query(String keyword, Integer type, Integer status, Integer start, Integer limit);

    /**
     * 查询红包模板总数
     * @param keyword
     * @param type
     *@param status @return
     */
    Integer queryCount(String keyword, Integer type, Integer status);

    /**
     * 根据id查询红包模板
     * @param id
     * @author 刘源
     * @date 2016/6/23
     */
    Map<String,Object> queryById(Integer id);

    /**
     * 保存或更新红包模板
     * @param template
     * @author 刘源
     * @date 2016/6/23
     */
    void save(HongbaoTemplate template);

    /**
     * 查询兑换可兑换的红包信息
     * @param redeemId
     * @author 刘源
     * @date 2016/7/7
     */
    List<HongbaoTemplate> queryByRedeemId(Integer redeemId);
}
