package com.goochou.p2b.admin.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectPackage;
import com.goochou.p2b.service.ProjectPackageService;
import com.goochou.p2b.service.ProjectService;

@Controller
@RequestMapping(value = "/package")
public class ProjectPackageController extends BaseController {//资产包
	private static final Logger logger = Logger.getLogger(ProjectPackageController.class);
    @Resource
    private ProjectPackageService projectPackageService;
    @Resource
    private ProjectService projectService;




    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, Integer status, Integer page ,Integer product) {
        if (page == null) {
            page = 1;
        }
        List<ProjectPackage> list = projectPackageService.selectProjectPackageList(status,product,(page-1)*20,20);
        Integer count = projectPackageService.selectProjectPackageCount(status,product);
        model.addAttribute("list", list);
        model.addAttribute("product", product);
        model.addAttribute("pages", calcPage(count, 20));
        model.addAttribute("page", page);
        model.addAttribute("status", status);
        return "package/list";
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model,@RequestParam(required = false)Integer productId) {//新增资产包
        List<Project> list=projectService.selectPackageList(productId,null,null);
        model.addAttribute("list",list);
        if(productId==null){
            model.addAttribute("productId",list.size()>0?list.get(0).getProductId():null);
        }else{
            model.addAttribute("productId",productId);
        }
        return "package/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model,Integer product,Integer[] projectId) {
        try {
            projectPackageService.savePackage(product,projectId);
        }catch (Exception e){
            logger.error(e);
        }
        return "redirect:/package/list";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model,Integer packageId) {//修改资产包
        List<Project> list=projectService.selectByPackageList(packageId);
        ProjectPackage pack=projectPackageService.selectByIdProjectPackage(packageId);
        model.addAttribute("list",list);
        model.addAttribute("pack",pack);
        if(list.size()>0) {
//            Product product=list.get(0).getProduct();
//            model.addAttribute("title", product.getName());
//            model.addAttribute("annualized",product.getAnnualizedMin());
//            model.addAttribute("outDays",product.getOutDays());
        }
        return "package/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model,Integer packageId,Integer status,Integer[] projectId) {
        try {
            projectPackageService.updatePackage(packageId,status,projectId);
        }catch (Exception e){
            logger.error(e);
        }
        return "redirect:/package/list";
    }

}