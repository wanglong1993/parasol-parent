package com.ginkgocap.parasol.sms.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.CommonService;
import com.ginkgocap.parasol.sms.model.ShortMessage;
import com.ginkgocap.parasol.sms.service.ShortMessageService;
import com.ginkgocap.ywxt.framework.dal.cache.CacheFactory;
import com.ginkgocap.ywxt.framework.dal.cache.exception.CacheException;
import com.ginkgocap.ywxt.framework.dal.cache.memcached.RemoteCacheFactoryImpl;

/**
 * 
* <p>Title: ShortMessageServiceImpl.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-11 
* @version 1.0
 */
@Service("shortMessageService")
public class ShortMessageServiceImpl implements ShortMessageService {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private MongoTemplate mongoTemplate;
    
    @Resource
    private CommonService commonService;
    
    @Override
    public int sendMessage(String phoneNum, String content, long uid, int type) {
        logger.debug("{} send a sms to {}:{}", uid, phoneNum, content);
        //调用校验手机号和短信内容
        int flag = validateMobileAndMsg(phoneNum, content);
        if(flag == 1){//校验通过	
        	ShortMessage sm = new ShortMessage();
        	sm.setId(commonService.getShortMessageIncreaseId());
        	sm.setPhoneNum(phoneNum);
        	sm.setUid(uid);
        	sm.setContent(content);
        	sm.setType(type);
        	Date date=new Date();
        	DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String createTime=format.format(date);
        	sm.setCreateTime(createTime);
        	mongoTemplate.save(sm);
			try {
				CacheFactory rc = RemoteCacheFactoryImpl.getInstance();
				flag = rc.getCache("sms-queue").save("shortMessage"+type, sm) ? 1:0;
				
			} catch (CacheException e) {
				logger.error("send sms result:{}",flag);
			}
        }
        logger.info("send sms result:{}",flag);
        return flag;
    }

    /**校验手机号和短信内容是不是合法
     * @param mobile 手机号 1开始的11位数字即可  多个手机号使用逗号分隔
     * @param msg 短信内容 可以是纯空格
     * @return 1:校验通过   -1手机号不符合规范  -2短信内容为空
     */
    private int validateMobileAndMsg(String mobile, String msg){
        int flag = 1;
        String regex = "^1\\d{10}(,1\\d{10})*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);
        if(!matcher.matches()){//验证手机号是不是符合要求，只要1开始，11位即可
            flag = -1;
        }
        if(flag == 1 && StringUtils.isEmpty(msg)){//验证内容是不是为空，可以发送纯空格
            flag = -2;
        }
        return flag;
    }
    
}
