package com.goochou.p2b.admin.web.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.WithdrawStatusEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.model.AllAssetsExcel;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.Withdraw;
import com.goochou.p2b.model.vo.CapitalDetail;
import com.goochou.p2b.model.vo.TransactionRecordVO;
import com.goochou.p2b.model.vo.WithdrawVO;
import com.goochou.p2b.service.CustomerListService;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.service.WithdrawCouponService;
import com.goochou.p2b.service.WithdrawService;
import com.goochou.p2b.utils.AjaxUtil;
import com.goochou.p2b.utils.CommonUtils;
import com.goochou.p2b.utils.DownloadUtils;
import com.goochou.p2b.utils.HSSFExcelUtils;

@Controller
@RequestMapping(value = "/withdraw")
public class WithDrawController extends BaseController {
	private static final Logger logger = Logger.getLogger(WithDrawController.class);
    @Resource
    private WithdrawService withdrawService;
    @Resource
    private UserAdminService userAdminServices;
    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private UserAdminService userAdminService;
    @Resource
    private CustomerListService customerListService;
    @Resource
    private WithdrawCouponService withdrawCouponService;
    @Resource
    private DepartmentService departmentService;
    
    @RequestMapping(value = "/demo")
    public String query() {
        return "/withdraw/demo";
    }

    @RequestMapping(value = "/list")
    public String query(Model model, String keyword, String payChannel, Date createStartTime, Date createEndTime, 
    		Date startTime, Date endTime, @RequestParam(required = false) Integer type, Integer status,
    		@RequestParam(required = false) Integer page, HttpSession session, Integer departmentId) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("withdraw:view");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            // 用来获取当前角色的角色名称
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            String username = ((UserAdmin) session.getAttribute("user")).getUsername();
            Integer adminId = null;
//            if (roleName.equalsIgnoreCase("normalCustomer")) {
//                adminId = admin.getId();
//            }
//            Subject subject = SecurityUtils.getSubject();
//            subject.isPermitted("user:seealluser","user:seealluser");
            Integer superDepartmentId = null;
            if(!subject.isPermitted("user:seealluser") && !subject.isPermitted("settle:allUser")) {
            	logger.info("没有全部用户权限============");
            	adminId = admin.getId();
            	superDepartmentId = admin.getDepartmentId();
            } else {
            	superDepartmentId = 0;
            }
            
            /*
            try {
                subject.checkPermission("user:seealluser");
                
            }catch (Exception e) {
            	adminId = admin.getId();
            }*/
            
            
            
            
            
            UserAdmin user = userAdminServices.getByUsername(username);
            user.setRoleName(userAdminServices.getRoleNameByUserId(user.getId()));
            //List<Map<String, Object>> list = withdrawService.query(keyword, method, startTime, endTime, status, type, (page - 1) * limit, limit, adminId);
            List<Map<String, Object>> list = withdrawService.query1(keyword, payChannel, createStartTime,
            		createEndTime, startTime, endTime, status, type, (page - 1) * limit, limit, adminId, departmentId);
//            for (int i = 0; i < list.size(); i++) {
//                Map map = list.get(i);
//                if (map.get("user_id") != null) {
//                    String trueName = customerListService.selectTrueNameByUserId(Integer.valueOf(map.get("user_id").toString()));
//                    if (trueName != null) {
//                        map.put("cusName", trueName);
//                    }
//                }
//            }

            Map<String, Object> result = withdrawService.queryCount1(keyword, payChannel, createStartTime,
            		createEndTime, startTime, endTime, status, type, adminId, departmentId);
            Long count = 0L;
            if (result != null) {
                count = (Long) result.get("count");
                model.addAttribute("totalAmount", result.get("amount") != null ? new DecimalFormat("#.00").format(Double.valueOf(result.get("amount").toString())) : 0);
            }
            int pages = calcPage(count.intValue(), limit);
            model.addAttribute("startTime", startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            model.addAttribute("endTime", endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            model.addAttribute("createStartTime", createStartTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(createStartTime) : null);
            model.addAttribute("createEndTime", createEndTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(createEndTime) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("payChannel", payChannel);
            model.addAttribute("status", status);
            model.addAttribute("keyword", keyword);
            model.addAttribute("user", user);
            model.addAttribute("type", type);
            model.addAttribute("withdrawStatus", WithdrawStatusEnum.values());
            model.addAttribute("payChannels", OutPayEnum.values());
            model.addAttribute("clients", ClientEnum.values());
            model.addAttribute("departmentId", departmentId);
          
			model.addAttribute("departments", departmentService.getShowDepartments(superDepartmentId));

        } catch (Exception e) {
            logger.error(e);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "/withdraw/list";
    }

    /**
     * 提现审核
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    public String audit(int id, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("withdraw:audit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            // 用来获取当前角色的角色名称
            String username = ((UserAdmin) session.getAttribute("user")).getUsername();
            UserAdmin user = userAdminServices.getByUsername(username);
            user.setRoleName(userAdminServices.getRoleNameByUserId(user.getId()));
//            Withdraw draw = withdrawService.detail(id);
            WithdrawVO draw = withdrawService.getWithdrawDetailById(id);
            model.addAttribute("draw", draw);
            model.addAttribute("user", user);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/withdraw/audit";
    }

    /**
     * @description 更新审核为  银行处理中
     * @author shuys
     * @date 2019/6/6
     * @param id
     * @param model
     * @param session
     * @return java.lang.String
    */
    @RequestMapping(value = "/auditBankProcess", method = RequestMethod.POST)
    public String auditBankProcess(int id, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("withdraw:audit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            // 用来获取当前角色的角色名称
//            String username = ((UserAdmin) session.getAttribute("user")).getUsername();
//            UserAdmin user = userAdminServices.getByUsername(username);
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            
            WithdrawVO draw = withdrawService.getWithdrawDetailById(id);
            if (draw == null ) {
                throw new Exception("提现订单不存在");
            }
            if (draw.getStatus().intValue() != WithdrawStatusEnum.AUDIT.getCode().intValue()) {
                throw new Exception("状态不是审核状态");
            }

            //////
            // 更新审核状态为 银行处理中
            Withdraw withdraw = new Withdraw();
            withdraw.setId(id);
            withdraw.setOrderNo(draw.getOrderNo());
            withdraw.setStatus(WithdrawStatusEnum.BANK_PROCESS.getCode());
            withdraw.setTechOperateUserId(admin.getId());
            withdraw.setTechOperateTime(new Date());
            withdrawService.update(withdraw);
            //////

            // TODO 发送短信 简单处理，待后期改造
            String orderNo = draw.getOrderNo();
            String cardNo = draw.getCardNo();
            String phone = draw.getPhone();
            // 发送短信
            SendMessageRequest smr = new SendMessageRequest();
            String messageContent = DictConstants.WITHDRAW_SUCCESS_VALIDATE_CODE;
            messageContent = messageContent.replaceAll("\\{orderNo}", orderNo);
            messageContent = messageContent.replaceAll("\\{cardNo}", CommonUtils.getCardFour(cardNo));
            smr.setContent(messageContent);
            smr.setPhone(phone);
            ServiceMessage msg = new ServiceMessage("message.send", smr);
            OpenApiClient.getInstance().setServiceMessage(msg).send();

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }

        return "redirect:/withdraw/list";
    }
    
    /**
     * 拒绝该次提现,返还资金
     * @author sxy
     * @param id
     * @param model
     * @param remark
     * @return
     */
    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    @ResponseBody
    public String refuse(Integer id, Model model, HttpSession session, String remark) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("withdraw:reject");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            
            WithdrawVO draw = withdrawService.getWithdrawDetailById(id);
            if (draw == null ) {
                throw new Exception("提现订单不存在");
            }
            if (draw.getStatus().intValue() != WithdrawStatusEnum.AUDIT.getCode().intValue()) {
                throw new Exception("状态不是审核状态");
            }
            
            Withdraw withdraw = new Withdraw();
            withdraw.setId(id);
            withdraw.setOrderNo(draw.getOrderNo());
            withdraw.setTechRemark(remark); //审核说明
            withdraw.setStatus(WithdrawStatusEnum.REFUSE.getCode());
            withdraw.setTechOperateUserId(admin.getId());
            withdraw.setTechOperateTime(new Date());
            withdrawService.audit(withdraw, 0, false);
            
            // TODO 发送短信 简单处理，待后期改造
            String orderNo = draw.getOrderNo();
            String cardNo = draw.getCardNo();
            String phone = draw.getPhone();
            // 发送短信
            SendMessageRequest smr = new SendMessageRequest();
            String messageContent = DictConstants.WITHDRAW_REFUSE_VALIDATE_CODE;
            messageContent = messageContent.replaceAll("\\{orderNo}", orderNo);
            messageContent = messageContent.replaceAll("\\{auditRemark}", remark);
            smr.setContent(messageContent);
            smr.setPhone(phone);
            ServiceMessage msg = new ServiceMessage("message.send", smr);
            OpenApiClient.getInstance().setServiceMessage(msg).send();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return "redirect:/withdraw/list";
    }

    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public String audit(Withdraw draw, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("withdraw:audit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            // 用来获取当前角色的角色名称
            String username = ((UserAdmin) session.getAttribute("user")).getUsername();
            UserAdmin uadmin = userAdminServices.getByUsername(username);
            // 技术
            if (draw.getTechOperateUserId() == null && draw.getFinanceOperateUserId() == null) {
                draw.setTechOperateUserId(uadmin.getId());
                draw.setTechOperateTime(new Date());
                if (draw.getStatus() != null) {
                    draw.setStatus(draw.getStatus());
                } else {
                    draw.setStatus(0);
                }
                if (draw.getTechRemark() != null && !"".equals(draw.getTechRemark())) {
                    draw.setTechRemark(draw.getTechRemark());
                }
                withdrawService.audit(draw, 0, false);
            } else if (draw.getFinanceOperateUserId() == null) {
                draw.setFinanceOperateTime(new Date());
                draw.setFinanceOperateUserId(uadmin.getId());
                if (draw.getStatus() != null) {
                    draw.setStatus(draw.getStatus());
                } else {
                    draw.setStatus(4); // 空的话修改成 银行处理中
                }
                if (draw.getTechRemark() != null && !"".equals(draw.getTechRemark())) {
                    draw.setTechRemark(draw.getTechRemark());
                }
                withdrawService.audit(draw, 0, false);
            }
//            else if (draw.getCeoOperateUserId() == null) {
//                draw.setCeoOperateUserId(uadmin.getId());
//                draw.setCeoOperateTime(new Date());
//                if (draw.getStatus() != null) {
//                    draw.setStatus(draw.getStatus());
//                }
//                if (draw.getCeoRemark() != null && !"".equals(draw.getCeoRemark())) {
//                    draw.setCeoRemark(draw.getCeoRemark());
//                }
//                withdrawService.audit(draw, 0, false);
//            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/withdraw/list?type=0&status=0";
    }

    /**
     * 活期提现失败 驳回，返还金额
     *
     * @return
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    @ResponseBody
    public String reject(Integer id, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("withdraw:reject");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            Withdraw w = withdrawService.get(id);
            w.setStatus(2);
            w.setTechRemark("新浪无记录，返还提现金额");
            withdrawService.audit(w, 1, false);

        } catch (Exception e) {
            logger.error(e);
            return "驳回失败，请重试";
        }
        return "驳回成功，请查询该用户账户情况核实";
    }

    @RequestMapping(value = "/bu", method = RequestMethod.POST)
    @ResponseBody
    public String bu(Integer id, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("withdraw:supplement");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            Withdraw w = withdrawService.get(id);
            w.setStatus(1);
            w.setTechRemark("通知失败,扣除冻结资金");
            withdrawService.audit(w, 1, false);

        } catch (Exception e) {
            logger.error(e);
            return "补单失败，请重试";
        }
        return "补单成功，请查询该用户账户情况核实";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Integer id, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("withdraw:detail");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            WithdrawVO draw = withdrawService.getWithdrawDetailById(id);
//            WithdrawCoupon wc = withdrawCouponService.getByWithdrawId(draw.getId());
//            model.addAttribute("wc", wc);
            model.addAttribute("draw", draw);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/withdraw/detail";
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String export(HttpServletRequest request, HttpServletResponse response, Integer status, String keyword) {
        LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        // 获取要导出的数据，根据前台status来查
        List<Withdraw> dataSet = withdrawService.getWithdrawForExport(status, keyword);
        // 要导出的列
        propertyHeaderMap.put("username", "提现用户名");
        propertyHeaderMap.put("trueName", "提现用户真实姓名");
        propertyHeaderMap.put("amount", "提现金额");
        propertyHeaderMap.put("cardNo", "提现卡号");
        propertyHeaderMap.put("bankName", "银行名称");
        propertyHeaderMap.put("bankBranchName", "支行地址");
        propertyHeaderMap.put("createTime", "提现申请时间");
        propertyHeaderMap.put("statusName", "提现状态");
        try {
            HSSFExcelUtils.ExpExs("用户提现", propertyHeaderMap, dataSet, response);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
        return null;
    }

    /**
     * 交易记录查询
     *
     * @return
     */
    @RequestMapping(value = "/trade", method = RequestMethod.GET)
    public void tradeRecord(HttpSession session,
                            @RequestParam Integer userId,
                            @RequestParam(required = false) Integer page,
                            @RequestParam(required = false) List<String> aoeTypes,
                            @RequestParam(required = false) Integer limit,
                            HttpServletResponse response) {
        try {
            if (limit == null || limit <= 0) {
                limit = 20;
            }
            page = page == null ? 1 : page;
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
//            List<Map<String, Object>> list = tradeRecordService.getWithRate(userId, aoeTypes, (page - 1) * limit, limit);
//            Integer count = tradeRecordService.getWithRateCount(userId, aoeTypes);
            List<TransactionRecordVO> list = tradeRecordService.queryTradeRecordByUserIdAndAoeType(userId, aoeTypes, (page - 1) * limit, limit, admin.getId());
            Integer count = tradeRecordService.queryTradeRecordCountByUserIdAndAoeType(userId, aoeTypes, admin.getId());
            int pages = calcPage(count, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("page", page);
            map.put("pages", pages);
            map.put("list", list);
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 所有用户资金明细查询
     *
     * @return
     */
    @RequestMapping(value = "/allAssets", method = RequestMethod.GET)
    public void allAssets(HttpServletResponse response, Integer page) {
        try {
            int limit = 20;
            page = page == null ? 1 : page;
            List<Map<String, Object>> list = userAdminServices.getAllUserAssets((page - 1) * limit, limit);
            Integer count = userAdminServices.getAllUserAssetsCount();
            int pages = calcPage(count, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("page", page);
            map.put("pages", pages);
            map.put("list", list);
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 所有用户资金明细查询
     *
     * @return
     */
    @RequestMapping(value = "/export/allAssets", method = RequestMethod.GET)
    public void allAssets(HttpServletResponse response) {
        try {
            List<AllAssetsExcel> list = userAdminServices.getAllUserAssetsExcel(0, 20);
            Integer count = userAdminServices.getAllUserAssetsCount();
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(new Date());
            // 获取要导出的数据，根据前台status来查

            propertyHeaderMap.put("num_id", "序号");
            propertyHeaderMap.put("username", "用户名");
            propertyHeaderMap.put("trueName", "真实姓名");
            propertyHeaderMap.put("rechargeAmount", "充值金额");
            propertyHeaderMap.put("totalInvestmentAmount", "实际投资");
            propertyHeaderMap.put("hongbao", "红包");
            propertyHeaderMap.put("frozenAmount", "冻结金额");
            propertyHeaderMap.put("totalIncome", "已收收益");
            propertyHeaderMap.put("capitalAmount", "已收本金");
            propertyHeaderMap.put("uncollectInterest", "待收收益");
            propertyHeaderMap.put("uncollectInterest", "佣金奖励");
            propertyHeaderMap.put("withdrawAmount", "提现金额");
            propertyHeaderMap.put("totalMoney", "资产总计");
            propertyHeaderMap.put("availableBalance", "可用余额");

            HSSFExcelUtils.ExpExs("用户资产统计总表", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

    /**
     * 所有用户资金明细查询
     *
     * @return
     */
    @RequestMapping(value = "/allCapitalDetail", method = RequestMethod.GET)
    public void allCapitalDetailByNow(HttpServletResponse response) {
        try {
            Date month = new Date();
            List<CapitalDetail> list = userAdminServices.getAllCapitalDetailForMonth(month);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", list);
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 所有用户资金明细查询
     *
     * @return
     */
    @RequestMapping(value = "/export/allCapitalDetail", method = RequestMethod.GET)
    public void allCapitalDetail(HttpServletResponse response) {
        try {
            List<CapitalDetail> list = userAdminServices.getAllCapitalDetail(0, 20);
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(new Date());
            // 获取要导出的数据，根据前台status来查

            propertyHeaderMap.put("num_id", "序号");
            propertyHeaderMap.put("investment", "投资总额");
            propertyHeaderMap.put("recharge", "充值总额");
            propertyHeaderMap.put("withdraw", "提现总额");
            propertyHeaderMap.put("earnings", "收益总额");
            propertyHeaderMap.put("receive", "回收本金总额");
            propertyHeaderMap.put("time", "时间");

            HSSFExcelUtils.ExpExs("鑫聚财资金明细总表", propertyHeaderMap, list, response);
        } catch (Exception e) {
            logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

}
