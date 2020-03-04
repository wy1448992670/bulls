package com.goochou.p2b.annotationin.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.goochou.p2b.annotationin.ValidatorCustom;
import com.goochou.p2b.annotationin.ValidatorNotNull;
import com.goochou.p2b.annotationin.ValidatorRegex;
import com.goochou.p2b.annotationin.client.ValidatorException;
import com.goochou.p2b.annotationin.iface.Validater;
import com.goochou.p2b.annotationin.impl.ValidatorCustomImpl;
import com.goochou.p2b.annotationin.impl.ValidatorNotNullImpl;
import com.goochou.p2b.annotationin.impl.ValidatorRegexImpl;

/**
 * Created on 2014-8-25
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [验证帮助类]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class ValidatorHelper {
	private static Map<Class<? extends Annotation>, Validater> validatorMap = new HashMap<Class<? extends Annotation>, Validater>();
	static {
		validatorMap.put(ValidatorNotNull.class, new ValidatorNotNullImpl());
		validatorMap.put(ValidatorRegex.class, new ValidatorRegexImpl());
		validatorMap.put(ValidatorCustom.class, new ValidatorCustomImpl());
	}
	
	public static String validate(Object validateObj, Field field, Annotation anno) {
        Validater validater = validatorMap.get(anno.annotationType());
        if (validater == null) return null;
        field.setAccessible(true);
        try {
            return validater.validate(field, anno, field.get(validateObj));
        } catch (IllegalArgumentException e) {
            throw new ValidatorException("字段[" + field.getName() + "]失败：不存在", e);
        } catch (IllegalAccessException e) {
            throw new ValidatorException("字段[" + field.getName() + "]失败：无法访问", e);
        }
    }
}
