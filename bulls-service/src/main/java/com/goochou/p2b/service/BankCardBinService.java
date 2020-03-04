package com.goochou.p2b.service;

import com.goochou.p2b.model.BankCardBin;

public interface BankCardBinService {
    
    /**
     * 查找卡bin信息
     * @author ydp
     * @param cardNo
     * @return
     * @throws Exception
     */
	public BankCardBin findBankCardBin(String cardNo) throws Exception;
}


