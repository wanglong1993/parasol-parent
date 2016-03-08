package com.ginkgocap.parasol.associate.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.exception.AssociateTypeServiceException;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.service.AssociateTypeService;

/**
 * 
 * @author allenshen
 * @date 2015年11月25日
 * @time 下午2:54:09
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("associateTypeService")
public class AssociateTypeServiceImpl extends BaseService<AssociateType> implements AssociateTypeService {
	private static Logger logger = Logger.getLogger(AssociateTypeServiceImpl.class);
	private static int len_name = 100; // 名字长度

	private static final String LIST_DIRECTORYTYPE_ID_APPID = "List_AssociateType_Id_AppId";
	private static final String MAP_DIRECTORYTYPE_ID_APPID_NAME = "Map_AssociateType_Id_AppId_Name";

	@Override
	public Long createAssociateType(Long appId, AssociateType associateType) throws AssociateTypeServiceException {
		ServiceError.assertForAssociateType(associateType);
		ServiceError.assertAppIdForAssociateType(appId);
		// 1 checkAppid
		if (ObjectUtils.equals(associateType.getAppId(), null) || associateType.getAppId() <= 0) { // appId
			throw new AssociateTypeServiceException(ServiceError.ERROR_PERTIES, "appId property must have a value");
		}
		// check name is blank
		if (StringUtils.isBlank(associateType.getName())) {
			throw new AssociateTypeServiceException(ServiceError.ERROR_PERTIES, "name property must have a value and non blank");
		} else {
			if (associateType.getName().length() > len_name) {
				throw new AssociateTypeServiceException(ServiceError.ERROR_NAME_LIMIT, associateType.getName() + "  length can not be more than " + len_name);
			}
		}
		// 2 check name exist?
		List<AssociateType> associateTypes = this.getAssociateTypessByAppId(appId);
		assertDuplicateName(associateTypes, associateType);

		try {
			associateType.setAppId(appId);
			associateType.setUpdateAt(System.currentTimeMillis());
			return (Long) this.saveEntity(associateType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new AssociateTypeServiceException(e);
		}
	}

	@Override
	public boolean removeAssociateType(Long appId, Long associateTypeId) throws AssociateTypeServiceException {
		ServiceError.assertAppIdForAssociateType(appId);
		ServiceError.assertIdForAssociateType(associateTypeId);

		// 1 checkAppid
		AssociateType associateType = null;
		try {
			associateType = this.getEntity(associateTypeId);
			if (associateType == null) {
				throw new AssociateTypeServiceException(ServiceError.ERROR_NOT_FOUND, "don't find AssociateType by id " + associateTypeId);
			}

			if (!ObjectUtils.equals(associateType.getAppId(), appId)) {
				throw new AssociateTypeServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own associateType");// 移动的不是指定APP的分类
			}

			return this.deleteEntity(associateTypeId);

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new AssociateTypeServiceException(e);
		}
	}

	@Override
	public boolean updateAssociateType(Long appId, AssociateType associateType) throws AssociateTypeServiceException {
		ServiceError.assertAppIdForAssociateType(appId);
		try {
			if (associateType != null) {
				if (ObjectUtils.equals(associateType.getAppId(), appId)) {
					if (StringUtils.isNotBlank(associateType.getName())) {
						if (associateType.getName().length() > len_name) {
							throw new AssociateTypeServiceException(ServiceError.ERROR_NAME_LIMIT, associateType.getName() + "  length can not be more than " + len_name);
						}
						List<AssociateType> associateTypes = this.getAssociateTypessByAppId(appId);
						assertDuplicateName(associateTypes, associateType); // 检查重名
						associateType.setUpdateAt(System.currentTimeMillis());
						return this.updateEntity(associateType);
					} else {
						throw new AssociateTypeServiceException(ServiceError.ERROR_PERTIES, "name property must have a non blank string "); // name
					}

				} else {
					throw new AssociateTypeServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own associateType");// 更新的不是指定的APP
				}
			} else {
				logger.error("paramenter associateType is null");
				return false;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new AssociateTypeServiceException(e);
		}
	}

	@Override
	public AssociateType getAssociateType(Long appId, Long associateTypeId) throws AssociateTypeServiceException {
		ServiceError.assertAppIdForAssociateType(appId);
		ServiceError.assertIdForAssociateType(associateTypeId);
		try {
			AssociateType associateType = this.getEntity(associateTypeId);
			if (associateType != null && ObjectUtils.equals(appId, associateType.getAppId())) {
				return associateType;
			} else {
				logger.error("AssociateType is null or appid of AssociateType not equal " + appId);
				return null;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new AssociateTypeServiceException(e);
		}
	}

	@Override
	public List<AssociateType> getAssociateTypessByAppId(Long appId) throws AssociateTypeServiceException {
		ServiceError.assertAppIdForAssociateType(appId);
		try {
			return this.getSubEntitys(LIST_DIRECTORYTYPE_ID_APPID, 0, 500, appId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new AssociateTypeServiceException(e);
		}
	}

	@Override
	public int countAssociateTypessByAppId(Long appId) throws AssociateTypeServiceException {
		ServiceError.assertAppIdForAssociateType(appId);
		try {
			return this.countEntitys(LIST_DIRECTORYTYPE_ID_APPID, appId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new AssociateTypeServiceException(e);
		}
	}

	/**
	 * 检查是否重名
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @param name
	 * @throws AssociateServiceException
	 */

	private void assertDuplicateName(List<AssociateType> associateTypes, AssociateType associateType) throws AssociateTypeServiceException {
		if (!CollectionUtils.isEmpty(associateTypes)) {
			for (AssociateType dirType : associateTypes) {
				if (dirType != null && !ObjectUtils.equals(dirType.getId(), associateType.getId()) && ObjectUtils.equals(dirType.getName(), associateType.getName())) {
					throw new AssociateTypeServiceException(ServiceError.ERROR_DUPLICATE, "the " + associateType.getName() + " already exists");
				}
			}
		}
	}

	@Override
	public AssociateType getAssociateTypeByName(Long appId, String name) throws AssociateTypeServiceException {
		ServiceError.assertAppIdForAssociateType(appId);
		ServiceError.assertNameForAssociateType(name);
		try {
			Long id = (Long) this.getMapId(MAP_DIRECTORYTYPE_ID_APPID_NAME, appId, name);
			if (id != null) {
				return this.getAssociateType(appId, id);
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return null;

	}
}
