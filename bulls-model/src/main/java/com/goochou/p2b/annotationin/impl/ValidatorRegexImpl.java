package com.goochou.p2b.annotationin.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.goochou.p2b.annotationin.ValidatorRegex;

/**
 * Created on 2014-8-25
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [正则注解实现]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class ValidatorRegexImpl extends ValidatorNotNullImpl {

	@Override
	public String validate(Field field, Annotation anno, Object o) {
		String result = super.validate(field, anno, o);
		if (result != null) return result;
		String strValue = o.toString();
		ValidatorRegex regexAnno = (ValidatorRegex) anno;
		if (strValue.matches(regexAnno.regex())) {
			return null;
		}
		return regexAnno.desc();
	}

}
