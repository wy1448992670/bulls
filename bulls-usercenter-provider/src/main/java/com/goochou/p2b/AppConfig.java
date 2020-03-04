package com.goochou.p2b;


import com.goochou.p2b.utils.PropertiesConfiguration;

/**
 * Created on 2014-8-21
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [APP config]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class AppConfig
{
	public static PropertiesConfiguration getConfig(String name)
	{
		if(OpenApiApp.SPRING_CONTEXT != null)
		{
			Object obj = OpenApiApp.SPRING_CONTEXT.getBean(name);
			if(obj instanceof PropertiesConfiguration)
			{
				return (PropertiesConfiguration)obj;
			}
		}
		return null;
	}
	
	public static String getConfigValue(String name, String key)
	{
		PropertiesConfiguration p = getConfig(name);
		if(p != null)
		{
			return p.get(key);
		}
		return null;
	}
	
	public static String getConfigValue(String name, String key, String def)
	{
		PropertiesConfiguration p = getConfig(name);
		if(p != null)
		{
			return p.get(key, def);
		}
		return null;
	}
	
	public static int getIntConfigValue(String name, String key)
	{
		PropertiesConfiguration p = getConfig(name);
		if(p != null)
		{
			return p.getIntConfig(key);
		}
		return 0;
	}
	
	public static int getIntConfigValue(String name, String key, int def)
	{
		PropertiesConfiguration p = getConfig(name);
		if(p != null)
		{
			return p.getIntConfig(key, def);
		}
		return def;
	}
	
	public static long getLongConfigValue(String name, String key)
	{
		PropertiesConfiguration p = getConfig(name);
		if(p != null)
		{
			return p.getLongConfig(key);
		}
		return 0;
	}
	
	public static long getLongConfigValue(String name, String key, long def)
	{
		PropertiesConfiguration p = getConfig(name);
		if(p != null)
		{
			return p.getLongConfig(key, def);
		}
		return def;
	}
	
	public static double getDoubleConfigValue(String name, String key)
	{
		PropertiesConfiguration p = getConfig(name);
		if(p != null)
		{
			return p.getDoubleConfig(key);
		}
		return 0;
	}
	
	public static double getDoubleConfigValue(String name, String key, double def)
	{
		PropertiesConfiguration p = getConfig(name);
		if(p != null)
		{
			return p.getDoubleConfig(key, def);
		}
		return def;
	}
}
