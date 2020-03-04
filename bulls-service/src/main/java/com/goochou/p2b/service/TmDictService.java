package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.TmDict;

public interface TmDictService {

	/**
	 * 查询字典列表
	 * 
	 * @author sxy
	 * @param keyword
	 * @param start
	 * @param limit
	 * @param name
	 * @param key
	 * @return
	 */
	List<TmDict> queryDictList(String keyword, Integer start, Integer limit, String name, String key);

	int queryDictListCount(String keyword, String name, String key);

	int save(TmDict tmDict);

	int updateDict(TmDict tmDict);

	TmDict get(Integer id);

	void doFulshCache();

	/**
	 * 获取同类字典
	 * 
	 * @Title: listTmDict
	 * @param key
	 * @return List<TmDict>
	 * @author zj
	 * @date 2019-06-27 15:43
	 */
	List<TmDict> listTmDict(String key);

}
