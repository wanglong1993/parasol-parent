package com.ginkgocap.parasol.directory.service.impl;

import java.rmi.ServerError;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectoryTypeServiceException;
import com.ginkgocap.parasol.directory.model.DirectoryType;
import com.ginkgocap.parasol.directory.service.DirectoryTypeService;

/**
 * 
 * @author allenshen
 * @date 2015年11月25日
 * @time 下午2:54:09
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("directoryTypeService")
public class DirectoryTypeServiceImpl extends BaseService<DirectoryType> implements DirectoryTypeService {
	private static Logger logger = Logger.getLogger(DirectoryTypeServiceImpl.class);
	private static int len_name = 15; // 名字长度

	private static final String LIST_DIRECTORYTYPE_ID_APPID = "List_DirectoryType_Id_AppId";
	private static final String MAP_DIRECTORYTYPE_ID_APPID_NAME = "Map_DirectoryType_Id_AppId_Name";

	@Override
	public Long createDirectoryType(Long appId, DirectoryType directoryType) throws DirectoryTypeServiceException {
		ServiceError.assertForDirectoryType(directoryType);
		ServiceError.assertAppIdForDirectoryType(appId);
		// 1 checkAppid
		if (ObjectUtils.equals(directoryType.getAppId(), null) || directoryType.getAppId() <= 0) { // appId
			throw new DirectoryTypeServiceException(ServiceError.ERROR_PERTIES, "appId property must have a value");
		}
		// check name is blank
		if (StringUtils.isBlank(directoryType.getName())) {
			throw new DirectoryTypeServiceException(ServiceError.ERROR_PERTIES, "name property must have a value and non blank");
		} else {
			if (directoryType.getName().length() > len_name) {
				throw new DirectoryTypeServiceException(ServiceError.ERROR_NAME_LIMIT, directoryType.getName() + "  length can not be more than " + len_name);
			}
		}
		// 2 check name exist?
		List<DirectoryType> directoryTypes = this.getDirectoryTypessByAppId(appId);
		assertDuplicateName(directoryTypes, directoryType);

		try {
			directoryType.setAppId(appId);
			directoryType.setUpdateAt(System.currentTimeMillis());
			return (Long) this.saveEntity(directoryType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryTypeServiceException(e);
		}
	}

	@Override
	public boolean removeDirectoryType(Long appId, Long directoryTypeId) throws DirectoryTypeServiceException {
		ServiceError.assertAppIdForDirectoryType(appId);
		ServiceError.assertIdForDirectoryType(directoryTypeId);

		// 1 checkAppid
		DirectoryType directoryType = null;
		try {
			directoryType = this.getEntity(directoryTypeId);
			if (directoryType == null) {
				throw new DirectoryTypeServiceException(ServiceError.ERROR_NOT_FOUND, "don't find DirectoryType by id " + directoryTypeId);
			}

			if (!ObjectUtils.equals(directoryType.getAppId(), appId)) {
				throw new DirectoryTypeServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directoryType");// 移动的不是自己的目录
			}

			return this.deleteEntity(directoryTypeId);

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryTypeServiceException(e);
		}
	}

	@Override
	public boolean updateDirectoryType(Long appId, DirectoryType directoryType) throws DirectoryTypeServiceException {
		ServiceError.assertAppIdForDirectoryType(appId);
		try {
			if (directoryType != null) {
				if (ObjectUtils.equals(directoryType.getAppId(), appId)) {
					if (StringUtils.isNotBlank(directoryType.getName())) {
						if (directoryType.getName().length() > len_name) {
							throw new DirectoryTypeServiceException(ServiceError.ERROR_NAME_LIMIT, directoryType.getName() + "  length can not be more than " + len_name);
						}
						List<DirectoryType> directoryTypes = this.getDirectoryTypessByAppId(appId);
						assertDuplicateName(directoryTypes, directoryType); // 检查重名
						directoryType.setUpdateAt(System.currentTimeMillis());
						return this.updateEntity(directoryType);
					} else {
						throw new DirectoryTypeServiceException(ServiceError.ERROR_PERTIES, "name property must have a non blank string "); // name
					}

				} else {
					throw new DirectoryTypeServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directoryType");// 移动的不是自己的目录
				}
			} else {
				logger.error("paramenter directoryType is null");
				return false;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryTypeServiceException(e);
		}
	}

	@Override
	public DirectoryType getDirectoryType(Long appId, Long directoryTypeId) throws DirectoryTypeServiceException {
		ServiceError.assertAppIdForDirectoryType(appId);
		ServiceError.assertIdForDirectoryType(directoryTypeId);
		try {
			DirectoryType directoryType = this.getEntity(directoryTypeId);
			if (directoryType != null && ObjectUtils.equals(appId, directoryType.getAppId())) {
				return directoryType;
			} else {
				logger.error("DirectoryType is null or appid of DirectoryType not equal " + appId);
				return null;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryTypeServiceException(e);
		}
	}

	@Override
	public List<DirectoryType> getDirectoryTypessByAppId(Long appId) throws DirectoryTypeServiceException {
		ServiceError.assertAppIdForDirectoryType(appId);
		try {
			return this.getSubEntitys(LIST_DIRECTORYTYPE_ID_APPID, 0, 500, appId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryTypeServiceException(e);
		}
	}

	@Override
	public int countDirectoryTypessByAppId(Long appId) throws DirectoryTypeServiceException {
		ServiceError.assertAppIdForDirectoryType(appId);
		try {
			return this.countEntitys(LIST_DIRECTORYTYPE_ID_APPID, appId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryTypeServiceException(e);
		}
	}

	/**
	 * 检查是否重名
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @param name
	 * @throws DirectoryServiceException
	 */

	private void assertDuplicateName(List<DirectoryType> directoryTypes, DirectoryType directoryType) throws DirectoryTypeServiceException {
		if (!CollectionUtils.isEmpty(directoryTypes)) {
			for (DirectoryType dirType : directoryTypes) {
				if (dirType != null && !ObjectUtils.equals(dirType.getId(), directoryType.getId()) && ObjectUtils.equals(dirType.getName(), directoryType.getName())) {
					throw new DirectoryTypeServiceException(ServiceError.ERROR_DUPLICATE, "the " + directoryType.getName() + " already exists");
				}
			}
		}
	}

	@Override
	public DirectoryType getDirectoryTypeByName(Long appId, String name) throws DirectoryTypeServiceException {
		ServiceError.assertAppIdForDirectoryType(appId);
		ServiceError.assertNameForDirectoryType(name);
		try {
			Long id = (Long) this.getMapId(MAP_DIRECTORYTYPE_ID_APPID_NAME, appId, name);
			if (id != null) {
				return this.getDirectoryType(appId, id);
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return null;

	}
}
