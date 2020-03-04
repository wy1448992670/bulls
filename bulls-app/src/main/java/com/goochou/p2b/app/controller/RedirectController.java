package com.goochou.p2b.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.UserService;


@Controller
@RequestMapping("/")
@Api(value = "跳转")
public class RedirectController extends BaseController {

	private static final Logger logger = Logger.getLogger(RedirectController.class);

	@Resource
	private UserService userService;
	//ClientConstants.H5_URL + "inviteNew.html?inviteCode=" + inviter.getInviteCode()
	@RequestMapping(value = "/i", method ={RequestMethod.POST, RequestMethod.GET})
	@ApiOperation(value = "推荐投资推广分享查看")
	public String redirectInviteNew(HttpServletRequest request, @ApiParam("userId") @RequestParam int i) {
		User user = userService.get(i);
		//ModelAndView model = new ModelAndView(ClientConstants.H5_URL + "inviteNew.html");
		//model.addObject("inviteCode", user.getInviteCode());
		return "redirect:"+ClientConstants.H5_URL + "inviteNew.html?inviteCode=" + user.getInviteCode();
	}
}
