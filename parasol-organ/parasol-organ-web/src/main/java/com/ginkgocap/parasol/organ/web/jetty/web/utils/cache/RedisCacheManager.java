
package com.ginkgocap.parasol.organ.web.jetty.web.utils.cache;

import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RedisCacheManager{
	
	private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
	
    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Autowired
    private com.ginkgocap.ywxt.cache.Cache cache;

    // 设置redis默认失效时间，防止永久存在redis中
    private int expiredTime = 3600;

    public Object get(String key) throws CacheException {
		try {
			if (key == null) {
				return null;
			} else {
				logger.debug("cache get key:" + key);
				return cache.get(key);

			}
		} catch (Exception t) {
			logger.error("cache get key:" + key + " error " + t.toString());
			throw new CacheException(t);
		}
	}

	public boolean put(String key, Object value) throws CacheException {
		try {
			logger.debug("cache set key:" + key + ";Value=" + value.toString());
			return this.cache.set(key, expiredTime, value);
		} catch (Exception t) {
			logger.error("cache put key:" + key + t.toString());
			throw new CacheException(t);
		}
	}

	public boolean remove(String key) throws CacheException {
		try {
			logger.debug("cache delete key:" + key + ";");
			return this.cache.remove((String) key);
		} catch (Exception t) {
			logger.error("cache remove key:" + key + t.toString());
			throw new CacheException(t);
		}
	}
    

}
