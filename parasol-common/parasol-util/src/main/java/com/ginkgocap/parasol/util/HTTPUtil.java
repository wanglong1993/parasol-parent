package com.ginkgocap.parasol.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HTTPUtil {

	private static Logger logger = LoggerFactory.getLogger(HTTPUtil.class);

	public static CloseableHttpClient httpClient;

	public static LinkedBlockingQueue<StandByUrl> urls;

	static {

		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
		httpClient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .build();
		urls = new LinkedBlockingQueue<StandByUrl>();
	}

	public static String post(String url, Map<String, String> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpPost post = new HttpPost(url);
		
		if (params != null) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			try {
				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = mapper.writeValueAsString(params);
				list.add(new BasicNameValuePair("json", jsonStr));
				UrlEncodedFormEntity formEntity;
				formEntity = new UrlEncodedFormEntity(list, "utf-8");
				post.setEntity(formEntity);
				logger.info("开始以POST方式访问路径{}，参数为{}", url, formEntity.toString());
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					result.put(Constants.status, response.getStatusLine()
							.getStatusCode());
					HttpEntity entity = response.getEntity();
					String respJson = EntityUtils.toString(entity);
					logger.info("访问路径{}成功", url);
					return respJson;
				}

			} catch (UnsupportedEncodingException e) {
				logger.error("编码错误", e);
			} catch (ClientProtocolException e) {
				logger.error("协议错误", e);
			} catch (IOException e) {
				logger.error("IO错误", e);
			} finally {
				post.releaseConnection();
			}
		}

		return null;
	}
	
	public static  String get(String url, Map<String, String> params) {

		HttpGet get = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(params);
			StringBuffer urlBuffer = new StringBuffer(url + "?json=" + jsonStr);
			urlBuffer.deleteCharAt(urlBuffer.length() - 1);
			get = new HttpGet(urlBuffer.toString());

			logger.info("开始以GET方式访问路径{}", get.getRequestLine().toString());
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String respJson = EntityUtils.toString(entity);
				logger.info("访问路径{}成功", url);
				return respJson;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}

		return null;
	}


	public static class StandByUrl {

		public static enum METHORD {
			POST, GET
		}

		private String url;

		private Map<String, String> params;

		private METHORD methord;

		public StandByUrl(String url, Map<String, String> params,
				METHORD methord) {
			super();
			this.url = url;
			this.params = params;
			this.methord = methord;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Map<String, String> getParams() {
			return params;
		}

		public void setParams(Map<String, String> params) {
			this.params = params;
		}

		public METHORD getMethord() {
			return methord;
		}

		public void setMethord(METHORD methord) {
			this.methord = methord;
		}

	}

	/** 移动端 zhangzhen add 2014-11-6 */
	public static String mobilePost(String url, Map<String, String> params) {

		Map<String, Object> result = new HashMap<String, Object>();
		HttpPost post = new HttpPost(url);
		if (params != null) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			try {
				
				for(Entry<String,String> entry : params.entrySet()){
					list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				
				UrlEncodedFormEntity formEntity;
				formEntity = new UrlEncodedFormEntity(list, "utf-8");
				post.setEntity(formEntity);
				logger.info("开始以POST方式访问路径{}，参数为{}", url, formEntity.toString());
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					result.put(Constants.status, response.getStatusLine()
							.getStatusCode());
					HttpEntity entity = response.getEntity();
					String respJson = EntityUtils.toString(entity);
					logger.info("访问路径{}成功", url);
					return respJson;
				}

			} catch (UnsupportedEncodingException e) {
				logger.error("编码错误", e);
			} catch (ClientProtocolException e) {
				logger.error("协议错误", e);
			} catch (IOException e) {
				logger.error("IO错误", e);
			} finally {
				post.releaseConnection();
			}
		}
		return null;
	}
	
	
	
	//复制其他项目中的util防止丢失
	
	public static String sendGet(String url) {
		HttpGet get = null;
		CloseableHttpResponse resp = null;
		CloseableHttpClient client = null;
		try {
			client = HttpClients.createDefault();
			get = new HttpGet(url);
			resp = client.execute(get);
			int statusCode = resp.getStatusLine().getStatusCode();
			if(statusCode>=200&&statusCode<300) {
				HttpEntity entity = resp.getEntity();
				String content = EntityUtils.toString(entity,"utf-8");
				return content;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(resp!=null) resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(client!=null) client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String sendPost(String url,String content,String type,Map<String,String> headers) {
		CloseableHttpClient client = null;
		CloseableHttpResponse resp = null;
		try {
			client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.addHeader("Content-type",type);
			for(String key:headers.keySet()){
				post.addHeader(key, headers.get(key));
			}
			StringEntity entity = new StringEntity(content, ContentType.create(type, "UTF-8"));
			post.setEntity(entity);
			resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			if(statusCode>=200&&statusCode<300) {
				String str = EntityUtils.toString(resp.getEntity(),"utf-8");
				return str;
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(client!=null) client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if(resp!=null) resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String sendPost(String url,String content,String type) {
		CloseableHttpClient client = null;
		CloseableHttpResponse resp = null;
		try {
			client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.addHeader("Content-type",type);
			StringEntity entity = new StringEntity(content, ContentType.create(type, "UTF-8"));
			post.setEntity(entity);
			resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			if(statusCode>=200&&statusCode<300) {
				String str = EntityUtils.toString(resp.getEntity(),"utf-8");
				return str;
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(client!=null) client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if(resp!=null) resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String sendJsonPost(String url,String content) {
		return sendPost(url, content, "application/json");
	}
	
	public static String sendXmlPost(String url,String content) {
		return sendPost(url, content, "application/xml");
	}
	
}
