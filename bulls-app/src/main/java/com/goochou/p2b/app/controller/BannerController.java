package com.goochou.p2b.app.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Banner;
import com.goochou.p2b.service.BannerService;


@Controller
@RequestMapping(value = "/banner")
@Api(value = "banner图-banner")
public class BannerController extends BaseController {

    @Resource
    private BannerService bannerService;

    /**
     * 获取最新的banner list
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "banner图列表")
    public AppResult banners(HttpServletRequest request,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = false) String client,
            @ApiParam("归属页:0首页,1首页-活动区,2商城-首页") @RequestParam(required = false) Integer source) {
        List<Banner> list = null;
        if(client != null && client.equals(ClientEnum.PC.getFeatureName())) {
            list = bannerService.listByStatus(0,1,1,null);
        } else if(source != null && source == 2){
            list = bannerService.listByStatus(0,0,2,null);
        } else {
            list = bannerService.listByStatus(0,0,0,null);
        }
        for (Banner banner : list) {
            banner.setPictureUrl(ClientConstants.ALIBABA_PATH  + "upload/" + banner.getPictureUrl());
        }
        return new AppResult(SUCCESS, list);
    }

}
