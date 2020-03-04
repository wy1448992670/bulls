package com.goochou.p2b.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.model.Share;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.ShareService;
import com.goochou.p2b.service.UploadService;


@Controller
@RequestMapping(value = "share")
@Api(value = "分享")
public class ShareController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(ShareController.class);
    @Resource
    private ShareService shareService;
    @Resource
    private UploadService uploadService;

    /**
     * @author ydp
     * @param request
     * @param page
     * @param status
     * @param client
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享链接")
    public AppResult cardmanage(HttpServletRequest request,
                                @ApiParam("shareId") @RequestParam Integer shareId,
                                @ApiParam("用户token") @RequestParam(required=false) String token,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("App版本号") @RequestParam String appVersion) {
        try {
            Share share = shareService.queryByKey(shareId);
            if(null != share.getUploadId()) {
                Upload upload = uploadService.get(share.getUploadId());
                share.setSharePic(upload.getCdnPath());

                if(token !=null) {
                    User user = userService.checkLogin(token);
                    if (null != user) {
                        if (null != share.getLink() && !"".equals(share.getLink())) {
                            if (share.getLink().indexOf("?") != -1) {
                                share.setLink(share.getLink() + "&inviteCode=" + user.getInviteCode());
                            } else {
                                share.setLink(share.getLink() + "?inviteCode=" + user.getInviteCode());
                            }
                        }
                    }
                    else{
                        return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
                    }
                }
            }
            return new AppResult(SUCCESS, share);
        } catch (Exception e) {
            logger.error(e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
}
