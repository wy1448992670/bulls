package com.goochou.p2b.app.controller;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.LotteryBullsTypeEnum;
import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.client.UserCenterClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;
import com.goochou.p2b.hessian.user.UserRegisterRequest;
import com.goochou.p2b.hessian.user.UserResponse;
import com.goochou.p2b.model.*;
import com.goochou.p2b.service.ActivityBlessingCardRecordService;
import com.goochou.p2b.service.ActivityBlessingChanceRecordService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.service.memcached.MemcachedManagerImpl;
import com.goochou.p2b.utils.AESUtil;
import com.goochou.p2b.utils.CommonUtils;
import com.goochou.p2b.utils.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.goochou.p2b.app.controller.BaseController.*;

/**
 * @author: huangsj
 * @Date: 2019/12/23 16:33
 * @Description: 春节集卡活动
 */

@Controller
@RequestMapping(value = "newyear")
@Api(value = "活动中心")
public class NewYearActivityController extends BaseController {

    private static final Logger logger = Logger.getLogger(NewYearActivityController.class);

    final String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    
    @Resource
    protected UserService userService;
    @Autowired
    private ActivityBlessingCardRecordService cardRecordService;
    @Autowired
    private ActivityBlessingChanceRecordService chanceRecordService;
    @Resource
    protected MemcachedManager memcachedManager;

    /**
     * 获取加密后的赠送的卡片数据lottery
     * @param cardId
     * @param token
     * @param client
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/getGiveCardNum", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取加密后的赠送的卡片数据")
    public AppResult cardmanage(@ApiParam("卡片编号") @RequestParam Integer cardId,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("App版本号") @RequestParam String appVersion) {

        Map<String, Object> returnMap = new HashMap<String, Object>();

        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Integer userId = user.getId();
            if (null == userId) {
                return new AppResult(FAILED, "登录超时");
            }

            //查询卡片信息
            ActivityBlessingCardRecordExample example = new ActivityBlessingCardRecordExample();
            ActivityBlessingCardRecordExample.Criteria c = example.createCriteria();
            c.andIdEqualTo(cardId);
            c.andUserIdEqualTo(userId);

            List<ActivityBlessingCardRecord> cards = cardRecordService.getMapper().selectByExample(example);
            if (cards != null && cards.size() == 1) {
                ActivityBlessingCardRecord card = cards.get(0);

                if(card.getType() == 6){
                    return new AppResult(FAILED, "合成卡不能赠送");
                }

                if(card.getIsUsed() || card.getIsTransfer()){
                    return new AppResult(FAILED, "此卡已使用");
                }

                returnMap.put("cardnum", AESUtil.encrypt(card.getCardNo()+"_"+userId));
                return new AppResult(SUCCESS, returnMap);

            }else{
                return new AppResult(FAILED, "卡片所属不一致");
            }


        }catch (Exception e){

        }

        return new AppResult(FAILED, returnMap);
    }

    
    private boolean checkPhone(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
        return false;
    }

    @RequestMapping(value = "/getLuckyCardNowStep1", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "立即领取福卡--第一步：发送验证码")
    public AppResult getLuckyCardNowStepOne(HttpServletRequest request, HttpSession session,
                                @ApiParam("app版本号") @RequestParam String appVersion,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("capImgCode") @RequestParam(required = false) String capImgCode,
                                @ApiParam("手机号") @RequestParam String phone) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            logger.info("用户注册==>phone=" + phone + ";capImgCode=" + capImgCode + "client=" + client);
            if (!checkPhone(phone)) {
                return new AppResult(FAILED, "请填入正确的手机号");
            }
            // 发送验证码
            String verifyCode = generateVerifyCode(6);
//            if (null != session.getAttribute("lastSendTime")) {
//                long diffTime = System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime")).getTime();
//                if (diffTime <= Constants.ALLOW_SECOUND * 1000) {
//                    int secound = Constants.ALLOW_SECOUND-Integer.valueOf((diffTime/1000)+"");
//                    return new AppResult(FAILED, "请一分钟后再发送验证码", secound);
//                }
//            }
            String cacheKey = String.format(Constants.NEW_YEAR_ACTIVITY_GET_CARD, phone);
            Map map = null;
            try {
                map = (Map) memcachedManager.get(cacheKey);
            } catch (Exception e) {
                logger.error("memcache error:" + e.getMessage(), e);
            }
            if (map != null && map.get("lastSendTime") != null) {
                long diffTime = System.currentTimeMillis() - ((Date) map.get("lastSendTime")).getTime();
                if (diffTime <= Constants.ALLOW_SECOUND * 1000) {
                    int secound = Constants.ALLOW_SECOUND-Integer.valueOf((diffTime/1000)+"");
                    return new AppResult(FAILED, "请一分钟后再发送验证码", secound);
                }
            }
            if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
                verifyCode = "000000";
            } else {
                logger.info("短信验证码："+verifyCode);
                SendMessageRequest smr = new SendMessageRequest();
                smr.setContent(DictConstants.VALIDATE_CODE.replaceAll("\\{code}", verifyCode));
                smr.setPhone(phone);
                ServiceMessage msg = new ServiceMessage("message.send", smr);
                SendMessageResponse result = (SendMessageResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if (!result.isSuccess()) {
                    return new AppResult(FAILED, "发送验证码失败");
                }
            }
            //String username = "ap" + System.currentTimeMillis();
            String username = "";
//            session.setAttribute("verifyPhone", phone);
//            session.setAttribute("username", username);
//            session.setAttribute("verifyCode", verifyCode);
//            session.setAttribute("lastSendTime", new Date());
//            session.setAttribute("source", client);
            map = new HashMap();
            map.put("username", username);
            map.put("verifyCode", verifyCode);
            map.put("verifyPhone", phone);
            map.put("lastSendTime", new Date());
            map.put("source", client);
            try {
                memcachedManager.addOrReplace(cacheKey, map, Constants.MOBILE_VERIFY_CODE_EXPIRE);
            } catch (Exception e) {
                logger.error("memcache error:" + e.getMessage(), e);
            }
            
            returnMap.put("step", 1);
            return new AppResult(SUCCESS, returnMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    @RequestMapping(value = "/getLuckyCardNowStep2", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "立即领取福卡--第二步：领取or注册并领取")
    public AppResult getLuckyCardNowStepTwo(HttpServletRequest request, HttpSession session,
                                @ApiParam("app版本号") @RequestParam String appVersion,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("手机号") @RequestParam String phone,
                                @ApiParam("手机验证码") @RequestParam String verifyCode,
                                @ApiParam("卡片编号") @RequestParam String cardNo) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String cacheKey = String.format(Constants.NEW_YEAR_ACTIVITY_GET_CARD, phone);
        try {
            if (!checkPhone(phone)) {
                return new AppResult(FAILED, "请填入正确的手机号");
            }
            Map map = null;
            try {
                map = (Map) memcachedManager.get(cacheKey);
            } catch (Exception e) {
                logger.error("memcache error:" + e.getMessage(), e);
            }
            if (map == null) {
                map = new HashMap();
            }
//            String sessionPhone = (String) session.getAttribute("verifyPhone");
//            String username = (String) session.getAttribute("username");
//            Date lastSendTime = (Date) session.getAttribute("lastSendTime");
//            String sessionVerifyCode = (String) session.getAttribute("verifyCode");
            String cachePhone = (String) map.get("verifyPhone");
            String username = (String) map.get("username");
            Date lastSendTime = (Date) map.get("lastSendTime");
            String cacheVerifyCode = (String) map.get("verifyCode");
            if (lastSendTime != null && System.currentTimeMillis() - lastSendTime.getTime() >= 15 * 60 * 1000) {
                session.removeAttribute("verifyCode");//有效期为15分钟
            }
            if (StringUtils.isBlank(cachePhone) 
                    || StringUtils.isBlank(cacheVerifyCode) || lastSendTime == null) {
                return new AppResult(FAILED, "您的验证码已过期，请重新发送验证码");
            }
            if (!cacheVerifyCode.equals(verifyCode)) {
                return new AppResult(FAILED, "验证码错误");
            }
            // 解密卡号  真实卡号_用户id
            String decryptStr = AESUtil.decrypt(cardNo);
            Integer cardSourceUserId = Integer.valueOf(decryptStr.split("_")[1]);
            User cardSourceUser = userService.queryById(cardSourceUserId);
            if (cardSourceUser == null) {
                return new AppResult(FAILED, "卡片所属用户未知");
            }
            String realCardNo = decryptStr.split("_")[0];
            ActivityBlessingCardRecord activityBlessingCardRecord = cardRecordService.getByUserIdAndCardNo(cardSourceUserId, realCardNo);
            if (activityBlessingCardRecord == null) {
                return new AppResult(FAILED, "卡片不存在");
            }
            User user = userService.getByPhone(phone);

            LotteryBullsTypeEnum bullsTypeEnum = LotteryBullsTypeEnum.getValueByType(activityBlessingCardRecord.getType());

            Date now = new Date();
            String buttonText = "", successContent = "";
            // 注册成功标识(本系统中无账号，先注册）
            boolean successFlag = false;
            if (user != null) {
                if (user.getId().equals(cardSourceUser.getId())) {
                    return new AppResult(FAILED, "不能领取自己分享的牛卡");
                }
                if (activityBlessingCardRecord.getIsTransfer() == true) {
                    returnMap.put("title", "领取失败");
                    returnMap.put("content", "有人比你手速更快，把牛卡抢走了。");
                    returnMap.put("buttonText", "确认");
                    returnMap.put("code", "3");
                    return new AppResult(SUCCESS, "", returnMap);
                } else {
                    successFlag = true;
                    // 两个%为string.format方法转义
                    successContent = "你的手速已经超过99%%的小伙伴了，一张“%s”已经到手~";
                    returnMap.put("buttonText", "确认");
                    returnMap.put("code", "1");
                }
            } else {
                // 如果用户账号不存在，先注册
                String password = "Bf" +phone.substring(phone.length() - 6); // 默认密码为 Bf + 用户手机号后六位
                //interfaces.register
                UserRegisterRequest reqUser = new UserRegisterRequest();
                reqUser.setClient(client);
                reqUser.setInviteByCode(cardSourceUser.getInviteCode());
                reqUser.setPassword(password);
                reqUser.setPhone(phone);
                reqUser.setRegisterIp(getIpAddr(request));
                reqUser.setUsername(username);
                // reqUser.setDataSource(dataSource);
                ServiceMessage serviceMsg = new ServiceMessage("interfaces.register", reqUser);
                UserResponse registerResult = (UserResponse) UserCenterClient.getInstance()
                        .setServiceMessage(serviceMsg).send();

                if (registerResult.isSuccess()) {
                    // 注册成功
                    user = registerResult.getUser();
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
                    
                    if (activityBlessingCardRecord.getIsTransfer() == true) {
                        returnMap.put("title", "有人比你手速更快，把牛卡抢走了。");
                        returnMap.put("content", "下载奔富牧业App自己抽牛卡吧，新人有光环，手气更棒喔~");
                        returnMap.put("buttonText", "前往下载");
                        returnMap.put("code", "4");
                        return new AppResult(SUCCESS, returnMap);
                    } else {
                        successFlag = true;
                        // 两个%为string.format方法转义
                        successContent = "你手速已经超过99%%的小伙伴了，一张“%s”已到手~下载奔富牧业App再继续抽牛卡吧，新人有光环，手气更棒喔~";
                        returnMap.put("buttonText", "前往下载");
                        returnMap.put("code", "2");
                    }
                } else {
                    // 注册失败
                    return new AppResult(FAILED, MESSAGE_EXCEPTION);
                }
            }
            if (successFlag) {
                successContent = String.format(successContent, bullsTypeEnum.getDescription());
                // 邀请人福卡被领走
                activityBlessingCardRecord.setIsTransfer(true);
                activityBlessingCardRecord.setChanceRecordId(null);
                cardRecordService.updateByExampleSelectiveForVersion(activityBlessingCardRecord);
                // 注册人领取福卡
                ActivityBlessingCardRecord cardRecordNew = new ActivityBlessingCardRecord();
                cardRecordNew.setCardNo(activityBlessingCardRecord.getCardNo());
                cardRecordNew.setParentId(activityBlessingCardRecord.getId());
                cardRecordNew.setAmount(activityBlessingCardRecord.getAmount());
                cardRecordNew.setType(activityBlessingCardRecord.getType());
                cardRecordNew.setCreateDate(now);
                cardRecordNew.setUserId(user.getId());
                cardRecordNew.setIsTransfer(false);
                cardRecordNew.setVersion(0);
                cardRecordService.getMapper().insertSelective(cardRecordNew);
                returnMap.put("title", "领取成功");
                returnMap.put("content", successContent);
                // 领取卡片成功后清空短信验证码发送时间
//                session.setAttribute("lastSendTime", null);
                return new AppResult(SUCCESS, returnMap);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                // 清除验证信息
                memcachedManager.delete(cacheKey);
            } catch (Exception e) {
                logger.error("memcache error:" + e.getMessage(), e);
            }
        }
        return new AppResult(FAILED, MESSAGE_EXCEPTION);
    }
    
}
