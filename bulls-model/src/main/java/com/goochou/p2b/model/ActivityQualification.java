package com.goochou.p2b.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 1、<p><p>
 * 参与抽奖资格实体对象
 * @author mr_zou
 * @date 2017/12/4 18:13
 */
public class ActivityQualification implements Serializable{

    /**
     * 主键id
     */
    private long idx;

    /**
     * uuid
     */
    private String id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 抽奖机会次数
     */
    private Integer opportunityNumber;

    /**
     * 活动Id
     */
    private Integer activityId;

    /**
     * 奖品档次
     */
    private String prizeLevel;

    /**
     * 版本乐观锁
     */
    private Integer version;

    /**
     * 是否有效
     */
    private int isDeleted;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 操作人id
     */
    private String operateId;

    /**
     * 操作人名称
     */
    private long operateName;

    public long getIdx() {
        return idx;
    }

    public void setIdx(long idx) {
        this.idx = idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOpportunityNumber() {
        return opportunityNumber;
    }

    public void setOpportunityNumber(Integer opportunityNumber) {
        this.opportunityNumber = opportunityNumber;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getPrizeLevel() {
        return prizeLevel;
    }

    public void setPrizeLevel(String prizeLevel) {
        this.prizeLevel = prizeLevel;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public long getOperateName() {
        return operateName;
    }

    public void setOperateName(long operateName) {
        this.operateName = operateName;
    }
}
