package com.ginkgocap.parasol.organ.web.jetty.web.utils.cache;


import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Administrator
 * 缓存操作类
 */
@Service
public class TicketCache {

    private static final Logger logger = LoggerFactory.getLogger(TicketCache.class);

    @Resource
    com.ginkgocap.parasol.organ.web.jetty.web.utils.cache.RedisCacheManager redisCacheManager;

    public boolean putLoginTicket(String loginTicket, Object value) {
        try {
            return redisCacheManager.put(loginTicket, value);
        } catch (Exception e) {
            logger.error("[ERROR]-保存login ticket到缓存错误 " + e.toString());
            return false;
        }
    }

    public String getLoginTicket(String key) {
        Object value = redisCacheManager.get(key);
        if(value != null){
            return (String)value;
        }
        return null;
    }

    public boolean removeLoginTicket(String key) {
        return redisCacheManager.remove(key);
    }

    public boolean putTGT(String tgt, Object value) {
        try {
            return redisCacheManager.put(tgt, value);
        } catch (Exception e) {
            logger.error("[ERROR]-保存TGT失败到缓存错误 " + e.toString());
            return false;
        }
    }

    public String getTGT(String key) {
        Object value = redisCacheManager.get(key);
        if(value != null){
            return (String)value;
        }
        return null;
    }

    public boolean removeTGT(String key) {
        return redisCacheManager.remove(key);
    }

    public boolean putServiceTicket(String serviceTicket, Object value) {
        try {
            return redisCacheManager.put(serviceTicket, value);
        } catch (Exception e) {
            logger.error("[ERROR]-保存service Ticket到缓存错误 " + e.toString());
            return false;
        }
    }

    public String getServiceTicket(String key) {
        Object value = redisCacheManager.get(key);
        if(value != null){
            return (String)value;
        }
        return null;
    }

    public boolean removeServiceTicket(String key) {
        return redisCacheManager.remove(key);
    }

    public boolean putLogoffST(String st, Object value) {
        try {
            return redisCacheManager.put(st, value);
        } catch (Exception e) {
            logger.error("[ERROR]-保存退出时需要验证的service Ticket到缓存错误 " + e.toString());
            return false;
        }
    }

    public String getLogOffServiceTicket(String key) {
        Object value = redisCacheManager.get(key);
        if(value != null){
            return (String)value;
        }
        return null;
    }

    public boolean removeLogOffServiceTicket(String key) {
        return redisCacheManager.remove(key);
    }

    public boolean putAccessingSys(String sys, Object value) {
        try {
            return redisCacheManager.put(sys, value);
        } catch (Exception e) {
            logger.error("[ERROR]-保存正在访问的系统标识到缓存错误 " + e.toString());
            return false;
        }
    }

    public Map<String, String> getAccessingSys(String key) {
        Object value = redisCacheManager.get(key);
        if(value != null){
            return (Map<String, String>)value;
        }
        return null;
    }

    public boolean removeAccessingSys(String key) {
        return redisCacheManager.remove(key);
    }

    public boolean putIdentifyCode(String key, Object value) {
        try {
            return redisCacheManager.put(key, value);
        } catch (Exception e) {
            logger.error("[ERROR]-保存验证码到缓存错误 " + e.toString());
            return false;
        }
    }

    public String getIdentifyCode(String key) {
        Object value = redisCacheManager.get(key);
        if(value != null){
            return (String)value;
        }
        return null;
    }

    public boolean removeIdentifyCode(String key) {
        return redisCacheManager.remove(key);
    }

    public boolean putSessionId(String key, Object value) {
        try {
            return redisCacheManager.put(key, value);
        } catch (Exception e) {
            logger.error("[ERROR]-保存已登录的系统的sessionId到缓存错误 " + e.toString());
            return false;
        }
    }

    public String getSessionId(String key) {
        Object value = redisCacheManager.get(key);
        if(value != null){
            return (String)value;
        }
        return null;
    }

    public boolean removeSessionId(String key) {
        return redisCacheManager.remove(key);
    }
}
