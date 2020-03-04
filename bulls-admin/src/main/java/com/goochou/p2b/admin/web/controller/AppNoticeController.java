package com.goochou.p2b.admin.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.model.AppNotice;
import com.goochou.p2b.model.AppNoticeWithBLOBs;
import com.goochou.p2b.service.AppNoticeService;
import com.goochou.p2b.utils.AjaxUtil;

@Controller
@RequestMapping(value = "/notice")
public class AppNoticeController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(AppNoticeController.class);
	
    @Resource
    private AppNoticeService appNoticeService;

    /**
     * 公告列表
     * @author sxy
     * @param model
     * @param page
     * @param status
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, Integer page, Integer status) {
        if (page == null) {
            page = 1;
        }
        Integer limit = ConstantsAdmin.PAGE_LIMIT5;
        List<AppNoticeWithBLOBs> noticeList = appNoticeService.list(status, (page - 1) * limit, limit);
        //    List<Map<String, Object>> noticeList = appNoticeService.listWithRoll(status, (page - 1) * limit, limit);
        model.addAttribute("list", noticeList);
        Integer count = appNoticeService.listCount(status);
        model.addAttribute("pages", calcPage(count, limit));
        model.addAttribute("page", page);
        model.addAttribute("status", status);
        return "notice/list";
    }

    /**
     * 添加公告页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("notice:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        return "notice/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(AppNoticeWithBLOBs notice, HttpSession session) {
        appNoticeService.add(notice);
        return "redirect:/notice/list";
    }

    /**
     * 更新notice
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(AppNoticeWithBLOBs notice) {
        appNoticeService.update(notice);
        return "redirect:/notice/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Integer id, Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("notice:update");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        AppNotice notice = appNoticeService.get(id);
        model.addAttribute("notice", notice);
        return "notice/update";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Integer id) {
        appNoticeService.delete(id);
        return "redirect:/notice/list";
    }

    /**
     * 设置公告显示
     *
     * @param noticeId
     * @author 刘源
     * @date 2016/5/3
     */
    @RequestMapping(value = "/saveSetRoll", method = RequestMethod.POST)
    public void saveSetRoll(Integer noticeId, HttpServletResponse response) {
        try {
            appNoticeService.saveSetRoll(noticeId);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(SUCCESS));
    }

    /**
     * 取消公告显示
     *
     * @param id
     * @author 刘源
     * @date 2016/5/3
     */
    @RequestMapping(value = "/saveCancelRoll", method = RequestMethod.POST)
    public void saveCancelRoll(Integer id, HttpServletResponse response) {
        try {
            appNoticeService.saveCancelRoll(id);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(SUCCESS));
    }

}
