 package com.goochou.p2b.model.vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import com.goochou.p2b.utils.BigDecimalUtil;

/**
 * @author ydp
 * @date 2019/06/29
 */
public class InvestmentDetailsVO implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 5524578279165216585L;
    private Integer id;
    private Integer projectId;
    private Double amount;
    private Double interestAmount;
    private Double addInterest;
    private Double balancePayMoney;
    private Double hongbaoMoney;
    private Double remainAmount;
    private String orderNo;
    private Date createDate;
    private String trueName;
    private Integer limitDays;
    private String title;
    private Integer sex;
    private String earNumber;
    private Date deadline;
    private Double annualized;
    private Double increaseAnnualized;
    private String path;
    private Double raiseFee;
    private Double manageFee;
    //project.total_amount
    private Double totalAmount;
    private Integer orderStatus;
    private String annualizedStr;
    private String createDateStr;
    private String deadlineStr;
    private String sellDateStr;
    private Date updateDate;
    private Integer isNoob;
    private Integer userId;
    private Boolean isPin;
    // 投资订单拼牛所占比例
    private String pinRate;
    // 剩余可投份数
    private Integer pinResiduePoint;

    private String limitDaysStr;
    
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getCreateDateStr() {
        return createDateStr;
    }
    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
    public String getDeadlineStr() {
        return deadlineStr;
    }
    public void setDeadlineStr(String deadlineStr) {
        this.deadlineStr = deadlineStr;
    }
    public String getSellDateStr() {
        return sellDateStr;
    }
    public void setSellDateStr(String sellDateStr) {
        this.sellDateStr = sellDateStr;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Double getInterestAmount() {
        return interestAmount;
    }
    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }
    public Double getAddInterest() {
        return addInterest;
    }
    public void setAddInterest(Double addInterest) {
        this.addInterest = addInterest;
    }
    public Double getHongbaoMoney() {
        return hongbaoMoney;
    }
    public void setHongbaoMoney(Double hongbaoMoney) {
        this.hongbaoMoney = hongbaoMoney;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getTrueName() {
        return trueName;
    }
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
    public Integer getLimitDays() {
        return limitDays;
    }
    public void setLimitDays(Integer limitDays) {
        this.limitDays = limitDays;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getEarNumber() {
        return earNumber;
    }
    public void setEarNumber(String earNumber) {
        this.earNumber = earNumber;
    }
    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    public Double getAnnualized() {
        return annualized;
    }
    public void setAnnualized(Double annualized) {
        this.annualized = annualized;
    }
    public Double getIncreaseAnnualized() {
        return increaseAnnualized;
    }
    public void setIncreaseAnnualized(Double increaseAnnualized) {
        this.increaseAnnualized = increaseAnnualized;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Double getRaiseFee() {
        return raiseFee;
    }
    public void setRaiseFee(Double raiseFee) {
        this.raiseFee = raiseFee;
    }
    public Double getManageFee() {
        return manageFee;
    }
    public void setManageFee(Double manageFee) {
        this.manageFee = manageFee;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getAnnualizedStr() {
        return annualizedStr;
    }
    public void setAnnualizedStr(String annualizedStr) {
        this.annualizedStr = annualizedStr;
    }
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getIsNoob() {
		return isNoob;
	}
	public void setIsNoob(Integer isNoob) {
		this.isNoob = isNoob;
	}

    public Boolean getPin() {
        return isPin;
    }

    public void setPin(Boolean pin) {
        isPin = pin;
    }

    public String getPinRate() {
        return pinRate;
    }
    public void setPinRate(String pinRate) {
        this.pinRate = pinRate;
    }
    public Integer getPinResiduePoint() {
        return pinResiduePoint;
    }
    public void setPinResiduePoint(Integer pinResiduePoint) {
        this.pinResiduePoint = pinResiduePoint;
    }
    public String getLimitDaysStr() {
        return limitDaysStr;
    }
    public void setLimitDaysStr(String limitDaysStr) {
        this.limitDaysStr = limitDaysStr;
    }
	public Double getBalancePayMoney() {
		return balancePayMoney;
	}
	public void setBalancePayMoney(Double balancePayMoney) {
		this.balancePayMoney = balancePayMoney;
	}
	public Double getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}
	
	public static DecimalFormat MONEY_FORMAT = new DecimalFormat("¥ ###,##0.00");
	
	//-------------------app显示文字内容
	//牛只单价(去除 管理费&饲养费 的价格)
	public Double getUnitPrice() {
		return BigDecimalUtil.sub(this.getAmount(), this.getRaiseFee(), this.getManageFee());
	}
	public String getUnitPriceStr() {
		return String.format("¥ %.2f",this.getUnitPrice());
	}
	public String getRaiseFeeStr() {
		return String.format("¥ %.2f",this.getRaiseFee());
	}
	public String getManageFeeStr() {
		return String.format("¥ %.2f",this.getManageFee());
	}
	
	public String getBalancePayMoneyStr() {
		return String.format("¥ %.2f",this.getBalancePayMoney());
	}
	//红包金额显示负值
	public String getHongbaoMoneyStr() {
		return String.format("¥ %.2f",-1 * this.getHongbaoMoney());
	}
	public String getRemainAmountStr() {
		return String.format("¥ %.2f",this.getRemainAmount());
	}
	
	public String getAddInterestStr() {
		return String.format("¥ %.2f",this.getAddInterest());
	}
	public String getInterestAmountStr() {
		return String.format("¥ %.2f",this.getInterestAmount());
	}
	public String getAmountStr() {
		return String.format("¥ %.2f",this.getAmount());
	}
	public String getTotalAmountStr() {
		return String.format("¥ %.2f",this.getTotalAmount());
	}
	
	
    public static void main(String[] args) {
		System.out.println(String.format("¥ %.2f",-1 * 0.669));
	}
}
