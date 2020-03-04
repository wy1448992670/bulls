package com.goochou.p2b.admin.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.admin.converter.FilterChainDefinitionsLoader;
import com.goochou.p2b.model.Resources;
import com.goochou.p2b.service.ResourcesService;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.utils.AjaxUtil;

/**
 * 权限管理
 */
@Controller
@RequestMapping(value = "/resources")
public class ResourcesController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(ResourcesController.class);
	
    @Resource
    private ResourcesService resourcesService;
    @Resource
    private UserAdminService userAdminService;

    @RequestMapping(value = "/list")
    public String findAllResource(Model model) {
        return "resources/list";
    }

    @RequestMapping(value = "/list/ajax")
    public void findAllResource(Model model, Integer id, HttpServletResponse response) {
        try {
            List<Resources> list = resourcesService.getResourceByParentId(id);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
        	logger.error(e);
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Integer id, Model model) {
        Resources resources = resourcesService.detail(id);
        model.addAttribute("resources", resources);
        return "/resources/detail";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, Model model) {
        Resources resources = resourcesService.detail(id);
        List<Resources> fatherList = resourcesService.findAllFather();
        model.addAttribute("fatherList", fatherList);
        model.addAttribute("resources", resources);
        return "/resources/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Resources resources) {
        try {
            int ret = resourcesService.update(resources);
            if (ret == 1) {
                logger.info("============修改权限成功，需要重新加载权限列表===============");
                filterChainDefinitionsLoader.changeFilters();
                logger.info("============重新加载到shiro完成===============");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/resources/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@ModelAttribute Resources resources, Model model) {
        List<Resources> fatherList = resourcesService.findAllFather();
        model.addAttribute("fatherList", fatherList);
        return "/resources/add";
    }

    @Resource
    private FilterChainDefinitionsLoader filterChainDefinitionsLoader;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Resources resources, HttpServletRequest request, HttpSession session) {
        try {
            int ret = resourcesService.save(resources);
            if (ret == 1) {
                logger.info("============添加权限成功，需要重新加载权限列表===============");
                filterChainDefinitionsLoader.changeFilters();
                logger.info("============重新加载到shiro完成===============");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/resources/list";
    }

    @RequestMapping(value = "/checkName", method = RequestMethod.GET)
    public void checkName(String name, String url, String permission, HttpServletResponse response) {
        Boolean flag = true;
        try {
            flag = resourcesService.checkNameOrUrlExists(name, url, permission);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(flag));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(required = true) int id) {
        try {
            int ret = resourcesService.delete(id);
            if (ret == 1) {
                logger.info("============删除权限成功，需要重新加载权限列表===============");
                filterChainDefinitionsLoader.changeFilters();
                logger.info("============重新加载到shiro完成===============");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/resources/list";
    }
}
