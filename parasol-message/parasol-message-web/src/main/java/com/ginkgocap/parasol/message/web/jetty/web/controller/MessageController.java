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

package com.ginkgocap.parasol.message.web.jetty.web.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.rpc.RpcException;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.message.exception.MessageEntityServiceException;
import com.ginkgocap.parasol.message.model.MessageEntity;
import com.ginkgocap.parasol.message.service.MessageEntityService;
import com.ginkgocap.parasol.message.web.jetty.web.ResponseError;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

/**
 * 
* @Title: MessageController.java
* @Package com.ginkgocap.parasol.message.web.jetty.web.controller
* @Description: TODO(消息提醒控制器)
* @author fuliwen@gintong.com  
* @date 2016年1月18日 上午11:03:00
* @version V1.0
 */
@RestController
public class MessageController extends BaseControl {
	private static Logger logger = Logger.getLogger(MessageController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterType = "type"; // 查询的应用分类
	private static final String parameterEntityId = "entityId"; // 目录ID
	private static final String parameterContent = "content"; // 消息内容
	private static final String parameterSourceId = "sourceId"; // 消息源id
	private static final String parameterSourceType = "sourceType"; // 消息源子类型
	private static final String parameterSourceTitle = "sourceTitle"; // 消息源标题
	private static final String parameterReceiverIds = "receiverIds"; // 消息接收人　

	@Resource
	private MessageEntityService messageEntityService;

	/**
	 * 2.创建分类下的根目录
	 * 
	 * @param request
	 * @return
	 * @throws MessageEntityServiceException 
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/message/message/createMessageEntity" }, method = { RequestMethod.GET })
	public MappingJacksonValue createMessageEntity(@RequestParam(name = MessageController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = MessageController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = MessageController.parameterContent, required = true) String content,
			@RequestParam(name = MessageController.parameterSourceId, required = true) Long sourceId,
			@RequestParam(name = MessageController.parameterSourceType, required = true) Integer sourceType,
			@RequestParam(name = MessageController.parameterSourceTitle, required = true) String sourceTitle,
			@RequestParam(name = MessageController.parameterReceiverIds, required = true) String receiverIds,
			@RequestParam(name = MessageController.parameterType, required = true) Long type) throws MessageEntityServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", type.toString());
		param.put("createrId", loginUserId.toString());
		param.put("content", content);
		param.put("sourceId", sourceId.toString());
		param.put("sourceType", sourceType.toString());
		param.put("sourceTitle", sourceTitle);
		param.put("receiverIds", receiverIds);
		param.put("appId", loginAppId.toString());
		
		try {
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			Map<String, Object> result = messageEntityService.insertMessageByParams(param);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(result);
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
	 * @throws MessageEntityServiceException 
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/message/message/getEntityById" }, method = { RequestMethod.GET })
	public MappingJacksonValue getEntityById(@RequestParam(name = MessageController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = MessageController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = MessageController.parameterEntityId, required = true) long id
			) throws MessageEntityServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		// 0.校验输入参数（框架搞定，如果业务业务搞定）
		// 1.查询后台服务
		MessageEntity entities = messageEntityService.getMessageEntityById(id);
		// 2.转成框架数据
		mappingJacksonValue = new MappingJacksonValue(entities);
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
	 * @throws MessageEntityServiceException 
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/message/message/getEntityByUserId" }, method = { RequestMethod.GET })
	public MappingJacksonValue getEntityByUserId(@RequestParam(name = MessageController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = MessageController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = MessageController.parameterType, required = true) Integer type
			) throws MessageEntityServiceException  {
		MappingJacksonValue mappingJacksonValue = null;
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		// 0.校验输入参数（框架搞定，如果业务业务搞定）
		// 1.查询后台服务
		List<MessageEntity> entities = messageEntityService.getMessagesByUserIdAndType(loginUserId, type, loginAppId);
		// 2.转成框架数据
		mappingJacksonValue = new MappingJacksonValue(entities);
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
			filter.add("createrId"); // '创建者',
			filter.add("content"); // '消息内容',
			filter.add("sourceId"); // '消息源ID',
			filter.add("sourceType"); // '消息源类型',
			filter.add("sourceTitle"); // '消息源标题',
			filter.add("appid"); // '应用ID',
		}

		filterProvider.addFilter(MessageEntity.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	@Override
	protected <T> void processBusinessException(ResponseError error,
			Exception ex) {
		// TODO Auto-generated method stub
		
	}
}
