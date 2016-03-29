package com.ginkgocap.parasol.mapping.service.impl;

import java.text.MessageFormat;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ginkgocap.parasol.mapping.enumtype.MappingType;
import com.ginkgocap.parasol.mapping.exception.MappingServiceException;
import com.ginkgocap.parasol.mapping.model.Mapping;



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
	public final static int ERROR_TAG_NAME_IS_BLANK = 111; //不能是空串
	public final static int ERROR_TAG_NAME_TOO_LENGTH = 112; //Tag名字太长


	public final static MessageFormat propertyParametrMessage = new MessageFormat("Required {0} property {1} is not present"); // "Required String property name is not present"

	public static void assertInputMappingType(MappingType type) throws MappingServiceException {
		if (type == null) {
			throw new MappingServiceException("input MappingType  parameter must have a value");
		}
	}

	public static void assertOpenIdAndUidNull(Long openId, Long uId) throws MappingServiceException {
		if ((openId == null || openId <= 0l) &&  (uId == null || uId <= 0l)) {
			throw new MappingServiceException(ERROR_PARAMETER_NULL, "There must be a value of openId and uId for the two parameters.");
		}
	}

	public static void assertOpenIdAndMappingTypeNull(Long openId, MappingType type) throws MappingServiceException {
		if ((openId == null || openId <= 0l) ||  type == null) {
			throw new MappingServiceException(ERROR_PARAMETER_NULL, "openId and mappingType parameters must have a value");
		}
	}
	
	public static void assertUIdAndMappingTypeNull(Long uId, MappingType type) throws MappingServiceException {
		if ((uId == null || uId <= 0l) ||  type == null) {
			throw new MappingServiceException(ERROR_PARAMETER_NULL, "openId and mappingType parameters must have a value");
		}
	}
	

}
