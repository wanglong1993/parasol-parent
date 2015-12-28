package com.ginkgocap.parasol.tags.service.impl;

import java.io.Serializable;
import java.text.MessageFormat;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ginkgocap.parasol.tags.exception.ServiceException;
import com.ginkgocap.parasol.tags.exception.TagServiceException;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.Tag;

public abstract class ServiceError {
	public final static Logger logger = Logger.getLogger(ServiceError.class);
	public final static int ERROR_INPUT_NULL = 102;// 输入了一个空对象
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

	public final static int ERROR_NOT_MYSELF = 108; // 不是自己的目录
	public final static int ERROR_OBJECT_EXIST = 109; // 重复对象存在
	public final static int ERROR_SQL = 200; // 数据库错误
	public final static int ERROR_TOO_MANY = 110; // 不能创建太多的对象_

	public final static MessageFormat propertyParametrMessage = new MessageFormat("Required {0} property {1} is not present"); // "Required String property name is not present"

	public static void assertInputTagIsNull(Tag tag) throws TagServiceException {
		if (tag == null) {
			throw new TagServiceException("input Tag entity parameter must have a value");
		}
	}

	public static void assertUserIdIsNull(Long userId) throws TagServiceException {
		if (userId == null || userId == 0l) {
			throw new TagServiceException(ERROR_PARAMETER_NULL, "Required Long parameter userId is not present");
		}
	}

	public static void assertTagIdIsNull(Long tagId) throws TagServiceException {
		if (tagId == null || tagId == 0l) {
			throw new TagServiceException(ERROR_PARAMETER_NULL, "Required Long parameter tagId is not present");
		}
	}

	@SuppressWarnings("rawtypes")
	public static void assertPopertiesIsNullForTag(String properName) throws TagServiceException {
		try {
			Class clazz = PropertyUtils.getPropertyType(Tag.class, properName);
			if (clazz == null) {
				logger.warn("properName isnot exist!");
				return;
			}
			String propertyType = clazz.getSimpleName();
			Object value = PropertyUtils.getSimpleProperty(Tag.class, properName);
			String message = propertyParametrMessage.format(new Object[] { propertyType, properName });
			if (value == null) {
				throw new TagServiceException(ERROR_PARAMETER_NULL, message);
			}
			if (value instanceof String && StringUtils.isEmpty((CharSequence) value)) {
				throw new TagServiceException(ERROR_PARAMETER_NULL, message);
			}

			if (value instanceof Number) {
				Number nValue = (Number) value;
				if (nValue.intValue() == 0) {
					throw new TagServiceException(ERROR_PARAMETER_NULL, message);
				}
			}
		} catch (Exception e) {
			if (e instanceof TagServiceException) {
				throw (TagServiceException) e;
			}
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static void assertPopertiesIsNullForTagSource(String properName) throws TagSourceServiceException {
		try {
			Class clazz = PropertyUtils.getPropertyType(Tag.class, properName);
			if (clazz == null) {
				logger.warn("properName isnot exist!");
				return;
			}
			String propertyType = clazz.getSimpleName();
			Object value = PropertyUtils.getSimpleProperty(Tag.class, properName);
			String message = propertyParametrMessage.format(new Object[] { propertyType, properName });
			if (value == null) {
				throw new TagSourceServiceException(ERROR_PARAMETER_NULL, message);
			}
			if (value instanceof String && StringUtils.isEmpty((CharSequence) value)) {
				throw new TagSourceServiceException(ERROR_PARAMETER_NULL, message);
			}

			if (value instanceof Number) {
				Number nValue = (Number) value;
				if (nValue.intValue() == 0) {
					throw new TagSourceServiceException(ERROR_PARAMETER_NULL, message);
				}
			}
		} catch (Exception e) {
			if (e instanceof TagServiceException) {
				throw (TagSourceServiceException) e;
			}
			e.printStackTrace();
		}
	}

	public static void assertAppidIsNullForTagSource(Long appId) throws TagSourceServiceException {
		if (appId == null || appId == 0l) {
			throw new TagSourceServiceException(ERROR_PARAMETER_NULL, "Required Long parameter userId is not present");
		}
	}

	public static void assertTagSourceIdIsNullForTagSource(Long sourceId) throws TagSourceServiceException {
		if (sourceId == null || sourceId == 0l) {
			throw new TagSourceServiceException(ERROR_PARAMETER_NULL, "Required Long parameter sourceId is not present");
		}
	}

	public static void assertTagIdIsNullForTagSource(Long tagId) throws TagSourceServiceException {
		if (tagId == null || tagId == 0l) {
			throw new TagSourceServiceException(ERROR_PARAMETER_NULL, "Required Long parameter tagId is not present");
		}
	}

	public static void assertUserIdIsNullForTagSource(Long userId) throws TagSourceServiceException {
		if (userId == null || userId == 0l) {
			throw new TagSourceServiceException(ERROR_PARAMETER_NULL, "Required Long parameter userId is not present");
		}
	}

	public static void assertTagSourceTypeIsNullForTagSource(Integer typeId) throws TagSourceServiceException {
		if (typeId == null) {
			throw new TagSourceServiceException(ERROR_PARAMETER_NULL, "Required Long parameter typeId is not present");
		}
	}

	public static void main(String[] args) throws TagServiceException {
		Tag tag = new Tag();
	}
}
