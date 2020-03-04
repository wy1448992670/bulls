package com.goochou.p2b.app.controller;

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

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.model.AppNoticeWithBLOBs;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.AppNoticeService;


@Controller
@RequestMapping(value = "/notice")
@Api(value = "公告接口")
public class AppNoticeController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(AppNoticeController.class);

    @Resource
    private AppNoticeService appNoticeService;

    /**
     * 获取最新的公告
     *	sxy
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "公告列表")
    public AppResult banners(HttpServletRequest request,
                             @ApiParam("App版本号") @RequestParam String appVersion,
                             @ApiParam("当前页") @RequestParam(required = false) Integer page,
                             @ApiParam("token") @RequestParam String token) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (page == null) {
                page = 1;
            }
            List<AppNoticeWithBLOBs> list = null;
            Integer count = null;
            User user = userService.checkLogin(token);
            if (user == null) {
                list = appNoticeService.list(1, (page - 1) * 10, 10);
                count = appNoticeService.listCount(1);
            } else {
                list = appNoticeService.getNoticeList(user.getId(), (page - 1) * 10, 10);
                count = appNoticeService.getNoticeListCount(user.getId());
            }
            map.put("list", list);
            map.put("page", page);
            map.put("pages", calcPage(count, 10));
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
    
    /**
     * 全部阅读公告
     * @author sxy
     * @param request
     * @param appVersion
     * @param token
     * @return
     */
    @RequestMapping(value = "/allReadNotice", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "全部阅读公告")
    public AppResult allReadNotice(HttpServletRequest request,
                                   @ApiParam("App版本号") @RequestParam String appVersion,
                                   @ApiParam("token") @RequestParam String token) {
        try {
            User user = userService.getByToken(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            List<Integer> noReadList = appNoticeService.selectAllNoReadNoticeList(user.getId());
            if (noReadList.size() <= 0) {
                return new AppResult(ERROR, "公告已经全部阅读");
            }
            appNoticeService.saveAllReadNotice(user.getId(), noReadList);
            return new AppResult(SUCCESS, "全部阅读");
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


}
