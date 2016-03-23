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

package com.ginkgocap.parasol.comment.web.jetty.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
import com.ginkgocap.parasol.comment.exception.CommentServiceException;
import com.ginkgocap.parasol.comment.model.Comment;
import com.ginkgocap.parasol.comment.service.CommentService;
import com.ginkgocap.parasol.comment.web.jetty.web.vo.CommentUserVO;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.user.exception.UserBasicServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.service.UserBasicService;

/**
 * 
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class CommentController extends BaseControl {
	private static Logger logger = Logger.getLogger(CommentController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";

	private static final String parameterId = "id"; // id标识
	private static final String parameterUserId = "userId";// 创建这个记录的人用户（OwerID）
	private static final String parameterToUserId = "toUserId";// 评论回复给谁
	private static final String parameterAppId = "appId"; // 应用Id
	private static final String parameterSourceTypeId = "sourceTypeId"; // 表示AppId中的类型
	private static final String parameterSourceId = "sourceId"; // 知识ID,
																// 人脉ID,组织ID，等资源ID
	private static final String parameterContent = "content"; // 评论内容
	private static final String parameterCreateAt = "createAt"; // 记录创建的时间
	private static final String parameterCommentId = "commentId"; // 评论Id,
	private static final String parameterForward = "forward"; // 评论查询的方向,
	private static final String parameterPage = "page"; // 页码,

	private static final String parameterCount = "count"; // 查询评论的个数,
	private static final int MAX_PAGESIZE = 200; // 可以查询的数据最多200个

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserBasicService userBasicService;

	/**
	 * 1.创建评论
	 * 
	 * @param request
	 * @return
	 * @throws CommenterviceException
	 * @throws CodeServiceException
	 */
	// @formatter:off
	@RequestMapping(path = { "/comment/comment/createComment" }, method = { RequestMethod.POST })
	public MappingJacksonValue createCommentRoot(
			@RequestParam(name = CommentController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = CommentController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = CommentController.parameterSourceTypeId, required = true) Long sourceTypeId,
			@RequestParam(name = CommentController.parameterSourceId, required = true) Long sourceId,
			@RequestParam(name = CommentController.parameterToUserId, required = false) Long toUserId,
			@RequestParam(name = CommentController.parameterContent, required = true) String content) throws CommentServiceException {
		MappingJacksonValue mappingJacksonValue = null;
	// @formatter:on
		try {

			// 登陆人的信息
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();

			// 检查数据的参数
			if (StringUtils.isEmpty(content)) {
				throw new CommentServiceException(107, "Required string parameter assocDesc is not empty or blank");
			}

			Comment comment = new Comment();
			comment.setAppId(loginAppId);
			comment.setUserId(loginUserId);
			comment.setSourceTypeId(sourceTypeId);
			comment.setSourceId(sourceTypeId);
			comment.setContent(content);
			// TODO 检查用户的合法性
			if (toUserId != null && toUserId > 0) {
				comment.setToUserId(toUserId);
			}
			Long id = commentService.createComment(loginAppId, loginUserId, comment);
			Map<String, Long> reusltMap = new HashMap<String, Long>();
			reusltMap.put("id", id);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		} catch (CommentServiceException e) {
			throw e;
		}
	}

	/**
	 * 2.删除一个评论
	 * 
	 * @param request
	 * @return
	 * @throws CommenterviceException
	 * @throws CodeServiceException
	 */
	// @formatter:off
	@RequestMapping(path = { "/comment/comment/deleteComment" }, method = { RequestMethod.POST })
	public MappingJacksonValue deleteComment(
			@RequestParam(name = CommentController.parameterDebug, defaultValue = "") String debug, 
			@RequestParam(name = CommentController.parameterId, required = true) Long id ) throws CommentServiceException {
	// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {

			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();

			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			boolean bResult = commentService.removeComment(loginAppId, loginUserId, id);
			Map<String, Boolean> reusltMap = new HashMap<String, Boolean>();
			reusltMap.put("success", bResult);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		} catch (CommentServiceException e) {
			throw e;
		}
	}

	/**
	 * 4. 给出一个评论的详情
	 * 
	 * @param request
	 * @return
	 * @throws CommenterviceException
	 * @throws CodeServiceException
	 */
	// @formatter:off
	@RequestMapping(path = { "/comment/comment/getCommentDetail" }, method = { RequestMethod.POST })
	public MappingJacksonValue getCommentDetail(
			@RequestParam(name = CommentController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = CommentController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = CommentController.parameterId, required = true) Long id ) throws CommentServiceException {
	// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			Comment comment = commentService.getComment(loginAppId, loginUserId, id);
			if (logger.isDebugEnabled() && comment == null) {
				logger.debug("comment is null by id " + id);
			}
			UserBasic ub = comment == null ? null : userBasicService.getUserBasic(comment.getUserId());
			if (logger.isDebugEnabled() && ub == null && comment != null) {
				logger.debug("UserBasic is null by id " + comment.getUserId());
			}
			
			UserBasic tUb = comment == null || comment.getToUserId() > 0 ? null : userBasicService.getUserBasic(comment.getToUserId());

			Map<String, Object> reusltMap = new HashMap<String, Object>();

			CommentUserVO vo = comment != null ? new CommentUserVO(comment, ub, tUb) : null;
			reusltMap.put("data", vo);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			mappingJacksonValue.setFilters(builderSimpleFilterProvider(fileds));
			return mappingJacksonValue;
		} catch (CommentServiceException e) {
			throw e;
		} catch (UserBasicServiceException e) {
			throw new CommentServiceException(e);
		}
	}

	// @formatter:off
	/**
	 * 
	 * @param fileds
	 * @param debug
	 * @param sourceTypeId
	 * @param sourceId 对象的ID
	 * @param commentId 评论的ID
	 * @param forward   查询的方向
	 * @param count     查询多少评论
	 * @return MappingJacksonValue
	 * 
	 * 通过对象ID查询对象的评论，查询相对于commandId位置的一定数量（count），方向（forward）的评论。
	 * 
	 * @throws CommentServiceException
	 */
	@RequestMapping(path = { "/comment/comment/getFllowSourceComments" }, method = { RequestMethod.GET })
	public MappingJacksonValue getFllowSourceComments(
			@RequestParam(name = CommentController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = CommentController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = CommentController.parameterSourceTypeId, required = true) Long sourceTypeId,
			@RequestParam(name = CommentController.parameterSourceId, required = true) long sourceId,
			@RequestParam(name = CommentController.parameterCommentId, required = false) long commentId,
			@RequestParam(name = CommentController.parameterForward, required = false, defaultValue="false") boolean forward,
			@RequestParam(name = CommentController.parameterCount, required = true) int count

		) throws CommentServiceException {
	// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;

		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		int iCount = Math.min(count, MAX_PAGESIZE);
		List<Comment> comments = null;
		int recordCount = commentService.countComment(loginAppId, sourceTypeId, sourceId);
		List<CommentUserVO> commentUserVOs = new ArrayList<CommentUserVO>();
		// 1.查询评论数据
		if (recordCount > 0 && count > 0) {
			if (commentId <= 0) {
				comments = commentService.getComments(loginAppId, sourceTypeId, sourceId, 0, iCount);
			} else {
				comments = commentService.getCommentsByMinId(loginAppId, sourceTypeId, sourceId, commentId, count, forward);
			}
		}

		// 2.查询参加评论的用户基本休息
		List<Long> userIds = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(comments)) {
			for (Comment comment : comments) {
				if (comment != null) {
					userIds.add(comment.getUserId());
					userIds.add(comment.getToUserId());
				}
			}
			//批量获取用户基本资料
			Map<Long, UserBasic> userBasicMap = getUserBasics(userIds);
			// 3.合成Comment和userBasic对象集合
			for (Comment comment : comments) {
				UserBasic ub = userBasicMap.get(comment.getUserId());
				UserBasic tUb = userBasicMap.get(comment.getUserId());
				CommentUserVO commentUserVO = new CommentUserVO(comment, ub, tUb);
				commentUserVOs.add(commentUserVO);
			}
		}

		Map<String, Object> reusltMap = new HashMap<String, Object>();
		reusltMap.put("count", recordCount);
		reusltMap.put("data", commentUserVOs);
		mappingJacksonValue = new MappingJacksonValue(reusltMap);
		mappingJacksonValue.setFilters(builderSimpleFilterProvider(fileds));
		return mappingJacksonValue;
	}

	// @formatter:off
	/**
	 * 
	 * @param fileds
	 * @param debug
	 * @param sourceTypeId
	 * @param sourceId
	 *            对象的ID
	 * @param commentId
	 *            评论的ID
	 * @param forward
	 *            查询的方向
	 * @param count
	 *            查询多少评论
	 * @return MappingJacksonValue
	 * 
	 *         通过对象ID查询对象的评论，查询相对于commandId位置的一定数量（count），方向（forward）的评论。
	 * 
	 * @throws CommentServiceException
	 */
	@RequestMapping(path = { "/comment/comment/getPageSourceComments" }, method = { RequestMethod.GET })
	public MappingJacksonValue getPageSourceComments(@RequestParam(name = CommentController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = CommentController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = CommentController.parameterSourceTypeId, required = true) Long sourceTypeId,
			@RequestParam(name = CommentController.parameterSourceId, required = true) long sourceId,
			@RequestParam(name = CommentController.parameterPage, required = false, defaultValue = "false") int page,
			@RequestParam(name = CommentController.parameterCount, required = true) int count) throws CommentServiceException {
		// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;

		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		int iCount = Math.min(count, MAX_PAGESIZE);
		List<Comment> comments = null;
		List<CommentUserVO> commentUserVOs = new ArrayList<CommentUserVO>();
		//1.查询评论
		int recordCount = commentService.countComment(loginAppId, sourceTypeId, sourceId);
		if (recordCount > 0 && iCount > 0) {
			comments = commentService.getComments(loginAppId, sourceTypeId, sourceId, page * iCount, iCount);
		}

		// 2.查询参加评论的用户基本休息
		List<Long> userIds = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(comments)) {
			for (Comment comment : comments) {
				if (comment != null) {
					userIds.add(comment.getUserId());
					userIds.add(comment.getToUserId());
				}
			}
			//批量获取用户基本资料
			Map<Long, UserBasic> userBasicMap = getUserBasics(userIds);
			// 3.合成Comment和userBasic对象集合
			for (Comment comment : comments) {
				UserBasic ub = userBasicMap.get(comment.getUserId());
				UserBasic tUb = userBasicMap.get(comment.getUserId());
				CommentUserVO commentUserVO = new CommentUserVO(comment, ub, tUb);
				commentUserVOs.add(commentUserVO);
			}
		}
		Map<String, Object> reusltMap = new HashMap<String, Object>();

		reusltMap.put("count", recordCount);
		reusltMap.put("data", commentUserVOs);
		mappingJacksonValue = new MappingJacksonValue(reusltMap);
		mappingJacksonValue.setFilters(builderSimpleFilterProvider(fileds));
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
			filter.add("userId"); // 发评论的人
			filter.add("toUserId"); // 回复给谁
			filter.add("content"); // 评论内容,
			filter.add("createAt"); // 发评论的时间,
			filter.add("user"); // 发评论的时间,
			filter.add("toUser");// 回复给谁
		}

		filterProvider.addFilter(CommentUserVO.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	/**
	 * 批量从dubbo上查询用户的基本消息
	 * 
	 * @param userIds
	 * @return
	 */
	private Map<Long, UserBasic> getUserBasics(List<Long> userIds) {
		Map<Long, UserBasic> resultMap = new HashMap<Long, UserBasic>();
		try {
			Map<Long, Boolean> idMap = new HashMap<Long, Boolean>(userIds == null ? 1 : userIds.size());
			List<Long> takeUserIds = new ArrayList<Long>();

			// 过滤重复的ID
			if (CollectionUtils.isNotEmpty(userIds)) {
				for (Long userId : userIds) {
					if (userId != null) {
						idMap.put(userId, Boolean.TRUE);
					}
				}

				if (MapUtils.isNotEmpty(idMap)) {
					for (Long userId : idMap.keySet()) {
						takeUserIds.add(userId);
					}
				}
			}

			// 从user dubbo 服务上查询用户
			if (CollectionUtils.isNotEmpty(takeUserIds)) {
				List<UserBasic> list = userBasicService.getUserBasecList(userIds);
				if (CollectionUtils.isNotEmpty(list)) {
					for (UserBasic userBasic : list) {
						if (userBasic != null) {
							resultMap.put(userBasic.getUserId(), userBasic);
						}
					}
				}
			}
		} catch (UserBasicServiceException e) {
			e.printStackTrace(System.err);
		}
		return resultMap;
	}

}
