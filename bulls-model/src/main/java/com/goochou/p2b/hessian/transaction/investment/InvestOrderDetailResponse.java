package com.goochou.p2b.hessian.transaction.investment;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.vo.InvestmentDetailsVO;

public class InvestOrderDetailResponse extends Response {

    /**
     *
     */
    private static final long serialVersionUID = -6371791263419857590L;
	
    private InvestmentDetailsVO details;

    public InvestmentDetailsVO getDetails() {
        return details;
    }

    public void setDetails(InvestmentDetailsVO details) {
        this.details = details;
    }
}
