package com.goochou.p2b.service.memcached;

public interface MemcachedManager{
	Object get(String key);
	
	boolean add(String key, Object value);
	
	boolean add(String key, Object value, int expireInSeconds);
	
	boolean delete(String key);
	
	boolean replace(String key, Object value);
	
	boolean replace(String key, Object value, int expireInSeconds);
	
	public boolean addOrReplace(String key, Object value);
	
	public boolean addOrReplace(String key, Object value, int expireInSeconds);
	
	Object getCacheValue(String key);
	
	String getCacheKeyValue(String key);

	String getCacheKeySingleValue(String key) throws Exception;
}
