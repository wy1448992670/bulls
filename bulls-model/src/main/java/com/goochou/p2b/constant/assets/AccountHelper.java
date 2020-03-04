package com.goochou.p2b.constant.assets;

import java.util.HashMap;
import java.util.Map;



public class AccountHelper
{
	/**
     * 操作类型"+"
     */
    public final static String ADD = ""; 
    
    
    /**
     * 操作类型"-"
     */
    public final static String CUT = "-";
    
	public static Map<String, String> operateMap = new HashMap<String, String>() {
        /**
         * <p>Discription:[字段功能描述]</p>
         */
        private static final long serialVersionUID = -6018067292594277273L;

        {
        	/*
            //提现
            put(AccountTypeEnum.FROZEN.getFeatureName()+"_"+AccountOperateEnum.WITHDRAW.getFeatureName(), ADD);
            put(AccountTypeEnum.BLANCE.getFeatureName()+"_"+AccountOperateEnum.WITHDRAW.getFeatureName(), CUT);
            //畜牧下单
            put(AccountTypeEnum.FROZEN.getFeatureName()+"_"+AccountOperateEnum.INVEST_BALANCE_PAY.getFeatureName(), ADD);
            put(AccountTypeEnum.BLANCE.getFeatureName()+"_"+AccountOperateEnum.INVEST_BALANCE_PAY.getFeatureName(), CUT);
            put(AccountTypeEnum.CREDITFROZEN.getFeatureName()+"_"+AccountOperateEnum.INVEST_BALANCE_PAY.getFeatureName(), ADD);
            put(AccountTypeEnum.CREDIT.getFeatureName()+"_"+AccountOperateEnum.INVEST_BALANCE_PAY.getFeatureName(), CUT);
            //电商下单
            put(AccountTypeEnum.FROZEN.getFeatureName()+"_"+AccountOperateEnum.BUYGOODS_BALANCE_PAY.getFeatureName(), ADD);
            put(AccountTypeEnum.BLANCE.getFeatureName()+"_"+AccountOperateEnum.BUYGOODS_BALANCE_PAY.getFeatureName(), CUT);
            put(AccountTypeEnum.CREDITFROZEN.getFeatureName()+"_"+AccountOperateEnum.BUYGOODS_BALANCE_PAY.getFeatureName(), ADD);
            put(AccountTypeEnum.CREDIT.getFeatureName()+"_"+AccountOperateEnum.BUYGOODS_BALANCE_PAY.getFeatureName(), CUT);
            //赎回
            put(AccountTypeEnum.BLANCE.getFeatureName()+"_"+AccountOperateEnum.PAYMENT.getFeatureName(), ADD);
            put(AccountTypeEnum.BLANCE.getFeatureName()+"_"+AccountOperateEnum.INTEREST.getFeatureName(), ADD);
            //使用红包
            put(AccountTypeEnum.BLANCE.getFeatureName()+"_"+AccountOperateEnum.USEHONGBAO.getFeatureName(), ADD);
            */
        }
    };
    
    /**
     *  Created on 2014-8-12 
     * <p>Discription:[需要传入操作类型枚举加账户类型返回'+','-'；扣钱还是加钱]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return String .
     */
    public static String getAccountOperate(AccountTypeEnum ate, AccountOperateEnum aoe){
        String accountOperate = "";
        if(operateMap.containsKey(ate.getFeatureName()+"_"+aoe.getFeatureName())){
            accountOperate = operateMap.get(ate.getFeatureName()+"_"+aoe.getFeatureName());
        }else{
            throw new RuntimeException("未找到相关枚举+，-配置");
        }
        return accountOperate;
    }
}
