package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.Icon;
import com.goochou.p2b.model.IconGroup;

public interface IconGroupContactService {

	/**
	 * 根据组ID查询
	 * @param groupId
	 * @author 刘源
	 * @date 2015年12月29日
	 * @parameter
	 * @return
	 */
	public List<Map<String, Object>> queryByGroupId(Integer groupId);

	/**
	 * 根据组ID查询
	 * @param groupId
	 * @author 刘源
	 * @date 2015年12月29日
	 * @parameter
	 * @return
	 */
	public List<Map<String, Object>> queryGroupIcons(Integer groupId);

	/**
	 * 删除选定icon
	 * @param icons
	 * @author 刘源
	 * @param uploads 
	 * @param uploads2 
	 * @date 2016年1月4日
	 * @parameter
	 * @return
	 */
	public void deleteIcons(Integer[] links, Integer[] icons, Integer[] uploads);

	/**
	 * 删除校验
	 * @param ids
	 * @author 刘源
	 * @date 2016年1月4日
	 * @parameter
	 * @return
	 */
	public Integer checkDelete(Integer[] ids);

}
