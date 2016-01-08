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

package com.ginkgocap.parasol.sensitive.web.jetty.web.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.sensitive.exception.SensitiveWordServiceException;
import com.ginkgocap.parasol.sensitive.model.SensitiveWord;
import com.ginkgocap.parasol.sensitive.service.SensitiveWordService;
import com.ginkgocap.parasol.sensitive.web.jetty.web.ResponseError;

/**
 * 
* @Title: SensitiveWordController.java
* @Package com.ginkgocap.parasol.sensitive.web.jetty.web.controller
* @Description: TODO(敏感词控制器)
* @author fuliwen@gintong.com  
* @date 2016年1月6日 上午10:05:55
* @version V1.0
 */
@RestController
public class SensitiveWordController extends BaseControl {

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterAppId = "appKey"; // 应用的Key
	private static final String parameterUserId = "userId"; // 访问的用户参数
	private static final String parameterType = "type"; // 查询的应用分类
	private static final String parameterWord = "word"; // 敏感词
	private static final String parameterLevel = "level"; // 敏感词等级
	private static final String parameterWordId = "wordId"; // 消息内容
	private static final String parameterText = "text"; // 校验文本

	@Resource
	private SensitiveWordService sensitiveWordService;

	/**
	 * 2.创建分类下的根目录
	 * 
	 * @param request
	 * @return
	 * @throws SensitiveWordServiceException 
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/sensitive/word/saveSensitive" }, method = { RequestMethod.GET })
	public MappingJacksonValue saveSensitive(@RequestParam(name = SensitiveWordController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = SensitiveWordController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = SensitiveWordController.parameterAppId, required = true) Long appId,
			@RequestParam(name = SensitiveWordController.parameterUserId, required = true) Long userId,
			@RequestParam(name = SensitiveWordController.parameterLevel, defaultValue = "2") Integer level,
			@RequestParam(name = SensitiveWordController.parameterWord, required = true) String word,
			@RequestParam(name = SensitiveWordController.parameterType, defaultValue = "0") Integer type) throws SensitiveWordServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", type.toString());
		param.put("createrId", userId.toString());
		param.put("appid", appId.toString());
		
		try {
			// 先检查敏感词是否存在
			sensitiveWordService.checkSensitiveWordExist(word);
			SensitiveWord sw = new SensitiveWord();
			sw.setWord(word);
			sw.setAppid(appId.toString());
			sw.setCreaterId(userId);
			sw.setLevel(level);
			sw.setType(type);
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			sw = sensitiveWordService.saveOrUpdate(sw);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(sw);
			return mappingJacksonValue;
		} catch (RpcException e) {
			Map<String, Serializable> resultMap = new HashMap<String, Serializable>();
			ResponseError error = processResponseError(e);
			if (error != null) {
				resultMap.put("error", error);
			}
			if (ObjectUtils.equals(debug, "all")) {
				// if (e.getErrorCode() > 0 ) {
				resultMap.put("__debug__", e.getMessage());
				// }
			}
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			e.printStackTrace(System.err);
			return mappingJacksonValue;
		}
	}

	/**
	 * 1. 根据entityid获取消息实体
	 * 
	 * @param request
	 * @return
	 * @throws SensitiveWordServiceException 
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/sensitive/word/getSensitiveWordById" }, method = { RequestMethod.GET })
	public MappingJacksonValue getSensitiveWordById(@RequestParam(name = SensitiveWordController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = SensitiveWordController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = SensitiveWordController.parameterAppId, required = true) Long appId,
			@RequestParam(name = SensitiveWordController.parameterUserId, required = true) Long userId,
			@RequestParam(name = SensitiveWordController.parameterWordId, required = true) Long id
			) throws SensitiveWordServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		// 0.校验输入参数（框架搞定，如果业务业务搞定）
		// 1.查询后台服务
		SensitiveWord sw = sensitiveWordService.getSensitiveWordById(id);
		// 2.转成框架数据
		mappingJacksonValue = new MappingJacksonValue(sw);
		// 3.创建页面显示数据项的过滤器
		SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
		mappingJacksonValue.setFilters(filterProvider);
		// 4.返回结果
		return mappingJacksonValue;
	}	
	/**
	 * 1. 获取用户消息实体列表
	 * 
	 * @param request
	 * @return
	 * @throws SensitiveWordServiceException 
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/sensitive/word/checkWord" }, method = { RequestMethod.GET })
	public MappingJacksonValue checkWord(@RequestParam(name = SensitiveWordController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = SensitiveWordController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = SensitiveWordController.parameterAppId, required = true) Long appId,
			@RequestParam(name = SensitiveWordController.parameterUserId, required = true) Long userId,
			@RequestParam(name = SensitiveWordController.parameterText, required = true) String text
			) throws SensitiveWordServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		// 0.校验输入参数（框架搞定，如果业务业务搞定）
		// 1.查询后台服务
		List<String> words = sensitiveWordService.sensitiveWord(text);
		// 2.转成框架数据
		mappingJacksonValue = new MappingJacksonValue(words);
		// 3.创建页面显示数据项的过滤器
		SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
		mappingJacksonValue.setFilters(filterProvider);
		// 4.返回结果
		return mappingJacksonValue;
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

		filterProvider.addFilter(Directory.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	@Override
	protected <T> void processBusinessException(ResponseError error,
			Exception ex) {
		// TODO Auto-generated method stub
		
	}
}
