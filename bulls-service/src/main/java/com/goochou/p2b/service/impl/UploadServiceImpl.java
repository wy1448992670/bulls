package com.goochou.p2b.service.impl;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.dao.UploadMapper;
import com.goochou.p2b.dao.UserAdminMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.ImageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class UploadServiceImpl implements UploadService {
	private static Logger logger = Logger.getLogger(UploadServiceImpl.class);
	@Resource
	private UploadMapper uploadMapper;
	@Resource
	private UserAdminMapper userAdminMapper;
	@Resource
	private UserMapper userMapper;

	public static final String MARK_TEXT = "wenteryan";
	public static final String FONT_NAME = "微软雅黑";

	public static final int FONT_SIZE = 120;
	public static final int FONT_STYPE = Font.BOLD;
	public static final Color FONT_COLOR = Color.RED;

	public static final int X = 10;
	public static final int Y = 10;

	public static float ALPHA = 0.3F;

	@Override
	public Map<String, Object> save(MultipartFile file, Integer type, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("==========进入 com.goochou.p2b.service.impl.UploadServiceImpl.save  ======== ");
		logger.info("file:=====> "+file);
		logger.info("type:=====> "+type);
		logger.info("id:=====> "+id);
		try {
			// 获取图片存储路径,支持的图片类型以及最大的图片大小
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("upload.properties");
			Properties p = new Properties();
			try {
				p.load(inputStream);
			} catch (IOException e) {
				logger.error(e);
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "系统错误");
				return map;
			}
			String basePath = p.getProperty(ConstantsAdmin.BASE_PATH);
			String maxPicSize = p.getProperty(ConstantsAdmin.MAX_PICTURE_SIZE); // 最大图片大小
			String maxVideoSize = p.getProperty(ConstantsAdmin.MAX_VIDEO_SIZE); // 最大视频大小
			String picType = p.getProperty(ConstantsAdmin.PICTURE_TYPE); // 支持的图片类型
			String videoType=p.getProperty(ConstantsAdmin.VIDEO_TYPE);//支持的视频类型
			String picPath = ""; // 用户
			if (type == 0 || type == 4) {
				picPath = basePath + p.getProperty(ConstantsAdmin.USER_PICTURE_PATH); // 头像或者身份证照片存储路径
			} else if (type == 1||type==13) { //13 项目小图
				picPath = basePath + p.getProperty(ConstantsAdmin.PROJECT_PICTURE_PATH); // 项目图片存储路径
			} else if (type == 2) {
				picPath = basePath + p.getProperty(ConstantsAdmin.ENTERPRISE_PICTURE_PATH); // 企业图片存储路径
			} else if (type == 3) {
				picPath = basePath + p.getProperty(ConstantsAdmin.GUARANTEE_PICTURE_PATH); // 担保机构图片存储路径
			} else if (type == 5) {
				picPath = basePath + p.getProperty(ConstantsAdmin.BANNER_PICTURE_PATH); // 担保机构图片存储路径
			} else if (type == 7) {
				picPath = basePath + p.getProperty(ConstantsAdmin.SKIN_PICTURE_PATH); // 用户App皮肤图片存储路径
			} else if (type == 6) {
				picPath = basePath + p.getProperty(ConstantsAdmin.FEEDBACK_PICTURE_PATH); // 用户反馈图片存储路径
			} else if (type == 8) {
				picPath = basePath + p.getProperty(ConstantsAdmin.ACTIVITY_PICTURE_PATH); // 活动图片存储路径
			} else if (type == 9) {
				picPath = basePath + p.getProperty(ConstantsAdmin.ICON_PICTURE_PATH); // icon图片存储路径
			} else if (type == 10) {
				picPath = basePath + p.getProperty(ConstantsAdmin.SPLASH_PICTURE_SCREEN); // 闪屏图片或者安全保障图片存储路径
			} else if (type == 11) {
				picPath = basePath + p.getProperty(ConstantsAdmin.NEWS_PICTURE_PATH); // 新闻媒体图片
			} else if (type == 12 || type==14|| type==22) {//14商品小图 22商品活动图片
				picPath = basePath + p.getProperty(ConstantsAdmin.GOODS_PICTURE_PATH); // 商城图片
			} else if (type == 15) {//评论图片
				picPath = basePath + p.getProperty(ConstantsAdmin.GOODS_COMMENT_PATH); // 商城图片
			} else if (type == 16) {
				picPath = basePath + p.getProperty(ConstantsAdmin.SHARE_PICTURE_PATH); // 分享图片
			}else if (type == 17) { // 牛只生活照
				picPath = basePath + p.getProperty(ConstantsAdmin.LIFE_PICTURE_PATH); // 牛只生活照
			}else if (type == 18) {
				picPath = basePath + p.getProperty(ConstantsAdmin.PROJECT_VIDEO_PATH); // 牛只视频路径
			}else if (type == 19) {
				picPath = basePath + p.getProperty(ConstantsAdmin.ADVERTISEMENT_CHANNEL_PATH); // 渠道推广顶部图片路径
			} else if(type == 20) {
				picPath = basePath + p.getProperty(ConstantsAdmin.PROJECT_VIDEO_COVER_PATH); // 牛只视频封面图片路径
			} else if(type == 21) {
                picPath = basePath + p.getProperty(ConstantsAdmin.RECHARGE_VOUCHER_PICTURE_PATH); // 线下转账凭证图片路径
            } else if(type == 23) {
            	picPath = basePath + p.getProperty(ConstantsAdmin.GOODS_CATEGORY_PATH); // 商品分类	上传banner
            }
            // type == 22 已经使用
			// 判断图片大小
			if(type != 18 && file.getSize() > Long.parseLong(maxPicSize)) {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "图片过大");
				return map;

			} else if (type == 18 && file.getSize() > Long.parseLong(maxVideoSize)) {//判断视频大小
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "视频过大");
				return map;
			}

			// 判断图片类型
			String fileExt = file.getContentType();
			fileExt = fileExt.substring(fileExt.lastIndexOf("/") + 1, fileExt.length()); // 截取图片类型
//			if (picType.indexOf(fileExt) == -1) {
//				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
//				map.put(ConstantsAdmin.MESSAGE, "不支持的图片类型");
//				return map;
//			}
			// 图片重命名
			String picName = UUID.randomUUID().toString();

			// 写入磁盘
			File fileDir = new File(picPath); // 目录不存在则创建
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			String savePath = picPath + "/" + picName + "." + fileExt;
			logger.info("图片保存路径=====>" + savePath);
			// 水印
//			if (type == 13||type==12) {/*
//				File tarFile = new File(picPath, picName);
//				FileOutputStream out = new FileOutputStream(tarFile);
//				InputStream in = file.getInputStream();
//				byte[] buff = new byte[1024];
//				int len = 0;
//				while (-1 != (len = in.read(buff))) {
//					out.write(buff, 0, len);
//				}
//				in.close();
//				out.close();
//				WaterMarkGenerate.generateWithTextMark(tarFile, savePath, "内蒙古奔富畜牧业发展有限公司");
//				tarFile.delete();*/
//				WaterMarkGenerate.generateWithTextMark(file.getInputStream(), savePath, "内蒙古奔富畜牧业发展有限公司");
//				file.getInputStream().close();
//			} else {

			// 判断图片大小  项目小图 并且大小超过2M

			if (type == 1 ) {
				//ImageUtil.compressImage(file.getInputStream(), savePath, 0.4);
				
				//原图
				BufferedImage baseImg = ImageIO.read(file.getInputStream());
				baseImg = ImageUtil.resize(baseImg, 504, 504);
				//头像
				ImageUtil.save(baseImg,savePath);
				//中国人民保险 覆盖层
				File templateFile = new File(basePath + p.getProperty(ConstantsAdmin.PROJECT_PICTURE_RENBAO_PATH)+"/template.png"); 
				BufferedImage templateImg = ImageIO.read(templateFile);
				//覆盖
				baseImg = ImageUtil.coverImage(baseImg, templateImg, 0, 0, 396, 54);
				//中国人民保险 水印图
				ImageUtil.save(baseImg,basePath + p.getProperty(ConstantsAdmin.PROJECT_PICTURE_RENBAO_PATH) + "/" + picName + "." + fileExt);

			} else if (type == 13 && file.getSize() > 2000000) {
				ImageUtil.compressImage(file.getInputStream(), savePath, 0.5);
			} else {
				file.transferTo(new File(savePath));
			}

		//	}
			// 插入数据库
			// 插入上传表
		    String path = picPath.substring(picPath.lastIndexOf("/") + 1, picPath.length()) + "/" + picName + "." + fileExt;


			Upload upload = new Upload();
			upload.setCreateTime(new Date());
			upload.setCreateUser(id);
			upload.setName(picName + "." + fileExt);
			upload.setPath(path);
			upload.setStatus(0);
//			uploadMapper.insert(upload);
			uploadMapper.insertSelective(upload);
			map.put(ConstantsAdmin.ID, upload.getId());
			map.put(ConstantsAdmin.PATH, path);
			map.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
			map.put(ConstantsAdmin.MESSAGE, "上传成功");
			map.put(ConstantsAdmin.PICTURE_PATH, basePath);
			return map;
		} catch (Exception e) {
			logger.error("上传图片出错=============>"+e.getMessage(),e);
			map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
			map.put(ConstantsAdmin.MESSAGE, "系统错误");
		}
		return map;
	}

	public static int getTextLength(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;
	}

	@Override
	public Upload saveWithoutFile(String fileName, String filePath, Integer userId) {
		Upload upload = new Upload();
		upload.setCreateTime(new Date());
		upload.setCreateUser(userId);
		upload.setName(fileName);
		upload.setPath(filePath);
		upload.setStatus(0);
		uploadMapper.insert(upload);
		return upload;
	}

	@Override
	public Upload get(Integer id) {
		return uploadMapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean updateAvatarUserId(Integer id, Integer uploadId) {
		User user = userMapper.selectByPrimaryKey(id);
		if (null != user.getAvatarId()) {
			Upload upload = uploadMapper.selectByPrimaryKey(uploadId);
			upload.setDeleteTime(new Date());
			upload.setDeleteUser(user.getId());
			upload.setStatus(2);
			uploadMapper.updateByPrimaryKey(upload);
		}
		user.setAvatarId(uploadId);
		userMapper.updateByPrimaryKey(user);
		return uploadMapper.updateAvatarUserId(id, uploadId);
	}

	@Override
	public Boolean updateSkinUserId(Integer userId, Integer id) {
		/*
		 * User user = userMapper.selectByPrimaryKey(userId); if (null !=
		 * user.getSkinId()) { Upload upload = uploadMapper.selectByPrimaryKey(id);
		 * upload.setDeleteTime(new Date()); upload.setDeleteUser(user.getId());
		 * upload.setStatus(2); uploadMapper.updateByPrimaryKey(upload); if
		 * (user.getSkinId().equals(id)) { user.setSkinId(null); } else {
		 * user.setSkinId(id); } } else { user.setSkinId(id); }
		 */
//        userMapper.updateByPrimaryKey(user);
		return uploadMapper.updateAvatarUserId(userId, id);
	}

	@Override
	public Map<String, Object> saveFile(MultipartFile file, Integer type, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 获取图片存储路径,支持的图片类型以及最大的图片大小
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("upload.properties");
			Properties p = new Properties();
			try {
				p.load(inputStream);
			} catch (IOException e) {
				logger.error(e);
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "系统错误");
				return map;
			}
			String basePath = p.getProperty(ConstantsAdmin.BASE_PATH);
			String maxPicSize = p.getProperty(ConstantsAdmin.MAX_PICTURE_SIZE); // 最大图片大小
//            String picType = p.getProperty(ConstantUtil.PICTURE_TYPE); // 支持的图片类型
			String picPath = ""; // 用户
			if (type == 0 || type == 4) {
				picPath = basePath + p.getProperty(ConstantsAdmin.USER_PICTURE_PATH); // 头像或者身份证照片存储路径
			} else if (type == 1) {
				picPath = basePath + p.getProperty(ConstantsAdmin.PROJECT_PICTURE_PATH); // 项目图片存储路径
			} else if (type == 2) {
				picPath = basePath + p.getProperty(ConstantsAdmin.ENTERPRISE_PICTURE_PATH); // 企业图片存储路径
			} else if (type == 3) {
				picPath = basePath + p.getProperty(ConstantsAdmin.GUARANTEE_PICTURE_PATH); // 担保机构图片存储路径
			} else if (type == 5) {
				picPath = basePath + p.getProperty(ConstantsAdmin.BANNER_PICTURE_PATH); // 担保机构图片存储路径
			} else if (type == 7) {
				picPath = basePath + p.getProperty(ConstantsAdmin.SKIN_PICTURE_PATH); // 用户App皮肤图片存储路径
			} else if (type == 6) {
				picPath = basePath + p.getProperty(ConstantsAdmin.FEEDBACK_PICTURE_PATH); // 用户反馈图片存储路径
			} else if (type == 8) {
				picPath = basePath + p.getProperty(ConstantsAdmin.ACTIVITY_PICTURE_PATH); // 活动图片存储路径
			} else if (type == 9) {
				picPath = basePath + p.getProperty(ConstantsAdmin.ICON_PICTURE_PATH); // icon图片存储路径
			} else if (type == 10) {
				picPath = basePath + p.getProperty(ConstantsAdmin.SPLASH_PICTURE_SCREEN); // 闪屏图片或者安全保障图片存储路径
			} else if (type == 11) {
				picPath = basePath + p.getProperty(ConstantsAdmin.NEWS_PICTURE_PATH); // 新闻媒体图片
			}
			// 判断图片大小
			if (file.getSize() > Long.parseLong(maxPicSize)) {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "文件过大");
				return map;
			}

			// 判断图片类型
			/*
			 * String fileExt = file.getContentType(); fileExt =
			 * fileExt.substring(fileExt.lastIndexOf("/") + 1, fileExt.length()); // 截取图片类型
			 */ /*
				 * if (picType.indexOf(fileExt) == -1) { map.put(ConstantUtil.STATUS,
				 * ConstantUtil.ERROR); map.put(ConstantUtil.MESSAGE, "不支持的图片类型"); return map; }
				 */

			String fileExt = file.getOriginalFilename();
			fileExt = fileExt.substring(fileExt.lastIndexOf(".") + 1, fileExt.length()); // 截取图片类型
			/*
			 * if (picType.indexOf(fileExt) == -1) { map.put(ConstantUtil.STATUS,
			 * ConstantUtil.ERROR); map.put(ConstantUtil.MESSAGE, "不支持的图片类型"); return map; }
			 */

			// 图片重命名
			String picName = UUID.randomUUID().toString();

			// 写入磁盘
			File fileDir = new File(picPath); // 目录不存在则创建
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			String savePath = picPath + "/" + picName + "." + fileExt;
			logger.info("图片保存路径=====>" + savePath);
			file.transferTo(new File(savePath));
			// 插入数据库
			// 插入上传表
			String path = picPath.substring(picPath.lastIndexOf("/") + 1, picPath.length()) + "/" + picName + "."
					+ fileExt;
			Upload upload = new Upload();
			upload.setCreateTime(new Date());
			upload.setCreateUser(id);
			upload.setName(picName + "." + fileExt);
			upload.setPath(path);
			upload.setStatus(0);
			uploadMapper.insert(upload);
			map.put(ConstantsAdmin.ID, upload.getId());
			map.put(ConstantsAdmin.PATH, path);
			map.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
			map.put(ConstantsAdmin.MESSAGE, "上传成功");
			map.put(ConstantsAdmin.PICTURE_PATH, basePath);
			return map;
		} catch (Exception e) {
			logger.error(e);
			map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
			map.put(ConstantsAdmin.MESSAGE, "系统错误");
		}
		return map;
	}
}
