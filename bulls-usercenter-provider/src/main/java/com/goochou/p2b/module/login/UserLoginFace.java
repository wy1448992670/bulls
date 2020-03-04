package com.goochou.p2b.module.login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.user.UserLoginRequest;
import com.goochou.p2b.hessian.user.UserResponse;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.UserService;

@Service
public class UserLoginFace implements HessianInterface{
	@Resource
	protected UserService userService;

	/**
	 * Code:400  	登录类型错误
	 * Code:401  	账号为空
	 * Code:402   	登录密码为空
	 * Code:410 	登录失败 
	 * Code:500 	登录异常
	 */
	@Override
	public UserResponse execute(ServiceMessage msg) {
		UserLoginRequest reqLogin =  (UserLoginRequest) msg.getReq();
		UserResponse response = new UserResponse();
		try {
		
			String account = reqLogin.getAccount();
			String password = reqLogin.getPassword();
			Integer type = reqLogin.getLoginType();
			String ip = reqLogin.getIp();
			String appVersion = reqLogin.getAppVersion();
			
			if(type == null || (type != 1 && type != 2 )) {
				response.setSuccess(false);
				response.setErrorCode("400");
				response.setErrorMsg("请选择正确的登录方式");
				return response;
			}
			
			if(account == null ) {
				response.setSuccess(false);
				response.setErrorCode("401");
				response.setErrorMsg("您还没有输入账号");
				return response;
			}
			Map<String, Object> map = new HashMap<>();
            
			if(type == 1) { //密码登录
				if(password == null) {
					response.setSuccess(false);
					response.setErrorCode("402");
					response.setErrorMsg("您还没有输入密码");
					return response;
				}
				map = userService.login(account, password, ip, reqLogin.getDeviceToken(), appVersion, reqLogin.getClient());
				
			} else if(type == 2) { //手机号登录
				map = userService.loginMobile(account, ip,  reqLogin.getClient(), appVersion);
			} 
			
			if (map == null || map.size() == 0) {
				response.setSuccess(false);
				response.setErrorCode("410");
				response.setErrorMsg("登录失败");
				return response;
	        }
			if((int) map.get("ret") != 0) {
				response.setSuccess(false);
				response.setErrorMsg(map.get("msg")+"");
				return response;
			}
			
			User user = (User) map.get("user");
			response.setUser(user);
			response.setSuccess(true);
            
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorCode("500");
			response.setErrorMsg("登录异常");
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
