package com.goochou.p2b.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.TradeMessageLogMapper;
import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.model.vo.TradeMessageLogVO;
import com.goochou.p2b.service.TradeMessageLogService;

@Service
public class TradeMessageLogServiceImpl implements TradeMessageLogService {
	@Resource
	private TradeMessageLogMapper tradeMessageLogMapper;

	///////////////////////
	@Override
	public boolean saveTradeMessageLog(TradeMessageLog tradeMessageLog) {
			return tradeMessageLogMapper.insertTradeMessageLog(tradeMessageLog) > 0;
	}

	@Override
	public boolean updateTradeMessageLogStatus(TradeMessageLogVO tradeMessageLogVO) {
		return tradeMessageLogMapper.updateTradeMessageLogStatus(tradeMessageLogVO) > 0;
	}

	@Override
	public List<TradeMessageLog> selectTradeMessageLog(String status, String messageType, Integer limit) {
		Map<String, Object> params = new HashMap<>();
		params.put("status", status);
		params.put("messageType", messageType);
		params.put("limit", limit);
		return tradeMessageLogMapper.selectTradeMessageLog(params);
	}

	@Override
	public TradeMessageLog getTradeMessageLog(String outOrderId, String messageType) {
		Map<String, Object> params = new HashMap<>();
		params.put("outOrderId", outOrderId);
		params.put("messageType", messageType);
		return tradeMessageLogMapper.getTradeMessageLog(params);
	}
}
