package com.goochou.p2b.admin.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.model.Icon;
import com.goochou.p2b.model.IconGroup;
import com.goochou.p2b.service.IconGroupContactService;
import com.goochou.p2b.service.IconGroupService;
import com.goochou.p2b.service.IconService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.AjaxUtil;
import com.goochou.p2b.utils.StringUtils;

@Controller
@RequestMapping(value = "/icon")
public class IconController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(IconController.class);
	
    @Resource
    private IconService iconService;
    @Resource
    private UploadService uploadService;
    @Resource
    private IconGroupService iconGroupService;
    @Resource
    private IconGroupContactService iconGroupContactService;
  
    /**
     * icon分组管理列表
     * @author sxy
     * @param model
     * @param type
     * @param status
     * @param title
     * @param version
     * @param page
     * @return
     */
    @RequestMapping(value = "/groups")
    public String query(Model model, Integer type, Integer status, String title, String version, Integer page) {
        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            if(StringUtils.isEmpty(title)){
            	title = null;
            }
            if(StringUtils.isEmpty(version)){
            	version = null;
            }
            List<IconGroup> list = iconService.query(type, status,title,version, (page - 1) * limit, limit);
            Integer count = iconService.queryCount(type, status, title, version);
            Integer pages = calcPage(count, limit);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("type", type);
            model.addAttribute("status", status);
            model.addAttribute("title", title);
            model.addAttribute("version", version);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/icon/groups";
    }

    /**
     * 分页查询icon以及对应关系列表
     * @author sxy
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String iconList(Model model, Integer page) {
        try {
        	int limit = ConstantsAdmin.PAGE_LIMIT2;
        	if (page == null) {
                page = 1;
            }
        	List<Map<String, Object>> list = iconService.list((page - 1) * limit,limit);
        	Integer count = iconService.queryListCount();
        	Integer pages = calcPage(count, limit);
            model.addAttribute("pages", pages);
            model.addAttribute("page", page);
            model.addAttribute("list", list);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/icon/list";
    }

    /**
     * 添加或编辑icon分组
     * @param model
     * @return
     */
    @RequestMapping(value = "/add/group", method = RequestMethod.GET)
    public String addGroup(Model model,Integer groupId) {
    	try{
    		if(groupId != null){
    			IconGroup group = iconGroupService.queryByGroupId(groupId);
    			List<Map<String, Object>> list = iconGroupContactService.queryByGroupId(groupId);
    			model.addAttribute("list", list);
    			model.addAttribute("group", group);
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
            logger.error(e);
        }
        return "/icon/add-group";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model,Icon icon, Integer picture,Integer page) {
    	try {
    		Map<String, Object> map = null;
            if (page == null) {
                page = 1;
            }
    		if(icon != null && icon.getId() != null){
    			map = iconService.queryById(icon.getId());
    		}
            model.addAttribute("page",page);
            model.addAttribute("icon", map);
		} catch (Exception e) {
			logger.error(e);
		}
        return "/icon/add-icon";
    }
    
    /**
     * 新增或编辑icon
     * @param linkId
     * @param icon
     * @param picture
     * @author 刘源
     * @date 2016年1月4日
     * @parameter
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addIcon(Integer linkId, Icon icon, Integer picture,Integer page) {
    	try {
            if (page == null) {
                page = 1;
            }
    		iconService.saveWithPic(linkId,icon,picture);
		} catch (Exception e) {
			logger.error(e);
		}
        return "redirect:/icon/list?page="+page;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateStatus(Integer groupId, Integer status) {
    	try {
    		iconService.updateGroupStatus(groupId, status);
		} catch (Exception e) {
			logger.error(e);
		}
        return "redirect:/icon/groups";
    }
    
    /**
     * 新增或者编辑组内容
     * @param model
     * @param groupId
     * @param title
     * @param type
     * @param homeId
     * @param home
     * @param homeIcon
     * @param meId
     * @param me
     * @param meIcon
     * @param version
     * @param status
     * @author	刘源
     * @date 2016年1月5日
     * @parameter
     * @return
     */
    @RequestMapping(value = "/saveIconGroup", method = RequestMethod.POST)
    public String saveIconGroup(Model model, Integer groupId, String title, Integer type, Integer[] homeId, Integer[] home, Integer[] homeIcon, Integer[] meId,Integer[] me, Integer[] meIcon, String version, Integer status) {
    	try {
    		/**
    		 * 参数配套使用，含有home一组，含有me是一组
    		 */
    		if(type == 0){
    			if(homeId == null){
    				homeId = new Integer[0];
    				home = new Integer[0];
    				homeIcon = new Integer[0];
    			}
    		}else {
    			if(meId == null){
    				meId = new Integer[0];
    				me = new Integer[0];
    				meIcon = new Integer[0];
    			}
    		}
    		iconService.saveIconGroup(groupId,title,type,homeId,home,homeIcon,meId,me,meIcon,version,status);
		} catch (Exception e) {
			logger.error(e);
		}
        return "redirect:/icon/groups";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam MultipartFile file, HttpServletResponse response, HttpSession session) {
        try {
            return uploadService.save(file, 9, null);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @RequestMapping(value = "/add/group", method = RequestMethod.POST)
    public String addGroup(IconGroup group) {
        return "/icon/add-group";
    }
    
    /**
     * 获取icon列表，只能获取该icon自己组的icon和未配置过组的icon
     * @param model
     * @return
     */
    @RequestMapping(value = "/getIconList", method = RequestMethod.GET)
    public void getIconList(Model model, HttpServletResponse response, Integer groupId) {
    	try {
        	List<Map<String,Object>> list= iconGroupContactService.queryGroupIcons(groupId);
        	AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    /**
     * 删除icon
     * @param model
     * @param response
     * @author 刘源
     * @date 2016年1月4日
     * @parameter
     * @return
     */
    @RequestMapping(value = "/deleteIcons", method = RequestMethod.POST)
    public String deleteIcons(Model model, HttpServletResponse response,Integer[] links, Integer[] icons, Integer[] uploads) {
    	try {
    		iconGroupContactService.deleteIcons(links,icons,uploads);
        } catch (Exception e) {
            logger.error(e);
        }
    	return "redirect:/icon/list";
    }
    
    /**
     * icon删除校验
     * @param model
     * @param response
     * @param ids
     * @author 刘源
     * @date 2016年1月4日
     * @parameter
     * @return
     */
    @RequestMapping(value = "/checkDelete", method = RequestMethod.GET)
    public void checkDelete(Model model, HttpServletResponse response, Integer[] ids) {
    	try {
    		Map<String,Object> map = new HashMap<String, Object>();
    		Integer count = iconGroupContactService.checkDelete(ids);
    		map.put("code", SUCCESS);
    		map.put("count", count);
        	AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
}
