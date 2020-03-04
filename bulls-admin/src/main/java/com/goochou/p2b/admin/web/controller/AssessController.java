package com.goochou.p2b.admin.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.dao.CronTriggersMapper;
import com.goochou.p2b.model.Assess;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.AssessService;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.EnterpriseService;
import com.goochou.p2b.service.GoodsBrandService;
import com.goochou.p2b.service.GoodsCategoryService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.GoodsPictureService;
import com.goochou.p2b.service.GoodsService;
import com.goochou.p2b.service.ProjectAccountService;
import com.goochou.p2b.service.ProjectActivitySettingService;
import com.goochou.p2b.service.ProjectClassService;
import com.goochou.p2b.service.ProjectPictureService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.UserService;

/**
 * 评价
 * 
 * @ClassName ShopController
 * @author zj
 * @Date 2019
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/shop")
public class AssessController extends BaseController {
	private static final Logger logger = Logger.getLogger(AssessController.class);
	@Resource
	private ProjectService projectService;
	@Resource
	private ProjectPictureService projectPictureService;
	@Resource
	private UserService userService;
	@Resource
	private ProjectAccountService projectAccountService;
	@Resource
	private AssetsService assetsService;
	@Resource
	private ProjectClassService projectClassService;
	@Resource
	private ProjectActivitySettingService projectActivitySettingService;

	@Resource
	private EnterpriseService enterpriseService;

	@Resource
	private CronTriggersMapper cronTriggersMapper;

	@Autowired
	GoodsService goodsService;
	@Autowired
	GoodsPictureService goodsPictureService;
	@Autowired
	GoodsCategoryService goodsCategoryService;
	@Autowired
	GoodsOrderService goodsOrderService;
	@Autowired
	GoodsBrandService goodsBrandService;
	@Autowired
	AssessService assessService;

	/**
	 * 商品评价列表
	 * 
	 * @Title: assessList
	 * @param model
	 * @param goodsName
	 * @param trueName
	 * @param page
	 * @return String
	 * @author zj
	 * @date 2019-06-04 11:41
	 */
	@RequestMapping(value = "/assessList")
	public String assessList(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer page, Integer replyStatus) {

		try {

			int limit = 20;
			if (page == null) {
				page = 1;
			}

			List<Map<String, Object>> assessMaps = assessService.listAssessByPage((page - 1) * limit, limit, keyword,
					replyStatus);
			// 统计总记录数

			int count = assessService.countAssess(keyword, replyStatus);

			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", assessMaps);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("replyStatus", replyStatus);

		} catch (Exception e) {
			logger.error("查询评价列表出错==========>" + e.getMessage(), e);
		}
		return "/assess/assessList";
	}

	/**
	 * 查看评价明细
	 * 
	 * @Title: assessDetail
	 * @param model
	 * @param goodsId
	 * @param userId
	 * @return String
	 * @author zj
	 * @date 2019-06-04 11:52
	 */
	@RequestMapping(value = "/assessDetail")
	public String assessDetail(Model model, @RequestParam(required = true) Integer goodsId,
			@RequestParam(required = true) Integer userId, @RequestParam(required = true) Integer orderId,
			@RequestParam(required = true) Integer parentId) {

		try {
		    Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("shop:assessDetail");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            
			List<Map<String, Object>> list = assessService.listOneAssessDetail(goodsId, userId);
			User user = userService.get(userId);
			model.addAttribute("assessDetails", list);
			model.addAttribute("goodsId", goodsId);
			model.addAttribute("userId", userId);
			model.addAttribute("orderId", orderId);
			model.addAttribute("parentId", parentId);
			model.addAttribute("buyer", user);
		} catch (Exception e) {
			logger.error("查询评价明细==========>" + e.getMessage(), e);
		}
		return "/assess/assessDetail";
	}

	/**
	 * 管理员回复
	 * 
	 * @Title: addReply
	 * @param model
	 * @param goodsId
	 * @param userId
	 * @return String
	 * @author zj
	 * @date 2019-06-04 17:21
	 */
	@RequestMapping(value = "/addReply")
	public String addReply(HttpServletRequest req, Model model, Assess assess) {
		try {
			HttpSession session = req.getSession();
			UserAdmin ua = (UserAdmin) session.getAttribute("user");
			Integer userId = assess.getUserId();// 卖家id 记录 以便后面传参
			assess.setUserId(ua.getId());// 保存 管理员id
			assess.setCreateDate(new Date());
			assess.setUpdateDate(new Date());
			assessService.addReply(assess);
			return "redirect:/shop/assessDetail?goodsId=" + assess.getGoodsId() + "&userId=" + userId + "&orderId="
					+ assess.getOrderId() + "&parentId=" + assess.getParentId();
		} catch (Exception e) {
			logger.error("卖家回复异常==========>" + e.getMessage(), e);
		}
		return "redirect:/shop/assessList";
	}

	/**
	 * 置顶评价
	 * 
	 * @Title: setTop
	 * @param req
	 * @param assessId 评价主键
	 * @param isTop    0 未置顶 1 置顶
	 * @return String
	 * @author zj
	 * @date 2019-06-05 15:01
	 */
	@RequestMapping(value = "/setTopAjax")
	@ResponseBody
	public Object setTopAjax(HttpServletRequest req, HttpServletResponse response,
			@RequestParam(required = true) Integer assessId, @RequestParam(required = true) Integer isTop) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			assessService.updateAssessToTop(assessId, isTop);
			map = new HashMap<String, Object>();
			map.put("code", 1);
			return map;
		} catch (Exception e) {
			logger.error("置顶评价异常==========>" + e.getMessage(), e);
			map.put("code", -1);
			return map;
		}
	}

	/**
	 * 删除评价（逻辑删除）
	 * 
	 * @Title: delAssess
	 * @param req
	 * @param response
	 * @param assessId
	 * @return Object
	 * @author zj
	 * @date 2019-06-05 16:24
	 */
	@RequestMapping(value = "/delAssessAjax")
	@ResponseBody
	public Object delAssessAjax(HttpServletRequest req, HttpServletResponse response,
			@RequestParam(required = true) Integer assessId, @RequestParam(required = true) Integer state) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			assessService.delAssess(assessId, state);
			map = new HashMap<String, Object>();
			map.put("code", 1);
			map.put("msg", "删除成功");
			return map;
		} catch (Exception e) {
			logger.error("删除评价异常==========>" + e.getMessage(), e);
			map.put("code", "-1");
			map.put("msg", "删除失败");
			return map;
		}
	}
}
