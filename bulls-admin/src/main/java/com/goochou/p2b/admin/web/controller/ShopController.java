package com.goochou.p2b.admin.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.*;
import com.goochou.p2b.service.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.goochou.p2b.admin.annotatioin.Token;
import com.goochou.p2b.constant.ApplyRefundStatusEnum;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.dao.CronTriggersMapper;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayResponse;
import com.goochou.p2b.hessian.openapi.pay.QueryYeePayResponse;
import com.goochou.p2b.hessian.openapi.pay.YeePayRequest;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsBrand;
import com.goochou.p2b.model.goods.GoodsBrandExample;
import com.goochou.p2b.model.goods.GoodsCategory;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.model.goods.GoodsOrderRefund;
import com.goochou.p2b.utils.AjaxUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.Money;
import com.goochou.p2b.utils.StringUtils;
import com.goochou.p2b.utils.alipay.AlipayConfig;
import com.goochou.p2b.utils.weixin.MyConfig;
import com.goochou.p2b.utils.weixin.WXPay;

/**
 * 商城
 *
 * @ClassName ShopController
 * @author zj
 * @Date 2019
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopController extends BaseController {
	private static final Logger logger = Logger.getLogger(ShopController.class);
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
	private AssessService assessService;

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
	GoodsOrderRefundService goodsOrderRefundService;
	@Autowired
	TmDictService tmDictService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
    GoodsOrderDetailService goodsOrderDetailService;
	@Resource
    private DepartmentService departmentService;
	
	/**
	 * 商品列表页展示
	 *
	 * @param model
	 * @param keyword
	 * @param status
	 * @param page
	 * @param noob
	 * @param limitDays
	 * @param desc
	 * @param lendBeginTimeStartTime
	 * @param lendBeginTimeEndTime
	 * @return
	 * @author: zj
	 */
	@RequestMapping(value = "/goodsList")
	public String goodsList(Model model, @RequestParam(required = false) String keyword, @RequestParam(required = false) String skuCode,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) String desc) {

		try {

			int limit = 20;
			if (page == null) {
				page = 1;
			}
			if (null == desc || "".equals(desc)) {
				desc = "desc";
			}

			List<Goods> list = goodsService.listGoods((page - 1) * limit, limit, keyword, skuCode);
			// 统计总记录数

			int count = goodsService.countGoods(keyword, skuCode);

			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("skuCode", skuCode);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "/shop/goodsList";
	}

	/**
	 * 新增商品页展示
	 *
	 * @param model
	 * @return
	 * @author: zj
	 */
	@Token(save = true)
	@RequestMapping(value = "/addGoods")
	public String addGoods(Model model) {
		try {
		    Subject subject = SecurityUtils.getSubject();
	        try {
	            subject.checkPermission("shop:addGoods");
	        }catch (Exception e) {
	            model.addAttribute("error", "您没有权限查看");
	            return "error";
	        }

			List<GoodsCategory> list = goodsCategoryService.selectGoodsCategoryList();
			model.addAttribute("goodsCategoryList", list);
			GoodsBrandExample example = new GoodsBrandExample();
			List<GoodsBrand> goodsBrands = goodsBrandService.getGoodsBrandMapper().selectByExample(example);
			model.addAttribute("goodsBrands", goodsBrands);

			model.addAttribute("stockUnit", tmDictService.listTmDict("stock_unit"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "/shop/addGoods";
	}

	/**
	 * 上传图片
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(String picName, @RequestParam MultipartFile file, Integer type, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> map = null;
		try {
			GoodsPicture picture = new GoodsPicture();
			picture.setStatus(1);
			picture.setCreateDate(new Date());
			picture.setName(picName.trim());
			picture.setType(type);
			map = goodsPictureService.save(picture, file, ((UserAdmin) session.getAttribute("user")).getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}

	/**
	 * 获取商品属性
	 *
	 * @param productId
	 * @return
	 * @author: zj
	 */
	@ResponseBody
	@RequestMapping(value = "/listGoodsPropertyAjax")
	public Object listGoodsPropertyAjax(int categoryId) {
		return goodsService.listGoodsProperty(categoryId);
	}

	/**
	 * 增加商品入库
	 *
	 * @param request
	 *
	 * @param packageType
	 * @return
	 * @author: zj
	 */
	@Token(remove = true)
	@RequestMapping(value = "/addGoods", method = RequestMethod.POST)
	public String addGoods(HttpServletRequest request, Goods good, String picture, String picture2, String picture3,
			String picture4, Integer detailId, String period, Integer copyId, String packageType) {
		try {

			String[] productPropertyIdArray = request.getParameterValues("ids");
			String[] propertyValueArray = request.getParameterValues("category");

			List<String> pictures = new ArrayList<>();
			pictures.add(picture);
			pictures.add(picture2);
//			pictures.add(picture3);
//			pictures.add(picture4);

			good.setCreateDate(new Date());
			good.setUpdateDate(new Date());

			if (good.getSort() == null) {
				good.setSort(99);
			}

			goodsService.saveWithPicture(good, pictures, productPropertyIdArray, propertyValueArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/shop/goodsList";

	}



	/**
	 * 添加商品评价
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addAppraise")
	public String goodsAppraise(int id, Model model) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("shop:addAppraise");
		}catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}

		model.addAttribute("goodId",id);
		return "/shop/addAppraise";
	}

	/**
	 * 上传评价图片
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadCommentImg", method = RequestMethod.POST)
	public void uploadCommentImg(@RequestParam MultipartFile file, HttpServletResponse response,
								 HttpSession session) {
		Map<String, Object> map = null;
		try {
			AssessImgs picture = new AssessImgs();
			picture.setCreateDate(new Date());
			map = assessService.saveImages(picture, file, ((UserAdmin) session.getAttribute("user")).getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}


	/**
	 * 后台添加评价
	 * @param request
	 * @param session
	 * @param goodId
	 * @param content
	 * @param picture2
	 * @return
	 */
//	@Token(remove = true)
	@RequestMapping(value = "/addGoodsComment", method = RequestMethod.POST)
	public String addGoodsComment(HttpSession session, Model model, Integer goodId, String content,String dateTime, String picture2,RedirectAttributes attributes) {
		try {
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
			assessService.addComment(goodId,admin.getId(),content,picture2,dateTime);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "error";
		}
		attributes.addAttribute("goodId",goodId);
		return "redirect:/shop/appraiseList";
	}


	/**
	 * 后台商品评价
	 * @param goodId
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/appraiseList")
	public String appraiseList(int goodId,@RequestParam(required = false) Integer page, Model model) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("shop:appraiseList");
		}catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}

			List<Assess> list = assessService.getSelfAssessByGoodsId(goodId,(page - 1) * limit, limit);
			// 统计总记录数
			int count = assessService.countSelfAssessByGoodsId(goodId);

			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		model.addAttribute("goodId",goodId);
		return "/shop/appraiseList";
	}

	/**
	 * 编辑商品页展示
	 *
	 * @param id
	 * @param model
	 * @return
	 * @author: zj
	 */
	@RequestMapping(value = "/goodsEdit")
	public String goodsEdit(int id, Model model) {
	    Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:goodsEdit");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }

		List<GoodsCategory> list = goodsCategoryService.selectGoodsCategoryList();
		model.addAttribute("goodsCategoryList", list);
		Goods good = goodsService.getGood(id);
		List<Map<String, Object>> properesMaps = goodsService.listGoodsProperties(id);
		model.addAttribute("good", good);
		model.addAttribute("properesMaps", properesMaps);// 存放商品相关的属性 分类 及 内容

		GoodsBrandExample example = new GoodsBrandExample();
		List<GoodsBrand> goodsBrands = goodsBrandService.getGoodsBrandMapper().selectByExample(example);
		model.addAttribute("goodsBrands", goodsBrands);// 商品品牌存入

		model.addAttribute("stockUnit", tmDictService.listTmDict("stock_unit"));// 单位
		return "/shop/goodsEdit";
	}

	/**
	 * 商品编辑入库
	 *
	 * @param request
	 * @param project
	 * @param picture
	 * @param picture2
	 * @param packageType
	 * @return
	 * @author: zj
	 */
	@RequestMapping(value = "/goodsEditData")
	public String goodsEditData(HttpServletRequest request, Goods good, String picture, String picture2,
			String packageType) {
		try {
			String[] productPropertyIdArray = request.getParameterValues("ids");
			String[] propertyValueArray = request.getParameterValues("category");
			goodsService.update(good, picture, picture2, productPropertyIdArray, propertyValueArray);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "redirect:/shop/goodsList";
	}

	/**
	 * 商品详情页跳转
	 *
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: zj
	 */
	@RequestMapping(value = "/goodsDetail")
	public String goodsDetail(int id, Model model) throws Exception {
	    Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:goodsDetail");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }

		model.addAttribute("good", goodsService.getGood(id));
		return "/shop/goodsDetail";
	}

	/**
	 * 获取商品购买记录
	 *
	 * @param model
	 * @return
	 * @author: zj
	 */
	@ResponseBody
	@RequestMapping(value = "/listShopAjax")
	public Object listShopAjax(Model model, Integer page, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}

			List<Map<String, Object>> orderList = goodsOrderService.listBuyGoodsRecord(id, (page - 1) * limit, limit);
			int count = goodsOrderService.countBuyGoodsRecord(id, (page - 1) * limit, limit);

			int pages = calcPage(count, limit);

			map.put("pages", pages);
			map.put("page", page);
			map.put("list", orderList);
			map.put("code", "1");
		} catch (Exception e) {
			map.put("code", "-1");
			logger.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 商品订单列表
	 *
	 * @author sxy
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
	public String orderList(Model model, HttpSession session, String trueName, String keyword, 
			@RequestParam(required = false) List<Integer> status, Date startTime, Date endTime,Integer page, Integer goodsId, Integer expressWay,
			Date payStartTime,Date payEndTime, Date refundFinishStartTime, Date refundFinishEndTime, Integer departmentId, String payChannel) {

		try {
			Subject subject = SecurityUtils.getSubject();
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId=null;
	        try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId=admin.getId();
			}
			List<Map<String, Object>> orderList = goodsOrderService.listGoodsOrder(trueName, goodsId, keyword, status,
					startTime, DateUtil.getDateAfter(endTime,1), (page - 1) * limit, limit, null, expressWay,
					payStartTime,DateUtil.getDateAfter(payEndTime,1),refundFinishStartTime,DateUtil.getDateAfter(refundFinishEndTime,1)
					,adminId,departmentId, payChannel);
			int count = goodsOrderService.countOrder(trueName, goodsId, keyword, status, startTime, DateUtil.getDateAfter(endTime,1), null, expressWay,
					payStartTime,DateUtil.getDateAfter(payEndTime,1),refundFinishStartTime,DateUtil.getDateAfter(refundFinishEndTime,1)
					,adminId,departmentId, payChannel);

			int pages = calcPage(count, limit);

			model.addAttribute("trueName", trueName);
			model.addAttribute("keyword", keyword);
			model.addAttribute("status", StringUtils.join(status));
			model.addAttribute("startTime",DateUtil.format(startTime,"yyyy-MM-dd"));
			model.addAttribute("endTime", DateUtil.format(endTime,"yyyy-MM-dd"));
			model.addAttribute("payStartTime",DateUtil.format(payStartTime,"yyyy-MM-dd"));
			model.addAttribute("payEndTime", DateUtil.format(payEndTime,"yyyy-MM-dd"));
			model.addAttribute("refundFinishStartTime",DateUtil.format(refundFinishStartTime,"yyyy-MM-dd"));
			model.addAttribute("refundFinishEndTime", DateUtil.format(refundFinishEndTime,"yyyy-MM-dd"));
			model.addAttribute("pages", pages);
			model.addAttribute("list", orderList);
			model.addAttribute("page", page);
			model.addAttribute("expressWay", expressWay);
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("departments", departmentService.getShowDepartments(admin.getDepartmentId()));
			model.addAttribute("payChannels", OutPayEnum.values());
			model.addAttribute("payChannel", payChannel);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return "shop/orderList";

	}

	/**
	 * 商城订单详情
	 *
	 * @author sxy
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
	public String orderDetail(Model model, Integer id) {
	    Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:orderDetail");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }

		GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKey(id);
		List<GoodsOrderDetail> orderDetailList = goodsOrder.getGoodsOrderDetail();
		List<Goods> goodsList = new ArrayList<Goods>();

		for (GoodsOrderDetail detail : orderDetailList) {
			Goods goods = detail.getGoods();
			goodsList.add(goods);
		}

		model.addAttribute("info", goodsOrder);
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("orderDetailList", orderDetailList);

		return "shop/orderDetail";

	}

	/**
	 * 用户详情-商城订单列表
	 *
	 * @author sxy
	 * @param response
	 * @param orderStatus
	 * @param page
	 * @param userId
	 */
	@RequestMapping(value = "/listOrderAjax", method = RequestMethod.GET)
	public void listOrderAjax(HttpServletResponse response,@RequestParam(required = false) List<Integer> orderStatus, Integer page, Integer userId) {
		try {
			int limit = 5;
			page = page == null ? 1 : page;

			List<Map<String, Object>> orderList = goodsOrderService.listGoodsOrder(null, null, null, orderStatus, null,
					null, (page - 1) * limit, limit, userId, null,
					null,null,null,null,null,null,null);
			int count = goodsOrderService.countOrder(null, null, null, orderStatus, null, null, userId, null,
					null,null,null,null,null,null,null);

			int pages = calcPage(count, limit);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("pages", pages);
			map.put("list", orderList);
			map.put("status", orderStatus);
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 订单发货页面
	 *
	 * @author sxy
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sendGoods", method = RequestMethod.GET)
	public String sendGoods(Model model, Integer id) {
	    Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("shop:sendGoods");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }

		GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKey(id);

		model.addAttribute("goodsOrder", goodsOrder);

		return "shop/sendGoods";
	}

	/**
	 * 订单发货
	 *
	 * @author sxy
	 * @param goodsOrder
	 * @return
	 */
	@RequestMapping(value = "/sendGoods", method = RequestMethod.POST)
	public String sendGoods(GoodsOrder goodsOrder, RedirectAttributes redirectAttributes) {
		try {
			// 更新订单状态和物流信息
			goodsOrderService.updateOrderExpress(goodsOrder);

			// 发货成功后发送短信
			GoodsOrder goodsOrderNew = goodsOrderService.getMapper().selectByPrimaryKey(goodsOrder.getId());
			User user = userService.get(goodsOrderNew.getUserId());

			SendMessageRequest smr = new SendMessageRequest();
			String messageContent = DictConstants.SEND_GOODS_VALIDATE_CODE;
			messageContent = messageContent
					.replaceAll("\\{title}",
							String.valueOf(goodsOrderNew.getGoodsOrderDetail().get(0).getGoods().getGoodsName()))
					.replaceAll("\\{orderNo}", String.valueOf(goodsOrderNew.getOrderNo()));
			smr.setContent(messageContent);
			smr.setPhone(user.getPhone());
			ServiceMessage msg = new ServiceMessage("message.send", smr);
			OpenApiClient.getInstance().setServiceMessage(msg).send();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

			redirectAttributes.addFlashAttribute("flag", -1);
			return "redirect:/shop/orderList";
		}

		redirectAttributes.addFlashAttribute("flag", 0);
		return "redirect:/shop/orderList";
	}

	/**
	 * 退款审核列表
	 *
	 * @author sxy
	 * @param model
	 * @param trueName
	 * @param keyword
	 * @param auditStatus
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/refundList", method = RequestMethod.GET)
	public String refundList(Model model, HttpSession session, String trueName, String keyword, Integer auditStatus,
							 Date startTime, Date endTime, Date auditStartTime, Date auditEndTime, Integer page,
							 Integer departmentId, String payChannel) {

		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			Subject subject = SecurityUtils.getSubject();
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId=null;
	        try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId=admin.getId();
			}
			List<Map<String, Object>> refundList = goodsOrderRefundService.listGoodsOrderRefund(trueName, keyword,
					auditStatus, startTime, endTime, auditStartTime, auditEndTime, (page - 1) * limit, limit
					,adminId,departmentId, payChannel);
			Integer count = goodsOrderRefundService.countGoodsOrderRefund(trueName, keyword, auditStatus,
					startTime, endTime, auditStartTime, auditEndTime,adminId,departmentId, payChannel);

			int pages = calcPage(count, limit);

			model.addAttribute("trueName", trueName);
			model.addAttribute("keyword", keyword);
			model.addAttribute("auditStatus", auditStatus);
			model.addAttribute("startTime",
					startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
			model.addAttribute("endTime", endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
			model.addAttribute("pages", pages);
			model.addAttribute("auditStartTime", auditStartTime != null ? DateFormatTools.dateToStr2(auditStartTime) : null);
			model.addAttribute("auditEndTime", auditEndTime != null ? DateFormatTools.dateToStr2(auditEndTime) : null);
			model.addAttribute("list", refundList);
			model.addAttribute("page", page);
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("departments", departmentService.getShowDepartments(admin.getDepartmentId()));
			model.addAttribute("payChannels", OutPayEnum.values());
            model.addAttribute("payChannel", payChannel);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return "shop/refundList";
	}

	/**
	 * 退款审核
	 *
	 * @author sxy
	 * @param session
	 * @param id
	 * @param auditRemark
	 * @param auditStatus
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/refundAudit", method = RequestMethod.POST)
	public String refundAudit(Model model, HttpSession session, Integer id, String auditRemark, Integer auditStatus,
			RedirectAttributes redirectAttributes) {

		try {
		    Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("shop:refundAudit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }

			UserAdmin userAdmin = (UserAdmin) session.getAttribute("user");
			GoodsOrderRefund orderRefund = goodsOrderRefundService.getMapper().selectByPrimaryKey(id);
			if (auditStatus == ApplyRefundStatusEnum.PASS.getCode()) { // 通过
				GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKey(orderRefund.getOrderId());
				boolean flag = true;
				if (goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO) > 0) {
					// todo:原通道返还第三方支付资金
					Recharge recharge = rechargeService.getPaySuccessRechargeByOrderTypeAndId(
							OrderTypeEnum.GOODS.getFeatureName(), goodsOrder.getId());
					if (null != recharge && recharge.getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
						// TODO没有异步控制
						AllinPayRequest pay = new AllinPayRequest();
						pay.setOrderNo(recharge.getOrderNo());
						pay.setAmount(new Money(goodsOrder.getRealPayMoney()).getCent());
						ServiceMessage msg = new ServiceMessage("allinpay.order.refund", pay);
						AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance().setServiceMessage(msg)
								.send();
						if (result.isSuccess()) {
							logger.info("退款审核通过");
							redirectAttributes.addFlashAttribute("flag", 0);
						} else {
						    flag = false;
							redirectAttributes.addFlashAttribute("msg", "调用通联支付接口失败,请查询支付状态");
							redirectAttributes.addFlashAttribute("flag", -1);
						}
					}else if (null != recharge && recharge.getPayChannel().equals(OutPayEnum.YEEPAY.getFeatureName())) {
					    YeePayRequest pay = new YeePayRequest();
                        pay.setOrderNo(recharge.getOrderNo());
                        pay.setAmount(String.valueOf(goodsOrder.getRealPayMoney()));
                        pay.setOutOrderNo(recharge.getOutOrderNo());
                        ServiceMessage msg = new ServiceMessage("yeepay.order.refund", pay);
                        QueryYeePayResponse result = (QueryYeePayResponse) OpenApiClient.getInstance().setServiceMessage(msg)
                                .send();
                        /**
                         * PROCESSING：处理中 SUCCESS：退款成功 FAILED：退款失败 REJECTIVE：退款拒绝 如有之外情况，一律认为失败
                         */
                        if (result.isSuccess()) {
                            if(result.getStatus().equals("SUCCESS") || result.getStatus().equals("PROCESSING")) {
                                logger.info("退款审核通过");
                                redirectAttributes.addFlashAttribute("flag", 0);
                            }else {
                                flag = false;
                                logger.info("status="+result.getStatus());
                                redirectAttributes.addFlashAttribute("flag", -1);
                            }
                        } else {
                            flag = false;
                            redirectAttributes.addFlashAttribute("msg", "调用易宝支付接口失败,请查询支付状态");
                            redirectAttributes.addFlashAttribute("flag", -1);
                        }
					}else if (null != recharge && recharge.getPayChannel().equals(OutPayEnum.WXPAY.getFeatureName())) {
					    Map<String, String> data = new HashMap<String, String>();
		                data.put("transaction_id", recharge.getOutOrderNo());
		                data.put("out_refund_no", System.currentTimeMillis()+"");
		                data.put("total_fee", String.valueOf(new Money(goodsOrder.getRealPayMoney()).getCent()));
		                data.put("refund_fee", String.valueOf(new Money(goodsOrder.getRealPayMoney()).getCent()));
		                logger.info(JSONArray.toJSONString(data));
		                Map<String, String> qresult = new HashMap<String, String>();
		                try {
		                    MyConfig config = new MyConfig();
		                    WXPay wxpay = new WXPay(config, false, false);
		                    qresult = wxpay.refund(data);
		                    logger.info("qresult="+JSONArray.toJSONString(qresult));
		                    if (!wxpay.isResponseSignatureValid(qresult)) {
		                        logger.info("==============微信签名校验失败==============");
		                        flag = false;
	                            redirectAttributes.addFlashAttribute("msg", "微信签名校验失败");
	                            redirectAttributes.addFlashAttribute("flag", -1);
		                    }
		                    if(qresult.get("return_code").equals("SUCCESS") && qresult.get("result_code").equals("SUCCESS")) {
		                        logger.info("退款审核通过");
                                redirectAttributes.addFlashAttribute("flag", 0);
		                    }else {
		                        flag = false;
                                redirectAttributes.addFlashAttribute("flag", -1);
                                redirectAttributes.addFlashAttribute("msg", "调用微信支付退款接口失败,请查询支付状态");
		                    }
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
					}else if (null != recharge && recharge.getPayChannel().equals(OutPayEnum.ALPAY.getFeatureName())) {
                        try {
                            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
                            certAlipayRequest.setServerUrl(AlipayConfig.URL);
                            certAlipayRequest.setAppId(AlipayConfig.APPID);
                            certAlipayRequest.setPrivateKey(AlipayConfig.RSA_PRIVATE_KEY);
                            certAlipayRequest.setFormat(AlipayConfig.FORMAT);
                            certAlipayRequest.setCharset(AlipayConfig.CHARSET);
                            certAlipayRequest.setSignType(AlipayConfig.SIGNTYPE);
                            certAlipayRequest.setCertPath(AlipayConfig.CERT_PATH);
                            certAlipayRequest.setAlipayPublicCertPath(AlipayConfig.ALIPAY_PUBLIC_CERT_PATH);
                            certAlipayRequest.setRootCertPath(AlipayConfig.ROOT_CERT_PATH);
                            DefaultAlipayClient client = new DefaultAlipayClient(certAlipayRequest);
                            AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();
                            AlipayTradeRefundModel alipayTradeRefundModel = new AlipayTradeRefundModel();
                            alipayTradeRefundModel.setOutTradeNo(recharge.getOrderNo());
                            alipayTradeRefundModel.setTradeNo(recharge.getOutOrderNo());
                            alipayTradeRefundModel.setRefundAmount(String.valueOf(new Money(recharge.getAmount()).getAmount()));
                            alipay_request.setBizModel(alipayTradeRefundModel);
                            AlipayTradeRefundResponse alipay_response = client.certificateExecute(alipay_request);
                            logger.info("qresult="+JSONArray.toJSONString(alipay_response));
                            if(alipay_response.getCode().equals("10000") && StringUtils.isNotEmpty(alipay_response.getRefundFee())) {
                                logger.info("退款审核通过");
                                redirectAttributes.addFlashAttribute("flag", 0);
                            }else {
                                flag = false;
                                redirectAttributes.addFlashAttribute("flag", -1);
                                redirectAttributes.addFlashAttribute("msg", "调用支付宝支付退款接口失败,请查询支付状态");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
					}else {
					    flag = false;
					}
				}
				if(flag) {
				    goodsOrderRefundService.updateGoodsOrderRefundPass(id, auditRemark, userAdmin.getId());
				}
			} else if (auditStatus == ApplyRefundStatusEnum.NO_PASS.getCode()) { // 打回
				goodsOrderRefundService.updateGoodsOrderRefundNoPass(id, auditRemark, userAdmin.getId(),
						orderRefund.getOrderId());
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("flag", -1);
			return "redirect:/shop/refundList";
		}
		return "redirect:/shop/refundList";
	}

	/**
	 * 获取商品评价记录
	 *
	 * @param model
	 * @return
	 * @author: zj
	 */
	@ResponseBody
	@RequestMapping(value = "/listGoodsAssessPageAjax")
	public Object listGoodsAssessPageAjax(Model model, Integer page, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}

			List<Map<String, Object>> assessList = goodsOrderService.listGoodsAssessPage(id, (page - 1) * limit, limit);
			int count = goodsOrderService.countGoodsAssess(id);

			int pages = calcPage(count, limit);
			map.put("code", 1);
			map.put("pages", pages);
			map.put("page", page);
			map.put("list", assessList);

		} catch (Exception e) {
			map.put("code", "-1");
			map.put("msg", "获取评价失败");
			logger.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 删除项目图片
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/picture", method = RequestMethod.POST)
	public void deletePicture(Integer id, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			goodsPictureService.delete(id);
			map.put(STATUS, SUCCESS);
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e);
			map.put(STATUS, ERROR);
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		}
	}

	public static void main(String[] args) {
		System.out.println(BigDecimal.valueOf(100).compareTo(BigDecimal.ZERO));
	}

	/**
	 * 明细查询列表
	 * @author sxy
	 * @param model
	 * @param trueName
	 * @param keyword
	 * @param orderStatus
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/orderDetailList", method = RequestMethod.GET)
    public String detailList(Model model,HttpSession session, String trueName, String keyword, Integer orderStatus,
    		Integer payStatus, String goodsCategory, Date startTime,Date endTime, Integer page,Integer departmentId) {

        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            Subject subject = SecurityUtils.getSubject();
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId=null;
	        try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId=admin.getId();
			}
	        
            List<Map<String,Object>> detailList = goodsOrderDetailService.listGoodsOrderDetail(trueName, keyword, 
            		orderStatus, payStatus, goodsCategory, startTime, endTime, (page - 1) * limit, limit
            		,adminId,departmentId);
            Integer count = goodsOrderDetailService.countGoodsOrderDetail(trueName, keyword, orderStatus, payStatus
            		, goodsCategory, startTime, endTime,adminId,departmentId);

            for(Map<String,Object> map : detailList) {
                BigDecimal totalAmount = BigDecimal.ZERO;
                BigDecimal balancePayMoney = BigDecimal.ZERO;
                BigDecimal cashPayMoney = BigDecimal.ZERO;
                BigDecimal creditPayMoney = BigDecimal.ZERO;
                BigDecimal hongbaoMoney = BigDecimal.ZERO;

                balancePayMoney = (BigDecimal)map.get("balance_pay_money");
                cashPayMoney = (BigDecimal)map.get("cash_pay_money");
                creditPayMoney = (BigDecimal)map.get("credit_pay_money");
                hongbaoMoney = (BigDecimal)map.get("hongbao_money");

                map.put("total_amount", totalAmount);
                if(balancePayMoney != null || cashPayMoney != null || creditPayMoney != null || hongbaoMoney != null) {
                    //合计金额=余额支付+现金支付+授信支付+红包支付
                    totalAmount = totalAmount.add(balancePayMoney).add(cashPayMoney).add(creditPayMoney).add(hongbaoMoney);
                    map.put("total_amount", totalAmount);
                }
            }

            int pages = calcPage(count, limit);

            model.addAttribute("trueName", trueName);
            model.addAttribute("keyword", keyword);
            model.addAttribute("orderStatus", orderStatus);
            model.addAttribute("payStatus", payStatus);
            model.addAttribute("goodsCategory", goodsCategory);
            model.addAttribute("startTime",
                    startTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(startTime) : null);
            model.addAttribute("endTime", endTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(endTime) : null);
            model.addAttribute("pages", pages);
            model.addAttribute("list", detailList);
            model.addAttribute("page", page);
            model.addAttribute("departmentId", departmentId);
			model.addAttribute("departments", departmentService.getShowDepartments(admin.getDepartmentId()));

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }

        return "shop/orderDetailList";
    }

	@RequestMapping(value = "/listGoodsByAjax")
	@ResponseBody
	public JSONObject listGoodsByAjax(Model model, HttpSession session, HttpServletResponse response, 
									 @RequestParam(required = false) String keyword,
									 @RequestParam(required = false) Integer status,
									 @RequestParam(required = false) Integer page,
									 @RequestParam(required = false) String desc) {
		JSONObject result = new JSONObject();
		result.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("shop:goodsList");
			}catch (Exception e) {
				result.put(ConstantsAdmin.MESSAGE, "您没有权限查看");
				return result;
			}
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			if (null == desc || "".equals(desc)) {
				desc = "desc";
			}
			List<Goods> list = goodsService.listGoods((page - 1) * limit, limit, keyword, null);
			// 统计总记录数
			int count = goodsService.countGoods(keyword, null);
			int pages = calcPage(count, limit);
			result.put("pages", pages);
			result.put("list", list);
			result.put("page", page);
			result.put("keyword", keyword);
			result.put("desc", desc);
			result.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.put(ConstantsAdmin.MESSAGE, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/getOneGoodsByAjax")
	@ResponseBody
	public JSONObject getOneGoodsByAjax(Model model, HttpSession session, HttpServletResponse response,Integer id) {
		JSONObject result = new JSONObject();
		result.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("shop:goodsEdit");
			}catch (Exception e) {
				result.put(ConstantsAdmin.MESSAGE, "您没有权限查看");
				return result;
			}
			String pathPrefix = ClientConstants.ALIBABA_PATH + "upload/";
			Goods goods = goodsService.queryGoodsDetailById(id);
			if (goods == null) {
				result.put(ConstantsAdmin.MESSAGE, "商品不存在");
			}
			if (goods.getSmallPicPath() != null) {
				goods.setSmallPicPath(pathPrefix + goods.getSmallPicPath());
			}
			if (goods.getActivityPicPath() != null) {
				goods.setActivityPicPath(pathPrefix + goods.getActivityPicPath());
			}
			result.put("data", goods);
			result.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.put(ConstantsAdmin.MESSAGE, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/carriageList", method = RequestMethod.GET)
    public String carriageList(Model model,HttpSession session, String orderNo, String skuCode, Integer orderStatus,
            Date payStartTime, Date payEndTime, Integer page) {

        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            Subject subject = SecurityUtils.getSubject();
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId=null;
//            try {
//                subject.checkPermission("user:seealluser");
//            } catch (Exception e) {
//                adminId=admin.getId();
//            }
            
            List<Map<String,Object>> list = goodsOrderService.listCarriage(orderNo, skuCode, orderStatus, payStartTime, payEndTime, (page - 1) * limit, limit);
            int count = goodsOrderService.countCarriage(orderNo, skuCode, orderStatus, payStartTime, payEndTime);
            int pages = calcPage(count, limit);

            model.addAttribute("orderNo", orderNo);
            model.addAttribute("skuCode", skuCode);
            model.addAttribute("orderStatus", orderStatus);
            model.addAttribute("payStartTime", payStartTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(payStartTime) : null);
            model.addAttribute("payEndTime", payEndTime != null ? new SimpleDateFormat("yyyy-MM-dd").format(payEndTime) : null);
            model.addAttribute("list", list);
            model.addAttribute("pages", pages);
            model.addAttribute("page", page);
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }

            return "shop/carriageList";
        }
	
	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
    public String listCategory(Model model,HttpSession session ,String categoryName, Integer page) {
        try {
			int limit = 10;
			if (page == null) {
			    page = 1;
			}
			Subject subject = SecurityUtils.getSubject();
			try {
			    subject.checkPermission("shop:listCategory");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
			return "error";
			}
			
			List<GoodsCategory> list = goodsCategoryService.listCategory(categoryName, (page - 1) * limit, limit);
			int count = goodsCategoryService.countCategory(categoryName);
			int pages = calcPage(count, limit);
			model.addAttribute("categoryName", categoryName);
			model.addAttribute("list", list);
			model.addAttribute("pages", pages);
			model.addAttribute("page", page);
		} catch (Exception e) {
		    logger.error(e);
		    e.printStackTrace();
		}

        return "shop/categoryList";
	}
	
	@RequestMapping(value = "/addCategory",method = RequestMethod.GET)
	public String addCategory(Model model) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("shop:addCategory");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限添加");
		return "error";
		}
		
		return "shop/addCategory";
	}
	
	@RequestMapping(value = "/addCategory",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addCategory(Model model, GoodsCategory category) {
		JSONObject result = new JSONObject();
		try {
			goodsCategoryService.saveCategory(category);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
			return result;
		}
		result.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
		return result;
	}
	
	/**
	 * 编辑商品分类
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editCategory",method = RequestMethod.GET)
	public String editCategory(Model model, Integer categoryId) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("shop:editCategory");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限修改");
		return "error";
		}
		GoodsCategory category = goodsCategoryService.getMapper().selectByPrimaryKey(categoryId);
		model.addAttribute("category", category);
		return "shop/editCategory";
	}
	
	@RequestMapping(value = "/editCategory",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject editCategory(Model model, GoodsCategory category) {
		JSONObject result = new JSONObject();
		try {
			goodsCategoryService.updateCategory(category);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
			return result;
		}
		result.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
		return result;
	}
}
