package com.goochou.p2b.module.register;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.user.UserRegisterRequest;
import com.goochou.p2b.hessian.user.UserResponse;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.UserService;

@Service
public class UserRegisterFace implements HessianInterface{
	@Resource
	protected UserService userService;
	/**
	 * Code:400  	该用户已经存在
	 * Code:401  	账号为空
	 * Code:510 	验证用户异常
	 */
	@Override
	public UserResponse execute(ServiceMessage msg) {
		UserRegisterRequest reqUser =  (UserRegisterRequest) msg.getReq();
		UserResponse response = new UserResponse();
		String username = reqUser.getUsername();
		try {
			if (StringUtils.isNotBlank(username) && !userService.checkNameExists(username, null)) {
				response.setSuccess(false);
				response.setErrorCode("400");
				response.setErrorMsg("该用户已经存在");
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode("510");
			response.setSuccess(false);
			response.setErrorMsg("验证用户异常");
			return response;
		}

		User inviter=null;
		if(reqUser.getInviteByCode()!=null) {
			inviter=userService.getByInviteCode(reqUser.getInviteByCode());
		}
		
        User user = new User();
        Date d = new Date();
        user.setPhone(reqUser.getPhone());
        user.setPassword(reqUser.getPassword());
        user.setUsername(username);
        user.setAvatarId(reqUser.getUploadId());
        user.setCreateDate(d);
        user.setLastLoginTime(d);
        user.setRegisterIp(reqUser.getRegisterIp());
        user.setLastLoginIp(reqUser.getRegisterIp());
        user.setClient(reqUser.getClient());
        user.setInviteByCode(reqUser.getInviteByCode());
        user.setDataSource(reqUser.getDataSource());
        user.setChannelId(reqUser.getChannelNo());
		if(inviter!=null && inviter.getDepartmentId()!=null) {
			user.setDepartmentId(inviter.getDepartmentId());
		}else {
			user.setDepartmentId(1);//新客
		}
		
		if("其他".equals(reqUser.getDataSource())) {
			user.setDataSource("-1");
		}
        try {
        	 Map<String, Object> map = userService.addUserByRegist(user);
             int code = (Integer) map.get("ret");
             if(code != 0) {
             	response.setSuccess(false);
             	response.setErrorCode(String.valueOf(code));
             	response.setErrorMsg(String.valueOf(map.get("msg")));
             } else {
             	response.setSuccess(true);
             	response.setUser((User)map.get("user"));
             }
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
         	response.setErrorCode("9");
         	response.setErrorMsg(e.getMessage());
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
