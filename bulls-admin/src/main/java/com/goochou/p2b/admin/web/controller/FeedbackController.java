package com.goochou.p2b.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.model.FeePicture;
import com.goochou.p2b.model.Feedback;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.FeePicetureService;
import com.goochou.p2b.service.FeedbackService;
import com.goochou.p2b.service.MessageReceiverService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.AjaxUtil;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

	private static final Logger logger = Logger.getLogger(FeedbackController.class);

    @Resource
    private FeedbackService feedbackService;
    @Resource
    private UserService userService;
    @Resource
    private MessageService messageService;
    @Resource
    private MessageReceiverService messageReceiverService;
    @Resource
    private FeePicetureService feePicetureService;
    @Resource
    private UploadService uploadService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, String keyword, Date startTime, Date endTime, Integer page, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("feedback:list");
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
            List<Map<String, Object>> list = feedbackService.query(keyword, startTime, endTime, (page - 1) * limit, limit, adminId);
            Integer count = feedbackService.queryCount(keyword, startTime, endTime, adminId);
            int pages = calcPage(count.intValue(), limit);
            model.addAttribute("startTime", startTime != null ? new
                    SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            model.addAttribute("endTime", endTime != null ? new
                    SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error(e);
        }
        return "feedback/list";
    }

    @RequestMapping(value = "/reply", method = RequestMethod.GET)
    public String reply(Model model, Integer id) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("feedback:reply");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            // 判断反馈消息是否已回复，如已回复不显示 回复BUTTON
            FeePicture feePicture =    feePicetureService.selectByFeeId(id);
            if(feePicture!=null && feePicture.getUploadId()!=null){
                Upload upload = uploadService.get(feePicture.getUploadId());
                if(upload!=null && upload.getPath()!=null){
                    model.addAttribute("path", upload.getPath());
                }
            }
            Feedback feedback = feedbackService.queryById(id);
            if(feedback!=null){
                model.addAttribute("content", feedback.getContent());
            }
            System.out.println(id);
            model.addAttribute("id", id);
        } catch (Exception e) {
            logger.error(e);
        }
        return "feedback/reply";
    }

    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public String reply(HttpSession session, String replyContent, Integer id, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("feedback:reply");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            // 1.查询feedback对象 2.新增meesage对象 3.新增message接收对象 4.更新feedfack对象
            System.out.println("回复的内容是:" + replyContent);
            UserAdmin user = (UserAdmin) session.getAttribute("user");
            Feedback fee = feedbackService.queryById(id);
            Date nowDate = new Date();
            if (fee != null) {
                Integer messageId = messageService.save("反馈信息的回复", replyContent, fee.getUserId());
                // 消息接收对象默认状态为0 (1已读0未读)
                messageReceiverService.save(null, messageId, fee.getUserId(), 0, nowDate, null);
                fee.setReplyTime(nowDate);
                fee.setReplyUser(user.getId());
                fee.setReplyMessageId(messageId);
                feedbackService.update(fee);

            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/feedback/list";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, Integer id) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("feedback:detail");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            Map<String, Object> map = feedbackService.queryByIdToUserFeedbackInfo(id);

            FeePicture feePicture =    feePicetureService.selectByFeeId(id);
            if(feePicture!=null && feePicture.getUploadId()!=null){
                Upload upload = uploadService.get(feePicture.getUploadId());
                if(upload!=null && upload.getPath()!=null){
                    model.addAttribute("path", upload.getPath());
                }
            }
            model.addAttribute("info", map);
        } catch (Exception e) {
            logger.error(e);
        }
        return "feedback/detail";
    }

    /**
     * 撤销用户反馈信息的回复内容
     *
     * @param model
     * @param id
     *            刘源 2015-10-27
     */
    @RequestMapping(value = "/revoke", method = RequestMethod.POST)
    public void revoke(Model model, HttpServletResponse response, Integer id) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("feedback:revoke");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
//                return "error";
            }
            Feedback fee = feedbackService.queryById(id);
            Map<String, String> map = new HashMap<String, String>();
            messageReceiverService.deleteByMessageId(fee.getReplyMessageId());

            messageService.deleteByMessageId(fee.getReplyMessageId());

            fee.setReplyMessageId(null);
            fee.setReplyTime(null);
            fee.setReplyUser(null);
            feedbackService.update(fee);

            map.put("status", "0");
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
