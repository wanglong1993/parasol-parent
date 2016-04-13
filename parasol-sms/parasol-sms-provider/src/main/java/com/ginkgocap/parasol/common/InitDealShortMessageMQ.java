package com.ginkgocap.parasol.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.ginkgocap.parasol.sms.model.ShortMessage;
import com.ginkgocap.parasol.sms.model.SmsTemplate;
import com.ginkgocap.ywxt.framework.dal.cache.CacheFactory;
import com.ginkgocap.ywxt.framework.dal.cache.exception.CacheException;
import com.ginkgocap.ywxt.framework.dal.cache.memcached.RemoteCacheFactoryImpl;

public class InitDealShortMessageMQ {
	
    @Resource
    private MongoTemplate mongoTemplate;
    
    @Resource
    private CommonService commonService;
    
    @Resource(name="smsTemplate")
    private SmsTemplate	 smsTemplate;
    
    @Resource(name="smsTemplateCoopert")
    private SmsTemplate	 smsTemplateCoopert;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    

    public void init() {
    	// 创建新的线程处理消息队列
    	new Thread(new Runnable(){
					@Override
					public void run() {
						initDealMessage();
					}}).start();
    } 
    
    public void initDealMessage() {
    	CacheFactory rc = RemoteCacheFactoryImpl.getInstance();
    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ShortMessage sm = null;
    	int i = 0;
    	while (true) {
    		try {
    			sm = (ShortMessage) rc.getCache("sms-queue").get("shortMessage1");
    			// 判断队列中数据是否有效
    			if(sm != null && StringUtils.isNotEmpty(sm.getPhoneNum()) && StringUtils.isNotEmpty(sm.getContent())) {
    				logger.info("短信发送手机号：{},content：{}", sm.getPhoneNum(), sm.getContent());
    				if(smsTemplate.getIsOpen().equals("1")) {
    					if(sm.getType()==1)i = sendMsg(sm.getPhoneNum(),sm.getContent());
    					if(sm.getType()==2)i = sendMsgCoopert(sm.getPhoneNum(),sm.getContent());
    					if(i==1) {
    						logger.info("短信发送成功");
    						sm.setId(commonService.getShortMessageIncreaseId());
    						sm.setCompleteTime(format.format(new Date()));
    						mongoTemplate.save(sm);
    					}else {
    						logger.warn("短信发送失败，手机号是否是空号！");
    					}
    				}
    			} else {
    				// 当队列中无有效数据时，暂停3s；
    				Thread.sleep(3000);
    			}
			} catch (CacheException e) {
				logger.error("从memcacheq获取数据失败：{}", e.toString());
				e.printStackTrace();
			
			} catch (Exception e) {
				logger.error("处理短信队列失败，异常："+e.toString());
				// 出异常后，休眠30s
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} finally {
				sm = null;
			}
    	}
    }
    
    /**
     * 美联软通国际 签订日期2015-04-15 仅支持国内版
     * 平台地址：http://m.5c.com.cn        国内
	 * 帐号：gintong
	 * 密码：ml150414  
	 * @author zhangzhen
     * @throws IOException 
     * 注意: 内容中必须带 "验证码" 三个字，发送会更快
     * */
    private int sendMsg(String mobile, String msg) throws IOException {
		
    	int result = 1;
    	
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(smsTemplate.getSendMessageUrl());
		
		// APIKEY
		sb.append("apikey="+smsTemplate.getSendMessageApiKey());
		
		//用户名
		sb.append("&username="+smsTemplate.getSendMessageUsername());

		// 向StringBuffer追加密码
		sb.append("&password="+smsTemplate.getSendMessagePassword());

		// 向StringBuffer追加手机号码
		sb.append("&mobile="+mobile);

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content="+URLEncoder.encode(msg,"GBK"));

		// 创建url对象
		URL url = new URL(sb.toString());
		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");
		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		// 返回发送结果
		String inputline = in.readLine();

		// 输出结果
		logger.info("mobile send sms result:{}",inputline);
		
		//反馈判断
		if(!inputline.contains("success")) {
			result = 0;
		}
		
		return result;
    }
    /**
     * 迈远平台短信发送
     * 平台地址：http://115.29.196.232:8888/sms.aspx
     * @author liurenyuan
     * @throws IOException 
     * */
    private int sendMsgCoopert(String Mobile, String Content) throws IOException {
    	
    	int result = 1;
    	//平台Base URL
        // 由平台提供 http://{IP}:{port}/{version}
        String baseUrl = smsTemplateCoopert.getSendMessageUrl();
        // 用户ID
        String UserId = smsTemplateCoopert.getSendMessageUserId();
        //发送用户帐号
        String Account = smsTemplateCoopert.getSendMessageUsername();
        //帐号密码
        String Password = smsTemplateCoopert.getSendMessagePassword();
        //全部被叫号码 
//        String Mobile = "13716683972";
        //发送内容 
//        String Content = "【coopert】你的注册验证码为：123456。30分钟内有效，请尽快完成验证";
        //定时发送时间
        String SendTime = "";
        //发送任务命令 
        String Action = "send";
        //是否检查内容包含非法关键字 
        String CheckContent = "1";
        //任务名称 
        String TaskName = "微信号绑定手机";
        //号码总数量 
        String CountNumber = "1";
        //手机号码数量  
        String MobileNumber = "1";
        //小灵通或座机号码数
        String TelephoneNumber = "0";
	    BufferedReader bufferedReader = null;
	    String responseResult = "";
	    HttpURLConnection httpURLConnection = null;					//创建HttpURLConnection
	    
	    try {			
	    	//拼接URL
	    	String url=String.format("%s?action=%s&userid=%s&account=%s&password=%s&mobile=%s&content=%s&sendTime=%s&taskName=%s&checkcontent=%s&mobilenumber=%s&countnumber=%s&telephonenumber=%s",
	    			baseUrl,Action,UserId,Account,Password,Mobile,URLEncoder.encode(Content, "utf-8"),SendTime,URLEncoder.encode(TaskName, "utf-8"),CheckContent,MobileNumber,CountNumber,TelephoneNumber);
	        URL realUrl = new URL(url);							
	        // 打开和URL之间的连接
	        httpURLConnection = (HttpURLConnection) realUrl.openConnection();
	        httpURLConnection.setDoOutput(true);
	        httpURLConnection.setDoInput(true);
	        // 根据ResponseCode判断连接是否成功
	        int responseCode = httpURLConnection.getResponseCode();
	        if (responseCode != 200) {
	            responseResult = " Error===" + responseCode;
	        } 
	        // 定义BufferedReader输入流来读取URL的ResponseData
	        bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	            responseResult += line;
	        }
	    } catch (Exception e) {
	        responseResult = "send post request error!" + e;
	    } finally {
	        httpURLConnection.disconnect();
	        try {
	            if (bufferedReader != null) {
	                bufferedReader.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	
	    }
	    //反馈判断
	    if(!responseResult.contains("Success")) {
	    	result = 0;
	    }
    	return result;
    }

}

