package com.goochou.p2b.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 * @author WuYJ
 *
 */
public class BaseDO implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 4540130567753373814L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	protected Boolean isDeleted=true;
	
	protected Date createDate = new Date();
	
	protected Date updateDate= new Date();
	
	protected String operateId;
	
	protected String operateName;
	
	protected String id;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
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

	public String getOperateName()
    {
        return operateName;
    }

    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
}
