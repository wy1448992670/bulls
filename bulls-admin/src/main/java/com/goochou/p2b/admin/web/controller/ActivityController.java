package com.goochou.p2b.admin.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.LotteryGift;
import com.goochou.p2b.model.LotteryRecord;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.LotteryCountService;
import com.goochou.p2b.service.LotteryGiftService;
import com.goochou.p2b.service.LotteryRecordService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.AjaxUtil;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.StringUtils;


/**
 * 
* @ClassName: ActivityController 
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivityController extends BaseController {

	private static final Logger logger = Logger.getLogger(ActivityController.class);

    @Resource
    private ActivityService activityService;
    @Resource
    private UploadService uploadService;
    @Resource
    private LotteryRecordService lotteryRecordService;
    @Resource
    private LotteryCountService lotteryCountService;
    @Resource
    private LotteryGiftService lotteryGiftService;


    /**
     * @param model
     * @param id
     * @return
     * @Description: 活动详情转发器
     * @author 王信
     * @date 2016年1月5日 上午9:42:39
     */
    @RequestMapping(value = "/activityForward", method = RequestMethod.GET)
    public String activityForward(Model model, Integer id) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("activity:forward");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        if (id != 0 || id != null) {
            Map<String, Object> ac = activityService.selectActivityById(id);
            if ("年末圣诞九宫格抽奖活动".equals(ac.get("name"))) {
                return "redirect:/activity/chrismasList";
            }
            if ("加息封侯 破10+0.5之战".equals(ac.get("name"))) {
                return "redirect:/activity/rateBreakList";
            }
            if ("问卷调查".equals(ac.get("name"))) {
                return "redirect:/activity/questionnaireList";
            }
            if (ActivityConstant.REGULAR_INVESTMENT_GIVE_NOOB.equals(ac.get("name"))) { //新人盛宴，16000体验金+0.3%加息特权
                return "redirect:/activity/noviceActivityList";
            }
            if (ActivityConstant.REGULAR_90_INVESTMENT_0903.equals(ac.get("name"))) { //90天。
                return "redirect:/activity/appleActivityList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_180_INVESTMENT_0903.equals(ac.get("name"))) { //90天。
                return "redirect:/activity/appleActivityList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_INVESTMENT_WEEKLY.equals(ac.get("name"))) { //week活动。
                return "redirect:/activity/weekActivityList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_INVESTMENT_HONGBAO.equals(ac.get("name"))) { //week活动。
                return "redirect:/activity/hongbaoActivityList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_INVESTMENT_JINGDONG.equals(ac.get("name"))) { //京东卡活动。
                return "redirect:/activity/jingdongActivityList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_INVESTMENT_GRAB.equals(ac.get("name"))) { //抢标活动
                return "redirect:/activity/projectAwardList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_AWARD_GOLD.equals(ac.get("name"))) { //国庆黄金活动
                return "redirect:/activity/getAwardGoldList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_AWARD_CANON.equals(ac.get("name"))) { //疯狂的礼物！投资即送，奖品团团赚！
                return "redirect:/activity/getAwardCanonList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_AWARD_SINGLES_DAY.equals(ac.get("name"))) { //双十一活动
                return "redirect:/activity/singlesDayList?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_AWARD_THANKSGING_DAY.equals(ac.get("name"))) { //感恩节活动
                return "redirect:/report/thanksgivingReport?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_AWARD_DOULE_EGGS.equals(ac.get("name"))) { //双旦活动
                return "redirect:/report/doubleEggsActivity?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_AWARD_NEW_YEAR.equals(ac.get("name"))) { //新年活动
                return "redirect:/report/newYearActivity?activityId=" + id;
            }
            if (ActivityConstant.REGULAR_AWARD_VALENTINES_DAY.equals(ac.get("name"))) { //情人节活动
                return "redirect:/activity/valentinesDaysList?activityId=" + id;
            }
            if (ActivityConstant.TWO_YEARS_VALENTINES_DAY.equals(ac.get("name"))) { //两周年新客户
                return "redirect:/activity/newAnniversary?activityId=" + id;
            }
            if (ActivityConstant.TWO_YEARS_THANKS_VALENTINES_DAY.equals(ac.get("name"))) { //两周年老客
                return "redirect:/activity/twoYearsThanksActivity?activityId=" + id;
            }
        }
        return "redirect:/activity/getAwardGoldList?activityId=" + id;
    }

    /**
     * @param model
     * @param page
     * @return
     * @Description: 加息封侯 破10+0.5之战   活动详情查询
     * @author 王信
     * @date 2016年1月5日 上午9:56:25
     */
    @RequestMapping(value = "/rateBreakList", method = RequestMethod.GET)
    public String rateBreakList(Model model, @RequestParam(required = false) Integer page) {
        Integer limit = 30;
        if (page == null || page == 0) {
            page = 1;
        }
        List<Map<String, Object>> list = activityService.rateBreakList(null, (page - 1) * limit, limit);
        Integer count = activityService.rateBreakListCount(null);
        int pages = calcPage(count, limit);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("pages", pages);
        return "activity/rateBreakList";
    }


    /**
     * 查询领取特权的状况
     *
     * @param model
     * @return
     * @author 王信
     * @Create Date: 2015年12月24日下午4:14:04
     */
    @RequestMapping(value = "/selectRateBreak", method = RequestMethod.GET)
    public String selectRateBreak(Model model, String keyword) {
        if (keyword == null) {
            return "redirect:/activity/rateBreakList";
        }
        List<Map<String, Object>> list = activityService.rateBreakList(keyword, null, null);
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", 1);
        model.addAttribute("pages", 1);
        return "activity/rateBreakList";
    }


    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public String sendMessage(Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("activity:sendMessage");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        return "activity/add";
    }

    /**
     * 新增活动
     *
     * @param model
     * @param name
     * @param file
     * @param descript
     * @param startTime
     * @param endTime
     * @param link
     * @param status
     * @return
     * @Title: ActivityController.java
     * @Package com.goochou.p2b.admin.web.controller
     * @Description:
     * @author sxy
     */
    @RequestMapping(value = "/addActivity", method = RequestMethod.POST)
    public String sendMessage(Model model, String name, String channel, @RequestParam("file") MultipartFile file, String descript, Date startTime, Date endTime, String link, Integer status) {
        Activity ac = new Activity();
        ac.setName(name);
        ac.setLink(link);
        ac.setStartTime(startTime);
        ac.setEndTime(endTime);
        ac.setDescript(descript);
        ac.setStatus(status);
        if (!file.isEmpty()) {
            Map<String, Object> result = uploadService.save(file, 8, null);
            ac.setUploadId(Integer.valueOf(result.get("id").toString()));
        }
        activityService.save(ac);
        return "redirect:/activity/list";
    }

    /**
     * 活动列表
     * @author sxy
     * @param model
     * @param page
     * @param status
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, Integer page, Integer status) {
        if (page == null || page < 1) {
            page = 1;
        }

        List<Map<String, Object>> list = activityService.lists((page - 1) * 20, 20, status);
        Long count = activityService.queryCount(null);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("status", status);
        model.addAttribute("pages", calcPage(count.intValue(), 20));
        return "activity/list";
    }


    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String sendMessage(Model model, Integer id) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("activity:detail");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        final String picUrl = ClientConstants.ALIBABA_PATH + "upload/";
        Map<String, Object> ac = activityService.selectActivityById(id);
        String path = (String) ac.get("path");
        String imgUrl = "";
        if (org.apache.commons.lang.StringUtils.isNotBlank(path)) {
            imgUrl = picUrl + path;
        }
        ac.put("imgUrl", imgUrl);
        model.addAttribute("activity", ac);
        return "activity/edit";
    }

    @RequestMapping(value = "/pushmessage", method = RequestMethod.GET)
    public String pushmessage(Model model, Integer page) {
        return "activity/pushmessage";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model, String name, @RequestParam("file") MultipartFile file, Integer id, String channel, String descript, Date startTime, Date endTime, String link, Integer status, Integer surface) {
        Activity ac = new Activity();
        if (surface != null) {//用户没有修改过图片
            ac.setUploadId(surface);//用原有的id  关联图片
        }
        if (!file.isEmpty()) {//用户有更新过图片
            Map<String, Object> result = uploadService.save(file, 8, null);
            ac.setUploadId(Integer.valueOf(result.get("id").toString()));
        }
        ac.setId(id);
        ac.setName(name);
        ac.setLink(link);
        ac.setStartTime(startTime);
        ac.setEndTime(endTime);
        ac.setDescript(descript);
        ac.setStatus(status);
        activityService.update(ac);
        return "redirect:/activity/list";
    }

    /**
     * 圣诞抽奖活动
     *
     * @param model
     * @return
     * @author
     * @date 2015年12月7日
     * @parameter
     */
    @RequestMapping(value = "/chrismasList", method = RequestMethod.GET)
    public String chrismasList(Model model,
                               @RequestParam(required = false) String status,
                               @RequestParam(required = false) Date date,
                               @RequestParam(required = false) Integer activityId,
                               @RequestParam(required = false) Integer orderId,
                               @RequestParam(required = false) Integer giftId,
                               @RequestParam(required = false) String account,
                               @RequestParam(required = false) Integer page) {
        if (StringUtils.isEmpty(status)) {
            status = null;
        }
        if (StringUtils.isEmpty(account)) {
            account = null;
        } else {
            account = account.replaceAll("\\s*", "");
            if (StringUtils.isEmpty(account)) {
                account = null;
            }
        }
        if (orderId == null || orderId == 0) {
            orderId = null;
        }
        if (activityId == null || activityId == 0) {
            activityId = null;
        }
        if (page == null) {
            page = 1;
        }
        int limit = ConstantsAdmin.PAGE_LIMIT5;
        List<Map<String, Object>> list = lotteryRecordService.queryAll(status, date, orderId, activityId, giftId, account, (page - 1) * limit, limit);
        Integer count = lotteryRecordService.queryCount(status, date, orderId, activityId, giftId, account);
        int pages = calcPage(count, limit);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("pages", pages);
        model.addAttribute("status", status);
        model.addAttribute("date", date);
        model.addAttribute("orderId", orderId);
        model.addAttribute("account", account);
        model.addAttribute("giftId", giftId);
        model.addAttribute("activityId", activityId);
        return "activity/chrismasList";
    }

    /**
     * 跳转奖品信息
     *
     * @param model
     * @param id
     * @return
     * @author
     * @date 2015年12月7日
     * @parameter
     */
    @RequestMapping(value = "/editChrismasLottery", method = RequestMethod.GET)
    public String editChrismasLottery(Model model, Integer id) {
        Map<String, Object> map = lotteryRecordService.queryDetailByPrimaryKey(id);
        model.addAttribute("map", map);
        return "activity/editChrismasLottery";
    }

    /**
     * 编辑奖品内容
     *
     * @param model
     * @param id
     * @return
     * @author
     * @date 2015年12月7日
     * @parameter
     */
    @RequestMapping(value = "/updateChrismasLottery", method = RequestMethod.POST)
    public String updateChrismasLottery(Model model, Integer id, Integer status,
                                        String postName, String postPhone,
                                        String postAddress,
                                        Integer postCode,
                                        Integer trackType,
                                        String trackNo) {

        try {
            LotteryRecord record = lotteryRecordService.queryByPrimaryKey(id);
            record.setStatus(status);
            record.setTrackType(trackType);
            record.setTrackNo(trackNo);
            if (record.getGiftId() != 3 && record.getGiftId() != 4 && record.getGiftId() != 10) {
            } else {
                lotteryRecordService.updateLotteryRecord(record, null);
            }
        } catch (Exception e) {
            logger.error("保存地址失败", e);
        }
        return "redirect:/activity/chrismasList";
    }


    /**
     * 查询用户抽奖次数
     *
     * @param model
     * @return
     * @author 王信
     * @Create Date: 2015年12月24日下午4:14:04
     */
    @RequestMapping(value = "/selectChrismasLotteryCount", method = RequestMethod.GET)
    public String selectChrismasLotteryCount(Model model, String phone) {
        if (phone == null || phone.length() != 11) {
            return "redirect:/activity/chrismasList";
        }
        Map<String, Object> map = lotteryCountService.selectChrismasLotteryCount(phone);
        model.addAttribute("map", map);
        model.addAttribute("phone", phone);
        return "activity/chrismasCount";
    }

    /**
     * 获取所有活动
     *
     * @param
     * @author 刘源
     * @date 2016/4/21
     */
    @RequestMapping(value = "getActivityTitle", method = RequestMethod.GET)
    public void getActivityTitle(HttpServletResponse response) {
        try {
            List<Activity> list = activityService.selectAll();
            if (list == null) {
                list = new ArrayList<>();
            }
            Activity activity = new Activity();
            activity.setName("全部活动");
            activity.setId(0);
            list.add(activity);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 获取活动对应奖品的所有子项
     *
     * @param
     * @author 刘源
     * @date 2016/4/21
     */
    @RequestMapping(value = "getActivityGiftItems", method = RequestMethod.GET)
    public void getActivityGiftItems(HttpServletResponse response, Integer activityId) {
        try {
            List<LotteryGift> list = lotteryGiftService.getActivityGiftItems(activityId);
            if (list == null) {
                list = new ArrayList<>();
            }
            LotteryGift gift = new LotteryGift();
            gift.setName("全部奖品");
            gift.setId(0);
            list.add(gift);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }


    /**
     * @Description(描述):苹果系列活动列表 90天活动专区，180天活动专区   九月份
     * @author 王信
     * @date 2016/8/31
     * @params
     **/
    @RequestMapping(value = "/appleActivityList", method = RequestMethod.GET)
    public String appleActivityList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                    @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2) {
        if (page == null || page <= 1) {
            page = 1;
        }
        Integer limit = 30;
        Activity activity = activityService.queryActivityById(activityId);
        List<Map<String, Object>> list = null;
        Integer count = 0;
        if (ActivityConstant.REGULAR_90_INVESTMENT_0903.equals(activity.getName())) {
            list = activityService.selectAppleActivityUserList(keyword, keyword1, keyword2, activity.getId(), activity.getStartTime(), activity.getEndTime(), 90, (page - 1) * limit, limit);
            count = activityService.selectAppleActivityUserCount(keyword, keyword1, keyword2, activity.getId(), activity.getStartTime(), activity.getEndTime(), 90);
        }
        if (ActivityConstant.REGULAR_180_INVESTMENT_0903.equals(activity.getName())) {
            list = activityService.selectAppleActivityUserList(keyword, keyword1, keyword2, activity.getId(), activity.getStartTime(), activity.getEndTime(), 180, (page - 1) * limit, limit);
            count = activityService.selectAppleActivityUserCount(keyword, keyword1, keyword2, activity.getId(), activity.getStartTime(), activity.getEndTime(), 180);
        }
        model.addAttribute("page", page);
        model.addAttribute("activityId", activityId);
        model.addAttribute("pages", calcPage(count, limit));
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("keyword1", keyword1);
        model.addAttribute("keyword2", keyword2);
        return "activity/appleActivityList";
    }

    /**
     * @Description(描述):周末专享，50元京东卡任意拿！
     * @author zxx
     * @date 2016/9/9
     * @params
     **/
    @RequestMapping(value = "/weekActivityList", method = RequestMethod.GET)
    public String weekActivityList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                   @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        Integer limit = 30;
        Activity activity = activityService.queryActivityById(activityId);
        List<Map<String, Object>> list = null;
        Integer count = 0;


        if (startTime == null || "".equals(startTime)) {
            startTime = activity.getStartTime();
        }
        if (endTime == null || "".equals(endTime)) {
            endTime = activity.getEndTime();
        }
        list = activityService.selectWeekInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90, (page - 1) * limit, limit);
        count = activityService.countWeekInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90);

        model.addAttribute("page", page);
        model.addAttribute("endTime", endTime);
        model.addAttribute("startTime", startTime);
        model.addAttribute("activityId", activityId);
        model.addAttribute("pages", calcPage(count, limit));
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("keyword1", keyword1);
        model.addAttribute("keyword2", keyword2);
        return "activity/weekActivityList";
    }

    /**
     * @Description(描述):3125 红包
     * @author zxx
     * @date 2016/9/9
     * @params
     **/
    @RequestMapping(value = "/hongbaoActivityList", method = RequestMethod.GET)
    public String hongbaoActivityList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                      @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        Integer limit = 30;
        Activity activity = activityService.queryActivityById(activityId);
        List<Map<String, Object>> list = null;
        Integer count = 0;


        if (startTime == null || "".equals(startTime)) {
            startTime = DateFormatTools.jumpOneDay(new Date(), -7);
        }
        if (endTime == null || "".equals(endTime)) {
            endTime = new Date();
        }
        list = activityService.selectHongbaoInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90, (page - 1) * limit, limit);
        count = activityService.countHongbaoInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90);

        model.addAttribute("page", page);
        model.addAttribute("endTime", endTime);
        model.addAttribute("startTime", startTime);
        model.addAttribute("activityId", activityId);
        model.addAttribute("pages", calcPage(count, limit));
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("keyword1", keyword1);
        model.addAttribute("keyword2", keyword2);
        return "activity/hongbaoActivityList";
    }


    /**
     * @Description(描述): 50元京东卡任意拿！
     * @author zxx
     * @date 2016/9/9
     * @params
     **/
    @RequestMapping(value = "/jingdongActivityList", method = RequestMethod.GET)
    public String jingdongActivityList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                       @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        Integer limit = 30;
        Activity activity = activityService.queryActivityById(activityId);
        List<Map<String, Object>> list = null;
        Integer count = 0;


        if (startTime == null || "".equals(startTime)) {
            startTime = DateFormatTools.jumpOneDay(new Date(), -7);
        }
        if (endTime == null || "".equals(endTime)) {
            endTime = new Date();
        }
        list = activityService.selectJingdongInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90, (page - 1) * limit, limit);
        count = activityService.countJingdongInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90);

        model.addAttribute("page", page);
        model.addAttribute("endTime", endTime);
        model.addAttribute("startTime", startTime);
        model.addAttribute("activityId", activityId);
        model.addAttribute("pages", calcPage(count, limit));
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("keyword1", keyword1);
        model.addAttribute("keyword2", keyword2);
        return "activity/jingdongActivityList";
    }

    /**
     * @Description(描述):抢标活动
     * @author 王信
     * @date 2016/9/21
     * @params
     **/
    @RequestMapping(value = "/projectAwardList", method = RequestMethod.GET)
    public String projectAwardList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                   @RequestParam(required = false) String keyword, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        try {
            Integer limit = 30;
            Activity activity = activityService.queryActivityById(activityId);
            if (startTime == null) {
                startTime = DateFormatTools.strToDate("2016-09-21 18:00:00");
                endTime = new Date();
            } else {
                model.addAttribute("endTime", endTime);
                model.addAttribute("startTime", startTime);
            }
            List<Map<String, Object>> list = activityService.projectAwardList(keyword, startTime, endTime, (page - 1) * limit, limit);
            Integer count = activityService.projectAwardListCount(keyword, startTime, endTime);
            model.addAttribute("page", page);
            model.addAttribute("activityId", activityId);
            model.addAttribute("pages", calcPage(count, limit));
            model.addAttribute("list", list);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error(e);
        }
        return "activity/projectAwardList";
    }

    /**
     * @Description(描述):国庆黄金活动。黄金国庆，拿金条，赢壕礼！
     * @author 王信
     * @date 2016/9/26
     * @params
     **/
    @RequestMapping(value = "/getAwardGoldList", method = RequestMethod.GET)
    public String getAwardGoldList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                   @RequestParam(required = false) String keyword, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        Activity activity = null;
        try {
            Integer limit = 30;
            activity = activityService.queryActivityById(activityId);
            if (startTime == null) {
                startTime = activity.getStartTime();
                endTime = activity.getEndTime();
            } else {
                model.addAttribute("endTime", endTime);
                model.addAttribute("startTime", startTime);
            }
            List<Map<String, Object>> list = activityService.selectActivityListByName(activity.getName(), keyword, activity.getId(), startTime, endTime, (page - 1) * limit, limit);
            if (activity != null && activity.getName().equals(ActivityConstant.REGULAR_AWARD_NEWYEAR)) {
                list = activityService.selectActivityListByName(activity.getName(), keyword, null, startTime, endTime, (page - 1) * limit, limit);
            }
            Integer count = activityService.selectActivityCountByName(activity.getName(), keyword, activity.getId(), startTime, endTime);
            model.addAttribute("page", page);
            model.addAttribute("activityId", activityId);
            model.addAttribute("activityName", activity.getName());
            model.addAttribute("pages", calcPage(count, limit));
            model.addAttribute("list", list);
            model.addAttribute("keyword", keyword);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
        } catch (Exception e) {
            logger.error(e);
        }
        // 6月666 清爽好礼0元购
        if (activity != null && activity.getName().equals(ActivityConstant.REGULAR_AWARD_NOVERBER)) {
            return "activity/activityInvestmentList";
        } else if (activity != null && activity.getName().equals(ActivityConstant.REGULAR_AWARD_NEWYEAR)) {
            return "activity/activityInvestmentNewYear";
        }
            return "activity/get666ActivityList";
    }


    /**
     * @Description: 疯狂的礼物！投资即送，奖品团团赚！
     * @date 2016/10/17
     * @author 王信
     */
    @RequestMapping(value = "/getAwardCanonList", method = RequestMethod.GET)
    public String getAwardCanonList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                    @RequestParam(required = false) String keyword, @RequestParam(required = false) Date
                                            startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        try {
            Integer limit = 30;
            Activity activity = activityService.queryActivityById(activityId);
            if (startTime == null) {
                startTime = activity.getStartTime();
                endTime = activity.getEndTime();
            } else {
                model.addAttribute("endTime", endTime);
                model.addAttribute("startTime", startTime);
            }
            List<Map<String, Object>> list = activityService.selectActivityListByName(activity.getName(), keyword, activity.getId(), startTime, endTime, (page - 1) * limit, limit);
            Integer count = activityService.selectActivityCountByName(activity.getName(), keyword, activity.getId(), startTime, endTime);
            model.addAttribute("page", page);
            model.addAttribute("activityId", activityId);
            model.addAttribute("pages", calcPage(count, limit));
            model.addAttribute("list", list);
            model.addAttribute("keyword", keyword);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
        } catch (Exception e) {
            logger.error(e);
        }
        return "activity/getAwardCanonList";
    }


    /**
     * @Description: 双重壕礼    后台统计功能
     * @date 2016/10/21
     * @author 王信
     */
    @RequestMapping(value = "/getDoubleGiftList", method = RequestMethod.GET)
    public String getDoubleGiftList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                    @RequestParam(required = false) String keyword, @RequestParam(required = false) Date
                                            startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        try {
            Integer limit = 30;
            Activity activity = activityService.queryActivityById(activityId);
            if (startTime == null) {
                startTime = activity.getStartTime();
                endTime = activity.getEndTime();
            } else {
                model.addAttribute("endTime", endTime);
                model.addAttribute("startTime", startTime);
            }
            List<Map<String, Object>> list = activityService.selectGetDoubleGiftList(keyword, activity.getId(), startTime, endTime, (page - 1) * limit, limit);
            Integer count = activityService.selectGetDoubleGiftCount(keyword, activity.getId(), startTime, endTime);
            Map<String, Object> map = activityService.selectGetDoubleGiftMap(startTime, endTime);
            model.addAttribute("rAmount", map == null ? 0 : map.get("rAmount"));
            model.addAttribute("huoAmount", map == null ? 0 : map.get("huoAmount"));
            model.addAttribute("rCount", map == null ? 0 : map.get("rCount"));
            model.addAttribute("huoCount", map == null ? 0 : map.get("huoCount"));
            model.addAttribute("uCount", map == null ? 0 : map.get("uCount"));
            model.addAttribute("page", page);
            model.addAttribute("activityId", activityId);
            model.addAttribute("pages", calcPage(count, limit));
            model.addAttribute("list", list);
            model.addAttribute("keyword", keyword);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
        } catch (Exception e) {
            logger.error(e);
        }
        return "activity/getDoubleGiftList";
    }


    /**
     * @Description: 2017-02-14  情人节活动 后台统计
     * @date 2017/1/18
     * @author 王信
     */
    @RequestMapping(value = "/valentinesDaysList", method = RequestMethod.GET)
    public String valentinesDaysList(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                     @RequestParam(required = false) String keyword, @RequestParam(required = false) Date
                                             startTime, @RequestParam(required = false) Date endTime) {
        try {
            if (page == null || page <= 1) {
                page = 1;
            }
            Integer limit = 30;
            Activity activity = activityService.queryActivityById(activityId);
            if (startTime == null) {
                startTime = activity.getStartTime();
            } else {
                model.addAttribute("startTime", startTime);
            }
            if (endTime == null) {
                endTime = activity.getEndTime();
            } else {
                model.addAttribute("endTime", endTime);
            }
//            Map<String,Object> map=activityService.selectInvestmentTotal(activity.getStartTime(),activity.getEndTime());
            List<Map<String, Object>> list = activityService.selectValentinesDayActivityList(keyword, startTime, endTime, (page - 1) * limit, limit);
            Double totalAc = 0d;
            Double totalAll = 0d;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                for (Object key : map.keySet()) {
                    String phone = (String) map.get("phone");
                    sb.append(phone.substring(0, 7)).append("****");
                    map.put("phone", sb.toString());
                    sb.setLength(0);
                    Object obj = map.get(key);
                    if (obj == null) {
                        map.put(key, "0");
                    } else {
                        if (key.toString().contains("romanticAmount")) {
                            map.put(key, BigDecimalUtil.fixed2(map.get(key).toString()).toString());
                            totalAc = BigDecimalUtil.add(BigDecimalUtil.fixed2(map.get(key).toString()), totalAc);
                        }
                    }
                }
                map.put("romanticTotalAmount", BigDecimalUtil.fixed2(totalAc));
                totalAc = 0d;
                totalAll = BigDecimalUtil.add(BigDecimalUtil.fixed2(map.get("romanticTotalAmount").toString()), totalAll);
            }
            Map<String, Object> mapInv = activityService.selectInvest(keyword, startTime, endTime);
            Map<String, Object> mapExp = activityService.selectExpMobey(keyword, startTime, endTime, activityId);
            Map<String, Object> mapHb = activityService.selectHongbao(keyword, startTime, endTime);
            Integer count = activityService.selectValentinesDayActivityCount(keyword, startTime, endTime);
            model.addAttribute("page", page);
//            model.addAttribute("map", map);
            if (mapInv != null) {
                model.addAttribute("inv_total_amount", mapInv.get("inv_total_amount"));
            } else {
                model.addAttribute("inv_total_amount", 0);
            }
            if (mapExp != null) {
                model.addAttribute("exp_amount", mapExp.get("exp_amount"));
            } else {
                model.addAttribute("exp_amount", 0);
            }
            if (mapHb != null) {
                model.addAttribute("inv_amount", mapHb.get("inv_amount"));
                model.addAttribute("hb_amount", mapHb.get("hb_amount"));
            } else {
                model.addAttribute("inv_amount", 0);
                model.addAttribute("hb_amount", 0);
            }
            model.addAttribute("activityId", activityId);
            model.addAttribute("activityId", activityId);
            model.addAttribute("pages", calcPage(count, limit));
            model.addAttribute("list", list);
            model.addAttribute("totalAc", totalAll);
            model.addAttribute("keyword", keyword);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
        } catch (Exception e) {
            logger.error(e);
        }
        return "activity/valentinesDaysList";
    }

    /**
     * @Description: 感恩2周年，加息券、体验金人人大狂欢！
     * @date 2017/03/29
     * @author zxx
     */
    @RequestMapping(value = "/twoYearsThanksActivity", method = RequestMethod.GET)
    public String twoYearsThanksActivity(Model model, @RequestParam(required = false) Integer page, Integer
            activityId,
                                         @RequestParam(required = false) String keyword, @RequestParam(required = false) Date
                                                 startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        try {
            Integer limit = 30;
            Activity activity = activityService.queryActivityById(activityId);
            if (startTime == null) {
                startTime = activity.getStartTime();
            }
            if (endTime == null) {
                endTime = activity.getEndTime();
            }
            List<Map<String, Object>> list = activityService.twoYearsThanksActivity(keyword, startTime, endTime, activity.getStartTime(), activity.getEndTime(), (page - 1) * limit, limit);
            Integer count = activityService.twoYearsThanksActivityCount(keyword, startTime, endTime, activity.getStartTime(), activity.getEndTime());
            model.addAttribute("page", page);
            model.addAttribute("activityId", activityId);
            model.addAttribute("pages", calcPage(count, limit));
            model.addAttribute("list", list);
            model.addAttribute("keyword", keyword);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
        } catch (Exception e) {
            logger.error(e);
        }
        return "activity/twoYearsThanksActivity";
    }

    /**
     * @Description: 2周年壕礼迎新！新手即送5000元体验金+独享2%加息！
     * @date 2017/03/29
     * @author zxx
     */
    @RequestMapping(value = "/newAnniversary", method = RequestMethod.GET)
    public String newAnniversary(Model model, @RequestParam(required = false) Integer page, Integer activityId,
                                 @RequestParam(required = false) String keyword, @RequestParam(required = false) Date
                                         startTime, @RequestParam(required = false) Date endTime) {
        if (page == null || page <= 1) {
            page = 1;
        }
        try {
            Integer limit = 30;
            Activity activity = activityService.queryActivityById(activityId);
            if (startTime == null) {
                startTime = activity.getStartTime();
            }
            if (endTime == null) {
                endTime = activity.getEndTime();
            }
            List<Map<String, Object>> list = activityService.newAnniversary(keyword, startTime, endTime, activity.getStartTime(), activity.getEndTime(), (page - 1) * limit, limit);
            Integer count = activityService.newAnniversaryCount(keyword, startTime, endTime, activity.getStartTime(), activity.getEndTime());
            model.addAttribute("page", page);
            model.addAttribute("activityId", activityId);
            model.addAttribute("pages", calcPage(count, limit));
            model.addAttribute("list", list);
            model.addAttribute("keyword", keyword);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
        } catch (Exception e) {
            logger.error(e);
        }
        return "activity/newAnniversary";
    }

}
