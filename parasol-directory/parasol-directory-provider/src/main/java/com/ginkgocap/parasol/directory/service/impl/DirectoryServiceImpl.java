package com.ginkgocap.parasol.directory.service.impl;

import java.sql.SQLException;
import java.util.*;

import com.ginkgocap.parasol.directory.dao.DirectoryDao;
import com.ginkgocap.parasol.directory.model.Page;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.util.PinyinComparatorList4ObjectName;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.CollectionUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectoryTypeService;

import javax.annotation.Resource;


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
	private static int len_name = 200;

	private static String LIST_DIRECTORY_PID_ID = "List_Directory_Id_Pid"; // 查询子节点
	private static String LIST_DIRECTORY_ID_APPID_USERID_TYPEID_PID = "List_Directory_Id_AppId_UserId_TypeId_Pid"; // 查询一个应用的分类根目录
	private static String List_Directory_By_Name = "List_Directory_By_Name";
	private static String LIST_DIRECTORY_TREE_APPID_USERID_TYPEID_PID = "List_Directory_Tree_AppId_UserId_TypeId_Pid"; //查询树形目录不包括根目录
	private static String LIST_DIRECTORY_ID_APPID_USERID_TYPEID = "List_Directory_Id_AppId_UserId_TypeId"; //查询树形根目录
	private static String LIST_DIRECTORY_ID_ALL = "List_Directory_Id_All"; //查询所有目录
	private static String LIST_DIRECTORY_SUBTREE_MAXORDERNO = "List_Directory_SubTree_MaxOrderNo"; //子目录下最大级别目录
	// 以下是 返回 directorySource
	//private static String LIST_DIRECTORYSOURCE_ID_USERID_TYPEID = "List_DirectorySource_Id_UserId_TypeId";
	//private static String LIST_DIRECTORYSOURCE_ID_DIRECTORYID = "List_DirectorySource_Id_DirectoryId";
	/*@Autowired
	private DirectoryTypeService directoryTypeService;*/

	@Resource
	private DirectorySourceService directorySourceService;

	@Autowired
	private DirectoryDao myDirectoryDao;

	@Resource
	private MongoTemplate mongoTemplate;

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
//		// @formatter:on
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
			throw new DirectoryServiceException(ServiceError.ERROR_USER_EXIST, "please set userId property");
		} else {
			// TODO: 检查用户是否存在
			logger.info("Please implement check user exist condition");
		}

		// 2. 检查父节点和现在的节点情况
		Directory parentDirectory = null;
		if (pId != null && pId != 0) {
			parentDirectory = this.getDirectory(directory.getAppId(), directory.getUserId(), pId);
			if (parentDirectory == null) {
				StringBuffer sb = new StringBuffer();
				sb.append("don't find the parent Directory by appid is ").append(directory.getAppId()).append(" userId is ").append(directory.getUserId());
				sb.append(" or directory type mismatched parent directory type");
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
			} else {
				directory.setPid(pId);
				directory.setTypeId(parentDirectory.getTypeId());
			}
		}

		// 3. 检查有没有重名根目录
		/*List<Directory> directorys = null;
		if (pId != null && pId != 0) { // 创建非根节点
			directorys = getDirectorysByParentId(directory.getAppId(), directory.getUserId(), pId);
		} else { // 创建根节点
			directorys = getDirectorysForRoot(directory.getAppId(), directory.getUserId(), directory.getTypeId());
		}
		assertDuplicateName(directorys, directory);*/

		// 4.创建
		try {
			logger.info("-------saveEntity start time : " + System.currentTimeMillis());
			directory.setPid(pId);
			directory.setUpdateAt(System.currentTimeMillis());
			Long id = (Long) this.saveEntity(directory);
			logger.info("-------saveEntity start time : " + System.currentTimeMillis());
			if (id > 0) {
				directory.setId(id);
				String parentNumberCode = getParentNumberCode(parentDirectory);
				if (StringUtils.isEmpty(parentNumberCode)) {
					directory.setNumberCode(parentNumberCode + id);
				} else {
					directory.setNumberCode(parentNumberCode + "-" + id);
				}
				logger.info("-------updateEntity start time : " + System.currentTimeMillis());
				this.updateEntity(directory);
				logger.info("-------updateEntity start time : " + System.currentTimeMillis());
			}
			return id;
		} catch (Throwable e) {
			List<Directory> directorys = null;
			try {
				if (pId != null && pId != 0) { // 创建非根节点
					directorys = getDirectorysByParentId(directory.getAppId(), directory.getUserId(), pId);
				} else { // 创建根节点
					directorys = getDirectorysForRoot(directory.getAppId(), directory.getUserId(), directory.getTypeId());
				}
			} catch (Exception ex) {
				e.printStackTrace();
				throw new DirectoryServiceException(e);
			}
			// 不是数据库连接问题，就是数据重复sqlException，返回-1判断
			return -1l;
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

			// 删除自己 并删除目录下资源(不是真正删除资源，只是删除目录和资源的关系)
			try {
				directorySourceService.removeDirectorySourcesByDirId(directoryId);
			} catch (Exception e) {
				logger.error("invoke directorySourceService failed! method : [removeDirectorySourcesBySourceId] directoryId = " + directoryId);
			}
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
							logger.error(directory.getName() + "  length can not be more than " + len_name);
							return false;
							//throw new DirectoryServiceException(ServiceError.ERROR_NAME_LIMIT, directory.getName() + "  length can not be more than " + len_name);
						}
					}

					/*// 检查重名
					List<Directory> directorys = oldDirectory.getPid() != 0 ? this
							.getDirectorysByParentId(oldDirectory.getAppId(), oldDirectory.getUserId(), oldDirectory.getPid())
							: getDirectorysForRoot(oldDirectory.getAppId(), oldDirectory.getUserId(), oldDirectory.getTypeId());
					assertDuplicateName(directorys, directory);*/

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
				logger.info("update directory entity is null");
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}
		return false;
	}

	@Override
	public boolean updateDirectory(Directory directory) {

		boolean flag = false;
		try {
			flag = this.updateEntity(directory);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public InterfaceResult moveDirectoryAndReturnTree(Long appId, Long userId, Long directoryId, Long toDirectoryId) throws DirectoryServiceException {

		InterfaceResult result = null;
		try {
			result = this.moveDirectoryToDirectory(appId, userId, directoryId, toDirectoryId);
			//Thread.sleep(5000);
			if (CommonResultCode.SUCCESS.getCode().equals(result.getNotification().getNotifCode())) {
				Directory directory = this.getEntity(directoryId);
				result = this.returnMyDirectoriesTreeList(appId, userId, directory.getTypeId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public InterfaceResult moveDirectoryToDirectory(Long appId, Long userId, Long directoryId, Long toDirectoryId) throws DirectoryServiceException {
		// check parameter
		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryIdForDirectory(directoryId);
		ServiceError.assertDirectoryIdForDirectory(toDirectoryId);

		InterfaceResult result = null;
		Directory targetDirectory = null;
		Directory toDirectory = null;
		int toOrderNo = 0;
		try {

			targetDirectory = this.getDirectory(appId, userId, directoryId);
			if (toDirectoryId != 0) {
				toDirectory = this.getDirectory(appId, userId, toDirectoryId);
			}
			if (targetDirectory == null || (toDirectoryId != 0 && toDirectory == null)) {
				result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION);
				result.getNotification().setNotifInfo("当前目录或目标目录已不存在");
				return result;
			}
			if (toDirectory != null) {
				toOrderNo = toDirectory.getOrderNo();
			}
			long typeId = targetDirectory.getTypeId();
			int orderNo = targetDirectory.getOrderNo();
			Directory subTreeMaxDirectory = this.getSubTreeMaxDirectory(appId, userId, directoryId, typeId);
			if (subTreeMaxDirectory != null) {
				int subOrderNo = subTreeMaxDirectory.getOrderNo();
				result = checkOrderNo(subOrderNo, orderNo, toOrderNo);
				if (result != null)
					return result;
			} else {
				result = checkOrderNo(0, 0, toOrderNo);
				if (result != null)
					return result;
			}

			String parentNumberCode = getParentNumberCode(toDirectory);
			long targetPid = targetDirectory.getPid();
			long type = targetDirectory.getTypeId();
			Directory parent = this.getEntity(targetPid);
			if (parent == null) {
				parent = new Directory();
				parent.setOrderNo(0); // 根目录是 0 级
			}
			// 移动 targetDirectory 目录 到 toDirectory
			targetDirectory.setPid(toDirectoryId);
			targetDirectory.setOrderNo(toDirectory == null ? 1 : toDirectory.getOrderNo() + 1);
			String numberCode = "".equals(parentNumberCode) ? directoryId + "" : parentNumberCode + "-" + directoryId;
			targetDirectory.setNumberCode(numberCode);
			boolean flag = this.updateEntity(targetDirectory);
			if (flag) {
				List<Directory> treeList = this.getTreeDirectorysByParentId(appId, userId, directoryId, type);

				if (CollectionUtils.isNotEmpty(treeList)) {
					Map<Long, Directory> map = new HashMap<Long, Directory>(treeList.size() + 1);
					map.put(directoryId, targetDirectory);
					for (Directory directory : treeList) {
						if (directory == null)
							continue;
						logger.info("directoryId :[" + directory.getId() + "]" + "start.............");
						long pid = directory.getPid();
						Directory parentDirectory = map.get(pid);
						if (parentDirectory == null) {
							logger.error("parentDirectory is not present");
						} else {
							directory.setOrderNo(parentDirectory.getOrderNo() + 1);
							directory.setNumberCode(parentDirectory.getNumberCode() + "-" + directory.getId());
							logger.info("directoryId :[" + directory.getId() + "]" + directory.getOrderNo() + directory.getNumberCode());
							logger.info("directoryId :[" + directory.getId() + "]" + "end.............");
						}
						map.put(directory.getId(), directory);
					}
					flag = this.updateEntitys(treeList);
				}
				if (flag) {
					result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS, flag);
					return result;
				}
			}
			result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION, flag);
			return result;

			// TODO 还的检查不能移动到自己的子目录下
			// TODO 不能存在相同的文件名字
			// TODO 检查是不是同一个分类

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

	@Override
	public Directory getDirectory(Long appId, Long id) {

		Directory directory = null;
		try {
			directory = (Directory) this.getMapId("GET_DIRECTORY_ID", appId, id);
		} catch (Exception e) {
			logger.error("invoke getMapId method failed! id = " + id);
		}
		return directory;
	}

	@Override
	public List<Directory> getDirectoryList(Long appId, Long userId, List<Long> ids) throws DirectoryServiceException {
		ServiceError.assertAppIdForDirectory(appId);
		if (ids == null || ids.size() <= 0) {
			return null;
		}
		List<Directory> directoryList = null;
		try {
			directoryList = getEntityByIds(ids);
			List<Directory> result = new ArrayList<Directory>(directoryList.size());
			if (CollectionUtils.isNotEmpty(directoryList)) {
				for (Directory directory : directoryList) {
					//userId = -1,For the case: if get others public resource that contain these tags;
					if (userId.intValue() == -1 || (ObjectUtils.equals(directory.getAppId(), appId) && ObjectUtils.equals(directory.getUserId(), userId))) {
						result.add(directory);
					}
				}
			}
			return result;
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

	@Override
	public Page<Directory> getDirectoryName(Long userId, String name, long typeId, int pageNo,int pageSize) throws DirectoryServiceException {
		ServiceError.assertUserIdForDirectory(userId);
		try {
			Page<Directory> page = new Page<Directory>();
			name = "%" + name + "%";
			List<Directory> directoryList = this.getSubEntitys(List_Directory_By_Name, pageNo, pageSize, userId, name, typeId);
			int count = this.countEntitys(List_Directory_By_Name,userId, name);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setTotalCount(count);

			if (CollectionUtils.isNotEmpty(directoryList)) {
				for (Directory directory : directoryList){
					if (directory.getPid() != 0) {
						String numberCode = directory.getNumberCode();
						String[] subNumberCode = numberCode.split("-");
						StringBuffer sBuffer = new StringBuffer();

						for (int i =0; i < subNumberCode.length ; i++) {
							long id = 0L;
							try {
								id = Long.valueOf(subNumberCode[i]);
							} catch (Exception ex) {
								logger.error("parser string to number failed. number: " + subNumberCode[i] +"error: "+ex.getMessage());
								continue;
							}
							Directory dir = this.getEntity(id);
							String name2 = dir.getName();
							sBuffer.append(name2).append("/");
							//i 应该 = 目录最大级别 稍后修改
							if (i == directory.getOrderNo()) {
								if (subNumberCode.length > 4) {
									sBuffer.append(".../").append(directory.getName()).append("/");
								}
								else if (id == directory.getId()) {
									break;
								} else {
									sBuffer.append(directory.getName()).append("/");
								}
								break;
							}
						}
						String newName = sBuffer.toString().substring(0,sBuffer.length()-1);
						directory.setName(newName);
					}
				}
			}
			page.setList(directoryList);
			return page;
		}catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}

	}

	@Override
	public List<Directory> getAllDirectory(final int page, final int size) {

		int count = 0;
		try {
			count = countEntitys(LIST_DIRECTORY_ID_ALL, 1l);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		final int start = page * size;
		if (start >= count) {
			logger.info("start exceed to end, so return null. page: " + page + " size: " + size);
			return null;
		}
		try {
			return this.getSubEntitys(LIST_DIRECTORY_ID_ALL, start, size, 1l);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
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
			if (parentIds != null && parentIds.length >= 19) {
				parentIds = Arrays.copyOfRange(parentIds, parentIds.length - 19, parentIds.length);
			}
		}
		String parentNumberCode = parentIds == null ? "" : StringUtils.join(parentIds, "-");
		return parentNumberCode;

	}

	@Override
	public List<Directory> getTreeDirectorysByParentId(long appId, long userId, long pid, long typeId) throws DirectoryServiceException {

		ServiceError.assertAppIdForDirectory(appId);
		ServiceError.assertUserIdForDirectory(userId);

		try {
			return this.getEntitys(LIST_DIRECTORY_TREE_APPID_USERID_TYPEID_PID,  appId, userId, pid + "-%", "%-" + pid + "-%", typeId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}

	}

	@Override
	public List<Directory> getDirectoryListByUserIdType(long appId, long userId, long typeId) throws DirectoryServiceException {

		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryTypeIdForDirectory(typeId);

		try {
			return myDirectoryDao.selectMyTreeDirectories(appId, userId, typeId);
			//return this.getEntitys(LIST_DIRECTORY_ID_APPID_USERID_TYPEID, appId, userId, typeId);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new DirectoryServiceException(e);
		}
	}

	public int getMyDirectoriesCount(long loginAppId, long userId, long typeId) throws DirectoryServiceException {

		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryTypeIdForDirectory(typeId);

		try {
			return this.countEntitys(LIST_DIRECTORY_ID_APPID_USERID_TYPEID, loginAppId, userId, typeId);
		} catch (BaseServiceException e) {
			logger.error(e.getMessage());
			throw new DirectoryServiceException(e);
		}
	}

	@Override
	public int getMySubDirectoriesCount(long loginAppId, long userId, long pid, long typeId) throws DirectoryServiceException {

		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryTypeIdForDirectory(typeId);

		try {
			return this.countEntitys(LIST_DIRECTORY_TREE_APPID_USERID_TYPEID_PID, loginAppId, userId, pid + "-%", "%-" + pid + "-%", typeId);
		} catch (BaseServiceException e) {
			logger.error(e.getMessage());
			throw new DirectoryServiceException(e);
		}
	}
	@Override
	public Directory getSubTreeMaxDirectory(long appId, long userId, long directory, long typeId) throws DirectoryServiceException {

		ServiceError.assertUserIdForDirectory(userId);
		ServiceError.assertDirectoryTypeIdForDirectory(typeId);

		try {
			Long id = (Long)this.getMapId(LIST_DIRECTORY_SUBTREE_MAXORDERNO, appId, userId, directory + "-%", "%-" + directory + "-%", typeId);
			return id == null ? null : this.getEntity(id);
		} catch (BaseServiceException e) {
			throw new DirectoryServiceException(e);
		}

	}

	/**
	 * 检查目录级别不能超过20级
	 */
	private InterfaceResult checkOrderNo(int subOrderNo, int orderNo, int toOrderNo) {

		InterfaceResult result = null;
		int level = subOrderNo - orderNo + toOrderNo + 1;
		if (level > 20) {
			result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			result.getNotification().setNotifInfo("目录级别不能超过20级哦");
		}
		return result;
	}
	/**
	 * 返回树结构
	 * @param loginAppId
	 * @param loginUserId
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	private InterfaceResult returnMyDirectoriesTreeList(long loginAppId, long loginUserId, long typeId) throws Exception{

		List<Directory> directoryList = new ArrayList<Directory>();
		List<Directory> directories = this.getDirectoryListByUserIdType(loginAppId, loginUserId, typeId);
		int total = this.getMyDirectoriesCount(loginAppId, loginUserId, typeId);
		int totalSourceCount = directorySourceService.getMyDirectoriesSouceCount(loginAppId, loginUserId, typeId);
		Map<Long, Directory> idMap = new HashMap<Long, Directory>();
		for (Directory direc: directories) {
			// 在移动目录（修改目录结构） 之后 目录的 childDirectory 会有数据缓存 设为null 则无缓存
			if (CollectionUtils.isNotEmpty(direc.getChildDirectory())) {
				direc.setChildDirectory(null);
			}
			long dirId = direc.getId();
			long pid = direc.getPid();
			//根目录下所有目录
			if (pid == 0) {
				directoryList.add(direc);
			}
			idMap.put(dirId, direc);
		}
		for (Map.Entry<Long, Directory> map : idMap.entrySet()) {
			Directory dire = map.getValue();
			long pid = dire.getPid();
			Directory parent = idMap.get(pid);
			if (parent != null) {
				logger.info("parent : [" + parent.getName() + "********************]");
				parent.addChildList(dire);
				logger.info("child list [: " + parent.getChildDirectory() + "------------------]");
				// 将目录按照拼音 自定义排序
				Collections.sort(parent.getChildDirectory(), new PinyinComparatorList4ObjectName());
			}
		}
		// 将目录按照拼音 自定义排序
		Collections.sort(directoryList, new PinyinComparatorList4ObjectName());
		// 2.转成框架数据
		Map<String, Object> result = new HashMap();
		result.put("totalCount", total);
		result.put("list", directoryList);
		result.put("sourceCount", totalSourceCount);
		InterfaceResult interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		interfaceResult.setResponseData(result);
		return interfaceResult;
	}

	@Override
	public boolean subtractSourceCountByDirId(long userId, long appId, long id) {

		Directory directory = null;
		try {
			directory = this.getEntity(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		directory.subtractSourceCount(directory.getSourceCount());
		return this.updateDirectory(directory);
	}

	@Override
	public boolean subtractSourceCountByDirectoryIds(long userId, long appId, List<Long> ids) {

		List<Directory> directories = null;
		try {
			directories = this.getDirectoryList(appId, userId, ids);
			if (CollectionUtils.isNotEmpty(directories)) {
				for (Directory directory : directories) {
					if (directory == null)
						continue;
					directory.subtractSourceCount(directory.getSourceCount());
				}
				return this.updateEntitys(directories);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Directory> getListByPId(long appId, int page, int size, long pid) {

		int count = 0;
		try {
			count = countEntitys(LIST_DIRECTORY_PID_ID, pid);
		} catch (Exception e) {
			logger.error("invoke countEntitys method failed! sql : LIST_DIRECTORY_PID_ID,");
		}
		int start = page * size;
		if (count < start) {
			logger.info("because start > count, so return null");
			return null;
		}
		List<Directory> directoryList = null;
		try {
			directoryList = getSubEntitys(LIST_DIRECTORY_PID_ID, start, size, pid);
		} catch (Exception e) {
			logger.error("invoke getSubEntitys method failed! sql : LIST_DIRECTORY_PID_ID, pid = " + pid);
		}
		return directoryList;
	}

	@Override
	public List<Directory> getDirectoriesTreeByCache(long userId, long typeId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		query.addCriteria(Criteria.where("typeId").is(typeId));
		return mongoTemplate.find(query, Directory.class, "directory");
	}

	@Override
	public void saveDirectoriesTreeByCache(List<Directory> directoryList) {

		mongoTemplate.insert(directoryList, "directory");
	}

	@Override
	public void saveDirectoryByCache(Directory directory) {

		mongoTemplate.insert(directory);
	}
}
