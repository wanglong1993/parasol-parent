package com.ginkgocap.parasol.associate.service;

import java.util.List;
import java.util.Map;

import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.model.Page;

/**
 * 操作Associate的接口定义
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:46:44
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface AssociateService {

	/**
	 * 创建关联关系
	 * 
	 * @param appId
	 *            , 应用的AppId
	 * @param userId
	 *            , 应用的用户Id
	 * @param associate
	 *            关联关系对象
	 * @return
	 * @throws AssociateServiceException
	 */
	public Long createAssociate(Long appId, Long userId, Associate associate) throws AssociateServiceException;

	/**
	 * 删除关联关系
	 * 
	 * @param appId
	 * @param userId
	 * @param associateId
	 * @return
	 * @throws AssociateServiceException
	 */
	public boolean removeAssociate(Long appId, Long userId, Long associateId) throws AssociateServiceException;

	/**
	 * 查询一个关联详情
	 * 
	 * @param appId
	 * @param userId
	 * @param associateId
	 * @return
	 * @throws AssociateServiceException
	 */
	public Associate getAssociate(Long appId, Long userId, Long associateId) throws AssociateServiceException;

	/**
	 * 查询一个资源的关联详情列表
	 * 
	 * @param appId
	 * @param userId
	 * @param sourceId
	 * @param sourceTypeId
	 * @return
	 * @throws AssociateServiceException
	 */
	public List<Associate> getAssociatesBySourceId(Long appId, Long userId, Long sourceId ,Long sourceTypeId)  throws AssociateServiceException;
	/**
	 *  查询一个对象的关联数据，需要定义一下格式
	 * @param appId
	 * @param sourceType
	 * @param sourceId
	 * @return
	 * @throws AssociateServiceException
	 */
	public Map<AssociateType, List<Associate>> getAssociatesBy(Long appId, Long sourceType, Long sourceId) throws AssociateServiceException;

	/**
	 *
	 * @param userId
	 * @param typeId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws AssociateServiceException
	 */
	public Page<Associate> getassociatesByPage(Long userId, Long typeId, int pageNo, int pageSize) throws AssociateServiceException;

	/**
	 * 查询一个资源的关联详情列表
	 * @param userId
	 * @param typeId
	 * @return
	 */
	public Page<Map<String, Object>> getAssociatesByPage(Long userId, Long typeId, int pageNo, int pageSize) throws AssociateServiceException;
}
