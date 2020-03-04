package com.goochou.p2b.app.controller;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.ApplyRefundStatusEnum;
import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderRefund;
import com.goochou.p2b.service.GoodsOrderRefundService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.GoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 申请退款
 * @author shuys
 * @since 2019/6/4 16:10
 */
@Controller
@RequestMapping(value = "refund")
@Api(value = "申请退款")
public class ApplyRefundController extends BaseController {


    private static final Logger logger = Logger.getLogger(ApplyRefundController.class);

    @Resource
    private GoodsOrderService goodsOrderService;

    @Resource
    private GoodsOrderRefundService goodsOrderRefundService;

    /**
     * @description 申请退款
     * @author shuys
     * @date 2019/6/5
     * @param appVersion
     * @param token
     * @param orderNo
     * @param reason
     * @return com.goochou.p2b.app.model.AppResult
    */
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "申请退款")
    public AppResult apply(
            @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("订单号") @RequestParam String orderNo,
            @ApiParam("退款原因") @RequestParam String reason
    ) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            goodsOrderRefundService.insertApplyRefund(user.getId(), orderNo, reason);
            return new AppResult(SUCCESS, "申请成功");
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, e.getMessage());
        }
    }

    /**
     * @description 修改退款申请原因
     * @author shuys
     * @date 2019/6/5
     * @param appVersion
     * @param token
     * @param orderNo
     * @param reason
     * @return com.goochou.p2b.app.model.AppResult
    */
    @RequestMapping(value = "/updateApply", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改退款申请原因")
    public AppResult updateApply(
            @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("订单号") @RequestParam String orderNo,
            @ApiParam("退款原因") @RequestParam String reason
    ) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            goodsOrderRefundService.updateApplyRefund(user.getId(), orderNo, reason);
            return new AppResult(SUCCESS, "修改申请成功");
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
}
