package com.goochou.p2b.app.controller;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.WithdrawCoupon;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.WithdrawCouponService;
import com.goochou.p2b.utils.DateFormatTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiutianjia on 2017/9/4.
 */
@Controller
@RequestMapping("withdrawCoupon")
@Api(value = "提现券-withdrawCoupon")
public class WithdrawCouponController extends BaseController {
    @Resource
    UserService userService;
    @Resource
    WithdrawCouponService withdrawCouponService;


    /**
     * 提现券列表
     * @author ydp
     * @param token
     * @param appVersion
     * @param page
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "提现券列表")

    public AppResult list(@ApiParam("用户token") @RequestParam String token,
                                        @ApiParam("app版本号") @RequestParam String appVersion,
                                        @ApiParam("分页当前页") @RequestParam Integer page) {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        if (page == null || page < 1) {
            page = 1;
        }
        Integer limit = 5;
        List<WithdrawCoupon> list = null;
        list = withdrawCouponService.getWithdrawCouponList(user.getId(), (page - 1) * 5, limit);
        int count = withdrawCouponService.getWithdrawCouponCount(user.getId());
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (WithdrawCoupon wc : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", String.valueOf(wc.getId()));
            map.put("title", wc.getTitle());
            map.put("createTime", DateFormatTools.dateToStrCN2(wc.getCreateDate())); //发放时间
            map.put("expireTime", "失效时间:" + DateFormatTools.dateToStrCN2(wc.getExpireTime()));//失效时间
            map.put("userId", String.valueOf(wc.getUserId()));
            map.put("status", String.valueOf(wc.getStatus()));
            list1.add(map);
        }
        Map<String, Object> map = new HashMap();
        map.put("rateCoupon", list1);
        map.put("page", page);
        map.put("pages", calcPage(count, 5));
        return new AppResult(SUCCESS, map);
    }
}
