package com.goochou.p2b.model.vo;

/**
 * Created by wangxin on 2017/9/20.
 */
public class ActivityInvestAndLotteryVO {
    /*****/
    private Integer userId;
    /*****/
    private Integer type;
    /***投资总额***/
    private double investAmount;
    /***根据投资计算的年化额**/
    private double yearAmount;
    /****抽奖记录奖品价值金额&年化额**/
    private double lotteryAmount;
    /***已经使用的年化额**/
    private double useYearAmount;
    /***活动期间抽奖次数**/
    private Integer lotteryCount;
    /***剩余抽奖次数**/
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public double getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(double investAmount) {
        this.investAmount = investAmount;
    }

    public double getYearAmount() {
        return yearAmount;
    }

    public void setYearAmount(double yearAmount) {
        this.yearAmount = yearAmount;
    }

    public double getLotteryAmount() {
        return lotteryAmount;
    }

    public void setLotteryAmount(double lotteryAmount) {
        this.lotteryAmount = lotteryAmount;
    }

    public double getUseYearAmount() {
        return useYearAmount;
    }

    public void setUseYearAmount(double useYearAmount) {
        this.useYearAmount = useYearAmount;
    }

    public Integer getLotteryCount() {
        return lotteryCount;
    }

    public void setLotteryCount(Integer lotteryCount) {
        this.lotteryCount = lotteryCount;
    }
}
