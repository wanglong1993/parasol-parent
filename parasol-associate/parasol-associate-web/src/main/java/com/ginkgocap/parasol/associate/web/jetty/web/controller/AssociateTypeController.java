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

package com.ginkgocap.parasol.associate.web.jetty.web.controller;

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
import com.ginkgocap.parasol.associate.exception.AssociateTypeServiceException;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.service.AssociateTypeService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class AssociateTypeController extends BaseControl {
	private static Logger logger = Logger.getLogger(AssociateTypeController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterName = "name";


	@Autowired
	private AssociateTypeService associateTypeService;

	/**
	 * 查询关联的类型列表</br>
	 * <code>
	 * export access_token=6b48bab7-e545-4ef0-ac25-af02bfe92a0b</br>
	 * curl -i http://api.test.gintong.com/associate/type/getTypeList?access_token=${access_token}</br>
	 * </code>
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/associate/type/getTypeList", method = { RequestMethod.GET })
	public MappingJacksonValue getFunctionClassList(@RequestParam(name = AssociateTypeController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = AssociateTypeController.parameterDebug, defaultValue = "") String debug) {
		Long loginAppId = LoginUserContextHolder.getAppKey();
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<AssociateType> associateTypes = associateTypeService.getAssociateTypessByAppId(loginAppId);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(associateTypes);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (AssociateTypeServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	
	//@format:
	/**
	 * 创建关联的类型
	 * </br>
	 * <code>
	 * export access_token=6b48bab7-e545-4ef0-ac25-af02bfe92a0b</br>
	 * curl -i http://api.test.gintong.com/associate/type/createTypeList?access_token=${access_token} -d"name=人脉"</br>
	 * curl -i http://api.test.gintong.com/associate/type/createTypeList?access_token=${access_token} -d"name=组织"</br>
	 * curl -i http://api.test.gintong.com/associate/type/createTypeList?access_token=${access_token} -d"name=需求"</br>
	 * curl -i http://api.test.gintong.com/associate/type/createTypeList?access_token=${access_token} -d"name=知识"</br>
	 * curl -i http://api.test.gintong.com/associate/type/createTypeList?access_token=${access_token} -d"name=会议"</br>
	 * </code>
	 * @param request
	 * @return
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = "/associate/type/createAssociateType", method = { RequestMethod.POST })
	public MappingJacksonValue createFunctionClassList(@RequestParam(name = AssociateTypeController.parameterName, defaultValue = "") String name,
			@RequestParam(name = AssociateTypeController.parameterDebug, defaultValue = "") String debug) {
		Long loginAppId = LoginUserContextHolder.getAppKey();
		MappingJacksonValue mappingJacksonValue = null;
		try {
	
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			AssociateType associateType = new AssociateType();
			associateType.setAppId(loginAppId);
			associateType.setName(name);
			Long id = associateTypeService.createAssociateType(loginAppId, associateType);
			Map<String, Long> reusltMap = new HashMap<String, Long>();
			reusltMap.put("id", id);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		} catch (AssociateTypeServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
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
		}

		filterProvider.addFilter(AssociateType.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
}
