package com.ginkgocap.parasol.directory.service.impl;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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

	private static Logger logger = Logger.getLogger(DirectorySourcesServiceImpl.class);
	private static String LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID = "List_DirectorySources_Id_userId_appId_sourceType_sourceId";
	private static String LIST_DIRECTORYSOURCE_ID_DIRECTORYID = "List_DirectorySource_Id_DirectoryId";
	private static String MAP_DIRECTORYSOURCE_ID_USERID_DIRECTORYID_APPID_SOURCETYPE_SOURICEID = "Map_DirectorySource_Id_UserId_DirectoryId_AppId_SourceType_SourceId";

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
			source.setSourceTitle(source.getSourceTitle());
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
	public boolean removeDirectorySourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws DirectorySourceServiceException {
		try {
			List<Long> ids = this.getIds(LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID, userId, appId, sourceType, sourceId);
			return this.deleteEntityByIds(ids);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return false;
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
}
