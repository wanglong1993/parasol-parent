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

package com.ginkgocap.parasol.tags.web.jetty.web.controller;

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
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagSourceService;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class TagSourceController extends BaseControl {
	private static Logger logger = Logger.getLogger(TagSourceController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterTagId = "tagId";
	private static final String parameterSourceId = "sourceId";
	private static final String parameterSourceType = "sourceType";
	private static final String parameterTagSourceId = "id";

	@Autowired
	private TagSourceService tagsSourceService;

	//@formatter:off
	/**
	 * 1. 查询一个资源下的标签
	 * curl -i "http://localhost:8081/tags/source/getSourceList?appKey=1&userId=111&sourceId=1&sourceType=1"
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/tags/source/getSourceList", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceList(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagSourceController.parameterSourceId, required = true) Long sourceId,
			@RequestParam(name = TagSourceController.parameterSourceType, required = true) Long sourceType) {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<TagSource> tagsTypes = tagsSourceService.getTagSourcesByAppIdSourceIdSourceType(loginAppId, sourceId, sourceType);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(tagsTypes);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (TagSourceServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	//@formatter:off
	/**
	 * 2. 创建一个TagSource
	 * curl -i "http://localhost:8081/tags/tags/createTag?appKey=1&userId=111&tagType=1&tagName=恶人" -d ""
	 * curl -i http://localhost:8081/tags/source/createTagSource -d "appKey=1&userId=111&tagId=3925085861971496&sourceId=1&sourceType=1"
	 * 
	 * @param request
	 * @return
	 * @throws TagSourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/tags/source/createTagSource", method = { RequestMethod.POST })
	public MappingJacksonValue createTagSource(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagSourceController.parameterTagId, required = true) Long tagsId,
			@RequestParam(name = TagSourceController.parameterSourceId, required = true) Long sourceId,
			@RequestParam(name = TagSourceController.parameterSourceType, required = true) int sourceType) throws TagSourceServiceException {
		//@formatter:on
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		MappingJacksonValue mappingJacksonValue = null;
		try {
			TagSource source = new TagSource();
			source.setAppId(loginAppId);
			source.setUserId(loginUserId);
			source.setTagId(tagsId);
			source.setSourceId(sourceId);
			source.setSourceType(sourceType);

			Long id = tagsSourceService.createTagSource(source);
			Map<String, Long> resultMap = new HashMap<String, Long>();
			resultMap.put("id", id);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (TagSourceServiceException e) {
			e.printStackTrace(System.err);
			throw e;
		}
	}

	//@formatter:off
	/**
	 * 2. 删除一个TagSource
	 * curl -i "http://localhost:8081/tags/source/deleteTagSource?appKey=1&userId=111&id=3925349171986436"
	 * @param request
	 * @return
	 * @throws TagSourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/tags/source/deleteTagSource", method = { RequestMethod.GET, RequestMethod.DELETE})
	public MappingJacksonValue deleteTagSource(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagSourceController.parameterTagSourceId, required = true) Long id) throws TagSourceServiceException {
		//@formatter:on
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Boolean success = tagsSourceService.removeTagSource(loginAppId, loginUserId, id); // 服务验证Owner
			Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
			resultMap.put("success", success);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (TagSourceServiceException e) {
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
			filter.add("tagName"); // 标签名称

		}

		filterProvider.addFilter(TagSource.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}




}
