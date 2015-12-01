package com.ginkgocap.parasol.directory.service;

import java.util.List;

import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.model.DirectorySource;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午1:10:30
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface DirectorySourceService {

	/**
	 * 创建一个目录下的资源 注意依赖条件：目录的存在。
	 * 
	 * @param appId
	 *            应用Id
	 * @param DirectorySource
	 *            目录分类
	 * @param directory
	 * @return
	 * @throws DirectoryServiceException
	 */
	public Long createDirectorySources(DirectorySource sources) throws DirectorySourceServiceException;

	/**
	 * 删除目录下的一个资源 小心使用
	 * 
	 * @param appId
	 * @param userId
	 * @param id
	 *            DirectorSource的ID
	 * @return
	 * @throws DirectoryServiceException
	 */
	public boolean removeDirectorySources(Long appId, Long userId, Long id) throws DirectorySourceServiceException;

	/**
	 * 从资源中是删除具有资源Id的所有目录资源 小心使用
	 * 
	 * @param appId
	 * @param userId
	 * @param DirectorySourcesId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public boolean removeDirectorySourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws DirectorySourceServiceException;

	/**
	 * 更新 directorySource
	 * 
	 * @param directorySource
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	public boolean updateDirectorySource(DirectorySource directorySource) throws DirectorySourceServiceException;

	/**
	 * 查询一个Directory
	 * 
	 * @param directoryId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public DirectorySource getDirectorySourceById(Long appId, Long directorySourceId) throws DirectorySourceServiceException;

	/**
	 * 查询一个目录下的资源列表
	 * 
	 * @param directoryId
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	public List<DirectorySource> getDirectorySourcesByDirectoryId(Long appId, Long userId, Long directoryId) throws DirectorySourceServiceException;

	/**
	 * 统计目录下的资源数量
	 * 
	 * @param appId
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	public int countDirectorySourcesByDirectoryId(Long appId, Long userId, Long directoryId) throws DirectorySourceServiceException;

	/**
	 * 查询某个资源是否在指定的目录下，存在就返回这个DirectorSource，否则, 返回 null
	 * 
	 * @param userId
	 * @param appId
	 * @param directoryId
	 * @param sourceType
	 * @param souriceId
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	public DirectorySource getDirectorySource(Long userId, Long directoryId, Long sourceAppId, Integer sourceType, Long souriceId) throws DirectorySourceServiceException;

}
