package com.ginkgocap.parasol.metadata.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.metadata.exception.CodeRegionServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.CodeRegionService;
import com.ginkgocap.parasol.metadata.type.CodeRegionType;

/**
 * 
 * @author allenshen
 * @date 2015年11月15日
 * @time 下午11:13:04
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("codeRegionService")
public class CodeRegionServiceImpl extends BaseService<CodeRegion> implements CodeRegionService {
	private static Logger logger = Logger.getLogger(CodeRegionServiceImpl.class);
	private static int error_duplicate = 100; // 重名
	private static int error_name_is_blank=101; //名字是空的
	private static int error_parentcode_null = 102; // 父对象不存在。

	
	private static final String CODEREGION_LIST_ID_PID = "CodeRegion_List_Id_ParentId";
	private static final String CODEREGION_LIST_ID_PID_TYPE = "CodeRegion_List_Id_ParentId_Type";
	private static final String CODEREGION_MAP_ID_TBID="CodeRegion_Map_Id_TbId";

	@Override
	public Long createCodeRegionForRoot(CodeRegion codeRegion, CodeRegionType type) throws CodeRegionServiceException {
		if (codeRegion != null && type != null) {
			//检查名字不能是空的
			if (StringUtils.isBlank(codeRegion.getCname())) {
				throw new CodeRegionServiceException(error_name_is_blank, "Cname property must have a value");
			}
			
			//检查是否又重名的
			List<CodeRegion> codeRegions = getCodeRegionsForRootByType(CodeRegionService.ROOT_PARENT_ID, type);
			if (CollectionUtils.isNotEmpty(codeRegions)) {
				for (CodeRegion existCodeRegion : codeRegions) {
					if (existCodeRegion != null && ObjectUtils.equals(existCodeRegion.getCname(), codeRegion.getCname())) {
						throw new CodeRegionServiceException(error_duplicate, "Name is " + existCodeRegion.getCname() + " already exists");
					}
				}
			}
			
			//设置Root的必须值
			codeRegion.setParentId(CodeRegionService.ROOT_PARENT_ID);
			codeRegion.setType(type.getValue());
			
			try {
				Long id = (Long) saveEntity(codeRegion);
				if (id != null) {
					CodeRegion newCodeRegion = getCodeRegionById(id);
					if (newCodeRegion != null) {
						newCodeRegion.setNumberCode(CodeRegionService.ROOT_PARENT_ID + "-" + id);
						updateCodeRegion(newCodeRegion);
					}
				}
				return id;
			} catch (BaseServiceException e) {
				if (logger.isDebugEnabled()){
					e.printStackTrace(System.err);
				}
				throw new CodeRegionServiceException(e);
			}
			
		} else {
			throw new CodeRegionServiceException("codeRegion parameter must have value or CodeRetionType type parameter must have value");
		}
	}

	@Override
	public Long createCodeRegionForChildren(Long parentId, CodeRegion codeRegion) throws CodeRegionServiceException {
		try {
			// 检查父对象情况
			if (parentId == null) {
				throw new CodeRegionServiceException(error_parentcode_null, "The specified CodeRegion by paraentId [" + ObjectUtils.toString(parentId, null)
						+ "] does not exist");
			}
			// 检查父对象的状态
			CodeRegion parentCodeRegion = getEntity(parentId);
			if (parentCodeRegion == null) {
				throw new CodeRegionServiceException(error_parentcode_null, "The specified CodeRegion by paraentId [" + ObjectUtils.toString(parentId, null)
						+ "] does not exist");
			}
			// 检查名字
			if (codeRegion == null || StringUtils.isBlank(codeRegion.getCname())) {
				throw new CodeRegionServiceException(error_name_is_blank, "Name property of code must have a value");
			}

			// 检查是否重名
			List<CodeRegion> codeRegions = getCodeRegionsByParentId(parentId);
			if (CollectionUtils.isNotEmpty(codeRegions)) {
				for (CodeRegion existCodeRegion : codeRegions) {
					if (existCodeRegion != null && ObjectUtils.equals(existCodeRegion.getCname(), codeRegion.getCname())) {
						throw new CodeRegionServiceException(error_duplicate, "Name is " + codeRegion.getCname() + " already exists");
					}
				}
			}
			codeRegion.setType(parentCodeRegion.getType());
			codeRegion.setParentId(parentId);
			
			Long id = (Long) saveEntity(codeRegion);
			if (id != null) {
				CodeRegion newCodeRegion = getCodeRegionById(id);
				if (newCodeRegion != null) {
					newCodeRegion.setNumberCode(parentCodeRegion.getNumberCode() + "-" + id);
					updateCodeRegion(newCodeRegion);
				}
			}

			return id;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeRegionServiceException(e);
		}		
	}

	@Override
	public boolean removeCodeRegion(Long codeRegionId) throws CodeRegionServiceException {
		try {
			if (codeRegionId != null) {
				CodeRegion myself = getEntity(codeRegionId);
				if (myself != null) {
					List<CodeRegion> codeRegions = getCodeRegionsByParentId(codeRegionId);
					if (CollectionUtils.isNotEmpty(codeRegions)) {
						for (CodeRegion existCodeRegion : codeRegions) {
							if (existCodeRegion != null) {
								removeCodeRegion(existCodeRegion.getId());
							}
						}
					}
					deleteEntity(codeRegionId);
				} else {
					logger.warn("remove CodeRegion don't exist by id [" + codeRegionId + "]");
				}

			} else {
				logger.warn("codeRegionId parameter is null");
			}
			return true;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeRegionServiceException(e);
		}
	}

	@Override
	public boolean updateCodeRegion(CodeRegion codeRegion) throws CodeRegionServiceException {
		try {
			return updateEntity(codeRegion);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeRegionServiceException(e);
		}
	}

	@Override
	public CodeRegion getCodeRegionById(Long codeRegionId) throws CodeRegionServiceException {
		try {
			return getEntity(codeRegionId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeRegionServiceException(e);
		}
	}

	@Override
	public List<CodeRegion> getCodeRegionsByParentId(Long parentId) throws CodeRegionServiceException {
		try {
			return getEntitys(CODEREGION_LIST_ID_PID, parentId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeRegionServiceException(e);
		}
	}

	@Override
	public int countCodeRegionsByParentId(Long parentId) throws CodeRegionServiceException {
		try {
			return countEntitys(CODEREGION_LIST_ID_PID, parentId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeRegionServiceException(e);
		}
	}

	@Override
	public List<CodeRegion> getCodeRegionsForRootByType(long parentId, CodeRegionType type) throws CodeRegionServiceException {
		try {
			return getEntitys(CODEREGION_LIST_ID_PID_TYPE, parentId,type.getValue());
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeRegionServiceException(e);
		}
	}

	@Override
	public Long getCodeRegionIdByTbId(String tbId) throws CodeRegionServiceException {
		try {
			Long id = (Long) getMapId(CODEREGION_MAP_ID_TBID, tbId);
			return id;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()){
				e.printStackTrace(System.err);
			}
			throw new CodeRegionServiceException(e);
		}
	}

}
