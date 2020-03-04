package com.goochou.p2b.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONAlibabaUtil 
{
	public static String parseMap(Map<String, Object> map)
	{
		try{
			return mapToSJson(map);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
//		try{
//			return JSONObject.fromMap(map).toString();
//		}catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
	}
	
	public static JSONObject parseString(String jsonString)
	{
		try{
			return JSON.parseObject(jsonString);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JSONArray parseJSONStrtoArray(JSONObject jsonObj,String str)
	{
		try{
			return jsonObj.getJSONArray(str);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List toList(String array, Class cla)
	{
		try{
			return JSON.parseArray(array, cla);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object toBean(JSONObject obj, Class cla)
	{
		try{
			return JSON.parseObject(obj.toJSONString(), cla);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static Object toBean(JSONObject obj, String key,Class cla)
	{
		try{
			return JSON.parseObject(obj.getString(key), cla);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static JSONObject getJson(JSONObject obj, String key)
	{
		if(obj==null) return null;
		try{
			return obj.getJSONObject(key);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static  List toList(JSONObject obj,String key, Class cla){
		try {
			return toList(obj.getString(key),cla);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static JSONObject getJson(JSONObject obj,String...  keys){
		try{
			for (int i = 0; i < keys.length; i++) {
				obj=obj.getJSONObject(keys[i]);
			}
			return obj;
		}catch (Exception e) {
			return null;
		}
	}
	
	public static JSONObject getJson(String objstr,String...  keys){
		try{
			JSONObject obj=parseString(objstr);
			for (int i = 0; i < keys.length; i++) {
				obj=obj.getJSONObject(keys[i]);
			}
			return obj;
		}catch (Exception e) {
			return null;
		}
	}
	public static String getJsonStr(JSONObject obj,String...  keys){
		try{
			for (int i = 0; i < keys.length-1; i++) {
				obj=obj.getJSONObject(keys[i]);
			}
			return obj.getString(keys[keys.length-1]);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static String getJsonStr(String jsonStr,String...  keys){
		JSONObject obj = parseString(jsonStr);
		try{
			for (int i = 0; i < keys.length-1; i++) {
				obj=obj.getJSONObject(keys[i]);
			}
			return obj.getString(keys[keys.length-1]);
		}catch (Exception e) {
			return null;
		}
	}
	
	//首字母大写情况下需此方法解析com.alibaba.fastjson.JSONArray
	public static List parse2List(String jsonStr,Class cla)
	{
		try{
			return JSON.parseArray(jsonStr, cla);
		}catch (Exception e) 
		{
			return null;
		}
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
//		Map map = new HashMap();
//		map.put("Strr", "strr");
//		map.put("sd", "11");
//		map.put("FF", "33");
//		CatalogValueInfo info=new CatalogValueInfo();
//		List<CatalogValueInfo> list=new ArrayList<CatalogValueInfo>();
////		info.setAHeadOfTime("122");
////		info.setBusiType("1");
//		list.add(info);
//		list.add(info);
//		map.put("info", info);
//		map.put("list", list);
//		map.put("strs", new String[]{"2","3"});
//		map.put("strss", new String[][]{{"2"},{"2"}});
//		System.out.println(mapToSJson(map));
		
	}
	
	/**
	 * map转json字符串
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static String mapToSJson(Map map) throws Exception {
		 boolean bool=false;
		 Set set =map.keySet();
		 Iterator it=set.iterator();
		 StringBuffer jsonStr=new StringBuffer("{");
		 Object key=null;
		 Object value=null;
		 while (it.hasNext()) {
			 key=it.next().toString();
			 value=map.get(key);
			 assembleJsonConsle(jsonStr, key, value);
			 bool=true;
		 }
		 if(bool)jsonStr.deleteCharAt(jsonStr.length()-1);
		 jsonStr.append("}");
		return jsonStr.toString();
	}
	
	/***
	 * bean转json字符串
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public static String beanToSJson(Object obj) throws Exception{
		boolean bool=false;
		Class clazz=obj.getClass();
		Field[] fields=clazz.getDeclaredFields();
		String key="";
		Object value=null;
		StringBuffer jsonStr=new StringBuffer("{");
		for (int i = 0; i < fields.length; i++) {
			key=fields[i].getName();
			if(key.equals("serialVersionUID")){
				continue;
			}
			value=invoke(obj, fields[i].getClass(), getGFunction(key), null);
			assembleJsonConsle(jsonStr, key, value);
			bool=true;
		}
		if(bool)jsonStr.deleteCharAt(jsonStr.length()-1);
		jsonStr.append("}");
		return jsonStr.toString();
	}
	
	/**
	 * lis转json字符串
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	public static String listToSJson(List list) throws Exception{
		StringBuffer jsonStr=new StringBuffer("[");
		boolean bool=false;
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				jsonStr.append("");
				jsonStr.append(beanToSJson(list.get(i)));
				jsonStr.append(",");
				bool=true;
			}
		}
		if(bool)jsonStr.deleteCharAt(jsonStr.length()-1);
		jsonStr.append("]");
		return jsonStr.toString();
	}
	/**
	 * 返回类型
	 * @param clazz
	 * @return
	 */
	private static int getType(Object obj){
		String type=obj.getClass().getSimpleName();
		if("String[][]".equals(type))return 0;
		if("String[]".equals(type))return 1;
		if("String".equals(type))return 2;
		if("Integer".equals(type))return 2;
		if("Long".equals(type))return 2;
		if("Short".equals(type))return 2;
		if("Double".equals(type))return 2;
		if(obj instanceof Map)return 3;
//		if(type.indexOf("Map")>-1)return 3;
		if(obj instanceof List)return 4;
//		if(type.indexOf("List")>-1)return 4;
		else return 5;
	} 
	
	
	/**
	 * 组装控制方法
	 * @param jsonStr
	 * @param key
	 * @param value
	 * @param type 1是对象 0是String
	 * @return
	 * @throws Exception 
	 */
	private static StringBuffer assembleJsonConsle(StringBuffer jsonStr,Object key,Object value) throws Exception{
		 if(value==null)return jsonStr;
		 int type=getType(value);
		 switch (type) {
				case 0:
				case 1:	
					assembleJsonStr(jsonStr, key, getSString(value, type), 1);
					break;
				case 2:
					assembleJsonStr(jsonStr, key, value, 0);
					break;
				case 3:
					assembleJsonStr(jsonStr, key, mapToSJson((Map)value), 1);
					break;
				case 4:
					assembleJsonStr(jsonStr, key, listToSJson((List)value), 1);
					break;
				case 5:
					assembleJsonStr(jsonStr, key, beanToSJson(value), 1);
					break;
				default:
					break;
			}
		 return jsonStr;
	}
	/**
	 * 组装
	 * @param jsonStr
	 * @param key
	 * @param value
	 * @param type 1是对象 0是String
	 * @return
	 */
	private static StringBuffer assembleJsonStr(StringBuffer jsonStr,Object key,Object value,int type){
		if(type==1)return jsonStr.append("\"").append(key).append("\":").append(value).append(",");
		else return jsonStr.append("\"").append(key).append("\":\"").append(value).append("\",");
	}
	
	/**
	 * 组装数组
	 * @param obj
	 * @param type
	 * @return
	 */
	private static StringBuffer getSString(Object obj,int type){
	   StringBuffer str=new StringBuffer("[");
	   boolean bool=false;
	   if (type==0 &&  obj!=null ) {
		   String[][] strs=(String[][]) obj;
		   for (int i = 0; i < strs.length; i++) {
			   str.append("[");
				if(strs[i]!=null)
					for (int j = 0; j < strs[i].length; j++) {
						str.append("\"").append(strs[i][j]).append("\",");
						bool=true;
					}
			   if(bool) str.deleteCharAt(str.length()-1);
			   str.append("],"); 
			   bool=true;
		   }
	   }else if(obj!=null){
		   String[] strs=(String[]) obj;
		   for (int j = 0; j < strs.length; j++) {
				str.append("\"").append(strs[j]).append("\",");
				bool=true;
			}
	   }
	   if(bool) str.deleteCharAt(str.length()-1);
	   str.append("]");
	   return str;
   }
	
	/**
	 * 获取get方法名称
	 * @param attr
	 * @return
	 */
   private static String getGFunction(String attr){
		return "get"+attr.substring(0,1).toUpperCase()+attr.substring(1);
	} 
	
	/**
	 * 获取set方法名称
	 * @param attr
	 * @return
	 */
	private static String getSFunction(String attr){
		return "set"+attr.substring(0,1).toUpperCase()+attr.substring(1);
	} 
	/**
	 * 调用方法
	 * @param clazz
	 * @param function
	 * @param objs
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	private static Object invoke(Object obj,Class attrType,String function,Object attr) throws Exception{
		Method method=obj.getClass().getMethod(function);
		return method.invoke(obj);
	} 

	/**
	 * 
	 *  Created on 2015年11月2日 
	 * <p>Discription:[不定类型的Integer值返回Integer]</p>
	 * @author:[李旭东]
	 * @update:[日期2015年11月2日] [李旭东]
	 * @return Integer .
	 */
	public static Integer getInt(JSONObject json, String key){
		if (json == null || key == null) return null;
		if (json.get(key) == null) return null;
		Object val = json.get(key);
		if (val instanceof String) {
			return Integer.valueOf((String)val);
		}
		if (val instanceof Integer) {
			return (Integer) val;
		}
		return Integer.valueOf(val.toString());
	}
}
