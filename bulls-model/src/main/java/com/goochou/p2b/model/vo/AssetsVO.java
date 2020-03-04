package com.goochou.p2b.model.vo;

import java.math.BigDecimal;

import com.goochou.p2b.model.Assets;

/**
 * @author 张琼麒
 * @version 创建时间：2019年10月8日 下午3:13:57
 * 类说明
 */
public class AssetsVO extends Assets {
	//序号
	private int rowNum;
	//客户名称
	private String userTrueName;
	//user.username
	private String username;
	//资产金额：客户持有的牛只物权本金
	private Double amount;

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getUserTrueName() {
		return userTrueName;
	}

	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getWholeBalanceAmount() {
		return BigDecimal.valueOf(getBalanceAmount()).add(BigDecimal.valueOf(getFrozenAmount())).doubleValue();
	}
	
	public Double getWholeCreditAmount() {
		return BigDecimal.valueOf(getCreditAmount()).add(BigDecimal.valueOf(getFreozenCreditAmount())).doubleValue();
	}
	
	public Double getSumAmount() {
		return BigDecimal.valueOf(getAmount()).add(BigDecimal.valueOf(getWholeBalanceAmount())).add(BigDecimal.valueOf(getWholeCreditAmount())).doubleValue();
	}
}
