package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;

import com.goochou.p2b.dao.PaymentCheckMapper;
import com.goochou.p2b.model.PaymentCheckView;

public interface PaymentCheckService {
	PaymentCheckMapper getPaymentCheckMapper();
	void doLoadFileByDate(Date date) throws Exception;
	
	List<PaymentCheckView> listPaymentCheckView(String orderNo, Date startDate, Date endDate, String channel, Integer warningType, Integer limitStart, Integer limitEnd);

	Integer countPaymentCheckView(String orderNo, Date startDate, Date endDate, String channel, Integer warningType);
 
}
