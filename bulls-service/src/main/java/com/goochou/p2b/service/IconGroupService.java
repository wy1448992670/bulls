package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.Icon;
import com.goochou.p2b.model.IconGroup;

public interface IconGroupService {

	/**
	 * 根据组ID查询
	 * @param id
	 * @author 刘源
	 * @date 2015年12月29日
	 * @parameter
	 * @return
	 */
	public IconGroup queryByGroupId(Integer id);

}
