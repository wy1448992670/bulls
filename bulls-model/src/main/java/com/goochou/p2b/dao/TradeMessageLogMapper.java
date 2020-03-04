package com.goochou.p2b.dao;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.model.vo.TradeMessageLogVO;

public interface TradeMessageLogMapper {
    /**
	 * 添加外部报文日志
	 * @param tradeMessageLog
	 * @return
	 */
	public int insertTradeMessageLog(TradeMessageLog tradeMessageLog);
	
	/**
	 * 修改外部报文日志处理状态
	 * @param outOrderId
	 * @param messageType
	 * @param status
	 * @return
	 */
	public int updateTradeMessageLogStatus(TradeMessageLogVO tradeMessageLogVO);
	
	/**
	 * 根据状态查询交易日志报文
	 * @param status
	 * @param limit
	 * @return
	 */
	public List<TradeMessageLog> selectTradeMessageLog(Map<String, Object> params);
	
	/**
	 * 根据外部订单号查询报文信息
	 * @return
	 */
	public TradeMessageLog getTradeMessageLog(Map<String, Object> params);
}