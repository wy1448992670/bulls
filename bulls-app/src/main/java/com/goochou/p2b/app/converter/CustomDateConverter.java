package com.goochou.p2b.app.converter;


import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: huangsj
 * @Date: 2019/7/2 18:52
 * @Description:
 */
public class CustomDateConverter implements Converter<String, Date> {

    private static final Logger logger = Logger.getLogger(CustomDateConverter.class);

    @Override
    public Date convert(String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            logger.error("日期参数输入格式不对，正确的为yyyyMMddHHmmss");
        }
        return null;
    }

}