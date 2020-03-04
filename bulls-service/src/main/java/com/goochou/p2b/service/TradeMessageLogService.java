package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.model.vo.TradeMessageLogVO;

public interface TradeMessageLogService {

    /**
     * 修改报文日志
     * @param tradeMessageLog
     */
    public boolean saveTradeMessageLog(TradeMessageLog tradeMessageLog);
    
    /**
	 * 修改外部报文日志处理状态
	 * @param outOrderId
	 * @param messageType
	 * @param status
	 * @return
	 */
	public boolean updateTradeMessageLogStatus(TradeMessageLogVO tradeMessageLogVO);
	
	/**
	 * 查询报文信息
	 * @param outOrderId
	 * @param messageType
	 * @return
	 */
	public TradeMessageLog getTradeMessageLog(String outOrderId, String messageType);
	
	/**
	 * 根据状态查询交易日志报文
	 * @param status
	 * @param limit
	 * @return
	 */
	public List<TradeMessageLog> selectTradeMessageLog(String status, String messageType, Integer limit);

}
