package com.goochou.p2b.model.vo.p2peye;

import java.text.ParseException;
import com.goochou.p2b.utils.DateUtil;

/**
 * 网贷天眼投资信息
 * @author xueqi
 *
 */
public class P2pEyeInvestData {
	
	private String id;
	
	private String link;
	
	private String username;
	
	private String userid;
	
	private Double account;
	
	private String add_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public Double getAccount() {
		return account;
	}

	public void setAccount(Double account) {
		this.account = account;
	}

	public String getAdd_time() {
		try {
			if (add_time != null) {
				return DateUtil.dateFullTimeFormat.format(DateUtil.dateFullTimeFormat.parse(add_time));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void setAddTime(String add_time) {
		this.add_time = add_time;
	}

	@Override
	public String toString() {
		return "P2pEyeInvestData [id=" + id + ", link=" + link + ", username="
				+ username + ", userid=" + userid + ", account=" + account
				+ ", add_time=" + add_time + "]";
	}
	

	
	
	
}
