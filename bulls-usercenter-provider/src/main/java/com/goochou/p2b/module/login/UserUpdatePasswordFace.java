package com.goochou.p2b.module.login;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fuiou.util.MD5;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.user.UserUpdatePasswordRequest;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.UserService;

@Service
public class UserUpdatePasswordFace implements HessianInterface{
	@Resource
	protected UserService userService;
	/**
	 * Code:410 	2次密码输入不一致
	 * Code:411 	密码为英文数字组合6-12位
	 * Code:412 	用户不存在
	 * Code:500 	重置密码异常
	 */
	@Override
	public Response execute(ServiceMessage msg) {
		UserUpdatePasswordRequest updatePwdReq =  (UserUpdatePasswordRequest) msg.getReq();
		Response response = new Response();
		try {
			String phone = updatePwdReq.getPhone();
			String password = updatePwdReq.getNewPassword();
			String confirmPassword = updatePwdReq.getConfirmPassoword();
			
			if(!password.equals(confirmPassword)) {
				response.setSuccess(false);
				response.setErrorCode("410");
				response.setErrorMsg("两次密码输入不一致");
                return response; 
			}
			if (!userService.checkPassword(password)) {
				response.setSuccess(false);
				response.setErrorCode("411");
				response.setErrorMsg("密码为英文数字组合6-12位");
                return response;  
            }
			User user = userService.getByPhone(phone);
			if(user == null) {
				response.setSuccess(false);
				response.setErrorCode("412");
				response.setErrorMsg("该用户不存在，请输入正确的手机号");
                return response;  
			}
//            user.setPassword(MD5.MD5Encode(password + Constants.PASSWORD_FEX));
			userService.setBackPassword(user.getId(), password);
			 
			//重置密码时,错误次数重置为0.
			User errorCount = new User();
			errorCount.setId(user.getId());
			errorCount.setErrorCount(0);
            userService.update(errorCount);
            response.setSuccess(true);
			return response;
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorCode("500");
			response.setErrorMsg("重置密码异常");
			return response;
		}
		
	}

	@Override
	public void before(ServiceMessage msg) {
		
	}

	@Override
	public void after(ServiceMessage msg) {
		
	}

}
