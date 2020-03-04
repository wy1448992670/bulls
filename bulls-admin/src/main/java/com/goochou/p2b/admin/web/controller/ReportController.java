package com.goochou.p2b.admin.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.constant.*;
import com.goochou.p2b.model.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.PropertySalesStoreStatusViewMapper;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.AssetsExample;
import com.goochou.p2b.model.Interest;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.TradeRecord;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.WLimit;
import com.goochou.p2b.model.Withdraw;
import com.goochou.p2b.model.vo.CapitalDetail;
import com.goochou.p2b.model.vo.HuoWithdrawVo;
import com.goochou.p2b.model.vo.InvestStatementVO;
import com.goochou.p2b.model.vo.InvestmentSearchCondition;
import com.goochou.p2b.model.vo.InvestmentVo;
import com.goochou.p2b.model.vo.PropertySalesStoreStatusView;
import com.goochou.p2b.model.vo.PropertySalesStoreStatusViewExample;
import com.goochou.p2b.model.vo.RechargeVO;
import com.goochou.p2b.model.vo.TradeRecordVO;
import com.goochou.p2b.model.vo.UserVO;
import com.goochou.p2b.model.goods.GoodsOrderRefund;
import com.goochou.p2b.model.vo.CapitalDetail;
import com.goochou.p2b.model.vo.HuoWithdrawVo;
import com.goochou.p2b.model.vo.InvestStatementVO;
import com.goochou.p2b.model.vo.InvestmentSearchCondition;
import com.goochou.p2b.model.vo.InvestmentVo;
import com.goochou.p2b.model.vo.ProjectView;
import com.goochou.p2b.model.vo.PropertySalesStoreStatusView;
import com.goochou.p2b.model.vo.PropertySalesStoreStatusViewExample;
import com.goochou.p2b.model.vo.RechargeVO;
import com.goochou.p2b.model.vo.TradeRecordVO;
import com.goochou.p2b.model.vo.UserVO;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.CustomerListService;
import com.goochou.p2b.service.GoodsOrderDetailService;
import com.goochou.p2b.service.GoodsOrderRefundService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.InterestService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.JobService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.service.UserInviteService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.WithdrawService;
import com.goochou.p2b.service.YaoRecordService;
import com.goochou.p2b.utils.AjaxUtil;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.CommonUtils;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.DownloadUtils;
import com.goochou.p2b.utils.HSSFExcelUtils;
import com.goochou.p2b.utils.StringUtil;
import com.goochou.p2b.utils.http.HttpsUtil;

import net.sf.jxls.transformer.XLSTransformer;

@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController {

	private static final Logger logger = Logger.getLogger(ReportController.class);

	// 通道费率
	private static final double CHANNEL_RATE = 0.0025;

    @Resource
    private UserService userService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private WithdrawService withdrawService;
    @Resource
    private UserAdminService userAdminService;
    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private AssetsService assetsService;
    @Resource
    private RechargeService rechargeService;
    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private YaoRecordService yaoRecordService;
    @Resource
    private InterestService interestService;
    @Resource
    private ProjectService projectService;
    @Resource
    private ActivityService activityService;
    @Resource
    private UserInviteService userInviteService;
    @Resource
    private CustomerListService customerListService;
    @Resource
    private InterestService interestervice;
    @Resource
    PropertySalesStoreStatusViewMapper propertySalesStoreStatusViewMapper;
    @Resource
    GoodsOrderDetailService goodsOrderDetailService;
    @Resource
    private JobService jobService;
    @Resource
    GoodsOrderService goodsOrderService;
    @Resource
    GoodsOrderRefundService goodsOrderRefundService;
    @Resource
    AssetsMapper assetsMapper;
    
    /**
     * @param model
     * @param response
     * @param day
     * @Title: ReportController.java
     * @Package com.goochou.p2b.admin.web.controller
     * @Description: 查看每天  查询用户每天资金情况，总充值，总投资，总提现
     * @author 王信
     * @date 2016年1月6日 下午5:15:46
     * @version V1.0
     */
    @RequestMapping(value = "/investmentDetails", method = RequestMethod.GET)
    public String investmentDetails(Model model, HttpServletResponse response, @RequestParam(required = false) Date day, HttpSession session) {
        try {
            System.out.println("测试是否提交成功了");
            if (day == null) {
                day = new Date();
            }
            String dayString = DateFormatTools.jumpOneDayToString(day, -1);
            List<Map<String, Object>> list = rechargeService.selectUsersAssetsDetailsDay(dayString);
            double sumRecharge = 0;
            double sumWithdraw = 0;
            double sumAssets = 0;
            for (Map<String, Object> map : list) {
                sumRecharge += Double.valueOf(map.get("sumr").toString());
                sumWithdraw += Double.valueOf(map.get("sumw").toString());
                sumAssets += Double.valueOf(map.get("suma").toString());
            }
            model.addAttribute("sumRecharge", sumRecharge);
            model.addAttribute("sumWithdraw", sumWithdraw);
            model.addAttribute("sumAssets", sumAssets);
            model.addAttribute("list", list);
            model.addAttribute("day", day);
            return "report/investmentDetails";
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * @param model
     * @param response
     * @return
     * @Title: ReportController.java
     * @Package com.goochou.p2b.admin.web.controller
     * @Description: 查询用户每天资金情况，总充值，总投资，总提现  导出
     * @author A18ccms A18ccms_gmail_com
     * @date 2016年1月6日 下午3:46:56
     * @version V1.0
     */
    @RequestMapping(value = "export/investmentDetailsDay", method = RequestMethod.GET)
    public void investmentDetailsDay(Model model, HttpServletResponse response, @RequestParam(required = false) Date day, HttpSession session) {
        try {
            if (day == null) {
                day = new Date();
            }
            String dayString = DateFormatTools.jumpOneDayToString(day, -1);
            List<Map<String, Object>> list = rechargeService.selectUsersAssetsDetailsDay(dayString);
            double sumRecharge = 0;
            double sumWithdraw = 0;
            double sumAssets = 0;
            for (Map<String, Object> map : list) {
                sumRecharge += Double.valueOf(map.get("sumr").toString());
                sumWithdraw += Double.valueOf(map.get("sumw").toString());
                sumAssets += Double.valueOf(map.get("suma").toString());
            }
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查
            propertyHeaderMap.put("num_id", "序号");
            propertyHeaderMap.put("id", "用户id");
            propertyHeaderMap.put("true_name", "真实姓名");
            propertyHeaderMap.put("phone", "电话");
            propertyHeaderMap.put("sumra", "总充值-总提现");
            propertyHeaderMap.put("sumr", "总充值");
            propertyHeaderMap.put("suma", "总投资");
            propertyHeaderMap.put("sumw", "总提现");

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("num_id", "");
            map.put("id", "");
            map.put("true_name", "");
            map.put("phone", "");
            map.put("sumra", "总计:");
            map.put("sumr", sumRecharge);
            map.put("suma", sumAssets);
            map.put("sumw", sumWithdraw);
            list.add(map);
            getList(session, list);
            HSSFExcelUtils.ExpExs(dayString + "每日用户投资明细", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    @RequestMapping(value = "/huo", method = RequestMethod.GET)
    public String huo(Model model) {
        // 查询实际用户总数
        Integer realUserCount;
        try {
            Integer trueNameCount = userService.trueNameCount();
            Long huoRechargeCount = userService.huoRecharge();
            model.addAttribute("trueNameCount", trueNameCount);
            model.addAttribute("huoRechargeCount", huoRechargeCount);
            Long huoCount = userService.countHuo();
            model.addAttribute("huoCount", huoCount.intValue());
            realUserCount = userService.count(3,null,null);
            model.addAttribute("realUserCount", realUserCount);
            Calendar c = Calendar.getInstance();//项目启动时间  2015-05-02
            c.set(Calendar.YEAR, 2015);
            c.set(Calendar.MONTH, 5);
            c.set(Calendar.DAY_OF_MONTH, 2);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            long time1 = c.getTimeInMillis();
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

            // 上线时长
            Integer betweenDay = Integer.parseInt(String.valueOf(betweenDays));
            model.addAttribute("betweenDay", betweenDay);

            // 查询总金额总数
            Double totalInvestAmount = investmentService.getAmountCount(null);
            model.addAttribute("totalInvestAmount", totalInvestAmount);
            // 1充值2提现3收益4回收本金5债权转让回收本金6认购债权7赎回
            Double recharge = tradeRecordService.selectAllAmountByType(1);
            Double withdraw = tradeRecordService.selectAllAmountByType(2);
            model.addAttribute("rechargeAmount", recharge != null ? recharge : 0);
            model.addAttribute("withdrawAmount", withdraw != null ? withdraw : 0);
            return "report/huo";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/huo/pie", method = RequestMethod.GET)
    public void huoPie(HttpServletResponse response) {
        try {
            List<Map<Integer, String>> list = assetsService.getHuoInvestPie();
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 按出生年代查询投资金额情况
     *
     * @return
     */
    @RequestMapping(value = "/huo/birthday", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> huoBirthday() {
        try {
            return assetsService.getByYear();
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 统计每月留存情况
     *
     * @param response
     * @author 王信
     * @Create Date: 2015年12月18日上午10:29:03
     */
    @RequestMapping(value = "/huo/keepAmount", method = RequestMethod.GET)
    public void keepAmount(HttpServletResponse response) {
        try {
            String years = DateFormatTools.dateToStr2(new Date());
            List<Map<Integer, String>> list = rechargeService.selectRechargeWithdrawYear(years);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }


    @RequestMapping(value = "/hour/report", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> hourReport(HttpSession session,Integer departmentId) {
        try {
        	Subject subject = SecurityUtils.getSubject();
        	UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId=admin.getId();
            Integer adminDepartmentId=admin.getDepartmentId();
            		
			try {
				subject.checkPermission("user:seealluser");
				adminId=null;
				adminDepartmentId=0;
			} catch (Exception e) {
			}
            //24小时投资数据
            //List<Map<String, Object>> list = tradeRecordService.hourReport(AccountOperateEnum.INVEST_CREDIT_ADD.getFeatureName());
        	List<Map<String, Object>> list = tradeRecordService.hourReport(adminId,departmentId,"'"+AccountOperateEnum.INVEST_BALANCE_SUBTRACT.getFeatureName()
        																			+"','"+AccountOperateEnum.INVEST_BALANCE_FROZEN_SUBTRACT.getFeatureName()
        																			+"','"+AccountOperateEnum.INVEST_CASH_SUBTRACT.getFeatureName()+"'");
            //24小时商城数据
            List<Map<String, Object>> list2 = tradeRecordService.hourReport(adminId,departmentId,"'"+AccountOperateEnum.GOODSORDER_CASH_SUBTRACT.getFeatureName()
			+"','"+AccountOperateEnum.GOODSORDER_BALANCE_SUBTRACT.getFeatureName()
			+"','"+AccountOperateEnum.GOODSORDER_CREDIT_SUBTRACT.getFeatureName()+"'");
            //24小时提现数据
            List<Map<String, Object>> list3 = tradeRecordService.hourReport(adminId,departmentId,"'"+AccountOperateEnum.WITHDRAW_BALANCE_FREEZE.getFeatureName()+"'");

            Map map = new HashMap();
            map.put("list", list);
            map.put("list2", list2);
            map.put("list3", list3);
            return map;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 每天用户注册数量
     *
     * @param response
     */
    @RequestMapping(value = "/user/increase", method = RequestMethod.GET)
    public void userIncrease(HttpServletResponse response, Integer departmentId, HttpSession session) {
        try {
        	UserAdmin admin = (UserAdmin) session.getAttribute("user"); 
            List<Map<String, Object>> list = userService.getCountByMonthDay(admin.getId(), departmentId);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 统计充值金额
     *
     * @param response
     */
    @RequestMapping(value = "/recharge/increase", method = RequestMethod.GET)
    public void rechargeIncrease(HttpServletResponse response,Integer departmentId, HttpSession session) {
        try {
        	UserAdmin admin = (UserAdmin) session.getAttribute("user"); 
        	
            List<Map<String, Object>> list = rechargeService.getRechargeAmountByMonthDay(admin.getId(), departmentId);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 统计投资金额
     */
    @RequestMapping(value = "/invest/increase", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> investIncrease(Integer departmentId, HttpSession session) {
        try {
        	UserAdmin admin = (UserAdmin) session.getAttribute("user"); 
        	
            List<Map<String, Object>> list = investmentService.getInvestAmountByMonthDay(admin.getId(), departmentId);
            List<Date> dates = new ArrayList<Date>();
            for (int i = 0; i <= 30; i++) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                c.set(Calendar.DATE, c.get(Calendar.DATE) - i);
                dates.add(c.getTime());
            }
            Map<String, Map<String, Object>> map = new TreeMap<String, Map<String, Object>>();
            Map<String, Map<String, Object>> map2 = new TreeMap<String, Map<String, Object>>();
            Map<String, Map<String, Object>> map3 = new TreeMap<String, Map<String, Object>>();

            for (Date date : dates) {
                Map<String, Object> i = new HashMap<String, Object>();
                i.put("time", new SimpleDateFormat("yyyy-MM-dd").format(date));
                i.put("money", 0);
                map.put(new SimpleDateFormat("yyyy-MM-dd").format(date), i);
                map2.put(new SimpleDateFormat("yyyy-MM-dd").format(date), i);
                map3.put(new SimpleDateFormat("yyyy-MM-dd").format(date), i);
            }
            if (null != list && !list.isEmpty()) {
                for (Map<String, Object> m : list) {
                    map.put((String) m.get("time"), m);
                }
            }

            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("huo", new ArrayList<>(map.values()));
            return returnMap;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 统计活期提现金额
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public void withdrawReport(HttpServletResponse response,Integer departmentId, HttpSession session) {
        try {
        	UserAdmin admin = (UserAdmin) session.getAttribute("user");
            List<Map<String, Object>> list = withdrawService.getWithdrawMonthDay(admin.getId(), departmentId);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 资金明细查询
     */
    @RequestMapping(value = "/listCurrent", method = RequestMethod.GET)
    public String allCapitalDetailByMonthCurrent(HttpServletResponse response, @RequestParam(required = false) String month, Model model) {
        try {
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM");
            Date d = getDate(month);
            long start = System.currentTimeMillis();
            List<CapitalDetail> list = getallCapitalDetail(d);
            System.out.println("时间===" + (System.currentTimeMillis() - start));
            double investAll = 0d, rechargeAll = 0d, withdrawAll = 0d, earningsAll = 0d, receiveAll = 0d;
            double totalDingEarnings = 0d, totalHuoEarnings = 0d, totalYyyEarnings=0d;
            for (CapitalDetail capitalDetail : list) {
                investAll = BigDecimalUtil.add(investAll, capitalDetail.getHuoInvest(), capitalDetail.getBondInvest(), capitalDetail.getDingInvest(), capitalDetail.getHuoToDingInvest(),capitalDetail.getYyyInvest());
                rechargeAll += capitalDetail.getRecharge();
                withdrawAll += capitalDetail.getWithdraw();
                earningsAll = BigDecimalUtil.add(earningsAll, capitalDetail.getDingEarnings(), capitalDetail.getHuoEarnings(),capitalDetail.getYyyEarnings());
                receiveAll += capitalDetail.getReceive();
                totalDingEarnings += capitalDetail.getDingEarnings();
                totalHuoEarnings += capitalDetail.getHuoEarnings();
                totalYyyEarnings += capitalDetail.getYyyEarnings();
            }
            CapitalDetail summarizing = new CapitalDetail();
            summarizing.setInvestment(investAll);
            summarizing.setRecharge(getFixed2(rechargeAll + ""));
            summarizing.setWithdraw(getFixed2(withdrawAll + ""));
            summarizing.setEarnings(getFixed2(earningsAll + ""));
            summarizing.setReceive(getFixed2(receiveAll + ""));
            model.addAttribute("list", list);
            model.addAttribute("summarizing", summarizing);
            model.addAttribute("month", sm.format(d));
            model.addAttribute("totalDingEarnings", getFixed2(totalDingEarnings+""));
            model.addAttribute("totalHuoEarnings", getFixed2(totalHuoEarnings+""));
            model.addAttribute("totalYyyEarnings", getFixed2(totalYyyEarnings+""));
            return "/report/list";
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @RequestMapping(value = "export/allCapitalDetailByMonth", method = RequestMethod.GET)
    public void exportAllCapitalDetailByMonth(String month, HttpServletResponse response, @RequestParam(required = false) Integer type, HttpSession session) {

        byte[] buffer = new byte[4096];// 缓冲区
        BufferedOutputStream output = null;
        BufferedInputStream input = null;
        try {
            String modelExl = session.getServletContext().getRealPath("excelModel");
            String path = session.getServletContext().getContextPath();
            String templateFileName = "";
            String bName = "";
            templateFileName = modelExl + File.separator + "capitalDetail.xlsx";
            bName = "capitalDetail";
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM");
            Date d = getDate(month);

            List<CapitalDetail> list = getallCapitalDetail(d);
            double investAll = 0d, rechargeAll = 0d, withdrawAll = 0d, earningsAll = 0d, receiveAll = 0d;
            for (CapitalDetail capitalDetail : list) {
                investAll = BigDecimalUtil.add(investAll, capitalDetail.getHuoInvest(), capitalDetail.getBondInvest(), capitalDetail.getDingInvest(), capitalDetail.getHuoToDingInvest());
                rechargeAll += capitalDetail.getRecharge();
                withdrawAll += capitalDetail.getWithdraw();
                earningsAll = BigDecimalUtil.add(earningsAll, capitalDetail.getDingEarnings(), capitalDetail.getHuoEarnings());
                receiveAll += capitalDetail.getReceive();
            }
            CapitalDetail summarizing = new CapitalDetail();
            summarizing.setInvestment(investAll);
            summarizing.setRecharge(getFixed2(rechargeAll + ""));
            summarizing.setWithdraw(getFixed2(withdrawAll + ""));
            summarizing.setEarnings(getFixed2(earningsAll + ""));
            summarizing.setReceive(getFixed2(receiveAll + ""));


            XLSTransformer transformer = new XLSTransformer();
            String excels = session.getServletContext().getRealPath("excels");
            String destFileName = excels + File.separator + "finance" + File.separator;
            File file = new File(excels + File.separator + "finance");
            if (file.exists()) {
                deleteAll(file);
                file.mkdirs();
            } else {
                file.mkdirs();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("result", list);
            map.put("summarizing", summarizing);
            transformer.transformXLS(templateFileName, map, destFileName + bName + ".xls");
            String name = destFileName + bName + ".xls";
            OutputStream out = DownloadUtils.getResponseOutput("鑫聚财资金明细总表.xls", response);
            output = new BufferedOutputStream(out);
            input = new BufferedInputStream(new FileInputStream(new File(name)));
            int n = -1;
            while ((n = input.read(buffer, 0, 4096)) > -1) {
                output.write(buffer, 0, n);
            }
            output.flush();
            response.flushBuffer();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {

            }
        }
        try {
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 导出用户充值记录
     */
    @RequestMapping(value = "export/recharge", method = RequestMethod.GET)
    public void exportRecharge(String keyword, Integer status, Date startTime, String payChannel, Date endTime, Integer page,String client, String code,Integer departmentId, HttpServletResponse response, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:export");
            }catch (Exception e) {
                throw new Exception("您没有权限操作");
            }
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            String roleName = userAdminService.getRoleNameByUserId(admin.getId());
            Integer adminId = null;
            if (roleName.equalsIgnoreCase("normalCustomer")) {
                adminId = admin.getId();
            }

            code = StringUtil.isNull(code) ? null : code;
            List<Map<String, Object>> list = rechargeService.query(keyword, status, payChannel, startTime, endTime, null, null, client, adminId, code, departmentId);
            Map<String, Object> result = rechargeService.queryCount(keyword, status, payChannel, startTime, endTime, client, adminId, code, departmentId);
            List<RechargeVO> voList = new ArrayList<RechargeVO>();
            if (null != list && !list.isEmpty()) {
                int i = 1;
                for (Map<String, Object> map : list) {
                    RechargeVO vo = new RechargeVO();
                    double amount = (double) map.get("amount");
                    double passageFee = BigDecimalUtil.multi(amount, CHANNEL_RATE);
                    vo.setNum(String.valueOf(i));
                    i++;
                    vo.setAmount(String.valueOf(BigDecimalUtil.parse(amount)));
                    vo.setTrueName((String) map.get("trueName"));
                    String cardNo = (String) map.get("card_no");
                    String bankName = "";
                    if (StringUtils.isNotBlank(cardNo)) {
                        bankName = map.get("bankName") + "(尾号" + CommonUtils.getCardFour(cardNo) + ")";
                    } else {
                        bankName = "未知";
                    }
                    vo.setBankName(bankName);

                    vo.setCardNo((String) map.get("card_no"));
                    vo.setPhone((String) map.get("phone"));
                    vo.setOrderNo((String) map.get("order_no"));
                    vo.setRemark((String) map.get("remark"));
                    String curPayChannel = (String) map.get("pay_channel");

                    OutPayEnum outPayEnum = OutPayEnum.getValueByName(curPayChannel);
                    if (!OutPayEnum.OFFLINE.equals(outPayEnum)) {
                        vo.setPassageFee(String.valueOf(BigDecimalUtil.parse(passageFee)));
                    } else {
                        vo.setPassageFee("0");
                    }
                    String payChannelDesc = "";
                    if (outPayEnum != null) {
                        payChannelDesc = outPayEnum.getDescription();
                    } else {
                        payChannelDesc = "未知";
                    }
                    vo.setPayChannel(payChannelDesc);
                    String curClient = (String) map.get("client");
                    ClientEnum clientEnum = ClientEnum.getValueByName(curClient);
                    String clientDesc = "";
                    if (clientEnum != null) {
                        clientDesc = clientEnum.getDescription();
                    } else {
                        clientDesc = "未知";
                    }
                    vo.setClient(clientDesc);
                    Integer s = (Integer) map.get("status");
                    String str = "";
                    if (s == 0) {
                        str = "成功";
                    } else if (s == 1) {
                        str = "处理中";
                    } else if (s == 2) {
                        str = "失败";
                    }
//                    if(map.get("user_id")!=null){
//                        String trueName = customerListService.selectTrueNameByUserId(Integer.valueOf(map.get("user_id").toString()));
//                        if(trueName!=null ){
//                            map.put("cusName",trueName);
//                        }
//                    }
                    vo.setStatus(str);
                    vo.setDate(sim.format((Date) map.get("create_date")));
                    vo.setCusName(map.get("cusName") == null ? "" : map.get("cusName").toString());
                    vo.setUserId(map.get("user_id") == null ? "" : map.get("user_id").toString());
                    vo.setCode(code == null ? "" : code);
                    vo.setBusinessOrderNo((String) map.get("businessOrderNo"));
                    String orderType = (String) map.get("order_type");
                    OrderTypeEnum orderTypeEnum = OrderTypeEnum.getValueByName(orderType);
                    String orderTypeDesc = "";
                    if (orderTypeEnum != null) {
                        orderTypeDesc = orderTypeEnum.getDescription();
                    }
                    vo.setOrderType(orderTypeDesc);
                    String orderTotalAmount = map.get("orderTotalAmount") == null ? "0" : String.valueOf(map.get("orderTotalAmount"));
                    vo.setOrderTotalAmount(orderTotalAmount);
                    voList.add(vo);
                }
            }
            getList(session, voList);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查
            propertyHeaderMap.put("num", "序号");
            propertyHeaderMap.put("date", "日期");
            propertyHeaderMap.put("cusName", "客户名称");
            propertyHeaderMap.put("amount", "付款金额");
            propertyHeaderMap.put("orderNo", "支付订单号");
            propertyHeaderMap.put("businessOrderNo", "商城/物权订单号");
            propertyHeaderMap.put("orderType", "订单类型");
            propertyHeaderMap.put("bankName", "客户银行");
            propertyHeaderMap.put("payChannel", "支付通道");
            propertyHeaderMap.put("passageFee", "通道费");
            propertyHeaderMap.put("orderTotalAmount", "订单总金额");
            propertyHeaderMap.put("status", "状态");
            String title = "奔富畜牧业用户支付记录表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, voList, response);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * @Description(描述):导出用户利息记录
     * @author 王信
     * @date 2016/4/29
     * @params
     **/
    @RequestMapping(value = "export/interest", method = RequestMethod.GET)
    public void exportInterest(
            String keyword,
            String orderNo,
            Date investmentStartDate,
            Date investmentEndDate,
            Integer hasDividended,
            Date interestStartDate,
            Date interestEndDate,
            HttpServletResponse response,
            HttpSession session,
            Integer departmentId
        ) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("interest:export");
            } catch (Exception e) {
                throw new Exception("您没有权限操作");
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId=null;
            try {
    			subject.checkPermission("user:seealluser");
    		} catch (Exception e) {
    			adminId=admin.getId();
    		}
            List<InterestVO> list = interestService.queryInterestList(keyword, orderNo, investmentStartDate, investmentEndDate, 
            		hasDividended, interestStartDate, interestEndDate,null, null, adminId, departmentId);
//            int count = interestService.queryInterestCount(keyword, orderNo, investmentStartDate, investmentEndDate, hasDividended, interestStartDate, interestEndDate);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查
            propertyHeaderMap.put("userId", "用户ID");
            propertyHeaderMap.put("trueName", "真实姓名");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("orderNo", "订单号");
            propertyHeaderMap.put("interestAmount", "利息");
            propertyHeaderMap.put("capitalAmount", "本金");
            propertyHeaderMap.put("interestAmountTotal", "总利息");
            propertyHeaderMap.put("stage", "项目期数");
            propertyHeaderMap.put("hasDividendedDesc", "派息状态");
            propertyHeaderMap.put("updateDate", "派息时间");
            propertyHeaderMap.put("investmentCreateDate", "投资时间");

            list = getList(session, list);
            String title = "奔富畜牧业用户利息记录表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 导出用户投资记录
     */
    @RequestMapping(value = "export/investment", method = RequestMethod.GET)
    public void exportInvestment(Integer status, Date startTime, Date endTime, String keyword, Integer type, Integer seq, Integer investType, Integer source, HttpServletResponse response, HttpSession session, String codes) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            String roleName = userAdminService.getRoleNameByUserId(admin.getId());
            Integer adminId = null;
            if (roleName.equalsIgnoreCase("normalCustomer")) {
                adminId = admin.getId();
            }
            InvestmentSearchCondition searchCondition = new InvestmentSearchCondition(keyword, startTime, endTime, type, seq, null, null, investType, source, codes, adminId);
            long start = System.currentTimeMillis();
            List<Map<String, Object>> list = investmentService.query(searchCondition);
            long end = System.currentTimeMillis();
            System.out.println("query="+(end-start)+"ms");
            start = System.currentTimeMillis();
            Map<String, Object> result = investmentService.queryCount(searchCondition);
            end = System.currentTimeMillis();
            System.out.println("queryCount="+(end-start)+"ms");
            List<InvestmentVo> voList = new ArrayList<InvestmentVo>();
            int i = 1;
            if (null != list && !list.isEmpty()) {
                for (Map<String, Object> map : list) {
                    InvestmentVo vo = new InvestmentVo();
                    vo.setNum(String.valueOf(i));
                    i++;

                    if (investType != null && investType.equals(1)) {
                        vo.setProjectName((String) (map.get("title") == null ? map.get("title2") : map.get("title")));
                    } else {
                        vo.setProjectName((String) (map.get("title") == null ? map.get("title2") : map.get("title")));
                    }
                    vo.setUsername((String) map.get("username"));
                    if (map.get("code") != null) {
                        vo.setCodes(map.get("code").toString());
                    }
                    if (map.get("difday") != null) {
                        vo.setDifDay(map.get("difday").toString());
                    } else {
                        vo.setDifDay("0");
                    }

                    vo.setTrueName(map.get("trueName") == null ? "" : map.get("trueName").toString());
                    vo.setAmount(String.valueOf(map.get("amount")));
                    vo.setTime(sim.format((Date) map.get("time")));
                    Integer sources = (Integer) map.get("terminal");
                    String sourceStr = "";

                    if(sources!=null){
                    	if (sources == 0) {
                            sourceStr = "PC";
                        } else if (sources == 1) {
                            sourceStr = "安卓";
                        } else if (sources == 2) {
                            sourceStr = "IOS";
                        } else if (sources == 3) {
                            sourceStr = "WAP";
                        }
                    }
                    Integer types = (Integer) map.get("type");

                    String strType = "";
                    if (types == 0) {
                    	if(map.get("parent_id1") != null){
                    		strType = "债转";
                    	}else{
                    		strType = "散标";
                    	}

                    } else if (types == 1) {
                        strType = "活期";
                    } else if (types == 2) {
                        strType = "新手标";
                    } else if (types == 4) {
                        strType = "VIP";
                    } else if (types == 8){
                    	strType = "智投";
                    }
                    vo.setType(strType);
                    if (map.get("hb_amount") != null) {
                        vo.setHbAmount(map.get("hb_amount").toString());
                    }
                    vo.setInvestmentId(map.get("id").toString());
                    vo.setSource(sourceStr);

                    vo.setUserId(map.get("userId").toString());
                    vo.setRegister_time(map.get("register_time") == null ? "" : map.get("register_time").toString());

                    // 智投，相关数据
                    if(types==8){
                    	vo.setProjectName(map.get("month_title").toString());
                    	vo.setDifDay(map.get("month_limit_day").toString());
                    }

                    voList.add(vo);
                }
                InvestmentVo vo = new InvestmentVo();
                vo.setNum("投资总额:");
                vo.setProjectName(new DecimalFormat("#.00").format(Double.valueOf(result.get("amount").toString())));
                voList.add(vo);
            }
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("num", "编号");
            propertyHeaderMap.put("projectName", "项目名称");
            propertyHeaderMap.put("investmentId", "投资编号");
            propertyHeaderMap.put("type", "类型");
            propertyHeaderMap.put("trueName", "用户名");
            propertyHeaderMap.put("username", "用户昵称");
            propertyHeaderMap.put("amount", "充值金额");
            propertyHeaderMap.put("time", "投资时间");
            propertyHeaderMap.put("hbAmount", "红包金额");
            propertyHeaderMap.put("difDay", "天数");
            propertyHeaderMap.put("codes", "渠道");
            propertyHeaderMap.put("amount", "金额");
            propertyHeaderMap.put("source", "来源");
            propertyHeaderMap.put("userId", "用户ID");
            propertyHeaderMap.put("register_time", "注册时间");
            getList(session, voList);
            String title = "鑫聚财用户投资记录表";
            start = System.currentTimeMillis();
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, voList, response);
            end = System.currentTimeMillis();
            System.out.println("excel="+(end-start)+"ms");
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 导出用户提现记录
     */
    @RequestMapping(value = "export/withdraw", method = RequestMethod.GET)
    public void exportWithdraw(String keyword, String payChannel, Date createStartTime, Date createEndTime,
    		Date startTime, Date endTime, Integer type, Integer status, HttpServletResponse response,
    		HttpSession session, Integer departmentId) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:export");
            }catch (Exception e) {
                throw new Exception("您没有权限操作");
            }
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            String roleName = userAdminService.getRoleNameByUserId(admin.getId());
            Integer adminId = null;
            if (roleName.equalsIgnoreCase("normalCustomer")) {
                adminId = admin.getId();
            }
            //List<Map<String, Object>> list = withdrawService.query(keyword, method, startTime, endTime, status, type, null, null, adminId);
            List<Map<String, Object>> list = withdrawService.query1(keyword, payChannel, createStartTime, createEndTime, startTime, endTime, status, type, null, null, adminId, departmentId);
            //Map<String, Object> result = withdrawService.queryCount(keyword, method, startTime, endTime, status, type, adminId);
//            Map<String, Object> result = withdrawService.queryCount1(keyword, payChannel, createStartTime, createEndTime, startTime, endTime, status, type, adminId, departmentId);
            int i = 1;
            String title = null;
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            XSSFWorkbook ex = null;
            List<HuoWithdrawVo> voList = new ArrayList<HuoWithdrawVo>();
            if (null != list && !list.isEmpty()) {
                for (Map<String, Object> map : list) {
                    HuoWithdrawVo hw = new HuoWithdrawVo();
                    double amount = (double) map.get("amount");
                    double passageFee = BigDecimalUtil.multi(amount, CHANNEL_RATE);
                    hw.setNum(String.valueOf(i));
                    i++;
                    hw.setPassageFee(String.valueOf(passageFee));
                    hw.setOrderNo((String) map.get("order_no"));
                    hw.setId(String.valueOf(map.get("id")));
                    hw.setUsername((String) map.get("username"));
                    hw.setTrueName((String) map.get("true_name"));
                    hw.setCusName((String) map.get("cusName"));
                    hw.setAmount(String.valueOf(BigDecimalUtil.parse(amount)));
                    hw.setRealAmount(String.valueOf(BigDecimalUtil.parse((Double) map.get("real_amount"))));
                    hw.setCreateTime(map.get("create_date") != null ? sim.format((Date) map.get("create_date")) : "");
                    hw.setSuccessTime(map.get("successTime") != null ? sim.format((Date) map.get("successTime")) : "");
                    String curPayChannel = (String) map.get("pay_channel");
                    OutPayEnum outPayEnum = OutPayEnum.getValueByName(curPayChannel);
                    String payChannelDesc = "";
                    if (outPayEnum != null) {
                        payChannelDesc = outPayEnum.getDescription();
                    } else {
                        payChannelDesc = "未知";
                    }
                    hw.setPayChannel(payChannelDesc);
                    String client = (String) map.get("client");
                    ClientEnum clientEnum = ClientEnum.getValueByName(client);
                    String clientDesc = "";
                    if (clientEnum != null) {
                        clientDesc = clientEnum.getDescription();
                    } else {
                        clientDesc = "未知";
                    }
                    hw.setClient(clientDesc);
                    int ctype = (Integer) map.get("type");
                    if (ctype == 0) {
                        hw.setType("T+0");
                    } else {
                    	hw.setType("T+1");
                    }
                    String cardNo = (String) map.get("card_no");
                    String bankName = "";
                    if (StringUtils.isNotBlank(cardNo)) {
                        bankName = map.get("bankName") + "(尾号" + CommonUtils.getCardFour(cardNo) + ")";
                        hw.setCardNo(cardNo);
                    }
                    hw.setBankName(bankName);

                    status = (Integer) map.get("status");
                    String statusStr = "";
                    WithdrawStatusEnum withdrawStatusEnum = WithdrawStatusEnum.getValueByCode(status);
                    if (withdrawStatusEnum != null) {
                        statusStr = withdrawStatusEnum.getDescription();
                    }
                    hw.setStatus(statusStr);
                    voList.add(hw);
                }
            }
            getList(session, voList);
            propertyHeaderMap.put("num", "编号");
            propertyHeaderMap.put("createTime", "申请日期");
            propertyHeaderMap.put("payChannel", "支付通道");
            propertyHeaderMap.put("cusName", "客户名称");
            propertyHeaderMap.put("amount", "提现金额");
//            propertyHeaderMap.put("realAmount", "实际金额");
            propertyHeaderMap.put("payChannel", "代付通道");
            propertyHeaderMap.put("orderNo", "提现订单号");
            propertyHeaderMap.put("bankName", "客户银行");
//            propertyHeaderMap.put("successTime", "提现成功时间");
            propertyHeaderMap.put("status", "状态");
//            propertyHeaderMap.put("cusName", "归属坐席");
            title = "奔富畜牧业用户提现记录表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, voList, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 导出用户记录
     */
    @RequestMapping(value = "export/user", method = RequestMethod.GET)
    public void exportUser(Double startAmount, Double endAmount, Integer status, Integer type, Integer level, String keyword, Date startTime, Date endTime, HttpServletResponse response, Date investStartTime, Date investndTime, String codes, HttpSession session, String customerName) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("keyword", StringUtils.trim(keyword));
            data.put("status", status);
            data.put("startTime", startTime);
            data.put("endTime", endTime);
            data.put("type", type);
            data.put("code", "".equals(StringUtils.trim(codes)) ? null : codes);
            data.put("investStartTime", investStartTime);
            data.put("investndTime", investndTime);
            data.put("startAmount", startAmount);
            data.put("endAmount", endAmount);
            data.put("level", level);
            data.put("start", null);
            data.put("limit", null);
            data.put("customerName", customerName);

            List<Map<String, Object>> list = userService.query1(data);
            List<UserVO> voList = new ArrayList<UserVO>();
            if (null != list && !list.isEmpty()) {
                for (Map<String, Object> map : list) {
                    UserVO vo = new UserVO();
                    Integer sex = (Integer) map.get("sex");
                    if (sex != null) {
                        String sexStr = "";
                        if (sex == 0) {
                            sexStr = "女";
                        } else if (sex == 1) {
                            sexStr = "男";
                        }
                        vo.setSex(sexStr);
                    }
                    String bcType = null;
                    if (map.get("bc_type") != null) {
                        bcType = map.get("bc_type").toString();
                    }
                    if (bcType != null) {
                        vo.setIsHasBankCard("是");
                    } else {
                        vo.setIsHasBankCard("否");
                    }
                    vo.setUserId(map.get("id") + "");
                    vo.setBalance(map.get("availableBalance") + "");
                    vo.setUsername((String) map.get("username"));
                    String trueName = (String) map.get("trueName");
                    if (StringUtils.isNotBlank(trueName)) {
                        vo.setTrueName(trueName);
                    }
                    Date birthday = (Date) map.get("birthday");
                    if (birthday != null) {
                        vo.setBirthday(new SimpleDateFormat("yyyy-MM-dd").format(birthday));
                    }
                    vo.setTotalAmount(BigDecimalUtil.add(map.get("huoInvestmentAmount").toString(), map.get("uncollectCapital").toString()).toString());
                    vo.setHuoAmount(map.get("huoInvestmentAmount") + "");
                    vo.setDingAmount(map.get("uncollectCapital") + "");
                    Object code = map.get("code");
                    if (code != null) {
                        vo.setCode(code.toString());
                    } else {
                        vo.setCode("");
                    }
                    vo.setPhone((String) map.get("phone"));
                    String email = (String) map.get("email");
                    if (StringUtils.isNotBlank(email)) {
                        vo.setEmail(email);
                    }
                    vo.setRegisterTime(sim.format(map.get("registerTime")));
                    vo.setRegisterIp((String) map.get("registerIp"));
                    vo.setCustomerName(map.get("customerName") == null ? "" : (String) map.get("customerName"));
                    voList.add(vo);
                }
            }
            getList(session, voList);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查
            propertyHeaderMap.put("userId", "鑫聚财ID");
            propertyHeaderMap.put("username", "用户名");
            propertyHeaderMap.put("trueName", "真实姓名");
            propertyHeaderMap.put("phone", "手机号码");
            propertyHeaderMap.put("balance", "可用余额");
            propertyHeaderMap.put("isHasBankCard", "是否绑卡");
            propertyHeaderMap.put("totalAmount", "总投资金额");
            propertyHeaderMap.put("dingAmount", "散标投资金额");
            propertyHeaderMap.put("huoAmount", "活期投资金额");
            propertyHeaderMap.put("sex", "性别");
            propertyHeaderMap.put("birthday", "生日");
            propertyHeaderMap.put("code", "渠道");
            propertyHeaderMap.put("email", "邮件");
            propertyHeaderMap.put("registerTime", "注册时间");
            propertyHeaderMap.put("registerIp", "注册IP");
            propertyHeaderMap.put("customerName", "所属坐席");
            getList(session, voList);
            String title = "鑫聚财用户表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, voList, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * 导出用户记录
     */
    @RequestMapping(value = "export/userPhone", method = RequestMethod.GET)
    public void exportUserPhone(HttpSession session, Double startAmount, Double endAmount, Integer status, Integer type, String keyword, Date startTime, Date endTime, HttpServletResponse response, Date investStartTime, Date investndTime, String customerName) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Map<String, Object>> list = userService.query2(keyword, startAmount, endAmount, status, startTime, endTime, type, 0, 100000, investStartTime, investndTime, customerName);
            List<String> voList = new LinkedList<String>();
            UserAdmin user = (UserAdmin) session.getAttribute("user");
            Integer userId = user.getId();
            List<Integer> listIds = Arrays.asList(ConstantsAdmin.AUTHORITY_IDS);
            StringBuffer sb = new StringBuffer();
            boolean flag = listIds.contains(userId);
            if (null != list && !list.isEmpty()) {
                for (Map<String, Object> map : list) {
                    if (map.get("phone") != null) {
                        String phone = (String) map.get("phone");
                        if (!flag) {
                            if (phone != null && phone.length() > 8) {
                                sb.append(phone.substring(0, 7)).append("****");
                                voList.add(sb.toString() + "\r\n");
                                sb.setLength(0);
                            }
                        } else {
                            voList.add(phone + "\r\n");
                        }

                    }
                }
            }


            String title = "鑫聚财用户表.txt";
            OutputStream out = DownloadUtils.getResponseOutput(title, response);
            BufferedOutputStream buff = buff = new BufferedOutputStream(out);
            buff.write(voList.toString().substring(1, voList.toString().length() - 1).replace(", ", "").getBytes("UTF-8"));
            buff.flush();
            buff.close();

        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * 导出用户记录
     */
    @RequestMapping(value = "export/project", method = RequestMethod.GET)
    public void exportProject(Integer status, String keyword, Integer noob, HttpServletResponse response) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Project> list = projectService.selectRegularReport(status, keyword, noob);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查
            propertyHeaderMap.put("title", "项目名称");
            propertyHeaderMap.put("annualized", "年化收益");
            propertyHeaderMap.put("deadline", "投资截止时间");
            propertyHeaderMap.put("limitDays", "项目期限");
            propertyHeaderMap.put("investedAmount", "已融资金额");
            propertyHeaderMap.put("trueAmount", "真实投资金额");
            propertyHeaderMap.put("totalAmount", "项目总额");
            propertyHeaderMap.put("createTime", "创建时间");
            propertyHeaderMap.put("lastRepayTime", "最后还款时间");

            String title = "鑫聚财散标项目列表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    @RequestMapping(value = "/yaoyao", method = RequestMethod.GET)
    public String reportYaoYao(Date startTime, Date endTime, Model model) {

        List<Map<String, Object>> map = yaoRecordService.getCountByType(startTime, endTime);
        for (Map<String, Object> map2 : map) {
            Object value = null;
            String name = null;
            for (Map.Entry<String, Object> entry : map2.entrySet()) {
                if (entry.getKey().equals("value")) {
                    value = entry.getValue();
                }
                if (entry.getKey().equals("name")) {
                    name = (String) entry.getValue();
                }
            }
            model.addAttribute(name, value);
        }
        return "report/yaoyao";

    }

    /**
     * 所有用户资金明细查询
     *
     * @return
     */
    @RequestMapping(value = "/export/trade", method = RequestMethod.GET)
    public String exportAllAssets(Integer userId, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        // 获取要导出的数据，根据前台status来查
        List<Map<String, Object>> list = null;
        List<TradeRecordVO> returnList = new ArrayList<TradeRecordVO>();
        try {
            list = tradeRecordService.getWithRate(userId, null, null, null);
            if (null != list && !list.isEmpty()) {
                for (Map<String, Object> map : list) {
                    TradeRecordVO vo = new TradeRecordVO();
                     vo.setAmount(map.get("amount") + "");
                    vo.setBalance(map.get("balance") + "");
                    vo.setUsername(map.get("username") + "");
                     vo.setTrueName(map.get("trueName") + "");
                     Double rate = (Double) map.get("rate");
                    if (rate == null) {
                        rate = ConstantsAdmin.RATE_DEFAULT_VALUE;
                    }
                    vo.setRate(rate + "");
                    Integer source = (Integer) map.get("source");
                    String sourceStr = "";
                    if (source == 0) {
                        sourceStr = "散标";
                        vo.setRate("--");
                    } else if (source == 1) {
                        sourceStr = "活期";
                     }else if(source == 2){
                    	  sourceStr = "新手标";
                    }else if(source == 6){
                    	sourceStr = "体验金";
                    }else if(source == 7){
                    	sourceStr = "加息券";
                    }else if(source == 8){
                    	sourceStr ="债转";
                    	vo.setRate("--");
                    }else if(source == 9){
                    	sourceStr ="智投";
                    	vo.setRate("--");
                    }

                    Integer type = (Integer) map.get("type");
                    String typeStr = "";
                    if (type == 0 || type == 6) {
                        typeStr = "投资";
                    } else if (type == 1) {
                        typeStr = "充值";
                    } else if (type == 2) {
                        typeStr = "提现";
                    } else if (type == 3) {
                        typeStr = "收益";
                    } else if (type == 4 || type == 5) {
                         typeStr = "回收本金";
                    } else if (type == 7) {
                        typeStr = "赎回";
                    } else if (type == 8) {
                        typeStr = "红包";
                    } else if (type == 9) {
                        typeStr = "复利";
                    } else if (type == 10) {
                        typeStr = "活转定";
                    } else if (type == 11) {
                        typeStr = "签到红包";
                    } else if(type == 12){
                    	typeStr = "活动收益";
                    } else if(type == 13){
                    	typeStr = "超级债权回收本金";
                    }else if(type == 14){
                    	typeStr = "超级债权回购";
                    }else if(type == 15){
                    	typeStr = "安鑫赚回收本金";
                    }else if(type == 16){
                    	typeStr = "借款人到账金额";
                    }

                    if(type==1||type==2||type==11||type==15||type==16){
                    	sourceStr="--";
                    }

                    vo.setType(typeStr);
                    vo.setSource(sourceStr);
                    vo.setTime(format.format(map.get("time")));
                    returnList.add(vo);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // 要导出的列
        propertyHeaderMap.put("username", "用户名");
        propertyHeaderMap.put("trueName", "真实姓名");
        propertyHeaderMap.put("type", "类型");
        propertyHeaderMap.put("source", "来源");
//        propertyHeaderMap.put("rate", "利率");
        propertyHeaderMap.put("amount", "金额");
        propertyHeaderMap.put("time", "时间");
        propertyHeaderMap.put("balance", "余额");
        try {
            HSSFExcelUtils.ExpExs("用户资产明细表", propertyHeaderMap, returnList, response);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
        return null;
    }


    public List<CapitalDetail> getallCapitalDetail(Date d) {
        List<CapitalDetail> list = null;
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM");
            long start = System.currentTimeMillis();
            list = this.userAdminService.getAllCapitalDetailForMonth(d);
            System.out.println("-----------------数据库查询--------" + (System.currentTimeMillis() - start));
            if ((list != null) && (list.size() > 0)) {
                int mondays = getMonthDays(d).intValue();
                Map map = new HashMap();
                for (CapitalDetail capitalDetail : list) {
                    map.put((Integer) capitalDetail.getNum_id(), capitalDetail);
                    capitalDetail.setFormatTime(sim.format(capitalDetail.getTime()));
                }
                list.clear();
                for (int i = 1; i < mondays + 1; i++) {
                    list.add(map.get(Integer.valueOf(i)) == null ? new CapitalDetail(sim.parse(sm.format(d) + "-" + i)) : (CapitalDetail) map.get(Integer.valueOf(i)));
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    public Date getDate(String month) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            return month == null ? new Date() : sim.parse(month + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据具体日期查询该月份有多少天
    public Integer getMonthDays(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, c.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, c.get(Calendar.MONTH));
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 每日报单访问
     */
    @RequestMapping(value = "/perday", method = RequestMethod.GET)
    public String perday(Model model, HttpServletResponse response) {
        return "report/perday";
    }

    /**
     * 导出用户充值记录
     *
     */
/*    @RequestMapping(value = "export/dailyreport", method = RequestMethod.GET)
    public void exportDailyReport(HttpServletResponse response) {
        try {

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查
            // propertyHeaderMap.put("num", "编号");
            // propertyHeaderMap.put("trueName", "用户名");
            // propertyHeaderMap.put("username", "用户昵称");
            // propertyHeaderMap.put("projectName", "项目名称");
            // propertyHeaderMap.put("amount", "充值金额");
            // propertyHeaderMap.put("time", "投资时间");
            // propertyHeaderMap.put("amount", "金额");
            // propertyHeaderMap.put("source", "来源");
            //
            // String title = "鑫聚财用户投资记录表";
            // XSSFWorkbook ex = ExcelUtil.generateXlsxWorkbook(title,
            // propertyHeaderMap, voList);
            // OutputStream out = DownloadUtils.getResponseOutput(title +
            // ".xlsx", response);
            // ex.write(out);
            // ex.close();
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }*/


    /**
     * 王信 每日新增投资用户（指当日时间晚于用户注册日期）
     *
     * @param response
     * @param code
     */
    @RequestMapping(value = "/tradeAdd", method = RequestMethod.GET)
    public void tradeAdd(Model model, HttpServletResponse response, String code, HttpSession session) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String date = sdf.format(new Date());
            List<Map<String, Object>> list = tradeRecordService.tradeAdd();
            getList(session, list);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("num_id", "序号");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("trueName", "真实名字");
            propertyHeaderMap.put("tradetime", "交易时间");
            propertyHeaderMap.put("amount", "交易金额");
            propertyHeaderMap.put("balance", "账户余额");
            propertyHeaderMap.put("investAmount", "总投资金额");
            propertyHeaderMap.put("registerTime", "注册时间");
            getList(session, list);
            HSSFExcelUtils.ExpExs(date + "前一天新增投资用户", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 每日拉取当天生日用户名单
     *
     * @param response
     * @author 王信
     * @date 2015年11月6日 上午9:52:25
     */
    @RequestMapping(value = "/userBirthday", method = RequestMethod.GET)
    public void userBirthday(Model model, HttpServletResponse response, String code, HttpSession session) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String date = sdf.format(new Date());
            List<Map<String, Object>> list = userService.userBirthday();
            getList(session, list);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("num_id", "序号");
            propertyHeaderMap.put("username", "昵称");
            propertyHeaderMap.put("trueName", "真实名字");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("sex", "性别");
            propertyHeaderMap.put("investmentAmount", "在投金额");
            propertyHeaderMap.put("birthday", "生日");
            propertyHeaderMap.put("lastLoginTime", "最后登录时间");
            propertyHeaderMap.put("email", "邮箱");
            HSSFExcelUtils.ExpExs(date + "当天生日用户名单", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 查询--账户有余额资金未投资（方便客服随时查询随时呼出）以上数据需每日拉取名单
     *
     * @param response
     * @author 王信
     * @date 2015年11月6日 上午9:52:25
     */
    @RequestMapping(value = "/assetsInvestment", method = RequestMethod.GET)
    public void assetsInvestment(Model model, HttpServletResponse response, String code, HttpSession session) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String date = sdf.format(new Date());
            List<Map<String, Object>> list = assetsService.assetsInvestment();
            getList(session, list);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("num_id", "序号");
            propertyHeaderMap.put("trueName", "真实名字");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("email", "邮箱");
            propertyHeaderMap.put("sex", "性别");
            propertyHeaderMap.put("balance", "可用余额");
            propertyHeaderMap.put("huoInverstment", "活期投资金额");
            HSSFExcelUtils.ExpExs(date + "有余额未投资用户名单", propertyHeaderMap, list ,response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 留存统计查询  按月查询每天
     *
     * @param model
     * @param response
     * @author 王信
     * @Create Date: 2015年12月16日下午3:43:16
     */
    @RequestMapping(value = "/keepStatistics", method = RequestMethod.GET)
    public String keepStatistics(Model model, HttpServletResponse response, @RequestParam(required = false) Date month) {
        try {
            if (month == null) {
                month = new Date();
            }
            String months = DateFormatTools.dateToStr2(month);
            String y = null;
            if (months != null) {
                y = months.substring(0, 4);
            }
            List<Map<String, Object>> list = rechargeService.selectRechargeWithdrawDay(months);
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                if (map.get("proportion").toString().contains(".%")) {
                    map.put("proportion", map.get("proportion").toString().substring(0, map.get("proportion").toString().length() - 2) + "%");
                }
            }
            double sumRecharge = 0;
            double sumWithdraw = 0;
            for (Map<String, Object> map : list) {
                sumRecharge += Double.valueOf(map.get("rss").toString());
                sumWithdraw += Double.valueOf(map.get("wss").toString());
            }
            double sumAmount = sumRecharge - sumWithdraw;
            model.addAttribute("sumRecharge", sumRecharge);//月充值总额
            model.addAttribute("sumWithdraw", sumWithdraw);//月体现总额
            model.addAttribute("sumAmount", sumAmount);//月留存总额
            model.addAttribute("list", list);
            model.addAttribute("y", y);
            model.addAttribute("month", month);
            return "report/keepStatisticsList";
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * 留存统计查询  按年查询每月
     *
     * @param model
     * @param response
     * @author 王信
     * @Create Date: 2015年12月16日下午3:43:16
     */
    @RequestMapping(value = "/keepStatisticsYear", method = RequestMethod.GET)
    public String keepStatisticsYear(Model model, HttpServletResponse response, @RequestParam(required = false) Date year) {
        try {
            Date date = null;
            if (year == null) {
                year = new Date();
            }
//            else{
//                Calendar ca = Calendar.getInstance();
//                ca.set(Calendar.YEAR,year);
//                date = ca.getTime();
//            }
            String years = DateFormatTools.dateToStr2(year);
            String y = null;
            if (years != null) {
                y = years.substring(0, 4);
            }
            List<Map<String, Object>> list = rechargeService.selectRechargeWithdrawMonth(years);
            double sumRecharge = 0;
            double sumWithdraw = 0;
            for (Map<String, Object> map : list) {
                sumRecharge += Double.valueOf(map.get("rss").toString());
                sumWithdraw += Double.valueOf(map.get("wss").toString());
            }
            double sumAmount = sumRecharge - sumWithdraw;
            model.addAttribute("sumRecharge", sumRecharge);//年充值总额
            model.addAttribute("sumWithdraw", sumWithdraw);//年体现总额
            model.addAttribute("sumAmount", sumAmount);//年留存总额
            model.addAttribute("list", list);
            model.addAttribute("y", y);
            model.addAttribute("year", year);
            return "report/keepStatisticsList";
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @RequestMapping(value = "/keepStatisticsExcel", method = RequestMethod.GET)
    public void keepStatisticsExcel(Model model, HttpServletResponse response, @RequestParam(required = false) Date year, @RequestParam(required = false) Date month) {
        try {
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            List<Map<String, Object>> list = null;
            String title = "";
            if (year != null) {
                String years = DateFormatTools.dateToStr2(year);
                title = years;
                list = rechargeService.selectRechargeWithdrawMonth(years);
                propertyHeaderMap.put("timeTotal", "留存月份");
                propertyHeaderMap.put("rss", "月充值总额");
                propertyHeaderMap.put("wss", "月提现总额");
                propertyHeaderMap.put("total", "留存金额");
                propertyHeaderMap.put("proportion", "留存百分比(%)");
                propertyHeaderMap.put("fang", "房东提现");
            } else if (month != null) {
                String months = DateFormatTools.dateToStr2(month);
                list = rechargeService.selectRechargeWithdrawDay(months);
                for (int i = 0; i < list.size(); i++) {
                    Map map = list.get(i);
                    if (map.get("proportion").toString().contains(".%")) {
                        map.put("proportion", map.get("proportion").toString().substring(0, map.get("proportion").toString().length() - 2) + "%");
                    }
                }
                title = months;
                propertyHeaderMap.put("timeTotal", "留存日期");
                propertyHeaderMap.put("rss", "日充值总额");
                propertyHeaderMap.put("wss", "日提现总额");
                propertyHeaderMap.put("total", "留存金额");
                propertyHeaderMap.put("proportion", "留存百分比(%)");
                propertyHeaderMap.put("fang", "房东提现");
            }
            HSSFExcelUtils.ExpExs(title + "留存统计", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 散标回款到用户余额后，用户未对进行投资、提现的用户名单
     *
     * @param
     * @author 刘源
     * @date 2016/5/27
     */
    @RequestMapping(value = "/export/noOperationAfterBackPay", method = RequestMethod.GET)
    public void noOperationAfterBackPay(Model model, HttpServletResponse response, HttpSession session) {
        try {
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            List<Map<String, Object>> list = tradeRecordService.noOperationAfterBackPay();
            String title = "汇款后无操作用户名单";
            getList(session, list);
            propertyHeaderMap.put("uid", "用户ID");
            propertyHeaderMap.put("trueName", "用户名");
            propertyHeaderMap.put("phone", "电话");
            propertyHeaderMap.put("registerTime", "注册时间");
            propertyHeaderMap.put("aamount", "回收本金");
            propertyHeaderMap.put("atime", "回款时间");
            getList(session, list);
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * @Description(描述):投资红包使用统计导出
     * @author 王信
     * @date 2016/7/11
     * @params keyword  用户关键字  isUse  是否使用  开始时间，结束时间，兑换码ID
     **/
    @RequestMapping(value = "/hongbaoUseExcel", method = RequestMethod.GET)
    public void hongbaoUseExcel(Model model, HttpServletResponse response,
                                @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            List<Map<String, Object>> list = hongbaoService.selectReportHongbaoUseList(startTime, endTime);
            getList(session, list);
            propertyHeaderMap.put("descript", "红包类型");
            propertyHeaderMap.put("limit_amount", "红包限制金额");
            propertyHeaderMap.put("send_time", "红包发送时间");
            propertyHeaderMap.put("expire_time", "红包过期时间");
            propertyHeaderMap.put("use_count", "兑换码剩余次数");
            propertyHeaderMap.put("redeem_code", "兑换码");
            propertyHeaderMap.put("true_name", "用户姓名");
            propertyHeaderMap.put("phone", "用户手机号码");
            propertyHeaderMap.put("register_time", "用户注册时间");
            propertyHeaderMap.put("use_time", "红包使用时间");
            propertyHeaderMap.put("hongbaoAmount", "红包金额");
            propertyHeaderMap.put("amount", "投资金额");
            propertyHeaderMap.put("limit_days", "项目期限");
            propertyHeaderMap.put("annualized", "项目年化率");
            Double hongbaoTotalAmount = 0d;
            Double investmentTotalAmount = 0d;
            for (Map<String, Object> smap : list) {
                hongbaoTotalAmount += Double.valueOf(smap.get("hongbaoAmount") == null ? "0" : smap.get("hongbaoAmount").toString());
                investmentTotalAmount += Double.valueOf(smap.get("amount") == null ? "0" : smap.get("amount").toString());
                Double annualized = BigDecimalUtil.multi(Double.valueOf(smap.get("annualized") == null ? "0" : smap.get("annualized").toString()), 100);
                smap.put("annualized", annualized + "%");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("descript", "总计");
            map.put("hongbaoAmount", hongbaoTotalAmount);
            map.put("amount", investmentTotalAmount);
            list.add(map);
            getList(session, list);
            HSSFExcelUtils.ExpExs("用户红包使用详情", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * @Description(描述):散标项目，回款计划导出
     * @author 王信
     * @date 2016/8/19
     * @params
     **/
    @RequestMapping(value = "/paymentExcel", method = RequestMethod.GET)
    public void paymentExcel(Model model, HttpServletResponse response, String keyword, Date startTime, Date endTime) {
        try {

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            if (StringUtils.isBlank(keyword)) {
                keyword = null;
            }
            List<Map<String, Object>> list = projectService.selectReportList(keyword, startTime, endTime);
            String title = "项目回款计划";
            propertyHeaderMap.put("id", "项目ID");
            propertyHeaderMap.put("title", "项目名称");
            propertyHeaderMap.put("annualized", "项目年华利率");
            propertyHeaderMap.put("deadline", "截止时间");
            propertyHeaderMap.put("totalAmount", "项目总金额");
            propertyHeaderMap.put("limitDays", "项目天数");
            propertyHeaderMap.put("date", "应还款时间");
            propertyHeaderMap.put("capitalAmount", "应还本金金额");
            propertyHeaderMap.put("interestAmount", "应还利息金额");
            propertyHeaderMap.put("capitalInterestAmount", "应还本息");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * @Description(描述):活动
     * @author 王信
     * @date 2016/9/1
     * @params
     **/
    @RequestMapping(value = "/appleReport", method = RequestMethod.GET)
    public void appleReport(Model model, HttpServletResponse response, Integer activityId,
                            @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2, HttpSession session) {
        try {
            Activity activity = activityService.queryActivityById(activityId);
            List<Map<String, Object>> list = null;
            if (ActivityConstant.REGULAR_90_INVESTMENT_0903.equals(activity.getName())) {
                list = activityService.selectAppleActivityUserList(keyword, keyword1, keyword2, activity.getId(), activity.getStartTime(), activity.getEndTime(), 90, null, null);
            }
            if (ActivityConstant.REGULAR_180_INVESTMENT_0903.equals(activity.getName())) {
                list = activityService.selectAppleActivityUserList(keyword, keyword1, keyword2, activity.getId(), activity.getStartTime(), activity.getEndTime(), 180, null, null);
            }
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            getList(session, list);
            String title = activity.getName() + "活动详情";
            propertyHeaderMap.put("name", "奖品名称");
            propertyHeaderMap.put("true_name", "中奖姓名");
            propertyHeaderMap.put("totalAmount", "用户投资金额");
            propertyHeaderMap.put("phone", "手机号码");
            propertyHeaderMap.put("register_time", "注册时间");
            propertyHeaderMap.put("time", "中奖时间");
            propertyHeaderMap.put("remark", "留言信息");
            propertyHeaderMap.put("address", "联系地址");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * @Description(描述):投资查询，回款列表导出
     * @author 王信
     * @date 2016/9/5
     * @params
     **/
    @RequestMapping(value = "/paymentListReport", method = RequestMethod.GET)
    public void paymentListReport(Model model, HttpServletResponse response,
                                  @RequestParam(required = false) String keyword, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            String roleName = userAdminService.getRoleNameByUserId(admin.getId());
            Integer adminId = null;
            if (roleName.equalsIgnoreCase("normalCustomer")) {
                adminId = admin.getId();
            }
            List<Map<String, Object>> list = interestService.selectUserPaymentList(keyword, startTime, endTime, null, null, adminId);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            //getList(session, list);
            propertyHeaderMap.put("id", "用户ID");
            propertyHeaderMap.put("username", "用户昵称");
            propertyHeaderMap.put("true_name", "真实姓名");
            propertyHeaderMap.put("phone", "手机号码");
            propertyHeaderMap.put("interest", "利息");
            propertyHeaderMap.put("capital", "本金");
            propertyHeaderMap.put("time", "投资时间");
            propertyHeaderMap.put("todayAmount", "今日投资");
            propertyHeaderMap.put("date", "回款时间");
            propertyHeaderMap.put("cusName", "归属坐席");
            HSSFExcelUtils.ExpExs("回款列表", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * @Description(描述):活动
     * @author zxx
     * @date 2016/9/10
     * @params
     **/
    @RequestMapping(value = "/weekReport", method = RequestMethod.GET)
    public void weekReport(Model model, HttpServletResponse response, Integer activityId,
                           @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {
            Activity activity = activityService.queryActivityById(activityId);


            if (startTime == null || "".equals(startTime)) {
                startTime = activity.getStartTime();
            }
            if (endTime == null || "".equals(endTime)) {
                endTime = activity.getEndTime();
            }
            List<Map<String, Object>> list = activityService.selectWeekInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90, null, null);
            List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                map.put("jingdong", map.get("jingdong") + "元京东卡");
                list2.add(map);
            }

            getList(session, list2);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            String title = activity.getName() + "活动详情";
            propertyHeaderMap.put("jingdong", "奖品名称");
            propertyHeaderMap.put("true_name", "中奖姓名");
            propertyHeaderMap.put("username", "中奖昵称");
            propertyHeaderMap.put("totalAmount", "用户投资金额");
            propertyHeaderMap.put("phone", "手机号码");
            propertyHeaderMap.put("register_time", "注册时间");
            propertyHeaderMap.put("time", "中奖时间");
            propertyHeaderMap.put("remark", "留言信息");
            propertyHeaderMap.put("address", "联系地址");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list2, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * @Description(描述):活动
     * @author zxx
     * @date 2016/9/18
     * @params
     **/
    @RequestMapping(value = "/jingdongReport", method = RequestMethod.GET)
    public void jingdongReport(Model model, HttpServletResponse response, Integer activityId,
                               @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {
            Activity activity = activityService.queryActivityById(activityId);


            if (startTime == null || "".equals(startTime)) {
                startTime = activity.getStartTime();
            }
            if (endTime == null || "".equals(endTime)) {
                endTime = activity.getEndTime();
            }
            List<Map<String, Object>> list = activityService.selectJingdongInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90, 0, 100000);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            String title = activity.getName() + "活动详情";

            propertyHeaderMap.put("true_name", "中奖姓名");
            propertyHeaderMap.put("username", "中奖昵称");
            propertyHeaderMap.put("totalAmount", "用户投资金额");
            propertyHeaderMap.put("phone", "手机号码");
            propertyHeaderMap.put("register_time", "注册时间");
            propertyHeaderMap.put("limit_days", "投资期限");
            getList(session, list);
            propertyHeaderMap.put("address", "联系地址");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * @Description(描述):活动
     * @author zxx
     * @date 2016/9/12
     * @params
     **/
    @RequestMapping(value = "/hongbaoReport", method = RequestMethod.GET)
    public void hongbaoReport(Model model, HttpServletResponse response, Integer activityId,
                              @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {
            Activity activity = activityService.queryActivityById(activityId);


            if (startTime == null || "".equals(startTime)) {
                startTime = activity.getStartTime();
            }
            if (endTime == null || "".equals(endTime)) {
                endTime = activity.getEndTime();
            }
            List<Map<String, Object>> list = activityService.selectHongbaoInvestmentAward(keyword, keyword1, keyword2, activity.getId(), startTime, endTime, 90, 0, 100000);
            getList(session, list);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            String title = activity.getName() + "活动详情";
            propertyHeaderMap.put("true_name", "用户名");
            propertyHeaderMap.put("phone", "电话");
            propertyHeaderMap.put("descript", "红包类型");
            propertyHeaderMap.put("send_time", "领取时间");
            propertyHeaderMap.put("amount", "使用红包金额");
            propertyHeaderMap.put("amonutInv", "投资额");
            propertyHeaderMap.put("limit_days", "项目期限");
            propertyHeaderMap.put("annualized", "项目年化率");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * @Description(描述):邀请查询
     * @author zxx
     * @date 2016/9/12
     * @params
     **/
    @RequestMapping(value = "/inviteReport", method = RequestMethod.GET)
    public void inviteReport(Model model, HttpServletResponse response, Integer activityId,
                             @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword1, @RequestParam(required = false) Date keyword2, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {

            List<Map<String, Object>> list = userInviteService.selectInvestmentAward(keyword, startTime, endTime, 0, 100000);
            getList(session, list);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            String title = "邀请详情";
            propertyHeaderMap.put("true_name", "邀请人姓名");
            propertyHeaderMap.put("phone", "电话");
            propertyHeaderMap.put("register_time", "注册时间");
            propertyHeaderMap.put("byTrueName", "被邀请人姓名");
            propertyHeaderMap.put("byPhone", "被邀请人电话");
            propertyHeaderMap.put("byRegisterTime", "被邀请人注册时间");
            propertyHeaderMap.put("amount", "投资额");
            propertyHeaderMap.put("limit_days", "项目期限");
            propertyHeaderMap.put("annualized", "项目年化率");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    @RequestMapping(value = "/awardReport", method = RequestMethod.GET)
    public void awardReport(Model model, HttpServletResponse response, Integer activityId,
                            @RequestParam(required = false) String keyword, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {

            if (startTime == null) {
                startTime = DateFormatTools.strToDate("2016-09-21 18:00:00");
                endTime = new Date();
            }
            List<Map<String, Object>> list = activityService.projectAwardList(keyword, startTime, endTime, null, null);
            getList(session, list);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            String title = "抢标有礼活动";
            propertyHeaderMap.put("true_name", "邀请人姓名");
            propertyHeaderMap.put("phone", "电话");
            propertyHeaderMap.put("name", "中奖名称");
            propertyHeaderMap.put("amount", "中奖金额");
            propertyHeaderMap.put("time", "中奖时间");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    @RequestMapping(value = "/getAwardGoldReport", method = RequestMethod.GET)
    public void getAwardGoldReport(Model model, HttpServletResponse response, Integer activityId,
                                   @RequestParam(required = false) String keyword, @RequestParam(required = false) String giftKeyword, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {

            if (startTime == null) {
                startTime = DateFormatTools.strToDate("2016-10-01 00:00:00");
                endTime = DateFormatTools.strToDate("2016-10-07 00:00:00");
            }
            Activity activity = activityService.queryActivityById(activityId);
            List<Map<String, Object>> list = activityService.selectActivityListByName(activity.getName(), keyword, activityId, startTime, endTime, null, null);
            if (activity.getName().equals(ActivityConstant.REGULAR_AWARD_NEWYEAR)) {
                list = activityService.selectActivityListByName(activity.getName(), keyword, null, startTime, endTime, null, null);
            }
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            getList(session, list);
            String title = activity.getName();
            propertyHeaderMap.put("true_name", "邀请人姓名");
            propertyHeaderMap.put("phone", "电话");
            propertyHeaderMap.put("name", "中奖名称");
            propertyHeaderMap.put("register_time", "注册时间");
            propertyHeaderMap.put("time", "中奖时间");
            propertyHeaderMap.put("address", "中奖地址");
            propertyHeaderMap.put("totalAmount", "投资总额");
            propertyHeaderMap.put("investmentAmount", "投资金额");
            propertyHeaderMap.put("yearAmount", "年化额");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    @RequestMapping(value = "/export/projectCost", method = RequestMethod.GET)
    public void projectCostExcel(Model model, HttpServletResponse response, @RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status,
                                 @RequestParam(required = false) Integer page, Date startTime, Date endTime, Integer type, Integer limitDays) {
        try {
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            List<Map<String, Object>> list = projectService.projectCost1(null, null, keyword, status, startTime, endTime, type, limitDays);

            String title = "项目运营成本详情";
            propertyHeaderMap.put("title", "项目名称");
            propertyHeaderMap.put("leixing", "项目类型");
            propertyHeaderMap.put("limit_days", "项目期限");
            propertyHeaderMap.put("total_amount", "项目总额（元）");
            propertyHeaderMap.put("sumAmount", "真实融资额（元）");
            propertyHeaderMap.put("annualized", "项目利率");
            propertyHeaderMap.put("increase_annualized", "项目加息利率");

            propertyHeaderMap.put("start_time", "发布时间");
            propertyHeaderMap.put("time", "满标时间");
            propertyHeaderMap.put("status", "状态");
            propertyHeaderMap.put("tzhb", " 投资红包总额（元）");
            propertyHeaderMap.put("xjhb", "现金红包总额（元）");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    /**
     * 删除对象f下的所有文件和文件夹
     *
     * @param f 文件路径
     */
    public static void deleteAll(File f) {
        //文件
        if (f.isFile()) {
            f.delete();
        } else { //文件夹
            //获得当前文件夹下的所有子文件和子文件夹
            File f1[] = f.listFiles();
            //循环处理每个对象
            int len = f1.length;
            for (int i = 0; i < len; i++) {
                //递归调用，处理每个文件对象
                deleteAll(f1[i]);
            }
            //删除当前文件夹
            f.delete();
        }
    }


    public List getList(HttpSession session, List list) {
        UserAdmin user = (UserAdmin) session.getAttribute("user");
        Integer userId = user.getId();

        List<Integer> listIds = Arrays.asList(ConstantsAdmin.AUTHORITY_IDS);
        boolean flag = listIds.contains(userId);
        if (!flag) {
            if (list != null && list.size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i) instanceof HashMap) {
                        Map map = (Map) list.get(i);
                        if (map != null && map.get("trueName") != null && !"".equals((String) map.get("trueName"))) {
                            String str = (String) map.get("trueName");
                            str = str.substring(0, 1);
                            sb.append(str).append("**");
                            map.put("trueName", sb.toString());
                            sb.setLength(0);
                        }
                        if (map != null && StringUtils.isNotBlank((String) map.get("true_name")) && (!userId.equals(68) && !userId.equals(69))) {
                            String str = (String) map.get("true_name");
                            str = str.substring(0, 1);
                            sb.append(str).append("**");
                            map.put("true_name", sb.toString());
                            sb.setLength(0);
                        }
                        if (map != null && map.get("phone") != null && !"".equals((String) map.get("phone"))) {
                            String phone = (String) map.get("phone");
                            if (phone != null && phone.length() > 10) {
                                sb.append(phone.substring(0, 3)).append("****").append(phone.substring(7));
                            }
                            map.put("phone", sb.toString());
                            sb.setLength(0);
                        }

                        if (map != null && map.get("byTrueName") != null && !"".equals((String) map.get("byTrueName"))) {
                            String str = (String) map.get("byTrueName");
                            str = str.substring(0, 1);
                            sb.append(str).append("**");
                            map.put("byTrueName", sb.toString());
                            sb.setLength(0);
                        }
                        if (map != null && map.get("byPhone") != null && !"".equals((String) map.get("byPhone"))) {
                            String phone = (String) map.get("byPhone");
                            if (phone != null && phone.length() > 10) {
                                sb.append(phone.substring(0, 3)).append("****").append(phone.substring(7));
                            }
                            sb.append(phone.substring(0, 3)).append("****");
                            map.put("byPhone", sb.toString());
                            sb.setLength(0);
                        }
                    } else if (list.get(i) instanceof RechargeVO) {
                        RechargeVO rechargeVO = (RechargeVO) list.get(i);
                        String str = rechargeVO.getTrueName();
                        if (str != null && !"".equals(str)) {
                            str = str.substring(0, 1);
                            sb.append(str).append("**");
                            rechargeVO.setTrueName(sb.toString());
                            sb.setLength(0);
                        }
                        String phone = rechargeVO.getPhone();
                        if (phone != null && !"".equals(phone) && phone.length() > 7) {
                            sb.append(phone.substring(0, 3)).append("****").append(phone.substring(7));
                            rechargeVO.setPhone(sb.toString());
                            sb.setLength(0);
                        }
                    } else if (list.get(i) instanceof InvestmentVo) {
                        InvestmentVo rechargeVO = (InvestmentVo) list.get(i);
                        String str = rechargeVO.getTrueName();
//                        if (str != null && !"".equals(str)) {
//                            str = str.substring(0, 1);
//                            sb.append(str).append("**");
//                            rechargeVO.setTrueName(sb.toString());
//                            sb.setLength(0);
//                        }
                    } else if (list.get(i) instanceof HuoWithdrawVo) {
                        HuoWithdrawVo rechargeVO = (HuoWithdrawVo) list.get(i);
                        String str = rechargeVO.getTrueName();
                        if (str != null && !"".equals(str)) {
                            str = str.substring(0, 1);
                            sb.append(str).append("**");
                            rechargeVO.setTrueName(sb.toString());
                            sb.setLength(0);
                        }
                    } else if (list.get(i) instanceof UserVO) {
                        UserVO rechargeVO = (UserVO) list.get(i);
                        String str = rechargeVO.getTrueName();
                        if (str != null && !"".equals(str)) {
                            str = str.substring(0, 1);
                            sb.append(str).append("**");
                            rechargeVO.setTrueName(sb.toString());
                            sb.setLength(0);
                        }
                        String phone = rechargeVO.getPhone();
                        if (phone != null && !"".equals(phone) && phone.length() > 7) {
                            if (phone != null && phone.length() > 10) {
                                sb.append(phone.substring(0, 3)).append("****").append(phone.substring(7));
                            }
                            rechargeVO.setPhone(sb.toString());
                            sb.setLength(0);
                        }
                    } else if (list.get(i) instanceof WLimit) {
                        WLimit rechargeVO = (WLimit) list.get(i);
                        String str = rechargeVO.getName();
                        if (str != null && !"".equals(str)) {
                            str = str.substring(0, 1);
                            sb.append(str).append("**");
                            rechargeVO.setName(sb.toString());
                            sb.setLength(0);
                        }
                        String phone = rechargeVO.getPhone();
                        if (phone != null && !"".equals(phone) && phone.length() > 7) {
                            if (phone != null && phone.length() > 10) {
                                sb.append(phone.substring(0, 3)).append("****").append(phone.substring(7));
                            }
                            rechargeVO.setPhone(sb.toString());
                            sb.setLength(0);
                        }
                    } else if (list.get(i) instanceof TradeRecordVO) {
                        TradeRecordVO rechargeVO = (TradeRecordVO) list.get(i);
                        String str = rechargeVO.getTrueName();
                        if (str != null && !"".equals(str)) {
                            str = str.substring(0, 1);
                            sb.append(str).append("**");
                            rechargeVO.setTrueName(sb.toString());
                            sb.setLength(0);
                        }
                    }

                }

            }

        }

        return list;
    }

    @RequestMapping(value = "/valentinesActivity", method = RequestMethod.GET)
    public void valentinesActivity(Model model, HttpServletResponse response, Integer activityId,
                                   @RequestParam(required = false) String keyword, @RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime, HttpSession session) {
        try {
            Activity activity = activityService.queryActivityById(activityId);


            if (startTime == null || "".equals(startTime)) {
                startTime = activity.getStartTime();
            }
            if (endTime == null || "".equals(endTime)) {
                endTime = activity.getEndTime();
            }
            List<Map<String, Object>> list = activityService.selectValentinesDayActivityList(keyword, startTime, endTime, null, null);
            Double totalAc = 0d;
            Double totalAll = 0d;
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                for (Object key : map.keySet()) {
                    Object obj = map.get(key);
                    if (obj == null) {
                        map.put(key, "0");
                    } else {
                        if (key.toString().contains("romanticAmount")) {
                            map.put(key, BigDecimalUtil.fixed2(map.get(key).toString()).toString());
                            totalAc = BigDecimalUtil.add(BigDecimalUtil.fixed2(map.get(key).toString()), totalAc);
                        }
                    }
                }
                map.put("romanticTotalAmount", BigDecimalUtil.fixed2(totalAc));
                totalAc = 0d;
                totalAll = BigDecimalUtil.add(BigDecimalUtil.fixed2(map.get("romanticTotalAmount").toString()), totalAll);
            }
            getList(session, list);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            String title = activity.getName() + "活动详情";
            propertyHeaderMap.put("true_name", "用户名");
            propertyHeaderMap.put("username", "用户昵称");
            propertyHeaderMap.put("phone", "电话");
            propertyHeaderMap.put("register_time", "注册时间");
            propertyHeaderMap.put("amount_30", "30天投资");
            propertyHeaderMap.put("romanticAmount_30", "30天返现");
            propertyHeaderMap.put("amount_90", "90天投资");
            propertyHeaderMap.put("romanticAmount_90", "90天返现");
            propertyHeaderMap.put("amount_180", "180天投资");
            propertyHeaderMap.put("romanticAmount_180", "180天返现");
//            propertyHeaderMap.put("totalAmount1", "投资总额");
            propertyHeaderMap.put("romanticTotalAmount", "累积返现");
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * @Description(描述):感恩2周年，加息券、体验金人人大狂欢！
     * @author zxx
     * @date 2017/3/22
     * @params
     **/
    @RequestMapping(value = "newAnniversaryReport", method = RequestMethod.GET)
    public void newAnniversary(Model model, Date endTime, Date startTime, Integer activityId, String keyword, HttpServletResponse response, HttpSession session) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            Activity activity = activityService.queryActivityById(activityId);
            List<Map<String, Object>> list = activityService.newAnniversary(keyword, startTime, endTime, activity.getStartTime(), activity.getEndTime(), null, null);
//            for (int i = 0; i <list.size() ; i++) {
//                Map map = list.get(i);
//                if(map.get("actual_income")!=null && Integer.valueOf(map.get("actual_income").toString())==0 ){
//                    map.put("actual_income",map.get("income"));
//                }
//            }
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("username", "用户昵称");
            propertyHeaderMap.put("true_name", "用户名");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("register_time", "注册时间");
            propertyHeaderMap.put("code", "渠道");
            propertyHeaderMap.put("amount", "首投金额");
            propertyHeaderMap.put("time", "首投时间");
            propertyHeaderMap.put("title", "项目名称");
            String st = "", et = "";
            if (startTime != null && endTime != null) {
                st = sim.format(startTime);
                et = sim.format(endTime);
            }
//            for (int i = 0; i <list.size() ; i++) {
//                Map map =list.get(i);
//                Integer rownum = Double.valueOf(map.get("rownum").toString()).intValue();
//                map.put("rownum",rownum);
//            }
            getList(session, list);
            String title = "2周年壕礼迎新！新手即送5000元体验金+独享2%加息！" + st + "___" + et;
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * @Description(描述):感恩2周年，加息券、体验金人人大狂欢！
     * @author zxx
     * @date 2017/3/22
     * @params
     **/
    @RequestMapping(value = "twoYearsThanksActivityReport", method = RequestMethod.GET)
    public void twoYearsThanksActivityReport(Model model, Date endTime, Date startTime, Integer activityId, String keyword, HttpServletResponse response, HttpSession session) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            Activity activity = activityService.queryActivityById(activityId);
            List<Map<String, Object>> list = activityService.twoYearsThanksActivity(keyword, startTime, endTime, activity.getStartTime(), activity.getEndTime(), null, null);
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                if (map.get("actual_income") == null || Double.valueOf(map.get("actual_income").toString()) <= 0) {
                    map.put("actual_income", map.get("income"));
                }
            }
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("username", "用户昵称");
            propertyHeaderMap.put("true_name", "用户名");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("register_time", "注册时间");
            propertyHeaderMap.put("code", "渠道");
            propertyHeaderMap.put("invAmount", "投资总额");
            propertyHeaderMap.put("amount", "体验金额");
            propertyHeaderMap.put("hbAmount", "红包金额");
            propertyHeaderMap.put("rate", "加息利率");
            propertyHeaderMap.put("begin_time", "计息开始时间");
            propertyHeaderMap.put("end_time", "计息结束时间");
            propertyHeaderMap.put("actual_income", "体验金收益");
            propertyHeaderMap.put("title", "项目名称");
            propertyHeaderMap.put("limit_days", "项目期限");
            String st = "", et = "";
            if (startTime != null && endTime != null) {
                st = sim.format(startTime);
                et = sim.format(endTime);
            }
//            for (int i = 0; i <list.size() ; i++) {
//                Map map =list.get(i);
//                Integer rownum = Double.valueOf(map.get("rownum").toString()).intValue();
//                map.put("rownum",rownum);
//            }
            getList(session, list);
            String title = "感恩2周年，加息券、体验金人人大狂欢！" + st + "___" + et;
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }
    @RequestMapping(value = "/tradeRecord", method = RequestMethod.GET)
    public String tradeRecord(String trueName,Model model, Date endTime, Date startTime,Integer page){
    	if(startTime==null&&endTime==null&&StringUtil.isNull(trueName)){
    		Calendar c = Calendar.getInstance();
    		c.setTime(new Date());
    		c.add(Calendar.DAY_OF_YEAR, -7);
			startTime=c.getTime();
			endTime=new Date();
		}
    	int limit = 20;
        if (page == null) {
            page = 1;
        }
    	List<Map<String, Object>> tradeRecordList=tradeRecordService.queryTradeRecordInfo(trueName,startTime,endTime,(page - 1) * limit,limit);
        model.addAttribute("tradeRecordList", tradeRecordList);
        Integer count = tradeRecordService.getTradeRecordInfoCount(trueName,startTime,endTime);
        model.addAttribute("pages", calcPage(count, 20));
        model.addAttribute("page", page);
        model.addAttribute("trueName", trueName);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
    	return "/report/tradeRecordList";
    }
    @RequestMapping(value = "/tradeRecord/export", method = RequestMethod.GET)
    public void tradeRecordexport(String trueName,Model model, Date endTime, Date startTime,Integer page,HttpSession session,HttpServletResponse response){
    	try{
    	if(startTime==null&&endTime==null&&StringUtil.isNull(trueName)){
    		Calendar c = Calendar.getInstance();
    		c.setTime(new Date());
    		c.add(Calendar.DAY_OF_YEAR, -30);
			startTime=c.getTime();
			endTime=new Date();
		}
    	//搜索用户时间为空设置时间为一个月
    	/*if(startTime==null&&endTime==null&&!StringUtil.isNull(trueName)){
    		Calendar c = Calendar.getInstance();
    		c.setTime(new Date());
    		c.add(c.DAY_OF_YEAR, -30);
			startTime=c.getTime();
			endTime=new Date();
    	}*/
    	if(DateUtil.dateToDateDay(startTime, endTime)>30){
    		response.setCharacterEncoding("utf-8");
	    	PrintWriter out=response.getWriter();
	    	out.println("时间间隔大于一个月,请重新选择");
	    	return;
    	}
    	List<Map<String, Object>> tradeRecordList=tradeRecordService.queryTradeRecordInfo(trueName,startTime,endTime,null,null);
    	 LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
    	 propertyHeaderMap.put("uid", "鑫聚财ID");
         propertyHeaderMap.put("trueName", "真实姓名");
         propertyHeaderMap.put("code", "渠道");
         propertyHeaderMap.put("regtime", "注册时间");
         propertyHeaderMap.put("type", "交易类型");
         propertyHeaderMap.put("ctime", "交易时间");
         propertyHeaderMap.put("retime", "最后时间");
         propertyHeaderMap.put("amount", "金额");
         propertyHeaderMap.put("amounts", "年化收益");
         propertyHeaderMap.put("isa", "是否");
         String title = DateUtil.getYear(startTime)+"年"+DateUtil.fomartDate2YM(startTime)+"至"+DateUtil.getYear(endTime)+"年"+DateUtil.fomartDate2YM(endTime)+"-交易记录";
         HSSFExcelUtils.ExpExs(title, propertyHeaderMap, tradeRecordList, response);
         DownloadUtils.closeResponseOutput(response);
    } catch (Exception e) {
        logger.error(e);
    }
    }

    @RequestMapping(value = "export/zzproject", method = RequestMethod.GET)
    public void zzproject(Integer status, String keyword, Integer noob, Integer bondDayDiff, String amount, Date startTime, Date endTime, HttpServletResponse response) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startAmount = null;
            String endAmount = null;
            if (!StringUtils.isEmpty(amount)) {
                startAmount = amount.substring(0, amount.indexOf("-"));
                endAmount = amount.substring(amount.indexOf("-") + 1, amount.length());
            }
            List<Project> list = projectService.queryzzList(keyword, startAmount, endAmount, bondDayDiff, status, startTime, endTime, null, null);
            //List<Project> list = projectService.selectRegularReport(status, keyword, noob);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查

            propertyHeaderMap.put("userId", "用户ID");
            propertyHeaderMap.put("userName", "昵称");
            propertyHeaderMap.put("trueName", "真名");
            propertyHeaderMap.put("title", "项目名称");
            propertyHeaderMap.put("createTime", "转让时间/已被取消时间");
            propertyHeaderMap.put("totalAmount", "转让金额");
            propertyHeaderMap.put("investedAmount", "成交金额");
            propertyHeaderMap.put("annualized", "年化收益");
            propertyHeaderMap.put("bondDayDiff", "剩余期限");
            propertyHeaderMap.put("bondManagementRate", "服务费率");
            propertyHeaderMap.put("status", "状态（0转让中1已转让2已取消）");

            String title = "鑫聚财债转项目列表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            DownloadUtils.closeResponseOutput(response);
        }
    }
    /**
     *   智投报表统计导出
     * @param keyword
     * @param startTime
     * @param endTime
     * @param response
     */
    @RequestMapping(value = "export/investPayment", method = RequestMethod.GET)
    public void investPayment(String keyword, Date startTime, Date endTime, HttpServletResponse response,HttpSession session,String quitable,Integer limitDay) {
        try {
        	Subject subject = SecurityUtils.getSubject();
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = admin.getId();
            List<Map<String, Object>> list = interestervice.investIntellectualList(keyword, startTime, endTime,null, null,null,quitable,limitDay);
            try {
                subject.checkPermission("user:seealluser");
            }catch (Exception e) {
            	list=interestervice.investIntellectualList(keyword, startTime, endTime, null,null,adminId,quitable,limitDay);
            }
            try {
            	subject.checkPermission("user:adminPhone");
			} catch (Exception e) {
				for (int i = 0; i < list.size(); i++) {
            		Map<String,Object> map = list.get(i);
            		if(map.get("phone")!=null){
            			String newPhone=String.valueOf(map.get("phone")).substring(0, 3)+"****"+String.valueOf(map.get("phone")).substring(7, 11);
            			map.put("phone",newPhone);
            		}
            	}
			}
            for(Map<String,Object> entity:list){
            	entity.put("isQuit", 0);//不可退出
            	entity.put("quitable", "否");//可退出
    			if(entity.get("status").equals(0)){
    		        if (entity.get("time") != null && entity.get("limitDays") != null) {
    					Date lastQuitDate = DateUtil.getDateAfter((Date)entity.get("time"), Integer.parseInt(entity.get("limitDays").toString()));
    					if (System.currentTimeMillis() >= lastQuitDate.getTime()) {
    						entity.put("isQuit", 1);//可退出
    						entity.put("quitable", "是");//可退出
    					}else{
    						entity.put("isQuit", 0);//不可退出
    					}
    				}
                 }
    			entity.put("annualized", Double.valueOf(String.valueOf(entity.get("annualized")))*100+"%");
    			entity.put("addAnnualized", Double.valueOf(String.valueOf(entity.get("addAnnualized")))*100+"%");
    			if(entity.get("successOutTime") != null) {
    				Date endDate=DateUtil.getStrToDate(String.valueOf(entity.get("successOutTime")));
    				Date startDate=DateUtil.getStrToDate(String.valueOf(entity.get("time")));
    				Integer hasDay=DateUtil.dateToDateDay(startDate, endDate);
    				entity.put("holdDay", hasDay);
    			}else {
    				Date startDate=DateUtil.getStrToDate(String.valueOf(entity.get("time")));
    				Integer hasDay=DateUtil.dateToDateDay(startDate, new Date());
    				entity.put("holdDay", hasDay);
    			}
    		}
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查

            propertyHeaderMap.put("userId", "用户ID");
            propertyHeaderMap.put("userName", "昵称");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("trueName", "真实姓名");
            propertyHeaderMap.put("title", "资产包名");
            propertyHeaderMap.put("annualized", "基础年化");
            propertyHeaderMap.put("addAnnualized", "加息年化");
            propertyHeaderMap.put("limitDays", "饲养期（天）");
            propertyHeaderMap.put("holdDay", "持有天数（天）");
            propertyHeaderMap.put("time", "投资时间");
            propertyHeaderMap.put("amount", "投资金额（元）");
            propertyHeaderMap.put("quitable", "是否可申请退出");
            propertyHeaderMap.put("applyOutTime", "申请退出时间");
            propertyHeaderMap.put("successOutTime", "退出完成时间");
            propertyHeaderMap.put("customerName", "归属坐席");
            HSSFExcelUtils.ExpExs("鑫聚财智投报表", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            DownloadUtils.closeResponseOutput(response);
        }
    }

    class Application{
        private String ip;
        private int port;
        private String module;
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }
        public String getModule() {
            return module;
        }
        public void setModule(String module) {
            this.module = module;
        }
        public int getPort() {
            return port;
        }
        public void setPort(int port) {
            this.port = port;
        }
        public Application(String ip, int port, String module) {
            this.ip = ip;
            this.port = port;
            this.module = module;
        }
    }

    class AppVO{
        private String name;
        private String module;
        private String ip;
        private int port;
        private int status;
        private String ctime;
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }
        public String getCtime() {
            return ctime;
        }
        public void setCtime(String ctime) {
            this.ctime = ctime;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getModule() {
            return module;
        }
        public void setModule(String module) {
            this.module = module;
        }
        public int getPort() {
            return port;
        }
        public void setPort(int port) {
            this.port = port;
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
        public AppVO(String name, String module, int port, int status, String ctime) {
            this.name = name;
            this.module = module;
            this.port = port;
            this.status = status;
            this.ctime = ctime;
        }
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "report/index";
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public void query(HttpServletResponse response) {
        Integer withdrawFail = -1;
        try {
            withdrawFail = withdrawService.queryWithdrawOfInProcessNeedAlert();
        } catch (Exception e) {
            logger.error(e);
        }
        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(d);
        logger.info("time:"+ctime);
        List<AppVO> list = new ArrayList<>();
        //a
        List<Application> aHost = new ArrayList<Application>();
        aHost.add(new Application("172.19.72.25", 8081, "app-tomcat"));
        aHost.add(new Application("172.19.72.22", 8081, "usercenter-tomcat"));
        aHost.add(new Application("172.19.72.26", 8081, "transaction-tomcat"));
        aHost.add(new Application("172.19.72.28", 8081, "task-tomcat"));
        aHost.add(new Application("172.19.72.23", 8081, "openapi-tomcat"));
        aHost.add(new Application("172.19.72.24", 8081, "admin-tomcat"));
        for(int i=0;i<aHost.size();i++){
            int status = 0;
            String module = "";
            String url = "http://"+aHost.get(i).ip+":"+aHost.get(i).port+""+module+"/test.jsp";
            HttpClient httpClient = HttpsUtil.getHttpsClient();
            try {
                if(aHost.get(i).ip.equals("172.19.72.28")) {//task无法访问jsp问题
                    url = "http://"+aHost.get(i).ip+":"+aHost.get(i).port+""+module+"/index.html";
                }
                // 创建HttpGET
                HttpGet  httpGet=new HttpGet();
                httpGet.setURI(new URI(url));
                HttpResponse res = httpClient.execute(httpGet); // 执行POST请求
                if(res.getStatusLine().getStatusCode() == 200){
                    status = 1;
                }
            } catch (Exception e){
                e.printStackTrace();
                list.add(new AppVO("a", aHost.get(i).module, aHost.get(i).port, 0, ctime));
                continue;
            } finally {
                httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
            }
            list.add(new AppVO("a", aHost.get(i).module, aHost.get(i).port, status, ctime));
        }
        //b
//        String bip = "10.139.53.174";
//        List<Application> bHost = new ArrayList<Application>();
//        bHost.add(new Application(8083, "web-tomcat"));
//        bHost.add(new Application(8080, "app-tomcat"));
//        bHost.add(new Application(8081, "mobile-tomcat"));
//        bHost.add(new Application(4040, "task-tomcat"));
//        bHost.add(new Application(18001, "openapi-tomcat"));
//        bHost.add(new Application(8084, "admin-tomcat"));
//        bHost.add(new Application(3030, "report-tomcat"));
//        for(int i=0;i<bHost.size();i++){
//            int status = 0;
//            String module = "";
//            if(bHost.get(i).module.equals("task-tomcat")){
//                module = "/task";
//            }
//            if(bHost.get(i).getModule().equals("openapi-tomcat")){
//                module = "/openapi";
//            }
//            String url = "http://"+bip+":"+bHost.get(i).port+""+module+"/test.jsp";
//            HttpClient httpClient = HttpsUtil.getHttpsClient();
//            try {
//                // 创建HttpGET
//                HttpGet  httpGet=new HttpGet();
//                httpGet.setURI(new URI(url));
//                HttpResponse res = httpClient.execute(httpGet); // 执行POST请求
//                if(res.getStatusLine().getStatusCode() == 200){
//                    status = 1;
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//                list.add(new AppVO("b", bHost.get(i).module, bHost.get(i).port, 0, ctime));
//                continue;
//            } finally {
//                httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
//            }
//            list.add(new AppVO("b", bHost.get(i).module, bHost.get(i).port, status, ctime));
//        }
//

        //当天提现总条数
        Integer totalWithdrawToday = 0;
        try {
            totalWithdrawToday = withdrawService.queryTotalWithdrawToday();
        } catch (Exception e) {
            logger.error(e);
        }


        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
//        c.add(Calendar.DAY_OF_MONTH, -1);
        Date date = c.getTime();

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        Date endDate = c.getTime();

        //当日提现
        Double withdrawAmount = 0d;
        List<Withdraw> withdrawList = withdrawService.getWithdrawByDate(date, endDate);
        if(withdrawList != null && withdrawList.size() > 0){
            for(Withdraw withdraw : withdrawList){
                withdrawAmount += withdraw.getAmount();
            }
        }

        //当天注册用户数
        Integer todayRegisterUser = 0;
        try {
            todayRegisterUser = userService.queryTodayRegisterUser(date);
        } catch (Exception e) {
            logger.error(e);
        }

        //当天注册用户数
        Integer allRegisterUser = 0;
        try {
            allRegisterUser = userService.queryAllRegisterUser();
        } catch (Exception e) {
            logger.error(e);
        }

        Double totalRedeemAmount = 0d;
        Integer totalRedeemCount = 0;
        Double totalCapitalAmount = 0d;
        Double totalInterestAmount = 0d;

        Integer hasInterestedCount = 0;
//      Double hasInterestedAmount = 0d;
        try {
            List<Interest> interestList = interestService.queryTodayInterestInfo();
            if(interestList != null && interestList.size() > 0){
                for(Interest interest : interestList){
                    totalRedeemCount ++;
                    totalCapitalAmount += interest.getCapitalAmount();
                    totalInterestAmount += interest.getInterestAmount();

                    if(interest.getHasDividended() == 1){
                        hasInterestedCount ++;
                    }
                }
                totalRedeemAmount = totalCapitalAmount + totalInterestAmount;
            }
            if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 16){
                hasInterestedCount = -999;
            }

        } catch (Exception e) {
            logger.error(e);
        }

        Double investmentAmount = 0d;
        Integer investmentCount = 0;
        try {
            List<TradeRecord> tradeRecordOfInvestmentList  = tradeRecordService.queryTodayInvestment();
            if(tradeRecordOfInvestmentList != null && tradeRecordOfInvestmentList.size() > 0){
                for(TradeRecord record : tradeRecordOfInvestmentList){
                    investmentAmount += record.getAmount();
                    investmentCount ++;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }

        Double totalChargeAmount = 0d;
        Integer chargeCount = 0;
        Integer chargeCountOfSuccess = 0;
        Integer chargeCountOfFail = 0;
        try {
            List<Recharge> rechargeList = rechargeService.queryAllChargeInfoToday();
            if(rechargeList != null && rechargeList.size() > 0){
                for(Recharge recharge : rechargeList){
                    if(recharge.getStatus() == 0){
                        chargeCountOfSuccess ++;
                        totalChargeAmount += recharge.getAmount();
                    }else if(recharge.getStatus() == 2 && !StringUtil.isNull(recharge.getRemark()) && !recharge.getRemark().equals("用户取消支付")){
                        chargeCountOfFail ++;
                    }
                }
                chargeCount = chargeCountOfSuccess + chargeCountOfFail;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        //查询当日签到人数
        Integer todaySignedUser = 0;
        try {
            todaySignedUser = userService.queryTodaySignedUser(date);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, "{\"server\":"+JSON.toJSONString(list)
                +", \"withdrawFail\":"+JSON.toJSONString(withdrawFail)
                +", \"totalWithdrawToday\":"+JSON.toJSONString(totalWithdrawToday)
                +", \"withdrawAmount\":"+JSON.toJSONString(BigDecimalUtil.fixed2(withdrawAmount))

                +", \"todayRegisterUser\":"+JSON.toJSONString(todayRegisterUser)
                +", \"allRegisterUser\":"+JSON.toJSONString(allRegisterUser)

                +", \"totalRedeemAmount\":"+JSON.toJSONString(totalRedeemAmount)
                +", \"totalRedeemCount\":"+JSON.toJSONString(totalRedeemCount)
                +", \"totalCapitalAmount\":"+JSON.toJSONString(totalCapitalAmount)
                +", \"totalInterestAmount\":"+JSON.toJSONString(totalInterestAmount)
                +", \"hasInterestedCount\":"+JSON.toJSONString(hasInterestedCount)

                +", \"investmentAmount\":"+JSON.toJSONString(investmentAmount)
                +", \"investmentCount\":"+JSON.toJSONString(investmentCount)

                +", \"totalChargeAmount\":"+JSON.toJSONString(totalChargeAmount)
                +", \"chargeCount\":"+JSON.toJSONString(chargeCount)
                +", \"chargeCountOfSuccess\":"+JSON.toJSONString(chargeCountOfSuccess)
                +", \"chargeCountOfFail\":"+JSON.toJSONString(chargeCountOfFail)
                +", \"todaySignedUser\":"+JSON.toJSONString(todaySignedUser)
                +"}");
    }


    /**
     * 导出回购查询
    * @Title: reportListBuyBack
    * @param model
    * @param response
    * @param startDate
    * @param endDate
    * @param startAge
    * @param endAge void
    * @author zj
    * @date 2019-07-30 16:21
     */
    @RequestMapping(value = "export/reportListBuyBack", method = RequestMethod.GET)
    public void reportListBuyBack(Model model, HttpSession session,HttpServletResponse response,
    		@RequestParam(required = false) String startDate,
    		@RequestParam(required = false) String endDate,
    		@RequestParam(required = false) Integer startAge,
    		@RequestParam(required = false) Integer endAge,
    		@RequestParam(required = false) Integer departmentId
    		) {
        try {
        	Subject subject = SecurityUtils.getSubject();
        	UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId=null;
	        try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId=admin.getId();
			}
	        
	        try {
				subject.checkPermission("project:buyBack:viewall");
				adminId=null;
			} catch (Exception e) {
			}
	        
        	Integer limit=60000;//兼容2003版最大行数
            List<Map<String, Object>> list = projectService.listBuyBack(startDate, endDate, startAge, endAge, 0, limit,adminId, departmentId);

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();

            propertyHeaderMap.put("buy_back", "回购日期");
            propertyHeaderMap.put("order_no", "订单号");
            propertyHeaderMap.put("true_name", "客户名称");
            propertyHeaderMap.put("quantity", "数量");
            propertyHeaderMap.put("amount", "应付本金");
            propertyHeaderMap.put("interest_amount", "应付授信");
            propertyHeaderMap.put("total", "应付合计");
            propertyHeaderMap.put("use_amount", "已使用授信");
            propertyHeaderMap.put("returned_money", "回款金额");
            propertyHeaderMap.put("return_status", "回款状态");
            propertyHeaderMap.put("status", "订单状态");
            propertyHeaderMap.put("limit_days", "饲养期限");
            propertyHeaderMap.put("ear_number", "耳标号");
            propertyHeaderMap.put("yue_ling", "牛只月龄");

            Map<String, Object> map = new HashMap<String, Object>();
            list.add(map);
            HSSFExcelUtils.ExpExs("回购查询", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }


    @RequestMapping(value = "/listBuyBackTJ")
	public String listBuyBackTJ(Model model,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate) {

		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("report:buyBack");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}


			long start = System.currentTimeMillis();
			List<Map<String, Object>> list = projectService.listBuyBackTJ(startDate, endDate);
			System.out.println("===============" + list.size() + "==============");
			long end = System.currentTimeMillis();
			logger.info("query" + (end - start));
			start = System.currentTimeMillis();
			end = System.currentTimeMillis();
			logger.info("queryCount" + (end - start));


			model.addAttribute("list", list);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "/report/listBuyBackTJ";
	}

    /**
     * 导出回购统计
    * @Title: reportListBuyBackTJ
    * @param model
    * @param response
    * @param startDate
    * @param endDate
    * @param startAge
    * @param endAge void
    * @author zj
    * @date 2019-07-31 13:43
     */
    @RequestMapping(value = "export/reportListBuyBackTJ", method = RequestMethod.GET)
    public void reportListBuyBackTJ(Model model, HttpServletResponse response,
    		@RequestParam(required = false) String startDate,
    		@RequestParam(required = false) String endDate,
    		@RequestParam(required = false) Integer startAge,
    		@RequestParam(required = false) Integer endAge
    		) {
        try {
            List<Map<String, Object>> list = projectService.listBuyBackTJ(startDate, endDate);

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();

            propertyHeaderMap.put("name", "项目");
            propertyHeaderMap.put("b_cow", "基础牛群");
            propertyHeaderMap.put("s_cow", "牛犊");
            propertyHeaderMap.put("hj", "合计");

            Map<String, Object> map = new HashMap<String, Object>();
            list.add(map);
            HSSFExcelUtils.ExpExs("回购统计", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }



	/**
	 *授信资金查询
	* @Title: listBuyBack
	* @param model
	* @param startAge
	* @param endAge
	* @param startDate
	* @param endDate
	* @param page
	* @return String
	* @author zj
	* @date 2019-08-01 10:05
	 */
	@RequestMapping(value = "/listCreditFunds")
	public String listCreditFunds(Model model,
			@RequestParam(required = false) Integer typeId,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) Integer page) {

		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("report:listCreditFunds");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}

			int limit = 10;
			if (page == null) {
				page = 1;
			}

			long start = System.currentTimeMillis();
			List<Map<String, Object>> list = projectService.listCreditFunds(typeId,startDate, endDate, (page - 1) * limit, limit);
			System.out.println("===============" + list.size() + "==============");
			long end = System.currentTimeMillis();
			logger.info("query" + (end - start));
			start = System.currentTimeMillis();
			int count = projectService.countCreditFunds(typeId, startDate, endDate);
			end = System.currentTimeMillis();
			logger.info("queryCount" + (end - start));

			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("typeId", typeId);
		} catch (Exception e) {
			logger.error("授信资金查询询出错=========>"+e.getMessage(), e);
		}
		return "/report/listCreditFunds";
	}


	/**
	 * 导出授信金额
	 *
	 * @Title: reportListCreditFunds
	 * @param model
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @param startAge
	 * @param endAge    void
	 * @author zj
	 * @date 2019-08-07 18:13
	 */
    @RequestMapping(value = "export/reportListCreditFunds")
    public void reportListCreditFunds(
    		 Model model, HttpServletResponse response,
			@RequestParam(required = false) Integer typeId,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) Integer page
    		) {
        try {
        	Integer limit=60000;//兼容2003版最大行数
        	List<Map<String, Object>> list = projectService.listCreditFunds(typeId,startDate, endDate, 0, limit);

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();




            propertyHeaderMap.put("create_date", "日期");
            propertyHeaderMap.put("status", "状态");
            propertyHeaderMap.put("user_true_name", "客户名称");
            propertyHeaderMap.put("flow_amount", "操作金额");
            propertyHeaderMap.put("investment_amount", "本金");
            propertyHeaderMap.put("recharge_order_no", "支付订单号");
            propertyHeaderMap.put("order_no", "订单号");
            propertyHeaderMap.put("order_type", "订单类型");

            Map<String, Object> map = new HashMap<String, Object>();
            list.add(map);
            HSSFExcelUtils.ExpExs("授信金额查询导出", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }



    /**
     * @description 用户资金明细
     * @author shuys
     * @date 2019/7/31
     * @param date
     * @param response
     * @param session
     * @return void
    */
    @RequestMapping(value = "export/tradeRecordDetail", method = RequestMethod.GET)
    public void customTradeRecordDetail(Date date, HttpServletResponse response, HttpSession session,
    		Integer departmentId) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("trade:exportDetail");
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
            
            List<Map<String, String>> list = tradeRecordService.customDetailOfFunds(date,adminId,departmentId);
            if (list != null && !list.isEmpty()) {
                Map<String, String> sumMap = tradeRecordService.customDetailOfFundsSum(date,adminId,departmentId);
                if (sumMap != null) {
                    sumMap.put("phone", "合计");
                    list.add(sumMap);
                }
            }
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("num", "序号");
            propertyHeaderMap.put("user_id", "注册号");
            propertyHeaderMap.put("cus_name", "客户名称");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("balance_amount", "账户余额合计");
            propertyHeaderMap.put("frozen_amount", "授信金额");
            propertyHeaderMap.put("credit_amount", "授信冻结");
            propertyHeaderMap.put("freozen_credit_amount", "余额冻结");
            String dateStr = DateUtil.yyyyMMdd.format(date);
            String title = dateStr + "_奔赴畜牧业客户资金明细表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        } finally{
            DownloadUtils.closeResponseOutput(response);
        }
    }

    //@RequiresPermissions({"report:propertySalesStoreStatusHistory:list"})
    /**
     * 牛只历史销售库存状态查询
     */
    @RequestMapping(value = "/propertySalesStoreStatusHistory/list")
	public String listPropertySalesStoreStatusHistory(Model model, HttpServletResponse response,
			@RequestParam(required = false) Date datePoint ,@RequestParam(required = false) Boolean saleStatus ,
			@RequestParam(required = false) Integer minYueLing , @RequestParam(required = false) Integer maxYueLing ,
			@RequestParam(required = false) String orderBy, @RequestParam(required = false) String adesc,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
    	String result="/report/propertySalesStoreStatusHistory/list";
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("report:propertySalesStoreStatusHistory:list");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			if(datePoint==null) {
				datePoint=new Date();
			}
			datePoint=DateUtil.getDayStartDate(datePoint);

			if (page == null) {
				page = 1;
			}
			if(pageSize==null) {
				pageSize = 10;
			}

			result=this.propertySalesStoreStatusHistory(false ,model ,response ,datePoint ,saleStatus,minYueLing ,maxYueLing ,orderBy ,adesc ,page ,pageSize) ;
		} catch (Exception e) {
			logger.error("授信资金查询询出错=========>"+e.getMessage(), e);
		}
		System.out.println("result"+result);
		return result;
	}

    /**
     * 牛只历史销售库存状态查询
     */
    @RequestMapping(value = "/propertySalesStoreStatusHistory/export")
	public void exportPropertySalesStoreStatusHistory(Model model, HttpServletResponse response,
			@RequestParam(required = false) Date datePoint ,@RequestParam(required = false) Boolean saleStatus ,
			@RequestParam(required = false) Integer minYueLing , @RequestParam(required = false) Integer maxYueLing ,
			@RequestParam(required = false) String orderBy, @RequestParam(required = false) String adesc,
			@RequestParam(required = false) Integer page,	@RequestParam(required = false) Integer pageSize) {

    	try {
    		Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("report:propertySalesStoreStatusHistory:export");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return;
			}
			if(datePoint==null) {
				datePoint=new Date();
			}
			datePoint=DateUtil.getDayStartDate(datePoint);

			if (page == null) {
				page = 0;
			}
			if(pageSize==null) {
				pageSize = 10;
			}

			this.propertySalesStoreStatusHistory(true ,model ,response ,datePoint ,saleStatus ,minYueLing ,maxYueLing ,orderBy ,adesc ,page ,pageSize ) ;

        } catch (Exception e) {
            logger.error(e);
        }
	}

	private String propertySalesStoreStatusHistory(Boolean isExport,Model model, HttpServletResponse response, Date datePoint,
			Boolean saleStatus ,Integer minYueLing,Integer maxYueLing,
			String orderBy ,String adesc ,Integer page, Integer pageSize) {
		PropertySalesStoreStatusViewExample example=new PropertySalesStoreStatusViewExample();
		example.setDatePoint(datePoint);
		example.setLimitStart((page - 1) * pageSize);
		example.setLimitEnd(pageSize);
		if(!StringUtils.isBlank(orderBy)) {
			example.setOrderByClause(orderBy+" "+adesc);
		}

		PropertySalesStoreStatusViewExample.Criteria criteria=example.createCriteria();
		/**
		 * saleStatus 出售状态
		 * saleStatus==true  isRaisedByUs==false "已出售"
		 * saleStatus==false  isRaisedByUs==true "未出售"
		 */
		if(saleStatus!=null) {
			criteria.andIsRaisedByUsEqualTo(!saleStatus);
		}
		if(minYueLing!=null) {
			criteria.andCurrentYueLingGreaterThanOrEqualTo(minYueLing.longValue());
		}
		if(maxYueLing!=null) {
			criteria.andCurrentYueLingLessThan(maxYueLing.longValue());
		}

		long start = System.currentTimeMillis();
		List<PropertySalesStoreStatusView> propertyList = propertySalesStoreStatusViewMapper
				.selectHistoryByDatePoint(example);
		System.out.println("listPropertySalesStoreStatusHistoryByDatePoint:" + JSON.toJSONString(propertyList));

		long end = System.currentTimeMillis();
		logger.info("query" + (end - start));
		start = System.currentTimeMillis();
		Long count = propertySalesStoreStatusViewMapper.countHistoryByDatePoint(example);
		end = System.currentTimeMillis();
		logger.info("queryCount" + (end - start));
		if (isExport) {
			try {
				LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
				propertyHeaderMap.put("rowNum", "序号");
				propertyHeaderMap.put("earNumber", "耳标号");
				propertyHeaderMap.put("currentYueLing", "牛只月龄");
				propertyHeaderMap.put("SaleStatus", "出售状态");
				propertyHeaderMap.put("Num", "数量");
				propertyHeaderMap.put("lastDeadline", "代养日期");
				propertyHeaderMap.put("lastOrderNo", "订单号");
				propertyHeaderMap.put("lastUserTrueName", "客户名称");
				propertyHeaderMap.put("lastLimitDays", "饲养期限");
				propertyHeaderMap.put("priorDueTime", "上次回购日期");
				propertyHeaderMap.put("PriorTotalAmount", "上次回购价款");
				propertyHeaderMap.put("currentMonthManager", "本期代养成本");
				propertyHeaderMap.put("sumManagerFee", "累计代养成本");
				HSSFExcelUtils.ExpExs(new SimpleDateFormat("yyyy-MM-dd").format(datePoint) + "牛只销售库存状态",
						propertyHeaderMap, propertyList, response);
			} catch (Exception e) {
				logger.error(e);
			} finally {
				DownloadUtils.closeResponseOutput(response);
			}
		} else {
			int pages = calcPage(count.intValue(), pageSize);
			model.addAttribute("pages", pages);
			model.addAttribute("list", propertyList);
			model.addAttribute("page", page);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("orderBy", orderBy);
			model.addAttribute("adesc", adesc);
			model.addAttribute("datePoint", datePoint);
			model.addAttribute("saleStatus", saleStatus);
			model.addAttribute("minYueLing", minYueLing);
			model.addAttribute("maxYueLing", maxYueLing);
			model.addAttribute("count", count);
			return "/report/propertySalesStoreStatusHistory/list";
		}
		return "";
	}


    /**
     * @date 2019年8月6日
     * @author wangyun
     * @time 下午2:34:11
     * @Description 认养统计
     *
     * @param model
     * @param response
     * @param startDate
     * @param endDate
     */
    @RequestMapping(value = "/investStatement", method = RequestMethod.GET)
    public String investList(Model model, HttpServletResponse response,
    		@RequestParam(required = false) String startDate,
    		@RequestParam(required = false) String endDate
    		) {
    	try {
    		Subject subject = SecurityUtils.getSubject();
    		try {
				subject.checkPermission("invest:investStatement");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}

    		List<InvestStatementVO> list = projectService.investStatement(StringUtils.trim(startDate),StringUtils.trim(endDate));
    		Map<String, Object> total = new HashMap<>();//合计
    		int count = 0;
    		BigDecimal amount = BigDecimal.ZERO;
    		BigDecimal manageFee = BigDecimal.ZERO;
    		BigDecimal raiseFee = BigDecimal.ZERO;
    		BigDecimal balancePayMoney	 = BigDecimal.ZERO;
    		BigDecimal hongbaoMoney = BigDecimal.ZERO;
    		BigDecimal remainAmount = BigDecimal.ZERO;
    		BigDecimal cowMoney = BigDecimal.ZERO;

    		for (InvestStatementVO vo : list) {
    			 count = count + vo.getTotalCount();
    			 amount =  amount.add(vo.getTotalAmount());
    			 manageFee = manageFee.add(vo.getTotalManageFee());
    			 raiseFee = raiseFee.add(vo.getTotalRaiseFee());
    			 balancePayMoney =balancePayMoney.add(vo.getTotalBalancePayMoney());
    			 hongbaoMoney = hongbaoMoney.add(vo.getTotalHongbaoMoney());
    			 remainAmount = remainAmount.add(vo.getTotalRemainAmount());
    			 cowMoney = cowMoney.add(vo.getTotalCowMoney());
			}
    		total.put("count", count);
    		total.put("amount", amount);
    		total.put("manageFee", manageFee);
    		total.put("raiseFee", raiseFee);
    		total.put("balancePayMoney", balancePayMoney);
    		total.put("hongbaoMoney", hongbaoMoney);
    		total.put("remainAmount", remainAmount);
    		total.put("cowMoney", cowMoney);

    		model.addAttribute("list", list);
    		model.addAttribute("total", total);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "/report/listInvestStatement";
    }


    @RequestMapping(value = "/reportInvestStatement", method = RequestMethod.GET)
    public void reportInvestStatement(Model model, HttpServletResponse response,
    		@RequestParam(required = false) String startDate,
    		@RequestParam(required = false) String endDate
    		) {
    	try {
    		Subject subject = SecurityUtils.getSubject();
    		try {
				subject.checkPermission("invest:reportInvestStatement");
			} catch (Exception e) {
				throw new Exception("您没有权限操作");
			}

    		List<InvestStatementVO> list = projectService.investStatement(startDate,endDate);

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            BigDecimal amount = BigDecimal.ZERO;
    		BigDecimal manageFee = BigDecimal.ZERO;
    		BigDecimal raiseFee = BigDecimal.ZERO;
    		BigDecimal balancePayMoney	 = BigDecimal.ZERO;
    		BigDecimal hongbaoMoney = BigDecimal.ZERO;
    		BigDecimal remainAmount = BigDecimal.ZERO;
    		BigDecimal cowMoney = BigDecimal.ZERO;

    		int count = 0;
    		for (InvestStatementVO vo : list) {
    			 count = count + vo.getTotalCount();
    			 amount =  amount.add(vo.getTotalAmount());
    			 manageFee = manageFee.add(vo.getTotalManageFee());
    			 raiseFee = raiseFee.add(vo.getTotalRaiseFee());
    			 balancePayMoney =balancePayMoney.add(vo.getTotalBalancePayMoney());
    			 hongbaoMoney = hongbaoMoney.add(vo.getTotalHongbaoMoney());
    			 remainAmount = remainAmount.add(vo.getTotalRemainAmount());
    			 cowMoney = cowMoney.add(vo.getTotalCowMoney());
			}
    		/*total.put("count", count);
    		total.put("amount", amount);
    		total.put("manageFee", manageFee);
    		total.put("raiseFee", raiseFee);
    		total.put("balancePayMoney", balancePayMoney);
    		total.put("hongbaoMoney", hongbaoMoney);
    		total.put("remainAmount", remainAmount);
    		total.put("cowMoney", cowMoney);*/
    		InvestStatementVO vo = new InvestStatementVO();
    		vo.setNoob(2);
    		vo.setNoobString("合计");
    		vo.setTotalAmount(amount);
    		vo.setTotalBalancePayMoney(balancePayMoney);
    		vo.setTotalCount(count);
    		vo.setTotalCowMoney(cowMoney);
    		vo.setTotalHongbaoMoney(hongbaoMoney);
    		vo.setTotalManageFee(manageFee);
    		vo.setTotalRaiseFee(raiseFee);
    		vo.setTotalRemainAmount(remainAmount);
    		list.add(vo);
    		System.out.println(vo.toString());

            propertyHeaderMap.put("noobString", "项目");
            propertyHeaderMap.put("totalCount", "认养数量（只）");
            propertyHeaderMap.put("totalCowMoney", "购牛款");
            propertyHeaderMap.put("totalManageFee", "管理费");
            propertyHeaderMap.put("totalRaiseFee", "饲养费");
            propertyHeaderMap.put("totalAmount", "认养金额合计");
            propertyHeaderMap.put("totalBalancePayMoney", "余额支付");
            propertyHeaderMap.put("totalRemainAmount", "现金支付");
            propertyHeaderMap.put("totalHongbaoMoney", "红包支付");
            HSSFExcelUtils.ExpExs("认养统计", propertyHeaderMap, list, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
            DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 客户资金历史汇总查询
     * @author 张琼麒
     * @version 创建时间：2019年8月9日 上午10:49:58
     * @return
     */
    @RequestMapping(value = "/assetsTradeHistorySum/list")
	public String listAssetsTradeHistorySum(Model model, HttpServletResponse response,
			@RequestParam(required = false) Date beginDate ,@RequestParam(required = false) Date endDate) {
    	Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("report:assetsTradeHistorySum:list");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}

		return assetsTradeHistorySum(false ,model ,response ,beginDate, endDate);
	}

	/**
	 * 报表不分页,可以直接从网页上复制下来,不做导出
	 */
	// @RequestMapping(value = "/assetsTradeHistorySum/export")
	public String exportAssetsTradeHistorySum(Model model, HttpServletResponse response,
			@RequestParam(required = false) Date beginDate, @RequestParam(required = false) Date endDate) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("report:assetsTradeHistorySum:export");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}
		return assetsTradeHistorySum(true, model, response, beginDate, endDate);
	}
    private String assetsTradeHistorySum(Boolean isExport, Model model, HttpServletResponse response,
			@RequestParam(required = false) Date beginDate ,@RequestParam(required = false) Date endDate) {
    	Date computationDate=null;
    	if(endDate!=null) {
    		//+1天
    		computationDate=DateUtil.getAbsoluteDate(endDate, Calendar.DATE, 1);
		}

		Map<String,Double> assetsTradeSumMap=assetsService.getAssetsTradeSum(beginDate,computationDate);

		if(isExport) {
			try {
				/*
				ReportController.exportAssetsTradeHistorySum(
						beginDate==null?"":new SimpleDateFormat("yyyy-MM-dd").format(beginDate) + " " +
						saveEndDate==null?"":new SimpleDateFormat("yyyy-MM-dd").format(saveEndDate)	+ "客户资金汇总表",
						assetsTradeSumMap,response);
						*/
				List<Map<String,String>> keyValueList=new ArrayList<Map<String,String>>();
				for(Entry<String,Double> entry:assetsTradeSumMap.entrySet()) {
					System.out.println(entry.getKey()+": "+entry.getValue());
					Map<String,String> map = new HashMap<String,String>();
					map.put("key", entry.getKey());
					map.put("value", entry.getValue()+"");
					keyValueList.add(map);
				}

				LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
				propertyHeaderMap.put("key", " ");
				propertyHeaderMap.put("value", " ");
				HSSFExcelUtils.ExpExs(
						(beginDate==null?" ":new SimpleDateFormat("yyyy-MM-dd").format(beginDate) )+ "-" +
						(endDate==null?" ":new SimpleDateFormat("yyyy-MM-dd").format(endDate)) + "客户资金汇总表",
						propertyHeaderMap, keyValueList, response);

			} catch (Exception e) {
				logger.error(e);
			} finally {
				DownloadUtils.closeResponseOutput(response);
			}
		}else {
			model.addAttribute("beginDate", beginDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("assetsTradeSumMap", assetsTradeSumMap);
			return "/report/assetsTradeHistorySum/list";
		}

    	return "";
    }

	private static void exportAssetsTradeHistorySum(String title, Map<String, Double> assetsTradeSumMap,
			HttpServletResponse response) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title); // +workbook.getNumberOfSheets()

		HSSFRow row;
		HSSFCell cell;
		// 设置这些样式
		HSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);// 字体
		font.setFontHeightInPoints((short) 16);// 字号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		// font.setColor(HSSFColor.BLUE.index);//颜色

		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 设置单元格样式
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(font);
	}


    /**
     * 导出商城商品明细列表
     * @author sxy
     * @param model
     * @param response
     * @param startDate
     * @param endDate
     */
    @RequestMapping(value = "/exportOrderDetailList", method = RequestMethod.GET)
    public void exportOrderDetailList(Model model,HttpSession session, HttpServletResponse response,String trueName,
    		String keyword, Integer orderStatus,Integer payStatus, String goodsCategory, Date startTime,
    		Date endTime,Integer departmentId) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("report:exportOrderDetailList");
            } catch (Exception e) {
                throw new Exception("您没有权限操作");
            }
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId=null;
	        try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId=admin.getId();
			}
	        
            List<Map<String,Object>> detailList = goodsOrderDetailService.listGoodsOrderDetail(trueName, keyword, 
            		orderStatus, payStatus, goodsCategory, startTime, endTime, null, null,adminId,departmentId);
            for(Map<String,Object> map : detailList) {
                String payStatusStr = String.valueOf(map.get("pay_status"));
                String orderStatusStr = String.valueOf(map.get("order_status"));

                if(payStatusStr.equals("0")) {
                    map.put("pay_status", "成功");
                } else if(payStatusStr.equals("1")){
                    map.put("pay_status", "处理中");
                } else if(payStatusStr.equals("2")){
                    map.put("pay_status", "失败");
                }

                if(orderStatusStr.equals("0")) {
                    map.put("order_status", "未支付");
                } else if(orderStatusStr.equals("1")){
                    map.put("order_status", "支付中");
                } else if(orderStatusStr.equals("2")){
                    map.put("order_status", "已支付");
                } else if(orderStatusStr.equals("3")){
                    map.put("order_status", "拣货中");
                } else if(orderStatusStr.equals("4")){
                    map.put("order_status", "已发货");
                } else if(orderStatusStr.equals("5")){
                    map.put("order_status", "订单取消");
                } else if(orderStatusStr.equals("6")){
                    map.put("order_status", "订单退款");
                } else if(orderStatusStr.equals("7")){
                    map.put("order_status", "交易完成");
                } else if(orderStatusStr.equals("8")){
                    map.put("order_status", "退款中");
                }

                BigDecimal totalAmount = BigDecimal.ZERO;
                BigDecimal balancePayMoney = BigDecimal.ZERO;
                BigDecimal cashPayMoney = BigDecimal.ZERO;
                BigDecimal creditPayMoney = BigDecimal.ZERO;
                BigDecimal hongbaoMoney = BigDecimal.ZERO;

                balancePayMoney = (BigDecimal)map.get("balance_pay_money");
                cashPayMoney = (BigDecimal)map.get("cash_pay_money");
                creditPayMoney = (BigDecimal)map.get("credit_pay_money");
                hongbaoMoney = (BigDecimal)map.get("hongbao_money");

                map.put("total_amount", totalAmount);
                if(balancePayMoney != null || cashPayMoney != null || creditPayMoney != null || hongbaoMoney != null) {
                    //合计金额=余额支付+现金支付+授信支付+红包支付
                    totalAmount = totalAmount.add(balancePayMoney).add(cashPayMoney).add(creditPayMoney).add(hongbaoMoney);
                    map.put("total_amount", totalAmount);
                }
            }

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("id", "ID");
            propertyHeaderMap.put("create_date", "销售日期");
            propertyHeaderMap.put("order_no", "订单号");
            propertyHeaderMap.put("true_name", "客户名称");
            propertyHeaderMap.put("category_name", "商品类别");
            propertyHeaderMap.put("goods_name", "商品名称");
            propertyHeaderMap.put("count", "数量");
            propertyHeaderMap.put("sale_price", "单价(元)");
            propertyHeaderMap.put("total_amount", "合计金额(元)");
            propertyHeaderMap.put("balance_pay_money", "余额支付(元)");
            propertyHeaderMap.put("cash_pay_money", "现金支付(元)");
            propertyHeaderMap.put("credit_pay_money", "授信支付(元)");
            propertyHeaderMap.put("hongbao_money", "红包支付(元)");
            propertyHeaderMap.put("pay_status", "支付单状态");
            propertyHeaderMap.put("pay_date", "支付单支付日期");
            propertyHeaderMap.put("order_status", "订单状态");
            propertyHeaderMap.put("pay_time", "订单支付日期");
            propertyHeaderMap.put("refund_finish_time", "退款日期");

            HSSFExcelUtils.ExpExs("商品明细列表", propertyHeaderMap, detailList, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 导出商城订单列表
     * @author sxy
     * @param model
     * @param response
     * @param trueName
     * @param keyword
     * @param status
     * @param startTime
     * @param endTime
     * @param expressWay
     */
    @RequestMapping(value = "/exportOrderList", method = RequestMethod.GET)
    public void exportOrderList(Model model, HttpServletResponse response, HttpSession session, String trueName, String keyword, @RequestParam(required = false)List<Integer> status, Date startTime, Date endTime, Integer expressWay,
			Date payStartTime,Date payEndTime, Date refundFinishStartTime, Date refundFinishEndTime, Integer departmentId, String payChannel) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("report:exportOrderList");
            } catch (Exception e) {
                throw new Exception("您没有权限操作");
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId=null;
	        try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId=admin.getId();
			}
            List<Map<String, Object>> orderList = goodsOrderService.listGoodsOrder(trueName, null, keyword, status,
                startTime, DateUtil.getDateAfter(endTime,1), null, null, null, expressWay,
                payStartTime,DateUtil.getDateAfter(payEndTime,1),refundFinishStartTime,DateUtil.getDateAfter(refundFinishEndTime,1)
                ,adminId,departmentId, payChannel);

            for(Map<String,Object> map : orderList) {
                String orderStatusStr = String.valueOf(map.get("state"));
                BigDecimal totalMoney = (BigDecimal)map.get("total_money");
                String payChannelStr = String.valueOf(map.get("pay_channel"));
                //订单状态
                if(orderStatusStr.equals("0")) {
                    map.put("order_status", "未支付");
                } else if(orderStatusStr.equals("1")){
                    map.put("order_status", "支付中");
                } else if(orderStatusStr.equals("2")){
                    map.put("order_status", "已支付");
                } else if(orderStatusStr.equals("3")){
                    map.put("order_status", "拣货中");
                } else if(orderStatusStr.equals("4")){
                    map.put("order_status", "已发货");
                } else if(orderStatusStr.equals("5")){
                    map.put("order_status", "订单取消");
                } else if(orderStatusStr.equals("6")){
                    map.put("order_status", "订单退款");
                } else if(orderStatusStr.equals("7")){
                    map.put("order_status", "交易完成");
                } else if(orderStatusStr.equals("8")){
                    map.put("order_status", "退款中");
                }
                //是否包邮,大于等于200包邮  小于200不包邮
                if(totalMoney.intValue() >= 200) {
                    map.put("expressWay","包邮");
                } else if(totalMoney.intValue() < 200) {
                    map.put("expressWay","不包邮");
                }
                //代付通道
                if(!payChannelStr.equals("null")) {
                    map.put("pay_channel", OutPayEnum.getValueByName(payChannelStr).getDescription());
                } else {
                    map.put("pay_channel", "");
                }
            }

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("id", "ID");
            propertyHeaderMap.put("order_no", "订单号");
            propertyHeaderMap.put("true_name", "真实姓名");
            propertyHeaderMap.put("count", "商品种类个数");
            propertyHeaderMap.put("total_money", "订单金额");
            propertyHeaderMap.put("balance_pay_money", "余额支付金额(元)");
            propertyHeaderMap.put("credit_pay_money", "授信支付金额(元)");
            propertyHeaderMap.put("hongbao_money", "红包金额(元)");
            propertyHeaderMap.put("real_pay_money", "第三方支付金额(元)");
            propertyHeaderMap.put("pay_channel", "代付通道");
            propertyHeaderMap.put("order_status", "订单状态");
            propertyHeaderMap.put("expressWay", "快递方式");
            propertyHeaderMap.put("create_date", "购买时间");

            HSSFExcelUtils.ExpExs("商城订单列表", propertyHeaderMap, orderList, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 导出退款审核列表
     * @author sxy
     * @param model
     * @param response
     * @param trueName
     * @param keyword
     * @param auditStatus
     * @param startTime
     * @param endTime
     */
    @RequestMapping(value = "/exportRefundList", method = RequestMethod.GET)
    public void refundList(Model model, HttpSession session, HttpServletResponse response, String trueName, String keyword, 
    		Integer auditStatus, Date startTime, Date endTime, Date auditStartTime, Date auditEndTime, Integer departmentId, String payChannel) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("report:exportRefundList");
            } catch (Exception e) {
                throw new Exception("您没有权限操作");
            }
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId=null;
	        try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId=admin.getId();
			}
            List<Map<String, Object>> refundList = goodsOrderRefundService.listGoodsOrderRefund(trueName, keyword,
                auditStatus, startTime, endTime, auditStartTime, auditEndTime, null, null,adminId,departmentId, payChannel);

            for(Map<String,Object> map : refundList) {
                String orderStatusStr = String.valueOf(map.get("state"));
                String auditStatusStr = String.valueOf(map.get("status"));
                String payChannelStr = String.valueOf(map.get("pay_channel"));
                //订单状态
                if(orderStatusStr.equals("0")) {
                    map.put("order_status", "未支付");
                } else if(orderStatusStr.equals("1")){
                    map.put("order_status", "支付中");
                } else if(orderStatusStr.equals("2")){
                    map.put("order_status", "已支付");
                } else if(orderStatusStr.equals("3")){
                    map.put("order_status", "拣货中");
                } else if(orderStatusStr.equals("4")){
                    map.put("order_status", "已发货");
                } else if(orderStatusStr.equals("5")){
                    map.put("order_status", "订单取消");
                } else if(orderStatusStr.equals("6")){
                    map.put("order_status", "订单退款");
                } else if(orderStatusStr.equals("7")){
                    map.put("order_status", "交易完成");
                } else if(orderStatusStr.equals("8")){
                    map.put("order_status", "退款中");
                }
                //审核状态
                if(auditStatusStr.equals("0")) {
                    map.put("audit_status", "提交申请");
                } else if(auditStatusStr.equals("1")){
                    map.put("audit_status", "审核中");
                } else if(auditStatusStr.equals("2")){
                    map.put("audit_status", "通过");
                } else if(auditStatusStr.equals("3")){
                    map.put("audit_status", "打回");
                }
                //代付通道
                if(!payChannelStr.equals("null")) {
                    map.put("pay_channel", OutPayEnum.getValueByName(payChannelStr).getDescription());
                } else {
                    map.put("pay_channel", "");
                }
            }

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("id", "ID");
            propertyHeaderMap.put("order_no", "订单号");
            propertyHeaderMap.put("user_name", "真实姓名");
            propertyHeaderMap.put("total_money", "订单金额(元)");
            propertyHeaderMap.put("balance_pay_money", "余额支付金额(元)");
            propertyHeaderMap.put("credit_pay_money", "授信支付金额(元)");
            propertyHeaderMap.put("hongbao_money", "红包金额(元)");
            propertyHeaderMap.put("real_pay_money", "第三方支付金额(元)");
            propertyHeaderMap.put("pay_channel", "代付通道");
            propertyHeaderMap.put("order_status", "订单状态");
            propertyHeaderMap.put("reason", "申请原因");
            propertyHeaderMap.put("create_date", "申请时间");
            propertyHeaderMap.put("audit_status", "审核状态");
            propertyHeaderMap.put("audit_user_name", "审核人员");
            propertyHeaderMap.put("audit_remark", "审核说明");
            propertyHeaderMap.put("update_date", "审核时间");

            HSSFExcelUtils.ExpExs("退款审核列表", propertyHeaderMap, refundList, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * @description 红包列表导出
     * @author shuys
     * @date 2019/8/19
     * @param keyword
     * @param application
     * @param type
     * @param startSendTime
     * @param endSendTime
     * @param startUseTime
     * @param endUseTime
     * @param response
     * @param session
     * @return void
    */
    @RequestMapping(value = "export/hongbao", method = RequestMethod.GET)
    public void exportHongbaoList(String keyword, Integer application, Integer type, Date startSendTime, Date endSendTime,
    		Date startUseTime, Date endUseTime, HttpServletResponse response, HttpSession session,Integer departmentId) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("hongbao:export");
            } catch (Exception e) {
                throw new Exception("您没有权限操作");
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = null;
            try {
                subject.checkPermission("user:seealluser");
            }catch (Exception e) {
                adminId = admin.getId();
            }
            List<Map<String, Object>> list = hongbaoService.query(keyword, application, type, startSendTime, endSendTime, startUseTime, endUseTime, null, null, adminId, departmentId);
            if (list != null && !list.isEmpty()) {
                for (Map<String, Object> temp : list) {
                    Integer _type = (Integer) temp.get("type");
                    String _typeMsg = "";
                    if (_type != null) {
                        switch (_type) {
                            case 1:
                                _typeMsg = "现金红包";
                                break;
                            case 2:
                                _typeMsg = "牧场红包";
                                break;
                            case 3:
                                _typeMsg = "商城券";
                                break;
                            default:
                                _typeMsg = "";
                                break;
                        }
                    }
                    temp.put("typeMsg", _typeMsg);
                }
            }
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            // 获取要导出的数据，根据前台status来查
            propertyHeaderMap.put("id", "id");
            propertyHeaderMap.put("trueName", "真实姓名");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("amount", "红包金额");
            propertyHeaderMap.put("typeMsg", "类型");
            propertyHeaderMap.put("descript", "红包标题");
            propertyHeaderMap.put("send_time", "发出时间");
            propertyHeaderMap.put("use_time", "使用时间");
            propertyHeaderMap.put("expire_time", "过期时间");
//            list = getList(session, list);
            String title = "奔富畜牧业红包列表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
            DownloadUtils.closeResponseOutput(response);
        }
    }



    /**
     * 运营报表页面
     * @author sxy
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = "/operateReport", method = RequestMethod.GET)
    public String operateReport(Model model, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("report:operateReport");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }

        return "/report/operateReport";
    }

    /**
     * 运营报表(物权交易统计)
     * @author sxy
     * @param model
     * @param response
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/operateReportInvestAjax", method = RequestMethod.GET)
    public void operateReportInvestAjax(Model model, HttpServletResponse response,
                                        @RequestParam(required = false) Date startTime,
                                        @RequestParam(required = false) Date endTime
    ) {
        try {
            Date startTimeNew = DateUtil.getDayStartDate(startTime);
            Date endTimeNew = DateUtil.getAbsoluteDate(endTime, Calendar.DATE, 1);
            //物权交易总头数
            List<Integer> statusInvestArr = new ArrayList<Integer>();
            statusInvestArr.add(3); //已出售
            statusInvestArr.add(4); //已回购
            long investCount = projectService.countProjectView(statusInvestArr, null, null, null, null, null, startTimeNew, endTimeNew, null, null,null,null, null, null, null);
            //已购回总头数
            List<Integer> statusBuyBackArr = new ArrayList<Integer>();
            statusBuyBackArr.add(4); //已回购
            long buyBackCount = projectService.countProjectView(statusBuyBackArr, null, null, null, null, null, null, null, startTimeNew, endTimeNew,null,null, null, null, null);
            //物权交易成功总额(含红包)
            Map sumInvestment = investmentService.sumInvestment(startTimeNew, endTimeNew, null);
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal noobNum = BigDecimal.ZERO;
            BigDecimal monthNum = BigDecimal.ZERO;
            BigDecimal seasonNum = BigDecimal.ZERO;
            BigDecimal halfYearNum = BigDecimal.ZERO;
            BigDecimal yearNum = BigDecimal.ZERO;
            BigDecimal hongbaoMoney = BigDecimal.ZERO;
            if(sumInvestment != null) {
                totalAmount = BigDecimal.valueOf((double)sumInvestment.get("amount")); //物权交易总额
                noobNum = (BigDecimal)sumInvestment.get("noob_num"); //新手交易头数
                monthNum = (BigDecimal)sumInvestment.get("30_num"); //30天交易头数
                seasonNum = (BigDecimal)sumInvestment.get("90_num"); //90天交易头数
                halfYearNum = (BigDecimal)sumInvestment.get("180_num"); //180天交易头数
                yearNum = (BigDecimal)sumInvestment.get("360_num"); //360天交易头数
                hongbaoMoney = (BigDecimal)sumInvestment.get("hongbao_money"); //红包使用金额
            }

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("startTime",startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            map.put("endTime",endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            map.put("investCount",investCount);
            map.put("buyBackCount",buyBackCount);
            map.put("totalAmount",totalAmount);
            map.put("noobNum",noobNum);
            map.put("monthNum",monthNum);
            map.put("seasonNum",seasonNum);
            map.put("halfYearNum",halfYearNum);
            map.put("yearNum",yearNum);
            map.put("hongbaoMoney",hongbaoMoney);
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运营报表(饲养期头数统计)
     * @author sxy
     * @param model
     * @param response
     * @param feedTime
     * @return
     */
    @RequestMapping(value = "/operateReportFeedAjax", method = RequestMethod.GET)
    public void operateReportFeedAjax(Model model, HttpServletResponse response,
                                      @RequestParam(required = false) Date feedTime
    ) {
        try {
            //饲养期总头数
            if(feedTime == null){
                feedTime = new Date();
            }
            //物权交易总头数
            List<Integer> statusInvestArr = new ArrayList<Integer>();
            statusInvestArr.add(3); //已出售
            statusInvestArr.add(4); //已回购
            long feedCount = projectService.countProjectView(statusInvestArr, null, null, null, null, null, null, null, null, null,null,null,feedTime, null, null);

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("feedTime",feedTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(feedTime) : null);
            map.put("feedCount",feedCount);
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 客户资金历史汇总查询
     * @author 张琼麒
     * @version 创建时间：2019年8月9日 上午10:49:58
     * @return
     */
    @RequestMapping(value = "/assetsSnapshootVO/list")
	public String listAssetsSnapshootVO(Model model, HttpServletResponse response,
			@RequestParam(required = false) Date datePoint , @RequestParam(required = false) String orderBy, @RequestParam(required = false) String adesc,
			@RequestParam(required = false) Integer page , @RequestParam(required = false) Integer pageSize) {
    	Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("report:assetsSnapshootVO:list");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}
		if (page == null) {
			page = 1;
		}
		if(pageSize==null) {
			pageSize = 10;
		}
		
		return listAssetsSnapshootVO(false ,model ,response , datePoint, orderBy,adesc,page,pageSize);
	}

	/**
	 * 报表不分页,可以直接从网页上复制下来,不做导出
	 */
	@RequestMapping(value = "/assetsSnapshootVO/export")
	public String exportAssetsSnapshootVO(Model model, HttpServletResponse response,
			@RequestParam(required = false) Date datePoint , @RequestParam(required = false) String orderBy, @RequestParam(required = false) String adesc ) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("report:assetsSnapshootVO:export");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}
		return listAssetsSnapshootVO(true, model, response , datePoint, orderBy,adesc, -1, 10);
	}
    
	
    private String listAssetsSnapshootVO(Boolean isExport,Model model, HttpServletResponse response, Date datePoint,
    		String orderBy ,String adesc ,Integer page, Integer pageSize) {
    	Date computationDate=null;
    	if(datePoint!=null) {
    		//0点
    		datePoint=DateUtil.getDayStartDate(datePoint);
    		//+1天
    		computationDate=DateUtil.getAbsoluteDate(DateUtil.getDayStartDate(datePoint), Calendar.DATE, 1);

		}
    	
    	AssetsExample example=new AssetsExample();
		example.setDatePoint(computationDate);
		example.setLimitStart((page - 1) * pageSize);
		example.setLimitEnd(pageSize);
		if(!StringUtils.isBlank(orderBy)) {
			example.setOrderByClause(orderBy+" "+adesc);
		}
		example.setTableName(jobService.getExistAssetsSnapshotTableName(computationDate));
		//AssetsExample.Criteria criteria=example.createCriteria();


		List<AssetsVO> assetsVOList = assetsMapper.listAssetsSnapshootVO(example);
		System.out.println("listAssetsSnapshootVO:" + JSON.toJSONString(assetsVOList));

		Long count = assetsMapper.countAssetsSnapshootVO(example);
		if (isExport) {
			try {
				LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
				propertyHeaderMap.put("rowNum", "序号");
				propertyHeaderMap.put("userTrueName", "客户名称");
				propertyHeaderMap.put("username", "用户名");
				propertyHeaderMap.put("wholeBalanceAmount", "余额");
				propertyHeaderMap.put("wholeCreditAmount", "授信金额");
				propertyHeaderMap.put("amount", "资产金额");
				propertyHeaderMap.put("sumAmount", "合计金额");
				
				HSSFExcelUtils.ExpExs((datePoint==null?" ":new SimpleDateFormat("yyyy-MM-dd").format(datePoint)) + "用户资金快照",
						propertyHeaderMap, assetsVOList, response);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			} finally {
				DownloadUtils.closeResponseOutput(response);
			}
		} else {
			int pages = calcPage(count.intValue(), pageSize);
			model.addAttribute("pages", pages);
			model.addAttribute("list", assetsVOList);
			model.addAttribute("page", page);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("orderBy", orderBy);
			model.addAttribute("adesc", adesc);
			model.addAttribute("datePoint", datePoint);
			model.addAttribute("count", count);
			return "/report/assetsSnapshootVO/list";
		}
		return "";
	}


    /**
     * 迁移用户信息列表
     * @param model
     * @param response
     * @param keyword
     * @param mirgationTime
     * @return
     */
    @RequestMapping(value = "/mirgationUser/list", method = RequestMethod.GET)
    public String mirgationUserReport(Model model, HttpServletResponse response,
                             @RequestParam(required = false) String keyword,
                             @RequestParam(required = false) Date mirgationTime, 
                             @RequestParam(required = false) Integer page
    ) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("report:mirgationUser:list");
            } catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            int limit = 20;
            if (page == null) {
                page = 1;
            }
//            if (mirgationTime == null) {
//                mirgationTime = new Date();
//            }
            List<Map<String, Object>> list = userService.listMirgationUserReport(keyword, mirgationTime, (page - 1) * limit, limit);
            int count = userService.countMirgationUserReport(keyword, mirgationTime);
            int pages = calcPage(count, limit);
            model.addAttribute("list", list);
            model.addAttribute("pages", pages);
            model.addAttribute("page", page);
            model.addAttribute("keyword", keyword);
            model.addAttribute("mirgationTime", mirgationTime == null ? "" : DateUtil.dateFormat.format(mirgationTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/report/mirgationUser/list";
    }
    
    
    /**
     * 分公司售牛统计
     * @param model
     * @param response
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/filialeSell")
    public String reportFilialeSellBulls(Model model, HttpServletResponse response,
    		@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,Integer page) {
    	Date startTimeNew = DateUtil.parse(startDate,"yyyy-MM-dd");
        Date endTimeNew = DateUtil.parse(endDate,"yyyy-MM-dd");
    	
        Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("invest:filialeSell");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}
	    int limit = 10;
        if (page == null) {
        	page = 1;
        }
		List<FilialeSellVO> list = projectService.listFilialeSell((page - 1) * limit,limit,keyword, startTimeNew, endTimeNew);
		int count = projectService.countFilialeSell(keyword, startTimeNew, endTimeNew);
		int pages = calcPage(count, limit);
		
		model.addAttribute("pages", pages);
        model.addAttribute("page", page);
		model.addAttribute("list", list); 
		model.addAttribute("startDate", startTimeNew);
		model.addAttribute("keyword", keyword);
		model.addAttribute("endDate", endTimeNew);
		return "/report/listFilialeSell";
    }
    
    
    @RequestMapping(value = "export/filialeSell")
    public void exportFilialeSellBulls(Model model, HttpServletResponse response,
    		@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws Exception {
    	try {
	    	Date startTimeNew = DateUtil.parse(startDate,"yyyy-MM-dd");
	        Date endTimeNew = DateUtil.parse(endDate,"yyyy-MM-dd");
	    	
	        Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("invest:filialeSell");
			} catch (Exception e) {
				 throw new Exception("您没有权限操作");
			}
		    
			List<FilialeSellVO> list = projectService.listFilialeSell(null, null,keyword, startTimeNew, endTimeNew); 
		
			for (int i = 0; i < list.size(); i++) {
				FilialeSellVO filialeSellVO = list.get(i);
				filialeSellVO.setRowNum(i+1);
				filialeSellVO.setTotalNum(filialeSellVO.getLimitDays30()+filialeSellVO.getLimitDays90()+
						filialeSellVO.getLimitDays180()+filialeSellVO.getLimitDays360());
				filialeSellVO.setTotalInvest(filialeSellVO.getInvestTotalAmount30().add(filialeSellVO.getInvestTotalAmount90())
						.add(filialeSellVO.getInvestTotalAmount180()).add(filialeSellVO.getInvestTotalAmount360()));
			}
			LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
			propertyHeaderMap.put("empId", "ID");
			propertyHeaderMap.put("company", "所属公司");
			propertyHeaderMap.put("realName", "姓名");
			propertyHeaderMap.put("mobile", "手机号");
			propertyHeaderMap.put("limitDays30", "30天");
			propertyHeaderMap.put("investTotalAmount30", "30天总金额");
			
			propertyHeaderMap.put("limitDays90", "90天");
			propertyHeaderMap.put("investTotalAmount90", "90天总金额");
			
			propertyHeaderMap.put("limitDays180", "180天");
			propertyHeaderMap.put("investTotalAmount180", "180天总金额");
			
			propertyHeaderMap.put("limitDays360", "360天");
			propertyHeaderMap.put("investTotalAmount360", "360天总金额");
			
			propertyHeaderMap.put("totalNum", "售牛数合计");
			propertyHeaderMap.put("totalInvest", "金额合计");
			
			HSSFExcelUtils.ExpExs(startDate + "分公司售牛统计",
					propertyHeaderMap, list, response);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error(e);
		} finally {
			 DownloadUtils.closeResponseOutput(response);
		}
    }
    
    /**
     * 分公司售牛统计详细
     * @param model
     * @param response
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/filialeSellDetail")
    public String filialeSellDetail(Model model, HttpServletResponse response,
    		Integer empId,
    		@RequestParam(required = false) String keyword,
    		@RequestParam(required = false) String recommendKeyword,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,Integer page) {
    	try {
    		Date startTimeNew = DateUtil.parse(startDate,"yyyy-MM-dd");
            Date endTimeNew = DateUtil.parse(endDate,"yyyy-MM-dd");
        	
            Subject subject = SecurityUtils.getSubject();
    		try {
    			subject.checkPermission("invest:filialeSellDetail");
    		} catch (Exception e) {
    			model.addAttribute("error", "您没有权限查看");
    			return "error";
    		}
    	    int limit = 10;
            if (page == null) {
            	page = 1;
            }
            
            List<FilialeSellDetailVO> list = projectService.detailFilialeSell(empId,keyword, recommendKeyword,(page - 1) * limit,limit, startTimeNew, endTimeNew);
    		int count = projectService.countDetailFilialeSell(empId, keyword, recommendKeyword, startTimeNew, endTimeNew);
    		int pages = calcPage(count, limit);
            
    		BigDecimal totalAmount = projectService.statisticsFilialeSell(empId,keyword, recommendKeyword,startTimeNew, endTimeNew);
    		
    		model.addAttribute("pages", pages);
            model.addAttribute("page", page);
    		model.addAttribute("list", list); 
    		model.addAttribute("keyword", keyword); 
    		model.addAttribute("empId", empId); 
    		model.addAttribute("recommendKeyword", recommendKeyword); 
    		model.addAttribute("startDate", startTimeNew);
    		model.addAttribute("endDate", endTimeNew);
    		model.addAttribute("totalAmount", totalAmount);
    		return "/report/detailFilialeSell";
		} catch (Exception e) {
			e.printStackTrace();
    		logger.error(e);
		}
    	return null;
    }
    
    
    
    @RequestMapping(value = "export/filialeSellDetail")
    public void exportFilialeSellDetail(Model model, HttpServletResponse response,
    		Integer empId,
    		@RequestParam(required = false) String keyword,
    		@RequestParam(required = false) String recommendKeyword,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws Exception {
    	try {
	    	Date startTimeNew = DateUtil.parse(startDate,"yyyy-MM-dd");
	        Date endTimeNew = DateUtil.parse(endDate,"yyyy-MM-dd");
	    	
	        Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("invest:export:filialeSellDetail");
			} catch (Exception e) {
				 throw new Exception("您没有权限操作");
			}
		    
			List<FilialeSellDetailVO> list = projectService.detailFilialeSell(empId,keyword, recommendKeyword,null,null, startTimeNew, endTimeNew); 
		
			for (int i = 0; i < list.size(); i++) {
				FilialeSellDetailVO detail = list.get(i);
				detail.setRowNum(i+1);
				if(detail.getIsExpire() == 1) {
					detail.setIsExpireStr("是");
				} else {
					detail.setIsExpireStr("否");
				}
			}
			LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
			propertyHeaderMap.put("rowNum", "序号"); 
			propertyHeaderMap.put("realName", "姓名");
			propertyHeaderMap.put("title", "项目名称");
			propertyHeaderMap.put("mobile", "手机号");
			propertyHeaderMap.put("createDate", "领养时间");
			propertyHeaderMap.put("number", "领养只数");
			propertyHeaderMap.put("limitDays", "领养期限");
			propertyHeaderMap.put("amount", "领养金额");
			propertyHeaderMap.put("isExpireStr", "到期否"); 
			
			HSSFExcelUtils.ExpExs(startDate + "分公司售牛统计详细",
					propertyHeaderMap, list, response);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error(e);
		} finally {
			 DownloadUtils.closeResponseOutput(response);
		}
    }
    
    /**
     * 导出订单运费列表
     * @author sxy
     * @param model
     * @param session
     * @param response
     * @param orderNo
     * @param skuCode
     * @param orderStatus
     * @param payStartTime
     * @param payEndTime
     */
    @RequestMapping(value = "/exportCarriageList", method = RequestMethod.GET)
    public void exportCarriageList(Model model, HttpSession session, HttpServletResponse response, String orderNo, String skuCode, Integer orderStatus,
        Date payStartTime, Date payEndTime) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("report:exportCarriageList");
            } catch (Exception e) {
                throw new Exception("您没有权限操作");
            }
//            UserAdmin admin = (UserAdmin) session.getAttribute("user");
//            Integer adminId=null;
//            try {
//                subject.checkPermission("user:seealluser");
//            } catch (Exception e) {
//                adminId=admin.getId();
//            }
            
            List<Map<String, Object>> list = goodsOrderService.listCarriage(orderNo, skuCode, orderStatus, payStartTime, payEndTime, null, null);

            for(Map<String,Object> map : list) {
                String orderStatusStr = String.valueOf(map.get("state"));
                //订单状态
                if(orderStatusStr.equals("0")) {
                    map.put("order_status", "未支付");
                } else if(orderStatusStr.equals("1")){
                    map.put("order_status", "支付中");
                } else if(orderStatusStr.equals("2")){
                    map.put("order_status", "已支付");
                } else if(orderStatusStr.equals("3")){
                    map.put("order_status", "拣货中");
                } else if(orderStatusStr.equals("4")){
                    map.put("order_status", "已发货");
                } else if(orderStatusStr.equals("5")){
                    map.put("order_status", "订单取消");
                } else if(orderStatusStr.equals("6")){
                    map.put("order_status", "订单退款");
                } else if(orderStatusStr.equals("7")){
                    map.put("order_status", "交易完成");
                } else if(orderStatusStr.equals("8")){
                    map.put("order_status", "退款中");
                }
            }

            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("id", "ID");
            propertyHeaderMap.put("order_no", "订单号");
            propertyHeaderMap.put("sku_code", "SKU码");
            propertyHeaderMap.put("name_and_count", "商品名称(个数)");
            propertyHeaderMap.put("true_name", "姓名");
            propertyHeaderMap.put("phone", "手机号");
            propertyHeaderMap.put("addressee_detail", "地址");
            propertyHeaderMap.put("total_money", "订单金额(元)");
            propertyHeaderMap.put("order_status", "订单状态");
            propertyHeaderMap.put("weight_sum", "总重量(KG)");
            propertyHeaderMap.put("express_fee", "快递费(元)");
            propertyHeaderMap.put("real_express_fee", "实际邮费(元)");
            propertyHeaderMap.put("pay_time", "付款时间");

            HSSFExcelUtils.ExpExs("订单运费列表", propertyHeaderMap, list, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DownloadUtils.closeResponseOutput(response);
        }
    }
    
}
