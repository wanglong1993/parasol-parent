package com.ginkgocap.parasol.associate.service;

import java.util.List;

import com.ginkgocap.parasol.associate.exception.AssociateTypeServiceException;
import com.ginkgocap.parasol.associate.model.AssociateType;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午1:10:30
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface AssociateTypeService {

	/**
	 * 创建指定应用的关联类型
	 * 
	 * @param appId
	 *            应用Id
	 * @param associateType
	 *            关联类型
	 * @param associate
	 * @return
	 * @throws AssociateerviceException
	 */
	public Long createAssociateType(Long appId, AssociateType associateType) throws AssociateTypeServiceException;

	/**
	 * 删除一个应用的关联分类
	 * 小心使用
	 * @param appId
	 * @param userId
	 * @param associateTypeId
	 * @return
	 * @throws AssociateerviceException
	 */
	public boolean removeAssociateType(Long appId, Long associateTypeId) throws AssociateTypeServiceException;

	/**
	 * 更新一个应用的关联分类
	 * @param appId
	 * @param associateType
	 * @return
	 * @throws AssociateTypeServiceException
	 */
	public boolean updateAssociateType(Long appId, AssociateType associateType) throws AssociateTypeServiceException;

	/**
	 * 查询一个关联分类
	 * 
	 * @param associateId
	 * @return
	 * @throws AssociateerviceException
	 */
	public AssociateType getAssociateType(Long appId, Long associateTypeId) throws AssociateTypeServiceException;

	/**
	 * 查询一个应用的关联分类列表
	 */
	public List<AssociateType> getAssociateTypessByAppId(Long appId) throws AssociateTypeServiceException;

	/**
	 * 查询一个应用的关联分类列表数量
	 * @param appId
	 * @return
	 * @throws AssociateTypeServiceException
	 */
	public int countAssociateTypessByAppId(Long appId) throws AssociateTypeServiceException;
	
	
	/**
	 * 通过名字查找一个分类对象
	 * @param appId
	 * @param name
	 * @return
	 * @throws AssociateTypeServiceException
	 */
	public AssociateType getAssociateTypeByName(Long appId, String name) throws AssociateTypeServiceException;



}
