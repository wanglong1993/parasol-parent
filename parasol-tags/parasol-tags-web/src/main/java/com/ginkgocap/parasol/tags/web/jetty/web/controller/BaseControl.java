package com.ginkgocap.parasol.tags.web.jetty.web.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import org.apache.log4j.Logger;

import com.ginkgocap.ywxt.user.model.User;

import net.sf.json.JSONObject;
import org.springframework.http.converter.json.MappingJacksonValue;

/**
 * 
 * @author allenshen
 * @date 2015年11月23日
 * @time 上午8:47:57
 * @Copyright Copyright©2015 www.gintong.com
 */
public abstract class BaseControl {
	private static Logger logger = Logger.getLogger(BaseControl.class);
protected final Long DefaultAppId=1l;
	
	/**
	 * 获取用户
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request) {
		//在AppFilter过滤器里面从cache获取了当前用户对象并设置到request中了
		return (User) request.getAttribute("sessionUser");
	}
	
	public Long getUserId(HttpServletRequest request){
		User user=this.getUser(request);
		Long uid=0l;
		if(user!=null){
			uid=user.getId();
		}
		return uid;
	}
	
	/**
	 * 获取前端数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public JSONObject getRequestJson(HttpServletRequest request) throws IOException {
		
		String requestString = (String)request.getAttribute("requestJson");
		
		JSONObject requestJson = JSONObject.fromObject(requestString == null ? "" : requestString);
		
		return requestJson;
	}
	
	public String readJSONString(HttpServletRequest request){
        StringBuffer json = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
        return json.toString();
		
    }
	protected String getBodyParam(HttpServletRequest request) {
		StringBuffer jsonIn = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonIn.append(line);
			}
		} catch (IOException e) {
			logger.error("read request body failed : "+e.getMessage());
			e.printStackTrace();
		}
		return jsonIn.toString();
	}
	/*protected MappingJacksonValue mappingJacksonValue(CommonResultCode resultCode)
	{
		return new MappingJacksonValue(InterfaceResult.getInterfaceResultInstance(resultCode));
	}*/
}
