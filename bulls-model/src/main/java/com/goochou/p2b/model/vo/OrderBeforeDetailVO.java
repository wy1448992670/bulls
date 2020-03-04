package com.goochou.p2b.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.constant.ProjectDaysEnum;
import com.goochou.p2b.utils.BigDecimalUtil;

public class OrderBeforeDetailVO implements Serializable {

    private static final long serialVersionUID = -4007695650502636401L;
    // project.id
    private Integer projectId;

    // user.id
    private Integer userId;

    // 项目名称
    private String title;

    // 年化利率
    private Float annualized;

    // 投资周期/天 （养殖周期）
    private Integer limitDays;

    // 总融资金额
    private Double totalAmount;

    // 认领单价
    private Double unitPrice;

    // 饲养费
    private Double raiseFee;

    // 管理费
    private Double manageFee;

	// 还款周期
    private String repayUnit;

    // 耳编号
    private String earNumber;

    // 保险编号
    private String safeNumber;

    // 标签
    private String tag;

//    private Integer version;

    // 红包数量
    private Integer hongbaoCount;
    // 红包类型描述
    private String hongbaoTypeDescription;
    // 委托牧场
    private String enterpriseName;

    //是否实名
    private String realName;

    //是否允许购买
    private String allowSale;
  //认购协议
    private String buyAgreeText;
    private String buyAgreeUrl;
  //回购协议
    private String backAgreeText;
    private String backAgreeUrl;
    private String orderType;
    private String sex;
    private String sexStr;
    private String annualizedStr;
    private String limitDaysStr;
    private Integer noob;
    private BigDecimal interestAmount;
    private Double sumAmount;
    private String allow;
    private Integer bullType;
    private String rateStr;
    private String rightLabel;
    private List<Map<String, Object>> projectDays;

    private String appVersion;
    
    public List<Map<String, Object>> getProjectDays() {
        return ProjectDaysEnum.enumParseMap();
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public Double getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Double sumAmount) {
        this.sumAmount = sumAmount;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Integer getNoob() {
        return noob;
    }

    public void setNoob(Integer noob) {
        this.noob = noob;
    }

    public String getAnnualizedStr() {
        return annualizedStr;
    }

    public void setAnnualizedStr(String annualizedStr) {
        this.annualizedStr = annualizedStr;
    }

    public String getLimitDaysStr() {
        return limitDaysStr;
    }

    public void setLimitDaysStr(String limitDaysStr) {
        this.limitDaysStr = limitDaysStr;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBuyAgreeText() {
        return buyAgreeText;
    }

    public void setBuyAgreeText(String buyAgreeText) {
        this.buyAgreeText = buyAgreeText;
    }

    public String getBuyAgreeUrl() {
        return buyAgreeUrl;
    }

    public void setBuyAgreeUrl(String buyAgreeUrl) {
        this.buyAgreeUrl = buyAgreeUrl;
    }

    public String getBackAgreeText() {
        return backAgreeText;
    }

    public void setBackAgreeText(String backAgreeText) {
        this.backAgreeText = backAgreeText;
    }

    public String getBackAgreeUrl() {
        return backAgreeUrl;
    }

    public void setBackAgreeUrl(String backAgreeUrl) {
        this.backAgreeUrl = backAgreeUrl;
    }

    public String getAllowSale() {
        return allowSale;
    }

    public void setAllowSale(String allowSale) {
        this.allowSale = allowSale;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getAnnualized() {
        return annualized;
    }

    public void setAnnualized(Float annualized) {
        this.annualized = annualized;
    }

    public Integer getLimitDays() {
        return limitDays;
    }

    public void setLimitDays(Integer limitDays) {
        this.limitDays = limitDays;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getRepayUnit() {
        return repayUnit;
    }

    public void setRepayUnit(String repayUnit) {
        this.repayUnit = repayUnit;
    }

    public String getEarNumber() {
        return earNumber;
    }

    public void setEarNumber(String earNumber) {
        this.earNumber = earNumber;
    }

    public String getSafeNumber() {
        return safeNumber;
    }

    public void setSafeNumber(String safeNumber) {
        this.safeNumber = safeNumber;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getHongbaoCount() {
        return hongbaoCount;
    }

    public void setHongbaoCount(Integer hongbaoCount) {
        this.hongbaoCount = hongbaoCount;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Integer getBullType() {
		return bullType;
	}

	public void setBullType(Integer bullType) {
		this.bullType = bullType;
	}

	public String getRateStr() {
		return rateStr;
	}

	public void setRateStr(String rateStr) {
		this.rateStr = rateStr;
	}
	
	public String getRightLabel() {
		return rightLabel;
	}

	public void setRightLabel(String rightLabel) {
		this.rightLabel = rightLabel;
	}
	
	public String getHongbaoTypeDescription() {
		return hongbaoTypeDescription;
	}

	public void setHongbaoTypeDescription(String hongbaoTypeDescription) {
		this.hongbaoTypeDescription = hongbaoTypeDescription;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public Double getUnitPrice() {
        if (totalAmount != null) {
            Double raiseFee = this.raiseFee == null ? 0 : this.raiseFee;
            Double manageFee = this.manageFee == null ? 0 : this.manageFee;
            return BigDecimalUtil.sub(totalAmount, raiseFee, manageFee);
        }
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
	// -------------------app显示文字内容
	public String getSexStr() {
		if ("0".equals(sex)) {
			return "公牛";
		}
		if ("1".equals(sex)) {
			return "母牛";
		}
		return "";
	}

	public static DecimalFormat MONEY_FORMAT = new DecimalFormat("¥ ###,##0.00");

	public String getTotalAmountStr() {
		return MONEY_FORMAT.format(this.getTotalAmount());
	}

	public String getUnitPriceStr() {
		return MONEY_FORMAT.format(this.getUnitPrice());
	}

	public String getRaiseFeeStr() {
		return MONEY_FORMAT.format(this.getRaiseFee());
	}

	public String getManageFeeStr() {
		return MONEY_FORMAT.format(this.getManageFee());
	}

	public String getInterestAmountStr() {
		return MONEY_FORMAT.format(this.getInterestAmount());
	}

	public Map<String, String> getLabel() {
		Map<String, String> result = new HashMap<>();
		result.put("safeNumber", "耳标号");
		result.put("sex", "性别");
		result.put("interestAmount", "饲养预计利润(金额)");
		result.put("limitDays", "饲养期");
		result.put("entrustDate", "委托饲养期限");
		result.put("entrustPasture", "委托牧场");
		if (this.getVersion(this.appVersion) >= 200) {
			result.put("unitPrice", "牛只单价");
			result.put("raiseFee", "饲养费用");
			result.put("manageFee", "管理费用");
			result.put("sumAmount", "订单总额");
		} else {
			result.put("unitPrice", "认购单价(元)");
			result.put("raiseFee", "饲养费用(元)");
			result.put("manageFee", "管理费用(元)");
			result.put("sumAmount", "订单总额(元)");
		}

		result.put("totalAmount", "订单金额");
		result.put("parentOrder", "原始订单");
		result.put("status", "已结算");
		result.put("rateStr", "所占份额");
		return result;
	}

	public int getVersion(String version) {
		String versions[] = version.split("\\.");
		version = "";
		for (int i = 0; i < versions.length; i++) {
			version += versions[i];
		}
		return Integer.parseInt(version);
	}
}
