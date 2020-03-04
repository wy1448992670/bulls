package com.goochou.p2b.model.vo;

import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.assets.AccountOperateTypeEnum;
import com.goochou.p2b.constant.assets.AccountTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.goochou.p2b.constant.Constants.SERVICE_TEL_STR;

/**
 * 交易记录
 * @author shuys
 * @since 2019/6/28 16:18
 */
public class TransactionRecordDetailVO implements Serializable{

    private static final long serialVersionUID = -3799338675957404111L;

    private String appShowAmount;

    private String payValue;

    private String createDate;

    private String successDate;

    private String orderNo;

    private String aoeType;

    private String aoeTypeMsg;

    private Integer accountOperateType;

    private String accountOperateTypeMsg;

    // 账户类型（现金，余额，授信余额）
    private Integer accountType;

    private String accountTypeMsg;

    private Map<String, String> label;

    public Map<String, String> getLabel() {
        Map<String, String> label = new HashMap<>();
        label.put("subordinate", "所属订单");
        label.put("doubt", "对此订单有疑问");
        label.put("telPhone", SERVICE_TEL_STR);
        label.put("completed", "已完成");
        AccountOperateTypeEnum accountOperateTypeEnum = AccountOperateTypeEnum.getValueByType(accountOperateType);
        if (accountOperateTypeEnum != null) {
            String operateType = accountOperateTypeEnum.getSignSymbolStr();
            if ("+".equals(operateType)) {
                label.put("payTitle", "钱款去向");
                label.put("time", "提现时间");
            } else {
                label.put("payTitle", "付款方式");
                label.put("time", "创建时间");
            }
        }
        return label;
    }

    public String getAppShowAmount() {
        return appShowAmount;
    }

    public void setAppShowAmount(String appShowAmount) {
        this.appShowAmount = appShowAmount;
    }

    public void setPayValue(String payValue) {
        this.payValue = payValue;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

//    public String getSuccessDate() {
//        return successDate;
//    }

    public void setSuccessDate(String successDate) {
        this.successDate = successDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setAoeType(String aoeType) {
        this.aoeType = aoeType;
    }

    public String getAoeTypeMsg() {
        if (aoeType == null) return "";
        return AccountOperateEnum.getValueByName(aoeType).getAppDescription();
    }

    public void setAoeTypeMsg(String aoeTypeMsg) {
        this.aoeTypeMsg = aoeTypeMsg;
    }

    public void setAccountOperateType(Integer accountOperateType) {
        this.accountOperateType = accountOperateType;
    }

//    public String getAccountOperateTypeMsg() {
//        return AccountOperateTypeEnum.getValueByType(accountOperateType).getDescription();
//    }

    public void setAccountOperateTypeMsg(String accountOperateTypeMsg) {
        this.accountOperateTypeMsg = accountOperateTypeMsg;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccountTypeMsg() {
        AccountTypeEnum accountTypeEnum = AccountTypeEnum.getValueByType(accountType);
        if (accountType == null || accountTypeEnum == null) return "";
        return accountTypeEnum.getAppDescription();
    }

    public void setAccountTypeMsg(String accountTypeMsg) {
        this.accountTypeMsg = accountTypeMsg;
    }
}
