package com.goochou.p2b.app.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.service.ProjectService;
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
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.HongbaoTemplateService;
import com.goochou.p2b.utils.DateFormatTools;

@Controller
@RequestMapping("/hongbao")
@Api(value = "红包-hongbao")
public class HongbaoController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(HongbaoController.class);

    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private ProjectService projectService;
    @Resource
    private HongbaoTemplateService hongbaoTemplateService;

    /**
     * 使用红包
     * @author sxy
     * @param token
     * @param appVersion
     * @param id 红包id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/use", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "使用红包")
    public AppResult useHongbao(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("红包id") @RequestParam Integer id) throws Exception {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        Map<String, Integer> ret = hongbaoService.useHongbao(user.getId(), id);
        if (ret.get("result") == 1) {
            return new AppResult(FAILED, "没有该红包!");
        }
        if (ret.get("result") == 2) {
            return new AppResult(FAILED, "该红包已经被使用了!");
        }
        if (ret.get("result") == 3) {
            return new AppResult(FAILED, "该红包已经过期了");
        }
        if (ret.get("result") == 5) {
            return new AppResult(FAILED, "不是现金红包不能使用");
        }
        if (ret.get("result") == 4) {
            return new AppResult(SUCCESS,"该红包使用成功!");
        }
        return null;
    }
    
    public static DecimalFormat MONEY_FORMAT = new DecimalFormat("¥ ###,##0.00");
    
    /**
     * 红包列表
     * @author sxy
     * @param token
     * @param appVersion
     * @param type 红包类型:1现金红包  2投资红包 3优惠券
     * @param status 红包状态: 0 没使用的红包 1 已经使用过的 2 已经过期的红包
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "红包列表")
    public AppResult hongbaoList(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("红包类型  1 现金红包  2牧场红包 3商城券 4拼牛红包") @RequestParam(required = false) Integer type,
            @ApiParam("红包状态 0 没使用的红包 1 在使用的 2 已经过期的红包") @RequestParam Integer status,
            @ApiParam("最低投资金额") @RequestParam(required = false) Double limitAmount,
            @ApiParam("最低投资天数") @RequestParam(required = false) Integer limitDay,
            @ApiParam("分页当前页") @RequestParam Integer page,
            @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client,
            @ApiParam("安卓渠道") @RequestParam(required = false) String dataSource,
            @ApiParam("app名字") @RequestParam(required = false) String appName,
            @ApiParam("包含设备信息") @RequestParam(required = false) String deviceInfo) {
        try {
            int pageSize = 100;
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            if (page == null || page < 1) {
                page = 1;
            }
            Map<String, Object> map1 = new HashMap<String, Object>();
            int count = 0;
            List<Hongbao> list = null;
            if (type == 2 || type == 3 || type == 4) {//2牧场红包 3商城券 4拼牛红包
                count = hongbaoService.getHongbaoInverstmentCount(user.getId(), type, status, limitAmount, limitDay);
                list = hongbaoService.getHongbaoInverstmentList(user.getId(), type, status, (page - 1) * pageSize, pageSize, limitAmount, limitDay);
            } else {
                count = hongbaoService.getCountByUser(user.getId(), status, type == null ? 1 : type, 1);
                list = hongbaoService.getByUser(user.getId(), null, status, type == null ? 1 : type, 1, (page - 1) * pageSize, pageSize);
            }

            List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
            for (Hongbao hongbao : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", hongbao.getId());
                map.put("amount", String.valueOf(hongbao.getAmount()));
                //红包列表中显示红包金额
                map.put("amountStr", MONEY_FORMAT.format(hongbao.getAmount()));
                //选中红包后,显示useAmountStr
                map.put("useAmountStr", MONEY_FORMAT.format(-1 * hongbao.getAmount()));
                map.put("unit","元");
                map.put("ban","不可叠加使用");
                if(type == 3) {
                    map.put("limitAmount", "*单笔满"+String.valueOf(hongbao.getLimitAmount())+"元可用");
                    map.put("useLimit", "*限商城专用");
                }
                if(type == 2) {
                    map.put("minInvestDay","*限饲养期"+hongbao.getLimitDay()+"天以上可用");
                    map.put("useLimit", "*限领养专用");
                }
                if(type == 4) {
                    map.put("minInvestDay","*限饲养期"+hongbao.getLimitDay()+"天以上可用");
                    map.put("limitAmount", "*单笔满"+String.valueOf(hongbao.getLimitAmount())+"元可用");
                    map.put("useLimit", "*限拼牛专用");
                }
                map.put("sendTime", new SimpleDateFormat("yyyy-MM-dd").format(hongbao.getSendTime()));
                map.put("userId", String.valueOf(hongbao.getUserId()));
                if (hongbao.getUseTime() == null) {
                    map.put("useTime", null);
                } else {
                    map.put("useTime", new SimpleDateFormat("yyyy-MM-dd").format(hongbao.getUseTime()));
                }
                map.put("expireTime", new SimpleDateFormat("yyyy-MM-dd").format(hongbao.getExpireTime()));
                map.put("type", String.valueOf(hongbao.getType()));
                if (type == 1) {
                    map.put("descript", "现金红包");
                    map.put("canInvestDesc","");
                    map.put("canFetchDesc","现金红包直接领取至余额");
                    map.put("expireTimeFront", "有效期:" + new SimpleDateFormat("yyyy.MM.dd").format(hongbao.getSendTime()) + " - " + new SimpleDateFormat("yyyy.MM.dd").format(hongbao.getExpireTime()));
                } else {
                    map.put("descript", hongbao.getDescript());
                    map.put("expireTimeFront", "有效期:" + new SimpleDateFormat("yyyy.MM.dd").format(hongbao.getSendTime()) + " - " + new SimpleDateFormat("yyyy.MM.dd").format(hongbao.getExpireTime()));
                }
                if (hongbao.getTemplateId() != null) {
                    Long residual = DateFormatTools.dayToDaySubtract(new Date(), hongbao.getExpireTime());
//                    String month = hongbaoTemplateService.queryById(hongbao.getTemplateId()).get("monthType").toString();
//                    List<String> ts = new ArrayList<String>();
//                    if (month.contains("天")) {
//                        //6.2.1版本以下
//                        if(getVersion(appVersion) < 621){
//                            ts.add("1.满" + String.valueOf(hongbao.getLimitAmount()) + "元可抵扣");
//                            ts.add("2.期限(" + month + ")");
//                        }else{
//                            ts.add("单笔出借满"+hongbao.getLimitAmount()+"元可用");
//                            ts.add("限" + month + "天及以上智投和散标使用");
//                            ts.add("不可叠加使用");
//                        }
//                    } else {
//                        //6.2.1版本以下
//                        if(getVersion(appVersion) < 621) {
//                            ts.add("1.满" + String.valueOf(hongbao.getLimitAmount()) + "元可返现");
//                            ts.add("2.使用期限≥" + month + "天");
//                        }else{
//                            ts.add("单笔出借满"+hongbao.getLimitAmount()+"元可用");
//                            ts.add("限" + month + "天及以上智投和散标使用");
//                            ts.add("不可叠加使用");
//                        }
//                    }
//                    map.put("prompt", ts);
                    //map.put("residual", "(" + (residual + 1) + "天后过期)");

                }
                if(new Date().getTime() > hongbao.getExpireTime().getTime()) {
                    map.put("isExpire", 1);
                } else {
                    map.put("isExpire", 0);
                }
                
                list1.add(map);

            }
            //是否屏蔽养牛信息
            boolean isShowBulls = projectService.isShowBulls(user,appName,dataSource,client);

            int num = 0;
            List<Map<String, Object>> orderStateList=new ArrayList<Map<String,Object>>();
            Map<String, Object> orderStateMap=new HashMap<String, Object>();
            num = hongbaoService.getHongbaoInverstmentCount(user.getId(), 2, status, limitAmount, limitDay);
            orderStateMap.put("code", "2");
            orderStateMap.put("description", "牧场红包");
            if(num > 0) {
                orderStateMap.put("description", "牧场红包("+num+")");
            }
            if(isShowBulls) {
                orderStateList.add(orderStateMap);
            }
            orderStateMap = new HashMap<String, Object>();
            num = hongbaoService.getHongbaoInverstmentCount(user.getId(), 3, status, limitAmount, limitDay);
            orderStateMap.put("code", "3");
            orderStateMap.put("description", "商城券");
            if(num > 0) {
                orderStateMap.put("description", "商城券("+num+")");
            }
            orderStateList.add(orderStateMap);
            orderStateMap = new HashMap<String, Object>();
            num = hongbaoService.getCountByUser(user.getId(), status, 1, 1);
            orderStateMap.put("code", "1");
            orderStateMap.put("description", "现金红包");
            if(num > 0) {
                orderStateMap.put("description", "现金红包("+num+")");
            }
            if(isShowBulls) {
                orderStateList.add(orderStateMap);
            }
            if(getVersion(appVersion) >= 200) {
                orderStateMap = new HashMap<String, Object>();
                num = hongbaoService.getHongbaoInverstmentCount(user.getId(), 4, status, limitAmount, limitDay);
                orderStateMap.put("code", "4");
                orderStateMap.put("description", "拼牛红包");
                if(num > 0) {
                    orderStateMap.put("description", "拼牛红包("+num+")");
                }
                if(isShowBulls) {
                    orderStateList.add(orderStateMap);
                }
            }
            
            map1.put("orderState",orderStateList);
            map1.put("rateCoupon", list1);
            map1.put("page", page);
            map1.put("pages", calcPage(count, pageSize));
            map1.put("count", count);
            
            return new AppResult(SUCCESS, map1);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, "红包列表异常");
        } 
    }

    public static void main(String[] args) {
        String a = "123天";
        System.out.println(a.substring(0, a.lastIndexOf("天")));
    }
}
