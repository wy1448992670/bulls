package com.goochou.p2b.admin.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.model.Banner;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.BannerService;
import com.goochou.p2b.service.UploadService;

@Controller
@RequestMapping(value = "/banner")
public class BannerController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(BannerController.class);

    @Resource
    private BannerService bannerService;
    @Resource
    private UploadService uploadService;

    /**
     * banner列表
     * @author sxy
     * @param model
     * @param status
     * @param page
     * @param type
     * @param source
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String bannerList(Model model, Integer status, Integer page,Integer type,Integer source) {
        if (page == null) {
            page = 1;
        }
        List<Banner> list = bannerService.listByStatus(status,type,source,page);
        model.addAttribute("bannerList", list);
        Integer count = bannerService.getCountBanner(status,type,source);
        model.addAttribute("pages", calcPage(count, 20));
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("source", source);
        model.addAttribute("status", status);
        return "banner/list";
    }

    /**
     * 获取banner list
     * 
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, Integer id, @RequestParam(required = false)Integer type, @RequestParam(required = false)Integer source, @RequestParam(required = false) Integer status) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("banner:detail");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        Banner b = bannerService.detail(id);
        model.addAttribute("banner", b);
        model.addAttribute("type", type);
        model.addAttribute("source", source);
        model.addAttribute("status", status);
        return "banner/detail";
    }

    /**
     * 跳转到添加banner页面
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAddBanner(Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("banner:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        return "banner/add";
    }

    /**
     * 获取banner list
     * 
     * @return
     */

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBanner(Banner banner, @RequestParam("file") MultipartFile file, HttpSession session,
                            @RequestParam(required = false)Integer types, @RequestParam(required = false)Integer sources, @RequestParam(required = false) Integer statuss) {
        Map<String, Object> map = null;
        try {
            UserAdmin u = (UserAdmin) session.getAttribute("user");
            if (file!=null){
                map = uploadService.save(file, 5, u.getId());
                banner.setPicture(Integer.parseInt(map.get(ConstantsAdmin.ID).toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        banner.setCreateTime(new Date());
        bannerService.save(banner);
        String s="?page=1";
        if (types!=null){
            s=s+"&type="+types;
        }
        if (statuss!=null){
            s=s+"&status="+statuss;
        }
        if (sources!=null){
            s=s+"&source="+sources;
        }
        return "redirect:/banner/list"+s;
    }

    /**
     * 弃用Banner
     * 
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String deleteBanner(Model model, Integer id, Integer status) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("banner:update");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        Banner banner = new Banner();
        banner.setId(id);
        banner.setStatus(status);
        bannerService.update(banner);
        return "redirect:/banner/list";
    }
    @RequestMapping(value = "/updateSeniority", method = RequestMethod.GET)
    public String updateSeniority(Integer id, Integer seniority,Integer type,Integer status) {
        Banner banner = new Banner();
        banner.setId(id);
        if (seniority<=0){
            seniority=1;
        }
        String pm="";
        if(type!=null){
            pm="?type="+type;
        }
        if (status!=null){
            if (StringUtils.isEmpty(pm)){
                pm="?status="+status;
            }else{
                pm+="&status="+status;
            }
        }
        banner.setSeniority(seniority);
        bannerService.update(banner);
        return "redirect:/banner/list"+pm;
    }

}
