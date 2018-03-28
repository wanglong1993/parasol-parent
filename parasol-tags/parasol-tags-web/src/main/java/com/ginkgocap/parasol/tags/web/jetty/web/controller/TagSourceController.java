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

import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.ginkgocap.parasol.tags.model.*;
import com.ginkgocap.parasol.tags.service.NewTagService;
import com.ginkgocap.parasol.tags.service.NewTagSourceService;
import com.ginkgocap.parasol.tags.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import com.ginkgocap.parasol.util.JsonUtils;
import com.ginkgocap.ywxt.knowledge.service.KnowledgeService;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;

import net.sf.json.JSONObject;

import static com.ginkgocap.ywxt.knowledge.service.common.KnowledgeBaseService.sourceType;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class TagSourceController extends BaseControl {
	private static Logger logger = LoggerFactory.getLogger(TagSourceController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterTagId = "tagId";
	private static final String parameterSourceId = "sourceId";
	private static final String parameterSourceTitle = "sourceTitle";
	private static final String parameterSourceType = "sourceType";
	private static final String parameterTagSourceId = "id";
	private static final String parameterCount = "count";
	private static final String parameterStart = "start";
	private static final String parameterKeyword = "keyword";

	@Resource
	private TagSourceService tagSourceService;
	@Resource
	private NewTagSourceService newTagSourceService;
	@Resource
	private NewTagService newTagService;
	@Resource
	private KnowledgeService knowledgeService;
	@Resource
	private TagService tagService;

	//@formatter:off
	/**
	 * 1. 查询一个资源下的标签
	 * curl -i "http://localhost:8081/tags/source/getSourceList?appKey=1&userId=111&sourceId=1&sourceType=1"
	 * @param request
	 */
	@RequestMapping(path = "/tags/source/getSourceList", method = { RequestMethod.GET })
	public Object getSourceList(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
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
			List<TagSource> tagsTypes = tagSourceService.getTagSourcesByAppIdSourceIdSourceType(loginAppId, sourceId, sourceType);
			// 2.转成框架数据
//			mappingJacksonValue = new MappingJacksonValue(tagsTypes);
//			// 3.创建页面显示数据项的过滤器
//			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
//			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return com.alibaba.fastjson.JSONObject.toJSON(tagsTypes);
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
	 */
	@RequestMapping(path = "/tags/source/getSourceListByTag", method = { RequestMethod.GET })
	public Object getSourceListByTag(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
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
			List<TagSource> tagsTypes = tagSourceService.getTagSourcesByAppIdTagId(loginAppId, tagId, start, count);
			// 2.转成框架数据
//			mappingJacksonValue = new MappingJacksonValue(tagsTypes);
//			// 3.创建页面显示数据项的过滤器
//			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
//			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return com.alibaba.fastjson.JSONObject.toJSON(tagsTypes);
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
	 */
	@RequestMapping(path = "/tags/source/getSourceListByTagAndType", method = { RequestMethod.GET })
	public Object getSourceListByTag(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
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
			List<TagSource> tagsTypes = tagSourceService.getTagSourcesByAppIdTagIdAndType(loginAppId, tagId, sourceType, start, count);
			// 2.转成框架数据
//			mappingJacksonValue = new MappingJacksonValue(tagsTypes);
//			// 3.创建页面显示数据项的过滤器
//			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
//			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return com.alibaba.fastjson.JSONObject.toJSON(tagsTypes);
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
	 */
	@RequestMapping(path = "/tags/source/getSourceCountByTag", method = { RequestMethod.GET })
	public Integer getSourceCountByTag(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
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
			Integer resCount = tagSourceService.countTagSourcesByAppIdTagId(loginAppId, tagId);
			// 2.转成框架数据
//			mappingJacksonValue = new MappingJacksonValue(resCount);
//			// 3.创建页面显示数据项的过滤器
//			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
//			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return resCount;
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
	 * @throws TagSourceServiceException
	 */
	@RequestMapping(path = "/tags/source/createTagSource", method = { RequestMethod.POST })
	public Object createTagSource(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
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

			Long id = tagSourceService.createTagSource(source);
			Map<String, Long> resultMap = new HashMap<String, Long>();
			resultMap.put("id", id);
//			// 2.转成框架数据
//			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return com.alibaba.fastjson.JSONObject.toJSON(resultMap);
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
	 * @throws TagSourceServiceException
	 */
	@RequestMapping(path = "/tags/source/deleteTagSource", method = { RequestMethod.GET, RequestMethod.DELETE})
	public Object deleteTagSource(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = TagSourceController.parameterTagSourceId, required = true) Long id,
			HttpServletRequest request) throws TagSourceServiceException {
		//@formatter:on
//		Long loginAppId = LoginUserContextHolder.getAppKey();
//		Long loginUserId = LoginUserContextHolder.getUserId();
		Long loginAppId=this.DefaultAppId;
		Long loginUserId=this.getUserId(request);
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Boolean success = tagSourceService.removeTagSource(loginAppId, loginUserId, id); // 服务验证Owner
			Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
			resultMap.put("success", success);
			// 2.转成框架数据
//			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return com.alibaba.fastjson.JSONObject.toJSON(resultMap);
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
			filter.add("tagId"); // 标签名称
			filter.add("userId"); // 创建人id

		}

		filterProvider.addFilter(TagSource.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	/**
	 * 根据sourceId查询资源列表
	 */
	@RequestMapping(path = "/tags/source/getSourceListBySourceId", method = { RequestMethod.GET })
	public InterfaceResult  getSourceListBySourceId(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
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
				return interfaceResult;
			}
			if(sourceId == null){
				logger.error("sourceId is null");
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"sourceId 不能为空！");
				return interfaceResult;
			}
			if(sourceType == null){
				logger.error("sourceType is null");
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"sourceType 不能为空！");
				return interfaceResult;
			}
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务r
			List<TagSource> tagSourceList = null;
			try {
				tagSourceList = tagSourceService.getTagSourcesBySourceId(loginAppId,sourceId,sourceType);
			} catch (Exception e) {
				e.printStackTrace();
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION,"数据库操作失败！");
				return interfaceResult;
			}
			// 2.转成框架数据
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
			interfaceResult.setResponseData(tagSourceList);
//			mappingJacksonValue = new MappingJacksonValue(interfaceResult);
//			// 3.创建页面显示数据项的过滤器
//			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
//			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return interfaceResult;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION,"获取标签列表失败！");
			return interfaceResult;
		}

	}

	/**
	 * 批量更新标签
	 */
	@RequestMapping(path = "/tags/source/updateTagSources", method = { RequestMethod.POST })
	public InterfaceResult updateTagSources(@RequestParam(name = TagSourceController.parameterFields, defaultValue = "") String fileds,
												HttpServletRequest request, HttpServletRequest response) throws TagSourceServiceException {
		String requestJson = null;
		MappingJacksonValue mappingJacksonValue = null;
		InterfaceResult interfaceResult=null;
		List<TagSource> tagSourceList = null;
		List<Property> tags=null;
		List<Long> tagIds=new ArrayList<Long>();
		try {
			Long loginAppId = this.DefaultAppId;
			Long loginUserId = this.getUserId(request);
			if(loginUserId==null){
				logger.error("userId is null");
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION,"用户长时间未操作或者未登录，权限失效！");
				return interfaceResult;
			}
			requestJson = this.getBodyParam(request);
			if (requestJson != null && !"".equals(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				Long sourceId = j.getLong("sourceId");
				String sourceTitle = j.getString("sourceTitle");
				long sourceType = j.getInt("sourceType");
				String sourceExtra=null;
				if(j.containsKey("sourceExtra")){
					sourceExtra = j.getString("sourceExtra");
				}
				int columnType=j.getInt("columnType");;
				if(sourceType==8){
					if (columnType<=0) {
						logger.error("columnType is null..");
						interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"columnType不能为空！");
						return interfaceResult;
					}
				}
				int supDem=0;
				if(j.containsKey("supDem")){
					supDem=j.getInt("supDem");
				}
				tags = JsonUtils.getList4Json(j.getString("tags"), Property.class);
				if (tags != null) {
					for (Property property : tags) {
						tagIds.add(property.getId());
					}
				}
				if (sourceId <= 0 || sourceId == null) {
					logger.error("sourceId is null..");
					interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION, "sourceId不能为空！");
					return interfaceResult;
				}
				if (sourceTitle == null || sourceTitle.trim().length() <= 0) {
					logger.error("sourceTitle is null..");
					interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION, "sourceTitle不能为空！");
					return interfaceResult;
				}
				if (sourceType <= 0) {
					logger.error("sourceType is null..");
					interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION, "sourceType不能为空！");
					return interfaceResult;
				}
				newTagSourceService.updateTagsources(loginAppId, loginUserId, sourceId, sourceType, tagIds, sourceTitle,columnType,supDem,sourceExtra);
			if (sourceType == 8) {
					try {
						knowledgeService.updateTag(loginUserId, sourceId, columnType, tagIds);

					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Add tags to knowledge failed,userId=" + loginUserId + ",knowledgeId=" + sourceId);
					}
				}
					try {
						tagSourceList = tagSourceService.getTagSourcesBySourceId(loginAppId, sourceId, sourceType);
					} catch (Exception e) {
						e.printStackTrace();
						interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION, "数据库操作失败！");
						return interfaceResult;
					}
					// 2.转成框架数据
					interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
					interfaceResult.setResponseData(tagSourceList);
//					mappingJacksonValue = new MappingJacksonValue(interfaceResult);
//					// 3.创建页面显示数据项的过滤器
//					SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
//					mappingJacksonValue.setFilters(filterProvider);
			}
			// 4.返回结果
			return interfaceResult;
		} catch (Exception e) {
			e.printStackTrace();
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION,"更新标签列表失败！");
			return interfaceResult;
		}
	}


	/**
	 * 模块下标签的删除（只删除标签下当前模块的资源）
	 * @param debug
	 * @param tagId
	 * @param sourceType
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(path = "/tags/source/deleteModulTagSource", method = { RequestMethod.POST})
	public InterfaceResult deleteModulTagSource(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
													@RequestParam(name = TagSourceController.parameterTagId, required = true) Long tagId,
													@RequestParam(name = TagSourceController.parameterSourceType, required = true) int sourceType,
													@RequestParam(name = TagSourceController.parameterSourceId, required = true) long sourceId,
													HttpServletRequest request) throws Exception {
		Long loginUserId=this.getUserId(request);
		Map<String, Boolean> responseDataMap = new HashMap<String, Boolean>();
		boolean flag=false;
		try {
			if(sourceType==0){
				tagService.removeTag(loginUserId,tagId);
				flag = newTagSourceService.deleteSourceByType(loginUserId, tagId, sourceType, sourceId);
			}else {
				flag = newTagSourceService.deleteSourceByType(loginUserId, tagId, sourceType, sourceId);
			}
			responseDataMap.put("result", flag);
			return InterfaceResult.getSuccessInterfaceResultInstance(responseDataMap);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return InterfaceResult.getInterfaceResultInstance("0002","系统异常");
		}
	}


	/**
	 * 新的标签列表及标签搜索
	 * @param debug
	 * @param sourceType
	 * @param keyword
	 * @param start
	 * @param count
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "/tags/tags/selectTagList", method = { RequestMethod.POST })
	public InterfaceResult selectTagList(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
											   @RequestParam(name = TagSourceController.parameterSourceType, required = true) int sourceType,
											   @RequestParam(name = TagSourceController.parameterKeyword, required = true) String keyword,
											   @RequestParam(name = TagSourceController.parameterStart, required = true) int start,
											   @RequestParam(name = TagSourceController.parameterCount, required = true) int count,
											   HttpServletRequest request) throws Exception {
		Long loginUserId=this.getUserId(request);
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		try {
			logger.info("新的标签列表及标签搜索:**sourceType="+sourceType+"**keyword="+keyword+"**start="+start+"**count="+count);
			List<TagSearchVO> tags = newTagService.selectTagListByKeword(loginUserId, keyword, sourceType, start, count);
			long counts = newTagService.countTagListByKeword(loginUserId, keyword);
			responseDataMap.put("list", tags);
			responseDataMap.put("totalcount", counts);
			return InterfaceResult.getSuccessInterfaceResultInstance(responseDataMap);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return InterfaceResult.getInterfaceResultInstance("0002","系统异常");
		}
	}

	/**
	 * 搜索资源
	 * @param debug
	 * @param sourceType
	 * @param keyword
	 * @param start
	 * @param count
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "/tags/source/searchSource", method = { RequestMethod.POST })
	public InterfaceResult searchSource(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
										   @RequestParam(name = TagSourceController.parameterTagId, required = true) long tagId,
										   @RequestParam(name = TagSourceController.parameterSourceType, required = true) int sourceType,
										   @RequestParam(name = TagSourceController.parameterKeyword, required = true) String keyword,
										   @RequestParam(name = TagSourceController.parameterStart, required = true) int start,
										   @RequestParam(name = TagSourceController.parameterCount, required = true) int count,
										   HttpServletRequest request) throws Exception {
		Long loginUserId=this.getUserId(request);
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		try {
			logger.info("搜索资源:**loginUserId="+loginUserId+"**sourceType="+sourceType+"**keyword="+keyword+"**start="+start+"**count="+count+
			"**标签tagId-"+tagId);
			List<SourceSearchVO> tags = newTagSourceService.searchTagSources(loginUserId,tagId,keyword,sourceType,start,count);
			responseDataMap.put("list", tags);
			responseDataMap.put("totalcount", newTagSourceService.countSourceByTagId(loginUserId,tagId,sourceType,keyword));
			responseDataMap.put("personcount", newTagSourceService.countSourceByTagId(loginUserId,tagId,2,keyword));
			responseDataMap.put("customercount", newTagSourceService.countSourceByTagId(loginUserId,tagId,3,keyword));
			responseDataMap.put("demandcount", newTagSourceService.countSourceByTagId(loginUserId,tagId,7,keyword));
			responseDataMap.put("knowledgecount", newTagSourceService.countSourceByTagId(loginUserId,tagId,8,keyword));
			return InterfaceResult.getSuccessInterfaceResultInstance(responseDataMap);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return InterfaceResult.getInterfaceResultInstance("0002","系统异常");
		}
	}


	/**
	 * 全局搜索资源
	 * @param debug
	 * @param sourceType
	 * @param keyword
	 * @param start
	 * @param count
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "/tags/source/searchTagSourceList", method = { RequestMethod.POST })
	public InterfaceResult searchTagSourceList(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
									  @RequestParam(name = TagSourceController.parameterSourceType, required = true) int sourceType,
									  @RequestParam(name = TagSourceController.parameterKeyword, required = true) String keyword,
									  @RequestParam(name = TagSourceController.parameterStart, required = true) int start,
							  		  @RequestParam(name = TagSourceController.parameterCount, required = true) int count,
									  HttpServletRequest request) throws Exception {
		Long loginUserId=this.getUserId(request);
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		try {
			logger.info("全局搜索资源:**sourceType="+sourceType+"**keyword="+keyword+"**start="+start+"**count="+count);
			List<TagSourceSearchVO> tags = newTagService.selectTagSourceList(loginUserId, keyword, sourceType, start, count);
			long counts = newTagService.countTagListByKeword(loginUserId,keyword);
			responseDataMap.put("list", tags);
			responseDataMap.put("totalcount", counts);
			if(tags==null || tags.size()==0){
				responseDataMap.put("sourceList", newTagSourceService.searchSourcesExctTag(loginUserId,keyword,sourceType));
			}else {
				responseDataMap.put("sourceList", null);
			}
//			return InterfaceResult.getSuccessInterfaceResultInstance(JsonUtils.beanToJson(responseDataMap));
			return InterfaceResult.getSuccessInterfaceResultInstance(responseDataMap);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return InterfaceResult.getInterfaceResultInstance("0002","系统异常");
		}
	}

	/**
	 * 更改或新增标签资源
	 * @param debug
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "/tags/source/addTagSource", method = { RequestMethod.POST })
	public InterfaceResult addTagSource(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
									  HttpServletRequest request) throws Exception {
		String requestJson = null;
		InterfaceResult interfaceResult=null;
		List<TagSource> tagSourceList = null;
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		try {
			Long loginUserId = this.getUserId(request);
			if(loginUserId==null){
				logger.error("userId is null");
				return interfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION,"用户长时间未操作或者未登录，权限失效！");
			}
			requestJson = this.getBodyParam(request);
			if (requestJson != null && !"".equals(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				Long tagId = j.getLong("tagId");
				int sourceType = j.getInt("sourceType");
				tagSourceList = JsonUtils.getList4Json(j.getString("tagSourceList"), TagSource.class);
				logger.info("客户端出入的资源列表：json="+JsonUtils.beanToJson(tagSourceList));
				boolean b1 = newTagSourceService.updateSourceByTagId(loginUserId, tagId, sourceType, tagSourceList);
				responseDataMap.put("result",b1);
			}
			return InterfaceResult.getSuccessInterfaceResultInstance(responseDataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return InterfaceResult.getInterfaceResultInstance("0002","更新标签资源失败");
		}
	}

	/**
	 * 更改或新增标签资源
	 * @param debug
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "/tags/source/batchDeleteSource", method = { RequestMethod.POST })
	public InterfaceResult batchDeleteSource(@RequestParam(name = TagSourceController.parameterDebug, defaultValue = "") String debug,
										HttpServletRequest request) throws Exception {
		String requestJson = null;
		InterfaceResult interfaceResult=null;
		List<TagSource> tagSourceIdList = null;
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		boolean flag=false;
		try {
			Long loginUserId = this.getUserId(request);
			if(loginUserId==null){
				logger.error("userId is null");
				return interfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION,"用户长时间未操作或者未登录，权限失效！");
			}
			requestJson = this.getBodyParam(request);
			if (requestJson != null && !"".equals(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				tagSourceIdList = JsonUtils.getList4Json(j.getString("tagSourceIdList"), TagSource.class);
				logger.info("客户端出入的资源列表：json="+JsonUtils.beanToJson(tagSourceIdList));
				if(tagSourceIdList!=null && tagSourceIdList.size()>0){
					Iterator<TagSource> iterator = tagSourceIdList.iterator();
					while (iterator.hasNext()){
						TagSource next = iterator.next();
						flag=newTagSourceService.deleteSourceByType(loginUserId,next.getTagId(),(int) next.getSourceType(),next.getSourceId());
					}
				}
				responseDataMap.put("result",flag);
			}
			return InterfaceResult.getSuccessInterfaceResultInstance(responseDataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return InterfaceResult.getInterfaceResultInstance("0002","更新标签资源失败");
		}
	}
}
