package com.goochou.p2b.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.constant.RechargeOfflineApplyStateEnum;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.RechargeOfflineApply;
import com.goochou.p2b.model.RechargeOfflineApplyExample;

import com.goochou.p2b.model.idGenerator.OrderIdGenerator;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.dao.RechargeOfflineApplyMapper;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.CustomerListService;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.RechargeOfflineApplyService;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.StringUtil;
import com.goochou.p2b.utils.StringUtils;

@Controller
@RequestMapping(value = "/recharge")
public class RechargeController extends BaseController {
	private static final Logger logger = Logger.getLogger(RechargeController.class);
    @Resource
    private RechargeService rechargeService;
    @Resource
    private UserAdminService userAdminService;
    @Resource
    private CustomerListService customerListService;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private RechargeOfflineApplyService rechargeOfflineApplyService;
    @Resource
    private UploadService uploadService;
    @Resource
    private RechargeOfflineApplyMapper rechargeOfflineApplyMapper;
    @Resource
    private OrderIdGenerator rechargeOrderIdGenerator;
    
    @RequestMapping(value = "/list")
    public String query(HttpSession session, Model model, String keyword, Integer status, Date startTime, String payChannel, Date endTime, Integer page,String client, String code,Integer departmentId) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:view");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            if (!StringUtils.isEmpty(keyword)) {
                keyword = keyword.replaceAll("\\s*", "");
                if (StringUtils.isEmpty(keyword)) {
                    keyword = null;
                }
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = null;
//            if (roleName.equalsIgnoreCase("normalCustomer")) {
//                adminId = admin.getId();
//            }
//            Subject subject = SecurityUtils.getSubject();
            Integer superDepartmentId = null;
            if(!subject.isPermitted("user:seealluser") && !subject.isPermitted("settle:allUser")) {
            	logger.info("没有全部用户权限============");
            	adminId = admin.getId();
            	superDepartmentId = admin.getDepartmentId();
            } else {
            	superDepartmentId = 0;
            }
            
           /* try {
                subject.checkPermission("user:seealluser");
            }catch (Exception e) {
            	adminId = admin.getId();
            }*/
            code = StringUtil.isNull(code) ? null : code;
            Map<String, Object> result = rechargeService.queryCount(keyword, status, payChannel, startTime, endTime,client,adminId, code,departmentId);
            List<Map<String, Object>> list = rechargeService.query(keyword, status, payChannel, startTime, endTime, (page - 1) * limit, limit, client,adminId, code,departmentId);

            Integer sum = rechargeService.querySum(keyword, status, payChannel, startTime, endTime, client,adminId, code,departmentId);
            Long count = 0L;
            if (result != null) {
                count = (Long) result.get("count");
//                model.addAttribute("totalAmount", result.get("amount") != null ? new DecimalFormat("#.00").format(Double.valueOf(result.get("amount").toString())) : 0);
            }
            // 充值成功总额
            double totalAmount = rechargeService.querySumAmount(keyword, payChannel, startTime, endTime,client,adminId, code,departmentId);

            int pages = calcPage(count.intValue(), limit);
            model.addAttribute("totalAmount", totalAmount);
            model.addAttribute("startTime", startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            model.addAttribute("endTime", endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("payChannel", payChannel);
            model.addAttribute("count", sum);
            model.addAttribute("status", status);
            model.addAttribute("client", client);
            model.addAttribute("keyword", keyword);
            model.addAttribute("code", code);
            model.addAttribute("departmentId", departmentId);
			model.addAttribute("departments", departmentService.getShowDepartments(superDepartmentId));
			
            model.addAttribute("payChannels", OutPayEnum.values());
            model.addAttribute("clients", ClientEnum.values());
            model.addAttribute("orderTypes", OrderTypeEnum.values());
        } catch (Exception e) {
            logger.error(e);
        }
        return "/recharge/list";
    }

    /**
     * 线下补单
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/fixByUnderLine", method = RequestMethod.POST)
    @ResponseBody
    public String fixUnderLine(Integer id, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:fixed");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error:auth";
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Recharge recharge = rechargeService.getMapper().selectByPrimaryKey(id);
            // 订单类型非充值单，支付渠道非线下充值 不允许操作
            if (!OrderTypeEnum.RECHARGE.getFeatureName().equals(recharge.getOrderType()) // 非充值单
                    || !OutPayEnum.OFFLINE.getFeatureName().equals(recharge.getPayChannel()) // 非线下支付
                    || recharge.getStatus() != 1) { // 非处理中
                return "error:notAllowOption";
            }
            rechargeService.updateFixed(recharge, admin);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error";
        }
        return "success";
    }

    /**
     * 线上补单
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/fixByOnLine", method = RequestMethod.POST)
    @ResponseBody
    public String fixOnLine(Integer id, Model model, HttpSession session) {
        try {
            // TODO 没有开放此功能，后续还需要完善
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:fixed");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            rechargeService.updateFixedByOnLine(id, admin);
        } catch (Exception e) {
            logger.error(e);
            return "error";
        }
        return "success";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Integer id, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:detail");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            Map<String, Object> recharge = rechargeService.detail(id);
            model.addAttribute("recharge", recharge);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/recharge/detail";
    }
    
    /**
     * 线下充值申请页面
     * @author sxy
     * @param model
     * @return
     */
    @RequestMapping(value = "rechargeOfflineApply/add", method = RequestMethod.GET)
    public String addApply(Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("recharge:rechargeOfflineApply:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        return "recharge/rechargeOfflineApply/add";
    }
    
    /**
     * 线下充值申请添加
     * @author sxy
     * @param model
     * @param session
     * @param rechargeOfflineApply
     * @param file
     * @return
     */
    @RequestMapping(value = "rechargeOfflineApply/save", method = RequestMethod.POST)
    public String saveApply(Model model, HttpSession session, RechargeOfflineApply rechargeOfflineApply,
        @RequestParam(value="file",required=false) MultipartFile file) {
        try {
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            rechargeOfflineApply.setApplyerId(admin.getId());
            if (file!=null && !file.isEmpty()) {
                Map<String, Object> result = uploadService.save(file, 21, admin.getId());
                rechargeOfflineApply.setVoucherPicId(Integer.valueOf(result.get("id").toString()));
            }
            
            rechargeOfflineApplyService.save(rechargeOfflineApply);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        
        return "redirect:/recharge/rechargeOfflineApply/list";
   }
    
    @RequestMapping(value = "/rechargeOfflineApply/list", method = RequestMethod.GET)
    public String rechargeOfflineApplyList(Model model, HttpSession session,String keyword, Integer state, Date startTime, Date endTime, Integer page) {
        try {
        	if (page == null) {
				page = 1;
			}
        	int limit = 20;
        	//仅可见自己创建的申请
        	Boolean onlySelfApply=true;
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:rechargeOfflineApply:list");
            }catch (Exception e) {
            	model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            //rechargeOfflineApply:apply	申请权	可以看自己的申请
            //rechargeOfflineApply:audit	审核权	可以看全部申请
            try {
                subject.checkPermission("recharge:rechargeOfflineApply:audit");
                onlySelfApply=false;
            }catch (Exception e2) {
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId=null;
            try {
    			subject.checkPermission("user:seealluser");
    			onlySelfApply=false;
    		} catch (Exception e) {
    			adminId=admin.getId();
    		}
            
            RechargeOfflineApplyExample example=new RechargeOfflineApplyExample();
            example.setLimitStart( (page - 1) * limit);
            example.setLimitEnd(limit);
            example.setOrderByClause(" id desc ");
            RechargeOfflineApplyExample.Criteria criteria=example.createCriteria();
            if(state!=null) {
            	criteria.andStateEqualTo(state);
            }
            if(startTime!=null) {
            	criteria.andCreateTimeGreaterThanOrEqualTo(startTime);
            }
            if(endTime!=null) {
            	criteria.andCreateTimeLessThan(DateUtil.getAbsoluteDate(endTime, Calendar.DATE, 1));
            }
            String keywordPitcher=null;
            if(!StringUtils.isEmpty(keyword)) {
            	keywordPitcher="%"+keyword+"%";
            }
            List<Map<String,Object>> list=rechargeOfflineApplyMapper.selectByExampleAndCondition(example,adminId,onlySelfApply,keywordPitcher);
            Long count = rechargeOfflineApplyMapper.countByExampleAndCondition(example,adminId,onlySelfApply,keywordPitcher);
            int pages = calcPage(count.intValue(), limit);
            
            model.addAttribute("keyword", keyword);
            model.addAttribute("state", state);
            model.addAttribute("startTime", startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            model.addAttribute("endTime", endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/recharge/rechargeOfflineApply/list";
    }
    @RequestMapping(value = "/rechargeOfflineApply/audit", method = RequestMethod.GET)
    public String offineApplyAudit(Integer id, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:rechargeOfflineApply:audit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            Map<String, Object> data = rechargeOfflineApplyService.getById(id);
            model.addAttribute("data", data);
        } catch (Exception e) {
            logger.error(e);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "/recharge/rechargeOfflineApply/audit";
    }

    @RequestMapping(value = "/rechargeOfflineApply/audit", method = RequestMethod.POST)
    public String offineApplyAudit(Integer id, Integer state, String auditRemark, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:rechargeOfflineApply:audit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            final String orderNo = rechargeOrderIdGenerator.next();
            rechargeOfflineApplyService.audit(id, state, auditRemark, orderNo, admin);
        } catch (Exception e) {
            logger.error(e);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "redirect:/recharge/rechargeOfflineApply/list";
    }


    @RequestMapping(value = "/rechargeOfflineApply/detail", method = RequestMethod.GET)
    public String offineApplyDetail(Integer id, Integer type, Model model, HttpSession session) {
        try {
            // type: 0.详情，1.编辑，2.审核
//            if (type == null || (type != 0 && type != 1 && type != 2)) {
//                model.addAttribute("error", "类型错误");
//                return "error";
//            }
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:rechargeOfflineApply:detail");
//                switch (type) {
//                    case 0:
//                        subject.checkPermission("recharge:rechargeOfflineApply:detail");
//                        break;
//                    case 1:
//                        subject.checkPermission("recharge:rechargeOfflineApply:edit");
//                        break;
//                    case 2:
//                        subject.checkPermission("recharge:rechargeOfflineApply:audit");
//                        break;
//                    default:
//                        model.addAttribute("error", "您没有权限操作");
//                        return "error";
//                }
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            Map<String, Object> data = rechargeOfflineApplyService.getById(id);
            model.addAttribute("data", data);
            model.addAttribute("type", type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "/recharge/rechargeOfflineApply/detail";
    }

    @RequestMapping(value = "/rechargeOfflineApply/edit", method = RequestMethod.POST)
    public String offineApplyEdit(Integer id, String bankcardNum, String serialNumber, 
                                  @RequestParam(value="file",required=false) MultipartFile file, 
                                  Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:rechargeOfflineApply:edit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer uploadId = null;
            if (file!=null && !file.isEmpty()) {
                Map<String, Object> result = uploadService.save(file, 21, admin.getId());
                uploadId = Integer.valueOf(result.get("id").toString());
            }
            rechargeOfflineApplyService.updateApply(id, bankcardNum, serialNumber, uploadId, admin);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "redirect:/recharge/rechargeOfflineApply/list";
    }
    
    @RequestMapping(value = "/rechargeOfflineApply/editLoading", method = RequestMethod.GET)
    public String offineApplyEdit(Integer id, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("recharge:rechargeOfflineApply:edit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            Map<String, Object> data = rechargeOfflineApplyService.getById(id);
            model.addAttribute("data", data);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "/recharge/rechargeOfflineApply/edit";
    }


    @RequestMapping(value = "/rechargeOfflineApply/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delAssessAjax(HttpServletRequest req, HttpServletResponse response,
                                @RequestParam Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "-1");
        try {
            rechargeOfflineApplyService.deleteApply(id);
            map = new HashMap<String, Object>();
            map.put("code", 1);
            map.put("msg", "删除成功");
            return map;
        } catch (Exception e) {
            logger.error("删除异常==========>" + e.getMessage(), e);
            e.printStackTrace();
            map.put("msg", "删除失败");
            return map;
        }
    }

}
