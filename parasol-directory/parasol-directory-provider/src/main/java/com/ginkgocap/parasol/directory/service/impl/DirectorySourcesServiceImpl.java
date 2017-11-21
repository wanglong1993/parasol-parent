package com.ginkgocap.parasol.directory.service.impl;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;

/**
 * 操作DirectorySource
 * 
 * @author allenshen
 * @date 2015年11月27日
 * @time 上午9:11:50
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("directorySourceService")
public class DirectorySourcesServiceImpl extends BaseService<DirectorySource> implements DirectorySourceService {
	private static int LEN_SOURCE_TITLE = 140;
	private static Logger logger = Logger.getLogger(DirectorySourcesServiceImpl.class);
	private static String LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID = "List_DirectorySources_Id_userId_appId_sourceType_sourceId";
	private static String LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_DIRECTORYID = "List_DirectorySources_Id_userId_appId_sourceType_directoryId";
	private static String LIST_DIRECTORYSOURCES_ID_APPID_SOURCETYPE = "List_DirectorySources_Id_appId_sourceType";
	private static String LIST_DIRECTORYSOURCE_ID_DIRECTORYID = "List_DirectorySource_Id_DirectoryId";
	private static String MAP_DIRECTORYSOURCE_ID_USERID_DIRECTORYID_APPID_SOURCETYPE_SOURICEID = "Map_DirectorySource_Id_UserId_DirectoryId_AppId_SourceType_SourceId";
	private static String LIST_DIRECTORYSOURCE_ID_USERID_TYPEID = "List_DirectorySource_Id_UserId_TypeId";

	@Autowired
	private DirectoryService directoryService;

	@Override
	public Long createDirectorySources(DirectorySource source) throws DirectorySourceServiceException {
		ServiceError.assertForDirectorySource(source);

		Long appId = source.getAppId(); // 资源的应用ID
		// TODO : please implements check App exist?
		logger.info("please implements check App exist?");
		Long userId = source.getUserId(); // 创建Source的人
		Long directoryId = source.getDirectoryId(); // 目录Id
		Integer sourceType = source.getSourceType(); // 资源类型
		Long sourceId = source.getSourceId(); // 资源ID
		ServiceError.assertPropertiesHaveValue(source, new String[] { "appId", "userId", "directoryId", "sourceType", "sourceId" });
		// 检查目录情况
		try {
			Directory directory = directoryService.getDirectory(appId, userId, directoryId);
			if (directory == null) { // 目录不存在
				throw new DirectorySourceServiceException(ServiceError.ERROR_NOT_FOUND, "Don't find then Directory entity by id [" + directoryId + "]");
			}
			directory.addSourceCount(directory.getSourceCount());
			boolean b = directoryService.updateDirectory(directory);
			if (!b)
				throw new DirectorySourceServiceException(ServiceError.ERROR_DIRECTORY_UPDATE, "update directory sourceCount failed [" + directoryId + "]");
			DirectorySource existSource = this.getDirectorySource(userId, directoryId, appId, sourceType, sourceId);
			if (existSource != null) { // 已经存在
				StringBuilder sb = new StringBuilder();
				sb.append("userId=").append(userId).append(" and ");
				sb.append("directoryId=").append(directoryId).append(" and ");
				sb.append("appId=").append(appId).append(" and ");
				sb.append("sourceType:=").append(sourceType).append(" and ");
				sb.append("sourceId=").append(sourceId).append(";");
				throw new DirectorySourceServiceException(ServiceError.ERROR_OBJECT_EXIST, "the DirectorySource to be created already exist!  " + existSource.getId()
						+ " entity by [" + sb.toString() + "]");
			}
			source.setCreateAt(System.currentTimeMillis());
			// TODO : 过滤敏感词?
			logger.info("please implements 过滤敏感词?");
			source.setSourceTitle(StringUtils.trim(source.getSourceTitle()));
			if (source.getSourceTitle() != null && source.getSourceTitle().length() > LEN_SOURCE_TITLE) {
				throw new DirectorySourceServiceException(ServiceError.ERROR_NAME_LIMIT, source.getSourceTitle() + "  length can not be more than " + LEN_SOURCE_TITLE);
			}
			source.setSourceData(StringUtils.trim(source.getSourceData()));
			if (source.getSourceData() != null && source.getSourceData().length() > LEN_SOURCE_TITLE) {
				throw new DirectorySourceServiceException(ServiceError.ERROR_NAME_LIMIT, source.getSourceData() + "  length can not be more than " + LEN_SOURCE_TITLE);
			}
			return (Long) this.saveEntity(source);
		} catch (DirectoryServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public boolean removeDirectorySourcesByDireIds(List<Long> ids) throws DirectorySourceServiceException{
		try {
			return this.deleteEntityByIds(ids);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public boolean removeDirectorySources(Long appId, Long userId, Long id) throws DirectorySourceServiceException {
		try {
			DirectorySource ds = this.getDirectorySourceById(appId, id);
			if (ds != null && ObjectUtils.equals(appId, ds.getAppId()) && ObjectUtils.equals(userId, ds.getUserId())) {
				return this.deleteEntity(id);
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("appId=").append(appId);
				sb.append(" and ").append("userId=").append(userId);
				sb.append(" and ").append("id=").append(id);
				throw new DirectorySourceServiceException(ServiceError.ERROR_NOT_FOUND, "don't find the DirectorySource by [ " + sb.toString() + " ]");
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public boolean removeDirectorySourceByIds(List<Long> ids) throws Exception {

		return this.deleteEntityByIds(ids);
	}

	@Override
	public boolean removeDirectorySourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws DirectorySourceServiceException {

		boolean b = false;
		List<DirectorySource> directorySourceList = null;
		List<Long> ids = null;
		List<Long> dirIds = new ArrayList<Long>();
		try {
			ids = this.getIds(LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID, userId, appId, sourceType, sourceId);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		try {
			directorySourceList = this.getEntityByIds(ids);
			for (DirectorySource directorySource : directorySourceList) {
				if (directorySource == null)
					continue;
				long directoryId = directorySource.getDirectoryId();
				dirIds.add(directoryId);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		try {
			b = directoryService.subtractSourceCountByDirectoryIds(userId, appId, dirIds);
		} catch (Exception e) {
			logger.error("invoke directory service failed, method : [ subtractSourceCountByDirectoryIds ]. sourceId = " + sourceId);
		}
		if (!b) {
			logger.error("subtractSourceCountByDirectoryIds method : failed!" );
		}
		try {
			return this.deleteEntityByIds(ids);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<DirectorySource> getDirectorySourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws DirectorySourceServiceException {
		try {
			return this.getEntitys(LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID, userId, appId, sourceType, sourceId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public List<Long> getDirectoryIdsBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws DirectorySourceServiceException {
		try {
			return this.getIds(LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID, userId, appId, sourceType, sourceId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public boolean removeDirectorySourcesByDirId(long directoryId) {

		int count = 0;
		try {
			count = this.deleteList(LIST_DIRECTORYSOURCE_ID_DIRECTORYID, directoryId);
			logger.info("deleteList {LIST_DIRECTORYSOURCE_ID_DIRECTORYID} count : " + count + "directoryId = " + directoryId);
			return count >= 0;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
		}
		return false;
	}

	@Override
	public List<DirectorySource> getSourcesByDirectoryIdAndSourceType(int start,int size,Object... parameters) throws DirectorySourceServiceException 
	{
		try {
			return this.getSubEntitys(LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_DIRECTORYID, start, size, parameters);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public List<DirectorySource> getSourcesBySourceType(int page, int size, byte sourceType) throws DirectorySourceServiceException
	{
		try {
			final int start = page * size;
			return this.getSubEntitys(LIST_DIRECTORYSOURCES_ID_APPID_SOURCETYPE, start, size, sourceType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public boolean moveDirectorySources(long userId, long appId, Long directoryId, Long[] ids) throws DirectorySourceServiceException {

		ServiceError.assertAppIdForDirectorySource(appId);
		ServiceError.assertUserIdForDirectorySource(userId);
		ServiceError.assertDirectoryIdForDirectorySource(directoryId);

		try {
			Directory directory = directoryService.getDirectory(appId, userId, directoryId);
			if (directory == null) {
				StringBuilder sb = new StringBuilder();
				sb.append("appId=").append(appId);
				sb.append(" and ").append("userId=").append(userId);
				sb.append(" and ").append("id=").append(directoryId);
				throw new DirectorySourceServiceException(ServiceError.ERROR_NOT_FOUND, "don't find the directory by [" + sb.toString() + "]");
			}

			if (!ArrayUtils.isEmpty(ids)) {
				for (Long id : ids) {
					if (id != null && id > 0) {
						DirectorySource ds = this.getDirectorySourceById(appId, id);
						if (ds != null && ObjectUtils.equals(ds.getAppId(), appId) && ObjectUtils.equals(ds.getUserId(), userId)) {
							ds.setDirectoryId(directoryId);
							this.updateEntity(ds);
						} else {
							if (ds != null) {
								logger.error("Operation of the non own directory source");
							} else {
								logger.error("dont find the DirectorySource by [ userId=" + userId + " and id=" + id + "]");
							}
						}

					}
				}
			}
		} catch (DirectoryServiceException e) {
			throw new DirectorySourceServiceException(e);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			String msg = getSqlErrorMessage(e);
			if (msg != null) {
				throw new DirectorySourceServiceException(ServiceError.ERROR_SQL, msg);
			} else {
				throw new DirectorySourceServiceException(e);

			}
		}
		return true;
	}

	@Override
	public boolean updateDirectorySource(DirectorySource source) throws DirectorySourceServiceException {
		ServiceError.assertForDirectorySource(source);

		Long appId = source.getAppId(); // 资源的应用ID
		ServiceError.assertAppIdForDirectorySource(appId);
		// TODO : please implements check App exist?
		logger.info("please implements check App exist?");

		DirectorySource old = this.getDirectorySourceById(appId, source.getId());
		ServiceError.assertModifyFields(old, source, new String[] { "appId", "userId", "sourceId", "sourceType", "directoryId" });
		source.setSourceTitle(StringUtils.trim(source.getSourceTitle()));
		if (source.getSourceTitle() != null && source.getSourceTitle().length() > LEN_SOURCE_TITLE) {
			throw new DirectorySourceServiceException(ServiceError.ERROR_NAME_LIMIT, source.getSourceTitle() + "  length can not be more than " + LEN_SOURCE_TITLE);
		}
		source.setSourceData(StringUtils.trim(source.getSourceData()));
		if (source.getSourceData() != null && source.getSourceData().length() > LEN_SOURCE_TITLE) {
			throw new DirectorySourceServiceException(ServiceError.ERROR_NAME_LIMIT, source.getSourceData() + "  length can not be more than " + LEN_SOURCE_TITLE);
		}
		try {
			return this.updateEntity(source);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e.getMessage());
		}
	}

	@Override
	public DirectorySource getDirectorySourceById(Long appId, Long id) throws DirectorySourceServiceException {
		try {
			ServiceError.assertAppIdForDirectorySource(appId);
			DirectorySource ds = this.getEntity(id);
			if (ds != null && ObjectUtils.equals(ds.getAppId(), appId)) {
				return ds;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	@Override
	public List<DirectorySource> getDirectorySourcesByDirectoryId(Long appId, Long userId, Long directoryId) throws DirectorySourceServiceException {
		try {
			Directory directory = directoryService.getDirectory(appId, userId, directoryId);
			if (directory == null) {
				StringBuilder builder = new StringBuilder();
				builder.append("appId=").append(appId);
				builder.append(" and ").append("userId=").append(userId);
				builder.append(" and ").append("id=").append(directoryId);
				throw new DirectoryServiceException(ServiceError.ERROR_NOT_FOUND, "don't find directory by : " + builder.toString());
			}
			List<DirectorySource> directorySources = this.getEntitys(LIST_DIRECTORYSOURCE_ID_DIRECTORYID, directoryId);
			return directorySources;
		} catch (DirectoryServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}

	}

	@Override
	public int countDirectorySourcesByDirectoryId(Long appId, Long userId, Long directoryId) throws DirectorySourceServiceException {
		try {
			return this.countEntitys(LIST_DIRECTORYSOURCE_ID_DIRECTORYID, directoryId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public int getMyDirectoriesSouceCount(Long appId, Long userId, Long typeId) throws DirectorySourceServiceException {
		try {
			return this.countEntitys(LIST_DIRECTORYSOURCE_ID_USERID_TYPEID, appId, userId, typeId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}
	}

	@Override
	public DirectorySource getDirectorySource(Long userId, Long directoryId, Long sourceAppId, Integer sourceType, Long souriceId) throws DirectorySourceServiceException {
		try {
			Long id = (Long) this.getMapId(MAP_DIRECTORYSOURCE_ID_USERID_DIRECTORYID_APPID_SOURCETYPE_SOURICEID, userId, directoryId, sourceAppId, sourceType, souriceId);
			if (id != null) {
				return this.getDirectorySourceById(sourceAppId, id);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new DirectorySourceServiceException(e);
		}

		return null;
	}

	/**
	 *快捷更新资源目录（知识、需求使用）
	 * @return
	 * @throws DirectorySourceServiceException
	 */
	@Override
	public boolean updateDiresources(Long appId, Long userId, Long sourceId, Long sourceType, List<Long> direIds, String sourceTitle) throws DirectorySourceServiceException{

		List<DirectorySource> newDireSourceList = new ArrayList<DirectorySource>();
		List<DirectorySource> direSourceList = null;
		try {
            long newSourceType = sourceType;
            direSourceList = this.getDirectorySourcesBySourceId(userId,appId,(int)newSourceType,sourceId);
            if (CollectionUtils.isEmpty(direSourceList)) {
                for (Long direId : direIds) {
                    if (direId != null) {
                        DirectorySource dirSource = newDirecotrySource(userId, sourceId, sourceTitle, sourceType, direId);
                        if(dirSource != null){
                            newDireSourceList.add(dirSource);
                        }
                    }
                }
                if (newDireSourceList != null) {
                    for (DirectorySource direSource : newDireSourceList) {
                        this.createDirectorySources(direSource);
                        logger.info("add DirectorySources success : [" + direSource.getDirectoryId() + "]");
                    }
                    logger.info("add DirectorySources success");
                }
            } else {
                List<Long> delIdList = new ArrayList<Long>();
                List<DirectorySource> addDireSourceList = new ArrayList<DirectorySource>();
                List<Long> subtractSourceDirIds = new ArrayList<Long>();
                Set<Long> existIdSet = new HashSet<Long>(direSourceList.size());

                for (DirectorySource source : direSourceList) {
                    existIdSet.add(source.getDirectoryId());
                }
                //帅选新增加的目录，并将其写入数据库
                for (Long direId : direIds) {
                    if (!(existIdSet.contains(direId))) {
                        DirectorySource tagSource = newDirecotrySource(userId, sourceId, sourceTitle, sourceType, direId);
                        if (tagSource != null) {
                            addDireSourceList.add(tagSource);
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(addDireSourceList)) {
                    for (DirectorySource direSource : addDireSourceList) {
                        this.createDirectorySources(direSource);
                        logger.info("add directorySource success : directoryId [" + direSource.getDirectoryId() + "]");
                    }
                    logger.info("add directorySource success");
                }
                //删除数据库中已被更新的数据
                for (DirectorySource directorySource : direSourceList) {
                    long id = directorySource.getId();
					long dirId = directorySource.getDirectoryId();
                    if (!direIds.contains(dirId)) {
                        delIdList.add(id);
						subtractSourceDirIds.add(dirId);
                    }
                }
				boolean b = directoryService.subtractSourceCountByDirectoryIds(userId, appId, subtractSourceDirIds);
                if (!b) {
                	logger.error("subtractSourceCountByDirectoryIds method : failed!" );
				}
				if (CollectionUtils.isNotEmpty(delIdList)) {
                    for (Long id : delIdList) {
                        logger.info("delete directorySource success : directoryId [" + id + "]");
                    }
                    this.removeDirectorySourcesByDireIds(delIdList);
                    logger.info("delete directorySource success");
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 
	 * @param ex
	 * @return
	 */
	private String getSqlErrorMessage(Throwable ex) {
		if (ex == null) {
			return null;
		} else {
			Throwable t = ex.getCause();
			do {
				if (logger.isDebugEnabled()) {
					logger.debug("package name : " + t.getClass().getPackage().getName());
					logger.debug("message: " + t.getMessage());
				}
				if (t != null && t.getClass().getPackage().getName().startsWith("java.sql")) {
					return t.getMessage();
				}
				t = t.getCause();
			} while (t != null);
		}
		return null;
	}

	private DirectorySource newDirecotrySource(long userId, long sourceId, String sourceTitle, long sourceType, long direcoryId){
		DirectorySource directorySource = new DirectorySource();
		directorySource.setUserId(userId);
		directorySource.setDirectoryId(direcoryId);
		directorySource.setAppId(ServiceError.appId);
		directorySource.setSourceId(sourceId);
		directorySource.setSourceType((int)sourceType);
		directorySource.setSourceTitle(sourceTitle);
		directorySource.setCreateAt(new Date().getTime());
		return directorySource;
	}
}
