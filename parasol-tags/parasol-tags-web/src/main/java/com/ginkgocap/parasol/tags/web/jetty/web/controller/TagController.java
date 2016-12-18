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

import javax.servlet.http.HttpServletRequest;

import com.ginkgocap.parasol.tags.model.Page;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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
	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterTagId = "tagId";
	private static final String parameterTagType = "tagType";
	private static final String parameterTagName = "tagName";

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
	public MappingJacksonValue getSourceList(@RequestParam(name = TagController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = TagController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagController.parameterTagType, required = false,defaultValue="0") Long tagType,
			HttpServletRequest request) throws TagServiceException {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);

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

	/**
	 * 给出一个用户和系统推荐的标签列表
	 * curl -i "http://localhost:8081/tags/tags/getRecomTagList?appKey=1&userId=111&tagType=1"
	 * @param request
	 * @return
	 * @throws TagServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/tags/tags/getRecomTagList", method = { RequestMethod.GET })
	public MappingJacksonValue getRecomTagList(@RequestParam(name = TagController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = TagController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagController.parameterTagType, required = false,defaultValue="0") Long tagType,
			HttpServletRequest request) throws TagServiceException {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);

			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<Tag> userTags = tagService.getTagsByUserIdAppidTagType(loginUserId, loginAppId, tagType);
			userTags = userTags == null ? new ArrayList<Tag>() : userTags;

			List<Tag> sysTags = tagService.getTagsByUserIdAppidTagType(0L, loginAppId, tagType);
			sysTags = sysTags == null ? new ArrayList<Tag>() : sysTags;
			// 2.转成框架数据
			Map<String, List<Tag>> resultMap = new HashMap<String, List<Tag>>();
			resultMap.put("user", userTags);
			resultMap.put("sys", sysTags);
			mappingJacksonValue = new MappingJacksonValue(resultMap);
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

	/**
	 * 给出一个大数据系统推荐的标签列表
	 * curl -i "http://localhost:8081/tags/tags/getRecomTagList?appKey=1&userId=111&tagType=1"
	 * @param request
	 * @return
	 * @throws TagServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/tags/tags/getRecomDefaultTagList", method = { RequestMethod.GET })
	public MappingJacksonValue getRecomDefaultTagList(@RequestParam(name = TagController.parameterFields, defaultValue = "") String fileds,
													   @RequestParam(name = "index",required = false,defaultValue="0")int index,
											           @RequestParam(name = "size", required = false,defaultValue="10") int size,
											           HttpServletRequest request) throws TagServiceException {
		InterfaceResult result = null;
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
			result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
			Long loginAppId = this.DefaultAppId;
			//Long loginUserId = this.getUserId(request);

			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			//List<Tag> userTags = tagService.getTagsByUserIdAppidTagType(loginUserId, loginAppId, tagType);
			//userTags = userTags == null ? new ArrayList<Tag>() : userTags;

			int total = tagService.countDefaultTagsByUserIdAppidTagType(0l, loginAppId, 0l);
			//带分页的系统推荐标签
			List<Tag> sysTags = tagService.getTagsByUserIdAppidTagTypePage(0L, loginAppId, 0L, index,size);
			sysTags = sysTags == null ? new ArrayList<Tag>() : sysTags;
			Page<Tag> tagPage = returnTagPage(sysTags,index,size,total);
			Map<String,Page<Tag>> map = new HashMap<String,Page<Tag>>();
			map.put("page",tagPage);
			//map.put("success",true);
			// 3.创建页面显示数据项的过滤器
			mappingJacksonValue = new MappingJacksonValue(map);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			//result.setResponseData(mappingJacksonValue);
			return mappingJacksonValue;
		} catch (TagServiceException e) {
			e.printStackTrace(System.err);
			throw e;
		}
	}

	private Page<Tag> returnTagPage(List<Tag> sysTags, int index,int size,int total){

		Page<Tag> tagPage = new Page<Tag>();
		tagPage.setIndex(index);
		tagPage.setList(sysTags);
		tagPage.setTotal((long)total);
		tagPage.setSize(size);
		return tagPage;
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
	public MappingJacksonValue createTagSource(@RequestParam(name = TagController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagController.parameterTagType, required = true) int tagType,
			@RequestParam(name = TagController.parameterTagName, required = true) String tagName,
			HttpServletRequest request)
			throws TagServiceException {
		//@formatter:on
//		Long loginAppId = LoginUserContextHolder.getAppKey();
//		Long loginUserId = LoginUserContextHolder.getUserId();
		Long loginAppId=this.DefaultAppId;
		Long loginUserId=this.getUserId(request);
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
	 * 2. 更新一个Tag
	 * curl -i "http://localhost:8081/tags/tags/updateTag?appKey=1&userId=111&tagId=1&tagName=恶人" -d ""
	 * @param request
	 * @return
	 * @throws TagSourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/tags/tags/updateTag", method = { RequestMethod.PUT })
	public MappingJacksonValue updateTagSource(@RequestParam(name = TagController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagController.parameterTagId, required = true) long tagId,
			@RequestParam(name = TagController.parameterTagName, required = true) String tagName,
			HttpServletRequest request)
			throws TagServiceException {
		//@formatter:on
//		Long loginAppId = LoginUserContextHolder.getAppKey();
//		Long loginUserId = LoginUserContextHolder.getUserId();
		Long loginAppId=this.DefaultAppId;
		Long loginUserId=this.getUserId(request);
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Tag tag = new Tag();
			tag.setId(tagId);
			tag.setAppId(loginAppId);
			tag.setUserId(loginUserId);
			tag.setTagName(tagName);

			Boolean result = tagService.updateTag(loginUserId, tag);
			Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
			resultMap.put("result", result);
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
	public MappingJacksonValue deleteTagSource(@RequestParam(name = TagController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagController.parameterTagId, required = true) Long id,
			HttpServletRequest request) throws TagSourceServiceException, TagServiceException {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);
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
			filter.add("tagType"); // tagType',
			filter.add("tagName"); // Tag名称
			//filter.add("success");
			filter.add("page");
			filter.add("total");
			filter.add("index");
			filter.add("list");
			filter.add("size");
		}

		filterProvider.addFilter(Page.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		filterProvider.addFilter(Tag.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
	
	@RequestMapping(path = "/tags/test", method = { RequestMethod.POST })
	public void test(HttpServletRequest request){
		Long loginAppId=this.DefaultAppId;
		Long loginUserId=this.getUserId(request);
		System.out.println("user："+loginUserId);
	}
}
