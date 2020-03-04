package com.goochou.p2b.annotationin.iface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created on 2014-8-25
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [验证接口]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public interface Validater {
    
    /**
     *  Created on 2014-8-25 
     * <p>Discription:[效验]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return String .
     */
	public String validate(Field field, Annotation anno, Object o);
}
