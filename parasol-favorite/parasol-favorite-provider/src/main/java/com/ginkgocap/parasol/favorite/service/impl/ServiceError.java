package com.ginkgocap.parasol.favorite.service.impl;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteSourceServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteTypeServiceException;
import com.ginkgocap.parasol.favorite.model.Favorite;
import com.ginkgocap.parasol.favorite.model.FavoriteSource;
import com.ginkgocap.parasol.favorite.model.FavoriteType;

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

	public final static int ERROR_NOT_MYSELF = 108; // 不是自己的收藏夹
	public final static int ERROR_OBJECT_EXIST = 109; // 重复对象存在
	public final static int ERROR_SQL=200; //数据库错误

	// Define Favorite's related assertions.
	public static void assertAppIdForFavorite(Long appId) throws FavoriteServiceException {
		if (appId == null || appId < 0) {
			throw new FavoriteServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertUserIdForFavorite(Long userId) throws FavoriteServiceException {
		if (userId == null || userId < 0) {
			throw new FavoriteServiceException(ERROR_PARAMETER_NULL, "Required long parameter userId is not present");
		}
	}

	public static void assertFavoriteIdForFavorite(Long favoriteId) throws FavoriteServiceException {
		if (favoriteId == null || favoriteId < 0) {
			throw new FavoriteServiceException(ERROR_PARAMETER_NULL, "Required long parameter favoriteId is not present");
		}
	}

	public static void assertFavoriteTypeIdForFavorite(Long favoriteTypeId) throws FavoriteServiceException {
		if (favoriteTypeId == null || favoriteTypeId < 0) {
			throw new FavoriteServiceException(ERROR_PARAMETER_NULL, "Required long parameter favoriteTypeId is not present");
		}
	}

	public static void assertFavoriteForFavorite(Favorite favorite) throws FavoriteServiceException {
		if (favorite == null) {
			throw new FavoriteServiceException("input favorite entity parameter must have a value");
		}
	}

	// Define FavoriteType's related assertions.
	public static void assertForFavoriteType(FavoriteType favoriteType) throws FavoriteTypeServiceException {
		if (favoriteType == null) {
			throw new FavoriteTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertAppIdForFavoriteType(Long appId) throws FavoriteTypeServiceException {
		if (appId == null || appId < 0) {
			throw new FavoriteTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertIdForFavoriteType(Long id) throws FavoriteTypeServiceException {
		if (id == null || id < 0) {
			throw new FavoriteTypeServiceException(ERROR_PARAMETER_NULL, "Required long parameter id is not present");
		}
	}

	public static void assertNameForFavoriteType(String name) throws FavoriteTypeServiceException {
		if (StringUtils.isBlank(name)) {
			throw new FavoriteTypeServiceException(ERROR_PARAMETER_NULL, "Required string parameter name is not present");
		}
	}

	// Define FavoriteSource's related assertions.
	public static void assertForFavoriteSource(FavoriteSource source) throws FavoriteSourceServiceException {
		if (source == null) {
			throw new FavoriteSourceServiceException(ERROR_PARAMETER_NULL, "Required FavoriteSource parameter source is not present");
		}
	}

	public static void assertAppIdForFavoriteSource(Long appId) throws FavoriteSourceServiceException {
		if (appId == null || appId < 0) {
			throw new FavoriteSourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter appId is not present");
		}
	}

	public static void assertUserIdForFavoriteSource(Long userId) throws FavoriteSourceServiceException {
		if (userId == null || userId < 0) {
			throw new FavoriteSourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter userId is not present");
		}
	}

	public static void assertFavoriteIdForFavoriteSource(Long favoriteId) throws FavoriteSourceServiceException {
		if (favoriteId == null || favoriteId < 0) {
			throw new FavoriteSourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter favoriteId is not present");
		}
	}

	public static void assertSourceIdForFavoriteSource(Long sourceId) throws FavoriteSourceServiceException {
		if (sourceId == null || sourceId < 0) {
			throw new FavoriteSourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter favoriteId is not present");
		}
	}

	public static void assertSourceTypeIdForFavoriteSource(Integer sourceType) throws FavoriteSourceServiceException {
		if (sourceType == null || sourceType < 0) {
			throw new FavoriteSourceServiceException(ERROR_PARAMETER_NULL, "Required int parameter sourceTypeId is not present");
		}
	}

	public static void assertIdForFavoriteSource(Long id) throws FavoriteSourceServiceException {
		if (id == null || id < 0) {
			throw new FavoriteSourceServiceException(ERROR_PARAMETER_NULL, "Required long parameter id is not present");
		}
	}

	public static void assertNameForFavoriteSource(String name) throws FavoriteSourceServiceException {
		if (StringUtils.isBlank(name)) {
			throw new FavoriteSourceServiceException(ERROR_PARAMETER_NULL, "Required int parameter soureType is not present");
		}
	}


	
	
	
	public static void assertPropertiesHaveValue(FavoriteSource favoriteSource, String[] fileds) throws FavoriteSourceServiceException {
		if (favoriteSource != null) {
			Object value;
			for (String filed : fileds) {
				value = null;
				try {
					value = BeanUtilsBean.getInstance().getSimpleProperty(favoriteSource, filed);
					if ("".equals(ObjectUtils.toString(value, ""))) {
						throw new FavoriteSourceServiceException(ServiceError.ERROR_PERTIES, filed + " property must have a value in "
								+ ClassUtils.getShortCanonicalName(FavoriteSource.class) + " Object.");
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
	 * @param favoriteSource
	 * @param fileds
	 * @throws FavoriteSourceServiceException
	 */
	public static void assertModifyFields(FavoriteSource oldSource, FavoriteSource targetSource, String[] fileds) throws FavoriteSourceServiceException {
		Long id = targetSource.getId();
		if (id == null) {
			throw new FavoriteSourceServiceException(ServiceError.ERROR_NOT_FOUND, "don't find id value of " + targetSource.toString());
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
						throw new FavoriteSourceServiceException(ServiceError.ERROR_PERTIES, filed +" property must have a value");
					}
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}
				if (!ObjectUtils.equals(oldValue, newValue)) {
					throw new FavoriteSourceServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favorite");
				}
			}
		} else {
			throw new FavoriteSourceServiceException(ServiceError.ERROR_NOT_FOUND, "don't find old favoriteSource by : appId=" + targetSource.getAppId() + " and id=" + id);
		}
	}
	
}
