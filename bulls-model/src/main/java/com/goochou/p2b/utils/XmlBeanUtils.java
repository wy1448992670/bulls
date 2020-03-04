package com.goochou.p2b.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Xml 和JavaBean转换工具类
 *
 * @author XiaoHao
 */
public class XmlBeanUtils {
    private XmlBeanUtils() {
    }

    /**
     * 将bean转换成Xml，编码方式为UTF-8
     *
     * @param bean 被转换的Bean
     * @return String
     * @throws JAXBException
     */
    public static <T> String convertBean2Xml(T bean) throws JAXBException {
        return convertBean2Xml(bean, "UTF-8", true);
    }

    /**
     * 将bean转换成指定编码方式的Xml
     *
     * @param bean    被转换的Bean
     * @param charSet 编码方式
     * @return String
     * @throws JAXBException
     */
    public static <T> String convertBean2Xml(T bean, String charSet, boolean containHeader) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(bean.getClass());
        Marshaller ms = jc.createMarshaller();
        ms.setProperty(Marshaller.JAXB_ENCODING, charSet);
        if (!containHeader) {
            ms.setProperty(Marshaller.JAXB_FRAGMENT, true);
        }
        StringWriter sw = new StringWriter();
        ms.marshal(bean, sw);
        return sw.toString();
    }

    /**
     * 将Xml数据转换成bean
     *
     * @param xml
     * @param clazz
     * @return
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertXml2Bean(String xml, Class<T> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }
}
