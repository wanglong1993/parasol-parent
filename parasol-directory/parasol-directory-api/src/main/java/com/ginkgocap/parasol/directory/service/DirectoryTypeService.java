package com.ginkgocap.parasol.directory.service;

import java.util.List;

import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectoryTypeServiceException;
import com.ginkgocap.parasol.directory.model.DirectoryType;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午1:10:30
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface DirectoryTypeService {

	/**
	 * 应用 用户 创建指定分类下的根目录
	 * 
	 * @param appId
	 *            应用Id
	 * @param directoryType
	 *            目录分类
	 * @param directory
	 * @return
	 * @throws DirectoryServiceException
	 */
	public Long createDirectoryType(Long appId, DirectoryType directoryType) throws DirectoryTypeServiceException;

	/**
	 * 删除一个应用的分类
	 * 小心使用
	 * @param appId
	 * @param userId
	 * @param directoryTypeId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public boolean removeDirectoryType(Long appId, Long directoryTypeId) throws DirectoryTypeServiceException;

	/**
	 * 应用 用户 更新一个指定的目录 不能重名
	 * @param appId
	 * @param directoryType
	 * @return
	 * @throws DirectoryTypeServiceException
	 */
	public boolean updateDirectoryType(Long appId, DirectoryType directoryType) throws DirectoryTypeServiceException;

	/**
	 * 应用 用户 查询一个Directory
	 * 
	 * @param directoryId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public DirectoryType getDirectoryType(Long appId, Long directoryTypeId) throws DirectoryTypeServiceException;

	/**
	 * 查询一个父节点下边的一级子节点
	 * 注意：最多500
	 * @param parentId
	 *            父节点ID
	 * @param displayDisabled
	 *            是否显示禁用的Directory，默认为false，不显示
	 * @return
	 * @throws DirectoryServiceException
	 */
	public List<DirectoryType> getDirectoryTypessByAppId(Long appId) throws DirectoryTypeServiceException;

	/**
	 * 查询一个App分类的数量
	 * @param appId
	 * @return
	 * @throws DirectoryTypeServiceException
	 */
	public int countDirectoryTypessByAppId(Long appId) throws DirectoryTypeServiceException;
	
	
	/**
	 * 通过名字查找一个DirectoryType对象
	 * @param appId
	 * @param name
	 * @return
	 * @throws DirectoryTypeServiceException
	 */
	public DirectoryType getDirectoryTypeByName(Long appId, String name) throws DirectoryTypeServiceException;



}
