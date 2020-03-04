package com.goochou.p2b.model.vo;


/**
 * @author 张琼麒
 * @version 创建时间：2019年10月8日 下午3:13:57
 * 类说明
 */
public class DataSourceSumVo  {
	private Integer id;
	
	private String name;
	
	private String client;
	
	private Integer registCount;
	
	private Integer firstCount;
	
	private Double firstAmount;
	
	private Integer repeatCount;
	
	private Double repeatAmount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getRegistCount() {
		return registCount;
	}

	public void setRegistCount(Integer registCount) {
		this.registCount = registCount;
	}

	public Integer getFirstCount() {
		return firstCount;
	}

	public void setFirstCount(Integer firstCount) {
		this.firstCount = firstCount;
	}

	public Double getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Double getRepeatAmount() {
		return repeatAmount;
	}

	public void setRepeatAmount(Double repeatAmount) {
		this.repeatAmount = repeatAmount;
	}

}
