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
import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.model.Favorite;
import com.ginkgocap.parasol.favorite.service.FavoriteService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class FavoriteController extends BaseControl {
	private static Logger logger = Logger.getLogger(FavoriteController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterRootType = "rootType"; // 查询的应用分类
	private static final String parameterName = "name"; // 收藏夹名称
	private static final String parameterFavoriteId = "favoriteId"; // 收藏夹ID
	private static final String parameterToFavoriteId = "toFavoriteId"; // 移动收藏夹的目标，移动那个收藏夹下
	private static final String parameterParentId = "pId"; // 父收藏夹ID


	@Autowired
	private FavoriteService favoriteService;

	/**
	 * 2.创建分类下的根收藏夹
	 * 
	 * @param request
	 * @return
	 * @throws FavoriteServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/favorite/favorite/createRootFavorite" }, method = { RequestMethod.POST })
	public MappingJacksonValue createFavoriteRoot(@RequestParam(name = FavoriteController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FavoriteController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteController.parameterName, required = true) String name,
			@RequestParam(name = FavoriteController.parameterRootType, required = true) long rootType) throws FavoriteServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			Favorite favorite = new Favorite();
			favorite.setAppId(loginAppId);
			favorite.setUserId(loginUserId);
			favorite.setName(name);
			favorite.setTypeId(rootType);

			Long id = favoriteService.createFavoriteForRoot(rootType, favorite);
			Map<String, Long> reusltMap = new HashMap<String, Long>();
			reusltMap.put("id", id);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		}  catch (FavoriteServiceException e) {
			throw e;
		}
	}
	
	/**
	 * 3.创建子收藏夹
	 * 
	 * @param request
	 * @return
	 * @throws FavoriteServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/favorite/favorite/createSubFavorite" }, method = {RequestMethod.POST })
	public MappingJacksonValue createSubFavorite(@RequestParam(name = FavoriteController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FavoriteController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteController.parameterName, required = true) String name,
			@RequestParam(name = FavoriteController.parameterParentId, required = true) long parentId) throws FavoriteServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			Favorite favorite = new Favorite();
			favorite.setAppId(loginAppId);
			favorite.setUserId(loginUserId);
			favorite.setName(name);
			favorite.setPid(parentId);

			Long id = favoriteService.createFavoriteForChildren(parentId, favorite);
			Map<String, Long> reusltMap = new HashMap<String, Long>();
			reusltMap.put("id", id);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		}  catch (FavoriteServiceException e) {
			throw e;
		}
	}

	/**
	 * 删除目录
	 * 
	 * @param debug
	 * @param appId
	 * @param userId
	 * @param favoriteId
	 * @return
	 * @throws FavoriteServiceException
	 */
	@RequestMapping(path = { "/favorite/favorite/deleteFavorite" }, method = { RequestMethod.GET, RequestMethod.DELETE })
	public MappingJacksonValue deleteFavoriteRoot(@RequestParam(name = FavoriteController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteController.parameterFavoriteId, required = true) Long favoriteId) throws FavoriteServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			Boolean b = favoriteService.removeFavorite(loginAppId, loginUserId, favoriteId);
			Map<String, Boolean> reusltMap = new HashMap<String, Boolean>();
			reusltMap.put("success", b);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		}  catch (FavoriteServiceException e) {
			throw e;
		}
	}

	/**
	 * 更新收藏夹
	 * 
	 * @param debug
	 * @param appId
	 * @param userId
	 * @param favoriteId
	 * @return
	 * @throws FavoriteServiceException
	 */
	@RequestMapping(path = { "/favorite/favorite/updateFavorite" }, method = { RequestMethod.POST })
	public MappingJacksonValue updateFavoriteRoot(@RequestParam(name = FavoriteController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FavoriteController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteController.parameterFavoriteId, required = true) Long favoriteId,
			@RequestParam(name = FavoriteController.parameterName, required = true) String name) throws FavoriteServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			Favorite favorite = new Favorite();
			favorite.setAppId(loginAppId);
			favorite.setName(name);
			favorite.setId(favoriteId);

			Boolean b = favoriteService.updateFavorite(loginAppId, loginUserId, favorite);
			Map<String, Boolean> reusltMap = new HashMap<String, Boolean>();
			reusltMap.put("success", b);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		} catch (FavoriteServiceException e) {
			throw e;
		}
	}

	/**
	 * 移动收藏夹
	 * @param debug
	 * @param appId
	 * @param userId
	 * @param favoriteId
	 * @return
	 * @throws FavoriteServiceException
	 */
	@RequestMapping(path = { "/favorite/favorite/moveFavorite" }, method = { RequestMethod.POST })
	public MappingJacksonValue moveFavorite(@RequestParam(name = FavoriteController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FavoriteController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteController.parameterFavoriteId, required = true) Long favoriteId,
			@RequestParam(name = FavoriteController.parameterToFavoriteId, required = true) Long toFavoriteId) throws FavoriteServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			Boolean b = favoriteService.moveFavoriteToFavorite(loginAppId, loginUserId, favoriteId,toFavoriteId);
			Map<String, Boolean> reusltMap = new HashMap<String, Boolean>();
			reusltMap.put("success", b);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		}  catch (FavoriteServiceException e) {
			throw e;
		}
	}

	/**
	 * 查询根收藏夹
	 * 
	 * @param request
	 * @return
	 * @throws FavoriteServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/favorite/favorite/getRootList" }, method = { RequestMethod.GET })
	public MappingJacksonValue getRootList(@RequestParam(name = FavoriteController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FavoriteController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteController.parameterRootType, required = true) Long rootType) throws FavoriteServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<Favorite> directories = favoriteService.getFavoritesForRoot(loginAppId, loginUserId, rootType);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(directories);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (FavoriteServiceException e) {
			throw e;
		}
	}

	/**
	 * 查询子收藏夹
	 * 
	 * @param request
	 * @return
	 * @throws FavoriteServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/favorite/favorite/getSubList" }, method = { RequestMethod.GET })
	public MappingJacksonValue getSubList(@RequestParam(name = FavoriteController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FavoriteController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FavoriteController.parameterParentId, required = true) Long pid) throws FavoriteServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<Favorite> directories = favoriteService.getFavoritesByParentId(loginAppId, loginUserId, pid);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(directories);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (FavoriteServiceException e) {
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

		filterProvider.addFilter(Favorite.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
}
