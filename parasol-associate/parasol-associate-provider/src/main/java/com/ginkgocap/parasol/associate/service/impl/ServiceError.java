package com.ginkgocap.parasol.associate.service.impl;

import java.text.MessageFormat;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.exception.AssociateTypeServiceException;
import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.model.AssociateType;

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

	// Define Associate's related assertions.
	public static void assertAppIdForAssociate(Long appId) throws AssociateServiceException {
		if (appId == null || appId < 0) {
			throw new AssociateServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertUserIdForAssociate(Long userId) throws AssociateServiceException {
		if (userId == null || userId < 0) {
			throw new AssociateServiceException(ERROR_PARAMETER_NULL, "Required long parameter userId is not present");
		}
	}

	public static void assertAssociateIdForAssociate(Long associateId) throws AssociateServiceException {
		if (associateId == null || associateId < 0) {
			throw new AssociateServiceException(ERROR_PARAMETER_NULL, "Required long parameter associateId is not present");
		}
	}

	public static void assertAssociateTypeIdForAssociate(Long associateTypeId) throws AssociateServiceException {
		if (associateTypeId == null || associateTypeId < 0) {
			throw new AssociateServiceException(ERROR_PARAMETER_NULL, "Required long parameter associateTypeId is not present");
		}
	}

	public static void assertAssociateForAssociate(Associate associate) throws AssociateServiceException {
		if (associate == null) {
			throw new AssociateServiceException("input associate entity parameter must have a value");
		}
	}

	@SuppressWarnings("rawtypes")
	public static void assertPopertiesIsNullForAssociate(String properName) throws AssociateServiceException {
		try {
			Class clazz = PropertyUtils.getPropertyType(Associate.class, properName);
			if (clazz == null) {
				logger.warn("properName is not exist!");
				return;
			}
			String propertyType = clazz.getSimpleName();
			Object value = PropertyUtils.getSimpleProperty(Associate.class, properName);
			String message = propertyParametrMessage.format(new Object[] { propertyType, properName });
			if (value == null) {
				throw new AssociateServiceException(ERROR_PARAMETER_NULL, message);
			}
			if (value instanceof String && StringUtils.isEmpty((CharSequence) value)) {
				throw new AssociateServiceException(ERROR_PARAMETER_NULL, message);
			}

			if (value instanceof Number) {
				Number nValue = (Number) value;
				if (nValue.intValue() == 0) {
					throw new AssociateServiceException(ERROR_PARAMETER_NULL, message);
				}
			}
		} catch (Exception e) {
			if (e instanceof AssociateServiceException) {
				throw (AssociateServiceException) e;
			}
			e.printStackTrace();
		}
	}

	// Define AssociateType's related assertions.
	public static void assertForAssociateType(AssociateType associateType) throws AssociateTypeServiceException {
		if (associateType == null) {
			throw new AssociateTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertAppIdForAssociateType(Long appId) throws AssociateTypeServiceException {
		if (appId == null || appId < 0) {
			throw new AssociateTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertIdForAssociateType(Long id) throws AssociateTypeServiceException {
		if (id == null || id < 0) {
			throw new AssociateTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter id is not present");
		}
	}

	public static void assertNameForAssociateType(String name) throws AssociateTypeServiceException {
		if (StringUtils.isBlank(name)) {
			throw new AssociateTypeServiceException(ERROR_PARAMETER_NULL, "Required string parameter name is not present");
		}
	}

}