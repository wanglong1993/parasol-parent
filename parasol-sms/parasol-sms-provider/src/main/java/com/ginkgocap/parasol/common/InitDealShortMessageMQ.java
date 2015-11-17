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
    
    @Resource
    private SmsTemplate	smsTemplate;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    

    public void init() {
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
    				if(smsTemplate.getIsOpen().equals("1")) {
    					i = sendMsg(sm.getPhoneNum(),sm.getContent());
    					if(i==1) {
    						logger.info("短信发送成功");
    						sm.setId(commonService.getShortMessageIncreaseId());
    						sm.setCompleteTime(format.format(new Date()));
    						mongoTemplate.save(sm);
    					}else {
    						// 发送失败时，将短信队列从新放入队列中
    						rc.getCache("sms-queue").save("shortMessage1", sm);
    						logger.warn("短信发送失败，请检查短信运营商配置！");
    					}
    				}
    			} else {
    				// 当队列中无有效数据时，暂停3s；
    				Thread.sleep(3000);
    			}
			} catch (CacheException e) {
				logger.warn("从memcacheq获取数据失败：{}", e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				logger.warn("调用短信运营商发送接口失败：{}", e.toString());
				e.printStackTrace();
			} catch (InterruptedException e) {
				logger.warn("执行发送短信线程被中断：{}", e.toString());
				e.printStackTrace();
			} finally {
				sm = null;
			}
    	}
    }
    
    public void dealMessageTest() {
    	System.out.println("what a fucking day!!");
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

}

