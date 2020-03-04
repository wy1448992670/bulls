package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.WithdrawTempMapper;
import com.goochou.p2b.model.WithdrawTemp;
import com.goochou.p2b.model.WithdrawTempExample;
import com.goochou.p2b.service.WithdrawTempService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created by qitianjia on 2017/9/22.
 */
@Service
public class WithdrawTempServiceImpl implements WithdrawTempService {
    @Resource
    private WithdrawTempMapper withdrawTempMapper;

	@Override
	public int update(WithdrawTemp withdrawTemp) {
		return withdrawTempMapper.updateByPrimaryKey(withdrawTemp);
	}

	@Override
	public List<WithdrawTemp> selectByExample(WithdrawTempExample example) {
		return withdrawTempMapper.selectByExample(example);
	}

    @Override
    public List<WithdrawTemp> queryWithdraw(Integer type, Date sendDate) {
         return withdrawTempMapper.queryWithdraw(type, sendDate);
    }

}
