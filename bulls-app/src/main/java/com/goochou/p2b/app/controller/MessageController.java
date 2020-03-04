package com.goochou.p2b.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.annotationin.NeedLogin;
import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.AppNoticeService;
import com.goochou.p2b.service.MessageReceiverService;


@Controller
@RequestMapping(value = "/message")
@Api(value = "message消息中心")
public class MessageController extends BaseController {

	private static final Logger logger = Logger.getLogger(MessageController.class);

    @Resource
    private MessageReceiverService messageReceiverService;
    @Resource
    private AppNoticeService appNoticeService;

    /**
     * message消息中心
     * @author sxy
     * @param request
     * @param token
     * @param page
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "message消息中心")
    public AppResult banners(HttpServletRequest request,
                             @ApiParam("用户token") @RequestParam String token,
                             @ApiParam("App版本号") @RequestParam String appVersion,
                             @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = false) String client,
                             @ApiParam("分页当前页") @RequestParam Integer page
    ) {
        try {

            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            if (page < 1) {
                page = 1;
            }
            int limit = 10;
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, Object>> list = messageReceiverService.listAppMessage(user.getId(), (page - 1) * limit, limit);
            Integer count = messageReceiverService.listAppMessageCount(user.getId(), null);
            Integer unReadCount = messageReceiverService.listAppMessageCount(user.getId(), 0); // 未读信息总量
            map.put("list", list);
            map.put("page", page);
            map.put("pages", calcPage(count, limit));
            map.put("count", count);
            map.put("unReadCount", unReadCount);
            return new AppResult(SUCCESS, map);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    String getTime(Date receiverTime) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        int days = getDistanceTime(receiverTime);
        if (days == 0 && fmt.format(receiverTime) == fmt.format(new Date())) {
            return new SimpleDateFormat("HH:mm").format(receiverTime);
        }
        if (days == 0 && fmt.format(receiverTime) != fmt.format(new Date())) {
            return "昨天";
        }
        if (days == 1) {
            return "昨天";
        }
        if (days < 30) {
            return days + "天前";
        }
        if (days > 30 && days < 365) {
            return days / 30 + "月前";
        }
        if (days > 365) {
            return days / 365 + "年前";
        }
        return null;
    }

    int getDistanceTime(Date time) {
        Integer day = 0;
        Date date = new Date();
        Long diff = date.getTime() - time.getTime();
        day = (int) (diff / (24 * 60 * 60 * 1000));
        return day;
    }

    /**
     * 删除或标记为已读
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新消息状态")
    public AppResult updateMessage(HttpServletRequest request,
                                   @ApiParam("用户token") @RequestParam String token,
                                   @ApiParam("App版本号") @RequestParam String appVersion,
                                   @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = false) String client,
                                   @ApiParam("消息id,公告则为公告ID，消息则为消息ID") @RequestParam List<Integer> id,
                                   @ApiParam("操作类型1标记已读2删除") @RequestParam Integer status,
                                   @ApiParam("类型0公告1个人消息（默认是1）") @RequestParam(required = false) Integer type
                                   ) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            if (status != 1 && status != 2) {
                return new AppResult(FAILED, "操作类型错误");
            }
            if (type == null || type == 1) {
                boolean flag = messageReceiverService.update(id, status);
                if (!flag) {
                    return new AppResult(FAILED, "不能删除未读消息");
                }
            } else if (type == 0) {
                appNoticeService.updateNoticeRead(user.getId(), id);
            }
        } catch (Exception e) {
            logger.error(e);
            return new AppResult(FAILED, "操作失败");
        }
        return new AppResult(SUCCESS);
    }

    /**
     * 一键阅读消息
     * @author sxy
     * @param request
     * @param token
     * @param appVersion
     * @param type
     * @return
     */
    @RequestMapping(value = "/allUpdate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "一键阅读消息")
    @NeedLogin
    public AppResult allUpdateMessage(HttpServletRequest request,
                                      @ApiParam("用户token") @RequestParam String token,
                                      @ApiParam("App版本号") @RequestParam String appVersion,
                                      @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = false) Integer type) {
        try {
            User user = userService.checkLogin(token);
            messageReceiverService.updateAllMessage(user.getId());
        } catch (Exception e) {
            logger.error(e);
            return new AppResult(FAILED, "操作失败");
        }
        return new AppResult(SUCCESS,"已全阅读");
    }
}
