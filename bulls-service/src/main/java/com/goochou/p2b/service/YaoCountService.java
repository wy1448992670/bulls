package com.goochou.p2b.service;

import com.goochou.p2b.model.YaoCount;

import java.util.List;
import java.util.Map;

public interface YaoCountService {

    YaoCount getById(Integer id);

    void update(YaoCount yc);

    void save(YaoCount yc);


    public YaoCount updateGetByUserId(Integer userId);

    /**
     */
    public Map<String, String> updateYaoYao(Integer id, String client);


    /**
     * @Description(描述):摇一摇定时器
     * @author 王信
     * @date 2016/4/28
     * @params userId  用户id  type  -1  查询用户当天的累计收益    type 0 查询用户前一天的累计收益
     **/
    public void updateCount(Integer userId, Double investAmount);

    public void updateCount(Integer userId);

    /**
     * @Description(描述):查询所有大于五十块的投资用户 更新并派发次数。
     * @author 王信
     * @date 2016/5/5
     * @params
     **/
    public List<Integer> selectAllIncomeUser();

}
