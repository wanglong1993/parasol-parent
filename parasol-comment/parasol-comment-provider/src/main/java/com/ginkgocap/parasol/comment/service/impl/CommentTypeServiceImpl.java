package com.ginkgocap.parasol.comment.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.comment.exception.CommentServiceException;
import com.ginkgocap.parasol.comment.exception.CommentTypeServiceException;
import com.ginkgocap.parasol.comment.model.CommentType;
import com.ginkgocap.parasol.comment.service.CommentTypeService;

/**
 * 
 * @author allenshen
 * @date 2015年11月25日
 * @time 下午2:54:09
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("commentTypeService")
public class CommentTypeServiceImpl extends BaseService<CommentType> implements CommentTypeService {
	private static Logger logger = Logger.getLogger(CommentTypeServiceImpl.class);
	private static int len_name = 100; // 名字长度

	private static final String LIST_DIRECTORYTYPE_ID_APPID = "List_CommentType_Id_AppId";
	private static final String MAP_DIRECTORYTYPE_ID_APPID_NAME = "Map_CommentType_Id_AppId_Name";

	@Override
	public Long createCommentType(Long appId, CommentType commentType) throws CommentTypeServiceException {
		ServiceError.assertForCommentType(commentType);
		ServiceError.assertAppIdForCommentType(appId);
		// 1 checkAppid
		if (ObjectUtils.equals(commentType.getAppId(), null) || commentType.getAppId() <= 0) { // appId
			throw new CommentTypeServiceException(ServiceError.ERROR_PERTIES, "appId property must have a value");
		}
		// check name is blank
		if (StringUtils.isBlank(commentType.getName())) {
			throw new CommentTypeServiceException(ServiceError.ERROR_PERTIES, "name property must have a value and non blank");
		} else {
			if (commentType.getName().length() > len_name) {
				throw new CommentTypeServiceException(ServiceError.ERROR_NAME_LIMIT, commentType.getName() + "  length can not be more than " + len_name);
			}
		}
		// 2 check name exist?
		List<CommentType> commentTypes = this.getCommentTypessByAppId(appId);
		assertDuplicateName(commentTypes, commentType);

		try {
			commentType.setAppId(appId);
			commentType.setUpdateAt(System.currentTimeMillis());
			return (Long) this.saveEntity(commentType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentTypeServiceException(e);
		}
	}

	@Override
	public boolean removeCommentType(Long appId, Long commentTypeId) throws CommentTypeServiceException {
		ServiceError.assertAppIdForCommentType(appId);
		ServiceError.assertIdForCommentType(commentTypeId);

		// 1 checkAppid
		CommentType commentType = null;
		try {
			commentType = this.getEntity(commentTypeId);
			if (commentType == null) {
				throw new CommentTypeServiceException(ServiceError.ERROR_NOT_FOUND, "don't find CommentType by id " + commentTypeId);
			}

			if (!ObjectUtils.equals(commentType.getAppId(), appId)) {
				throw new CommentTypeServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own commentType");// 移动的不是指定APP的分类
			}

			return this.deleteEntity(commentTypeId);

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentTypeServiceException(e);
		}
	}

	@Override
	public boolean updateCommentType(Long appId, CommentType commentType) throws CommentTypeServiceException {
		ServiceError.assertAppIdForCommentType(appId);
		try {
			if (commentType != null) {
				if (ObjectUtils.equals(commentType.getAppId(), appId)) {
					if (StringUtils.isNotBlank(commentType.getName())) {
						if (commentType.getName().length() > len_name) {
							throw new CommentTypeServiceException(ServiceError.ERROR_NAME_LIMIT, commentType.getName() + "  length can not be more than " + len_name);
						}
						List<CommentType> commentTypes = this.getCommentTypessByAppId(appId);
						assertDuplicateName(commentTypes, commentType); // 检查重名
						commentType.setUpdateAt(System.currentTimeMillis());
						return this.updateEntity(commentType);
					} else {
						throw new CommentTypeServiceException(ServiceError.ERROR_PERTIES, "name property must have a non blank string "); // name
					}

				} else {
					throw new CommentTypeServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own commentType");// 更新的不是指定的APP
				}
			} else {
				logger.error("paramenter commentType is null");
				return false;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentTypeServiceException(e);
		}
	}

	@Override
	public CommentType getCommentType(Long appId, Long commentTypeId) throws CommentTypeServiceException {
		ServiceError.assertAppIdForCommentType(appId);
		ServiceError.assertIdForCommentType(commentTypeId);
		try {
			CommentType commentType = this.getEntity(commentTypeId);
			if (commentType != null && ObjectUtils.equals(appId, commentType.getAppId())) {
				return commentType;
			} else {
				logger.error("CommentType is null or appid of CommentType not equal " + appId);
				return null;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentTypeServiceException(e);
		}
	}

	@Override
	public List<CommentType> getCommentTypessByAppId(Long appId) throws CommentTypeServiceException {
		ServiceError.assertAppIdForCommentType(appId);
		try {
			return this.getSubEntitys(LIST_DIRECTORYTYPE_ID_APPID, 0, 500, appId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentTypeServiceException(e);
		}
	}

	@Override
	public int countCommentTypessByAppId(Long appId) throws CommentTypeServiceException {
		ServiceError.assertAppIdForCommentType(appId);
		try {
			return this.countEntitys(LIST_DIRECTORYTYPE_ID_APPID, appId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentTypeServiceException(e);
		}
	}

	/**
	 * 检查是否重名
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @param name
	 * @throws CommentServiceException
	 */

	private void assertDuplicateName(List<CommentType> commentTypes, CommentType commentType) throws CommentTypeServiceException {
		if (!CollectionUtils.isEmpty(commentTypes)) {
			for (CommentType dirType : commentTypes) {
				if (dirType != null && !ObjectUtils.equals(dirType.getId(), commentType.getId()) && ObjectUtils.equals(dirType.getName(), commentType.getName())) {
					throw new CommentTypeServiceException(ServiceError.ERROR_DUPLICATE, "the " + commentType.getName() + " already exists");
				}
			}
		}
	}

	@Override
	public CommentType getCommentTypeByName(Long appId, String name) throws CommentTypeServiceException {
		ServiceError.assertAppIdForCommentType(appId);
		ServiceError.assertNameForCommentType(name);
		try {
			Long id = (Long) this.getMapId(MAP_DIRECTORYTYPE_ID_APPID_NAME, appId, name);
			if (id != null) {
				return this.getCommentType(appId, id);
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return null;

	}
}
