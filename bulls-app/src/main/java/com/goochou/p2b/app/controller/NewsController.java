package com.goochou.p2b.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.goochou.p2b.model.AppNotice;
import com.goochou.p2b.model.News;
import com.goochou.p2b.service.AppNoticeService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.BeanToMapUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;
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
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.NewsWithBLOBs;
import com.goochou.p2b.service.NewsService;


@Controller
@RequestMapping(value = "/news")
@Api(value = "新闻")
public class NewsController extends BaseController {

	private static final Logger logger = Logger.getLogger(NewsController.class);

    @Resource
    private NewsService newsService;

    @Resource
    private UserService userService;

    @Resource
    private AppNoticeService appNoticeService;
    /**
     * 媒体报道
     * @author sxy
     * @param request
     * @param appVersion
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "媒体报道列表")
    public AppResult banners(HttpServletRequest request,
                             @ApiParam("App版本号") @RequestParam String appVersion,
                             @ApiParam("当前页") @RequestParam(required = false) Integer page) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (page == null) {
                page = 1;
            }
            Integer limit = 10;
            List<NewsWithBLOBs> list = newsService.selectAppNewsList((page - 1) * limit, limit, 1);
            for (NewsWithBLOBs news : list) {
                news.setPictureUrl(ClientConstants.ALIBABA_PATH + "upload/" + news.getUpload().getPath());
                //news.setLink(Constants.NEWS_URL + news.getId());
                news.setCreateDateStr(DateUtil.dateFormat.format(news.getCreateDate()));
            }
            Integer count = newsService.selectAppNewsCount();
            map.put("list", list);
            map.put("page", page);
            map.put("pages", calcPage(count, limit));
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "媒体报道详情")
    public AppResult newsDetail(HttpServletRequest request,
                @ApiParam("App版本号") @RequestParam String appVersion,
                @ApiParam("终端") @RequestParam(required = false) String client,
                @ApiParam("token") @RequestParam(required = false) String token,
                @ApiParam("新闻ID") @RequestParam Integer id,
                @ApiParam("渠道（0.新闻，1.公告）") @RequestParam Integer channelType
    ) {
        try {
//            User user = userService.updateCheckLogin(token, 1, getIpAddr(request));
            if (channelType != 0 && channelType != 1) {
                return new AppResult(FAILED, "渠道类型错误");
            }
            Map result = null;
            if (channelType == 0) {
                News news = newsService.getMapper().selectByPrimaryKey(id);
                if (news == null) {
                    return new AppResult(FAILED, PARAM_ERROR);
                }
                result = BeanToMapUtil.convertBean(news);
            }
            if (channelType == 1) {
                AppNotice appNotice = appNoticeService.getShowNoticeById(id);
                if (appNotice == null) {
                    return new AppResult(FAILED, PARAM_ERROR);
                }
                result = BeanToMapUtil.convertBean(appNotice);
            }
            Date createDate = (Date) result.get("createDate");
            result.put("createDateStr", createDate != null ? DateFormatTools.dateToStr1(createDate) : "");
            if (result == null) {
                return new AppResult(FAILED, PARAM_ERROR);
            }
            return new AppResult(SUCCESS, result);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


}
