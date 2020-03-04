package com.goochou.p2b.module.address;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.user.UserAddressDeleteRequest;
import com.goochou.p2b.model.UserAddress;
import com.goochou.p2b.service.UserAddressService;

@Service
public class DeleteUserAddressFace  implements HessianInterface{
	@Resource
	private UserAddressService userAddressService;
	
	/**
	 * Code:400  	地址ID不能为空
	 * Code:401  	用户ID不能为空
	 * Code:410  	参数有误
	 * Code:411 	地址ID有误
	 * Code:412 	地址归属有误
	 * Code:500 	地址保存异常
	 */
	@Override
	public Response execute(ServiceMessage msg) {
		UserAddressDeleteRequest req =  (UserAddressDeleteRequest) msg.getReq();
		Response response = new Response();
		Integer addressId = req.getAddressId();
		
		if(addressId == null) {
			response.setSuccess(false);
			response.setErrorCode("400");
            response.setErrorMsg("你还没有填写收货地址");
            return response;
		}
		
		Integer userId = req.getUserId();
		if(userId == null) {
			response.setSuccess(false);
			response.setErrorCode("401");
            response.setErrorMsg("您还没有填写手机号");
            return response;
		}
		
		try {
			UserAddress userAddress = userAddressService.selectKeyUserAddress(addressId);
			
	        if (userAddress == null) {
	        	response.setSuccess(false);
	        	response.setErrorCode("411");
	            response.setErrorMsg("您删除的地址不存在！");
	            return response;
	        }
	        if( !userAddress.getUserId().equals(userId)) {
				response.setSuccess(false);
				response.setErrorCode("410");
	            response.setErrorMsg("地址归属错误");
	            return response;
			}
	        userAddress.setReserve(2);//逻辑删除
	        userAddressService.updateAddress(userAddress);
	        response.setSuccess(true);
	        return response;
	        
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorCode("500");
            response.setErrorMsg("删除收货地址异常");
            return response;
		}
        
	}

	@Override
	public void before(ServiceMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after(ServiceMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
