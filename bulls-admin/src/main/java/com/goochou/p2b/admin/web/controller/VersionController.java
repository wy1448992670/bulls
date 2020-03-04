package com.goochou.p2b.admin.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.goochou.p2b.model.AppVersionContent;
import com.goochou.p2b.model.AppVersionContentWithBLOBs;
import com.goochou.p2b.service.AppVersionService;

@Controller
@RequestMapping(value = "/version")
public class VersionController extends BaseController {
    @Resource
    private AppVersionService appVersionService;

    /**
     * app版本控制管理list页面
     * @author sxy
     * @param model
     * @param response
     * @param keyword
     * @param page
     * @return
     */
    @RequestMapping(value = "versionList", method = RequestMethod.GET)
    public String versionList(Model model, HttpServletResponse response, String keyword, Integer page) {
    	Integer limit = 20;
    	if (page == null) {
    	    page = 1;
    	}
    	List<AppVersionContent> list = appVersionService.queryAppVersionContentList(keyword, (page - 1) * limit, limit);
    	int count = appVersionService.queryAppVersionContentCount(keyword);
    	int pages = calcPage(count, limit);
    	model.addAttribute("pages", pages);
    	model.addAttribute("list", list);
    	model.addAttribute("page", page);
    	model.addAttribute("keyword", keyword);
    	return "/project/versionList";
    }

    /**
     * @Description(描述):新增版本管理内容
     * @author 王信
     * @date 2016/4/12
     * @params
     **/
    @RequestMapping(value = "versionAdd", method = RequestMethod.GET)
    public String versionAdd(Model model, HttpServletResponse response, @RequestParam(required = false) Integer id,
	    @RequestParam(required = false) Integer page) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("version:versionAdd");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
    	AppVersionContent appVersionContent = appVersionService.selectAppVersionContentKey(id);
    	model.addAttribute("map", appVersionContent);
    	model.addAttribute("page", page);
    	return "project/versionAdd";
    }

    /**
     * @Description(描述):新增保存
     * @author 王信
     * @date 2016/4/12
     * @params
     **/
    @RequestMapping(value = "versionAddSave", method = RequestMethod.POST)
    public String versionAddSave(Model model, HttpServletResponse response, AppVersionContentWithBLOBs group,
	    @RequestParam(required = false) Integer page) {
    	appVersionService.saveAppVersionContent(group);
    	return "redirect:/project/versionList";
    }

    /**
     * @Description(描述):新增保存
     * @author 王信
     * @date 2016/4/12
     * @params
     **/
    @RequestMapping(value = "versionDelete", method = RequestMethod.GET)
    public String versionDelete(Model model, HttpServletResponse response, Integer id) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("version:versionDelete");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
    	appVersionService.deleteAppVersionContentKey(id);
    	return "redirect:/project/versionList";
    }
}
