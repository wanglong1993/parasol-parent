package org.parasol.column.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.ginkgocap.parasol.user.model.User;



public class BaseController {
	
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
			uid=user.getUserLoginRegister().getId();
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
	
}