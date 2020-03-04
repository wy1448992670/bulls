package com.goochou.p2b.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.model.MallActivity;
import com.goochou.p2b.model.MallActivitySecondKill;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.bulls.MallActivitySecondKillVO;
import com.goochou.p2b.model.vo.bulls.MallActivityVO;
import com.goochou.p2b.service.MallActivityService;
import com.goochou.p2b.utils.DateUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年12月12日 17:57:00
 */

@Controller
@RequestMapping(value = "/shop/activity")
public class MallActivityController extends BaseController {
    
    private static final Logger logger = Logger.getLogger(ShopController.class);
    
    @Resource
    private MallActivityService mallActivityService;

    @RequestMapping(value = "/list")
    public String list(HttpSession session, Model model, String name, Date startDateStart, Date startDateEnd, Date stopDateStart, Date stopDateEnd, Integer page) {
        int limit = 20;
        if (page == null) {
            page = 1;
        }
        // UserAdmin admin = (UserAdmin) session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:activity:list");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        try{
            List<MallActivityVO> list = mallActivityService.listMallActivity(name, startDateStart, startDateEnd, stopDateStart,  stopDateEnd, (page - 1) * limit, limit);
            long count = mallActivityService.countMallActivity(name, startDateStart, startDateEnd, stopDateStart,  stopDateEnd);

            long pages = calcPage(count, limit);
            
            model.addAttribute("name", name);
            model.addAttribute("startDateStart", startDateStart== null ? "" : DateUtil.dateFormat.format(startDateStart));
            model.addAttribute("startDateEnd", startDateEnd== null ? "" : DateUtil.dateFormat.format(startDateEnd));
            model.addAttribute("stopDateStart", stopDateStart== null ? "" : DateUtil.dateFormat.format(stopDateStart));
            model.addAttribute("stopDateEnd", stopDateEnd== null ? "" : DateUtil.dateFormat.format(stopDateEnd));
            model.addAttribute("count", count);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("pages", pages);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "shop/activity/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String add(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:activity:save");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        try{
            model.addAttribute("mallActivity", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "shop/activity/save";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(HttpSession session, Model model, MallActivity mallActivity, Integer target) {
        UserAdmin admin = (UserAdmin) session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:activity:save");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        try{
            if (mallActivity.getId() != null) {
                
            }
            mallActivityService.saveMallActivity(mallActivity, admin);
            if (target!= null) {
                // target 跳转目标页面 1.商品秒杀编辑页面
                switch (target) {
                    case 1: // 商品秒杀活动
                        String targetUrl = "redirect:/shop/activity/detail?optType=1&activityType=%s&id=%s";
                        return String.format(targetUrl, mallActivity.getType(), mallActivity.getId());
//                        return "redirect:/shop/activity/detail?optType=1&activityType=" + mallActivity.getType() + "&id=" + mallActivity.getId();
                    default:
                        model.addAttribute("error", "target错误");
                        return "error";
                }
            }
            return "redirect:/shop/activity/list";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/updateActivityEnableStatus", method = RequestMethod.POST)
    @ResponseBody
    public Object updateActivityEnableStatus(Integer id, Integer enableStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "-1");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:activity:enable");
        }catch (Exception e) {
            map.put("msg", "您没有权限操作");
            return map;
        }
        try {
            mallActivityService.updateMallActivityEableStatus(id, enableStatus);
            map.put("code", 1);
            map.put("msg", "修改成功");
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            map.put("msg", "修改失败:" + e.getMessage());
            return map;
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(HttpSession session, Model model, Integer id, Integer activityType, Integer optType) {
        // optType 操作类型 0.详情页，1.编辑页
        if (optType == null) {
            optType = 0;
        }
        Subject subject = SecurityUtils.getSubject();
        try{
            switch (optType) {
                case 0:
                    try {
                        subject.checkPermission("shop:activity:detail");
                    }catch (Exception e) {
                        model.addAttribute("error", "您没有权限查看");
                        return "error";
                    }
                    break;
                case 1:
                    try {
                        subject.checkPermission("shop:activity:save");
                    }catch (Exception e) {
                        model.addAttribute("error", "您没有权限操作");
                        return "error";
                    }
                    break;
                default:
                    model.addAttribute("error", "optType错误");
                    return "error";
            }
//            if (activityType == 1) { // 1.秒杀活动 （以后可能还有其他活动，此字段用于扩展）
//            }
            MallActivityVO detail = mallActivityService.detailMallActivity(id);
            List<MallActivitySecondKillVO> listSecondKill = mallActivityService.listMallActivitySecondKillByActivityId(id);
            model.addAttribute("mallActivity", detail);
            model.addAttribute("listSecondKill", listSecondKill);
            switch (optType) {
                case 0:
                    return "shop/activity/detail";
                case 1:
                    return "shop/activity/edit";
                default:
                    model.addAttribute("error", "optType错误");
                    return "error";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/saveGoodsSecondKill", method = RequestMethod.GET)
    public String gotoSave(HttpSession session, Model model, Integer activityId, Integer id) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:activity:secondKill:save");
        } catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        try {
            MallActivitySecondKillVO data = null;
            if (id != null) {
                data = mallActivityService.getMallActivitySecondKillById(id);
                model.addAttribute("optType", "edit");
            } else {
                model.addAttribute("optType", "add");
            }
            MallActivity activity = mallActivityService.getMapper().selectByPrimaryKey(activityId);
            model.addAttribute("data", data);
            model.addAttribute("activity", activity);
        } catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        return "shop/activity/saveGoodsSecondKill";
    }

    @RequestMapping(value = "/saveGoodsSecondKill", method = RequestMethod.POST)
    public String save(HttpSession session, Model model, MallActivitySecondKill mallActivitySecondKill, MultipartFile file, String startTimeStr) {
        UserAdmin admin = (UserAdmin) session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        try {
            try {
                subject.checkPermission("shop:activity:secondKill:save");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            if (mallActivitySecondKill.getGoodId() == null) {
                model.addAttribute("error", "秒杀商品不能为空");
                return "error";
            }
            if (mallActivitySecondKill.getLimitCount() <= 0) {
                model.addAttribute("error", "每人限购数量不能小于1");
                return "error";
            }
            if (mallActivitySecondKill.getStockCount() <= 0) {
                model.addAttribute("error", "活动库存不能小于1");
                return "error";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date startTime = sdf.parse(startTimeStr);
            mallActivitySecondKill.setStartTime(startTime);
            mallActivityService.saveMallActivitySecondKill(mallActivitySecondKill, file, admin);
            MallActivity activity = mallActivityService.getMapper().selectByPrimaryKey(mallActivitySecondKill.getActivityId());
            String targetUrl = "redirect:/shop/activity/detail?optType=1&activityType=%s&id=%s";
            return String.format(targetUrl, activity.getType(), activity.getId());
        }catch (Exception e) {
            model.addAttribute("error", "保存失败：" + e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/delGoodsSecondKill", method = RequestMethod.POST)
    @ResponseBody
    public Object delGoodsSecondKill(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "-1");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:activity:secondKill:save");
        }catch (Exception e) {
            map.put("msg", "您没有权限操作");
            return map;
        }
        try {
            mallActivityService.deleteMallActivitySecondKill(id);
            map.put("code", 1);
            map.put("msg", "删除成功");
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            map.put("msg", "删除失败:" + e.getMessage());
            return map;
        }
    }


    @RequestMapping(value = "/calculateLockStock", method = RequestMethod.POST)
    @ResponseBody
    public Object calculateLockStock(Integer mallActivityId, Integer stockCount, Integer weekDay) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "-1");
        
        try {
            JSONObject data = new JSONObject();
            MallActivity activity = mallActivityService.getMapper().selectByPrimaryKey(mallActivityId);
            if (activity != null) {
                int weekCount = mallActivityService.calculateCycleTimes(activity.getStartDate(), activity.getStopDate(), weekDay);
                int lockStock = weekCount * stockCount;
                data.put("lockStock", lockStock);
                data.put("weekDay", lockStock);
                data.put("weekCount", weekCount);
                data.put("startDate", DateUtil.dateFormat.format(activity.getStartDate()));
                data.put("stopDate", DateUtil.dateFormat.format(activity.getStopDate()));
            } else {
                map.put("msg", "活动不存在");
            }
            map.put("code", 1);
            map.put("data", data);
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            map.put("msg", "异常：" + e.getMessage());
            return map;
        }
    }
    
}
