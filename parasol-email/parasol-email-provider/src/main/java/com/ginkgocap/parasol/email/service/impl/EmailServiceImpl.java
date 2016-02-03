package com.ginkgocap.parasol.email.service.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.cache.Cache;
import com.ginkgocap.parasol.cache.CacheModule;
import com.ginkgocap.parasol.email.exception.EmailServiceException;
import com.ginkgocap.parasol.email.model.Email;
import com.ginkgocap.parasol.email.service.EmailService;
import com.ginkgocap.parasol.email.util.SendHtmlMail;
import com.ginkgocap.parasol.email.util.TemplateUtils;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	@Resource
	private Cache cache;
    private Logger logger =Logger.getLogger(EmailServiceImpl.class);

    @Override
    public boolean sendEmailSync(String mailTo, String mailFrom, String mailTitle,String attachment,Map<String, Object> map,String template) throws EmailServiceException {
		try {
			String mailContext = TemplateUtils.mergeTemplateContent(template, map);
			String fromName="金桐网";
	        Email email = new Email(mailTo, mailFrom,fromName, mailTitle, mailContext, attachment);
	        if(isEmail(mailTo)){
//		        Object value=cache.get(cache.getCacheHelper().buildKey(CacheModule.REGISTER, mailTo));
//		        if(value!=null)return true;
		        if(sendEmailNoThread(email)){
		        	if(setCache(mailTo,map.get("code").toString()))return true; 
		        	else return false;
		        }
		        return false;
	        }else return false;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new EmailServiceException(e);
		}        
    }
    /**
     * 发送邮件
     * @param email
     * @return
     */
    private boolean sendEmailNoThread(Email email) {
        SendHtmlMail sender;
        try {
            sender = new SendHtmlMail(email);
            if(!StringUtils.isEmpty(email.getAttachment())){
            	sender.setAttach(email.getAttachment());
            }
            boolean result = sender.sendMail();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("sendEmailNoThread error", e);
            return false;
        }
    }
    /**
     * 验证是否是正常的邮箱
     * @param email
     * @return
     */
    private boolean isEmail(String email){     
        String str="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
           Pattern p = Pattern.compile(str);     
           Matcher m = p.matcher(email);     
           logger.info(m.matches()+"---");     
           return m.matches();     
    }
    
	/**
	 * 根据手机号设置缓存,时间为30分钟
	 * @param mobile
	 * @return boolean
	 */
	private  boolean setCache(String email,String flag){
		boolean bl =false;
		String key=cache.getCacheHelper().buildKey(CacheModule.REGISTER, email);
//		Object object =cache.get(key);
//		String value=null;
//		if(object!=null)value=object.toString();
//		if(StringUtils.isEmpty(value))
		bl = cache.set(key, 1 * 60 * 30, flag);
		return bl;
	}

}
