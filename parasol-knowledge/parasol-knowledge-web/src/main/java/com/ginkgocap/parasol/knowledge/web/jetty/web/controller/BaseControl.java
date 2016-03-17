package com.ginkgocap.parasol.knowledge.web.jetty.web.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.dubbo.rpc.RpcException;
import com.ginkgocap.parasol.knowledge.web.jetty.web.ResponseError;
import com.ginkgocap.parasol.knowledge.model.LoginInfo;
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
	
	/**
	 * 获取用户
	 * @author 周仕奇
	 * @date 2016年1月15日 下午3:20:50
	 * @param request
	 * @return
	 */
	public LoginInfo getLoginInfo(HttpServletRequest request) {
        // 登陆人的信息
        Long loginAppId = LoginUserContextHolder.getAppKey();
        Long loginUserId = LoginUserContextHolder.getUserId();
		return new LoginInfo(loginAppId, loginUserId);
	}
	
	/**
	 * 获取前端数据
	 * @author 周仕奇
	 * @date 2016年1月15日 下午3:21:19
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public JSONObject getRequestJson(HttpServletRequest request) throws IOException {
		
		String requestString = (String)request.getAttribute("requestJson");
		
		JSONObject requestJson = JSONObject.fromObject(requestString == null ? "" : requestString);
		
		return requestJson;
	}
}
