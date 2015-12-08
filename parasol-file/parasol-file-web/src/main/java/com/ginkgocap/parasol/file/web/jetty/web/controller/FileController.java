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

package com.ginkgocap.parasol.file.web.jetty.web.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.file.web.jetty.web.ResponseError;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class FileController extends BaseControl {
	private static Logger logger = Logger.getLogger(FileController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterAppId = "appKey"; // 应用的Key
	private static final String parameterUserId = "userId"; // 访问的用户参数
	private static final String parameterType = "type"; // 查询的应用分类
	private static final String parameterWord = "word"; // 敏感词
	private static final String parameterLevel = "level"; // 敏感词等级
	private static final String parameterWordId = "wordId"; // 消息内容
	private static final String parameterText = "text"; // 校验文本

	@Resource
	private FileIndexService fileIndexService;

	/**
	 * 2.创建分类下的根目录
	 * 
	 * @param request
	 * @return
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/sensitive/word/saveSensitive" }, method = { RequestMethod.GET })
	public MappingJacksonValue createMessageEntity(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterAppId, required = true) Long appId,
			@RequestParam(name = FileController.parameterUserId, required = true) Long userId,
			@RequestParam(name = FileController.parameterLevel, defaultValue = "2") Integer level,
			@RequestParam(name = FileController.parameterWord, required = true) String word,
			@RequestParam(name = FileController.parameterType, defaultValue = "0") Integer type) {
		MappingJacksonValue mappingJacksonValue = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", type.toString());
		param.put("createrId", userId.toString());
		param.put("appid", appId.toString());
		FileIndex index = new FileIndex();
		
		try {
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			index = fileIndexService.insertFileIndex(index);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(index);
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
		} catch (Exception e) {
			
		}
		return mappingJacksonValue;
	}

	/**
	 * 1. 根据entityid获取消息实体
	 * 
	 * @param request
	 * @return
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/sensitive/word/getSensitiveWordById" }, method = { RequestMethod.GET })
	public MappingJacksonValue getEntityById(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterAppId, required = true) Long appId,
			@RequestParam(name = FileController.parameterUserId, required = true) Long userId,
			@RequestParam(name = FileController.parameterWordId, required = true) long id
			) throws Exception {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<FileIndex> files = fileIndexService.getFileIndexesByTaskId("");
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(files);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (Exception e) {
			throw e;
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
			filter.add("id"); // id',
			filter.add("name"); // '分类名称',
			filter.add("typeId"); // '应用的分类分类ID',
			filter.add("appId"); // '应用的分类分类ID',
			filter.add("userId"); // '应用的分类分类ID',
		}

		filterProvider.addFilter(Directory.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	@Override
	protected <T> void processBusinessException(ResponseError error,
			Exception ex) {
		// TODO Auto-generated method stub
		
	}
}
