/**   
* @Title: GoodsBrandService.java 
* @Package com.goochou.p2b.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zj
* @date 2019-05-24 15:51 
* @version V1.0   
*/
package com.goochou.p2b.service;

import com.goochou.p2b.dao.GoodsBrandMapper;

/**
 * 商品品牌服务
 * 
 * @ClassName: GoodsBrandService
 * @author zj
 * @date 2019-05-24 15:51
 */
public interface GoodsBrandService {

	/**
	 * 获取dao层方法
	 * 
	 * @Title: getGoodsBrandMapper
	 * @return GoodsBrandMapper
	 * @author zj
	 * @date 2019-05-24 15:54
	 */
	GoodsBrandMapper getGoodsBrandMapper();

}
