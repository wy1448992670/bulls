package com.goochou.p2b.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.goochou.p2b.constant.ProjectTypeEnum;

public class InvestmentOrderListVO implements Serializable{
	private static final long serialVersionUID = -7603881190465689976L;
	private Integer id;
	private Integer projectId;
	private String orderNo;
	private Integer orderStatus;
	private Integer payStatus;
	private Date createDate;
	private BigDecimal amount;
	private BigDecimal remainAmount;
	private Integer limitDays;
	private String earNumber;
	private String path;
	private String title;
	private String sex;
	private String repayUnit;
	private BigDecimal annualized;
	
	private String limitDayStr;
	private	Long lockTime;
	private String createDateStr;
	private String annualizedStr;
	private String orderType;
	private Date deadline;
	private Date updateDate;
    private String updateDateStr;
    
    private String annualizedLable;
    private String limitDayLable;
    private String sexLable;
    private String amountLable;
    private Boolean isBugAgain;
    private String isbugAgainLable;
    private Integer bugAgainStatus;
    
    private Integer projectType;
    private String pinResiduePoint;
    private String pinResiduePointLable;
    private String pinRateStr; //拼牛占比
    private String pinRateLable; //拼牛占比lable
    private String pinBuyedAmountLable; //拼牛已成团本金lable 
    private String pinInterestAmountLable; // 拼牛利息lable
    private BigDecimal interestAmount; // 总利息
    private String sumLabel; //我的订单列表,各订单合计label
    
    public String getPinResiduePointLable() {
		return pinResiduePointLable;
	}
	public void setPinResiduePointLable(String pinResiduePointLable) {
		this.pinResiduePointLable = pinResiduePointLable;
	}
	public String getPinRateStr() {
		return pinRateStr;
	}
	public void setPinRateStr(String pinRateStr) {
		this.pinRateStr = pinRateStr;
	}
	public String getPinRateLable() {
		return pinRateLable;
	}
	public void setPinRateLable(String pinRateLable) {
		this.pinRateLable = pinRateLable;
	}
	public String getAmountLable() {
        return amountLable;
    }
    public void setAmountLable(String amountLable) {
        this.amountLable = amountLable;
    }
    public String getOrderStatusStr() {
    	if(this.projectType == 0) {
    		if(this.orderStatus == 0) {
    			return "待付款";
        	} else if(this.orderStatus == 1) {
        		return "已认养";
        	} else if (this.orderStatus == 2) {
        		return "已出售";
        	}
    	}else if(this.projectType == 1) {
    		if(this.orderStatus == 0) {
    			return "待付款";
        	} else if(this.orderStatus == 1) {
        		return "已成团";
        	} else if (this.orderStatus == 2) {
        		return "已结算";
        	} else if (this.orderStatus == 4) {
        		return "待成团";
        	}
    	}
		return "";
	}
	
	public String getAnnualizedLable() {
		return annualizedLable;
	}
	public void setAnnualizedLable(String annualizedLable) {
		this.annualizedLable = annualizedLable;
	}
	public String getLimitDayLable() {
		return limitDayLable;
	}
	public void setLimitDayLable(String limitDayLable) {
		this.limitDayLable = limitDayLable;
	}
	public String getSexLable() {
		return sexLable;
	}
	public void setSexLable(String sexLable) {
		this.sexLable = sexLable;
	}
	public String getUpdateDateStr() {
        return updateDateStr;
    }
    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }
    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getLimitDayStr() {
		return limitDayStr;
	}
	public void setLimitDayStr(String limitDayStr) {
		this.limitDayStr = limitDayStr;
	}
	public Long getLockTime() {
		return lockTime;
	}
	public void setLockTime(Long lockTime) {
		this.lockTime = lockTime;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getAnnualizedStr() {
		return annualizedStr;
	}
	public void setAnnualizedStr(String annualizedStr) {
		this.annualizedStr = annualizedStr;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}
	public Integer getLimitDays() {
		return limitDays;
	}
	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}
	 
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRepayUnit() {
		return repayUnit;
	}
	public void setRepayUnit(String repayUnit) {
		this.repayUnit = repayUnit;
	}
	public BigDecimal getAnnualized() {
		return annualized;
	}
	public void setAnnualized(BigDecimal annualized) {
		this.annualized = annualized;
	}
	public String getEarNumber() {
		return earNumber;
	}
	public void setEarNumber(String earNumber) {
		this.earNumber = earNumber;
	}
	 
	 
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getBugAgainStatus() {
		return bugAgainStatus;
	}
	public void setBugAgainStatus(Integer bugAgainStatus) {
		this.bugAgainStatus = bugAgainStatus;
	}
	public Boolean getIsBugAgain() {
		return isBugAgain;
	}
	public void setIsBugAgain(Boolean isBugAgain) {
		this.isBugAgain = isBugAgain;
	}
	public String getIsbugAgainLable() {
		return isbugAgainLable;
	}
	public void setIsbugAgainLable(String isbugAgainLable) {
		this.isbugAgainLable = isbugAgainLable;
	}
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getPinBuyedAmountLable() {
		return pinBuyedAmountLable;
	}
	public void setPinBuyedAmountLable(String pinBuyedAmountLable) {
		this.pinBuyedAmountLable = pinBuyedAmountLable;
	}
	public BigDecimal getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}
	public String getPinInterestAmountLable() {
		return pinInterestAmountLable;
	}
	public void setPinInterestAmountLable(String pinInterestAmountLable) {
		this.pinInterestAmountLable = pinInterestAmountLable;
	}
	public String getPinResiduePoint() {
		return pinResiduePoint;
	}
	public void setPinResiduePoint(String pinResiduePoint) {
		this.pinResiduePoint = pinResiduePoint;
	}
	
	
	// -------------------app显示文字内容
	public void setSumLabel(String sumLabel) {
		this.sumLabel = sumLabel;
	}
	public String getSumLabel() {
		return sumLabel==null?"合计:":sumLabel;
	}
	public static DecimalFormat MONEY_FORMAT = new DecimalFormat("¥ ###,##0.00");
	
	public String getAmountStr() {
		return MONEY_FORMAT.format(this.getAmount());
	}
	//getInterestAmountStr
	public String getPinInterestAmount() {
		return MONEY_FORMAT.format(this.getInterestAmount());
	}
	//getAmountStr
	public String getPinBuyedAmount() {
		return MONEY_FORMAT.format(this.getAmount());
	}
    
}
