package com.goochou.p2b.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图像处理工具
 * 
 * @ClassName: ImageUtil
 * @author zj
 * @date 2019-07-08 10:51
 */
public class ImageUtil {
	private static final Logger log = Logger.getLogger(ImageUtil.class);

	/**
	 * 等比压缩图片
	 * 
	 * @Title: compressImage
	 * @param inputStream 原图流
	 * @param outFilepath 输出图片路径
	 * @param scale       缩放比例，大于1就是变大，小于1就是缩小
	 * @throws Exception void
	 * @author zj
	 * @date 2019-07-08 11:40
	 */
	public static void compressImage(InputStream inputStream, String outFilepath, double scale) {
		try {
			Thumbnails.of(inputStream).scale(scale).toFile(outFilepath);
		} catch (IOException e) {
			log.error("压缩图片出错======>" + e.getMessage(), e);
		}
	}

	/**
	 * 等比压缩图片
	 * 
	 * @Title: compressImage
	 * @param sourcePath 原图路径
	 * @param outPath    输出图片路径
	 * @param scale      缩放比例，大于1就是变大，小于1就是缩小
	 * @throws Exception void
	 * @author zj
	 * @date 2019-07-08 11:32
	 */
	public static void compressImage(String sourcePath, String outPath, double scale) {
		try {
			Thumbnails.of(sourcePath).scale(scale).toFile(outPath);
		} catch (IOException e) {
			log.error("压缩图片出错======>" + e.getMessage(), e);
		}
	}
	
	/**
     * 缩放
     * @author 张琼麒
     * @version 创建时间：2019年10月17日 下午3:15:59
     * @param baseBufferedImage
     * @param coverBufferedImage
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
	public static BufferedImage resize(BufferedImage baseBufferedImage, int width, int height) throws Exception {
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 创建画布
		Graphics2D newGraphics = newImage.createGraphics();
		
		// 设置图片透明 注********：只有png格式的图片才能设置背景透明，jpg设置图片颜色变的乱七八糟
		// newImage = newImageG.getDeviceConfiguration().createCompatibleImage(width,
		// height, Transparency.TRANSLUCENT);
		// 重新创建画布
		//newGraphics = newImage.createGraphics();
		
		// 绘制
		newGraphics.drawImage(baseBufferedImage, 0, 0, width, height, null);
		//关闭资源
		newGraphics.dispose();
		return newImage;
	}
	
	/**
     * 图片覆盖（覆盖图压缩到width*height大小，覆盖到底图上）
     *
     * @param baseBufferedImage 底图
     * @param coverBufferedImage 覆盖图
     * @param x 起始x轴
     * @param y 起始y轴
     * @param width 覆盖宽度
     * @param height 覆盖长度度
     * @return
     * @throws Exception
     */
	public static BufferedImage coverImage(BufferedImage baseBufferedImage, BufferedImage coverBufferedImage, int x,
			int y, int width, int height) throws Exception {

		// 创建Graphics2D对象，用在底图对象上绘图
		Graphics2D g2d = baseBufferedImage.createGraphics();
		// 绘制
		g2d.drawImage(coverBufferedImage, x, y, width, height, null);
		g2d.dispose();// 释放图形上下文使用的系统资源
		
		return baseBufferedImage;
	}
	
	public static File save(BufferedImage bufferedImage,String path) throws IOException {
		File file = new File(path);//底图
		ImageIO.write(bufferedImage, "png", file);
		return file;
	}
	
	public static void main(String[] args) {
		try {
			compressImage("e:\\1.jpg", "e:\\333.jpg", 0.5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
