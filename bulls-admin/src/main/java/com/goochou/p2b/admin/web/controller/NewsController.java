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
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.News;
import com.goochou.p2b.model.NewsWithBLOBs;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.NewsService;
import com.goochou.p2b.service.UploadService;

/**
 * 新闻管理
 *
 * @author irving
 */
@Controller
@RequestMapping(value = "/news")
public class NewsController extends BaseController {

	private static final Logger logger = Logger.getLogger(NewsController.class);

    @Resource
    private NewsService newsService;
    @Resource
    private UploadService uploadService;

    /**
     * 新闻管理列表
     * @author sxy
     * @param model
     * @param page
     * @param isTop
     * @param status
     * @param type
     * @return
     */
    @RequestMapping(value = "/list")
    public String query(Model model, @RequestParam(required = false) Integer page, Integer isTop, Integer status, Integer type) {
        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            List<NewsWithBLOBs> list = newsService.query(isTop, status, (page - 1) * limit, limit, type);
            int count = newsService.queryCount(isTop, status, type);
            int pages = calcPage(count, limit);
            model.addAttribute("newsUrl", ClientConstants.H5_URL+"newsDetail.html?channelType=0&id=");
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("isTop", isTop);
            model.addAttribute("status", status);
            model.addAttribute("page", page);
            model.addAttribute("type", type);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/news/list";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Integer id, Model model) {
        News news = newsService.detail(id);
        model.addAttribute("news", news);
        return "/news/detail";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("news:edit");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        News news = newsService.detail(id);
        if (news.getPicture() != null) {
            Upload upload = uploadService.get(news.getPicture());
            news.setPictureUrl(upload.getPath());
        }
        model.addAttribute("news", news);
        return "/news/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(NewsWithBLOBs news, Model model, HttpSession session, @RequestParam MultipartFile file) {
        try {
            UserAdmin u = (UserAdmin) session.getAttribute("user");
            Map<String, Object> map = null;
            if (file.getSize() > 0) {
                map = uploadService.save(file, 11, u.getId());
                news.setPicture(Integer.parseInt(map.get(ConstantsAdmin.ID).toString()));
            }
            if (news.getStatus() == 1 && news.getReleaseDate() == null) {
                news.setReleaseDate(new Date());
            }
            newsService.update(news);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/news/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("news:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        return "/news/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(NewsWithBLOBs news, Model model, @RequestParam MultipartFile file, HttpSession session) {
        try {
            UserAdmin u = (UserAdmin) session.getAttribute("user");
            Map<String, Object> map = null;
            if (file.getSize() > 0) {
                map = uploadService.save(file, 11, u.getId());
                news.setPicture(Integer.parseInt(map.get(ConstantsAdmin.ID).toString()));
            }
            if (news.getStatus() == 1 && news.getReleaseDate() == null) {
                news.setReleaseDate(new Date());
            }
            if (news.getCreateDate() == null) {
                news.setCreateDate(new Date());
            }
            newsService.save(news);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/news/list";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, Integer id) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("news:delete");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        newsService.delete(id);
        return "redirect:/news/list";
    }
}
