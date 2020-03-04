package com.goochou.p2b.admin.web.controller;


import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.utils.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.InterestHasDividendedEnum;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.model.Interest;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.InvestmentOrderVO;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.InterestService;
import com.goochou.p2b.service.InvestmentService;

@Controller
@RequestMapping("/investment")
public class InvestmentController extends BaseController {

    private static final Logger logger = Logger.getLogger(InvestmentController.class);

    @Resource
    private InvestmentService investmentService;

    @Resource
    private InterestService interestService;
    
    @Resource
    private DepartmentService departmentService;

    /**
     * @description 牛只订单列表
     * @author shuys
     * @date 2019/5/21
     * @param model
     * @param page
     * @param keyword
     * @param orderNo
     * @param orderStatus
     * @param payStatus
     * @param startDate
     * @param endDate
     * @return java.lang.String
    */
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public String orderList(Model model, HttpSession session, @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false)String keyword,
                                      @RequestParam(required = false)String orderNo,
                                      @RequestParam(required = false)Integer orderStatus,
                                      @RequestParam(required = false)Integer payStatus,
                                      @RequestParam(required = false) Date startDate,
                                      @RequestParam(required = false) Date endDate,
                                      @RequestParam(required = false) Date startDueTime,
                                      @RequestParam(required = false) Date endDueTime,
                                      @RequestParam(required = false) Date startBuyBackTime,
                                      @RequestParam(required = false) Date endBuyBackTime,
                                      @RequestParam(required = false) Integer yueLing,
                                      @RequestParam(required = false) Integer departmentId) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("investment:view");
        }catch (Exception e) {
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
        Integer limit = 10;
//        List<InvestmentOrderVO> list = investmentService.queryBullsOrderList(keyword, orderNo, null, orderStatus, payStatus, startDate, endDate,(page-1)*limit, limit, null,yueLing);
//        int count = investmentService.queryBullsOrderCount(keyword, orderNo, null, orderStatus, payStatus, startDate, endDate, null,yueLing);
        List<Integer> orderStatusArr = null;
        if (orderStatus != null) {
            orderStatusArr = new ArrayList<>(1);
            orderStatusArr.add(orderStatus);
        }

        startDate = DateUtil.getDayStartDate(startDate);
        endDate = DateUtil.getDayStartDate(endDate);
        Date endDateNew = DateUtil.getAbsoluteDate(endDate, Calendar.DATE, 1);
        startDueTime = DateUtil.getDayStartDate(startDueTime);
        endDueTime = DateUtil.getDayStartDate(endDueTime);
        Date endDueTimeNew = DateUtil.getAbsoluteDate(endDueTime, Calendar.DATE, 1);
        startBuyBackTime = DateUtil.getDayStartDate(startBuyBackTime);
        endBuyBackTime = DateUtil.getDayStartDate(endBuyBackTime);
        Date endBuyBackTimeNew = DateUtil.getAbsoluteDate(endBuyBackTime, Calendar.DATE, 1);
        
        List<InvestmentOrderVO> list = investmentService.listInvestment(keyword, orderNo, orderStatusArr, payStatus, startDate, endDateNew,
        		startDueTime,endDueTimeNew,startBuyBackTime,endBuyBackTimeNew,(page-1)*limit, limit, null, yueLing,adminId,departmentId);
        long count = investmentService.countInvestment(keyword, orderNo, orderStatusArr, payStatus, startDate, endDateNew,
        		startDueTime,endDueTimeNew,startBuyBackTime,endBuyBackTimeNew,null, yueLing,adminId,departmentId);

        model.addAttribute("page",page);
        model.addAttribute("startDate", startDate != null ? DateFormatTools.dateToStr2(startDate) : null);
        model.addAttribute("endDate", endDate != null ? DateFormatTools.dateToStr2(endDate) : null);
        model.addAttribute("startDueTime", startDueTime != null ? DateFormatTools.dateToStr2(startDueTime) : null);
        model.addAttribute("endDueTime", endDueTime != null ? DateFormatTools.dateToStr2(endDueTime) : null);
        model.addAttribute("startBuyBackTime", startBuyBackTime != null ? DateFormatTools.dateToStr2(startBuyBackTime) : null);
        model.addAttribute("endBuyBackTime", endBuyBackTime != null ? DateFormatTools.dateToStr2(endBuyBackTime) : null);
        model.addAttribute("pages",calcPage(count, limit));
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("payStatus", payStatus);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("yueLing", yueLing);
        model.addAttribute("investmentState", InvestmentStateEnum.values());
        model.addAttribute("investPayState", InvestPayStateEnum.values());
        model.addAttribute("departmentId", departmentId);
		model.addAttribute("departments", departmentService.getShowDepartments(admin.getDepartmentId()));
        return "/investment/orderList";
    }

    /**
     * @date 2019年8月14日
     * @author wangyun
     * @time 上午10:12:41
     * @Description
     *
     * @param model
     * @param page
     * @param keyword
     * @param orderNo
     * @param orderStatus
     * @param payStatus
     * @param startDate
     * @param endDate
     * @param yueLing
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/orderListReport", method = RequestMethod.GET)
    public String orderListReport(Model model, HttpServletResponse response,HttpSession session,
                                      @RequestParam(required = false)String keyword,
                                      @RequestParam(required = false)String orderNo,
                                      @RequestParam(required = false)List<Integer> orderStatus,
                                      @RequestParam(required = false)Integer payStatus,
                                      @RequestParam(required = false) Date startDate,
                                      @RequestParam(required = false) Date endDate,
                                      @RequestParam(required = false) Date startDueTime,
                                      @RequestParam(required = false) Date endDueTime,
                                      @RequestParam(required = false) Date startBuyBackTime,
                                      @RequestParam(required = false) Date endBuyBackTime,
                                      @RequestParam(required = false) Integer yueLing,
                                      @RequestParam(required = false) Integer departmentId) throws Exception {

        Subject subject = SecurityUtils.getSubject();

        try {
        	try {
    			subject.checkPermission("investment:export");
    		} catch (Exception e) {
    			model.addAttribute("error", "您没有权限操作");
    			return "error";
    		}
        	UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId=null;
            try {
    			subject.checkPermission("user:seealluser");
    		} catch (Exception e) {
    			adminId=admin.getId();
    		}
//	        List<InvestmentOrderVO> list = investmentService.queryBullsOrderList(keyword, orderNo, null, orderStatus, payStatus, startDate, endDate, null, null, null,yueLing);

        	startDate = DateUtil.getDayStartDate(startDate);
            endDate = DateUtil.getDayStartDate(endDate);
            Date endDateNew = DateUtil.getAbsoluteDate(endDate, Calendar.DATE, 1);
            startDueTime = DateUtil.getDayStartDate(startDueTime);
            endDueTime = DateUtil.getDayStartDate(endDueTime);
            Date endDueTimeNew = DateUtil.getAbsoluteDate(endDueTime, Calendar.DATE, 1);
            startBuyBackTime = DateUtil.getDayStartDate(startBuyBackTime);
            endBuyBackTime = DateUtil.getDayStartDate(endBuyBackTime);
            Date endBuyBackTimeNew = DateUtil.getAbsoluteDate(endBuyBackTime, Calendar.DATE, 1);
            
            List<InvestmentOrderVO> list = investmentService.listInvestment(keyword, orderNo, orderStatus, payStatus, startDate, endDateNew,
            		startDueTime,endDueTimeNew,startBuyBackTime,endBuyBackTimeNew,null, null, null, yueLing,adminId,departmentId);

        	LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
	        propertyHeaderMap.put("id", "ID");
	        propertyHeaderMap.put("orderNo", "订单号");
	        propertyHeaderMap.put("trueName", "客户名称");
	        propertyHeaderMap.put("deadline", "饲养日期");
	        propertyHeaderMap.put("num", "数量");
	        propertyHeaderMap.put("manageFee", "管理费");
	        propertyHeaderMap.put("raiseFee", "饲养费");
	        propertyHeaderMap.put("cowAmount", "购牛款（元）");
	        propertyHeaderMap.put("totalAmount", "合计金额（元）");
	        propertyHeaderMap.put("balancePayMoney", "余额支付金额（元）");
	        propertyHeaderMap.put("hongbaoMoney", "红包金额（元）");
	        propertyHeaderMap.put("remainAmount", "需支付金额（元）");
	        propertyHeaderMap.put("addInterest", "加息总金额（元）");
	        propertyHeaderMap.put("payStatusStr", "支付状态");
	        propertyHeaderMap.put("orderStatusStr", "订单状态");
	        propertyHeaderMap.put("limitDays", "饲养期限(天)");
	        propertyHeaderMap.put("payTime", "支付日期");
            propertyHeaderMap.put("dueTime", "到期日期");
            propertyHeaderMap.put("buyBackTime", "回购日期");
	        propertyHeaderMap.put("earNumber", "耳标号");
	        propertyHeaderMap.put("yueLing", "牛只月龄");
	        propertyHeaderMap.put("parentBuyBackTime", "上次回购日期");
	        propertyHeaderMap.put("parentBuyBackAmount", "上次回购价款");

	        HSSFExcelUtils.ExpExs("订单查询列表", propertyHeaderMap, list, response);
		} catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        } finally {
        	DownloadUtils.closeResponseOutput(response);
        }
        return null;
    }

    /**
     * @description 牛只订单详情
     * @author shuys
     * @date 2019/5/21
     * @param id
     * @param model
     * @return java.lang.String
    */
    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public String detail(Integer id, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("investment:orderDetail");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }

            InvestmentOrderVO investment = investmentService.queryOrderDetail(id);
            model.addAttribute("investment", investment);
            model.addAttribute("investPayState", InvestPayStateEnum.values());
            model.addAttribute("investmentState", InvestmentStateEnum.values());
        } catch (Exception e) {
            logger.error(e);
        }
        return "/investment/orderDetail";
    }


    /**
     * @description 派息明细
     * @author shuys
     * @date 2019/5/21
     * @param investmentId
     * @param response
     * @return void
    */
    @RequestMapping(value = "/interest", method = RequestMethod.GET)
    public void tradeRecord(Integer investmentId, Integer page, HttpServletResponse response) {
        try {
            int limit = 10;
            List<Interest> list = interestService.getByInvestmentId(investmentId,(page-1)*limit, limit);
            int count = interestService.getCountByInvestmentId(investmentId);
            int pages = calcPage(count, limit);
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            map.put("page", page);
            map.put("pages", pages);
            map.put("dividended", this.convertInterestHasDividended());
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * @description 派息类型转换
     * @author shuys
     * @date 2019/5/29
     * @param
     * @return java.util.Map<java.lang.String,java.lang.String>
    */
    private List<Map<String, String>> convertInterestHasDividended() {
        List<Map<String, String>> result = new ArrayList<>();
        InterestHasDividendedEnum[] emums = InterestHasDividendedEnum.values();
        for (InterestHasDividendedEnum e: emums) {
            Map<String, String> map = new HashMap();
            map.put("code", String.valueOf(e.getFeatureType()));
            map.put("description", e.getDescription());
            result.add(map);
        }
        return result;
    }

    /**
             * 用户详情-物权订单列表
     * @author sxy
     * @param response
     * @param orderStatus
     * @param page
     */
    @RequestMapping(value = "/listOrderAjax", method = RequestMethod.GET)
    public void listOrderAjax(HttpServletResponse response, Integer orderStatus, Integer page, Integer userId) {
        try {
            int limit = 5;
            page = page == null ? 1 : page;

            List<InvestmentOrderVO> list = investmentService.queryBullsOrderList(null, null, null, orderStatus, null, null, null, (page-1)*limit, limit, userId,null);
            Integer count = investmentService.queryBullsOrderCount(null, null, null, orderStatus, null, null, null, userId,null);

            int pages = calcPage(count, limit);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("page", page);
            map.put("pages", pages);
            map.put("list", list);
            map.put("status", orderStatus);
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
