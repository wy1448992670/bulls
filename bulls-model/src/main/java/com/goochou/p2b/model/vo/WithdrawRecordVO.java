package com.goochou.p2b.model.vo;

import com.goochou.p2b.constant.WithdrawStatusEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.utils.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * 提现记录  app
 * @author shuys
 * @since 2019/6/28 10:12
 */
public class WithdrawRecordVO implements Serializable {

    private static final long serialVersionUID = 3072097473487206055L;

    private Integer id;

    // 提现类型
    private Integer type;

    // 提现类型 message
    private String typeMsg;

    // 提现订单号
    private String orderNo;

    // 实际到账金额
    private BigDecimal amount;

    // 银行卡号
    private String cardNo;

    // 银行编号
    private String bankCode;

    // 银行名称
    private String bankName;

    // 支付渠道
    private String payChannel;

    // 申请时间
    private Date createDate;

    // 更新时间
    private Date updateDate;

    // 成功
    private String successDate;

    // 状态
    private Integer status;

    // 状态描述
    private String statusMsg;

    private String overDate;

    private String createDateStr;

    private String updateDateStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

//    public String getTypeMsg() {
//        return typeMsg;
//    }

    public void setTypeMsg(String typeMsg) {
        this.typeMsg = typeMsg;
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

    public String getCardNo() {
        return bankName + "(" + StringUtils.parseLastFourCardNo(cardNo) + ")";
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPayChannel() {
        return OutPayEnum.getValueByName(payChannel).getDescription();
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getSuccessDate() {
        if (updateDate == null) return null;
        if (WithdrawStatusEnum.SUCCESS.getCode().equals(status) || WithdrawStatusEnum.SUCCESS.getCode() == status) {
            return DateFormatTools.dateToStr1(updateDate);
        }
        return null;
    }

    public void setSuccessDate(String successDate) {
        this.successDate = successDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return WithdrawStatusEnum.getValueByCode(status).getDescription();
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getOverDate() throws ParseException {
        if (createDate == null) return null;
        return CommonUtils.getTime(createDate, type);
    }

    public String getCreateDateStr() {
        if (createDate == null) return null;
        return DateFormatTools.dateToStr1(createDate);
    }

    public String getUpdateDateStr() {
        if (updateDate == null) return null;
        return DateFormatTools.dateToStr1(updateDate);
    }
}
