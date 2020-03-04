package com.goochou.p2b.utils;

import java.util.ResourceBundle;

/**
 * @author xiaohao@fuiou.com
 */
public class ConfigReader {
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("init");

    /**
     * @param param
     * @return
     */
    public static String getString(String param) {
        return RESOURCE_BUNDLE.getString(param);
    }

    public static int getInt(String param) {
        return Integer.valueOf(RESOURCE_BUNDLE.getString(param));
    }
}
