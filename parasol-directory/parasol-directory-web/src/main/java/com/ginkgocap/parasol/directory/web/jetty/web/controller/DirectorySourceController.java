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

package com.ginkgocap.parasol.directory.web.jetty.web.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class DirectorySourceController extends BaseControl {
	private static Logger logger = Logger.getLogger(DirectorySourceController.class);
	private static final String paramenterFields = "fields";
	private static final String paramenterDebug = "debug";
	private static final String paramenterDirectoryId = "directoryId";
	private static final String paramenterSourceId = "sourceId";
	private static final String paramenterSourceType = "sourceType";
	private static final String paramenterSourceUrl = "sourceUrl";
	private static final String paramenterSourceTitle = "sourceTitle";
	private static final String paramenterSourceData = "sourceData";
	private static final String paramenterDirectorySourceIds="ids";
	private static final String paramenterDirectorySourceId="id";


	@Autowired
	private DirectorySourceService directorySourceService;

	/**
	 * 查询目录下的资源
	 * 
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/directory/source/getSourceList", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceList(@RequestParam(name = DirectorySourceController.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = DirectorySourceController.paramenterDebug, defaultValue = "") String debug,
			@RequestParam(name = DirectorySourceController.paramenterDirectoryId, required = true) Long directoryId) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<DirectorySource> directoryTypes = directorySourceService.getDirectorySourcesByDirectoryId(loginAppId, loginUserId, directoryId);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(directoryTypes);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (DirectorySourceServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	/**
	 * 2. 创建一个DirectorySource
	 * 
	 * @param request
	 * @return
	 * @throws DirectorySourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/directory/source/createSource", method = { RequestMethod.POST })
	public MappingJacksonValue createDirectorySource(@RequestParam(name = DirectorySourceController.paramenterDebug, defaultValue = "") String debug,
			@RequestParam(name = DirectorySourceController.paramenterDirectoryId, required = true) Long directoryId,
			@RequestParam(name = DirectorySourceController.paramenterSourceId, required = true) Long sourceId,
			@RequestParam(name = DirectorySourceController.paramenterSourceType, required = true) int sourceType,
			@RequestParam(name = DirectorySourceController.paramenterSourceUrl, defaultValue = "", required = false) String sourceUrl,
			@RequestParam(name = DirectorySourceController.paramenterSourceTitle, required = true) String sourceTitle,
			@RequestParam(name = DirectorySourceController.paramenterSourceData, defaultValue = "", required = false) String sourceData) throws DirectorySourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			DirectorySource source = new DirectorySource();
			source.setAppId(loginAppId);
			source.setUserId(loginUserId);
			source.setDirectoryId(directoryId);
			source.setSourceId(sourceId);
			source.setSourceType(sourceType);
			source.setSourceUrl(sourceUrl);
			source.setSourceTitle(sourceTitle);
			source.setSourceData(sourceData);

			Long id = directorySourceService.createDirectorySources(source);
			Map<String, Long> resultMap = new HashMap<String, Long>();
			resultMap.put("id", id);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (DirectorySourceServiceException e) {
			e.printStackTrace(System.err);
			throw e;
		}
	}

	/**
	 * 2. 删除一个DirectorySource
	 * 
	 * @param request
	 * @return
	 * @throws DirectorySourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/directory/source/deleteSource", method = { RequestMethod.GET })
	public MappingJacksonValue deleteDirectorySource(@RequestParam(name = DirectorySourceController.paramenterDebug, defaultValue = "") String debug,
			@RequestParam(name = DirectorySourceController.paramenterDirectorySourceId, required = true) Long id) throws DirectorySourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			Boolean success = directorySourceService.removeDirectorySources(loginAppId,loginUserId,id);
			Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
			resultMap.put("success", success);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		}  catch (DirectorySourceServiceException e) {
			e.printStackTrace(System.err);
			throw e;
		}
	}

	
	/**
	 * 2. 移动多个或者一个DirectorySource到其它目录下
	 * 
	 * @param request
	 * @return
	 * @throws DirectorySourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/directory/source/moveSource", method = { RequestMethod.POST })
	public MappingJacksonValue moveDirectorySource(@RequestParam(name = DirectorySourceController.paramenterDebug, defaultValue = "") String debug,
			@RequestParam(name = DirectorySourceController.paramenterDirectorySourceIds, required = true) Long[] ids,
			@RequestParam(name = DirectorySourceController.paramenterDirectoryId, required = true) Long directoryId) throws DirectorySourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			// TODO: 没有实现这个方法
			Boolean success = directorySourceService.moveDirectorySources(loginUserId, loginAppId, directoryId, ids);
			Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
			resultMap.put("success", success);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		}  catch (DirectorySourceServiceException e) {
			e.printStackTrace(System.err);
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
			filter.add("sourceId"); // 资源ID
			filter.add("sourceType"); // 资源类型
			filter.add("sourceUrl"); // 资源URL
			filter.add("sourceTitle"); // 资源的title
			filter.add("sourceData"); // 资源的Data
		}

		filterProvider.addFilter(DirectorySource.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
}
