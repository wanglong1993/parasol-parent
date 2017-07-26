package com.ginkgocap.parasol.file.web.jetty.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.dubbo.rpc.RpcException;
import com.ginkgocap.parasol.file.web.jetty.web.ResponseError;
import com.ginkgocap.ywxt.user.model.User;

import net.sf.json.JSONObject;
/**
 * 
 * @author allenshen
 * @date 2015年11月23日
 * @time 上午8:47:57
 * @Copyright Copyright©2015 www.gintong.com
 */
public abstract class BaseControl {
	private static Pattern service_method_parttern = Pattern.compile("Failed to invoke the method (.+?) in the service (.+?). No provider");
	private static Logger logger = Logger.getLogger(BaseControl.class);

	/**
	 * 从错误日志中找到服务名称
	 * 
	 * @param errMessage
	 * @return
	 */
	protected String getServiceNameByRpcMessage(RpcException rpc) {
		if (rpc != null && StringUtils.isNotBlank(rpc.getMessage())) {
			Matcher matcher = service_method_parttern.matcher(rpc.getMessage());
			if (matcher.find()) {
				return ClassUtils.getShortCanonicalName(matcher.group(2)) + "#" + matcher.group(1);
			}
		}
		return null;
	}

	/**
	 * 讲notification统一包装起来
	 * @param responseDataMap 协议的消息体部分， 对应 responseData
	 * @param notificationMap 协议的消息部分， 对应 notification
	 */
	public Map<String, Object> genRespBody(Map<String, Object> responseDataMap,
										   Map<String, Object> notificationMap) {
		if (notificationMap == null) {
			notificationMap = new HashMap<String, Object>();
			notificationMap.put("notifCode", "");
			notificationMap.put("notifInfo", "");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	/**
	 * 处理RpcException
	 * 
	 * @param exception
	 * @return
	 */
	protected  ResponseError processResponseError(Exception exception) {
		if (exception != null) {
			ResponseError error = new ResponseError();
			if (exception instanceof RpcException) {
				RpcException rpcException = (RpcException) exception;
				if (rpcException.isBiz()) { // 业务错误
					error.setType("RpcException");
					error.setMessage("业务错误");
				} else if (rpcException.isNetwork()) {
					error.setType("RpcException");
					error.setMessage("网络故障");
				} else if (rpcException.isSerialization()) {
					error.setType("RpcException");
					error.setMessage("无法序列化");
				} else if (rpcException.isTimeout()) {
					error.setType("RpcException");
					error.setMessage("请求超时");
				} else {
					String serviceName_err = getServiceNameByRpcMessage(rpcException);
					if (serviceName_err != null) {
						logger.info(serviceName_err + " error");
						error.setType("RpcException");
						error.setMessage(serviceName_err + "停止服务，请稍后重试！");
					}
				}

			}

			processBusinessException(error,exception);
			return error;
		} else {
			return null;
		}
	}

	protected abstract <T> void processBusinessException(ResponseError error, Exception ex);
	
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
}
