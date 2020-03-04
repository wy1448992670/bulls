package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.Bank;
import com.goochou.p2b.model.BankExample;

public interface BankService {
    public List<Bank> list();

    /**
     * 根据银行code查询银行卡
     * 
     * @param code
     * @param type
     *            0 ec code 1连连2易宝3新浪
     * @return
     */
    public Bank getByCode(String code, Integer type);

    public Bank getByName(String name);

    public Bank getBankByBankCardId(Integer cardId);

    public Bank get(Integer id);

    /**
     * 获取银行列表
     * @param keyword
     * @author 刘源
     * @date 2016/4/27
     */
    List<Bank> getBankList(String keyword);

    /**
     * 分页查询银行记录
     * @param keyword
     * @param start
     *@param limit @author 刘源
     * @date 2016/5/18
     */
    List<Bank> query(String keyword, Integer start, Integer limit);

    /**
     * 查询银行记录总数
     * @param keyword
     * @author 刘源
     * @date 2016/5/18
     */
    Integer queryCount(String keyword);

    /**
     * 根据id查询银行信息
     * @param id
     * @author 刘源
     * @date 2016/5/18
     */
    Bank queryById(Integer id);

    /**
     * 保存银行信息
     * @param bank
     * @author 刘源
     * @date 2016/5/18
     */
    void save(Bank bank);

    /**
     * 查询所有可用银行列表
     * @param id
     * @author 刘源
     * @date 2016/5/19
     */
    List<Map<String,Object>> queryAvailableBankList(Integer id);
    
    /**
     * 根据设定的检索条件进行数据查询
     * @param example
     * @return
     */
    List<Bank> getBankListByExample(BankExample example);

}
