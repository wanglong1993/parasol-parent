/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ginkgocap.parasol.metadata.web.jetty.web.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.rpc.RpcException;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;
import com.ginkgocap.parasol.metadata.service.CodeService;
import com.ginkgocap.parasol.metadata.web.jetty.service.HelloWorldService;
import com.ginkgocap.parasol.metadata.web.jetty.web.ResponseError;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class MetadataController extends BaseControl{
	private static Logger logger = Logger.getLogger(MetadataController.class);

	private static final String paramenterFields = "fields";
	private static final String paramenterDebug = "debug";

	@Autowired
	private HelloWorldService helloWorldService;

	@Autowired
	private CodeService codeService;

	/**
	 * 查询有哪些大分类
	 * 
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/getFunctionClassList", method = { RequestMethod.GET })
	public MappingJacksonValue getFunctionClassList(@RequestParam(name = MetadataController.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataController.paramenterDebug, defaultValue = "") String debug) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			List<Code> codes = codeService.getCodesForRoot(true);
			mappingJacksonValue = new MappingJacksonValue(codes);
			SimpleFilterProvider filterProvider = new SimpleFilterProvider();

			// 请求指定字段
			String[] filedNames = StringUtils.split(fileds, ",");
			Set<String> filter = new HashSet<String>();
			if (filedNames != null && filedNames.length > 0) {
				for (int i = 0; i < filedNames.length; i++) {
					String filedName = filedNames[i];

					if (!StringUtils.isEmpty(filedName)) {
						filter.add(filedName);
					}
				}
			} else {
				filter.add("id"); // 主键',
				filter.add("pid"); // 父主键',
				filter.add("name"); // '类型名称',
			}

			filterProvider.addFilter(Code.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		} catch (RpcException e) {
			Map<String, Serializable> resultMap = new HashMap<String, Serializable>();
			ResponseError error = processResponseError(e);
			if (error != null) {
				resultMap.put("error", error);
			}
			if (ObjectUtils.equals(debug, "all")) {
				// if (e.getErrorCode() > 0 ) {
				resultMap.put("__debug__", e.getMessage());
				// }
			}
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			e.printStackTrace(System.err);
			return mappingJacksonValue;
		} catch (CodeServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	/**
	 * 查询有哪些大分类
	 * 
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/createCodeForRoot", method = { RequestMethod.GET })
	public MappingJacksonValue createCodeForRoot(@RequestParam(name = "name", required = true) String name, @RequestParam(name = "remark", defaultValue = "") String remark,
			@RequestParam(name = "orderNo", defaultValue = "0") int orderNo, @RequestParam(name = MetadataController.paramenterDebug, defaultValue = "") String debug) {
		MappingJacksonValue mappingJacksonValue = null;
		try {

			Code code = new Code();
			code.setName(name);
			code.setRemark(remark);
			code.setOrderNo(orderNo);

			Long id = codeService.createCodeForRoot(code);
			Code newCode = null;
			if (id != null && id > 0) {
				codeService.getCode(id);
				newCode = codeService.getCode(id);
			}
			if (newCode == null) {
				return null;
			} else {
				mappingJacksonValue = new MappingJacksonValue(code);
				Set<String> filter = new HashSet<String>();
				filter.add("id"); // 主键',
				filter.add("pid"); // 父主键',
				filter.add("name"); // '类型名称',
				SimpleFilterProvider filterProvider = new SimpleFilterProvider();
				filterProvider.addFilter(Code.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
				mappingJacksonValue.setFilters(filterProvider);
				return mappingJacksonValue;
			}
		} catch (Exception e) {
			Map<String, Serializable> resultMap = new HashMap<String, Serializable>();
			ResponseError error = processResponseError(e);
			if (error != null) {
				resultMap.put("error", error);
			}
			if (ObjectUtils.equals(debug, "all")) {
				// if (e.getErrorCode() > 0 ) {
				resultMap.put("__debug__", e.getMessage());
				// }
			}
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			return mappingJacksonValue;
		}
	}



	/**
	 * 处理RpcException
	 * 
	 * @param exception
	 * @return
	 */
	private  ResponseError processResponseError(Exception exception) {
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

			if (exception instanceof CodeServiceException) {
				CodeServiceException codeServiceException = (CodeServiceException) exception;
				error.setType("BizException");
				error.setCode(codeServiceException.getErrorCode());
				error.setMessage(codeServiceException.getMessage());
			}
			return error;
		} else {
			return null;
		}
	}

}
