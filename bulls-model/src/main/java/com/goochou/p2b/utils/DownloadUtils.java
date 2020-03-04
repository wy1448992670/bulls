package com.goochou.p2b.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
/**
 * 类DownloadUtils.java的实现描述：下载方法

 */
public class DownloadUtils {
	/**
	 * 获得response输出流，用于下载
	 * @param fileName 下载框显示的文件名
	 * @return
	 */
	public static OutputStream getResponseOutput(String fileName,HttpServletResponse response){
		 
		 // Set the content type
		response.setContentType("application/x-msdownload");
         try {
			//Set the content-disposition
        	 response.addHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("GBK"),"iso-8859-1"));
			 //// Get the outputstream
			 return response.getOutputStream();
		}catch (Exception e) {
			new RuntimeException(e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 关闭response输出流
	 */
	public static void closeResponseOutput(HttpServletResponse response){
		try {
			OutputStream os = response.getOutputStream();
			os.flush();
			os.close();
		} catch (IOException e) {
			new RuntimeException(e.getMessage(),e);
		}
	}
}
