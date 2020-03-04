package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;

import com.goochou.p2b.model.WithdrawTemp;
import com.goochou.p2b.model.WithdrawTempExample;

/**
 * Created by qiutianjia on 2017/9/22.
 */
public interface WithdrawTempService {

	int update(WithdrawTemp withdrawTemp);

	List<WithdrawTemp> selectByExample(WithdrawTempExample example);

    /**
     * @param type
     * @param sendDate
     * @return
     */
    List<WithdrawTemp> queryWithdraw(Integer type, Date sendDate);

}
