package com.goochou.p2b.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Assess;
import com.goochou.p2b.model.GoodsPicture;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsProperty;
import com.goochou.p2b.model.goods.GoodsPropertyValue;
import com.goochou.p2b.service.AssessService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.GoodsPropertyValueService;
import com.goochou.p2b.service.GoodsService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.CommonUtils;


@Controller
@RequestMapping(value = "/assess")
@Api(value = "评论")
public class AssessController extends BaseController{
	private static final Logger logger = Logger.getLogger(AssessController.class);
	
	
	@Resource
	private GoodsOrderService goodsOrderService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private AssessService assessService;
	@Resource
	private UploadService uploadService;
    @Autowired
    private GoodsPropertyValueService goodsPropertyValueService;
    
	/**
	 * @date 2019年6月5日
	 * @author wangyun
	 * @time 上午11:19:50
	 * @Description 添加评论
	 * 
	 * @param session
	 * @param request
	 * @param token
	 * @param client
	 * @param orderNo
	 * @param goodsId
	 * @param content
	 * @param isAnonymous
	 * @return
	 */
	@RequestMapping(value = "/addAssess", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加评论")
	public AppResult addAssess(HttpSession session, HttpServletRequest request,
			@ApiParam("用户token") @RequestParam String token,
			@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
			@ApiParam("版本号") @RequestParam String appVersion,
			@ApiParam("订单号") @RequestParam String orderNo,
			@ApiParam("商品Id") @RequestParam Integer goodsId,
			@ApiParam("评论内容") @RequestParam String content,
			@ApiParam("是否匿名") @RequestParam(required = false) Integer isAnonymous,
			@ApiParam("评论图片") @RequestParam(value = "file", required = false) MultipartFile[] files
			) {
		try {
			User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
           
            if(user.getIsForbidComment() != null && user.getIsForbidComment() == 1) {
            	 return new AppResult(FAILED, "用户被禁止评论");
            }
            if(isAnonymous == null) {
            	isAnonymous = 0;//不填默认不匿名
            }
            
            //添加评论
            assessService.addAssess(orderNo, goodsId, user.getId(), content, isAnonymous, files);
            
            return new AppResult(SUCCESS, "评论成功");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/getGoodsAssessList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "评论列表")
	public AppResult getGoodsAssessList(HttpSession session, HttpServletRequest request, 
			@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
			@ApiParam("商品Id") @RequestParam Integer goodsId,
			@ApiParam("当前页号") @RequestParam Integer page ) {
		try {
			int limit = 6;
			if (page == null) {
				page = 1;
			}
			Map<String, Object> resultMap = new HashMap<>();
			// 获取goods评论列表(带回复,层级显示)
			List<Assess> assess = assessService.getAssessByGoodsId(goodsId, (page - 1) * limit, limit);
			Integer count = assessService.getMapper().countGoodsAssess(goodsId);
			int pages = 1;
			if (limit != 0) {
				pages = calcPage(count, limit);
			}
			
			List<Map<String, Object>> assList = new ArrayList<>();
			Map<String, Object> assMap = null;
			for (Assess ass : assess) {
				//获取每一个评论用户头像框和用户名
				assMap = new HashMap<String, Object>();
				User user = userService.get(ass.getUserId());
				// 头像
				if (user!=null && null != user.getAvatarId()) {
					Upload upload = uploadService.get(user.getAvatarId());
					assMap.put("userIcon", ClientConstants.ALIBABA_PATH + "upload/" + upload.getPath());
				} else {
					assMap.put("userIcon", ClientConstants.ALIBABA_PATH + "upload/login.png");
				}
				
				//昵称
				if(ass.getIsAnonymous() == 1) {
					assMap.put("userName", "匿名");
				} else {
					assMap.put("userName", user.getUsername());
				}
					
				assMap.put("assess", ass);
				assList.add(assMap);
			}
			
			
			// 获取商品信息(展示在顶部)
			Map<String, Object> goodsInfo = new HashMap<>();
			Goods goods = goodsService.getGood(goodsId);
			if(goods != null) {
				List<String> smallPics = new ArrayList<String>();//小图列表
				List<GoodsPicture> picList = goods.getGoodsPictures();

				Iterator<GoodsPicture> iterator = picList.iterator();
		        while(iterator.hasNext()){
		        	GoodsPicture pic = iterator.next();
		        	if(pic.getType().intValue() == 14) {//小图
						smallPics.add(pic.getUpload().getCdnPath());
						iterator.remove();//原来列表移除小图
					} 
		        }
				//如有多张小图只显示一张
				String smallPic = (smallPics!=null && smallPics.size() > 0) ?  smallPics.get(0) : null;
				goodsInfo.put("smallPic", smallPic);
				
				//查询产品属性
				String propertyValue = "";
				List<GoodsProperty> goodsPropertises = new ArrayList<>();
				if(goods.getGoodsCategory() != null ) {
					goodsPropertises = goods.getGoodsCategory().getGoodsProperties();
					String name = "规格";

		            if(goodsPropertises != null  && goodsPropertises.size() >  0) {
		            	for (GoodsProperty goodsProperty : goodsPropertises) {
		            		if(name.equals(goodsProperty.getPropertyName())) {
		            			GoodsPropertyValue goodsPropertyValue = goodsPropertyValueService.getValueByGoodsIdAndPropertyId(goodsId, goodsProperty.getId());
		            			if(goodsPropertyValue != null) {
		            				propertyValue = goodsPropertyValue.getPropertyValue();
		            			}
		            			break;
		            		}
						}
		            }
				}
				goodsInfo.put("propertyValue", propertyValue);
				goodsInfo.put("goodsName", goods.getGoodsName());
				resultMap.put("goodsInfo", goodsInfo);
			} else {
				resultMap.put("goodsInfo", null);
			}
			
			resultMap.put("pages", pages);
			resultMap.put("count", count);
			resultMap.put("page", page);

			resultMap.put("assess", assList);
			return  new AppResult(SUCCESS, resultMap);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, e.getMessage());
		}
	}
	
}
