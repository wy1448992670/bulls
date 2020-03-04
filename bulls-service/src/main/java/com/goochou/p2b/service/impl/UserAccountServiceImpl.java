package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.TradeRecord;
import com.goochou.p2b.service.GoodsOrderInvestRelationService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserAccountService;
import com.goochou.p2b.utils.BigDecimalUtil;
import static com.goochou.p2b.constant.assets.AccountTypeEnum.*;
import static com.goochou.p2b.constant.assets.AccountOperateTypeEnum.*;

/**
 * @Auther: huangsj
 * @Date: 2019/5/9 16:28
 * @Description:
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private TradeRecordService tradeRecordervice;
    @Autowired
	private GoodsOrderInvestRelationService relationService;
    
    @Override
    public int modifyAccount(Assets assets, BigDecimal money, Integer businessId, BusinessTableEnum businessTableEnum, AccountOperateEnum accountOperateEnum) throws Exception {
        if(money.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("账户操作数不能是负数");
        }else if (money.compareTo(BigDecimal.ZERO) == 0) {
            return 1;
        }
        if(accountOperateEnum.getAccountType().equals(CASH)) {
        	
        }else if(accountOperateEnum.getAccountType().equals(BALANCE)) {
        	if(accountOperateEnum.getAccountOperateType().equals(ADD)) {
        		assets.setBalanceAmount(BigDecimalUtil.parse(assets.getBalanceAmount()).add(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(SUBTRACT)) {
        		if(money.compareTo(BigDecimal.valueOf(assets.getBalanceAmount()))>0) {
        			throw new Exception("账户余额不足");
        		}
        		assets.setBalanceAmount(BigDecimalUtil.parse(assets.getBalanceAmount()).subtract(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(FROZEN_ADD)) {
        		assets.setFrozenAmount(BigDecimalUtil.parse(assets.getFrozenAmount()).add(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(FROZEN_SUBTRACT)) {
        		if(money.compareTo(BigDecimal.valueOf(assets.getFrozenAmount()))>0) {
        			throw new Exception("账户冻结余额不足");
        		}
        		assets.setFrozenAmount(BigDecimalUtil.parse(assets.getFrozenAmount()).subtract(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(UNFREEZE)) {
        		if(money.compareTo(BigDecimal.valueOf(assets.getFrozenAmount()))>0) {
        			throw new Exception("账户冻结余额不足");
        		}
        		assets.setFrozenAmount(BigDecimalUtil.parse(assets.getFrozenAmount()).subtract(money).doubleValue());
        		assets.setBalanceAmount(BigDecimalUtil.parse(assets.getBalanceAmount()).add(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(FREEZE)) {
        		if(money.compareTo(BigDecimal.valueOf(assets.getBalanceAmount()))>0) {
        			throw new Exception("账户余额不足");
        		}
        		assets.setBalanceAmount(BigDecimalUtil.parse(assets.getBalanceAmount()).subtract(money).doubleValue());
        		assets.setFrozenAmount(BigDecimalUtil.parse(assets.getFrozenAmount()).add(money).doubleValue());
        	}
        }else if(accountOperateEnum.getAccountType().equals(CREDIT)) {
        	if(accountOperateEnum.getAccountOperateType().equals(ADD)) {
        		assets.setCreditAmount(BigDecimalUtil.parse(assets.getCreditAmount()).add(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(SUBTRACT)) {
        		if(money.compareTo(BigDecimal.valueOf(assets.getCreditAmount()))>0) {
        			throw new Exception("账户信用额度不足");
        		}
        		assets.setCreditAmount(BigDecimalUtil.parse(assets.getCreditAmount()).subtract(money).doubleValue());
        		//非回购利息兑付
        		if(accountOperateEnum == AccountOperateEnum.VIPDIVIDEND_CREDIT_SUBTRACT) {
        			// 添加授信金额出处,方便退单时返回,使用就加,没有中间状态,用户利息发放记录
            		relationService.addRelationWhenUseCreditBalance(assets.getUserId(), businessId,money, true, 2);
            		
        		} else if(accountOperateEnum!=AccountOperateEnum.INVEST_BUYBACK_INTEREST_CREDIT_SUBTRACT) {
        			// 添加授信金额出处,方便退单时返回,使用就加，没有中间状态
            		relationService.addRelationWhenUseCreditBalance(assets.getUserId(), businessId,money, true, 1);
        		}
        	}else if(accountOperateEnum.getAccountOperateType().equals(FROZEN_ADD)) {
        		assets.setFreozenCreditAmount(BigDecimalUtil.parse(assets.getFreozenCreditAmount()).add(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(FROZEN_SUBTRACT)) {
        		if(money.compareTo(BigDecimal.valueOf(assets.getFreozenCreditAmount()))>0) {
        			throw new Exception("账户冻结信用额度不足");
        		}
        		assets.setFreozenCreditAmount(BigDecimalUtil.parse(assets.getFreozenCreditAmount()).subtract(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(UNFREEZE)) {
        		if(money.compareTo(BigDecimal.valueOf(assets.getFreozenCreditAmount()))>0) {
        			throw new Exception("账户冻结信用额度不足");
        		}
        		assets.setFreozenCreditAmount(BigDecimalUtil.parse(assets.getFreozenCreditAmount()).subtract(money).doubleValue());
        		assets.setCreditAmount(BigDecimalUtil.parse(assets.getCreditAmount()).add(money).doubleValue());
        	}else if(accountOperateEnum.getAccountOperateType().equals(FREEZE)) {
        		if(money.compareTo(BigDecimal.valueOf(assets.getCreditAmount()))>0) {
        			throw new Exception("账户信用额度不足");
        		}
        		assets.setCreditAmount(BigDecimalUtil.parse(assets.getCreditAmount()).subtract(money).doubleValue());
        		assets.setFreozenCreditAmount(BigDecimalUtil.parse(assets.getFreozenCreditAmount()).add(money).doubleValue());
        		// 添加授信金额出处,方便退单时返回,使用就加，没有中间状态
        		relationService.addRelationWhenUseCreditBalance(assets.getUserId(), businessId,money, true, 1);
        	}
        }

        //添加资金纪录
        return tradeRecordervice.addRecord(assets, money, businessId, businessTableEnum, accountOperateEnum);
    }

    private TradeRecord initTradeRecord(Assets assets, BigDecimal money, BusinessTableEnum businessTableEnum, AccountOperateEnum accountOperateEnum) {

        TradeRecord record = new TradeRecord();
        record.setUserId(assets.getUserId());

        record.setTableName(businessTableEnum.name());

        record.setAmount(money.abs().doubleValue());
        record.setBalanceAmount(assets.getBalanceAmount());
        record.setFrozenAmount(assets.getFrozenAmount());
        record.setCreditAmount(assets.getCreditAmount());
        record.setFrozenCreditAmount(assets.getFreozenCreditAmount());

        record.setAoeType(accountOperateEnum.getFeatureName());

        record.setCreateDate(new Date());
        return record;
    }
}

