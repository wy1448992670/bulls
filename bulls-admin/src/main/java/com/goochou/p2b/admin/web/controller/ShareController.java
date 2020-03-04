package com.goochou.p2b.admin.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Share;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.BankCardService;
import com.goochou.p2b.service.BannerService;
import com.goochou.p2b.service.LoginRecordService;
import com.goochou.p2b.service.ShareService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserAddressService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.UserSignedService;

@Controller
@RequestMapping(value = "/share")
public class ShareController extends BaseController {

	private static final Logger logger = Logger.getLogger(ShareController.class);
    @Resource
    private UserService userService;
    @Resource
    private AssetsService assetsService;
    @Resource
    private BannerService bannerService;
    @Resource
    private UploadService uploadService;
    @Resource
    private BankCardService bankCardService;
    /*@Resource
    private MQSendAdminLog mqSendMessage;*/
    @Resource
    private LoginRecordService loginRecordService;
    @Resource
    private UserSignedService userSignedService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private ShareService shareService;

    /**
     * 分享管理列表
     * @author sxy
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(required = false) Integer page) {
        int limit = 20;
        if (page == null) {
            page = 1;
        }
//        List<Share> list = shareService.queryAll((page - 1) * limit, limit);
        List<Map<String, Object>> list = shareService.queryShareList((page - 1) * limit, limit);
        Integer count = shareService.queryCount();
        int pages = calcPage(count, limit);
        model.addAttribute("list", list);
        model.addAttribute("pages", pages);
        model.addAttribute("page", page);
        return "share/list";
    }

    /**
     *
     *
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, Integer id) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("share:detail");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        final String picUrl = ClientConstants.ALIBABA_PATH + "upload/";
        Share s = shareService.queryByKey(id);
        Upload upload = uploadService.get(s.getUploadId());
        String path = upload == null ? "" : upload.getPath();
        String imgUrl = "";
        if (org.apache.commons.lang.StringUtils.isNotBlank(path)) {
            imgUrl = picUrl + path;
        }
        model.addAttribute("share", s);
        model.addAttribute("path", path);
        model.addAttribute("imgUrl", imgUrl);
        return "share/detail";
    }


    /**
     * 跳转到添加share页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAddShare(Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("share:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        
        return "share/add";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addShare(Share share, @RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            if (!file.isEmpty()) {//用户有更新过图片
                Map<String, Object> result = uploadService.save(file, 16, null);
                share.setUploadId(Integer.valueOf(result.get("id").toString()));
            }
            shareService.saveOrUpdate(share);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/share/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteShare(Integer id, Integer status, HttpSession session) {
        try {
            shareService.delShare(id, status);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/share/list";
    }

}
