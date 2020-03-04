/**   
* @Title: CheckEmptyUtil.java 
* @Package com.goochou.p2b.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zj
* @date 2019-08-13 17:02 
* @version V1.0   
*/
package com.goochou.p2b.utils;

/**
 * 判空
 * 
 * @ClassName: CheckEmptyUtil
 * @author zj
 * @date 2019-08-13 17:02
 */
public class CheckEmptyUtil {

	/**
	 * 空对象返回""
	 * 
	 * @Title: ifnull
	 * @param obj
	 * @return String
	 * @author zj
	 * @date 2019-08-13 17:02
	 */
	public static String ifnull(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}
}
