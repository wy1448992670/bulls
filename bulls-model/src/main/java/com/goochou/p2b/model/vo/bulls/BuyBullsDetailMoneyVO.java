package com.goochou.p2b.model.vo.bulls;

import com.goochou.p2b.utils.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * @author: huangsj
 * @Date: 2019/10/30 16:26
 * @Description:
 */
public class BuyBullsDetailMoneyVO {

    private BigDecimal unitFeedMoney;
    private BigDecimal unitManageMoney;
    private BigDecimal unitAddWeightMoney;
    //肉单价/斤
    private BigDecimal unitBullPrice;

    private BigDecimal bullMoney;
    private BigDecimal feedMoney;
    private BigDecimal manageMoney;
    private BigDecimal totalMoney;
    private BigDecimal totalInterest;

    private String bullMoneyStr;
    private String feedMoneyStr;
    private String manageMoneyStr;
    private String totalMoneyStr;
    private String totalInterestStr;

    private BigDecimal cheapMoney;



    private String bullMoneyStyle;
    private String totalInterestStyle;
    private String cheapMoneyStyle;

    public BigDecimal getBullMoney() {
        return bullMoney;
    }

    public void setBullMoney(BigDecimal bullMoney) {
        this.bullMoney = bullMoney;
    }

    public BigDecimal getFeedMoney() {
        return feedMoney;
    }

    public void setFeedMoney(BigDecimal feedMoney) {
        this.feedMoney = feedMoney;
    }

    public BigDecimal getManageMoney() {
        return manageMoney;
    }

    public void setManageMoney(BigDecimal manageMoney) {
        this.manageMoney = manageMoney;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getUnitFeedMoney() {
        return unitFeedMoney;
    }

    public void setUnitFeedMoney(BigDecimal unitFeedMoney) {
        this.unitFeedMoney = unitFeedMoney;
    }

    public BigDecimal getUnitManageMoney() {
        return unitManageMoney;
    }

    public void setUnitManageMoney(BigDecimal unitManageMoney) {
        this.unitManageMoney = unitManageMoney;
    }

    public BigDecimal getUnitAddWeightMoney() {
        return unitAddWeightMoney;
    }

    public void setUnitAddWeightMoney(BigDecimal unitAddWeightMoney) {
        this.unitAddWeightMoney = unitAddWeightMoney;
    }

    public BigDecimal getUnitBullPrice() {
        return unitBullPrice;
    }

    public void setUnitBullPrice(BigDecimal unitBullPrice) {
        this.unitBullPrice = unitBullPrice;
    }

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(BigDecimal totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getBullMoneyStyle() {
        return "<span style='color:#EE4035;'>¥ " + bullMoney.setScale(2,BigDecimal.ROUND_DOWN) + "</span>";
    }

    public String getTotalInterestStyle() {
        return "<span style='color:#EE4035;'>¥ " + totalInterest.setScale(2,BigDecimal.ROUND_DOWN) + "</span>";
    }

    public String getCheapMoneyStyle() {
        return "<span style='color:#EE4035;'>¥ " + (totalMoney == null ? 0.00 : (totalMoney.divideAndRemainder(BigDecimalUtil.parse(100))[1]).setScale(2,BigDecimal.ROUND_DOWN) + "</span>");
    }

    public BigDecimal getCheapMoney() {
        return (totalMoney == null ? BigDecimal.ZERO : totalMoney.divideAndRemainder(BigDecimalUtil.parse(100))[1]);
    }


    public String getBullMoneyStr() {
        return bullMoney.setScale(2,BigDecimal.ROUND_DOWN).toString();
    }

    public String getFeedMoneyStr() {
        return "¥ "+feedMoney.setScale(2,BigDecimal.ROUND_DOWN).toString();
    }

    public String getManageMoneyStr() {
        return "¥ "+manageMoney.setScale(2,BigDecimal.ROUND_DOWN).toString().toString();
    }

    public String getTotalMoneyStr() {
        return "¥ "+totalMoney.setScale(2,BigDecimal.ROUND_DOWN).toString();
    }

    public String getTotalInterestStr() {
        return totalInterestStr;
    }

	@Override
	public String toString() {
		return "BuyBullsDetailMoneyVO [unitFeedMoney=" + unitFeedMoney + ", unitManageMoney=" + unitManageMoney
				+ ", unitAddWeightMoney=" + unitAddWeightMoney + ", unitBullPrice=" + unitBullPrice + ", bullMoney="
				+ bullMoney + ", feedMoney=" + feedMoney + ", manageMoney=" + manageMoney + ", totalMoney=" + totalMoney
				+ ", cheapMoney=" + cheapMoney + ", totalInterest=" + totalInterest + ", bullMoneyStyle="
				+ bullMoneyStyle + ", totalInterestStyle=" + totalInterestStyle + ", cheapMoneyStyle=" + cheapMoneyStyle
				+ "]";
	}
    
    
}
