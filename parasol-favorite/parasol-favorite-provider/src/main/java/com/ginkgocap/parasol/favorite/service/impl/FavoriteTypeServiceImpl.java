package com.ginkgocap.parasol.favorite.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteTypeServiceException;
import com.ginkgocap.parasol.favorite.model.FavoriteType;
import com.ginkgocap.parasol.favorite.service.FavoriteTypeService;

/**
 * 
 * @author allenshen
 * @date 2015年11月25日
 * @time 下午2:54:09
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("favoriteTypeService")
public class FavoriteTypeServiceImpl extends BaseService<FavoriteType> implements FavoriteTypeService {
	private static Logger logger = Logger.getLogger(FavoriteTypeServiceImpl.class);
	private static int len_name = 100; // 名字长度

	private static final String LIST_DIRECTORYTYPE_ID_APPID = "List_FavoriteType_Id_AppId";
	private static final String MAP_DIRECTORYTYPE_ID_APPID_NAME = "Map_FavoriteType_Id_AppId_Name";

	@Override
	public Long createFavoriteType(Long appId, FavoriteType favoriteType) throws FavoriteTypeServiceException {
		ServiceError.assertForFavoriteType(favoriteType);
		ServiceError.assertAppIdForFavoriteType(appId);
		// 1 checkAppid
		if (ObjectUtils.equals(favoriteType.getAppId(), null) || favoriteType.getAppId() <= 0) { // appId
			throw new FavoriteTypeServiceException(ServiceError.ERROR_PERTIES, "appId property must have a value");
		}
		// check name is blank
		if (StringUtils.isBlank(favoriteType.getName())) {
			throw new FavoriteTypeServiceException(ServiceError.ERROR_PERTIES, "name property must have a value and non blank");
		} else {
			if (favoriteType.getName().length() > len_name) {
				throw new FavoriteTypeServiceException(ServiceError.ERROR_NAME_LIMIT, favoriteType.getName() + "  length can not be more than " + len_name);
			}
		}
		// 2 check name exist?
		List<FavoriteType> favoriteTypes = this.getFavoriteTypessByAppId(appId);
		assertDuplicateName(favoriteTypes, favoriteType);

		try {
			favoriteType.setAppId(appId);
			favoriteType.setUpdateAt(System.currentTimeMillis());
			return (Long) this.saveEntity(favoriteType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteTypeServiceException(e);
		}
	}

	@Override
	public boolean removeFavoriteType(Long appId, Long favoriteTypeId) throws FavoriteTypeServiceException {
		ServiceError.assertAppIdForFavoriteType(appId);
		ServiceError.assertIdForFavoriteType(favoriteTypeId);

		// 1 checkAppid
		FavoriteType favoriteType = null;
		try {
			favoriteType = this.getEntity(favoriteTypeId);
			if (favoriteType == null) {
				throw new FavoriteTypeServiceException(ServiceError.ERROR_NOT_FOUND, "don't find FavoriteType by id " + favoriteTypeId);
			}

			if (!ObjectUtils.equals(favoriteType.getAppId(), appId)) {
				throw new FavoriteTypeServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favoriteType");// 移动的不是自己的目录
			}

			return this.deleteEntity(favoriteTypeId);

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteTypeServiceException(e);
		}
	}

	@Override
	public boolean updateFavoriteType(Long appId, FavoriteType favoriteType) throws FavoriteTypeServiceException {
		ServiceError.assertAppIdForFavoriteType(appId);
		try {
			if (favoriteType != null) {
				if (ObjectUtils.equals(favoriteType.getAppId(), appId)) {
					if (StringUtils.isNotBlank(favoriteType.getName())) {
						if (favoriteType.getName().length() > len_name) {
							throw new FavoriteTypeServiceException(ServiceError.ERROR_NAME_LIMIT, favoriteType.getName() + "  length can not be more than " + len_name);
						}
						List<FavoriteType> favoriteTypes = this.getFavoriteTypessByAppId(appId);
						assertDuplicateName(favoriteTypes, favoriteType); // 检查重名
						favoriteType.setUpdateAt(System.currentTimeMillis());
						return this.updateEntity(favoriteType);
					} else {
						throw new FavoriteTypeServiceException(ServiceError.ERROR_PERTIES, "name property must have a non blank string "); // name
					}

				} else {
					throw new FavoriteTypeServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favoriteType");// 移动的不是自己的目录
				}
			} else {
				logger.error("paramenter favoriteType is null");
				return false;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteTypeServiceException(e);
		}
	}

	@Override
	public FavoriteType getFavoriteType(Long appId, Long favoriteTypeId) throws FavoriteTypeServiceException {
		ServiceError.assertAppIdForFavoriteType(appId);
		ServiceError.assertIdForFavoriteType(favoriteTypeId);
		try {
			FavoriteType favoriteType = this.getEntity(favoriteTypeId);
			if (favoriteType != null && ObjectUtils.equals(appId, favoriteType.getAppId())) {
				return favoriteType;
			} else {
				logger.error("FavoriteType is null or appid of FavoriteType not equal " + appId);
				return null;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteTypeServiceException(e);
		}
	}

	@Override
	public List<FavoriteType> getFavoriteTypessByAppId(Long appId) throws FavoriteTypeServiceException {
		ServiceError.assertAppIdForFavoriteType(appId);
		try {
			return this.getSubEntitys(LIST_DIRECTORYTYPE_ID_APPID, 0, 500, appId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteTypeServiceException(e);
		}
	}

	@Override
	public int countFavoriteTypessByAppId(Long appId) throws FavoriteTypeServiceException {
		ServiceError.assertAppIdForFavoriteType(appId);
		try {
			return this.countEntitys(LIST_DIRECTORYTYPE_ID_APPID, appId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteTypeServiceException(e);
		}
	}

	/**
	 * 检查是否重名
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @param name
	 * @throws FavoriteServiceException
	 */

	private void assertDuplicateName(List<FavoriteType> favoriteTypes, FavoriteType favoriteType) throws FavoriteTypeServiceException {
		if (!CollectionUtils.isEmpty(favoriteTypes)) {
			for (FavoriteType dirType : favoriteTypes) {
				if (dirType != null && !ObjectUtils.equals(dirType.getId(), favoriteType.getId()) && ObjectUtils.equals(dirType.getName(), favoriteType.getName())) {
					throw new FavoriteTypeServiceException(ServiceError.ERROR_DUPLICATE, "the " + favoriteType.getName() + " already exists");
				}
			}
		}
	}

	@Override
	public FavoriteType getFavoriteTypeByName(Long appId, String name) throws FavoriteTypeServiceException {
		ServiceError.assertAppIdForFavoriteType(appId);
		ServiceError.assertNameForFavoriteType(name);
		try {
			Long id = (Long) this.getMapId(MAP_DIRECTORYTYPE_ID_APPID_NAME, appId, name);
			if (id != null) {
				return this.getFavoriteType(appId, id);
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return null;

	}
}
