package com.goochou.p2b.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.dao.InvestmentBlanceMapper;
import com.goochou.p2b.model.InvestmentBlance;

/**
 * @Auther: huangsj
 * @Date: 2019/5/10 15:46
 * @Description:
 */
public interface InvestmentBlanceService {


    InvestmentBlanceMapper getMapper();

    /**
     * 查找用户的投资授信订单中还有可用的授信资金的订单 优先用最后赎回的订单的授信资金
     * @param userId
     * @return
     */
    List<InvestmentBlance> findAvailableCreditBalanceInvestList(int userId);


    InvestmentBlance findByInvestId(int investId);


    int add(Integer investId, Integer userId, BigDecimal totalInterest);
    
	/**
	 * selectByPrimaryKey 的 for update 版
	 * @param id
	 * @return
	 */
    InvestmentBlance selectByPrimaryKeyForUpdate(Integer id);
    
    /**
	 * 通过investmentId查找InvestmentBlance
	 * @param id
	 * @return
     * @throws Exception 
	 */
    InvestmentBlance selectByInvestmentId(Integer investmentId) throws Exception;

	/** 
	 * 根据用户id 统计该用户预支回报的总额
	* @Title: countInvestmentBlance 
	* @param userId
	* @return {@link Map}
	* @author zj
	* @date 2019-06-17 16:19
	*/ 
	Map<String, Object> countInvestmentBlance(Integer userId,Integer state);

	/** 
	 * 获取用户授信金额订单明细
	* @Title: listInvestmentBlance 
	* @param userId
	* @return List<Map<String,Object>>
	* @author zj
	* @date 2019-06-17 17:40
	*/ 
	List<Map<String, Object>> listInvestmentBlanceByPage(Integer userId,Integer limitStart,Integer limitEnd,Integer state);

	/** 
	 * 统计数量
	* @Title: countListInvestmentBlance 
	* @param userId
	* @return int
	* @author zj
	* @date 2019-06-18 11:41
	*/ 
	int countListInvestmentBlance(Integer userId,Integer state);
    
    
}
