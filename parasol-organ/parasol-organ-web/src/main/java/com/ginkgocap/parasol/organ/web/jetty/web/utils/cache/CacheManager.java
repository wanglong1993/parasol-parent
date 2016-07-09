package com.ginkgocap.parasol.organ.web.jetty.web.utils.cache;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**   
* @Title: CacheManager.java 
* @Package com.ginkgocap.ywxt.utils.cache 
* @Description: 
* @author haiyan   
* @date 2015-5-29 下午5:04:08    
*/
@Service
public class CacheManager {

    @Resource
    TicketCache ticketCache;

    public boolean add(CacheType type, String key, Object value) {
        switch (type) {

        case LOGIN_TICKET:
            return ticketCache.putLoginTicket(key, value);

        case TGT:
            return ticketCache.putTGT(key, value);

        case SERVICE_TICKET:
            return ticketCache.putServiceTicket(key, value);

        case LOGOFF_SERVICE_TICKET:
            return ticketCache.putLogoffST(key, value);

        case SESSION_ID:
            return ticketCache.putSessionId(key, value);

        case ACCESSING_SYS:
            return ticketCache.putAccessingSys(key, value);

        case IDENTIFY_CODE:
            return ticketCache.putIdentifyCode(key, value);
        }
        return false;
    }

    public String get(CacheType type, String key) {
        switch (type) {

        case LOGIN_TICKET:
            return ticketCache.getLoginTicket(key);

        case TGT:
            return ticketCache.getTGT(key);

        case SERVICE_TICKET:
            return ticketCache.getServiceTicket(key);

        case LOGOFF_SERVICE_TICKET:
            return ticketCache.getLogOffServiceTicket(key);

        case SESSION_ID:
            return ticketCache.getSessionId(key);

        case IDENTIFY_CODE:
            return ticketCache.getIdentifyCode(key);
        }
        return null;
    }

    public Map<String, String> getMap(CacheType type, String key) {
        switch (type) {
        
        case ACCESSING_SYS:
            return ticketCache.getAccessingSys(key);
        }
        return null;
    }

    public boolean remove(CacheType type, String key) {
        switch (type) {

        case LOGIN_TICKET:
            return ticketCache.removeLoginTicket(key);

        case TGT:
            return ticketCache.removeTGT(key);

        case SERVICE_TICKET:
            return ticketCache.removeServiceTicket(key);

        case LOGOFF_SERVICE_TICKET:
            return ticketCache.removeLogOffServiceTicket(key);

        case SESSION_ID:
            return ticketCache.removeSessionId(key);

        case ACCESSING_SYS:
            return ticketCache.removeAccessingSys(key);

        case IDENTIFY_CODE:
            return ticketCache.removeIdentifyCode(key);
        }
        return false;
    }
}
