/**   
* @Title: ScreenInfo.java 
* @Package com.goochou.p2b.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zj
* @date 2019-07-16 09:37 
* @version V1.0   
*/
package com.goochou.p2b.model.vo;

import java.io.Serializable;

/**
 * 大屏幕显示信息
 * 
 * @ClassName: ScreenInfo
 * @author zj
 * @date 2019-07-16 09:37
 */
public class ScreenInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer bullQuantity;
	private Integer cowQuantity;
	private Integer allCattleQuantity;
	private Integer calfQuantity;
	private Integer expectantCowQuantity;
	private Double bullQuantityRatio;
	private Double cowQuantityRatio;
	private Double calfQuantityRatio;
	private Double expectantCowQuantityRatio;

	/**
	 * 月龄范围描述
	 */
	private String ageDesc;
	/**
	 * 月龄范围牛数量
	 */
	private Integer ageQuantity;

	public Integer getBullQuantity() {
		return bullQuantity;
	}

	public void setBullQuantity(Integer bullQuantity) {
		this.bullQuantity = bullQuantity;
	}

	public Integer getCowQuantity() {
		return cowQuantity;
	}

	public void setCowQuantity(Integer cowQuantity) {
		this.cowQuantity = cowQuantity;
	}

	public Integer getAllCattleQuantity() {
		return allCattleQuantity;
	}

	public void setAllCattleQuantity(Integer allCattleQuantity) {
		this.allCattleQuantity = allCattleQuantity;
	}

	public Integer getCalfQuantity() {
		return calfQuantity;
	}

	public void setCalfQuantity(Integer calfQuantity) {
		this.calfQuantity = calfQuantity;
	}

	public Integer getExpectantCowQuantity() {
		return expectantCowQuantity;
	}

	public void setExpectantCowQuantity(Integer expectantCowQuantity) {
		this.expectantCowQuantity = expectantCowQuantity;
	}

	public Double getBullQuantityRatio() {
		return bullQuantityRatio;
	}

	public void setBullQuantityRatio(Double bullQuantityRatio) {
		this.bullQuantityRatio = bullQuantityRatio;
	}

	public Double getCowQuantityRatio() {
		return cowQuantityRatio;
	}

	public void setCowQuantityRatio(Double cowQuantityRatio) {
		this.cowQuantityRatio = cowQuantityRatio;
	}

	public Double getCalfQuantityRatio() {
		return calfQuantityRatio;
	}

	public void setCalfQuantityRatio(Double calfQuantityRatio) {
		this.calfQuantityRatio = calfQuantityRatio;
	}

	public Double getExpectantCowQuantityRatio() {
		return expectantCowQuantityRatio;
	}

	public void setExpectantCowQuantityRatio(Double expectantCowQuantityRatio) {
		this.expectantCowQuantityRatio = expectantCowQuantityRatio;
	}

	public String getAgeDesc() {
		return ageDesc;
	}

	public void setAgeDesc(String ageDesc) {
		this.ageDesc = ageDesc;
	}

	public Integer getAgeQuantity() {
		return ageQuantity;
	}

	public void setAgeQuantity(Integer ageQuantity) {
		this.ageQuantity = ageQuantity;
	}

	@Override
	public String toString() {
		return "ScreenInfo [bullQuantity=" + bullQuantity + ", cowQuantity=" + cowQuantity + ", allCattleQuantity=" + allCattleQuantity
				+ ", calfQuantity=" + calfQuantity + ", expectantCowQuantity=" + expectantCowQuantity + ", bullQuantityRatio=" + bullQuantityRatio
				+ ", cowQuantityRatio=" + cowQuantityRatio + ", calfQuantityRatio=" + calfQuantityRatio + ", expectantCowQuantityRatio="
				+ expectantCowQuantityRatio + ", ageDesc=" + ageDesc + ", ageQuantity=" + ageQuantity + "]";
	}

}
