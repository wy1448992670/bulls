package com.goochou.p2b.model.vo;

public class ProjectTabVO {
	private String tabType;
	private String tabName;
	private boolean tabFlag;
	private String url;
	public boolean isTabFlag() {
		return tabFlag;
	}
	public void setTabFlag(boolean tabFlag) {
		this.tabFlag = tabFlag;
	}
	public String getTabType() {
		return tabType;
	}
	public void setTabType(String tabType) {
		this.tabType = tabType;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ProjectTabVO(String tabType, String tabName, boolean tabFlag) {
		this.tabType = tabType;
		this.tabName = tabName;
		this.tabFlag = tabFlag;
	}

	public ProjectTabVO(String tabType, String tabName, boolean tabFlag, String url) {
		this.tabType = tabType;
		this.tabName = tabName;
		this.tabFlag = tabFlag;
		this.url = url;
	}
}
