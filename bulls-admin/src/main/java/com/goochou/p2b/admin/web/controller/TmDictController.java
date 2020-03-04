package com.goochou.p2b.admin.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.dao.AdminLogMapper;
import com.goochou.p2b.model.TmDict;
import com.goochou.p2b.service.TmDictService;

@Controller
@RequestMapping("/dict")
public class TmDictController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(TmDictController.class);

    @Resource
    private TmDictService tmDictService;
    @Resource
    private AdminLogMapper adminLogMapper;

    /**
     * 字典列表
     * @author sxy
     * @param model
     * @param keyword
     * @param page
     * @param name
     * @param key
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String dictList(Model model, @RequestParam(required = false) String keyword, Integer page, String name, String key) {
        try {
            if(page==null){
                page=1;
            }
            Integer limit= ConstantsAdmin.PAGE_LIMIT2;
            List<TmDict> list=tmDictService.queryDictList(keyword, (page - 1) * limit, limit, name, key);
            Integer count=tmDictService.queryDictListCount(keyword, name, key);
            model.addAttribute("list",list);
            model.addAttribute("page",page);
            model.addAttribute("keyword", keyword);
            model.addAttribute("name", name);
            model.addAttribute("key", key);
            model.addAttribute("pages", calcPage(count.intValue(), limit));
        } catch (Exception e) {
        	logger.error(e);
        }
        return "/project/dictList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model,Integer id) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("dict:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        TmDict tmDict = tmDictService.get(id);
        model.addAttribute("tmDict", tmDict);
        return "project/addDict";
    }

    @RequestMapping(value = "/addDict", method = RequestMethod.POST)
    public String addOrEdit(@ModelAttribute TmDict tmDict) {
        if(tmDict.getId()==null){
            tmDictService.save(tmDict);
        }else {
            tmDictService.updateDict(tmDict);
        }
        return "redirect:/dict/list";
    }

    @ResponseBody
    @RequestMapping(value = "/reflush")
    public boolean reflush() {
        boolean flag = true;
        
        tmDictService.doFulshCache();
        
        return flag;
    }

    @ResponseBody
    @RequestMapping(value = "/reflushKey")
    public boolean reflushKey(String key) {
        boolean flag = false;
        try {
            flag = memcachedManager.delete(Constants.PROJECT_CLASS_LIST);
            if (!flag) {
                System.out.println("初始化memcached.server1.host " + key + " 失败");
            } else {
                System.out.println("初始化memcached.server1.host " + key + " 成功..");
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化缓存异常", e);
        }
        return flag;
    }

}
