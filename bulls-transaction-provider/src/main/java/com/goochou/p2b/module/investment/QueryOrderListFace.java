package com.goochou.p2b.module.investment;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderListRequest;
import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderListResponse;
import com.goochou.p2b.model.vo.InvestmentOrderListVO;
import com.goochou.p2b.service.PastureOrderService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryOrderListFace  implements HessianInterface {

    @Autowired
    private PastureOrderService orderService;
 
    
    @Override
    public InvestmentOrderListResponse execute(ServiceMessage msg) {

    	InvestmentOrderListRequest orderRequest = (InvestmentOrderListRequest) msg.getReq();
    	InvestmentOrderListResponse response = new InvestmentOrderListResponse();
        try {
        	List<InvestmentOrderListVO> list = orderService.queryPayList(orderRequest);
        	int count = orderService.queryPayListCount(orderRequest);
            response.setSuccess(true);
            response.setList(list);
            response.setCount(count);
        } catch (Exception e) {
        	e.printStackTrace();
            OpenApiApp.EXCEPTION.exception(msg, e);
            response.setSuccess(false);
            response.setErrorMsg("出现异常");
        }
        return response;
    }

    @Override
    public void before(ServiceMessage msg) {

    }

    @Override
    public void after(ServiceMessage msg) {

    }
}