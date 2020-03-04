package com.goochou.p2b.admin.annotatioin;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @author szy
 * 防止重复提交
 */
import java.lang.annotation.Target;

/**
 * 令牌Annotationin
 * @author xueqi
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	  boolean save() default false;
	  
	  boolean remove() default false;
	  
	  boolean urlnothing() default false;
}
