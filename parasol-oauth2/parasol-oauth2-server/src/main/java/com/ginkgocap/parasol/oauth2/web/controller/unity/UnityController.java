package com.ginkgocap.parasol.oauth2.web.controller.unity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.oauth2.service.UserDetailService;
import com.ginkgocap.parasol.oauth2.service.UserService;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/openapi/")
public class UnityController {


    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService userDetailService;
    
//    @Value("#{properties['getIdentifyingCode']}")
//    private String getIdentifyingCode;

    @RequestMapping("/user/login")
    @ResponseBody
    public JSONObject login(HttpServletRequest request) {
    	JSONObject json = new JSONObject();
        return json.accumulate("id", 1212112212).accumulate("passport", "esofmdsa@126.com");
    }

    @RequestMapping("/user/getIdentifyingCode")
    @ResponseBody
    	public JSONObject getIdentifyingCode(HttpServletRequest request) throws Exception {
    	CloseableHttpClient httpClient = null;  
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	HttpEntity entity =null;
    	JSONObject json = null;
    	String passport=request.getParameter("passport");
    	try{
	        try{
	        	RequestConfig defaultRequestConfig = RequestConfig.custom()
	        			  .setSocketTimeout(5000)
	        			  .setConnectTimeout(5000)
	        			  .setConnectionRequestTimeout(5000)
	        			  .setStaleConnectionCheckEnabled(true)
	        			  .build();
	        	httpClient = HttpClients.custom()
	        			.setDefaultRequestConfig(defaultRequestConfig)
	        			.build();
	        	RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
	        		.setProxy(new HttpHost("localhost", 8088))
	        	    .build();
	            HttpPost httpPost = new HttpPost("http://localhost:8088/user/getIdentifyingCode");
	            HttpEntity reqEntity = MultipartEntityBuilder.create()  
	            .addPart("passport",  new StringBody(passport, ContentType.create("text/plain", Consts.UTF_8)))
	            .build();  
	            httpPost.setEntity(reqEntity);  
	            httpPost.setConfig(requestConfig);
	            CloseableHttpResponse response = httpClient.execute(httpPost);  
	            try {  
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						resultMap.put("status", response.getStatusLine().getStatusCode());
						entity = response.getEntity();
						String respJson = EntityUtils.toString(entity);
						json = JSONObject.fromObject(respJson);
					}
	                EntityUtils.consume(entity);
	                return json;
	            } finally {  
	                response.close();  
	            }  
	        }finally{  
	            httpClient.close();  
	        }
    	}catch(Exception  e){
    		throw e;
    	}
        }

}