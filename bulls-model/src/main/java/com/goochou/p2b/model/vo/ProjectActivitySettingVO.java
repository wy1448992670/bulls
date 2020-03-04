package com.goochou.p2b.model.vo;

import java.io.Serializable;
import java.util.Date;

public class ProjectActivitySettingVO implements Serializable {
    
    private Integer id;

    private Integer projectId;
    
    private Integer period;

    private Integer winNumber;

    private Integer userId;

    private String trueName;

    private Date createTime;

    private Date updateTime;

    private Date openTime;

    private Integer operateId;

    private String operateName;

    private String projectName;
    
    //当前状态
    private String status;
    //幸运号码个数
    private String winNumberCount;
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	//当前状态描述
    private String statusStr;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(Integer winNumber) {
        this.winNumber = winNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public String getOperateName() {
        return operateName;
    }

    public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getWinNumberCount() {
        return winNumberCount;
    }

    public void setWinNumberCount(String winNumberCount) {
        this.winNumberCount = winNumberCount;
    }
}