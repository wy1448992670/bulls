package com.goochou.p2b.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.dao.ProjectPictureMapper;
import com.goochou.p2b.dao.UploadMapper;
import com.goochou.p2b.model.ProjectPicture;
import com.goochou.p2b.model.ProjectPictureExample;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.service.ProjectPictureService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.ImageUtil;

@Service
public class ProjectPictureServiceImpl implements ProjectPictureService {
    private static Logger logger = Logger.getLogger(ProjectPictureServiceImpl.class);
    @Resource
    private ProjectPictureMapper projectPictureMapper;
    @Resource
    private UploadService uploadService;
    @Resource
    private UploadMapper uploadMapper;

    @Override
    public Map<String, Object> save(ProjectPicture picture, MultipartFile file, Integer userId) throws Exception {
        logger.info("==========进入 com.goochou.p2b.service.impl.ProjectPictureServiceImpl.save  ======== ");
        Map<String, Object> map = uploadService.save(file, picture.getType(), userId);
        if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
            picture.setUploadId((Integer) (map.get(ConstantsAdmin.ID)));
            int count = projectPictureMapper.insert(picture);
            if (count == 1) {
                // 插入成功之后设置图片路径
                picture.setPicturePath((String) map.get(ConstantsAdmin.PATH));
                map.put("object", picture);
            }
        }
        logger.info("==========结束 com.goochou.p2b.service.impl.ProjectPictureServiceImpl.save  ======== ");
        return map;
    }

    @Override
    public void delete(Integer id) throws Exception {
        ProjectPicture p = projectPictureMapper.selectByPrimaryKey(id);
        if (p != null) {
            Upload upload = uploadMapper.selectByPrimaryKey(p.getUpload().getId());
            if (upload != null) {
                String path = upload.getPath();
                // 获取图片存储路径,支持的图片类型以及最大的图片大小
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("upload.properties");
                Properties properties = new Properties();
                properties.load(inputStream);
                String basePath = properties.getProperty(ConstantsAdmin.BASE_PATH);
                File file = new File(basePath + path);
                if (file.exists()) {
                    file.delete(); // 删除硬盘图片
                }
                // 删除企业图片表记录
                projectPictureMapper.deleteByPrimaryKey(id);
                // 删除上传表记录
                uploadMapper.deleteByPrimaryKey(upload.getId());
            }
        }
    }

	@Override
	public void addProjectPicture(ProjectPicture pic) {
		projectPictureMapper.insert(pic);
	}
	
	
	@Override
	public List<ProjectPicture> doCopyPicture(List<ProjectPicture> projectPictures) {
		List<ProjectPicture> list = new ArrayList<ProjectPicture>();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = projectPictures.iterator(); iterator.hasNext();) {
			ProjectPicture projectPicture = (ProjectPicture) iterator.next();
			ProjectPicture picture = projectPictureMapper.selectByPrimaryKey(projectPicture.getId());
			ProjectPicture pic=new ProjectPicture();
			 BeanUtils.copyProperties(picture, pic);
			pic.setCreateDate(new Date());
			pic.setUploadId(picture.getUpload().getId());
			pic.setId(null);
			pic.setProjectId(null);
			projectPictureMapper.insert(pic);
			list.add(pic);
		}
		return list;
	}
	
	@Deprecated
	@Override
	public void batchRenbaoCover() throws Exception {
        logger.info("==========进入 com.goochou.p2b.service.impl.ProjectPictureServiceImpl.batchRenbaoCover  ======== ");
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("upload.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e) {
			logger.error(e);
		}
		String basePath = p.getProperty(ConstantsAdmin.BASE_PATH);
		String RENBAO_PATH=p.getProperty(ConstantsAdmin.PROJECT_PICTURE_RENBAO_PATH);
		
        ProjectPictureExample example=new ProjectPictureExample();
        example.createCriteria().andTypeEqualTo(1).andProjectIdIsNotNull().andUploadIdIsNotNull();
        List<ProjectPicture> projectPictureList=projectPictureMapper.selectByExample(example);
        for(ProjectPicture projectPicture:projectPictureList) {
        	Upload upload=uploadMapper.selectByPrimaryKey(projectPicture.getUploadId());
        	
        	File baseFile=new File(basePath+upload.getPath());
        	if(!baseFile.exists()) {
        		logger.info(upload.getId()+" "+upload.getPath()+":不存在");
        		continue;
        	}

        	//原图
			BufferedImage baseImg = ImageIO.read(baseFile);
			baseImg = ImageUtil.resize(baseImg, 504, 504);
			//头像
			ImageUtil.save(baseImg,basePath+upload.getPath());
			//中国人民保险 覆盖层
			File templateFile = new File(basePath +RENBAO_PATH+"/template.png"); 
			BufferedImage templateImg = ImageIO.read(templateFile);
			//覆盖
			baseImg = ImageUtil.coverImage(baseImg, templateImg, 0, 0, 396, 54);
			//中国人民保险 水印图
			ImageUtil.save(baseImg,basePath + RENBAO_PATH + "/" + baseFile.getName());
			
        }
        

        logger.info("==========结束 com.goochou.p2b.service.impl.ProjectPictureServiceImpl.batchRenbaoCover  ======== ");
    }
}

