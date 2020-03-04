package com.goochou.p2b.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.hessian.InterFaceRequest;
import com.goochou.p2b.hessian.InterFaceResponse;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.Service;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.client.TransactionClient;
import com.goochou.p2b.model.vo.InterfaceVO;
import com.goochou.p2b.model.vo.ParamVO;
import com.goochou.p2b.utils.AppUtil;
import com.goochou.p2b.utils.BeanToMapUtil;
import com.goochou.p2b.utils.JSONAlibabaUtil;
import com.goochou.p2b.utils.JSONSerializer;
import com.goochou.p2b.utils.StringUtil;

public class InterFaceServlet extends HttpServlet {

	final Logger log = Logger.getLogger(this.getClass());
	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 7614689386681070047L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message = request.getParameter("message");
		PrintWriter pw = null;
		if(null != message && !"".equals(message)){
			JSONObject obj = JSONAlibabaUtil.parseString(message);
			String req = JSONAlibabaUtil.getJsonStr(obj, "req");
			ServiceMessage sm = (ServiceMessage) JSONAlibabaUtil.toBean(JSONObject.parseObject(message), ServiceMessage.class);
			Map<String, com.goochou.p2b.hessian.Service> map = (Map<String, com.goochou.p2b.hessian.Service>) OpenApiApp.SPRING_CONTEXT.getBean("interfaceMap");
	    	Iterator<Map.Entry<String, com.goochou.p2b.hessian.Service>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, com.goochou.p2b.hessian.Service> entry = it.next();
				if(sm.getMethod().equals(entry.getKey())){
					Service s = entry.getValue();
					try {
						Class<?> cl = Class.forName(s.getRequest());
						sm.setReq((Request)JSONAlibabaUtil.toBean(JSONObject.parseObject(req), cl));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			if(TestEnum.RELEASE.equals(Constants.TEST_SWITCH)){
				//验证签名
				Map<?,?> mapParams = null;
				try {
					mapParams = BeanToMapUtil.convertBean(sm.getReq());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				if(null != map){
					if (!AppUtil.checkSign(sm.getSign(), mapParams , Constants.APP_MD5_KEY)) {
						log.error("签名验证未通过");
						sm = null;
					}
				}
			}
			Response result =  TransactionClient.getInstance()
	                .setServiceMessage(sm).send();
			try{
	        	pw = response.getWriter();
	        	pw.print(JSONArray.toJSONString(result));
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(null != pw){
					pw.close();
				}
			}
		}else{
			String name = request.getParameter("name");
			InterFaceRequest ifr = new InterFaceRequest();
			if(null != name && !"".equals(name)){
				ifr.setName(name);
			}
	        ServiceMessage msg = new ServiceMessage(Constants.INTERFACES, ifr);
	        InterFaceResponse result = (InterFaceResponse) TransactionClient.getInstance()
	                .setServiceMessage(msg).send();
	        try{
	        	pw = response.getWriter();
	        	if(null != name && !"".equals(name)){
	        		Service service = result.getInterfaces().get(0).getService();
	        		Class<?> impl = Class.forName(service.getRequest());
	        		List<ParamVO> req = new ArrayList<ParamVO>();
					Field[] fs = impl.getDeclaredFields();  
					for(int i = 0 ; i < fs.length; i++){
						Field f = fs[i];  
						f.setAccessible(true); //设置些属性是可以访问的 
						if(!f.getName().equals("serialVersionUID")){
							FieldMeta meta = f.getAnnotation(FieldMeta.class);
							if(null != meta){
								req.add(new ParamVO(f.getName(), f.getType().getSimpleName(), meta.description(), meta.have()));
							}
						}
					}
					List<ParamVO> res = new ArrayList<ParamVO>();
					impl = Class.forName(service.getResponse());
					fs = impl.getDeclaredFields();  
					for(int i = 0 ; i < fs.length; i++){
						Field f = fs[i];  
						f.setAccessible(true); //设置些属性是可以访问的 
						if(!f.getName().equals("serialVersionUID")){
							FieldMeta meta = f.getAnnotation(FieldMeta.class);
							if(null != meta){
								res.add(new ParamVO(f.getName(), f.getType().getSimpleName(), meta.description(), meta.have()));
							}
						}
					}
					pw.print("{\"req\":"+JSONSerializer.serialize(req)+", \"reqClass\":\""+service.getRequest()+"\", \"res\":"+JSONSerializer.serialize(res)+", \"resClass\":\""+service.getResponse()+"\"}");
	    		}else{
	    			List<InterfaceVO> inters = result.getInterfaces();
	    			Collections.sort(inters,new Comparator<InterfaceVO>(){
	    			    @Override
	    	            public int compare(InterfaceVO arg0, InterfaceVO arg1) {
	    	                return arg0.getName().compareTo(arg1.getName());
	    	            }
	    	        });
	        		pw.print(JSONAlibabaUtil.listToSJson(inters));
	        	}
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }finally{
				if(null != pw){
					pw.close();
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public ServiceMessage getRequestParams(String message){
    	ServiceMessage sm = null;
    	if (!StringUtil.isNull(message)) {
    		log.info("request:"+message);
			sm = (ServiceMessage) JSONAlibabaUtil.toBean(com.alibaba.fastjson.JSONObject.parseObject(message), ServiceMessage.class);
			if(TestEnum.RELEASE.equals(Constants.TEST_SWITCH)){
				//验证签名
				Map map = null;
				try {
					map = BeanToMapUtil.convertBean(sm.getReq());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				if(null != map){
					if (!AppUtil.checkSign(sm.getSign(), map , Constants.APP_MD5_KEY)) {
						log.error("签名验证未通过");
						sm = null;
					}
				}
			}
		}
    	return sm;
    }
}
