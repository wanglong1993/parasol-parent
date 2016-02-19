package com.ginkgocap.parasol.favorite.service.impl;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteSourceServiceException;
import com.ginkgocap.parasol.favorite.model.Favorite;
import com.ginkgocap.parasol.favorite.model.FavoriteSource;
import com.ginkgocap.parasol.favorite.service.FavoriteService;
import com.ginkgocap.parasol.favorite.service.FavoriteSourceService;

/**
 * 操作FavoriteSource
 * 
 * @author allenshen
 * @date 2015年11月27日
 * @time 上午9:11:50
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("favoriteSourceService")
public class FavoriteSourcesServiceImpl extends BaseService<FavoriteSource> implements FavoriteSourceService {
	private static int LEN_SOURCE_TITLE = 140;
	private static Logger logger = Logger.getLogger(FavoriteSourcesServiceImpl.class);
	private static String LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID = "List_FavoriteSources_Id_userId_appId_sourceType_sourceId";
	private static String LIST_DIRECTORYSOURCE_ID_DIRECTORYID = "List_FavoriteSource_Id_FavoriteId";
	private static String MAP_DIRECTORYSOURCE_ID_USERID_DIRECTORYID_APPID_SOURCETYPE_SOURICEID = "Map_FavoriteSource_Id_UserId_FavoriteId_AppId_SourceType_SourceId";

	@Autowired
	private FavoriteService favoriteService;

	@Override
	public Long createFavoriteSources(FavoriteSource source) throws FavoriteSourceServiceException {
		ServiceError.assertForFavoriteSource(source);

		Long appId = source.getAppId(); // 资源的应用ID
		// TODO : please implements check App exist?
		logger.info("please implements check App exist?");
		Long userId = source.getUserId(); // 创建Source的人
		Long favoriteId = source.getFavoriteId(); // 收藏夹Id
		Integer sourceType = source.getSourceType(); // 资源类型
		Long sourceId = source.getSourceId(); // 资源ID
		ServiceError.assertPropertiesHaveValue(source, new String[] { "appId", "userId", "favoriteId", "sourceType", "sourceId" });
		// 检查收藏夹情况
		try {
			Favorite favorite = favoriteService.getFavorite(appId, userId, favoriteId);
			if (favorite == null) { // 收藏夹不存在
				throw new FavoriteSourceServiceException(ServiceError.ERROR_NOT_FOUND, "Don't find then Favorite entity by id [" + favoriteId + "]");
			}

			FavoriteSource existSource = this.getFavoriteSource(userId, favoriteId, appId, sourceType, sourceId);
			if (existSource != null) { // 已经存在
				StringBuilder sb = new StringBuilder();
				sb.append("userId=").append(userId).append(" and ");
				sb.append("favoriteId=").append(favoriteId).append(" and ");
				sb.append("appId=").append(appId).append(" and ");
				sb.append("sourceType:=").append(sourceType).append(" and ");
				sb.append("sourceId=").append(sourceId).append(";");
				throw new FavoriteSourceServiceException(ServiceError.ERROR_OBJECT_EXIST, "the FavoriteSource to be created already exist!  " + existSource.getId()
						+ " entity by [" + sb.toString() + "]");
			}
			source.setCreateAt(System.currentTimeMillis());
			// TODO : 过滤敏感词?
			logger.info("please implements 过滤敏感词?");
			source.setSourceTitle(StringUtils.trim(source.getSourceTitle()));
			if (source.getSourceTitle() != null && source.getSourceTitle().length() > LEN_SOURCE_TITLE) {
				throw new FavoriteSourceServiceException(ServiceError.ERROR_NAME_LIMIT, source.getSourceTitle() + "  length can not be more than " + LEN_SOURCE_TITLE);
			}
			source.setSourceData(StringUtils.trim(source.getSourceData()));
			if (source.getSourceData() != null && source.getSourceData().length() > LEN_SOURCE_TITLE) {
				throw new FavoriteSourceServiceException(ServiceError.ERROR_NAME_LIMIT, source.getSourceData() + "  length can not be more than " + LEN_SOURCE_TITLE);
			}
			return (Long) this.saveEntity(source);
		} catch (FavoriteServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e);
		}
	}

	@Override
	public boolean removeFavoriteSources(Long appId, Long userId, Long id) throws FavoriteSourceServiceException {
		try {
			FavoriteSource ds = this.getFavoriteSourceById(appId, id);
			if (ds != null && ObjectUtils.equals(appId, ds.getAppId()) && ObjectUtils.equals(userId, ds.getUserId())) {
				return this.deleteEntity(id);
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("appId=").append(appId);
				sb.append(" and ").append("userId=").append(userId);
				sb.append(" and ").append("id=").append(id);
				throw new FavoriteSourceServiceException(ServiceError.ERROR_NOT_FOUND, "don't find the FavoriteSource by [ " + sb.toString() + " ]");
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e);
		}
	}

	@Override
	public boolean removeFavoriteSourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws FavoriteSourceServiceException {
		try {
			List<Long> ids = this.getIds(LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID, userId, appId, sourceType, sourceId);
			return this.deleteEntityByIds(ids);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<FavoriteSource> getFavoriteSourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws FavoriteSourceServiceException {
		try {
			return this.getEntitys(LIST_DIRECTORYSOURCES_ID_USERID_APPID_SOURCETYPE_SOURCEID, userId, appId, sourceType, sourceId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e);
		}
	}

	@Override
	public boolean moveFavoriteSources(long userId, long appId, Long favoriteId, Long[] ids) throws FavoriteSourceServiceException {

		ServiceError.assertAppIdForFavoriteSource(appId);
		ServiceError.assertUserIdForFavoriteSource(userId);
		ServiceError.assertFavoriteIdForFavoriteSource(favoriteId);

		try {
			Favorite favorite = favoriteService.getFavorite(appId, userId, favoriteId);
			if (favorite == null) {
				StringBuilder sb = new StringBuilder();
				sb.append("appId=").append(appId);
				sb.append(" and ").append("userId=").append(userId);
				sb.append(" and ").append("id=").append(favoriteId);
				throw new FavoriteSourceServiceException(ServiceError.ERROR_NOT_FOUND, "don't find the favorite by [" + sb.toString() + "]");
			}

			if (!ArrayUtils.isEmpty(ids)) {
				for (Long id : ids) {
					if (id != null && id > 0) {
						FavoriteSource ds = this.getFavoriteSourceById(appId, id);
						if (ds != null && ObjectUtils.equals(ds.getAppId(), appId) && ObjectUtils.equals(ds.getUserId(), userId)) {
							ds.setFavoriteId(favoriteId);
							this.updateEntity(ds);
						} else {
							if (ds != null) {
								logger.error("Operation of the non own favorite source");
							} else {
								logger.error("dont find the FavoriteSource by [ userId=" + userId + " and id=" + id + "]");
							}
						}

					}
				}
			}
		} catch (FavoriteServiceException e) {
			throw new FavoriteSourceServiceException(e);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			String msg = getSqlErrorMessage(e);
			if (msg != null) {
				throw new FavoriteSourceServiceException(ServiceError.ERROR_SQL, msg);
			} else {
				throw new FavoriteSourceServiceException(e);

			}
		}
		return true;
	}

	@Override
	public boolean updateFavoriteSource(FavoriteSource source) throws FavoriteSourceServiceException {
		ServiceError.assertForFavoriteSource(source);

		Long appId = source.getAppId(); // 资源的应用ID
		ServiceError.assertAppIdForFavoriteSource(appId);
		// TODO : please implements check App exist?
		logger.info("please implements check App exist?");

		FavoriteSource old = this.getFavoriteSourceById(appId, source.getId());
		ServiceError.assertModifyFields(old, source, new String[] { "appId", "userId", "sourceId", "sourceType", "favoriteId" });
		source.setSourceTitle(StringUtils.trim(source.getSourceTitle()));
		if (source.getSourceTitle() != null && source.getSourceTitle().length() > LEN_SOURCE_TITLE) {
			throw new FavoriteSourceServiceException(ServiceError.ERROR_NAME_LIMIT, source.getSourceTitle() + "  length can not be more than " + LEN_SOURCE_TITLE);
		}
		source.setSourceData(StringUtils.trim(source.getSourceData()));
		if (source.getSourceData() != null && source.getSourceData().length() > LEN_SOURCE_TITLE) {
			throw new FavoriteSourceServiceException(ServiceError.ERROR_NAME_LIMIT, source.getSourceData() + "  length can not be more than " + LEN_SOURCE_TITLE);
		}
		try {
			return this.updateEntity(source);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e.getMessage());
		}
	}

	@Override
	public FavoriteSource getFavoriteSourceById(Long appId, Long id) throws FavoriteSourceServiceException {
		try {
			ServiceError.assertAppIdForFavoriteSource(appId);
			FavoriteSource ds = this.getEntity(id);
			if (ds != null && ObjectUtils.equals(ds.getAppId(), appId)) {
				return ds;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	@Override
	public List<FavoriteSource> getFavoriteSourcesByFavoriteId(Long appId, Long userId, Long favoriteId) throws FavoriteSourceServiceException {
		try {
			Favorite favorite = favoriteService.getFavorite(appId, userId, favoriteId);
			if (favorite == null) {
				StringBuilder builder = new StringBuilder();
				builder.append("appId=").append(appId);
				builder.append(" and ").append("userId=").append(userId);
				builder.append(" and ").append("id=").append(favoriteId);
				throw new FavoriteServiceException(ServiceError.ERROR_NOT_FOUND, "don't find favorite by : " + builder.toString());
			}
			List<FavoriteSource> favoriteSources = this.getEntitys(LIST_DIRECTORYSOURCE_ID_DIRECTORYID, favoriteId);
			return favoriteSources;
		} catch (FavoriteServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e);
		}

	}

	@Override
	public int countFavoriteSourcesByFavoriteId(Long appId, Long userId, Long favoriteId) throws FavoriteSourceServiceException {
		try {
			return this.countEntitys(LIST_DIRECTORYSOURCE_ID_DIRECTORYID, favoriteId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e);
		}
	}

	@Override
	public FavoriteSource getFavoriteSource(Long userId, Long favoriteId, Long sourceAppId, Integer sourceType, Long souriceId) throws FavoriteSourceServiceException {
		try {
			Long id = (Long) this.getMapId(MAP_DIRECTORYSOURCE_ID_USERID_DIRECTORYID_APPID_SOURCETYPE_SOURICEID, userId, favoriteId, sourceAppId, sourceType, souriceId);
			if (id != null) {
				return this.getFavoriteSourceById(sourceAppId, id);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteSourceServiceException(e);
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
