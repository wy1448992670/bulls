package com.goochou.p2b.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.dao.GoodsPictureMapper;
import com.goochou.p2b.dao.UploadMapper;
import com.goochou.p2b.model.GoodsPicture;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.service.GoodsPictureService;
import com.goochou.p2b.service.UploadService;

@Service
public class GoodsPictureServiceImpl implements GoodsPictureService {
	@Autowired
	UploadService uploadService;
	@Autowired
	GoodsPictureMapper goodsPictureMapper;
	@Autowired
	UploadMapper uploadMapper;

	@Override
	public Map<String, Object> save(GoodsPicture picture, MultipartFile file, Integer userId) throws Exception {
		Map<String, Object> map = uploadService.save(file, picture.getType(), userId);
		if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
			picture.setUploadId((Integer) (map.get(ConstantsAdmin.ID)));
			int count = goodsPictureMapper.insert(picture);
			if (count == 1) {
				// 插入成功之后设置图片路径
				picture.setPicturePath((String) map.get(ConstantsAdmin.PATH));
				map.put("object", picture);
			}
		}
		return map;
	}

	@Override
    public void delete(Integer id) throws Exception {
        GoodsPicture p = goodsPictureMapper.selectByPrimaryKey(id);
        if (p != null) {
            Upload upload = uploadMapper.selectByPrimaryKey(p.getUploadId());
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
                // 删除商品图片表记录
                goodsPictureMapper.deleteByPrimaryKey(id);
                // 删除上传表记录
                uploadMapper.deleteByPrimaryKey(upload.getId());
            }
        }
    }

}
