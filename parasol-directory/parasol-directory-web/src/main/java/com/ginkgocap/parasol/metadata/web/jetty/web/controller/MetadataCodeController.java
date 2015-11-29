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
import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;
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
public class MetadataCodeController extends BaseControl {
	private static Logger logger = Logger.getLogger(MetadataCodeController.class);

	private static final String paramenterFields = "fields";
	private static final String paramenterDebug = "debug";

	@Autowired
	private CodeService codeService;

	/**
	 * 查询有哪些大分类
	 * 
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/code/getFunctionClassList", method = { RequestMethod.GET })
	public MappingJacksonValue getFunctionClassList(@RequestParam(name = MetadataCodeController.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataCodeController.paramenterDebug, defaultValue = "") String debug) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			//0.校验输入参数（框架搞定，如果业务业务搞定）
			//1.查询后台服务
			List<Code> codes = codeService.getCodesForRoot(true);
			//2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(codes);
			//3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			//4.返回结果
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
	 * 查询大类下面的子类
	 * 
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/code/getSubFuctionClassList", method = { RequestMethod.GET })
	public MappingJacksonValue getSubFuctionClassList(@RequestParam(name = "pid", required = true) Long pid,
			@RequestParam(name = MetadataCodeController.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataCodeController.paramenterDebug, defaultValue = "") String debug) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			List<Code> codes = codeService.getCodesByParentId(pid, false);
			codes = CollectionUtils.isEmpty(codes) ? new ArrayList<Code>() : codes;
			mappingJacksonValue = new MappingJacksonValue(codes);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
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
	 * 创建一个大分类（提供测试使用，上线时候不能使用）
	 * 
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/code/createCodeForRoot", method = { RequestMethod.GET })
	public MappingJacksonValue createCodeForRoot(@RequestParam(name = "name", required = true) String name, @RequestParam(name = "remark", defaultValue = "") String remark,
			@RequestParam(name = "orderNo", defaultValue = "0") int orderNo, @RequestParam(name = MetadataCodeController.paramenterDebug, defaultValue = "") String debug) {
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

	@Override
	protected void processBusinessException(ResponseError error, Exception ex) {
		if (ex instanceof CodeServiceException) {
			CodeServiceException codeServiceException = (CodeServiceException) ex;
			error.setType("BizException");
			error.setCode(codeServiceException.getErrorCode());
			error.setMessage(codeServiceException.getMessage());
		}
	}

	/**
	 * 指定显示那些字段
	 * 
	 * @param fileds
	 * @return
	 */
	private SimpleFilterProvider builderSimpleFilterProvider(String fileds) {
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
			//filter.add("pid"); // 父主键',
			filter.add("name"); // '类型名称',
		}

		filterProvider.addFilter(Code.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
}
