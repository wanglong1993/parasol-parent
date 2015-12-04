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

package com.ginkgocap.parasol.sms.web.jetty.web.controller;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.rpc.cluster.Directory;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.sms.service.ShortMessageService;
import com.ginkgocap.parasol.sms.web.jetty.web.ResponseError;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class SmsController extends BaseControl {
	private static Logger logger = Logger.getLogger(SmsController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterAppId = "appKey"; // 应用的Key
	private static final String parameterUserId = "userId"; // 访问的用户参数
	private static final String parameterType = "type"; // 查询的应用分类
	private static final String parameterName = "name"; // 目录名称
	private static final String parameterEntityId = "entityId"; // 目录ID
	private static final String parameterContent = "content"; // 消息内容
	private static final String parameterSourceId = "sourceId"; // 消息源id
	private static final String parameterSourceType = "sourceType"; // 消息源子类型
	private static final String parameterSourceTitle = "sourceTitle"; // 消息源标题
	private static final String parameterReceiverIds = "receiverIds"; // 消息接收人　

	@Resource
	private ShortMessageService shortMessageService;


	/**
	 * 1. 根据entityid获取消息实体
	 * 
	 * @param request
	 * @return
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/sms/sendMessage" }, method = { RequestMethod.GET })
	public MappingJacksonValue getEntityById(@RequestParam(name = SmsController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = SmsController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = SmsController.parameterAppId, required = true) Long appId,
			@RequestParam(name = SmsController.parameterUserId, required = true) Long userId,
			@RequestParam(name = SmsController.parameterEntityId, required = true) long id
			) throws Exception {

		MappingJacksonValue mappingJacksonValue = null;
		
		try {
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			int result = shortMessageService.sendMessage("15011307812", "你的验证码是6969", 1374, 1);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(result);
			return mappingJacksonValue;
		} catch (Exception e) {
			
		}
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
