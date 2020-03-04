package com.goochou.p2b.model.vo;

/**
 * 每个用户对账文件
 * 
 * @author irving
 * 
 */
public class AccounChecking {
    private String username;
    private String phone;
    private String trueName;
    private Double rechargeAmount;
    private Double interestAmount;
    private Double hbAmount;
    private Double withdrawAmount;
    private Double huoInvestAmount;
    private Double frozenAmount;
    private Double balance;
    private Double margin; // 差额

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Double frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Double getHbAmount() {
        return hbAmount;
    }

    public void setHbAmount(Double hbAmount) {
        this.hbAmount = hbAmount;
    }

    public Double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Double getHuoInvestAmount() {
        return huoInvestAmount;
    }

    public void setHuoInvestAmount(Double huoInvestAmount) {
        this.huoInvestAmount = huoInvestAmount;
    }

}
