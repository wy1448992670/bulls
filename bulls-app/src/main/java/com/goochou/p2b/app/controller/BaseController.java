package com.goochou.p2b.app.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.hessian.ErrorResponse;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;
import com.goochou.p2b.hessian.transaction.OrderResponse;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.model.AppVersionContent;
import com.goochou.p2b.model.CodeLimit;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.AppVersionService;
import com.goochou.p2b.service.CodeLimitService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.StringUtil;


@SuppressWarnings("unused")
public abstract class BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class);

    public static String STATUS = "status";
    public static String ERROR = "error";
    public static String MESSAGE = "message";

    public static String SUCCESS = "1";
    public static String FAILED = "0";
    public static String CONFIRM = "998";//
    public static String ALERT = "999";//级别，弹出提示框
    public static String NO_LOGIN = "2";
    public static String SIGN_NOT_ENOUGH = "3"; // 签到金额不足
    public static String CARD_INVALID = "4";        //银行卡无效
    public static String CURRENT_BANLANCE_INVALID = "5"; //活期在投余额不足
    public static String UPDATE = Constants.YES;//是否需要更新的标记
    public static String NOUPDATE = Constants.NO;
    public static String MISS_STATUS = "-1";    //缺少参数

    public static final String MESSAGE_NO_LOGIN = "您还没有登录";
    public static final String MESSAGE_EXCEPTION = "您的网络好像有点问题";
    public static final String VERSION_LOW = "您的版本过低，请更新！";
    public static final String MISS_EXCEPTION = "缺少参数";
    public static final String PARAM_ERROR = "参数有误";
    public static final String SIGN_ERROR = "签名失败";
    public static final String SUCCESS_MSG = "success";


    /**
     *
     */
    public static int PAGE_SIZE_THREE = 3;
    public static int PAGE_SIZE_FIVE = 5;
    public static int PAGE_SIZE_TEN = 10;

    /**
     * 银行卡 规则类型 ： 排除
     */
    public final static int RULE_TYPE_EXCLUDE = 0;
    /**
     * 银行卡 规则类型 ： 包含
     */
    public final static int RULE_TYPE_INCLUDE = 1;
    /**
     * 银行卡 规则类型 ： 触发
     */
    public final static int RULE_TYPE_TRIGGER = 2;

    protected static class Status {

        public static String OK = "ok";
        public static String HAS_SEND = "has_send";
        public static String FAILED = "failed";
        public static String FAILED_LOGIN = "failed_login";
        public static String FAILED_LOCKED = "failed_locked";
        public static String NOLOGIN = "nologin";
        public static String INVALID_PARAMS = "invalid_params";
        public static String INVALID_USERNAME = "invalid_username";
        public static String INVALID_PASSWORD = "invalid_password";
        public static String INVALID_PHONE = "invalid_phone";
        public static String EXISTS_USERNAME = "exists_username";
        public static String EXISTS_PHONE = "exists_phone";
        public static String EMPTY_VCODE = "empty_vcode";
        public static String WRONG_VCODE = "wrong_vcode";
        public static String WRONG_VERIFY_CODE = "wrong_verify_code";
        public static String WRONG_OLDPASSWORD = "wrong_oldpassword";
        public static String NO_VERIFY_CODE = "no_verify_code";
        public static String EXISTS = "exists";
        public static String OTHERS = "others";
        public static String SAME_WITH_PASSWORD = "same_with_password";
        public static String SAME_WITH_PAYPASSWORD = "same_with_paypassword";
        public static String WRONG_OLDSECPROBLEM = "wrong_oldsecproblem";
        public static String NOT_ENOUGH_AMOUNT = "not_enough_amount";
        public static String UNEXPECTED_OPERATION = "unexpected_operation";
        public static String WRONG_PAYPASSWORD = "wrong_paypassword";
        public static String NOT_A_DEBT = "not_a_debt";
    }

    private static final String[] HEADERS_TO_TRY = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"};

    @Resource
    protected UserService userService;
    @Resource
    private CodeLimitService codeLimitService;
    @Resource
    private AppVersionService appVersionService;

    public static Integer calcPage(Integer total, Integer limit) {
        return total == 0 ? 1 : (total / limit + ((total % limit) > 0 ? 1 : 0));
    }

    protected static class MessageProperty {

        public static String LEFT_COUNT = "left_count";
        public static String UNLOCK_TIME = "unlock_time";
    }

    @ExceptionHandler
    @ResponseBody
    public AppResult exp(HttpServletRequest request, Exception e) {
        logger.error(e);
        e.printStackTrace();
        return new AppResult(FAILED, MESSAGE_EXCEPTION);
    }

    protected User getLoginUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    /**
     * 获取登录的用户id,未登录状态userId是---0
     */
    protected int getUserId(HttpSession session) {
        User user = (User) session.getAttribute("user");
        int userId = 0;
        if (user != null) {
            userId = user.getId();
        }
        return userId;
    }

    protected String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        return basePath;
    }

    protected String generateVerifyCode(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 通用的发送手机验证码接口
     *
     * @param phone 需要发送验证码的手机号(已经注册成功的用户调用!)
     */
//    @RequestMapping(value = "/sendcode", method = RequestMethod.GET)
//    @ResponseBody
    public AppResult sendcode(HttpSession session,
        @ApiParam("手机号码") @RequestParam String phone,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("类型0默认短信验证码1语音验证码") @RequestParam(required = false) Integer type)
        throws Exception {
        Integer count = codeLimitService.listCountByPhone(phone);
        if (count >= 10) {
            return new AppResult(FAILED, "今天发送短信已经超过10条");
        }
        if (!userService.checkPhone(phone)) {
            return new AppResult(FAILED, "请输入正确的手机号");
        }
//        if (!RegularUtils.checkPhone(phone, RegularUtils.REGEX_MOBILE)) {
//            return new AppResult(FAILED, "电话号段异常！");
//        }
        AppResult appResult = null;
        String verifyCode = null;
        Object o = session.getAttribute("verifyCode");
        if (o != null) {
            if (System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime()
                <= 60 * 1000) {
                appResult = new AppResult(FAILED, "请一分钟后再尝试发送");
                return appResult;
            }
            if (System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime() >= 15 * 60 * 1000) {
            	session.removeAttribute("verifyCode");//有效期为15分钟
            }
        }

        verifyCode = generateVerifyCode(6);
        if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
            verifyCode = "000000";
        }
        logger.info("===========" + phone + ":" + verifyCode + "============");
        Integer ret = null;
        if (type == null || type.equals(0)) {
        	  SendMessageRequest smr = new SendMessageRequest();
              smr.setContent(DictConstants.VALIDATE_CODE.replaceAll("\\{code}", verifyCode));
              smr.setPhone(phone);
              ServiceMessage msg = new ServiceMessage("message.send", smr);
              SendMessageResponse result = (SendMessageResponse) OpenApiClient.getInstance()
                      .setServiceMessage(msg).send();
              if (result.isSuccess()) {
                  session.setAttribute("verifyPhone", phone);
                  session.setAttribute("verifyCode", verifyCode);
                  session.setAttribute("lastSendTime", new Date()); 
                  CodeLimit limit = new CodeLimit();
                  limit.setCode(verifyCode);
                  limit.setCreateTime(new Date());
                  limit.setPhone(phone);
                  codeLimitService.save(limit);
                  appResult = new AppResult(SUCCESS, "验证码已发送，请查收");
                  return new AppResult(SUCCESS, appResult);
              } else {
                  return new AppResult(FAILED, "验证码发送失败，请稍后重试。");
              }
        } else if (type.equals(1)) {
            //ret = YunSendMessage.send(new ShortMessage(phone, verifyCode, 1)); //语音验证码
        }
       
        return appResult;
    }

    /**
     * 校验图形验证码
     */
//    @RequestMapping(value = "/checkCapImgCode", method = RequestMethod.GET)
//    @ResponseBody
    public Map<String, Object> checkCapImgCode(@ApiParam("图形验证码key") @RequestParam String key,
        @ApiParam("验证码") @RequestParam(required = false) String capImgCode,
        HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (null == capImgCode || "".equals(capImgCode)) {
            map.put(STATUS, Status.FAILED);
            map.put("msg", "请输入图形验证码");
            return map;
        }
        Object o = memcachedManager.get(key);
        logger.info("===============" + capImgCode + "======" + memcachedManager.get(key) + "===============");
        capImgCode = capImgCode.replaceAll("\\s*", "");
        if (StringUtil.isNull(capImgCode) || com.goochou.p2b.utils.StringUtils
            .isBlank(capImgCode)) {
            map.put(STATUS, Status.FAILED);
            map.put("msg", "请输入图形验证码");
            return map;
        }
        String sessionVerifyCode = (String) o;
        if (!capImgCode.equalsIgnoreCase(sessionVerifyCode)) {
            map.put(STATUS, Status.FAILED);
            map.put("msg", "请输入正确的图形验证码");
            logger.info("===============图形验证码错误=====================");
            return map;
        }
        map.put(STATUS, Status.OK);
        map.put("msg", "验证成功");
        return map;
    }


    public int getVersion(String version) {
        String versions[] = version.split("\\.");
        version = "";
        for (int i = 0; i < versions.length; i++) {
            version += versions[i];
        }
        return Integer.parseInt(version);
    }

    public static void main(String[] args) {
        String version = "2.1.2";
        String versions[] = version.split("\\.");
        version = "";
        for (int i = 0; i < versions.length; i++) {
            version += versions[i];
        }
        System.out.println(version);
    }

    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * Author:xinjiang 获得服务器文件上传路径
     */
    public String getUploadPath(HttpServletRequest request) {
        return request.getRequestURL().toString().replaceAll(request.getRequestURI().toString(), "")
            + "/upload/";
    }

    public String checkVersion(String client, String appVersion) {
        if (StringUtils.isBlank(appVersion)) {//版本号是否为空
            return UPDATE;
        }
        if (client == null) {
            return UPDATE;
        }
        String update = NOUPDATE;
        if (ClientEnum.ANDROID.getFeatureName().equals(client)) {//Android
            AppVersionContent aVersion = appVersionService
                .getAppVersionContent(ClientEnum.ANDROID.getFeatureName());
            if (aVersion != null) {
                if (getVersion(appVersion) < getVersion(aVersion.getVersion())) {
                    update = UPDATE;
                }
            }
        } else if (ClientEnum.IOS.getFeatureName().equals(client)) {// ios
            AppVersionContent aVersion = appVersionService
                .getAppVersionContent(ClientEnum.IOS.getFeatureName());
            if (aVersion != null) {
                if (getVersion(appVersion) < getVersion(aVersion.getVersion())) {
                    update = UPDATE;
                }
            }
        } else {
            update = UPDATE;
        }
        return update;
    }

    public static String getWapUrl() {
        String url = "http://m2.xinjucai.com";
        if (TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)) {
            url = "http://test.xinjucai.com:9999";
        }

        return url;
    }

    public static String getStaticUrl() {
        String url = "http://static.xinjucai.com";
        if (TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)) {
            url = "http://test.xinjucai.com";
        }

        return url;
    }

    @Resource
    protected MemcachedManager memcachedManager;

    public MemcachedManager getCache() {
        return memcachedManager;
    }

    public void setCache(MemcachedManager memcachedManager) {
        this.memcachedManager = memcachedManager;
    }

    /**
     * <p>获取缓存</p>
     */
    @SuppressWarnings("rawtypes")
    public Object getCacheValue(String key) {
        Object obj = null;
        Map map = (Map) memcachedManager.get(com.goochou.p2b.constant.Constants.DICTS);
        obj = map.get(key);
        return obj;
    }


    /**
     * <p>获取键值对形式的值</p>
     */
    @SuppressWarnings("unchecked")
    public String getCacheKeyValue(String key) {
        String val = null;
        if (getCacheValue(key) != null) {
            Map<String, String> map = (HashMap<String, String>) getCacheValue(key);
            Set<String> set = map.keySet();
            val = map.get(set.toArray()[0]);
        }
        return val;
    }

    public AppResult getHessianResult(Response response, String successMsg, String errorMsg) {

        if (response instanceof ErrorResponse) {
            ErrorResponse result = (ErrorResponse) response;
            return new AppResult(FAILED, "请求接口出现问题", response.getMsg());
        } else {
            if (response.isSuccess()) {
                return new AppResult(SUCCESS, successMsg, response);
            } else {
                return new AppResult(FAILED, errorMsg, response.getErrorMsg());
            }
        }
    }

}