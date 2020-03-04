package com.goochou.p2b.annotationin.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.goochou.p2b.annotationin.helper.ValidatorHelper;

/**
 * Created on 2014-8-25
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [验证工具类]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class ValidatorUtil {
	public static ValidatorResult validate(Object o) {
		ValidatorResult result = new ValidatorResult();
		result.setValidateObject(o);
		
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation anno : annotations) {
				String fieldAnnoResult = ValidatorHelper.validate(o, field, anno);
				if (fieldAnnoResult != null) {
					result.getInvalidateDescList().add(fieldAnnoResult);
					result.getInvalidateDescList();//add("\r\n");
					result.setValidated(false);
					break;
				}
			}
		}
		
		return result;
	}
}
