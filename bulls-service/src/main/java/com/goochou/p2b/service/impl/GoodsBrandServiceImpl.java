/**   
* @Title: GoodsBrandServiceImpl.java 
* @Package com.goochou.p2b.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zj
* @date 2019-05-24 15:52 
* @version V1.0   
*/
package com.goochou.p2b.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.GoodsBrandMapper;
import com.goochou.p2b.service.GoodsBrandService;

/**
 * 商品品牌服务实现类
 * 
 * @ClassName: GoodsBrandServiceImpl
 * @author zj
 * @date 2019-05-24 15:52
 */
@Service
public class GoodsBrandServiceImpl implements GoodsBrandService {
	@Autowired
	GoodsBrandMapper goodsBrandMapper;

	@Override
	public GoodsBrandMapper getGoodsBrandMapper() {
		return goodsBrandMapper;
	}
}
