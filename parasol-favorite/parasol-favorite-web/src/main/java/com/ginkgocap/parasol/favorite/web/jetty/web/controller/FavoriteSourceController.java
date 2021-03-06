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

package com.ginkgocap.parasol.favorite.web.jetty.web.controller;

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
import com.ginkgocap.parasol.favorite.exception.FavoriteSourceServiceException;
import com.ginkgocap.parasol.favorite.model.FavoriteSource;
import com.ginkgocap.parasol.favorite.service.FavoriteSourceService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class FavoriteSourceController extends BaseControl {
	private static Logger logger = Logger.getLogger(FavoriteSourceController.class);
	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterFavoriteId = "favoriteId";
	private static final String parameterSourceId = "sourceId";
	private static final String parameterSourceType = "sourceType";
	private static final String parameterSourceUrl = "sourceUrl";
	private static final String parameterSourceTitle = "sourceTitle";
	private static final String parameterSourceData = "sourceData";
	private static final String parameterFavoriteSourceIds="ids";
	private static final String parameterFavoriteSourceId="id";


	@Autowired
	private FavoriteSourceService favoriteSourceService;

	/**
	 * 查询收藏夹下的资源
	 * 
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/favorite/source/getSourceList", method = { RequestMethod.GET })
	public MappingJacksonValue getSourceList(@RequestParam(name = FavoriteSourceController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FavoriteSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteSourceController.parameterFavoriteId, required = true) Long favoriteId) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<FavoriteSource> favoriteTypes = favoriteSourceService.getFavoriteSourcesByFavoriteId(loginAppId, loginUserId, favoriteId);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(favoriteTypes);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (FavoriteSourceServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	/**
	 * 2. 创建一个FavoriteSource
	 * 
	 * @param request
	 * @return
	 * @throws FavoriteSourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/favorite/source/createSource", method = { RequestMethod.POST })
	public MappingJacksonValue createFavoriteSource(@RequestParam(name = FavoriteSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteSourceController.parameterFavoriteId, required = true) Long favoriteId,
			@RequestParam(name = FavoriteSourceController.parameterSourceId, required = true) Long sourceId,
			@RequestParam(name = FavoriteSourceController.parameterSourceType, required = true) int sourceType,
			@RequestParam(name = FavoriteSourceController.parameterSourceUrl, defaultValue = "", required = false) String sourceUrl,
			@RequestParam(name = FavoriteSourceController.parameterSourceTitle, required = true) String sourceTitle,
			@RequestParam(name = FavoriteSourceController.parameterSourceData, defaultValue = "", required = false) String sourceData) throws FavoriteSourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			FavoriteSource source = new FavoriteSource();
			source.setAppId(loginAppId);
			source.setUserId(loginUserId);
			source.setFavoriteId(favoriteId);
			source.setSourceId(sourceId);
			source.setSourceType(sourceType);
			source.setSourceUrl(sourceUrl);
			source.setSourceTitle(sourceTitle);
			source.setSourceData(sourceData);

			Long id = favoriteSourceService.createFavoriteSources(source);
			Map<String, Long> resultMap = new HashMap<String, Long>();
			resultMap.put("id", id);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (FavoriteSourceServiceException e) {
			e.printStackTrace(System.err);
			throw e;
		}
	}

	/**
	 * 2. 删除一个FavoriteSource
	 * 
	 * @param request
	 * @return
	 * @throws FavoriteSourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/favorite/source/deleteSource", method = { RequestMethod.GET })
	public MappingJacksonValue deleteFavoriteSource(@RequestParam(name = FavoriteSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteSourceController.parameterFavoriteSourceId, required = true) Long id) throws FavoriteSourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			Boolean success = favoriteSourceService.removeFavoriteSources(loginAppId,loginUserId,id);
			Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
			resultMap.put("success", success);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		}  catch (FavoriteSourceServiceException e) {
			e.printStackTrace(System.err);
			throw e;
		}
	}

	
	/**
	 * 2. 移动多个或者一个FavoriteSource到其它收藏夹下
	 * 
	 * @param request
	 * @return
	 * @throws FavoriteSourceServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/favorite/source/moveSource", method = { RequestMethod.POST })
	public MappingJacksonValue moveFavoriteSource(@RequestParam(name = FavoriteSourceController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteSourceController.parameterFavoriteSourceIds, required = true) Long[] ids,
			@RequestParam(name = FavoriteSourceController.parameterFavoriteId, required = true) Long favoriteId) throws FavoriteSourceServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			// TODO: 没有实现这个方法
			Boolean success = favoriteSourceService.moveFavoriteSources(loginUserId, loginAppId, favoriteId, ids);
			Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
			resultMap.put("success", success);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			// 4.返回结果
			return mappingJacksonValue;
		}  catch (FavoriteSourceServiceException e) {
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

		filterProvider.addFilter(FavoriteSource.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
}
