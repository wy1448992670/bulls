/**   
* @Title: ScreenInfoService.java 
* @Package com.goochou.p2b.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zj
* @date 2019-07-16 09:56 
* @version V1.0   
*/
package com.goochou.p2b.service;

import java.util.Map;

/**
 * 大屏幕数据处理
 * 
 * @ClassName: ScreenInfoService
 * @author zj
 * @date 2019-07-16 09:56
 */
public interface ScreenInfoService {

	/**
	 * 大屏幕统计信息
	 * 
	 * @Title: getScreenInfo
	 * @return {@link Map}
	 * @author zj
	 * @date 2019-07-16 10:17
	 */
	Map<String, Object> getScreenInfo(String prairieValue);

}
