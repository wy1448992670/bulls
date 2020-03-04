package com.goochou.p2b.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goochou.p2b.model.AdminLog;
import com.goochou.p2b.service.AdminLogService;
import com.goochou.p2b.utils.DownloadUtils;
import com.goochou.p2b.utils.HSSFExcelUtils;

@Controller
@RequestMapping(value = "/log")
public class LogController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(LogController.class);

    @Resource
    private AdminLogService adminLogService;


    /**
     * admin
     * @author sxy
     * @param model
     * @param keyWord
     * @param startTime
     * @param endTime
     * @param lvl
     * @param page
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String bannerList(Model model, String keyWord, Date startTime, Date endTime, Integer lvl, Integer page) {
        if (page == null) {
            page = 1;
        }
        List<AdminLog> list = adminLogService.query(keyWord, startTime, endTime, lvl, (page - 1) * 20, 20);
        Long count = adminLogService.queryCount(keyWord, startTime, endTime, lvl);
        model.addAttribute("list", list);
        model.addAttribute("pages", calcPage(count.intValue(), 20));
        model.addAttribute("page", page);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("startTime", startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
        model.addAttribute("endTime", endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
        model.addAttribute("lvl", lvl);
        return "log/admin-log";
    }
    
    
    /**
     * 导出用户投资记录
     */
    @RequestMapping(value = "export/admin/log", method = RequestMethod.GET)
    public void exportAdminLog(HttpServletResponse response, HttpSession session, Model model, 
    		String keyWord, Date startTime, Date endTime, Integer lvl) {
        try {
            
            List<AdminLog> list = adminLogService.query(keyWord, startTime, endTime, lvl, null, null);
            
            LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
            propertyHeaderMap.put("id", "编号");
            propertyHeaderMap.put("adminId", "操作员ID");
            propertyHeaderMap.put("adminIp", "操作员IP");
            propertyHeaderMap.put("operateTime", "操作时间");
            propertyHeaderMap.put("remark", "操作内容");
            propertyHeaderMap.put("lvl", "充值金额");
            String title = "操作员操作记录表";
            HSSFExcelUtils.ExpExs(title, propertyHeaderMap, list, response);
        } catch (Exception e) {
        	logger.error(e);
        }finally{
        	DownloadUtils.closeResponseOutput(response);
        }
    }

}
