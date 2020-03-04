package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.InvestmentBlanceMapper;
import com.goochou.p2b.model.InvestmentBlance;
import com.goochou.p2b.model.InvestmentBlanceExample;
import com.goochou.p2b.service.InvestmentBlanceService;

/**
 * @Auther: huangsj
 * @Date: 2019/5/10 15:46
 * @Description:
 */
@Service
public class InvestmentBlanceServiceImpl implements InvestmentBlanceService {

    @Autowired
    private InvestmentBlanceMapper investmentBlanceMapper;


    @Override
    public InvestmentBlanceMapper getMapper(){
        return investmentBlanceMapper;
    }

    @Override
    public int add(Integer investId, Integer userId, BigDecimal totalInterest) {

        InvestmentBlance investmentBlance = new InvestmentBlance();
        investmentBlance.setInvestmentId(investId);
        investmentBlance.setUserId(userId);
        investmentBlance.setAmount(totalInterest);
        investmentBlance.setUseAmount(BigDecimal.ZERO);
        investmentBlance.setState(0);
        investmentBlance.setVersion(0);
        investmentBlance.setCreateDate(new Date());

        return investmentBlanceMapper.insertSelective(investmentBlance);

    }

    @Override
    public InvestmentBlance findByInvestId(int investId) {
        InvestmentBlanceExample example = new InvestmentBlanceExample();
        InvestmentBlanceExample.Criteria c = example.createCriteria();
        c.andInvestmentIdEqualTo(investId);

        List<InvestmentBlance> orders = investmentBlanceMapper.selectByExample(example);
        if (orders != null && orders.size() > 0) {
            return orders.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<InvestmentBlance> findAvailableCreditBalanceInvestList(int userId) {
        return investmentBlanceMapper.selectAvailableCreditBalanceInvestList(userId);
    }

    /**
   	 * selectByPrimaryKey 的 for update 版
   	 * @param id
   	 * @return
   	 */
   	@Override
   	public InvestmentBlance selectByPrimaryKeyForUpdate(Integer id) {
   		return investmentBlanceMapper.selectByPrimaryKeyForUpdate(id);
   	}

   	/**
   	 * 通过investmentId查找InvestmentBlance
   	 * @param id
   	 * @return
   	 * @throws Exception 
   	 */
   	@Override
   	public InvestmentBlance selectByInvestmentId(Integer investmentId) throws Exception {
   		InvestmentBlanceExample example = new InvestmentBlanceExample();
   		InvestmentBlanceExample.Criteria c = example.createCriteria();
           c.andInvestmentIdEqualTo(investmentId);
           
           List<InvestmentBlance> investmentBlanceList = this.getMapper().selectByExample(example);
           if(investmentBlanceList.size()!=1) {
           	throw new Exception("InvestmentBlance数据错误!");
           }
   		
   		return investmentBlanceList.get(0);
    }
   	
   	@Override
	public Map<String, Object> countInvestmentBlance(Integer userId,Integer state) {
		return this.getMapper().countInvestmentBlance(userId,state);
	}
   	
   	@Override
   	public List<Map<String, Object>> listInvestmentBlanceByPage(Integer userId,Integer limitStart,Integer limitEnd,Integer state){
   		return this.getMapper().listInvestmentBlanceByPage(userId, limitStart, limitEnd,state);
   	}
   	
   	@Override
   	public int countListInvestmentBlance(Integer userId,Integer state) {
   		return this.getMapper().countListInvestmentBlance(userId,state);
   	}
}
