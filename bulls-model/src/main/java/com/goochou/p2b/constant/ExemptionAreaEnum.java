package com.goochou.p2b.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 包邮地区（暂时按省份）
 * @author wangyun
 * @date 2020年1月7日
 * @time 上午11:11:30
 */
public enum ExemptionAreaEnum {
	// 除江浙沪外满300包邮  1满200包邮 0默认 满300包邮
	SHANGHAI(1, 310000, BigDecimal.valueOf(200), "上海"),
	JIANGSU(1,320000, BigDecimal.valueOf(200), "江苏省"),
	ZHEJIANG(1, 330000, BigDecimal.valueOf(200), "浙江省"),
	OTHER(0, 000000, BigDecimal.valueOf(300), "其他");
	
	
	ExemptionAreaEnum(int type, int cityCode, BigDecimal amount, String cityName){
		this.type = type;
		this.setCityCode(cityCode);
		this.cityName = cityName;
		this.amount = amount;
	}
	private int type;
	private int cityCode;
	private String cityName;
	private BigDecimal amount;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
	 * @desc 满200包邮的地区
	 * @author wangyun
	 * @return
	 */
	public static List<ExemptionAreaEnum> getExemptionCityList(){
		List<ExemptionAreaEnum> list = new ArrayList<ExemptionAreaEnum>();
		for (ExemptionAreaEnum enums : values()) {
			if(enums.getType() == 1) { // 满200包邮的地区
				list.add(enums);
			}
		}
		return list;
	}
	
	/**
	 * @desc 根据cityId查询
	 * @author wangyun
	 * @param cityId
	 * @return
	 */
	public static ExemptionAreaEnum getExemptionByCityId(int cityId) {
		for (ExemptionAreaEnum enums : values()) {
			if(enums.getCityCode() == cityId) {
				 return enums;
			}
		}
		return null;
	}
	
	/**
	 * @desc 根据类型区分包邮地区
	 * @author wangyun
	 * @param type 1满200包邮,0满300包邮. 其他type可添加
	 * @return
	 */
	public static ExemptionAreaEnum getExemptionByType(int type, int cityId) {
		for (ExemptionAreaEnum enums : values()) {
			if(enums.getType() == type && enums.getCityCode() == cityId) {
				 return enums;
			}
		}
		return null;
	}
}
