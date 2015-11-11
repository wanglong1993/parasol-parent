package com.ginkgocap.parasol.sms.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    
    private final static String SEND_MESSAGE_APIKEY = "abded701459c50a016766b4a9e823a39";
    private final static String SEND_MESSAGE_USERNAME = "gintong";
    private final static String SEND_MESSAGE_PASSWORD = "ml150414";
    
    @Override
    public int sendMessage(String mobile, String msg) {
        logger.debug("send a sms to {}:{}",mobile,msg);
        //调用校验手机号和短信内容
        int flag = validateMobileAndMsg(mobile, msg);
        if(flag == 1){//校验通过
            try {
				flag = sendMsg(mobile, msg);
			} catch (IOException e) {
				flag = -1;
			}
        }
        logger.debug("send sms result:{}",flag);
        logger.info("send sms result:{}",flag);
        return flag;
    }

    @Override
    public int sendMiniMessage(String mobile, String msg) {
        logger.debug("send a mini sms to {}:{}",mobile,msg);
        //调用校验手机号和短信内容
        int flag = validateMobileAndMsg(mobile, msg);
        if(flag == 1){//校验通过
            try {
				flag = sendMsg(mobile, msg);
			} catch (IOException e) {
				flag = -1;
			}
        }
        logger.debug("send mini sms result:{}",flag);
        logger.info("send mini sms result:{}",flag);
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
		StringBuffer sb = new StringBuffer("http://m.5c.com.cn/api/send/?");
		
		// APIKEY
		sb.append("apikey="+SEND_MESSAGE_APIKEY);
		
		//用户名
		sb.append("&username="+SEND_MESSAGE_USERNAME);

		// 向StringBuffer追加密码
		sb.append("&password="+SEND_MESSAGE_PASSWORD);

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
		logger.debug("mobile send sms result:{}",inputline);
		logger.info("mobile send sms result:{}",inputline);
		
		//反馈判断
		if(!inputline.contains("success")) {
			
			result = 0;
		
		}
		
		return result;
    }
    
    public static void main(String[] args) throws CacheException {
    	try {
    		System.out.print("aaaa");
			MemcachedClient mm = new MemcachedClient(new InetSocketAddress("192.168.130.21", 22201));
			CacheFactory rc = RemoteCacheFactoryImpl.getInstance();
			Object o1 = rc.getCache("q4").save("q8", "what nice");
			Object o2 = rc.getCache("q4").get("q8");
			System.out.println("ddd="+o1.toString());
			System.out.println("eee="+o2.toString());
			mm.set("q4", 0, "hello world");
			mm.set("q5", 0, "what a fucking day!");
			System.out.println("bbb");
			Object o = mm.get("q4");
			System.out.println("ccc="+o.toString());
			mm.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		} 
    }
    
}
