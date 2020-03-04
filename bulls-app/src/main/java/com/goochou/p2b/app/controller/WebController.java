package com.goochou.p2b.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.WebResult;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.BankCardExample;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.InvestmentExample;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.RechargeExample;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.BankCardService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.BigDecimalUtil;


@Controller
@RequestMapping("/web")
public class WebController extends BaseController {
	private static final Logger logger = Logger.getLogger(WebController.class);
	public static String NO_LOGIN = "2";
	public static String SUCCESS = "1";
	public static String FAILED = "0";
	public static final String MESSAGE_NO_LOGIN = "您还没有登录";
	public static final String MESSAGE_EXCEPTION = "您的网络好像有点问题";
	@Autowired
	private AssetsService assetsService;
	@Autowired
	protected UserService userService;
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private RechargeService rechargeService;

	/**
	 * web充值用户中心信息
	 * 
	 * @Title: home
	 * @param request
	 * @param appVersion
	 * @param client
	 * @param token
	 * @return
	 * @throws Exception WebResult
	 * @author zj
	 * @date 2019-08-02 14:27
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "用户个人WEB充值中心基本信息")
	public WebResult home(HttpServletRequest request, @ApiParam("app版本号") @RequestParam(required = false) String appVersion,
			@ApiParam("终端来源 IOS,Android,PC,WAP,WEB") @RequestParam(required = false) String client) {

		User user = (User) request.getSession().getAttribute("user");
		if (null == user) {
			return new WebResult(NO_LOGIN, MESSAGE_NO_LOGIN);
		}
		try {
			// 资金信息
			Map<String, Object> fundMap = new HashMap<String, Object>();
			List<Investment> invests = new ArrayList<Investment>();

			Assets assets = assetsService.findByuserId(user.getId());
			double totalAmount = investmentService.getAmountCount(user.getId());
			totalAmount = BigDecimalUtil.add(totalAmount, assets.getBalanceAmount(), assets.getFrozenAmount(), assets.getCreditAmount(),
					assets.getFreozenCreditAmount());

			// 牛只订单
			InvestmentExample example = new InvestmentExample();
			example.createCriteria().andUserIdEqualTo(user.getId()).andOrderStatusNotEqualTo(3);// 订单取消的不统计
			invests = investmentService.getMapper().selectByExample(example);
			double interestAmount = 0.00;
			for (Investment i : invests) {
				// 已卖牛
				if (i.getOrderStatus() == 2) {
					interestAmount = BigDecimalUtil.add(interestAmount, i.getInterestAmount());
				}
			}

			fundMap.put("blance", assets.getBalanceAmount());// 余额
			fundMap.put("totalAssets", totalAmount);// 总资产
			fundMap.put("freezeProfit", assets.getCreditAmount());// 冻结利润
			fundMap.put("interestAmount", interestAmount);// 累计利润

			Map<String, Object> userCenterInfo = new HashMap<String, Object>();
			userCenterInfo.put("fundInfo", fundMap);// 存放用户资金信息集合

			Map<String, Object> userinfoMap = new HashMap<String, Object>();
			userinfoMap.put("phone", user.getPhone());
			if (StringUtils.isEmpty(user.getTrueName())) {
				userinfoMap.put("realName", "未实名");// 未实名
				userinfoMap.put("nameUrl", ClientConstants.ALIBABA_PATH + "images/pc/img_idcard_false.png");
			} else {
				userinfoMap.put("realName", "已实名");// 已实名
				userinfoMap.put("nameUrl", ClientConstants.ALIBABA_PATH + "images/pc/img_idcard_true.png");
			}

			BankCardExample example2 = new BankCardExample();
			example2.createCriteria().andUserIdEqualTo(user.getId()).andStatusEqualTo(0);
			int cardCount = bankCardService.getBankCardCountByUserId(user.getId());
			if (cardCount == 0) {
				userinfoMap.put("haveCard", "未绑卡");// 未绑卡
				userinfoMap.put("cardImgUrl", ClientConstants.ALIBABA_PATH + "images/pc/img_bankcard_false.png");
			} else {
				userinfoMap.put("haveCard", "已绑卡");// 已否绑卡
				userinfoMap.put("cardImgUrl", ClientConstants.ALIBABA_PATH + "images/pc/img_bankcard_true.png");
			}

			userinfoMap.put("headincon", ClientConstants.ALIBABA_PATH + "images/pc/tonglian_logo.png");// 用户头像

			userCenterInfo.put("userInfo", userinfoMap);// 存放用户基本信息集合

			// 验证码 使用 /captcha4j/getCaptImg 这个接口
			return new WebResult(SUCCESS, "查询成功", userCenterInfo);
		} catch (Exception e) {
			logger.error("用户中心信息查询出错==========>" + e.getMessage(), e);
			return new WebResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

//	/**
//	 * 校验随机码
//	 * 
//	 * @Title: checkCode
//	 * @param request
//	 * @param session
//	 * @param appVersion
//	 * @param client
//	 * @param verifyCode
//	 * @param deviceToken
//	 * @return AppResult
//	 * @author zj
//	 * @date 2019-08-02 14:28
//	 */
//	@RequestMapping(value = "/checkCode", method = RequestMethod.POST)
//	@ResponseBody
//	@ApiOperation(value = "校验随机码")
//	public WebResult checkCode(HttpServletRequest request, HttpSession session, @ApiParam("app版本号") @RequestParam(required = false) String appVersion,
//			@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = false) String client,
//			@ApiParam("capImgCode") @RequestParam(required = true) String capImgCode,
//			@ApiParam("phone") @RequestParam(required = true) String phone) {
//		try {
//			Map<String, Object> m = checkCapImgCode(Constants.CAP_IMG_CODE + phone, capImgCode, request);
//			if (!m.get(STATUS).equals(Status.OK)) {
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("error", Constants.CAP_IMG_CODE);
//				return new WebResult(FAILED, "验证失败");
//			}
//			return new WebResult(SUCCESS, "验证成功");
//		} catch (Exception e) {
//			logger.error("随机码校验失败===========>" + e.getMessage());
//			e.printStackTrace();
//			return new WebResult(FAILED, MESSAGE_EXCEPTION);
//		}
//	}

	/**
	 * 检查充值结果
	 * 
	 * @Title: checkRecharge
	 * @param request
	 * @param session
	 * @param appVersion
	 * @param client
	 * @param capImgCode
	 * @param phone
	 * @return WebResult
	 * @author zj
	 * @date 2019-08-02 15:45
	 */
	@RequestMapping(value = "/checkRecharge", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	@ApiOperation(value = "检查WEB充值结果")
	public WebResult checkRecharge(HttpServletRequest request, HttpSession session,
			@ApiParam("app版本号") @RequestParam(required = false) String appVersion,
			@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = false) String client,
			@ApiParam("rechargeOrderNo") @RequestParam(required = true) String rechargeOrderNo) {
		try {

			RechargeExample rechargeExample = new RechargeExample();
			rechargeExample.createCriteria().andOrderNoEqualTo(rechargeOrderNo);
			List<Recharge> list = rechargeService.getMapper().selectByExample(rechargeExample);
			if (CollectionUtils.isEmpty(list)) {
				return new WebResult("1", "处理中");
			} else {
				return new WebResult(list.get(0).getStatus() + "",
						list.get(0).getStatus() == 0 ? "充值成功" : list.get(0).getStatus() == 2 ? "充值失败" : list.get(0).getStatus() == 1 ? "处理中" : "");
			}
		} catch (Exception e) {
			logger.error("随机码校验失败===========>" + e.getMessage());
			e.printStackTrace();
			return new WebResult("-1", MESSAGE_EXCEPTION);
		}
	}

	/**
	 * 获取用户登录信息
	 * 
	 * @Title: getUserSessionInfo
	 * @param request
	 * @param session
	 * @param appVersion
	 * @param client
	 * @return WebResult
	 * @author zj
	 * @date 2019-08-05 09:56
	 */
	@RequestMapping(value = "/getUserSessionInfo", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取登录用户信息")
	public WebResult getUserSessionInfo(HttpServletRequest request, HttpSession session,
			@ApiParam("app版本号") @RequestParam(required = false) String appVersion,
			@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = false) String client) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			return new WebResult(SUCCESS, SUCCESS_MSG, user);
		} catch (Exception e) {
			logger.error("获取登录用户信息失败===========>" + e.getMessage());
			e.printStackTrace();
			return new WebResult("-1", MESSAGE_EXCEPTION);
		}
	}
}
