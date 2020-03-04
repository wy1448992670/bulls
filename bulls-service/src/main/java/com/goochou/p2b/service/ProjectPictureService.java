package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.model.ProjectPicture;

public interface ProjectPictureService {

    public Map<String, Object> save(ProjectPicture picture, MultipartFile file, Integer userId) throws Exception;

    /**
     * 删除图片同时删除硬盘上图片
     * 
     * @param id
     */
    public void delete(Integer id) throws Exception;

	public void addProjectPicture(ProjectPicture pic);

	/** 
	 * 复制project 图片信息
	* @Title: copyPicture 
	* @param projectPictures
	* @return List<ProjectPicture>
	* @author zj
	* @date 2019-06-13 17:24
	*/ 
	List<ProjectPicture> doCopyPicture(List<ProjectPicture> projectPictures);
	
	@Deprecated
	void batchRenbaoCover() throws Exception;

}
