package com.goochou.p2b.app.controller;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAddress;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.vo.ShoppingCartPickedVO;
import com.goochou.p2b.model.vo.ShoppingCartVO;
import com.goochou.p2b.service.GoodsService;
import com.goochou.p2b.service.GoodsShoppingCartService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.UserAddressService;
import com.goochou.p2b.utils.BigDecimalUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 购物车
 *
 * @ClassName: ShopingCartController
 * @author zj
 * @date 2019-05-27 16:43
 */
@Controller
@RequestMapping(value = "shoppingcart")
@Api(value = "购物车")
public class ShopingCartController extends BaseController {

	private static final String CART_ADD = "cart_add";
	private static final String CART_DEL = "cart_del";
	private static final String CART_CUT = "cart_cut"; // 操作购物车（选中，加减购物车数量）
	private static final String CART_SEL = "cart_sel"; // 选中/取消选中状态

	private static final Logger logger = Logger.getLogger(ShopingCartController.class);
	@Autowired
	UserAddressService userAddressService;

	@Resource
	private GoodsService goodsService;

	@Autowired
	HongbaoService hongbaoService;

	@Resource
	private GoodsShoppingCartService goodsShoppingCartService;

	/**
	 *
	 * 购物车结算
	 *
	 * @Title: shoppingCartSettle
	 * @param request
	 * @param appVersion
	 * @param client
	 * @param token
	 * @param userId
	 * @return AppResult
	 * @author zj
	 * @date 2019-05-28 15:24
	 */
	@RequestMapping(value = "/shoppingCartSettle" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "购物车结算")
	public AppResult shoppingCartSettle(HttpServletRequest request,
										@ApiParam("app版本号") @RequestParam(required = true) String appVersion,
										@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = true) String client,
										@ApiParam("用户token") @RequestParam(required = true) String token,
										@ApiParam("用户主键") @RequestParam(required = true) Integer userId) {
		try {

			User user = userService.updateCheckLogin(token, 1, getIpAddr(request));
			if (null == user) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			// 用户地址
			List<UserAddress> addressList = userAddressService.listUserAddressesByUserId(userId);
			List<Hongbao> hongbaoList = hongbaoService.listHongbaoByUserId(userId);// 可用红包
			Map<String, Object> returnMap = new HashMap<String, Object>(16);
			returnMap.put("hongbaoList", hongbaoList);
			returnMap.put("addressList", addressList);
			return new AppResult(SUCCESS, returnMap);
		} catch (Exception e) {
			logger.error("shoppingCartSettle====>出错" + e.getMessage(), e);
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

	/**
	 * @description 添加商品到购物车
	 * @author shuys
	 * @date 2019/5/27
	 * @param token
	 * @param appVersion
	 * @param goodsId
	 * @param goodsCount
	 * @return com.goochou.p2b.app.model.AppResult
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "添加商品到购物车")
	public AppResult add(@ApiParam("用户token") @RequestParam String token,
						 @ApiParam("app版本号") @RequestParam String appVersion,
						 @ApiParam("终端来源 IOS,Android,PC,WAP") String client,
						 @ApiParam("商品ID") @RequestParam Integer goodsId,
						 @ApiParam("商品数量") @RequestParam Integer goodsCount) {
		try {
			if (goodsCount <= 0) {
				return new AppResult(FAILED, "添加商品数量不能小于1");
			}
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			this.change(user, CART_ADD, goodsId, null);
			return new AppResult(SUCCESS, "添加成功");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, e.getMessage());
		}
	}

	/**
	 * @description 购物车列表
	 * @author shuys
	 * @date 2019/5/28
	 * @param token
	 * @param appVersion
	 * @return com.goochou.p2b.app.model.AppResult
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "购物车列表")
	public AppResult list(
			@ApiParam("App版本号") @RequestParam String appVersion,
			@ApiParam("终端来源 IOS,Android,PC,WAP") String client,
			@ApiParam("用户token") @RequestParam String token) {
		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			boolean isVIP = user.getLevel() > 0 ? true : false;
			Map<String, Object> map = new HashMap<>();

			List<ShoppingCartVO> list = goodsShoppingCartService.getCacheShoppingCartListByUserId(user.getId());
			List<Integer> goodsIdList = goodsShoppingCartService.getGoodsIdList(list); // 购物车中所有商品ID
            List<Goods> goodsList = goodsService.getGoodsByIds(goodsIdList); // 购物车中所有商品信息
            List<ShoppingCartVO> newList = new ArrayList<ShoppingCartVO>();
			for(ShoppingCartVO shoppingCartVO : list) {
			    int shopCartGoodsId = shoppingCartVO.getGoodsId(); // 购物车中商品ID
			    Goods goods = goodsShoppingCartService.findLocalGoodsById(goodsList, shopCartGoodsId);
                goodsShoppingCartService.updateLocalShoppingCartByGoods(shoppingCartVO, goods, isVIP); //同步商品信息
			    newList.add(shoppingCartVO);
			}
			logger.info("同步商品到购物车中的商品");
			goodsShoppingCartService.updateCacheShoppingCartListByUserId(user.getId(), newList); //更新购物车缓存
			ShoppingCartPickedVO shoppingCartPickedInfo = goodsShoppingCartService.getShoppingCartPickedInfo(isVIP, user.getId()); //购物车选中商品信息

			map.put("userId", user.getId());
			map.put("list", newList);
			map.put("selectedGoodsCount", shoppingCartPickedInfo.getPickedGoodsCount()); // 选中商品数量
			map.put("totalAmout", shoppingCartPickedInfo.getTotalAmout());
			map.put("totalAmoutStr", shoppingCartPickedInfo.getTotalAmoutStr());
			return new AppResult(SUCCESS, map);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

	/**
	 * 删除购物车里的商品
	 *
	 * @Title: delShoppingCartGoods
	 * @return AppResult
	 * @author zj
	 * @date 2019-05-28 12:26
	 */
	@RequestMapping(value = "/delShoppingCartGoods" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "删除购物车里的商品")
	public AppResult delShoppingCartGoods(HttpServletRequest request,
										  @ApiParam("用户token") @RequestParam(required = true) String token,
										  @ApiParam("商品主键数组") @RequestParam(required = true) List<Integer> goodsId,
										  @ApiParam("app版本号") @RequestParam(required = true) String appVersion,
										  @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = true) String client) {
		try {

			User user = userService.updateCheckLogin(token, 1, getIpAddr(request));
			if (null == user) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
//			goodsShoppingCartService.deleteCacheShoppingCartByUserAndGoods(user.getId(), goodsId);
			if (!goodsId.isEmpty()) {
				for (Integer i: goodsId) {
					this.change(user, CART_DEL, i, null);
				}
			}
			return new AppResult(SUCCESS, "删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new AppResult(FAILED, e.getMessage());
		}
	}

	/**
	 * @description 修改购物车商品和勾选商品总金额计算
	 * @author shuys
	 * @date 2019/6/4
	 * @param token
	 * @param appVersion
	 * @return com.goochou.p2b.app.model.AppResult
	 */
	@RequestMapping(value = "updateGoodsOrCalculatedAmount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "修改购物车商品或勾选商品金额计算")
	public AppResult updateGoodsOrCalculatedAmount(
			@ApiParam("用户token") @RequestParam String token,
			@ApiParam("app版本号") @RequestParam String appVersion,
			@ApiParam("终端来源 IOS,Android,PC,WAP") String client,
			@ApiParam("商品ID") @RequestParam Integer goodsId,
			@ApiParam("商品数量") @RequestParam Integer goodsCount,
			@ApiParam("商品是否被选中（0否 1是）") @RequestParam(required = false) Integer isSelected

	) {
		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			String operator = "";
			ShoppingCartVO shoppingCartVO = goodsShoppingCartService.getCacheShoppingCartByUserAndGoods(user.getId(), goodsId);
			if (shoppingCartVO == null) {
				return new AppResult(FAILED, "购物车中不存在此商品");
			}
			Integer curGoodsCount = shoppingCartVO.getGoodsCount();
			Integer curIsSelected = shoppingCartVO.getIsSelected();
			if (goodsCount > curGoodsCount) {
				operator = CART_ADD;
			} else if (goodsCount < curGoodsCount) {
				operator = CART_CUT;
			} else { // 选中时
				operator = CART_SEL;
			}
			if (!isSelected.equals(curIsSelected)) { // 选中/取消选中操作
				operator = CART_SEL;
			}
			// 改变购物车状态
			this.change(user, operator, goodsId, isSelected);
			boolean isVIP = user.getLevel() > 0 ? true : false;
			// 计算购物车选中商品信息
			ShoppingCartPickedVO shoppingCartPickedInfo = goodsShoppingCartService.getShoppingCartPickedInfo(isVIP, user.getId());
			Map<String, Object> result = new HashedMap(16);
			result.put("selectedGoodsCount", shoppingCartPickedInfo.getPickedGoodsCount()); // 选中商品数量
			result.put("totalAmount", shoppingCartPickedInfo.getTotalAmout());
			result.put("totalAmountStr", shoppingCartPickedInfo.getTotalAmoutStr());
			return new AppResult(SUCCESS, "操作成功", result);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, e.getMessage());
		}
	}



	private void change(User user, String operator, Integer goodsId, Integer isSelected) throws Exception {
		if (user == null) {
			throw new Exception(MESSAGE_NO_LOGIN);
		}
		if (goodsId == null) {
			throw new Exception("商品ID不能为空");
		}
		// 当前商品信息
		Goods curGoods = goodsService.getGood(goodsId);

		if (curGoods == null) {
			throw new Exception("商品不存在");
		}

		// 库存不足提示信息
		String insufficientStockMsg = "商品" + curGoods.getGoodsName() + "库存不足";
		Integer userId = user.getId();
		boolean isVIP = user.getLevel() > 0 ? true : false;
		// 用户购物车列表
		List<ShoppingCartVO> shoppingCartList = goodsShoppingCartService.getCacheShoppingCartListByUserId(user.getId());
		// 购物车中当前商品
		ShoppingCartVO curShoppingGoods = goodsShoppingCartService.getCacheShoppingCartByUserAndGoods(userId, goodsId, shoppingCartList);

		switch (operator) {
			case CART_ADD: // 新增购物车/增加商品数量
			case CART_CUT: // 减少商品数量）
			case CART_SEL:
				int goodsCount = curShoppingGoods == null ? 0 : curShoppingGoods.getGoodsCount();
				if (CART_ADD.equals(operator)) {
					goodsCount += 1;
				}
				if (CART_CUT.equals(operator)) {
					goodsCount -= 1;
				}
				if(CART_SEL.equals(operator)) {
					goodsCount = goodsCount;
				}
				if(goodsCount < 1) {
					throw new Exception("商品数量不能减少啦！");
				}

				if (curShoppingGoods == null) { // 新增商品到购物车中
					curShoppingGoods = new ShoppingCartVO();
					// 同步商品信息
					goodsShoppingCartService.updateLocalShoppingCartByGoods(curShoppingGoods, curGoods, isVIP);
					curShoppingGoods.setUserId(userId);
					curShoppingGoods.setIsSelected(0);
					curShoppingGoods.setGoodsCount(goodsCount);
					shoppingCartList.add(curShoppingGoods); // 新增
					// 更新缓存
					goodsShoppingCartService.updateCacheShoppingCartListByUserId(user.getId(), shoppingCartList);
				} else { // 修改购物车中商品信息(加减数量，选中或取消选中)
					List<Integer> goodsIdList = goodsShoppingCartService.getGoodsIdList(shoppingCartList); // 购物车中所有商品ID
					List<Goods> goodsList = goodsService.getGoodsByIds(goodsIdList); // 购物车中所有商品信息
					if (shoppingCartList.size() != goodsList.size()) { // 如果购物车中的商品和系统中的商品不匹配，抛出异常
						throw new Exception("购物车中商品与实际商品不匹配");
					}
					// 操作后的结果
					List<ShoppingCartVO> resultList = new ArrayList<>();
					// 库存不足时，是否操作成功
					boolean isSuccess = true;
					// 操作商品是否存在购物车中
					boolean isExist = false;

					for (ShoppingCartVO vo : shoppingCartList) {
						ShoppingCartVO tempCart = vo;
						int shopCartGoodsId = vo.getGoodsId(); // 购物火车中商品ID
						Goods goods = goodsShoppingCartService.findLocalGoodsById(goodsList, shopCartGoodsId); // 当前商品
						if (goods == null) {
//							// 商品不存在时，自动更新购物车中选中商品为非选中状态
//							if (vo.getIsSelected() == 1) {
//								tempCart.setIsSelected(0);
//							}
							logger.info("商品不存在， goodsId：" + goodsId);
						} else {
							int stock = goods.getStock(); // 商品库存
							int tempIsSelected = tempCart.getIsSelected(); // 购物车中商品选中状态
							if (goodsId.equals(shopCartGoodsId)) {
								isExist = true;
								// 同步当前商品信息（有可能商品信息变动）
								goodsShoppingCartService.updateLocalShoppingCartByGoods(tempCart, goods, isVIP);
								if (stock < 1 || stock < goodsCount) { //  商品库存不足
//									tempCart.setGoodsCount(stock);
									isSuccess = false;
								} else {
									// 更新购物车中商品数量
									tempCart.setGoodsCount(goodsCount);
								}
								// 是否存在 勾选/取消勾选 商品
								if (isSuccess && isSelected != null) {
									tempCart.setIsSelected(isSelected);
								}
								// 如果库存不足，更新选中状态为取消选中
								if (!isSuccess) {
//									if (tempIsSelected == 1) { // 历史是选中
//										tempCart.setIsSelected(0);
//									}
									if (isSelected!= null && isSelected == 0 && tempIsSelected == 1) { // 历史是选中，操作为取消选中，让用户操作成功
										isSuccess = true;
									}
									if (CART_CUT.equals(operator)) {
										// 更新购物车中商品数量为商品最大库存
										tempCart.setGoodsCount(stock);
										isSuccess = true;
									}
//									if (CART_ADD.equals(operator)) {
//
//									}
								}
							}
						}
						resultList.add(tempCart);
					}

					// 更新缓存
					goodsShoppingCartService.updateCacheShoppingCartListByUserId(user.getId(), resultList);
					if (!isExist) {
						throw new Exception("购物车中不存在此商品");
					}
					// 是否库存不足
					if (!isSuccess) {
						throw new Exception(insufficientStockMsg);
					}
				}
				break;
			case CART_DEL: // 删除购物车中商品并更新缓存
				goodsShoppingCartService.deleteCacheShoppingCartByUserAndGoods(user.getId(), goodsId);
				break;
			default:
				throw new Exception("错误的操作符");
		}
	}

}
