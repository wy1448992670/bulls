package com.goochou.p2b.utils;
import com.google.common.collect.Lists;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2014-8-13
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [MAP2BEAN]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class BeanToMapUtil {

    /**
     *  Created on 2014-8-13 
     * <p>Discription:[将一个 Map 对象转化为一个 JavaBean]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return Object .
     */
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                try {
                    Object value = map.get(propertyName);

                    Object[] args = new Object[1];
                    args[0] = value;

                    descriptor.getWriteMethod().invoke(obj, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     *  Created on 2014-8-13 
     * <p>Discription:[将一个 JavaBean 对象转化为一个  Map]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return Map .
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class") && !propertyName.equals("declaringClass")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    public static <T> List<T> mapsToObjects(List<Map> maps, Class<T> clazz) throws InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            for (int i = 0,size = maps.size(); i < size; i++) {
                try {
                    map = maps.get(i);
                    list.add((T) convertMap(clazz, map));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

}


