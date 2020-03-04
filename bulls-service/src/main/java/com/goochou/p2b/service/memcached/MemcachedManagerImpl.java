package com.goochou.p2b.service.memcached;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.Constants;

@Service
public class MemcachedManagerImpl implements MemcachedManager
{
	private static final Logger logger = Logger.getLogger(MemcachedManagerImpl.class);

	@Autowired
	private MemcachedClient memcachedClient;

    public MemcachedClient getMemcachedClient()
    {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient)
    {
        this.memcachedClient = memcachedClient;
    }

    public MemcachedManagerImpl()
	{
	}

	public void initialize()
	{
		
	}

	public boolean addOrReplace(String key, Object value) {
	
		return this.addOrReplace(key,value,0);
	
	}
	
	
	
	public boolean add(String key, Object value, int expireInSeconds)
	{
		try
        {
            return this.memcachedClient.add(key, expireInSeconds, value);
        }
        catch (TimeoutException e)
        {

            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
  
            e.printStackTrace();
        }
        catch (MemcachedException e)
        {
         
            e.printStackTrace();
        }
		return false;
	}

	public boolean add(String key, Object value)
	{
		try
        {
            return this.memcachedClient.add(key, 0, value);
        }
        catch (TimeoutException e)
        {
      
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {

            e.printStackTrace();
        }
        catch (MemcachedException e)
        {
         
            e.printStackTrace();
        }
		return false;
	}

	public boolean delete(String key)
	{
		try
        {
            return this.memcachedClient.delete(key);
        }
        catch (TimeoutException e)
        {
     
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
       
            e.printStackTrace();
        }
        catch (MemcachedException e)
        {
        
            e.printStackTrace();
        }
		return false;
	}

	public boolean replace(String key, Object value)
	{
		try
        {
            return this.memcachedClient.replace(key, 0, value);
        }
        catch (TimeoutException e)
        {
         
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
     
            e.printStackTrace();
        }
        catch (MemcachedException e)
        {
         
            e.printStackTrace();
        }
		return false;
	}
	
	public boolean replace(String key, Object value, int expireInSeconds)
	{
		try
        {
            return this.memcachedClient.replace(key, expireInSeconds, value);
        }
        catch (TimeoutException e)
        {
       
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
     
            e.printStackTrace();
        }
        catch (MemcachedException e)
        {
    
            e.printStackTrace();
        }
		return false;
	}
	
	public Object get(String key)
	{
		try
        {
            return this.memcachedClient.get(key);
        }
        catch (TimeoutException e)
        {
      
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
      
            e.printStackTrace();
        }
        catch (MemcachedException e)
        {
   
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public boolean addOrReplace(String key, Object value, int expireInSeconds) {
		try {
			return this.memcachedClient.set(key, expireInSeconds, value);
			/*
			if (null != get(key)) {
				return this.memcachedClient.replace(key, expireInSeconds,value);
			} else {
				return this.memcachedClient.add(key, expireInSeconds,value);
			}*/
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <p>获取缓存中tm_dict缓存对应key的键值对组</p>
	 * 一个key对应多条记录
	 * @param key
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Map<String, String> getCacheValue(String key){
		Map<String, Map<String, String>> map = null;
        Map<String, String> result = null;
        try{
        	map = (Map<String, Map<String, String>>)this.memcachedClient.get(com.goochou.p2b.constant.Constants.DICTS);
        }catch (TimeoutException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (MemcachedException e){
            e.printStackTrace();
        }
        
        if(map!=null){
        	result = map.get(key);
        }
		return result;
    }

	@Override
	@SuppressWarnings("unchecked")
	public String getCacheKeyValue(String key) {
		String val = null;
		Map<String, String> map=this.getCacheValue(key);
        if(map != null){
            Set<String> set = map.keySet();
            val = map.get(set.toArray()[0]);
        }
        return val;
	}
	
	/**
	 * <p>获取缓存中tm_dict缓存对应key的唯一值</p>
	 * @param key
	 * @return
	 */
	@Override
	public String getCacheKeySingleValue(String key) throws Exception {
		Map<String, String> map = this.getCacheValue(key);
		if(map==null) {
			return null;
		}
		if (map.entrySet().size() != 1) {
			throw new Exception("缓存值不止一条 无法取得单值 getCacheKeySingleValue");
		}
		return map.entrySet().iterator().next().getValue();
	}
}
