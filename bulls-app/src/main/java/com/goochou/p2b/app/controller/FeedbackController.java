package com.goochou.p2b.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.model.Feedback;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.FeedbackService;
import com.goochou.p2b.service.UploadService;


@Controller
@RequestMapping(value = "/feedback")
@Api(value = "用户反馈")
public class FeedbackController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(FeedbackController.class);

    @Resource
    private FeedbackService feedbackService;
    @Resource
    private UploadService uploadService;

    /**
     * 上传用户反馈图片
     *
     * @return
     */
    @RequestMapping(value = "/add/pic", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传用户反馈图片")
    public AppResult addPic(@ApiParam("图片") @RequestParam(required = false) MultipartFile file,
                            @ApiParam("token") @RequestParam String token,
                            @ApiParam("App版本号") @RequestParam String appVersion,
                            HttpServletRequest request, HttpSession session) {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        if (file != null) {
            Map<String, Object> map = uploadService.save(file, 6, user.getId());
            if (map.get("status").equals(ConstantsAdmin.SUCCESS)) {
                List<Integer> list = (List<Integer>) session.getAttribute("feeList");
                if (null == list || list.isEmpty()) {
                    list = new ArrayList<Integer>();
                }
                list.add((Integer) map.get("id"));
                session.setAttribute("feeList", list);
                return new AppResult(SUCCESS, null, getUploadPath(request) + map.get("path"));
            } else {
                return new AppResult(FAILED, "对不起，图片上传失败");
            }
        }
        return null;
    }

    /**
     * 添加用户反馈
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加用户反馈")
    public AppResult add(@ApiParam("建议内容") @RequestParam String content,
                         @ApiParam("token") @RequestParam String token,
                         @ApiParam("App版本号") @RequestParam String appVersion,
                         HttpSession session) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            List<Integer> list = (List<Integer>) session.getAttribute("feeList");
            //LogUtil.infoLogs("图片IDs"+list.toString());  //注释   否则不传图片报错,空指针异常.
            if (StringUtils.isBlank(content)) {
                return new AppResult(FAILED, "建议内容不能为空");
            }
            Feedback fee = new Feedback();
            fee.setContent(content);
            fee.setCreateTime(new Date());
            fee.setUserId(user.getId());
            feedbackService.save(fee, list);
            return new AppResult(SUCCESS, "提交成功,感谢您的反馈");
        } catch (Exception e) {
        	logger.error(e);
        }
        return new AppResult(SUCCESS, "提交失败,请稍后重试");
    }
}
