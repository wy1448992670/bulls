package com.goochou.p2b.admin.web.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.ProjectActivityRecordVO;
import com.goochou.p2b.model.vo.ProjectActivitySettingVO;
import com.goochou.p2b.service.ProjectActivitySettingService;
import com.goochou.p2b.utils.StringUtil;

@Controller
@RequestMapping(value = "/luckeyCode")
public class ProjectActivityRecordController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(ProjectActivityRecordController.class);
	
	@Resource
	private ProjectActivitySettingService projectActivitySettingService;
    
    /**
     * 幸运号码列表
     * @param model
     * @return list
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,Integer page,Integer period) {

        if (page == null) {
            page = 1;
        }
        List<ProjectActivitySettingVO> list = projectActivitySettingService.queryProjectActivityRecordForAdmin(period,(page-1)*20,20);
        Integer count = projectActivitySettingService.queryProjectActivityRecordCountForAdmin(period);
        model.addAttribute("list", list);
        model.addAttribute("pages", calcPage(count, 20));
        model.addAttribute("page", page);
        model.addAttribute("period", period);
    	return "/activity/projectActivitySettingList";
    }

    /**
     * 设置奖品
     * @param model
     * @return
     */
    @RequestMapping(value = "/give/prize")
    @ResponseBody
    public Map<String, Object> givePrize(HttpServletRequest request, HttpSession session, Model model) {
    	
    	UserAdmin admin = (UserAdmin) session.getAttribute("user");
    	String settingId = request.getParameter("settingId");
    	String luckyCode = request.getParameter("luckyCode");
    	String period = request.getParameter("period");
    	
    	Map<String, Object> result = new HashMap<>();
		result.put("msg", "设置中奖号码失败");
		result.put("code", "fail");
		
    	if(StringUtil.isNull(settingId) || StringUtil.isNull(luckyCode) || StringUtil.isNull(period)){
    		logger.info("givePrize : 设置奖品失败, 参数错误");
    		return result;
    	}
    	
    	return projectActivitySettingService.savePrize(Integer.parseInt(settingId), Integer.parseInt(luckyCode), admin.getId(), Integer.parseInt(period));
    }
    
    /**
     * 根据期数获取幸运号码详情
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, Model model, Integer page) {
    	
    	Integer limit = 20;
        if (page == null || page == 0) {
            page = 1;
        }
    	
    	String period = request.getParameter("period");
    	if(StringUtil.isNull(period)){
    		logger.info("givePrize : 设置奖品失败, 参数错误");
    		return "/activity/projectActivityRecordDetail";
    	}
    	List<ProjectActivityRecordVO> list = projectActivitySettingService.queryProjectActivityDetail(Integer.parseInt(period), (page - 1) * limit, limit);
    	
    	Integer count = projectActivitySettingService.queryProjectActivityDetailCount(Integer.parseInt(period));
    	int pages = calcPage(count, limit);
    	model.addAttribute("list", list);
    	model.addAttribute("period", period);
    	model.addAttribute("page", page);
    	model.addAttribute("pages", pages);
    	return "/activity/projectActivityRecordDetail";
        
    }
    
    @RequestMapping(value = "/all/code")
    @ResponseBody
    public List<ProjectActivityRecordVO> allCode(Integer period, Integer userId){
    	
    	return projectActivitySettingService.queryProjectActivityByUser(period, userId);
    }
    

}