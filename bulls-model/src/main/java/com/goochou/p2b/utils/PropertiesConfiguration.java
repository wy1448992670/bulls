package com.goochou.p2b.utils;


import java.util.Properties;


/**
 * Created on 2014-8-21
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [资源文件属性处理类]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class PropertiesConfiguration
{
	private Properties config = new Properties();
	
	public String get(String key)
	{
		return this.getConfig(key);
	}
	
	public String get(String key, String def)
	{
		return this.getConfig(key, def);
	}
	
	public String getConfig(String key)
	{
		return this.config.getProperty(key);
	}
	
	public String getConfig(String key, String def)
	{
		String s = this.getConfig(key);
		return s == null ? def : s;
	}
	
	public int getIntConfig(String key)
	{
		String exp = this.getConfig(key, "0");
		return Integer.parseInt(exp);
	}
	
	public int getIntConfig(String key, int def)
	{
		String exp = this.getConfig(key, "" + def);
		return Integer.parseInt(exp);
	}
	
	public long getLongConfig(String key)
	{
		String exp = this.getConfig(key, "0");
		return Long.parseLong(exp);
	}
	
	public long getLongConfig(String key, long def)
	{
		String exp = this.getConfig(key, "" + def);
		return Long.parseLong(exp);
	}
	
	public double getDoubleConfig(String key)
	{
		String exp = this.getConfig(key, "0.0");
		return Double.parseDouble(exp);
	}
	
	public double getDoubleConfig(String key, double def)
	{
		String exp = this.getConfig(key, "" + def);
		return Double.parseDouble(exp);
	}

	//获取解密值
	public String getStringFromDES3Encrypt(String key){
	   return DES3Encrypt.getInstance().decrypt(key);
	}
	
	
	public Properties getConfig()
	{
		return config;
	}

	public void setConfig(Properties config)
	{
		this.config = config;
	}
}
