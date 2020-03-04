package com.goochou.p2b.admin.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.UserInviteService;
import com.goochou.p2b.utils.DateFormatTools;

@Controller
@RequestMapping("/invite")
public class UserInviteController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(UserInviteController.class);

    @Resource
    private UserInviteService userInviteService;

    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public String list(Model model, String keyword, Integer page, HttpSession session) {
        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = null;
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:seealluser");
            }catch (Exception e) {
            	adminId = admin.getId();
            }
            List<Map<String, Object>> list = userInviteService.inviteReport(keyword, (page - 1) * limit, limit, adminId);
            for (int i = 0; i <list.size() ; i++) {
                Map map = list.get(i);
                Integer userId = Integer.valueOf(map.get("userId").toString());
                map.put("count",userInviteService.getInviteDetailCount(userId));
            }
            Integer count = userInviteService.inviteReportCount(keyword, adminId);
            int pages = calcPage(count.intValue(), limit);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error(e);
        }
        return "invite/list";
    }
    /**
     * 
    * @Title: UserInviteController.java 
    * @Package com.goochou.p2b.admin.web.controller 
    * @Description: 用户邀请者查询 （用户是被谁邀请的）
    * @author 王信  
    * @date 2016年1月20日 下午1:53:26 
    * @param model
    * @param
    * @return
     */
    @RequestMapping(value = "/qrcodes", method = RequestMethod.GET)
    public String list(Model model, String keywords) {
        try {
            List<Map<String, Object>> list = userInviteService.userInviteDetail(keywords);
            model.addAttribute("list", list);
        } catch (Exception e) {
            logger.error(e);
        }
        return "invite/detail";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Integer userId, Model model) {
        try {
            List<Map<String, Object>> list = userInviteService.getInviteDetail(userId);
            model.addAttribute("list", list);
        } catch (Exception e) {
            logger.error(e);
        }
        return "invite/detail";
    }



    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String hongbaoActivityList(Model model, @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false)String keyword, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime) {
        if (page==null || page <=1){
            page =1;
        }
        Integer limit=30;
        List<Map<String,Object>> list =null;
        Integer count=0;


        if(startTime == null || "".equals(startTime)){
            startTime = DateFormatTools.jumpOneDay(new Date(),-7);
        }
        if(endTime == null || "".equals(endTime)){
            endTime = new Date();
        }
        list=userInviteService.selectInvestmentAward(keyword,startTime,endTime,(page-1)*limit,limit);
        count=userInviteService.countInvestmentAward(keyword,startTime,endTime);

        model.addAttribute("page",page);
        model.addAttribute("endTime",endTime);
        model.addAttribute("startTime",startTime);
        model.addAttribute("pages",calcPage(count,limit));
        model.addAttribute("list",list);
        model.addAttribute("keyword",keyword);
        return "invite/inviteList";
    }









}
