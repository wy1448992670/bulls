package com.goochou.p2b.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.goochou.p2b.model.InvestmentView;

/**
 * 牧场订单列表返回对象
 */
public class InvestmentOrderVO extends InvestmentView implements Serializable {

    private static final long serialVersionUID = -6917055038471528670L;
    private Integer num;
    private Integer limitDays;
    private String earNumber;
    private BigDecimal raiseFee;
    private BigDecimal manageFee;
    private BigDecimal totalAmount;
    private Integer noob;
    private Integer yueLing;
    private String trueName;
    private Date parentBuyBackTime;
    private BigDecimal parentBuyBackAmount;
    private BigDecimal buyBackAmount;
    
    
	public Integer getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}

	public String getEarNumber() {
		return earNumber;
	}

	public void setEarNumber(String earNumber) {
		this.earNumber = earNumber;
	}

	public BigDecimal getRaiseFee() {
		return raiseFee;
	}

	public void setRaiseFee(BigDecimal raiseFee) {
		this.raiseFee = raiseFee;
	}

	public BigDecimal getManageFee() {
		return manageFee;
	}

	public void setManageFee(BigDecimal manageFee) {
		this.manageFee = manageFee;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getNoob() {
		return noob;
	}

	public void setNoob(Integer noob) {
		this.noob = noob;
	}

	public Integer getYueLing() {
		return yueLing;
	}

	public void setYueLing(Integer yueLing) {
		this.yueLing = yueLing;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Date getParentBuyBackTime() {
		return parentBuyBackTime;
	}

	public void setParentBuyBackTime(Date parentBuyBackTime) {
		this.parentBuyBackTime = parentBuyBackTime;
	}

	public BigDecimal getParentBuyBackAmount() {
		return parentBuyBackAmount;
	}

	public void setParentBuyBackAmount(BigDecimal parentBuyBackAmount) {
		this.parentBuyBackAmount = parentBuyBackAmount;
	}

	public BigDecimal getBuyBackAmount() {
		return buyBackAmount;
	}

	public void setBuyBackAmount(BigDecimal buyBackAmount) {
		this.buyBackAmount = buyBackAmount;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getCowAmount() {
		if(totalAmount != null && totalAmount.compareTo(BigDecimal.ZERO) > 0) {
			return totalAmount.subtract(manageFee).subtract(raiseFee);
		}
		return BigDecimal.ZERO;
	}
	
    public int getNum() {
		 if(num == null) {
			 num = 1;
		 }
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPayStatusStr() {
		if(getPayStatus() == 0) {
			return "未支付";
   	} else if(getPayStatus() == 1) {
   		return "支付中";
   	}else if(getPayStatus() == 2) {
   		return "已支付";
   	}
		return "";
	}

	public String getOrderStatusStr() {
   	if(getOrderStatus() == 0) {
   		return "未饲养";
   	} else if(getOrderStatus() == 1) {
   		return "饲养期";
   	}else if(getOrderStatus() == 2) {
   		return "已卖牛";
   	}else if(getOrderStatus() == 3) {
   		return "已取消牛";
   	}
		return "";
	}

}
