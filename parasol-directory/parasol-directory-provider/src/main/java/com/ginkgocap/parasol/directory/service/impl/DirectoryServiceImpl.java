package com.ginkgocap.parasol.directory.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectoryTypeServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.DirectoryType;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectoryTypeService;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午4:26:56
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("directoryService")
public class DirectoryServiceImpl extends BaseService<Directory> implements DirectoryService {
	private static Logger logger = Logger.getLogger(DirectoryServiceImpl.class);
	private static int len_name = 50;

	private static String LIST_DIRECTORY_PID_ID = "List_Directory_Id_Pid"; // 查询子节点
	private static String LIST_DIRECTORY_ID_APPID_USERID_TYPEID_PID = "List_Directory_Id_AppId_UserId_TypeId_Pid"; // 查询一个应用的分类根目录

	@Autowired
	private DirectoryTypeService directoryTypeService;

	/**
	 * 创建应用分类下边的根目录 比如创建系统应用知识下的根目录
	 */
	@Override
	public Long createDirectoryForRoot(Long directoryTypeId, Directory directory) throws DirectoryServiceException {
		return createDirectoryForChildren(0l, directory);
	}

	@Override
	public Long createDirectoryForChildren(Long pId, Directory directory) throws DirectoryServiceException {
		// 1.check input parameters(appId, typeId, name，userId)
		ServiceError.assertDirectoryForDirectory(directory);
		// 1.1 checkAppid
		if (ObjectUtils.equals(directory.getAppId(), null) || directory.getAppId() <= 0) { // appId
			throw new DirectoryServiceException(ServiceError.ERROR_PERTIES, "appId property must have a value and greater than zero");
		}

		// 1.2 check type id
		if (directory.getTypeId() <= 0) { // type 必须有值且的大于1
			throw new DirectoryServiceException(ServiceError.ERROR_PERTIES, "type property must have a value and greater than zero");
		} else {
			// 1.2.1 检查目录树分类是否存在
			DirectoryType directoryType = null;
			try {
				directoryType = directoryTypeService.getDirectoryType(directory.getAppId(), directory.getTypeId());
			} catch (DirectoryTypeServiceException e) {
				if (logger.isDebugEnabled()) {
					e.printStackTrace(System.err);
				}
				throw new DirectoryServiceException(e);
			}
			if (directoryType == null) {
				StringBuffer sb = new StringBuffer();
				sb.append("don't find the DirectoryType by appid is ").append(directory.getAppId()).append(" and type is ").append(directory.getTypeId());
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
			}
		}

		// 1.3 检查名字
		// TODO: 检查敏感词
		logger.info("请检查敏感词");
		directory.setName(StringUtils.trim(directory.getName()));
		if (StringUtils.isBlank(directory.getName())) { // 名字不能为空
			throw new DirectoryServiceException(ServiceError.ERROR_PERTIES, "name property must have a non blank string");
		} else {
			if (directory.getName().length() > len_name) {
				throw new DirectoryServiceException(ServiceError.ERROR_NAME_LIMIT, directory.getName() + "  length can not be more than " + len_name);
			}
		}

		// 1.4 用户Id
		if (directory.getUserId() <= 0) {
			throw new DirectoryServiceException(ServiceError.ERROR_USER_EXIST, "pelase set userId property");
		} else {
			// TODO: 检查用户是否存在
			logger.info("Please implement check user exist condition");
		}

		// 2. 检查父节点和现在的节点情况
		Directory parentDirectory = null;
		if (pId != null && pId != 0) {
			parentDirectory = this.getDirectory(directory.getAppId(), directory.getUserId(), pId);
			if (parentDirectory == null || !ObjectUtils.equals(parentDirectory.getTypeId(), directory.getTypeId())) {
				StringBuffer sb = new StringBuffer();
				sb.append("don't find the parent Directory by appid is ").append(directory.getAppId()).append(" userId is ").append(directory.getUserId());
				sb.append(" or directory type mismatched parent directory type");
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
			} else {
				directory.setPid(pId);
			}
		}

		// 3. 检查有没有重名根目录
		List<Directory> directorys = null;
		if (pId != null && pId != 0) { // 创建非根节点
			directorys = getDirectorysByParentId(directory.getAppId(), directory.getUserId(), pId);
		} else { // 创建根节点
			directorys = getDirectorysForRoot(directory.getAppId(), directory.getUserId(), directory.getTypeId());
		}
		assertDuplicateName(directorys, directory);

		// 4.创建
		try {
			directory.setPid(pId);
			directory.setUpdateAt(System.currentTimeMillis());
			Long id = (Long) this.saveEntity(directory);
			if (id > 0) {
				directory.setId(id);
				String parentNumberCode = getParentNumberCode(parentDirectory);
				directory.setNumberCode(parentNumberCode + "-" + id);
				this.updateEntity(directory);
			}
			return id;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}
	}

	@Override
	public boolean removeDirectory(Long appId, Long userId, Long directoryId) throws DirectoryServiceException {
		// 1.check input parameters
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryIdForDirectory(directoryId);
		try {
			Directory directory = this.getEntity(directoryId);

			if (directory != null) {
				if (!ObjectUtils.equals(directory.getAppId(), appId) || !ObjectUtils.equals(directory.getUserId(), userId)) {
					throw new DirectoryServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directory");// 删除的不是自己的目录
				}
			} else {
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, "remove " + directoryId + " directory not exist");
			}

			// 删除Sub Dir
			List<Directory> subDirectorys = this.getDirectorysByParentId(appId, userId, directoryId);
			if (!CollectionUtils.isEmpty(subDirectorys)) {
				for (Directory subDirectory : subDirectorys) {
					this.removeDirectory(appId, userId, subDirectory.getId());
				}
			}

			// 删除自己
			return this.deleteEntity(directoryId);

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}

	}

	@Override
	public boolean updateDirectory(Long appId, Long userId, Directory directory) throws DirectoryServiceException {
		// 1.check input parameters
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		// find old directory
		try {
			if (directory != null) {
				ServiceError.assertDirectoryIdForDirectory(directory.getId());
				Directory oldDirectory = getEntity(directory.getId());
				if (oldDirectory != null) {
					if (!ObjectUtils.equals(oldDirectory.getAppId(), appId) || !ObjectUtils.equals(oldDirectory.getUserId(), userId)) {
						throw new DirectoryServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directory");// 更新的不是自己的目录
					}

					// 检查名字
					// TODO: 检查敏感词
					logger.info("请检查敏感词");
					directory.setName(StringUtils.trim(directory.getName()));
					if (StringUtils.isBlank(directory.getName())) { // 名字不能为空
						throw new DirectoryServiceException(ServiceError.ERROR_PERTIES, "name property must have a non blank string");
					} else {
						if (directory.getName().length() > len_name) {
							throw new DirectoryServiceException(ServiceError.ERROR_NAME_LIMIT, directory.getName() + "  length can not be more than " + len_name);
						}
					}

					// 检查重名
					List<Directory> directorys = oldDirectory.getPid() != 0 ? this
							.getDirectorysByParentId(oldDirectory.getAppId(), oldDirectory.getUserId(), oldDirectory.getPid())
							: getDirectorysForRoot(oldDirectory.getAppId(), oldDirectory.getUserId(), oldDirectory.getTypeId());
					assertDuplicateName(directorys, directory);

					// 确保如下数据不能修改
					directory.setAppId(oldDirectory.getAppId());
					directory.setUserId(oldDirectory.getUserId());
					directory.setTypeId(oldDirectory.getTypeId());
					directory.setPid(oldDirectory.getPid());
					directory.setNumberCode(oldDirectory.getNumberCode());

					directory.setUpdateAt(System.currentTimeMillis());
					return this.updateEntity(directory);
				} else {
					logger.info("don't find the old Directory entity by id " + directory.getId());
				}
			} else {
				logger.info("update diectory entity is null");
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}
		return false;
	}

	@Override
	public boolean moveDirectoryToDirectory(Long appId, Long userId, Long directoryId, Long toDirectoryId) throws DirectoryServiceException {
		// check parameter
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryIdForDirectory(directoryId);
		ServiceError.assertDirectoryIdForDirectory(toDirectoryId);

		try {
			Directory targetDirectory = this.getEntity(directoryId);
			if (targetDirectory == null) {
				StringBuffer sb = new StringBuffer();
				sb.append("don't find the from Directory by id " + directoryId);
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
			}

			Directory to = this.getEntity(toDirectoryId);
			if (to == null) {
				StringBuffer sb = new StringBuffer();
				sb.append("don't find the to Directory by id " + toDirectoryId);
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
			}

			// 检查是不是同一个人，同一个应用，同一个分类
			if (ObjectUtils.equals(targetDirectory.getUserId(), to.getUserId()) && ObjectUtils.equals(targetDirectory.getAppId(), to.getAppId()) && ObjectUtils.equals(targetDirectory.getTypeId(), to.getTypeId())) {
				targetDirectory.setPid(toDirectoryId);
				String numbreCode = getParentNumberCode(to);
				targetDirectory.setNumberCode(numbreCode+"-" + targetDirectory); //更新索要
				return this.updateEntity(targetDirectory);
			} else {
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directory");// 移动的不是自己的目录
			}

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}

	}

	@Override
	public Directory getDirectory(Long appId, Long userId, Long id) throws DirectoryServiceException {
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		if (id == null) {
			return null;
		}
		Directory directory;
		try {
			directory = getEntity(id);
			if (directory != null) {
				if (ObjectUtils.equals(directory.getAppId(), appId) && ObjectUtils.equals(directory.getUserId(), userId)) {
					return directory;
				} else {
					throw new DirectoryServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directory");// 查询的不是指定应用和指定用户的Directory
				}
			} else {
				return null;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}
	}

	/**
	 * 最多返回500
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @return
	 * @throws DirectoryServiceException
	 */
	@Override
	public List<Directory> getDirectorysByParentId(Long appId, Long userId, Long pId) throws DirectoryServiceException {
		// check parameters
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryIdForDirectory(pId);

		Directory parentDirectory = null;
		try {
			parentDirectory = this.getEntity(pId);
			if (parentDirectory != null) { // 检查父对象是否存在
				if (ObjectUtils.equals(parentDirectory.getAppId(), appId) && ObjectUtils.equals(parentDirectory.getUserId(), userId)) {
					return getSubEntitys(LIST_DIRECTORY_PID_ID, 0, 500, pId);
				} else {
					throw new DirectoryServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directory");// 移动的不是自己的目录
				}
			} else {
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, "don't find the parent directory by id " + pId);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}
	}

	@Override
	public int countDirectorysByParentId(Long appId, Long userId, Long pId) throws DirectoryServiceException {
		// check parameters
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryIdForDirectory(pId);

		Directory parentDirectory = null;
		try {
			parentDirectory = this.getEntity(pId);
			if (parentDirectory != null) { // 检查父对象是否存在
				if (ObjectUtils.equals(parentDirectory.getAppId(), appId) && ObjectUtils.equals(parentDirectory.getUserId(), userId)) {
					return countEntitys(LIST_DIRECTORY_PID_ID, pId);
				} else {
					throw new DirectoryServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own directory");// 移动的不是自己的目录
				}
			} else {
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, "don't find the parent directory by id " + pId);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}
	}

	@Override
	public List<Directory> getDirectorysForRoot(Long appId, Long userId, Long directoryTypeId) throws DirectoryServiceException {
		// check parameter
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryTypeIdForDirectory(directoryTypeId);

		try {
			List<Directory> directories = this.getSubEntitys(LIST_DIRECTORY_ID_APPID_USERID_TYPEID_PID, 0, 500, appId, userId, directoryTypeId, 0l);
			return directories;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}

	}

	@Override
	public int countDirectorysForRoot(Long appId, Long userId, Long directoryTypeId) throws DirectoryServiceException {
		// check parameter
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryTypeIdForDirectory(directoryTypeId);

		try {
			return this.countEntitys(LIST_DIRECTORY_ID_APPID_USERID_TYPEID_PID, 0, 500, appId, userId, directoryTypeId, 0l);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}
	}

	/**
	 * 
	 * @param directory
	 * @return
	 */
	private String getParentNumberCode(Directory directory) {
		String[] parentIds = null;
		if (directory != null) {
			parentIds = directory.getNumberCode().split("-");
			if (parentIds != null && parentIds.length >= 4) {
				parentIds = Arrays.copyOfRange(parentIds, parentIds.length - 4, parentIds.length);
			}
		}
		String parentNumberCode = parentIds == null ? "" : StringUtils.join(parentIds, "-");
		return parentNumberCode;

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

	private void assertDuplicateName(List<Directory> directorys, Directory directory) throws DirectoryServiceException {
		if (!CollectionUtils.isEmpty(directorys)) {
			for (Directory dir : directorys) {
				if (dir != null && !ObjectUtils.equals(dir.getId(), directory.getId()) && ObjectUtils.equals(dir.getName(), directory.getName())) {
					throw new DirectoryServiceException(ServiceError.ERROR_DUPLICATE, "the " + directory.getName() + " already exists");
				}
			}
		}
	}
}
