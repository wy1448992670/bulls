package com.goochou.p2b.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

import com.goochou.p2b.model.AdminRole;
import com.goochou.p2b.model.BaseResult;
import com.goochou.p2b.model.CouponTemplate;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.AdminRoleService;
import com.goochou.p2b.service.RateCouponService;

@Controller
@RequestMapping("/coupon")
public class RateCouponController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(RateCouponController.class);

    @Resource
    private RateCouponService rateCouponService;
    
    @Resource
    private AdminRoleService adminRoleService;
    

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, String keyword, Integer source, Integer type, Date startTime, Date endTime, Integer page, Integer days, HttpSession session) {
        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = null;
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:seealluser");
            }catch (Exception e) {
            	adminId = admin.getId();
            }
            List<Map<String, Object>> list = rateCouponService.query(keyword, source, type, startTime, endTime, (page - 1) * limit, limit, days, adminId);
            Integer count = rateCouponService.queryCount(keyword, source, type, startTime, endTime, days, adminId);
            Integer sum = rateCouponService.querySum(keyword, source, type, startTime, endTime, days, adminId);
            int pages = calcPage(count.intValue(), limit);
            model.addAttribute("startTime", startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            model.addAttribute("endTime", endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("source", source);
            model.addAttribute("type", type);
            model.addAttribute("count", count);
            model.addAttribute("sum", sum);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error(e);
        }
        return "rateCoupon/list";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String sendRateCoupon(HttpSession session, Model model) {
    	
    	try {
    		UserAdmin userAdmin = (UserAdmin) session.getAttribute("user");
    		
    		//判断是否是坐席
    		if(userAdmin != null){
    			List<AdminRole> adminRoleList = adminRoleService.getByUserId(userAdmin.getId());
    			if(adminRoleList != null && adminRoleList.size() > 0){
    				for(AdminRole adminRole : adminRoleList){
        				if(adminRole.getRoleId() == 18){
        					model.addAttribute("isZx", "isZx");
        					break;
        				}
        			}
    			}
    			
    		}
        	
		} catch (Exception e) {
			logger.error(e);
		}
        return "rateCoupon/sendCoupon";
    }

    /**
     * 派发加息券
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String sendRateCoupon(Double rate, Integer type, String userId, String title, String descript, String couponTitle,
                                 Integer days, HttpServletRequest request, String template,String rateCouponType,
                                 @RequestParam MultipartFile file,
                                 HttpServletResponse response, HttpSession session) {
        UserAdmin ua = (UserAdmin) session.getAttribute("user");

        try {
            rateCouponService.saveBatchCoupon(couponTitle, file, descript, type, userId, template, title, ua.getId(),rateCouponType);
        } catch (Exception e) {
            logger.error(e);
            return "redirect:/coupon/send.action";
        }
        return "redirect:/coupon/list.action";
    }
    
	/**
     * 
     * <p>领券中心</p> 
     * @author: lxfeng  
     * @date: Created on 2018-6-21 上午10:27:00
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/couponList", method = RequestMethod.GET)
    public String couponList(Model model, Integer page, Integer type, Integer stockBalance,
    		Integer minDays, Long minAmount, String keyword){
    	
    	int limit = 20;
        if (page == null) {
            page = 1;
        }
        BaseResult result = rateCouponService.couponTemplateList(type, stockBalance, minDays, 
        		minAmount, keyword, (page - 1) * limit, limit);
        if (result.isSuccess()) {
			List<CouponTemplate> list = (List<CouponTemplate>) result.getMap().get("list");
			Integer count = (Integer) result.getMap().get("count");
			int pages = calcPage(count.intValue(), limit);
			model.addAttribute("pages", pages);
	        model.addAttribute("list", list);
	        model.addAttribute("page", page);
	        model.addAttribute("type", type);
	        model.addAttribute("stockBalance", stockBalance);
	        model.addAttribute("minDays", minDays);
	        model.addAttribute("minAmount", minAmount);
	        model.addAttribute("keyword", keyword);
		}
        
    	return "/rateCoupon/couponList";
    }
    
    /**
     * 
     * <p>添加</p> 
     * @return
     * @author: lxfeng  
     * @date: Created on 2018-6-21 下午1:23:15
     */
    @RequestMapping(value = "/couponAdd", method = RequestMethod.GET)
    public String couponAdd(){
    	return "/rateCoupon/couponAdd";
    }
    
    
    @RequestMapping(value = "/couponAdd", method = RequestMethod.POST)
    public String couponAdd(CouponTemplate couponTemplate){
    	rateCouponService.addCouponTemplate(couponTemplate);
    	return "redirect:/coupon/couponList";
    }
    
    @RequestMapping(value = "/couponEdit", method = RequestMethod.GET)
    public String couponEdit(Model model,String templateId){
    	CouponTemplate couponTemplate = rateCouponService.getCouponTemplate(templateId);
    	model.addAttribute("couponTemplate", couponTemplate);
    	return "/rateCoupon/couponEdit";
    }
    
    @RequestMapping(value = "/couponEdit", method = RequestMethod.POST)
    public String couponEdit(CouponTemplate couponTemplate,Integer addNum){
    	couponTemplate.setStockBalance(couponTemplate.getStockBalance() + addNum);
    	rateCouponService.updateCouponTemplate(couponTemplate);
    	return "redirect:/coupon/couponList";
    }
    
    @RequestMapping(value = "/couponDeleted", method = RequestMethod.GET)
    public String deleted(String templateId){
    	rateCouponService.updateCouponTemplate(templateId);
    	return "redirect:/coupon/couponList";
    }
}
