package com.goochou.p2b.admin.web.controller;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.SearchRuleEnum;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.dto.ExportApplyDTO;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.ExportApplyService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.BeanToMapUtil;
import com.goochou.p2b.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年11月13日 09:55:00
 */
@Controller
@RequestMapping("/exportApply")
public class ExportApplyController extends BaseController {

    private static final Logger logger = Logger.getLogger(ExportApplyController.class);

    @Resource
    ExportApplyService exportApplyService;

    @Resource
    DepartmentService departmentService;

    @Resource
    UserService userService;
    
    @RequestMapping(value = "/exportApplyList", method = RequestMethod.GET)
    public String exportApplyList(Model model, HttpSession session, String trueName, Integer auditStatus,
        Date startTime, Date endTime, Integer page) {
        
        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = null;
            
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("exportApply:exportApplyAudit");
            }catch (Exception e) {
                adminId = admin.getId();
            }
            
            List<Map<String,Object>> listExportApply = exportApplyService.listExportApply(trueName, auditStatus, startTime, endTime, (page - 1) * limit, limit, adminId);
            int count = exportApplyService.countExportApply(trueName, auditStatus, startTime, endTime, adminId);
            
            int pages = calcPage(count, limit);

            model.addAttribute("trueName", trueName);
            model.addAttribute("auditStatus", auditStatus);
            model.addAttribute("startTime",
                    startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            model.addAttribute("endTime", endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", listExportApply);
            model.addAttribute("page", page);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        
        return "exportApply/exportApplyList";
        
    }

    @RequestMapping(value = "/add/user-app", method = RequestMethod.GET)
    public String exportApplyUserApp(HttpSession session, Model model,
                                     Integer status, Integer level, Integer isBankCard, Integer isMigration, Integer departmentId,
                                     String keyword, String inviteKeyword, Date startTime, Date endTime,
                                     Date investStartTime, Date investndTime, Integer page){
        UserAdmin admin = (UserAdmin) session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("user:export:apply");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        List<ExportApplyCondition> conditionList = new ArrayList<>();
        String statusMsg = "";
        if (status != null) { // 0可用1不可用2已删除
            if (status == 0) statusMsg = "可用";
            else if (status == 1) statusMsg = "不可用";
            else statusMsg = "已删除";
        }
        this.formatParam(status, statusMsg, "status","用户状态", SearchRuleEnum.EQ, conditionList);

        String levelMsg = "";
        if (level != null) { // 用户等级0普通用户1会员用户 2vip用户
            if (level == 0) levelMsg = "普通用户";
            else if (level == 1) levelMsg = "会员用户";
            else levelMsg = "vip用户";
        }
        this.formatParam(level, levelMsg, "level","用户等级", SearchRuleEnum.EQ, conditionList);

        String isBankCardMsg = "";
        if (isBankCard != null) { // 是否绑卡0未绑卡1已绑卡
            if (isBankCard == 0) isBankCardMsg = "未绑卡";
            else isBankCardMsg = "已绑卡";
        }
        this.formatParam(isBankCard, isBankCardMsg, "isBankCard","是否绑卡", SearchRuleEnum.EQ, conditionList);

        String isMigrationMsg = "";
        if (isMigration != null) { // 是否迁移用户 0否1是
            if (isMigration == 0) isMigrationMsg = "否";
            else isMigrationMsg = "是";
        }
        this.formatParam(isMigration, isMigrationMsg, "isMigration","是否迁移用户", SearchRuleEnum.EQ, conditionList);
        
        String departmentMsg = "";
        if (departmentId != null) { // 公司
            DepartmentExample example=new DepartmentExample();
            example.createCriteria().andIsShowEqualTo(true).andIdEqualTo(departmentId);
            Department department = departmentService.getDepartmentById(departmentId);
            if (department == null) {
                model.addAttribute("error", "所属部门异常");
                return "error";
            }
            departmentMsg = department.getName();
        }
        this.formatParam(departmentId, departmentMsg, "departmentId","所属部门", SearchRuleEnum.EQ, conditionList);

        this.formatParam(keyword, null, "keyword","用户真实姓名、手机号", SearchRuleEnum.LIKE, conditionList);

        this.formatParam(inviteKeyword, null, "inviteKeyword","推荐人真实姓名、手机号", SearchRuleEnum.LIKE, conditionList);

        this.formatParam(startTime, null, "startTime","注册起始时间", SearchRuleEnum.GTE, conditionList);

        this.formatParam(endTime, null, "endTime","注册结束时间", SearchRuleEnum.LT, conditionList);

        // this.formatParam(page, null, "page","当前页号", null, conditionList);

        model.addAttribute("conditionList", JSON.toJSON(conditionList));
        Map<String, Object> params = exportApplyService.formatSearchParams(conditionList);
        Integer count = userService.queryCount1(params);
        model.addAttribute("count", count);
        return "exportApply/user-app";
    }

    /**
     * 将页面传入参数转为数据库的查询条件规则 <br/>
     * <>
     * @author shuys
     * @date 2019/11/18
     * @param value 页面传入值
     * @param valueName 页面传入值的描述，如 用户等级（0普通用户1会员用户 2vip用户），如果value为0，valueName为普通用户
     * @param propertyCode 页面传入查询条件对应字段， 如 用户等级，值为 level
     * @param propertyName 页面传入查询条件描述， 如 用户等级，值为 用户等级
     * @param searchRuleEnum 查询规则
     * @param list 导出列表查询条件规则集合
     * @return void
    */
    private void formatParam(Object value, String valueName, String propertyCode, String propertyName, SearchRuleEnum searchRuleEnum, List<ExportApplyCondition> list) {
        if (value == null || list == null) {
            return;
        }
        String formatValue = String.valueOf(value);
        if (value instanceof String && StringUtils.isBlank(formatValue)) {
            return;
        }
        if (value instanceof Date) {
            // formatValue = DateUtil.dateFullTimeFormat.format(value);
            formatValue = DateUtil.dateFormat.format(value);
        }
        if (valueName == null) {
            valueName = formatValue;
        }
        ExportApplyCondition condition = new ExportApplyCondition();
        condition.setPropertyCode(propertyCode);
        condition.setPropertyName(propertyName);
        condition.setValueCode(formatValue);
        condition.setValueName(valueName);
        if (searchRuleEnum != null) {
            condition.setRule(searchRuleEnum.name());
        }
        list.add(condition);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(HttpSession session, Model model, ExportApplyDTO params) {
        try {
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            if (admin == null) {
                return "error";
            }
            List<ExportApplyCondition> exportApplyConditions = BeanToMapUtil.mapsToObjects(params.getExportApplyConditions(), ExportApplyCondition.class);
            if (exportApplyConditions == null || exportApplyConditions.isEmpty()) {
                model.addAttribute("error", "数据筛选条件不能为空。");
                return "error";
            }
            List<ExportApplyColumns> exportApplyColumns = BeanToMapUtil.mapsToObjects(params.getExportApplyColumns(), ExportApplyColumns.class);
            if (exportApplyColumns == null || exportApplyColumns.isEmpty()) {
                model.addAttribute("error", "数据列不能为空。");
                return "error";
            }
            // 默认加密,当设置导出数据列为不加密时
            if (params.getEncryptColumn() != null) {
//            if (params.getEncrypt() != null && !params.getEncrypt() && params.getEncryptColumn() != null) {
                for (ExportApplyColumns col : exportApplyColumns) {
                    if (params.getEncryptColumn().contains(col.getColCode())) {
                        if (params.getEncrypt() != null && params.getEncrypt() == 0) {
                            col.setIsEncrypt(false);
                        } else {
                            col.setIsEncrypt(true);
                        }
                    }
                }
            }
            exportApplyService.addExportApply(admin.getId(), params.getApplyReason(), 0, exportApplyConditions, exportApplyColumns);
            return "success";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return "error";
        }
    }
    
    /**
     * 审核导出申请
     * @author sxy
     * @param model
     * @param session
     * @param id
     * @param auditRemark
     * @param auditStatus
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/exportApplyAudit", method = RequestMethod.POST)
    public String exportApplyAudit(Model model, HttpSession session, Integer id, String auditRemark, Integer auditStatus,
            RedirectAttributes redirectAttributes) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("exportApply:exportApplyAudit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            UserAdmin userAdmin = (UserAdmin) session.getAttribute("user");
            exportApplyService.auditExportApply(id, auditRemark, auditStatus, userAdmin.getId());
            redirectAttributes.addFlashAttribute("flag", 0);
            
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flag", -1);
            return "redirect:/exportApply/exportApplyList";
        }
        return "redirect:/exportApply/exportApplyList";
    }
    
    /**
     * 导出申请详情
     * @author sxy
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/applyDetail", method = RequestMethod.GET)
    public String exportApplyDetail(Model model, Integer id) {
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.checkPermission("shop:orderDetail");
//        }catch (Exception e) {
//            model.addAttribute("error", "您没有权限查看");
//            return "error";
//        }
        ExportApply apply = exportApplyService.getMapper().selectByPrimaryKey(id);
        List<ExportApplyColumns> listColumns = exportApplyService.listApplyColumns(id);
        List<ExportApplyCondition> listCondition = exportApplyService.listApplyCondition(id);
        
        
        model.addAttribute("apply", apply);
        model.addAttribute("listColumns", listColumns);
        model.addAttribute("listCondition", listCondition);

        return "exportApply/applyDetail";
    }
    
}
