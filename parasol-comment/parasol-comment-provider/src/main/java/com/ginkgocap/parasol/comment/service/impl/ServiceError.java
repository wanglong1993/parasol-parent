package com.ginkgocap.parasol.comment.service.impl;

import java.text.MessageFormat;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ginkgocap.parasol.comment.exception.CommentServiceException;
import com.ginkgocap.parasol.comment.exception.CommentTypeServiceException;
import com.ginkgocap.parasol.comment.model.Comment;
import com.ginkgocap.parasol.comment.model.CommentType;

public abstract class ServiceError {
	private final static Logger logger = Logger.getLogger(ServiceError.class);
	public final static int ERROR_PERTIES = 104; // 对象的属性参数错误
	public final static int ERROR_NOT_FOUND = 105; // 对象不存在

	public final static int ERROR_DUPLICATE = 100; // 重名
	public final static int ERROR_NAME_LIMIT = 103; // 名字太长
	public final static int ERROR_USER_EXIST = 106; // 指定的用户不存在或者没有指定用户
	public final static int ERROR_PARAMETER_NULL = 107; // return "Required " +
	// this.parameterType +
	// " parameter '" +
	// this.parameterName +
	// "' is not present";

	public final static int ERROR_NOT_MYSELF = 108; // 不是自己的收藏夹
	public final static int ERROR_OBJECT_EXIST = 109; // 重复对象存在
	public final static int ERROR_SQL = 200; // 数据库错误
	public final static MessageFormat propertyParametrMessage = new MessageFormat("Required {0} property {1} is not present"); // "Required String property name is not present"

	// Define Comment's related assertions.
	public static void assertAppIdForComment(Long appId) throws CommentServiceException {
		if (appId == null || appId < 0) {
			throw new CommentServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertUserIdForComment(Long userId) throws CommentServiceException {
		if (userId == null || userId < 0) {
			throw new CommentServiceException(ERROR_PARAMETER_NULL, "Required long parameter userId is not present");
		}
	}

	public static void assertCommentIdForComment(Long commentId) throws CommentServiceException {
		if (commentId == null || commentId < 0) {
			throw new CommentServiceException(ERROR_PARAMETER_NULL, "Required long parameter commentId is not present");
		}
	}

	public static void assertCommentTypeIdForComment(Long commentTypeId) throws CommentServiceException {
		if (commentTypeId == null || commentTypeId < 0) {
			throw new CommentServiceException(ERROR_PARAMETER_NULL, "Required long parameter commentTypeId is not present");
		}
	}

	public static void assertCommentForComment(Comment comment) throws CommentServiceException {
		if (comment == null) {
			throw new CommentServiceException("input comment entity parameter must have a value");
		}
	}

	@SuppressWarnings("rawtypes")
	public static void assertPopertiesIsNullForComment(String properName) throws CommentServiceException {
		try {
			Class clazz = PropertyUtils.getPropertyType(Comment.class, properName);
			if (clazz == null) {
				logger.warn("properName is not exist!");
				return;
			}
			String propertyType = clazz.getSimpleName();
			Object value = PropertyUtils.getSimpleProperty(Comment.class, properName);
			String message = propertyParametrMessage.format(new Object[] { propertyType, properName });
			if (value == null) {
				throw new CommentServiceException(ERROR_PARAMETER_NULL, message);
			}
			if (value instanceof String && StringUtils.isEmpty((CharSequence) value)) {
				throw new CommentServiceException(ERROR_PARAMETER_NULL, message);
			}

			if (value instanceof Number) {
				Number nValue = (Number) value;
				if (nValue.intValue() == 0) {
					throw new CommentServiceException(ERROR_PARAMETER_NULL, message);
				}
			}
		} catch (Exception e) {
			if (e instanceof CommentServiceException) {
				throw (CommentServiceException) e;
			}
			e.printStackTrace();
		}
	}

	// Define CommentType's related assertions.
	public static void assertForCommentType(CommentType commentType) throws CommentTypeServiceException {
		if (commentType == null) {
			throw new CommentTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertAppIdForCommentType(Long appId) throws CommentTypeServiceException {
		if (appId == null || appId < 0) {
			throw new CommentTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertIdForCommentType(Long id) throws CommentTypeServiceException {
		if (id == null || id < 0) {
			throw new CommentTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter id is not present");
		}
	}

	public static void assertNameForCommentType(String name) throws CommentTypeServiceException {
		if (StringUtils.isBlank(name)) {
			throw new CommentTypeServiceException(ERROR_PARAMETER_NULL, "Required string parameter name is not present");
		}
	}

}
