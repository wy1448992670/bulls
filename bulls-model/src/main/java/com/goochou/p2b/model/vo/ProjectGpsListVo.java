package com.goochou.p2b.model.vo;

public class ProjectGpsListVo {
	private Integer  id;
	private String title;
	/*private Float annualized;
	private Float increaseAnnualized;
	private String repayUnit;
	private Integer limitDays;
	private String safeNumber;*/
	private String earNumber;
	private String realEarNumber;
	
/*	private String enterpriseName;
	private double totalAmount;*/
	/*private String sex;
	private Integer noob;*/
	private String littleImagePath; 
	/*private String annualizedStr;
	private String increaseAnnualizedStr;
	private String limitDayStr;*/
	private String prairieValue;
	private String gpsNumber;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEarNumber() {
		return earNumber;
	}
	public void setEarNumber(String earNumber) {
		this.earNumber = earNumber;
	}
	public String getRealEarNumber() {
		return realEarNumber;
	}
	public void setRealEarNumber(String realEarNumber) {
		this.realEarNumber = realEarNumber;
	}
	public String getLittleImagePath() {
		return littleImagePath;
	}
	public void setLittleImagePath(String littleImagePath) {
		this.littleImagePath = littleImagePath;
	}
	public String getPrairieValue() {
		return prairieValue;
	}
	public void setPrairieValue(String prairieValue) {
		this.prairieValue = prairieValue;
	}
	public String getGpsNumber() {
		return gpsNumber;
	}
	public void setGpsNumber(String gpsNumber) {
		this.gpsNumber = gpsNumber;
	}
	
}
