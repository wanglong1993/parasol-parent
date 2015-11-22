package com.ginkgocap.parasol.metadata.web.jetty.web.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.rpc.RpcException;

public abstract class BaseControl {
	private static Pattern service_method_parttern = Pattern.compile("Failed to invoke the method (.+?) in the service (.+?). No provider");

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
}
