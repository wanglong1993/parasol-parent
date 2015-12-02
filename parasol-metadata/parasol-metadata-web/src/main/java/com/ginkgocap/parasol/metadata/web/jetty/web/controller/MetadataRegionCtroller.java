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

package com.ginkgocap.parasol.metadata.web.jetty.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.rpc.RpcException;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.metadata.exception.CodeRegionServiceException;
import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.CodeRegionService;
import com.ginkgocap.parasol.metadata.type.CodeRegionType;
import com.ginkgocap.parasol.metadata.web.jetty.web.ResponseError;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class MetadataRegionCtroller extends BaseControl {
	private static Logger logger = Logger.getLogger(MetadataRegionCtroller.class);

	private static final String paramenterFields = "fields";
	private static final String paramenterDebug = "debug";

	@Autowired
	private CodeRegionService codeRegionService;

	/**
	 * 查询国内省
	 * 
	 * @param request
	 * @return
	 * @throws CodeRegionServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/region/getProvinces", method = { RequestMethod.GET })
	public MappingJacksonValue getProvinces(@RequestParam(name = MetadataRegionCtroller.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataRegionCtroller.paramenterDebug, defaultValue = "") String debug) throws CodeRegionServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<CodeRegion> codeRegions = codeRegionService.getCodeRegionsForRootByType(CodeRegionService.ROOT_PARENT_ID, CodeRegionType.TYPE_CHINAINLAND);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(codeRegions);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (CodeRegionServiceException e) {
			throw e;
		}
	}

	/**
	 * 根据省份ID查询下边的城市
	 * 
	 * @param request
	 * @return
	 * @throws CodeRegionServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/region/getCitys", method = { RequestMethod.GET })
	public MappingJacksonValue getCitys(@RequestParam(name = "pid", required = true) Long pid,
			@RequestParam(name = MetadataRegionCtroller.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataRegionCtroller.paramenterDebug, defaultValue = "") String debug) throws CodeRegionServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			List<CodeRegion> codeRegions = null;
			if (matchCodeRegionType(pid, CodeRegionType.TYPE_CHINAINLAND)) {
				codeRegions = codeRegionService.getCodeRegionsByParentId(pid);
			} else {
				logger.equals("pid object type don't match or parent codeRegion is null");
			}
			codeRegions = CollectionUtils.isEmpty(codeRegions) ? new ArrayList<CodeRegion>() : codeRegions;
			mappingJacksonValue = new MappingJacksonValue(codeRegions);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		} catch (CodeRegionServiceException e) {
			throw e;
		}
	}

	/**
	 * 根据城市ID查询区县ID
	 * 
	 * @param request
	 * @return
	 * @throws CodeRegionServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/region/getDistrictCounty", method = { RequestMethod.GET })
	public MappingJacksonValue getDistrictCounty(@RequestParam(name = "pid", required = true) Long pid,
			@RequestParam(name = MetadataRegionCtroller.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataRegionCtroller.paramenterDebug, defaultValue = "") String debug) throws CodeRegionServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			List<CodeRegion> codeRegions = null;
			if (matchCodeRegionType(pid, CodeRegionType.TYPE_CHINAINLAND)) {
				codeRegions = codeRegionService.getCodeRegionsByParentId(pid);
			} else {
				logger.equals("pid object type don't match or parent codeRegion is null");
			}
			codeRegions = CollectionUtils.isEmpty(codeRegions) ? new ArrayList<CodeRegion>() : codeRegions;
			mappingJacksonValue = new MappingJacksonValue(codeRegions);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		} catch (CodeRegionServiceException e) {
			throw e;
		}
	}

	/**
	 * 查询港澳 如果查询港澳的子节点直接输入ID，不输入就是查询父节点
	 * 
	 * @param request
	 * @return
	 * @throws CodeRegionServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/region/getHongKongMacao", method = { RequestMethod.GET })
	public MappingJacksonValue getHongKongMacao(@RequestParam(name = "pid", required = false) Long pid,
			@RequestParam(name = MetadataRegionCtroller.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataRegionCtroller.paramenterDebug, defaultValue = "") String debug) throws CodeRegionServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 1.查询港澳台
			List<CodeRegion> codeRegions = null;
			if (pid == null) {
				codeRegions = codeRegionService.getCodeRegionsForRootByType(CodeRegionService.ROOT_PARENT_ID, CodeRegionType.TYPE_GANGAOTAI);
			} else {
				codeRegions = matchCodeRegionType(pid, CodeRegionType.TYPE_GANGAOTAI) ? codeRegionService.getCodeRegionsByParentId(pid) : null;
			}
			codeRegions = CollectionUtils.isEmpty(codeRegions) ? new ArrayList<CodeRegion>() : codeRegions;
			mappingJacksonValue = new MappingJacksonValue(codeRegions);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		} catch (CodeRegionServiceException e) {
			throw e;
		}
	}

	/**
	 * 查询台湾 如果查询台湾的子节点直接输入ID，不输入就是查询父节点
	 * 
	 * @param request
	 * @return
	 * @throws CodeRegionServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/region/getTaiWan", method = { RequestMethod.GET })
	public MappingJacksonValue getTaiWan(@RequestParam(name = "pid", required = false) Long pid,
			@RequestParam(name = MetadataRegionCtroller.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataRegionCtroller.paramenterDebug, defaultValue = "") String debug) throws CodeRegionServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 1. 查询台湾
			List<CodeRegion> codeRegions = null;
			if (pid == null) {
				codeRegions = codeRegionService.getCodeRegionsForRootByType(CodeRegionService.ROOT_PARENT_ID, CodeRegionType.TYPE_TAIWAN);
			} else {
				codeRegions = matchCodeRegionType(pid, CodeRegionType.TYPE_TAIWAN) ? codeRegionService.getCodeRegionsByParentId(pid) : null;
			}
			codeRegions = CollectionUtils.isEmpty(codeRegions) ? new ArrayList<CodeRegion>() : codeRegions;
			mappingJacksonValue = new MappingJacksonValue(codeRegions);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		} catch (CodeRegionServiceException e) {
			throw e;
		}
	}

	/**
	 * 查询马来西亚，如果查询马来西亚的子节点直接输入ID，不输入就是查询马来西亚
	 * 
	 * @param request
	 * @return
	 * @throws CodeRegionServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/region/getMalaysia", method = { RequestMethod.GET })
	public MappingJacksonValue getMalaysia(@RequestParam(name = "pid", required = false) Long pid,
			@RequestParam(name = MetadataRegionCtroller.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataRegionCtroller.paramenterDebug, defaultValue = "") String debug) throws CodeRegionServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 1. 查询马来西亚
			List<CodeRegion> codeRegions = null;
			if (pid == null) {
				codeRegions = codeRegionService.getCodeRegionsForRootByType(CodeRegionService.ROOT_PARENT_ID, CodeRegionType.TYPE_MALAYSIA);
			} else {
				// 1. 查询马来西亚下边的内容
				codeRegions = matchCodeRegionType(pid, CodeRegionType.TYPE_MALAYSIA) ? codeRegionService.getCodeRegionsByParentId(pid) : null;
			}
			codeRegions = CollectionUtils.isEmpty(codeRegions) ? new ArrayList<CodeRegion>() : codeRegions;
			mappingJacksonValue = new MappingJacksonValue(codeRegions);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		} catch (CodeRegionServiceException e) {
			throw e;
		}
	}

	/**
	 * 查询海外
	 * 
	 * @param request
	 * @return
	 * @throws CodeRegionServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/metadata/region/getForignCountry", method = { RequestMethod.GET })
	public MappingJacksonValue getForignCountry(@RequestParam(name = "pid", required = false) Long pid,
			@RequestParam(name = MetadataRegionCtroller.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = MetadataRegionCtroller.paramenterDebug, defaultValue = "") String debug) throws CodeRegionServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 1. 查询海外
			List<CodeRegion> codeRegions = null;
			if (pid == null) {
				codeRegions = codeRegionService.getCodeRegionsForRootByType(CodeRegionService.ROOT_PARENT_ID, CodeRegionType.TYPE_FOREIGNCOUNTRY);
			} else {
				// 1. 查询查询海外下边的内容
				codeRegions = matchCodeRegionType(pid, CodeRegionType.TYPE_FOREIGNCOUNTRY) ? codeRegionService.getCodeRegionsByParentId(pid) : null;
			}
			codeRegions = CollectionUtils.isEmpty(codeRegions) ? new ArrayList<CodeRegion>() : codeRegions;
			mappingJacksonValue = new MappingJacksonValue(codeRegions);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		} catch (CodeRegionServiceException e) {
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
			filter.add("id"); // 主键',
			// filter.add("pid"); // 父主键',
			filter.add("cname"); // '类型名称',
		}

		filterProvider.addFilter(CodeRegion.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	/**
	 * 检查一个CodeRegion的type
	 * 
	 * @param parentId
	 * @param codeRegionType
	 * @return
	 * @throws CodeRegionServiceException
	 */
	private boolean matchCodeRegionType(Long id, CodeRegionType codeRegionType) throws CodeRegionServiceException {
		CodeRegion parentCodeRegion = null;
		if (id != null && id > 0) {
			parentCodeRegion = codeRegionService.getCodeRegionById(id);
		}

		if (parentCodeRegion != null && ObjectUtils.equals(parentCodeRegion.getType(), codeRegionType.getValue())) {
			return true;
		} else {
			return false;
		}

	}

}
