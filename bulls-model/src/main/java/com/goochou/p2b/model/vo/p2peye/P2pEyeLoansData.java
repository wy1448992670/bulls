package com.goochou.p2b.model.vo.p2peye;

import java.text.ParseException;
import com.goochou.p2b.utils.DateUtil;

/**
 * 网贷天眼借款标信息
 * @author xueqi
 *
 */
public class P2pEyeLoansData {
	
	private String id;
	
	private String platform_name;
	
	private String url;
	
	private String title;
	
	private String username;
	
	private String userid;
	
	private Integer status;
	
	private Float c_type;
	
	private Double amount;
	
	private Double rate;
	
	private Integer period;
	
	private Integer p_type;
	
	private Integer pay_way;
	
	private Double process;
	
	private Double reward;
	
	private Double guarantee;
	
	private String start_time;
	
	private String end_time;
	
	private Integer invest_num;
	
	private Double c_reward;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatform_name() {
		return platform_name;
	}

	public void setPlatformName(String platform_name) {
		this.platform_name = platform_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Float getC_type() {
		return c_type;
	}

	public void setCType(Float c_type) {
		this.c_type = c_type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getP_type() {
		return p_type;
	}

	public void setPType(Integer p_type) {
		this.p_type = p_type;
	}

	public Integer getPay_way() {
		return pay_way;
	}

	public void setPayWay(Integer pay_way) {
		this.pay_way = pay_way;
	}

	public Double getProcess() {
		return process;
	}

	public void setProcess(Double process) {
		this.process = process;
	}

	public Double getReward() {
		return reward;
	}

	public void setReward(Double reward) {
		this.reward = reward;
	}

	public Double getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Double guarantee) {
		this.guarantee = guarantee;
	}

	public String getStart_time() {
		try {
			if (start_time != null) {
				return DateUtil.dateFullTimeFormat.format(DateUtil.dateFullTimeFormat.parse(start_time));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void setStartTime(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		try {
			if (end_time != null) {
				return DateUtil.dateFullTimeFormat.format(DateUtil.dateFullTimeFormat.parse(end_time));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void setEndTime(String end_time) {
		this.end_time = end_time;
	}

	public Integer getInvest_num() {
		return invest_num;
	}

	public void setInvestNum(Integer invest_num) {
		this.invest_num = invest_num;
	}

	public Double getC_reward() {
		return c_reward;
	}

	public void setCReward(Double c_reward) {
		this.c_reward = c_reward;
	}

	@Override
	public String toString() {
		return "P2pEyeLoansData [id=" + id + ", platform_name=" + platform_name
				+ ", url=" + url + ", title=" + title + ", username="
				+ username + ", userid=" + userid + ", status=" + status
				+ ", c_type=" + c_type + ", amount=" + amount + ", rate="
				+ rate + ", period=" + period + ", p_type=" + p_type
				+ ", pay_way=" + pay_way + ", process=" + process + ", reward="
				+ reward + ", guarantee=" + guarantee + ", start_time="
				+ start_time + ", end_time=" + end_time + ", invest_num="
				+ invest_num + ", c_reward=" + c_reward + "]";
	}
}
