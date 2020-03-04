package com.goochou.p2b.service;

import com.goochou.p2b.model.Upload;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadService {
    /**
     * @param file
     * @param type   0管理用户头像1项目图片2企业图片3担保机构图片4身份证照片5banner6用户反馈7皮肤图片8活动图片9icon图片
     * @param userId 当前操作用户ID
     * @return Map key:status success 或者 error message 错误的返回信息 path 如果成功,返回图片的路径
     */
    public Map<String, Object> save(MultipartFile file, Integer type, Integer userId);

    public Upload saveWithoutFile(String fileName, String filePath, Integer userId);

    public Upload get(Integer id);

    public Boolean updateAvatarUserId(Integer id, Integer uploadId);

    /**
     * @param uploadId
     * @param userId
     */
    public Boolean updateSkinUserId(Integer uploadId, Integer userId);
    
    public Map<String, Object> saveFile(MultipartFile file, Integer type, Integer userId);
}
