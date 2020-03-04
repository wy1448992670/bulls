package com.goochou.p2b.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.model.GoodsPicture;

/**
 * 商品图片关系表
 * 
 * @ClassName GoodsPictureService
 * @这里用一句话描述这个方法的作用
 * @author zj
 * @Date 2019年5月15日 上午11:53:17
 * @version 1.0.0
 */
public interface GoodsPictureService {

	/**
	 * 保存商品图片关系
	 * 
	 * @param picture
	 * @param file
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: zj
	 */
	Map<String, Object> save(GoodsPicture picture, MultipartFile file, Integer userId) throws Exception;

    /**
     * 删除商品图片
     * @author ydp
     * @param id
     * @throws Exception
     */
    void delete(Integer id)throws Exception;

}
