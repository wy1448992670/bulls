package com.goochou.p2b.model.vo;

/**
 * 每个用户对账文件
 * 
 * @author irving
 * 
 */
public class ActivityDataVO {
	
    private Double investAmount;
    
    private Integer investCount;
    
    private Double investMaxPrice;
    
    private Double investMinPrice;
    
    private Double keDanJia;
    
    private Integer count;
    
    private String area;
    
    private Integer age;
    
    private Integer hour;
    
    public ActivityDataVO(Double investAmount, Integer investCount,
			Double investMaxPrice, Double investMinPrice, Double keDanJia) {
		super();
		this.investAmount = investAmount;
		this.investCount = investCount;
		this.investMaxPrice = investMaxPrice;
		this.investMinPrice = investMinPrice;
		this.keDanJia = keDanJia;
	}
    
    public ActivityDataVO() {}
    
	public Double getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}
	public Integer getInvestCount() {
		return investCount;
	}
	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
	}
	public Double getInvestMaxPrice() {
		return investMaxPrice;
	}
	public void setInvestMaxPrice(Double investMaxPrice) {
		this.investMaxPrice = investMaxPrice;
	}
	public Double getInvestMinPrice() {
		return investMinPrice;
	}
	public void setInvestMinPrice(Double investMinPrice) {
		this.investMinPrice = investMinPrice;
	}
	public Double getKeDanJia() {
		return keDanJia;
	}
	public void setKeDanJia(Double keDanJia) {
		this.keDanJia = keDanJia;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}


}
