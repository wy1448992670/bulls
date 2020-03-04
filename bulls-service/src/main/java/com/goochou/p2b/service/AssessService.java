package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.AssessImgs;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.dao.AssessMapper;
import com.goochou.p2b.model.Assess;

/**
 * 商品评价
 * 
 * @ClassName: AssessService
 * @author zj
 * @date 2019-06-03 16:17
 */
public interface AssessService {
	AssessMapper getMapper();


	Map<String, Object> saveImages(AssessImgs picture, MultipartFile file, Integer userId) throws Exception;


	void addComment(Integer goodsId, Integer userId, String content, String picture,String dateTime) throws Exception;

	/**
	 * @date 2019年6月6日
	 * @author wangyun
	 * @time 下午4:26:52
	 * @Description 添加评论
	 * 
	 * @param orderNo
	 * @param goodsId
	 * @param userId
	 * @param comment
	 * @param isAnonymous
	 * @param files
	 * @throws Exception
	 */
	void addAssess(String orderNo, Integer goodsId, Integer userId, String comment,Integer isAnonymous,MultipartFile[] files) throws Exception;

	/**
	 * 获取所有评价
	 * 
	 * @Title: listAssess
	 * @return List<Assess>
	 * @author zj
	 * @date 2019-06-03 17:55
	 */
	public List<Map<String, Object>> listAssessByPage(Integer limitStart, Integer limitEnd, String keyword, Integer replyStatus);

	/**
	 * 统计分页记录总数
	 * 
	 * @Title: countAssess
	 * @param goodsName
	 * @param trueName
	 * @return int
	 * @author zj
	 * @date 2019-06-03 18:15
	 */
	public int countAssess(String keyword, Integer replyStatus);

	/**
	 * 获取该商品下此用户的所有评论
	 * 
	 * @Title: listOneAssessDetail
	 * @param goodsId
	 * @param userId
	 * @return List<Map<String,Object>>
	 * @author zj
	 * @date 2019-06-04 14:26
	 */
	public List<Map<String, Object>> listOneAssessDetail(Integer goodsId, Integer userId);

	/** 
	*卖家回复
	* @Title: addReply 
	* @param assess void
	* @author zj
	* @date 2019-06-04 17:23
	*/ 
	public void addReply(Assess assess);
	
	public Assess getTopAssessByGoodsId(Integer goodsId);
	
	
	/**
	 * 指顶评价
	* @Title: updateAssessToTop 
	* @param id  评价主键
	* @param isTop  置顶标识  0  未置顶  1  置顶
	* @author zj
	* @date 2019-06-05 14:57
	 */
	public void updateAssessToTop(Integer id,Integer isTop) ;

	/** 
	 * 删除评价（逻辑删除）
	* @Title: delAssess 
	* @param id void
	* @author zj
	* @date 2019-06-05 16:20
	*/ 
	void delAssess(Integer id,Integer state);
	
	/**
	 * @date 2019年6月6日
	 * @author wangyun
	 * @time 上午10:22:49
	 * @Description 查询订单是否已被用户评论过
	 * 
	 * @param goodsId
	 * @param userId
	 * @return
	 */
	List<Assess> getAssessByGoodsIdAndUserId(Integer goodsId, Integer userId, Integer orderId);
	
	/**
	 * @date 2019年6月6日
	 * @author wangyun
	 * @time 上午11:32:58
	 * @Description 查询用户订单详情中的评价
	 * 
	 * @param goodsId
	 * @param orderId
	 * @return
	 */
	List<Assess> getAssessByGoodsIdAndOrderId(Integer goodsId, Integer orderId);

	/**
	 * @date 2019年6月6日
	 * @author wangyun
	 * @time 下午5:40:24
	 * @Description 查询商品下所有评论
	 * 
	 * @param goodsId
	 * @return
	 */
	List<Assess> getAssessByGoodsId(Integer goodsId, Integer limitStart, Integer limitEnd);
	
	int countAssessByGoodsId(Integer goodsId);


	/**
	 * 获取后台评价列表
	 * @param goodsId
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	List<Assess> getSelfAssessByGoodsId(Integer goodsId, Integer limitStart,  Integer limitEnd);

	/**
	 * 获取后台评价总数
	 * @param goodsId
	 * @return
	 */
	int countSelfAssessByGoodsId(Integer goodsId);
	
}
