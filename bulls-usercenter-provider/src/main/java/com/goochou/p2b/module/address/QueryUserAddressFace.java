package com.goochou.p2b.module.address;

import java.util.List;

import javax.annotation.Resource;

import com.goochou.p2b.model.UserAddressExample;
import org.springframework.stereotype.Service;

import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.user.UserAddressListRequest;
import com.goochou.p2b.hessian.user.UserAddressListResponse;
import com.goochou.p2b.model.UserAddress;
import com.goochou.p2b.service.UserAddressService;

@Service
public class QueryUserAddressFace  implements HessianInterface{
	@Resource
	private UserAddressService userAddressService;
	
	@Override
	public UserAddressListResponse execute(ServiceMessage msg) {
		UserAddressListRequest req =  (UserAddressListRequest) msg.getReq();
		UserAddressListResponse response = new UserAddressListResponse();
		
		try {
			Integer page = req.getPage();
			if (page == null || page == 0) {
	            page = 1;
	        }
			
			int limit = 10;
			Integer userId = req.getUserId(); 
//	        List<UserAddress> list = userAddressService.selectAllAddress(userId, (page - 1) * limit, limit);
//	        Integer count = userAddressService.selectAllAddressCount(userId);
			UserAddressExample example = new UserAddressExample();
			example.setLimitStart((page - 1) * limit);
			example.setLimitEnd(limit);
			example.setOrderByClause("reserve desc, id desc");
			example.createCriteria().andUserIdEqualTo(userId).andReserveNotEqualTo(2);
			List<UserAddress> list = userAddressService.getUserAddressMapper().listUserAddress(example);
			int count = userAddressService.getUserAddressMapper().countUserAddress(example);
			response.setSuccess(true);
	        response.setCount(count);
	        response.setList(list);
	        
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg("查询列表异常");
		}
		
		return response;
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
