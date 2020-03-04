package com.goochou.p2b.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.model.EnterprisePicture;

public interface EnterprisePictureService {

    public Map<String, Object> save(EnterprisePicture picture, MultipartFile file, Integer userId) throws Exception;

    /**
     * 删除企业图片同时删除硬盘上图片
     * 
     * @param id
     */
    public void delete(Integer id) throws Exception;
    
    public Map<String, Object> saveFile(EnterprisePicture picture, MultipartFile file, Integer userId) throws Exception;

}
