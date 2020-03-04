package com.goochou.p2b.model.vo;

/**
 * 外部报文日志
 * @author WuYJ
 *
 */
public class TradeMessageLogVO implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 5555855815274183973L;

	/**
     * 报文类别
     */
    private String messageType;

    /**
     * 用户内部账号
     */
    private String userId;

    /**
     * 内部订单编号
     */
    private String inOrderId;
    
    /**
     * 外部订单号
     */
    private String outOrderId;
    
    /**
     * 未加密签名内容
     */
    private String messageInfo;
    
    private String messageStatus;

    /**
     * 状态根据需要确认使用
     */
    private String editStatus;
    
    /**
     * 修改前状态
     */
    private String haveStatus;

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInOrderId() {
		return inOrderId;
	}

	public void setInOrderId(String inOrderId) {
		this.inOrderId = inOrderId;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	public String getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	public String getHaveStatus() {
		return haveStatus;
	}

	public void setHaveStatus(String haveStatus) {
		this.haveStatus = haveStatus;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

}

