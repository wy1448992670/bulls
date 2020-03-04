package com.goochou.p2b.utils.umeng;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.PushUrlTypeEnum;
import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.utils.umeng.android.AndroidFilecast;
import com.goochou.p2b.utils.umeng.ios.IOSFilecast;

import net.sf.json.JSONObject;

public class UMengUtil {

	private static final Logger logger = Logger.getLogger(UMengUtil.class);
	public final static String IOS_APP_KEY = Constants.IOS_APP_KEY;
	public final static String IOS_APP_SECRET = Constants.IOS_APP_SECRET;
	public final static String ANDROID_APP_KEY = Constants.ANDROID_APP_KEY;
	public final static String ANDROID_APP_SECRET = Constants.ANDROID_APP_SECRET;

	private final static String STATUS_URL = Constants.STATUS_URL;

	private String appkey = null;
	private String appMasterSecret = null;
	private PushClient client = new PushClient();

	public UMengUtil() {
		super();
	}

	public UMengUtil(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 安卓手机消息推送
	 * 
	 * @Title: sendAndroidFilecast
	 * @param title        标题
	 * @param content      消息内容
	 * @param startTime    定时发送时间，若不填写表示立即发送。格式: "YYYY-MM-DD hh:mm:ss"。
	 * @param url          跳转url
	 * @param deviceTokens 设备号
	 * @param urlType      url类型 h5 （url） 或者原生 （native）
	 * @param params       额外手机端需要的参数 可以自定义
	 * @return task_id
	 * @throws Exception
	 * @author zj
	 * @date 2019-08-15 15:49
	 */
	public String sendAndroidFilecast(String title, String content, String startTime, String url, String deviceTokens, String urlType,
			JSONObject params) throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there are
		// multiple tokens

		logger.info("Android_appkey====>" + appkey);
		logger.info("Android_appMasterSecret====>" + appMasterSecret);
		logger.info("Android_title====>" + title);
		logger.info("Android_content====>" + content);
		logger.info("Android_startTime====>" + startTime);
		logger.info("Android_url====>" + url);
		logger.info("Android_deviceTokens====>" + deviceTokens);
		logger.info("Android_urlType====>" + urlType);
		logger.info("Android_params====>" + params);

		String fileId = client.uploadContents(appkey, appMasterSecret, deviceTokens);
		filecast.setDescription("[BF-API-SEND]" + title);
		filecast.setFileId(fileId);
		filecast.setTicker("");
		filecast.setText(content);
		filecast.setTitle(title);
		filecast.goUrlAfterOpen("http://www.baidu.com");
		filecast.goActivityAfterOpen("com.yiyi.rancher.push.MipushTestActivity");
		filecast.setProductionMode();
		filecast.setMipush(true);
		
		if (null != url && !"".equals(url)) {
//			filecast.setAfterOpenAction(AfterOpenAction.go_url);
//			filecast.setUrl(url);

			//filecast.setAfterOpenAction(AfterOpenAction.go_custom);
			JSONObject json = new JSONObject();
			if (urlType.equals(PushUrlTypeEnum.URL.getFeatureName())) {
				json.put("url", url);
			} else {
				json.put("key", url);
				json.put("params", params);
			}
			filecast.setCustomField(json.toString());
		}
		if (null != startTime && !"".equals(startTime)) {
			filecast.setStartTime(startTime);
		}
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		return client.send(filecast);
	}

	/**
	 * IOS消息推送
	 * 
	 * @Title: sendIOSFilecast
	 * @param title        标题
	 * @param content      内容
	 * @param startTime    定时发送时间，若不填写表示立即发送。格式: "YYYY-MM-DD hh:mm:ss"。
	 * @param url          跳转连接 url类型 h5 （url） 或者原生 （native）
	 * @param deviceTokens 设备号
	 * @param urlType      跳转类型
	 * @param params       额外参数自定义。手机端使用
	 * @return task_id
	 * @throws Exception
	 * @author zj
	 * @date 2019-08-15 15:54
	 */
	public String sendIOSFilecast(String title, String content, String startTime, String url, String deviceTokens, String urlType, JSONObject params)
			throws Exception {

		IOSFilecast filecast = new IOSFilecast(appkey, appMasterSecret);

		logger.info("IOS_appkey====>" + appkey);
		logger.info("IOS_appMasterSecret====>" + appMasterSecret);
		logger.info("IOS_title====>" + title);
		logger.info("IOS_content====>" + content);
		logger.info("IOS_startTime====>" + startTime);
		logger.info("IOS_url====>" + url);
		logger.info("IOS_deviceTokens====>" + deviceTokens);
		logger.info("IOS_urlType====>" + urlType);
		logger.info("IOS_params====>" + params);
		// TODO upload your device tokens, and use '\n' to split them if there are
		// multiple tokens
		String fileId = client.uploadContents(appkey, appMasterSecret, deviceTokens);
		filecast.setDescription("[BF-API-SEND]" + title);
		filecast.setFileId(fileId);
		filecast.setAlert("{\"title\": \"\",\"subtitle\": \"" + title + "\",\"body\": \"" + content + "\"}");
		filecast.setBadge(0);

		if (!TestEnum.RELEASE.getFeatureName().equals(com.goochou.p2b.constant.Constants.TEST_SWITCH)) {
			filecast.setTestMode();
		}

		filecast.setSound("default");
		if (null != url && !"".equals(url)) {
//			filecast.setCustomizedField("key", url);

			if (urlType.equals(PushUrlTypeEnum.URL.getFeatureName())) {
				filecast.setCustomizedField("url", url);
			} else {
				filecast.setCustomizedField("key", url);
				filecast.setCustomizedField("params", params == null ? null : params.toString());
			}

		}
		if (null != startTime && !"".equals(startTime)) {
			filecast.setStartTime(startTime);
		}
		filecast.setProductionMode();
		return client.send(filecast);
	}

	/**
	 * 查询消息推送结果
	 * 
	 * @Title: queryStatus
	 * @param taskId
	 * @param appKey
	 * @param appSecret
	 * @return String
	 * @author zj
	 * @date 2019-08-15 16:20
	 * 
	 * 
	 */

//	"ret":"SUCCESS/FAIL",
//  "data": {
//      // 当"ret"为"SUCCESS"时，包含如下参数:
//      "task_id":"xx",
//      "status": xx,    // 消息状态: 0-排队中, 1-发送中，2-发送完成，3-发送失败，4-消息被撤销，
//                      // 5-消息过期, 6-筛选结果为空，7-定时任务尚未开始处理
//      // Android消息，包含以下参数
//      "sent_count":xx,    // 消息收到数
//      "open_count":xx,    // 打开数
//      "dismiss_count":xx    // 忽略数
//      // iOS消息，包含以下参数
//      "total_count": xx,    // 投递APNs设备数
//      "sent_count": xx,    // APNs返回SUCCESS的设备数
//      "open_count": xx    // 打开数
//      // 当"ret"为"FAIL"时，包含参数如下:
//      "error_code": "xx",    // 错误码详见附录I。
//      "error_msg": "xx"    // 错误码详见附录I。
//    }
	public static String queryStatus(String taskId, String appKey, String appSecret) {
		Map<String, String> params = new HashMap<String, String>();
		StringBuffer result = new StringBuffer();
		params.put("appkey", appKey);
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("task_id", taskId);
		String json = JSON.toJSONString(params);
		try {
			String sign = DigestUtils.md5Hex(("POST" + STATUS_URL + json + appSecret).getBytes("utf8"));
			HttpPost post = new HttpPost(STATUS_URL + "?sign=" + sign);
			post.setHeader("User-Agent", "Mozilla/5.0");
			StringEntity se = new StringEntity(json, "UTF-8");
			post.setEntity(se);
			// Send the post request and get the response
			HttpClient http = new DefaultHttpClient();
			HttpResponse response = http.execute(post);
			int status = response.getStatusLine().getStatusCode();
			System.out.println("Response Code : " + status);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static void main(String[] args) {
		String taskId = "";
	//	UMengUtil umeng = new UMengUtil(UMengUtil.ANDROID_APP_KEY, UMengUtil.ANDROID_APP_SECRET);
//		try {
//			taskId = umeng.sendAndroidFilecast("新春祝福", "热烈祝贺推送", null, "http://www.xinjucai.com", "AmcX3nUo-pd_xr_WKysDCM0p0ld1YsryaKs-_2g85kj9"+"\n");
//			System.out.println(taskId);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		UMengUtil umeng = new UMengUtil(UMengUtil.IOS_APP_KEY, UMengUtil.IOS_APP_SECRET);
//		try {
//			taskId = umeng.sendIOSFilecast("新春祝福", "热烈祝贺推送", null, "http://www.xinjucai.com", "f30f4bc8823c18fe19db3362e4a8ae5ee5182611fc99352ec848b366f9fb5ebd"+"\n");
//			System.out.println(taskId);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		/*
		 * { "ret":"SUCCESS/FAIL", "data": { // 当"ret"为"SUCCESS"时,包含如下参数:
		 * "task_id":"xx", "status": xx, // 消息状态: 0-排队中, 1-发送中，2-发送完成，3-发送失败，4-消息被撤销， //
		 * 5-消息过期, 6-筛选结果为空，7-定时任务尚未开始处理 "sent_count":xx, // 消息实际发送数 "open_count":xx, //
		 * 打开数 "dismiss_count":xx // 忽略数
		 * 
		 * // 当"ret"为"FAIL"时，包含参数如下: "error_code": "xx" // 错误码详见附录I。 } } Android:
		 * sent_count表示消息送达设备的数量。由于设备可能不在线，在消息有效时间内(expire_time)，设备上线后还会收到消息。
		 * 所以"sent_count"的数字会一直增加，直至到达消息过期时间后，该值不再变化。 iOS:
		 * sent_count表示友盟成功推送到APNs平台的数字，不代表送达设备的个数。友盟将消息发送到APNs服务器之后，如果APNs没有返回错误，
		 * 友盟认为发送成功。
		 */
		// System.out.println(queryStatus("uffxzt8152168781481301", ANDROID_APP_KEY,
		// ANDROID_APP_SECRET));
		// System.out.println(queryStatus("uful9qq152168781964801", IOS_APP_KEY,
		// IOS_APP_SECRET));

//		JSONObject json = new JSONObject();
//		json.put("key", "43#dingqi");
//		JSONObject params = new JSONObject();
//		params.put("id", "1111");
//		params.put("type", "121");
//		json.put("params", params);

		UMengUtil umeng = new UMengUtil(UMengUtil.ANDROID_APP_KEY, UMengUtil.ANDROID_APP_SECRET);
		try {
			umeng.sendAndroidFilecast("2019", "推推推", null, "https://wap.bfmuchang.com/ezuikit/live.html", "AiSCkohUubR2NpEoLoozkXxJ1UpithJXb0W9fqDreXT2", "url", null);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	/*	UMengUtil iosUmeng = new UMengUtil(UMengUtil.IOS_APP_KEY, UMengUtil.IOS_APP_SECRET);
		try {

			iosUmeng.sendIOSFilecast("测试", "推送测试", null, "https://www.baidu.com", "AhiMujaBz0qbu248vMpJ7ldzdXaNPFMX7NnomGvWN",
					"url", null);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	//	System.out.println("9Sw+fiJFFm/G6K0IHgvtscPvN19XwYsMGrLcfNzM5M5dw/CuapLSe6mICLGMuAbG".length());
//		System.out.println(umeng.queryStatus("ufamlib157500597033001",UMengUtil.ANDROID_APP_KEY, UMengUtil.ANDROID_APP_SECRET));
//		System.out.println(queryStatus("uftq74s157499655516601", IOS_APP_KEY, IOS_APP_SECRET));
		// System.out.println(json.toString());
	}

}
