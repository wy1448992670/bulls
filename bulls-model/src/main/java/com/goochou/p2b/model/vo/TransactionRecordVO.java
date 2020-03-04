package com.goochou.p2b.model.vo;

import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.assets.AccountOperateTypeEnum;
import com.goochou.p2b.constant.assets.AccountTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易记录
 * @author shuys
 * @since 2019/6/13 14:38
 */
public class TransactionRecordVO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -8899930567688075195L;

    private Integer id;

    private Integer userId;

    private Integer otherId;

    private String tableName;

    // 操作类型
    private String aoeType;

    private String aoeTypeMsg;

    private String adminAoeTypeMsg;

    // 操作金额
    private BigDecimal amount;

    // 当前账户余额
    private BigDecimal balanceAmount;

    // 当前账户冻结余额
    private BigDecimal frozenAmount;

    // 操作后授信资金
    private BigDecimal creditAmount;

    // 操作后冻结的授信资金
    private BigDecimal frozenCreditAmount;

    // 创建时间
    private String createDate;

    // 账户类型（现金，余额，授信余额）
    private Integer accountTypeId;

    private String accountTypeMsg;

    // 资金操作类型
    private Integer accountOperateTypeId;

    private String accountOperateTypeMsg;

    // 真实姓名
    private String trueName;

    // 手机号
    private String phone;

    private String appShowAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOtherId() {
        return otherId;
    }

    public void setOtherId(Integer otherId) {
        this.otherId = otherId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAoeType() {
        return aoeType;
    }

    public void setAoeType(String aoeType) {
        this.aoeType = aoeType;
    }

    public String getAoeTypeMsg() {
        AccountOperateEnum accountOperateEnum = AccountOperateEnum.getValueByName(aoeType);
        if (aoeType == null || accountOperateEnum == null) return "";
        return accountOperateEnum.getDescription();
    }

    public void setAoeTypeMsg(String aoeTypeMsg) {
        this.aoeTypeMsg = aoeTypeMsg;
    }

    public String getAdminAoeTypeMsg() {
        AccountOperateEnum accountOperateEnum = AccountOperateEnum.getValueByName(aoeType);
        if (aoeType == null || accountOperateEnum == null) return "";
        String result = accountOperateEnum.getDescription()
                + " ("+ accountOperateEnum.getAccountType().getDescription() + "："
                + accountOperateEnum.getAccountOperateType().getDescription() +")";
        return result;
    }

    public void setAdminAoeTypeMsg(String adminAoeTypeMsg) {
        this.adminAoeTypeMsg = adminAoeTypeMsg;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getFrozenCreditAmount() {
        return frozenCreditAmount;
    }

    public void setFrozenCreditAmount(BigDecimal frozenCreditAmount) {
        this.frozenCreditAmount = frozenCreditAmount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Integer getAccountOperateTypeId() {
        return accountOperateTypeId;
    }

    public void setAccountOperateTypeId(Integer accountOperateTypeId) {
        this.accountOperateTypeId = accountOperateTypeId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountTypeMsg() {
        AccountTypeEnum accountTypeEnum = AccountTypeEnum.getValueByType(accountTypeId);
        if (accountTypeId == null || accountTypeEnum == null) return "";
        return accountTypeEnum.getAppDescription();
    }

    public void setAccountTypeMsg(String accountTypeMsg) {
        this.accountTypeMsg = accountTypeMsg;
    }

    public String getAccountOperateTypeMsg() {
        AccountOperateTypeEnum accountOperateTypeEnum = AccountOperateTypeEnum.getValueByType(accountOperateTypeId);
        if (accountOperateTypeId == null || accountOperateTypeEnum == null) return "";
        return accountOperateTypeEnum.getDescription();
    }

    public void setAccountOperateTypeMsg(String accountOperateTypeMsg) {
        this.accountOperateTypeMsg = accountOperateTypeMsg;
    }

    public String getAppShowAmount() {
        AccountOperateTypeEnum accountOperateTypeEnum = AccountOperateTypeEnum.getValueByType(accountOperateTypeId);
        if (accountOperateTypeId == null || accountOperateTypeEnum == null) return "";
        String signSymbol = accountOperateTypeEnum.getSignSymbolStr(); // 操作符
        return signSymbol + amount;
    }

    public void setAppShowAmount(String appShowAmount) {
        this.appShowAmount = appShowAmount;
    }
}
