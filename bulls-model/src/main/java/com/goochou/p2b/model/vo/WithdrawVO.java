package com.goochou.p2b.model.vo;

import com.goochou.p2b.constant.WithdrawStatusEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.utils.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithdrawVO implements Serializable {

    private static final long serialVersionUID = -6130191021139678918L;

    private Integer id;

    private Integer userId;

    private String trueName;

    private String phone;

    private Integer type;

    // 提现类型
    private String withdrawals;

    private String orderNo;

    private BigDecimal amount;

    // 实际到账金额
    private BigDecimal realAmount;

    // 要提现的卡号
    private String cardNo;

    private String payChannel;

    private String client;

    private Integer bankId;

    private String bankName;

    // 提现申请时间
    private Date createDate;

    // 操作员ID   技术
    private String techOperateUserId;

    // 操作员  技术
    private String techOperateUserName;

    // 操作时间  技术
    private Date techOperateTime;

    // 备注  技术
    private String techRemark;

    // 操作员Id  财务
    private String financeOperateUserId;

    // 操作员  财务
    private String financeOperateUserName;

    // 操作时间  财务
    private Date financeOperateTime;

    // 备注  财务
    private String financeRemark;

    // 提现状态
    private Integer status;

    // 提现状态
    private String statusMsg;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getWithdrawals() {
        if (type == 0) {
            return "快速提现";
        }
        if (type == 1) {
            return "普通提现";
        }
        return "";
    }

    public void setWithdrawals(String withdrawals) {
        this.withdrawals = withdrawals;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public String getCardNo() {
        if (StringUtils.isNotBlank(bankName)) {
            return cardNo + "(" + bankName + ")";
        }
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPayChannel() {
        OutPayEnum outPayEnum = OutPayEnum.getValueByName(payChannel);
        return outPayEnum.getDescription();
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTechOperateUserId() {
        return techOperateUserId;
    }

    public void setTechOperateUserId(String techOperateUserId) {
        this.techOperateUserId = techOperateUserId;
    }

    public String getTechOperateUserName() {
        return techOperateUserName;
    }

    public void setTechOperateUserName(String techOperateUserName) {
        this.techOperateUserName = techOperateUserName;
    }

    public Date getTechOperateTime() {
        return techOperateTime;
    }

    public void setTechOperateTime(Date techOperateTime) {
        this.techOperateTime = techOperateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMsg() {
        WithdrawStatusEnum withdrawStatusEnum = WithdrawStatusEnum.getValueByCode(status);
        return withdrawStatusEnum.getDescription();
    }

    public String getTechRemark() {
        return techRemark;
    }

    public void setTechRemark(String techRemark) {
        this.techRemark = techRemark;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getFinanceOperateUserId() {
        return financeOperateUserId;
    }

    public void setFinanceOperateUserId(String financeOperateUserId) {
        this.financeOperateUserId = financeOperateUserId;
    }

    public String getFinanceOperateUserName() {
        return financeOperateUserName;
    }

    public void setFinanceOperateUserName(String financeOperateUserName) {
        this.financeOperateUserName = financeOperateUserName;
    }

    public Date getFinanceOperateTime() {
        return financeOperateTime;
    }

    public void setFinanceOperateTime(Date financeOperateTime) {
        this.financeOperateTime = financeOperateTime;
    }

    public String getFinanceRemark() {
        return financeRemark;
    }

    public void setFinanceRemark(String financeRemark) {
        this.financeRemark = financeRemark;
    }
}
