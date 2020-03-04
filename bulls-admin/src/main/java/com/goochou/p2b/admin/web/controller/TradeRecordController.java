package com.goochou.p2b.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.constant.YaoRecordTypeEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.assets.AccountTypeEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.model.PaymentCheckView;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.TransactionRecordVO;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.PaymentCheckService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.DownloadUtils;
import com.goochou.p2b.utils.HSSFExcelUtils;

@Controller
@RequestMapping("/trade")
public class TradeRecordController extends BaseController {

	private static final Logger logger = Logger.getLogger(TradeRecordController.class);

    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private UserService userService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private PaymentCheckService paymentCheckService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, String keyword, String aoeType, Integer accountType, Date startDate, Date endDate, Integer page, HttpSession session, Integer departmentId) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("trade:all");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = null;
//			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:seealluser");
			}catch (Exception e) {
				adminId = admin.getId();
			}
//            List<Map<String, Object>> list = tradeRecordService.query(keyword, aoeType, startDate, endDate, (page - 1) * limit, limit, adminId);
//            Integer count = tradeRecordService.queryCount(keyword, aoeType, startDate, endDate, adminId);
            List<TransactionRecordVO> list = tradeRecordService.queryTradeRecord(keyword, aoeType, accountType, startDate, endDate, null,(page - 1) * limit, limit, adminId, departmentId);
            Integer count = tradeRecordService.queryTradeRecordCount(keyword, aoeType, accountType, startDate, endDate, null, adminId, departmentId);
            int pages = calcPage(count.intValue(), limit);
            model.addAttribute("startDate", startDate != null ? DateFormatTools.dateToStr2(startDate) : null);
            model.addAttribute("endDate", endDate != null ? DateFormatTools.dateToStr2(endDate) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("aoeType", aoeType);
            model.addAttribute("accountType", accountType);
            model.addAttribute("keyword", keyword);
            model.addAttribute("accountOperate", AccountOperateEnum.values());
            model.addAttribute("accountTypes", AccountTypeEnum.values());
            model.addAttribute("departmentId", departmentId);
			model.addAttribute("departments", departmentService.getShowDepartments(admin.getDepartmentId()));

        } catch (Exception e) {
            logger.error(e);
        }
        return "trade/list";
    }
    
    @RequestMapping(value = "/list/export", method = RequestMethod.GET)
    public void exportList(Model model,HttpServletResponse response, String keyword, String aoeType, Integer accountType, Date startDate, Date endDate,  HttpSession session, Integer departmentId) {
    	try {
	        Subject subject = SecurityUtils.getSubject();
	        try {
	        	subject.checkPermission("trade:list:export");
	        }catch (Exception e) {
	            throw new Exception("您没有权限操作");
	        }
	        UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId = null;
//			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:seealluser");
			}catch (Exception e) {
				adminId = admin.getId();
			}
	        List<TransactionRecordVO> list = tradeRecordService.queryTradeRecord(keyword, aoeType, accountType, startDate, endDate, null,null,null, adminId, departmentId);
        
			LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
			propertyHeaderMap.put("id", "ID");
			propertyHeaderMap.put("trueName", "真名");
			propertyHeaderMap.put("phone", "手机号");
			propertyHeaderMap.put("amount", "交易金额");
			propertyHeaderMap.put("accountTypeMsg", "交易账户");
			propertyHeaderMap.put("accountOperateTypeMsg", "账户操作");
			propertyHeaderMap.put("balanceAmount", "账户可用余额");
			propertyHeaderMap.put("frozenAmount", "冻结余额");
			propertyHeaderMap.put("creditAmount", "授信资金");
			propertyHeaderMap.put("frozenCreditAmount", "冻结授信资金");
			propertyHeaderMap.put("adminAoeTypeMsg", "交易类型");
			propertyHeaderMap.put("createDate", "交易时间");

			HSSFExcelUtils.ExpExs((startDate != null ? DateFormatTools.dateToStr2(startDate) : "")+"-"+ (endDate != null ? DateFormatTools.dateToStr2(endDate) : "")
							+ "交易记录",
					propertyHeaderMap, list, response);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			DownloadUtils.closeResponseOutput(response);
		}

    }
    /**
     * @Description: 重写摇一摇记录  后台查询功能.
     * @date 2016/11/10
     * @author 王信
     */
    @RequestMapping(value = "/shakeRecord", method = RequestMethod.GET)
    public String shakeRecord(Model model, String keyword, Integer type, Date startTime, Date endTime, Integer page, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("trade:shakeRecord");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = null;
//            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:seealluser");
            }catch (Exception e) {
            	adminId = admin.getId();
            }
            long start = System.currentTimeMillis();
            List<Map<String, Object>> list = tradeRecordService.selectShakeRecord(keyword, type, startTime, endTime, (page - 1) * limit, limit, adminId);
            System.out.println("时间-----" + (System.currentTimeMillis() - start));
            Integer count = tradeRecordService.selectShakeRecordCount(keyword, type, startTime, endTime, adminId);
            int pages = calcPage(count.intValue(), limit);
            model.addAttribute("startTime", startTime != null ? new
                    SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            model.addAttribute("endTime", endTime != null ? new
                    SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("type", type);
            model.addAttribute("keyword", keyword);
            model.addAttribute("recordTypes", YaoRecordTypeEnum.values());
        } catch (Exception e) {
            logger.error(e);
        }
        return "trade/shakeRecord";
    }

    @RequestMapping(value = "/shakeCount", method = RequestMethod.GET)
    public String shakeCount(Model model, String phone) {
        if (phone == null || phone.length() != 11) {
            return "redirect:/trade/shakeRecord";
        }
        Map<String, Object> map = tradeRecordService.selectShakeCount(phone);
        model.addAttribute("map", map);
        model.addAttribute("phone", phone);
        return "trade/shakeCount";
    }

    @RequestMapping(value = "/checkPaymentList", method = RequestMethod.GET)
    public String checkPaymentList(Model model, Integer page, String orderNo, Date startDate, Date endDate, String channel, Integer warningType) {
        int limit = 20;
        if (page == null) {
            page = 1;
        }
        Date endDateNew = DateUtil.getAbsoluteDate(endDate, Calendar.DATE, 1);
        
        List<PaymentCheckView> list = paymentCheckService.listPaymentCheckView(orderNo, startDate, endDateNew, channel, warningType, (page - 1) * limit, limit);
        Integer count = paymentCheckService.countPaymentCheckView(orderNo, startDate, endDateNew, channel, warningType);
        
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("pages",calcPage(count, limit));
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("startDate", startDate != null ? DateFormatTools.dateToStr2(startDate) : null);
        model.addAttribute("endDate", endDate != null ? DateFormatTools.dateToStr2(endDate) : null);
        model.addAttribute("channels", OutPayEnum.values());
        model.addAttribute("channel", channel);
        model.addAttribute("warningType", warningType);
        
        return "trade/checkPaymentList";
    }

    /**
     * @desc 根据日期修复账单
     * @author wangyun
     * @param model
     * @param repairDate
     * @return
     */
	@RequestMapping(value = "/repairCheckPayment", method = RequestMethod.GET)
    @ResponseBody
    public String repairCheckPayment(Model model, Date repairDate, HttpServletResponse response) {
    	try {
    		if(repairDate != null && repairDate.after(DateUtil.getDateAfter(new Date(), -1))) {
    			throw new Exception("日期错误！");
    		}
    		
    		// 查询全部账单,没有则生成从2019-07-09至今前一天的全部账单数据
    		Date maxDate = paymentCheckService.getPaymentCheckMapper().selectMaxDayPayment(repairDate);
    		if(maxDate == null && repairDate == null) { // 生成全部数据
    			Date beginDate = DateUtil.dateFormat.parse("2019-07-09");
    			Date endDate = new Date();
    			int days = DateUtil.dateToDateDay(beginDate, endDate);
    			
    			for (int i = 0; i < days; i++) {//生成从2019-07-09至今前一天
    				paymentCheckService.doLoadFileByDate((Date)DateUtil.dateAddType2("day", i ,beginDate));
    			}
    			
    		} else if(repairDate != null && maxDate == null) {// 该日期账单未生成
    			paymentCheckService.doLoadFileByDate(repairDate);
    			
    		} else {
    			throw new Exception(DateUtil.dateFormat.format(maxDate)+"账单已存在,不重复生成数据");
    		}
    		
//    		paymentCheckService.recheckOrGeneratePayment(repairDate);
    		return "修复账单成功";
		} catch (Exception e) {
			logger.error(e,e);
			e.printStackTrace();
			return "修复账单失败," + e.getMessage();
		}
    
    }
}
