package com.ginkgocap.parasol.comment.web.jetty.web.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.dubbo.common.json.JSONObject;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.user.model.User;
/**
 * 
 * @author allenshen
 * @date 2015年11月23日
 * @time 上午8:47:57
 * @Copyright Copyright©2015 www.gintong.com
 */
public abstract class BaseControl {
	private static Logger logger = Logger.getLogger(BaseControl.class);
	
	/**
	 * 获取用户
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request) {
		//在AppFilter过滤器里面从cache获取了当前用户对象并设置到request中了
		return (User) request.getAttribute("sessionUser");
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
}
