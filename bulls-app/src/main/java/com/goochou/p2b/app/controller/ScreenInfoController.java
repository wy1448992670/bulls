package com.goochou.p2b.app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.ProjectDaysEnum;
import com.goochou.p2b.constant.RepayUnitEnum;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.vo.ProjectGpsListVo;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.ScreenInfoService;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.PayUtil;


/**
 * 大屏幕数据接口
 *
 * @ClassName ScreenInfoController
 * @author zj
 * @Date 2019年5月20日 下午4:27:11
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "screen")
@Api(value = "大屏幕信息")
public class ScreenInfoController extends BaseController {
	private static final Logger logger = Logger.getLogger(ScreenInfoController.class);
	@Autowired
	ScreenInfoService screenInfoService;
	@Autowired
	private ProjectService projectService;
	/**
	 * 数据统计（大屏幕显示使用）
	 * 
	 * @Title: screenInfo
	 * @param prairieNo 牧场编号  传 0  表示  所有牧场  1   2   3  分别对应几号牧场
	 * @author zj
	 * @date 2019-07-16 09:23
	 */
	@RequestMapping(value = "/screenInfo/{prairieNo}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "大屏幕信息统计数据")
	public AppResult screenInfo(@PathVariable("prairieNo") String prairieNo) {
		try {
			return new AppResult(SUCCESS, SUCCESS_MSG, screenInfoService.getScreenInfo(prairieNo));
		} catch (Exception e) {
			logger.error("获取大屏幕数据信息出错===========>" + e.getMessage(), e);
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}
	
	 /**
     * @date 2019年7月16日
     * @author wangyun
     * @time 上午11:38:23
     * @Description 牛只列表 （大屏幕显示使用）
     * 
     * @param request
     * @param token
     * @param page
     * @param appVersion
     * @param client
     * @param siteId
     * @return
     */
    @RequestMapping(value = "/projectList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "项目列表")
    public AppResult projectList(HttpServletRequest request, 
            @ApiParam("当前页号") Integer page,
            @ApiParam("牧场ID")@RequestParam Integer prairieValue,
            @ApiParam("标投资期限及类型")@RequestParam(required=false) Integer limitDays,
            @ApiParam("关键词（耳标号）")@RequestParam(required=false) String keyword) {
    		try {
    			int limit = 7;
        		if (page == null) {
        			page = 1;
        		}
        		
        		Map<String, Object> parMap = new HashMap<String, Object>();
//                User user = userService.checkLogin(token);
        		if (!StringUtils.isEmpty(limitDays)) {
    				if (limitDays == 1) {
    					parMap.put("noob", 1);
    				}else {
    					parMap.put("limitDays", limitDays);
    				}
    			}
               
                parMap.put("limitEnd", limit);
                parMap.put("keyword", keyword);
                parMap.put("status", 1);
                if(prairieValue == null) {
                	prairieValue = 1;
                }
                
                parMap.put("prairieValue", prairieValue);
                
                Integer count = projectService.getMapper().countListGpsProjectByPage(parMap);
                int pages = 1;
//            	Random random = new Random();
//        	    int page_ = random.nextInt(pages) % (pages - 2 + 1) + 2;
                //除了基地，其他只显示100条数据
                if(limit != 0 && prairieValue == 1) {
                    pages = calcPage(count, limit);
                }
                
                parMap.put("limitStart", (page - 1) * limit);
                List<ProjectGpsListVo> projectlist = projectService.projectGpsList(parMap);
                Map<String, Object> returnMap = new HashMap<String, Object>(16);
                returnMap.put("projectDetailInfo", projectlist);
                returnMap.put("pages", pages);
                returnMap.put("count", count);
                returnMap.put("page", page);


                List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

                Map<String, Object> allMap=new HashMap<String, Object>();
                allMap.put("days", "");
                allMap.put("daysName", "全部");
                list.add(allMap);
                Map<String, Object> newMap=new HashMap<String, Object>();
                newMap.put("days", "1");
                newMap.put("daysName", "新手");
                list.add(newMap);

                List<Map<String, Object>> projectDays=ProjectDaysEnum.enumParseMap(list);
                returnMap.put("limitDays", projectDays);
                return new AppResult(SUCCESS, returnMap);
    		} catch (Exception e) {
    			logger.error("访问GPS项目列表====>" + e.getMessage(), e);
    			return new AppResult(FAILED, MESSAGE_EXCEPTION);
    		}
    }
    
    public static void main(String[] args) {
    	/*Random r = new Random();
        int n3 = r.nextInt(11);
        n3 = Math.abs(r.nextInt() % 11);
        System.err.println(n3);
        
        
        int n2 = r.nextInt(n);

        n2 = Math.abs(r.nextInt() % n);*/
	}
}
