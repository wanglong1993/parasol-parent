package com.ginkgocap.parasol.directory.service;

import java.util.List;

import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.Page;

/**
 * 操作Directory的接口定义
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:46:44
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface DirectoryService {

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
	public Long createDirectoryForRoot(Long directoryType, Directory directory) throws DirectoryServiceException;

	/**
	 * 应用 用户 创建指定父目录下的目录
	 * 
	 * @param appId
	 *            应用Id
	 * @param directoryType
	 *            目录分类
	 * @param directory
	 * @return
	 * @throws DirectoryServiceException
	 */
	public Long createDirectoryForChildren(Long pId, Directory directory) throws DirectoryServiceException;

	/**
	 * 应用 用户 删除一个指定的目录
	 * 也得删除子的目录
	 * @param directoryId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public boolean removeDirectory(Long appId, Long userId, Long directoryId) throws DirectoryServiceException;

	/**
	 * 应用 用户 更新一个指定的目录 不能重名
	 * 
	 * @param directory
	 * @return
	 * @throws DirectoryServiceException
	 */
	public boolean updateDirectory(Long appId, Long userId, Directory directory) throws DirectoryServiceException;

	/**
	 * 应用 用户 移动一个目录 不能重名
	 * 
	 * @param directory
	 * @return
	 * @throws DirectoryServiceException
	 */
	public boolean moveDirectoryToDirectory(Long appId, Long userId, Long directoryId, Long toDirectoryId, List<Directory> treeList) throws DirectoryServiceException;

	/**
	 * 应用 用户 查询一个Directory
	 * 
	 * @param directoryId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public Directory getDirectory(Long appId, Long userId, Long id) throws DirectoryServiceException;
	
	/**
	 * 根据 Directory Id列表 查询
	 * 
	 * @param ids
	 * @return
	 * @throws DirectoryServiceException
	 */
	public List<Directory> getDirectoryList(Long appId, Long userId, List<Long> ids) throws DirectoryServiceException;

	/**
	 * 查询一个父节点下边的一级子节点 注意：最多返回500
	 * 
	 * @param parentId
	 *            父节点ID
	 * @param displayDisabled
	 *            是否显示禁用的Directory，默认为false，不显示
	 * @return
	 * @throws DirectoryServiceException
	 */
	public List<Directory> getDirectorysByParentId(Long appId, Long userId, Long pId) throws DirectoryServiceException;

	/**
	 * 查询一个父节点下边的一级子节点数量
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public int countDirectorysByParentId(Long appId, Long userId, Long pId) throws DirectoryServiceException;

	/**
	 * 查询父节点的列表
	 * 注意：最多500个
	 * @param appId
	 * @param userId
	 * @param directoryTypeId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public List<Directory> getDirectorysForRoot(Long appId, Long userId, Long directoryTypeId) throws DirectoryServiceException;

	/**
	 * 查询父节点的总数
	 * 
	 * @param appId
	 * @param userId
	 * @param directoryTypeId
	 * @return
	 * @throws DirectoryServiceException
	 */
	public int countDirectorysForRoot(Long appId, Long userId, Long directoryTypeId) throws DirectoryServiceException;

	/**
	 * 搜索目录 通过 name（只支持知识，需求）
	 * @param userId
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws DirectoryServiceException
	 */
	public Page<Directory> getDirectoryName(Long userId, String name, long typeId, int pageNo,int pageSize)throws DirectoryServiceException;

	/**
	 * 查询所有目录
	 * 没有实现
	 * @return
	 */
	List<Directory> getAllDirectory(final int page, final int size);

	/**
	 * 修改目录 实体
	 * @param directory
	 * @return
	 */
	boolean updateDirectory(Directory directory);

	/**
	 * 我的目录总个数
	 * @param loginAppId
	 * @param userId
	 * @param typeId
	 * @return
	 */
	int getMyDirectoriesCount(long loginAppId, long userId, long typeId) throws DirectoryServiceException;

	/**
	 * 我的子目录总个数
	 * @param loginAppId
	 * @param userId
	 * @param pid
	 * @param typeId
	 * @return
	 */
	int getMySubDirectoriesCount(long loginAppId, long userId, long pid, long typeId) throws DirectoryServiceException;

	/**
	 * 获取根目录下所有目录
	 * @param loginAppId
	 * @param userId
	 * @param typeId
	 * @return
	 * @throws DirectoryServiceException
	 */
	 List<Directory> getDirectoryListByUserIdType(long loginAppId, long userId, long typeId) throws DirectoryServiceException;

	/**
	 * 通过 pid 获取树形结构目录 （pid 下所有目录）
	 * @param appId
	 * @param userId
	 * @param pid
	 * @return
	 */
	 List<Directory> getTreeDirectorysByParentId(long appId, long userId, long pid, long typeId) throws DirectoryServiceException;
}
