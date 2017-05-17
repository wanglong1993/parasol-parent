package com.ginkgocap.parasol.directory.service.impl;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.exception.DirectoryTypeServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.model.DirectoryType;

public abstract class ServiceError {
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

	public final static long appId=1l;
	public final static int ERROR_NOT_MYSELF = 108; // 不是自己的目录
	public final static int ERROR_OBJECT_EXIST = 109; // 重复对象存在
	public final static int ERROR_SQL=200; //数据库错误
	public final static int ERROR_DIRECTORY_UPDATE = 110; //修改目录失败

	// Define Directory's related assertions.
	public static void assertPidForDirectory(Long pId) throws DirectoryServiceException {
		if (pId == null || pId < 0) {
			throw new DirectoryServiceException(ERROR_PARAMETER_NULL, "Required long parameter pId is not present");
		}
	}

	public static void assertAppIdForDirectory(Long appId) throws DirectoryServiceException {
		if (appId == null || appId < 0) {
			throw new DirectoryServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertUserIdForDirectory(Long userId) throws DirectoryServiceException {
		if (userId == null || userId < 0) {
			throw new DirectoryServiceException(ERROR_PARAMETER_NULL, "Required long parameter userId is not present");
		}
	}

	public static void assertDirectoryIdForDirectory(Long directoryId) throws DirectoryServiceException {
		if (directoryId == null || directoryId < 0) {
			throw new DirectoryServiceException(ERROR_PARAMETER_NULL, "Required long parameter directoryId is not present");
		}
	}

	public static void assertDirectoryTypeIdForDirectory(Long directoryTypeId) throws DirectoryServiceException {
		if (directoryTypeId == null || directoryTypeId < 0) {
			throw new DirectoryServiceException(ERROR_PARAMETER_NULL, "Required long parameter directoryTypeId is not present");
		}
	}

	public static void assertDirectoryForDirectory(Directory directory) throws DirectoryServiceException {
		if (directory == null) {
			throw new DirectoryServiceException("input directory entity parameter must have a value");
		}
	}

	// Define DirectoryType's related assertions.
	public static void assertForDirectoryType(DirectoryType directoryType) throws DirectoryTypeServiceException {
		if (directoryType == null) {
			throw new DirectoryTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertAppIdForDirectoryType(Long appId) throws DirectoryTypeServiceException {
		if (appId == null || appId < 0) {
			throw new DirectoryTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertIdForDirectoryType(Long id) throws DirectoryTypeServiceException {
		if (id == null || id < 0) {
			throw new DirectoryTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter id is not present");
		}
	}

	public static void assertNameForDirectoryType(String name) throws DirectoryTypeServiceException {
		if (StringUtils.isBlank(name)) {
			throw new DirectoryTypeServiceException(ERROR_PARAMETER_NULL, "Required string parameter name is not present");
		}
	}

	// Define DirectorySource's related assertions.
	public static void assertForDirectorySource(DirectorySource source) throws DirectorySourceServiceException {
		if (source == null) {
			throw new DirectorySourceServiceException(ERROR_PARAMETER_NULL, "Required DirectorySource parameter source is not present");
		}
	}

	public static void assertAppIdForDirectorySource(Long appId) throws DirectorySourceServiceException {
		if (appId == null || appId < 0) {
			throw new DirectorySourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertUserIdForDirectorySource(Long userId) throws DirectorySourceServiceException {
		if (userId == null || userId < 0) {
			throw new DirectorySourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter userId is not present");
		}
	}

	public static void assertDirectoryIdForDirectorySource(Long directoryId) throws DirectorySourceServiceException {
		if (directoryId == null || directoryId < 0) {
			throw new DirectorySourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter directoryId is not present");
		}
	}

	public static void assertSourceIdForDirectorySource(Long sourceId) throws DirectorySourceServiceException {
		if (sourceId == null || sourceId < 0) {
			throw new DirectorySourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter directoryId is not present");
		}
	}

	public static void assertSourceTypeIdForDirectorySource(Integer sourceType) throws DirectorySourceServiceException {
		if (sourceType == null || sourceType < 0) {
			throw new DirectorySourceServiceException(ERROR_PARAMETER_NULL, "Required int parameter sourceTypeId is not present");
		}
	}

	public static void assertIdForDirectorySource(Long id) throws DirectorySourceServiceException {
		if (id == null || id < 0) {
			throw new DirectorySourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter id is not present");
		}
	}

	public static void assertNameForDirectorySource(String name) throws DirectorySourceServiceException {
		if (StringUtils.isBlank(name)) {
			throw new DirectorySourceServiceException(ERROR_PARAMETER_NULL, "Required int parameter soureType is not present");
		}
	}


	
	
	
	public static void assertPropertiesHaveValue(DirectorySource directorySource, String[] fileds) throws DirectorySourceServiceException {
		if (directorySource != null) {
			Object value;
			for (String filed : fileds) {
				value = null;
				try {
					value = BeanUtilsBean.getInstance().getSimpleProperty(directorySource, filed);
					if ("".equals(ObjectUtils.toString(value, ""))) {
						throw new DirectorySourceServiceException(ServiceError.ERROR_PERTIES, filed + " property must have a value in "
								+ ClassUtils.getShortCanonicalName(DirectorySource.class) + " Object.");
					}
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}
			}
		}
	}
	
	/**
	 * 不能修改的字段
	 * 
	 * @param directorySource
	 * @param fileds
	 * @throws DirectorySourceServiceException
	 */
	public static void assertModifyFields(DirectorySource oldSource, DirectorySource targetSource, String[] fileds) throws DirectorySourceServiceException {
		Long id = targetSource.getId();
		if (id == null) {
			throw new DirectorySourceServiceException(ServiceError.ERROR_NOT_FOUND, "don't find id value of " + targetSource.toString());
		}
		if (oldSource != null) {
			Object oldValue, newValue;
			for (String filed : fileds) {
				oldValue = null;
				newValue = null;
				try {
					oldValue = BeanUtilsBean.getInstance().getSimpleProperty(oldSource, filed);
					newValue = BeanUtilsBean.getInstance().getSimpleProperty(targetSource, filed);
					if ("".equals(ObjectUtils.toString(newValue,""))) {
						throw new DirectorySourceServiceException(ServiceError.ERROR_PERTIES, filed +" property must have a value");
					}
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}
				if (!ObjectUtils.equals(oldValue, newValue)) {
					throw new DirectorySourceServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directory");
				}
			}
		} else {
			throw new DirectorySourceServiceException(ServiceError.ERROR_NOT_FOUND, "don't find old directorySource by : appId=" + targetSource.getAppId() + " and id=" + id);
		}
	}
	
}
