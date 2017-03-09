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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.directory.web.jetty.modle.Property;
import com.ginkgocap.parasol.directory.web.jetty.utils.JsonReadUtil;
import com.ginkgocap.parasol.directory.web.jetty.utils.JsonUtils;
import com.ginkgocap.ywxt.knowledge.service.KnowledgeService;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterDirectoryId = "directoryId";
	private static final String parameterSourceId = "sourceId";
	private static final String parameterSourceType = "sourceType";
	private static final String parameterSourceUrl = "sourceUrl";
	private static final String parameterSourceTitle = "sourceTitle";
	private static final String parameterSourceData = "sourceData";
	private static final String parameterDirectorySourceIds="ids";
	private static final String parameterDirectorySourceId="id";


	@Autowired
	private DirectorySourceService directorySourceService;

	@Autowired
	private KnowledgeService knowledgeService;
	/**
	 * 查询目录下的资源
	 * 
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/directory/source/getSourceList", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceList(@RequestParam(name = DirectorySourceController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = DirectorySourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = DirectorySourceController.parameterDirectoryId, required = true) Long directoryId,
			HttpServletRequest request) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// Long loginAppId = LoginUserContextHolder.getAppKey();
			// Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId = this.DefaultAppId;
			Long loginUserId = this.getUserId(request);
			
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
	 * @throws com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/directory/source/createSource", method = { RequestMethod.POST })
	public MappingJacksonValue createDirectorySource(@RequestParam(name = DirectorySourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = DirectorySourceController.parameterDirectoryId, required = true) Long directoryId,
			@RequestParam(name = DirectorySourceController.parameterSourceId, required = true) Long sourceId,
			@RequestParam(name = DirectorySourceController.parameterSourceType, required = true) int sourceType,
			@RequestParam(name = DirectorySourceController.parameterSourceUrl, defaultValue = "", required = false) String sourceUrl,
			@RequestParam(name = DirectorySourceController.parameterSourceTitle, required = true) String sourceTitle,
			@RequestParam(name = DirectorySourceController.parameterSourceData, defaultValue = "", required = false) String sourceData,
			HttpServletRequest request) throws DirectorySourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// Long loginAppId = LoginUserContextHolder.getAppKey();
			// Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId = this.DefaultAppId;
			Long loginUserId = this.getUserId(request);
			
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
	 * @throws com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/directory/source/deleteSource", method = { RequestMethod.GET })
	public MappingJacksonValue deleteDirectorySource(@RequestParam(name = DirectorySourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = DirectorySourceController.parameterDirectorySourceId, required = true) Long id,
			HttpServletRequest request) throws DirectorySourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// Long loginAppId = LoginUserContextHolder.getAppKey();
			// Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId = this.DefaultAppId;
			Long loginUserId = this.getUserId(request);
			
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
	 * @throws com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/directory/source/moveSource", method = { RequestMethod.POST })
	public MappingJacksonValue moveDirectorySource(@RequestParam(name = DirectorySourceController.parameterDebug, defaultValue = "") String debug,
		@RequestParam(name = DirectorySourceController.parameterDirectorySourceIds, required = true) Long[] ids,
		@RequestParam(name = DirectorySourceController.parameterDirectoryId, required = true) Long directoryId,
		HttpServletRequest request) throws DirectorySourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// Long loginAppId = LoginUserContextHolder.getAppKey();
			// Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId = this.DefaultAppId;
			Long loginUserId = this.getUserId(request);
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
			filter.add("directoryId"); // 资源的Data
		}

		filterProvider.addFilter(DirectorySource.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	//@RequestMapping(path ="/directory/source/getSource", method = { RequestMethod.GET })
	@RequestMapping(path = "/directory/source/getSourceListBySourceId", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceListBySourceId(@RequestParam(name = DirectorySourceController.parameterFields, defaultValue = "") String fileds,
												   @RequestParam(name = DirectorySourceController.parameterDebug, defaultValue = "") String debug,
												   @RequestParam(name = DirectorySourceController.parameterSourceId, required = true) Long sourceId,
												   @RequestParam(name = DirectorySourceController.parameterSourceType, required = true) Integer sourceType,
												   HttpServletRequest request) {
		MappingJacksonValue mappingJacksonValue = null;
		List<DirectorySource> directorySourceList=null;
		InterfaceResult interfaceResult=null;
		try {
			Long loginAppId = this.DefaultAppId;
			Long loginUserId = this.getUserId(request);
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
			try {
				directorySourceList = directorySourceService.getDirectorySourcesBySourceId(loginUserId, loginAppId, sourceType, sourceId);
			} catch (Exception e) {
				e.printStackTrace();
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION,"数据库操作失败！");
				return new MappingJacksonValue(interfaceResult);
			}
			// 2.转成框架数据
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
			interfaceResult.setResponseData(directorySourceList);
			mappingJacksonValue = new MappingJacksonValue(interfaceResult);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION,"获取目录失败！");
			return new MappingJacksonValue(interfaceResult);
		}
	}

	/**
	 * 批量更新目录
	 * @return
	 */
	@RequestMapping(path = "/directory/source/updateSources", method = { RequestMethod.POST })
	public MappingJacksonValue updateDirectorySource(@RequestParam(name = DirectorySourceController.parameterDebug, defaultValue = "") String debug,
			                               @RequestParam(name = DirectorySourceController.parameterFields, defaultValue = "") String fileds,
										   HttpServletRequest request, HttpServletResponse response) throws DirectorySourceServiceException {
		String requestJson = null;
		MappingJacksonValue mappingJacksonValue = null;
		InterfaceResult interfaceResult=null;
		List<Property> directorysList=null;
		List<Long> direIds=new ArrayList<Long>();
		Long loginUserId = null;
		try {
			Long loginAppId = this.DefaultAppId;
			loginUserId = this.getUserId(request);
			if(loginUserId==null){
				logger.error("userId is null");
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION,"用户长时间未操作或者未登录，权限失效！");
				return new MappingJacksonValue(interfaceResult);
			}
			requestJson = this.getBodyParam(request);
			if (requestJson != null && !"".equals(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				long sourceId = j.getLong("sourceId");
				String sourceTitle = j.getString("sourceTitle");
				int sourceType = j.getInt("sourceType");
				int columnType=0;
				if(sourceType==8){
					columnType=j.getInt("columnType");
					if (columnType<=0) {
						logger.error("columnType is null..");
						interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"columnType不能为空！");
						return new MappingJacksonValue(interfaceResult);
					}
				}
				directorysList=JsonUtils.getList4Json(j.getString("directorys"), Property.class);
				if (directorysList != null) {
					for (Property property : directorysList) {
						direIds.add(property.getId());
					}
				}
				if (sourceId<=0) {
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
				Long sType=new Long(sourceType);
				directorySourceService.updateDiresources(loginAppId,loginUserId,sourceId,sType,direIds,sourceTitle);
				if (sourceType == 8) {
					try {
						knowledgeService.updateDirectory(loginUserId, sourceId, columnType, direIds);

					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Add tags to knowledge failed,userId=" + loginUserId + ",knowledgeId=" + sourceId);
					}
				}
				List<DirectorySource> directorySourceList = directorySourceService.getDirectorySourcesBySourceId(loginUserId, loginAppId, sourceType, sourceId);
				// 2.转成框架数据
				interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
				interfaceResult.setResponseData(directorySourceList);
				mappingJacksonValue = new MappingJacksonValue(interfaceResult);
				// 3.创建页面显示数据项的过滤器
				SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
				mappingJacksonValue.setFilters(filterProvider);
			}
			return mappingJacksonValue;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update categorys remove failed...userid=" + loginUserId);
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION,"更新目录失败！");
			return new MappingJacksonValue(interfaceResult);
		}
	}
}
