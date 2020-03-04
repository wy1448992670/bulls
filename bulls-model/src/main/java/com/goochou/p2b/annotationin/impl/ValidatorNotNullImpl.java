package com.goochou.p2b.annotationin.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.goochou.p2b.annotationin.ValidatorNotNull;
import com.goochou.p2b.annotationin.iface.Validater;

/**
 * Created on 2014-8-25
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [不为NULL注解实现]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class ValidatorNotNullImpl implements Validater {

	public String validate(Field field, Annotation anno, Object o) {
		if (o != null) {
			return null;
		}
		return ((anno instanceof ValidatorNotNull) ? ((ValidatorNotNull) anno).desc() : ValidatorNotNull.defaultDesc);
	}

}
