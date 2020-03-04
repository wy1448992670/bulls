package com.goochou.p2b.app.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.model.vo.AreaIndexVO;
import com.goochou.p2b.service.*;
import com.goochou.p2b.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.fuiou.util.MD5;
import com.goochou.p2b.annotationin.NeedLogin;
import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.dao.AdvertisementChannelMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.client.UserCenterClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;
import com.goochou.p2b.hessian.openapi.pay.AllinPayBindCardRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayBindCardResponse;
import com.goochou.p2b.hessian.openapi.pay.FuiouBindCardRequest;
import com.goochou.p2b.hessian.openapi.pay.FuiouBindCardResponse;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListRequest;
import com.goochou.p2b.hessian.user.UserAddressDeleteRequest;
import com.goochou.p2b.hessian.user.UserAddressListRequest;
import com.goochou.p2b.hessian.user.UserAddressListResponse;
import com.goochou.p2b.hessian.user.UserAddressRequest;
import com.goochou.p2b.hessian.user.UserAddressResponse;
import com.goochou.p2b.hessian.user.UserLoginRequest;
import com.goochou.p2b.hessian.user.UserRegisterRequest;
import com.goochou.p2b.hessian.user.UserResponse;
import com.goochou.p2b.hessian.user.UserUpdatePasswordRequest;
import com.goochou.p2b.model.AdvertisementChannel;
import com.goochou.p2b.model.AdvertisementChannelExample;
import com.goochou.p2b.model.AppVersionContentWithBLOBs;
import com.goochou.p2b.model.Assess;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Bank;
import com.goochou.p2b.model.BankCard;
import com.goochou.p2b.model.BankCardBin;
import com.goochou.p2b.model.BankExample;
import com.goochou.p2b.model.CodeLimit;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.HongbaoRedeem;
import com.goochou.p2b.model.Icon;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.InvestmentExample;
import com.goochou.p2b.model.LoginRecord;
import com.goochou.p2b.model.ProvinceCity;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAddress;
import com.goochou.p2b.model.UserAuthentication;
import com.goochou.p2b.model.UserPhoneExchange;
import com.goochou.p2b.model.VipDividend;
import com.goochou.p2b.model.VipDividendExample;
import com.goochou.p2b.model.goods.GoodsOrderInvestRelation;
import com.goochou.p2b.model.goods.GoodsOrderInvestRelationExample;
import com.goochou.p2b.model.vo.CityVO;
import com.goochou.p2b.model.vo.IconIndexVO;
import com.goochou.p2b.model.vo.ProvinceVO;
import com.goochou.p2b.utils.alipay.AlipayConfig;


@Controller
@RequestMapping("user")
@Api(value = "用户-user")
public class UserController extends BaseController {
    private static final Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private AssetsService assetsService;
    @Resource
    private UploadService uploadService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private BankCardService bankCardService;
    @Resource
    private WithdrawService withdrawService;
    @Resource
    private UserSignedService userSignedService;
    @Resource
    private YaoCountService yaoCountService;
    @Resource
    private RateCouponService rateCouponService;
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private IconService iconService;
    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private MessageReceiverService messageReceiverService;
    @Resource
    private UserInviteService userInviteService;
    @Resource
    private ActivityService activityService;
    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private LoginRecordService loginRecordService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private CodeLimitService codeLimitService;
    @Resource
    private HongbaoRedeemService hongbaoRedeemService;
    @Resource
    private HongbaoTemplateService hongbaoTemplateService;
    @Resource
    private MessageService messageService;
    @Resource
    private ProductService productService;
    @Resource
    private BankService bankService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private UserAuthenticationService userAuthenticationService;
    @Resource
    private ProvinceCityService provinceCityService;
    @Resource
    private VipDividendService vipDividendService;
    @Resource
    private GoodsOrderInvestRelationService goodsOrderInvestRelationService;
    @Resource
    private BankCardBinService bankCardBinService;
    @Autowired
    private ProjectService projectService;

    @Resource
    private AdvertisementChannelService advertisementChannelService;
    @Resource
    private RechargeService rechargeService;
    @Resource
    private AreaService areaService;
    @Resource
    private GoodsOrderService goodsOrderService;
    /**
     * 查询实名信息
     * @param request
     * @param session
     * @param token
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/getAuthInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询实名信息")
    public AppResult getAuthInfo(HttpServletRequest request, HttpSession session,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("app版本号") @RequestParam String appVersion) {
        try {
            User user = userService.checkLogin(token);
            if (null == user) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("trueName", CommonUtils.getTrueName(user.getTrueName()));
            map.put("identityCard", CommonUtils.getIDCard(user.getIdentityCard()));
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * 上传用户头像
     * @param session
     * @param file
     * @param token
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/edit/avatar", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传用户头像")
    public AppResult editAvatar(HttpSession session,
                                @ApiParam("头像图片") @RequestParam("file") MultipartFile file,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("app版本号") @RequestParam String appVersion) {

        if (file == null) {
            return new AppResult(FAILED, "头像是空的");
        }
        try {
            User user = userService.checkLogin(token);
            if(null == user) {
            	 return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Map<String, Object> updateResult = uploadService.save(file, 0, user == null ? null : user.getId());
            if (user != null) {
                uploadService.updateAvatarUserId(user.getId(), Integer.valueOf(updateResult.get("id").toString()));
            }
            String status = (String) updateResult.get("status");
            if ("error".equals(status)) {
                String message = (String) updateResult.get("message");
                return new AppResult(FAILED, message);
            }
            session.setAttribute("uploadId", updateResult.get("id"));
            return new AppResult(SUCCESS, new HashMap<String, Object>().put("step", 0));
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }

    }

    /**
     * @date 2019年5月13日
     * @author wangyun
     * @time 下午4:27:17
     * 获取手机验证码
     * @param request
     * @param session
     * @param username
     * @param phone
     * @param appVersion
     * @param password
     * @param inviteCode
     * @param imei
     * @param capImgCode
     * @param client
     * @return
     */
    @RequestMapping(value = "/regist1", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "注册第一步发送验证码")
    public AppResult regist1(HttpServletRequest request, HttpSession session,
                             @ApiParam("用户昵称") @RequestParam(required = false) String username,
                             @ApiParam("手机号") @RequestParam String phone,
                             @ApiParam("app版本号") @RequestParam String appVersion,
                             @ApiParam("登录密码") @RequestParam String password,
                             @ApiParam("邀请码") @RequestParam(required = false) String inviteCode,
                             @ApiParam("imei") @RequestParam(required = false) String imei,
                             @ApiParam("capImgCode") @RequestParam(required = false) String capImgCode,
                             @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                             @ApiParam("注册市场来源") @RequestParam(required = false) String dataSource) {
        try {
            logger.info("用户注册==>phone=" + phone + ";inviteCode=" + inviteCode + ";capImgCode=" + capImgCode + "client=" + client);
            if (!userService.checkPhone(phone)) {
                return new AppResult(FAILED, "手机号长度为11位");
            }
           /* if (StringUtils.isNotBlank(username) && !userService.checkUsername(username)) {
                return new AppResult(FAILED, "不合法的昵称");
            }
            if (StringUtils.isNotBlank(username) && !userService.checkNameExists(username, null)) {
                return new AppResult(FAILED, "昵称已经存在");
            }*/
            if (Constants.YES.equals(getCacheKeyValue(DictConstants.SHOW_CAPTIMG_PROMPT))) {
                Map<String, Object> m = checkCapImgCode(Constants.CAP_IMG_CODE + phone, capImgCode, request);
                if (!m.get(STATUS).equals(Status.OK)) {
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("error", Constants.CAP_IMG_CODE);
                    return new AppResult(FAILED, m.get("msg").toString(), map);
                }
            }
            if(RegularUtils.checkString(password, RegularUtils.REGEX_BLANK)) {
            	 return new AppResult(FAILED, "密码不能含空格");
            }
            if (!userService.checkPassword(password)) {
                return new AppResult(FAILED, "密码为英文数字组合6-12位");
            }
            logger.info("用户注册==>phone=" + phone + ";inviteCode=" + inviteCode);
            int ret1 = userService.check(phone, "phone");
            logger.info("用户注册==>ret1=" + ret1);

            if (ret1 == 0) {
                String verifyCode = null;
                if (null != session.getAttribute("lastSendTime")) {
                    long diffTime = System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime();
                    if (diffTime <= Constants.ALLOW_SECOUND * 1000) {
                        int secound = Constants.ALLOW_SECOUND-Integer.valueOf((diffTime/1000)+"");
                        return new AppResult(FAILED, "请一分钟后再发送验证码", secound);
                    }
                }

                verifyCode = generateVerifyCode(6);
                if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
                	verifyCode = "000000";
                }
                logger.info("短信验证码："+verifyCode);
                SendMessageRequest smr = new SendMessageRequest();
                smr.setContent(DictConstants.VALIDATE_CODE.replaceAll("\\{code}", verifyCode));
                smr.setPhone(phone);
                ServiceMessage msg = new ServiceMessage("message.send", smr);
                SendMessageResponse result = (SendMessageResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if (result.isSuccess()) {
                    session.setAttribute("verifyPhone", phone);
//                    session.setAttribute("username", username);
                    session.setAttribute("password", password);
                    session.setAttribute("verifyCode", verifyCode);
                    session.setAttribute("lastSendTime", new Date());
                    session.setAttribute("inviteCode", inviteCode);
                    session.setAttribute("imei", imei);
                    session.setAttribute("source", client);
                    if(StringUtils.isEmpty(dataSource)) {
                    	dataSource = "0" ;//平台
                    }
                    session.setAttribute("dataSource", dataSource);
                    CodeLimit limit = new CodeLimit();
                    limit.setCode(verifyCode);
                    limit.setCreateTime(new Date());
                    limit.setPhone(phone);
                    codeLimitService.save(limit);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("step", 1);
                    return new AppResult(SUCCESS, map);
                } else {
                    return new AppResult(FAILED, "验证码发送失败，请稍后重试。");
                }
            } else if (ret1 == 1) {
                return new AppResult(FAILED, "请输入正确的手机号");
            } else if (ret1 == 2) {
                return new AppResult(FAILED, "该手机号已被注册，请更换手机号");
            } else {
                return new AppResult(FAILED, "系统繁忙");
            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * @date 2019年5月13日
     * @author wangyun
     * @time 下午4:25:34
     * 提交注册接口
     * @param request
     * @param session
     * @param appVersion
     * @param client
     * @param verifyCode
     * @param deviceToken
     * @return
     */
    @RequestMapping(value = "/regist2", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "注册第二步")
    public AppResult regist2(HttpServletRequest request, HttpSession session,
                             @ApiParam("app版本号") @RequestParam String appVersion,
                             @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = false) String client,
                             @ApiParam("手机验证码") @RequestParam String verifyCode,
                             @ApiParam("设备token") @RequestParam(required = false) String deviceToken) {
        try {
            String phone = (String) session.getAttribute("verifyPhone");
//            Integer uploadId = (Integer) session.getAttribute("uploadId");
//            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");
            String dataSource = (String) session.getAttribute("dataSource");
            if(null == phone ||  null == password) {
                return new AppResult(FAILED, "请先获取验证码");
            }

            String sessionInviteCode = (String) session.getAttribute("inviteCode");
            if (System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime() >= 15 * 60 * 1000) {
            	session.removeAttribute("verifyCode");//有效期为15分钟
            }

            String sessionVerifyCode = (String) session.getAttribute("verifyCode");
            if (StringUtils.isBlank(phone) || StringUtils.isBlank(password) || StringUtils.isBlank(sessionVerifyCode) || StringUtils.isBlank(session.getAttribute("lastSendTime")+"")) {
                return new AppResult(FAILED, "您的验证码已过期，请重新发送验证码");
            }


            if (!sessionVerifyCode.equals(verifyCode)) {
                return new AppResult(FAILED, "验证码错误");
            }
            //interfaces.register
            UserRegisterRequest reqUser = new UserRegisterRequest();
    		reqUser.setClient(client);
    		if (StringUtils.isNotBlank(sessionInviteCode) && userService.checkInviteCode(sessionInviteCode)) {
                reqUser.setInviteByCode(sessionInviteCode);
            }
    		reqUser.setPassword(password);
    		reqUser.setPhone(phone);
    		reqUser.setRegisterIp(getIpAddr(request));
//    		reqUser.setUploadId(uploadId);
//    		reqUser.setUsername("");
    		reqUser.setDataSource(dataSource);
            ServiceMessage serviceMsg = new ServiceMessage("interfaces.register", reqUser);
            UserResponse result = (UserResponse) UserCenterClient.getInstance()
                    .setServiceMessage(serviceMsg).send();

            String ret = result.getErrorCode();
            String msg = null;
            if (result.isSuccess()) {
                session.setAttribute("user", result.getUser());
                Map<String, Object> loginResult = new HashMap<String, Object>();

                loginResult.put("user",  result.getUser());
                loginResult.put("successTips", "注册成功");
                //注册成功之后需要做的事情
                // 生成摇一摇次数
//                YaoCount yc = new YaoCount();
//                yc.setUserId(user.getId());
//                yc.setCount(0);
//                yc.setTodayCount(1);
//                yc.setTime(new Date());
//                yaoCountMapper.insert(yc);
                return new AppResult(SUCCESS, loginResult);
    //            Map<String, Object> map1 = userService.login(phone, password, null, deviceToken, appVersion);
                /*Map<String, Object> map1 = userService.login(phone, password, getIpAddr(request), deviceToken, appVersion, client);
                Map<String, Object> loginResult = new HashMap<String, Object>();
                if (map1 == null || map1.size() == 0) {
                    return new AppResult(FAILED, "登录失败");
                }
                User user1 = (User) map1.get("user");
                LoginRecord record = new LoginRecord();
                record.setIp(getIpAddr(request));
                if (client == null) {
                    return new AppResult(FAILED, "请输入终端来源");
                }

                record.setClient(client);
                record.setCreateDate(new Date());
                record.setUserId(user1.getId());
                record.setVersion(appVersion);
                loginRecordService.saveLoginRecord(record);
                loginResult.put("token", user1.getToken());
                loginResult.put("successUrl", getWapUrl() + Constants.REGISTER_SUCCESS);
                loginResult.put("successTips", "注册成功");*/

            } else if (ret == "1") {
                msg = "请输入正确的用户名";
            } else if (ret == "2") {
                msg = "该用户名已被注册，请更换用户名";
            } else if (ret == "3") {
                msg = "请输入正确的密码";
            } else if (ret == "4") {
                msg = "请输入正确的手机号";
            } else if (ret == "5") {
                msg = "该手机号已被注册，请更换手机号";
            } else {
                msg = "未知错误";
            }
            return new AppResult(FAILED, msg);
        }catch (Exception e) {
            e.printStackTrace();
            return new AppResult(FAILED, "注册异常");
        }
    }

    @RequestMapping(value = "/channelRegister", method = RequestMethod.POST)
    @ApiOperation(value = "渠道注册接口")
    @ResponseBody
    public AppResult channelRegister(
        HttpServletRequest request,
        HttpSession session,
        @ApiParam("手机号") @RequestParam String phone,
        @ApiParam("手机验证码") @RequestParam(required = false) String vCode,
        @ApiParam("请求步骤") @RequestParam String reqStep,
        @ApiParam("邀请码") @RequestParam(required = false) String inviteCode,
        @ApiParam("渠道号") @RequestParam String channelNo) {
        String appVersion = "1.2.0";
        String client = ClientEnum.WAP.getFeatureName();
        long start = System.currentTimeMillis();
        try {
            if(StringUtils.isEmpty(channelNo)) {
                return new AppResult(FAILED, "渠道异常");
            }
            if(channelNo.equals("WAP")) {
            	channelNo=null;
            }
            logger.info("用户注册==>phone=" + phone + ";verifyCode=" + vCode + ";reqStep= "+reqStep+"");
            if (!userService.checkPhone(phone)) {
                return new AppResult(FAILED, "手机号长度为11位");
            }
            if ("2".equals(reqStep) && StringUtils.isBlank(vCode)) {
                return new AppResult(FAILED, "请输入验证码");
            }
            int ret1 = userService.check(phone, "phone");
            logger.info("用户注册==>ret1=" + ret1);
            if (!StringUtils.equals("2", reqStep)) {
                if (ret1 == 0) {
                    String verifyCode = null;
                    if (null != session.getAttribute("lastSendTime")) {
                        long diffTime = System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime();
                        if (diffTime <= Constants.ALLOW_SECOUND * 1000) {
                            int secound = Constants.ALLOW_SECOUND-Integer.valueOf((diffTime/1000)+"");
                            return new AppResult(FAILED, "请一分钟后再发送验证码", secound);
                        }
                    }
    
                    verifyCode = generateVerifyCode(6);
                    if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
                        verifyCode = "000000";
                    }
                    logger.info("短信验证码："+verifyCode);
                    SendMessageRequest smr = new SendMessageRequest();
                    smr.setContent(DictConstants.VALIDATE_CODE.replaceAll("\\{code}", verifyCode));
                    smr.setPhone(phone);
                    ServiceMessage msg = new ServiceMessage("message.send", smr);
                    SendMessageResponse result = (SendMessageResponse) OpenApiClient.getInstance()
                            .setServiceMessage(msg).send();
                    if (result.isSuccess()) {
                    	 
                        //String username = "ap" + System.currentTimeMillis();
                        String username = "";
                        session.setAttribute("username", username);
                        session.setAttribute("verifyPhone", phone);
                        session.setAttribute("verifyCode", verifyCode);
                        session.setAttribute("lastSendTime", new Date());
                        session.setAttribute("inviteCode", inviteCode);
                        session.setAttribute("channelNo", channelNo);
                        CodeLimit limit = new CodeLimit();
                        limit.setCode(verifyCode);
                        limit.setCreateTime(new Date());
                        limit.setPhone(phone);
                        codeLimitService.save(limit);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("step", 1);
                        return new AppResult(SUCCESS, map);
                    } else {
                        return new AppResult(FAILED, "验证码发送失败，请稍后重试。");
                    }
                } else if (ret1 == 1) {
                    return new AppResult(FAILED, "请输入正确的手机号");
                } else if (ret1 == 2) {
                    return new AppResult(FAILED, "该手机号已被注册，请更换手机号");
                } else {
                    return new AppResult(FAILED, "系统繁忙");
                }
            }
            
            if(session.getAttribute("verifyPhone") == null || session.getAttribute("verifyCode") == null || session.getAttribute("lastSendTime") == null) {
            	return new AppResult(FAILED, "请先获取验证码");
            }
            
            if (System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime() >= 15 * 60 * 1000) {
                session.removeAttribute("verifyCode");//有效期为15分钟
            }
            //第二步：验证验证码信息
            String sessionPhoneNo = (String) session.getAttribute("verifyPhone");
            String sessionVerifyCode = (String) session.getAttribute("verifyCode");
            String sessionChannelNo = (String) session.getAttribute("channelNo");
            String sessionUsername = (String) session.getAttribute("username");
            if (StringUtils.isBlank(sessionPhoneNo) || StringUtils.isBlank(sessionVerifyCode) ) {
                return new AppResult(FAILED, "验证超时，请重新获取验证码");
            }
            if (!sessionPhoneNo.equals(phone)) {
                return new AppResult(FAILED, "手机号变更，请重新获取验证码");
            }
            if (!sessionVerifyCode.equals(vCode)) {
                return new AppResult(FAILED, "验证码错误");
            }
            if (sessionChannelNo == null) {
            	if(channelNo != null) {
            		return new AppResult(FAILED, "渠道错误");
            	}
            }else if (!sessionChannelNo.equals(channelNo)) {
            	return new AppResult(FAILED, "渠道错误");
            }
            //自动生成密码
            String password = "Bf"+ phone.substring(phone.length() - 6, phone.length());
            //interfaces.register
            UserRegisterRequest reqUser = new UserRegisterRequest();
            reqUser.setClient(client);
            reqUser.setPassword(password);
            reqUser.setPhone(phone);
            reqUser.setRegisterIp(getIpAddr(request));
            reqUser.setChannelNo(channelNo);
            reqUser.setUsername(sessionUsername);
            String sessionInviteCode = (String) session.getAttribute("inviteCode");
            System.out.println("邀请码: "+inviteCode+"-------------------------");
            if(StringUtils.isBlank(sessionInviteCode)){
                sessionInviteCode = inviteCode;
            }

            if(StringUtils.isBlank(sessionInviteCode) && sessionInviteCode.length()>31){
                sessionInviteCode = sessionVerifyCode.substring(0,32);
            }


            if (StringUtils.isNotBlank(sessionInviteCode) && userService.checkInviteCode(sessionInviteCode)) {
            	reqUser.setInviteByCode(sessionInviteCode);
            }
            
            ServiceMessage serviceMsg = new ServiceMessage("interfaces.register", reqUser);
            UserResponse result = (UserResponse) UserCenterClient.getInstance()
                    .setServiceMessage(serviceMsg).send();

            String ret = result.getErrorCode();
            String msg = null;
            
            // 发送默认密码短信
            SendMessageRequest smr = new SendMessageRequest();
            String content = DictConstants.DEFAULT_PASSWORD;
            content = content.replaceAll("\\{phone}", CommonUtils.getPhone(phone));
            content = content.replaceAll("\\{password}", password);
            smr.setContent(content);
            smr.setPhone(phone);
            ServiceMessage passwordMsg = new ServiceMessage("message.send", smr);
            SendMessageResponse passwordSmsResult = (SendMessageResponse) OpenApiClient.getInstance()
                    .setServiceMessage(passwordMsg).send();
            
            if (result.isSuccess() && passwordSmsResult.isSuccess()) {
                session.setAttribute("user", result.getUser());
                Map<String, Object> loginResult = new HashMap<String, Object>();
                Map<String, Object> map = userService.login(phone, password, getIpAddr(request), null, appVersion, client);
                if (map == null || map.size() == 0) {
                    return new AppResult(FAILED, "登录失败");
                }
                User user = (User) map.get("user");
                LoginRecord record = new LoginRecord();
                record.setIp(getIpAddr(request));
                if (client == null) {
                    return new AppResult(FAILED, "请输入终端来源");
                }
                record.setClient(client);
                record.setCreateDate(new Date());
                record.setUserId(user.getId());
                record.setVersion(appVersion);
                loginRecordService.saveLoginRecord(record);
                loginResult.put("token", user.getToken());
                loginResult.put("successUrl", getWapUrl() + Constants.REGISTER_SUCCESS);
                loginResult.put("successTips", "注册成功");
                session.removeAttribute("verifyPhone");
                session.removeAttribute("verifyCode");
                session.removeAttribute("lastSendTime");
                return new AppResult(SUCCESS, loginResult);
            } else if (ret == "1") {
                msg = "请输入正确的用户名";
            } else if (ret == "2") {
                msg = "该用户名已被注册，请更换用户名";
            } else if (ret == "3") {
                msg = "请输入正确的密码";
            } else if (ret == "4") {
                msg = "请输入正确的手机号";
            } else if (ret == "5") {
                msg = "该手机号已被注册，请更换手机号";
            } else {
                msg = "未知错误";
            }
            long end = System.currentTimeMillis();
            logger.info((end-start)+"ms");
            return new AppResult(FAILED, msg);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
   /**
    * 登录接口
    * @date 2019年5月13日
    * @author wangyun
    * @time 下午4:24:28
    *
    * @param request
    * @param session
    * @param appVersion
    * @param account
    * @param password
    * @param deviceToken
    * @param equipment
    * @param client
    * @return
    */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户登录")
    public AppResult login(HttpServletRequest request, HttpSession session,
                           @ApiParam("app版本号") @RequestParam String appVersion,
                           @ApiParam("登陆名(手机,用户名)") @RequestParam String account,
                           @ApiParam("登陆密码") @RequestParam String password,
                           @ApiParam("设备token") @RequestParam(required = false) String deviceToken,
                           @ApiParam("设备名称及型号") @RequestParam(required = false) String equipment,
                           @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                           @ApiParam("图形验证码") @RequestParam(required = false) String capImgCode) {
    	Map<String, Object> loginResult = new HashMap<String, Object>();
    	loginResult.put("errorCount", 0);
    	loginResult.put("token", "");
    	try {
            if (StringUtils.isBlank(account)) {
                return new AppResult(FAILED, "您还没有输入账号", loginResult);
            }
            if (StringUtils.isBlank(password)) {
                return new AppResult(FAILED, "您还没有输入密码", loginResult);
            }

            User user = userService.getLoginUser(account);
            if(user == null) {
            	 return new AppResult(FAILED, "当前账号不存在", loginResult);
            }
            //密码错误次数超过3次
            if(user.getErrorCount() >= 3) {
            	if(StringUtils.isEmpty(capImgCode)){
            		loginResult.put("errorCount", user.getErrorCount());
            		return new AppResult(FAILED, "请输入正确的图形验证码", loginResult);
            	}
            	if (Constants.YES.equals(getCacheKeyValue(DictConstants.SHOW_CAPTIMG_PROMPT))) {
                     Map<String, Object> m = checkCapImgCode(Constants.CAP_IMG_CODE + account, capImgCode, request);
                     if (!m.get(STATUS).equals(Status.OK)) {
                    	 loginResult.put("errorCount", user.getErrorCount());
                         return new AppResult(FAILED, m.get("msg").toString());
                     }
                 }
            }

            UserLoginRequest loginRequest = new UserLoginRequest();
            UserResponse response = new UserResponse();

            loginRequest.setAccount(account);
            loginRequest.setAppVersion(appVersion);
            loginRequest.setClient(client);
            loginRequest.setDeviceToken(deviceToken);
            loginRequest.setIp(getIpAddr(request));
            loginRequest.setLoginType(1);//登陆方式(1:密码登录 2:手机号登录)
            loginRequest.setPassword(password);

            ServiceMessage serviceMsg = new ServiceMessage("interfaces.login", loginRequest);
            response = (UserResponse) UserCenterClient.getInstance()
                    .setServiceMessage(serviceMsg).send();

            if(!response.isSuccess()) {
            	loginResult.put("errorCount", user.getErrorCount());
            	return new AppResult(FAILED, response.getErrorMsg(), loginResult);
            }

            User userRes = response.getUser();
            loginResult.put("errorCount", userRes.getErrorCount());

            if (null == userRes || userRes.getId() == null) {
                return new AppResult(FAILED, "登录失败", loginResult);
            }
            //冻结账户限制
            if (null == userRes || userRes.getStatus() == 1) {
                return new AppResult(FAILED, "对不起，您的账户已被冻结，请联系客服。", loginResult);
            }
            loginResult.put("token", userRes.getToken());
            session.setAttribute("user", userRes);
            return new AppResult(SUCCESS, "登录成功", loginResult);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION, loginResult);
        }
    }

    @RequestMapping(value = "/mobileLogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "手机号登录")
    public AppResult mobileLogin(HttpServletRequest request, HttpSession session,
                           @ApiParam("app版本号") @RequestParam(required = false) String appVersion,
                           @ApiParam("手机号") @RequestParam String mobile,
                           @ApiParam("终端来源 IOS,Android,PC,WAP") String client,
                           @ApiParam("图形验证码") @RequestParam String capImgCode,
                           @ApiParam("设备名称及型号") @RequestParam(required = false) String equipment,
                           @ApiParam("手机验证码") @RequestParam  String verifyCode) {
    	Map<String, Object> loginResult = new HashMap<String, Object>();
    	loginResult.put("errorCount", 0);
    	loginResult.put("token", "");
    	try {
    		if (Constants.YES.equals(getCacheKeyValue(DictConstants.SHOW_CAPTIMG_PROMPT))) {
                Map<String, Object> m = checkCapImgCode(Constants.CAP_IMG_CODE + mobile, capImgCode, request);
                if (!m.get(STATUS).equals(Status.OK)) {
                    return new AppResult(FAILED, m.get("msg").toString());
                }
            }
    	    String phone = (String) session.getAttribute("verifyPhone");
    	    String sessionVerifyCode = (String) session.getAttribute("verifyCode");

            if (StringUtils.isBlank(phone) ||  StringUtils.isBlank(sessionVerifyCode) ||  StringUtils.isBlank(session.getAttribute("lastSendTime")+"")) {
                return new AppResult(FAILED, "您的验证码已过期，请重新发送验证码");
            }

            if (System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime() >= 15 * 60 * 1000) {
            	session.removeAttribute("verifyCode");//有效期为15分钟
            }
            //验证码过期
            if(StringUtils.isBlank(sessionVerifyCode)) {
            	 return new AppResult(FAILED, "您的验证码已过期，请重新发送验证码");
            }

            if (!sessionVerifyCode.equals(verifyCode)) {
                return new AppResult(FAILED, "验证码错误");
            }
//            Map<String, Object> map = userService.loginMobile(mobile, getIpAddr(request), appVersion);
            UserLoginRequest loginRequest = new UserLoginRequest();
            UserResponse response = new UserResponse();

            loginRequest.setAccount(mobile);
            loginRequest.setAppVersion(appVersion);
            loginRequest.setClient(client);
            loginRequest.setIp(getIpAddr(request));
            loginRequest.setLoginType(2); //登陆方式(1:密码登录 2:手机号登录)

            ServiceMessage serviceMsg = new ServiceMessage("interfaces.login", loginRequest);
            response = (UserResponse) UserCenterClient.getInstance()
                    .setServiceMessage(serviceMsg).send();


            if(!response.isSuccess()) {
            	return new AppResult(FAILED, response.getErrorMsg(), loginResult);
            }
            User user = response.getUser();

            if (null == user || user.getId() == null) {
                return new AppResult(FAILED, "登录失败", loginResult);
            }
            //冻结账户限制
            if (null == user || user.getStatus() == 1) {
                return new AppResult(FAILED, "对不起，您的账户已被冻结，请联系客服。", loginResult);
            }

            loginResult.put("token", user.getToken());
            
    	    return new AppResult(SUCCESS, "登录成功", loginResult);
		} catch (Exception e) {
			logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION, loginResult);
		}
    }

   /**
    * @date 2019年7月4日
    * @author wangyun
    * @time 下午4:41:13
    * @Description 重置密码第一步
    * 
    * @param session
    * @param phone
    * @param appVersion
    * @param capImgCode
    * @return
    * @throws Exception
    */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public AppResult resetPassword(HttpSession session,HttpServletRequest request, 
        @ApiParam("手机号码(忘记密码时必传)") @RequestParam(required = false) String phone,
        @ApiParam("用戶token(个人中心重置密码必传)") @RequestParam(required = false) String token,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") String client,
        @ApiParam("图形验证码") @RequestParam String capImgCode)  throws Exception {
        
    	if(StringUtils.isEmpty(phone)  && StringUtils.isEmpty(token)) {
    		return new AppResult(FAILED, "请输入用户信息");
    	}
    	//token不为空,用户中心重置密码,否则是忘记密码的重置，需输入手机号
    	if(StringUtils.isNotEmpty(token)) {
    		User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            phone = user.getPhone();
    	}
    	if (!userService.checkPhone(phone)) {
			return new AppResult(FAILED, "请输入正确的手机号");
		}
		if (!RegularUtils.checkString(phone, RegularUtils.REGEX_MOBILE)) {
			return new AppResult(FAILED, "电话号段异常！");
		}
		
    	if(StringUtils.isEmpty(capImgCode)){
    		return new AppResult(FAILED, "请输入正确的图形验证码");
    	}
    	if (Constants.YES.equals(getCacheKeyValue(DictConstants.SHOW_CAPTIMG_PROMPT))) {
             Map<String, Object> m = checkCapImgCode(Constants.CAP_IMG_CODE + phone, capImgCode, request);
             if (!m.get(STATUS).equals(Status.OK)) {
                 return new AppResult(FAILED, m.get("msg").toString());
             }
        }
    
		String verifyCode = null;
		if (null != session.getAttribute("lastSendTime")) {
			long diffTime = System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime();
			if (diffTime <= Constants.ALLOW_SECOUND * 1000) {
				int secound = Constants.ALLOW_SECOUND - Integer.valueOf((diffTime / 1000) + "");
				return new AppResult(FAILED, "请一分钟后再发送验证码", secound);
			}
		}

		verifyCode = generateVerifyCode(6);
		if (TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)) {
			verifyCode = "000000";
		}
		logger.info("短信验证码：" + verifyCode);
		SendMessageRequest smr = new SendMessageRequest();
		smr.setContent(DictConstants.VALIDATE_CODE.replaceAll("\\{code}", verifyCode));
		smr.setPhone(phone);
		ServiceMessage msg = new ServiceMessage("message.send", smr);
		SendMessageResponse result = (SendMessageResponse) OpenApiClient.getInstance().setServiceMessage(msg).send();
		if (result.isSuccess()) {
			session.setAttribute("verifyPhone", phone);
			session.setAttribute("verifyCode", verifyCode);
			session.setAttribute("lastSendTime", new Date());
			CodeLimit limit = new CodeLimit();
			limit.setCode(verifyCode);
			limit.setCreateTime(new Date());
			limit.setPhone(phone);
			codeLimitService.save(limit);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("step", 1);
			return new AppResult(SUCCESS, map);
		} else {
			return new AppResult(FAILED, "验证码发送失败，请稍后重试。");
		}
    }
    /**
     * 重置密码第二步
     * @param session
     * @param verifyCode
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/getBackPassword2", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "找回密码第二步")
    public AppResult checkVerifyCode(HttpSession session, HttpServletRequest request,
                                     @ApiParam("手机验证码") @RequestParam String verifyCode,
                                     @ApiParam("图形验证码") @RequestParam String capImgCode,
                                     @ApiParam("app版本号") @RequestParam String appVersion,
                                     @ApiParam("终端来源 IOS,Android,PC,WAP") String client) {

    	Integer count = session.getAttribute("verifyCodeErrorCount") == null ? 0 : (Integer)session.getAttribute("verifyCodeErrorCount");
    	try {
            String phone = (String) session.getAttribute("verifyPhone");
            if (Constants.YES.equals(getCacheKeyValue(DictConstants.SHOW_CAPTIMG_PROMPT))) {
                Map<String, Object> m = checkCapImgCode(Constants.CAP_IMG_CODE + phone, capImgCode, request);
                if (!m.get(STATUS).equals(Status.OK)) {
                    return new AppResult(FAILED, m.get("msg").toString());
                }
            }
            logger.info("==========进入修改密码方法========phone=" + phone);
            if (StringUtils.isBlank(phone)) {
                return new AppResult(FAILED, "请先获取验证码");
            }
            User user = userService.getByPhone(phone);
            if (user == null) {
                return new AppResult(FAILED, "该用户不存在，请输入正确的手机号");
            }
            String sessionVerifyCode = (String) session.getAttribute("verifyCode");
            if (StringUtils.isBlank(verifyCode)) {
                return new AppResult(FAILED, "请输入验证码");
            }
            if (StringUtils.isBlank(sessionVerifyCode)) {
                return new AppResult(FAILED, "验证码已超时，请重新获取验证码。");
            }
            if (!verifyCode.equals(sessionVerifyCode)) {
            	session.setAttribute("verifyCodeErrorCount", ++count);
            	if(count >= 5){
            		session.removeAttribute("verifyCode");
            		session.removeAttribute("verifyPhone");
            	}
                return new AppResult(FAILED, "请输入正确的验证码");
            }else{
            	session.removeAttribute("verifyCodeErrorCount");
            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    	JSONObject json = new JSONObject();
        return new AppResult(SUCCESS,json);
    }

    /**
     * 找回密码第三步 （修改密码）
     * @param session
     * @param newPassword
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/getBackPassword3", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "找回密码第三步")
    public AppResult updatePassword(HttpSession session,
                                    @ApiParam("新的登陆密码") @RequestParam String newPassword,
                                    @ApiParam("确认密码") @RequestParam String confirmPassoword,
                                    @ApiParam("app版本号") @RequestParam String appVersion,
                                    @ApiParam("终端来源 IOS,Android,PC,WAP") String client) {
        try {
            if (StringUtils.isBlank(newPassword) || newPassword.length() < 6) {
                return new AppResult(FAILED, "密码为英文加数字组合6-12位");
            }
            String phone = (String) session.getAttribute("verifyPhone");
            if(phone == null) {
            	return new AppResult(FAILED, "页面已超时，请刷新重试");
            }
            UserUpdatePasswordRequest request = new UserUpdatePasswordRequest();
            Response response = new Response();
            request.setPhone(phone);
            request.setNewPassword(newPassword);
            request.setConfirmPassoword(confirmPassoword);

            ServiceMessage serviceMsg = new ServiceMessage("interfaces.updatePassword", request);
            response =  UserCenterClient.getInstance()
                    .setServiceMessage(serviceMsg).send();

            if(!response.isSuccess()) {
            	return new AppResult(FAILED, response.getErrorMsg());
            }
            session.removeAttribute("verifyCode");
            session.removeAttribute("verifyPhone");

            return new AppResult(SUCCESS, "重置密码成功");
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * 登出接口
     * @param session
     * @param token
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "退出登录")
    public AppResult logout(HttpSession session,
                            @ApiParam("用户token") @RequestParam String token,
                            @ApiParam("app版本号") @RequestParam String appVersion) {
        session.removeAttribute("user");
        session.invalidate();
        userService.updateLogoutByToken(token);
        JSONObject json = new JSONObject();
        return new AppResult(SUCCESS, json);
    }

   
   /**
    * @date 2019年7月5日
    * @author wangyun
    * @time 上午11:22:24
    * @Description 修改密码
    * 
    * @param session
    * @param oldPassword
    * @param newPassword
    * @param confirmPassword
    * @param token
    * @param appVersion
    * @return
    */
    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改密码")
    public AppResult updateUserPassword(HttpSession session,
                                    @ApiParam("老的密码") @RequestParam String oldPassword,
                                    @ApiParam("新的密码") @RequestParam String newPassword,
                                    @ApiParam("确认密码") @RequestParam String confirmPassword,
                                    @ApiParam("用户token") @RequestParam String token, 
                                    @ApiParam("app版本号") @RequestParam String appVersion,
                                    @ApiParam("终端来源 IOS,Android,PC,WAP") String client) {
    	try {
    		User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            if (!user.getPassword().equals(MD5.MD5Encode((oldPassword+Constants.PASSWORD_FEX)))) {
                return new AppResult(FAILED, "原密码错误");
            }
            if(RegularUtils.checkString(newPassword, RegularUtils.REGEX_BLANK)) {
              	 return new AppResult(FAILED, "密码不能含空格");
            }
            if(!userService.checkPassword(newPassword)) {
            	return new AppResult(FAILED, "密码为英文数字组合6-12位");
            }
            if(!newPassword.equals(confirmPassword)) {
            	return new AppResult(FAILED, "新密码和确认密码不一致");
            }
            if (user.getPassword().equals(MD5.MD5Encode(newPassword+Constants.PASSWORD_FEX))) {
                return new AppResult(FAILED, "新密码不能与原密码一致");
            }
            
            int code = userService.setpassword(user,oldPassword, newPassword);
    		if(code == 0) {
    			return new AppResult(SUCCESS, "修改成功");
    		} else if(code == 1){
    			return new AppResult(FAILED, "密码为英文数字组合6-12位");
    		} else if(code == 2) {
    			return new AppResult(FAILED, "旧密码错误");
    		} else if(code == 3){
    			return new AppResult(FAILED, "新密码不能与原密码一致");
    		} else  {
    			return new AppResult(FAILED, "未知错误");
    		}
    	 
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
			
		}
    }
    
    /**
     * 设置支付密码/重置支付密码(如果输入oldpaypassword就是重置支付密码,没有输入就是设置支付密码
     * @param session
     * @param oldpaypassword
     * @param paypassword
     * @param token
     * @param verifyCode
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/setpaypassword", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置/重置支付密码")
    public AppResult setpaypassword(HttpSession session,
                                    @ApiParam("老的支付密码") @RequestParam(required = false) String oldpaypassword,
                                    @ApiParam("新的支付密码") @RequestParam String paypassword,
                                    @ApiParam("用户token") @RequestParam String token,
                                    @ApiParam("手机验证码") @RequestParam String verifyCode,
                                    @ApiParam("app版本号") @RequestParam String appVersion) {
        int type = 1;
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            String verifyPhone = (String) session.getAttribute("verifyPhone");
            String vc = (String) session.getAttribute("verifyCode");
            if (StringUtils.isBlank(vc)) {
                return new AppResult(FAILED, "验证码已超时，请重新获取验证码。");
            }
            if (!verifyPhone.equals(user.getPhone())) {
                return new AppResult(FAILED, "非法请求!");
            }
            if (!verifyCode.equals(vc)) {
                return new AppResult(FAILED, "请输入正确的验证码");
            }
            if (StringUtils.isBlank(paypassword)) {
                return new AppResult(FAILED, "请输入6位数字组成的交易密码");
            }
            if (StringUtils.isNotBlank(oldpaypassword)) {

                int ret = userService.checkpaypassword(user.getId(), oldpaypassword);
                type = 0;
                if (ret == 1) {
                    return new AppResult(FAILED, "不合法的旧密码");
                }
                if (StringUtils.isNotBlank(user.getPayPassword()) && !user.getPayPassword().equals(MD5.MD5Encode((oldpaypassword+Constants.PASSWORD_FEX)))) {
                    return new AppResult(FAILED, "原始密码错误");
                }
            }
            int ret = userService.checknewpaypassword(user.getId(), paypassword);
            if (ret == 1) {
                return new AppResult(FAILED, "新密码为6位数字");
            } else if (ret == 2) {
                return new AppResult(FAILED, "新密码不能与登录密码相同");
            }

            if (StringUtils.isNotBlank(user.getPayPassword()) && user.getPayPassword().equals(MD5.MD5Encode(paypassword+Constants.PASSWORD_FEX))) {
                return new AppResult(FAILED, "新密码不能与原交易密码一致");
            }

            ret = userService.setpaypassword(user.getId(), paypassword, type);
            if (ret == 0) {
            	JSONObject json = new JSONObject();
                return new AppResult(SUCCESS,json);
            } else if (ret == 1) {
                return new AppResult(FAILED, "新密码为6位数字");
            } else if (ret == 2) {
                return new AppResult(FAILED, "新密码不能与登录密码相同");
            } else if (ret == 3) {
                return new AppResult(FAILED, "新密码不能与原交易密码一致");
            } else {
                return new AppResult(FAILED, "未知错误");
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 个人账户主页
     * @param request
     * @param appVersion
     * @param client
     * @param token
     * @param dataSource
     * 阿里分发市场 2
                应用宝 3
                华为应用市场 4
        vivo应用商店 5
        oppo 6
                魅族 7
                联通wo 8
                联想 9
                百度 10
                小米 11
        360手机助手 12
     * @param appName
     * @param deviceInfo
     * 苹果：iOS版本号，手机型号
               安卓：安卓版本号，第三方UI版本号，手机型号，厂商
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "个人账户主页")
    public AppResult home(HttpServletRequest request,
                          @ApiParam("app版本号") @RequestParam String appVersion,
                          @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                          @ApiParam("用户token") @RequestParam String token,
                          @ApiParam("安卓渠道") @RequestParam(required = false) String dataSource,
                          @ApiParam("app名字") @RequestParam(required = false) String appName,
                          @ApiParam("包含设备信息") @RequestParam(required = false) String deviceInfo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.checkLogin(token);
        String update = checkVersion(client, appVersion);//检查是否需要更新
        map.put("headIcon", "");
        map.put("tips", "奔富牧业提前发放的利润收益在饲养周期未结束前（冻结期）仅可用于购买商城产品。");
        map.put("message", "0");
        List<Investment> invests = new ArrayList<Investment>();
        int sellsInvestCount = 0; //已出售牛只数量
        int pinInvestCount = 0; //拼牛数量
        int hongbaoCount = 0;
        try {
	        if (user == null) {
	            map.put("headIcon", ClientConstants.ALIBABA_PATH + "upload/nologin.png");
	            map.put("lableTotal", "总资产(元）");
                map.put("lableTotalAmount", "0.00");
                map.put("lableTotalAmountStrValue", "0.00");
	            map.put("phone", "未登录");
	            map.put("lableAccount", "点击登录/注册");
	            map.put("lockAmount", "0.00");
                map.put("lockAmountStrValue", "0.00");
	            map.put("lableLock", "冻结利润(元）");
	            map.put("interestAmount", "0.00");
                map.put("interestAmountStrValue", "0.00");
	            map.put("lableInterest", "累计利润(元）");
	            map.put("banceAmount", "0.00");
                map.put("banceAmountStrValue", "0.00");
	            map.put("lableBance", "余额(元）");
	            map.put("lableWithdraw", "提现");
	        }else {
	            if(null != user.getAvatarId()) {
	                Upload upload = uploadService.get(user.getAvatarId());
	                map.put("headIcon", ClientConstants.ALIBABA_PATH + "upload/" + upload.getPath());
	            }else {
	                map.put("headIcon", ClientConstants.ALIBABA_PATH + "upload/login.png");
	            }
	            map.put("lableTotal", "总资产(元）");
	            Assets assets = assetsService.findByuserId(user.getId());
	            double totalAmount = investmentService.getAmountCount(user.getId());
	            totalAmount  = BigDecimalUtil.add(totalAmount, assets.getBalanceAmount(), assets.getFrozenAmount(), assets.getCreditAmount(), assets.getFreozenCreditAmount());
                map.put("lableTotalAmount", totalAmount);
                map.put("lableTotalAmountStrValue", String.format("%.2f",totalAmount));
	        	map.put("phone", CommonUtils.getPhone(user.getPhone()));
	        	String username = user.getUsername();
	        	if(StringUtils.isNotEmpty(username)) {
	        	    map.put("userName", username);
	        	} else {
	        	    map.put("userName", "设置昵称");
	        	}
	            map.put("lableAccount", "账户中心>");
	            map.put("lockAmount", assets.getCreditAmount());
                map.put("lockAmountStrValue", String.format("%.2f",assets.getCreditAmount()));
	            map.put("lableLock", "冻结利润(元）");
	            map.put("lableLockUrl", ClientConstants.H5_URL+"lockAmount.html");
	            //牛只订单
	            InvestmentExample example = new InvestmentExample();
	            example.createCriteria().andUserIdEqualTo(user.getId())
	            .andOrderStatusNotEqualTo(3);//订单取消,的不统计

	            invests = investmentService.getMapper().selectByExample(example);
	            double interestAmount = 0.00;
	            double pinInterestAmount = 0.00;
	            for(Investment i : invests) {
	                //养牛已卖牛及利润
	                if(i.getOrderStatus() == 2 && i.getType() == 0) {
	                    interestAmount = BigDecimalUtil.add(interestAmount, i.getInterestAmount());
	                    sellsInvestCount++;
	                }
	                // 拼牛已卖牛及利润
	                if(i.getOrderStatus() == 2 && i.getType() == 1) {
	                	pinInterestAmount = BigDecimalUtil.add(pinInterestAmount, i.getInterestAmount());
	                }
	                if(i.getType() == 1) {// 过滤所有拼牛订单
	                	pinInvestCount++;
	                }
	            }
	            map.put("interestAmount", interestAmount+pinInterestAmount);// 拼牛利润需展示
                map.put("interestAmountStrValue", String.format("%.2f", interestAmount+pinInterestAmount));
	            map.put("lableInterest", "累计利润(元）");
	            map.put("banceAmount", assets.getBalanceAmount());
                map.put("banceAmountStrValue", String.format("%.2f", assets.getBalanceAmount()));
	            map.put("lableBance", "余额(元）");
	            map.put("lableWithdraw", "提现");
	            int messageCount = messageReceiverService.listCount(user.getId(), 0);
	            if(messageCount>0) {
	                map.put("message", "1");
	            }
	            // 有效优惠券个数
	            hongbaoCount = hongbaoService.getHongbaoInverstmentCount(user.getId(), null, 0, null, null);

	        }
	        List<Icon> icons = iconService.getUsingIcons(1, appVersion);


            //是否显示牛
            boolean bullsShow = projectService.isShowBulls(user,appName,dataSource,client);

            map.put("bulls_show", bullsShow);

            //是否充值开关
            Boolean rechargeShow = false;
            if(client.equals(ClientEnum.IOS.getFeatureName())) {
                if(Constants.YES.equals(getCacheKeyValue(DictConstants.RECHARGE_SHOW_IOS))) {
                	rechargeShow = true;
                }
            }else if(client.equals(ClientEnum.ANDROID.getFeatureName())) {
                if(Constants.YES.equals(getCacheKeyValue(DictConstants.RECHARGE_SHOW_ANDROID))) {
                	rechargeShow = true;
                }
            }else if(client.equals(ClientEnum.WAP.getFeatureName())) {
                if(Constants.YES.equals(getCacheKeyValue(DictConstants.RECHARGE_SHOW_WAP))) {
                	rechargeShow = true;
                }
            }
            map.put("rechargeShow", rechargeShow);
            map.put("rechargeUrl", "https://www.bfmuchang.com/recharge/#/?shareId=2&toLink=1");
            //商城订单
//          int goodsCount = goodsService.countGoods(null);
            Iterator<Icon> itr = icons.iterator();
            while(itr.hasNext()) {
	          	Icon icon = itr.next();
	          	icon.setPath(ClientConstants.ALIBABA_PATH + "upload/" + icon.getPath());
	              if (icon.getKey().equals("myCattle")) {
	            	  // 所有订单 - 养牛已出售 -所有拼牛订单 （拼牛订单数据不显示在养牛里）
	            	  int count = invests.size() - sellsInvestCount - pinInvestCount;
	            	  if(count > 0) {
	            		  icon.setSubtitle(count + "");
	                   }
	              } else if (icon.getSeq() == 2) {
	                  //icon.setSubtitle(goodsCount+"");
	              } else if (icon.getKey().equals("myBouns")) {
	                  if (user != null && hongbaoCount > 0) {
	                      icon.setSubtitle(hongbaoCount + "");
	                  }
	              }
	              //未登录和非vip不显示
	              if((user == null || (user != null && user.getLevel() != 2)) && icon.getKey().equals("vipInterest")) {//vipInterest
	            	  itr.remove();
	              }
	              //不显示牛只,移除我的牧场
	              if(!bullsShow && icon.getKey().equals("myCattle")) {//myCattle
	            	  itr.remove();
	              }
                //不显示牛只,移除我的拼牛
                if(!bullsShow && icon.getKey().equals("myPinCattle")) {//myCattle
                    itr.remove();
                }
	              // 未登录和非迁移用户不显示亿亿理财
	              if((user == null || (user != null && !user.getIsMigration()))&& icon.getKey().equals("migration")) {
	            	  itr.remove();
	              }
            }
            Integer userId = null;
            if(user != null) {
            	userId = user.getId();
            }
            //不显示牛,显示商品
            if(!bullsShow) {
                shopInit(map, appVersion,userId);
            }else {
            	map.put("shopIcons", null);
            	//2.0.0 也显示商城
                if(getVersion(appVersion)>=200) {
                    shopInit(map, appVersion,userId);
                }
            }
            map.put("icons", icons);
	        if (UPDATE.equals(update)) {//此处不需要。
                AppVersionContentWithBLOBs blobs = appVersionService.getAppVersionContentWithBLOBs(client);
                if (blobs != null) {
                    map.put("noticeTitle", blobs.getTitle());
                    map.put("noticeText", blobs.getTextContent());
                    map.put("noticeHtml", blobs.getHtmlContent());
                    map.put("url", blobs.getUrl());
                } else {
                    update = NOUPDATE;
                }
            } else {
                map.put("noticeTitle", "不需要更新");
                map.put("noticeText", "不需要更新");
                map.put("noticeHtml", "不需要更新");
            }
            map.put("update", update);
            map.put("otherTitle", "其他服务");
	        return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    private void shopInit(Map<String, Object> map, String appVersion,Integer userId) {
        List<IconIndexVO> shopIconIndexVO = new ArrayList<IconIndexVO>();
        List<Icon> shopIcons = iconService.getUsingIcons(6, appVersion);
        GoodsOrderListRequest orderRequest = new GoodsOrderListRequest();
        orderRequest.setUserId(userId);

        for (Icon icon : shopIcons) {
            IconIndexVO i = new IconIndexVO();
            i.setId(icon.getId());
            i.setKey(icon.getKey());
            i.setLink(icon.getLink());
            i.setPath(ClientConstants.ALIBABA_PATH + "upload/" + icon.getPath());
            i.setTitle(icon.getTitle());
            if(userId != null) {
            	if("myOrder#status=0".equals(icon.getKey())) { // 待付款
                	orderRequest.setStatus(0);// 待付款
                    int count = goodsOrderService.countOrder(orderRequest);
                    if(count > 0) {
                    	i.setSubtitle(count+"");
                    }
                } else if("myOrder#status=2".equals(icon.getKey())) {// 待发货
                	 orderRequest.setStatus(2);// 待发货 (已付款)
                     int count = goodsOrderService.countOrder(orderRequest);
                     if(count > 0) {
                     	i.setSubtitle(count+"");
                     }
                } else if("myOrder#status=4".equals(icon.getKey())) {// 待收货
                	 orderRequest.setStatus(4);// 待发货 (已付款)
                     int count = goodsOrderService.countOrder(orderRequest);
                     if(count > 0) {
                     	i.setSubtitle(count+"");
                     }
                } else if("myOrder#status=7".equals(icon.getKey())) {// 待评价
                	orderRequest.setStatus(7);// 待发货 (已付款)
                    int count = goodsOrderService.countOrder(orderRequest);
                    // 商品是否显示评价按钮

                    if(count > 0) {
                    	i.setSubtitle(count+"");
                    }
                }
            }

            shopIconIndexVO.add(i);
        }
        map.put("shopIcons", shopIconIndexVO);
        map.put("shopLableTitle", "商城订单");
        map.put("shopLableOperator", "查看全部订单");
        map.put("shopLableOperatorKey", "myOrder");
    }

    /**
     * 签到
     *
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signed", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "签到")
    public AppResult signed(
            @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token) {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        try {
            int ret = userSignedService.saveSignedAgain(user.getId());

            if (ret == 0) {
                return new AppResult(FAILED, "今日已签到");
            } else if (ret == 1) {
                return new AppResult(SUCCESS, "签到成功,获得日加息券一张");
            } else if (ret == 2) {
                return new AppResult(SUCCESS, "签到成功,获得日加息券和月加息券一张");
            } else if (ret == 3) {
                return new AppResult(SUCCESS, "签到成功,奖励次日派发！");
            } else if (ret == 4) {
                return new AppResult(SUCCESS, "请更新，签到惊喜更多！");
            } else if (ret == 5) {
                return new AppResult(SUCCESS, "您已经签到，请不要重复签到！");
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
        return null;
    }

    /**
     * 签到进入获取数据方法
     */
    @RequestMapping(value = "/signed/listNew", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "3.0.0 签到记录列表")
    public AppResult signedListNew(
            @ApiParam("app版本") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String type,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("签到月份") @RequestParam(required = false) String date,
            HttpServletRequest request) {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        String update = checkVersion(type, appVersion);// 检查是否需要更新
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> list = userSignedService.selectSignedAgainYesterday(user.getId());
            Calendar c = Calendar.getInstance();

            Integer todayCount = userSignedService.getDailySignedCount(c.getTime());
            c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
            Integer yesCount = userSignedService.getDailySignedCount(c.getTime());
            map.put("yesterday", yesCount * 4 + 7);//昨日签到人数
            map.put("today", todayCount * 4 + 7);//今日签到人数
            Double amount = list.get("amount") == null ? 0 : BigDecimalUtil.fixed2(list.get("amount"));
            map.put("amount", amount.toString());//昨日签到奖励
            map.put("url", "https://qmlic.com/user/sign-help.html");
            map.put("update", update);
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 签到奖励记录
     * @param appVersion
     * @param client
     * @param token
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/signed/signedAwardList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "签到奖励记录")
    public AppResult signedAwardList(
            @ApiParam("app版本") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("页码 page") @RequestParam Integer page,
            HttpServletRequest request) {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        if (page == null) {
            page = 1;
        }
        String update = checkVersion(client, appVersion);// 检查是否需要更新
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> list = userSignedService.selectSignedAwardDetail(user.getId());
            List<Map<String, Object>> listMap = userSignedService.selectSignedAwardDetailList(user.getId(), (page - 1) * PAGE_SIZE_TEN, PAGE_SIZE_TEN);
            if (listMap == null) {//
                listMap = new ArrayList<Map<String, Object>>();
            }
            map.put("list", listMap);//收益信息
            map.put("page", page);//页码
            Integer count = Integer.valueOf(list.get("sCount") == null ? "0" : list.get("sCount").toString());
            map.put("pages", calcPage(count, PAGE_SIZE_TEN));
            map.put("totalAmount", list.get("sumAmount") == null ? "0" : list.get("sumAmount").toString());//累计金额
            if (getVersion(appVersion) > 501) {
                map.put("count", userSignedService.getAllSignedCount(user.getId()));//累计签到次数
            } else {
                map.put("count", count.toString());//累计签到次数
            }
            Integer counts = count == 0 ? 1 : count;
            int investAmount = (int) ((Double.valueOf(list.get("sumAmount") == null ? "0" : list.get("sumAmount").toString()) * 365 / ConstantsAdmin.RATE_DEFAULT_VALUE) / counts);
            map.put("investAmount", investAmount);//预计收益签到人数
            map.put("update", update);
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 验证是否签到
     * @param appVersion
     * @param token
     * @return
     */
    @RequestMapping(value = "/signed/check", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "验证是否签到")
    public AppResult signedCheck(
            @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token) {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        try {
            boolean ret = userSignedService.checkSigned(user.getId(), new Date());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ret", ret);
            map.put("count", userSignedService.getDailySignedCount(new Date()) * 4 + 7);
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * 修改用户昵称
     * @param username
     * @param appVersion
     * @param token
     * @return
     */
    @RequestMapping(value = "/editUserName", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改用户昵称")
    public AppResult editUserName(
            @ApiParam("用户新昵称") @RequestParam String username,
            @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token) {
        if (appVersion != null) {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            try {
                if (!userService.checkUsername(username)) {
                    return new AppResult(FAILED, "不合法的昵称");
                }
                if (!userService.checkNameExists(username, null)) {
                    return new AppResult(FAILED, "昵称已经存在");
                }
                user.setUsername(username);
                userService.updateUserName(user);
                JSONObject json = new JSONObject();
                return new AppResult(SUCCESS,json);
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
                return new AppResult(FAILED, MESSAGE_EXCEPTION);
            }
        } else {
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 功能：把15位身份证转换成18位
     *
     * @param id
     * @return newid or id
     */
    public final String getIdCard18bit(String id) {
        // 若是15位，则转换成18位；否则直接返回ID
        if (15 == id.length()) {
            final int[] w = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
                    2, 1};
            final String[] a = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
                    "3", "2"};
            int i, j, s = 0;
            String newid;
            newid = id;
            newid = newid.substring(0, 6) + "19"
                    + newid.substring(6, id.length());
            for (i = 0; i < newid.length(); i++) {

                j = Integer.parseInt(newid.substring(i, i + 1)) * w[i];
                s = s + j;
            }
            s = s % 11;
            newid = newid + a[s];
            return newid;
        } else {
            return id;
        }
    }

    /**
     * 用户地址列表
     * @param token
     * @param page
     * @return
     */
    @RequestMapping(value = "/addressList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "用户地址列表")
    public AppResult addressList(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("页码") @RequestParam(required = false) Integer page) {
    	Map<String, Object> map = new HashMap<>();
        try {
            User user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            if (page == null || page == 0) {
                page = 1;
            }
            int limit = 10;
            UserAddressListRequest req = new UserAddressListRequest();
    		UserAddressListResponse response = new UserAddressListResponse();
    		req.setUserId(user.getId());
    		req.setPage(page);

            ServiceMessage serviceMsg = new ServiceMessage("interfaces.address.list", req);
            response = (UserAddressListResponse) UserCenterClient.getInstance()
                    .setServiceMessage(serviceMsg).send();

    		if(response.isSuccess()) {
    			map.put("list", response.getList());
                map.put("page", page);
                map.put("pages", calcPage(response.getCount(), limit));
                return new AppResult(SUCCESS, map);
    		} else {
    			return getHessianResult(response, response.getMsg(), response.getErrorMsg());
    		}

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * @date 2019年5月29日
     * @author wangyun
     * @time 下午4:16:26
     * @Description 查询默认地址
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/defaultAddress", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "默认地址")
    public AppResult queryDefaultAddress(
            @ApiParam("用户token") @RequestParam String token) {
    	try {
    		 User user = userService.getByToken(token);
             if (user == null) {
                 return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
             }

             UserAddress address = userAddressService.queryAddress(user.getId());
    		return new AppResult(SUCCESS,address);
		} catch (Exception e) {
			logger.error(e);
	        e.printStackTrace();
	        return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
    }


   /**
    * @date 2019年5月21日
    * @author wangyun
    * @time 上午9:16:57
    * @Description 地址删除
    *
    * @param token
    * @param id
    * @return
    */
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "地址删除")
    public AppResult deleteAddress(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("地址ID") @RequestParam Integer id) {
        try {
            User user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            UserAddressDeleteRequest req =  new UserAddressDeleteRequest();
    		Response response = new Response();

    		req.setAddressId(Integer.valueOf(id));
    		req.setUserId(user.getId());

            ServiceMessage serviceMsg = new ServiceMessage("interfaces.address.delete", req);
            response = UserCenterClient.getInstance()
                    .setServiceMessage(serviceMsg).send();

    		return getHessianResult(response, response.getMsg(), response.getErrorMsg());

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

   /**
    * @date 2019年5月13日
    * @author wangyun
    * @time 下午5:06:35
    * @Description 新增/编辑地址
    *
    * @param token
    * @param name
    * @param phone
    * @param detail
    * @param remarks
    * @param postcode
    * @param id
    * @param reserve
    * @return
    */
    @RequestMapping(value = "/newAddress", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增地址/编辑保存地址")
    public AppResult newAddress(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("收件姓名") @RequestParam String name,
            @ApiParam("收件电话") @RequestParam String phone,
            @ApiParam("详细地址") @RequestParam String detail,
            @ApiParam("备注") @RequestParam(required = false) String remarks,
            @ApiParam("省ID") @RequestParam String provinceId,
            @ApiParam("市ID") @RequestParam String cityId,
            @ApiParam("区/县ID") @RequestParam(required = false) String areaId,
            @ApiParam("用户邮编") @RequestParam(required = false) Integer postcode,
            @ApiParam("地址id 保存时用，新增时不传") @RequestParam(required = false) String id,
            @ApiParam("是否默认  0否1是 ") @RequestParam(required = false) Integer reserve
    ) {
        try {
            User user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            if (StringUtils.isEmpty(id) || "null".equals(id)) {
                id = null;
            }
            if (StringUtils.isEmpty(name) || "null".equals(name)) {
                return new AppResult(FAILED, "请输入收件人姓名！");
            }
            if (StringUtils.isEmpty(phone) || "null".equals(phone)) {
                return new AppResult(FAILED, "请输入收件人电话！");
            }
            if (phone.length() != 11) {
                return new AppResult(FAILED, "请输入正确的联系电话");
            }
            if (!RegularUtils.checkString(phone, RegularUtils.REGEX_MOBILE)) {
                return new AppResult(FAILED, "请输入正确的联系电话");
            }
            if (StringUtils.isEmpty(detail) || "null".equals(detail)) {
                return new AppResult(FAILED, "请输入收件人详细地址！");
            }
      /*      if (StringUtils.isEmpty(remarks) || "null".equals(remarks)) {
                return new AppResult(FAILED, "收件人省市没有选择！");
            }*/
            if (StringUtils.isBlank(cityId)) {
            	return new AppResult(FAILED, "请选择所在城市");
            }
            if (StringUtils.isBlank(provinceId)) {
            	return new AppResult(FAILED, "请选择所在省份");
            }

            UserAddressRequest req =  new UserAddressRequest();
            UserAddressResponse response = new UserAddressResponse();

    		req.setAddressId(!StringUtils.isEmpty(id) ? Integer.valueOf(id) : null);
    		req.setRemarks(remarks);
    		req.setDetailAddress(detail);
    		req.setIsDefault(reserve);
    		req.setName(name);
    		req.setPhone(phone);
    		req.setPostcode(postcode);
    		req.setUserId(user.getId());
//    		req.setCityId(cityId);
//    		req.setProvinceId(provinceId);
    		req.setProvinceCode(provinceId);
    		req.setCityCode(cityId);
    		req.setAreaCode(areaId == null ? null : areaId);
    		req.setRemarks(remarks);
            ServiceMessage serviceMsg = new ServiceMessage("interfaces.address.saveOrUpdate", req);
            response = (UserAddressResponse) UserCenterClient.getInstance()
                    .setServiceMessage(serviceMsg).send();

            if(response.isSuccess()){
            	return new AppResult(SUCCESS, response.getAddress());
            } else {
            	return new AppResult(FAILED, response.getErrorMsg());
            }

        } catch (Exception e) {
        	logger.error(e);
        	e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    @RequestMapping(value = "/getAreaAll", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有省/市/区县信息")
    public AppResult getAreaByParentCode(
            HttpServletRequest request,
            @ApiParam("token") @RequestParam(required = false) String token,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("app版本") @RequestParam String appVersion) {
        try {
            List<AreaIndexVO> list = null;
            if(null == memcachedManager.get(Constants.AREA_KET)) {
                list = areaService.getAllArea();
                memcachedManager.addOrReplace(Constants.AREA_KET, list, Constants.AREA_EXPIRE);
            }else {
                list = (List<AreaIndexVO>)memcachedManager.get(Constants.AREA_KET);
            }
//            memcachedManager.delete(Constants.AREA_KET);
//            List<AreaIndexVO> list = areaService.getAllArea();
            return new AppResult(SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


   /**
    * @date 2019年5月13日
    * @author wangyun
    * @time 下午5:21:53
    * @Description 地址详情
    *
    * @param token
    * @param id
    * @return
    */
    @RequestMapping(value = "/detailAddress", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "地址详情")
    public AppResult detailAddress(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("地址id") @RequestParam Integer id) {
        try {
            User user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            UserAddress ua = userAddressService.selectKeyUserAddress(Integer.valueOf(id));
            if (ua == null) {
                return new AppResult(FAILED, "您输入的参数有误，请重新输入");
            }
            if(ua.getUserId().intValue() != user.getId()) {
            	return new AppResult(FAILED, "地址归属不正确！");
            }
            return new AppResult(SUCCESS, ua);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * @date 2019年5月13日
     * @author wangyun
     * @time 下午5:23:01
     * @Description 修改默认地址接口
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/editDefaultAddress", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改默认地址接口")
    public AppResult editAddress(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("地址id") @RequestParam Integer id) {
        try {
            User user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            userAddressService.updateKeyReserve(id, user.getId());
            return new AppResult(SUCCESS, "选择地址为默认地址成功");
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * @Description(描述):更换手机号第一步
     * @author 王信
     * @date 2016/5/20
     * @params
     **/
    @RequestMapping(value = "/editPhone1", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更换手机号第一步")
    @NeedLogin
    public AppResult editPhone1(HttpSession session,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("app版本") @RequestParam String appVersion,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
        try {
            String update = checkVersion(client, appVersion);//检查是否需要更新
            User user = userService.getByToken(token);
            Integer count = codeLimitService.listCountByPhone(user.getPhone());
            if (count >= 10) {
                return new AppResult(FAILED, "今天发送短信已经超过10条");
            }
            if (getVersion(appVersion) <= 302) {
                return new AppResult(FAILED, "请更新版本！");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            String verifyCode = generateVerifyCode(6);
            Object o = session.getAttribute("verifyCode");
            if (o != null) {
                if (System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime() <= 60 * 1000) {
                    return new AppResult(FAILED, "请一分钟后再发送验证码");
                }
            }
            //YunSendMessage.send(new ShortMessage(user.getPhone(), verifyCode));
            session.setAttribute("verifyCode", verifyCode);
            session.setAttribute("lastSendTime", new Date());
            map.put("editphone", 1);//表示发送短信成功
            map.put("update", update);
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * @Description(描述):更换手机号第2步
     * @author 王信
     * @date 2016/5/20
     * @params
     **/
    @RequestMapping(value = "/editPhone2", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更换手机号第二步")
    @NeedLogin
    public AppResult editPhone2(HttpSession session,
                                @ApiParam("app版本") @RequestParam String appVersion,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("原手机验证码") @RequestParam String verifyCode, @ApiParam("新手机号码") @RequestParam String phone) {
        try {
            Integer count = codeLimitService.listCountByPhone(phone);
            if (count >= 10) {
                return new AppResult(FAILED, "今天发送短信已经超过10条");
            }
            String update = checkVersion(client, appVersion);//检查是否需要更新
            if (getVersion(appVersion) <= 302) {
                return new AppResult(FAILED, "请更新版本！");
            }
            if (verifyCode == null) {
                return new AppResult(FAILED, "验证码为空！");
            }
            User user = userService.getByToken(token);
            if (user.getPhone().equals(phone)) {
                return new AppResult(FAILED, "更换手机号与现有手机号一致！");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            String verifyCodeSession = (String) session.getAttribute("verifyCode");
            if (!verifyCode.equals(verifyCodeSession)) {
                return new AppResult(FAILED, "验证码不一致！");
            }
            if (!userService.checkPhone(phone)) {
                return new AppResult(FAILED, "请输入正确的手机号");
            }
//            if (!RegularUtils.checkPhone(phone, RegularUtils.REGEX_MOBILE)) {
//                return new AppResult(FAILED, "收件人电话号段异常！");
//            }
            Object o = session.getAttribute("verifyCodeNew");
            if (o != null) {
                if (System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTimeNew")).getTime() <= 60 * 1000) {
                    return new AppResult(FAILED, "请一分钟后再发送验证码");
                }
            }
            String code = generateVerifyCode(6);
            //YunSendMessage.send(new ShortMessage(phone, Code));//给新手机发送短信
            session.setAttribute("verifyCodeNew", code);
            session.setAttribute("isEditPhone", Constants.YES);
            session.setAttribute("newPhone", phone);
            session.setAttribute("lastSendTimeNew", new Date());
            session.setAttribute("editPhoneUserId", user.getId());
            map.put("editphone", 1);//表示发送短信成功
            map.put("update", update);
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * @Description(描述):更换手机号第3步
     * @author 王信
     * @date 2016/5/20
     * @params
     **/
    @RequestMapping(value = "/editPhone3", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更换手机号第三步")
    @NeedLogin
    public AppResult editPhone3(HttpSession session, HttpServletRequest request,
                                @ApiParam("app版本") @RequestParam String appVersion,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("新手机验证码") @RequestParam String verifyCode, @ApiParam("新手机号码") @RequestParam String phone) {
        try {
            Integer count = codeLimitService.listCountByPhone(phone);
            if (count >= 10) {
                return new AppResult(FAILED, "今天发送短信已经超过10条");
            }
            String update = checkVersion(client, appVersion);//检查是否需要更新
            if (getVersion(appVersion) <= 302) {
                return new AppResult(FAILED, "请更新版本！");
            }
            if (verifyCode == null) {
                return new AppResult(FAILED, "验证码为空！");
            }
            if (!userService.checkPhone(phone)) {
                return new AppResult(FAILED, "请输入正确的手机号");
            }
            if ((String) session.getAttribute("isEditPhone") == null && !Constants.YES.equals((String) session.getAttribute("isEditPhone"))) {
                return new AppResult(FAILED, "请求不合法");
            }
//            if (!RegularUtils.checkPhone(phone, RegularUtils.REGEX_MOBILE)) {
//                return new AppResult(FAILED, "电话号段异常！");
//            }
            User user = userService.getByToken(token);
            if (userService.getByPhone(phone) != null) {
                return new AppResult(FAILED, "此号码已经存在！");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            String verifyCodeSession = (String) session.getAttribute("verifyCodeNew");//新手机的验证码
            String phoneSession = (String) session.getAttribute("newPhone");
            Integer userId = (Integer) session.getAttribute("editPhoneUserId");
            if (!phone.equals(phoneSession)) {
                return new AppResult(FAILED, "您操作的手机号有误！");
            }
            if (!verifyCodeSession.equals(verifyCode)) {
                return new AppResult(FAILED, "验证码不一致！");
            }
            if (!userId.equals(user.getId())) {
                return new AppResult(FAILED, "更新手机号的用户不一致！");
            }
            UserPhoneExchange userPhoneExchange = new UserPhoneExchange();//保存更换手机的记录
            userPhoneExchange.setUserId(user.getId());
            userPhoneExchange.setNewPhone(phone);
            userPhoneExchange.setOldPhone(user.getPhone());
            userPhoneExchange.setType(0);
            userPhoneExchange.setStatus(1);
            userPhoneExchange.setTime(new Date());
            int ret = userService.updatephone(user.getId(), phone, getIpAddr(request));
            if (ret != 0) {//保存电话号码失败时，重新绑定电话号码原有的电话号码
                return new AppResult(FAILED, "电话号码已经存在");
            }
            map.put("update", update);
            logger.info("======用户ID: " + user.getId() + "==,姓名为:" + user.getTrueName() + "====更换手机号码成功===");
            return new AppResult(SUCCESS, "更换号码成功", map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 红包兑换码
     * @param session
     * @param request
     * @param appVersion
     * @param client
     * @param phone
     * @param token
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/redeemCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "红包兑换码")
    public AppResult redeemCode(HttpSession session, HttpServletRequest request,
                                @ApiParam("app版本") @RequestParam String appVersion,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam Integer client,
                                @ApiParam("用户电话号码(无token时传电话号码)") @RequestParam(required = false) String phone,
                                @ApiParam("用户token") @RequestParam(required = false) String token, @ApiParam("兑换码") @RequestParam String code) throws Exception {

        User user = null;
        if (!StringUtils.isEmpty(token)) {
            user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(FAILED, "请重新登录");
            }
        }
        if (StringUtils.isEmpty(phone) && user == null) {
            return new AppResult(FAILED, "手机号码为空");
        }
        if (!RegularUtils.checkString(phone, RegularUtils.REGEX_MOBILE) && user == null) {
            return new AppResult(FAILED, "手机号码格式有误");
        }
        if (user == null) {
            user = userService.getByPhone(phone);
        }
        HongbaoRedeem redeem = hongbaoRedeemService.queryRedeemCode(code);
        if (redeem == null) {
            return new AppResult(FAILED, "您输入的兑换码不存在");
        }
        if (redeem.getStatus() == 0) {//兑换码状态0不可用1可用
            return new AppResult(FAILED, "兑换码失效");
        }
        if (redeem.getUserId() != null && user != null) {
            if (!redeem.getUserId().equals(user.getId())) {
                return new AppResult(FAILED, "此兑换码需指定用户使用");
            }
        }
        if (redeem.getType() == 1) {
            Integer count = hongbaoRedeemService.queryByRedeemCodeUse(redeem.getId());
            if (count > 0) {
                return new AppResult(FAILED, "唯一兑换码已使用过，不可用");
            }
        }
        if (redeem.getUseCount() < 1) {
            return new AppResult(FAILED, "兑换码次数已经用尽");
        }
        Date date = new Date();
        if (date.before(redeem.getCreateTime())) {
            return new AppResult(FAILED, "兑换码时间还没到");
        }
        if (date.after(redeem.getExpireTime())) {
            return new AppResult(FAILED, "兑换码已经过期");
        }
        Map<String, Object> map = hongbaoRedeemService.saveHongbao(redeem, user, phone);
        Integer ret = Integer.valueOf(map.get("ret").toString());
        if (ret == 1) {
            return new AppResult(FAILED, "您已经使用过兑换码");
        }
        if (ret == 2) {
            return new AppResult(FAILED, "兑换码失效");//红包模版组为空，管理员没有配置兑换码对应的模版组
        }
        map.put("phone", phone);
        List flist = new ArrayList();
        Object hbs = map.get("list");
        if (hbs != null) {
            for (Hongbao hb : (List<Hongbao>) hbs) {
                Long residual = DateFormatTools.dayToDaySubtract(new Date(), hb.getExpireTime());
                Map<String, Object> min = new HashMap<String, Object>();
                min.put("amount", hb.getAmount());
                String month = hongbaoTemplateService.queryById(hb.getTemplateId()).get("monthType").toString();
                List<String> ts = new ArrayList<String>();
                if (month.contains("天")) {
                    ts.add("1.满" + String.valueOf(hb.getLimitAmount()) + "元可抵扣");
                    ts.add("2.期限(" + month + ")");
                } else {
                    ts.add("1.满" + String.valueOf(hb.getLimitAmount()) + "元可返现");
                    ts.add("2.使用期限≥" + month + "天");
                }
                min.put("prompt", ts);
                min.put("residual", "(" + residual + "天后过期)");
                flist.add(min);
            }
        }
        map.put("list", flist);
        return new AppResult(SUCCESS, map);
    }

    /**
     * 设置手势密码、指纹密码
     * @param token
     * @param isGesturePassword
     * @param gesturePassword
     * @param isFingerprintPassword
     * @return
     */
    @ApiOperation(value = "设置手势密码、指纹密码")
    @RequestMapping(value = "/setGestureAndFingerprintPwd", method = RequestMethod.POST)
    @ResponseBody
    public AppResult setGestureAndFingerprintPwd(
    		@ApiParam("用户token") @RequestParam String token,
    		@ApiParam("是否启用手势密码") @RequestParam(required = false) Integer isGesturePassword,
    		@ApiParam("手势密码") @RequestParam(required = false) String gesturePassword,
    		@ApiParam("是否启用指纹密码") @RequestParam(required = false) Integer isFingerprintPassword){

    	try {
			User user = userService.checkLogin(token);
			if(user == null){
				 return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}

			if (isGesturePassword ==  null) {
				isGesturePassword = 0;
			}

			if (isFingerprintPassword == null) {
				isFingerprintPassword = 0;
			}

			int ret = userService.updatePwdByUserId(user.getId(), isGesturePassword, gesturePassword, isFingerprintPassword);
			if (ret == 1) {
				return new AppResult(SUCCESS, "设置成功");
			}else{
				return new AppResult(FAILED, "设置失败");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
    }

    /**
     * 获取支持的银行信息
     * @param token
     * @param appVersion
     * @return
     */
    @ApiOperation(value = "获取支持的银行信息")
    @ResponseBody
    @RequestMapping(value = "/getBankList", method = RequestMethod.GET)
    public AppResult getBankList(@ApiParam("用户token") @RequestParam(required = false) String token,
                                 @ApiParam("App版本号") @RequestParam String appVersion) {
        try {
            Map<String, Object> result = new HashMap<>();
            // 富友支持的银行，按照银行卡的ID进行升序排列
            BankExample example = new BankExample();
            example.createCriteria().andFuiouCodeIsNotNull();
            example.setOrderByClause("id asc");
            List<Bank> bankList = bankService.getBankListByExample(example);
            for(Bank bank : bankList) {
                bank.setBankIcon(ClientConstants.ALIBABA_PATH + "images/bank-icon/bank" + bank.getId() + "@2x.png");
            }
            result.put("bankList", bankList);
            return new AppResult(SUCCESS, result);
        } catch (Exception e) {
        	e.printStackTrace();
            return new AppResult(FAILED, "查询失败！");
        }
    }

    /**
     * 获取用户银行卡列表
     * @param token
     * @param appVersion
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBankCardList", method = RequestMethod.GET)
    public AppResult getBankCardList(@ApiParam("用户token") @RequestParam(required = false) String token,
                                 @ApiParam("App版本号") @RequestParam String appVersion) {
        try {
        	User user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Map<String, Object> result = new HashMap<>();
            List<BankCard> bankCards = bankCardService.getByUserId(user.getId());
            if(bankCards != null  && bankCards.size() > 0) {
            	for (BankCard bankCard : bankCards) {
            		String cardNo = bankCard.getCardNumber();
            		cardNo = CommonUtils.getCardNoSixFour(cardNo);
            		bankCard.setCardNumber(cardNo);
            		bankCard.setBankIcon(ClientConstants.ALIBABA_PATH + "images/bank-icon/bank" + bankCard.getBankId() + "@2x.png");
				}
            	
            }
            if(bankCards != null  && bankCards.size() >= 5) {//5张银行卡不可以再添加
       			result.put("canAddBankCards", false);
       			result.put("canAddBankCardsTips", "最多只可添加5张银行卡");
            } else {
            	result.put("canAddBankCards", true);
            }
            result.put("bankCards", bankCards);
            result.put("tips", "如有疑问，请致电奔富畜牧业发展有限公司");
            result.put("telphoneTips", "电话：400-179-8099");
            result.put("cardType", "储蓄卡");
            result.put("rule_lable", "*最多只可添加5张银行卡");
            return new AppResult(SUCCESS, result);
        } catch (Exception e) {
        	e.printStackTrace();
            return new AppResult(FAILED, "查询失败！");
        }
    }

    /**
     * 新增银行卡
     * @param token
     * @param bankId
     * @param phone
     * @param cardNumber
     * @param provinceId
     * @param cityId
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/addBankCard", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增银行卡")
    public AppResult addBankCard(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("银行ID") @RequestParam Integer bankId,
            @ApiParam("银行预留手机号") @RequestParam String phone,
            @ApiParam("银行卡号") @RequestParam String cardNumber,
            @ApiParam("省") @RequestParam Integer provinceId,
            @ApiParam("市") @RequestParam Integer cityId,
            @ApiParam("App版本号") @RequestParam String appVersion
    ) {
        try {
        	Map<String, Object> result = new HashMap<>();
            User user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }

            if(null == user.getTrueName() || null == user.getIdentityCard()) {
            	return new AppResult(FAILED, "请先实名认证！");
            }
            //判断卡号重复添加
            List<BankCard> isExists  = bankCardService.getByCardnumber(cardNumber);
            if(isExists == null || isExists.size() > 0) {
            	return new AppResult(FAILED, "该卡已被绑定");
            }
            //最多只能添加5张
            List<BankCard> hasBankCards = bankCardService.getBankNoByUserId(user.getId());
            if(hasBankCards != null && hasBankCards.size() >= 5) {
            	 return new AppResult(FAILED, "最多只能添加5张银行卡");
            }

            //调用四要素认证

            //添加
            BankCard bankCard = new BankCard();
            bankCard.setUserId(user.getId());
            bankCard.setPhone(phone);
            bankCard.setBankId(bankId);
            bankCard.setCardNumber(cardNumber);
            bankCard.setCityId(cityId);
            bankCard.setCreateDate(new Date());
            bankCard.setProvinceId(provinceId);
            bankCard.setStatus(0);
            bankCardService.saveNewBankCard(bankCard);

            return new AppResult(SUCCESS, result);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * 账户中心 ydp
     * @param request
     * @param client
     * @param token
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    @ApiOperation(value = "账户中心")
    @ResponseBody
    public AppResult aboutus(HttpServletRequest request,
    		@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
    		@ApiParam("用户token") @RequestParam String token,
    		@ApiParam("app版本") @RequestParam String appVersion) {
        try {
        	User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            String auth = "未认证";
            if(null != user.getTrueName() && null != user.getIdentityCard()) {
            	auth = "已认证";
            }
            String password = "未设置";
            if(null != user.getPassword()) {
            	password = "修改";
            }
            String username = "未设置";
            if(StringUtils.isNotEmpty(user.getUsername())) {
                username = user.getUsername();
            }
            List<BankCard> cards = bankCardService.getCardByUserId(user.getId(), 0);
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("headIcon", ClientConstants.ALIBABA_PATH + "upload/login.png");
        	if(null != user.getAvatarId()) {
                Upload upload = uploadService.get(user.getAvatarId());
                map.put("headIcon", upload.getCdnPath());
            }
        	List<Icon> icons = iconService.getUsingIcons(4, appVersion);
            for (Icon icon : icons) {
            	icon.setPath(ClientConstants.ALIBABA_PATH + "upload/" + icon.getPath());
            	if("myRealName".equals(icon.getKey())) { //实名
            		icon.setSubtitle(auth);
            	}else if("myBankList".equals(icon.getKey())){ //绑卡
            		if(cards.size() == 0) {
            			icon.setSubtitle("未绑卡");
            		}else {
            			icon.setSubtitle("");
            		}
            	}else if("myResetLoginPassword".equals(icon.getKey())){// 修改登录密码
            		icon.setSubtitle(password);
            	} else if("myNickName".equals(icon.getKey())) {
            		icon.setSubtitle(username);
            	}
            }
            map.put("icons", icons);
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, "账户中心异常");
        }
    }

   /**
    * @date 2019年5月15日
    * @author wangyun
    * @time 下午4:33:43
    * @Description 实名认证接口
    *
    * @param request
    * @param client
    * @param token
    * @param realName
    * @param idNumber
    * @return
    */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ApiOperation(value = "实名认证接口")
    @ResponseBody
    public AppResult authentication(HttpServletRequest request,
    		@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
    		@ApiParam("用户token") @RequestParam String token,
    		@ApiParam("真实姓名") @RequestParam String realName,
    		@ApiParam("身份证") @RequestParam String idNumber) {
    	try {
    		   User user = userService.getByToken(token);
               if (user == null) {
                   return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
               }
               if (StringUtils.isBlank(realName) || !RegularUtils.checkString(realName, RegularUtils.REGEX_TRUENAME)) {
                   return new AppResult(FAILED, "真实姓名格式不正确");
               }
               boolean isValidate = RegularUtils.checkString(idNumber, RegularUtils.REGEX_ID_CARD);
               if(!isValidate) {
            	   return new AppResult(FAILED, "请输入正确的身份证号码！");
               }
               //判断身份证是否存在
               boolean isIDCardExists  = userService.getByTrueNameAndIDCard(idNumber);
    		   if(isIDCardExists) {
    			   return new AppResult(FAILED, "身份证号已存在！");
    		   }

    		   //判断是否实名
    		   if(userAuthenticationService.queryUserIsAuth(user.getId(),realName, idNumber)) {
    			   return new AppResult(FAILED, "用户已实名");
    		   }

    		   idNumber = idNumber.toUpperCase();
               user.setTrueName(realName);
               user.setIdentityCard(idNumber);
               user.setBirthday(CommonUtils.getBirthDateFromCard(idNumber).getTime());
               user.setSex(CommonUtils.getSexFromCard(idNumber));
               int authId = userService.insertUserAuth(user.getId(), realName, idNumber);

               //插入成功后调用第三方认证
               int code = 0;
               if(authId != 0) {
                  code = userService.updateAuth(user, authId);
               }
               Map<String, String> map = new HashMap<String, String>();
               if(code == 1) {
                   return new AppResult(SUCCESS,"认证成功", map);
               } else {
                   return new AppResult(FAILED,"认证失败", map);
               }
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			 return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
    }

    @RequestMapping(value = "/toBindCard", method = RequestMethod.POST)
    @ApiOperation(value = "跳转银行绑卡")
    @ResponseBody
    public AppResult toBindCard(
        HttpServletRequest request,
        HttpSession session,
        @ApiParam("token") @RequestParam String token,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
        try {
        	User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, "登录失效，请重新登录");
            }
            //用户实名未绑卡
            if (StringUtils.isBlank(user.getTrueName()) && StringUtils.isBlank(user.getIdentityCard())) {
                return new AppResult(FAILED, "请先实名");
            }

            return new AppResult(SUCCESS, user);
        } catch (Exception e) {
            logger.error("查询用户信息失败！");
            e.printStackTrace();
            return new AppResult(ERROR, "跳转银行绑卡页面异常");
        }
    }

    /**
     * 绑卡 ydp
     * @param request
     * @param session
     * @param token
     * @param trueName
     * @param identityCard
     * @param cardNo
     * @param phoneNo
     * @param verifycode
     * @return
     */
    @RequestMapping(value = "/bindCard", method = RequestMethod.POST)
    @ApiOperation(value = "银行绑卡")
    @ResponseBody
    public AppResult bindCard(
        HttpServletRequest request,
        HttpSession session,
        @ApiParam("token") @RequestParam String token,
        @ApiParam("银行卡号") @RequestParam String cardNo,
        @ApiParam("银行预留手机号") @RequestParam String phoneNo,
        @ApiParam("验证码") @RequestParam(required = false) String verifycode,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("请求步骤 1：发送验证码，2：验证绑卡") @RequestParam(required = false) String reqStep,
        @ApiParam("真实姓名") @RequestParam(required = false) String trueName,
        @ApiParam("身份证号码") @RequestParam(required = false) String idCard) {
        long start = System.currentTimeMillis();
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, "登录失效，请重新登录");
            }
            //用户实名未绑卡
//            if (StringUtils.isBlank(user.getTrueName()) && StringUtils.isBlank(user.getIdentityCard())) {
//                return new AppResult(FAILED, "请先实名");
//            }
            boolean realName = true;
            if(StringUtils.isBlank(user.getTrueName()) && StringUtils.isBlank(user.getIdentityCard())) {
                realName = false;
            }else {
                idCard = user.getIdentityCard();
                trueName = user.getTrueName();
            }
            if(!realName) {
                if (StringUtils.isBlank(trueName) || !RegularUtils.checkString(trueName, RegularUtils.REGEX_TRUENAME)) {
                    return new AppResult(FAILED, "真实姓名格式不正确");
                }
                if (StringUtils.isBlank(idCard) || !RegularUtils.checkString(idCard, RegularUtils.REGEX_ID_CARD)) {
                    return new AppResult(FAILED, "身份证号格式不正确");
                }
            }
            if (StringUtils.isBlank(cardNo) || !RegularUtils.checkString(cardNo, RegularUtils.REGEX_CARD)) {
                return new AppResult(FAILED, "银行卡号不正确");
            }
            if (StringUtils.isBlank(phoneNo) || !RegularUtils.checkString(phoneNo, RegularUtils.REGEX_MOBILE)) {
                return new AppResult(FAILED, "银行预留手机号格式不正确");
            }
            //最多只能添加5张
            List<BankCard> hasBankCards = bankCardService.getBankNoByUserId(user.getId());
            if(hasBankCards != null && hasBankCards.size() >= 5) {
                 return new AppResult(FAILED, "最多只能添加5张银行卡");
            }
            if ("2".equals(reqStep) && StringUtils.isBlank(verifycode)) {
                return new AppResult(FAILED, "请输入验证码");
            }
            //绑卡记录
            UserAuthentication authentication = new UserAuthentication();
            if(!realName) {
                //验证姓名和身份证是否存在
                if (user.getTrueName() == null && user.getIdentityCard() == null) {
                    if (userService.getByTrueNameAndIDCard(idCard)) {
                        logger.info("=====================该身份信息已经被注册===============================");
                        authentication.setStatus(ConstantsAdmin.USER_STATUS_FAILED);
                        authentication.setMessage("该身份证已经注册");
                        authentication.setType(ConstantsAdmin.USER_AUTHENTICATION_TYPE_ADD);
                        userAuthenticationService.save(authentication);
                        return new AppResult(FAILED, "该身份证已经注册");
                    }
                }
            }
            /*if (bankCardService.getByUserId(user.getId()) != null) {
                return new AppResult(FAILED, "您已经绑定过一张银行卡了");
            }*/
            List<BankCard> bcs = bankCardService.getByCardnumber(cardNo);
            if (bcs != null && !bcs.isEmpty()) {
                return new AppResult(FAILED, "该银行卡已经被其他用户绑定,请更换银行卡 ");
            }
            //查看cardBin信息，没有找到cardBin提示用户换卡
            BankCardBin cardBin = bankCardBinService.findBankCardBin(cardNo);
            if(null == cardBin || null == cardBin.getBank()  || null == cardBin.getBank().getPayChannel()) {
                return new AppResult(FAILED, "您的银行卡片暂不支持，请联系客服！");
            }
            //协议号
            String protoColNo = "";
            //第一步：发送验证码
            if (!StringUtils.equals("2", reqStep)) {
                if(null != session.getAttribute("lastSendTime")) {
                    long diffTime = System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime();
                    if (diffTime <= Constants.ALLOW_SECOUND * 1000) {
                        int secound = Constants.ALLOW_SECOUND-Integer.valueOf((diffTime/1000)+"");
                        return new AppResult(FAILED, "请一分钟后再发送验证码", secound);
                    }
                }
                Map<String,Object> map = new HashMap<String, Object>();
                if(cardBin.getBank().getPayChannel().equals(OutPayEnum.FUIOU_QUICK.getFeatureName())) {
                    String verifyCode = generateVerifyCode(6);
                    if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
                        verifyCode = "000000";
                    }
                    logger.info("短信验证码："+verifyCode);
                    SendMessageRequest smr = new SendMessageRequest();
                    smr.setContent(DictConstants.VALIDATE_CODE.replaceAll("\\{code}", verifyCode));
                    smr.setPhone(phoneNo);
                    ServiceMessage msg = new ServiceMessage("message.send", smr);
                    SendMessageResponse result = (SendMessageResponse) OpenApiClient.getInstance()
                            .setServiceMessage(msg).send();
                    if (result.isSuccess()) {
                        session.setAttribute("verifyPhone", phoneNo);
                        session.setAttribute("verifyCode", verifyCode);
                        session.setAttribute("lastSendTime", new Date());
                        CodeLimit limit = new CodeLimit();
                        limit.setCode(verifyCode);
                        limit.setCreateTime(new Date());
                        limit.setPhone(phoneNo);
                        codeLimitService.save(limit);
                    } else {
                    	return new AppResult(FAILED, result.getErrorMsg());
                    }
                }else if(cardBin.getBank().getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
                    AllinPayBindCardRequest pay = new AllinPayBindCardRequest();
                    pay.setBankCard(cardNo);
                    pay.setIdentityCard(idCard);
                    pay.setPhoneNo(phoneNo);
                    pay.setTrueName(trueName);
                    pay.setUserId(user.getId().toString());
                    ServiceMessage msg = new ServiceMessage("allinpay.bind.card", pay);
                    AllinPayBindCardResponse result = (AllinPayBindCardResponse) OpenApiClient.getInstance()
                            .setServiceMessage(msg).send();
                    if (result.isSuccess()) {
                        session.setAttribute("verifyPhone", phoneNo);
                        session.setAttribute("lastSendTime", new Date());
                        session.setAttribute("thpInfo", result.getThpInfo());
                    } else {
                    	return new AppResult(FAILED, result.getErrorMsg());
                    }
                }
                return new AppResult(SUCCESS, map);
            }
            long startapi = System.currentTimeMillis();
            if(cardBin.getBank().getPayChannel().equals(OutPayEnum.FUIOU_QUICK.getFeatureName())) {
                //第二步：验证验证码信息
                String sessionPhoneNo = (String) session.getAttribute("verifyPhone");
                String sessionVerifyCode = (String) session.getAttribute("verifyCode");
                if (StringUtils.isBlank(sessionPhoneNo) || StringUtils.isBlank(sessionVerifyCode)) {
                    return new AppResult(FAILED, "验证超时，请重新获取验证码");
                }
                if (!sessionPhoneNo.equals(phoneNo)) {
                    return new AppResult(FAILED, "手机号变更，请重新获取验证码");
                }
                if (!sessionVerifyCode.equals(verifycode)) {
                    return new AppResult(FAILED, "验证码错误");
                }
                logger.info(user.getUsername() + "======================"+OutPayEnum.FUIOU_QUICK.getDescription()+"实名绑卡======================");
                //富友银行卡验证(四要素)接口
                FuiouBindCardRequest smr = new FuiouBindCardRequest();
                smr.setBankCard(cardNo);
                smr.setIdentityCard(user.getIdentityCard());
                smr.setPhoneNo(phoneNo);
                smr.setTrueName(user.getTrueName());
                smr.setUserId(String.valueOf(user.getId()));
                ServiceMessage msg = new ServiceMessage("fuiou.bind.card", smr);
                FuiouBindCardResponse fbcr = (FuiouBindCardResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if(!fbcr.isSuccess()) {
                    if(!realName) {
                        authentication.setIdentificationNo(user.getIdentityCard());
                        authentication.setTrueName(user.getTrueName());
                        authentication.setUserId(user.getId());
                        authentication.setTime(new Date());
                        authentication.setStatus(ConstantsAdmin.USER_STATUS_FAILED);
                        authentication.setMessage(fbcr.getBankCardResqData().getRdesc());
                        authentication.setType(ConstantsAdmin.USER_AUTHENTICATION_TYPE_ADD);
                        userAuthenticationService.save(authentication);
                    }
                    return new AppResult(FAILED, fbcr.getErrorMsg());
                }
            }else if(cardBin.getBank().getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
                logger.info(user.getUsername() + "======================"+OutPayEnum.ALLINPAY.getDescription()+"实名绑卡======================");
                AllinPayBindCardRequest pay = new AllinPayBindCardRequest();
                pay.setBankCard(cardNo);
                pay.setIdentityCard(idCard);
                pay.setPhoneNo(phoneNo);
                pay.setTrueName(trueName);
                pay.setUserId(user.getId().toString());
                pay.setCode(verifycode);
                String thpInfo = (String) session.getAttribute("thpInfo");
                if(null != thpInfo) {
                    pay.setThpInfo(thpInfo);
                }
                ServiceMessage msg = new ServiceMessage("allinpay.bind.card.confirm", pay);
                AllinPayBindCardResponse result = (AllinPayBindCardResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if(!result.isSuccess()) {
                    if(!realName) {
                        authentication.setIdentificationNo(user.getIdentityCard());
                        authentication.setTrueName(user.getTrueName());
                        authentication.setUserId(user.getId());
                        authentication.setTime(new Date());
                        authentication.setStatus(ConstantsAdmin.USER_STATUS_FAILED);
                        authentication.setMessage(OutPayEnum.ALLINPAY.getDescription()+"认证失败");
                        authentication.setType(ConstantsAdmin.USER_AUTHENTICATION_TYPE_ADD);
                        userAuthenticationService.save(authentication);
                    }
                    return new AppResult(FAILED, result.getErrorMsg());
                }
                protoColNo = result.getAgreeId();
            }
            long endapi = System.currentTimeMillis();
            logger.info((endapi-startapi)+"api ms");
            if(!realName) {
                //认证成功更新用户
                user.setIdentityCard(idCard);
                user.setTrueName(trueName);
                user.setBirthday(CommonUtils.getBirthDateFromCard(user.getIdentityCard()).getTime());
                user.setSex(CommonUtils.getSexFromCard(user.getIdentityCard()));
                userService.update(user);
                //认证成功保存认证信息
                authentication.setStatus(ConstantsAdmin.USER_STATUS_SUCCESS);
                authentication.setMessage("认证成功");
                authentication.setType(ConstantsAdmin.USER_AUTHENTICATION_TYPE_ADD);
                userAuthenticationService.save(authentication);
            }

            // 本平台绑定银行卡
            BankCard newCard = new BankCard();
            newCard.setUserId(user.getId());
            newCard.setCardNumber(cardNo);
            newCard.setPhone(phoneNo);
            newCard.setStatus(0);
            newCard.setBankId(cardBin.getBank().getId());
            newCard.setProtoColNo(protoColNo);
            newCard.setPayChannel(cardBin.getBank().getPayChannel());
            bankCardService.saveNewBankCard(newCard);
            logger.info("=========     用户：" + user.getUsername() + "（" + cardNo + "）绑卡操作结束    ===========");
        } catch (Exception e) {
            logger.error("绑定银行卡异常");
            e.printStackTrace();
            return new AppResult(ERROR, "绑定银行卡异常");
        }
        long end = System.currentTimeMillis();
        logger.info((end-start)+"ms");
        session.removeAttribute("verifyPhone");
        session.removeAttribute("verifyCode");
        session.removeAttribute("lastSendTime");
        return new AppResult(SUCCESS, "恭喜你,绑定银行卡成功！", new HashMap<String, String>());
    }

    /**
     * 获取省市
     * @author ydp
     * @param request
     * @param token
     * @param client
     * @param appVersion
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getProvinceCity", method = RequestMethod.GET)
    @ApiOperation(value = "获取省市")
    @ResponseBody
    public AppResult getProvinceCity(
        HttpServletRequest request,
        @ApiParam("token") @RequestParam(required = false) String token,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("app版本") @RequestParam String appVersion) {
//        List<ProvinceVO> list = new ArrayList<ProvinceVO>();
        List<AreaIndexVO> list = null;
        try {
//            if(null == memcachedManager.get(Constants.PROVICE_CITY)) {
//                Map<Integer, ProvinceVO> map = new HashMap<Integer, ProvinceVO>();
//                List<ProvinceCity> provinceCitys = provinceCityService.query();
//                for(ProvinceCity pc : provinceCitys) {
//                    if(pc.getType()==0) {
//                        ProvinceVO p = new ProvinceVO(pc.getId(), pc.getName(), new ArrayList<CityVO>());
//                        list.add(p);
//                        map.put(p.getId(), p);
//                    }else {
//                        List<CityVO> citys = map.get(pc.getParentId()).getCitys();
//                        citys.add(new CityVO(pc.getId(), pc.getName()));
//                    }
//                }
//                list.clear();
//                //遍历map
//                Iterator<Map.Entry<Integer, ProvinceVO>> it = map.entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry<Integer, ProvinceVO> entry = it.next();
//                    list.add(entry.getValue());
//                }
//                memcachedManager.addOrReplace(Constants.PROVICE_CITY, list, 3600);
//                logger.info("provice_city select from mysql");
//            }else {
//                list = (List<ProvinceVO>)memcachedManager.get(Constants.PROVICE_CITY);
//                logger.info("provice_city select from cache");
//            }
            if(null == memcachedManager.get(Constants.AREA_KET)) {
                list = areaService.getAllArea();
                memcachedManager.addOrReplace(Constants.AREA_KET, list, Constants.AREA_EXPIRE);
            }else {
                list = (List<AreaIndexVO>)memcachedManager.get(Constants.AREA_KET);
            }
//            memcachedManager.delete(Constants.AREA_KET);
//            List<AreaIndexVO> list = areaService.getAllArea();
        } catch (Exception e) {
             e.printStackTrace();
        }
        return new AppResult(SUCCESS, list);
    }

    /**
     * @description 邀请记录查询
     * @author shuys
     * @date 2019/6/24
     * @param request
     * @param token
     * @param client
     * @param appVersion
     * @return com.goochou.p2b.app.model.AppResult
    */
    @RequestMapping(value = "/getInviteRecord", method = RequestMethod.GET)
    @ApiOperation(value = "邀请记录查询")
    @ResponseBody
    public AppResult getInviteRecord(
            HttpServletRequest request,
            @ApiParam("token") @RequestParam(required = false) String token,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("app版本") @RequestParam String appVersion,
            @ApiParam("推荐码") @RequestParam(required = false) String inviteCode) {
        try {
            User user = null;
            if(token != null) {
                user = userService.checkLogin(token);
                if (user == null) {
                    return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
                }
            } else if(inviteCode != null) {
                user = userService.getByInviteCode(inviteCode);
                if (user == null) {
                    return new AppResult(NO_LOGIN, "未找到该用户");
                }
            } else {
                return new AppResult(NO_LOGIN, "未找到该用户");
            }
            Map result = userService.queryInviteRecord(user.getId());
            return new AppResult(SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    @RequestMapping(value = "/getInterestForVipUser", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户的利润发放记录")
    @ResponseBody
    public AppResult getInterestForVipUser(
            @ApiParam("token") @RequestParam String token,
            @ApiParam("每页显示的个数") @RequestParam(required = false) Integer pageSize,
            @ApiParam("当前页数") @RequestParam(required = false) Integer curPage,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("app版本") @RequestParam String appVersion) {

        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }


            Map<String, Object> map = new HashMap<String, Object>();

            user = userService.queryById(user.getId());

            if (user.getLevel() != 2) {

                return new AppResult(FAILED, "您没有相关数据。");
            }

            String tip = "尊敬的%s您好，根据您的需求，我们专门为您制定了冻结利润发放通道，您可在这里查看您每月利润发放明细";
            if (user.getSex() == 0) {
                tip = String.format(tip, user.getTrueName() + "女士");
            } else if (user.getSex() == 1) {
                tip = String.format(tip, user.getTrueName() + "先生");
            } else {
                tip = String.format(tip, user.getTrueName());
            }
            map.put("tip", tip);

            String title = "发放须知：每月%d日发放冻结利润总金额的%.2f%%发放至您的余额中";
            title = String.format(title, user.getGiveOutDate(), user.getGiveScale());
            map.put("title", title);


            if (pageSize != null && pageSize < 1) {
                pageSize = 100;
            }
            if (curPage == null || curPage < 1) {
                curPage = 1;
            }
            VipDividendExample example = new VipDividendExample();
            example.setOrderByClause("id desc");
            if (pageSize != null) {
                example.setLimitStart((curPage - 1) * pageSize);
                example.setLimitEnd(pageSize);
            }
            VipDividendExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(user.getId());
            criteria.andIsDividendEqualTo(true);
            criteria.andDividendAmountNotEqualTo(BigDecimal.ZERO);

            List<VipDividend> list = vipDividendService.getVipDividendMapper().selectByExample(example);
            Long count =0L;

            int pageCount = 1;
            if (pageSize != null) {
                pageCount = calcPage(count.intValue(), pageSize);
            } else {
                pageSize = count.intValue();
            }

            map.put("list", list);
            map.put("count", count);
            map.put("curPage", curPage);
            map.put("pageSize", pageSize);
            map.put("pageCount", pageCount);

            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }

    }


    @RequestMapping(value = "/getInterestFromOrder", method = RequestMethod.GET)
    @ApiOperation(value = "查询利润出处")
    @ResponseBody
    public AppResult getInterestFromOrder(
            @ApiParam("token") @RequestParam String token,
            @ApiParam("orderid") @RequestParam Integer orderId,
            @ApiParam("每页显示的个数") @RequestParam(required = false) Integer pageSize,
            @ApiParam("当前页数") @RequestParam(required = false) Integer curPage,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("app版本") @RequestParam String appVersion) {

        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }

            Map<String, Object> map = new HashMap<String, Object>();

            VipDividend vipDividend = vipDividendService.getVipDividendMapper().selectByPrimaryKey(orderId);

            if (vipDividend == null) {
                return new AppResult(FAILED, "参数错误。");
            }

            if (pageSize != null && pageSize < 1) {
                pageSize = 100;
            }
            if (curPage == null || curPage < 1) {
                curPage = 1;
            }
            GoodsOrderInvestRelationExample example = new GoodsOrderInvestRelationExample();
            if (pageSize != null) {
                example.setLimitStart((curPage - 1) * pageSize);
                example.setLimitEnd(pageSize);
            }
            GoodsOrderInvestRelationExample.Criteria criteria = example.createCriteria();
            criteria.andTableTypeEqualTo(2);
            criteria.andGoodsOrderIdEqualTo(orderId);

            List<GoodsOrderInvestRelation>  relationList =goodsOrderInvestRelationService.getMapper().selectByExample(example);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for(GoodsOrderInvestRelation goodsOrderInvestRelation : relationList) {
                Map<String, Object> relationMap = new HashMap<String, Object>();
                Investment investment = investmentService.get(goodsOrderInvestRelation.getInvestId());
                relationMap.put("id", goodsOrderInvestRelation.getId());
                relationMap.put("orderNo", investment.getOrderNo());
                relationMap.put("amount", goodsOrderInvestRelation.getAmount());

                list.add(relationMap);
            }
            Long count = goodsOrderInvestRelationService.getMapper().countByExample(example);

            int pageCount = 1;
            if (pageSize != null) {
                pageCount = calcPage(count.intValue(), pageSize);
            } else {
                pageSize = count.intValue();
            }

            map.put("list", list);
            map.put("count", count);
            map.put("curPage", curPage);
            map.put("pageSize", pageSize);
            map.put("pageCount", pageCount);

            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }

    }

    /**
     * @description 邀请新用户获得的总额
     * @author shuys
     * @date 2019/6/24
     * @param request
     * @param token
     * @param client
     * @param appVersion
     * @return com.goochou.p2b.app.model.AppResult
    */
    @RequestMapping(value = "/getInviteRecordTotalAmount", method = RequestMethod.GET)
    @ApiOperation(value = "邀请新用户获得的现金总额")
    @ResponseBody
    public AppResult getInviteRecordTotalAmount(
            HttpServletRequest request,
            @ApiParam("推荐人邀请码") @RequestParam String inviteCode,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("app版本") @RequestParam String appVersion) {
        try {
            Double result = Double.valueOf(0);
            if (StringUtils.isNotBlank(inviteCode)) {
                User userBy = userService.getByInviteCode(inviteCode); // 推荐人信息
                result = userService.getInvitRecordTotalAmount(userBy.getId());
            }
            return new AppResult(SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
    @RequestMapping(value = "/channel_regist/{channelNo}", method = RequestMethod.GET)
    @ApiOperation(value = "注册第一步发送验证码")
    public String channelRegist(Model model,HttpServletRequest request, HttpSession session,
                             @ApiParam("渠道编号") @PathVariable("channelNo") String channelNo,
                             @ApiParam("邀请码") @RequestParam(required = false) String inviteCode) {
        try {
            logger.info("用户注册==>channelNo=" + channelNo );
            
            AdvertisementChannelExample example=new AdvertisementChannelExample();
            example.createCriteria().andChannelNoEqualTo(channelNo);
            AdvertisementChannelMapper channelMapper=advertisementChannelService.getAdvertisementChannelMapper();
            List<AdvertisementChannel> channelList=channelMapper.selectByExample(example);
            if(channelList.size()!=1) {
            	return "error";
            }
            
            
            AdvertisementChannel advertisementChannel=channelList.get(0);
            advertisementChannel.setButtonText(advertisementChannel.getButtonText().replaceAll("'", "\\\\'"));
            advertisementChannel.setButtonText(advertisementChannel.getButtonText().replaceAll("\"", "\\\\\""));

            advertisementChannel.setGuizeText(advertisementChannel.getGuizeText().replaceAll("'", "\\\\'"));
            advertisementChannel.setGuizeText(advertisementChannel.getGuizeText().replaceAll("\"", "\\\\\""));

            if(advertisementChannel.getSuccessText()!=null) {
                advertisementChannel.setSuccessText(advertisementChannel.getSuccessText().replaceAll("'", "\\\\'"));
                advertisementChannel.setSuccessText(advertisementChannel.getSuccessText().replaceAll("\"", "\\\\\""));
            }

            if(advertisementChannel.getRedirectUrl() == null || advertisementChannel.getRedirectUrl().equals("")) {
                advertisementChannel.setRedirectUrl(ClientConstants.H5_URL);
            }

            if(advertisementChannel.getTopImageId()!=null) {
            	Upload upload = uploadService.get(advertisementChannel.getTopImageId());
                if(upload!=null) {
                	advertisementChannel.setTopImagePath(ClientConstants.ALIBABA_PATH + "upload/" + upload.getPath());
                }
            }
            
            model.addAttribute("channel", advertisementChannel);
            return "/user/channel_regist";
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return "error";
        }
		
    }
    
    @RequestMapping(value = "/alipayJump", method = RequestMethod.GET)
    @ApiOperation(value = "支付宝跳转页")
    public void alipayJump(HttpServletRequest request, HttpServletResponse response,
        @ApiParam("支付完成返回页面") @RequestParam String payResult,
        @ApiParam("orderNo") @RequestParam String orderNo,
        @ApiParam("支付内容") @RequestParam String subject) {
        try {
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET); 
            response.setCharacterEncoding("utf-8"); 
            //转义
            payResult = URLEncoder.encode(payResult);
            subject = URLEncoder.encode(subject);
            logger.info("subject="+subject);
            logger.info("payResult="+payResult);
            Recharge recharge =  rechargeService.getByOrder(orderNo);
            // 超时时间 可空
            String timeout_express="15m";
            // 销售产品码 必填
            String product_code="QUICK_WAP_WAY";
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //调用RSA签名方式
            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
            certAlipayRequest.setServerUrl(AlipayConfig.URL);
            certAlipayRequest.setAppId(AlipayConfig.APPID);
            certAlipayRequest.setPrivateKey(AlipayConfig.RSA_PRIVATE_KEY);
            certAlipayRequest.setFormat(AlipayConfig.FORMAT);
            certAlipayRequest.setCharset(AlipayConfig.CHARSET);
            certAlipayRequest.setSignType(AlipayConfig.SIGNTYPE);
            certAlipayRequest.setCertPath(AlipayConfig.CERT_PATH);
            certAlipayRequest.setAlipayPublicCertPath(AlipayConfig.ALIPAY_PUBLIC_CERT_PATH);
            certAlipayRequest.setRootCertPath(AlipayConfig.ROOT_CERT_PATH);
            DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
            AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

            // 封装请求支付信息
            AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
            model.setOutTradeNo(recharge.getOrderNo());
            model.setSubject(subject);
            model.setTotalAmount(String.valueOf(new Money(recharge.getAmount()).getAmount()));
            model.setBody(subject);
            model.setTimeoutExpress(timeout_express);
            model.setProductCode(product_code);
            model.setQuitUrl(payResult);
            alipay_request.setBizModel(model);
            // 设置异步通知地址
            alipay_request.setNotifyUrl(PayConstants.ADVITE_URL.replace("/backRecharge", "/alipay/notify"));
            // 设置同步地址
            alipay_request.setReturnUrl(payResult);
            // form表单生产
            String form = "";
            PrintWriter pw = null;
            try {
                pw = response.getWriter();
                // 调用SDK生成表单
                form = alipayClient.pageExecute(alipay_request).getBody();
                logger.info(form);
                pw.write(form);
                pw.flush();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }finally {
                if(null != pw) {
                    pw.close();
                }
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getUserInviteCode", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户invite code")
    @ResponseBody
    public AppResult getUserInviteCode(
            HttpServletRequest request,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("app版本") @RequestParam String appVersion,
            @ApiParam("token") @RequestParam String token
            ) {
        try {
            User user = userService.checkLogin(token);
            if (null == user) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            return new AppResult(SUCCESS, null, user.getInviteCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
    public static void main(String[] args) {
//        String str = "{\"sign\":\"\",\"tphtrxcrtime\":\"20190920105202\",\"tphtrxid\":0,\"trxflag\":\"\",\"trxsn\":\"0920105202405694\"}";
//        System.out.println(str.replaceAll("\\\"", "\""));

        System.out.println(String.format("test%.2f%%test", 0.02));
        double s = 29;
        System.out.println(String.valueOf(new Money(s).getAmount()));
    }
    
    @RequestMapping(value = "/channel/{dataSource}", method = RequestMethod.GET)
    @ApiOperation(value = "渠道注册落地页")
    @ResponseBody
    public AppResult channel(
        HttpServletRequest request,
        HttpSession session,
        @PathVariable String dataSource) {
        logger.info("dataSource="+dataSource);
        return null;
    }
    
   
}
