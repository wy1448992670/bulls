package com.goochou.p2b.annotationin.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.goochou.p2b.annotationin.ValidatorCustom;
import com.goochou.p2b.annotationin.client.IValidatorCustom;
import com.goochou.p2b.annotationin.client.ValidatorException;

/**
 * Created on 2014-8-25
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [自定义验证注解实现]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class ValidatorCustomImpl extends ValidatorNotNullImpl {

	@Override
	public String validate(Field field, Annotation anno, Object o) {
	    String result = super.validate(field, anno, o);
        if (result != null) return result;
        ValidatorCustom customAnno = (ValidatorCustom) anno;
        Object customHandle = null;
        try {
            Class<?> customClass = Class.forName(customAnno.clazz());
            customHandle = customClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new ValidatorException("未找到自定义处理类：" + customAnno.clazz(), e);
        } catch (InstantiationException e) {
            throw new ValidatorException("自定义处理类实例化失败：" + customAnno.clazz(), e);
        } catch (IllegalAccessException e) {
            throw new ValidatorException("自定义处理类无法实例化：" + customAnno.clazz(), e);
        }
        if (customHandle == null || !(customHandle instanceof IValidatorCustom)) {
            throw new ValidatorException("自定义处理类无效：" + customAnno.clazz());
        }
        IValidatorCustom customValidater = (IValidatorCustom) customHandle;
        if (!customValidater.validate(o)) {
            return customAnno.desc();
        }
        return null;
	}

}
