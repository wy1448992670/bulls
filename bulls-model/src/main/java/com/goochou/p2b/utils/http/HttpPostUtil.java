package com.goochou.p2b.utils.http;

import static com.goochou.p2b.utils.weixin.WXPayConstants.USER_AGENT;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.goochou.p2b.utils.weixin.MyConfig;
import com.goochou.p2b.utils.weixin.WXPayConfig;
import com.goochou.p2b.utils.weixin.WXPayUtil;

/**
 * @author user
 */
public class HttpPostUtil {

    private static final Logger LOGGER = Logger.getLogger(HttpPostUtil.class);

    /**
     * 指定UTF-8编码格式请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String postForward(String url, Map<String, String> params) throws Exception {
        return postForward(url, params, "UTF-8", false);
    }

    public static String postForward(String url, Map<String, String> params, boolean isCode) throws Exception {
        return postForward(url, params, "UTF-8", isCode);
    }

    /**
     * 使用Map作为参数请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws Exception
     */
    public static String postForward(String url, Map<String, String> params, String charset, boolean isCode)
            throws Exception {
        LOGGER.info("http-post请求" + url + ":" + params);
        boolean https = isHttps(url);
        if (https) {
            Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
            Protocol.registerProtocol("https", myhttps);
        }

        HttpClient httpclient = new HttpClient();
        PostMethod xmlpost = new PostMethod(url);

        try {
            httpclient.getHttpConnectionManager().getParams()
                    .setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 30);
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);

            NameValuePair[] nameValuePairs = new NameValuePair[params.size()];
            Iterator<String> keyIterator = params.keySet().iterator();
            int i = 0;
            while (keyIterator.hasNext()) {
                Object key = keyIterator.next();
                String value = (String) params.get(key);
                if (isCode)
                    value = URLEncoder.encode(value, charset);
                NameValuePair name = new NameValuePair((String) key, value);
                nameValuePairs[i] = name;
                i++;
            }

            xmlpost.setRequestBody(nameValuePairs);
            int status = httpclient.executeMethod(xmlpost);
            String response = xmlpost.getResponseBodyAsString();

            LOGGER.info("http-post请求返回" + status + ":" + response);
            return response;
        } catch (Exception e) {
            LOGGER.error("http-post请求异常", e);
            throw e;
        } finally {
            if (xmlpost != null) {
                xmlpost.releaseConnection();
            }
            if (httpclient != null) {
                httpclient.getHttpConnectionManager().closeIdleConnections(0);
            }

            if (https) {
                Protocol.unregisterProtocol("https");
            }
        }
    }

    /**
     * 指定UTF-8编码格式请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String postForward(String url, String params) throws Exception {
        return postForward(url, params, "UTF-8", false);
    }

    public static String postForward(String url, String params, boolean isCode) throws Exception {
        return postForward(url, params, "UTF-8", isCode);
    }

    /**
     * 使用String作为参数请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws Exception
     */
    public static String postForward(String url, String params, String charset, boolean isCode) throws Exception {
        LOGGER.info("http-post请求" + url + ":" + params);
        boolean https = isHttps(url);
        if (https) {
            Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
            Protocol.registerProtocol("https", myhttps);
        }

        HttpClient httpclient = new HttpClient();
        PostMethod xmlpost = new PostMethod(url);
        if (isCode)
            params = URLEncoder.encode(params, charset);
        byte[] input = params.getBytes(charset);
        InputStream inputStream = new ByteArrayInputStream(input);
        try {
            httpclient.getHttpConnectionManager().getParams()
                    .setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 30);
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            xmlpost.setRequestEntity(new InputStreamRequestEntity(inputStream, params.length()));

            int status = httpclient.executeMethod(xmlpost);
            String response = xmlpost.getResponseBodyAsString();

            LOGGER.info("http-post请求返回" + status + ":" + response);
            return response;
        } catch (Exception e) {
            LOGGER.error("http-post请求异常", e);
            throw e;
        } finally {
            if (xmlpost != null) {
                xmlpost.releaseConnection();
            }
            if (httpclient != null) {
                httpclient.getHttpConnectionManager().closeIdleConnections(0);
            }

            if (https) {
                Protocol.unregisterProtocol("https");
            }
        }
    }
    
    public static String postForwardWx(String url, Map<String, String> params) throws Exception {
        BasicHttpClientConnectionManager connManager;
        connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );
        org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
        HttpPost httpPost = new HttpPost(url);
        String reqBody = WXPayUtil.mapToXml(params);
        StringEntity postEntity = new StringEntity(reqBody, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", USER_AGENT + " " + new MyConfig().getMchID());
        httpPost.setEntity(postEntity);
    
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    private static boolean isHttps(String url) {
        if (url.startsWith("https")) {
            return true;
        } else {
            return false;
        }
    }
}

class MySSLSocketFactory implements ProtocolSocketFactory {
    private SSLContext sslcontext = null;

    private SSLContext createSSLContext() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, new TrustManager[]{
                    new TrustAnyTrustManager()}, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslcontext;
    }

    private SSLContext getSSLContext() {
        if (this.sslcontext == null) {
            this.sslcontext = createSSLContext();
        }
        return this.sslcontext;
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
            UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException,
            UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort,
                               HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        SocketFactory socketfactory = getSSLContext().getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress, localPort);
        } else {
            Socket socket = socketfactory.createSocket();
            SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
            SocketAddress remoteaddr = new InetSocketAddress(host, port);
            socket.bind(localaddr);
            socket.connect(remoteaddr, timeout);
            return socket;
        }
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
}