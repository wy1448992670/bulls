package com.goochou.p2b.model;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.UUID;

/**
 * 外部报文日志
 * @author WuYJ
 *
 */
public class TradeMessageLog extends BaseDO {


    /**
	 * 
	 */
	private static final long serialVersionUID = -8210715159387024895L;
	
	/**
	 * 静态创建外部报文日志对象
	 * @param id
	 * @param messageUrl
	 * @param messageChannel
	 * @param messageOperate
	 * @param messageType
	 * @param userId
	 * @param inOrderId
	 * @param outOrderId
	 * @param messageInfoCiphertext
	 * @param messageInfo
	 * @param status
	 * @return
	 */
	public static TradeMessageLog createTradeMessageLogFactory( String messageUrl, String messageChannel, String messageOperate, String messageType,
			String userId, String inOrderId, String outOrderId, String messageInfoCiphertext, String messageInfo,
			String messageStatus, String status) {
		TradeMessageLog  tradeMessageLog = new TradeMessageLog();
		tradeMessageLog.id = UUID.randomUUID().toString();
		tradeMessageLog.messageUrl = messageUrl;
		tradeMessageLog.messageChannel = messageChannel;
		tradeMessageLog.messageOperate = messageOperate;
		tradeMessageLog.messageType = messageType;
		tradeMessageLog.userId = userId;
		tradeMessageLog.inOrderId = inOrderId;
		tradeMessageLog.outOrderId = outOrderId;
		tradeMessageLog.messageInfoCiphertext = messageInfoCiphertext;
		tradeMessageLog.messageInfo = messageInfo;
		tradeMessageLog.messageStatus = messageStatus;
		tradeMessageLog.status = status;
		tradeMessageLog.setOperateId(userId);
		tradeMessageLog.setOperateName(userId);
		return tradeMessageLog;
	}

	/**
	 * 赋值交易报文日志
	 * @param tradeMessageLog
	 * @param id
	 * @param messageUrl
	 * @param messageChannel
	 * @param messageOperate
	 * @param messageType
	 * @param userId
	 * @param inOrderId
	 * @param outOrderId
	 * @param messageInfoCiphertext
	 * @param messageInfo
	 * @param status
	 */
	public static void voluationTradeMessageLog(TradeMessageLog tradeMessageLog, String messageUrl, String messageChannel, String messageOperate, String messageType,
			String userId, String inOrderId, String outOrderId, String messageInfoCiphertext, String messageInfo,
			String messageStatus, String status) {
		tradeMessageLog.id = UUID.randomUUID().toString();
		tradeMessageLog.messageUrl = messageUrl;
		tradeMessageLog.messageChannel = messageChannel;
		tradeMessageLog.messageOperate = messageOperate;
		tradeMessageLog.messageType = messageType;
		tradeMessageLog.userId = userId;
		tradeMessageLog.inOrderId = inOrderId;
		tradeMessageLog.outOrderId = outOrderId;
		tradeMessageLog.messageInfoCiphertext = messageInfoCiphertext;
		tradeMessageLog.messageInfo = messageInfo;
		tradeMessageLog.messageStatus = messageStatus;
		tradeMessageLog.status = status;
	}
    /**
     * 发送地址
     */
    private String messageUrl;
    /**
     * 发送渠道
     */
    private String messageChannel;
    
    /**
     * 操作类型
     */
    private String messageOperate;
    
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
     * 发送内容 (加密后)
     */
    private String messageInfoCiphertext;
    /**
     * 内容
     */
    private String messageInfo;
    
    /**
     * 报文状态
     */
    private String messageStatus;

    /**
     * 状态根据需要确认使用
     */
    private String status;
    
	public String getMessageUrl() {
		return messageUrl;
	}

	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}

	public String getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(String messageChannel) {
		this.messageChannel = messageChannel;
	}

	public String getMessageOperate() {
		return messageOperate;
	}

	public void setMessageOperate(String messageOperate) {
		this.messageOperate = messageOperate;
	}

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

	public String getMessageInfoCiphertext() {
		return messageInfoCiphertext;
	}

	public void setMessageInfoCiphertext(String messageInfoCiphertext) {
		this.messageInfoCiphertext = messageInfoCiphertext;
	}

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public TradeMessageLog(String messageUrl, String messageChannel, String messageOperate, String messageType,
			String userId, String inOrderId, String outOrderId, String messageInfoCiphertext, String messageInfo,
			String messageStatus, String status) {
		super();
		this.id = UUID.randomUUID().toString();
		this.messageUrl = messageUrl;
		this.messageChannel = messageChannel;
		this.messageOperate = messageOperate;
		this.messageType = messageType;
		this.userId = userId;
		this.inOrderId = inOrderId;
		this.outOrderId = outOrderId;
		this.messageInfoCiphertext = messageInfoCiphertext;
		this.messageInfo = messageInfo;
		this.messageStatus = messageStatus;
		this.status = status;
	}

	public TradeMessageLog() {
		super();
		this.id = UUID.randomUUID().toString();
	}

	@Override
	public String toString() {
		return "TradeMessageLog [messageUrl=" + messageUrl + ", messageChannel=" + messageChannel + ", messageOperate="
				+ messageOperate + ", messageType=" + messageType + ", userId=" + userId + ", inOrderId=" + inOrderId
				+ ", outOrderId=" + outOrderId + ", messageInfoCiphertext=" + messageInfoCiphertext + ", messageInfo="
				+ messageInfo + ", messageStatus=" + messageStatus + ", status=" + status + "]";
	}
    
}

