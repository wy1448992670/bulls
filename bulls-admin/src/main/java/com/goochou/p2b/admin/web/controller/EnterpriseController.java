package com.goochou.p2b.admin.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.model.Enterprise;
import com.goochou.p2b.model.EnterprisePicture;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.EnterprisePictureService;
import com.goochou.p2b.service.EnterpriseService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.AjaxUtil;

@Controller
@RequestMapping(value = "/enterprise")
public class EnterpriseController extends BaseController {
	private static final Logger logger = Logger.getLogger(EnterpriseController.class);
    @Resource
    private EnterpriseService enterpriseService;
    @Resource
    private EnterprisePictureService enterprisePictureService;
    @Resource
    private UploadService uploadService;

    @RequestMapping(value = "/list")
    public String query(Model model, @RequestParam(required = false) String
            keyword,@RequestParam(required = false) Integer page) {
        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            List<Enterprise> list = enterpriseService.query(keyword, (page - 1) * limit, limit,null);
            int count = enterpriseService.queryCount(keyword,null);

            int pages = calcPage(count, limit);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("keyword", keyword);
            return "/enterprise/list";
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(int id, Model model) {
        Enterprise e = enterpriseService.detail(id);
        model.addAttribute("enterprise", e);
        return "/enterprise/detail";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("enterprise:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        return "/enterprise/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Enterprise enterprise, String picture,String picture2) {
        try {
            enterpriseService.saveWithPicture(enterprise, picture,picture2);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/enterprise/list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(int id, Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("enterprise:edit");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        Enterprise e = enterpriseService.detail(id);
        model.addAttribute("enterprise", e);
        return "/enterprise/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Enterprise enterprise, String picture,String picture2) {
        try {
            enterpriseService.update(enterprise, picture,picture2);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/enterprise/list";
    }

    /**
     * 删除企业图片
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/picture", method = RequestMethod.POST)
    public void deletePicture(Integer id, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            enterprisePictureService.delete(id);
            map.put(STATUS, SUCCESS);
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            logger.error(e);
            map.put(STATUS, ERROR);
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        }
    }

    /**
     * 
     * 企业图片上传
     * 
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(Integer id, Integer type, String picName, @RequestParam MultipartFile file, HttpServletResponse response, HttpSession session) {
        Map<String, Object> map = null;
        try {
            EnterprisePicture picture = new EnterprisePicture();
            picture.setStatus(1); // 当前图片还是不可用的状态
            picture.setCreateTime(new Date());
            picture.setName(picName.trim());
            picture.setEnterpriseId(id);
            picture.setType(type); //图片类型:0企业信息1企业公章
            map = enterprisePictureService.save(picture, file, ((UserAdmin) session.getAttribute("user")).getId());
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(map));
    }


    @RequestMapping(value = "/checkName", method = RequestMethod.GET)
    public void checkName(@RequestParam String name, Integer id, HttpServletResponse response) {
        Boolean flag = true;
        try {
            flag = enterpriseService.checkNameExists(name, id);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(flag));
    }

    /**
     * 
     * 创建项目时查询企业用
     * 
     * @param keyword
     *            企业名称
     * @param response
     */
    @RequestMapping(value = "/list/usable", method = RequestMethod.GET)
    public void detail(String keyword, HttpServletResponse response,Integer type) {
        try {
            List<Enterprise> list = enterpriseService.query(keyword, 0, 10,type);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
