package com.goochou.p2b.admin.memcached;

@Deprecated
public interface IMemcachedManager
{
	Object get(String key);
	
	boolean add(String key, Object value);
	
	boolean add(String key, Object value, int expireInSeconds);
	
	boolean delete(String key);
	
	boolean replace(String key, Object value);
	
	boolean replace(String key, Object value, int expireInSeconds);
	
	public boolean addOrReplace(String key, Object value);
	
	public boolean addOrReplace(String key, Object value, int expireInSeconds);
}
