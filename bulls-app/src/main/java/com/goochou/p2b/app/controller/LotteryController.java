package com.goochou.p2b.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Banner;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.YaoCount;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.BannerService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.YaoCountService;
import com.goochou.p2b.service.YaoRecordService;


@Controller
@RequestMapping("/lottery")
@Api(value = "游戏中心-lottery")
public class LotteryController extends BaseController {

    @Resource
    private RateCouponService rateCouponService;

    @Resource
    private YaoCountService yaoCountService;

    @Resource
    private AssetsService assetsService;

    @Resource
    private YaoRecordService yaoRecordService;

    @Resource
    private HongbaoService hongbaoService;

    @Resource
    private MessageService messageService;
    @Resource
    private BannerService bannerService;


    /**
     * 摇一摇页面
     * @param token
     * @param appVersion
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yaoPage", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "摇一摇页面")
    public AppResult yaoPage(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion) throws Exception {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            YaoCount yc = yaoCountService.getById(user.getId());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("count", yc.getCount() + yc.getTodayCount());
    //        List<String> list = projectService.selectTitlePrompt("摇一摇奖励");
    //        map.put("award", list); // 奖励
    //        List<String> list2 = projectService.selectTitlePrompt("摇一摇规则");
    //        map.put("raw", list2);//规则
            //轮播最新10条记录
            map.put("scrollList", yaoRecordService.listByUser(null, 0, 10));
            return new AppResult(SUCCESS, map);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 摇一摇奖励记录
     * @param token
     * @param page
     * @param appVersion
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yao/record", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "摇一摇奖励记录")
    public AppResult yaoRecord(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("当前页码") @RequestParam Integer page,
            @ApiParam("App版本号") @RequestParam String appVersion) throws Exception {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (page == null) {
            page = 1;
        }
        Integer limit = 10;
        //获得的奖励记录
        map.put("list", yaoRecordService.listByUser(user.getId(), (page - 1) * limit, limit));
        map.put("page", page);
        Integer count = yaoRecordService.listByUserCount(user.getId());
        map.put("pages", calcPage(count, limit));
        return new AppResult(SUCCESS, map);
    }

    /**
     * 使用摇一摇
     * @param token
     * @param appVersion
     * @param client
     * @return
     */
    @RequestMapping(value = "/use", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "使用摇一摇")
    public AppResult useLottery(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
        User user = userService.checkLogin(token);
        Map<String, String> map = null;
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        map = yaoCountService.updateYaoYao(user.getId(), client);
        if (map.get("type").toString().equals("3")) {
            return new AppResult(SUCCESS, "没有摇一摇次数");
        } else {
            return new AppResult(SUCCESS, map);
        }

    }
}
