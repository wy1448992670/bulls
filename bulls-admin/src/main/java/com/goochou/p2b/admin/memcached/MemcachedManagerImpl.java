package com.goochou.p2b.admin.memcached;

import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

//@Service("cache")
@Deprecated
public class MemcachedManagerImpl implements IMemcachedManager
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

	@Override
	public boolean addOrReplace(String key, Object value) {
		try {
			if (null != get(key)) {
				return this.memcachedClient.replace(key, 0, value);
			} else {
				return this.memcachedClient.add(key, 0, value);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
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

	@Override
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

	@Override
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

	@Override
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
	
	@Override
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
	
	@Override
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
			if (null != get(key)) {
				return this.memcachedClient.replace(key, expireInSeconds,value);
			} else {
				return this.memcachedClient.add(key, expireInSeconds,value);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
