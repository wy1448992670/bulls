package com.goochou.p2b.model.vo;

public class CustomerStatisticsData implements Comparable<CustomerStatisticsData>{
	
	private String trueName;
	
	private Double allAmount;
	
	private Double planPercent;
	
	private Double timePercent;
	
	private String date;
	
	private Double monthPlan;
	
	private Double dayPlan;
	
	private boolean isDay;
	
	private Integer dayRanking;
	private double amount15;
	private double amount30;
	private double amount90;
	private double amount180;
	private double amount360;
	
	public double getAmount15() {
		return amount15;
	}

	public void setAmount15(double amount15) {
		this.amount15 = amount15;
	}

	public double getAmount30() {
		return amount30;
	}

	public void setAmount30(double amount30) {
		this.amount30 = amount30;
	}

	public double getAmount90() {
		return amount90;
	}

	public void setAmount90(double amount90) {
		this.amount90 = amount90;
	}

	public double getAmount180() {
		return amount180;
	}

	public void setAmount180(double amount180) {
		this.amount180 = amount180;
	}

	public double getAmount360() {
		return amount360;
	}

	public void setAmount360(double amount360) {
		this.amount360 = amount360;
	}

	public CustomerStatisticsData(){
		super();
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Double getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(Double allAmount) {
		this.allAmount = allAmount;
	}

	public Double getPlanPercent() {
		return planPercent;
	}

	public void setPlanPercent(Double planPercent) {
		this.planPercent = planPercent;
	}

	public Double getTimePercent() {
		return timePercent;
	}

	public void setTimePercent(Double timePercent) {
		this.timePercent = timePercent;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getMonthPlan() {
		return monthPlan;
	}

	public void setMonthPlan(Double monthPlan) {
		this.monthPlan = monthPlan;
	}

	public Double getDayPlan() {
		return dayPlan;
	}

	public void setDayPlan(Double dayPlan) {
		this.dayPlan = dayPlan;
	}

	public boolean isDay() {
		return isDay;
	}

	public void setDay(boolean isDay) {
		this.isDay = isDay;
	}

	public Integer getDayRanking() {
		return dayRanking;
	}

	public void setDayRanking(Integer dayRanking) {
		this.dayRanking = dayRanking;
	}

	@Override
	public int compareTo(CustomerStatisticsData o) {
		
		/*if(this.isDay()){
			//按照达成率排名
			String s1 = this.getPlanPercent().toString();
			String s2 = o.getPlanPercent().toString();
            return Integer.parseInt(s2.substring(0, s2.indexOf(".")) + s2.substring(s2.indexOf(".")+1))
            		- Integer.parseInt(s1.substring(0, s1.indexOf(".")) + s1.substring(s1.indexOf(".")+1));
		}else{
			//先按照总金额排序
			String s1 = this.getAllAmount().toString();
			String s2 = o.getAllAmount().toString();
			int i = Integer.parseInt(s2.substring(0, s2.indexOf(".")) + s2.substring(s2.indexOf(".")+1))
					 - Integer.parseInt(s1.substring(0, s1.indexOf(".")) + s1.substring(s1.indexOf(".")+1));  
	        if(i == 0){  
	        	
	        	s1 = this.getPlanPercent().toString();
	    		s2 = o.getPlanPercent().toString();
	        	//如果总金额相等了再用完成进度进行排序
	            return Integer.parseInt(s2.substring(0, s2.indexOf(".")) + s2.substring(s2.indexOf(".")+1))
	            		- Integer.parseInt(s1.substring(0, s1.indexOf(".")) + s1.substring(s1.indexOf(".")+1));
	        }  
	        
	        return i;
		}*/
		int i = 0;
		if(this.isDay()){
			//按照达成率排名
            i = (int)o.getPlanPercent().doubleValue() - (int)this.getPlanPercent().doubleValue();
            if(i == 0){  
            	//如果达成率相等了再用日入金量进行排序
            	i = (int)o.getAllAmount().doubleValue() - (int)this.getAllAmount().doubleValue();
            }
            
		}else{
			//先按照总金额排序
			i = (int)o.getAllAmount().doubleValue() - (int)this.getAllAmount().doubleValue();  
	        if(i == 0){  
	        	//如果总金额相等了再用完成进度进行排序
	        	i = (int)o.getPlanPercent().doubleValue() - (int)this.getPlanPercent().doubleValue();
	        }  
		}
		
		return i;
	}
}
