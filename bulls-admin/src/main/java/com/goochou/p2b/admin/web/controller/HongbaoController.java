package com.goochou.p2b.admin.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.HongbaoTemplate;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.HongbaoTemplateModel;
import com.goochou.p2b.service.AdminRoleService;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.HongbaoTemplateService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.SmsSendService;
import com.goochou.p2b.service.UserService;

@Controller
@RequestMapping("/hongbao")
public class HongbaoController extends BaseController {

	private static final Logger logger = Logger.getLogger(HongbaoController.class);

    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private UserService userService;
    @Resource
    private HongbaoTemplateService hongbaoTemplateService;
    @Resource
    private MessageService messageService;
    @Resource
    private AdminRoleService adminRoleService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private SmsSendService smsSendService;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, String keyword, Integer application, Integer type, Date startSendTime,
    		Date endSendTime, Date startUseTime, Date endUseTime, Integer page, HttpSession session, Integer departmentId) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("hongbao:all");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            if (application == null) {
                application = 2;
            }
            if (type == null) {
                type = 0;
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId = null;
//            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:seealluser");
            }catch (Exception e) {
            	adminId = admin.getId();
            }
            List<Map<String, Object>> list = hongbaoService.query(keyword, application, type, startSendTime, endSendTime, startUseTime, endUseTime, (page - 1) * limit, limit, adminId,departmentId);
            Integer count = hongbaoService.queryCount(keyword, application, type, startSendTime, endSendTime, startUseTime, endUseTime, adminId,departmentId);
            int pages = calcPage(count.intValue(), limit);
            List<Map<String, Object>> listSum = hongbaoService.unUserSum(keyword, application, type, startSendTime, endSendTime, startUseTime, endUseTime, adminId,departmentId);
            if (listSum != null && listSum.size() > 0) {
                if (listSum.get(0) != null) {
                    model.addAttribute("useSum", listSum.get(0).get("s"));
                    model.addAttribute("unuseSum", listSum.get(0).get("s1"));
                }
            } else {
//                model.addAttribute("sum",new HashMap<String,Object>());
            }
            model.addAttribute("startSendTime", startSendTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startSendTime) : null);
            model.addAttribute("endSendTime", endSendTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endSendTime) : null);
            model.addAttribute("startUseTime", startUseTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startUseTime) : null);
            model.addAttribute("endUseTime", endUseTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endUseTime) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("application", application);
            model.addAttribute("type", type);
            model.addAttribute("keyword", keyword);
            model.addAttribute("departmentId", departmentId);
			model.addAttribute("departments", departmentService.getShowDepartments(admin.getDepartmentId()));

        } catch (Exception e) {
            logger.error(e);
        }
        return "hongbao/list";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String sendHongbao(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartment());
        
        return "hongbao/sendHongbao";
    }

    /**
     * 派发红包到指定用户
     * @author sxy
     * @param session
     * @param amount
     * @param type
     * @param userId
     * @param descript
     * @param content
     * @param file
     * @param redirectAttributes
     * @param days
     * @param template
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String sendHongbao(HttpSession session, Double amount, Integer type, String userId, String descript, String content, String cashTitle, String message,
                              @RequestParam(required = false) MultipartFile file, RedirectAttributes redirectAttributes,
                              @RequestParam(required = false) Integer days, @RequestParam(required = false) String template, Integer departmentId) {
        try {
            UserAdmin userAdmin = (UserAdmin) session.getAttribute("user");
            StringBuffer logs = new StringBuffer();
            logger.info("======================管理员开始派发红包===" + userAdmin.getTrueName() + "===管理员ID====" + userAdmin.getId() + "===============");
            if (type == 1) { //现金红包
                List<Hongbao> list2 = new LinkedList<Hongbao>();
                List<String> list = new LinkedList<String>();
                if (!file.isEmpty()) {
                    InputStreamReader isr = new InputStreamReader(file.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    String str = "";
                    int i = 0;
                    Hongbao h = null;
                    while (i < 10) {
                        str = reader.readLine();
                        if (StringUtils.isEmpty(str)) {
                            i++;
                            continue;
                        }
                        // BufferedReader.readLine(),读取第一行会出现bug,首行第一个字符会是一个空字符
                        //文件第一行的首个字符,而是一个空字符(不是空字符串),但读取第二行就不会出现这问题。
                        //把第一行的第一个字符去掉。
                        char c =str.trim().charAt(0);
                        if(c == 65279){
                            if(str.length()>1){
                                str=str.substring(1);
                            }
                        }
                        logs.append(str + ",");
                        String[] s = str.split(",");
                        h = new Hongbao();
                        h.setUserId(Integer.parseInt(s[0]));
                        h.setDescript(s[0]);
                        Double hbAmount = Double.valueOf(s[1]);
                        h.setAmount(hbAmount);
                        //if (hbAmount <= 10000) {
                            list2.add(h);
                            list.add(s[0]);
                        //}
                    }
                    hongbaoService.saveToUserCashHongbao(list2, cashTitle + "=派发管理员ID-->" + userAdmin.getId(), days, userAdmin.getId());
                }else {
                	redirectAttributes.addFlashAttribute("flag", -2);
                    return "redirect:/hongbao/send";
                }

                if (StringUtils.isNotBlank(content)) {
                    messageService.saveInternalList(cashTitle, content, list);
                }
                
                if(StringUtils.isNotBlank(message)) {
                    for(String idStr : list) {
                        User user = userService.get(Integer.valueOf(idStr));
                        smsSendService.addSmsSend(user.getPhone(), message, null, new Date());
                    }
                }
            } else if (type == 2 || type == 3  || type == 4) {//2投资红包 3优惠券 4拼牛红包
                List<String> list2 = new LinkedList<String>();
                List<HongbaoTemplateModel> list = JSONArray.parseArray(template, HongbaoTemplateModel.class);
                if(list.size() != 0) {
                    if (!file.isEmpty() && departmentId == null) {
                        InputStreamReader isr = new InputStreamReader(file.getInputStream());
                        BufferedReader reader = new BufferedReader(isr);
                        String str = "";
                        int i = 0;
                        while (i < 10) {
                            str = reader.readLine();
                            if (StringUtils.isEmpty(str)) {
                                i++;
                                continue;
                            }
                            // BufferedReader.readLine(),读取第一行会出现bug,首行第一个字符会是一个空字符
                            //文件第一行的首个字符,而是一个空字符(不是空字符串),但读取第二行就不会出现这问题。
                            //把第一行的第一个字符去掉。
                            char c =str.trim().charAt(0);
                            if(c == 65279){
                                if(str.length()>1){
                                    str=str.substring(1);
                                }
                            }
                            logs.append(str + ",");
                            list2.add(str);
                        }
                        hongbaoService.saveToUserInvestmentHongbao(list, list2, type, descript, days, userAdmin.getId());
                    } else if(departmentId != null && file.isEmpty()) {
                        //选择部门发送
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("departmentId", departmentId);
                        List<Map<String, Object>> userList = userService.query1(map);
                        for(Map<String, Object> user : userList) {
                            list2.add(String.valueOf(user.get("id")));
                        }
                        hongbaoService.saveToUserInvestmentHongbao(list, list2, type, descript, days, userAdmin.getId());
                    } else {
                        redirectAttributes.addFlashAttribute("flag", -3);
                        return "redirect:/hongbao/send";
                    }
                } else {
                    redirectAttributes.addFlashAttribute("flag", -1);
                    return "redirect:/hongbao/send";
                }

                if (StringUtils.isNotBlank(content)) {
                    messageService.saveInternalList(descript, content, list2);
                }
                
                if(StringUtils.isNotBlank(message)) {
                    for(String idStr : list2) {
                        User user = userService.get(Integer.valueOf(idStr));
                        smsSendService.addSmsSend(user.getPhone(), message, null, new Date());
                    }
                }
            }
            logger.info("==管理员派发红包名单===" + logs.toString());
            logger.info("===========管理员派发红包结束===" + userAdmin.getTrueName() + "===管理员ID===" + userAdmin.getId());
        } catch (Exception e) {
            logger.error(e);
            redirectAttributes.addFlashAttribute("flag", 0);
            return "redirect:/hongbao/send";
        }
        redirectAttributes.addFlashAttribute("flag", 1);
        return "redirect:/hongbao/send";
    }


    /**
     * @Description(描述):
     * @author 王信
     * @date 2016/7/12
     * @params keyword 用户关键字，type     红包类型，isUse 0未使用 1使用 ，startTime 开始时间，endTime 结束时间，redeemId 兑换码ID
     **/
    @RequestMapping(value = "/hongbaoUseDetailList", method = RequestMethod.GET)
    public String hongbaoUseDetailList(Model model, String keyword, Integer type, Integer isUse, Integer page,
                                       Date startTime, Date endTime, Integer redeemId) {
        int limit = ConstantsAdmin.PAGE_LIMIT2;
        if (page == null) {
            page = 1;
        }
        if (StringUtils.isEmpty(keyword)) {
            keyword = null;
        }
        if (type == null) {
            type = 2;
        }
        List<Map<String, Object>> list = hongbaoService.selectUserUseList(keyword, type, isUse, startTime, endTime, redeemId, (page - 1) * limit, limit);
        Integer count = hongbaoService.selectUserUseCount(keyword, type, isUse, startTime, endTime, redeemId);
        int pages = calcPage(count, limit);
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        model.addAttribute("isUse", isUse);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("redeemId", redeemId);
        model.addAttribute("pages", pages);
        model.addAttribute("page", page);
        model.addAttribute("list", list);
        return "hongbao/hongbaoUseDetailList";
    }

    /**
     * @param
     * @author 刘源
     * @date 2016/6/23
     */
    @RequestMapping(value = "/hongbaoTemplateDetail", method = RequestMethod.GET)
    public String hongbaoTemplateDetail(Model model, Integer id, String keyword, Integer page) {
        if (id != null) {
            Map<String, Object> map = hongbaoTemplateService.queryById(id);
            JSONObject json = JSONObject.parseObject(map.get("monthType").toString());
            map.put("box", json);
            model.addAttribute("map", map);
        }
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "hongbao/hongbaoTemplateDetail";
    }

    /**
     * 编辑红包模板
     *
     * @param
     * @author 刘源
     * @date 2016/6/23
     */
    @RequestMapping(value = "/editHongbaoTemplate", method = RequestMethod.POST)
    public String editHongbaoTemplate(Model model, HongbaoTemplate template, String keyword, Integer page) {
        hongbaoTemplateService.save(template);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "redirect:/hongbao/hongbaoTemplateList";
    }
}
