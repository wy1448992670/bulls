package com.goochou.p2b.admin.web.controller;

import java.util.Date;
import java.util.List;

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

import com.goochou.p2b.constant.InterestHasDividendedEnum;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.InterestVO;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.InterestService;
import com.goochou.p2b.utils.DateFormatTools;

@Controller
@RequestMapping("/interest")
public class InterestController extends BaseController {

    private static final Logger logger = Logger.getLogger(InterestController.class);

    @Resource
    private InterestService interestService;
	@Resource
    private DepartmentService departmentService;
	
    /**
     * @description 利息查询
     * @author shuys
     * @date 2019/5/21
     * @param model
     * @param page
     * @param keyword
     * @param orderNo
     * @param investmentStartDate
     * @param investmentEndDate
     * @param hasDividended
     * @param interestStartDate
     * @param interestEndDate
     * @return java.lang.String
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String interestList(Model model, HttpSession session, 
    						@RequestParam(required = false) Integer page,
                            @RequestParam(required = false)String keyword,
                            @RequestParam(required = false)String orderNo,
                               @RequestParam(required = false) Date investmentStartDate,
                               @RequestParam(required = false) Date investmentEndDate,
                            @RequestParam(required = false)Integer hasDividended,
                               @RequestParam(required = false) Date interestStartDate,
                               @RequestParam(required = false) Date interestEndDate,
                               @RequestParam(required = false) Integer departmentId) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("home:all");
        } catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        UserAdmin admin = (UserAdmin) session.getAttribute("user");
        Integer adminId=null;
        try {
			subject.checkPermission("user:seealluser");
		} catch (Exception e) {
			adminId=admin.getId();
		}
        if (page==null || page <=1){
            page =1;
        }
        int limit = 20;
        List<InterestVO> list = interestService.queryInterestList(keyword, orderNo, investmentStartDate, investmentEndDate,
        		hasDividended, interestStartDate, interestEndDate,(page-1)*limit, limit, adminId, departmentId);
        int count = interestService.queryInterestCount(keyword, orderNo, investmentStartDate, investmentEndDate, hasDividended,
        		interestStartDate, interestEndDate, adminId, departmentId);

        model.addAttribute("page",page);
        model.addAttribute("investmentStartDate", investmentStartDate != null ? DateFormatTools.dateToStr2(investmentStartDate) : null);
        model.addAttribute("investmentEndDate", investmentEndDate != null ? DateFormatTools.dateToStr2(investmentEndDate) : null);
        model.addAttribute("interestStartDate", interestStartDate != null ? DateFormatTools.dateToStr2(interestStartDate) : null);
        model.addAttribute("interestEndDate", interestEndDate != null ? DateFormatTools.dateToStr2(interestEndDate) : null);
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("hasDividended", hasDividended);
        model.addAttribute("pages",calcPage(count,limit));
        model.addAttribute("list", list);
        model.addAttribute("dividended", InterestHasDividendedEnum.values());
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("departments", departmentService.getShowDepartments(admin.getDepartmentId()));

        return "/interest/list";
    }

}
