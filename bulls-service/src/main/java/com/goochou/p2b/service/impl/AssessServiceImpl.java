/**   
* @Title: AssessServiceImpl.java 
* @Package com.goochou.p2b.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zj
* @date 2019-06-03 16:18 
* @version V1.0   
*/
package com.goochou.p2b.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.dao.GoodsPictureMapper;
import com.goochou.p2b.model.GoodsPicture;
import com.goochou.p2b.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.dao.AssessImgsMapper;
import com.goochou.p2b.dao.AssessMapper;
import com.goochou.p2b.model.Assess;
import com.goochou.p2b.model.AssessImgs;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.service.AssessService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.GoodsService;
import com.goochou.p2b.service.UploadService;

/**
 * 商品评价实现类
 * 
 * @ClassName: AssessServiceImpl
 * @author zj
 * @date 2019-06-03 16:18
 */
@Service
public class AssessServiceImpl implements AssessService {
	@Resource
	private GoodsOrderService goodsOrderService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private AssessMapper assessMapper;
	@Autowired
	AssessImgsMapper assessImgsMapper;
	@Resource
	private UploadService uploadService;

	@Override
	public Map<String, Object> saveImages(AssessImgs picture, MultipartFile file, Integer userId) throws Exception {
		Map<String, Object> map = uploadService.save(file, 12, userId);
		if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
			picture.setUploadId((Integer) (map.get(ConstantsAdmin.ID)));
			int count = assessImgsMapper.insertSelective(picture);
			if (count == 1) {
				// 插入成功之后设置图片路径
				picture.setPicturePath((String) map.get(ConstantsAdmin.PATH));
				map.put("object", picture);
			}
		}
		return map;
	}



	@Override
	public void addComment(Integer goodsId, Integer userId, String content, String picture,String dateTime) throws Exception {

		Assess ass = new Assess();
		ass.setContent(content);

		if(StringUtils.isNotBlank(dateTime)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ass.setCreateDate(simpleDateFormat.parse(dateTime));
		}else{
			ass.setCreateDate(new Date());
		}

		ass.setGoodsId(goodsId);
		ass.setIsAnonymous(1);
		ass.setIsTop(0);//是否置顶 0 不  1是
		ass.setUserId(userId);
		ass.setOrderId(0);
		ass.setType(0);//记录类型 0评价  1回复
		ass.setState(0);//状态0未删除  1  已删除
		if(assessMapper.insert(ass) != 1) {
			throw new Exception("新增评论失败");
		}

		try {
			if (StringUtils.isNotBlank(picture)) {
				String[] ps = picture.split( ",");
				if (ps != null && ps.length > 0) {
					for (int i = 0; i < ps.length; i++) {
						AssessImgs p = assessImgsMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
						if (null != p) {
							p.setAssessId(ass.getId());
							assessImgsMapper.updateByPrimaryKeySelective(p);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("上传评论图片异常");
		}
	}

	@Override
	public void addAssess(String orderNo, Integer goodsId, Integer userId, String content, Integer isAnonymous,MultipartFile[] files) throws Exception {

        //订单状态 0未支付 1支付中 2已支付 3拣货中 4已发货 5订单取消 6订单退款 7交易完成 
        GoodsOrder order = goodsOrderService.findByOrderNum(orderNo);
        if(order == null) {
        	throw new Exception("订单有误");
        }
        if(content != null &&  content.length() > 120) {//字数限制120字
        	throw new Exception("字数限制120字");
        }
        List<GoodsOrderDetail> detail = order.getGoodsOrderDetail();
        if(order == null || (detail == null || detail.size() == 0)) {
        	throw new Exception("订单有误");
        }
        if(order.getState() != 7) {
        	throw new Exception("订单未完成，不能评价");
        }
        if(order.getUserId().intValue() != userId) {
        	throw new Exception("订单归属有误");
        }
        //查询订单是否有该商品
        int count = 0;
        for (GoodsOrderDetail goodsOrderDetail : detail) {
        	if(goodsOrderDetail.getGoodsId().equals(goodsId)) {
        		count++;
        	} 
		}
        if(count == 0) {
        	throw new Exception("订单商品有误");
        }
        
        //查询用户是否有评论过该商品
        List<Assess> list = assessMapper.getAssessByGoodsIdAndUserId(goodsId, userId, order.getId());
        if(list!= null && list.size() > 0 ) {
        	throw new Exception("您已评论过该商品了");
        }
        
        Assess ass = new Assess();
        ass.setContent(content);
        ass.setCreateDate(new Date());
        ass.setGoodsId(goodsId);
        ass.setIsAnonymous(isAnonymous);
        ass.setIsTop(0);//是否置顶 0 不  1是
        ass.setUserId(userId);
        ass.setOrderId(order.getId());
        ass.setType(0);//记录类型 0评价  1回复
        ass.setState(0);//状态0未删除  1  已删除
        if(assessMapper.insert(ass) != 1) {
        	throw new Exception("新增评论失败");
        }
        
        try {
	        if(files!= null && files.length > 0) {
	        	if(files.length > 3) {
	        		throw new Exception("评论图片最多添加三张");
	        	}
	        	for (int i = 0; i < files.length; i++) {
	        		 Map<String, Object> updateResult = uploadService.save(files[i], 15, userId);
	        		 
	                 String status = (String) updateResult.get("status");
	                 if ("error".equals(status)) {
	                     String message = (String) updateResult.get("message");
	                   	 throw new Exception(message);
	                 }
	                 
	                 AssessImgs img = new AssessImgs();
	                 img.setAssessId(ass.getId());
	                 img.setCreateDate(new Date());
	                 img.setUploadId(Integer.parseInt(updateResult.get("id")+""));
	                 assessImgsMapper.insert(img);
				}
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new Exception("上传评论图片异常");
		}
	}
	@Override
	public List<Map<String, Object>> listAssessByPage(Integer limitStart, Integer limitEnd, String keyword,
			Integer replyStatus) {
		return getMapper().listAssessByPage(limitStart, limitEnd, keyword, replyStatus);
	}

	@Override
	public int countAssess(String keyword, Integer replyStatus) {
		return getMapper().countAssess(keyword, replyStatus);
	}

	@Override
	public List<Map<String, Object>> listOneAssessDetail(Integer goodsId, Integer userId) {
		
		List<Map<String, Object>> assessList=getMapper().listOneAssessDetail(goodsId, userId);
		for (Iterator iterator = assessList.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			//每个评价对应的图片
			List<AssessImgs> path = assessImgsMapper.listAssessImgPath((Integer)map.get("id"));
			map.put("imgPath", path);
		}
		return assessList;
	}

	@Override
	public  void addReply(Assess assess) {
		getMapper().insert(assess);
	}	

	@Override
	public Assess getTopAssessByGoodsId(Integer goodsId) {
		return assessMapper.getTopAssessByGoodsId(goodsId);
	}
	
	@Override
	public List<Assess> getAssessByGoodsIdAndUserId(Integer goodsId, Integer userId, Integer orderId){
		return assessMapper.getAssessByGoodsIdAndUserId(goodsId,userId, orderId);
	}

	@Override
	public void updateAssessToTop(Integer id, Integer isTop) {
		Assess assess = getMapper().selectByPrimaryKey(id);
		assess.setIsTop(isTop);
		assess.setTopDate(new Date());
		getMapper().updateByPrimaryKeySelective(assess);
	}
	
	@Override
	public void delAssess(Integer id,Integer state) {
		Assess assess = getMapper().selectByPrimaryKey(id);
		assess.setState(state);
		assess.setUpdateDate(new Date());
		getMapper().updateByPrimaryKeySelective(assess);
	}

	@Override
	public List<Assess> getAssessByGoodsIdAndOrderId(Integer goodsId, Integer orderId) {
		return assessMapper.getAssessByGoodsIdAndOrderId(goodsId,orderId);
	}
	@Override
	public List<Assess> getAssessByGoodsId(Integer goodsId, Integer limitStart,  Integer limitEnd) {
		return assessMapper.getAssessByGoodsId(goodsId, limitStart, limitEnd);
	}

	@Override
	public AssessMapper getMapper() {
		return assessMapper;
	}

	@Override
	public int countAssessByGoodsId(Integer goodsId) {
		return assessMapper.countGoodsAssess(goodsId);
	}


	@Override
	public List<Assess> getSelfAssessByGoodsId(Integer goodsId, Integer limitStart,  Integer limitEnd) {
		return assessMapper.listSelfGoodsAssessPage(goodsId, limitStart, limitEnd);
	}
	@Override
	public int countSelfAssessByGoodsId(Integer goodsId) {
		return assessMapper.countSelfGoodsAssess(goodsId);
	}
	
}
