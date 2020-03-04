package com.goochou.p2b.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.WithdrawBlackMapper;
import com.goochou.p2b.model.WithdrawBlack;
import com.goochou.p2b.model.WithdrawBlackExample;
import com.goochou.p2b.service.WithdrawBlackService;

@Service
public class WithdrawBlackServiceImpl implements WithdrawBlackService {

    @Resource
    private WithdrawBlackMapper withdrawBlackMapper;

    @Override
    public List<Map<String, Object>> list(String keywords, Integer start, Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void add(WithdrawBlack wb) {
        withdrawBlackMapper.insert(wb);
    }

    @Override
    public Integer get(Integer userId) {
        WithdrawBlackExample example = new WithdrawBlackExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return withdrawBlackMapper.countByExample(example);
    }

}
