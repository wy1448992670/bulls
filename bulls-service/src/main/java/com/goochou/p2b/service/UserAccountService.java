package com.goochou.p2b.service;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.TradeRecord;

import java.math.BigDecimal;

/**
 * @Auther: huangsj
 * @Date: 2019/5/9 16:28
 * @Description:
 */
public interface UserAccountService {

	int modifyAccount(Assets assets, BigDecimal money, Integer businessId, BusinessTableEnum businessTableEnum,
			AccountOperateEnum accountOperateEnum) throws Exception;
	
}
