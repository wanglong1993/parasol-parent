package com.ginkgocap.parasol.directory.service;

import java.util.List;

import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.model.DirectorySource;

/**
 * 
 * 
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午1:10:30
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface DirectorySourceService {

	/**
	 * 创建一个目录下的资源 注意依赖条件：目录必须存在。
	 * 
	 * @param DirectorySource
	 * @return Long
	 * 
	 * @throws DirectoryServiceException
	 */
	public Long createDirectorySources(DirectorySource sources) throws DirectorySourceServiceException;

	/**
	 * 删除目录下的一个资源 小心使用
	 * @param appId
	 * @param userId
	 * @param id
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	public boolean removeDirectorySources(Long appId, Long userId, Long id) throws DirectorySourceServiceException;

	/**
	 * 批量删除资源 通过ids
	 * @param ids
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	public boolean removeDirectorySourceByIds(List<Long> ids) throws Exception;

	/**
	 * 从资源中是删除具有资源Id的所有目录资源 小心使用
	 * 
	 * @param appId
	 * @param userId
	 * @param sourceId : DirectorySource.sourceId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public boolean removeDirectorySourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws DirectorySourceServiceException;
	
	
	
	/**
	 * 从资源中是查询具有资源Id的所有目录资源
	 * Example: 一篇知识都是在什么目录下；一个文件都是在什么组织下
	 * @param appId
	 * @param userId
	 * @param sourceType
	 * @param sourceId 业务数据的Id(DirectorySource.sourceId)
	 * @param sourceId : 
	 * @return
	 * @throws DirectoryServiceException
	 */
	public List<DirectorySource> getDirectorySourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws DirectorySourceServiceException;
	
	/**
	 * 从资源中是查询具有目录Id的所有目录资源
	 * Example: 一篇知识都是在什么目录下；一个文件都是在什么组织下
	 * @param appId
	 * @param userId
	 * @param sourceType
	 * @param directoryId 业务数据的Id(DirectorySource.sourceId) 
	 * @return
	 * @throws DirectoryServiceException
	 */
	public List<DirectorySource> getSourcesByDirectoryIdAndSourceType(int start,int size,Object... parameters) throws DirectorySourceServiceException;
	
	/**
	 * 改变资源所在的目录
	 * @param userId
	 * @param appId
	 * @param directoryId
	 * @param ids
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	public boolean moveDirectorySources(long userId, long appId, Long directoryId , Long[] ids) throws DirectorySourceServiceException;

	

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
	 * @param id
	 * @return
	 * @throws DirectoryServiceException
	 */
	public DirectorySource getDirectorySourceById(Long appId, Long id) throws DirectorySourceServiceException;

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
	 * @param directoryId
	 * @param sourceType
	 * @param souriceId
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	public DirectorySource getDirectorySource(Long userId, Long directoryId, Long sourceAppId, Integer sourceType, Long souriceId) throws DirectorySourceServiceException;

	/**
	 * 根据sourceId查询标签集合
	 * @throws DirectorySourceServiceException
	 */
	public List<Long> getDirectoryIdsBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws DirectorySourceServiceException;

	/**
	 * 删除资源目录 通过目录id
	 * @param directoryId
	 * @return
	 */
	boolean removeDirectorySourcesByDirId(long directoryId);
	/**
	 * 批量删除资源目录
	 * @throws DirectorySourceServiceException
	 */
	public boolean removeDirectorySourcesByDireIds(List<Long> ids) throws DirectorySourceServiceException;

	/**
	 *更新目录
	 * @throws DirectorySourceServiceException
	 */
	public boolean updateDiresources(Long appId, Long userId, Long sourceId,Long sourceType,List<Long> direIds,String sourceTitle) throws DirectorySourceServiceException;

	/**
	 * 我的目录下所有资源个数
	 * @param appId
	 * @param userId
	 * @param typeId
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	int getMyDirectoriesSouceCount(Long appId, Long userId, Long typeId) throws DirectorySourceServiceException;

	/**
	 * 查找所有资源 通过资源类型
	 * @param start
	 * @param size
	 * @param sourceType
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	List<DirectorySource> getSourcesBySourceType(int start, int size, byte sourceType) throws DirectorySourceServiceException;
}
