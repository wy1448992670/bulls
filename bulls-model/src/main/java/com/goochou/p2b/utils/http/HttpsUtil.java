package com.goochou.p2b.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

/**
 * 
 * @author tanshen
 *
 */
public class HttpsUtil {	
    private static final Logger log = Logger.getLogger(HttpsUtil.class);
	/**
	 * 向HTTPS地址发送POST请求
	 * 
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 响应内容
	 */
	@SuppressWarnings("finally")
	public static String httpsPost(String reqURL,String params) {
		// 响应内容
		String responseContent = null; 
		// 创建默认的httpClient实例
		HttpClient httpClient = getHttpsClient();
		
		try {
			// 创建HttpPost
			HttpPost httpPost = new HttpPost(reqURL);
			httpPost.setEntity(new StringEntity(params,"UTF-8"));

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			return responseContent;
		}
	}
	
	/**
	 *  向HTTPS地址发送get请求
	 * @param reqURL
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String httpsGet(String reqURL){
		// 响应内容
		String responseContent = null; 
		// 创建默认的httpClient实例
		HttpClient httpClient = getHttpsClient();
		
		HttpGet get = new HttpGet(reqURL);
		try {
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity(); // 获取响应实体
			
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, getResponseEncoding(response, "UTF-8"));
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			return responseContent;
		}
	}
	/**
	 *  向HTTPS地址发送get请求
	 * @param reqURL
	 * @return
	 */
	public static String httpsGet(String reqURL, int conntTimeout, int socketTimeout){
		// 响应内容
		String responseContent = null; 
		// 创建默认的httpClient实例
		HttpClient httpClient = getHttpsClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, conntTimeout);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
		
		HttpGet get = new HttpGet(reqURL);
		try {
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity(); // 获取响应实体
			
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, getResponseEncoding(response, "UTF-8"));
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
		}
		return responseContent;
	}
	/**
	 * 向HTTPS地址发送POST请求
	 * 
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 响应内容
	 */
	public static String httpsPost(String reqURL,String params, int conntTimeout, int socketTimeout) {
		// 响应内容
		String responseContent = null; 
		// 创建默认的httpClient实例
		HttpClient httpClient = getHttpsClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, conntTimeout);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
		try {
			// 创建HttpPost
			HttpPost method = new HttpPost(reqURL);
			method.setHeader("Content-Encoding", "UTF-8");
			method.setEntity(new StringEntity(params,"UTF-8"));

			HttpResponse response = httpClient.execute(method); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseContent = EntityUtils.toString(entity, getResponseEncoding(response, "UTF-8"));
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
		}	
		return responseContent;
	}
	
	/**
	 * 向HTTPS地址发送POST请求
	 * 
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 响应内容
	 */
	@SuppressWarnings("finally")
	public static String httpsPost(String reqURL, Map<String, String> params, int conntTimeout, int socketTimeout) {
		// 响应内容
		String responseContent = null; 
		// 创建默认的httpClient实例
		HttpClient httpClient = getHttpsClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, conntTimeout);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
		try {
			// 创建HttpPost
			HttpPost method = new HttpPost(reqURL);
			method.setHeader("Content-Encoding", "UTF-8");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			if (params != null)
				for (Map.Entry<String, String> entry : params.entrySet())
					param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			method.setEntity(new UrlEncodedFormEntity(param,"UTF-8"));

			HttpResponse response = httpClient.execute(method); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseContent = EntityUtils.toString(entity, getResponseEncoding(response, "UTF-8"));
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			return responseContent;
		}
	}
	
	
	/**
	 * 向HTTPS地址发送POST请求
	 * 
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 响应内容
	 */
	@SuppressWarnings("finally")
	public static  JSONObject httpsPost(String reqURL,Object params) {
		//params to jsonStr
		JSONObject jsonObject = null;
		//result
		JSONObject result=null;
		// 响应内容
		String responseContent = null; 
		// 创建默认的httpClient实例
		HttpClient httpClient = getHttpsClient();
		jsonObject=JSONObject.fromObject(params);
		try {
			// 创建HttpPost
			HttpPost httpPost = new HttpPost(reqURL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("message", jsonObject.toString())); 
			httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));  
			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
			if(!StringUtils.isBlank(responseContent)){
				result=JSONObject.fromObject(responseContent);
			}
			if(log.isInfoEnabled()){
				log.info("httpPost:reqURL:"+reqURL+"|message:"+jsonObject.toString()+"|responseContent:"+responseContent
						+"|response:"+response.getStatusLine().getStatusCode());
			}
		} catch(Exception e){
			log.error("e:"+e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			return result;
		}
	}
	
	
	/**
	 * 向HTTPS地址发送GET请求
	 * 
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 
	 * @return 响应内容
	 */
	@SuppressWarnings("finally")
	public static  JSONObject httpsGET(String reqURL,Object params) {
		//params to jsonStr
		JSONObject jsonObject = null;
		//result
		JSONObject result=null;
		// 响应内容
		String responseContent = null; 
		// 创建默认的httpClient实例
		HttpClient httpClient = getHttpsClient();
		jsonObject=JSONObject.fromObject(params);
		try {
			// 创建HttpGET
			HttpGet  httpGet=new HttpGet();
			reqURL=reqURL+"?message="+jsonObject.toString();
			httpGet.setURI(new URI(reqURL));  
			HttpResponse response = httpClient.execute(httpGet); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
			if(!StringUtils.isBlank(responseContent)){
				result=JSONObject.fromObject(responseContent);
			}
		} catch (Exception e){
			log.error("httpsGET error: " + e);
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			return result;
		}
	}
	
/**
 * Content-Type字符编码匹配
 */
	private static final Pattern ENCODING = Pattern.compile("(?i)charset=([-a-zA-Z0-9]+)");
	/**
	 * 获取HTTP响应体编码
	 * 
	 * @param response
	 * @param defEncoding
	 * @return
	 */
	public static String getResponseEncoding(HttpResponse response, String defEncoding){
		Header header = response.getFirstHeader("Content-Encoding");
		String encoding = null;
		
		if (header != null && header.getValue() != null && header.getValue().length() > 0) {
			encoding = header.getValue().trim();	
		} else {
			header = response.getFirstHeader("Content-Type");
			if (header != null && header.getValue() != null &&header.getValue().length() > 0){
				Matcher matcher = ENCODING.matcher(header.getValue());
				if (matcher.find())
					encoding = matcher.group(1).trim();
			}
		}

		try {
			Charset.isSupported(encoding);
		} catch (Exception e) {
			encoding = defEncoding;
		}
		
		return encoding;
	}
	
	/**
	 * 获取HttpClient
	 * @return
	 */
	public static HttpClient getHttpsClient(){
		// 创建默认的httpClient实例
		HttpClient httpClient = new DefaultHttpClient();
		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() { 
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
		} catch (KeyManagementException e) {
			e.printStackTrace();
			log.error(e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.error(e);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error(e);
		}
		return httpClient;
	}
	
	/**
	 * 解決wap域名风控拦截
	 * @param httpurl
	 * @return
	 */
	public static String do302JumpUrl(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Host", "cash.yeepay.com");
            connection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.addRequestProperty("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8");
            connection.addRequestProperty("Cache-Control", "no-cache");
            connection.addRequestProperty("Connection", "keep-alive");
            connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
            connection.setInstanceFollowRedirects(false);
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 302) {
                result = connection.getHeaderField("Location");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();// 关闭远程连接
        }
        return result;
    }
	
	/**
     * 解決微信域名风控拦截
     * @param httpurl
     * @return
     */
    public static String doWx302JumpUrl(String httpurl) {
        log.info("http=="+httpurl);
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
//          conn.setRequestProperty("Accept-Charset", "GBK");
            connection.setRequestProperty("contentType", "utf-8");
            connection.addRequestProperty("referer", "wap.bfmuchang.com");
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null){
                  buffer.append(line);
                }
               String str = buffer.toString();
               log.info("str="+str);
               //正则取出
               String code = null;
               Pattern pattern = Pattern.compile("deeplink : \"(.*?)\"");
               Matcher matcher = pattern.matcher(str);
               if (matcher.find()) {
                   matcher.reset();
                   while (matcher.find()) {
                       code = matcher.group(0);
                   }
               }
               result = URLDecoder.decode(code.replace("\"", "").replace("deeplink : ", ""));
               log.info("WX>>>>"+result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();// 关闭远程连接
        }
        return result;
    }
    
    public static void main(String[] args) {
        //System.out.println(HttpsUtil.doWx302JumpUrl("https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx22144212128713d1b91f9bac1641614900&package=3983206457"));
    }
}