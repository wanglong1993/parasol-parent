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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.tags.exception.TagServiceException;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.service.TagService;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class TagController extends BaseControl {
	private static final String paramenterFields = "fields";
	private static final String paramenterDebug = "debug";
	private static final String paramenterAppId = "appKey";
	private static final String paramenterUserId = "userId";
	private static final String paramenterTagId = "tagId";
	private static final String paramenterTagType = "tagType";
	private static final String paramenterTagName = "tagName";

	@Autowired
	private TagService tagService;

	//@formatter:off
	/**
	 * 给出一个的标签列表
	 * curl -i "http://localhost:8081/tags/tags/getTagList?appKey=1&userId=111&tagType=1"
	 * @param request
	 * @return
	 * @throws TagServiceException
	 * @throws CodeServiceException
	 */
							
	@RequestMapping(path = "/tags/tags/getTagList", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceList(@RequestParam(name = TagController.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = TagController.paramenterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagController.paramenterTagType, required = false,defaultValue="0") Long tagType) throws TagServiceException {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();

			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<Tag> tag = tagService.getTagsByUserIdAppidTagType(loginUserId, loginAppId, tagType);
			tag = tag == null ? new ArrayList<Tag>() : tag;
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(tag);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (TagServiceException e) {
			e.printStackTrace(System.err);
			throw e;

		}
	}

	//@formatter:off
	/**
	 * 2. 创建一个Tag
	 * curl -i "http://localhost:8081/tags/tags/createTag?appKey=1&userId=111&tagType=1&tagName=恶人" -d ""
	 * @param request
	 * @return
	 * @throws TagSourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/tags/tags/createTag", method = { RequestMethod.POST })
	public MappingJacksonValue createTagSource(@RequestParam(name = TagController.paramenterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagController.paramenterTagType, required = true) int tagType,
			@RequestParam(name = TagController.paramenterTagName, required = true) String tagName)
			throws TagServiceException {
		//@formatter:on
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Tag tag = new Tag();
			tag.setAppId(loginAppId);
			tag.setUserId(loginUserId);
			tag.setTagType(tagType);
			tag.setTagName(tagName);

			Long id = tagService.createTag(loginUserId, tag);
			Map<String, Long> resultMap = new HashMap<String, Long>();
			resultMap.put("id", id);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (TagServiceException e) {
			e.printStackTrace(System.err);
			throw e;
		}
	}

	//@formatter:off
	/**
	 * 2. 删除一个TagSource
	 * curl -i http://localhost:8081/tags/tags/deleteTag?appKey=1\&userId=111\&tagId=$i
	 * @param request
	 * @return
	 * @throws TagSourceServiceException
	 * @throws TagServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/tags/tags/deleteTag", method = { RequestMethod.GET , RequestMethod.DELETE})
	public MappingJacksonValue deleteTagSource(@RequestParam(name = TagController.paramenterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagController.paramenterTagId, required = true) Long id) throws TagSourceServiceException, TagServiceException {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			Boolean success = tagService.removeTag(loginUserId, id); // 服务验证Owner
			Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
			resultMap.put("success", success);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (TagServiceException e) {
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
			filter.add("tagName"); // Tag名称
		}

		filterProvider.addFilter(Tag.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
}
