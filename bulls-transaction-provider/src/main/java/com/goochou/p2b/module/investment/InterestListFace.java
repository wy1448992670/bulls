package com.goochou.p2b.module.investment;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.hessian.ListResponse;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.investment.InterestListRequest;
import com.goochou.p2b.service.InterestService;

@Service
@Deprecated
public class InterestListFace implements HessianInterface {
	
	@Autowired
	private InterestService interestService;
	
	@Override
	public ListResponse execute(ServiceMessage msg) {
		InterestListRequest request = (InterestListRequest) msg.getReq();
        ListResponse response = new ListResponse();
        
		try {
			Integer userId = request.getUserId();
			Integer investId = request.getInvestId();
			
			List<Map<String, Object>> list = interestService.getInterestListByInvestId(investId,userId);
			
			response.setSuccess(true);
			response.setList(list);
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
