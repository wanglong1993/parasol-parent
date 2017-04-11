package com.ginkgocap.parasol.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 美联软通辅助功能
 * */
public class MessageUtil {
	
	 private final static String SEND_MESSAGE_APIKEY = "abded701459c50a016766b4a9e823a39";
	 private final static String SEND_MESSAGE_USERNAME = "gintong";
	 private final static String SEND_MESSAGE_PASSWORD = "ml150414";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		BalanceInquiry();
		
		sendMsg("13501317970");
	
	}
	
	/**
	 * 查询美联软通的余额条数
	 * 2015-04-15 本次总共30110条
	 * 例：返回结果为：30029.000/30110.000 剩余条数/总条数
	 * */
	 private static void BalanceInquiry() throws IOException {
			
			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer("http://m.5c.com.cn/api/query/?");
			
			// APIKEY
			sb.append("apikey="+SEND_MESSAGE_APIKEY);
			
			//用户名
			sb.append("&username="+SEND_MESSAGE_USERNAME);

			// 向StringBuffer追加密码
			sb.append("&password="+SEND_MESSAGE_PASSWORD);

			// 输出结果
			System.out.println("金桐网剩余短信条数："+result(sb.toString()));
			
	    }
	 
	 /**
	  * 临时测试手机号方法
	  * */
	 private static void sendMsg(String mobile) throws IOException {
			
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
			sb.append("&content="+URLEncoder.encode("验证码:金桐脑临时测试111111111111111111111111111111","GBK"));

			System.out.println(result(sb.toString()));
	    }
	 
	 /**
	  * 公共反馈结果
	 * @throws IOException 
	  * */
	 private static String result(String sendUrl) throws IOException {
		 
		 	URL url = new URL(sendUrl);

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// 返回发送结果
			return in.readLine();
			
	 }

}
