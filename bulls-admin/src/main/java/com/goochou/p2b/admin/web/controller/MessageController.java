package com.goochou.p2b.admin.web.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.model.MessageTemplate;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.AjaxUtil;

/**
 * 后台管理员用户发送短信功能模块
 *
 * @author 王信
 * @Create Date: 2015年12月25日上午10:56:51
 */
@Controller
@RequestMapping(value = "/sms")
public class MessageController extends BaseController {
	private static final Logger logger = Logger.getLogger(MessageController.class);

    @Resource
    private UserService userService;

    /*@Resource(name = "taskMessageTemplate")
    private JmsTemplate jmsTemplate;*/

    @Resource
    private MessageService messageService;

    /**
     * 进入发送短信页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日上午10:56:27
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public String sendMessage(HttpServletRequest request, HttpServletResponse response, Model model) {
        //从数据读取模板
        return "message/sendMessage";
    }


    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    public void listJson(HttpServletRequest request, HttpServletResponse response, Model model) {
        //从数据读取模板
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("sms:detailsSMS:list");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
//                return "error";
            }
            List<MessageTemplate> list = messageService.selectList(1);
            AjaxUtil.str2front(response, JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 短信管理页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日上午10:56:27
     */
    @RequestMapping(value = "/detailsSMS", method = RequestMethod.GET)
    public String detailsMessage(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("sms:detailsSMS:list");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            //从数据读取模板
            List<MessageTemplate> list = messageService.selectList(null);
            model.addAttribute("list", list);

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return "message/detailsSMS";
    }

    /**
     * 跳转新增页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日上午10:56:27
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
        //从数据读取模板
        return "message/addTemplet";
    }

    /**
     * 新增短信模板
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日上午10:56:27
     */
    @RequestMapping(value = "/addTemplet", method = RequestMethod.POST)
    @ResponseBody
    public String addTemplet(HttpServletRequest request, HttpServletResponse response, Model model, String content, Integer type, Integer status, @RequestParam(required = false) Integer id) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("sms:detailsSMS:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        //从数据读取模板
        if (id == null || id == 0) {
            Integer count = messageService.addTemplet(content, type, status);
            if (count > 0) {
                return "新增模板成功！";
            } else {
                return "新增失败！";
            }
        } else {
            Integer update = messageService.updatedTemplet(content, type, status, id);
            if (update > 0) {
                return "编辑模板成功！";
            } else {
                return "编辑失败！";
            }
        }
    }

    /**
     * 删除模板
     *
     * @param request
     * @param response
     * @param id
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日下午2:43:01
     */
    @RequestMapping(value = "/deleteTemplet", method = RequestMethod.GET)
    public String deleteTemplet(HttpServletRequest request, HttpServletResponse response, Integer id, Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("sms:detailsSMS:delete");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        messageService.deleteTemplet(id);
        return "redirect:/sms/detailsSMS.action";
    }

    /**
     * 更新模板第一步
     *
     * @param request
     * @param response
     * @param id
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日下午2:43:01
     */
    @RequestMapping(value = "/editTemplet", method = RequestMethod.GET)
    public String editTemplet(HttpServletRequest request, HttpServletResponse response, Integer id, Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("sms:detailsSMS:edit");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        MessageTemplate m = messageService.selectByIdMessageTemplate(id);
        model.addAttribute("content", m.getContent());
        model.addAttribute("type", m.getType());
        model.addAttribute("status", m.getStatus());
        model.addAttribute("id", m.getId());
        return "message/editTemplet";
    }


    @RequestMapping(value = "/send/internal/msg", method = RequestMethod.GET)
    public String sendInternalMsg() {
        return "message/sendInternalMessage";
    }


    @RequestMapping(value = "/send/internal/msg", method = RequestMethod.POST)
    public String sendInternalMsg(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(required = false) MultipartFile file,
                                  RedirectAttributes redirectAttributes,
                                  String title, String content, String userId, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("send:internalMsg");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            if (!file.isEmpty()) {
                List<String> list = new LinkedList<>();
                InputStreamReader isr = new InputStreamReader(file.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                String str = new String();
                int i = 0;
                while (i < 10) {
                    str = reader.readLine();
                    if (StringUtils.isEmpty(str)) {
                        i++;
                        continue;
                    }
                    //BufferedReader.readLine(),读取第一行会出现bug,首行第一个字符会是一个空字符
                    //文件第一行的首个字符,而是一个空字符(不是空字符串),但读取第二行就不会出现这问题。
                    //把第一行的第一个字符去掉。
                    char s =str.trim().charAt(0);
                    if(s == 65279){
                    	if(str.length() > 1){
                    		str=str.substring(1);
                    	}
                    }
                    list.add(str);
                }
                reader.close();
                messageService.saveInternalList(title, content, list);
            } else if (org.apache.commons.lang.StringUtils.isBlank(userId)) {
                List<User> user = userService.list();
                for (User user2 : user) {
                    messageService.save(title, content, user2.getId());
                }
            } else {
                String[] users = userId.split(",");
                for (int i = 0; i < users.length; i++) {
                    messageService.save(title, content, Integer.parseInt(users[i]));
                }
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flag", 0);
            return "redirect:/sms/send/internal/msg";
        }
        redirectAttributes.addFlashAttribute("flag", 1);
        return "redirect:/sms/send/internal/msg";
    }

    @RequestMapping(value = "/internal/msg/list", method = RequestMethod.GET)
    public String internalMsgList(Model model, String keyword, Integer page) {
        if (page == null) {
            page = 1;
        }
        Integer limit = 20;
        List<Map<String, Object>> list = messageService.list(keyword, (page - 1) * limit, limit);
        Integer count = messageService.listCount(keyword);
        model.addAttribute("list", list);
        model.addAttribute("pages", calcPage(count, limit));
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "message/internalMsgList";
    }


    public static void main(String[] args) throws IOException {
    	InputStreamReader ir =new InputStreamReader(new FileInputStream("C:\\Users\\MSI\\Desktop\\ASNI.txt"));
    	BufferedReader reader = new BufferedReader(ir);
        String str = new String();
        int i = 0;
        List<Integer> list = new ArrayList<>();
        while (i < 10) {
            str = reader.readLine();

            if (StringUtils.isEmpty(str)) {
                i++;
                continue;
            }
            // BufferedReader.readLine()读取第一行会出现bug,首行第一个字符会是一个空字符
            //文件第一行的首个字符,而是一个空字符(不是空字符串),但读取第二行就不会出现这问题。
            //把第一行的第一个字符去掉。
            char s =str.trim().charAt(0);
            if(s == 65279){
            	if(str.length()>1){
            		str=str.substring(1);
            	}
            }
        }
        reader.close();

	}
}
