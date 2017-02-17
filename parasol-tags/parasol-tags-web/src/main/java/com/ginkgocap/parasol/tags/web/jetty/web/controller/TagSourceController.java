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

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ginkgocap.parasol.tags.web.jetty.modle.Property;
import com.ginkgocap.parasol.tags.web.jetty.utils.JsonReadUtil;
import com.ginkgocap.parasol.tags.web.jetty.utils.JsonUtils;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
	private static final String parameterSourceTitle = "sourceTitle";
	private static final String parameterSourceType = "sourceType";
	private static final String parameterTagSourceId = "id";
	private static final String parameterCount = "count";
	private static final String parameterStart = "start";

	@Autowired
	private TagSourceService tagsSourceService;

	//@formatter:off
	/**
	 * 1. 查询一个资源下的标签
	 * curl -i "http://localhost:8081/tags/source/getSourceList?appKey=1&userId=111&sourceId=1&sourceType=1"
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(path = "/tags/source/getSourceList", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceList(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagSourceController.parameterSourceId, required = true) Long sourceId,
			@RequestParam(name = TagSourceController.parameterSourceType, required = true) Long sourceType,
			HttpServletRequest request) {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);
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
	
	/**
	 * 3.根据标签获取资源
	 * curl -i "http://localhost:8081/tags/source/getSourceListByTag?appKey=1&userId=111&tagId=1&start=0&count=10"
	 * @param fileds
	 * @param debug
	 * @param tagId
	 * @return
	 */
	@RequestMapping(path = "/tags/source/getSourceListByTag", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceListByTag(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagSourceController.parameterTagId, required = true) Long tagId,
			@RequestParam(name = TagSourceController.parameterStart, required = true) Integer start,
			@RequestParam(name = TagSourceController.parameterCount, required = true) Integer count,
			HttpServletRequest request) {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<TagSource> tagsTypes = tagsSourceService.getTagSourcesByAppIdTagId(loginAppId, tagId, start, count);
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
	
	/**
	 * 3.根据标签获取资源
	 * curl -i "http://localhost:8081/tags/source/getSourceListByTagAndType?appKey=1&userId=111&tagId=1&sourceType=7&start=0&count=10"
	 * @param fileds
	 * @param debug
	 * @param tagId
	 * @return
	 */
	@RequestMapping(path = "/tags/source/getSourceListByTagAndType", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceListByTag(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagSourceController.parameterTagId, required = true) Long tagId,
			@RequestParam(name = TagSourceController.parameterSourceType, required = true) Long sourceType,
			@RequestParam(name = TagSourceController.parameterStart, required = true) Integer start,
			@RequestParam(name = TagSourceController.parameterCount, required = true) Integer count,
			HttpServletRequest request) {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<TagSource> tagsTypes = tagsSourceService.getTagSourcesByAppIdTagIdAndType(loginAppId, tagId, sourceType, start, count);
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
	
	/**
	 * 3.根据标签获取资源数量
	 * curl -i "http://localhost:8081/tags/source/getSourceCountByTag?appKey=1&userId=111&tagId=1&start=0&count=10"
	 * @param fileds
	 * @param debug
	 * @param tagId
	 * @return
	 */
	@RequestMapping(path = "/tags/source/getSourceCountByTag", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceCountByTag(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagSourceController.parameterTagId, required = true) Long tagId,
			HttpServletRequest request) {
		//@formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			Integer resCount = tagsSourceService.countTagSourcesByAppIdTagId(loginAppId, tagId);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resCount);
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
			@RequestParam(name = TagSourceController.parameterSourceTitle, required = true) String sourceTitle,
			@RequestParam(name = TagSourceController.parameterSourceType, required = true) int sourceType,
			HttpServletRequest request) throws TagSourceServiceException {
		//@formatter:on
//		Long loginAppId = LoginUserContextHolder.getAppKey();
//		Long loginUserId = LoginUserContextHolder.getUserId();
		Long loginAppId=this.DefaultAppId;
		Long loginUserId=this.getUserId(request);
		MappingJacksonValue mappingJacksonValue = null;
		try {
			TagSource source = new TagSource();
			source.setAppId(loginAppId);
			source.setUserId(loginUserId);
			source.setTagId(tagsId);
			source.setSourceId(sourceId);
			source.setSourceTitle(sourceTitle);
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
			@RequestParam(name = TagSourceController.parameterTagSourceId, required = true) Long id,
			HttpServletRequest request) throws TagSourceServiceException {
		//@formatter:on
//		Long loginAppId = LoginUserContextHolder.getAppKey();
//		Long loginUserId = LoginUserContextHolder.getUserId();
		Long loginAppId=this.DefaultAppId;
		Long loginUserId=this.getUserId(request);
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
			filter.add("sourceTitle"); // 资源标题
			filter.add("createAt"); // 创建时间
			filter.add("tagName"); // 标签名称

		}

		filterProvider.addFilter(TagSource.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	/**
	 * 根据sourceId查询资源列表
	 * @return
	 */
	@RequestMapping(path = "/tags/source/getSourceListBySourceId", method = { RequestMethod.GET })
	public MappingJacksonValue  getSourceListBySourceId(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
											 @RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
											 @RequestParam(name = TagSourceController.parameterSourceId, required = true) Long sourceId,
											 @RequestParam(name = TagSourceController.parameterSourceType, required = true) Long sourceType,
											 HttpServletRequest request) {
		//@formatter:on+
		MappingJacksonValue mappingJacksonValue = null;
		InterfaceResult interfaceResult=null;
		try {
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);
			if(loginUserId==null){
				logger.error("userId is null");
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION,"用户长时间未操作或者未登录，权限失效！");
				return new MappingJacksonValue(interfaceResult);
			}
			if(sourceId == null){
				logger.error("sourceId is null");
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"sourceId 不能为空！");
				return new MappingJacksonValue(interfaceResult);
			}
			if(sourceType == null){
				logger.error("sourceType is null");
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"sourceType 不能为空！");
				return new MappingJacksonValue(interfaceResult);
			}
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务r
			List<TagSource> tagSourceList = null;
			try {
				tagSourceList = tagsSourceService.getTagSourcesBySourceId(loginAppId,sourceId,sourceType);
			} catch (Exception e) {
				e.printStackTrace();
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION,"数据库操作失败！");
				return new MappingJacksonValue(interfaceResult);
			}
			// 2.转成框架数据
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
			interfaceResult.setResponseData(tagSourceList);
			mappingJacksonValue = new MappingJacksonValue(interfaceResult);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION,"获取标签列表失败！");
			return new MappingJacksonValue(interfaceResult);
		}

	}

	/**
	 * 批量更新标签
	 * @return
	 */
	@RequestMapping(path = "/tags/source/updateTagSources", method = { RequestMethod.POST })
	public MappingJacksonValue updateTagSources(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
												HttpServletRequest request, HttpServletRequest response) throws TagSourceServiceException {
		String requestJson = null;
		MappingJacksonValue mappingJacksonValue = null;
		InterfaceResult interfaceResult=null;
		List<TagSource> tagSourceList = null;
		List<Property> tags=null;
			try {
				Long loginAppId = this.DefaultAppId;
				Long loginUserId = this.getUserId(request);
				if(loginUserId==null){
					logger.error("userId is null");
					interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION,"用户长时间未操作或者未登录，权限失效！");
					return new MappingJacksonValue(interfaceResult);
				}
				requestJson = this.getBodyParam(request);
				if (requestJson != null && !"".equals(requestJson)) {
					JSONObject j = JSONObject.fromObject(requestJson);
					Long sourceId = j.getLong("sourceId");
					String sourceTitle = j.getString("sourceTitle");
					long sourceType = j.getInt("sourceType");
					tags = JsonUtils.getList4Json(j.getString("tags"), Property.class);
					if (sourceId<=0 || sourceId==null) {
						logger.error("sourceId is null..");
						interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"sourceId不能为空！");
						return new MappingJacksonValue(interfaceResult);
					}
					if (sourceTitle == null || "".equals(sourceTitle.trim())) {
						logger.error("sourceTitle is null..");
						interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"sourceTitle不能为空！");
						return new MappingJacksonValue(interfaceResult);
					}
					if (sourceType<=0) {
						logger.error("sourceType is null..");
						interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"sourceType不能为空！");
						return new MappingJacksonValue(interfaceResult);
					}
					try {
						tagSourceList = tagsSourceService.getTagSourcesBySourceId(loginAppId, sourceId, sourceType);
						if (tagSourceList != null) {
							for (TagSource ts : tagSourceList) {
								tagsSourceService.removeTagSource(loginAppId, loginUserId, ts.getId());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION,"数据库操作失败！");
						return new MappingJacksonValue(interfaceResult);
					}
					if (tags != null) {
						for (Property property : tags) {
							if ((property != null) && StringUtils.isNotBlank(property.getId())) {
								TagSource tagSource = new TagSource();
								tagSource.setUserId(loginUserId);
								tagSource.setAppId(loginAppId);
								tagSource.setSourceId(sourceId);
								tagSource.setSourceTitle(sourceTitle);
								tagSource.setSourceType(sourceType);
								tagSource.setTagId(Long.parseLong(property.getId()));
								tagSource.setTagName(property.getName());
								tagSource.setCreateAt(new Date().getTime());
								Long tagId = tagsSourceService.createTagSource(tagSource);
								logger.info("tagId:" + tagId);
							}
						}
					}
					try {
						tagSourceList = tagsSourceService.getTagSourcesBySourceId(loginAppId, sourceId, sourceType);
					} catch (Exception e) {
						e.printStackTrace();
						interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION,"数据库操作失败！");
						return new MappingJacksonValue(interfaceResult);
					}
					// 2.转成框架数据
					interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
					interfaceResult.setResponseData(tagSourceList);
					mappingJacksonValue = new MappingJacksonValue(interfaceResult);
					// 3.创建页面显示数据项的过滤器
					SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
					mappingJacksonValue.setFilters(filterProvider);
				}
				// 4.返回结果
				return mappingJacksonValue;
			} catch (Exception e) {
				e.printStackTrace();
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION,"更新标签列表失败！");
				return new MappingJacksonValue(interfaceResult);
			}
	}
}
