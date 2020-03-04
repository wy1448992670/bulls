package com.goochou.p2b.model.vo;

import com.goochou.p2b.constant.InterestHasDividendedEnum;

import java.util.Date;

public class InterestVO {

    private Integer id;

    // 类型：1定期
    private Integer type;

    private String typeDesc;

    private Integer userId;

    // 真实姓名
    private String trueName;

    // 手机号
    private String phone;


    private Integer investmentId;

    // 利息
    private Double interestAmount;

    // 本金
    private Double capitalAmount;

    private Date date;

    // 项目期数
    private Integer stage;

    // 0：未派息，1：已派息 2：已计算（未实际发放）
    private Integer hasDividended;

    // 0：未派息，1：已派息 2：已计算（未实际发放）
    private String hasDividendedDesc;

    // 加息金额
    private Double addInterest;

    private Date createDate;

    private Date updateDate;

    private String orderNo;

    // 总利息
    private String interestAmountTotal;

    // 投资时间
    private Date investmentCreateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        String typeDesc = this.typeDesc;
        if (type == 1) {
            typeDesc = "定期";
        }
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Double getCapitalAmount() {
        return capitalAmount;
    }

    public void setCapitalAmount(Double capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getHasDividended() {
        return hasDividended;
    }

    public void setHasDividended(Integer hasDividended) {
        this.hasDividended = hasDividended;
    }

    public String getHasDividendedDesc() {
        Integer code =  this.hasDividended;
        InterestHasDividendedEnum dividendedEnum = InterestHasDividendedEnum.getValueByType(code);
        return dividendedEnum.getDescription();
    }

    public void setHasDividendedDesc(String hasDividendedDesc) {
        this.hasDividendedDesc = hasDividendedDesc;
    }

    public Double getAddInterest() {
        return addInterest;
    }

    public void setAddInterest(Double addInterest) {
        this.addInterest = addInterest;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInterestAmountTotal() {
        return interestAmountTotal;
    }

    public void setInterestAmountTotal(String interestAmountTotal) {
        this.interestAmountTotal = interestAmountTotal;
    }

    public Date getInvestmentCreateDate() {
        return investmentCreateDate;
    }

    public void setInvestmentCreateDate(Date investmentCreateDate) {
        this.investmentCreateDate = investmentCreateDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
