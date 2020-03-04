package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.pay.fuiou.BankCardResqData;


public class FuiouBindCardResponse extends Response {
	
	/**
     *
     */
    private static final long serialVersionUID = 4693773332514810606L;
    
    private BankCardResqData bankCardResqData;
    
    public BankCardResqData getBankCardResqData() {
        return bankCardResqData;
    }
    public void setBankCardResqData(BankCardResqData bankCardResqData) {
        this.bankCardResqData = bankCardResqData;
    }
}
