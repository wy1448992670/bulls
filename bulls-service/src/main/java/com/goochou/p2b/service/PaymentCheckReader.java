package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.PaymentCheck;

public interface PaymentCheckReader {

	List<PaymentCheck> readFile(String filePath) throws Exception;
}
