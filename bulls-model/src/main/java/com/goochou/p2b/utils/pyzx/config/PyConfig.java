package com.goochou.p2b.utils.pyzx.config;

import com.goochou.p2b.constant.client.ClientConstants;

/**
 *
 */
public class PyConfig {

    // API域名
    public final static String HOST = ClientConstants.getLableValue("pyzx.host"); // 测试环境
//    public final static String HOST = "https://www.pycredit.com:8443"; // 生产环境

    // 返回报文压缩的URL
    public final static String PATH_ZIP = "/rest/query/report/zip";

    // 返回报文不压缩的URL
    public final static String PATH_UNZIP = "/rest/query/report/unzip";

    // 认证信息
    public final static String USERID = ClientConstants.getLableValue("pyzx.user");
    public final static String PASSWORD = ClientConstants.getLableValue("pyzx.pwd");

    // 是否测试模式
    public final static boolean IS_TEST = false;

    // 请求内容样本文件，实际中可使用具体对象组装
    public final static String QUERY_FILE = "/sample.json";

    public final static String KEYSTORE_FILE = ClientConstants.getLableValue("pyzx.keystore.file");
    public final static String KEYSTORE_PASSWORD = ClientConstants.getLableValue("pyzx.keystore.pwd");
    public final static String TRUSTSTORE_FILE = ClientConstants.getLableValue("pyzx.truststore.file");
    public final static String TRUSTSTORE_PASSWORD = ClientConstants.getLableValue("pyzx.truststore.pwd");
}
