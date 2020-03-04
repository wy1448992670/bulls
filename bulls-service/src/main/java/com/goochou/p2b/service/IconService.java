package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.Icon;
import com.goochou.p2b.model.IconGroup;
import com.goochou.p2b.model.Upload;

public interface IconService {

    /**
     * 获取ICONS
     * @author ydp
     * @param type
     * @param version
     * @return
     */
    List<Icon> getUsingIcons(Integer type, String version);

    /**
     * 分页查询icon以及对应关系列表
     * @author sxy
     * @param start
     * @param limit
     * @return
     */
    public List<Map<String, Object>> list(Integer start, Integer limit);

    public void saveWithPic(Integer linkId, Icon icon, Integer picture);

    public List<IconGroup> query(Integer type, Integer status, String title, String version, Integer start, Integer limit);

    public Integer queryCount(Integer type, Integer status, String title, String version);

    /**
     * 根据icon组ID查询所有的icon图片路径和icon标题
     * 
     * @param groupId
     * @return
     */
    List<Map<String, String>> getPicPathByGroupId(Integer groupId);

    public void updateGroupStatus(Integer groupId, Integer status);

    /**
     * 新增或更新icon组信息
     * @param groupName
     * @param type
     * @param home
     * @param me
     * @param version
     * @param status
     * @author 刘源
     * @param id 
     * @param meIcon 
     * @param me 
     * @param meIcon2 
     * @param meIcon2 
     * @date 2015年12月29日
     * @return
     */
	public void saveIconGroup(Integer id, String groupName, Integer type, Integer[] homeId, Integer[] home,Integer[] homeIcon, Integer[] meId, Integer[] me, Integer[] meIcon, String version, Integer status);

	/**
	 * 根据ID查询
	 * @param id
	 * @author 刘源
	 * @date 2015年12月31日
	 * @parameter
	 * @return
	 */
	public Map<String, Object> queryById(Integer id);

	/**
	 * 查询icon列表总数
	 * @author sxy
	 * @return
	 */
	public Integer queryListCount();

}
