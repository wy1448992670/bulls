package com.goochou.p2b.model.vo;

import com.goochou.p2b.utils.CommonUtils;
import com.goochou.p2b.utils.MoneyUtil;

public class InvestmentVo {
	private String num;
	private String projectName;
	private String username;
	private String trueName;
	private String amount;
	private String time;
	private String source;
	private String codes;
	private String difDay;
	private String hbAmount;
	private String type;
	private String userId;
	private String register_time;
	private String investmentId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRegister_time() {
		return register_time;
	}

	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}

	public String getHbAmount() {
		return hbAmount;
	}

	public void setHbAmount(String hbAmount) {
		this.hbAmount = hbAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUsername() {
		return username;
	}
	public String getUsernameStr() {
		if (username != null) {
			return CommonUtils.getUserNameStr(username);
		}
		return "";
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTrueName() {
		return trueName;
	}
	
	public String getTrueNameStr(){
		if (trueName != null) {
			return CommonUtils.getTrueName(trueName);
		}
		return "";
	}
	
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getAmount() {
		return amount;
	}
	
	public String getAmountStr(){
		amount = amount == null ? "0" : amount;
		return MoneyUtil.insertComma(amount);
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTime() {
		return time;
	}
	
	/**
	 * 谁把time定义为String类型的。。。真麻烦
	 */
	public String getTimeYMD(){
		if (time != null) {
			String[] split = time.split(" ");
			return split[0];
		}
		return "";
	}
	
	public String getTimeHMS(){
		if (time != null) {
			String[] split = time.split(" ");
			return split[1].substring(0, split[1].indexOf("."));
		}
		return "";
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getDifDay() {
		return difDay;
	}

	public void setDifDay(String difDay) {
		this.difDay = difDay;
	}

	public String getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(String investmentId) {
		this.investmentId = investmentId;
	}
}
